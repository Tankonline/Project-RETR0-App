package rikka.shizuku;

import java.io.IOException;
import rikka.html.text.style.BulletSpan;

/* JADX INFO: loaded from: classes.dex */
public final class F4 implements InterfaceC0202g, InterfaceC0126df {
    public final /* synthetic */ int a;
    public C0788z b;

    public /* synthetic */ F4(int i) {
        this.a = i;
    }

    public static C0611t8 e(C0788z c0788z) throws C0293j {
        try {
            return new C0611t8(O8.a(c0788z.f()));
        } catch (IllegalArgumentException e) {
            throw new C0293j(e.getMessage(), e);
        }
    }

    @Override // rikka.shizuku.InterfaceC0126df
    public final AbstractC0633u a() throws IOException {
        switch (this.a) {
            case 0:
                return new E4(this.b.f());
            case 1:
                return new G4(this.b.f());
            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                return e(this.b);
            case 3:
                return O8.a(this.b.f());
            default:
                C0233h c0233hF = this.b.f();
                Q8 q8 = O8.a;
                if (c0233hF.b < 1) {
                    return O8.b;
                }
                R8 r8 = new R8(c0233hF);
                r8.c = -1;
                return r8;
        }
    }

    @Override // rikka.shizuku.InterfaceC0202g
    public final AbstractC0633u d() {
        switch (this.a) {
            case 0:
                try {
                    return new E4(this.b.f());
                } catch (IOException e) {
                    throw new IllegalStateException(e.getMessage());
                }
            case 1:
                try {
                    return new G4(this.b.f());
                } catch (IOException e2) {
                    throw new C0602t(e2.getMessage(), e2);
                }
            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                try {
                    return e(this.b);
                } catch (IOException e3) {
                    throw new C0602t("unable to get DER object", e3);
                } catch (IllegalArgumentException e4) {
                    throw new C0602t("unable to get DER object", e4);
                }
            case 3:
                try {
                    return a();
                } catch (IOException e5) {
                    throw new IllegalStateException(e5.getMessage());
                }
            default:
                try {
                    return a();
                } catch (IOException e6) {
                    throw new C0602t(e6.getMessage(), e6);
                }
        }
    }

    public F4(C0788z c0788z) {
        this.a = 2;
        this.b = c0788z;
    }
}
