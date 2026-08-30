package androidx.core.widget;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ScrollView;
import rikka.shizuku.C0018a0;
import rikka.shizuku.C0141e0;
import rikka.shizuku.S;

/* JADX INFO: loaded from: classes.dex */
public final class a extends S {
    @Override // rikka.shizuku.S
    public final void c(View view, AccessibilityEvent accessibilityEvent) {
        super.c(view, accessibilityEvent);
        NestedScrollView nestedScrollView = (NestedScrollView) view;
        accessibilityEvent.setClassName(ScrollView.class.getName());
        accessibilityEvent.setScrollable(nestedScrollView.l() > 0);
        accessibilityEvent.setScrollX(nestedScrollView.getScrollX());
        accessibilityEvent.setScrollY(nestedScrollView.getScrollY());
        accessibilityEvent.setMaxScrollX(nestedScrollView.getScrollX());
        accessibilityEvent.setMaxScrollY(nestedScrollView.l());
    }

    @Override // rikka.shizuku.S
    public final void d(View view, C0141e0 c0141e0) {
        int iL;
        this.a.onInitializeAccessibilityNodeInfo(view, c0141e0.a);
        NestedScrollView nestedScrollView = (NestedScrollView) view;
        c0141e0.i(ScrollView.class.getName());
        if (!nestedScrollView.isEnabled() || (iL = nestedScrollView.l()) <= 0) {
            return;
        }
        c0141e0.l(true);
        if (nestedScrollView.getScrollY() > 0) {
            c0141e0.b(C0018a0.g);
            c0141e0.b(C0018a0.l);
        }
        if (nestedScrollView.getScrollY() < iL) {
            c0141e0.b(C0018a0.f);
            c0141e0.b(C0018a0.m);
        }
    }

    /* JADX WARN: Code duplicated, block: B:26:0x006a  */
    /* JADX WARN: Code duplicated, block: B:28:0x0087  */
    @Override // rikka.shizuku.S
    public final boolean g(View view, int i, Bundle bundle) {
        int iMin;
        if (super.g(view, i, bundle)) {
            return true;
        }
        NestedScrollView nestedScrollView = (NestedScrollView) view;
        if (nestedScrollView.isEnabled()) {
            int height = nestedScrollView.getHeight();
            Rect rect = new Rect();
            if (nestedScrollView.getMatrix().isIdentity() && nestedScrollView.getGlobalVisibleRect(rect)) {
                height = rect.height();
            }
            if (i == 4096) {
                iMin = Math.min(nestedScrollView.getScrollY() + ((height - nestedScrollView.getPaddingBottom()) - nestedScrollView.getPaddingTop()), nestedScrollView.l());
                if (iMin != nestedScrollView.getScrollY()) {
                    nestedScrollView.w(0 - nestedScrollView.getScrollX(), iMin - nestedScrollView.getScrollY(), true);
                    return true;
                }
            } else if (i == 8192 || i == 16908344) {
                int iMax = Math.max(nestedScrollView.getScrollY() - ((height - nestedScrollView.getPaddingBottom()) - nestedScrollView.getPaddingTop()), 0);
                if (iMax != nestedScrollView.getScrollY()) {
                    nestedScrollView.w(0 - nestedScrollView.getScrollX(), iMax - nestedScrollView.getScrollY(), true);
                    return true;
                }
            } else if (i == 16908346) {
                iMin = Math.min(nestedScrollView.getScrollY() + ((height - nestedScrollView.getPaddingBottom()) - nestedScrollView.getPaddingTop()), nestedScrollView.l());
                if (iMin != nestedScrollView.getScrollY()) {
                    nestedScrollView.w(0 - nestedScrollView.getScrollX(), iMin - nestedScrollView.getScrollY(), true);
                    return true;
                }
            }
        }
        return false;
    }
}
