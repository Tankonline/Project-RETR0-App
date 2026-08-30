package rikka.shizuku;

import android.graphics.Insets;

/* JADX INFO: renamed from: rikka.shizuku.sf, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0587sf {
    public static final C0587sf e = new C0587sf(0, 0, 0, 0);
    public final int a;
    public final int b;
    public final int c;
    public final int d;

    public C0587sf(int i, int i2, int i3, int i4) {
        this.a = i;
        this.b = i2;
        this.c = i3;
        this.d = i4;
    }

    public static C0587sf a(C0587sf c0587sf, C0587sf c0587sf2) {
        return b(Math.max(c0587sf.a, c0587sf2.a), Math.max(c0587sf.b, c0587sf2.b), Math.max(c0587sf.c, c0587sf2.c), Math.max(c0587sf.d, c0587sf2.d));
    }

    public static C0587sf b(int i, int i2, int i3, int i4) {
        return (i == 0 && i2 == 0 && i3 == 0 && i4 == 0) ? e : new C0587sf(i, i2, i3, i4);
    }

    public static C0587sf c(Insets insets) {
        return b(insets.left, insets.top, insets.right, insets.bottom);
    }

    public final Insets d() {
        return Q3.c(this.a, this.b, this.c, this.d);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || C0587sf.class != obj.getClass()) {
            return false;
        }
        C0587sf c0587sf = (C0587sf) obj;
        return this.d == c0587sf.d && this.a == c0587sf.a && this.c == c0587sf.c && this.b == c0587sf.b;
    }

    public final int hashCode() {
        return (((((this.a * 31) + this.b) * 31) + this.c) * 31) + this.d;
    }

    public final String toString() {
        return "Insets{left=" + this.a + ", top=" + this.b + ", right=" + this.c + ", bottom=" + this.d + '}';
    }
}
