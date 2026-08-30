package rikka.shizuku;

import java.util.ArrayList;

/* JADX INFO: renamed from: rikka.shizuku.ne, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0432ne extends AbstractC0725wt {
    @Override // rikka.shizuku.K9
    public final void a(K9 k9) {
        P4 p4 = (P4) this.b;
        int i = p4.f0;
        M9 m9 = this.h;
        ArrayList arrayList = m9.l;
        int size = arrayList.size();
        int i2 = 0;
        int i3 = 0;
        int i4 = -1;
        while (i3 < size) {
            Object obj = arrayList.get(i3);
            i3++;
            int i5 = ((M9) obj).g;
            if (i4 == -1 || i5 < i4) {
                i4 = i5;
            }
            if (i2 < i5) {
                i2 = i5;
            }
        }
        if (i == 0 || i == 2) {
            m9.d(i4 + p4.h0);
        } else {
            m9.d(i2 + p4.h0);
        }
    }

    @Override // rikka.shizuku.AbstractC0725wt
    public final void d() {
        C0425n7 c0425n7 = this.b;
        if (c0425n7 instanceof P4) {
            M9 m9 = this.h;
            m9.b = true;
            P4 p4 = (P4) c0425n7;
            int i = p4.f0;
            boolean z = p4.g0;
            int i2 = 0;
            if (i == 0) {
                m9.e = 4;
                while (i2 < p4.e0) {
                    C0425n7 c0425n8 = p4.d0[i2];
                    if (z || c0425n8.V != 8) {
                        M9 m10 = c0425n8.d.h;
                        m10.k.add(m9);
                        m9.l.add(m10);
                    }
                    i2++;
                }
                m(this.b.d.h);
                m(this.b.d.i);
                return;
            }
            if (i == 1) {
                m9.e = 5;
                while (i2 < p4.e0) {
                    C0425n7 c0425n9 = p4.d0[i2];
                    if (z || c0425n9.V != 8) {
                        M9 m11 = c0425n9.d.i;
                        m11.k.add(m9);
                        m9.l.add(m11);
                    }
                    i2++;
                }
                m(this.b.d.h);
                m(this.b.d.i);
                return;
            }
            if (i == 2) {
                m9.e = 6;
                while (i2 < p4.e0) {
                    C0425n7 c0425n10 = p4.d0[i2];
                    if (z || c0425n10.V != 8) {
                        M9 m12 = c0425n10.e.h;
                        m12.k.add(m9);
                        m9.l.add(m12);
                    }
                    i2++;
                }
                m(this.b.e.h);
                m(this.b.e.i);
                return;
            }
            if (i != 3) {
                return;
            }
            m9.e = 7;
            while (i2 < p4.e0) {
                C0425n7 c0425n11 = p4.d0[i2];
                if (z || c0425n11.V != 8) {
                    M9 m13 = c0425n11.e.i;
                    m13.k.add(m9);
                    m9.l.add(m13);
                }
                i2++;
            }
            m(this.b.e.h);
            m(this.b.e.i);
        }
    }

    @Override // rikka.shizuku.AbstractC0725wt
    public final void e() {
        C0425n7 c0425n7 = this.b;
        if (c0425n7 instanceof P4) {
            int i = ((P4) c0425n7).f0;
            M9 m9 = this.h;
            if (i == 0 || i == 1) {
                c0425n7.N = m9.g;
            } else {
                c0425n7.O = m9.g;
            }
        }
    }

    @Override // rikka.shizuku.AbstractC0725wt
    public final void f() {
        this.c = null;
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
