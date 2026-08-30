package com.google.android.material.carousel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.carousel.CarouselLayoutManager;
import moe.shizuku.privileged.api.R;
import rikka.shizuku.AbstractC0162el;
import rikka.shizuku.AbstractC0535qo;
import rikka.shizuku.B5;
import rikka.shizuku.C0193fl;
import rikka.shizuku.C0285il;
import rikka.shizuku.C0408ml;
import rikka.shizuku.C5;
import rikka.shizuku.D5;
import rikka.shizuku.Ii;
import rikka.shizuku.InterfaceC0377ll;
import rikka.shizuku.Lk;

/* JADX INFO: loaded from: classes.dex */
public class CarouselLayoutManager extends AbstractC0162el implements InterfaceC0377ll {
    public final Ii p;
    public D5 q;
    public final View.OnLayoutChangeListener r;

    public CarouselLayoutManager() {
        Ii ii = new Ii();
        new C5();
        this.r = new View.OnLayoutChangeListener() { // from class: rikka.shizuku.A5
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                CarouselLayoutManager carouselLayoutManager = this.a;
                if (i == i5 && i2 == i6 && i3 == i7 && i4 == i8) {
                    return;
                }
                view.post(new G0(2, carouselLayoutManager));
            }
        };
        this.p = ii;
        o0();
        F0(0);
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final void A0(RecyclerView recyclerView, int i) {
        B5 b5 = new B5(0, recyclerView.getContext(), this);
        b5.a = i;
        B0(b5);
    }

    public final boolean D0() {
        return this.q.a == 0;
    }

    public final boolean E0() {
        return D0() && this.b.getLayoutDirection() == 1;
    }

    public final void F0(int i) {
        D5 d5;
        if (i != 0 && i != 1) {
            throw new IllegalArgumentException(AbstractC0535qo.b(i, "invalid orientation:"));
        }
        c(null);
        D5 d6 = this.q;
        if (d6 == null || i != d6.a) {
            if (i == 0) {
                d5 = new D5(this, 1);
            } else {
                if (i != 1) {
                    throw new IllegalArgumentException("invalid orientation");
                }
                d5 = new D5(this, 0);
            }
            this.q = d5;
            o0();
        }
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final boolean K() {
        return true;
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final void R(RecyclerView recyclerView) {
        Ii ii = this.p;
        Context context = recyclerView.getContext();
        float dimension = ii.a;
        if (dimension <= 0.0f) {
            dimension = context.getResources().getDimension(R.dimen._0_resource_name_obfuscated_res_0x7f070107);
        }
        ii.a = dimension;
        float dimension2 = ii.b;
        if (dimension2 <= 0.0f) {
            dimension2 = context.getResources().getDimension(R.dimen._0_resource_name_obfuscated_res_0x7f070106);
        }
        ii.b = dimension2;
        o0();
        recyclerView.addOnLayoutChangeListener(this.r);
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final void S(RecyclerView recyclerView) {
        recyclerView.removeOnLayoutChangeListener(this.r);
    }

    /* JADX WARN: Code duplicated, block: B:17:0x0025  */
    /* JADX WARN: Code duplicated, block: B:19:0x0029  */
    /* JADX WARN: Code duplicated, block: B:23:0x0033  */
    @Override // rikka.shizuku.AbstractC0162el
    public final View T(View view, int i, C0285il c0285il, C0408ml c0408ml) {
        byte b;
        if (v() == 0) {
            return null;
        }
        int i2 = this.q.a;
        if (i == 1) {
            b = -1;
        } else if (i == 2) {
            b = 1;
        } else if (i != 17) {
            if (i != 33) {
                if (i != 66) {
                    if (i == 130 && i2 == 1) {
                        b = 1;
                    } else {
                        b = -2147483648;
                    }
                } else if (i2 != 0) {
                    b = -2147483648;
                } else if (E0()) {
                    b = -1;
                } else {
                    b = 1;
                }
            } else if (i2 == 1) {
                b = -1;
            } else {
                b = -2147483648;
            }
        } else if (i2 != 0) {
            b = -2147483648;
        } else if (E0()) {
            b = 1;
        } else {
            b = -1;
        }
        if (b == -2147483648) {
            return null;
        }
        if (b == -1) {
            if (AbstractC0162el.G(view) == 0) {
                return null;
            }
            int iG = AbstractC0162el.G(u(0)) - 1;
            if (iG < 0 || iG >= B()) {
                return u(E0() ? v() - 1 : 0);
            }
            this.q.a();
            throw null;
        }
        if (AbstractC0162el.G(view) == B() - 1) {
            return null;
        }
        int iG2 = AbstractC0162el.G(u(v() - 1)) + 1;
        if (iG2 < 0 || iG2 >= B()) {
            return u(E0() ? 0 : v() - 1);
        }
        this.q.a();
        throw null;
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final void U(AccessibilityEvent accessibilityEvent) {
        super.U(accessibilityEvent);
        if (v() > 0) {
            accessibilityEvent.setFromIndex(AbstractC0162el.G(u(0)));
            accessibilityEvent.setToIndex(AbstractC0162el.G(u(v() - 1)));
        }
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final void Y(int i, int i2) {
        B();
    }

    @Override // rikka.shizuku.InterfaceC0377ll
    public final PointF a(int i) {
        return null;
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final void b0(int i, int i2) {
        B();
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final boolean d() {
        return D0();
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final void d0(C0285il c0285il, C0408ml c0408ml) {
        if (c0408ml.b() > 0) {
            if ((D0() ? this.n : this.o) > 0.0f) {
                E0();
                View view = c0285il.k(0, Long.MAX_VALUE).a;
                throw new IllegalStateException("All children of a RecyclerView using CarouselLayoutManager must use MaskableFrameLayout as their root ViewGroup.");
            }
        }
        j0(c0285il);
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final boolean e() {
        return !D0();
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final void e0(C0408ml c0408ml) {
        if (v() == 0) {
            return;
        }
        AbstractC0162el.G(u(0));
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final int j(C0408ml c0408ml) {
        v();
        return 0;
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final int k(C0408ml c0408ml) {
        return 0;
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final int l(C0408ml c0408ml) {
        return 0;
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final int m(C0408ml c0408ml) {
        v();
        return 0;
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final int n(C0408ml c0408ml) {
        return 0;
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final boolean n0(RecyclerView recyclerView, View view, Rect rect, boolean z, boolean z2) {
        return false;
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final int o(C0408ml c0408ml) {
        return 0;
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final int p0(int i, C0285il c0285il, C0408ml c0408ml) {
        if (!D0() || v() == 0 || i == 0) {
            return 0;
        }
        View view = c0285il.k(0, Long.MAX_VALUE).a;
        throw new IllegalStateException("All children of a RecyclerView using CarouselLayoutManager must use MaskableFrameLayout as their root ViewGroup.");
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final void q0(int i) {
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final C0193fl r() {
        return new C0193fl(-2, -2);
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final int r0(int i, C0285il c0285il, C0408ml c0408ml) {
        if (!e() || v() == 0 || i == 0) {
            return 0;
        }
        View view = c0285il.k(0, Long.MAX_VALUE).a;
        throw new IllegalStateException("All children of a RecyclerView using CarouselLayoutManager must use MaskableFrameLayout as their root ViewGroup.");
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final void y(View view, Rect rect) {
        super.y(view, rect);
        rect.centerY();
        if (D0()) {
            rect.centerX();
        }
        throw null;
    }

    @SuppressLint({"UnknownNullness"})
    public CarouselLayoutManager(Context context, AttributeSet attributeSet, int i, int i2) {
        new C5();
        this.r = new View.OnLayoutChangeListener() { // from class: rikka.shizuku.A5
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
                CarouselLayoutManager carouselLayoutManager = this.a;
                if (i3 == i7 && i4 == i8 && i5 == i9 && i6 == i10) {
                    return;
                }
                view.post(new G0(2, carouselLayoutManager));
            }
        };
        this.p = new Ii();
        o0();
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Lk.e);
            typedArrayObtainStyledAttributes.getInt(0, 0);
            o0();
            F0(typedArrayObtainStyledAttributes.getInt(0, 0));
            typedArrayObtainStyledAttributes.recycle();
        }
    }
}
