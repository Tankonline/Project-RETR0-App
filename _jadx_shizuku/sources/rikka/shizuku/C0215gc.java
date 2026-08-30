package rikka.shizuku;

import java.util.List;
import java.util.Objects;

/* JADX INFO: renamed from: rikka.shizuku.gc, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0215gc {
    public String a;
    public String b;
    public List c;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C0215gc)) {
            return false;
        }
        C0215gc c0215gc = (C0215gc) obj;
        return Objects.equals(this.a, c0215gc.a) && Objects.equals(this.b, c0215gc.b) && Objects.equals(this.c, c0215gc.c);
    }

    public final int hashCode() {
        return Objects.hash(this.a, this.b, this.c);
    }
}
