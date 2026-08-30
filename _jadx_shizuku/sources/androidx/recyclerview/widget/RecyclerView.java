package androidx.recyclerview.widget;

import android.R;
import android.animation.LayoutTransition;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Parcelable;
import android.os.SystemClock;
import android.os.Trace;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.widget.EdgeEffect;
import android.widget.OverScroller;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.WeakHashMap;
import rikka.shizuku.AbstractC0070bl;
import rikka.shizuku.AbstractC0162el;
import rikka.shizuku.AbstractC0190fi;
import rikka.shizuku.AbstractC0224gl;
import rikka.shizuku.AbstractC0470ol;
import rikka.shizuku.AbstractC0535qo;
import rikka.shizuku.AbstractC0599sr;
import rikka.shizuku.AbstractC0702w6;
import rikka.shizuku.AbstractC0744xh;
import rikka.shizuku.AbstractC0817zs;
import rikka.shizuku.B5;
import rikka.shizuku.Bs;
import rikka.shizuku.C0104co;
import rikka.shizuku.C0193fl;
import rikka.shizuku.C0255hl;
import rikka.shizuku.C0285il;
import rikka.shizuku.C0315jl;
import rikka.shizuku.C0408ml;
import rikka.shizuku.C0458o9;
import rikka.shizuku.C0532ql;
import rikka.shizuku.C0620th;
import rikka.shizuku.C0788z;
import rikka.shizuku.Fd;
import rikka.shizuku.Hd;
import rikka.shizuku.Hs;
import rikka.shizuku.InterfaceC0377ll;
import rikka.shizuku.Is;
import rikka.shizuku.Ja;
import rikka.shizuku.Jg;
import rikka.shizuku.Kk;
import rikka.shizuku.N1;
import rikka.shizuku.Ns;
import rikka.shizuku.Pe;
import rikka.shizuku.R2;
import rikka.shizuku.RunnableC0439nl;
import rikka.shizuku.S0;
import rikka.shizuku.T0;
import rikka.shizuku.Tb;
import rikka.shizuku.U3;
import rikka.shizuku.Ui;
import rikka.shizuku.V9;
import rikka.shizuku.Vi;
import rikka.shizuku.Vk;
import rikka.shizuku.W5;
import rikka.shizuku.Wk;
import rikka.shizuku.Xj;
import rikka.shizuku.Xk;
import rikka.shizuku.Xn;
import rikka.shizuku.Yi;
import rikka.shizuku.Yk;

/* JADX INFO: loaded from: classes.dex */
public class RecyclerView extends ViewGroup implements Ui {
    public static final Class[] D0;
    public static final Wk E0;
    public static final N1 F0;
    public boolean A;
    public final AccessibilityManager B;
    public boolean C;
    public boolean D;
    public int E;
    public int F;
    public Pe G;
    public EdgeEffect H;
    public EdgeEffect I;
    public EdgeEffect J;
    public EdgeEffect K;
    public C0458o9 L;
    public int M;
    public int N;
    public VelocityTracker O;
    public int P;
    public int Q;
    public int R;
    public int S;
    public final int T;
    public Xn U;
    public final int V;
    public final int W;
    public final float a;
    public final float a0;
    public final U3 b;
    public final float b0;
    public final C0285il c;
    public final boolean c0;
    public C0315jl d;
    public final RunnableC0439nl d0;
    public final T0 e;
    public Hd e0;
    public final W5 f;
    public final Fd f0;
    public final R2 g;
    public final C0408ml g0;
    public boolean h;
    public ArrayList h0;
    public final Vk i;
    public boolean i0;
    public final Rect j;
    public boolean j0;
    public final Rect k;
    public boolean k0;
    public final RectF l;
    public C0532ql l0;
    public Yk m;
    public final int[] m0;
    public AbstractC0162el n;
    public Vi n0;
    public final ArrayList o;
    public final int[] o0;
    public final ArrayList p;
    public final int[] p0;
    public final ArrayList q;
    public final int[] q0;
    public Tb r;
    public final ArrayList r0;
    public boolean s;
    public final Vk s0;
    public boolean t;
    public boolean t0;
    public boolean u;
    public int u0;
    public int v;
    public int v0;
    public boolean w;
    public final boolean w0;
    public boolean x;
    public final Xk x0;
    public boolean y;
    public final V9 y0;
    public int z;
    public static final int[] z0 = {R.attr.nestedScrollingEnabled};
    public static final float A0 = (float) (Math.log(0.78d) / Math.log(0.9d));
    public static final boolean B0 = true;
    public static final boolean C0 = true;

    static {
        Class cls = Integer.TYPE;
        D0 = new Class[]{Context.class, AttributeSet.class, cls, cls};
        E0 = new Wk(0);
        F0 = new N1(1);
    }

    public RecyclerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f040405);
    }

    public static RecyclerView E(View view) {
        if (!(view instanceof ViewGroup)) {
            return null;
        }
        if (view instanceof RecyclerView) {
            return (RecyclerView) view;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            RecyclerView recyclerViewE = E(viewGroup.getChildAt(i));
            if (recyclerViewE != null) {
                return recyclerViewE;
            }
        }
        return null;
    }

    public static AbstractC0470ol K(View view) {
        if (view == null) {
            return null;
        }
        return ((C0193fl) view.getLayoutParams()).a;
    }

    public static long M() {
        if (C0) {
            return System.nanoTime();
        }
        return 0L;
    }

    public static void l(AbstractC0470ol abstractC0470ol) {
        WeakReference weakReference = abstractC0470ol.b;
        if (weakReference != null) {
            View view = (View) weakReference.get();
            while (view != null) {
                if (view == abstractC0470ol.a) {
                    return;
                }
                Object parent = view.getParent();
                view = parent instanceof View ? (View) parent : null;
            }
            abstractC0470ol.b = null;
        }
    }

    public static int o(int i, EdgeEffect edgeEffect, EdgeEffect edgeEffect2, int i2) {
        if (i > 0 && edgeEffect != null && AbstractC0744xh.u(edgeEffect) != 0.0f) {
            int iRound = Math.round(AbstractC0744xh.N(edgeEffect, ((-i) * 4.0f) / i2, 0.5f) * ((-i2) / 4.0f));
            if (iRound != i) {
                edgeEffect.finish();
            }
            return i - iRound;
        }
        if (i >= 0 || edgeEffect2 == null || AbstractC0744xh.u(edgeEffect2) == 0.0f) {
            return i;
        }
        float f = i2;
        int iRound2 = Math.round(AbstractC0744xh.N(edgeEffect2, (i * 4.0f) / f, 0.5f) * (f / 4.0f));
        if (iRound2 != i) {
            edgeEffect2.finish();
        }
        return i - iRound2;
    }

    public final void A(C0408ml c0408ml) {
        if (this.M != 2) {
            c0408ml.getClass();
            return;
        }
        OverScroller overScroller = this.d0.c;
        overScroller.getFinalX();
        overScroller.getCurrX();
        c0408ml.getClass();
        overScroller.getFinalY();
        overScroller.getCurrY();
    }

    public final View B(View view) {
        ViewParent parent = view.getParent();
        while (parent != null && parent != this && (parent instanceof View)) {
            view = parent;
            parent = view.getParent();
        }
        if (parent == this) {
            return view;
        }
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:23:0x005e A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:27:0x0061 A[SYNTHETIC] */
    public final boolean C(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        ArrayList arrayList = this.q;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            Tb tb = (Tb) arrayList.get(i);
            int i2 = tb.v;
            if (i2 == 1) {
                boolean zE = tb.e(motionEvent.getX(), motionEvent.getY());
                boolean zD = tb.d(motionEvent.getX(), motionEvent.getY());
                if (motionEvent.getAction() == 0 && (zE || zD)) {
                    if (zD) {
                        tb.w = 1;
                        tb.p = (int) motionEvent.getX();
                    } else if (zE) {
                        tb.w = 2;
                        tb.m = (int) motionEvent.getY();
                    }
                    tb.g(2);
                    if (action != 3) {
                        this.r = tb;
                        return true;
                    }
                }
            } else if (i2 != 2) {
                continue;
            } else if (action != 3) {
                this.r = tb;
                return true;
            }
        }
        return false;
    }

    public final void D(int[] iArr) {
        int iE = this.f.e();
        if (iE == 0) {
            iArr[0] = -1;
            iArr[1] = -1;
            return;
        }
        int i = Integer.MAX_VALUE;
        int i2 = Integer.MIN_VALUE;
        for (int i3 = 0; i3 < iE; i3++) {
            AbstractC0470ol abstractC0470olK = K(this.f.d(i3));
            if (!abstractC0470olK.o()) {
                int iB = abstractC0470olK.b();
                if (iB < i) {
                    i = iB;
                }
                if (iB > i2) {
                    i2 = iB;
                }
            }
        }
        iArr[0] = i;
        iArr[1] = i2;
    }

    public final AbstractC0470ol F(int i) {
        AbstractC0470ol abstractC0470ol = null;
        if (this.C) {
            return null;
        }
        int iH = this.f.h();
        for (int i2 = 0; i2 < iH; i2++) {
            AbstractC0470ol abstractC0470olK = K(this.f.g(i2));
            if (abstractC0470olK != null && !abstractC0470olK.h() && H(abstractC0470olK) == i) {
                if (!this.f.c.contains(abstractC0470olK.a)) {
                    return abstractC0470olK;
                }
                abstractC0470ol = abstractC0470olK;
            }
        }
        return abstractC0470ol;
    }

    /* JADX WARN: Code duplicated, block: B:101:0x014a  */
    /* JADX WARN: Code duplicated, block: B:165:0x0216  */
    /* JADX WARN: Code duplicated, block: B:43:0x0087  */
    /* JADX WARN: Code duplicated, block: B:61:0x00c9  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public final boolean G(int i, int i2, int i3, int i4) {
        int iMax;
        int i5;
        boolean z;
        boolean z2;
        boolean z3;
        RecyclerView recyclerView;
        AbstractC0162el abstractC0162el;
        boolean z4;
        Ja jaN;
        int iG;
        PointF pointFA;
        int i6;
        AbstractC0162el abstractC0162el2 = this.n;
        if (abstractC0162el2 == null) {
            Log.e("RecyclerView", "Cannot fling without a LayoutManager set. Call setLayoutManager with a non-null argument.");
            return false;
        }
        if (!this.x) {
            boolean zD = abstractC0162el2.d();
            boolean zE = this.n.e();
            int i7 = (!zD || Math.abs(i) < i3) ? 0 : i;
            int iMax2 = (!zE || Math.abs(i2) < i3) ? 0 : i2;
            if (i7 != 0 || iMax2 != 0) {
                if (i7 == 0) {
                    iMax = 0;
                } else {
                    EdgeEffect edgeEffect = this.H;
                    if (edgeEffect == null || AbstractC0744xh.u(edgeEffect) == 0.0f) {
                        EdgeEffect edgeEffect2 = this.J;
                        if (edgeEffect2 == null || AbstractC0744xh.u(edgeEffect2) == 0.0f) {
                            iMax = 0;
                        } else if (k0(this.J, i7, getWidth())) {
                            this.J.onAbsorb(i7);
                            i7 = 0;
                        }
                    } else {
                        int i8 = -i7;
                        if (k0(this.H, i8, getWidth())) {
                            this.H.onAbsorb(i8);
                            i7 = 0;
                        }
                    }
                    iMax = i7;
                    i7 = 0;
                }
                if (iMax2 == 0) {
                    i5 = iMax2;
                    iMax2 = 0;
                } else {
                    EdgeEffect edgeEffect3 = this.I;
                    if (edgeEffect3 == null || AbstractC0744xh.u(edgeEffect3) == 0.0f) {
                        EdgeEffect edgeEffect4 = this.K;
                        if (edgeEffect4 == null || AbstractC0744xh.u(edgeEffect4) == 0.0f) {
                            i5 = iMax2;
                            iMax2 = 0;
                        } else if (k0(this.K, iMax2, getHeight())) {
                            this.K.onAbsorb(iMax2);
                            iMax2 = 0;
                        }
                    } else {
                        int i9 = -iMax2;
                        if (k0(this.I, i9, getHeight())) {
                            this.I.onAbsorb(i9);
                            iMax2 = 0;
                        }
                    }
                    i5 = 0;
                }
                RunnableC0439nl runnableC0439nl = this.d0;
                if (iMax != 0 || iMax2 != 0) {
                    int i10 = -i4;
                    iMax = Math.max(i10, Math.min(iMax, i4));
                    iMax2 = Math.max(i10, Math.min(iMax2, i4));
                    o0(1);
                    runnableC0439nl.a(iMax, iMax2);
                }
                if (i7 != 0 || i5 != 0) {
                    float f = i7;
                    float f2 = i5;
                    if (!dispatchNestedPreFling(f, f2)) {
                        boolean z5 = zD || zE;
                        dispatchNestedFling(f, f2, z5);
                        Xn xn = this.U;
                        if (xn == null || (abstractC0162el = (recyclerView = (RecyclerView) xn.a).n) == 0 || recyclerView.m == null) {
                            z = z5;
                            z2 = false;
                            z3 = true;
                        } else {
                            int iAbs = Math.abs(i5);
                            int i11 = recyclerView.V;
                            if ((iAbs > i11 || Math.abs(i7) > i11) && ((z4 = abstractC0162el instanceof InterfaceC0377ll))) {
                                View view = null;
                                B5 b5 = !z4 ? null : new B5(1, ((RecyclerView) xn.a).getContext(), xn);
                                if (b5 == null) {
                                    z = z5;
                                    z2 = false;
                                    z3 = true;
                                } else {
                                    int iB = abstractC0162el.B();
                                    if (iB != 0) {
                                        if (abstractC0162el.e()) {
                                            jaN = xn.o(abstractC0162el);
                                        } else {
                                            jaN = abstractC0162el.d() ? xn.n(abstractC0162el) : null;
                                        }
                                        if (jaN == null) {
                                            z = z5;
                                            z2 = false;
                                            z3 = true;
                                        } else {
                                            z2 = false;
                                            int iV = abstractC0162el.v();
                                            z3 = true;
                                            int i12 = 0;
                                            int i13 = Integer.MIN_VALUE;
                                            int i14 = Integer.MAX_VALUE;
                                            View view2 = null;
                                            while (i12 < iV) {
                                                boolean z6 = z5;
                                                View viewU = abstractC0162el.u(i12);
                                                if (viewU == null) {
                                                    i6 = iV;
                                                } else {
                                                    i6 = iV;
                                                    int iE = Xn.e(viewU, jaN);
                                                    if (iE <= 0 && iE > i13) {
                                                        view2 = viewU;
                                                        i13 = iE;
                                                    }
                                                    if (iE >= 0 && iE < i14) {
                                                        view = viewU;
                                                        i14 = iE;
                                                    }
                                                }
                                                i12++;
                                                z5 = z6;
                                                iV = i6;
                                            }
                                            z = z5;
                                            Object[] objArr = !abstractC0162el.d() ? i5 <= 0 : i7 <= 0;
                                            if (objArr == true && view != null) {
                                                iG = AbstractC0162el.G(view);
                                            } else if (objArr == true || view2 == null) {
                                                if (objArr != false) {
                                                    view = view2;
                                                }
                                                if (view != null) {
                                                    iG = ((z4 && (pointFA = ((InterfaceC0377ll) abstractC0162el).a(abstractC0162el.B() + (-1))) != null && ((pointFA.x > 0.0f ? 1 : (pointFA.x == 0.0f ? 0 : -1)) < 0 || (pointFA.y > 0.0f ? 1 : (pointFA.y == 0.0f ? 0 : -1)) < 0)) == objArr ? -1 : 1) + AbstractC0162el.G(view);
                                                    if (iG < 0 || iG >= iB) {
                                                    }
                                                }
                                            } else {
                                                iG = AbstractC0162el.G(view2);
                                            }
                                        }
                                        iG = -1;
                                    } else {
                                        z = z5;
                                        z2 = false;
                                        z3 = true;
                                        iG = -1;
                                    }
                                    if (iG != -1) {
                                        b5.a = iG;
                                        abstractC0162el.B0(b5);
                                        return z3;
                                    }
                                }
                            } else {
                                z = z5;
                                z2 = false;
                                z3 = true;
                            }
                        }
                        if (!z) {
                            return z2;
                        }
                        boolean z7 = z3;
                        o0(z7 ? 1 : 0);
                        int i15 = -i4;
                        runnableC0439nl.a(Math.max(i15, Math.min(i7, i4)), Math.max(i15, Math.min(i5, i4)));
                        return z7;
                    }
                } else if (iMax != 0 || iMax2 != 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public final int H(AbstractC0470ol abstractC0470ol) {
        if (((abstractC0470ol.j & 524) != 0) || !abstractC0470ol.e()) {
            return -1;
        }
        T0 t0 = this.e;
        int i = abstractC0470ol.c;
        ArrayList arrayList = (ArrayList) t0.c;
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            S0 s0 = (S0) arrayList.get(i2);
            int i3 = s0.a;
            if (i3 != 1) {
                if (i3 == 2) {
                    int i4 = s0.b;
                    if (i4 <= i) {
                        int i5 = s0.d;
                        if (i4 + i5 > i) {
                            return -1;
                        }
                        i -= i5;
                    } else {
                        continue;
                    }
                } else if (i3 == 8) {
                    int i6 = s0.b;
                    if (i6 == i) {
                        i = s0.d;
                    } else {
                        if (i6 < i) {
                            i--;
                        }
                        if (s0.d <= i) {
                            i++;
                        }
                    }
                }
            } else if (s0.b <= i) {
                i += s0.d;
            }
        }
        return i;
    }

    public final long I(AbstractC0470ol abstractC0470ol) {
        return this.m.b ? abstractC0470ol.e : abstractC0470ol.c;
    }

    public final AbstractC0470ol J(View view) {
        ViewParent parent = view.getParent();
        if (parent == null || parent == this) {
            return K(view);
        }
        throw new IllegalArgumentException("View " + view + " is not a direct child of " + this);
    }

    public final Rect L(View view) {
        C0193fl c0193fl = (C0193fl) view.getLayoutParams();
        boolean z = c0193fl.c;
        Rect rect = c0193fl.b;
        if (!z || (this.g0.g && (c0193fl.a.k() || c0193fl.a.f()))) {
            return rect;
        }
        rect.set(0, 0, 0, 0);
        ArrayList arrayList = this.p;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            Rect rect2 = this.j;
            rect2.set(0, 0, 0, 0);
            ((AbstractC0070bl) arrayList.get(i)).a(rect2, view, this);
            rect.left += rect2.left;
            rect.top += rect2.top;
            rect.right += rect2.right;
            rect.bottom += rect2.bottom;
        }
        c0193fl.c = false;
        return rect;
    }

    public final Vi N() {
        if (this.n0 == null) {
            this.n0 = new Vi(this);
        }
        return this.n0;
    }

    public final boolean O() {
        return !this.u || this.C || this.e.h();
    }

    public final boolean P() {
        return this.E > 0;
    }

    public final void Q(int i) {
        if (this.n == null) {
            return;
        }
        j0(2);
        this.n.q0(i);
        awakenScrollBars();
    }

    public final void R() {
        int iH = this.f.h();
        for (int i = 0; i < iH; i++) {
            ((C0193fl) this.f.g(i).getLayoutParams()).c = true;
        }
        ArrayList arrayList = this.c.c;
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            C0193fl c0193fl = (C0193fl) ((AbstractC0470ol) arrayList.get(i2)).a.getLayoutParams();
            if (c0193fl != null) {
                c0193fl.c = true;
            }
        }
    }

    public final void S(int i, int i2, boolean z) {
        int i3 = i + i2;
        int iH = this.f.h();
        for (int i4 = 0; i4 < iH; i4++) {
            AbstractC0470ol abstractC0470olK = K(this.f.g(i4));
            if (abstractC0470olK != null && !abstractC0470olK.o()) {
                int i5 = abstractC0470olK.c;
                C0408ml c0408ml = this.g0;
                if (i5 >= i3) {
                    abstractC0470olK.l(-i2, z);
                    c0408ml.f = true;
                } else if (i5 >= i) {
                    abstractC0470olK.a(8);
                    abstractC0470olK.l(-i2, z);
                    abstractC0470olK.c = i - 1;
                    c0408ml.f = true;
                }
            }
        }
        C0285il c0285il = this.c;
        ArrayList arrayList = c0285il.c;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            AbstractC0470ol abstractC0470ol = (AbstractC0470ol) arrayList.get(size);
            if (abstractC0470ol != null) {
                int i6 = abstractC0470ol.c;
                if (i6 >= i3) {
                    abstractC0470ol.l(-i2, z);
                } else if (i6 >= i) {
                    abstractC0470ol.a(8);
                    c0285il.g(size);
                }
            }
        }
        requestLayout();
    }

    public final void T() {
        this.E++;
    }

    public final void U(boolean z) {
        int i;
        AccessibilityManager accessibilityManager;
        int i2 = this.E - 1;
        this.E = i2;
        if (i2 < 1) {
            this.E = 0;
            if (z) {
                int i3 = this.z;
                this.z = 0;
                if (i3 != 0 && (accessibilityManager = this.B) != null && accessibilityManager.isEnabled()) {
                    AccessibilityEvent accessibilityEventObtain = AccessibilityEvent.obtain();
                    accessibilityEventObtain.setEventType(2048);
                    accessibilityEventObtain.setContentChangeTypes(i3);
                    sendAccessibilityEventUnchecked(accessibilityEventObtain);
                }
                ArrayList arrayList = this.r0;
                for (int size = arrayList.size() - 1; size >= 0; size--) {
                    AbstractC0470ol abstractC0470ol = (AbstractC0470ol) arrayList.get(size);
                    if (abstractC0470ol.a.getParent() == this && !abstractC0470ol.o() && (i = abstractC0470ol.q) != -1) {
                        abstractC0470ol.a.setImportantForAccessibility(i);
                        abstractC0470ol.q = -1;
                    }
                }
                arrayList.clear();
            }
        }
    }

    public final void V(MotionEvent motionEvent) {
        int actionIndex = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(actionIndex) == this.N) {
            int i = actionIndex == 0 ? 1 : 0;
            this.N = motionEvent.getPointerId(i);
            int x = (int) (motionEvent.getX(i) + 0.5f);
            this.R = x;
            this.P = x;
            int y = (int) (motionEvent.getY(i) + 0.5f);
            this.S = y;
            this.Q = y;
        }
    }

    public final void W() {
        if (this.k0 || !this.s) {
            return;
        }
        WeakHashMap weakHashMap = Hs.a;
        postOnAnimation(this.s0);
        this.k0 = true;
    }

    public final void X() {
        boolean z;
        boolean z2 = false;
        if (this.C) {
            T0 t0 = this.e;
            t0.o((ArrayList) t0.c);
            t0.o((ArrayList) t0.d);
            t0.a = 0;
            if (this.D) {
                this.n.Z();
            }
        }
        if (this.L == null || !this.n.C0()) {
            this.e.d();
        } else {
            this.e.n();
        }
        boolean z3 = this.i0 || this.j0;
        boolean z4 = this.u && this.L != null && ((z = this.C) || z3 || this.n.f) && (!z || this.m.b);
        C0408ml c0408ml = this.g0;
        c0408ml.j = z4;
        if (z4 && z3 && !this.C && this.L != null && this.n.C0()) {
            z2 = true;
        }
        c0408ml.k = z2;
    }

    public final void Y(boolean z) {
        this.D = z | this.D;
        this.C = true;
        int iH = this.f.h();
        for (int i = 0; i < iH; i++) {
            AbstractC0470ol abstractC0470olK = K(this.f.g(i));
            if (abstractC0470olK != null && !abstractC0470olK.o()) {
                abstractC0470olK.a(6);
            }
        }
        R();
        C0285il c0285il = this.c;
        ArrayList arrayList = c0285il.c;
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            AbstractC0470ol abstractC0470ol = (AbstractC0470ol) arrayList.get(i2);
            if (abstractC0470ol != null) {
                abstractC0470ol.a(6);
                abstractC0470ol.a(1024);
            }
        }
        Yk yk = c0285il.h.m;
        if (yk == null || !yk.b) {
            c0285il.f();
        }
    }

    public final void Z(AbstractC0470ol abstractC0470ol, Yi yi) {
        abstractC0470ol.j &= -8193;
        boolean z = this.g0.h;
        R2 r2 = this.g;
        if (z && abstractC0470ol.k() && !abstractC0470ol.h() && !abstractC0470ol.o()) {
            ((C0620th) r2.c).d(I(abstractC0470ol), abstractC0470ol);
        }
        C0104co c0104co = (C0104co) r2.b;
        Ns nsA = (Ns) c0104co.get(abstractC0470ol);
        if (nsA == null) {
            nsA = Ns.a();
            c0104co.put(abstractC0470ol, nsA);
        }
        nsA.b = yi;
        nsA.a |= 4;
    }

    public final void a0() {
        boolean zIsFinished;
        EdgeEffect edgeEffect = this.H;
        if (edgeEffect != null) {
            edgeEffect.onRelease();
            zIsFinished = this.H.isFinished();
        } else {
            zIsFinished = false;
        }
        EdgeEffect edgeEffect2 = this.I;
        if (edgeEffect2 != null) {
            edgeEffect2.onRelease();
            zIsFinished |= this.I.isFinished();
        }
        EdgeEffect edgeEffect3 = this.J;
        if (edgeEffect3 != null) {
            edgeEffect3.onRelease();
            zIsFinished |= this.J.isFinished();
        }
        EdgeEffect edgeEffect4 = this.K;
        if (edgeEffect4 != null) {
            edgeEffect4.onRelease();
            zIsFinished |= this.K.isFinished();
        }
        if (zIsFinished) {
            postInvalidateOnAnimation();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void addFocusables(ArrayList arrayList, int i, int i2) {
        AbstractC0162el abstractC0162el = this.n;
        if (abstractC0162el != null) {
            abstractC0162el.getClass();
        }
        super.addFocusables(arrayList, i, i2);
    }

    public final int b0(int i, float f) {
        float height = f / getHeight();
        float width = i / getWidth();
        EdgeEffect edgeEffect = this.H;
        float f2 = 0.0f;
        if (edgeEffect == null || AbstractC0744xh.u(edgeEffect) == 0.0f) {
            EdgeEffect edgeEffect2 = this.J;
            if (edgeEffect2 != null && AbstractC0744xh.u(edgeEffect2) != 0.0f) {
                if (canScrollHorizontally(1)) {
                    this.J.onRelease();
                } else {
                    float fN = AbstractC0744xh.N(this.J, width, height);
                    if (AbstractC0744xh.u(this.J) == 0.0f) {
                        this.J.onRelease();
                    }
                    f2 = fN;
                }
                invalidate();
            }
        } else {
            if (canScrollHorizontally(-1)) {
                this.H.onRelease();
            } else {
                float f3 = -AbstractC0744xh.N(this.H, -width, 1.0f - height);
                if (AbstractC0744xh.u(this.H) == 0.0f) {
                    this.H.onRelease();
                }
                f2 = f3;
            }
            invalidate();
        }
        return Math.round(f2 * getWidth());
    }

    public final int c0(int i, float f) {
        float width = f / getWidth();
        float height = i / getHeight();
        EdgeEffect edgeEffect = this.I;
        float f2 = 0.0f;
        if (edgeEffect == null || AbstractC0744xh.u(edgeEffect) == 0.0f) {
            EdgeEffect edgeEffect2 = this.K;
            if (edgeEffect2 != null && AbstractC0744xh.u(edgeEffect2) != 0.0f) {
                if (canScrollVertically(1)) {
                    this.K.onRelease();
                } else {
                    float fN = AbstractC0744xh.N(this.K, height, 1.0f - width);
                    if (AbstractC0744xh.u(this.K) == 0.0f) {
                        this.K.onRelease();
                    }
                    f2 = fN;
                }
                invalidate();
            }
        } else {
            if (canScrollVertically(-1)) {
                this.I.onRelease();
            } else {
                float f3 = -AbstractC0744xh.N(this.I, -height, width);
                if (AbstractC0744xh.u(this.I) == 0.0f) {
                    this.I.onRelease();
                }
                f2 = f3;
            }
            invalidate();
        }
        return Math.round(f2 * getHeight());
    }

    @Override // android.view.ViewGroup
    public final boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return (layoutParams instanceof C0193fl) && this.n.f((C0193fl) layoutParams);
    }

    @Override // android.view.View
    public final int computeHorizontalScrollExtent() {
        AbstractC0162el abstractC0162el = this.n;
        if (abstractC0162el != null && abstractC0162el.d()) {
            return this.n.j(this.g0);
        }
        return 0;
    }

    @Override // android.view.View
    public final int computeHorizontalScrollOffset() {
        AbstractC0162el abstractC0162el = this.n;
        if (abstractC0162el != null && abstractC0162el.d()) {
            return this.n.k(this.g0);
        }
        return 0;
    }

    @Override // android.view.View
    public final int computeHorizontalScrollRange() {
        AbstractC0162el abstractC0162el = this.n;
        if (abstractC0162el != null && abstractC0162el.d()) {
            return this.n.l(this.g0);
        }
        return 0;
    }

    @Override // android.view.View
    public final int computeVerticalScrollExtent() {
        AbstractC0162el abstractC0162el = this.n;
        if (abstractC0162el != null && abstractC0162el.e()) {
            return this.n.m(this.g0);
        }
        return 0;
    }

    @Override // android.view.View
    public final int computeVerticalScrollOffset() {
        AbstractC0162el abstractC0162el = this.n;
        if (abstractC0162el != null && abstractC0162el.e()) {
            return this.n.n(this.g0);
        }
        return 0;
    }

    @Override // android.view.View
    public final int computeVerticalScrollRange() {
        AbstractC0162el abstractC0162el = this.n;
        if (abstractC0162el != null && abstractC0162el.e()) {
            return this.n.o(this.g0);
        }
        return 0;
    }

    public final void d0(View view, View view2) {
        View view3 = view2 != null ? view2 : view;
        int width = view3.getWidth();
        int height = view3.getHeight();
        Rect rect = this.j;
        rect.set(0, 0, width, height);
        ViewGroup.LayoutParams layoutParams = view3.getLayoutParams();
        if (layoutParams instanceof C0193fl) {
            C0193fl c0193fl = (C0193fl) layoutParams;
            if (!c0193fl.c) {
                int i = rect.left;
                Rect rect2 = c0193fl.b;
                rect.left = i - rect2.left;
                rect.right += rect2.right;
                rect.top -= rect2.top;
                rect.bottom += rect2.bottom;
            }
        }
        if (view2 != null) {
            offsetDescendantRectToMyCoords(view2, rect);
            offsetRectIntoDescendantCoords(view, rect);
        }
        this.n.n0(this, view, this.j, !this.u, view2 == null);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (super.dispatchKeyEvent(keyEvent)) {
            return true;
        }
        AbstractC0162el abstractC0162el = this.n;
        int iA = 0;
        if (abstractC0162el != null) {
            if (abstractC0162el.e()) {
                int keyCode = keyEvent.getKeyCode();
                if (keyCode == 92 || keyCode == 93) {
                    int measuredHeight = getMeasuredHeight();
                    if (keyCode == 93) {
                        l0(0, measuredHeight, false);
                        return true;
                    }
                    l0(0, -measuredHeight, false);
                    return true;
                }
                if (keyCode == 122 || keyCode == 123) {
                    boolean zL = abstractC0162el.L();
                    if (keyCode == 122) {
                        if (zL) {
                            iA = this.m.a();
                        }
                    } else if (!zL) {
                        iA = this.m.a();
                    }
                    m0(iA);
                    return true;
                }
            } else if (abstractC0162el.d()) {
                int keyCode2 = keyEvent.getKeyCode();
                if (keyCode2 == 92 || keyCode2 == 93) {
                    int measuredWidth = getMeasuredWidth();
                    if (keyCode2 == 93) {
                        l0(measuredWidth, 0, false);
                        return true;
                    }
                    l0(-measuredWidth, 0, false);
                    return true;
                }
                if (keyCode2 == 122 || keyCode2 == 123) {
                    boolean zL2 = abstractC0162el.L();
                    if (keyCode2 == 122) {
                        if (zL2) {
                            iA = this.m.a();
                        }
                    } else if (!zL2) {
                        iA = this.m.a();
                    }
                    m0(iA);
                    return true;
                }
            }
        }
        return false;
    }

    @Override // android.view.View
    public final boolean dispatchNestedFling(float f, float f2, boolean z) {
        return N().a(f, f2, z);
    }

    @Override // android.view.View
    public final boolean dispatchNestedPreFling(float f, float f2) {
        return N().b(f, f2);
    }

    @Override // android.view.View
    public final boolean dispatchNestedPreScroll(int i, int i2, int[] iArr, int[] iArr2) {
        return N().c(i, i2, 0, iArr, iArr2);
    }

    @Override // android.view.View
    public final boolean dispatchNestedScroll(int i, int i2, int i3, int i4, int[] iArr) {
        return N().d(i, i2, i3, i4, iArr, 0, null);
    }

    @Override // android.view.View
    public final boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        onPopulateAccessibilityEvent(accessibilityEvent);
        return true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchRestoreInstanceState(SparseArray sparseArray) {
        dispatchThawSelfOnly(sparseArray);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchSaveInstanceState(SparseArray sparseArray) {
        dispatchFreezeSelfOnly(sparseArray);
    }

    @Override // android.view.View
    public final void draw(Canvas canvas) {
        boolean z;
        super.draw(canvas);
        ArrayList arrayList = this.p;
        int size = arrayList.size();
        boolean z2 = false;
        for (int i = 0; i < size; i++) {
            ((AbstractC0070bl) arrayList.get(i)).c(canvas, this);
        }
        EdgeEffect edgeEffect = this.H;
        if (edgeEffect == null || edgeEffect.isFinished()) {
            z = false;
        } else {
            int iSave = canvas.save();
            int paddingBottom = this.h ? getPaddingBottom() : 0;
            canvas.rotate(270.0f);
            canvas.translate((-getHeight()) + paddingBottom, 0.0f);
            EdgeEffect edgeEffect2 = this.H;
            z = edgeEffect2 != null && edgeEffect2.draw(canvas);
            canvas.restoreToCount(iSave);
        }
        EdgeEffect edgeEffect3 = this.I;
        if (edgeEffect3 != null && !edgeEffect3.isFinished()) {
            int iSave2 = canvas.save();
            if (this.h) {
                canvas.translate(getPaddingLeft(), getPaddingTop());
            }
            EdgeEffect edgeEffect4 = this.I;
            z |= edgeEffect4 != null && edgeEffect4.draw(canvas);
            canvas.restoreToCount(iSave2);
        }
        EdgeEffect edgeEffect5 = this.J;
        if (edgeEffect5 != null && !edgeEffect5.isFinished()) {
            int iSave3 = canvas.save();
            int width = getWidth();
            int paddingTop = this.h ? getPaddingTop() : 0;
            canvas.rotate(90.0f);
            canvas.translate(paddingTop, -width);
            EdgeEffect edgeEffect6 = this.J;
            z |= edgeEffect6 != null && edgeEffect6.draw(canvas);
            canvas.restoreToCount(iSave3);
        }
        EdgeEffect edgeEffect7 = this.K;
        if (edgeEffect7 != null && !edgeEffect7.isFinished()) {
            int iSave4 = canvas.save();
            canvas.rotate(180.0f);
            if (this.h) {
                canvas.translate(getPaddingRight() + (-getWidth()), getPaddingBottom() + (-getHeight()));
            } else {
                canvas.translate(-getWidth(), -getHeight());
            }
            EdgeEffect edgeEffect8 = this.K;
            if (edgeEffect8 != null && edgeEffect8.draw(canvas)) {
                z2 = true;
            }
            z |= z2;
            canvas.restoreToCount(iSave4);
        }
        if ((z || this.L == null || arrayList.size() <= 0 || !this.L.k()) ? z : true) {
            postInvalidateOnAnimation();
        }
    }

    @Override // android.view.ViewGroup
    public final boolean drawChild(Canvas canvas, View view, long j) {
        return super.drawChild(canvas, view, j);
    }

    /* JADX WARN: Code duplicated, block: B:30:0x00d2  */
    /* JADX WARN: Code duplicated, block: B:32:0x00ea  */
    /* JADX WARN: Code duplicated, block: B:34:0x00ee  */
    /* JADX WARN: Code duplicated, block: B:35:0x0105 A[DONT_INVERT, PHI: r10
  0x0105: PHI (r10v9 boolean) = (r10v7 boolean), (r10v10 boolean) binds: [B:33:0x00ec, B:31:0x00e7] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:36:0x0107  */
    /* JADX WARN: Code duplicated, block: B:40:0x010f  */
    /* JADX WARN: Code duplicated, block: B:43:0x0118  */
    public final boolean e0(int i, int i2, MotionEvent motionEvent, int i3) {
        int i4;
        int i5;
        int i6;
        int i7;
        boolean z;
        float f;
        boolean z2;
        p();
        Yk yk = this.m;
        int[] iArr = this.q0;
        if (yk != null) {
            iArr[0] = 0;
            iArr[1] = 0;
            f0(i, i2, iArr);
            int i8 = iArr[0];
            int i9 = iArr[1];
            i6 = i - i8;
            i7 = i2 - i9;
            i4 = i8;
            i5 = i9;
        } else {
            i4 = 0;
            i5 = 0;
            i6 = 0;
            i7 = 0;
        }
        if (!this.p.isEmpty()) {
            invalidate();
        }
        iArr[0] = 0;
        iArr[1] = 0;
        N().d(i4, i5, i6, i7, this.o0, i3, iArr);
        int i10 = iArr[0];
        int i11 = i6 - i10;
        int i12 = iArr[1];
        int i13 = i7 - i12;
        boolean z3 = (i10 == 0 && i12 == 0) ? false : true;
        int i14 = this.R;
        int[] iArr2 = this.o0;
        int i15 = iArr2[0];
        this.R = i14 - i15;
        int i16 = this.S;
        int i17 = iArr2[1];
        this.S = i16 - i17;
        int[] iArr3 = this.p0;
        iArr3[0] = iArr3[0] + i15;
        iArr3[1] = iArr3[1] + i17;
        if (getOverScrollMode() != 2) {
            if (motionEvent == null || AbstractC0599sr.x(motionEvent, 8194)) {
                z = true;
            } else {
                float x = motionEvent.getX();
                float f2 = i11;
                float y = motionEvent.getY();
                float f3 = i13;
                if (f2 < 0.0f) {
                    w();
                    f = 0.0f;
                    z = true;
                    AbstractC0744xh.N(this.H, (-f2) / getWidth(), 1.0f - (y / getHeight()));
                } else {
                    f = 0.0f;
                    z = true;
                    if (f2 > 0.0f) {
                        x();
                        AbstractC0744xh.N(this.J, f2 / getWidth(), y / getHeight());
                    } else {
                        z2 = false;
                    }
                    if (f3 < f) {
                        y();
                        AbstractC0744xh.N(this.I, (-f3) / getHeight(), x / getWidth());
                    } else if (f3 > f) {
                        v();
                        AbstractC0744xh.N(this.K, f3 / getHeight(), 1.0f - (x / getWidth()));
                    } else {
                        if (z2 || f2 != f || f3 != f) {
                            postInvalidateOnAnimation();
                        }
                        if (Build.VERSION.SDK_INT >= 31 && AbstractC0599sr.x(motionEvent, 4194304)) {
                            a0();
                        }
                    }
                    z2 = z;
                    if (z2) {
                        postInvalidateOnAnimation();
                    } else {
                        postInvalidateOnAnimation();
                    }
                    if (Build.VERSION.SDK_INT >= 31) {
                        a0();
                    }
                }
                z2 = z;
                if (f3 < f) {
                    y();
                    AbstractC0744xh.N(this.I, (-f3) / getHeight(), x / getWidth());
                } else if (f3 > f) {
                    v();
                    AbstractC0744xh.N(this.K, f3 / getHeight(), 1.0f - (x / getWidth()));
                } else {
                    if (z2) {
                        postInvalidateOnAnimation();
                    } else {
                        postInvalidateOnAnimation();
                    }
                    if (Build.VERSION.SDK_INT >= 31) {
                        a0();
                    }
                }
                z2 = z;
                if (z2) {
                    postInvalidateOnAnimation();
                } else {
                    postInvalidateOnAnimation();
                }
                if (Build.VERSION.SDK_INT >= 31) {
                    a0();
                }
            }
            n(i, i2);
        } else {
            z = true;
        }
        if (i4 != 0 || i5 != 0) {
            u(i4, i5);
        }
        if (!awakenScrollBars()) {
            invalidate();
        }
        if (!z3 && i4 == 0 && i5 == 0) {
            return false;
        }
        return z;
    }

    public final void f0(int i, int i2, int[] iArr) {
        AbstractC0470ol abstractC0470ol;
        n0();
        T();
        Trace.beginSection("RV Scroll");
        C0408ml c0408ml = this.g0;
        A(c0408ml);
        C0285il c0285il = this.c;
        int iP0 = i != 0 ? this.n.p0(i, c0285il, c0408ml) : 0;
        int iR0 = i2 != 0 ? this.n.r0(i2, c0285il, c0408ml) : 0;
        Trace.endSection();
        int iE = this.f.e();
        for (int i3 = 0; i3 < iE; i3++) {
            View viewD = this.f.d(i3);
            AbstractC0470ol abstractC0470olJ = J(viewD);
            if (abstractC0470olJ != null && (abstractC0470ol = abstractC0470olJ.i) != null) {
                int left = viewD.getLeft();
                int top = viewD.getTop();
                View view = abstractC0470ol.a;
                if (left != view.getLeft() || top != view.getTop()) {
                    view.layout(left, top, view.getWidth() + left, view.getHeight() + top);
                }
            }
        }
        U(true);
        p0(false);
        if (iArr != null) {
            iArr[0] = iP0;
            iArr[1] = iR0;
        }
    }

    /* JADX WARN: Code duplicated, block: B:118:0x0167  */
    /* JADX WARN: Code duplicated, block: B:137:0x019e A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:138:0x019f  */
    /* JADX WARN: Code duplicated, block: B:24:0x004c  */
    @Override // android.view.ViewGroup, android.view.ViewParent
    public final View focusSearch(View view, int i) {
        View viewT;
        int i2;
        byte b;
        boolean z;
        this.n.getClass();
        boolean z2 = true;
        boolean z3 = (this.m == null || this.n == null || P() || this.x) ? false : true;
        FocusFinder focusFinder = FocusFinder.getInstance();
        C0408ml c0408ml = this.g0;
        C0285il c0285il = this.c;
        if (z3 && (i == 2 || i == 1)) {
            if (this.n.e()) {
                if (focusFinder.findNextFocus(this, view, i == 2 ? 130 : 33) == null) {
                    z = true;
                } else {
                    z = false;
                }
            } else {
                z = false;
            }
            if (!z && this.n.d()) {
                z = focusFinder.findNextFocus(this, view, (this.n.b.getLayoutDirection() == 1) ^ (i == 2) ? 66 : 17) == null;
            }
            if (z) {
                p();
                if (B(view) != null) {
                    n0();
                    this.n.T(view, i, c0285il, c0408ml);
                    p0(false);
                }
                return null;
            }
            viewT = focusFinder.findNextFocus(this, view, i);
            if (viewT == null) {
            }
            if (viewT != null) {
                z2 = false;
            } else {
                z2 = false;
            }
            if (z2) {
                return viewT;
            }
            return super.focusSearch(view, i);
        }
        View viewFindNextFocus = focusFinder.findNextFocus(this, view, i);
        if (viewFindNextFocus == null && z3) {
            p();
            if (B(view) != null) {
                n0();
                viewT = this.n.T(view, i, c0285il, c0408ml);
                p0(false);
            }
            return null;
        }
        viewT = viewFindNextFocus;
        if (viewT == null && !viewT.hasFocusable()) {
            if (getFocusedChild() == null) {
                return super.focusSearch(view, i);
            }
            d0(viewT, null);
            return view;
        }
        if (viewT != null || viewT == this || viewT == view) {
            z2 = false;
        } else if (B(viewT) == null) {
            z2 = false;
        } else if (view != null && B(view) != null) {
            int width = view.getWidth();
            int height = view.getHeight();
            Rect rect = this.j;
            rect.set(0, 0, width, height);
            int width2 = viewT.getWidth();
            int height2 = viewT.getHeight();
            Rect rect2 = this.k;
            rect2.set(0, 0, width2, height2);
            offsetDescendantRectToMyCoords(view, rect);
            offsetDescendantRectToMyCoords(viewT, rect2);
            int i3 = this.n.b.getLayoutDirection() == 1 ? -1 : 1;
            int i4 = rect.left;
            int i5 = rect2.left;
            if ((i4 < i5 || rect.right <= i5) && rect.right < rect2.right) {
                i2 = 1;
            } else {
                int i6 = rect.right;
                int i7 = rect2.right;
                i2 = ((i6 > i7 || i4 >= i7) && i4 > i5) ? -1 : 0;
            }
            int i8 = rect.top;
            int i9 = rect2.top;
            if ((i8 < i9 || rect.bottom <= i9) && rect.bottom < rect2.bottom) {
                b = 1;
            } else {
                int i10 = rect.bottom;
                int i11 = rect2.bottom;
                b = ((i10 > i11 || i8 >= i11) && i8 > i9) ? (byte) -1 : (byte) 0;
            }
            if (i != 1) {
                if (i != 2) {
                    if (i != 17) {
                        if (i != 33) {
                            if (i != 66) {
                                if (i != 130) {
                                    throw new IllegalArgumentException("Invalid direction: " + i + z());
                                }
                                if (b <= 0) {
                                    z2 = false;
                                }
                            } else if (i2 <= 0) {
                                z2 = false;
                            }
                        } else if (b >= 0) {
                            z2 = false;
                        }
                    } else if (i2 >= 0) {
                        z2 = false;
                    }
                } else if (b <= 0 && (b != 0 || i2 * i3 <= 0)) {
                    z2 = false;
                }
            } else if (b >= 0 && (b != 0 || i2 * i3 >= 0)) {
                z2 = false;
            }
        }
        if (z2) {
            return viewT;
        }
        return super.focusSearch(view, i);
    }

    public final void g0(int i) {
        if (this.x) {
            return;
        }
        q0();
        AbstractC0162el abstractC0162el = this.n;
        if (abstractC0162el == null) {
            Log.e("RecyclerView", "Cannot scroll to position a LayoutManager set. Call setLayoutManager with a non-null argument.");
        } else {
            abstractC0162el.q0(i);
            awakenScrollBars();
        }
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateDefaultLayoutParams() {
        AbstractC0162el abstractC0162el = this.n;
        if (abstractC0162el != null) {
            return abstractC0162el.r();
        }
        throw new IllegalStateException("RecyclerView has no LayoutManager" + z());
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        AbstractC0162el abstractC0162el = this.n;
        if (abstractC0162el != null) {
            return abstractC0162el.s(getContext(), attributeSet);
        }
        throw new IllegalStateException("RecyclerView has no LayoutManager" + z());
    }

    @Override // android.view.ViewGroup, android.view.View
    public final CharSequence getAccessibilityClassName() {
        return "androidx.recyclerview.widget.RecyclerView";
    }

    @Override // android.view.View
    public final int getBaseline() {
        AbstractC0162el abstractC0162el = this.n;
        if (abstractC0162el == null) {
            return super.getBaseline();
        }
        abstractC0162el.getClass();
        return -1;
    }

    @Override // android.view.ViewGroup
    public final int getChildDrawingOrder(int i, int i2) {
        return super.getChildDrawingOrder(i, i2);
    }

    @Override // android.view.ViewGroup
    public final boolean getClipToPadding() {
        return this.h;
    }

    public final void h(AbstractC0470ol abstractC0470ol) {
        View view = abstractC0470ol.a;
        boolean z = view.getParent() == this;
        this.c.l(J(view));
        if (abstractC0470ol.j()) {
            this.f.b(view, -1, view.getLayoutParams(), true);
            return;
        }
        if (!z) {
            this.f.a(view, -1, true);
            return;
        }
        W5 w5 = this.f;
        int iIndexOfChild = w5.a.a.indexOfChild(view);
        if (iIndexOfChild >= 0) {
            w5.b.h(iIndexOfChild);
            w5.i(view);
        } else {
            throw new IllegalArgumentException("view is not a child, cannot hide " + view);
        }
    }

    public final void h0(Yk yk) {
        suppressLayout(false);
        Yk yk2 = this.m;
        U3 u3 = this.b;
        if (yk2 != null) {
            yk2.a.unregisterObserver(u3);
            this.m.getClass();
        }
        C0458o9 c0458o9 = this.L;
        if (c0458o9 != null) {
            c0458o9.h();
        }
        AbstractC0162el abstractC0162el = this.n;
        C0285il c0285il = this.c;
        if (abstractC0162el != null) {
            abstractC0162el.j0(c0285il);
            this.n.k0(c0285il);
        }
        c0285il.a.clear();
        c0285il.f();
        T0 t0 = this.e;
        t0.o((ArrayList) t0.c);
        t0.o((ArrayList) t0.d);
        t0.a = 0;
        Yk yk3 = this.m;
        this.m = yk;
        if (yk != null) {
            yk.a.registerObserver(u3);
        }
        AbstractC0162el abstractC0162el2 = this.n;
        if (abstractC0162el2 != null) {
            abstractC0162el2.Q();
        }
        Yk yk4 = this.m;
        c0285il.a.clear();
        c0285il.f();
        c0285il.e(yk3, true);
        C0788z c0788zC = c0285il.c();
        if (yk3 != null) {
            c0788zC.a--;
        }
        if (c0788zC.a == 0) {
            int i = 0;
            while (true) {
                SparseArray sparseArray = (SparseArray) c0788zC.b;
                if (i >= sparseArray.size()) {
                    break;
                }
                C0255hl c0255hl = (C0255hl) sparseArray.valueAt(i);
                ArrayList arrayList = c0255hl.a;
                int size = arrayList.size();
                int i2 = 0;
                while (i2 < size) {
                    Object obj = arrayList.get(i2);
                    i2++;
                    AbstractC0599sr.c(((AbstractC0470ol) obj).a);
                }
                c0255hl.a.clear();
                i++;
            }
        }
        if (yk4 != null) {
            c0788zC.a++;
        }
        c0285il.d();
        this.g0.f = true;
        Y(false);
        requestLayout();
    }

    @Override // android.view.View
    public final boolean hasNestedScrollingParent() {
        return N().f(0);
    }

    public final void i(AbstractC0070bl abstractC0070bl) {
        AbstractC0162el abstractC0162el = this.n;
        if (abstractC0162el != null) {
            abstractC0162el.c("Cannot add item decoration during a scroll  or layout");
        }
        ArrayList arrayList = this.p;
        if (arrayList.isEmpty()) {
            setWillNotDraw(false);
        }
        arrayList.add(abstractC0070bl);
        R();
        requestLayout();
    }

    public final void i0(AbstractC0162el abstractC0162el) {
        RecyclerView recyclerView;
        if (abstractC0162el == this.n) {
            return;
        }
        q0();
        AbstractC0162el abstractC0162el2 = this.n;
        C0285il c0285il = this.c;
        if (abstractC0162el2 != null) {
            C0458o9 c0458o9 = this.L;
            if (c0458o9 != null) {
                c0458o9.h();
            }
            this.n.j0(c0285il);
            this.n.k0(c0285il);
            c0285il.a.clear();
            c0285il.f();
            if (this.s) {
                AbstractC0162el abstractC0162el3 = this.n;
                abstractC0162el3.g = false;
                abstractC0162el3.S(this);
            }
            this.n.w0(null);
            this.n = null;
        } else {
            c0285il.a.clear();
            c0285il.f();
        }
        W5 w5 = this.f;
        w5.b.g();
        ArrayList arrayList = w5.c;
        int size = arrayList.size() - 1;
        while (true) {
            recyclerView = w5.a.a;
            if (size < 0) {
                break;
            }
            AbstractC0470ol abstractC0470olK = K((View) arrayList.get(size));
            if (abstractC0470olK != null) {
                int i = abstractC0470olK.p;
                if (recyclerView.P()) {
                    abstractC0470olK.q = i;
                    recyclerView.r0.add(abstractC0470olK);
                } else {
                    abstractC0470olK.a.setImportantForAccessibility(i);
                }
                abstractC0470olK.p = 0;
            }
            arrayList.remove(size);
            size--;
        }
        int childCount = recyclerView.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = recyclerView.getChildAt(i2);
            AbstractC0470ol abstractC0470olK2 = K(childAt);
            Yk yk = recyclerView.m;
            if (yk != null && abstractC0470olK2 != null) {
                yk.h(abstractC0470olK2);
            }
            childAt.clearAnimation();
        }
        recyclerView.removeAllViews();
        this.n = abstractC0162el;
        if (abstractC0162el != null) {
            if (abstractC0162el.b != null) {
                throw new IllegalArgumentException("LayoutManager " + abstractC0162el + " is already attached to a RecyclerView:" + abstractC0162el.b.z());
            }
            abstractC0162el.w0(this);
            if (this.s) {
                AbstractC0162el abstractC0162el4 = this.n;
                abstractC0162el4.g = true;
                abstractC0162el4.R(this);
            }
        }
        c0285il.m();
        requestLayout();
    }

    @Override // android.view.View
    public final boolean isAttachedToWindow() {
        return this.s;
    }

    @Override // android.view.ViewGroup
    public final boolean isLayoutSuppressed() {
        return this.x;
    }

    @Override // android.view.View
    public final boolean isNestedScrollingEnabled() {
        return N().d;
    }

    public final void j(AbstractC0224gl abstractC0224gl) {
        if (this.h0 == null) {
            this.h0 = new ArrayList();
        }
        this.h0.add(abstractC0224gl);
    }

    public final void j0(int i) {
        Jg jg;
        if (i == this.M) {
            return;
        }
        this.M = i;
        if (i != 2) {
            RunnableC0439nl runnableC0439nl = this.d0;
            runnableC0439nl.g.removeCallbacks(runnableC0439nl);
            runnableC0439nl.c.abortAnimation();
            AbstractC0162el abstractC0162el = this.n;
            if (abstractC0162el != null && (jg = abstractC0162el.e) != null) {
                jg.i();
            }
        }
        AbstractC0162el abstractC0162el2 = this.n;
        if (abstractC0162el2 != null) {
            abstractC0162el2.h0(i);
        }
        ArrayList arrayList = this.h0;
        if (arrayList != null) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                ((AbstractC0224gl) this.h0.get(size)).a(this, i);
            }
        }
    }

    public final void k(String str) {
        if (P()) {
            if (str != null) {
                throw new IllegalStateException(str);
            }
            throw new IllegalStateException("Cannot call this method while RecyclerView is computing a layout or scrolling" + z());
        }
        if (this.F > 0) {
            Log.w("RecyclerView", "Cannot call this method in a scroll callback. Scroll callbacks mightbe run during a measure & layout pass where you cannot change theRecyclerView data. Any method call that might change the structureof the RecyclerView or the adapter contents should be postponed tothe next frame.", new IllegalStateException("" + z()));
        }
    }

    public final boolean k0(EdgeEffect edgeEffect, int i, int i2) {
        if (i > 0) {
            return true;
        }
        float fU = AbstractC0744xh.u(edgeEffect) * i2;
        float fAbs = Math.abs(-i) * 0.35f;
        float f = this.a * 0.015f;
        double dLog = Math.log(fAbs / f);
        double d = A0;
        return ((float) (Math.exp((d / (d - 1.0d)) * dLog) * ((double) f))) < fU;
    }

    public final void l0(int i, int i2, boolean z) {
        AbstractC0162el abstractC0162el = this.n;
        if (abstractC0162el == null) {
            Log.e("RecyclerView", "Cannot smooth scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
            return;
        }
        if (this.x) {
            return;
        }
        if (!abstractC0162el.d()) {
            i = 0;
        }
        if (!this.n.e()) {
            i2 = 0;
        }
        if (i == 0 && i2 == 0) {
            return;
        }
        if (z) {
            int i3 = i != 0 ? 1 : 0;
            if (i2 != 0) {
                i3 |= 2;
            }
            N().g(i3, 1);
        }
        this.d0.c(i, i2, Integer.MIN_VALUE, null);
    }

    public final void m() {
        int iH = this.f.h();
        for (int i = 0; i < iH; i++) {
            AbstractC0470ol abstractC0470olK = K(this.f.g(i));
            if (!abstractC0470olK.o()) {
                abstractC0470olK.d = -1;
                abstractC0470olK.g = -1;
            }
        }
        C0285il c0285il = this.c;
        ArrayList arrayList = c0285il.c;
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            AbstractC0470ol abstractC0470ol = (AbstractC0470ol) arrayList.get(i2);
            abstractC0470ol.d = -1;
            abstractC0470ol.g = -1;
        }
        ArrayList arrayList2 = c0285il.a;
        int size2 = arrayList2.size();
        for (int i3 = 0; i3 < size2; i3++) {
            AbstractC0470ol abstractC0470ol2 = (AbstractC0470ol) arrayList2.get(i3);
            abstractC0470ol2.d = -1;
            abstractC0470ol2.g = -1;
        }
        ArrayList arrayList3 = c0285il.b;
        if (arrayList3 != null) {
            int size3 = arrayList3.size();
            for (int i4 = 0; i4 < size3; i4++) {
                AbstractC0470ol abstractC0470ol3 = (AbstractC0470ol) c0285il.b.get(i4);
                abstractC0470ol3.d = -1;
                abstractC0470ol3.g = -1;
            }
        }
    }

    public final void m0(int i) {
        if (this.x) {
            return;
        }
        AbstractC0162el abstractC0162el = this.n;
        if (abstractC0162el == null) {
            Log.e("RecyclerView", "Cannot smooth scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
        } else {
            abstractC0162el.A0(this, i);
        }
    }

    public final void n(int i, int i2) {
        boolean zIsFinished;
        EdgeEffect edgeEffect = this.H;
        if (edgeEffect == null || edgeEffect.isFinished() || i <= 0) {
            zIsFinished = false;
        } else {
            this.H.onRelease();
            zIsFinished = this.H.isFinished();
        }
        EdgeEffect edgeEffect2 = this.J;
        if (edgeEffect2 != null && !edgeEffect2.isFinished() && i < 0) {
            this.J.onRelease();
            zIsFinished |= this.J.isFinished();
        }
        EdgeEffect edgeEffect3 = this.I;
        if (edgeEffect3 != null && !edgeEffect3.isFinished() && i2 > 0) {
            this.I.onRelease();
            zIsFinished |= this.I.isFinished();
        }
        EdgeEffect edgeEffect4 = this.K;
        if (edgeEffect4 != null && !edgeEffect4.isFinished() && i2 < 0) {
            this.K.onRelease();
            zIsFinished |= this.K.isFinished();
        }
        if (zIsFinished) {
            postInvalidateOnAnimation();
        }
    }

    public final void n0() {
        int i = this.v + 1;
        this.v = i;
        if (i != 1 || this.x) {
            return;
        }
        this.w = false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void o0(int i) {
        boolean zD = this.n.d();
        int i2 = zD;
        if (this.n.e()) {
            i2 = (zD ? 1 : 0) | 2;
        }
        N().g(i2, i);
    }

    /* JADX WARN: Code duplicated, block: B:21:0x0058  */
    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        float refreshRate;
        super.onAttachedToWindow();
        this.E = 0;
        this.s = true;
        this.u = this.u && !isLayoutRequested();
        this.c.d();
        AbstractC0162el abstractC0162el = this.n;
        if (abstractC0162el != null) {
            abstractC0162el.g = true;
            abstractC0162el.R(this);
        }
        this.k0 = false;
        if (C0) {
            ThreadLocal threadLocal = Hd.e;
            Hd hd = (Hd) threadLocal.get();
            this.e0 = hd;
            if (hd == null) {
                this.e0 = new Hd();
                WeakHashMap weakHashMap = Hs.a;
                Display display = getDisplay();
                if (isInEditMode() || display == null) {
                    refreshRate = 60.0f;
                } else {
                    refreshRate = display.getRefreshRate();
                    if (refreshRate < 30.0f) {
                        refreshRate = 60.0f;
                    }
                }
                Hd hd2 = this.e0;
                hd2.c = (long) (1.0E9f / refreshRate);
                threadLocal.set(hd2);
            }
            Hd hd3 = this.e0;
            hd3.getClass();
            hd3.a.add(this);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        C0285il c0285il;
        Hd hd;
        super.onDetachedFromWindow();
        C0458o9 c0458o9 = this.L;
        if (c0458o9 != null) {
            c0458o9.h();
        }
        q0();
        int i = 0;
        this.s = false;
        AbstractC0162el abstractC0162el = this.n;
        if (abstractC0162el != null) {
            abstractC0162el.g = false;
            abstractC0162el.S(this);
        }
        this.r0.clear();
        removeCallbacks(this.s0);
        this.g.getClass();
        while (Ns.d.a() != null) {
        }
        int i2 = 0;
        while (true) {
            c0285il = this.c;
            ArrayList arrayList = c0285il.c;
            if (i2 >= arrayList.size()) {
                break;
            }
            AbstractC0599sr.c(((AbstractC0470ol) arrayList.get(i2)).a);
            i2++;
        }
        c0285il.e(c0285il.h.m, false);
        while (i < getChildCount()) {
            int i3 = i + 1;
            View childAt = getChildAt(i);
            if (childAt == null) {
                throw new IndexOutOfBoundsException();
            }
            Xj xj = (Xj) childAt.getTag(moe.shizuku.privileged.api.R.id._0_resource_name_obfuscated_res_0x7f090158);
            if (xj == null) {
                xj = new Xj();
                childAt.setTag(moe.shizuku.privileged.api.R.id._0_resource_name_obfuscated_res_0x7f090158, xj);
            }
            ArrayList arrayList2 = xj.a;
            int iA0 = AbstractC0702w6.a0(arrayList2);
            if (-1 < iA0) {
                arrayList2.get(iA0).getClass();
                throw new ClassCastException();
            }
            i = i3;
        }
        if (!C0 || (hd = this.e0) == null) {
            return;
        }
        hd.a.remove(this);
        this.e0 = null;
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        ArrayList arrayList = this.p;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            ((AbstractC0070bl) arrayList.get(i)).b(this);
        }
    }

    @Override // android.view.View
    public final boolean onGenericMotionEvent(MotionEvent motionEvent) {
        int i;
        boolean z;
        float axisValue;
        if (this.n != null && !this.x && motionEvent.getAction() == 8) {
            float f = 0.0f;
            if ((motionEvent.getSource() & 2) != 0) {
                float f2 = this.n.e() ? -motionEvent.getAxisValue(9) : 0.0f;
                z = false;
                axisValue = this.n.d() ? motionEvent.getAxisValue(10) : 0.0f;
                f = f2;
                i = 0;
            } else if ((motionEvent.getSource() & 4194304) != 0) {
                i = 26;
                axisValue = motionEvent.getAxisValue(26);
                if (this.n.e()) {
                    float f3 = -axisValue;
                    axisValue = 0.0f;
                    f = f3;
                } else if (!this.n.d()) {
                    axisValue = 0.0f;
                }
                z = this.w0;
            } else {
                i = 0;
                z = false;
                axisValue = 0.0f;
            }
            int i2 = (int) (f * this.b0);
            int i3 = (int) (axisValue * this.a0);
            if (z) {
                OverScroller overScroller = this.d0.c;
                l0((overScroller.getFinalX() - overScroller.getCurrX()) + i3, (overScroller.getFinalY() - overScroller.getCurrY()) + i2, true);
            } else {
                AbstractC0162el abstractC0162el = this.n;
                if (abstractC0162el == null) {
                    Log.e("RecyclerView", "Cannot scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
                } else if (!this.x) {
                    int[] iArr = this.q0;
                    iArr[0] = 0;
                    iArr[1] = 0;
                    boolean zD = abstractC0162el.d();
                    boolean zE = this.n.e();
                    int i4 = zE ? (zD ? 1 : 0) | 2 : zD ? 1 : 0;
                    float y = motionEvent.getY();
                    float x = motionEvent.getX();
                    int iB0 = i3 - b0(i3, y);
                    int iC0 = i2 - c0(i2, x);
                    N().g(i4, 1);
                    if (N().c(zD ? iB0 : 0, zE ? iC0 : 0, 1, this.q0, this.o0)) {
                        iB0 -= iArr[0];
                        iC0 -= iArr[1];
                    }
                    e0(zD ? iB0 : 0, zE ? iC0 : 0, motionEvent, 1);
                    Hd hd = this.e0;
                    if (hd != null && (iB0 != 0 || iC0 != 0)) {
                        hd.a(this, iB0, iC0);
                    }
                    N().h(1);
                }
            }
            if (i != 0 && !z) {
                this.y0.a(motionEvent, i);
            }
        }
        return false;
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean z;
        boolean z2;
        if (!this.x) {
            this.r = null;
            if (C(motionEvent)) {
                VelocityTracker velocityTracker = this.O;
                if (velocityTracker != null) {
                    velocityTracker.clear();
                }
                N().h(0);
                a0();
                j0(0);
                return true;
            }
            AbstractC0162el abstractC0162el = this.n;
            if (abstractC0162el != null) {
                boolean zD = abstractC0162el.d();
                boolean zE = this.n.e();
                if (this.O == null) {
                    this.O = VelocityTracker.obtain();
                }
                this.O.addMovement(motionEvent);
                int actionMasked = motionEvent.getActionMasked();
                int actionIndex = motionEvent.getActionIndex();
                if (actionMasked == 0) {
                    if (this.y) {
                        this.y = false;
                    }
                    this.N = motionEvent.getPointerId(0);
                    int x = (int) (motionEvent.getX() + 0.5f);
                    this.R = x;
                    this.P = x;
                    int y = (int) (motionEvent.getY() + 0.5f);
                    this.S = y;
                    this.Q = y;
                    EdgeEffect edgeEffect = this.H;
                    if (edgeEffect == null || AbstractC0744xh.u(edgeEffect) == 0.0f || canScrollHorizontally(-1)) {
                        z = false;
                    } else {
                        AbstractC0744xh.N(this.H, 0.0f, 1.0f - (motionEvent.getY() / getHeight()));
                        z = true;
                    }
                    EdgeEffect edgeEffect2 = this.J;
                    if (edgeEffect2 != null && AbstractC0744xh.u(edgeEffect2) != 0.0f && !canScrollHorizontally(1)) {
                        AbstractC0744xh.N(this.J, 0.0f, motionEvent.getY() / getHeight());
                        z = true;
                    }
                    EdgeEffect edgeEffect3 = this.I;
                    if (edgeEffect3 != null && AbstractC0744xh.u(edgeEffect3) != 0.0f && !canScrollVertically(-1)) {
                        AbstractC0744xh.N(this.I, 0.0f, motionEvent.getX() / getWidth());
                        z = true;
                    }
                    EdgeEffect edgeEffect4 = this.K;
                    if (edgeEffect4 != null && AbstractC0744xh.u(edgeEffect4) != 0.0f && !canScrollVertically(1)) {
                        AbstractC0744xh.N(this.K, 0.0f, 1.0f - (motionEvent.getX() / getWidth()));
                        z = true;
                    }
                    if (z || this.M == 2) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                        j0(1);
                        N().h(1);
                    }
                    int[] iArr = this.p0;
                    iArr[1] = 0;
                    iArr[0] = 0;
                    o0(0);
                } else if (actionMasked == 1) {
                    this.O.clear();
                    N().h(0);
                } else if (actionMasked == 2) {
                    int iFindPointerIndex = motionEvent.findPointerIndex(this.N);
                    if (iFindPointerIndex < 0) {
                        Log.e("RecyclerView", "Error processing scroll; pointer index for id " + this.N + " not found. Did any MotionEvents get skipped?");
                        return false;
                    }
                    int x2 = (int) (motionEvent.getX(iFindPointerIndex) + 0.5f);
                    int y2 = (int) (motionEvent.getY(iFindPointerIndex) + 0.5f);
                    if (this.M != 1) {
                        int i = x2 - this.P;
                        int i2 = y2 - this.Q;
                        int i3 = this.T;
                        if (!zD || Math.abs(i) <= i3) {
                            z2 = false;
                        } else {
                            this.R = x2;
                            z2 = true;
                        }
                        if (zE && Math.abs(i2) > i3) {
                            this.S = y2;
                            z2 = true;
                        }
                        if (z2) {
                            j0(1);
                        }
                    }
                } else if (actionMasked == 3) {
                    VelocityTracker velocityTracker2 = this.O;
                    if (velocityTracker2 != null) {
                        velocityTracker2.clear();
                    }
                    N().h(0);
                    a0();
                    j0(0);
                } else if (actionMasked == 5) {
                    this.N = motionEvent.getPointerId(actionIndex);
                    int x3 = (int) (motionEvent.getX(actionIndex) + 0.5f);
                    this.R = x3;
                    this.P = x3;
                    int y3 = (int) (motionEvent.getY(actionIndex) + 0.5f);
                    this.S = y3;
                    this.Q = y3;
                } else if (actionMasked == 6) {
                    V(motionEvent);
                }
                if (this.M == 1) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        Trace.beginSection("RV OnLayout");
        r();
        Trace.endSection();
        this.u = true;
    }

    @Override // android.view.View
    public final void onMeasure(int i, int i2) {
        AbstractC0162el abstractC0162el = this.n;
        if (abstractC0162el == null) {
            q(i, i2);
            return;
        }
        boolean zK = abstractC0162el.K();
        boolean z = false;
        C0408ml c0408ml = this.g0;
        if (zK) {
            int mode = View.MeasureSpec.getMode(i);
            int mode2 = View.MeasureSpec.getMode(i2);
            this.n.b.q(i, i2);
            if (mode == 1073741824 && mode2 == 1073741824) {
                z = true;
            }
            this.t0 = z;
            if (z || this.m == null) {
                return;
            }
            if (c0408ml.d == 1) {
                s();
            }
            this.n.t0(i, i2);
            c0408ml.i = true;
            t();
            this.n.v0(i, i2);
            if (this.n.y0()) {
                this.n.t0(View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824));
                c0408ml.i = true;
                t();
                this.n.v0(i, i2);
            }
            this.u0 = getMeasuredWidth();
            this.v0 = getMeasuredHeight();
            return;
        }
        if (this.t) {
            this.n.b.q(i, i2);
            return;
        }
        if (this.A) {
            n0();
            T();
            X();
            U(true);
            if (c0408ml.k) {
                c0408ml.g = true;
            } else {
                this.e.d();
                c0408ml.g = false;
            }
            this.A = false;
            p0(false);
        } else if (c0408ml.k) {
            setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
            return;
        }
        Yk yk = this.m;
        if (yk != null) {
            c0408ml.e = yk.a();
        } else {
            c0408ml.e = 0;
        }
        n0();
        this.n.b.q(i, i2);
        p0(false);
        c0408ml.g = false;
    }

    @Override // android.view.ViewGroup
    public final boolean onRequestFocusInDescendants(int i, Rect rect) {
        if (P()) {
            return false;
        }
        return super.onRequestFocusInDescendants(i, rect);
    }

    @Override // android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof C0315jl)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        C0315jl c0315jl = (C0315jl) parcelable;
        this.d = c0315jl;
        super.onRestoreInstanceState(c0315jl.a);
        requestLayout();
    }

    @Override // android.view.View
    public final Parcelable onSaveInstanceState() {
        C0315jl c0315jl = new C0315jl(super.onSaveInstanceState());
        C0315jl c0315jl2 = this.d;
        if (c0315jl2 != null) {
            c0315jl.c = c0315jl2.c;
            return c0315jl;
        }
        AbstractC0162el abstractC0162el = this.n;
        if (abstractC0162el != null) {
            c0315jl.c = abstractC0162el.g0();
            return c0315jl;
        }
        c0315jl.c = null;
        return c0315jl;
    }

    @Override // android.view.View
    public final void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (i == i3 && i2 == i4) {
            return;
        }
        this.K = null;
        this.I = null;
        this.J = null;
        this.H = null;
    }

    /* JADX WARN: Code duplicated, block: B:102:0x0212 A[PHI: r6
  0x0212: PHI (r6v7 int) = (r6v2 int), (r6v11 int) binds: [B:96:0x01ff, B:100:0x020e] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        boolean zC;
        int i;
        char c;
        int i2;
        int i3;
        boolean z;
        if (!this.x && !this.y) {
            Tb tb = this.r;
            if (tb == null) {
                zC = motionEvent.getAction() == 0 ? false : C(motionEvent);
            } else {
                if (tb.v != 0) {
                    if (motionEvent.getAction() == 0) {
                        boolean zE = tb.e(motionEvent.getX(), motionEvent.getY());
                        boolean zD = tb.d(motionEvent.getX(), motionEvent.getY());
                        if (zE || zD) {
                            if (zD) {
                                tb.w = 1;
                                tb.p = (int) motionEvent.getX();
                            } else if (zE) {
                                tb.w = 2;
                                tb.m = (int) motionEvent.getY();
                            }
                            tb.g(2);
                        }
                    } else if (motionEvent.getAction() == 1 && tb.v == 2) {
                        tb.m = 0.0f;
                        tb.p = 0.0f;
                        tb.g(1);
                        tb.w = 0;
                    } else if (motionEvent.getAction() == 2 && tb.v == 2) {
                        tb.h();
                        int i4 = tb.w;
                        int i5 = tb.b;
                        if (i4 == 1) {
                            float x = motionEvent.getX();
                            int[] iArr = tb.y;
                            iArr[0] = i5;
                            int i6 = tb.q - i5;
                            iArr[1] = i6;
                            float fMax = Math.max(i5, Math.min(i6, x));
                            if (Math.abs(tb.o - fMax) >= 2.0f) {
                                int iF = Tb.f(tb.p, fMax, iArr, tb.s.computeHorizontalScrollRange(), tb.s.computeHorizontalScrollOffset(), tb.q);
                                if (iF != 0) {
                                    tb.s.scrollBy(iF, 0);
                                }
                                tb.p = fMax;
                            }
                        }
                        if (tb.w == 2) {
                            float y = motionEvent.getY();
                            int[] iArr2 = tb.x;
                            iArr2[0] = i5;
                            int i7 = tb.r - i5;
                            iArr2[1] = i7;
                            float fMax2 = Math.max(i5, Math.min(i7, y));
                            if (Math.abs(tb.l - fMax2) >= 2.0f) {
                                int iF2 = Tb.f(tb.m, fMax2, iArr2, tb.s.computeVerticalScrollRange(), tb.s.computeVerticalScrollOffset(), tb.r);
                                if (iF2 != 0) {
                                    tb.s.scrollBy(0, iF2);
                                }
                                tb.m = fMax2;
                            }
                        }
                    }
                }
                int action = motionEvent.getAction();
                if (action == 3 || action == 1) {
                    this.r = null;
                }
                zC = true;
            }
            if (zC) {
                VelocityTracker velocityTracker = this.O;
                if (velocityTracker != null) {
                    velocityTracker.clear();
                }
                N().h(0);
                a0();
                j0(0);
                return true;
            }
            AbstractC0162el abstractC0162el = this.n;
            if (abstractC0162el != null) {
                boolean zD2 = abstractC0162el.d();
                boolean zE2 = this.n.e();
                if (this.O == null) {
                    this.O = VelocityTracker.obtain();
                }
                int actionMasked = motionEvent.getActionMasked();
                int actionIndex = motionEvent.getActionIndex();
                int[] iArr3 = this.p0;
                if (actionMasked == 0) {
                    iArr3[1] = 0;
                    iArr3[0] = 0;
                }
                MotionEvent motionEventObtain = MotionEvent.obtain(motionEvent);
                motionEventObtain.offsetLocation(iArr3[0], iArr3[1]);
                if (actionMasked != 0) {
                    if (actionMasked == 1) {
                        this.O.addMovement(motionEventObtain);
                        VelocityTracker velocityTracker2 = this.O;
                        int i8 = this.W;
                        velocityTracker2.computeCurrentVelocity(1000, i8);
                        float f = zD2 ? -this.O.getXVelocity(this.N) : 0.0f;
                        float f2 = zE2 ? -this.O.getYVelocity(this.N) : 0.0f;
                        if (!(f == 0.0f && f2 == 0.0f) && G((int) f, (int) f2, this.V, i8)) {
                            i = 0;
                        } else {
                            i = 0;
                            j0(0);
                        }
                        VelocityTracker velocityTracker3 = this.O;
                        if (velocityTracker3 != null) {
                            velocityTracker3.clear();
                        }
                        N().h(i);
                        a0();
                    } else if (actionMasked == 2) {
                        int iFindPointerIndex = motionEvent.findPointerIndex(this.N);
                        if (iFindPointerIndex < 0) {
                            Log.e("RecyclerView", "Error processing scroll; pointer index for id " + this.N + " not found. Did any MotionEvents get skipped?");
                            return false;
                        }
                        int x2 = (int) (motionEvent.getX(iFindPointerIndex) + 0.5f);
                        int y2 = (int) (motionEvent.getY(iFindPointerIndex) + 0.5f);
                        int iMax = this.R - x2;
                        int iMax2 = this.S - y2;
                        if (this.M != 1) {
                            int i9 = this.T;
                            if (zD2) {
                                iMax = iMax > 0 ? Math.max(0, iMax - i9) : Math.min(0, iMax + i9);
                                if (iMax != 0) {
                                    z = true;
                                } else {
                                    z = false;
                                }
                            } else {
                                z = false;
                            }
                            if (zE2) {
                                iMax2 = iMax2 > 0 ? Math.max(0, iMax2 - i9) : Math.min(0, iMax2 + i9);
                                if (iMax2 != 0) {
                                    z = true;
                                }
                            }
                            if (z) {
                                j0(1);
                            }
                        }
                        if (this.M == 1) {
                            int[] iArr4 = this.q0;
                            iArr4[0] = 0;
                            iArr4[1] = 0;
                            int iB0 = iMax - b0(iMax, motionEvent.getY());
                            int iC0 = iMax2 - c0(iMax2, motionEvent.getX());
                            boolean zC2 = N().c(zD2 ? iB0 : 0, zE2 ? iC0 : 0, 0, this.q0, this.o0);
                            int[] iArr5 = this.o0;
                            if (zC2) {
                                iB0 -= iArr4[0];
                                iC0 -= iArr4[1];
                                iArr3[0] = iArr3[0] + iArr5[0];
                                iArr3[1] = iArr3[1] + iArr5[1];
                                c = 1;
                                getParent().requestDisallowInterceptTouchEvent(true);
                            } else {
                                c = 1;
                            }
                            this.R = x2 - iArr5[0];
                            this.S = y2 - iArr5[c];
                            int i10 = zD2 ? iB0 : 0;
                            if (zE2) {
                                i2 = iC0;
                                i3 = 0;
                            } else {
                                i2 = 0;
                                i3 = 0;
                            }
                            if (e0(i10, i2, motionEvent, i3)) {
                                getParent().requestDisallowInterceptTouchEvent(true);
                            }
                            Hd hd = this.e0;
                            if (hd != null && (iB0 != 0 || iC0 != 0)) {
                                hd.a(this, iB0, iC0);
                            }
                        }
                    } else if (actionMasked == 3) {
                        VelocityTracker velocityTracker4 = this.O;
                        if (velocityTracker4 != null) {
                            velocityTracker4.clear();
                        }
                        N().h(0);
                        a0();
                        j0(0);
                    } else if (actionMasked == 5) {
                        this.N = motionEvent.getPointerId(actionIndex);
                        int x3 = (int) (motionEvent.getX(actionIndex) + 0.5f);
                        this.R = x3;
                        this.P = x3;
                        int y3 = (int) (motionEvent.getY(actionIndex) + 0.5f);
                        this.S = y3;
                        this.Q = y3;
                    } else if (actionMasked == 6) {
                        V(motionEvent);
                    }
                    motionEventObtain.recycle();
                    return true;
                }
                this.N = motionEvent.getPointerId(0);
                int x4 = (int) (motionEvent.getX() + 0.5f);
                this.R = x4;
                this.P = x4;
                int y4 = (int) (motionEvent.getY() + 0.5f);
                this.S = y4;
                this.Q = y4;
                o0(0);
                this.O.addMovement(motionEventObtain);
                motionEventObtain.recycle();
                return true;
            }
        }
        return false;
    }

    public final void p() {
        if (!this.u || this.C) {
            Trace.beginSection("RV FullInvalidate");
            r();
            Trace.endSection();
            return;
        }
        if (this.e.h()) {
            T0 t0 = this.e;
            int i = t0.a;
            if ((i & 4) == 0 || (i & 11) != 0) {
                if (t0.h()) {
                    Trace.beginSection("RV FullInvalidate");
                    r();
                    Trace.endSection();
                    return;
                }
                return;
            }
            Trace.beginSection("RV PartialInvalidate");
            n0();
            T();
            this.e.n();
            if (!this.w) {
                int iE = this.f.e();
                for (int i2 = 0; i2 < iE; i2++) {
                    AbstractC0470ol abstractC0470olK = K(this.f.d(i2));
                    if (abstractC0470olK != null && !abstractC0470olK.o() && abstractC0470olK.k()) {
                        r();
                    }
                }
                this.e.c();
            }
            p0(true);
            U(true);
            Trace.endSection();
        }
    }

    public final void p0(boolean z) {
        if (this.v < 1) {
            this.v = 1;
        }
        if (!z && !this.x) {
            this.w = false;
        }
        if (this.v == 1) {
            if (z && this.w && !this.x && this.n != null && this.m != null) {
                r();
            }
            if (!this.x) {
                this.w = false;
            }
        }
        this.v--;
    }

    public final void q(int i, int i2) {
        int paddingRight = getPaddingRight() + getPaddingLeft();
        WeakHashMap weakHashMap = Hs.a;
        setMeasuredDimension(AbstractC0162el.g(i, paddingRight, getMinimumWidth()), AbstractC0162el.g(i2, getPaddingBottom() + getPaddingTop(), getMinimumHeight()));
    }

    public final void q0() {
        Jg jg;
        j0(0);
        RunnableC0439nl runnableC0439nl = this.d0;
        runnableC0439nl.g.removeCallbacks(runnableC0439nl);
        runnableC0439nl.c.abortAnimation();
        AbstractC0162el abstractC0162el = this.n;
        if (abstractC0162el == null || (jg = abstractC0162el.e) == null) {
            return;
        }
        jg.i();
    }

    /* JADX WARN: Code duplicated, block: B:166:0x0356  */
    /* JADX WARN: Code duplicated, block: B:185:0x039a  */
    /* JADX WARN: Code duplicated, block: B:187:0x039d  */
    /* JADX WARN: Code duplicated, block: B:193:0x03b2  */
    /* JADX WARN: Code duplicated, block: B:195:0x03ba  */
    /* JADX WARN: Code duplicated, block: B:197:0x03be  */
    /* JADX WARN: Code duplicated, block: B:200:0x03c6  */
    /* JADX WARN: Code duplicated, block: B:203:0x03cd  */
    /* JADX WARN: Code duplicated, block: B:206:0x03d7 A[LOOP:4: B:199:0x03c4->B:206:0x03d7, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:209:0x03e4  */
    /* JADX WARN: Code duplicated, block: B:212:0x03eb  */
    /* JADX WARN: Code duplicated, block: B:215:0x03f5 A[LOOP:5: B:208:0x03e2->B:215:0x03f5, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:217:0x03fa  */
    /* JADX WARN: Code duplicated, block: B:247:0x03da A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:248:0x03da A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:249:0x03d5 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:251:0x03f8 A[EDGE_INSN: B:251:0x03f8->B:216:0x03f8 BREAK  A[LOOP:5: B:208:0x03e2->B:215:0x03f5], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:252:0x03f3 A[SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v12 */
    /* JADX WARN: Type inference failed for: r3v19 */
    /* JADX WARN: Type inference failed for: r3v20, types: [int] */
    /* JADX WARN: Type inference failed for: r3v23 */
    /* JADX WARN: Type inference failed for: r3v26 */
    /* JADX WARN: Type inference failed for: r3v27 */
    /* JADX WARN: Type inference failed for: r3v28 */
    /* JADX WARN: Type inference failed for: r3v29 */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public final void r() {
        boolean z;
        long j;
        AbstractC0470ol abstractC0470ol;
        int i;
        int iB;
        int i2;
        int iMin;
        AbstractC0470ol abstractC0470olF;
        View view;
        AbstractC0470ol abstractC0470olF2;
        View view2;
        int i3;
        View viewFindViewById;
        ArrayList arrayList;
        View view3;
        boolean z2;
        Yi yi;
        ?? r3;
        RecyclerView recyclerView;
        boolean zB;
        boolean z3;
        if (this.m == null) {
            Log.w("RecyclerView", "No adapter attached; skipping layout");
            return;
        }
        if (this.n == null) {
            Log.e("RecyclerView", "No layout manager attached; skipping layout");
            return;
        }
        C0408ml c0408ml = this.g0;
        boolean z4 = false;
        c0408ml.i = false;
        boolean z5 = true;
        boolean z6 = this.t0 && !(this.u0 == getWidth() && this.v0 == getHeight());
        this.u0 = 0;
        this.v0 = 0;
        this.t0 = false;
        if (c0408ml.d == 1) {
            s();
            this.n.s0(this);
            t();
        } else {
            T0 t0 = this.e;
            if ((((ArrayList) t0.d).isEmpty() || ((ArrayList) t0.c).isEmpty()) && !z6 && this.n.n == getWidth() && this.n.o == getHeight()) {
                this.n.s0(this);
            } else {
                this.n.s0(this);
                t();
            }
        }
        c0408ml.a(4);
        n0();
        T();
        c0408ml.d = 1;
        boolean z7 = c0408ml.j;
        C0285il c0285il = this.c;
        R2 r2 = this.g;
        if (z7) {
            int iE = this.f.e() - 1;
            while (iE >= 0) {
                AbstractC0470ol abstractC0470olK = K(this.f.d(iE));
                if (abstractC0470olK.o()) {
                    z3 = z5;
                } else {
                    long jI = I(abstractC0470olK);
                    this.L.getClass();
                    Yi yi2 = new Yi();
                    yi2.a(abstractC0470olK);
                    AbstractC0470ol abstractC0470ol2 = (AbstractC0470ol) ((C0620th) r2.c).b(jI);
                    if (abstractC0470ol2 == null || abstractC0470ol2.o()) {
                        z3 = z5;
                        r2.d(abstractC0470olK, yi2);
                    } else {
                        C0104co c0104co = (C0104co) r2.b;
                        z3 = z5;
                        Ns ns = (Ns) c0104co.get(abstractC0470ol2);
                        boolean z8 = (ns == null || (ns.a & 1) == 0) ? false : z3;
                        Ns ns2 = (Ns) c0104co.get(abstractC0470olK);
                        boolean z9 = (ns2 == null || (ns2.a & 1) == 0) ? false : z3;
                        if (z8 && abstractC0470ol2 == abstractC0470olK) {
                            r2.d(abstractC0470olK, yi2);
                        } else {
                            Yi yiR = r2.R(abstractC0470ol2, 4);
                            r2.d(abstractC0470olK, yi2);
                            Yi yiR2 = r2.R(abstractC0470olK, 8);
                            if (yiR == null) {
                                int iE2 = this.f.e();
                                for (int i4 = 0; i4 < iE2; i4++) {
                                    AbstractC0470ol abstractC0470olK2 = K(this.f.d(i4));
                                    if (abstractC0470olK2 != abstractC0470olK && I(abstractC0470olK2) == jI) {
                                        Yk yk = this.m;
                                        if (yk == null || !yk.b) {
                                            throw new IllegalStateException("Two different ViewHolders have the same change ID. This might happen due to inconsistent Adapter update events or if the LayoutManager lays out the same View multiple times.\n ViewHolder 1:" + abstractC0470olK2 + " \n View Holder 2:" + abstractC0470olK + z());
                                        }
                                        throw new IllegalStateException("Two different ViewHolders have the same stable ID. Stable IDs in your adapter MUST BE unique and SHOULD NOT change.\n ViewHolder 1:" + abstractC0470olK2 + " \n View Holder 2:" + abstractC0470olK + z());
                                    }
                                }
                                Log.e("RecyclerView", "Problem while matching changed view holders with the newones. The pre-layout information for the change holder " + abstractC0470ol2 + " cannot be found but it is necessary for " + abstractC0470olK + z());
                            } else {
                                abstractC0470ol2.n(false);
                                if (z8) {
                                    h(abstractC0470ol2);
                                }
                                if (abstractC0470ol2 != abstractC0470olK) {
                                    if (z9) {
                                        h(abstractC0470olK);
                                    }
                                    abstractC0470ol2.h = abstractC0470olK;
                                    h(abstractC0470ol2);
                                    c0285il.l(abstractC0470ol2);
                                    abstractC0470olK.n(false);
                                    abstractC0470olK.i = abstractC0470ol2;
                                }
                                if (this.L.a(abstractC0470ol2, abstractC0470olK, yiR, yiR2)) {
                                    W();
                                }
                            }
                        }
                    }
                }
                iE--;
                z5 = z3;
            }
            z = z5;
            C0104co c0104co2 = (C0104co) r2.b;
            int i5 = c0104co2.c - 1;
            while (i5 >= 0) {
                AbstractC0470ol abstractC0470ol3 = (AbstractC0470ol) c0104co2.f(i5);
                Ns ns3 = (Ns) c0104co2.g(i5);
                int i6 = ns3.a;
                int i7 = i6 & 3;
                Xk xk = this.x0;
                if (i7 == 3) {
                    RecyclerView recyclerView2 = xk.a;
                    recyclerView2.n.l0(abstractC0470ol3.a, recyclerView2.c);
                    r3 = z4;
                } else if ((i6 & 1) != 0) {
                    Yi yi3 = ns3.b;
                    if (yi3 == null) {
                        RecyclerView recyclerView3 = xk.a;
                        recyclerView3.n.l0(abstractC0470ol3.a, recyclerView3.c);
                        r3 = z4;
                    } else {
                        xk.h(abstractC0470ol3, yi3, ns3.c);
                        r3 = z4;
                    }
                } else if ((i6 & 14) == 14) {
                    xk.g(abstractC0470ol3, ns3.b, ns3.c);
                    r3 = z4;
                } else {
                    if ((i6 & 12) == 12) {
                        Yi yi4 = ns3.b;
                        Yi yi5 = ns3.c;
                        xk.getClass();
                        abstractC0470ol3.n(z4);
                        RecyclerView recyclerView4 = xk.a;
                        if (!recyclerView4.C) {
                            C0458o9 c0458o9 = recyclerView4.L;
                            c0458o9.getClass();
                            int i8 = yi4.a;
                            int i9 = yi5.a;
                            if (i8 == i9 && yi4.b == yi5.b) {
                                c0458o9.e(abstractC0470ol3);
                                recyclerView = recyclerView4;
                                zB = false;
                            } else {
                                recyclerView = recyclerView4;
                                zB = c0458o9.b(abstractC0470ol3, i8, yi4.b, i9, yi5.b);
                            }
                            if (zB) {
                                recyclerView.W();
                            }
                        } else if (recyclerView4.L.a(abstractC0470ol3, abstractC0470ol3, yi4, yi5)) {
                            recyclerView4.W();
                        }
                        r3 = 0;
                    } else {
                        if ((i6 & 4) != 0) {
                            yi = null;
                            xk.h(abstractC0470ol3, ns3.b, null);
                        } else {
                            yi = null;
                            if ((i6 & 8) != 0) {
                                xk.g(abstractC0470ol3, ns3.b, ns3.c);
                            }
                        }
                        r3 = 0;
                    }
                    ns3.a = r3;
                    ns3.b = yi;
                    ns3.c = yi;
                    Ns.d.c(ns3);
                    i5--;
                    z4 = false;
                }
                yi = null;
                ns3.a = r3;
                ns3.b = yi;
                ns3.c = yi;
                Ns.d.c(ns3);
                i5--;
                z4 = false;
            }
        } else {
            z = true;
        }
        View view4 = null;
        this.n.k0(c0285il);
        c0408ml.b = c0408ml.e;
        this.C = false;
        this.D = false;
        c0408ml.j = false;
        c0408ml.k = false;
        this.n.f = false;
        ArrayList arrayList2 = c0285il.b;
        if (arrayList2 != null) {
            arrayList2.clear();
        }
        AbstractC0162el abstractC0162el = this.n;
        if (abstractC0162el.k) {
            abstractC0162el.j = 0;
            abstractC0162el.k = false;
            c0285il.m();
        }
        this.n.e0(c0408ml);
        U(z);
        p0(false);
        ((C0104co) r2.b).clear();
        ((C0620th) r2.c).a();
        int[] iArr = this.m0;
        int i10 = iArr[0];
        int i11 = iArr[1];
        D(iArr);
        if ((iArr[0] == i10 && iArr[1] == i11) ? false : true) {
            u(0, 0);
        }
        if (this.c0 && this.m != null && hasFocus() && getDescendantFocusability() != 393216 && (getDescendantFocusability() != 131072 || !isFocused())) {
            if (isFocused()) {
                j = c0408ml.m;
                if (j == -1) {
                    abstractC0470ol = null;
                } else {
                    abstractC0470ol = null;
                }
                if (abstractC0470ol != null) {
                    arrayList = this.f.c;
                    view3 = abstractC0470ol.a;
                    if (!arrayList.contains(view3)) {
                        if (this.f.e() > 0) {
                            int i12 = c0408ml.l;
                            if (i12 != -1) {
                            }
                            iB = c0408ml.b();
                            i2 = i;
                            while (true) {
                                if (i2 < iB) {
                                    abstractC0470olF2 = F(i2);
                                    if (abstractC0470olF2 != null) {
                                        view2 = abstractC0470olF2.a;
                                        if (view2.hasFocusable()) {
                                            view4 = view2;
                                        } else {
                                            i2++;
                                        }
                                    }
                                }
                                for (iMin = Math.min(iB, i) - 1; iMin >= 0; iMin--) {
                                    abstractC0470olF = F(iMin);
                                    if (abstractC0470olF == null) {
                                        break;
                                        break;
                                    }
                                    view = abstractC0470olF.a;
                                    if (view.hasFocusable()) {
                                        view4 = view;
                                        break;
                                    }
                                }
                            }
                        }
                    } else if (this.f.e() > 0) {
                        int i13 = c0408ml.l;
                        if (i13 != -1) {
                        }
                        iB = c0408ml.b();
                        i2 = i;
                        while (true) {
                            if (i2 < iB) {
                                abstractC0470olF2 = F(i2);
                                if (abstractC0470olF2 != null) {
                                    view2 = abstractC0470olF2.a;
                                    if (view2.hasFocusable()) {
                                        view4 = view2;
                                    } else {
                                        i2++;
                                    }
                                }
                            }
                            while (iMin >= 0) {
                                abstractC0470olF = F(iMin);
                                if (abstractC0470olF == null) {
                                    break;
                                    break;
                                }
                                view = abstractC0470olF.a;
                                if (view.hasFocusable()) {
                                    view4 = view;
                                    break;
                                }
                            }
                        }
                    }
                } else if (this.f.e() > 0) {
                    int i14 = c0408ml.l;
                    if (i14 != -1) {
                    }
                    iB = c0408ml.b();
                    i2 = i;
                    while (true) {
                        if (i2 < iB) {
                            abstractC0470olF2 = F(i2);
                            if (abstractC0470olF2 != null) {
                                view2 = abstractC0470olF2.a;
                                if (view2.hasFocusable()) {
                                    view4 = view2;
                                } else {
                                    i2++;
                                }
                            }
                        }
                        while (iMin >= 0) {
                            abstractC0470olF = F(iMin);
                            if (abstractC0470olF == null) {
                                break;
                                break;
                            }
                            view = abstractC0470olF.a;
                            if (view.hasFocusable()) {
                                view4 = view;
                                break;
                            }
                        }
                    }
                }
                if (view4 != null) {
                    i3 = c0408ml.n;
                    if (i3 != -1) {
                        view4 = viewFindViewById;
                    }
                    view4.requestFocus();
                }
            } else if (this.f.c.contains(getFocusedChild())) {
                j = c0408ml.m;
                if (j == -1 && (z2 = this.m.b) && z2) {
                    int iH = this.f.h();
                    abstractC0470ol = null;
                    for (int i15 = 0; i15 < iH; i15++) {
                        AbstractC0470ol abstractC0470olK3 = K(this.f.g(i15));
                        if (abstractC0470olK3 != null && !abstractC0470olK3.h() && abstractC0470olK3.e == j) {
                            if (!this.f.c.contains(abstractC0470olK3.a)) {
                                abstractC0470ol = abstractC0470olK3;
                                break;
                            }
                            abstractC0470ol = abstractC0470olK3;
                        }
                    }
                } else {
                    abstractC0470ol = null;
                }
                if (abstractC0470ol != null) {
                    arrayList = this.f.c;
                    view3 = abstractC0470ol.a;
                    if (!arrayList.contains(view3) && view3.hasFocusable()) {
                        view4 = view3;
                    } else if (this.f.e() > 0) {
                        int i16 = c0408ml.l;
                        i = i16 != -1 ? i16 : 0;
                        iB = c0408ml.b();
                        i2 = i;
                        while (true) {
                            if (i2 < iB) {
                                abstractC0470olF2 = F(i2);
                                if (abstractC0470olF2 != null) {
                                    view2 = abstractC0470olF2.a;
                                    if (view2.hasFocusable()) {
                                        view4 = view2;
                                    } else {
                                        i2++;
                                    }
                                }
                            }
                            while (iMin >= 0) {
                                abstractC0470olF = F(iMin);
                                if (abstractC0470olF == null) {
                                    break;
                                }
                                view = abstractC0470olF.a;
                                if (view.hasFocusable()) {
                                    view4 = view;
                                    break;
                                }
                            }
                        }
                    }
                } else if (this.f.e() > 0) {
                    int i17 = c0408ml.l;
                    if (i17 != -1) {
                    }
                    iB = c0408ml.b();
                    i2 = i;
                    while (true) {
                        if (i2 < iB) {
                            abstractC0470olF2 = F(i2);
                            if (abstractC0470olF2 != null) {
                                view2 = abstractC0470olF2.a;
                                if (view2.hasFocusable()) {
                                    view4 = view2;
                                } else {
                                    i2++;
                                }
                            }
                        }
                        while (iMin >= 0) {
                            abstractC0470olF = F(iMin);
                            if (abstractC0470olF == null) {
                                break;
                                break;
                            }
                            view = abstractC0470olF.a;
                            if (view.hasFocusable()) {
                                view4 = view;
                                break;
                            }
                        }
                    }
                }
                if (view4 != null) {
                    i3 = c0408ml.n;
                    if (i3 != -1 && (viewFindViewById = view4.findViewById(i3)) != null && viewFindViewById.isFocusable()) {
                        view4 = viewFindViewById;
                    }
                    view4.requestFocus();
                }
            }
        }
        c0408ml.m = -1L;
        c0408ml.l = -1;
        c0408ml.n = -1;
    }

    @Override // android.view.ViewGroup
    public final void removeDetachedView(View view, boolean z) {
        AbstractC0470ol abstractC0470olK = K(view);
        if (abstractC0470olK != null) {
            if (abstractC0470olK.j()) {
                abstractC0470olK.j &= -257;
            } else if (!abstractC0470olK.o()) {
                throw new IllegalArgumentException("Called removeDetachedView with a view which is not flagged as tmp detached." + abstractC0470olK + z());
            }
        }
        view.clearAnimation();
        AbstractC0470ol abstractC0470olK2 = K(view);
        Yk yk = this.m;
        if (yk != null && abstractC0470olK2 != null) {
            yk.h(abstractC0470olK2);
        }
        super.removeDetachedView(view, z);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void requestChildFocus(View view, View view2) {
        Jg jg = this.n.e;
        if ((jg == null || !jg.e) && !P() && view2 != null) {
            d0(view, view2);
        }
        super.requestChildFocus(view, view2);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final boolean requestChildRectangleOnScreen(View view, Rect rect, boolean z) {
        return this.n.n0(this, view, rect, z, false);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void requestDisallowInterceptTouchEvent(boolean z) {
        ArrayList arrayList = this.q;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            ((Tb) arrayList.get(i)).getClass();
        }
        super.requestDisallowInterceptTouchEvent(z);
    }

    @Override // android.view.View, android.view.ViewParent
    public final void requestLayout() {
        if (this.v != 0 || this.x) {
            this.w = true;
        } else {
            super.requestLayout();
        }
    }

    /* JADX WARN: Code duplicated, block: B:24:0x0062  */
    public final void s() {
        int iH;
        Ns ns;
        View viewB;
        C0408ml c0408ml = this.g0;
        c0408ml.a(1);
        A(c0408ml);
        c0408ml.i = false;
        n0();
        R2 r2 = this.g;
        ((C0104co) r2.b).clear();
        C0620th c0620th = (C0620th) r2.c;
        c0620th.a();
        T();
        X();
        AbstractC0470ol abstractC0470olJ = null;
        View focusedChild = (this.c0 && hasFocus() && this.m != null) ? getFocusedChild() : null;
        if (focusedChild != null && (viewB = B(focusedChild)) != null) {
            abstractC0470olJ = J(viewB);
        }
        if (abstractC0470olJ == null) {
            c0408ml.m = -1L;
            c0408ml.l = -1;
            c0408ml.n = -1;
        } else {
            c0408ml.m = this.m.b ? abstractC0470olJ.e : -1L;
            if (this.C) {
                iH = -1;
            } else if (abstractC0470olJ.h()) {
                iH = abstractC0470olJ.d;
            } else {
                RecyclerView recyclerView = abstractC0470olJ.r;
                if (recyclerView == null) {
                    iH = -1;
                } else {
                    iH = recyclerView.H(abstractC0470olJ);
                }
            }
            c0408ml.l = iH;
            View focusedChild2 = abstractC0470olJ.a;
            int id = focusedChild2.getId();
            while (!focusedChild2.isFocused() && (focusedChild2 instanceof ViewGroup) && focusedChild2.hasFocus()) {
                focusedChild2 = ((ViewGroup) focusedChild2).getFocusedChild();
                if (focusedChild2.getId() != -1) {
                    id = focusedChild2.getId();
                }
            }
            c0408ml.n = id;
        }
        c0408ml.h = c0408ml.j && this.j0;
        this.j0 = false;
        this.i0 = false;
        c0408ml.g = c0408ml.k;
        c0408ml.e = this.m.a();
        D(this.m0);
        boolean z = c0408ml.j;
        C0104co c0104co = (C0104co) r2.b;
        if (z) {
            int iE = this.f.e();
            for (int i = 0; i < iE; i++) {
                AbstractC0470ol abstractC0470olK = K(this.f.d(i));
                if (!abstractC0470olK.o() && (!abstractC0470olK.f() || this.m.b)) {
                    C0458o9 c0458o9 = this.L;
                    C0458o9.c(abstractC0470olK);
                    abstractC0470olK.c();
                    c0458o9.getClass();
                    Yi yi = new Yi();
                    yi.a(abstractC0470olK);
                    Ns nsA = (Ns) c0104co.get(abstractC0470olK);
                    if (nsA == null) {
                        nsA = Ns.a();
                        c0104co.put(abstractC0470olK, nsA);
                    }
                    nsA.b = yi;
                    nsA.a |= 4;
                    if (c0408ml.h && abstractC0470olK.k() && !abstractC0470olK.h() && !abstractC0470olK.o() && !abstractC0470olK.f()) {
                        c0620th.d(I(abstractC0470olK), abstractC0470olK);
                    }
                }
            }
        }
        if (c0408ml.k) {
            int iH2 = this.f.h();
            for (int i2 = 0; i2 < iH2; i2++) {
                AbstractC0470ol abstractC0470olK2 = K(this.f.g(i2));
                if (!abstractC0470olK2.o() && abstractC0470olK2.d == -1) {
                    abstractC0470olK2.d = abstractC0470olK2.c;
                }
            }
            boolean z2 = c0408ml.f;
            c0408ml.f = false;
            this.n.d0(this.c, c0408ml);
            c0408ml.f = z2;
            for (int i3 = 0; i3 < this.f.e(); i3++) {
                AbstractC0470ol abstractC0470olK3 = K(this.f.d(i3));
                if (!abstractC0470olK3.o() && ((ns = (Ns) c0104co.get(abstractC0470olK3)) == null || (ns.a & 4) == 0)) {
                    C0458o9.c(abstractC0470olK3);
                    boolean z3 = (abstractC0470olK3.j & 8192) != 0;
                    C0458o9 c0458o10 = this.L;
                    abstractC0470olK3.c();
                    c0458o10.getClass();
                    Yi yi2 = new Yi();
                    yi2.a(abstractC0470olK3);
                    if (z3) {
                        Z(abstractC0470olK3, yi2);
                    } else {
                        Ns nsA2 = (Ns) c0104co.get(abstractC0470olK3);
                        if (nsA2 == null) {
                            nsA2 = Ns.a();
                            c0104co.put(abstractC0470olK3, nsA2);
                        }
                        nsA2.a |= 2;
                        nsA2.b = yi2;
                    }
                }
            }
            m();
        } else {
            m();
        }
        U(true);
        p0(false);
        c0408ml.d = 2;
    }

    @Override // android.view.View
    public final void scrollBy(int i, int i2) {
        AbstractC0162el abstractC0162el = this.n;
        if (abstractC0162el == null) {
            Log.e("RecyclerView", "Cannot scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
            return;
        }
        if (this.x) {
            return;
        }
        boolean zD = abstractC0162el.d();
        boolean zE = this.n.e();
        if (zD || zE) {
            if (!zD) {
                i = 0;
            }
            if (!zE) {
                i2 = 0;
            }
            e0(i, i2, null, 0);
        }
    }

    @Override // android.view.View
    public final void scrollTo(int i, int i2) {
        Log.w("RecyclerView", "RecyclerView does not support scrolling to an absolute position. Use scrollToPosition instead");
    }

    @Override // android.view.View, android.view.accessibility.AccessibilityEventSource
    public final void sendAccessibilityEventUnchecked(AccessibilityEvent accessibilityEvent) {
        if (!P()) {
            super.sendAccessibilityEventUnchecked(accessibilityEvent);
        } else {
            int contentChangeTypes = accessibilityEvent != null ? accessibilityEvent.getContentChangeTypes() : 0;
            this.z |= contentChangeTypes != 0 ? contentChangeTypes : 0;
        }
    }

    @Override // android.view.ViewGroup
    public final void setClipToPadding(boolean z) {
        if (z != this.h) {
            this.K = null;
            this.I = null;
            this.J = null;
            this.H = null;
        }
        this.h = z;
        super.setClipToPadding(z);
        if (this.u) {
            requestLayout();
        }
    }

    @Override // android.view.ViewGroup
    public final void setLayoutTransition(LayoutTransition layoutTransition) {
        if (layoutTransition != null) {
            throw new IllegalArgumentException("Providing a LayoutTransition into RecyclerView is not supported. Please use setItemAnimator() instead for animating changes to the items in this RecyclerView");
        }
        super.setLayoutTransition(null);
    }

    @Override // android.view.View
    public final void setNestedScrollingEnabled(boolean z) {
        Vi viN = N();
        if (viN.d) {
            WeakHashMap weakHashMap = Hs.a;
            AbstractC0817zs.m(viN.c);
        }
        viN.d = z;
    }

    @Override // android.view.View
    public final boolean startNestedScroll(int i) {
        return N().g(i, 0);
    }

    @Override // android.view.View
    public final void stopNestedScroll() {
        N().h(0);
    }

    @Override // android.view.ViewGroup
    public final void suppressLayout(boolean z) {
        if (z != this.x) {
            k("Do not suppressLayout in layout or scroll");
            if (z) {
                long jUptimeMillis = SystemClock.uptimeMillis();
                onTouchEvent(MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 3, 0.0f, 0.0f, 0));
                this.x = true;
                this.y = true;
                q0();
                return;
            }
            this.x = false;
            if (this.w && this.n != null && this.m != null) {
                requestLayout();
            }
            this.w = false;
        }
    }

    public final void t() {
        n0();
        T();
        C0408ml c0408ml = this.g0;
        c0408ml.a(6);
        this.e.d();
        c0408ml.e = this.m.a();
        c0408ml.c = 0;
        if (this.d != null) {
            Yk yk = this.m;
            int iJ = AbstractC0535qo.j(yk.c);
            if (iJ == 1 ? yk.a() > 0 : iJ != 2) {
                Parcelable parcelable = this.d.c;
                if (parcelable != null) {
                    this.n.f0(parcelable);
                }
                this.d = null;
            }
        }
        c0408ml.g = false;
        this.n.d0(this.c, c0408ml);
        c0408ml.f = false;
        c0408ml.j = c0408ml.j && this.L != null;
        c0408ml.d = 4;
        U(true);
        p0(false);
    }

    public final void u(int i, int i2) {
        this.F++;
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        onScrollChanged(scrollX, scrollY, scrollX - i, scrollY - i2);
        ArrayList arrayList = this.h0;
        if (arrayList != null) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                ((AbstractC0224gl) this.h0.get(size)).b(this, i, i2);
            }
        }
        this.F--;
    }

    public final void v() {
        if (this.K != null) {
            return;
        }
        EdgeEffect edgeEffectA = this.G.a(this, 3);
        this.K = edgeEffectA;
        if (this.h) {
            edgeEffectA.setSize((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight(), (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom());
        } else {
            edgeEffectA.setSize(getMeasuredWidth(), getMeasuredHeight());
        }
    }

    public final void w() {
        if (this.H != null) {
            return;
        }
        EdgeEffect edgeEffectA = this.G.a(this, 0);
        this.H = edgeEffectA;
        if (this.h) {
            edgeEffectA.setSize((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom(), (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight());
        } else {
            edgeEffectA.setSize(getMeasuredHeight(), getMeasuredWidth());
        }
    }

    public final void x() {
        if (this.J != null) {
            return;
        }
        EdgeEffect edgeEffectA = this.G.a(this, 2);
        this.J = edgeEffectA;
        if (this.h) {
            edgeEffectA.setSize((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom(), (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight());
        } else {
            edgeEffectA.setSize(getMeasuredHeight(), getMeasuredWidth());
        }
    }

    public final void y() {
        if (this.I != null) {
            return;
        }
        EdgeEffect edgeEffectA = this.G.a(this, 1);
        this.I = edgeEffectA;
        if (this.h) {
            edgeEffectA.setSize((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight(), (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom());
        } else {
            edgeEffectA.setSize(getMeasuredWidth(), getMeasuredHeight());
        }
    }

    public final String z() {
        return " " + super.toString() + ", adapter:" + this.m + ", layout:" + this.n + ", context:" + getContext();
    }

    public RecyclerView(Context context, AttributeSet attributeSet, int i) {
        float fA;
        int i2;
        Constructor constructor;
        Object[] objArr;
        super(context, attributeSet, i);
        int i3 = 1;
        this.b = new U3(this, 1);
        this.c = new C0285il(this);
        this.g = new R2(24);
        this.i = new Vk(this, 0);
        this.j = new Rect();
        this.k = new Rect();
        this.l = new RectF();
        this.o = new ArrayList();
        this.p = new ArrayList();
        this.q = new ArrayList();
        this.v = 0;
        this.C = false;
        this.D = false;
        this.E = 0;
        this.F = 0;
        this.G = F0;
        C0458o9 c0458o9 = new C0458o9();
        c0458o9.a = null;
        c0458o9.b = new ArrayList();
        c0458o9.c = 120L;
        c0458o9.d = 120L;
        c0458o9.e = 250L;
        c0458o9.f = 250L;
        c0458o9.g = true;
        c0458o9.h = new ArrayList();
        c0458o9.i = new ArrayList();
        c0458o9.j = new ArrayList();
        c0458o9.k = new ArrayList();
        c0458o9.l = new ArrayList();
        c0458o9.m = new ArrayList();
        c0458o9.n = new ArrayList();
        c0458o9.o = new ArrayList();
        c0458o9.p = new ArrayList();
        c0458o9.q = new ArrayList();
        c0458o9.r = new ArrayList();
        this.L = c0458o9;
        this.M = 0;
        this.N = -1;
        this.a0 = Float.MIN_VALUE;
        this.b0 = Float.MIN_VALUE;
        this.c0 = true;
        this.d0 = new RunnableC0439nl(this);
        this.f0 = C0 ? new Fd() : null;
        C0408ml c0408ml = new C0408ml();
        c0408ml.a = -1;
        c0408ml.b = 0;
        c0408ml.c = 0;
        c0408ml.d = 1;
        c0408ml.e = 0;
        c0408ml.f = false;
        c0408ml.g = false;
        c0408ml.h = false;
        c0408ml.i = false;
        c0408ml.j = false;
        c0408ml.k = false;
        this.g0 = c0408ml;
        this.i0 = false;
        this.j0 = false;
        Xk xk = new Xk(this);
        this.k0 = false;
        this.m0 = new int[2];
        this.o0 = new int[2];
        this.p0 = new int[2];
        this.q0 = new int[2];
        this.r0 = new ArrayList();
        this.s0 = new Vk(this, i3);
        this.u0 = 0;
        this.v0 = 0;
        this.x0 = new Xk(this);
        this.y0 = new V9(getContext(), new Xk(this));
        setScrollContainer(true);
        setFocusableInTouchMode(true);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.T = viewConfiguration.getScaledTouchSlop();
        int i4 = Build.VERSION.SDK_INT;
        if (i4 >= 26) {
            Method method = Is.a;
            fA = AbstractC0190fi.a(viewConfiguration);
        } else {
            fA = Is.a(viewConfiguration, context);
        }
        this.a0 = fA;
        this.b0 = i4 >= 26 ? AbstractC0190fi.b(viewConfiguration) : Is.a(viewConfiguration, context);
        this.V = viewConfiguration.getScaledMinimumFlingVelocity();
        this.W = viewConfiguration.getScaledMaximumFlingVelocity();
        this.a = context.getResources().getDisplayMetrics().density * 160.0f * 386.0878f * 0.84f;
        setWillNotDraw(getOverScrollMode() == 2);
        this.L.a = xk;
        this.e = new T0(new Xk(this));
        this.f = new W5(new Xk(this));
        WeakHashMap weakHashMap = Hs.a;
        if ((i4 >= 26 ? Bs.a(this) : 0) == 0 && i4 >= 26) {
            Bs.b(this, 8);
        }
        if (getImportantForAccessibility() == 0) {
            setImportantForAccessibility(1);
        }
        this.B = (AccessibilityManager) getContext().getSystemService("accessibility");
        C0532ql c0532ql = new C0532ql(this);
        this.l0 = c0532ql;
        Hs.m(this, c0532ql);
        int[] iArr = Kk.a;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, iArr, i, 0);
        Hs.l(this, context, iArr, attributeSet, typedArrayObtainStyledAttributes, i);
        String string = typedArrayObtainStyledAttributes.getString(8);
        if (typedArrayObtainStyledAttributes.getInt(2, -1) == -1) {
            setDescendantFocusability(262144);
        }
        this.h = typedArrayObtainStyledAttributes.getBoolean(1, true);
        if (typedArrayObtainStyledAttributes.getBoolean(3, false)) {
            StateListDrawable stateListDrawable = (StateListDrawable) typedArrayObtainStyledAttributes.getDrawable(6);
            Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(7);
            StateListDrawable stateListDrawable2 = (StateListDrawable) typedArrayObtainStyledAttributes.getDrawable(4);
            Drawable drawable2 = typedArrayObtainStyledAttributes.getDrawable(5);
            if (stateListDrawable == null || drawable == null || stateListDrawable2 == null || drawable2 == null) {
                throw new IllegalArgumentException("Trying to set fast scroller without both required drawables." + z());
            }
            Resources resources = getContext().getResources();
            i2 = 4;
            new Tb(this, stateListDrawable, drawable, stateListDrawable2, drawable2, resources.getDimensionPixelSize(moe.shizuku.privileged.api.R.dimen._0_resource_name_obfuscated_res_0x7f0700a0), resources.getDimensionPixelSize(moe.shizuku.privileged.api.R.dimen._0_resource_name_obfuscated_res_0x7f0700a2), resources.getDimensionPixelOffset(moe.shizuku.privileged.api.R.dimen._0_resource_name_obfuscated_res_0x7f0700a1));
        } else {
            i2 = 4;
        }
        typedArrayObtainStyledAttributes.recycle();
        this.w0 = context.getPackageManager().hasSystemFeature("android.hardware.rotaryencoder.lowres");
        if (string != null) {
            String strTrim = string.trim();
            if (!strTrim.isEmpty()) {
                if (strTrim.charAt(0) == '.') {
                    strTrim = context.getPackageName() + strTrim;
                } else if (!strTrim.contains(".")) {
                    strTrim = RecyclerView.class.getPackage().getName() + '.' + strTrim;
                }
                String str = strTrim;
                try {
                    Class<? extends U> clsAsSubclass = Class.forName(str, false, isInEditMode() ? getClass().getClassLoader() : context.getClassLoader()).asSubclass(AbstractC0162el.class);
                    try {
                        constructor = clsAsSubclass.getConstructor(D0);
                        Object[] objArr2 = new Object[i2];
                        objArr2[0] = context;
                        objArr2[i3] = attributeSet;
                        objArr2[2] = Integer.valueOf(i);
                        objArr2[3] = 0;
                        objArr = objArr2;
                    } catch (NoSuchMethodException e) {
                        try {
                            constructor = clsAsSubclass.getConstructor(null);
                            objArr = null;
                        } catch (NoSuchMethodException e2) {
                            e2.initCause(e);
                            throw new IllegalStateException(attributeSet.getPositionDescription() + ": Error creating LayoutManager " + str, e2);
                        }
                    }
                    constructor.setAccessible(true);
                    i0((AbstractC0162el) constructor.newInstance(objArr));
                } catch (ClassCastException e3) {
                    throw new IllegalStateException(attributeSet.getPositionDescription() + ": Class is not a LayoutManager " + str, e3);
                } catch (ClassNotFoundException e4) {
                    throw new IllegalStateException(attributeSet.getPositionDescription() + ": Unable to find LayoutManager " + str, e4);
                } catch (IllegalAccessException e5) {
                    throw new IllegalStateException(attributeSet.getPositionDescription() + ": Cannot access non-public constructor " + str, e5);
                } catch (InstantiationException e6) {
                    throw new IllegalStateException(attributeSet.getPositionDescription() + ": Could not instantiate the LayoutManager: " + str, e6);
                } catch (InvocationTargetException e7) {
                    throw new IllegalStateException(attributeSet.getPositionDescription() + ": Could not instantiate the LayoutManager: " + str, e7);
                }
            }
        }
        int[] iArr2 = z0;
        TypedArray typedArrayObtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, iArr2, i, 0);
        Hs.l(this, context, iArr2, attributeSet, typedArrayObtainStyledAttributes2, i);
        boolean z = typedArrayObtainStyledAttributes2.getBoolean(0, true);
        typedArrayObtainStyledAttributes2.recycle();
        setNestedScrollingEnabled(z);
        setTag(moe.shizuku.privileged.api.R.id._0_resource_name_obfuscated_res_0x7f0900d8, Boolean.TRUE);
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        AbstractC0162el abstractC0162el = this.n;
        if (abstractC0162el != null) {
            return abstractC0162el.t(layoutParams);
        }
        throw new IllegalStateException("RecyclerView has no LayoutManager" + z());
    }
}
