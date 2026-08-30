package rikka.shizuku;

import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
public final class D4 implements InterfaceC0571s {
    public final /* synthetic */ int a = 1;
    public Object b;

    public /* synthetic */ D4() {
    }

    public static C4 e(C0788z c0788z) {
        return new C4(Hn.k(new C0518q7(c0788z)), null);
    }

    @Override // rikka.shizuku.InterfaceC0126df
    public final AbstractC0633u a() {
        switch (this.a) {
            case 0:
                return e((C0788z) this.b);
            default:
                return new A8(((I9) this.b).b());
        }
    }

    @Override // rikka.shizuku.InterfaceC0571s
    public final InputStream c() {
        switch (this.a) {
            case 0:
                return new C0518q7((C0788z) this.b);
            default:
                return (I9) this.b;
        }
    }

    @Override // rikka.shizuku.InterfaceC0202g
    public final AbstractC0633u d() {
        switch (this.a) {
            case 0:
                try {
                    return e((C0788z) this.b);
                } catch (IOException e) {
                    throw new C0602t("IOException converting stream to byte array: " + e.getMessage(), e);
                }
            default:
                try {
                    return a();
                } catch (IOException e2) {
                    throw new C0602t("IOException converting stream to byte array: " + e2.getMessage(), e2);
                }
        }
    }

    public D4(C0788z c0788z) {
        this.b = c0788z;
    }
}
