package rikka.shizuku;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.text.TextUtils;
import android.util.Property;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.material.textfield.TextInputLayout;
import java.util.ArrayList;
import java.util.WeakHashMap;
import moe.shizuku.privileged.api.R;

/* JADX INFO: renamed from: rikka.shizuku.lf, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0371lf {
    public ColorStateList A;
    public final int a;
    public final int b;
    public final int c;
    public final TimeInterpolator d;
    public final TimeInterpolator e;
    public final TimeInterpolator f;
    public final Context g;
    public final TextInputLayout h;
    public LinearLayout i;
    public int j;
    public FrameLayout k;
    public AnimatorSet l;
    public final float m;
    public int n;
    public int o;
    public CharSequence p;
    public boolean q;
    public C0606t3 r;
    public CharSequence s;
    public int t;
    public int u;
    public ColorStateList v;
    public CharSequence w;
    public boolean x;
    public C0606t3 y;
    public int z;

    public C0371lf(TextInputLayout textInputLayout) {
        Context context = textInputLayout.getContext();
        this.g = context;
        this.h = textInputLayout;
        this.m = context.getResources().getDimensionPixelSize(R.dimen._0_resource_name_obfuscated_res_0x7f07009d);
        this.a = AbstractC0046as.O(context, R.attr._0_resource_name_obfuscated_res_0x7f040345, 217);
        this.b = AbstractC0046as.O(context, R.attr._0_resource_name_obfuscated_res_0x7f040341, 167);
        this.c = AbstractC0046as.O(context, R.attr._0_resource_name_obfuscated_res_0x7f040345, 167);
        this.d = AbstractC0046as.P(context, R.attr._0_resource_name_obfuscated_res_0x7f04034a, V1.d);
        LinearInterpolator linearInterpolator = V1.a;
        this.e = AbstractC0046as.P(context, R.attr._0_resource_name_obfuscated_res_0x7f04034a, linearInterpolator);
        this.f = AbstractC0046as.P(context, R.attr._0_resource_name_obfuscated_res_0x7f04034d, linearInterpolator);
    }

    public final void a(C0606t3 c0606t3, int i) {
        if (this.i == null && this.k == null) {
            Context context = this.g;
            LinearLayout linearLayout = new LinearLayout(context);
            this.i = linearLayout;
            linearLayout.setOrientation(0);
            LinearLayout linearLayout2 = this.i;
            TextInputLayout textInputLayout = this.h;
            textInputLayout.addView(linearLayout2, -1, -2);
            this.k = new FrameLayout(context);
            this.i.addView(this.k, new LinearLayout.LayoutParams(0, -2, 1.0f));
            if (textInputLayout.d != null) {
                b();
            }
        }
        if (i == 0 || i == 1) {
            this.k.setVisibility(0);
            this.k.addView(c0606t3);
        } else {
            this.i.addView(c0606t3, new LinearLayout.LayoutParams(-2, -2));
        }
        this.i.setVisibility(0);
        this.j++;
    }

    public final void b() {
        EditText editText;
        if (this.i == null || (editText = this.h.d) == null) {
            return;
        }
        Context context = this.g;
        boolean zI = X8.I(context);
        LinearLayout linearLayout = this.i;
        WeakHashMap weakHashMap = Hs.a;
        int paddingStart = editText.getPaddingStart();
        if (zI) {
            paddingStart = context.getResources().getDimensionPixelSize(R.dimen._0_resource_name_obfuscated_res_0x7f07025a);
        }
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen._0_resource_name_obfuscated_res_0x7f070259);
        if (zI) {
            dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen._0_resource_name_obfuscated_res_0x7f07025b);
        }
        int paddingEnd = editText.getPaddingEnd();
        if (zI) {
            paddingEnd = context.getResources().getDimensionPixelSize(R.dimen._0_resource_name_obfuscated_res_0x7f07025a);
        }
        linearLayout.setPaddingRelative(paddingStart, dimensionPixelSize, paddingEnd, 0);
    }

    public final void c() {
        AnimatorSet animatorSet = this.l;
        if (animatorSet != null) {
            animatorSet.cancel();
        }
    }

    public final void d(ArrayList arrayList, boolean z, C0606t3 c0606t3, int i, int i2, int i3) {
        if (c0606t3 == null || !z) {
            return;
        }
        if (i == i3 || i == i2) {
            boolean z2 = i3 == i;
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(c0606t3, (Property<C0606t3, Float>) View.ALPHA, z2 ? 1.0f : 0.0f);
            int i4 = this.c;
            objectAnimatorOfFloat.setDuration(z2 ? this.b : i4);
            objectAnimatorOfFloat.setInterpolator(z2 ? this.e : this.f);
            if (i == i3 && i2 != 0) {
                objectAnimatorOfFloat.setStartDelay(i4);
            }
            arrayList.add(objectAnimatorOfFloat);
            if (i3 != i || i2 == 0) {
                return;
            }
            ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(c0606t3, (Property<C0606t3, Float>) View.TRANSLATION_Y, -this.m, 0.0f);
            objectAnimatorOfFloat2.setDuration(this.a);
            objectAnimatorOfFloat2.setInterpolator(this.d);
            objectAnimatorOfFloat2.setStartDelay(i4);
            arrayList.add(objectAnimatorOfFloat2);
        }
    }

    public final TextView e(int i) {
        if (i == 1) {
            return this.r;
        }
        if (i != 2) {
            return null;
        }
        return this.y;
    }

    public final void f() {
        this.p = null;
        c();
        if (this.n == 1) {
            if (!this.x || TextUtils.isEmpty(this.w)) {
                this.o = 0;
            } else {
                this.o = 2;
            }
        }
        i(this.n, this.o, h(this.r, ""));
    }

    public final void g(C0606t3 c0606t3, int i) {
        FrameLayout frameLayout;
        LinearLayout linearLayout = this.i;
        if (linearLayout == null) {
            return;
        }
        if ((i == 0 || i == 1) && (frameLayout = this.k) != null) {
            frameLayout.removeView(c0606t3);
        } else {
            linearLayout.removeView(c0606t3);
        }
        int i2 = this.j - 1;
        this.j = i2;
        LinearLayout linearLayout2 = this.i;
        if (i2 == 0) {
            linearLayout2.setVisibility(8);
        }
    }

    public final boolean h(C0606t3 c0606t3, CharSequence charSequence) {
        WeakHashMap weakHashMap = Hs.a;
        TextInputLayout textInputLayout = this.h;
        if (textInputLayout.isLaidOut() && textInputLayout.isEnabled()) {
            return (this.o == this.n && c0606t3 != null && TextUtils.equals(c0606t3.getText(), charSequence)) ? false : true;
        }
        return false;
    }

    public final void i(int i, int i2, boolean z) {
        TextView textViewE;
        TextView textViewE2;
        C0371lf c0371lf = this;
        if (i == i2) {
            return;
        }
        if (z) {
            AnimatorSet animatorSet = new AnimatorSet();
            c0371lf.l = animatorSet;
            ArrayList arrayList = new ArrayList();
            c0371lf.d(arrayList, c0371lf.x, c0371lf.y, 2, i, i2);
            c0371lf.d(arrayList, c0371lf.q, c0371lf.r, 1, i, i2);
            int size = arrayList.size();
            long jMax = 0;
            for (int i3 = 0; i3 < size; i3++) {
                Animator animator = (Animator) arrayList.get(i3);
                jMax = Math.max(jMax, animator.getDuration() + animator.getStartDelay());
            }
            ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(0, 0);
            valueAnimatorOfInt.setDuration(jMax);
            arrayList.add(0, valueAnimatorOfInt);
            animatorSet.playTogether(arrayList);
            C0309jf c0309jf = new C0309jf(this, i2, e(i), i, c0371lf.e(i2));
            c0371lf = this;
            animatorSet.addListener(c0309jf);
            animatorSet.start();
        } else if (i != i2) {
            if (i2 != 0 && (textViewE2 = c0371lf.e(i2)) != null) {
                textViewE2.setVisibility(0);
                textViewE2.setAlpha(1.0f);
            }
            if (i != 0 && (textViewE = e(i)) != null) {
                textViewE.setVisibility(4);
                if (i == 1) {
                    textViewE.setText((CharSequence) null);
                }
            }
            c0371lf.n = i2;
        }
        TextInputLayout textInputLayout = c0371lf.h;
        textInputLayout.w();
        textInputLayout.z(z, false);
        textInputLayout.C();
    }
}
