package rikka.shizuku;

import android.content.Context;
import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes.dex */
public class Jg {
    public int a = -1;
    public RecyclerView b;
    public AbstractC0162el c;
    public boolean d;
    public boolean e;
    public View f;
    public final C0346kl g;
    public boolean h;
    public final LinearInterpolator i;
    public final DecelerateInterpolator j;
    public PointF k;
    public final DisplayMetrics l;
    public boolean m;
    public float n;
    public int o;
    public int p;

    public Jg(Context context) {
        C0346kl c0346kl = new C0346kl();
        c0346kl.d = -1;
        c0346kl.f = false;
        c0346kl.g = 0;
        c0346kl.a = 0;
        c0346kl.b = 0;
        c0346kl.c = Integer.MIN_VALUE;
        c0346kl.e = null;
        this.g = c0346kl;
        this.i = new LinearInterpolator();
        this.j = new DecelerateInterpolator();
        this.m = false;
        this.o = 0;
        this.p = 0;
        this.l = context.getResources().getDisplayMetrics();
    }

    public static int a(int i, int i2, int i3, int i4, int i5) {
        if (i5 == -1) {
            return i3 - i;
        }
        if (i5 != 0) {
            if (i5 == 1) {
                return i4 - i2;
            }
            throw new IllegalArgumentException("snap preference should be one of the constants defined in SmoothScroller, starting with SNAP_");
        }
        int i6 = i3 - i;
        if (i6 > 0) {
            return i6;
        }
        int i7 = i4 - i2;
        if (i7 < 0) {
            return i7;
        }
        return 0;
    }

    public int b(View view, int i) {
        AbstractC0162el abstractC0162el = this.c;
        if (abstractC0162el == null || !abstractC0162el.d()) {
            return 0;
        }
        C0193fl c0193fl = (C0193fl) view.getLayoutParams();
        return a((view.getLeft() - ((C0193fl) view.getLayoutParams()).b.left) - ((ViewGroup.MarginLayoutParams) c0193fl).leftMargin, view.getRight() + ((C0193fl) view.getLayoutParams()).b.right + ((ViewGroup.MarginLayoutParams) c0193fl).rightMargin, abstractC0162el.D(), abstractC0162el.n - abstractC0162el.E(), i);
    }

    public int c(View view, int i) {
        AbstractC0162el abstractC0162el = this.c;
        if (abstractC0162el == null || !abstractC0162el.e()) {
            return 0;
        }
        C0193fl c0193fl = (C0193fl) view.getLayoutParams();
        return a((view.getTop() - ((C0193fl) view.getLayoutParams()).b.top) - ((ViewGroup.MarginLayoutParams) c0193fl).topMargin, view.getBottom() + ((C0193fl) view.getLayoutParams()).b.bottom + ((ViewGroup.MarginLayoutParams) c0193fl).bottomMargin, abstractC0162el.F(), abstractC0162el.o - abstractC0162el.C(), i);
    }

    public float d(DisplayMetrics displayMetrics) {
        return 25.0f / displayMetrics.densityDpi;
    }

    public int e(int i) {
        float fAbs = Math.abs(i);
        if (!this.m) {
            this.n = d(this.l);
            this.m = true;
        }
        return (int) Math.ceil(fAbs * this.n);
    }

    public PointF f(int i) {
        Object obj = this.c;
        if (obj instanceof InterfaceC0377ll) {
            return ((InterfaceC0377ll) obj).a(i);
        }
        Log.w("RecyclerView", "You should override computeScrollVectorForPosition when the LayoutManager does not implement " + InterfaceC0377ll.class.getCanonicalName());
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:50:0x00f8  */
    public final void g(int i, int i2) {
        PointF pointFF;
        RecyclerView recyclerView = this.b;
        if (this.a == -1 || recyclerView == null) {
            i();
        }
        if (this.d && this.f == null && this.c != null && (pointFF = f(this.a)) != null) {
            float f = pointFF.x;
            if (f != 0.0f || pointFF.y != 0.0f) {
                recyclerView.f0((int) Math.signum(f), (int) Math.signum(pointFF.y), null);
            }
        }
        this.d = false;
        View view = this.f;
        C0346kl c0346kl = this.g;
        if (view != null) {
            this.b.getClass();
            AbstractC0470ol abstractC0470olK = RecyclerView.K(view);
            if ((abstractC0470olK != null ? abstractC0470olK.b() : -1) == this.a) {
                View view2 = this.f;
                C0408ml c0408ml = recyclerView.g0;
                h(view2, c0346kl);
                c0346kl.a(recyclerView);
                i();
            } else {
                Log.e("RecyclerView", "Passed over target position while smooth scrolling.");
                this.f = null;
            }
        }
        if (this.e) {
            C0408ml c0408ml2 = recyclerView.g0;
            if (this.b.n.v() == 0) {
                i();
            } else {
                int i3 = this.o;
                int i4 = i3 - i;
                if (i3 * i4 <= 0) {
                    i4 = 0;
                }
                this.o = i4;
                int i5 = this.p;
                int i6 = i5 - i2;
                if (i5 * i6 <= 0) {
                    i6 = 0;
                }
                this.p = i6;
                if (i4 == 0 && i6 == 0) {
                    PointF pointFF2 = f(this.a);
                    if (pointFF2 != null) {
                        float f2 = pointFF2.x;
                        if (f2 == 0.0f && pointFF2.y == 0.0f) {
                            c0346kl.d = this.a;
                            i();
                        } else {
                            float f3 = pointFF2.y;
                            float fSqrt = (float) Math.sqrt((f3 * f3) + (f2 * f2));
                            float f4 = pointFF2.x / fSqrt;
                            pointFF2.x = f4;
                            float f5 = pointFF2.y / fSqrt;
                            pointFF2.y = f5;
                            this.k = pointFF2;
                            this.o = (int) (f4 * 10000.0f);
                            this.p = (int) (f5 * 10000.0f);
                            int iE = e(10000);
                            int i7 = (int) (this.o * 1.2f);
                            int i8 = (int) (this.p * 1.2f);
                            LinearInterpolator linearInterpolator = this.i;
                            c0346kl.a = i7;
                            c0346kl.b = i8;
                            c0346kl.c = (int) (iE * 1.2f);
                            c0346kl.e = linearInterpolator;
                            c0346kl.f = true;
                        }
                    } else {
                        c0346kl.d = this.a;
                        i();
                    }
                }
            }
            boolean z = c0346kl.d >= 0;
            c0346kl.a(recyclerView);
            if (z && this.e) {
                this.d = true;
                recyclerView.d0.b();
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:10:0x0015  */
    public void h(View view, C0346kl c0346kl) {
        int i;
        PointF pointF = this.k;
        int i2 = 0;
        if (pointF != null) {
            float f = pointF.x;
            if (f == 0.0f) {
                i = 0;
            } else {
                i = f > 0.0f ? 1 : -1;
            }
        } else {
            i = 0;
        }
        int iB = b(view, i);
        PointF pointF2 = this.k;
        if (pointF2 != null) {
            float f2 = pointF2.y;
            if (f2 != 0.0f) {
                i2 = f2 > 0.0f ? 1 : -1;
            }
        }
        int iC = c(view, i2);
        int iCeil = (int) Math.ceil(((double) e((int) Math.sqrt((iC * iC) + (iB * iB)))) / 0.3356d);
        if (iCeil > 0) {
            DecelerateInterpolator decelerateInterpolator = this.j;
            c0346kl.a = -iB;
            c0346kl.b = -iC;
            c0346kl.c = iCeil;
            c0346kl.e = decelerateInterpolator;
            c0346kl.f = true;
        }
    }

    public final void i() {
        if (this.e) {
            this.e = false;
            this.p = 0;
            this.o = 0;
            this.k = null;
            this.b.g0.a = -1;
            this.f = null;
            this.a = -1;
            this.d = false;
            AbstractC0162el abstractC0162el = this.c;
            if (abstractC0162el.e == this) {
                abstractC0162el.e = null;
            }
            this.c = null;
            this.b = null;
        }
    }
}
