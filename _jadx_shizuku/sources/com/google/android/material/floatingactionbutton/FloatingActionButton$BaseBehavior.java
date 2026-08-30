package com.google.android.material.floatingactionbutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import rikka.shizuku.K7;
import rikka.shizuku.Lk;
import rikka.shizuku.N7;

/* JADX INFO: loaded from: classes.dex */
public class FloatingActionButton$BaseBehavior<T> extends K7 {
    public FloatingActionButton$BaseBehavior() {
    }

    @Override // rikka.shizuku.K7
    public final boolean a(View view) {
        throw new ClassCastException();
    }

    @Override // rikka.shizuku.K7
    public final void c(N7 n7) {
        if (n7.h == 0) {
            n7.h = 80;
        }
    }

    @Override // rikka.shizuku.K7
    public final boolean d(CoordinatorLayout coordinatorLayout, View view, View view2) {
        throw new ClassCastException();
    }

    @Override // rikka.shizuku.K7
    public final boolean h(CoordinatorLayout coordinatorLayout, View view, int i) {
        throw new ClassCastException();
    }

    public FloatingActionButton$BaseBehavior(Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Lk.j);
        typedArrayObtainStyledAttributes.getBoolean(0, true);
        typedArrayObtainStyledAttributes.recycle();
    }
}
