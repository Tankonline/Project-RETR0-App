package rikka.shizuku;

/* JADX INFO: loaded from: classes.dex */
public abstract class Pt {
    public final au a;
    public C0587sf[] b;

    public Pt() {
        this(new au());
    }

    public final void a() {
        C0587sf[] c0587sfArr = this.b;
        if (c0587sfArr != null) {
            C0587sf c0587sfF = c0587sfArr[0];
            C0587sf c0587sfF2 = c0587sfArr[1];
            au auVar = this.a;
            if (c0587sfF2 == null) {
                c0587sfF2 = auVar.a.f(2);
            }
            if (c0587sfF == null) {
                c0587sfF = auVar.a.f(1);
            }
            g(C0587sf.a(c0587sfF, c0587sfF2));
            C0587sf c0587sf = this.b[Hn.h(16)];
            if (c0587sf != null) {
                f(c0587sf);
            }
            C0587sf c0587sf2 = this.b[Hn.h(32)];
            if (c0587sf2 != null) {
                d(c0587sf2);
            }
            C0587sf c0587sf3 = this.b[Hn.h(64)];
            if (c0587sf3 != null) {
                h(c0587sf3);
            }
        }
    }

    public abstract au b();

    public void c(int i, C0587sf c0587sf) {
        if (this.b == null) {
            this.b = new C0587sf[10];
        }
        for (int i2 = 1; i2 <= 512; i2 <<= 1) {
            if ((i & i2) != 0) {
                this.b[Hn.h(i2)] = c0587sf;
            }
        }
    }

    public abstract void e(C0587sf c0587sf);

    public abstract void g(C0587sf c0587sf);

    public Pt(au auVar) {
        this.a = auVar;
    }

    public void d(C0587sf c0587sf) {
    }

    public void f(C0587sf c0587sf) {
    }

    public void h(C0587sf c0587sf) {
    }
}
