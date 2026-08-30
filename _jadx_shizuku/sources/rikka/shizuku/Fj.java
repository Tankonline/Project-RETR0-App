package rikka.shizuku;

/* JADX INFO: loaded from: classes.dex */
public final class Fj implements InterfaceC0117d6 {
    public final Class a;

    public Fj(Class cls) {
        this.a = cls;
    }

    @Override // rikka.shizuku.InterfaceC0117d6
    public final Class a() {
        return this.a;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof Fj) {
            return AbstractC0046as.c(this.a, ((Fj) obj).a);
        }
        return false;
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    public final String toString() {
        return this.a + " (Kotlin reflection is not available)";
    }
}
