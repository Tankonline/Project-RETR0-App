package rikka.shizuku;

import android.animation.AnimatorSet;
import android.animation.FloatEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.util.Log;
import android.util.Property;
import android.view.ActionMode;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.lang.reflect.Field;
import moe.shizuku.privileged.api.R;

/* JADX INFO: loaded from: classes.dex */
public abstract class An {
    public static boolean a = false;
    public static boolean b = true;
    public static Field c;
    public static boolean d;

    public static final hu f(LinearLayout linearLayout) {
        Object tag = linearLayout.getTag(R.id._0_resource_name_obfuscated_res_0x7f0901c2);
        if (tag instanceof hu) {
            return (hu) tag;
        }
        return null;
    }

    public static void g(Context context, Intent intent) {
        Bundle bundleExtra;
        IBinder binder;
        if (!AbstractC0046as.c(intent.getAction(), "rikka.shizuku.intent.action.REQUEST_BINDER") || (bundleExtra = intent.getBundleExtra("data")) == null || (binder = bundleExtra.getBinder("binder")) == null) {
            return;
        }
        IBinder iBinder = Nn.a;
        if (iBinder == null) {
            Log.w("ShizukuManager", "Binder not received or Shizuku service not running");
        }
        Parcel parcelObtain = Parcel.obtain();
        try {
            parcelObtain.writeStrongBinder(iBinder);
            parcelObtain.writeString(context.getApplicationInfo().sourceDir);
            binder.transact(1, parcelObtain, null, 1);
            parcelObtain.recycle();
        } catch (Throwable th) {
            try {
                th.printStackTrace();
            } finally {
                parcelObtain.recycle();
            }
        }
    }

    public static Typeface h(Configuration configuration, Typeface typeface) {
        if (Build.VERSION.SDK_INT < 31 || configuration.fontWeightAdjustment == Integer.MAX_VALUE || configuration.fontWeightAdjustment == 0 || typeface == null) {
            return null;
        }
        return Typeface.create(typeface, AbstractC0744xh.i(configuration.fontWeightAdjustment + typeface.getWeight(), 1, 1000), typeface.isItalic());
    }

    public static void m(TextView textView, int i) {
        AbstractC0046as.i(i);
        if (Build.VERSION.SDK_INT >= 28) {
            G9.h(textView, i);
            return;
        }
        Paint.FontMetricsInt fontMetricsInt = textView.getPaint().getFontMetricsInt();
        int i2 = textView.getIncludeFontPadding() ? fontMetricsInt.top : fontMetricsInt.ascent;
        if (i > Math.abs(i2)) {
            textView.setPadding(textView.getPaddingLeft(), i + i2, textView.getPaddingRight(), textView.getPaddingBottom());
        }
    }

    public static void n(TextView textView, int i) {
        AbstractC0046as.i(i);
        Paint.FontMetricsInt fontMetricsInt = textView.getPaint().getFontMetricsInt();
        int i2 = textView.getIncludeFontPadding() ? fontMetricsInt.bottom : fontMetricsInt.descent;
        if (i > Math.abs(i2)) {
            textView.setPadding(textView.getPaddingLeft(), textView.getPaddingTop(), textView.getPaddingRight(), i - i2);
        }
    }

    public static void o(TextView textView, int i) {
        AbstractC0046as.i(i);
        int fontMetricsInt = textView.getPaint().getFontMetricsInt(null);
        if (i != fontMetricsInt) {
            textView.setLineSpacing(i - fontMetricsInt, 1.0f);
        }
    }

    public static void r(C0302j8 c0302j8, RecyclerView recyclerView, int i, int i2, int i3, int i4, Rect rect, int i5, int i6, int i7) {
        C0778yk c0778yk = new C0778yk(c0302j8, recyclerView);
        int i8 = i - i3;
        int iMax = Math.max(i3, i8);
        int i9 = i2 - i4;
        int iMax2 = Math.max(i4, i9);
        int iMax3 = Math.max(i3, i8);
        int iMax4 = Math.max(i4, i9);
        Rect[] rectArr = {new Rect(i3 - iMax3, i4 - iMax4, iMax3 + i3, i4 + iMax4), new Rect(0, 0, i, i2)};
        Rect rect2 = rectArr[0];
        Rect rect3 = rectArr[1];
        long jMin = Math.min(Math.max((long) ((Math.max(iMax, iMax2) / 4096) * 1000.0f), 150L), 300L);
        ObjectAnimator objectAnimatorOfObject = ObjectAnimator.ofObject(c0778yk, C0165eo.a, new Uk(rect3), rect, rect2);
        objectAnimatorOfObject.setInterpolator(new DecelerateInterpolator());
        objectAnimatorOfObject.setDuration(jMin);
        ObjectAnimator objectAnimatorOfObject2 = ObjectAnimator.ofObject(recyclerView, (Property<RecyclerView, V>) View.TRANSLATION_Z, new FloatEvaluator(), Float.valueOf(-i6), Float.valueOf(0.0f));
        objectAnimatorOfObject2.setInterpolator(new Pb(1));
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimatorOfObject, objectAnimatorOfObject2);
        animatorSet.setDuration(objectAnimatorOfObject.getDuration());
        animatorSet.start();
        if (recyclerView != null) {
            for (int i10 = 0; i10 < recyclerView.getChildCount(); i10++) {
                int i11 = i7 - i10;
                View childAt = recyclerView.getChildAt(i10);
                long jAbs = Math.abs(i11) * 30;
                int i12 = i11 == 0 ? 0 : ((int) (((double) i5) * 0.2d)) * (i11 < 0 ? -1 : 1);
                childAt.setAlpha(0.0f);
                ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(childAt, "alpha", 0.0f, 1.0f);
                objectAnimatorOfFloat.setDuration(200L);
                objectAnimatorOfFloat.setInterpolator(new AccelerateInterpolator());
                ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(childAt, "translationY", i12, 0.0f);
                objectAnimatorOfFloat2.setDuration(275L);
                objectAnimatorOfFloat2.setInterpolator(new DecelerateInterpolator());
                AnimatorSet animatorSet2 = new AnimatorSet();
                animatorSet2.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2);
                animatorSet2.setStartDelay(jAbs);
                animatorSet2.start();
            }
        }
    }

    public static ActionMode.Callback t(ActionMode.Callback callback) {
        return (!(callback instanceof Ip) || Build.VERSION.SDK_INT < 26) ? callback : ((Ip) callback).a;
    }

    public static ActionMode.Callback u(ActionMode.Callback callback, TextView textView) {
        int i = Build.VERSION.SDK_INT;
        return (i < 26 || i > 27 || (callback instanceof Ip) || callback == null) ? callback : new Ip(callback, textView);
    }

    public abstract int a(View view, int i);

    public abstract int b(View view, int i);

    public float c(View view) {
        if (b) {
            try {
                return AbstractC0354kt.a(view);
            } catch (NoSuchMethodError unused) {
                b = false;
            }
        }
        return view.getAlpha();
    }

    public int d(View view) {
        return 0;
    }

    public int e() {
        return 0;
    }

    public abstract void j(int i);

    public abstract void k(View view, int i, int i2);

    public abstract void l(View view, float f, float f2);

    public void p(View view, float f) {
        if (b) {
            try {
                AbstractC0354kt.b(view, f);
                return;
            } catch (NoSuchMethodError unused) {
                b = false;
            }
        }
        view.setAlpha(f);
    }

    public void q(View view, int i) {
        if (!d) {
            try {
                Field declaredField = View.class.getDeclaredField("mViewFlags");
                c = declaredField;
                declaredField.setAccessible(true);
            } catch (NoSuchFieldException unused) {
                Log.i("ViewUtilsApi19", "fetchViewFlagsField: ");
            }
            d = true;
        }
        Field field = c;
        if (field != null) {
            try {
                c.setInt(view, i | (field.getInt(view) & (-13)));
            } catch (IllegalAccessException unused2) {
            }
        }
    }

    public abstract boolean s(View view, int i);

    public void i(View view, int i) {
    }
}
