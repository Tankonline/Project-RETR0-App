package com.google.android.material.appbar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import java.util.ArrayList;
import java.util.Collections;
import java.util.WeakHashMap;
import moe.shizuku.privileged.api.R;
import rikka.shizuku.AbstractC0046as;
import rikka.shizuku.AbstractC0599sr;
import rikka.shizuku.AbstractC0817zs;
import rikka.shizuku.Hs;
import rikka.shizuku.Lk;
import rikka.shizuku.MenuC0129di;
import rikka.shizuku.O2;
import rikka.shizuku.Q7;
import rikka.shizuku.X8;
import rikka.shizuku.Xh;

/* JADX INFO: loaded from: classes.dex */
public class MaterialToolbar extends Toolbar {
    public static final ImageView.ScaleType[] b0 = {ImageView.ScaleType.MATRIX, ImageView.ScaleType.FIT_XY, ImageView.ScaleType.FIT_START, ImageView.ScaleType.FIT_CENTER, ImageView.ScaleType.FIT_END, ImageView.ScaleType.CENTER, ImageView.ScaleType.CENTER_CROP, ImageView.ScaleType.CENTER_INSIDE};
    public final Integer T;
    public final boolean U;
    public final boolean V;
    public final ImageView.ScaleType W;
    public final Boolean a0;

    public MaterialToolbar(Context context, AttributeSet attributeSet) {
        super(X8.c0(context, attributeSet, R.attr._0_resource_name_obfuscated_res_0x7f040532, R.style._0_resource_name_obfuscated_res_0x7f1204c9), attributeSet, 0);
        Context context2 = getContext();
        TypedArray typedArrayJ = X8.J(context2, attributeSet, Lk.y, R.attr._0_resource_name_obfuscated_res_0x7f040532, R.style._0_resource_name_obfuscated_res_0x7f1204c9, new int[0]);
        if (typedArrayJ.hasValue(2)) {
            this.T = Integer.valueOf(typedArrayJ.getColor(2, -1));
            Drawable drawableN = n();
            if (drawableN != null) {
                y(drawableN);
            }
        }
        this.U = typedArrayJ.getBoolean(4, false);
        this.V = typedArrayJ.getBoolean(3, false);
        int i = typedArrayJ.getInt(1, -1);
        if (i >= 0) {
            ImageView.ScaleType[] scaleTypeArr = b0;
            if (i < scaleTypeArr.length) {
                this.W = scaleTypeArr[i];
            }
        }
        if (typedArrayJ.hasValue(0)) {
            this.a0 = Boolean.valueOf(typedArrayJ.getBoolean(0, false));
        }
        typedArrayJ.recycle();
        Drawable background = getBackground();
        ColorStateList colorStateListValueOf = background == null ? ColorStateList.valueOf(0) : AbstractC0046as.u(background);
        if (colorStateListValueOf != null) {
            Xh xh = new Xh();
            xh.l(colorStateListValueOf);
            xh.j(context2);
            WeakHashMap weakHashMap = Hs.a;
            xh.k(AbstractC0817zs.e(this));
            setBackground(xh);
        }
    }

    public final void E(TextView textView, Pair pair) {
        int measuredWidth = getMeasuredWidth();
        int measuredWidth2 = textView.getMeasuredWidth();
        int i = (measuredWidth / 2) - (measuredWidth2 / 2);
        int i2 = measuredWidth2 + i;
        int iMax = Math.max(Math.max(((Integer) pair.first).intValue() - i, 0), Math.max(i2 - ((Integer) pair.second).intValue(), 0));
        if (iMax > 0) {
            i += iMax;
            i2 -= iMax;
            textView.measure(View.MeasureSpec.makeMeasureSpec(i2 - i, 1073741824), textView.getMeasuredHeightAndState());
        }
        textView.layout(i, textView.getTop(), i2, textView.getBottom());
    }

    @Override // androidx.appcompat.widget.Toolbar, android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        Drawable background = getBackground();
        if (background instanceof Xh) {
            AbstractC0599sr.H(this, (Xh) background);
        }
    }

    @Override // androidx.appcompat.widget.Toolbar, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        ImageView imageView;
        Drawable drawable;
        super.onLayout(z, i, i2, i3, i4);
        boolean z2 = this.V;
        boolean z3 = this.U;
        ImageView imageView2 = null;
        if (z3 || z2) {
            ArrayList arrayListB = AbstractC0046as.B(this, this.x);
            boolean zIsEmpty = arrayListB.isEmpty();
            Q7 q7 = AbstractC0046as.h;
            TextView textView = zIsEmpty ? null : (TextView) Collections.min(arrayListB, q7);
            ArrayList arrayListB2 = AbstractC0046as.B(this, this.y);
            TextView textView2 = arrayListB2.isEmpty() ? null : (TextView) Collections.max(arrayListB2, q7);
            if (textView != null || textView2 != null) {
                int measuredWidth = getMeasuredWidth();
                int i5 = measuredWidth / 2;
                int paddingLeft = getPaddingLeft();
                int paddingRight = measuredWidth - getPaddingRight();
                for (int i6 = 0; i6 < getChildCount(); i6++) {
                    View childAt = getChildAt(i6);
                    if (childAt.getVisibility() != 8 && childAt != textView && childAt != textView2) {
                        if (childAt.getRight() < i5 && childAt.getRight() > paddingLeft) {
                            paddingLeft = childAt.getRight();
                        }
                        if (childAt.getLeft() > i5 && childAt.getLeft() < paddingRight) {
                            paddingRight = childAt.getLeft();
                        }
                    }
                }
                Pair pair = new Pair(Integer.valueOf(paddingLeft), Integer.valueOf(paddingRight));
                if (z3 && textView != null) {
                    E(textView, pair);
                }
                if (z2 && textView2 != null) {
                    E(textView2, pair);
                }
            }
        }
        O2 o2 = this.e;
        Drawable drawable2 = o2 != null ? o2.getDrawable() : null;
        if (drawable2 != null) {
            for (int i7 = 0; i7 < getChildCount(); i7++) {
                View childAt2 = getChildAt(i7);
                if ((childAt2 instanceof ImageView) && (drawable = (imageView = (ImageView) childAt2).getDrawable()) != null && drawable.getConstantState() != null && drawable.getConstantState().equals(drawable2.getConstantState())) {
                    imageView2 = imageView;
                    break;
                }
            }
        }
        if (imageView2 != null) {
            Boolean bool = this.a0;
            if (bool != null) {
                imageView2.setAdjustViewBounds(bool.booleanValue());
            }
            ImageView.ScaleType scaleType = this.W;
            if (scaleType != null) {
                imageView2.setScaleType(scaleType);
            }
        }
    }

    @Override // androidx.appcompat.widget.Toolbar
    public final void p(int i) {
        MenuC0129di menuC0129diM = m();
        if (menuC0129diM != null) {
            menuC0129diM.w();
        }
        super.p(i);
        if (menuC0129diM != null) {
            menuC0129diM.v();
        }
    }

    @Override // android.view.View
    public final void setElevation(float f) {
        super.setElevation(f);
        Drawable background = getBackground();
        if (background instanceof Xh) {
            ((Xh) background).k(f);
        }
    }

    @Override // androidx.appcompat.widget.Toolbar
    public final void y(Drawable drawable) {
        if (drawable != null && this.T != null) {
            drawable = drawable.mutate();
            drawable.setTint(this.T.intValue());
        }
        super.y(drawable);
    }
}
