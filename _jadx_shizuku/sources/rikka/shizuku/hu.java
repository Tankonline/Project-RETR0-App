package rikka.shizuku;

import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

/* JADX INFO: loaded from: classes.dex */
public final class hu implements InterfaceC0344kj {
    public static final gu q = new gu();
    public final View a;
    public final int b;
    public final int c;
    public final int d;
    public int e;
    public int f;
    public int g;
    public int h;
    public boolean i;
    public int j;
    public int k;
    public int l;
    public int m;
    public int n;
    public int o;
    public au p;

    public hu(View view, int i, int i2, int i3) {
        this.a = view;
        this.b = i;
        this.c = i2;
        this.d = i3;
        this.e = view.getPaddingLeft();
        this.f = view.getPaddingTop();
        this.g = view.getPaddingRight();
        this.h = view.getPaddingBottom();
    }

    /* JADX WARN: Code duplicated, block: B:37:0x0051 A[PHI: r7
  0x0051: PHI (r7v4 boolean) = (r7v1 boolean), (r7v7 boolean), (r7v7 boolean) binds: [B:43:0x005e, B:36:0x004f, B:34:0x004b] A[DONT_GENERATE, DONT_INLINE]] */
    public final Object a(C0587sf c0587sf, int i, InterfaceC0678vd interfaceC0678vd) {
        boolean z;
        boolean z2 = false;
        boolean z3 = (i & 8388608) == 8388608;
        boolean z4 = this.a.getLayoutDirection() == 1;
        boolean z5 = (i & 48) == 48;
        boolean z6 = (i & 80) == 80;
        if (z3) {
            boolean z7 = (i & 8388611) == 8388611;
            boolean z8 = (i & 8388613) == 8388613;
            z = (!z4 && z7) || (z4 && z8);
            if ((!z4 && z8) || (z4 && z7)) {
                z2 = true;
            }
        } else {
            z = (i & 3) == 3;
            if ((i & 5) == 5) {
                z2 = true;
            }
        }
        return interfaceC0678vd.c(c0587sf, Boolean.valueOf(z), Boolean.valueOf(z5), Boolean.valueOf(z2), Boolean.valueOf(z6));
    }

    public final au b(au auVar) {
        Pt mt;
        View view = this.a;
        int i = this.b;
        if (i != 0) {
            Rect rect = new Rect(this.e, this.f, this.g, this.h);
            a(auVar.a.j(), i, new V3(rect));
            view.setPadding(rect.left, rect.top, rect.right, rect.bottom);
        }
        int i2 = this.c;
        if (i2 != 0) {
            if (!this.i) {
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                ViewGroup.MarginLayoutParams marginLayoutParams = layoutParams instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams : null;
                this.j = marginLayoutParams != null ? marginLayoutParams.leftMargin : 0;
                ViewGroup.LayoutParams layoutParams2 = view.getLayoutParams();
                ViewGroup.MarginLayoutParams marginLayoutParams2 = layoutParams2 instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams2 : null;
                this.k = marginLayoutParams2 != null ? marginLayoutParams2.topMargin : 0;
                ViewGroup.LayoutParams layoutParams3 = view.getLayoutParams();
                ViewGroup.MarginLayoutParams marginLayoutParams3 = layoutParams3 instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams3 : null;
                this.l = marginLayoutParams3 != null ? marginLayoutParams3.rightMargin : 0;
                ViewGroup.LayoutParams layoutParams4 = view.getLayoutParams();
                ViewGroup.MarginLayoutParams marginLayoutParams4 = layoutParams4 instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams4 : null;
                this.m = marginLayoutParams4 != null ? marginLayoutParams4.bottomMargin : 0;
                ViewGroup.LayoutParams layoutParams5 = view.getLayoutParams();
                ViewGroup.MarginLayoutParams marginLayoutParams5 = layoutParams5 instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams5 : null;
                this.n = marginLayoutParams5 != null ? marginLayoutParams5.getMarginStart() : 0;
                ViewGroup.LayoutParams layoutParams6 = view.getLayoutParams();
                ViewGroup.MarginLayoutParams marginLayoutParams6 = layoutParams6 instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams6 : null;
                this.o = marginLayoutParams6 != null ? marginLayoutParams6.getMarginEnd() : 0;
                this.i = true;
            }
            int i3 = i2 & 8388608;
            Rect rect2 = i3 == 8388608 ? new Rect(this.j, this.k, this.l, this.m) : new Rect(this.n, this.k, this.o, this.m);
            a(auVar.a.j(), i2, new V3(rect2));
            ViewGroup.LayoutParams layoutParams7 = view.getLayoutParams();
            if (layoutParams7 instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams marginLayoutParams7 = (ViewGroup.MarginLayoutParams) layoutParams7;
                marginLayoutParams7.topMargin = rect2.top;
                marginLayoutParams7.bottomMargin = rect2.bottom;
                if (i3 == 8388608) {
                    marginLayoutParams7.setMarginStart(rect2.left);
                    marginLayoutParams7.setMarginEnd(rect2.right);
                } else {
                    marginLayoutParams7.leftMargin = rect2.left;
                    marginLayoutParams7.rightMargin = rect2.right;
                }
                view.setLayoutParams(layoutParams7);
            }
        }
        int i4 = this.d;
        C0587sf c0587sfJ = i4 != 0 ? (C0587sf) a(auVar.a.j(), i4, new C0703w7()) : auVar.a.j();
        int i5 = Build.VERSION.SDK_INT;
        if (i5 >= 34) {
            mt = new Ot(auVar);
        } else if (i5 >= 30) {
            mt = new Nt(auVar);
        } else {
            mt = i5 >= 29 ? new Mt(auVar) : new Kt(auVar);
        }
        mt.g(c0587sfJ);
        return mt.b();
    }

    @Override // rikka.shizuku.InterfaceC0344kj
    public final au f(View view, au auVar) {
        if (AbstractC0046as.c(this.p, auVar)) {
            return auVar;
        }
        this.p = auVar;
        return b(auVar);
    }
}
