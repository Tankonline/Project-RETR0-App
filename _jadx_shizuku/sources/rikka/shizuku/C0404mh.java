package rikka.shizuku;

/* JADX INFO: renamed from: rikka.shizuku.mh, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class C0404mh extends AbstractC0639u5 implements InterfaceC0124dd, Xf {
    public final boolean g;

    public C0404mh(C0435nh c0435nh) {
        super(c0435nh, X8.class, "classSimpleName", "getClassSimpleName(Ljava/lang/Object;)Ljava/lang/String;", true);
        this.g = false;
    }

    @Override // rikka.shizuku.InterfaceC0124dd
    public final Object a() {
        return this.b.getClass().getSimpleName();
    }

    public final Xf e() {
        if (this.g) {
            return this;
        }
        Xf xf = this.a;
        if (xf != null) {
            return xf;
        }
        AbstractC0593sl.a.getClass();
        this.a = this;
        return this;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof C0404mh) {
            C0404mh c0404mh = (C0404mh) obj;
            return d().equals(c0404mh.d()) && this.d.equals(c0404mh.d) && this.e.equals(c0404mh.e) && this.b.equals(c0404mh.b);
        }
        if (obj instanceof C0404mh) {
            return obj.equals(e());
        }
        return false;
    }

    public final int hashCode() {
        return this.e.hashCode() + ((this.d.hashCode() + (d().hashCode() * 31)) * 31);
    }

    public final String toString() {
        Xf xfE = e();
        if (xfE != this) {
            return xfE.toString();
        }
        return "property " + this.d + " (Kotlin reflection is not available)";
    }
}
