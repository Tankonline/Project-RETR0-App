package rikka.shizuku;

import android.os.SystemClock;
import android.view.ViewTreeObserver;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public final class R6 implements Executor, ViewTreeObserver.OnDrawListener, Runnable {
    public final long a = SystemClock.uptimeMillis() + 10000;
    public Runnable b;
    public final /* synthetic */ AbstractActivityC0051b2 c;

    public R6(AbstractActivityC0051b2 abstractActivityC0051b2) {
        this.c = abstractActivityC0051b2;
    }

    @Override // java.util.concurrent.Executor
    public final void execute(Runnable runnable) {
        this.b = runnable;
        this.c.getWindow().getDecorView().postOnAnimation(new G0(5, this));
    }

    @Override // android.view.ViewTreeObserver.OnDrawListener
    public final void onDraw() {
        boolean z;
        Runnable runnable = this.b;
        if (runnable == null) {
            if (SystemClock.uptimeMillis() > this.a) {
                this.c.getWindow().getDecorView().post(this);
                return;
            }
            return;
        }
        runnable.run();
        this.b = null;
        C0672v7 c0672v7 = this.c.i;
        synchronized (c0672v7.d) {
            z = c0672v7.b;
        }
        if (z) {
            this.c.getWindow().getDecorView().post(this);
        }
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.c.getWindow().getDecorView().getViewTreeObserver().removeOnDrawListener(this);
    }
}
