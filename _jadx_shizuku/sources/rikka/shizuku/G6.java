package rikka.shizuku;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public final class G6 implements V7, Serializable {
    public final V7 a;
    public final T7 b;

    public G6(V7 v7, T7 t7) {
        this.a = v7;
        this.b = t7;
    }

    @Override // rikka.shizuku.V7
    public final V7 b(V7 v7) {
        return AbstractC0744xh.P(this, v7);
    }

    public final boolean equals(Object obj) {
        boolean zC;
        if (this == obj) {
            return true;
        }
        if (obj instanceof G6) {
            G6 g6 = (G6) obj;
            g6.getClass();
            int i = 2;
            G6 g7 = g6;
            int i2 = 2;
            while (true) {
                V7 v7 = g7.a;
                g7 = v7 instanceof G6 ? (G6) v7 : null;
                if (g7 == null) {
                    break;
                }
                i2++;
            }
            G6 g8 = this;
            while (true) {
                V7 v8 = g8.a;
                g8 = v8 instanceof G6 ? (G6) v8 : null;
                if (g8 == null) {
                    break;
                }
                i++;
            }
            if (i2 == i) {
                G6 g9 = this;
                while (true) {
                    T7 t7 = g9.b;
                    if (!AbstractC0046as.c(g6.g(t7.getKey()), t7)) {
                        zC = false;
                        break;
                    }
                    V7 v9 = g9.a;
                    if (!(v9 instanceof G6)) {
                        T7 t8 = (T7) v9;
                        zC = AbstractC0046as.c(g6.g(t8.getKey()), t8);
                        break;
                    }
                    g9 = (G6) v9;
                }
                if (zC) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // rikka.shizuku.V7
    public final T7 g(U7 u7) {
        G6 g6 = this;
        while (true) {
            T7 t7G = g6.b.g(u7);
            if (t7G != null) {
                return t7G;
            }
            V7 v7 = g6.a;
            if (!(v7 instanceof G6)) {
                return v7.g(u7);
            }
            g6 = (G6) v7;
        }
    }

    public final int hashCode() {
        return this.b.hashCode() + this.a.hashCode();
    }

    @Override // rikka.shizuku.V7
    public final V7 j(U7 u7) {
        T7 t7 = this.b;
        T7 t7G = t7.g(u7);
        V7 v7 = this.a;
        if (t7G != null) {
            return v7;
        }
        V7 v7J = v7.j(u7);
        if (v7J == v7) {
            return this;
        }
        return v7J == C0152eb.a ? t7 : new G6(v7J, t7);
    }

    @Override // rikka.shizuku.V7
    public final Object l(Object obj, InterfaceC0585sd interfaceC0585sd) {
        return interfaceC0585sd.f(this.a.l(obj, interfaceC0585sd), this.b);
    }

    public final String toString() {
        return "[" + ((String) l("", new F6(0))) + ']';
    }
}
