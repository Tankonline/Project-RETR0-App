package rikka.shizuku;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.recyclerview.widget.RecyclerView;
import java.util.WeakHashMap;

/* JADX INFO: renamed from: rikka.shizuku.pl, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0501pl extends S {
    public final C0532ql d;
    public final WeakHashMap e = new WeakHashMap();

    public C0501pl(C0532ql c0532ql) {
        this.d = c0532ql;
    }

    @Override // rikka.shizuku.S
    public final boolean a(View view, AccessibilityEvent accessibilityEvent) {
        S s = (S) this.e.get(view);
        return s != null ? s.a(view, accessibilityEvent) : this.a.dispatchPopulateAccessibilityEvent(view, accessibilityEvent);
    }

    @Override // rikka.shizuku.S
    public final Ys b(View view) {
        S s = (S) this.e.get(view);
        return s != null ? s.b(view) : super.b(view);
    }

    @Override // rikka.shizuku.S
    public final void c(View view, AccessibilityEvent accessibilityEvent) {
        S s = (S) this.e.get(view);
        if (s != null) {
            s.c(view, accessibilityEvent);
        } else {
            super.c(view, accessibilityEvent);
        }
    }

    @Override // rikka.shizuku.S
    public final void d(View view, C0141e0 c0141e0) {
        AbstractC0162el abstractC0162el;
        C0532ql c0532ql = this.d;
        boolean zO = c0532ql.d.O();
        View.AccessibilityDelegate accessibilityDelegate = this.a;
        AccessibilityNodeInfo accessibilityNodeInfo = c0141e0.a;
        if (zO || (abstractC0162el = c0532ql.d.n) == null) {
            accessibilityDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
            return;
        }
        abstractC0162el.W(view, c0141e0);
        S s = (S) this.e.get(view);
        if (s != null) {
            s.d(view, c0141e0);
        } else {
            accessibilityDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
        }
    }

    @Override // rikka.shizuku.S
    public final void e(View view, AccessibilityEvent accessibilityEvent) {
        S s = (S) this.e.get(view);
        if (s != null) {
            s.e(view, accessibilityEvent);
        } else {
            super.e(view, accessibilityEvent);
        }
    }

    @Override // rikka.shizuku.S
    public final boolean f(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
        S s = (S) this.e.get(viewGroup);
        return s != null ? s.f(viewGroup, view, accessibilityEvent) : this.a.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent);
    }

    @Override // rikka.shizuku.S
    public final boolean g(View view, int i, Bundle bundle) {
        C0532ql c0532ql = this.d;
        if (!c0532ql.d.O()) {
            RecyclerView recyclerView = c0532ql.d;
            if (recyclerView.n != null) {
                S s = (S) this.e.get(view);
                if (s != null) {
                    if (s.g(view, i, bundle)) {
                        return true;
                    }
                } else if (super.g(view, i, bundle)) {
                    return true;
                }
                C0285il c0285il = recyclerView.n.b.c;
                return false;
            }
        }
        return super.g(view, i, bundle);
    }

    @Override // rikka.shizuku.S
    public final void h(View view, int i) {
        S s = (S) this.e.get(view);
        if (s != null) {
            s.h(view, i);
        } else {
            super.h(view, i);
        }
    }

    @Override // rikka.shizuku.S
    public final void i(View view, AccessibilityEvent accessibilityEvent) {
        S s = (S) this.e.get(view);
        if (s != null) {
            s.i(view, accessibilityEvent);
        } else {
            super.i(view, accessibilityEvent);
        }
    }
}
