package rikka.shizuku;

import java.io.IOException;

/* JADX INFO: renamed from: rikka.shizuku.t8, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0611t8 extends AbstractC0633u {
    public C0510q a;
    public C0386m b;
    public AbstractC0633u c;
    public int d;
    public AbstractC0633u e;
    public final /* synthetic */ int f = 0;

    public /* synthetic */ C0611t8() {
    }

    public static AbstractC0633u s(AbstractC0726x abstractC0726x, int i) {
        if (abstractC0726x.size() > i) {
            return abstractC0726x.u(i).d();
        }
        throw new IllegalArgumentException("too few objects in input sequence");
    }

    @Override // rikka.shizuku.AbstractC0633u, rikka.shizuku.AbstractC0417n
    public final int hashCode() {
        C0510q c0510q = this.a;
        int iHashCode = c0510q == null ? 0 : c0510q.hashCode();
        C0386m c0386m = this.b;
        int iHashCode2 = iHashCode ^ (c0386m == null ? 0 : c0386m.hashCode());
        AbstractC0633u abstractC0633u = this.c;
        return ((iHashCode2 ^ (abstractC0633u != null ? abstractC0633u.hashCode() : 0)) ^ this.d) ^ this.e.hashCode();
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final boolean i(AbstractC0633u abstractC0633u) {
        C0611t8 c0611t8;
        Object obj;
        C0510q c0510q;
        if (this == abstractC0633u) {
            return true;
        }
        if (!(abstractC0633u instanceof C0611t8) || ((c0510q = this.a) != (obj = (c0611t8 = (C0611t8) abstractC0633u).a) && (c0510q == null || obj == null || !c0510q.equals(obj)))) {
            return false;
        }
        C0386m c0386m = this.b;
        C0386m c0386m2 = c0611t8.b;
        if (c0386m != c0386m2 && (c0386m == null || c0386m2 == null || !c0386m.equals(c0386m2))) {
            return false;
        }
        AbstractC0633u abstractC0633u2 = this.c;
        AbstractC0633u abstractC0633u3 = c0611t8.c;
        return (abstractC0633u2 == abstractC0633u3 || !(abstractC0633u2 == null || abstractC0633u3 == null || !abstractC0633u2.equals(abstractC0633u3))) && this.d == c0611t8.d && this.e.n(c0611t8.e);
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final void j(Ys ys, boolean z) throws IOException {
        ys.K(40, z);
        r().j(ys, false);
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final boolean k() {
        return true;
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final int m(boolean z) {
        return r().m(z);
    }

    @Override // rikka.shizuku.AbstractC0633u
    public AbstractC0633u p() {
        switch (this.f) {
            case 0:
                return this;
            default:
                C0611t8 c0611t8 = new C0611t8();
                c0611t8.a = this.a;
                c0611t8.b = this.b;
                c0611t8.c = this.c;
                int i = this.d;
                if (i < 0 || i > 2) {
                    throw new IllegalArgumentException(AbstractC0535qo.b(i, "invalid encoding value: "));
                }
                c0611t8.d = i;
                AbstractC0633u abstractC0633u = this.e;
                if (i != 1) {
                    if (i == 2 && !AbstractC0079c.class.isInstance(abstractC0633u)) {
                        throw new IllegalStateException("unexpected object: ".concat(abstractC0633u.getClass().getName()));
                    }
                } else if (!r.class.isInstance(abstractC0633u)) {
                    throw new IllegalStateException("unexpected object: ".concat(abstractC0633u.getClass().getName()));
                }
                c0611t8.e = abstractC0633u;
                return c0611t8;
        }
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final AbstractC0633u q() {
        int i = this.f;
        return this;
    }

    public final AbstractC0726x r() {
        switch (this.f) {
            case 0:
                C0233h c0233h = new C0233h(4);
                C0510q c0510q = this.a;
                if (c0510q != null) {
                    c0233h.d(c0510q);
                }
                C0386m c0386m = this.b;
                if (c0386m != null) {
                    c0233h.d(c0386m);
                }
                AbstractC0633u abstractC0633u = this.c;
                if (abstractC0633u != null) {
                    c0233h.d(abstractC0633u.p());
                }
                int i = this.d;
                c0233h.d(new H4(i == 0, i, this.e, 1));
                D8 d8 = new D8(c0233h);
                d8.c = -1;
                return d8;
            default:
                C0233h c0233h2 = new C0233h(4);
                C0510q c0510q2 = this.a;
                if (c0510q2 != null) {
                    c0233h2.d(c0510q2);
                }
                C0386m c0386m2 = this.b;
                if (c0386m2 != null) {
                    c0233h2.d(c0386m2);
                }
                AbstractC0633u abstractC0633u2 = this.c;
                if (abstractC0633u2 != null) {
                    c0233h2.d(abstractC0633u2.q());
                }
                int i2 = this.d;
                c0233h2.d(new H4(i2 == 0, i2, this.e, 2));
                Q8 q8 = new Q8(c0233h2);
                q8.c = -1;
                return q8;
        }
    }

    public C0611t8(AbstractC0726x abstractC0726x) {
        int i;
        AbstractC0633u abstractC0633uD;
        AbstractC0633u abstractC0633uS = s(abstractC0726x, 0);
        if (abstractC0633uS instanceof C0510q) {
            this.a = (C0510q) abstractC0633uS;
            abstractC0633uS = s(abstractC0726x, 1);
            i = 1;
        } else {
            i = 0;
        }
        if (abstractC0633uS instanceof C0386m) {
            this.b = (C0386m) abstractC0633uS;
            i++;
            abstractC0633uS = s(abstractC0726x, i);
        }
        if (!(abstractC0633uS instanceof H4)) {
            this.c = abstractC0633uS;
            i++;
            abstractC0633uS = s(abstractC0726x, i);
        }
        if (abstractC0726x.size() != i + 1) {
            throw new IllegalArgumentException("input sequence too large");
        }
        if (!(abstractC0633uS instanceof H4)) {
            throw new IllegalArgumentException("No tagged object found in sequence. Structure doesn't seem to be of type External");
        }
        H4 h4 = (H4) abstractC0633uS;
        int i2 = h4.c;
        if (i2 < 0 || i2 > 2) {
            throw new IllegalArgumentException(AbstractC0535qo.b(i2, "invalid encoding value: "));
        }
        this.d = i2;
        int i3 = h4.b;
        if (128 != i3) {
            throw new IllegalArgumentException("invalid tag: " + AbstractC0046as.A(i3, i2));
        }
        if (i2 != 0) {
            if (i2 == 1) {
                abstractC0633uD = (r) r.b.d(h4, false);
            } else {
                if (i2 != 2) {
                    throw new IllegalArgumentException("invalid tag: " + AbstractC0046as.A(i3, i2));
                }
                abstractC0633uD = (AbstractC0079c) AbstractC0079c.b.d(h4, false);
            }
        } else {
            if (!h4.s()) {
                throw new IllegalStateException("object implicit - explicit expected.");
            }
            InterfaceC0202g interfaceC0202g = h4.d;
            abstractC0633uD = (interfaceC0202g instanceof AbstractC0417n ? (AbstractC0417n) interfaceC0202g : interfaceC0202g.d()).d();
        }
        this.e = abstractC0633uD;
    }
}
