package rikka.shizuku;

/* JADX INFO: loaded from: classes.dex */
public final class P9 extends RuntimeException {
    public final transient V7 a;

    public P9(V7 v7) {
        this.a = v7;
    }

    @Override // java.lang.Throwable
    public final Throwable fillInStackTrace() {
        setStackTrace(new StackTraceElement[0]);
        return this;
    }

    @Override // java.lang.Throwable
    public final String getLocalizedMessage() {
        return String.valueOf(this.a);
    }
}
