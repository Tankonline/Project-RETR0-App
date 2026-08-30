package rikka.shizuku;

import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: renamed from: rikka.shizuku.ql, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0532ql extends S {
    public final RecyclerView d;
    public final C0501pl e;

    public C0532ql(RecyclerView recyclerView) {
        this.d = recyclerView;
        S sJ = j();
        if (sJ == null || !(sJ instanceof C0501pl)) {
            this.e = new C0501pl(this);
        } else {
            this.e = (C0501pl) sJ;
        }
    }

    @Override // rikka.shizuku.S
    public final void c(View view, AccessibilityEvent accessibilityEvent) {
        AbstractC0162el abstractC0162el;
        super.c(view, accessibilityEvent);
        if (!(view instanceof RecyclerView) || this.d.O() || (abstractC0162el = ((RecyclerView) view).n) == null) {
            return;
        }
        abstractC0162el.U(accessibilityEvent);
    }

    @Override // rikka.shizuku.S
    public final void d(View view, C0141e0 c0141e0) {
        AbstractC0162el abstractC0162el;
        this.a.onInitializeAccessibilityNodeInfo(view, c0141e0.a);
        RecyclerView recyclerView = this.d;
        if (recyclerView.O() || (abstractC0162el = recyclerView.n) == null) {
            return;
        }
        RecyclerView recyclerView2 = abstractC0162el.b;
        abstractC0162el.V(recyclerView2.c, recyclerView2.g0, c0141e0);
    }

    @Override // rikka.shizuku.S
    public final boolean g(View view, int i, Bundle bundle) {
        AbstractC0162el abstractC0162el;
        if (super.g(view, i, bundle)) {
            return true;
        }
        RecyclerView recyclerView = this.d;
        if (recyclerView.O() || (abstractC0162el = recyclerView.n) == null) {
            return false;
        }
        return abstractC0162el.i0(i, bundle);
    }

    public S j() {
        return this.e;
    }
}
