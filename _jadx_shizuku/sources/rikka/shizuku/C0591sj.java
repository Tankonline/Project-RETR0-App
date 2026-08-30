package rikka.shizuku;

/* JADX INFO: renamed from: rikka.shizuku.sj, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class C0591sj extends AbstractC0639u5 implements InterfaceC0124dd, Cd, Xf, Ad {
    public final int g;
    public final int h;
    public final /* synthetic */ int i;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0591sj(int i, Object obj, Class cls, String str, String str2, int i2) {
        super(obj, cls, str, str2, false);
        this.i = i2;
        this.g = i;
        this.h = 0;
    }

    @Override // rikka.shizuku.InterfaceC0124dd
    public final Object a() {
        switch (this.i) {
            case 0:
                ((C0622tj) this.b).c();
                break;
            default:
                ((C0622tj) this.b).c();
                break;
        }
        return Pe.b;
    }

    @Override // rikka.shizuku.Cd
    public final int b() {
        return this.g;
    }

    public final Xf e() {
        AbstractC0593sl.a.getClass();
        return this;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof C0591sj) {
            C0591sj c0591sj = (C0591sj) obj;
            return this.d.equals(c0591sj.d) && this.e.equals(c0591sj.e) && this.h == c0591sj.h && this.g == c0591sj.g && this.b.equals(c0591sj.b) && d().equals(c0591sj.d());
        }
        if (!(obj instanceof C0591sj)) {
            return false;
        }
        Xf xf = this.a;
        if (xf == null) {
            e();
            this.a = this;
            xf = this;
        }
        return obj.equals(xf);
    }

    public final int hashCode() {
        d();
        return this.e.hashCode() + ((this.d.hashCode() + (d().hashCode() * 31)) * 31);
    }

    public final String toString() {
        Xf xf = this.a;
        if (xf == null) {
            e();
            this.a = this;
            xf = this;
        }
        if (xf != this) {
            return xf.toString();
        }
        String str = this.d;
        return "<init>".equals(str) ? "constructor (Kotlin reflection is not available)" : AbstractC0535qo.d("function ", str, " (Kotlin reflection is not available)");
    }
}
