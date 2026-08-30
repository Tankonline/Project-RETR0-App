package rikka.shizuku;

import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
public final class A4 implements InterfaceC0110d {
    public final C0788z a;
    public C0487p7 b;

    public A4(C0788z c0788z) {
        this.a = c0788z;
    }

    public static C0793z4 e(C0788z c0788z) {
        C0487p7 c0487p7 = new C0487p7(c0788z);
        return new C0793z4(Hn.k(c0487p7), c0487p7.c);
    }

    @Override // rikka.shizuku.InterfaceC0126df
    public final AbstractC0633u a() {
        return e(this.a);
    }

    @Override // rikka.shizuku.InterfaceC0110d
    public final InputStream b() {
        C0487p7 c0487p7 = new C0487p7(this.a);
        this.b = c0487p7;
        return c0487p7;
    }

    @Override // rikka.shizuku.InterfaceC0202g
    public final AbstractC0633u d() {
        try {
            return e(this.a);
        } catch (IOException e) {
            throw new C0602t("IOException converting stream to byte array: " + e.getMessage(), e);
        }
    }

    @Override // rikka.shizuku.InterfaceC0110d
    public final int f() {
        return this.b.c;
    }
}
