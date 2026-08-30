package rikka.shizuku;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public final class Gq implements Serializable {
    public final String a;
    public final Integer b;
    public final Integer c;

    public Gq(String str, Integer num, Integer num2) {
        this.a = str;
        this.b = num;
        this.c = num2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Gq)) {
            return false;
        }
        Gq gq = (Gq) obj;
        return this.a.equals(gq.a) && this.b.equals(gq.b) && this.c.equals(gq.c);
    }

    public final int hashCode() {
        return this.c.hashCode() + ((this.b.hashCode() + (this.a.hashCode() * 31)) * 31);
    }

    public final String toString() {
        return "(" + ((Object) this.a) + ", " + this.b + ", " + this.c + ')';
    }
}
