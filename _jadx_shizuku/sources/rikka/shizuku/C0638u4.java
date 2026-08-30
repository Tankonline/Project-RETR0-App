package rikka.shizuku;

import android.animation.TimeInterpolator;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

/* JADX INFO: renamed from: rikka.shizuku.u4, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0638u4 extends AbstractC0722wq {
    public ArrayList C;
    public boolean D;
    public int E;
    public boolean F;
    public int G;

    @Override // rikka.shizuku.AbstractC0722wq
    public final void A(View view) {
        super.A(view);
        int size = this.C.size();
        for (int i = 0; i < size; i++) {
            ((AbstractC0722wq) this.C.get(i)).A(view);
        }
    }

    @Override // rikka.shizuku.AbstractC0722wq
    public final void B() {
        if (this.C.isEmpty()) {
            J();
            m();
            return;
        }
        Bq bq = new Bq();
        bq.b = this;
        ArrayList arrayList = this.C;
        int size = arrayList.size();
        int i = 0;
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            ((AbstractC0722wq) obj).a(bq);
        }
        this.E = this.C.size();
        if (this.D) {
            ArrayList arrayList2 = this.C;
            int size2 = arrayList2.size();
            while (i < size2) {
                Object obj2 = arrayList2.get(i);
                i++;
                ((AbstractC0722wq) obj2).B();
            }
            return;
        }
        for (int i3 = 1; i3 < this.C.size(); i3++) {
            ((AbstractC0722wq) this.C.get(i3 - 1)).a(new Bq((AbstractC0722wq) this.C.get(i3), 2));
        }
        AbstractC0722wq abstractC0722wq = (AbstractC0722wq) this.C.get(0);
        if (abstractC0722wq != null) {
            abstractC0722wq.B();
        }
    }

    /* JADX WARN: Code duplicated, block: B:55:0x00c9  */
    /* JADX WARN: Code duplicated, block: B:63:0x00d9  */
    /* JADX WARN: Code duplicated, block: B:74:? A[RETURN, SYNTHETIC] */
    @Override // rikka.shizuku.AbstractC0722wq
    public final void C(long j, long j2) {
        long j3;
        long j4 = this.w;
        long j5 = 0;
        if (this.i != null) {
            if (j < 0 && j2 < 0) {
                return;
            }
            if (j > j4 && j2 > j4) {
                return;
            }
        }
        boolean z = j < j2;
        if ((j >= 0 && j2 < 0) || (j <= j4 && j2 > j4)) {
            this.r = false;
            w(this, C0579s7.b, z);
        }
        if (!this.D) {
            int size = 1;
            while (true) {
                if (size >= this.C.size()) {
                    size = this.C.size();
                    break;
                } else if (((AbstractC0722wq) this.C.get(size)).x > j2) {
                    break;
                } else {
                    size++;
                }
            }
            int i = size - 1;
            if (j >= j2) {
                while (true) {
                    if (i < this.C.size()) {
                        AbstractC0722wq abstractC0722wq = (AbstractC0722wq) this.C.get(i);
                        long j6 = abstractC0722wq.x;
                        j3 = j5;
                        long j7 = j - j6;
                        if (j7 < j3) {
                            break;
                        }
                        abstractC0722wq.C(j7, j2 - j6);
                        i++;
                        j5 = j3;
                    }
                }
            } else {
                j3 = 0;
                while (i >= 0) {
                    AbstractC0722wq abstractC0722wq2 = (AbstractC0722wq) this.C.get(i);
                    long j8 = abstractC0722wq2.x;
                    long j9 = j - j8;
                    abstractC0722wq2.C(j9, j2 - j8);
                    if (j9 >= 0) {
                        break;
                    } else {
                        i--;
                    }
                }
            }
            if (this.i != null) {
                if ((j > j4 || j2 > j4) && (j >= 0 || j2 < j3)) {
                    return;
                }
                if (j > j4) {
                    this.r = true;
                }
                w(this, C0579s7.c, z);
            }
        }
        for (int i2 = 0; i2 < this.C.size(); i2++) {
            ((AbstractC0722wq) this.C.get(i2)).C(j, j2);
        }
        j3 = j5;
        if (this.i != null) {
            if (j > j4) {
                return;
            } else {
                return;
            }
            if (j > j4) {
                this.r = true;
            }
            w(this, C0579s7.c, z);
        }
    }

    @Override // rikka.shizuku.AbstractC0722wq
    public final void D(long j) {
        ArrayList arrayList;
        this.c = j;
        if (j < 0 || (arrayList = this.C) == null) {
            return;
        }
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            ((AbstractC0722wq) this.C.get(i)).D(j);
        }
    }

    @Override // rikka.shizuku.AbstractC0722wq
    public final void E(An an) {
        this.G |= 8;
        int size = this.C.size();
        for (int i = 0; i < size; i++) {
            ((AbstractC0722wq) this.C.get(i)).E(an);
        }
    }

    @Override // rikka.shizuku.AbstractC0722wq
    public final void F(TimeInterpolator timeInterpolator) {
        this.G |= 1;
        ArrayList arrayList = this.C;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                ((AbstractC0722wq) this.C.get(i)).F(timeInterpolator);
            }
        }
        this.d = timeInterpolator;
    }

    @Override // rikka.shizuku.AbstractC0722wq
    public final void G(Pe pe) {
        super.G(pe);
        this.G |= 4;
        if (this.C != null) {
            for (int i = 0; i < this.C.size(); i++) {
                ((AbstractC0722wq) this.C.get(i)).G(pe);
            }
        }
    }

    @Override // rikka.shizuku.AbstractC0722wq
    public final void H() {
        this.G |= 2;
        int size = this.C.size();
        for (int i = 0; i < size; i++) {
            ((AbstractC0722wq) this.C.get(i)).H();
        }
    }

    @Override // rikka.shizuku.AbstractC0722wq
    public final void I(long j) {
        this.b = j;
    }

    @Override // rikka.shizuku.AbstractC0722wq
    public final String K(String str) {
        String strK = super.K(str);
        for (int i = 0; i < this.C.size(); i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(strK);
            sb.append("\n");
            sb.append(((AbstractC0722wq) this.C.get(i)).K(str + "  "));
            strK = sb.toString();
        }
        return strK;
    }

    public final void L(AbstractC0722wq abstractC0722wq) {
        this.C.add(abstractC0722wq);
        abstractC0722wq.i = this;
        long j = this.c;
        if (j >= 0) {
            abstractC0722wq.D(j);
        }
        if ((this.G & 1) != 0) {
            abstractC0722wq.F(this.d);
        }
        if ((this.G & 2) != 0) {
            abstractC0722wq.H();
        }
        if ((this.G & 4) != 0) {
            abstractC0722wq.G(this.v);
        }
        if ((this.G & 8) != 0) {
            abstractC0722wq.E(null);
        }
    }

    @Override // rikka.shizuku.AbstractC0722wq
    public final void c() {
        super.c();
        int size = this.C.size();
        for (int i = 0; i < size; i++) {
            ((AbstractC0722wq) this.C.get(i)).c();
        }
    }

    @Override // rikka.shizuku.AbstractC0722wq
    public final void d(Eq eq) {
        if (u(eq.b)) {
            ArrayList arrayList = this.C;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                AbstractC0722wq abstractC0722wq = (AbstractC0722wq) obj;
                if (abstractC0722wq.u(eq.b)) {
                    abstractC0722wq.d(eq);
                    eq.c.add(abstractC0722wq);
                }
            }
        }
    }

    @Override // rikka.shizuku.AbstractC0722wq
    public final void f(Eq eq) {
        int size = this.C.size();
        for (int i = 0; i < size; i++) {
            ((AbstractC0722wq) this.C.get(i)).f(eq);
        }
    }

    @Override // rikka.shizuku.AbstractC0722wq
    public final void g(Eq eq) {
        if (u(eq.b)) {
            ArrayList arrayList = this.C;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                AbstractC0722wq abstractC0722wq = (AbstractC0722wq) obj;
                if (abstractC0722wq.u(eq.b)) {
                    abstractC0722wq.g(eq);
                    eq.c.add(abstractC0722wq);
                }
            }
        }
    }

    @Override // rikka.shizuku.AbstractC0722wq
    /* JADX INFO: renamed from: j */
    public final AbstractC0722wq clone() {
        C0638u4 c0638u4 = (C0638u4) super.clone();
        c0638u4.C = new ArrayList();
        int size = this.C.size();
        for (int i = 0; i < size; i++) {
            AbstractC0722wq abstractC0722wqClone = ((AbstractC0722wq) this.C.get(i)).clone();
            c0638u4.C.add(abstractC0722wqClone);
            abstractC0722wqClone.i = c0638u4;
        }
        return c0638u4;
    }

    @Override // rikka.shizuku.AbstractC0722wq
    public final void l(ViewGroup viewGroup, Xn xn, Xn xn2, ArrayList arrayList, ArrayList arrayList2) {
        long j = this.b;
        int size = this.C.size();
        for (int i = 0; i < size; i++) {
            AbstractC0722wq abstractC0722wq = (AbstractC0722wq) this.C.get(i);
            if (j > 0 && (this.D || i == 0)) {
                long j2 = abstractC0722wq.b;
                if (j2 > 0) {
                    abstractC0722wq.I(j2 + j);
                } else {
                    abstractC0722wq.I(j);
                }
            }
            abstractC0722wq.l(viewGroup, xn, xn2, arrayList, arrayList2);
        }
    }

    @Override // rikka.shizuku.AbstractC0722wq
    public final boolean s() {
        for (int i = 0; i < this.C.size(); i++) {
            if (((AbstractC0722wq) this.C.get(i)).s()) {
                return true;
            }
        }
        return false;
    }

    @Override // rikka.shizuku.AbstractC0722wq
    public final void x(ViewGroup viewGroup) {
        super.x(viewGroup);
        int size = this.C.size();
        for (int i = 0; i < size; i++) {
            ((AbstractC0722wq) this.C.get(i)).x(viewGroup);
        }
    }

    @Override // rikka.shizuku.AbstractC0722wq
    public final void y() {
        this.w = 0L;
        int i = 0;
        Bq bq = new Bq(this, i);
        while (i < this.C.size()) {
            AbstractC0722wq abstractC0722wq = (AbstractC0722wq) this.C.get(i);
            abstractC0722wq.a(bq);
            abstractC0722wq.y();
            long j = abstractC0722wq.w;
            if (this.D) {
                this.w = Math.max(this.w, j);
            } else {
                long j2 = this.w;
                abstractC0722wq.x = j2;
                this.w = j2 + j;
            }
            i++;
        }
    }

    @Override // rikka.shizuku.AbstractC0722wq
    public final AbstractC0722wq z(InterfaceC0691vq interfaceC0691vq) {
        super.z(interfaceC0691vq);
        return this;
    }
}
