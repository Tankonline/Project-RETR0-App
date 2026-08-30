package rikka.shizuku;

import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.EditText;

/* JADX INFO: renamed from: rikka.shizuku.kf, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0340kf extends View.AccessibilityDelegate {
    public final /* synthetic */ C0371lf a;

    public C0340kf(C0371lf c0371lf) {
        this.a = c0371lf;
    }

    @Override // android.view.View.AccessibilityDelegate
    public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
        EditText editText = this.a.h.d;
        if (editText != null) {
            accessibilityNodeInfo.setLabeledBy(editText);
        }
    }
}
