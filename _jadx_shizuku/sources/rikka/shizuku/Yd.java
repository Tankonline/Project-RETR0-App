package rikka.shizuku;

/* JADX INFO: loaded from: classes.dex */
public final class Yd extends AbstractC0725wt {
    @Override // rikka.shizuku.K9
    public final void a(K9 k9) {
        M9 m9 = this.h;
        if (m9.c && !m9.j) {
            m9.d((int) ((((M9) m9.l.get(0)).g * ((Xd) this.b).d0) + 0.5f));
        }
    }

    @Override // rikka.shizuku.AbstractC0725wt
    public final void d() {
        C0425n7 c0425n7 = this.b;
        Xd xd = (Xd) c0425n7;
        int i = xd.e0;
        int i2 = xd.f0;
        int i3 = xd.h0;
        M9 m9 = this.h;
        if (i3 == 1) {
            if (i != -1) {
                m9.l.add(c0425n7.I.d.h);
                this.b.I.d.h.k.add(m9);
                m9.f = i;
            } else if (i2 != -1) {
                m9.l.add(c0425n7.I.d.i);
                this.b.I.d.i.k.add(m9);
                m9.f = -i2;
            } else {
                m9.b = true;
                m9.l.add(c0425n7.I.d.i);
                this.b.I.d.i.k.add(m9);
            }
            m(this.b.d.h);
            m(this.b.d.i);
            return;
        }
        if (i != -1) {
            m9.l.add(c0425n7.I.e.h);
            this.b.I.e.h.k.add(m9);
            m9.f = i;
        } else if (i2 != -1) {
            m9.l.add(c0425n7.I.e.i);
            this.b.I.e.i.k.add(m9);
            m9.f = -i2;
        } else {
            m9.b = true;
            m9.l.add(c0425n7.I.e.i);
            this.b.I.e.i.k.add(m9);
        }
        m(this.b.e.h);
        m(this.b.e.i);
    }

    @Override // rikka.shizuku.AbstractC0725wt
    public final void e() {
        C0425n7 c0425n7 = this.b;
        int i = ((Xd) c0425n7).h0;
        M9 m9 = this.h;
        if (i == 1) {
            c0425n7.N = m9.g;
        } else {
            c0425n7.O = m9.g;
        }
    }

    @Override // rikka.shizuku.AbstractC0725wt
    public final void f() {
        this.h.c();
    }

    @Override // rikka.shizuku.AbstractC0725wt
    public final boolean k() {
        return false;
    }

    public final void m(M9 m9) {
        M9 m10 = this.h;
        m10.k.add(m9);
        m9.l.add(m10);
    }
}
