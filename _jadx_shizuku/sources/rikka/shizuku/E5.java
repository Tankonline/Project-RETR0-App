package rikka.shizuku;

/* JADX INFO: loaded from: classes.dex */
public final class E5 implements Runnable {
    public final /* synthetic */ F5 a;
    public final /* synthetic */ C0221gi b;
    public final /* synthetic */ MenuC0129di c;
    public final /* synthetic */ Ys d;

    public E5(Ys ys, F5 f5, C0221gi c0221gi, MenuC0129di menuC0129di) {
        this.d = ys;
        this.a = f5;
        this.b = c0221gi;
        this.c = menuC0129di;
    }

    @Override // java.lang.Runnable
    public final void run() {
        F5 f5 = this.a;
        if (f5 != null) {
            Ys ys = this.d;
            ((G5) ys.b).z = true;
            f5.b.c(false);
            ((G5) ys.b).z = false;
        }
        C0221gi c0221gi = this.b;
        if (c0221gi.isEnabled() && c0221gi.hasSubMenu()) {
            this.c.q(c0221gi, null, 4);
        }
    }
}
