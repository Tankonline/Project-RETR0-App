package rikka.shizuku;

/* JADX INFO: renamed from: rikka.shizuku.vf, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0680vf extends AbstractC0618tf {
    static {
        new C0680vf(1, 0, 1);
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof C0680vf)) {
            return false;
        }
        if (isEmpty() && ((C0680vf) obj).isEmpty()) {
            return true;
        }
        C0680vf c0680vf = (C0680vf) obj;
        if (this.a == c0680vf.a) {
            return this.b == c0680vf.b;
        }
        return false;
    }

    public final int hashCode() {
        if (isEmpty()) {
            return -1;
        }
        return (this.a * 31) + this.b;
    }

    public final boolean isEmpty() {
        return this.a > this.b;
    }

    public final String toString() {
        return this.a + ".." + this.b;
    }
}
