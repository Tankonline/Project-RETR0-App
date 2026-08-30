package rikka.shizuku;

/* JADX INFO: loaded from: classes.dex */
public final class Y9 extends Exception {
    public final Throwable a;

    public Y9(Throwable th, W7 w7, V7 v7) {
        super("Coroutine dispatcher " + w7 + " threw an exception, context = " + v7, th);
        this.a = th;
    }

    @Override // java.lang.Throwable
    public final Throwable getCause() {
        return this.a;
    }
}
