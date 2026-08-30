package rikka.shizuku;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
public final class I9 extends Cg {
    public static final byte[] e = new byte[0];
    public final int c;
    public int d;

    public I9(InputStream inputStream, int i, int i2) {
        super(inputStream, i2);
        if (i <= 0) {
            if (i < 0) {
                throw new IllegalArgumentException("negative lengths not allowed");
            }
            a();
        }
        this.c = i;
        this.d = i;
    }

    public final byte[] b() {
        int i = this.d;
        if (i == 0) {
            return e;
        }
        int i2 = this.b;
        if (i >= i2) {
            throw new IOException("corrupted stream - out of bounds length found: " + this.d + " >= " + i2);
        }
        byte[] bArr = new byte[i];
        int iL = i - Hn.l(this.a, bArr, i);
        this.d = iL;
        if (iL == 0) {
            a();
            return bArr;
        }
        throw new EOFException("DEF length " + this.c + " object truncated by " + this.d);
    }

    @Override // java.io.InputStream
    public final int read() throws IOException {
        if (this.d == 0) {
            return -1;
        }
        int i = this.a.read();
        if (i >= 0) {
            int i2 = this.d - 1;
            this.d = i2;
            if (i2 == 0) {
                a();
            }
            return i;
        }
        throw new EOFException("DEF length " + this.c + " object truncated by " + this.d);
    }

    @Override // java.io.InputStream
    public final int read(byte[] bArr, int i, int i2) throws IOException {
        int i3 = this.d;
        if (i3 == 0) {
            return -1;
        }
        int i4 = this.a.read(bArr, i, Math.min(i2, i3));
        if (i4 >= 0) {
            int i5 = this.d - i4;
            this.d = i5;
            if (i5 == 0) {
                a();
            }
            return i4;
        }
        throw new EOFException("DEF length " + this.c + " object truncated by " + this.d);
    }
}
