package rikka.shizuku;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/* JADX INFO: loaded from: classes.dex */
public final class J6 {
    public static final /* synthetic */ AtomicIntegerFieldUpdater b = AtomicIntegerFieldUpdater.newUpdater(J6.class, "_handled$volatile");
    private volatile /* synthetic */ int _handled$volatile = 0;
    public final Throwable a;

    public J6(Throwable th) {
        this.a = th;
    }

    public final String toString() {
        return J6.class.getSimpleName() + '[' + this.a + ']';
    }
}
