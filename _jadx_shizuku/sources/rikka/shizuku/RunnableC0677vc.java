package rikka.shizuku;

import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;

/* JADX INFO: renamed from: rikka.shizuku.vc, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class RunnableC0677vc implements Runnable {
    public final /* synthetic */ int a;
    public final /* synthetic */ AbstractViewOnTouchListenerC0708wc b;

    public /* synthetic */ RunnableC0677vc(AbstractViewOnTouchListenerC0708wc abstractViewOnTouchListenerC0708wc, int i) {
        this.a = i;
        this.b = abstractViewOnTouchListenerC0708wc;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.a) {
            case 0:
                ViewParent parent = this.b.d.getParent();
                if (parent != null) {
                    parent.requestDisallowInterceptTouchEvent(true);
                }
                break;
            default:
                AbstractViewOnTouchListenerC0708wc abstractViewOnTouchListenerC0708wc = this.b;
                abstractViewOnTouchListenerC0708wc.a();
                View view = abstractViewOnTouchListenerC0708wc.d;
                if (view.isEnabled() && !view.isLongClickable() && abstractViewOnTouchListenerC0708wc.c()) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    long jUptimeMillis = SystemClock.uptimeMillis();
                    MotionEvent motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 3, 0.0f, 0.0f, 0);
                    view.onTouchEvent(motionEventObtain);
                    motionEventObtain.recycle();
                    abstractViewOnTouchListenerC0708wc.g = true;
                    break;
                }
                break;
        }
    }
}
