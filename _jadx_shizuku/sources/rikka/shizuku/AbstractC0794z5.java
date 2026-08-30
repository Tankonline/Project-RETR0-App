package rikka.shizuku;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/* JADX INFO: renamed from: rikka.shizuku.z5, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0794z5 extends FrameLayout {
    public static final int[] f = {R.attr.colorBackground};
    public final boolean a;
    public final boolean b;
    public final Rect c;
    public final Rect d;
    public final R2 e;

    public AbstractC0794z5(Context context, AttributeSet attributeSet) {
        ColorStateList colorStateListValueOf;
        super(context, attributeSet, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f040305);
        Rect rect = new Rect();
        this.c = rect;
        this.d = new Rect();
        R2 r2 = new R2(this);
        this.e = r2;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Ek.a, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f040305, moe.shizuku.privileged.api.R.style._0_resource_name_obfuscated_res_0x7f12013b);
        if (typedArrayObtainStyledAttributes.hasValue(2)) {
            colorStateListValueOf = typedArrayObtainStyledAttributes.getColorStateList(2);
        } else {
            TypedArray typedArrayObtainStyledAttributes2 = getContext().obtainStyledAttributes(f);
            int color = typedArrayObtainStyledAttributes2.getColor(0, 0);
            typedArrayObtainStyledAttributes2.recycle();
            float[] fArr = new float[3];
            Color.colorToHSV(color, fArr);
            colorStateListValueOf = ColorStateList.valueOf(fArr[2] > 0.5f ? getResources().getColor(moe.shizuku.privileged.api.R.color._0_resource_name_obfuscated_res_0x7f060034) : getResources().getColor(moe.shizuku.privileged.api.R.color._0_resource_name_obfuscated_res_0x7f060033));
        }
        float dimension = typedArrayObtainStyledAttributes.getDimension(3, 0.0f);
        float dimension2 = typedArrayObtainStyledAttributes.getDimension(4, 0.0f);
        float dimension3 = typedArrayObtainStyledAttributes.getDimension(5, 0.0f);
        boolean z = typedArrayObtainStyledAttributes.getBoolean(7, false);
        this.a = z;
        boolean z2 = typedArrayObtainStyledAttributes.getBoolean(6, true);
        this.b = z2;
        int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(8, 0);
        rect.left = typedArrayObtainStyledAttributes.getDimensionPixelSize(10, dimensionPixelSize);
        rect.top = typedArrayObtainStyledAttributes.getDimensionPixelSize(12, dimensionPixelSize);
        rect.right = typedArrayObtainStyledAttributes.getDimensionPixelSize(11, dimensionPixelSize);
        rect.bottom = typedArrayObtainStyledAttributes.getDimensionPixelSize(9, dimensionPixelSize);
        dimension3 = dimension2 > dimension3 ? dimension2 : dimension3;
        typedArrayObtainStyledAttributes.getDimensionPixelSize(0, 0);
        typedArrayObtainStyledAttributes.getDimensionPixelSize(1, 0);
        typedArrayObtainStyledAttributes.recycle();
        C0194fm c0194fm = new C0194fm(colorStateListValueOf, dimension);
        r2.b = c0194fm;
        setBackgroundDrawable(c0194fm);
        setClipToOutline(true);
        setElevation(dimension2);
        C0194fm c0194fm2 = (C0194fm) ((Drawable) r2.b);
        if (dimension3 != c0194fm2.e || c0194fm2.f != z || c0194fm2.g != z2) {
            c0194fm2.e = dimension3;
            c0194fm2.f = z;
            c0194fm2.g = z2;
            c0194fm2.b(null);
            c0194fm2.invalidateSelf();
        }
        if (!this.a) {
            r2.Y(0, 0, 0, 0);
            return;
        }
        C0194fm c0194fm3 = (C0194fm) ((Drawable) r2.b);
        float f2 = c0194fm3.e;
        float f3 = c0194fm3.a;
        int iCeil = (int) Math.ceil(AbstractC0225gm.a(f2, f3, this.b));
        int iCeil2 = (int) Math.ceil(AbstractC0225gm.b(f2, f3, this.b));
        r2.Y(iCeil, iCeil2, iCeil, iCeil2);
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
    }

    @Override // android.view.View
    public final void setPadding(int i, int i2, int i3, int i4) {
    }

    @Override // android.view.View
    public final void setPaddingRelative(int i, int i2, int i3, int i4) {
    }
}
