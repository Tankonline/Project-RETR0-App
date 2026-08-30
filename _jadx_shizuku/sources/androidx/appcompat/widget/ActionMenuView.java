package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.LinearLayout;
import androidx.appcompat.view.menu.ActionMenuItemView;
import rikka.shizuku.AbstractC0323jt;
import rikka.shizuku.C0;
import rikka.shizuku.C0075bq;
import rikka.shizuku.C0221gi;
import rikka.shizuku.C0330k5;
import rikka.shizuku.C0758y0;
import rikka.shizuku.D0;
import rikka.shizuku.Dg;
import rikka.shizuku.E0;
import rikka.shizuku.Eg;
import rikka.shizuku.InterfaceC0067bi;
import rikka.shizuku.InterfaceC0098ci;
import rikka.shizuku.InterfaceC0652ui;
import rikka.shizuku.InterfaceC0745xi;
import rikka.shizuku.MenuC0129di;
import rikka.shizuku.Re;
import rikka.shizuku.Ys;

/* JADX INFO: loaded from: classes.dex */
public class ActionMenuView extends Eg implements InterfaceC0098ci, InterfaceC0745xi {
    public C0075bq A;
    public MenuC0129di p;
    public Context q;
    public int r;
    public boolean s;
    public C0 t;
    public C0330k5 u;
    public InterfaceC0067bi v;
    public boolean w;
    public int x;
    public final int y;
    public final int z;

    public ActionMenuView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
        this.a = false;
        float f = context.getResources().getDisplayMetrics().density;
        this.y = (int) (56.0f * f);
        this.z = (int) (f * 4.0f);
        this.q = context;
        this.r = 0;
    }

    public static E0 m() {
        E0 e0 = new E0(-2, -2);
        e0.a = false;
        ((LinearLayout.LayoutParams) e0).gravity = 16;
        return e0;
    }

    public static E0 n(ViewGroup.LayoutParams layoutParams) {
        E0 e0;
        if (layoutParams == null) {
            return m();
        }
        if (layoutParams instanceof E0) {
            E0 e1 = (E0) layoutParams;
            e0 = new E0(e1);
            e0.a = e1.a;
        } else {
            e0 = new E0(layoutParams);
        }
        if (((LinearLayout.LayoutParams) e0).gravity <= 0) {
            ((LinearLayout.LayoutParams) e0).gravity = 16;
        }
        return e0;
    }

    @Override // rikka.shizuku.InterfaceC0745xi
    public final void a(MenuC0129di menuC0129di) {
        this.p = menuC0129di;
    }

    @Override // rikka.shizuku.InterfaceC0098ci
    public final boolean c(C0221gi c0221gi) {
        return this.p.q(c0221gi, null, 0);
    }

    @Override // rikka.shizuku.Eg, android.view.ViewGroup
    public final boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof E0;
    }

    @Override // android.view.View
    public final boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        return false;
    }

    @Override // rikka.shizuku.Eg, android.view.ViewGroup
    public final /* bridge */ /* synthetic */ ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return m();
    }

    @Override // rikka.shizuku.Eg, android.view.ViewGroup
    public final /* bridge */ /* synthetic */ ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return n(layoutParams);
    }

    @Override // rikka.shizuku.Eg
    /* JADX INFO: renamed from: i */
    public final /* bridge */ /* synthetic */ Dg generateDefaultLayoutParams() {
        return m();
    }

    @Override // rikka.shizuku.Eg
    /* JADX INFO: renamed from: j */
    public final Dg generateLayoutParams(AttributeSet attributeSet) {
        return new E0(getContext(), attributeSet);
    }

    @Override // rikka.shizuku.Eg
    /* JADX INFO: renamed from: k */
    public final /* bridge */ /* synthetic */ Dg generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return n(layoutParams);
    }

    public final MenuC0129di o() {
        if (this.p == null) {
            Context context = getContext();
            MenuC0129di menuC0129di = new MenuC0129di(context);
            this.p = menuC0129di;
            menuC0129di.e = new Ys(4, this);
            C0 c0 = new C0(context);
            this.t = c0;
            c0.j = true;
            c0.k = true;
            InterfaceC0652ui re = this.u;
            if (re == null) {
                re = new Re();
            }
            c0.e = re;
            this.p.b(c0, this.q);
            C0 c1 = this.t;
            c1.h = this;
            this.p = c1.c;
        }
        return this.p;
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        C0 c0 = this.t;
        if (c0 != null) {
            c0.i();
            if (this.t.j()) {
                this.t.e();
                this.t.l();
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        C0 c0 = this.t;
        if (c0 != null) {
            c0.e();
            C0758y0 c0758y0 = c0.r;
            if (c0758y0 == null || !c0758y0.b()) {
                return;
            }
            c0758y0.i.dismiss();
        }
    }

    @Override // rikka.shizuku.Eg, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int width;
        int paddingLeft;
        if (!this.w) {
            super.onLayout(z, i, i2, i3, i4);
            return;
        }
        int childCount = getChildCount();
        int i5 = (i4 - i2) / 2;
        int i6 = this.l;
        int i7 = i3 - i;
        int paddingRight = (i7 - getPaddingRight()) - getPaddingLeft();
        boolean zA = AbstractC0323jt.a(this);
        int i8 = 0;
        int i9 = 0;
        for (int i10 = 0; i10 < childCount; i10++) {
            View childAt = getChildAt(i10);
            if (childAt.getVisibility() != 8) {
                E0 e0 = (E0) childAt.getLayoutParams();
                if (e0.a) {
                    int measuredWidth = childAt.getMeasuredWidth();
                    if (p(i10)) {
                        measuredWidth += i6;
                    }
                    int measuredHeight = childAt.getMeasuredHeight();
                    if (zA) {
                        paddingLeft = getPaddingLeft() + ((LinearLayout.LayoutParams) e0).leftMargin;
                        width = paddingLeft + measuredWidth;
                    } else {
                        width = (getWidth() - getPaddingRight()) - ((LinearLayout.LayoutParams) e0).rightMargin;
                        paddingLeft = width - measuredWidth;
                    }
                    int i11 = i5 - (measuredHeight / 2);
                    childAt.layout(paddingLeft, i11, width, measuredHeight + i11);
                    paddingRight -= measuredWidth;
                    i8 = 1;
                } else {
                    paddingRight -= (childAt.getMeasuredWidth() + ((LinearLayout.LayoutParams) e0).leftMargin) + ((LinearLayout.LayoutParams) e0).rightMargin;
                    p(i10);
                    i9++;
                }
            }
        }
        if (childCount == 1 && i8 == 0) {
            View childAt2 = getChildAt(0);
            int measuredWidth2 = childAt2.getMeasuredWidth();
            int measuredHeight2 = childAt2.getMeasuredHeight();
            int i12 = (i7 / 2) - (measuredWidth2 / 2);
            int i13 = i5 - (measuredHeight2 / 2);
            childAt2.layout(i12, i13, measuredWidth2 + i12, measuredHeight2 + i13);
            return;
        }
        int i14 = i9 - (i8 ^ 1);
        int iMax = Math.max(0, i14 > 0 ? paddingRight / i14 : 0);
        if (zA) {
            int width2 = getWidth() - getPaddingRight();
            for (int i15 = 0; i15 < childCount; i15++) {
                View childAt3 = getChildAt(i15);
                E0 e1 = (E0) childAt3.getLayoutParams();
                if (childAt3.getVisibility() != 8 && !e1.a) {
                    int i16 = width2 - ((LinearLayout.LayoutParams) e1).rightMargin;
                    int measuredWidth3 = childAt3.getMeasuredWidth();
                    int measuredHeight3 = childAt3.getMeasuredHeight();
                    int i17 = i5 - (measuredHeight3 / 2);
                    childAt3.layout(i16 - measuredWidth3, i17, i16, measuredHeight3 + i17);
                    width2 = i16 - ((measuredWidth3 + ((LinearLayout.LayoutParams) e1).leftMargin) + iMax);
                }
            }
            return;
        }
        int paddingLeft2 = getPaddingLeft();
        for (int i18 = 0; i18 < childCount; i18++) {
            View childAt4 = getChildAt(i18);
            E0 e2 = (E0) childAt4.getLayoutParams();
            if (childAt4.getVisibility() != 8 && !e2.a) {
                int i19 = paddingLeft2 + ((LinearLayout.LayoutParams) e2).leftMargin;
                int measuredWidth4 = childAt4.getMeasuredWidth();
                int measuredHeight4 = childAt4.getMeasuredHeight();
                int i20 = i5 - (measuredHeight4 / 2);
                childAt4.layout(i19, i20, i19 + measuredWidth4, measuredHeight4 + i20);
                paddingLeft2 = measuredWidth4 + ((LinearLayout.LayoutParams) e2).rightMargin + iMax + i19;
            }
        }
    }

    /* JADX WARN: Type inference failed for: r11v14 */
    /* JADX WARN: Type inference failed for: r11v15, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r11v17 */
    /* JADX WARN: Type inference failed for: r11v40 */
    @Override // rikka.shizuku.Eg, android.view.View
    public final void onMeasure(int i, int i2) {
        int i3;
        int i4;
        ?? r11;
        int i5;
        int i6;
        MenuC0129di menuC0129di;
        boolean z = this.w;
        boolean z2 = View.MeasureSpec.getMode(i) == 1073741824;
        this.w = z2;
        if (z != z2) {
            this.x = 0;
        }
        int size = View.MeasureSpec.getSize(i);
        if (this.w && (menuC0129di = this.p) != null && size != this.x) {
            this.x = size;
            menuC0129di.p(true);
        }
        int childCount = getChildCount();
        if (!this.w || childCount <= 0) {
            for (int i7 = 0; i7 < childCount; i7++) {
                E0 e0 = (E0) getChildAt(i7).getLayoutParams();
                ((LinearLayout.LayoutParams) e0).rightMargin = 0;
                ((LinearLayout.LayoutParams) e0).leftMargin = 0;
            }
            super.onMeasure(i, i2);
            return;
        }
        int mode = View.MeasureSpec.getMode(i2);
        int size2 = View.MeasureSpec.getSize(i);
        int size3 = View.MeasureSpec.getSize(i2);
        int paddingRight = getPaddingRight() + getPaddingLeft();
        int paddingBottom = getPaddingBottom() + getPaddingTop();
        int childMeasureSpec = ViewGroup.getChildMeasureSpec(i2, paddingBottom, -2);
        int i8 = size2 - paddingRight;
        int i9 = this.y;
        int i10 = i8 / i9;
        int i11 = i8 % i9;
        if (i10 == 0) {
            setMeasuredDimension(i8, 0);
            return;
        }
        int i12 = (i11 / i10) + i9;
        int childCount2 = getChildCount();
        int iMax = 0;
        int i13 = 0;
        int iMax2 = 0;
        int i14 = 0;
        boolean z3 = false;
        int i15 = 0;
        long j = 0;
        while (true) {
            i3 = this.z;
            if (i14 >= childCount2) {
                break;
            }
            View childAt = getChildAt(i14);
            int i16 = size3;
            int i17 = paddingBottom;
            if (childAt.getVisibility() == 8) {
                i5 = i12;
            } else {
                boolean z4 = childAt instanceof ActionMenuItemView;
                i13++;
                if (z4) {
                    childAt.setPadding(i3, 0, i3, 0);
                }
                E0 e1 = (E0) childAt.getLayoutParams();
                e1.f = false;
                e1.c = 0;
                e1.b = 0;
                e1.d = false;
                ((LinearLayout.LayoutParams) e1).leftMargin = 0;
                ((LinearLayout.LayoutParams) e1).rightMargin = 0;
                e1.e = z4 && !TextUtils.isEmpty(((ActionMenuItemView) childAt).getText());
                int i18 = e1.a ? 1 : i10;
                E0 e2 = (E0) childAt.getLayoutParams();
                int i19 = i10;
                i5 = i12;
                int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(childMeasureSpec) - i17, View.MeasureSpec.getMode(childMeasureSpec));
                ActionMenuItemView actionMenuItemView = z4 ? (ActionMenuItemView) childAt : null;
                boolean z5 = (actionMenuItemView == null || TextUtils.isEmpty(actionMenuItemView.getText())) ? false : true;
                boolean z6 = z5;
                if (i18 <= 0 || (z5 && i18 < 2)) {
                    i6 = 0;
                } else {
                    childAt.measure(View.MeasureSpec.makeMeasureSpec(i5 * i18, Integer.MIN_VALUE), iMakeMeasureSpec);
                    int measuredWidth = childAt.getMeasuredWidth();
                    i6 = measuredWidth / i5;
                    if (measuredWidth % i5 != 0) {
                        i6++;
                    }
                    if (z6 && i6 < 2) {
                        i6 = 2;
                    }
                }
                e2.d = !e2.a && z6;
                e2.b = i6;
                childAt.measure(View.MeasureSpec.makeMeasureSpec(i6 * i5, 1073741824), iMakeMeasureSpec);
                iMax2 = Math.max(iMax2, i6);
                if (e1.d) {
                    i15++;
                }
                if (e1.a) {
                    z3 = true;
                }
                i10 = i19 - i6;
                iMax = Math.max(iMax, childAt.getMeasuredHeight());
                if (i6 == 1) {
                    j |= (long) (1 << i14);
                }
            }
            i14++;
            size3 = i16;
            paddingBottom = i17;
            i12 = i5;
        }
        int i20 = size3;
        int i21 = i10;
        int i22 = i12;
        boolean z7 = z3 && i13 == 2;
        int i23 = i21;
        boolean z8 = false;
        while (true) {
            if (i15 <= 0 || i23 <= 0) {
                i4 = iMax;
                break;
            }
            int i24 = Integer.MAX_VALUE;
            long j2 = 0;
            int i25 = 0;
            int i26 = 0;
            while (i26 < childCount2) {
                boolean z9 = z7;
                E0 e3 = (E0) getChildAt(i26).getLayoutParams();
                int i27 = iMax;
                if (e3.d) {
                    int i28 = e3.b;
                    if (i28 < i24) {
                        j2 = 1 << i26;
                        i24 = i28;
                        i25 = 1;
                    } else if (i28 == i24) {
                        j2 |= 1 << i26;
                        i25++;
                    }
                }
                i26++;
                iMax = i27;
                z7 = z9;
            }
            boolean z10 = z7;
            i4 = iMax;
            j |= j2;
            if (i25 > i23) {
                break;
            }
            int i29 = i24 + 1;
            int i30 = 0;
            while (i30 < childCount2) {
                View childAt2 = getChildAt(i30);
                E0 e4 = (E0) childAt2.getLayoutParams();
                boolean z11 = z3;
                long j3 = 1 << i30;
                if ((j2 & j3) != 0) {
                    if (z10 && e4.e) {
                        r11 = 1;
                        r11 = 1;
                        if (i23 == 1) {
                            childAt2.setPadding(i3 + i22, 0, i3, 0);
                        }
                    } else {
                        r11 = 1;
                    }
                    e4.b += r11;
                    e4.f = r11;
                    i23--;
                } else if (e4.b == i29) {
                    j |= j3;
                }
                i30++;
                z3 = z11;
            }
            iMax = i4;
            z7 = z10;
            z8 = true;
        }
        boolean z12 = !z3 && i13 == 1;
        if (i23 > 0 && j != 0 && (i23 < i13 - 1 || z12 || iMax2 > 1)) {
            float fBitCount = Long.bitCount(j);
            if (!z12) {
                if ((j & 1) != 0 && !((E0) getChildAt(0).getLayoutParams()).e) {
                    fBitCount -= 0.5f;
                }
                int i31 = childCount2 - 1;
                if ((j & ((long) (1 << i31))) != 0 && !((E0) getChildAt(i31).getLayoutParams()).e) {
                    fBitCount -= 0.5f;
                }
            }
            int i32 = fBitCount > 0.0f ? (int) ((i23 * i22) / fBitCount) : 0;
            boolean z13 = z8;
            for (int i33 = 0; i33 < childCount2; i33++) {
                if ((j & ((long) (1 << i33))) != 0) {
                    View childAt3 = getChildAt(i33);
                    E0 e5 = (E0) childAt3.getLayoutParams();
                    if (childAt3 instanceof ActionMenuItemView) {
                        e5.c = i32;
                        e5.f = true;
                        if (i33 == 0 && !e5.e) {
                            ((LinearLayout.LayoutParams) e5).leftMargin = (-i32) / 2;
                        }
                        z13 = true;
                    } else if (e5.a) {
                        e5.c = i32;
                        e5.f = true;
                        ((LinearLayout.LayoutParams) e5).rightMargin = (-i32) / 2;
                        z13 = true;
                    } else {
                        if (i33 != 0) {
                            ((LinearLayout.LayoutParams) e5).leftMargin = i32 / 2;
                        }
                        if (i33 != childCount2 - 1) {
                            ((LinearLayout.LayoutParams) e5).rightMargin = i32 / 2;
                        }
                    }
                }
            }
            z8 = z13;
        }
        if (z8) {
            for (int i34 = 0; i34 < childCount2; i34++) {
                View childAt4 = getChildAt(i34);
                E0 e6 = (E0) childAt4.getLayoutParams();
                if (e6.f) {
                    childAt4.measure(View.MeasureSpec.makeMeasureSpec((e6.b * i22) + e6.c, 1073741824), childMeasureSpec);
                }
            }
        }
        setMeasuredDimension(i8, mode != 1073741824 ? i4 : i20);
    }

    public final boolean p(int i) {
        boolean zA = false;
        if (i == 0) {
            return false;
        }
        KeyEvent.Callback childAt = getChildAt(i - 1);
        KeyEvent.Callback childAt2 = getChildAt(i);
        if (i < getChildCount() && (childAt instanceof D0)) {
            zA = ((D0) childAt).a();
        }
        return (i <= 0 || !(childAt2 instanceof D0)) ? zA : ((D0) childAt2).c() | zA;
    }

    @Override // rikka.shizuku.Eg, android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new E0(getContext(), attributeSet);
    }
}
