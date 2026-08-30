package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;
import java.util.ArrayList;
import java.util.WeakHashMap;
import moe.shizuku.privileged.api.R;
import rikka.shizuku.AbstractC0106cq;
import rikka.shizuku.AbstractC0323jt;
import rikka.shizuku.AbstractC0688vn;
import rikka.shizuku.C0;
import rikka.shizuku.C0075bq;
import rikka.shizuku.C0136dq;
import rikka.shizuku.C0167eq;
import rikka.shizuku.C0198fq;
import rikka.shizuku.C0221gi;
import rikka.shizuku.C0229gq;
import rikka.shizuku.C0286im;
import rikka.shizuku.C0330k5;
import rikka.shizuku.C0351kq;
import rikka.shizuku.C0381lp;
import rikka.shizuku.C0606t3;
import rikka.shizuku.Hs;
import rikka.shizuku.K3;
import rikka.shizuku.MenuC0129di;
import rikka.shizuku.N2;
import rikka.shizuku.Nk;
import rikka.shizuku.O2;
import rikka.shizuku.RunnableC0044aq;
import rikka.shizuku.RunnableC0607t4;

/* JADX INFO: loaded from: classes.dex */
public class Toolbar extends ViewGroup {
    public final ColorStateList A;
    public boolean B;
    public boolean C;
    public final ArrayList D;
    public final ArrayList E;
    public final int[] F;
    public final K3 G;
    public ArrayList H;
    public C0229gq I;
    public final C0075bq J;
    public C0351kq K;
    public C0 L;
    public C0136dq M;
    public C0330k5 N;
    public C0229gq O;
    public OnBackInvokedCallback P;
    public OnBackInvokedDispatcher Q;
    public boolean R;
    public final RunnableC0607t4 S;
    public ActionMenuView a;
    public C0606t3 b;
    public C0606t3 c;
    public N2 d;
    public O2 e;
    public final Drawable f;
    public final CharSequence g;
    public N2 h;
    public View i;
    public Context j;
    public int k;
    public int l;
    public int m;
    public final int n;
    public final int o;
    public final int p;
    public final int q;
    public final int r;
    public final int s;
    public C0286im t;
    public int u;
    public final int v;
    public final int w;
    public CharSequence x;
    public CharSequence y;
    public final ColorStateList z;

    public Toolbar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public static C0167eq f() {
        C0167eq c0167eq = new C0167eq(-2, -2);
        c0167eq.b = 0;
        c0167eq.a = 8388627;
        return c0167eq;
    }

    public static C0167eq g(ViewGroup.LayoutParams layoutParams) {
        boolean z = layoutParams instanceof C0167eq;
        if (z) {
            C0167eq c0167eq = (C0167eq) layoutParams;
            C0167eq c0167eq2 = new C0167eq(c0167eq);
            c0167eq2.b = 0;
            c0167eq2.b = c0167eq.b;
            return c0167eq2;
        }
        if (z) {
            C0167eq c0167eq3 = new C0167eq((C0167eq) layoutParams);
            c0167eq3.b = 0;
            return c0167eq3;
        }
        if (!(layoutParams instanceof ViewGroup.MarginLayoutParams)) {
            C0167eq c0167eq4 = new C0167eq(layoutParams);
            c0167eq4.b = 0;
            return c0167eq4;
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
        C0167eq c0167eq5 = new C0167eq(marginLayoutParams);
        c0167eq5.b = 0;
        ((ViewGroup.MarginLayoutParams) c0167eq5).leftMargin = marginLayoutParams.leftMargin;
        ((ViewGroup.MarginLayoutParams) c0167eq5).topMargin = marginLayoutParams.topMargin;
        ((ViewGroup.MarginLayoutParams) c0167eq5).rightMargin = marginLayoutParams.rightMargin;
        ((ViewGroup.MarginLayoutParams) c0167eq5).bottomMargin = marginLayoutParams.bottomMargin;
        return c0167eq5;
    }

    public static int l(View view) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        return marginLayoutParams.getMarginEnd() + marginLayoutParams.getMarginStart();
    }

    public static int o(View view) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        return marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
    }

    public final void A(CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            C0606t3 c0606t3 = this.b;
            if (c0606t3 != null && q(c0606t3)) {
                removeView(this.b);
                this.E.remove(this.b);
            }
        } else {
            if (this.b == null) {
                Context context = getContext();
                C0606t3 c0606t4 = new C0606t3(context, null);
                this.b = c0606t4;
                c0606t4.setSingleLine();
                this.b.setEllipsize(TextUtils.TruncateAt.END);
                int i = this.l;
                if (i != 0) {
                    this.b.setTextAppearance(context, i);
                }
                ColorStateList colorStateList = this.z;
                if (colorStateList != null) {
                    this.b.setTextColor(colorStateList);
                }
            }
            if (!q(this.b)) {
                b(this.b, true);
            }
        }
        C0606t3 c0606t5 = this.b;
        if (c0606t5 != null) {
            c0606t5.setText(charSequence);
        }
        this.x = charSequence;
    }

    public final boolean B(View view) {
        return (view == null || view.getParent() != this || view.getVisibility() == 8) ? false : true;
    }

    public final boolean C() {
        C0 c0;
        ActionMenuView actionMenuView = this.a;
        return (actionMenuView == null || (c0 = actionMenuView.t) == null || !c0.l()) ? false : true;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x0023  */
    public final void D() {
        boolean z;
        OnBackInvokedDispatcher onBackInvokedDispatcher;
        int i = 0;
        if (Build.VERSION.SDK_INT >= 33) {
            OnBackInvokedDispatcher onBackInvokedDispatcherA = AbstractC0106cq.a(this);
            C0136dq c0136dq = this.M;
            if (c0136dq == null || c0136dq.b == null || onBackInvokedDispatcherA == null) {
                z = false;
            } else {
                WeakHashMap weakHashMap = Hs.a;
                if (isAttachedToWindow() && this.R) {
                    z = true;
                } else {
                    z = false;
                }
            }
            if (z && this.Q == null) {
                if (this.P == null) {
                    this.P = AbstractC0106cq.b(new RunnableC0044aq(this, i));
                }
                AbstractC0106cq.c(onBackInvokedDispatcherA, this.P);
                this.Q = onBackInvokedDispatcherA;
                return;
            }
            if (z || (onBackInvokedDispatcher = this.Q) == null) {
                return;
            }
            AbstractC0106cq.d(onBackInvokedDispatcher, this.P);
            this.Q = null;
        }
    }

    public final void a(ArrayList arrayList, int i) {
        WeakHashMap weakHashMap = Hs.a;
        boolean z = getLayoutDirection() == 1;
        int childCount = getChildCount();
        int absoluteGravity = Gravity.getAbsoluteGravity(i, getLayoutDirection());
        arrayList.clear();
        if (!z) {
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = getChildAt(i2);
                C0167eq c0167eq = (C0167eq) childAt.getLayoutParams();
                if (c0167eq.b == 0 && B(childAt)) {
                    int i3 = c0167eq.a;
                    WeakHashMap weakHashMap2 = Hs.a;
                    int layoutDirection = getLayoutDirection();
                    int absoluteGravity2 = Gravity.getAbsoluteGravity(i3, layoutDirection) & 7;
                    if (absoluteGravity2 != 1 && absoluteGravity2 != 3 && absoluteGravity2 != 5) {
                        absoluteGravity2 = layoutDirection == 1 ? 5 : 3;
                    }
                    if (absoluteGravity2 == absoluteGravity) {
                        arrayList.add(childAt);
                    }
                }
            }
            return;
        }
        for (int i4 = childCount - 1; i4 >= 0; i4--) {
            View childAt2 = getChildAt(i4);
            C0167eq c0167eq2 = (C0167eq) childAt2.getLayoutParams();
            if (c0167eq2.b == 0 && B(childAt2)) {
                int i5 = c0167eq2.a;
                WeakHashMap weakHashMap3 = Hs.a;
                int layoutDirection2 = getLayoutDirection();
                int absoluteGravity3 = Gravity.getAbsoluteGravity(i5, layoutDirection2) & 7;
                if (absoluteGravity3 != 1 && absoluteGravity3 != 3 && absoluteGravity3 != 5) {
                    absoluteGravity3 = layoutDirection2 == 1 ? 5 : 3;
                }
                if (absoluteGravity3 == absoluteGravity) {
                    arrayList.add(childAt2);
                }
            }
        }
    }

    public final void b(View view, boolean z) {
        C0167eq c0167eqG;
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            c0167eqG = f();
        } else {
            c0167eqG = !checkLayoutParams(layoutParams) ? g(layoutParams) : (C0167eq) layoutParams;
        }
        c0167eqG.b = 1;
        if (!z || this.i == null) {
            addView(view, c0167eqG);
        } else {
            view.setLayoutParams(c0167eqG);
            this.E.add(view);
        }
    }

    public final void c() {
        if (this.t == null) {
            C0286im c0286im = new C0286im();
            c0286im.a = 0;
            c0286im.b = 0;
            c0286im.c = Integer.MIN_VALUE;
            c0286im.d = Integer.MIN_VALUE;
            c0286im.e = 0;
            c0286im.f = 0;
            c0286im.g = false;
            c0286im.h = false;
            this.t = c0286im;
        }
    }

    @Override // android.view.ViewGroup
    public final boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return super.checkLayoutParams(layoutParams) && (layoutParams instanceof C0167eq);
    }

    public final void d() {
        if (this.a == null) {
            ActionMenuView actionMenuView = new ActionMenuView(getContext(), null);
            this.a = actionMenuView;
            int i = this.k;
            if (actionMenuView.r != i) {
                actionMenuView.r = i;
                if (i == 0) {
                    actionMenuView.q = actionMenuView.getContext();
                } else {
                    actionMenuView.q = new ContextThemeWrapper(actionMenuView.getContext(), i);
                }
            }
            ActionMenuView actionMenuView2 = this.a;
            actionMenuView2.A = this.J;
            C0330k5 c0330k5 = this.N;
            C0075bq c0075bq = new C0075bq(this);
            actionMenuView2.u = c0330k5;
            actionMenuView2.v = c0075bq;
            C0167eq c0167eqF = f();
            c0167eqF.a = (this.n & 112) | 8388613;
            this.a.setLayoutParams(c0167eqF);
            b(this.a, false);
        }
    }

    public final void e() {
        if (this.d == null) {
            this.d = new N2(getContext(), null, R.attr._0_resource_name_obfuscated_res_0x7f040531);
            C0167eq c0167eqF = f();
            c0167eqF.a = (this.n & 112) | 8388611;
            this.d.setLayoutParams(c0167eqF);
        }
    }

    @Override // android.view.ViewGroup
    public final /* bridge */ /* synthetic */ ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return f();
    }

    @Override // android.view.ViewGroup
    public final /* bridge */ /* synthetic */ ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return g(layoutParams);
    }

    public final int h(View view, int i) {
        C0167eq c0167eq = (C0167eq) view.getLayoutParams();
        int measuredHeight = view.getMeasuredHeight();
        int i2 = i > 0 ? (measuredHeight - i) / 2 : 0;
        int i3 = c0167eq.a & 112;
        if (i3 != 16 && i3 != 48 && i3 != 80) {
            i3 = this.w & 112;
        }
        if (i3 == 48) {
            return getPaddingTop() - i2;
        }
        if (i3 == 80) {
            return (((getHeight() - getPaddingBottom()) - measuredHeight) - ((ViewGroup.MarginLayoutParams) c0167eq).bottomMargin) - i2;
        }
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int height = getHeight();
        int iMax = (((height - paddingTop) - paddingBottom) - measuredHeight) / 2;
        int i4 = ((ViewGroup.MarginLayoutParams) c0167eq).topMargin;
        if (iMax < i4) {
            iMax = i4;
        } else {
            int i5 = (((height - paddingBottom) - measuredHeight) - iMax) - paddingTop;
            int i6 = ((ViewGroup.MarginLayoutParams) c0167eq).bottomMargin;
            if (i5 < i6) {
                iMax = Math.max(0, iMax - (i6 - i5));
            }
        }
        return paddingTop + iMax;
    }

    public final int i() {
        MenuC0129di menuC0129di;
        int i;
        ActionMenuView actionMenuView = this.a;
        if (actionMenuView == null || (menuC0129di = actionMenuView.p) == null || !menuC0129di.hasVisibleItems()) {
            C0286im c0286im = this.t;
            if (c0286im != null) {
                return c0286im.g ? c0286im.a : c0286im.b;
            }
            return 0;
        }
        C0286im c0286im2 = this.t;
        if (c0286im2 != null) {
            i = c0286im2.g ? c0286im2.a : c0286im2.b;
        } else {
            i = 0;
        }
        return Math.max(i, Math.max(this.v, 0));
    }

    public final int j() {
        int i;
        if (n() == null) {
            C0286im c0286im = this.t;
            if (c0286im != null) {
                return c0286im.g ? c0286im.b : c0286im.a;
            }
            return 0;
        }
        C0286im c0286im2 = this.t;
        if (c0286im2 != null) {
            i = c0286im2.g ? c0286im2.b : c0286im2.a;
        } else {
            i = 0;
        }
        return Math.max(i, Math.max(this.u, 0));
    }

    public final ArrayList k() {
        ArrayList arrayList = new ArrayList();
        MenuC0129di menuC0129diM = m();
        for (int i = 0; i < menuC0129diM.f.size(); i++) {
            arrayList.add(menuC0129diM.getItem(i));
        }
        return arrayList;
    }

    public final MenuC0129di m() {
        d();
        ActionMenuView actionMenuView = this.a;
        if (actionMenuView.p == null) {
            MenuC0129di menuC0129diO = actionMenuView.o();
            if (this.M == null) {
                this.M = new C0136dq(this);
            }
            this.a.t.o = true;
            menuC0129diO.b(this.M, this.j);
            D();
        }
        return this.a.o();
    }

    public final Drawable n() {
        N2 n2 = this.d;
        if (n2 != null) {
            return n2.getDrawable();
        }
        return null;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        D();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(this.S);
        D();
    }

    @Override // android.view.View
    public final boolean onHoverEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 9) {
            this.C = false;
        }
        if (!this.C) {
            boolean zOnHoverEvent = super.onHoverEvent(motionEvent);
            if (actionMasked == 9 && !zOnHoverEvent) {
                this.C = true;
            }
        }
        if (actionMasked != 10 && actionMasked != 3) {
            return true;
        }
        this.C = false;
        return true;
    }

    /* JADX WARN: Code duplicated, block: B:100:0x022e  */
    /* JADX WARN: Code duplicated, block: B:101:0x0231 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:103:0x0235  */
    /* JADX WARN: Code duplicated, block: B:106:0x0249  */
    /* JADX WARN: Code duplicated, block: B:107:0x026a  */
    /* JADX WARN: Code duplicated, block: B:109:0x026d  */
    /* JADX WARN: Code duplicated, block: B:110:0x028d  */
    /* JADX WARN: Code duplicated, block: B:112:0x0290  */
    /* JADX WARN: Code duplicated, block: B:113:0x0297  */
    /* JADX WARN: Code duplicated, block: B:116:0x02a8 A[LOOP:0: B:115:0x02a6->B:116:0x02a8, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:119:0x02c1 A[LOOP:1: B:118:0x02bf->B:119:0x02c1, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:122:0x02e1 A[LOOP:2: B:121:0x02df->B:122:0x02e1, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:126:0x0327 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:127:0x0329  */
    /* JADX WARN: Code duplicated, block: B:128:0x032d  */
    /* JADX WARN: Code duplicated, block: B:131:0x0334 A[LOOP:3: B:130:0x0332->B:131:0x0334, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:19:0x0062 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:20:0x0064  */
    /* JADX WARN: Code duplicated, block: B:21:0x006b  */
    /* JADX WARN: Code duplicated, block: B:24:0x0079 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:25:0x007b  */
    /* JADX WARN: Code duplicated, block: B:26:0x0082  */
    /* JADX WARN: Code duplicated, block: B:29:0x008e  */
    /* JADX WARN: Code duplicated, block: B:30:0x0093  */
    /* JADX WARN: Code duplicated, block: B:33:0x009d  */
    /* JADX WARN: Code duplicated, block: B:35:0x00a4  */
    /* JADX WARN: Code duplicated, block: B:38:0x00cf A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:39:0x00d1  */
    /* JADX WARN: Code duplicated, block: B:40:0x00d8  */
    /* JADX WARN: Code duplicated, block: B:43:0x00e6 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:44:0x00e8  */
    /* JADX WARN: Code duplicated, block: B:45:0x00ef  */
    /* JADX WARN: Code duplicated, block: B:48:0x0103  */
    /* JADX WARN: Code duplicated, block: B:49:0x0118  */
    /* JADX WARN: Code duplicated, block: B:51:0x011b  */
    /* JADX WARN: Code duplicated, block: B:52:0x0133  */
    /* JADX WARN: Code duplicated, block: B:57:0x013e A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:58:0x0140  */
    /* JADX WARN: Code duplicated, block: B:59:0x0143  */
    /* JADX WARN: Code duplicated, block: B:61:0x0147  */
    /* JADX WARN: Code duplicated, block: B:62:0x014a  */
    /* JADX WARN: Code duplicated, block: B:65:0x015a  */
    /* JADX WARN: Code duplicated, block: B:67:0x0162 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:75:0x0181  */
    /* JADX WARN: Code duplicated, block: B:77:0x0189  */
    /* JADX WARN: Code duplicated, block: B:79:0x0195  */
    /* JADX WARN: Code duplicated, block: B:80:0x0197  */
    /* JADX WARN: Code duplicated, block: B:82:0x01a1  */
    /* JADX WARN: Code duplicated, block: B:84:0x01ae  */
    /* JADX WARN: Code duplicated, block: B:85:0x01b7  */
    /* JADX WARN: Code duplicated, block: B:88:0x01c6 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:90:0x01c9  */
    /* JADX WARN: Code duplicated, block: B:93:0x01dd  */
    /* JADX WARN: Code duplicated, block: B:94:0x01ff  */
    /* JADX WARN: Code duplicated, block: B:96:0x0202  */
    /* JADX WARN: Code duplicated, block: B:97:0x0224  */
    /* JADX WARN: Code duplicated, block: B:99:0x0227  */
    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int iS;
        int iT;
        int iJ;
        int i5;
        boolean z2;
        int iMax;
        int iMin;
        boolean zB;
        boolean zB2;
        int measuredHeight;
        C0606t3 c0606t3;
        C0606t3 c0606t4;
        C0167eq c0167eq;
        C0167eq c0167eq2;
        boolean z3;
        int i6;
        int i7;
        int i8;
        int paddingTop;
        int i9;
        int i10;
        int iMax2;
        int i11;
        int i12;
        int iMax3;
        int iMax4;
        int i13;
        int i14;
        int i15;
        int iMax5;
        int i16;
        int i17;
        ArrayList arrayList;
        int size;
        int iS2;
        int i18;
        int size2;
        int iT2;
        int i19;
        int i20;
        int size3;
        int i21;
        int i22;
        int measuredWidth;
        int i23;
        int i24;
        int i25;
        int size4;
        WeakHashMap weakHashMap = Hs.a;
        boolean z4 = getLayoutDirection() == 1;
        int width = getWidth();
        int height = getHeight();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop2 = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int i26 = width - paddingRight;
        int[] iArr = this.F;
        iArr[1] = 0;
        iArr[0] = 0;
        int minimumHeight = getMinimumHeight();
        int iMin2 = minimumHeight >= 0 ? Math.min(minimumHeight, i4 - i2) : 0;
        if (B(this.d)) {
            if (z4) {
                iT = t(this.d, i26, iMin2, iArr);
                iS = paddingLeft;
            } else {
                iS = s(this.d, paddingLeft, iMin2, iArr);
            }
            if (B(this.h)) {
                if (z4) {
                    iT = t(this.h, iT, iMin2, iArr);
                } else {
                    iS = s(this.h, iS, iMin2, iArr);
                }
            }
            if (B(this.a)) {
                if (z4) {
                    iS = s(this.a, iS, iMin2, iArr);
                } else {
                    iT = t(this.a, iT, iMin2, iArr);
                }
            }
            if (getLayoutDirection() == 1) {
                iJ = i();
            } else {
                iJ = j();
            }
            if (getLayoutDirection() == 1) {
                i5 = j();
            } else {
                i5 = i();
            }
            z2 = z4;
            iArr[0] = Math.max(0, iJ - iS);
            iArr[1] = Math.max(0, i5 - (i26 - iT));
            iMax = Math.max(iS, iJ);
            iMin = Math.min(iT, i26 - i5);
            if (B(this.i)) {
                if (z2) {
                    iMin = t(this.i, iMin, iMin2, iArr);
                } else {
                    iMax = s(this.i, iMax, iMin2, iArr);
                }
            }
            if (B(this.e)) {
                if (z2) {
                    iMin = t(this.e, iMin, iMin2, iArr);
                } else {
                    iMax = s(this.e, iMax, iMin2, iArr);
                }
            }
            zB = B(this.b);
            zB2 = B(this.c);
            if (zB) {
                C0167eq c0167eq3 = (C0167eq) this.b.getLayoutParams();
                measuredHeight = ((ViewGroup.MarginLayoutParams) c0167eq3).bottomMargin + this.b.getMeasuredHeight() + ((ViewGroup.MarginLayoutParams) c0167eq3).topMargin;
            } else {
                measuredHeight = 0;
            }
            if (zB2) {
                C0167eq c0167eq4 = (C0167eq) this.c.getLayoutParams();
                measuredHeight += this.c.getMeasuredHeight() + ((ViewGroup.MarginLayoutParams) c0167eq4).topMargin + ((ViewGroup.MarginLayoutParams) c0167eq4).bottomMargin;
            }
            if (!zB || zB2) {
                if (zB) {
                    c0606t3 = this.b;
                } else {
                    c0606t3 = this.c;
                }
                if (zB2) {
                    c0606t4 = this.c;
                } else {
                    c0606t4 = this.b;
                }
                c0167eq = (C0167eq) c0606t3.getLayoutParams();
                c0167eq2 = (C0167eq) c0606t4.getLayoutParams();
                z3 = (!zB && this.b.getMeasuredWidth() > 0) || (zB2 && this.c.getMeasuredWidth() > 0);
                i6 = iMin;
                i7 = this.w & 112;
                i8 = this.r;
                if (i7 != 48) {
                    i15 = this.s;
                    if (i7 != 80) {
                        iMax5 = (((height - paddingTop2) - paddingBottom) - measuredHeight) / 2;
                        i16 = ((ViewGroup.MarginLayoutParams) c0167eq).topMargin + i8;
                        if (iMax5 < i16) {
                            iMax5 = i16;
                        } else {
                            i17 = (((height - paddingBottom) - measuredHeight) - iMax5) - paddingTop2;
                            if (i17 < ((ViewGroup.MarginLayoutParams) c0167eq).bottomMargin + i15) {
                                iMax5 = Math.max(0, iMax5 - ((((ViewGroup.MarginLayoutParams) c0167eq2).bottomMargin + i15) - i17));
                            }
                        }
                        paddingTop = paddingTop2 + iMax5;
                    } else {
                        paddingTop = (((height - paddingBottom) - ((ViewGroup.MarginLayoutParams) c0167eq2).bottomMargin) - i15) - measuredHeight;
                    }
                } else {
                    paddingTop = getPaddingTop() + ((ViewGroup.MarginLayoutParams) c0167eq).topMargin + i8;
                }
                i9 = this.p;
                i10 = this.q;
                if (z2) {
                    if (!z3) {
                        i9 = 0;
                    }
                    int i27 = i9 - iArr[1];
                    iMax4 = i6 - Math.max(0, i27);
                    iArr[1] = Math.max(0, -i27);
                    if (zB) {
                        C0167eq c0167eq5 = (C0167eq) this.b.getLayoutParams();
                        int measuredWidth2 = iMax4 - this.b.getMeasuredWidth();
                        int measuredHeight2 = this.b.getMeasuredHeight() + paddingTop;
                        this.b.layout(measuredWidth2, paddingTop, iMax4, measuredHeight2);
                        i13 = measuredWidth2 - i10;
                        paddingTop = measuredHeight2 + ((ViewGroup.MarginLayoutParams) c0167eq5).bottomMargin;
                    } else {
                        i13 = iMax4;
                    }
                    if (zB2) {
                        int i28 = paddingTop + ((ViewGroup.MarginLayoutParams) ((C0167eq) this.c.getLayoutParams())).topMargin;
                        this.c.layout(iMax4 - this.c.getMeasuredWidth(), i28, iMax4, this.c.getMeasuredHeight() + i28);
                        i14 = iMax4 - i10;
                    } else {
                        i14 = iMax4;
                    }
                    if (z3) {
                        iMin = Math.min(i13, i14);
                    } else {
                        iMin = iMax4;
                    }
                    iMax3 = iMax;
                } else {
                    if (!z3) {
                        i9 = 0;
                    }
                    int i29 = i9 - iArr[0];
                    iMax2 = Math.max(0, i29) + iMax;
                    iArr[0] = Math.max(0, -i29);
                    if (zB) {
                        C0167eq c0167eq6 = (C0167eq) this.b.getLayoutParams();
                        int measuredWidth3 = this.b.getMeasuredWidth() + iMax2;
                        int measuredHeight3 = this.b.getMeasuredHeight() + paddingTop;
                        this.b.layout(iMax2, paddingTop, measuredWidth3, measuredHeight3);
                        i11 = measuredWidth3 + i10;
                        paddingTop = measuredHeight3 + ((ViewGroup.MarginLayoutParams) c0167eq6).bottomMargin;
                    } else {
                        i11 = iMax2;
                    }
                    if (zB2) {
                        int i30 = paddingTop + ((ViewGroup.MarginLayoutParams) ((C0167eq) this.c.getLayoutParams())).topMargin;
                        int measuredWidth4 = this.c.getMeasuredWidth() + iMax2;
                        this.c.layout(iMax2, i30, measuredWidth4, this.c.getMeasuredHeight() + i30);
                        i12 = measuredWidth4 + i10;
                    } else {
                        i12 = iMax2;
                    }
                    if (z3) {
                        iMax3 = Math.max(i11, i12);
                        iMin = i6;
                    } else {
                        iMin = i6;
                        iMax3 = iMax2;
                    }
                }
            } else {
                iMax3 = iMax;
            }
            arrayList = this.D;
            a(arrayList, 3);
            size = arrayList.size();
            iS2 = iMax3;
            for (i18 = 0; i18 < size; i18++) {
                iS2 = s((View) arrayList.get(i18), iS2, iMin2, iArr);
            }
            a(arrayList, 5);
            size2 = arrayList.size();
            iT2 = iMin;
            for (i19 = 0; i19 < size2; i19++) {
                iT2 = t((View) arrayList.get(i19), iT2, iMin2, iArr);
            }
            a(arrayList, 1);
            int i31 = iArr[0];
            i20 = iArr[1];
            size3 = arrayList.size();
            i21 = i31;
            i22 = 0;
            measuredWidth = 0;
            while (i22 < size3) {
                View view = (View) arrayList.get(i22);
                C0167eq c0167eq7 = (C0167eq) view.getLayoutParams();
                int i32 = i20;
                int i33 = ((ViewGroup.MarginLayoutParams) c0167eq7).leftMargin - i21;
                int i34 = ((ViewGroup.MarginLayoutParams) c0167eq7).rightMargin - i32;
                int iMax6 = Math.max(0, i33);
                int iMax7 = Math.max(0, i34);
                int iMax8 = Math.max(0, -i33);
                int iMax9 = Math.max(0, -i34);
                measuredWidth += view.getMeasuredWidth() + iMax6 + iMax7;
                i22++;
                i21 = iMax8;
                i20 = iMax9;
            }
            i24 = ((((width - paddingLeft) - paddingRight) / 2) + paddingLeft) - (measuredWidth / 2);
            i25 = measuredWidth + i24;
            if (i24 >= iS2) {
                if (i25 > iT2) {
                    iS2 = i24 - (i25 - iT2);
                } else {
                    iS2 = i24;
                }
            }
            size4 = arrayList.size();
            for (i23 = 0; i23 < size4; i23++) {
                iS2 = s((View) arrayList.get(i23), iS2, iMin2, iArr);
            }
            arrayList.clear();
        }
        iS = paddingLeft;
        iT = i26;
        if (B(this.h)) {
            if (z4) {
                iT = t(this.h, iT, iMin2, iArr);
            } else {
                iS = s(this.h, iS, iMin2, iArr);
            }
        }
        if (B(this.a)) {
            if (z4) {
                iS = s(this.a, iS, iMin2, iArr);
            } else {
                iT = t(this.a, iT, iMin2, iArr);
            }
        }
        if (getLayoutDirection() == 1) {
            iJ = i();
        } else {
            iJ = j();
        }
        if (getLayoutDirection() == 1) {
            i5 = j();
        } else {
            i5 = i();
        }
        z2 = z4;
        iArr[0] = Math.max(0, iJ - iS);
        iArr[1] = Math.max(0, i5 - (i26 - iT));
        iMax = Math.max(iS, iJ);
        iMin = Math.min(iT, i26 - i5);
        if (B(this.i)) {
            if (z2) {
                iMin = t(this.i, iMin, iMin2, iArr);
            } else {
                iMax = s(this.i, iMax, iMin2, iArr);
            }
        }
        if (B(this.e)) {
            if (z2) {
                iMin = t(this.e, iMin, iMin2, iArr);
            } else {
                iMax = s(this.e, iMax, iMin2, iArr);
            }
        }
        zB = B(this.b);
        zB2 = B(this.c);
        if (zB) {
            C0167eq c0167eq8 = (C0167eq) this.b.getLayoutParams();
            measuredHeight = ((ViewGroup.MarginLayoutParams) c0167eq8).bottomMargin + this.b.getMeasuredHeight() + ((ViewGroup.MarginLayoutParams) c0167eq8).topMargin;
        } else {
            measuredHeight = 0;
        }
        if (zB2) {
            C0167eq c0167eq9 = (C0167eq) this.c.getLayoutParams();
            measuredHeight += this.c.getMeasuredHeight() + ((ViewGroup.MarginLayoutParams) c0167eq9).topMargin + ((ViewGroup.MarginLayoutParams) c0167eq9).bottomMargin;
        }
        if (zB) {
            if (zB) {
                c0606t3 = this.b;
            } else {
                c0606t3 = this.c;
            }
            if (zB2) {
                c0606t4 = this.c;
            } else {
                c0606t4 = this.b;
            }
            c0167eq = (C0167eq) c0606t3.getLayoutParams();
            c0167eq2 = (C0167eq) c0606t4.getLayoutParams();
            if (zB) {
            }
            i6 = iMin;
            i7 = this.w & 112;
            i8 = this.r;
            if (i7 != 48) {
                i15 = this.s;
                if (i7 != 80) {
                    iMax5 = (((height - paddingTop2) - paddingBottom) - measuredHeight) / 2;
                    i16 = ((ViewGroup.MarginLayoutParams) c0167eq).topMargin + i8;
                    if (iMax5 < i16) {
                        iMax5 = i16;
                    } else {
                        i17 = (((height - paddingBottom) - measuredHeight) - iMax5) - paddingTop2;
                        if (i17 < ((ViewGroup.MarginLayoutParams) c0167eq).bottomMargin + i15) {
                            iMax5 = Math.max(0, iMax5 - ((((ViewGroup.MarginLayoutParams) c0167eq2).bottomMargin + i15) - i17));
                        }
                    }
                    paddingTop = paddingTop2 + iMax5;
                } else {
                    paddingTop = (((height - paddingBottom) - ((ViewGroup.MarginLayoutParams) c0167eq2).bottomMargin) - i15) - measuredHeight;
                }
            } else {
                paddingTop = getPaddingTop() + ((ViewGroup.MarginLayoutParams) c0167eq).topMargin + i8;
            }
            i9 = this.p;
            i10 = this.q;
            if (z2) {
                if (!z3) {
                    i9 = 0;
                }
                int i210 = i9 - iArr[1];
                iMax4 = i6 - Math.max(0, i210);
                iArr[1] = Math.max(0, -i210);
                if (zB) {
                    C0167eq c0167eq10 = (C0167eq) this.b.getLayoutParams();
                    int measuredWidth5 = iMax4 - this.b.getMeasuredWidth();
                    int measuredHeight4 = this.b.getMeasuredHeight() + paddingTop;
                    this.b.layout(measuredWidth5, paddingTop, iMax4, measuredHeight4);
                    i13 = measuredWidth5 - i10;
                    paddingTop = measuredHeight4 + ((ViewGroup.MarginLayoutParams) c0167eq10).bottomMargin;
                } else {
                    i13 = iMax4;
                }
                if (zB2) {
                    int i211 = paddingTop + ((ViewGroup.MarginLayoutParams) ((C0167eq) this.c.getLayoutParams())).topMargin;
                    this.c.layout(iMax4 - this.c.getMeasuredWidth(), i211, iMax4, this.c.getMeasuredHeight() + i211);
                    i14 = iMax4 - i10;
                } else {
                    i14 = iMax4;
                }
                if (z3) {
                    iMin = Math.min(i13, i14);
                } else {
                    iMin = iMax4;
                }
                iMax3 = iMax;
            } else {
                if (!z3) {
                    i9 = 0;
                }
                int i212 = i9 - iArr[0];
                iMax2 = Math.max(0, i212) + iMax;
                iArr[0] = Math.max(0, -i212);
                if (zB) {
                    C0167eq c0167eq11 = (C0167eq) this.b.getLayoutParams();
                    int measuredWidth6 = this.b.getMeasuredWidth() + iMax2;
                    int measuredHeight5 = this.b.getMeasuredHeight() + paddingTop;
                    this.b.layout(iMax2, paddingTop, measuredWidth6, measuredHeight5);
                    i11 = measuredWidth6 + i10;
                    paddingTop = measuredHeight5 + ((ViewGroup.MarginLayoutParams) c0167eq11).bottomMargin;
                } else {
                    i11 = iMax2;
                }
                if (zB2) {
                    int i35 = paddingTop + ((ViewGroup.MarginLayoutParams) ((C0167eq) this.c.getLayoutParams())).topMargin;
                    int measuredWidth7 = this.c.getMeasuredWidth() + iMax2;
                    this.c.layout(iMax2, i35, measuredWidth7, this.c.getMeasuredHeight() + i35);
                    i12 = measuredWidth7 + i10;
                } else {
                    i12 = iMax2;
                }
                if (z3) {
                    iMax3 = Math.max(i11, i12);
                    iMin = i6;
                } else {
                    iMin = i6;
                    iMax3 = iMax2;
                }
            }
        } else {
            if (zB) {
                c0606t3 = this.b;
            } else {
                c0606t3 = this.c;
            }
            if (zB2) {
                c0606t4 = this.c;
            } else {
                c0606t4 = this.b;
            }
            c0167eq = (C0167eq) c0606t3.getLayoutParams();
            c0167eq2 = (C0167eq) c0606t4.getLayoutParams();
            if (zB) {
            }
            i6 = iMin;
            i7 = this.w & 112;
            i8 = this.r;
            if (i7 != 48) {
                i15 = this.s;
                if (i7 != 80) {
                    iMax5 = (((height - paddingTop2) - paddingBottom) - measuredHeight) / 2;
                    i16 = ((ViewGroup.MarginLayoutParams) c0167eq).topMargin + i8;
                    if (iMax5 < i16) {
                        iMax5 = i16;
                    } else {
                        i17 = (((height - paddingBottom) - measuredHeight) - iMax5) - paddingTop2;
                        if (i17 < ((ViewGroup.MarginLayoutParams) c0167eq).bottomMargin + i15) {
                            iMax5 = Math.max(0, iMax5 - ((((ViewGroup.MarginLayoutParams) c0167eq2).bottomMargin + i15) - i17));
                        }
                    }
                    paddingTop = paddingTop2 + iMax5;
                } else {
                    paddingTop = (((height - paddingBottom) - ((ViewGroup.MarginLayoutParams) c0167eq2).bottomMargin) - i15) - measuredHeight;
                }
            } else {
                paddingTop = getPaddingTop() + ((ViewGroup.MarginLayoutParams) c0167eq).topMargin + i8;
            }
            i9 = this.p;
            i10 = this.q;
            if (z2) {
                if (!z3) {
                    i9 = 0;
                }
                int i213 = i9 - iArr[1];
                iMax4 = i6 - Math.max(0, i213);
                iArr[1] = Math.max(0, -i213);
                if (zB) {
                    C0167eq c0167eq12 = (C0167eq) this.b.getLayoutParams();
                    int measuredWidth8 = iMax4 - this.b.getMeasuredWidth();
                    int measuredHeight6 = this.b.getMeasuredHeight() + paddingTop;
                    this.b.layout(measuredWidth8, paddingTop, iMax4, measuredHeight6);
                    i13 = measuredWidth8 - i10;
                    paddingTop = measuredHeight6 + ((ViewGroup.MarginLayoutParams) c0167eq12).bottomMargin;
                } else {
                    i13 = iMax4;
                }
                if (zB2) {
                    int i214 = paddingTop + ((ViewGroup.MarginLayoutParams) ((C0167eq) this.c.getLayoutParams())).topMargin;
                    this.c.layout(iMax4 - this.c.getMeasuredWidth(), i214, iMax4, this.c.getMeasuredHeight() + i214);
                    i14 = iMax4 - i10;
                } else {
                    i14 = iMax4;
                }
                if (z3) {
                    iMin = Math.min(i13, i14);
                } else {
                    iMin = iMax4;
                }
                iMax3 = iMax;
            } else {
                if (!z3) {
                    i9 = 0;
                }
                int i215 = i9 - iArr[0];
                iMax2 = Math.max(0, i215) + iMax;
                iArr[0] = Math.max(0, -i215);
                if (zB) {
                    C0167eq c0167eq13 = (C0167eq) this.b.getLayoutParams();
                    int measuredWidth9 = this.b.getMeasuredWidth() + iMax2;
                    int measuredHeight7 = this.b.getMeasuredHeight() + paddingTop;
                    this.b.layout(iMax2, paddingTop, measuredWidth9, measuredHeight7);
                    i11 = measuredWidth9 + i10;
                    paddingTop = measuredHeight7 + ((ViewGroup.MarginLayoutParams) c0167eq13).bottomMargin;
                } else {
                    i11 = iMax2;
                }
                if (zB2) {
                    int i36 = paddingTop + ((ViewGroup.MarginLayoutParams) ((C0167eq) this.c.getLayoutParams())).topMargin;
                    int measuredWidth10 = this.c.getMeasuredWidth() + iMax2;
                    this.c.layout(iMax2, i36, measuredWidth10, this.c.getMeasuredHeight() + i36);
                    i12 = measuredWidth10 + i10;
                } else {
                    i12 = iMax2;
                }
                if (z3) {
                    iMax3 = Math.max(i11, i12);
                    iMin = i6;
                } else {
                    iMin = i6;
                    iMax3 = iMax2;
                }
            }
        }
        arrayList = this.D;
        a(arrayList, 3);
        size = arrayList.size();
        iS2 = iMax3;
        while (i18 < size) {
            iS2 = s((View) arrayList.get(i18), iS2, iMin2, iArr);
        }
        a(arrayList, 5);
        size2 = arrayList.size();
        iT2 = iMin;
        while (i19 < size2) {
            iT2 = t((View) arrayList.get(i19), iT2, iMin2, iArr);
        }
        a(arrayList, 1);
        int i37 = iArr[0];
        i20 = iArr[1];
        size3 = arrayList.size();
        i21 = i37;
        i22 = 0;
        measuredWidth = 0;
        while (i22 < size3) {
            View view2 = (View) arrayList.get(i22);
            C0167eq c0167eq14 = (C0167eq) view2.getLayoutParams();
            int i38 = i20;
            int i39 = ((ViewGroup.MarginLayoutParams) c0167eq14).leftMargin - i21;
            int i310 = ((ViewGroup.MarginLayoutParams) c0167eq14).rightMargin - i38;
            int iMax10 = Math.max(0, i39);
            int iMax11 = Math.max(0, i310);
            int iMax12 = Math.max(0, -i39);
            int iMax13 = Math.max(0, -i310);
            measuredWidth += view2.getMeasuredWidth() + iMax10 + iMax11;
            i22++;
            i21 = iMax12;
            i20 = iMax13;
        }
        i24 = ((((width - paddingLeft) - paddingRight) / 2) + paddingLeft) - (measuredWidth / 2);
        i25 = measuredWidth + i24;
        if (i24 >= iS2) {
            if (i25 > iT2) {
                iS2 = i24 - (i25 - iT2);
            } else {
                iS2 = i24;
            }
        }
        size4 = arrayList.size();
        while (i23 < size4) {
            iS2 = s((View) arrayList.get(i23), iS2, iMin2, iArr);
        }
        arrayList.clear();
    }

    @Override // android.view.View
    public final void onMeasure(int i, int i2) {
        int iL;
        int iMax;
        int iCombineMeasuredStates;
        int iL2;
        int iCombineMeasuredStates2;
        int iO;
        boolean zA = AbstractC0323jt.a(this);
        int i3 = !zA ? 1 : 0;
        int iMax2 = 0;
        if (B(this.d)) {
            v(this.d, i, 0, i2, this.o);
            iL = l(this.d) + this.d.getMeasuredWidth();
            iMax = Math.max(0, o(this.d) + this.d.getMeasuredHeight());
            iCombineMeasuredStates = View.combineMeasuredStates(0, this.d.getMeasuredState());
        } else {
            iL = 0;
            iMax = 0;
            iCombineMeasuredStates = 0;
        }
        if (B(this.h)) {
            v(this.h, i, 0, i2, this.o);
            iL = l(this.h) + this.h.getMeasuredWidth();
            iMax = Math.max(iMax, o(this.h) + this.h.getMeasuredHeight());
            iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, this.h.getMeasuredState());
        }
        int iJ = j();
        int iMax3 = Math.max(iJ, iL);
        int iMax4 = Math.max(0, iJ - iL);
        int[] iArr = this.F;
        iArr[zA ? 1 : 0] = iMax4;
        if (B(this.a)) {
            v(this.a, i, iMax3, i2, this.o);
            iL2 = l(this.a) + this.a.getMeasuredWidth();
            iMax = Math.max(iMax, o(this.a) + this.a.getMeasuredHeight());
            iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, this.a.getMeasuredState());
        } else {
            iL2 = 0;
        }
        int i4 = i();
        int iMax5 = iMax3 + Math.max(i4, iL2);
        iArr[i3] = Math.max(0, i4 - iL2);
        if (B(this.i)) {
            iMax5 += u(this.i, i, iMax5, i2, 0, iArr);
            iMax = Math.max(iMax, o(this.i) + this.i.getMeasuredHeight());
            iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, this.i.getMeasuredState());
        }
        if (B(this.e)) {
            iMax5 += u(this.e, i, iMax5, i2, 0, iArr);
            iMax = Math.max(iMax, o(this.e) + this.e.getMeasuredHeight());
            iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, this.e.getMeasuredState());
        }
        int childCount = getChildCount();
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            if (((C0167eq) childAt.getLayoutParams()).b == 0 && B(childAt)) {
                iMax5 += u(childAt, i, iMax5, i2, 0, iArr);
                int iMax6 = Math.max(iMax, o(childAt) + childAt.getMeasuredHeight());
                iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, childAt.getMeasuredState());
                iMax = iMax6;
            } else {
                iMax5 = iMax5;
            }
        }
        int i6 = iMax5;
        int i7 = this.r + this.s;
        int i8 = this.p + this.q;
        if (B(this.b)) {
            u(this.b, i, i6 + i8, i2, i7, iArr);
            iMax2 = l(this.b) + this.b.getMeasuredWidth();
            int measuredHeight = this.b.getMeasuredHeight() + o(this.b);
            iCombineMeasuredStates2 = View.combineMeasuredStates(iCombineMeasuredStates, this.b.getMeasuredState());
            iO = measuredHeight;
        } else {
            iCombineMeasuredStates2 = iCombineMeasuredStates;
            iO = 0;
        }
        if (B(this.c)) {
            iMax2 = Math.max(iMax2, u(this.c, i, i6 + i8, i2, i7 + iO, iArr));
            iO += o(this.c) + this.c.getMeasuredHeight();
            iCombineMeasuredStates2 = View.combineMeasuredStates(iCombineMeasuredStates2, this.c.getMeasuredState());
        }
        setMeasuredDimension(View.resolveSizeAndState(Math.max(getPaddingRight() + getPaddingLeft() + i6 + iMax2, getSuggestedMinimumWidth()), i, (-16777216) & iCombineMeasuredStates2), View.resolveSizeAndState(Math.max(getPaddingBottom() + getPaddingTop() + Math.max(iMax, iO), getSuggestedMinimumHeight()), i2, iCombineMeasuredStates2 << 16));
    }

    @Override // android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        MenuItem menuItemFindItem;
        if (!(parcelable instanceof C0198fq)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        C0198fq c0198fq = (C0198fq) parcelable;
        super.onRestoreInstanceState(c0198fq.a);
        ActionMenuView actionMenuView = this.a;
        MenuC0129di menuC0129di = actionMenuView != null ? actionMenuView.p : null;
        int i = c0198fq.c;
        if (i != 0 && this.M != null && menuC0129di != null && (menuItemFindItem = menuC0129di.findItem(i)) != null) {
            menuItemFindItem.expandActionView();
        }
        if (c0198fq.d) {
            RunnableC0607t4 runnableC0607t4 = this.S;
            removeCallbacks(runnableC0607t4);
            post(runnableC0607t4);
        }
    }

    @Override // android.view.View
    public final void onRtlPropertiesChanged(int i) {
        super.onRtlPropertiesChanged(i);
        c();
        C0286im c0286im = this.t;
        boolean z = i == 1;
        if (z == c0286im.g) {
            return;
        }
        c0286im.g = z;
        if (!c0286im.h) {
            c0286im.a = c0286im.e;
            c0286im.b = c0286im.f;
            return;
        }
        if (z) {
            int i2 = c0286im.d;
            if (i2 == Integer.MIN_VALUE) {
                i2 = c0286im.e;
            }
            c0286im.a = i2;
            int i3 = c0286im.c;
            if (i3 == Integer.MIN_VALUE) {
                i3 = c0286im.f;
            }
            c0286im.b = i3;
            return;
        }
        int i4 = c0286im.c;
        if (i4 == Integer.MIN_VALUE) {
            i4 = c0286im.e;
        }
        c0286im.a = i4;
        int i5 = c0286im.d;
        if (i5 == Integer.MIN_VALUE) {
            i5 = c0286im.f;
        }
        c0286im.b = i5;
    }

    @Override // android.view.View
    public final Parcelable onSaveInstanceState() {
        C0221gi c0221gi;
        C0198fq c0198fq = new C0198fq(super.onSaveInstanceState());
        C0136dq c0136dq = this.M;
        if (c0136dq != null && (c0221gi = c0136dq.b) != null) {
            c0198fq.c = c0221gi.a;
        }
        c0198fq.d = r();
        return c0198fq;
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.B = false;
        }
        if (!this.B) {
            boolean zOnTouchEvent = super.onTouchEvent(motionEvent);
            if (actionMasked == 0 && !zOnTouchEvent) {
                this.B = true;
            }
        }
        if (actionMasked != 1 && actionMasked != 3) {
            return true;
        }
        this.B = false;
        return true;
    }

    public void p(int i) {
        new C0381lp(getContext()).inflate(i, m());
    }

    public final boolean q(View view) {
        return view.getParent() == this || this.E.contains(view);
    }

    public final boolean r() {
        C0 c0;
        ActionMenuView actionMenuView = this.a;
        return (actionMenuView == null || (c0 = actionMenuView.t) == null || !c0.j()) ? false : true;
    }

    public final int s(View view, int i, int i2, int[] iArr) {
        C0167eq c0167eq = (C0167eq) view.getLayoutParams();
        int i3 = ((ViewGroup.MarginLayoutParams) c0167eq).leftMargin - iArr[0];
        int iMax = Math.max(0, i3) + i;
        iArr[0] = Math.max(0, -i3);
        int iH = h(view, i2);
        int measuredWidth = view.getMeasuredWidth();
        view.layout(iMax, iH, iMax + measuredWidth, view.getMeasuredHeight() + iH);
        return measuredWidth + ((ViewGroup.MarginLayoutParams) c0167eq).rightMargin + iMax;
    }

    public final int t(View view, int i, int i2, int[] iArr) {
        C0167eq c0167eq = (C0167eq) view.getLayoutParams();
        int i3 = ((ViewGroup.MarginLayoutParams) c0167eq).rightMargin - iArr[1];
        int iMax = i - Math.max(0, i3);
        iArr[1] = Math.max(0, -i3);
        int iH = h(view, i2);
        int measuredWidth = view.getMeasuredWidth();
        view.layout(iMax - measuredWidth, iH, iMax, view.getMeasuredHeight() + iH);
        return iMax - (measuredWidth + ((ViewGroup.MarginLayoutParams) c0167eq).leftMargin);
    }

    public final int u(View view, int i, int i2, int i3, int i4, int[] iArr) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        int i5 = marginLayoutParams.leftMargin - iArr[0];
        int i6 = marginLayoutParams.rightMargin - iArr[1];
        int iMax = Math.max(0, i6) + Math.max(0, i5);
        iArr[0] = Math.max(0, -i5);
        iArr[1] = Math.max(0, -i6);
        view.measure(ViewGroup.getChildMeasureSpec(i, getPaddingRight() + getPaddingLeft() + iMax + i2, marginLayoutParams.width), ViewGroup.getChildMeasureSpec(i3, getPaddingBottom() + getPaddingTop() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + i4, marginLayoutParams.height));
        return view.getMeasuredWidth() + iMax;
    }

    public final void v(View view, int i, int i2, int i3, int i4) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        int childMeasureSpec = ViewGroup.getChildMeasureSpec(i, getPaddingRight() + getPaddingLeft() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + i2, marginLayoutParams.width);
        int childMeasureSpec2 = ViewGroup.getChildMeasureSpec(i3, getPaddingBottom() + getPaddingTop() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin, marginLayoutParams.height);
        int mode = View.MeasureSpec.getMode(childMeasureSpec2);
        if (mode != 1073741824 && i4 >= 0) {
            if (mode != 0) {
                i4 = Math.min(View.MeasureSpec.getSize(childMeasureSpec2), i4);
            }
            childMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(i4, 1073741824);
        }
        view.measure(childMeasureSpec, childMeasureSpec2);
    }

    public final void w(Drawable drawable) {
        if (drawable != null) {
            if (this.e == null) {
                this.e = new O2(getContext(), null, 0);
            }
            if (!q(this.e)) {
                b(this.e, true);
            }
        } else {
            O2 o2 = this.e;
            if (o2 != null && q(o2)) {
                removeView(this.e);
                this.E.remove(this.e);
            }
        }
        O2 o3 = this.e;
        if (o3 != null) {
            o3.setImageDrawable(drawable);
        }
    }

    public final void x(CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            e();
        }
        N2 n2 = this.d;
        if (n2 != null) {
            n2.setContentDescription(charSequence);
            AbstractC0688vn.y(this.d, charSequence);
        }
    }

    public void y(Drawable drawable) {
        if (drawable != null) {
            e();
            if (!q(this.d)) {
                b(this.d, true);
            }
        } else {
            N2 n2 = this.d;
            if (n2 != null && q(n2)) {
                removeView(this.d);
                this.E.remove(this.d);
            }
        }
        N2 n3 = this.d;
        if (n3 != null) {
            n3.setImageDrawable(drawable);
        }
    }

    public final void z(CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            C0606t3 c0606t3 = this.c;
            if (c0606t3 != null && q(c0606t3)) {
                removeView(this.c);
                this.E.remove(this.c);
            }
        } else {
            if (this.c == null) {
                Context context = getContext();
                C0606t3 c0606t4 = new C0606t3(context, null);
                this.c = c0606t4;
                c0606t4.setSingleLine();
                this.c.setEllipsize(TextUtils.TruncateAt.END);
                int i = this.m;
                if (i != 0) {
                    this.c.setTextAppearance(context, i);
                }
                ColorStateList colorStateList = this.A;
                if (colorStateList != null) {
                    this.c.setTextColor(colorStateList);
                }
            }
            if (!q(this.c)) {
                b(this.c, true);
            }
        }
        C0606t3 c0606t5 = this.c;
        if (c0606t5 != null) {
            c0606t5.setText(charSequence);
        }
        this.y = charSequence;
    }

    public Toolbar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, R.attr._0_resource_name_obfuscated_res_0x7f040532);
        this.w = 8388627;
        this.D = new ArrayList();
        this.E = new ArrayList();
        this.F = new int[2];
        this.G = new K3(new RunnableC0044aq(this, 1));
        this.H = new ArrayList();
        this.J = new C0075bq(this);
        this.S = new RunnableC0607t4(16, this);
        Context context2 = getContext();
        int[] iArr = Nk.y;
        K3 k3N = K3.n(context2, attributeSet, iArr, R.attr._0_resource_name_obfuscated_res_0x7f040532);
        Hs.l(this, context, iArr, attributeSet, (TypedArray) k3N.a, R.attr._0_resource_name_obfuscated_res_0x7f040532);
        TypedArray typedArray = (TypedArray) k3N.a;
        this.l = typedArray.getResourceId(28, 0);
        this.m = typedArray.getResourceId(19, 0);
        this.w = typedArray.getInteger(0, 8388627);
        this.n = typedArray.getInteger(2, 48);
        int dimensionPixelOffset = typedArray.getDimensionPixelOffset(22, 0);
        dimensionPixelOffset = typedArray.hasValue(27) ? typedArray.getDimensionPixelOffset(27, dimensionPixelOffset) : dimensionPixelOffset;
        this.s = dimensionPixelOffset;
        this.r = dimensionPixelOffset;
        this.q = dimensionPixelOffset;
        this.p = dimensionPixelOffset;
        int dimensionPixelOffset2 = typedArray.getDimensionPixelOffset(25, -1);
        if (dimensionPixelOffset2 >= 0) {
            this.p = dimensionPixelOffset2;
        }
        int dimensionPixelOffset3 = typedArray.getDimensionPixelOffset(24, -1);
        if (dimensionPixelOffset3 >= 0) {
            this.q = dimensionPixelOffset3;
        }
        int dimensionPixelOffset4 = typedArray.getDimensionPixelOffset(26, -1);
        if (dimensionPixelOffset4 >= 0) {
            this.r = dimensionPixelOffset4;
        }
        int dimensionPixelOffset5 = typedArray.getDimensionPixelOffset(23, -1);
        if (dimensionPixelOffset5 >= 0) {
            this.s = dimensionPixelOffset5;
        }
        this.o = typedArray.getDimensionPixelSize(13, -1);
        int dimensionPixelOffset6 = typedArray.getDimensionPixelOffset(9, Integer.MIN_VALUE);
        int dimensionPixelOffset7 = typedArray.getDimensionPixelOffset(5, Integer.MIN_VALUE);
        int dimensionPixelSize = typedArray.getDimensionPixelSize(7, 0);
        int dimensionPixelSize2 = typedArray.getDimensionPixelSize(8, 0);
        c();
        C0286im c0286im = this.t;
        c0286im.h = false;
        if (dimensionPixelSize != Integer.MIN_VALUE) {
            c0286im.e = dimensionPixelSize;
            c0286im.a = dimensionPixelSize;
        }
        if (dimensionPixelSize2 != Integer.MIN_VALUE) {
            c0286im.f = dimensionPixelSize2;
            c0286im.b = dimensionPixelSize2;
        }
        if (dimensionPixelOffset6 != Integer.MIN_VALUE || dimensionPixelOffset7 != Integer.MIN_VALUE) {
            c0286im.a(dimensionPixelOffset6, dimensionPixelOffset7);
        }
        this.u = typedArray.getDimensionPixelOffset(10, Integer.MIN_VALUE);
        this.v = typedArray.getDimensionPixelOffset(6, Integer.MIN_VALUE);
        this.f = k3N.i(4);
        this.g = typedArray.getText(3);
        CharSequence text = typedArray.getText(21);
        if (!TextUtils.isEmpty(text)) {
            A(text);
        }
        CharSequence text2 = typedArray.getText(18);
        if (!TextUtils.isEmpty(text2)) {
            z(text2);
        }
        this.j = getContext();
        int resourceId = typedArray.getResourceId(17, 0);
        if (this.k != resourceId) {
            this.k = resourceId;
            if (resourceId == 0) {
                this.j = getContext();
            } else {
                this.j = new ContextThemeWrapper(getContext(), resourceId);
            }
        }
        Drawable drawableI = k3N.i(16);
        if (drawableI != null) {
            y(drawableI);
        }
        CharSequence text3 = typedArray.getText(15);
        if (!TextUtils.isEmpty(text3)) {
            x(text3);
        }
        Drawable drawableI2 = k3N.i(11);
        if (drawableI2 != null) {
            w(drawableI2);
        }
        CharSequence text4 = typedArray.getText(12);
        if (!TextUtils.isEmpty(text4)) {
            if (!TextUtils.isEmpty(text4) && this.e == null) {
                this.e = new O2(getContext(), null, 0);
            }
            O2 o2 = this.e;
            if (o2 != null) {
                o2.setContentDescription(text4);
            }
        }
        if (typedArray.hasValue(29)) {
            ColorStateList colorStateListH = k3N.h(29);
            this.z = colorStateListH;
            C0606t3 c0606t3 = this.b;
            if (c0606t3 != null) {
                c0606t3.setTextColor(colorStateListH);
            }
        }
        if (typedArray.hasValue(20)) {
            ColorStateList colorStateListH2 = k3N.h(20);
            this.A = colorStateListH2;
            C0606t3 c0606t4 = this.c;
            if (c0606t4 != null) {
                c0606t4.setTextColor(colorStateListH2);
            }
        }
        if (typedArray.hasValue(14)) {
            p(typedArray.getResourceId(14, 0));
        }
        k3N.o();
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        Context context = getContext();
        C0167eq c0167eq = new C0167eq(context, attributeSet);
        c0167eq.a = 0;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Nk.b);
        c0167eq.a = typedArrayObtainStyledAttributes.getInt(0, 0);
        typedArrayObtainStyledAttributes.recycle();
        c0167eq.b = 0;
        return c0167eq;
    }
}
