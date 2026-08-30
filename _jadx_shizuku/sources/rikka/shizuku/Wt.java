package rikka.shizuku;

import android.os.Build;
import android.view.View;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class Wt {
    public static final au b;
    public final au a;

    static {
        Pt mt;
        int i = Build.VERSION.SDK_INT;
        if (i >= 34) {
            mt = new Ot();
        } else if (i >= 30) {
            mt = new Nt();
        } else {
            mt = i >= 29 ? new Mt() : new Kt();
        }
        b = mt.b().a.a().a.b().a.c();
    }

    public Wt(au auVar) {
        this.a = auVar;
    }

    public au a() {
        return this.a;
    }

    public au b() {
        return this.a;
    }

    public au c() {
        return this.a;
    }

    public C0121da e() {
        return null;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Wt)) {
            return false;
        }
        Wt wt = (Wt) obj;
        return n() == wt.n() && m() == wt.m() && Objects.equals(j(), wt.j()) && Objects.equals(h(), wt.h()) && Objects.equals(e(), wt.e());
    }

    public C0587sf f(int i) {
        return C0587sf.e;
    }

    public C0587sf g() {
        return j();
    }

    public C0587sf h() {
        return C0587sf.e;
    }

    public int hashCode() {
        return Objects.hash(Boolean.valueOf(n()), Boolean.valueOf(m()), j(), h(), e());
    }

    public C0587sf i() {
        return j();
    }

    public C0587sf j() {
        return C0587sf.e;
    }

    public C0587sf k() {
        return j();
    }

    public au l(int i, int i2, int i3, int i4) {
        return b;
    }

    public boolean m() {
        return false;
    }

    public boolean n() {
        return false;
    }

    public void d(View view) {
    }

    public void o(C0587sf[] c0587sfArr) {
    }

    public void p(au auVar) {
    }

    public void q(C0587sf c0587sf) {
    }

    public void r(int i) {
    }
}
