package rikka.shizuku;

import android.animation.AnimatorSet;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.view.View;
import android.widget.EditText;
import com.google.android.material.internal.CheckableImageButton;
import moe.shizuku.privileged.api.R;

/* JADX INFO: renamed from: rikka.shizuku.m6, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0393m6 extends AbstractC0491pb {
    public final int e;
    public final int f;
    public final TimeInterpolator g;
    public final TimeInterpolator h;
    public EditText i;
    public final Y0 j;
    public final ViewOnFocusChangeListenerC0300j6 k;
    public AnimatorSet l;
    public ValueAnimator m;

    public C0393m6(C0460ob c0460ob) {
        super(c0460ob);
        this.j = new Y0(2, this);
        this.k = new ViewOnFocusChangeListenerC0300j6(this, 0);
        this.e = AbstractC0046as.O(c0460ob.getContext(), R.attr._0_resource_name_obfuscated_res_0x7f040344, 100);
        this.f = AbstractC0046as.O(c0460ob.getContext(), R.attr._0_resource_name_obfuscated_res_0x7f040344, 150);
        this.g = AbstractC0046as.P(c0460ob.getContext(), R.attr._0_resource_name_obfuscated_res_0x7f04034d, V1.a);
        this.h = AbstractC0046as.P(c0460ob.getContext(), R.attr._0_resource_name_obfuscated_res_0x7f04034b, V1.d);
    }

    @Override // rikka.shizuku.AbstractC0491pb
    public final void a() {
        if (this.b.m != null) {
            return;
        }
        t(u());
    }

    @Override // rikka.shizuku.AbstractC0491pb
    public final int c() {
        return R.string._0_resource_name_obfuscated_res_0x7f110045;
    }

    @Override // rikka.shizuku.AbstractC0491pb
    public final int d() {
        return R.drawable._0_resource_name_obfuscated_res_0x7f080100;
    }

    @Override // rikka.shizuku.AbstractC0491pb
    public final View.OnFocusChangeListener e() {
        return this.k;
    }

    @Override // rikka.shizuku.AbstractC0491pb
    public final View.OnClickListener f() {
        return this.j;
    }

    @Override // rikka.shizuku.AbstractC0491pb
    public final View.OnFocusChangeListener g() {
        return this.k;
    }

    @Override // rikka.shizuku.AbstractC0491pb
    public final void m(EditText editText) {
        this.i = editText;
        this.a.c.h(u());
    }

    @Override // rikka.shizuku.AbstractC0491pb
    public final void p(boolean z) {
        if (this.b.m == null) {
            return;
        }
        t(z);
    }

    @Override // rikka.shizuku.AbstractC0491pb
    public final void r() {
        final int i = 1;
        final int i2 = 0;
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.8f, 1.0f);
        valueAnimatorOfFloat.setInterpolator(this.h);
        valueAnimatorOfFloat.setDuration(this.f);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(this) { // from class: rikka.shizuku.k6
            public final /* synthetic */ C0393m6 b;

            {
                this.b = this;
            }

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                switch (i) {
                    case 0:
                        C0393m6 c0393m6 = this.b;
                        c0393m6.getClass();
                        c0393m6.d.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                        break;
                    default:
                        C0393m6 c0393m7 = this.b;
                        c0393m7.getClass();
                        float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                        CheckableImageButton checkableImageButton = c0393m7.d;
                        checkableImageButton.setScaleX(fFloatValue);
                        checkableImageButton.setScaleY(fFloatValue);
                        break;
                }
            }
        });
        ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
        TimeInterpolator timeInterpolator = this.g;
        valueAnimatorOfFloat2.setInterpolator(timeInterpolator);
        int i3 = this.e;
        valueAnimatorOfFloat2.setDuration(i3);
        valueAnimatorOfFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(this) { // from class: rikka.shizuku.k6
            public final /* synthetic */ C0393m6 b;

            {
                this.b = this;
            }

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                switch (i2) {
                    case 0:
                        C0393m6 c0393m6 = this.b;
                        c0393m6.getClass();
                        c0393m6.d.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                        break;
                    default:
                        C0393m6 c0393m7 = this.b;
                        c0393m7.getClass();
                        float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                        CheckableImageButton checkableImageButton = c0393m7.d;
                        checkableImageButton.setScaleX(fFloatValue);
                        checkableImageButton.setScaleY(fFloatValue);
                        break;
                }
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        this.l = animatorSet;
        animatorSet.playTogether(valueAnimatorOfFloat, valueAnimatorOfFloat2);
        this.l.addListener(new C0362l6(this, i2));
        ValueAnimator valueAnimatorOfFloat3 = ValueAnimator.ofFloat(1.0f, 0.0f);
        valueAnimatorOfFloat3.setInterpolator(timeInterpolator);
        valueAnimatorOfFloat3.setDuration(i3);
        valueAnimatorOfFloat3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(this) { // from class: rikka.shizuku.k6
            public final /* synthetic */ C0393m6 b;

            {
                this.b = this;
            }

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                switch (i2) {
                    case 0:
                        C0393m6 c0393m6 = this.b;
                        c0393m6.getClass();
                        c0393m6.d.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                        break;
                    default:
                        C0393m6 c0393m7 = this.b;
                        c0393m7.getClass();
                        float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                        CheckableImageButton checkableImageButton = c0393m7.d;
                        checkableImageButton.setScaleX(fFloatValue);
                        checkableImageButton.setScaleY(fFloatValue);
                        break;
                }
            }
        });
        this.m = valueAnimatorOfFloat3;
        valueAnimatorOfFloat3.addListener(new C0362l6(this, i));
    }

    @Override // rikka.shizuku.AbstractC0491pb
    public final void s() {
        EditText editText = this.i;
        if (editText != null) {
            editText.post(new G0(3, this));
        }
    }

    public final void t(boolean z) {
        boolean z2 = this.b.d() == z;
        if (z && !this.l.isRunning()) {
            this.m.cancel();
            this.l.start();
            if (z2) {
                this.l.end();
                return;
            }
            return;
        }
        if (z) {
            return;
        }
        this.l.cancel();
        this.m.start();
        if (z2) {
            this.m.end();
        }
    }

    public final boolean u() {
        EditText editText = this.i;
        if (editText != null) {
            return (editText.hasFocus() || this.d.hasFocus()) && this.i.getText().length() > 0;
        }
        return false;
    }
}
