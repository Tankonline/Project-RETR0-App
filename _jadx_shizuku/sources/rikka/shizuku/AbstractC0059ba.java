package rikka.shizuku;

/* JADX INFO: renamed from: rikka.shizuku.ba, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0059ba extends AbstractRunnableC0721wp {
    public int c;

    public final void a(Throwable th) {
        AbstractC0046as.C(((Z9) this).e.b, new C0119d8("Fatal exception in coroutines machinery for " + this + ". Please read KDoc to 'handleFatalException' method and report this incident to maintainers", th));
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i;
        try {
            Z9 z9 = (Z9) this;
            H7 h7 = z9.e;
            V7 v7 = h7.b;
            Object objO = AbstractC0599sr.O(v7, z9.g);
            Ef ef = null;
            Gr grB0 = objO != AbstractC0599sr.r ? X8.b0(h7, v7, objO) : null;
            try {
                Z9 z10 = (Z9) this;
                Object obj = z10.f;
                z10.f = X8.g;
                J6 j6 = obj instanceof J6 ? (J6) obj : null;
                Throwable th = j6 != null ? j6.a : null;
                if (th == null && ((i = this.c) == 1 || i == 2)) {
                    ef = (Ef) v7.g(Fe.a);
                }
                if (ef != null && !ef.a()) {
                    h7.h(new C0071bm(ef.c()));
                } else if (th != null) {
                    h7.h(new C0071bm(th));
                } else {
                    h7.h(obj);
                }
            } finally {
                if (grB0 == null || grB0.N()) {
                    AbstractC0599sr.E(v7, objO);
                }
            }
        } catch (Y9 e) {
            AbstractC0046as.C(((Z9) this).e.b, e.a);
        } catch (Throwable th2) {
            a(th2);
        }
    }
}
