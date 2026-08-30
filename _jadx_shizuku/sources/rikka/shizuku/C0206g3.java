package rikka.shizuku;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.ListAdapter;
import android.widget.SpinnerAdapter;
import moe.shizuku.privileged.api.R;

/* JADX INFO: renamed from: rikka.shizuku.g3, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0206g3 extends Zg implements InterfaceC0268i3 {
    public CharSequence C;
    public ListAdapter D;
    public final Rect E;
    public int F;
    public final /* synthetic */ C0297j3 G;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0206g3(C0297j3 c0297j3, Context context, AttributeSet attributeSet) {
        super(context, attributeSet, R.attr._0_resource_name_obfuscated_res_0x7f04044d);
        this.G = c0297j3;
        this.E = new Rect();
        this.o = c0297j3;
        this.y = true;
        this.z.setFocusable(true);
        this.p = new C0144e3(0, this);
    }

    @Override // rikka.shizuku.InterfaceC0268i3
    public final void d(int i, int i2) {
        ViewTreeObserver viewTreeObserver;
        Q2 q2 = this.z;
        boolean zIsShowing = q2.isShowing();
        q();
        this.z.setInputMethodMode(2);
        e();
        C0490pa c0490pa = this.c;
        c0490pa.setChoiceMode(1);
        AbstractC0021a3.d(c0490pa, i);
        AbstractC0021a3.c(c0490pa, i2);
        C0297j3 c0297j3 = this.G;
        int selectedItemPosition = c0297j3.getSelectedItemPosition();
        C0490pa c0490pa2 = this.c;
        if (q2.isShowing() && c0490pa2 != null) {
            c0490pa2.h = false;
            c0490pa2.setSelection(selectedItemPosition);
            if (c0490pa2.getChoiceMode() != 0) {
                c0490pa2.setItemChecked(selectedItemPosition, true);
            }
        }
        if (zIsShowing || (viewTreeObserver = c0297j3.getViewTreeObserver()) == null) {
            return;
        }
        Y2 y2 = new Y2(1, this);
        viewTreeObserver.addOnGlobalLayoutListener(y2);
        this.z.setOnDismissListener(new C0175f3(this, y2));
    }

    @Override // rikka.shizuku.InterfaceC0268i3
    public final CharSequence h() {
        return this.C;
    }

    @Override // rikka.shizuku.InterfaceC0268i3
    public final void i(CharSequence charSequence) {
        this.C = charSequence;
    }

    @Override // rikka.shizuku.Zg, rikka.shizuku.InterfaceC0268i3
    public final void m(ListAdapter listAdapter) {
        super.m(listAdapter);
        this.D = listAdapter;
    }

    @Override // rikka.shizuku.InterfaceC0268i3
    public final void n(int i) {
        this.F = i;
    }

    public final void q() {
        int i;
        Q2 q2 = this.z;
        Drawable background = q2.getBackground();
        C0297j3 c0297j3 = this.G;
        if (background != null) {
            background.getPadding(c0297j3.h);
            boolean zA = AbstractC0323jt.a(c0297j3);
            Rect rect = c0297j3.h;
            i = zA ? rect.right : -rect.left;
        } else {
            Rect rect2 = c0297j3.h;
            rect2.right = 0;
            rect2.left = 0;
            i = 0;
        }
        int paddingLeft = c0297j3.getPaddingLeft();
        int paddingRight = c0297j3.getPaddingRight();
        int width = c0297j3.getWidth();
        int i2 = c0297j3.g;
        if (i2 == -2) {
            int iA = c0297j3.a((SpinnerAdapter) this.D, q2.getBackground());
            int i3 = c0297j3.getContext().getResources().getDisplayMetrics().widthPixels;
            Rect rect3 = c0297j3.h;
            int i4 = (i3 - rect3.left) - rect3.right;
            if (iA > i4) {
                iA = i4;
            }
            p(Math.max(iA, (width - paddingLeft) - paddingRight));
        } else if (i2 == -1) {
            p((width - paddingLeft) - paddingRight);
        } else {
            p(i2);
        }
        this.f = AbstractC0323jt.a(c0297j3) ? (((width - paddingRight) - this.e) - this.F) + i : paddingLeft + this.F + i;
    }
}
