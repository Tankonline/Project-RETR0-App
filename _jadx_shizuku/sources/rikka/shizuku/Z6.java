package rikka.shizuku;

import java.util.HashSet;
import java.util.Iterator;
import rikka.html.text.style.BulletSpan;

/* JADX INFO: loaded from: classes.dex */
public final class Z6 {
    public final C0425n7 b;
    public final int c;
    public Z6 d;
    public C0565ro g;
    public HashSet a = null;
    public int e = 0;
    public int f = -1;

    public Z6(C0425n7 c0425n7, int i) {
        this.b = c0425n7;
        this.c = i;
    }

    public final void a(Z6 z6, int i) {
        b(z6, i, -1, false);
    }

    public final boolean b(Z6 z6, int i, int i2, boolean z) {
        if (z6 == null) {
            h();
            return true;
        }
        if (!z && !g(z6)) {
            return false;
        }
        this.d = z6;
        if (z6.a == null) {
            z6.a = new HashSet();
        }
        this.d.a.add(this);
        if (i > 0) {
            this.e = i;
        } else {
            this.e = 0;
        }
        this.f = i2;
        return true;
    }

    public final int c() {
        Z6 z6;
        if (this.b.V == 8) {
            return 0;
        }
        int i = this.f;
        return (i <= -1 || (z6 = this.d) == null || z6.b.V != 8) ? this.e : i;
    }

    public final Z6 d() {
        int i = this.c;
        int iJ = AbstractC0535qo.j(i);
        C0425n7 c0425n7 = this.b;
        switch (iJ) {
            case 0:
            case BulletSpan.STANDARD_BULLET_RADIUS /* 5 */:
            case 6:
            case 7:
            case 8:
                return null;
            case 1:
                return c0425n7.z;
            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                return c0425n7.A;
            case 3:
                return c0425n7.x;
            case 4:
                return c0425n7.y;
            default:
                throw new AssertionError(AbstractC0535qo.i(i));
        }
    }

    public final boolean e() {
        HashSet hashSet = this.a;
        if (hashSet == null) {
            return false;
        }
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            if (((Z6) it.next()).d().f()) {
                return true;
            }
        }
        return false;
    }

    public final boolean f() {
        return this.d != null;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:45:0x005e A[RETURN] */
    public final boolean g(Z6 z6) {
        if (z6 != null) {
            int i = this.c;
            C0425n7 c0425n7 = z6.b;
            int i2 = z6.c;
            if (i2 != i) {
                switch (AbstractC0535qo.j(i)) {
                    case 0:
                    case BulletSpan.STANDARD_BULLET_RADIUS /* 5 */:
                    case 7:
                    case 8:
                        break;
                    case 1:
                    case 3:
                        boolean z = i2 == 2 || i2 == 4;
                        if (!(c0425n7 instanceof Xd)) {
                            return z;
                        }
                        if (z || i2 == 8) {
                            return true;
                        }
                        break;
                    case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                    case 4:
                        boolean z2 = i2 == 3 || i2 == 5;
                        if (!(c0425n7 instanceof Xd)) {
                            return z2;
                        }
                        if (z2 || i2 == 9) {
                            return true;
                        }
                        break;
                    case 6:
                        if (i2 != 6 && i2 != 8 && i2 != 9) {
                            return true;
                        }
                        break;
                    default:
                        throw new AssertionError(AbstractC0535qo.i(i));
                }
            } else if (i != 6 || (c0425n7.w && this.b.w)) {
                return true;
            }
        }
        return false;
    }

    public final void h() {
        HashSet hashSet;
        Z6 z6 = this.d;
        if (z6 != null && (hashSet = z6.a) != null) {
            hashSet.remove(this);
        }
        this.d = null;
        this.e = 0;
        this.f = -1;
    }

    public final void i() {
        C0565ro c0565ro = this.g;
        if (c0565ro == null) {
            this.g = new C0565ro(1);
        } else {
            c0565ro.c();
        }
    }

    public final String toString() {
        return this.b.W + ":" + AbstractC0535qo.i(this.c);
    }
}
