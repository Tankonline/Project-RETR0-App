package rikka.shizuku;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.material.materialswitch.MaterialSwitch;
import moe.shizuku.privileged.api.R;

/* JADX INFO: loaded from: classes.dex */
public final class K2 {
    public final Object a;
    public final Object b;
    public final Object c;
    public final Object d;
    public final Object e;
    public final Object f;

    public K2(LinearLayout linearLayout, ImageView imageView, TextView textView, TextView textView2, MaterialSwitch materialSwitch, TextView textView3) {
        this.a = linearLayout;
        this.b = imageView;
        this.c = textView;
        this.d = textView2;
        this.e = materialSwitch;
        this.f = textView3;
    }

    public static boolean a(int[] iArr, int i) {
        for (int i2 : iArr) {
            if (i2 == i) {
                return true;
            }
        }
        return false;
    }

    public static ColorStateList b(Context context, int i) {
        int iC = Lp.c(context, R.attr._0_resource_name_obfuscated_res_0x7f0400f5);
        int iB = Lp.b(context, R.attr._0_resource_name_obfuscated_res_0x7f0400f2);
        int[] iArr = Lp.b;
        int[] iArr2 = Lp.d;
        int iC2 = E6.c(iC, i);
        return new ColorStateList(new int[][]{iArr, iArr2, Lp.c, Lp.f}, new int[]{iB, iC2, E6.c(iC, i), i});
    }

    public static LayerDrawable c(Tl tl, Context context, int i) {
        BitmapDrawable bitmapDrawable;
        BitmapDrawable bitmapDrawable2;
        BitmapDrawable bitmapDrawable3;
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(i);
        Drawable drawableD = tl.d(context, R.drawable._0_resource_name_obfuscated_res_0x7f08006a);
        Drawable drawableD2 = tl.d(context, R.drawable._0_resource_name_obfuscated_res_0x7f08006b);
        if ((drawableD instanceof BitmapDrawable) && drawableD.getIntrinsicWidth() == dimensionPixelSize && drawableD.getIntrinsicHeight() == dimensionPixelSize) {
            bitmapDrawable = (BitmapDrawable) drawableD;
            bitmapDrawable2 = new BitmapDrawable(bitmapDrawable.getBitmap());
        } else {
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(dimensionPixelSize, dimensionPixelSize, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            drawableD.setBounds(0, 0, dimensionPixelSize, dimensionPixelSize);
            drawableD.draw(canvas);
            bitmapDrawable = new BitmapDrawable(bitmapCreateBitmap);
            bitmapDrawable2 = new BitmapDrawable(bitmapCreateBitmap);
        }
        bitmapDrawable2.setTileModeX(Shader.TileMode.REPEAT);
        if ((drawableD2 instanceof BitmapDrawable) && drawableD2.getIntrinsicWidth() == dimensionPixelSize && drawableD2.getIntrinsicHeight() == dimensionPixelSize) {
            bitmapDrawable3 = (BitmapDrawable) drawableD2;
        } else {
            Bitmap bitmapCreateBitmap2 = Bitmap.createBitmap(dimensionPixelSize, dimensionPixelSize, Bitmap.Config.ARGB_8888);
            Canvas canvas2 = new Canvas(bitmapCreateBitmap2);
            drawableD2.setBounds(0, 0, dimensionPixelSize, dimensionPixelSize);
            drawableD2.draw(canvas2);
            bitmapDrawable3 = new BitmapDrawable(bitmapCreateBitmap2);
        }
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{bitmapDrawable, bitmapDrawable3, bitmapDrawable2});
        layerDrawable.setId(0, android.R.id.background);
        layerDrawable.setId(1, android.R.id.secondaryProgress);
        layerDrawable.setId(2, android.R.id.progress);
        return layerDrawable;
    }

    public static void e(Drawable drawable, int i, PorterDuff.Mode mode) {
        int[] iArr = AbstractC0304ja.a;
        Drawable drawableMutate = drawable.mutate();
        if (mode == null) {
            mode = L2.b;
        }
        drawableMutate.setColorFilter(L2.c(i, mode));
    }

    public ColorStateList d(Context context, int i) {
        if (i == R.drawable._0_resource_name_obfuscated_res_0x7f08003f) {
            return AbstractC0599sr.o(context, R.color._0_resource_name_obfuscated_res_0x7f060015);
        }
        if (i == R.drawable._0_resource_name_obfuscated_res_0x7f08006d) {
            return AbstractC0599sr.o(context, R.color._0_resource_name_obfuscated_res_0x7f060018);
        }
        if (i != R.drawable._0_resource_name_obfuscated_res_0x7f08006c) {
            if (i == R.drawable._0_resource_name_obfuscated_res_0x7f080033) {
                return b(context, Lp.c(context, R.attr._0_resource_name_obfuscated_res_0x7f0400f2));
            }
            if (i == R.drawable._0_resource_name_obfuscated_res_0x7f08002d) {
                return b(context, 0);
            }
            if (i == R.drawable._0_resource_name_obfuscated_res_0x7f080032) {
                return b(context, Lp.c(context, R.attr._0_resource_name_obfuscated_res_0x7f0400ef));
            }
            if (i == R.drawable._0_resource_name_obfuscated_res_0x7f080068 || i == R.drawable._0_resource_name_obfuscated_res_0x7f080069) {
                return AbstractC0599sr.o(context, R.color._0_resource_name_obfuscated_res_0x7f060017);
            }
            if (a((int[]) this.b, i)) {
                return Lp.d(context, R.attr._0_resource_name_obfuscated_res_0x7f0400f6);
            }
            if (a((int[]) this.e, i)) {
                return AbstractC0599sr.o(context, R.color._0_resource_name_obfuscated_res_0x7f060014);
            }
            if (a((int[]) this.f, i)) {
                return AbstractC0599sr.o(context, R.color._0_resource_name_obfuscated_res_0x7f060013);
            }
            if (i == R.drawable._0_resource_name_obfuscated_res_0x7f080065) {
                return AbstractC0599sr.o(context, R.color._0_resource_name_obfuscated_res_0x7f060016);
            }
            return null;
        }
        int[][] iArr = new int[3][];
        int[] iArr2 = new int[3];
        ColorStateList colorStateListD = Lp.d(context, R.attr._0_resource_name_obfuscated_res_0x7f040129);
        if (colorStateListD == null || !colorStateListD.isStateful()) {
            iArr[0] = Lp.b;
            iArr2[0] = Lp.b(context, R.attr._0_resource_name_obfuscated_res_0x7f040129);
            iArr[1] = Lp.e;
            iArr2[1] = Lp.c(context, R.attr._0_resource_name_obfuscated_res_0x7f0400f4);
            iArr[2] = Lp.f;
            iArr2[2] = Lp.c(context, R.attr._0_resource_name_obfuscated_res_0x7f040129);
        } else {
            int[] iArr3 = Lp.b;
            iArr[0] = iArr3;
            iArr2[0] = colorStateListD.getColorForState(iArr3, 0);
            iArr[1] = Lp.e;
            iArr2[1] = Lp.c(context, R.attr._0_resource_name_obfuscated_res_0x7f0400f4);
            iArr[2] = Lp.f;
            iArr2[2] = colorStateListD.getDefaultColor();
        }
        return new ColorStateList(iArr, iArr2);
    }

    public K2() {
        this.a = new int[]{R.drawable._0_resource_name_obfuscated_res_0x7f080077, R.drawable._0_resource_name_obfuscated_res_0x7f080075, R.drawable._0_resource_name_obfuscated_res_0x7f08002b};
        this.b = new int[]{R.drawable._0_resource_name_obfuscated_res_0x7f080043, R.drawable._0_resource_name_obfuscated_res_0x7f080066, R.drawable._0_resource_name_obfuscated_res_0x7f08004a, R.drawable._0_resource_name_obfuscated_res_0x7f080045, R.drawable._0_resource_name_obfuscated_res_0x7f080046, R.drawable._0_resource_name_obfuscated_res_0x7f080049, R.drawable._0_resource_name_obfuscated_res_0x7f080048};
        this.c = new int[]{R.drawable._0_resource_name_obfuscated_res_0x7f080074, R.drawable._0_resource_name_obfuscated_res_0x7f080076, R.drawable._0_resource_name_obfuscated_res_0x7f08003c, R.drawable._0_resource_name_obfuscated_res_0x7f080070, R.drawable._0_resource_name_obfuscated_res_0x7f080071, R.drawable._0_resource_name_obfuscated_res_0x7f080072, R.drawable._0_resource_name_obfuscated_res_0x7f080073};
        this.d = new int[]{R.drawable._0_resource_name_obfuscated_res_0x7f08005c, R.drawable._0_resource_name_obfuscated_res_0x7f08003a, R.drawable._0_resource_name_obfuscated_res_0x7f08005b};
        this.e = new int[]{R.drawable._0_resource_name_obfuscated_res_0x7f08006e, R.drawable._0_resource_name_obfuscated_res_0x7f080078};
        this.f = new int[]{R.drawable._0_resource_name_obfuscated_res_0x7f08002e, R.drawable._0_resource_name_obfuscated_res_0x7f080034, R.drawable._0_resource_name_obfuscated_res_0x7f08002f, R.drawable._0_resource_name_obfuscated_res_0x7f080035};
    }
}
