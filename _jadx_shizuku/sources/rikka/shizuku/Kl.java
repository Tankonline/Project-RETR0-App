package rikka.shizuku;

import android.os.Process;

/* JADX INFO: loaded from: classes.dex */
public final class Kl extends Thread {
    public final int a;

    public Kl(Runnable runnable) {
        super(runnable, "fonts-androidx");
        this.a = 10;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final void run() {
        Process.setThreadPriority(this.a);
        super.run();
    }
}
