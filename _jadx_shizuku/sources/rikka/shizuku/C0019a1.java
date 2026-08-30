package rikka.shizuku;

/* JADX INFO: renamed from: rikka.shizuku.a1, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class C0019a1 implements InterfaceC0313jj, Bd {
    public final /* synthetic */ int a;
    public final /* synthetic */ InterfaceC0462od b;

    public /* synthetic */ C0019a1(InterfaceC0462od interfaceC0462od, int i) {
        this.a = i;
        this.b = interfaceC0462od;
    }

    @Override // rikka.shizuku.Bd
    public final InterfaceC0462od a() {
        switch (this.a) {
            case 0:
                return (Z0) this.b;
            case 1:
                return (Z0) this.b;
            default:
                return (C0450o1) this.b;
        }
    }

    @Override // rikka.shizuku.InterfaceC0313jj
    public final /* synthetic */ void b(Object obj) {
        switch (this.a) {
            case 0:
                ((Z0) this.b).g(obj);
                break;
            case 1:
                ((Z0) this.b).g(obj);
                break;
            default:
                ((C0450o1) this.b).g(obj);
                break;
        }
    }

    public final boolean equals(Object obj) {
        switch (this.a) {
            case 0:
                if ((obj instanceof InterfaceC0313jj) && (obj instanceof Bd)) {
                    return ((Z0) this.b).equals(((Bd) obj).a());
                }
                return false;
            case 1:
                if ((obj instanceof InterfaceC0313jj) && (obj instanceof Bd)) {
                    return ((Z0) this.b).equals(((Bd) obj).a());
                }
                return false;
            default:
                if ((obj instanceof InterfaceC0313jj) && (obj instanceof Bd)) {
                    return ((C0450o1) this.b).equals(((Bd) obj).a());
                }
                return false;
        }
    }

    public final int hashCode() {
        switch (this.a) {
            case 0:
                return ((Z0) this.b).hashCode();
            case 1:
                return ((Z0) this.b).hashCode();
            default:
                return ((C0450o1) this.b).hashCode();
        }
    }
}
