package com.google.android.material.snackbar;

import android.view.MotionEvent;
import android.view.View;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.behavior.SwipeDismissBehavior;
import rikka.shizuku.Aa;
import rikka.shizuku.Hm;

/* JADX INFO: loaded from: classes.dex */
public class BaseTransientBottomBar$Behavior extends SwipeDismissBehavior<View> {
    public final Aa h;

    public BaseTransientBottomBar$Behavior() {
        Aa aa = new Aa();
        this.e = Math.min(Math.max(0.0f, 0.1f), 1.0f);
        this.f = Math.min(Math.max(0.0f, 0.6f), 1.0f);
        this.d = 0;
        this.h = aa;
    }

    @Override // com.google.android.material.behavior.SwipeDismissBehavior, rikka.shizuku.K7
    public final boolean g(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
        this.h.getClass();
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 0) {
            if (actionMasked == 1 || actionMasked == 3) {
                if (Hm.b == null) {
                    Hm.b = new Hm();
                }
                synchronized (Hm.b.a) {
                }
            }
        } else if (coordinatorLayout.o(view, (int) motionEvent.getX(), (int) motionEvent.getY())) {
            if (Hm.b == null) {
                Hm.b = new Hm();
            }
            synchronized (Hm.b.a) {
            }
        }
        return super.g(coordinatorLayout, view, motionEvent);
    }

    @Override // com.google.android.material.behavior.SwipeDismissBehavior
    public final boolean s(View view) {
        this.h.getClass();
        return view instanceof Snackbar$SnackbarLayout;
    }
}
