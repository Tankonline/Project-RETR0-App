package com.google.android.material.timepicker;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import moe.shizuku.privileged.api.R;
import rikka.shizuku.AbstractC0599sr;
import rikka.shizuku.C0241h7;
import rikka.shizuku.C0272i7;
import rikka.shizuku.C0394m7;
import rikka.shizuku.Hs;
import rikka.shizuku.InterfaceC0486p6;
import rikka.shizuku.Lk;
import rikka.shizuku.Qk;
import rikka.shizuku.X8;

/* JADX INFO: loaded from: classes.dex */
class ClockFaceView extends Qk implements InterfaceC0486p6 {
    public final int A;
    public final int B;
    public final int C;
    public final String[] D;
    public float E;
    public final ColorStateList F;
    public final ClockHandView r;
    public final Rect s;
    public final RectF t;
    public final Rect u;
    public final SparseArray v;
    public final c w;
    public final int[] x;
    public final float[] y;
    public final int z;

    public ClockFaceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.s = new Rect();
        this.t = new RectF();
        this.u = new Rect();
        SparseArray sparseArray = new SparseArray();
        this.v = sparseArray;
        this.y = new float[]{0.0f, 0.9f, 1.0f};
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Lk.g, R.attr._0_resource_name_obfuscated_res_0x7f040307, R.style._0_resource_name_obfuscated_res_0x7f1204c1);
        Resources resources = getResources();
        ColorStateList colorStateListW = X8.w(context, typedArrayObtainStyledAttributes, 1);
        this.F = colorStateListW;
        LayoutInflater.from(context).inflate(R.layout._0_resource_name_obfuscated_res_0x7f0c0059, (ViewGroup) this, true);
        ClockHandView clockHandView = (ClockHandView) findViewById(R.id._0_resource_name_obfuscated_res_0x7f0900f1);
        this.r = clockHandView;
        this.z = resources.getDimensionPixelSize(R.dimen._0_resource_name_obfuscated_res_0x7f070244);
        int colorForState = colorStateListW.getColorForState(new int[]{android.R.attr.state_selected}, colorStateListW.getDefaultColor());
        this.x = new int[]{colorForState, colorForState, colorStateListW.getDefaultColor()};
        clockHandView.c.add(this);
        int defaultColor = AbstractC0599sr.o(context, R.color._0_resource_name_obfuscated_res_0x7f0603e6).getDefaultColor();
        ColorStateList colorStateListW2 = X8.w(context, typedArrayObtainStyledAttributes, 0);
        setBackgroundColor(colorStateListW2 != null ? colorStateListW2.getDefaultColor() : defaultColor);
        getViewTreeObserver().addOnPreDrawListener(new b(this));
        setFocusable(true);
        typedArrayObtainStyledAttributes.recycle();
        this.w = new c(this);
        String[] strArr = new String[12];
        Arrays.fill(strArr, "");
        this.D = strArr;
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(getContext());
        int size = sparseArray.size();
        boolean z = false;
        for (int i = 0; i < Math.max(this.D.length, size); i++) {
            TextView textView = (TextView) sparseArray.get(i);
            if (i >= this.D.length) {
                removeView(textView);
                sparseArray.remove(i);
            } else {
                if (textView == null) {
                    textView = (TextView) layoutInflaterFrom.inflate(R.layout._0_resource_name_obfuscated_res_0x7f0c0058, (ViewGroup) this, false);
                    sparseArray.put(i, textView);
                    addView(textView);
                }
                textView.setText(this.D[i]);
                textView.setTag(R.id._0_resource_name_obfuscated_res_0x7f090101, Integer.valueOf(i));
                int i2 = (i / 12) + 1;
                textView.setTag(R.id._0_resource_name_obfuscated_res_0x7f0900f2, Integer.valueOf(i2));
                z = i2 > 1 ? true : z;
                Hs.m(textView, this.w);
                textView.setTextColor(this.F);
            }
        }
        ClockHandView clockHandView2 = this.r;
        if (clockHandView2.b && !z) {
            clockHandView2.m = 1;
        }
        clockHandView2.b = z;
        clockHandView2.invalidate();
        this.A = resources.getDimensionPixelSize(R.dimen._0_resource_name_obfuscated_res_0x7f070260);
        this.B = resources.getDimensionPixelSize(R.dimen._0_resource_name_obfuscated_res_0x7f070261);
        this.C = resources.getDimensionPixelSize(R.dimen._0_resource_name_obfuscated_res_0x7f07024b);
    }

    @Override // rikka.shizuku.Qk
    public final void f() {
        C0394m7 c0394m7 = new C0394m7();
        c0394m7.b(this);
        HashMap map = new HashMap();
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt.getId() != R.id._0_resource_name_obfuscated_res_0x7f09006d && !"skip".equals(childAt.getTag())) {
                int i2 = (Integer) childAt.getTag(R.id._0_resource_name_obfuscated_res_0x7f0900f2);
                if (i2 == null) {
                    i2 = 1;
                }
                if (!map.containsKey(i2)) {
                    map.put(i2, new ArrayList());
                }
                ((List) map.get(i2)).add(childAt);
            }
        }
        for (Map.Entry entry : map.entrySet()) {
            List list = (List) entry.getValue();
            int iRound = ((Integer) entry.getKey()).intValue() == 2 ? Math.round(this.p * 0.66f) : this.p;
            Iterator it = list.iterator();
            float size = 0.0f;
            while (it.hasNext()) {
                int id = ((View) it.next()).getId();
                HashMap map2 = c0394m7.c;
                if (!map2.containsKey(Integer.valueOf(id))) {
                    map2.put(Integer.valueOf(id), new C0241h7());
                }
                C0272i7 c0272i7 = ((C0241h7) map2.get(Integer.valueOf(id))).d;
                c0272i7.w = R.id._0_resource_name_obfuscated_res_0x7f09006d;
                c0272i7.x = iRound;
                c0272i7.y = size;
                size += 360.0f / list.size();
            }
        }
        c0394m7.a(this);
        this.j = null;
        requestLayout();
        int i3 = 0;
        while (true) {
            SparseArray sparseArray = this.v;
            if (i3 >= sparseArray.size()) {
                return;
            }
            ((TextView) sparseArray.get(i3)).setVisibility(0);
            i3++;
        }
    }

    public final void g() {
        SparseArray sparseArray;
        RectF rectF;
        Rect rect;
        RectF rectF2 = this.r.g;
        float f = Float.MAX_VALUE;
        TextView textView = null;
        int i = 0;
        while (true) {
            sparseArray = this.v;
            int size = sparseArray.size();
            rectF = this.t;
            rect = this.s;
            if (i >= size) {
                break;
            }
            TextView textView2 = (TextView) sparseArray.get(i);
            if (textView2 != null) {
                textView2.getHitRect(rect);
                rectF.set(rect);
                rectF.union(rectF2);
                float fHeight = rectF.height() * rectF.width();
                if (fHeight < f) {
                    textView = textView2;
                    f = fHeight;
                }
            }
            i++;
        }
        for (int i2 = 0; i2 < sparseArray.size(); i2++) {
            TextView textView3 = (TextView) sparseArray.get(i2);
            if (textView3 != null) {
                textView3.setSelected(textView3 == textView);
                textView3.getHitRect(rect);
                rectF.set(rect);
                Rect rect2 = this.u;
                textView3.getLineBounds(0, rect2);
                rectF.inset(rect2.left, rect2.top);
                textView3.getPaint().setShader(RectF.intersects(rectF2, rectF) ? new RadialGradient(rectF2.centerX() - rectF.left, rectF2.centerY() - rectF.top, 0.5f * rectF2.width(), this.x, this.y, Shader.TileMode.CLAMP) : null);
                textView3.invalidate();
            }
        }
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setCollectionInfo(AccessibilityNodeInfo.CollectionInfo.obtain(1, this.D.length, false, 1));
    }

    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        g();
    }

    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int iMax = (int) (this.C / Math.max(Math.max(this.A / displayMetrics.heightPixels, this.B / displayMetrics.widthPixels), 1.0f));
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(iMax, 1073741824);
        setMeasuredDimension(iMax, iMax);
        super.onMeasure(iMakeMeasureSpec, iMakeMeasureSpec);
    }
}
