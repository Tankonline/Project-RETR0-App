package rikka.shizuku;

import android.graphics.drawable.Drawable;

/* JADX INFO: loaded from: classes.dex */
public final class R1 implements Drawable.Callback {
    public final /* synthetic */ U1 a;

    public R1(U1 u1) {
        this.a = u1;
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public final void invalidateDrawable(Drawable drawable) {
        this.a.invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public final void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
        this.a.scheduleSelf(runnable, j);
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public final void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        this.a.unscheduleSelf(runnable);
    }
}
