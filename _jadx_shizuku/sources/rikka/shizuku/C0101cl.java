package rikka.shizuku;

import android.view.View;
import android.view.ViewGroup;

/* JADX INFO: renamed from: rikka.shizuku.cl, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0101cl {
    public final /* synthetic */ int a;
    public final /* synthetic */ AbstractC0162el b;

    public /* synthetic */ C0101cl(AbstractC0162el abstractC0162el, int i) {
        this.a = i;
        this.b = abstractC0162el;
    }

    public final int a(View view) {
        switch (this.a) {
            case 0:
                C0193fl c0193fl = (C0193fl) view.getLayoutParams();
                this.b.getClass();
                return view.getRight() + ((C0193fl) view.getLayoutParams()).b.right + ((ViewGroup.MarginLayoutParams) c0193fl).rightMargin;
            default:
                C0193fl c0193fl2 = (C0193fl) view.getLayoutParams();
                this.b.getClass();
                return view.getBottom() + ((C0193fl) view.getLayoutParams()).b.bottom + ((ViewGroup.MarginLayoutParams) c0193fl2).bottomMargin;
        }
    }

    public final int b(View view) {
        switch (this.a) {
            case 0:
                C0193fl c0193fl = (C0193fl) view.getLayoutParams();
                this.b.getClass();
                return (view.getLeft() - ((C0193fl) view.getLayoutParams()).b.left) - ((ViewGroup.MarginLayoutParams) c0193fl).leftMargin;
            default:
                C0193fl c0193fl2 = (C0193fl) view.getLayoutParams();
                this.b.getClass();
                return (view.getTop() - ((C0193fl) view.getLayoutParams()).b.top) - ((ViewGroup.MarginLayoutParams) c0193fl2).topMargin;
        }
    }

    public final int c() {
        switch (this.a) {
            case 0:
                AbstractC0162el abstractC0162el = this.b;
                return abstractC0162el.n - abstractC0162el.E();
            default:
                AbstractC0162el abstractC0162el2 = this.b;
                return abstractC0162el2.o - abstractC0162el2.C();
        }
    }

    public final int d() {
        switch (this.a) {
            case 0:
                return this.b.D();
            default:
                return this.b.F();
        }
    }
}
