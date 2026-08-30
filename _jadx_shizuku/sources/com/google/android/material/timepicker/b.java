package com.google.android.material.timepicker;

import android.view.ViewTreeObserver;

/* JADX INFO: loaded from: classes.dex */
public final class b implements ViewTreeObserver.OnPreDrawListener {
    public final /* synthetic */ ClockFaceView a;

    public b(ClockFaceView clockFaceView) {
        this.a = clockFaceView;
    }

    @Override // android.view.ViewTreeObserver.OnPreDrawListener
    public final boolean onPreDraw() {
        ClockFaceView clockFaceView = this.a;
        if (!clockFaceView.isShown()) {
            return true;
        }
        clockFaceView.getViewTreeObserver().removeOnPreDrawListener(this);
        int height = ((clockFaceView.getHeight() / 2) - clockFaceView.r.d) - clockFaceView.z;
        if (height != clockFaceView.p) {
            clockFaceView.p = height;
            clockFaceView.f();
            int i = clockFaceView.p;
            ClockHandView clockHandView = clockFaceView.r;
            clockHandView.l = i;
            clockHandView.invalidate();
        }
        return true;
    }
}
