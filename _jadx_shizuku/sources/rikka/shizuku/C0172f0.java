package rikka.shizuku;

import android.os.Bundle;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import java.util.List;

/* JADX INFO: renamed from: rikka.shizuku.f0, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0172f0 extends AccessibilityNodeProvider {
    public final Ys a;

    public C0172f0(Ys ys) {
        this.a = ys;
    }

    @Override // android.view.accessibility.AccessibilityNodeProvider
    public final AccessibilityNodeInfo createAccessibilityNodeInfo(int i) {
        C0141e0 c0141e0P = this.a.p(i);
        if (c0141e0P == null) {
            return null;
        }
        return c0141e0P.a;
    }

    @Override // android.view.accessibility.AccessibilityNodeProvider
    public final List findAccessibilityNodeInfosByText(String str, int i) {
        this.a.getClass();
        return null;
    }

    @Override // android.view.accessibility.AccessibilityNodeProvider
    public final AccessibilityNodeInfo findFocus(int i) {
        C0141e0 c0141e0Q = this.a.q(i);
        if (c0141e0Q == null) {
            return null;
        }
        return c0141e0Q.a;
    }

    @Override // android.view.accessibility.AccessibilityNodeProvider
    public final boolean performAction(int i, int i2, Bundle bundle) {
        return this.a.A(i, i2, bundle);
    }
}
