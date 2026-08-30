package rikka.shizuku;

/* JADX INFO: loaded from: classes.dex */
public final class Us extends Vs {
    public final StringBuilder b;
    public final Pi c;
    public final Pi d;

    public Us(boolean z, String str, int i) {
        StringBuilder sb = new StringBuilder();
        this.b = sb;
        Pi pi = new Pi();
        this.c = pi;
        this.d = pi;
        try {
            if (!z) {
                e(i, str);
                return;
            }
            sb.append("Starting with root...");
            sb.append('\n');
            sb.append('\n');
            d(null);
            Ld ld = Ld.a;
            C0550r9 c0550r9 = AbstractC0090ca.a;
            ExecutorC0243h9 executorC0243h9 = ExecutorC0243h9.b;
            AbstractC0599sr.z(ld, new Ss(this, null), 2);
        } catch (Throwable th) {
            d(th);
        }
    }

    public final void d(Throwable th) {
        StringBuilder sb = this.b;
        Pi pi = this.c;
        if (th == null) {
            pi.e(new Rl(1, sb, Sl.a));
        } else {
            pi.e(new Rl(2, sb, th));
        }
    }

    public final void e(int i, String str) {
        StringBuilder sb = this.b;
        sb.append("Starting with wireless adb in port " + i + "...");
        sb.append('\n');
        sb.append('\n');
        d(null);
        Ld ld = Ld.a;
        C0550r9 c0550r9 = AbstractC0090ca.a;
        ExecutorC0243h9 executorC0243h9 = ExecutorC0243h9.b;
        AbstractC0599sr.z(ld, new Qs(this, str, i, null), 2);
    }
}
