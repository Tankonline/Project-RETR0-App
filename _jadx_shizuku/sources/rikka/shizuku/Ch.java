package rikka.shizuku;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.Window;
import java.util.WeakHashMap;
import moe.shizuku.privileged.api.R;

/* JADX INFO: loaded from: classes.dex */
public final class Ch extends I1 {
    public final Xh c;
    public final Rect d;

    /* JADX WARN: Illegal instructions before constructor call */
    public Ch(Context context) {
        TypedValue typedValueM = AbstractC0046as.M(context, R.attr._0_resource_name_obfuscated_res_0x7f0402eb);
        int i = typedValueM == null ? 0 : typedValueM.data;
        Context contextC0 = X8.c0(context, null, R.attr._0_resource_name_obfuscated_res_0x7f04002c, R.style._0_resource_name_obfuscated_res_0x7f12014d);
        contextC0 = i != 0 ? new F7(contextC0, i) : contextC0;
        TypedValue typedValueM2 = AbstractC0046as.M(context, R.attr._0_resource_name_obfuscated_res_0x7f0402eb);
        super(contextC0, typedValueM2 == null ? 0 : typedValueM2.data);
        ContextThemeWrapper contextThemeWrapper = ((E1) this.b).a;
        Resources.Theme theme = contextThemeWrapper.getTheme();
        int[] iArr = Lk.l;
        X8.j(contextThemeWrapper, null, R.attr._0_resource_name_obfuscated_res_0x7f04002c, R.style._0_resource_name_obfuscated_res_0x7f12014d);
        X8.p(contextThemeWrapper, null, iArr, R.attr._0_resource_name_obfuscated_res_0x7f04002c, R.style._0_resource_name_obfuscated_res_0x7f12014d, new int[0]);
        TypedArray typedArrayObtainStyledAttributes = contextThemeWrapper.obtainStyledAttributes(null, iArr, R.attr._0_resource_name_obfuscated_res_0x7f04002c, R.style._0_resource_name_obfuscated_res_0x7f12014d);
        int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(2, contextThemeWrapper.getResources().getDimensionPixelSize(R.dimen._0_resource_name_obfuscated_res_0x7f070264));
        int dimensionPixelSize2 = typedArrayObtainStyledAttributes.getDimensionPixelSize(3, contextThemeWrapper.getResources().getDimensionPixelSize(R.dimen._0_resource_name_obfuscated_res_0x7f070265));
        int dimensionPixelSize3 = typedArrayObtainStyledAttributes.getDimensionPixelSize(1, contextThemeWrapper.getResources().getDimensionPixelSize(R.dimen._0_resource_name_obfuscated_res_0x7f070263));
        int dimensionPixelSize4 = typedArrayObtainStyledAttributes.getDimensionPixelSize(0, contextThemeWrapper.getResources().getDimensionPixelSize(R.dimen._0_resource_name_obfuscated_res_0x7f070262));
        typedArrayObtainStyledAttributes.recycle();
        if (contextThemeWrapper.getResources().getConfiguration().getLayoutDirection() == 1) {
            dimensionPixelSize3 = dimensionPixelSize;
            dimensionPixelSize = dimensionPixelSize3;
        }
        this.d = new Rect(dimensionPixelSize, dimensionPixelSize2, dimensionPixelSize3, dimensionPixelSize4);
        TypedValue typedValueQ = AbstractC0046as.Q(contextThemeWrapper, R.attr._0_resource_name_obfuscated_res_0x7f04011f, Ch.class.getCanonicalName());
        int i2 = typedValueQ.resourceId;
        int color = i2 != 0 ? contextThemeWrapper.getColor(i2) : typedValueQ.data;
        TypedArray typedArrayObtainStyledAttributes2 = contextThemeWrapper.obtainStyledAttributes(null, iArr, R.attr._0_resource_name_obfuscated_res_0x7f04002c, R.style._0_resource_name_obfuscated_res_0x7f12014d);
        int color2 = typedArrayObtainStyledAttributes2.getColor(4, color);
        typedArrayObtainStyledAttributes2.recycle();
        Xh xh = new Xh(contextThemeWrapper, null, R.attr._0_resource_name_obfuscated_res_0x7f04002c, R.style._0_resource_name_obfuscated_res_0x7f12014d);
        xh.j(contextThemeWrapper);
        xh.l(ColorStateList.valueOf(color2));
        if (Build.VERSION.SDK_INT >= 28) {
            TypedValue typedValue = new TypedValue();
            theme.resolveAttribute(android.R.attr.dialogCornerRadius, typedValue, true);
            float dimension = typedValue.getDimension(((E1) this.b).a.getResources().getDisplayMetrics());
            if (typedValue.type == 5 && dimension >= 0.0f) {
                C0317jn c0317jnG = xh.a.a.g();
                c0317jnG.e = new G(dimension);
                c0317jnG.f = new G(dimension);
                c0317jnG.g = new G(dimension);
                c0317jnG.h = new G(dimension);
                xh.a(c0317jnG.a());
            }
        }
        this.c = xh;
    }

    @Override // rikka.shizuku.I1
    public final J1 b() {
        J1 j1B = super.b();
        Window window = j1B.getWindow();
        View decorView = window.getDecorView();
        Xh xh = this.c;
        if (xh != null) {
            WeakHashMap weakHashMap = Hs.a;
            xh.k(AbstractC0817zs.e(decorView));
        }
        Rect rect = this.d;
        window.setBackgroundDrawable(new InsetDrawable((Drawable) xh, rect.left, rect.top, rect.right, rect.bottom));
        decorView.setOnTouchListener(new ViewOnTouchListenerC0556rf(j1B, rect));
        return j1B;
    }

    @Override // rikka.shizuku.I1
    public final void e(CharSequence charSequence, DialogInterface.OnClickListener onClickListener) {
        super.e(null, null);
    }

    public final void g(int i, DialogInterface.OnClickListener onClickListener) {
        E1 e1 = (E1) this.b;
        e1.g = e1.a.getText(i);
        e1.h = onClickListener;
    }
}
