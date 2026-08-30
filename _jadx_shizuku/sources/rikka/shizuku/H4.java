package rikka.shizuku;

import java.io.IOException;
import java.util.Arrays;
import rikka.html.text.style.BulletSpan;

/* JADX INFO: loaded from: classes.dex */
public final class H4 extends AbstractC0633u implements InterfaceC0126df {
    public final int a;
    public final int b;
    public final int c;
    public final InterfaceC0202g d;
    public final /* synthetic */ int e;

    public H4(int i, int i2, int i3, InterfaceC0202g interfaceC0202g) {
        if (interfaceC0202g == null) {
            throw new NullPointerException("'obj' cannot be null");
        }
        if (i2 == 0 || (i2 & 192) != i2) {
            throw new IllegalArgumentException(AbstractC0535qo.b(i2, "invalid tag class: "));
        }
        this.a = interfaceC0202g instanceof InterfaceC0171f ? 1 : i;
        this.b = i2;
        this.c = i3;
        this.d = interfaceC0202g;
    }

    public static AbstractC0633u r(int i, int i2, C0233h c0233h) {
        int i3;
        H4 h4;
        if (c0233h.b == 1) {
            i3 = i;
            h4 = new H4(3, i3, i2, c0233h.f(0), 2);
        } else {
            i3 = i;
            h4 = new H4(4, i, i2, O8.a(c0233h), 2);
        }
        return i3 != 64 ? h4 : new L8(h4);
    }

    @Override // rikka.shizuku.AbstractC0633u, rikka.shizuku.AbstractC0417n
    public final int hashCode() {
        return (((this.b * 7919) ^ this.c) ^ (s() ? 15 : 240)) ^ this.d.d().hashCode();
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final boolean i(AbstractC0633u abstractC0633u) {
        if (abstractC0633u instanceof AbstractC0017a) {
            return abstractC0633u.n(this);
        }
        if (!(abstractC0633u instanceof H4)) {
            return false;
        }
        H4 h4 = (H4) abstractC0633u;
        if (this.c != h4.c || this.b != h4.b) {
            return false;
        }
        if (this.a != h4.a && s() != h4.s()) {
            return false;
        }
        AbstractC0633u abstractC0633uD = this.d.d();
        AbstractC0633u abstractC0633uD2 = h4.d.d();
        if (abstractC0633uD == abstractC0633uD2) {
            return true;
        }
        if (s()) {
            return abstractC0633uD.i(abstractC0633uD2);
        }
        try {
            return Arrays.equals(g(), h4.g());
        } catch (IOException unused) {
            return false;
        }
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final void j(Ys ys, boolean z) throws IOException {
        switch (this.e) {
            case 0:
                AbstractC0633u abstractC0633uD = this.d.d();
                boolean zS = s();
                if (z) {
                    int i = this.b;
                    if (zS || abstractC0633uD.k()) {
                        i |= 32;
                    }
                    ys.J(i, this.c);
                }
                if (!zS) {
                    abstractC0633uD.j(ys, false);
                } else {
                    ys.D(128);
                    abstractC0633uD.j(ys, true);
                    ys.D(0);
                    ys.D(0);
                }
                break;
            case 1:
                AbstractC0633u abstractC0633uP = this.d.d().p();
                boolean zS2 = s();
                if (z) {
                    int i2 = this.b;
                    if (zS2 || abstractC0633uP.k()) {
                        i2 |= 32;
                    }
                    ys.J(i2, this.c);
                }
                if (zS2) {
                    ys.F(abstractC0633uP.m(true));
                }
                abstractC0633uP.j(ys.s(), zS2);
                break;
            default:
                AbstractC0633u abstractC0633uQ = this.d.d().q();
                boolean zS3 = s();
                if (z) {
                    int i3 = this.b;
                    if (zS3 || abstractC0633uQ.k()) {
                        i3 |= 32;
                    }
                    ys.J(i3, this.c);
                }
                if (zS3) {
                    ys.F(abstractC0633uQ.m(true));
                }
                abstractC0633uQ.j(ys.t(), zS3);
                break;
        }
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final boolean k() {
        switch (this.e) {
            case 0:
                return s() || this.d.d().k();
            case 1:
                return s() || this.d.d().p().k();
            default:
                return s() || this.d.d().q().k();
        }
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final int m(boolean z) {
        switch (this.e) {
            case 0:
                AbstractC0633u abstractC0633uD = this.d.d();
                boolean zS = s();
                int iM = abstractC0633uD.m(zS);
                if (zS) {
                    iM += 3;
                }
                return iM + (z ? Ys.w(this.c) : 0);
            case 1:
                AbstractC0633u abstractC0633uP = this.d.d().p();
                boolean zS2 = s();
                int iM2 = abstractC0633uP.m(zS2);
                if (zS2) {
                    iM2 += Ys.u(iM2);
                }
                return iM2 + (z ? Ys.w(this.c) : 0);
            default:
                AbstractC0633u abstractC0633uQ = this.d.d().q();
                boolean zS3 = s();
                int iM3 = abstractC0633uQ.m(zS3);
                if (zS3) {
                    iM3 += Ys.u(iM3);
                }
                return iM3 + (z ? Ys.w(this.c) : 0);
        }
    }

    @Override // rikka.shizuku.AbstractC0633u
    public AbstractC0633u p() {
        switch (this.e) {
            case 1:
                return this;
            default:
                return new H4(this.a, this.b, this.c, this.d, 1);
        }
    }

    @Override // rikka.shizuku.AbstractC0633u
    public AbstractC0633u q() {
        switch (this.e) {
            case 1:
            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                return this;
            default:
                return new H4(this.a, this.b, this.c, this.d, 2);
        }
    }

    public final boolean s() {
        int i = this.a;
        return i == 1 || i == 3;
    }

    public final String toString() {
        return AbstractC0046as.A(this.b, this.c) + this.d;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ H4(int i, int i2, int i3, InterfaceC0202g interfaceC0202g, int i4) {
        this(i, i2, i3, interfaceC0202g);
        this.e = i4;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public H4(boolean z, int i, InterfaceC0202g interfaceC0202g, int i2) {
        this(z ? 1 : 2, 128, i, interfaceC0202g);
        this.e = i2;
    }

    @Override // rikka.shizuku.InterfaceC0126df
    public final AbstractC0633u a() {
        return this;
    }
}
