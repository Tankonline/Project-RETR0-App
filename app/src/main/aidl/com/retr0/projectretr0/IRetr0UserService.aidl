package com.retr0.projectretr0;

interface IRetr0UserService {

    boolean ping() = 1;

    boolean exists(String path) = 2;

    boolean isDirectory(String path) = 3;

    boolean createDirectory(String path) = 4;

    boolean delete(String path) = 5;

    boolean copyFile(String source, String destination) = 6;

    boolean move(String source, String destination) = 7;

    boolean copyDirectory(String source, String destination) = 15;

    boolean importFile(in ParcelFileDescriptor pfd, String destination) = 16;

    boolean exportFile(String source, in ParcelFileDescriptor pfd) = 17;

    long getFileSize(String path) = 8;

    long getLastModified(String path) = 18;

    String[] listFiles(String path) = 9;

    byte[] readFile(String path) = 10;

    boolean writeFile(String path, in byte[] data) = 11;

    long[] getDirectoryStats(String path) = 12;

    String testRR3Access() = 13;

    String testRR3Write() = 14;

    void forceStopPackage(String packageName) = 20;

    boolean isPackageRunning(String packageName) = 21;

    boolean dumpLogcat(String destinationPath) = 22;

    boolean applyPatch(String targetPath, String patchPath) = 23;

    int nativeExtract(in ParcelFileDescriptor pfd, String destPath) = 24;

    String[] listDirectoryEntries(String packageRoot, String path) = 25;

    String[] scanSkinTargets(String packageRoot, String vehiclesRoot, String importedFileName, int maxDirectories, int maxEntriesPerDirectory) = 30;

    String sha256File(String path) = 26;

    boolean createImmutableSkinBackup(String targetPath, String backupPath, String expectedSha256) = 27;

    boolean atomicImportSkin(in ParcelFileDescriptor pfd, String targetPath, String expectedSha256, long expectedSize) = 28;

    boolean restoreSkinBackup(String backupPath, String targetPath, String expectedBackupSha256) = 29;

    void destroy() = 16777114;
}
