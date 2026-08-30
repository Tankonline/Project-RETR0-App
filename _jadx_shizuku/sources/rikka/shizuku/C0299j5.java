package rikka.shizuku;

import android.animation.ValueAnimator;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.textfield.TextInputLayout;

/* JADX INFO: renamed from: rikka.shizuku.j5, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0299j5 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ int a;
    public final /* synthetic */ Object b;

    public /* synthetic */ C0299j5(int i, Object obj) {
        this.a = i;
        this.b = obj;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        switch (this.a) {
            case 0:
                float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                Xh xh = ((BottomSheetBehavior) this.b).i;
                if (xh != null) {
                    Wh wh = xh.a;
                    if (wh.i != fFloatValue) {
                        wh.i = fFloatValue;
                        xh.e = true;
                        xh.invalidateSelf();
                    }
                }
                break;
            case 1:
                int iFloatValue = (int) (((Float) valueAnimator.getAnimatedValue()).floatValue() * 255.0f);
                Tb tb = (Tb) this.b;
                tb.c.setAlpha(iFloatValue);
                tb.d.setAlpha(iFloatValue);
                tb.s.invalidate();
                break;
            default:
                ((TextInputLayout) this.b).u0.k(((Float) valueAnimator.getAnimatedValue()).floatValue());
                break;
        }
    }
}
