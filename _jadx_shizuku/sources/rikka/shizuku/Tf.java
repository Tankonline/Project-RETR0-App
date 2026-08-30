package rikka.shizuku;

/* JADX INFO: loaded from: classes.dex */
public final class Tf extends Pf {
    public final Pg a = new Pg(false);

    public final boolean equals(Object obj) {
        if (obj != this) {
            return (obj instanceof Tf) && ((Tf) obj).a.equals(this.a);
        }
        return true;
    }

    public final int hashCode() {
        return this.a.hashCode();
    }
}
