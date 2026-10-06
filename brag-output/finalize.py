"""Final delivery encode for the Project RETR0 brag video.

brag.raw.mp4 (Hyperframes render)  ->  brag.mp4
  * removes the AdGuard assistant badge that the machine-level AdGuard service injects into the
    render browser's pages (bottom-right corner; every scene is plain background there)
  * bakes brag.jpg (the poster) into frame 0 so every platform's idle thumbnail is the poster
  * two-pass loudness normalisation to -16 LUFS / -1.5 dBTP (social-feed level)
"""
import json, os, re, subprocess, sys

HERE = os.path.dirname(os.path.abspath(__file__))
RAW, POSTER, OUT = (os.path.join(HERE, n) for n in ("brag.raw.mp4", "brag.jpg", "brag.mp4"))
DELOGO = "delogo=x=1862:y=1022:w=56:h=56"
POSTER_T = 8.5  # settled beat: "Swap the skin." + Aurora Drift flipped to Active

def run(cmd):
    r = subprocess.run(cmd, capture_output=True, text=True)
    if r.returncode != 0:
        print(" ".join(cmd)); print(r.stderr[-2500:]); sys.exit(1)
    return r

# poster from the same render, badge removed, at full res
run(["ffmpeg", "-hide_banner", "-loglevel", "error", "-y", "-ss", str(POSTER_T), "-i", RAW,
     "-frames:v", "1", "-vf", DELOGO, "-q:v", "2", POSTER])

# pass 1: measure
r = run(["ffmpeg", "-hide_banner", "-i", RAW, "-af", "loudnorm=I=-16:TP=-1.5:LRA=11:print_format=json",
         "-vn", "-f", "null", "-"])
m = json.loads(re.search(r"\{[^{}]*\"input_i\"[^{}]*\}", r.stderr, re.S).group(0))
print("measured:", {k: m[k] for k in ("input_i", "input_tp", "input_lra", "input_thresh", "target_offset")})
ln = (f"loudnorm=I=-16:TP=-1.5:LRA=11:measured_I={m['input_i']}:measured_TP={m['input_tp']}"
      f":measured_LRA={m['input_lra']}:measured_thresh={m['input_thresh']}:offset={m['target_offset']}:linear=true")

# pass 2: encode (badge removed + poster on frame 0 + normalised audio)
tmp = os.path.join(HERE, "brag.final.tmp.mp4")
run(["ffmpeg", "-hide_banner", "-loglevel", "error", "-y", "-i", RAW, "-i", POSTER,
     "-filter_complex", f"[0:v]{DELOGO}[c];[c][1:v]overlay=0:0:enable='eq(n,0)'[v]",
     "-map", "[v]", "-map", "0:a", "-af", ln,
     "-c:v", "libx264", "-crf", "17", "-preset", "slow", "-pix_fmt", "yuv420p",
     "-c:a", "aac", "-b:a", "192k", "-ar", "48000", "-movflags", "+faststart", tmp])
os.replace(tmp, OUT)
print("ok:", OUT, round(os.path.getsize(OUT) / 1e6, 2), "MB")
