package rikka.shizuku;

/* JADX INFO: loaded from: classes.dex */
public abstract class W7 extends I implements I7 {
    public W7() {
        super(Aa.a);
    }

    public abstract void d(V7 v7, Runnable runnable);

    public boolean e(V7 v7) {
        return !(this instanceof Fr);
    }

    public W7 f(int i) {
        X8.m(i);
        return new Bg(this, i);
    }

    @Override // rikka.shizuku.I, rikka.shizuku.V7
    public final T7 g(U7 u7) {
        boolean z;
        T7 t7A;
        if (!(u7 instanceof J)) {
            if (Aa.a == u7) {
                return this;
            }
            return null;
        }
        J j = (J) u7;
        if (this.a != j) {
            j.getClass();
            z = false;
        } else {
            j.getClass();
            z = true;
        }
        if (!z || (t7A = j.a(this)) == null) {
            return null;
        }
        return t7A;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x0022 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:15:0x0023 A[RETURN] */
    @Override // rikka.shizuku.I, rikka.shizuku.V7
    public final V7 j(U7 u7) {
        boolean z;
        boolean z2 = u7 instanceof J;
        C0152eb c0152eb = C0152eb.a;
        if (!z2) {
            if (Aa.a == u7) {
                return c0152eb;
            }
            return this;
        }
        J j = (J) u7;
        if (this.a != j) {
            j.getClass();
            z = false;
        } else {
            j.getClass();
            z = true;
        }
        if (!z || j.a(this) == null) {
            return this;
        }
        return c0152eb;
    }

    public String toString() {
        return getClass().getSimpleName() + '@' + X8.B(this);
    }
}
