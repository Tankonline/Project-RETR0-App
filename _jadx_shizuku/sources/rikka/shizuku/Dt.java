package rikka.shizuku;

import android.animation.ValueAnimator;
import android.os.Build;
import android.view.View;
import android.view.animation.PathInterpolator;
import java.util.Collections;

/* JADX INFO: loaded from: classes.dex */
public final class Dt implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ Jt a;
    public final /* synthetic */ au b;
    public final /* synthetic */ au c;
    public final /* synthetic */ int d;
    public final /* synthetic */ View e;

    public Dt(Jt jt, au auVar, au auVar2, int i, View view) {
        this.a = jt;
        this.b = auVar;
        this.c = auVar2;
        this.d = i;
        this.e = view;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        Pt mt;
        int i;
        float animatedFraction = valueAnimator.getAnimatedFraction();
        Jt jt = this.a;
        jt.a.d(animatedFraction);
        float fB = jt.a.b();
        PathInterpolator pathInterpolator = Ft.e;
        int i2 = Build.VERSION.SDK_INT;
        au auVar = this.b;
        if (i2 >= 34) {
            mt = new Ot(auVar);
        } else if (i2 >= 30) {
            mt = new Nt(auVar);
        } else {
            mt = i2 >= 29 ? new Mt(auVar) : new Kt(auVar);
        }
        int i3 = 1;
        while (i3 <= 512) {
            int i4 = this.d & i3;
            Wt wt = auVar.a;
            if (i4 == 0) {
                mt.c(i3, wt.f(i3));
                i = 1;
            } else {
                C0587sf c0587sfF = wt.f(i3);
                C0587sf c0587sfF2 = this.c.a.f(i3);
                float f = 1.0f - fB;
                i = 1;
                mt.c(i3, au.c(c0587sfF, (int) (((double) ((c0587sfF.a - c0587sfF2.a) * f)) + 0.5d), (int) (((double) ((c0587sfF.b - c0587sfF2.b) * f)) + 0.5d), (int) (((double) ((c0587sfF.c - c0587sfF2.c) * f)) + 0.5d), (int) (((double) ((c0587sfF.d - c0587sfF2.d) * f)) + 0.5d)));
            }
            i3 <<= i;
            fB = fB;
        }
        Ft.g(this.e, mt.b(), Collections.singletonList(jt));
    }
}
