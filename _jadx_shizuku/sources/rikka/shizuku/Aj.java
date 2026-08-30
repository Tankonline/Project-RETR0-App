package rikka.shizuku;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;

/* JADX INFO: loaded from: classes.dex */
public final class Aj extends Ja {
    public final /* synthetic */ int d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ Aj(AbstractC0162el abstractC0162el, int i) {
        super(abstractC0162el);
        this.d = i;
    }

    @Override // rikka.shizuku.Ja
    public final int b(View view) {
        switch (this.d) {
            case 0:
                C0193fl c0193fl = (C0193fl) view.getLayoutParams();
                ((AbstractC0162el) this.b).getClass();
                return view.getRight() + ((C0193fl) view.getLayoutParams()).b.right + ((ViewGroup.MarginLayoutParams) c0193fl).rightMargin;
            default:
                C0193fl c0193fl2 = (C0193fl) view.getLayoutParams();
                ((AbstractC0162el) this.b).getClass();
                return view.getBottom() + ((C0193fl) view.getLayoutParams()).b.bottom + ((ViewGroup.MarginLayoutParams) c0193fl2).bottomMargin;
        }
    }

    @Override // rikka.shizuku.Ja
    public final int c(View view) {
        switch (this.d) {
            case 0:
                C0193fl c0193fl = (C0193fl) view.getLayoutParams();
                ((AbstractC0162el) this.b).getClass();
                return AbstractC0162el.A(view) + ((ViewGroup.MarginLayoutParams) c0193fl).leftMargin + ((ViewGroup.MarginLayoutParams) c0193fl).rightMargin;
            default:
                C0193fl c0193fl2 = (C0193fl) view.getLayoutParams();
                ((AbstractC0162el) this.b).getClass();
                return AbstractC0162el.z(view) + ((ViewGroup.MarginLayoutParams) c0193fl2).topMargin + ((ViewGroup.MarginLayoutParams) c0193fl2).bottomMargin;
        }
    }

    @Override // rikka.shizuku.Ja
    public final int d(View view) {
        switch (this.d) {
            case 0:
                C0193fl c0193fl = (C0193fl) view.getLayoutParams();
                ((AbstractC0162el) this.b).getClass();
                return AbstractC0162el.z(view) + ((ViewGroup.MarginLayoutParams) c0193fl).topMargin + ((ViewGroup.MarginLayoutParams) c0193fl).bottomMargin;
            default:
                C0193fl c0193fl2 = (C0193fl) view.getLayoutParams();
                ((AbstractC0162el) this.b).getClass();
                return AbstractC0162el.A(view) + ((ViewGroup.MarginLayoutParams) c0193fl2).leftMargin + ((ViewGroup.MarginLayoutParams) c0193fl2).rightMargin;
        }
    }

    @Override // rikka.shizuku.Ja
    public final int e(View view) {
        switch (this.d) {
            case 0:
                C0193fl c0193fl = (C0193fl) view.getLayoutParams();
                ((AbstractC0162el) this.b).getClass();
                return (view.getLeft() - ((C0193fl) view.getLayoutParams()).b.left) - ((ViewGroup.MarginLayoutParams) c0193fl).leftMargin;
            default:
                C0193fl c0193fl2 = (C0193fl) view.getLayoutParams();
                ((AbstractC0162el) this.b).getClass();
                return (view.getTop() - ((C0193fl) view.getLayoutParams()).b.top) - ((ViewGroup.MarginLayoutParams) c0193fl2).topMargin;
        }
    }

    @Override // rikka.shizuku.Ja
    public final int f() {
        switch (this.d) {
            case 0:
                return ((AbstractC0162el) this.b).n;
            default:
                return ((AbstractC0162el) this.b).o;
        }
    }

    @Override // rikka.shizuku.Ja
    public final int g() {
        switch (this.d) {
            case 0:
                AbstractC0162el abstractC0162el = (AbstractC0162el) this.b;
                return abstractC0162el.n - abstractC0162el.E();
            default:
                AbstractC0162el abstractC0162el2 = (AbstractC0162el) this.b;
                return abstractC0162el2.o - abstractC0162el2.C();
        }
    }

    @Override // rikka.shizuku.Ja
    public final int h() {
        switch (this.d) {
            case 0:
                return ((AbstractC0162el) this.b).E();
            default:
                return ((AbstractC0162el) this.b).C();
        }
    }

    @Override // rikka.shizuku.Ja
    public final int i() {
        switch (this.d) {
            case 0:
                return ((AbstractC0162el) this.b).l;
            default:
                return ((AbstractC0162el) this.b).m;
        }
    }

    @Override // rikka.shizuku.Ja
    public final int j() {
        switch (this.d) {
            case 0:
                return ((AbstractC0162el) this.b).m;
            default:
                return ((AbstractC0162el) this.b).l;
        }
    }

    @Override // rikka.shizuku.Ja
    public final int k() {
        switch (this.d) {
            case 0:
                return ((AbstractC0162el) this.b).D();
            default:
                return ((AbstractC0162el) this.b).F();
        }
    }

    @Override // rikka.shizuku.Ja
    public final int l() {
        switch (this.d) {
            case 0:
                AbstractC0162el abstractC0162el = (AbstractC0162el) this.b;
                return (abstractC0162el.n - abstractC0162el.D()) - abstractC0162el.E();
            default:
                AbstractC0162el abstractC0162el2 = (AbstractC0162el) this.b;
                return (abstractC0162el2.o - abstractC0162el2.F()) - abstractC0162el2.C();
        }
    }

    @Override // rikka.shizuku.Ja
    public final int m(View view) {
        switch (this.d) {
            case 0:
                AbstractC0162el abstractC0162el = (AbstractC0162el) this.b;
                Rect rect = (Rect) this.c;
                abstractC0162el.J(view, rect);
                return rect.right;
            default:
                AbstractC0162el abstractC0162el2 = (AbstractC0162el) this.b;
                Rect rect2 = (Rect) this.c;
                abstractC0162el2.J(view, rect2);
                return rect2.bottom;
        }
    }

    @Override // rikka.shizuku.Ja
    public final int n(View view) {
        switch (this.d) {
            case 0:
                AbstractC0162el abstractC0162el = (AbstractC0162el) this.b;
                Rect rect = (Rect) this.c;
                abstractC0162el.J(view, rect);
                return rect.left;
            default:
                AbstractC0162el abstractC0162el2 = (AbstractC0162el) this.b;
                Rect rect2 = (Rect) this.c;
                abstractC0162el2.J(view, rect2);
                return rect2.top;
        }
    }

    @Override // rikka.shizuku.Ja
    public final void o(int i) {
        switch (this.d) {
            case 0:
                ((AbstractC0162el) this.b).O(i);
                break;
            default:
                ((AbstractC0162el) this.b).P(i);
                break;
        }
    }
}
