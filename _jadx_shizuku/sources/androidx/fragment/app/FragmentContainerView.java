package androidx.fragment.app;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.FrameLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;
import java.util.WeakHashMap;
import moe.shizuku.privileged.api.R;
import rikka.shizuku.AbstractC0535qo;
import rikka.shizuku.AbstractC0755xs;
import rikka.shizuku.Ac;
import rikka.shizuku.C0062bd;
import rikka.shizuku.Cc;
import rikka.shizuku.Hs;
import rikka.shizuku.Ik;
import rikka.shizuku.Kc;
import rikka.shizuku.L4;
import rikka.shizuku.Qc;
import rikka.shizuku.Xc;
import rikka.shizuku.au;

/* JADX INFO: loaded from: classes.dex */
public final class FragmentContainerView extends FrameLayout {
    public final ArrayList a;
    public final ArrayList b;
    public View.OnApplyWindowInsetsListener c;
    public boolean d;

    public FragmentContainerView(Context context, AttributeSet attributeSet) {
        String str;
        super(context, attributeSet, 0);
        this.a = new ArrayList();
        this.b = new ArrayList();
        this.d = true;
        if (attributeSet != null) {
            String classAttribute = attributeSet.getClassAttribute();
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Ik.b, 0, 0);
            if (classAttribute == null) {
                classAttribute = typedArrayObtainStyledAttributes.getString(0);
                str = "android:name";
            } else {
                str = "class";
            }
            typedArrayObtainStyledAttributes.recycle();
            if (classAttribute == null || isInEditMode()) {
                return;
            }
            throw new UnsupportedOperationException("FragmentContainerView must be within a FragmentActivity to use " + str + "=\"" + classAttribute + '\"');
        }
    }

    public final void a(View view) {
        if (this.b.contains(view)) {
            this.a.add(view);
        }
    }

    @Override // android.view.ViewGroup
    public final void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        Object tag = view.getTag(R.id._0_resource_name_obfuscated_res_0x7f0900bd);
        if ((tag instanceof Ac ? (Ac) tag : null) != null) {
            super.addView(view, i, layoutParams);
            return;
        }
        throw new IllegalStateException(("Views added to a FragmentContainerView must be associated with a Fragment. View " + view + " is not associated with a Fragment.").toString());
    }

    @Override // android.view.ViewGroup, android.view.View
    public final WindowInsets dispatchApplyWindowInsets(WindowInsets windowInsets) {
        au auVarE;
        au auVarE2 = au.e(null, windowInsets);
        View.OnApplyWindowInsetsListener onApplyWindowInsetsListener = this.c;
        if (onApplyWindowInsetsListener != null) {
            auVarE = au.e(null, onApplyWindowInsetsListener.onApplyWindowInsets(this, windowInsets));
        } else {
            WeakHashMap weakHashMap = Hs.a;
            WindowInsets windowInsetsD = auVarE2.d();
            if (windowInsetsD != null) {
                WindowInsets windowInsetsB = AbstractC0755xs.b(this, windowInsetsD);
                if (!windowInsetsB.equals(windowInsetsD)) {
                    auVarE2 = au.e(this, windowInsetsB);
                }
            }
            auVarE = auVarE2;
        }
        if (!auVarE.a.m()) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                Hs.b(getChildAt(i), auVarE);
            }
        }
        return windowInsets;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchDraw(Canvas canvas) {
        if (this.d) {
            ArrayList arrayList = this.a;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                super.drawChild(canvas, (View) obj, getDrawingTime());
            }
        }
        super.dispatchDraw(canvas);
    }

    @Override // android.view.ViewGroup
    public final boolean drawChild(Canvas canvas, View view, long j) {
        if (this.d) {
            ArrayList arrayList = this.a;
            if (!arrayList.isEmpty() && arrayList.contains(view)) {
                return false;
            }
        }
        return super.drawChild(canvas, view, j);
    }

    @Override // android.view.ViewGroup
    public final void endViewTransition(View view) {
        this.b.remove(view);
        if (this.a.remove(view)) {
            this.d = true;
        }
        super.endViewTransition(view);
    }

    @Override // android.view.View
    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        return windowInsets;
    }

    @Override // android.view.ViewGroup
    public final void removeAllViewsInLayout() {
        int childCount = getChildCount();
        while (true) {
            childCount--;
            if (-1 >= childCount) {
                super.removeAllViewsInLayout();
                return;
            }
            a(getChildAt(childCount));
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewManager
    public final void removeView(View view) {
        a(view);
        super.removeView(view);
    }

    @Override // android.view.ViewGroup
    public final void removeViewAt(int i) {
        a(getChildAt(i));
        super.removeViewAt(i);
    }

    @Override // android.view.ViewGroup
    public final void removeViewInLayout(View view) {
        a(view);
        super.removeViewInLayout(view);
    }

    @Override // android.view.ViewGroup
    public final void removeViews(int i, int i2) {
        int i3 = i + i2;
        for (int i4 = i; i4 < i3; i4++) {
            a(getChildAt(i4));
        }
        super.removeViews(i, i2);
    }

    @Override // android.view.ViewGroup
    public final void removeViewsInLayout(int i, int i2) {
        int i3 = i + i2;
        for (int i4 = i; i4 < i3; i4++) {
            a(getChildAt(i4));
        }
        super.removeViewsInLayout(i, i2);
    }

    @Override // android.view.ViewGroup
    public final void setLayoutTransition(LayoutTransition layoutTransition) {
        throw new UnsupportedOperationException("FragmentContainerView does not support Layout Transitions or animateLayoutChanges=\"true\".");
    }

    @Override // android.view.View
    public final void setOnApplyWindowInsetsListener(View.OnApplyWindowInsetsListener onApplyWindowInsetsListener) {
        this.c = onApplyWindowInsetsListener;
    }

    @Override // android.view.ViewGroup
    public final void startViewTransition(View view) {
        if (view.getParent() == this) {
            this.b.add(view);
        }
        super.startViewTransition(view);
    }

    public FragmentContainerView(Context context, AttributeSet attributeSet, Qc qc) {
        View view;
        super(context, attributeSet);
        this.a = new ArrayList();
        this.b = new ArrayList();
        this.d = true;
        String classAttribute = attributeSet.getClassAttribute();
        int i = 0;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Ik.b, 0, 0);
        classAttribute = classAttribute == null ? typedArrayObtainStyledAttributes.getString(0) : classAttribute;
        String string = typedArrayObtainStyledAttributes.getString(1);
        typedArrayObtainStyledAttributes.recycle();
        int id = getId();
        Ac acB = qc.B(id);
        if (classAttribute != null && acB == null) {
            if (id == -1) {
                throw new IllegalStateException(AbstractC0535qo.d("FragmentContainerView must have an android:id to add Fragment ", classAttribute, string != null ? " with tag ".concat(string) : ""));
            }
            Kc kcG = qc.G();
            context.getClassLoader();
            Ac acA = kcG.a(classAttribute);
            acA.x = id;
            acA.y = id;
            acA.z = string;
            acA.t = qc;
            Cc cc = qc.w;
            acA.u = cc;
            acA.E = true;
            if ((cc == null ? null : cc.i) != null) {
                acA.E = true;
            }
            L4 l4 = new L4(qc);
            l4.p = true;
            acA.F = this;
            acA.p = true;
            l4.f(getId(), acA, string, 1);
            if (!l4.g) {
                l4.h = false;
                Qc qc2 = l4.r;
                if (qc2.w != null && !qc2.J) {
                    qc2.y(true);
                    L4 l5 = qc2.h;
                    if (l5 != null) {
                        l5.s = false;
                        l5.d();
                        if (Qc.J(3)) {
                            Objects.toString(qc2.h);
                            Objects.toString(l4);
                        }
                        qc2.h.e(false, false);
                        qc2.h.a(qc2.L, qc2.M);
                        ArrayList arrayList = qc2.h.a;
                        int size = arrayList.size();
                        int i2 = 0;
                        while (i2 < size) {
                            Object obj = arrayList.get(i2);
                            i2++;
                            Ac ac = ((C0062bd) obj).b;
                            if (ac != null) {
                                ac.m = false;
                            }
                        }
                        qc2.h = null;
                    }
                    l4.a(qc2.L, qc2.M);
                    qc2.b = true;
                    try {
                        qc2.V(qc2.L, qc2.M);
                        qc2.d();
                        qc2.g0();
                        if (qc2.K) {
                            qc2.K = false;
                            qc2.e0();
                        }
                        ((HashMap) qc2.c.b).values().removeAll(Collections.singleton(null));
                    } catch (Throwable th) {
                        qc2.d();
                        throw th;
                    }
                }
            } else {
                throw new IllegalStateException("This transaction is already being added to the back stack");
            }
        }
        ArrayList arrayListK = qc.c.k();
        int size2 = arrayListK.size();
        while (i < size2) {
            Object obj2 = arrayListK.get(i);
            i++;
            Xc xc = (Xc) obj2;
            Ac ac2 = xc.c;
            if (ac2.y == getId() && (view = ac2.G) != null && view.getParent() == null) {
                ac2.F = this;
                xc.b();
                xc.k();
            }
        }
    }
}
