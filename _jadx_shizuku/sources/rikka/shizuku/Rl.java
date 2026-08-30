package rikka.shizuku;

/* JADX INFO: loaded from: classes.dex */
public final class Rl {
    public final int a;
    public final Object b;
    public final Throwable c;

    public Rl(int i, Object obj, Throwable th) {
        this.a = i;
        this.b = obj;
        this.c = th;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!Rl.class.equals(obj != null ? obj.getClass() : null)) {
            return false;
        }
        if (obj == null) {
            throw new NullPointerException("null cannot be cast to non-null type rikka.lifecycle.Resource<*>");
        }
        Rl rl = (Rl) obj;
        return this.a == rl.a && AbstractC0046as.c(this.b, rl.b) && AbstractC0046as.c(this.c, rl.c);
    }

    public final int hashCode() {
        int iJ = AbstractC0535qo.j(this.a) * 31;
        Object obj = this.b;
        return this.c.hashCode() + ((iJ + (obj != null ? obj.hashCode() : 0)) * 31);
    }

    public final String toString() {
        String str;
        StringBuilder sb = new StringBuilder("Resource(status=");
        int i = this.a;
        if (i == 1) {
            str = "SUCCESS";
        } else if (i != 2) {
            str = i != 3 ? "null" : "LOADING";
        } else {
            str = "ERROR";
        }
        sb.append(str);
        sb.append(", data=");
        sb.append(this.b);
        sb.append(", error=");
        sb.append(this.c);
        sb.append(')');
        return sb.toString();
    }
}
