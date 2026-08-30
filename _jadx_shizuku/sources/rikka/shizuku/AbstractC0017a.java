package rikka.shizuku;

/* JADX INFO: renamed from: rikka.shizuku.a, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0017a extends AbstractC0633u implements InterfaceC0126df {
    public final H4 a;

    public AbstractC0017a(H4 h4) {
        if (64 != h4.b) {
            throw new IllegalArgumentException();
        }
        this.a = h4;
    }

    @Override // rikka.shizuku.AbstractC0633u, rikka.shizuku.AbstractC0417n
    public final int hashCode() {
        return this.a.hashCode();
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final boolean i(AbstractC0633u abstractC0633u) {
        H4 h4;
        if (abstractC0633u instanceof AbstractC0017a) {
            h4 = ((AbstractC0017a) abstractC0633u).a;
        } else {
            if (!(abstractC0633u instanceof H4)) {
                return false;
            }
            h4 = (H4) abstractC0633u;
        }
        return this.a.n(h4);
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final void j(Ys ys, boolean z) {
        this.a.j(ys, z);
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final boolean k() {
        return this.a.k();
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final int m(boolean z) {
        return this.a.m(z);
    }

    @Override // rikka.shizuku.AbstractC0633u
    public AbstractC0633u p() {
        return new C0731x4((H4) this.a.p(), 1);
    }

    @Override // rikka.shizuku.AbstractC0633u
    public AbstractC0633u q() {
        return new L8((H4) this.a.q());
    }

    @Override // rikka.shizuku.InterfaceC0126df
    public final AbstractC0633u a() {
        return this;
    }
}
