package rikka.shizuku;

import android.animation.ValueAnimator;
import android.os.Handler;
import android.view.View;
import android.widget.OverScroller;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.appbar.AppBarLayout;

/* JADX INFO: renamed from: rikka.shizuku.fe, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class RunnableC0186fe implements Runnable {
    public final /* synthetic */ int a = 1;
    public Object b;
    public Object c;
    public Object d;

    public /* synthetic */ RunnableC0186fe() {
    }

    @Override // java.lang.Runnable
    public final void run() {
        Object objCall;
        AppBarLayout.BaseBehavior baseBehavior;
        OverScroller overScroller;
        switch (this.a) {
            case 0:
                View view = (View) this.c;
                if (view != null && (overScroller = (baseBehavior = (AppBarLayout.BaseBehavior) this.d).d) != null) {
                    boolean zComputeScrollOffset = overScroller.computeScrollOffset();
                    CoordinatorLayout coordinatorLayout = (CoordinatorLayout) this.b;
                    if (!zComputeScrollOffset) {
                        AppBarLayout appBarLayout = (AppBarLayout) view;
                        baseBehavior.C(coordinatorLayout, appBarLayout);
                        if (appBarLayout.j) {
                            appBarLayout.i(appBarLayout.j(AppBarLayout.BaseBehavior.w(coordinatorLayout)));
                        }
                    } else {
                        baseBehavior.B(coordinatorLayout, view, baseBehavior.d.getCurrY());
                        view.postOnAnimation(this);
                    }
                    break;
                }
                break;
            case 1:
                try {
                    objCall = ((CallableC0368lc) this.b).call();
                } catch (Exception unused) {
                    objCall = null;
                }
                ((Handler) this.d).post(new A0((C0399mc) this.c, objCall, 6, false));
                break;
            default:
                Ft.h((View) this.c, (R2) this.b);
                ((ValueAnimator) this.d).start();
                break;
        }
    }

    public RunnableC0186fe(AppBarLayout.BaseBehavior baseBehavior, CoordinatorLayout coordinatorLayout, View view) {
        this.d = baseBehavior;
        this.b = coordinatorLayout;
        this.c = view;
    }

    public RunnableC0186fe(View view, Jt jt, R2 r2, ValueAnimator valueAnimator) {
        this.c = view;
        this.b = r2;
        this.d = valueAnimator;
    }
}
