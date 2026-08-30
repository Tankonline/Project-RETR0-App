package com.google.android.material.behavior;

import android.view.MotionEvent;
import android.view.View;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import java.util.WeakHashMap;
import rikka.shizuku.C0018a0;
import rikka.shizuku.C0474op;
import rikka.shizuku.Hm;
import rikka.shizuku.Hs;
import rikka.shizuku.Js;
import rikka.shizuku.K7;

/* JADX INFO: loaded from: classes.dex */
public class SwipeDismissBehavior<V extends View> extends K7 {
    public Js a;
    public boolean b;
    public boolean c;
    public int d = 2;
    public float e = 0.0f;
    public float f = 0.5f;
    public final C0474op g = new C0474op(this);

    @Override // rikka.shizuku.K7
    public boolean g(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
        boolean zO = this.b;
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            zO = coordinatorLayout.o(view, (int) motionEvent.getX(), (int) motionEvent.getY());
            this.b = zO;
        } else if (actionMasked == 1 || actionMasked == 3) {
            this.b = false;
        }
        if (zO) {
            if (this.a == null) {
                this.a = new Js(coordinatorLayout.getContext(), coordinatorLayout, this.g);
            }
            if (!this.c && this.a.p(motionEvent)) {
                return true;
            }
        }
        return false;
    }

    @Override // rikka.shizuku.K7
    public final boolean h(CoordinatorLayout coordinatorLayout, View view, int i) {
        WeakHashMap weakHashMap = Hs.a;
        if (view.getImportantForAccessibility() == 0) {
            view.setImportantForAccessibility(1);
            Hs.j(view, 1048576);
            Hs.h(view, 0);
            if (s(view)) {
                Hs.k(view, C0018a0.j, new Hm(this));
            }
        }
        return false;
    }

    @Override // rikka.shizuku.K7
    public final boolean r(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
        if (this.a == null) {
            return false;
        }
        if (this.c && motionEvent.getActionMasked() == 3) {
            return true;
        }
        this.a.j(motionEvent);
        return true;
    }

    public boolean s(View view) {
        return true;
    }
}
