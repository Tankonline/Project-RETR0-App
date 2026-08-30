package rikka.shizuku;

import android.animation.ValueAnimator;
import android.view.View;

/* JADX INFO: renamed from: rikka.shizuku.ra, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class C0551ra implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ int a;
    public final /* synthetic */ Object b;

    public /* synthetic */ C0551ra(int i, Object obj) {
        this.a = i;
        this.b = obj;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        switch (this.a) {
            case 0:
                C0644ua c0644ua = (C0644ua) this.b;
                c0644ua.getClass();
                c0644ua.d.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                break;
            case 1:
                Qh qh = (Qh) this.b;
                qh.getClass();
                float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                qh.j.setAlpha((int) (255.0f * fFloatValue));
                qh.x = fFloatValue;
                break;
            default:
                ((View) ((Ct) ((Hm) this.b).a).t.getParent()).invalidate();
                break;
        }
    }

    public /* synthetic */ C0551ra(Hm hm, View view) {
        this.a = 2;
        this.b = hm;
    }
}
