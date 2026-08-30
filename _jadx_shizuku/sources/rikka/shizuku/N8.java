package rikka.shizuku;

import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
public final class N8 implements InterfaceC0110d {
    public final I9 a;
    public int b = 0;

    public N8(I9 i9) {
        this.a = i9;
    }

    @Override // rikka.shizuku.InterfaceC0126df
    public final AbstractC0633u a() {
        return AbstractC0079c.r(this.a.b());
    }

    @Override // rikka.shizuku.InterfaceC0110d
    public final InputStream b() throws IOException {
        I9 i9 = this.a;
        int i = i9.d;
        if (i < 1) {
            throw new IllegalStateException("content octets cannot be empty");
        }
        int i2 = i9.read();
        this.b = i2;
        if (i2 > 0) {
            if (i < 2) {
                throw new IllegalStateException("zero length data with non-zero pad bits");
            }
            if (i2 > 7) {
                throw new IllegalStateException("pad bits cannot be greater than 7 or less than 0");
            }
        }
        return i9;
    }

    @Override // rikka.shizuku.InterfaceC0202g
    public final AbstractC0633u d() {
        try {
            return a();
        } catch (IOException e) {
            throw new C0602t("IOException converting stream to byte array: " + e.getMessage(), e);
        }
    }

    @Override // rikka.shizuku.InterfaceC0110d
    public final int f() {
        return this.b;
    }
}
