package rikka.shizuku;

import android.os.Build;
import android.view.animation.BaseInterpolator;
import android.view.animation.Interpolator;
import android.widget.OverScroller;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Arrays;
import java.util.WeakHashMap;

/* JADX INFO: renamed from: rikka.shizuku.nl, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class RunnableC0439nl implements Runnable {
    public int a;
    public int b;
    public OverScroller c;
    public Interpolator d;
    public boolean e;
    public boolean f;
    public final /* synthetic */ RecyclerView g;

    public RunnableC0439nl(RecyclerView recyclerView) {
        this.g = recyclerView;
        Wk wk = RecyclerView.E0;
        this.d = wk;
        this.e = false;
        this.f = false;
        this.c = new OverScroller(recyclerView.getContext(), wk);
    }

    public final void a(int i, int i2) {
        RecyclerView recyclerView = this.g;
        recyclerView.j0(2);
        this.b = 0;
        this.a = 0;
        Interpolator interpolator = this.d;
        Wk wk = RecyclerView.E0;
        if (interpolator != wk) {
            this.d = wk;
            this.c = new OverScroller(recyclerView.getContext(), wk);
        }
        this.c.fling(0, 0, i, i2, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
        b();
    }

    public final void b() {
        if (this.e) {
            this.f = true;
            return;
        }
        RecyclerView recyclerView = this.g;
        recyclerView.removeCallbacks(this);
        WeakHashMap weakHashMap = Hs.a;
        recyclerView.postOnAnimation(this);
    }

    public final void c(int i, int i2, int i3, BaseInterpolator baseInterpolator) {
        RecyclerView recyclerView = this.g;
        if (i3 == Integer.MIN_VALUE) {
            int iAbs = Math.abs(i);
            int iAbs2 = Math.abs(i2);
            boolean z = iAbs > iAbs2;
            int width = z ? recyclerView.getWidth() : recyclerView.getHeight();
            if (!z) {
                iAbs = iAbs2;
            }
            i3 = Math.min((int) (((iAbs / width) + 1.0f) * 300.0f), 2000);
        }
        int i4 = i3;
        Interpolator interpolator = baseInterpolator;
        if (baseInterpolator == null) {
            interpolator = RecyclerView.E0;
        }
        if (this.d != interpolator) {
            this.d = interpolator;
            this.c = new OverScroller(recyclerView.getContext(), interpolator);
        }
        this.b = 0;
        this.a = 0;
        recyclerView.j0(2);
        this.c.startScroll(0, 0, i, i2, i4);
        b();
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i;
        int i2;
        int i3;
        RecyclerView recyclerView = this.g;
        if (recyclerView.n == null) {
            recyclerView.removeCallbacks(this);
            this.c.abortAnimation();
            return;
        }
        this.f = false;
        this.e = true;
        recyclerView.p();
        OverScroller overScroller = this.c;
        if (overScroller.computeScrollOffset()) {
            int currX = overScroller.getCurrX();
            int currY = overScroller.getCurrY();
            int i4 = currX - this.a;
            int i5 = currY - this.b;
            this.a = currX;
            this.b = currY;
            int iO = RecyclerView.o(i4, recyclerView.H, recyclerView.J, recyclerView.getWidth());
            int iO2 = RecyclerView.o(i5, recyclerView.I, recyclerView.K, recyclerView.getHeight());
            int[] iArr = recyclerView.q0;
            iArr[0] = 0;
            iArr[1] = 0;
            boolean zC = recyclerView.N().c(iO, iO2, 1, iArr, null);
            int[] iArr2 = recyclerView.q0;
            if (zC) {
                iO -= iArr2[0];
                iO2 -= iArr2[1];
            }
            if (recyclerView.getOverScrollMode() != 2) {
                recyclerView.n(iO, iO2);
            }
            if (recyclerView.m != null) {
                iArr2[0] = 0;
                iArr2[1] = 0;
                recyclerView.f0(iO, iO2, iArr2);
                int i6 = iArr2[0];
                int i7 = iArr2[1];
                iO -= i6;
                iO2 -= i7;
                Jg jg = recyclerView.n.e;
                if (jg != null && !jg.d && jg.e) {
                    int iB = recyclerView.g0.b();
                    if (iB == 0) {
                        jg.i();
                    } else if (jg.a >= iB) {
                        jg.a = iB - 1;
                        jg.g(i6, i7);
                    } else {
                        jg.g(i6, i7);
                    }
                }
                i = i6;
                i2 = i7;
            } else {
                i = 0;
                i2 = 0;
            }
            int i8 = iO;
            int i9 = iO2;
            if (!recyclerView.p.isEmpty()) {
                recyclerView.invalidate();
            }
            int[] iArr3 = recyclerView.q0;
            iArr3[0] = 0;
            iArr3[1] = 0;
            recyclerView.N().d(i, i2, i8, i9, null, 1, iArr3);
            int i10 = i8 - iArr2[0];
            int i11 = i9 - iArr2[1];
            if (i != 0 || i2 != 0) {
                recyclerView.u(i, i2);
            }
            if (!recyclerView.awakenScrollBars()) {
                recyclerView.invalidate();
            }
            boolean z = overScroller.isFinished() || (((overScroller.getCurrX() == overScroller.getFinalX()) || i10 != 0) && ((overScroller.getCurrY() == overScroller.getFinalY()) || i11 != 0));
            Jg jg2 = recyclerView.n.e;
            if ((jg2 == null || !jg2.d) && z) {
                if (recyclerView.getOverScrollMode() != 2) {
                    int currVelocity = (int) overScroller.getCurrVelocity();
                    if (i10 < 0) {
                        i3 = -currVelocity;
                    } else {
                        i3 = i10 > 0 ? currVelocity : 0;
                    }
                    if (i11 < 0) {
                        currVelocity = -currVelocity;
                    } else if (i11 <= 0) {
                        currVelocity = 0;
                    }
                    if (i3 < 0) {
                        recyclerView.w();
                        if (recyclerView.H.isFinished()) {
                            recyclerView.H.onAbsorb(-i3);
                        }
                    } else if (i3 > 0) {
                        recyclerView.x();
                        if (recyclerView.J.isFinished()) {
                            recyclerView.J.onAbsorb(i3);
                        }
                    }
                    if (currVelocity < 0) {
                        recyclerView.y();
                        if (recyclerView.I.isFinished()) {
                            recyclerView.I.onAbsorb(-currVelocity);
                        }
                    } else if (currVelocity > 0) {
                        recyclerView.v();
                        if (recyclerView.K.isFinished()) {
                            recyclerView.K.onAbsorb(currVelocity);
                        }
                    }
                    if (i3 != 0 || currVelocity != 0) {
                        recyclerView.postInvalidateOnAnimation();
                    }
                }
                if (RecyclerView.C0) {
                    Fd fd = recyclerView.f0;
                    int[] iArr4 = (int[]) fd.d;
                    if (iArr4 != null) {
                        Arrays.fill(iArr4, -1);
                    }
                    fd.c = 0;
                }
            } else {
                b();
                Hd hd = recyclerView.e0;
                if (hd != null) {
                    hd.a(recyclerView, i, i2);
                }
            }
            if (Build.VERSION.SDK_INT >= 35) {
                AbstractC0039al.a(recyclerView, Math.abs(overScroller.getCurrVelocity()));
            }
        }
        Jg jg3 = recyclerView.n.e;
        if (jg3 != null && jg3.d) {
            jg3.g(0, 0);
        }
        this.e = false;
        if (!this.f) {
            recyclerView.j0(0);
            recyclerView.N().h(1);
        } else {
            recyclerView.removeCallbacks(this);
            WeakHashMap weakHashMap = Hs.a;
            recyclerView.postOnAnimation(this);
        }
    }
}
