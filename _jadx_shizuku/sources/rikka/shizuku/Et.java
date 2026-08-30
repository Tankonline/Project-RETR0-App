package rikka.shizuku;

import android.animation.ValueAnimator;
import android.os.Build;
import android.view.View;
import android.view.WindowInsets;
import android.view.animation.Interpolator;
import java.util.Objects;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public final class Et implements View.OnApplyWindowInsetsListener {
    public final C0796z7 a;
    public au b;

    public Et(View view, C0796z7 c0796z7) {
        au auVarB;
        this.a = c0796z7;
        WeakHashMap weakHashMap = Hs.a;
        au auVarA = As.a(view);
        if (auVarA != null) {
            int i = Build.VERSION.SDK_INT;
            auVarB = (i >= 34 ? new Ot(auVarA) : i >= 30 ? new Nt(auVarA) : i >= 29 ? new Mt(auVarA) : new Kt(auVarA)).b();
        } else {
            auVarB = null;
        }
        this.b = auVarB;
    }

    @Override // android.view.View.OnApplyWindowInsetsListener
    public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
        Wt wt;
        Interpolator interpolator;
        boolean z = true;
        if (!view.isLaidOut()) {
            this.b = au.e(view, windowInsets);
            return Ft.i(view, windowInsets);
        }
        au auVarE = au.e(view, windowInsets);
        if (this.b == null) {
            WeakHashMap weakHashMap = Hs.a;
            this.b = As.a(view);
        }
        if (this.b == null) {
            this.b = auVarE;
            return Ft.i(view, windowInsets);
        }
        C0796z7 c0796z7J = Ft.j(view);
        if (c0796z7J != null && Objects.equals((au) c0796z7J.b, auVarE)) {
            return Ft.i(view, windowInsets);
        }
        int[] iArr = new int[1];
        int[] iArr2 = new int[1];
        au auVar = this.b;
        int i = 1;
        while (true) {
            wt = auVarE.a;
            if (i > 512) {
                break;
            }
            C0587sf c0587sfF = wt.f(i);
            C0587sf c0587sfF2 = auVar.a.f(i);
            int i2 = c0587sfF.a;
            int i3 = c0587sfF2.a;
            int i4 = c0587sfF.d;
            int i5 = c0587sfF.c;
            int i6 = c0587sfF.b;
            int i7 = c0587sfF2.d;
            boolean z2 = z;
            int i8 = c0587sfF2.c;
            int i9 = c0587sfF2.b;
            boolean z3 = (i2 > i3 || i6 > i9 || i5 > i8 || i4 > i7) ? z2 : false;
            if (z3 != ((i2 < i3 || i6 < i9 || i5 < i8 || i4 < i7) ? z2 : false)) {
                if (z3) {
                    iArr[0] = iArr[0] | i;
                } else {
                    iArr2[0] = iArr2[0] | i;
                }
            }
            i <<= 1;
            z = z2;
        }
        boolean z4 = false;
        int i10 = iArr[0];
        int i11 = iArr2[0];
        int i12 = i10 | i11;
        if (i12 == 0) {
            this.b = auVarE;
            return Ft.i(view, windowInsets);
        }
        au auVar2 = this.b;
        if ((i10 & 8) != 0) {
            interpolator = Ft.e;
        } else if ((i11 & 8) != 0) {
            interpolator = Ft.f;
        } else if ((i10 & 519) != 0) {
            interpolator = Ft.g;
        } else {
            interpolator = (i11 & 519) != 0 ? Ft.h : null;
        }
        Jt jt = new Jt(i12, interpolator, (i12 & 8) != 0 ? 160L : 250L);
        jt.a.d(0.0f);
        ValueAnimator duration = ValueAnimator.ofFloat(0.0f, 1.0f).setDuration(jt.a.a());
        C0587sf c0587sfF3 = wt.f(i12);
        C0587sf c0587sfF4 = auVar2.a.f(i12);
        int iMin = Math.min(c0587sfF3.a, c0587sfF4.a);
        int i13 = c0587sfF3.b;
        int i14 = c0587sfF4.b;
        int iMin2 = Math.min(i13, i14);
        int i15 = c0587sfF3.c;
        int i16 = c0587sfF4.c;
        int iMin3 = Math.min(i15, i16);
        int i17 = c0587sfF3.d;
        int i18 = c0587sfF4.d;
        R2 r2 = new R2(C0587sf.b(iMin, iMin2, iMin3, Math.min(i17, i18)), C0587sf.b(Math.max(c0587sfF3.a, c0587sfF4.a), Math.max(i13, i14), Math.max(i15, i16), Math.max(i17, i18)), 26, z4);
        Ft.f(view, auVarE, false);
        duration.addUpdateListener(new Dt(jt, auVarE, auVar2, i12, view));
        duration.addListener(new C0567rq(jt, view));
        ViewTreeObserverOnPreDrawListenerC0746xj.a(view, new RunnableC0186fe(view, jt, r2, duration));
        this.b = auVarE;
        return Ft.i(view, windowInsets);
    }
}
