#include <jni.h>
#include <android/log.h>
#include <cerrno>
#include <cstdint>
#include <cstring>
#include <fcntl.h>
#include <limits>
#include <set>
#include <string>
#include <sys/stat.h>
#include <sys/types.h>
#include <unistd.h>
#include <vector>
#include <zlib.h>

#define LOG_TAG "SonicExtractor"
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

namespace {
constexpr uint32_t LOCAL = 0x04034b50;
constexpr uint32_t CENTRAL = 0x02014b50;
constexpr uint32_t EOCD = 0x06054b50;
constexpr uint32_t DD = 0x08074b50;
constexpr uint32_t ZIP64_EOCD = 0x06064b50;
constexpr uint32_t ZIP64_LOCATOR = 0x07064b50;
constexpr uint16_t FLAG_ENCRYPTED = 1;
constexpr uint16_t FLAG_DESCRIPTOR = 1u << 3;
constexpr uint16_t FLAG_RESERVED = 1u << 4;
constexpr uint16_t FLAG_PATCHED = 1u << 5;
constexpr uint16_t FLAG_STRONG_ENCRYPTION = 1u << 6;
constexpr uint16_t FLAG_UTF8 = 1u << 11;
constexpr uint16_t FLAG_MASKED = 1u << 13;
constexpr size_t MAX_NAME = 4096;
constexpr size_t BUFFER_SIZE = 256 * 1024;

class Reader {
    int fd_;
    std::vector<uint8_t> buffer_ = std::vector<uint8_t>(BUFFER_SIZE);
    size_t pos_ = 0;
    size_t end_ = 0;
public:
    explicit Reader(int fd) : fd_(fd) {}
    ssize_t read(void* dst, size_t count) {
        size_t done = 0;
        auto* out = static_cast<uint8_t*>(dst);
        while (done < count) {
            if (pos_ == end_) {
                ssize_t n;
                do { n = ::read(fd_, buffer_.data(), buffer_.size()); } while (n < 0 && errno == EINTR);
                if (n <= 0) return done == 0 ? n : static_cast<ssize_t>(done);
                pos_ = 0; end_ = static_cast<size_t>(n);
            }
            size_t take = std::min(count - done, end_ - pos_);
            if (out != nullptr) std::memcpy(out + done, buffer_.data() + pos_, take);
            pos_ += take; done += take;
        }
        return static_cast<ssize_t>(done);
    }
    bool skip(uint64_t count) {
        while (count) {
            ssize_t n = read(nullptr, static_cast<size_t>(std::min<uint64_t>(count, BUFFER_SIZE)));
            if (n <= 0) return false;
            count -= static_cast<uint64_t>(n);
        }
        return true;
    }
    ssize_t available() {
        if (pos_ == end_) {
            ssize_t n;
            do { n = ::read(fd_, buffer_.data(), buffer_.size()); } while (n < 0 && errno == EINTR);
            if (n <= 0) return n;
            pos_ = 0; end_ = static_cast<size_t>(n);
        }
        return static_cast<ssize_t>(end_ - pos_);
    }
    const uint8_t* data() const { return buffer_.data() + pos_; }
    void consume(size_t count) { pos_ += count; }
};

bool read16(Reader& r, uint16_t& value) { uint8_t b[2]; if (r.read(b, 2) != 2) return false; value = b[0] | (uint16_t(b[1]) << 8); return true; }
bool read32(Reader& r, uint32_t& value) { uint8_t b[4]; if (r.read(b, 4) != 4) return false; value = uint32_t(b[0]) | (uint32_t(b[1]) << 8) | (uint32_t(b[2]) << 16) | (uint32_t(b[3]) << 24); return true; }
bool read64(Reader& r, uint64_t& value) { uint32_t lo, hi; if (!read32(r, lo) || !read32(r, hi)) return false; value = lo | (uint64_t(hi) << 32); return true; }

bool safe_name(const std::string& name) {
    if (name.empty() || name.find('\0') != std::string::npos || name[0] == '/' || name[0] == '\\') return false;
    if (name.size() > MAX_NAME || name.find(':') != std::string::npos) return false;
    size_t start = 0;
    while (start <= name.size()) {
        size_t slash = name.find('/', start);
        std::string part = name.substr(start, slash == std::string::npos ? std::string::npos : slash - start);
        if (part == ".." || part.empty() && slash != std::string::npos) return false;
        if (slash == std::string::npos) break;
        start = slash + 1;
    }
    return true;
}

std::string map_rr3_entry(const std::string& name) {
    static const std::string rowPrefix = "com.ea.games.r3_row/";
    static const std::string naPrefix = "com.ea.games.r3_na/";
    if (name.rfind(rowPrefix, 0) == 0) return name.substr(rowPrefix.size());
    if (name.rfind(naPrefix, 0) == 0) return name.substr(naPrefix.size());
    return name;
}

bool contained(const std::string& root, const std::string& path) {
    std::string prefix = root;
    if (prefix.empty() || prefix.back() != '/') prefix.push_back('/');
    return path.rfind(prefix, 0) == 0;
}

bool mkdir_secure(const std::string& root, const std::string& relative) {
    int dir = open(root.c_str(), O_RDONLY | O_DIRECTORY | O_CLOEXEC | O_NOFOLLOW);
    if (dir < 0) return false;
    size_t start = 0;
    while (start < relative.size()) {
        size_t slash = relative.find('/', start);
        std::string part = relative.substr(start, slash == std::string::npos ? std::string::npos : slash - start);
        if (!part.empty()) {
            if (mkdirat(dir, part.c_str(), 0755) != 0 && errno != EEXIST) { close(dir); return false; }
            int next = openat(dir, part.c_str(), O_RDONLY | O_DIRECTORY | O_CLOEXEC | O_NOFOLLOW);
            if (next < 0) { close(dir); return false; }
            close(dir); dir = next;
        }
        if (slash == std::string::npos) break;
        start = slash + 1;
    }
    return close(dir) == 0;
}

bool skip_descriptor(Reader& r, uint32_t& crc, uint64_t& compressed, uint64_t& uncompressed) {
    uint32_t first;
    if (!read32(r, first)) return false;
    if (first == DD) { if (!read32(r, crc)) return false; }
    else crc = first;
    uint32_t c, u;
    if (!read32(r, c) || !read32(r, u)) return false;
    compressed = c; uncompressed = u;
    return true;
}

bool unsupported_extra(const std::vector<uint8_t>& extra) {
    size_t p = 0;
    while (p + 4 <= extra.size()) {
        uint16_t id = extra[p] | (uint16_t(extra[p + 1]) << 8);
        uint16_t len = extra[p + 2] | (uint16_t(extra[p + 3]) << 8);
        p += 4;
        if (p + len > extra.size()) return true;
        if (id == 0x0001) return true; // ZIP64 is explicitly rejected.
        p += len;
    }
    return p != extra.size();
}

bool extract_one(Reader& r, uint16_t method, uint16_t flags, uint64_t expectedC, uint64_t expectedU, uint32_t expectedCrc, int out, bool descriptor) {
    uint64_t compressed = 0, uncompressed = 0;
    uint32_t crc = crc32(0L, Z_NULL, 0);
    std::vector<uint8_t> outBuf(BUFFER_SIZE);
    bool ok = true;
    if (method == 0) {
        while (!descriptor && compressed < expectedC || descriptor && compressed < std::numeric_limits<uint64_t>::max()) {
            size_t want = descriptor ? BUFFER_SIZE : static_cast<size_t>(std::min<uint64_t>(BUFFER_SIZE, expectedC - compressed));
            if (!descriptor && want == 0) break;
            ssize_t n = r.read(outBuf.data(), want);
            if (n <= 0) { ok = false; break; }
            if (write(out, outBuf.data(), static_cast<size_t>(n)) != n) { ok = false; break; }
            crc = crc32(crc, outBuf.data(), static_cast<uInt>(n)); compressed += n; uncompressed += n;
            if (descriptor && n < static_cast<ssize_t>(want)) { ok = false; break; }
            if (descriptor && r.available() < 4) { ok = false; break; }
            if (descriptor && compressed >= expectedC && expectedC != 0) break;
        }
    } else if (method == 8) {
        z_stream z{};
        if (inflateInit2(&z, -MAX_WBITS) != Z_OK) return false;
        int ret = Z_OK;
        while (ret != Z_STREAM_END) {
            ssize_t avail = r.available();
            if (avail <= 0) { ok = false; break; }
            z.next_in = const_cast<Bytef*>(reinterpret_cast<const Bytef*>(r.data())); z.avail_in = static_cast<uInt>(avail);
            z.next_out = outBuf.data(); z.avail_out = static_cast<uInt>(outBuf.size());
            ret = inflate(&z, Z_NO_FLUSH);
            size_t consumed = static_cast<size_t>(avail) - z.avail_in; r.consume(consumed); compressed += consumed;
            size_t produced = outBuf.size() - z.avail_out;
            if (produced && write(out, outBuf.data(), produced) != static_cast<ssize_t>(produced)) { ok = false; break; }
            if (produced) { crc = crc32(crc, outBuf.data(), static_cast<uInt>(produced)); uncompressed += produced; }
            if (ret != Z_OK && ret != Z_STREAM_END) { ok = false; break; }
        }
        inflateEnd(&z);
    } else { LOGE("Unsupported compression method: %u", method); return false; }
    if (descriptor) { uint64_t dc, du; uint32_t dcrc; if (!skip_descriptor(r, dcrc, dc, du)) return false; expectedCrc = dcrc; expectedC = dc; expectedU = du; }
    return ok && compressed == expectedC && uncompressed == expectedU && crc == expectedCrc;
}
} // namespace

extern "C" JNIEXPORT jint JNICALL Java_com_example_projectretr0installer_Retr0UserService_nativeExtractPrivileged(JNIEnv* env, jobject, jint fd, jstring destPath) {
    if (!destPath) { LOGE("Missing destination path"); return -1; }
    const char* raw = env->GetStringUTFChars(destPath, nullptr); if (!raw) { LOGE("Unable to read destination path"); return -1; }
    std::string root(raw); env->ReleaseStringUTFChars(destPath, raw);
    if (!root.empty() && root.back() == '/') root.pop_back();
    if (root.empty()) { LOGE("Empty destination path"); return -1; }
    struct stat rootSt{};
    if (stat(root.c_str(), &rootSt) != 0) { LOGE("Destination stat failed for %s: %s", root.c_str(), strerror(errno)); return -1; }
    if (!S_ISDIR(rootSt.st_mode)) { LOGE("Destination is not a directory: %s", root.c_str()); return -1; }
    LOGI("Starting extraction to %s", root.c_str());
    Reader r(fd); std::set<std::string> entries; std::vector<std::string> committed;
    int count = 0; bool archiveDone = false;
    while (!archiveDone) {
        uint32_t sig; if (!read32(r, sig)) { LOGE("Truncated archive signature"); goto fail; }
        // Local entries are complete once the central directory begins. The remaining directory
        // metadata is not needed by this forward-only streaming extractor.
        if (sig == CENTRAL || sig == EOCD || sig == ZIP64_EOCD || sig == ZIP64_LOCATOR) {
            archiveDone = true;
            break;
        }
        if (sig != LOCAL) { LOGE("Malformed local header signature: 0x%08x", sig); goto fail; }
        uint16_t version, flags, method, time, date, nameLen, extraLen; uint32_t expectedCrc, c32, u32;
        if (!read16(r, version) || !read16(r, flags) || !read16(r, method) || !read16(r, time) || !read16(r, date) || !read32(r, expectedCrc) || !read32(r, c32) || !read32(r, u32) || !read16(r, nameLen) || !read16(r, extraLen)) { LOGE("Truncated local ZIP header after %d files", count); goto fail; }
        if ((flags & (FLAG_ENCRYPTED | FLAG_RESERVED | FLAG_PATCHED | FLAG_STRONG_ENCRYPTION | FLAG_MASKED)) != 0 || !(flags & FLAG_UTF8) && false) { LOGE("Unsupported/encrypted ZIP flags: 0x%04x", flags); goto fail; }
        if (nameLen == 0 || nameLen > MAX_NAME) { LOGE("Invalid ZIP entry name length: %u", nameLen); goto fail; }
        std::string name(nameLen, '\0'); if (r.read(name.data(), nameLen) != nameLen) { LOGE("Truncated ZIP entry name"); goto fail; }
        std::vector<uint8_t> extra(extraLen); if (extraLen && r.read(extra.data(), extraLen) != extraLen) { LOGE("Truncated extra field for %s", name.c_str()); goto fail; }
        if (unsupported_extra(extra)) { LOGE("Unsupported ZIP64/malformed extra field"); goto fail; }
        if (!safe_name(name)) { LOGE("Unsafe ZIP path rejected"); goto fail; }
        bool directory = name.back() == '/';
        if (!entries.insert(name).second) { LOGE("Duplicate ZIP entry rejected"); goto fail; }
        std::string relative = map_rr3_entry(name);
        if (directory && !relative.empty() && relative.back() == '/') relative.pop_back();
        // An archive may contain only the package directory itself. Its contents map to root.
        if (relative.empty()) continue;
        if (!safe_name(relative)) { LOGE("Unsafe mapped ZIP path rejected: %s", relative.c_str()); goto fail; }
        std::string full = root + "/" + relative;
        if (!contained(root, full)) goto fail;
        if (directory) { if (!mkdir_secure(root, relative)) { LOGE("Cannot create directory %s: %s", full.c_str(), strerror(errno)); goto fail; } continue; }
        const size_t lastSlash = relative.find_last_of('/');
        const std::string parent = lastSlash == std::string::npos ? std::string() : relative.substr(0, lastSlash);
        if (!parent.empty() && !mkdir_secure(root, parent)) { LOGE("Cannot create parent for %s: %s", full.c_str(), strerror(errno)); goto fail; }
        std::string temp = full + ".retr0-partial-" + std::to_string(count) + ".tmp";
        unlink(temp.c_str());
        int out = open(temp.c_str(), O_WRONLY | O_CREAT | O_EXCL | O_CLOEXEC | O_NOFOLLOW, 0644);
        if (out < 0) { LOGE("Cannot open output %s: %s", temp.c_str(), strerror(errno)); goto fail; }
        bool ok = extract_one(r, method, flags, c32, u32, expectedCrc, out, (flags & FLAG_DESCRIPTOR) != 0);
        if (fsync(out) != 0 || close(out) != 0) ok = false;
        if (!ok) { LOGE("Data/CRC extraction failed for %s (method=%u flags=0x%04x c=%u u=%u)", name.c_str(), method, flags, c32, u32); unlink(temp.c_str()); goto fail; }
        if (rename(temp.c_str(), full.c_str()) != 0) { LOGE("Cannot commit %s: %s", full.c_str(), strerror(errno)); unlink(temp.c_str()); goto fail; }
        committed.push_back(full); ++count;
    }
    LOGI("Extraction finished with %d files", count);
    return count > 0 ? count : -1;
fail:
    LOGE("Extraction aborted after %d files; rolling back %zu files", count, committed.size());
    for (const auto& path : committed) unlink(path.c_str());
    return -1;
}

extern "C" JNIEXPORT jint JNICALL Java_com_example_projectretr0installer_installer_SonicInstaller_nativeExtract(JNIEnv* env, jobject thiz, jint fd, jstring destPath) {
    return Java_com_example_projectretr0installer_Retr0UserService_nativeExtractPrivileged(env, thiz, fd, destPath);
}
