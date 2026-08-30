package com.google.android.material.timepicker;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import moe.shizuku.privileged.api.R;
import rikka.shizuku.C0018a0;
import rikka.shizuku.C0141e0;
import rikka.shizuku.S;
import rikka.shizuku.Ys;

/* JADX INFO: loaded from: classes.dex */
public final class c extends S {
    public final /* synthetic */ ClockFaceView d;

    public c(ClockFaceView clockFaceView) {
        this.d = clockFaceView;
    }

    @Override // rikka.shizuku.S
    public final void d(View view, C0141e0 c0141e0) {
        View.AccessibilityDelegate accessibilityDelegate = this.a;
        AccessibilityNodeInfo accessibilityNodeInfo = c0141e0.a;
        accessibilityDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
        int iIntValue = ((Integer) view.getTag(R.id._0_resource_name_obfuscated_res_0x7f090101)).intValue();
        if (iIntValue > 0) {
            accessibilityNodeInfo.setTraversalAfter((View) this.d.v.get(iIntValue - 1));
        }
        c0141e0.j(Ys.z(view.isSelected(), 0, 1, iIntValue, 1));
        accessibilityNodeInfo.setClickable(true);
        c0141e0.b(C0018a0.e);
    }

    @Override // rikka.shizuku.S
    public final boolean g(View view, int i, Bundle bundle) {
        if (i != 16) {
            return super.g(view, i, bundle);
        }
        long jUptimeMillis = SystemClock.uptimeMillis();
        ClockFaceView clockFaceView = this.d;
        view.getHitRect(clockFaceView.s);
        float fCenterX = clockFaceView.s.centerX();
        float fCenterY = clockFaceView.s.centerY();
        clockFaceView.r.onTouchEvent(MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 0, fCenterX, fCenterY, 0));
        clockFaceView.r.onTouchEvent(MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 1, fCenterX, fCenterY, 0));
        return true;
    }
}
