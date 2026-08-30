package defpackage;

import android.view.Choreographer;

/* JADX INFO: loaded from: assets/rish_shizuku.dex */
public abstract class B {
    public static void a(final Runnable runnable) {
        Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() { // from class: A
            @Override // android.view.Choreographer.FrameCallback
            public final void doFrame(long j) {
                runnable.run();
            }
        });
    }
}
