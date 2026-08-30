package rikka.shizuku;

/* JADX INFO: renamed from: rikka.shizuku.cb, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0091cb implements InterfaceC0187ff {
    public final boolean a;

    public C0091cb(boolean z) {
        this.a = z;
    }

    @Override // rikka.shizuku.InterfaceC0187ff
    public final boolean a() {
        return this.a;
    }

    @Override // rikka.shizuku.InterfaceC0187ff
    public final C0037aj d() {
        return null;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("Empty{");
        sb.append(this.a ? "Active" : "New");
        sb.append('}');
        return sb.toString();
    }
}
