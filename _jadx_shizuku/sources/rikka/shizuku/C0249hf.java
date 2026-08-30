package rikka.shizuku;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: renamed from: rikka.shizuku.hf, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0249hf extends Cg {
    public int c;
    public int d;
    public boolean e;
    public boolean f;

    public C0249hf(InputStream inputStream, int i) throws IOException {
        super(inputStream, i);
        this.e = false;
        this.f = true;
        this.c = inputStream.read();
        int i2 = inputStream.read();
        this.d = i2;
        if (i2 < 0) {
            throw new EOFException();
        }
        b();
    }

    public final boolean b() {
        if (!this.e && this.f && this.c == 0 && this.d == 0) {
            this.e = true;
            a();
        }
        return this.e;
    }

    @Override // java.io.InputStream
    public final int read() throws IOException {
        if (b()) {
            return -1;
        }
        int i = this.a.read();
        if (i < 0) {
            throw new EOFException();
        }
        int i2 = this.c;
        this.c = this.d;
        this.d = i;
        return i2;
    }

    @Override // java.io.InputStream
    public final int read(byte[] bArr, int i, int i2) throws IOException {
        if (this.f || i2 < 3) {
            return super.read(bArr, i, i2);
        }
        if (this.e) {
            return -1;
        }
        InputStream inputStream = this.a;
        int i3 = inputStream.read(bArr, i + 2, i2 - 2);
        if (i3 < 0) {
            throw new EOFException();
        }
        bArr[i] = (byte) this.c;
        bArr[i + 1] = (byte) this.d;
        this.c = inputStream.read();
        int i4 = inputStream.read();
        this.d = i4;
        if (i4 >= 0) {
            return i3 + 2;
        }
        throw new EOFException();
    }
}
