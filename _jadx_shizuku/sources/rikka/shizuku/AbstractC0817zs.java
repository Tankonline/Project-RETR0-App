package rikka.shizuku;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.WindowInsets;
import moe.shizuku.privileged.api.R;

/* JADX INFO: renamed from: rikka.shizuku.zs, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0817zs {
    public static void a(WindowInsets windowInsets, View view) {
        View.OnApplyWindowInsetsListener onApplyWindowInsetsListener = (View.OnApplyWindowInsetsListener) view.getTag(R.id._0_resource_name_obfuscated_res_0x7f0901ca);
        if (onApplyWindowInsetsListener != null) {
            onApplyWindowInsetsListener.onApplyWindowInsets(view, windowInsets);
        }
    }

    public static au b(View view, au auVar, Rect rect) {
        WindowInsets windowInsetsD = auVar.d();
        if (windowInsetsD != null) {
            return au.e(view, view.computeSystemWindowInsets(windowInsetsD, rect));
        }
        rect.setEmpty();
        return auVar;
    }

    public static ColorStateList c(View view) {
        return view.getBackgroundTintList();
    }

    public static PorterDuff.Mode d(View view) {
        return view.getBackgroundTintMode();
    }

    public static float e(View view) {
        return view.getElevation();
    }

    public static String f(View view) {
        return view.getTransitionName();
    }

    public static float g(View view) {
        return view.getZ();
    }

    public static boolean h(View view) {
        return view.isNestedScrollingEnabled();
    }

    public static void i(View view, ColorStateList colorStateList) {
        view.setBackgroundTintList(colorStateList);
    }

    public static void j(View view, PorterDuff.Mode mode) {
        view.setBackgroundTintMode(mode);
    }

    public static void k(View view, float f) {
        view.setElevation(f);
    }

    public static void l(View view, InterfaceC0344kj interfaceC0344kj) {
        ViewOnApplyWindowInsetsListenerC0786ys viewOnApplyWindowInsetsListenerC0786ys = interfaceC0344kj != null ? new ViewOnApplyWindowInsetsListenerC0786ys(view, interfaceC0344kj) : null;
        if (Build.VERSION.SDK_INT < 30) {
            view.setTag(R.id._0_resource_name_obfuscated_res_0x7f0901bf, viewOnApplyWindowInsetsListenerC0786ys);
        }
        if (view.getTag(R.id._0_resource_name_obfuscated_res_0x7f0901be) != null) {
            return;
        }
        if (viewOnApplyWindowInsetsListenerC0786ys != null) {
            view.setOnApplyWindowInsetsListener(viewOnApplyWindowInsetsListenerC0786ys);
        } else {
            view.setOnApplyWindowInsetsListener((View.OnApplyWindowInsetsListener) view.getTag(R.id._0_resource_name_obfuscated_res_0x7f0901ca));
        }
    }

    public static void m(View view) {
        view.stopNestedScroll();
    }
}
