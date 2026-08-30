package moe.shizuku.manager.adb;

/* JADX INFO: loaded from: classes.dex */
final class PairingContext {
    public final long a;
    public final byte[] b;

    public PairingContext(long j) {
        this.a = j;
        this.b = nativeMsg(j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final native long nativeConstructor(boolean z, byte[] bArr);

    private final native byte[] nativeDecrypt(long j, byte[] bArr);

    private final native void nativeDestroy(long j);

    private final native byte[] nativeEncrypt(long j, byte[] bArr);

    private final native boolean nativeInitCipher(long j, byte[] bArr);

    private final native byte[] nativeMsg(long j);

    public final byte[] b(byte[] bArr) {
        return nativeDecrypt(this.a, bArr);
    }

    public final void c() {
        nativeDestroy(this.a);
    }

    public final byte[] d(byte[] bArr) {
        return nativeEncrypt(this.a, bArr);
    }

    public final boolean e(byte[] bArr) {
        return nativeInitCipher(this.a, bArr);
    }
}
