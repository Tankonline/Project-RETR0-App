package rikka.shizuku;

import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.PathInterpolator;
import java.util.List;
import moe.shizuku.privileged.api.R;

/* JADX INFO: loaded from: classes.dex */
public final class Ft extends It {
    public static final PathInterpolator e = new PathInterpolator(0.0f, 1.1f, 0.0f, 1.0f);
    public static final Pb f = new Pb(0);
    public static final DecelerateInterpolator g = new DecelerateInterpolator(1.5f);
    public static final AccelerateInterpolator h = new AccelerateInterpolator(1.5f);

    public static void e(View view) {
        C0796z7 c0796z7J = j(view);
        if (c0796z7J != null) {
            ((View) c0796z7J.e).setTranslationY(0.0f);
            return;
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                e(viewGroup.getChildAt(i));
            }
        }
    }

    public static void f(View view, au auVar, boolean z) {
        C0796z7 c0796z7J = j(view);
        if (c0796z7J != null) {
            c0796z7J.b = auVar;
            if (!z) {
                View view2 = (View) c0796z7J.e;
                int[] iArr = (int[]) c0796z7J.f;
                view2.getLocationOnScreen(iArr);
                z = true;
                c0796z7J.c = iArr[1];
            }
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                f(viewGroup.getChildAt(i), auVar, z);
            }
        }
    }

    public static void g(View view, au auVar, List list) {
        C0796z7 c0796z7J = j(view);
        if (c0796z7J != null) {
            c0796z7J.a(auVar, list);
            return;
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                g(viewGroup.getChildAt(i), auVar, list);
            }
        }
    }

    public static void h(View view, R2 r2) {
        C0796z7 c0796z7J = j(view);
        if (c0796z7J != null) {
            View view2 = (View) c0796z7J.e;
            int[] iArr = (int[]) c0796z7J.f;
            view2.getLocationOnScreen(iArr);
            int i = c0796z7J.c - iArr[1];
            c0796z7J.d = i;
            view2.setTranslationY(i);
            return;
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i2 = 0; i2 < viewGroup.getChildCount(); i2++) {
                h(viewGroup.getChildAt(i2), r2);
            }
        }
    }

    public static WindowInsets i(View view, WindowInsets windowInsets) {
        return view.getTag(R.id._0_resource_name_obfuscated_res_0x7f0901bf) != null ? windowInsets : view.onApplyWindowInsets(windowInsets);
    }

    public static C0796z7 j(View view) {
        Object tag = view.getTag(R.id._0_resource_name_obfuscated_res_0x7f0901ca);
        if (tag instanceof Et) {
            return ((Et) tag).a;
        }
        return null;
    }
}
