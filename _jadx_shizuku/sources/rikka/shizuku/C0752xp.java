package rikka.shizuku;

/* JADX INFO: renamed from: rikka.shizuku.xp, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0752xp extends AbstractRunnableC0721wp {
    public final Runnable c;

    public C0752xp(Runnable runnable, long j, boolean z) {
        super(j, z);
        this.c = runnable;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.c.run();
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("Task[");
        Runnable runnable = this.c;
        sb.append(runnable.getClass().getSimpleName());
        sb.append('@');
        sb.append(X8.B(runnable));
        sb.append(", ");
        sb.append(this.a);
        sb.append(", ");
        sb.append(this.b ? "Blocking" : "Non-blocking");
        sb.append(']');
        return sb.toString();
    }
}
