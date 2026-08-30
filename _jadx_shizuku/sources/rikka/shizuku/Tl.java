package rikka.shizuku;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.TypedValue;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;
import moe.shizuku.privileged.api.R;

/* JADX INFO: loaded from: classes.dex */
public final class Tl {
    public static Tl g;
    public WeakHashMap a;
    public final WeakHashMap b = new WeakHashMap(0);
    public TypedValue c;
    public boolean d;
    public K2 e;
    public static final PorterDuff.Mode f = PorterDuff.Mode.SRC_IN;
    public static final E3 h = new E3(6, 1);

    public static synchronized Tl b() {
        try {
            if (g == null) {
                g = new Tl();
            }
        } catch (Throwable th) {
            throw th;
        }
        return g;
    }

    public static synchronized PorterDuffColorFilter e(int i, PorterDuff.Mode mode) {
        PorterDuffColorFilter porterDuffColorFilter;
        E3 e3 = h;
        e3.getClass();
        int i2 = (31 + i) * 31;
        porterDuffColorFilter = (PorterDuffColorFilter) e3.f(Integer.valueOf(mode.hashCode() + i2));
        if (porterDuffColorFilter == null) {
            porterDuffColorFilter = new PorterDuffColorFilter(i, mode);
        }
        return porterDuffColorFilter;
    }

    public final Drawable a(Context context, int i) {
        Drawable drawableNewDrawable;
        WeakReference weakReference;
        if (this.c == null) {
            this.c = new TypedValue();
        }
        TypedValue typedValue = this.c;
        context.getResources().getValue(i, typedValue, true);
        long j = (((long) typedValue.assetCookie) << 32) | ((long) typedValue.data);
        synchronized (this) {
            C0620th c0620th = (C0620th) this.b.get(context);
            drawableNewDrawable = null;
            if (c0620th != null && (weakReference = (WeakReference) c0620th.b(j)) != null) {
                Drawable.ConstantState constantState = (Drawable.ConstantState) weakReference.get();
                if (constantState != null) {
                    drawableNewDrawable = constantState.newDrawable(context.getResources());
                } else {
                    int iE = AbstractC0744xh.e(c0620th.b, c0620th.d, j);
                    if (iE >= 0) {
                        Object[] objArr = c0620th.c;
                        Object obj = objArr[iE];
                        Object obj2 = AbstractC0744xh.g;
                        if (obj != obj2) {
                            objArr[iE] = obj2;
                            c0620th.a = true;
                        }
                    }
                }
            }
        }
        if (drawableNewDrawable != null) {
            return drawableNewDrawable;
        }
        LayerDrawable layerDrawableC = null;
        if (this.e != null) {
            if (i == R.drawable._0_resource_name_obfuscated_res_0x7f08003b) {
                layerDrawableC = new LayerDrawable(new Drawable[]{d(context, R.drawable._0_resource_name_obfuscated_res_0x7f08003a), d(context, R.drawable._0_resource_name_obfuscated_res_0x7f08003c)});
            } else if (i == R.drawable._0_resource_name_obfuscated_res_0x7f08005e) {
                layerDrawableC = K2.c(this, context, R.dimen._0_resource_name_obfuscated_res_0x7f07003b);
            } else if (i == R.drawable._0_resource_name_obfuscated_res_0x7f08005d) {
                layerDrawableC = K2.c(this, context, R.dimen._0_resource_name_obfuscated_res_0x7f07003c);
            } else if (i == R.drawable._0_resource_name_obfuscated_res_0x7f08005f) {
                layerDrawableC = K2.c(this, context, R.dimen._0_resource_name_obfuscated_res_0x7f07003d);
            }
        }
        if (layerDrawableC == null) {
            return layerDrawableC;
        }
        layerDrawableC.setChangingConfigurations(typedValue.changingConfigurations);
        synchronized (this) {
            try {
                Drawable.ConstantState constantState2 = layerDrawableC.getConstantState();
                if (constantState2 != null) {
                    C0620th c0620th2 = (C0620th) this.b.get(context);
                    if (c0620th2 == null) {
                        c0620th2 = new C0620th();
                        this.b.put(context, c0620th2);
                    }
                    c0620th2.d(j, new WeakReference(constantState2));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return layerDrawableC;
    }

    public final synchronized Drawable c(int i, Context context, boolean z) {
        Drawable drawableA;
        try {
            if (!this.d) {
                this.d = true;
                Drawable drawableD = d(context, R.drawable._0_resource_name_obfuscated_res_0x7f080079);
                if (drawableD == null || (!(drawableD instanceof C0384ls) && !"android.graphics.drawable.VectorDrawable".equals(drawableD.getClass().getName()))) {
                    this.d = false;
                    throw new IllegalStateException("This app has been built with an incorrect configuration. Please configure your build for VectorDrawableCompat.");
                }
            }
            drawableA = a(context, i);
            if (drawableA == null) {
                drawableA = context.getDrawable(i);
            }
            if (drawableA != null) {
                drawableA = g(context, i, z, drawableA);
            }
            if (drawableA != null) {
                AbstractC0304ja.a(drawableA);
            }
        } catch (Throwable th) {
            throw th;
        }
        return drawableA;
    }

    public final synchronized Drawable d(Context context, int i) {
        return c(i, context, false);
    }

    public final synchronized ColorStateList f(Context context, int i) {
        ColorStateList colorStateList;
        C0689vo c0689vo;
        Object obj;
        WeakHashMap weakHashMap = this.a;
        ColorStateList colorStateListD = null;
        if (weakHashMap == null || (c0689vo = (C0689vo) weakHashMap.get(context)) == null) {
            colorStateList = null;
        } else {
            int iD = AbstractC0744xh.d(c0689vo.c, i, c0689vo.a);
            if (iD < 0 || (obj = c0689vo.b[iD]) == AbstractC0744xh.i) {
                obj = null;
            }
            colorStateList = (ColorStateList) obj;
        }
        if (colorStateList == null) {
            K2 k2 = this.e;
            if (k2 != null) {
                colorStateListD = k2.d(context, i);
            }
            if (colorStateListD != null) {
                if (this.a == null) {
                    this.a = new WeakHashMap();
                }
                C0689vo c0689vo2 = (C0689vo) this.a.get(context);
                if (c0689vo2 == null) {
                    c0689vo2 = new C0689vo();
                    this.a.put(context, c0689vo2);
                }
                c0689vo2.a(i, colorStateListD);
            }
            colorStateList = colorStateListD;
        }
        return colorStateList;
    }

    public final Drawable g(Context context, int i, boolean z, Drawable drawable) {
        boolean z2;
        int iRound;
        int i2 = R.attr._0_resource_name_obfuscated_res_0x7f0400f6;
        int i3 = R.attr._0_resource_name_obfuscated_res_0x7f0400f4;
        ColorStateList colorStateListF = f(context, i);
        PorterDuff.Mode mode = null;
        if (colorStateListF != null) {
            int[] iArr = AbstractC0304ja.a;
            Drawable drawableMutate = drawable.mutate();
            drawableMutate.setTintList(colorStateListF);
            if (this.e != null && i == R.drawable._0_resource_name_obfuscated_res_0x7f08006c) {
                mode = PorterDuff.Mode.MULTIPLY;
            }
            if (mode != null) {
                drawableMutate.setTintMode(mode);
            }
            return drawableMutate;
        }
        if (this.e != null) {
            if (i == R.drawable._0_resource_name_obfuscated_res_0x7f080067) {
                LayerDrawable layerDrawable = (LayerDrawable) drawable;
                Drawable drawableFindDrawableByLayerId = layerDrawable.findDrawableByLayerId(android.R.id.background);
                int iC = Lp.c(context, R.attr._0_resource_name_obfuscated_res_0x7f0400f6);
                PorterDuff.Mode mode2 = L2.b;
                K2.e(drawableFindDrawableByLayerId, iC, mode2);
                K2.e(layerDrawable.findDrawableByLayerId(android.R.id.secondaryProgress), Lp.c(context, R.attr._0_resource_name_obfuscated_res_0x7f0400f6), mode2);
                K2.e(layerDrawable.findDrawableByLayerId(android.R.id.progress), Lp.c(context, R.attr._0_resource_name_obfuscated_res_0x7f0400f4), mode2);
                return drawable;
            }
            if (i == R.drawable._0_resource_name_obfuscated_res_0x7f08005e || i == R.drawable._0_resource_name_obfuscated_res_0x7f08005d || i == R.drawable._0_resource_name_obfuscated_res_0x7f08005f) {
                LayerDrawable layerDrawable2 = (LayerDrawable) drawable;
                Drawable drawableFindDrawableByLayerId2 = layerDrawable2.findDrawableByLayerId(android.R.id.background);
                int iB = Lp.b(context, R.attr._0_resource_name_obfuscated_res_0x7f0400f6);
                PorterDuff.Mode mode3 = L2.b;
                K2.e(drawableFindDrawableByLayerId2, iB, mode3);
                K2.e(layerDrawable2.findDrawableByLayerId(android.R.id.secondaryProgress), Lp.c(context, R.attr._0_resource_name_obfuscated_res_0x7f0400f4), mode3);
                K2.e(layerDrawable2.findDrawableByLayerId(android.R.id.progress), Lp.c(context, R.attr._0_resource_name_obfuscated_res_0x7f0400f4), mode3);
                return drawable;
            }
        }
        K2 k2 = this.e;
        boolean z3 = false;
        if (k2 != null) {
            PorterDuff.Mode mode4 = L2.b;
            if (K2.a((int[]) k2.a, i)) {
                z2 = true;
                iRound = -1;
            } else {
                if (!K2.a((int[]) k2.c, i)) {
                    boolean zA = K2.a((int[]) k2.d, i);
                    i3 = android.R.attr.colorBackground;
                    if (zA) {
                        mode4 = PorterDuff.Mode.MULTIPLY;
                    } else if (i == R.drawable._0_resource_name_obfuscated_res_0x7f080050) {
                        iRound = Math.round(40.8f);
                        i2 = android.R.attr.colorForeground;
                        z2 = true;
                    } else {
                        if (i != R.drawable._0_resource_name_obfuscated_res_0x7f08003e) {
                            i2 = 0;
                            z2 = false;
                        }
                        iRound = -1;
                    }
                }
                i2 = i3;
                z2 = true;
                iRound = -1;
            }
            if (z2) {
                int[] iArr2 = AbstractC0304ja.a;
                Drawable drawableMutate2 = drawable.mutate();
                drawableMutate2.setColorFilter(L2.c(Lp.c(context, i2), mode4));
                if (iRound != -1) {
                    drawableMutate2.setAlpha(iRound);
                }
                z3 = true;
            }
        }
        if (z3 || !z) {
            return drawable;
        }
        return null;
    }
}
