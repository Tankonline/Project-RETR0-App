package rikka.shizuku;

import android.content.res.Resources;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import java.util.WeakHashMap;

/* JADX INFO: renamed from: rikka.shizuku.dh, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class ViewOnTouchListenerC0128dh implements View.OnTouchListener {
    public static final int r = ViewConfiguration.getTapTimeout();
    public final C0576s4 a;
    public final AccelerateInterpolator b;
    public final ListView c;
    public RunnableC0607t4 d;
    public final float[] e;
    public final float[] f;
    public final int g;
    public final int h;
    public final float[] i;
    public final float[] j;
    public final float[] k;
    public boolean l;
    public boolean m;
    public boolean n;
    public boolean o;
    public boolean p;
    public final C0490pa q;

    public ViewOnTouchListenerC0128dh(C0490pa c0490pa) {
        C0576s4 c0576s4 = new C0576s4();
        c0576s4.e = Long.MIN_VALUE;
        c0576s4.g = -1L;
        c0576s4.f = 0L;
        this.a = c0576s4;
        this.b = new AccelerateInterpolator();
        float[] fArr = {0.0f, 0.0f};
        this.e = fArr;
        float[] fArr2 = {Float.MAX_VALUE, Float.MAX_VALUE};
        this.f = fArr2;
        float[] fArr3 = {0.0f, 0.0f};
        this.i = fArr3;
        float[] fArr4 = {0.0f, 0.0f};
        this.j = fArr4;
        float[] fArr5 = {Float.MAX_VALUE, Float.MAX_VALUE};
        this.k = fArr5;
        this.c = c0490pa;
        float f = Resources.getSystem().getDisplayMetrics().density;
        float f2 = ((int) ((1575.0f * f) + 0.5f)) / 1000.0f;
        fArr5[0] = f2;
        fArr5[1] = f2;
        float f3 = ((int) ((f * 315.0f) + 0.5f)) / 1000.0f;
        fArr4[0] = f3;
        fArr4[1] = f3;
        this.g = 1;
        fArr2[0] = Float.MAX_VALUE;
        fArr2[1] = Float.MAX_VALUE;
        fArr[0] = 0.2f;
        fArr[1] = 0.2f;
        fArr3[0] = 0.001f;
        fArr3[1] = 0.001f;
        this.h = r;
        c0576s4.a = 500;
        c0576s4.b = 500;
        this.q = c0490pa;
    }

    public static float b(float f, float f2, float f3) {
        if (f > f3) {
            return f3;
        }
        return f < f2 ? f2 : f;
    }

    /* JADX WARN: Code duplicated, block: B:12:0x003b A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:13:0x003c  */
    /* JADX WARN: Code duplicated, block: B:15:0x004b  */
    /* JADX WARN: Code duplicated, block: B:17:0x0051  */
    public final float a(float f, float f2, float f3, int i) {
        float fB;
        float interpolation;
        float fB2 = b(this.e[i] * f2, 0.0f, this.f[i]);
        float fC = c(f2 - f, fB2) - c(f, fB2);
        AccelerateInterpolator accelerateInterpolator = this.b;
        if (fC >= 0.0f) {
            if (fC > 0.0f) {
                interpolation = accelerateInterpolator.getInterpolation(fC);
            } else {
                fB = 0.0f;
            }
            if (fB == 0.0f) {
                return 0.0f;
            }
            float f4 = this.i[i];
            float f5 = this.j[i];
            float f6 = this.k[i];
            float f7 = f4 * f3;
            return fB > 0.0f ? b(fB * f7, f5, f6) : -b((-fB) * f7, f5, f6);
        }
        interpolation = -accelerateInterpolator.getInterpolation(-fC);
        fB = b(interpolation, -1.0f, 1.0f);
        if (fB == 0.0f) {
            return 0.0f;
        }
        float f8 = this.i[i];
        float f9 = this.j[i];
        float f10 = this.k[i];
        float f11 = f8 * f3;
        if (fB > 0.0f) {
        }
    }

    public final float c(float f, float f2) {
        if (f2 != 0.0f) {
            int i = this.g;
            if (i == 0 || i == 1) {
                if (f < f2) {
                    if (f >= 0.0f) {
                        return 1.0f - (f / f2);
                    }
                    if (this.o && i == 1) {
                        return 1.0f;
                    }
                }
            } else if (i == 2 && f < 0.0f) {
                return f / (-f2);
            }
        }
        return 0.0f;
    }

    public final void d() {
        int i = 0;
        if (this.m) {
            this.o = false;
            return;
        }
        C0576s4 c0576s4 = this.a;
        long jCurrentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
        int i2 = (int) (jCurrentAnimationTimeMillis - c0576s4.e);
        int i3 = c0576s4.b;
        if (i2 > i3) {
            i = i3;
        } else if (i2 >= 0) {
            i = i2;
        }
        c0576s4.i = i;
        c0576s4.h = c0576s4.a(jCurrentAnimationTimeMillis);
        c0576s4.g = jCurrentAnimationTimeMillis;
    }

    public final boolean e() {
        C0490pa c0490pa;
        int count;
        C0576s4 c0576s4 = this.a;
        float f = c0576s4.d;
        int iAbs = (int) (f / Math.abs(f));
        Math.abs(c0576s4.c);
        if (iAbs != 0 && (count = (c0490pa = this.q).getCount()) != 0) {
            int childCount = c0490pa.getChildCount();
            int firstVisiblePosition = c0490pa.getFirstVisiblePosition();
            int i = firstVisiblePosition + childCount;
            if (iAbs <= 0 ? !(iAbs >= 0 || (firstVisiblePosition <= 0 && c0490pa.getChildAt(0).getTop() >= 0)) : !(i >= count && c0490pa.getChildAt(childCount - 1).getBottom() <= c0490pa.getHeight())) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0014, code lost:
    
        if (r0 != 3) goto L30;
     */
    @Override // android.view.View.OnTouchListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean onTouch(View view, MotionEvent motionEvent) {
        int i;
        int i2 = 0;
        if (this.p) {
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked != 0) {
                if (actionMasked != 1) {
                    if (actionMasked != 2) {
                    }
                }
                d();
                return false;
            }
            this.n = true;
            this.l = false;
            float x = motionEvent.getX();
            float width = view.getWidth();
            ListView listView = this.c;
            float fA = a(x, width, listView.getWidth(), 0);
            float fA2 = a(motionEvent.getY(), view.getHeight(), listView.getHeight(), 1);
            C0576s4 c0576s4 = this.a;
            c0576s4.c = fA;
            c0576s4.d = fA2;
            if (!this.o && e()) {
                if (this.d == null) {
                    this.d = new RunnableC0607t4(i2, this);
                }
                this.o = true;
                this.m = true;
                if (this.l || (i = this.h) <= 0) {
                    this.d.run();
                } else {
                    RunnableC0607t4 runnableC0607t4 = this.d;
                    long j = i;
                    WeakHashMap weakHashMap = Hs.a;
                    listView.postOnAnimationDelayed(runnableC0607t4, j);
                }
                this.l = true;
            }
        }
        return false;
    }
}
