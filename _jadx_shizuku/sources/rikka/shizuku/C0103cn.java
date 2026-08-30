package rikka.shizuku;

/* JADX INFO: renamed from: rikka.shizuku.cn, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0103cn {
    public final int a;
    public final int b;
    public final int c;
    public final String d;
    public final boolean e;

    public /* synthetic */ C0103cn() {
        this(-1, -1, -1, null, false);
    }

    public final boolean a() {
        return this.a != -1 && Nn.h();
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C0103cn)) {
            return false;
        }
        C0103cn c0103cn = (C0103cn) obj;
        return this.a == c0103cn.a && this.b == c0103cn.b && this.c == c0103cn.c && AbstractC0046as.c(this.d, c0103cn.d) && this.e == c0103cn.e;
    }

    public final int hashCode() {
        int iHashCode = (Integer.hashCode(this.c) + ((Integer.hashCode(this.b) + (Integer.hashCode(this.a) * 31)) * 31)) * 31;
        String str = this.d;
        return Boolean.hashCode(this.e) + ((iHashCode + (str == null ? 0 : str.hashCode())) * 31);
    }

    public final String toString() {
        return "ServiceStatus(uid=" + this.a + ", apiVersion=" + this.b + ", patchVersion=" + this.c + ", seContext=" + this.d + ", permission=" + this.e + ")";
    }

    public C0103cn(int i, int i2, int i3, String str, boolean z) {
        this.a = i;
        this.b = i2;
        this.c = i3;
        this.d = str;
        this.e = z;
    }
}
