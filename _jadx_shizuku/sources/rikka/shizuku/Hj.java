package rikka.shizuku;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public final class Hj implements Serializable {
    public final Object a;
    public final Object b;

    public Hj(Object obj, Object obj2) {
        this.a = obj;
        this.b = obj2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Hj)) {
            return false;
        }
        Hj hj = (Hj) obj;
        return AbstractC0046as.c(this.a, hj.a) && AbstractC0046as.c(this.b, hj.b);
    }

    public final int hashCode() {
        Object obj = this.a;
        int iHashCode = (obj == null ? 0 : obj.hashCode()) * 31;
        Object obj2 = this.b;
        return iHashCode + (obj2 != null ? obj2.hashCode() : 0);
    }

    public final String toString() {
        return "(" + this.a + ", " + this.b + ')';
    }
}
