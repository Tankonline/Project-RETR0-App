package com.google.android.material.button;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ToggleButton;
import com.google.android.material.timepicker.d;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.WeakHashMap;
import moe.shizuku.privileged.api.R;
import rikka.shizuku.AbstractC0744xh;
import rikka.shizuku.AbstractC0750xn;
import rikka.shizuku.C0317jn;
import rikka.shizuku.G;
import rikka.shizuku.Gh;
import rikka.shizuku.Hh;
import rikka.shizuku.Hs;
import rikka.shizuku.Ih;
import rikka.shizuku.Lk;
import rikka.shizuku.R5;
import rikka.shizuku.Wh;
import rikka.shizuku.X8;
import rikka.shizuku.Xh;
import rikka.shizuku.Ys;

/* JADX INFO: loaded from: classes.dex */
public class MaterialButtonToggleGroup extends LinearLayout {
    public static final /* synthetic */ int k = 0;
    public final ArrayList a;
    public final Ys b;
    public final LinkedHashSet c;
    public final Hh d;
    public Integer[] e;
    public boolean f;
    public final boolean g;
    public final boolean h;
    public final int i;
    public HashSet j;

    public MaterialButtonToggleGroup(Context context, AttributeSet attributeSet) {
        super(X8.c0(context, attributeSet, R.attr._0_resource_name_obfuscated_res_0x7f0402f2, R.style._0_resource_name_obfuscated_res_0x7f120485), attributeSet, R.attr._0_resource_name_obfuscated_res_0x7f0402f2);
        this.a = new ArrayList();
        this.b = new Ys(22, this);
        this.c = new LinkedHashSet();
        this.d = new Hh(this);
        this.f = false;
        this.j = new HashSet();
        TypedArray typedArrayJ = X8.J(getContext(), attributeSet, Lk.o, R.attr._0_resource_name_obfuscated_res_0x7f0402f2, R.style._0_resource_name_obfuscated_res_0x7f120485, new int[0]);
        boolean z = typedArrayJ.getBoolean(3, false);
        if (this.g != z) {
            this.g = z;
            d(new HashSet());
        }
        for (int i = 0; i < getChildCount(); i++) {
            ((MaterialButton) getChildAt(i)).j = (this.g ? RadioButton.class : ToggleButton.class).getName();
        }
        this.i = typedArrayJ.getResourceId(1, -1);
        this.h = typedArrayJ.getBoolean(2, false);
        setChildrenDrawingOrderEnabled(true);
        setEnabled(typedArrayJ.getBoolean(0, true));
        typedArrayJ.recycle();
        WeakHashMap weakHashMap = Hs.a;
        setImportantForAccessibility(1);
    }

    public final void a() {
        int childCount = getChildCount();
        int i = 0;
        while (true) {
            if (i >= childCount) {
                i = -1;
                break;
            } else if (c(i)) {
                break;
            } else {
                i++;
            }
        }
        if (i == -1) {
            return;
        }
        for (int i2 = i + 1; i2 < getChildCount(); i2++) {
            MaterialButton materialButton = (MaterialButton) getChildAt(i2);
            MaterialButton materialButton2 = (MaterialButton) getChildAt(i2 - 1);
            int iMin = Math.min(materialButton.b() ? materialButton.d.g : 0, materialButton2.b() ? materialButton2.d.g : 0);
            ViewGroup.LayoutParams layoutParams = materialButton.getLayoutParams();
            LinearLayout.LayoutParams layoutParams2 = layoutParams instanceof LinearLayout.LayoutParams ? (LinearLayout.LayoutParams) layoutParams : new LinearLayout.LayoutParams(layoutParams.width, layoutParams.height);
            if (getOrientation() == 0) {
                layoutParams2.setMarginEnd(0);
                layoutParams2.setMarginStart(-iMin);
                layoutParams2.topMargin = 0;
            } else {
                layoutParams2.bottomMargin = 0;
                layoutParams2.topMargin = -iMin;
                layoutParams2.setMarginStart(0);
            }
            materialButton.setLayoutParams(layoutParams2);
        }
        if (getChildCount() == 0 || i == -1) {
            return;
        }
        LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) ((MaterialButton) getChildAt(i)).getLayoutParams();
        if (getOrientation() == 1) {
            layoutParams3.topMargin = 0;
            layoutParams3.bottomMargin = 0;
        } else {
            layoutParams3.setMarginEnd(0);
            layoutParams3.setMarginStart(0);
            layoutParams3.leftMargin = 0;
            layoutParams3.rightMargin = 0;
        }
    }

    @Override // android.view.ViewGroup
    public final void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        int i2 = 1;
        if (!(view instanceof MaterialButton)) {
            Log.e("MButtonToggleGroup", "Child views must be of type MaterialButton.");
            return;
        }
        super.addView(view, i, layoutParams);
        MaterialButton materialButton = (MaterialButton) view;
        if (materialButton.getId() == -1) {
            WeakHashMap weakHashMap = Hs.a;
            materialButton.setId(View.generateViewId());
        }
        materialButton.setMaxLines(1);
        materialButton.setEllipsize(TextUtils.TruncateAt.END);
        boolean zB = materialButton.b();
        Gh gh = materialButton.d;
        if (zB) {
            gh.o = true;
        }
        materialButton.f = this.b;
        if (materialButton.b()) {
            gh.m = true;
            Xh xhB = gh.b(false);
            Xh xhB2 = gh.b(true);
            if (xhB != null) {
                float f = gh.g;
                ColorStateList colorStateList = gh.j;
                xhB.a.j = f;
                xhB.invalidateSelf();
                Wh wh = xhB.a;
                if (wh.d != colorStateList) {
                    wh.d = colorStateList;
                    xhB.onStateChange(xhB.getState());
                }
                if (xhB2 != null) {
                    float f2 = gh.g;
                    int iS = gh.m ? AbstractC0744xh.s(gh.a, R.attr._0_resource_name_obfuscated_res_0x7f04011f) : 0;
                    xhB2.a.j = f2;
                    xhB2.invalidateSelf();
                    ColorStateList colorStateListValueOf = ColorStateList.valueOf(iS);
                    Wh wh2 = xhB2.a;
                    if (wh2.d != colorStateListValueOf) {
                        wh2.d = colorStateListValueOf;
                        xhB2.onStateChange(xhB2.getState());
                    }
                }
            }
        }
        b(materialButton.getId(), materialButton.o);
        if (!materialButton.b()) {
            throw new IllegalStateException("Attempted to get ShapeAppearanceModel from a MaterialButton which has an overwritten background.");
        }
        C0317jn c0317jn = gh.b;
        this.a.add(new Ih(c0317jn.e, c0317jn.h, c0317jn.f, c0317jn.g));
        materialButton.setEnabled(isEnabled());
        Hs.m(materialButton, new R5(i2, this));
    }

    public final void b(int i, boolean z) {
        if (i == -1) {
            Log.e("MButtonToggleGroup", "Button ID is not valid: " + i);
            return;
        }
        HashSet hashSet = new HashSet(this.j);
        if (z && !hashSet.contains(Integer.valueOf(i))) {
            if (this.g && !hashSet.isEmpty()) {
                hashSet.clear();
            }
            hashSet.add(Integer.valueOf(i));
        } else {
            if (z || !hashSet.contains(Integer.valueOf(i))) {
                return;
            }
            if (!this.h || hashSet.size() > 1) {
                hashSet.remove(Integer.valueOf(i));
            }
        }
        d(hashSet);
    }

    public final boolean c(int i) {
        return getChildAt(i).getVisibility() != 8;
    }

    public final void d(Set set) {
        HashSet hashSet = this.j;
        this.j = new HashSet(set);
        for (int i = 0; i < getChildCount(); i++) {
            int id = ((MaterialButton) getChildAt(i)).getId();
            boolean zContains = set.contains(Integer.valueOf(id));
            View viewFindViewById = findViewById(id);
            if (viewFindViewById instanceof MaterialButton) {
                this.f = true;
                ((MaterialButton) viewFindViewById).setChecked(zContains);
                this.f = false;
            }
            if (hashSet.contains(Integer.valueOf(id)) != set.contains(Integer.valueOf(id))) {
                set.contains(Integer.valueOf(id));
                Iterator it = this.c.iterator();
                while (it.hasNext()) {
                    ((d) it.next()).a();
                }
            }
        }
        invalidate();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchDraw(Canvas canvas) {
        TreeMap treeMap = new TreeMap(this.d);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            treeMap.put((MaterialButton) getChildAt(i), Integer.valueOf(i));
        }
        this.e = (Integer[]) treeMap.values().toArray(new Integer[0]);
        super.dispatchDraw(canvas);
    }

    public final void e() {
        int i;
        Ih ih;
        int childCount = getChildCount();
        int childCount2 = getChildCount();
        int i2 = 0;
        while (true) {
            i = -1;
            if (i2 >= childCount2) {
                i2 = -1;
                break;
            } else if (c(i2)) {
                break;
            } else {
                i2++;
            }
        }
        for (int childCount3 = getChildCount() - 1; childCount3 >= 0; childCount3--) {
            if (c(childCount3)) {
                i = childCount3;
                break;
            }
        }
        for (int i3 = 0; i3 < childCount; i3++) {
            MaterialButton materialButton = (MaterialButton) getChildAt(i3);
            if (materialButton.getVisibility() != 8) {
                if (!materialButton.b()) {
                    throw new IllegalStateException("Attempted to get ShapeAppearanceModel from a MaterialButton which has an overwritten background.");
                }
                C0317jn c0317jnG = materialButton.d.b.g();
                Ih ih2 = (Ih) this.a.get(i3);
                if (i2 != i) {
                    boolean z = getOrientation() == 0;
                    G g = Ih.e;
                    if (i3 == i2) {
                        ih = z ? AbstractC0750xn.m(this) ? new Ih(g, g, ih2.b, ih2.c) : new Ih(ih2.a, ih2.d, g, g) : new Ih(ih2.a, g, ih2.b, g);
                    } else if (i3 != i) {
                        ih2 = null;
                    } else if (z) {
                        ih = AbstractC0750xn.m(this) ? new Ih(ih2.a, ih2.d, g, g) : new Ih(g, g, ih2.b, ih2.c);
                    } else {
                        ih = new Ih(g, ih2.d, g, ih2.c);
                    }
                    ih2 = ih;
                }
                if (ih2 == null) {
                    c0317jnG.e = new G(0.0f);
                    c0317jnG.f = new G(0.0f);
                    c0317jnG.g = new G(0.0f);
                    c0317jnG.h = new G(0.0f);
                } else {
                    c0317jnG.e = ih2.a;
                    c0317jnG.h = ih2.d;
                    c0317jnG.f = ih2.b;
                    c0317jnG.g = ih2.c;
                }
                materialButton.a(c0317jnG.a());
            }
        }
    }

    @Override // android.view.ViewGroup
    public final int getChildDrawingOrder(int i, int i2) {
        Integer[] numArr = this.e;
        if (numArr != null && i2 < numArr.length) {
            return numArr[i2].intValue();
        }
        Log.w("MButtonToggleGroup", "Child order wasn't updated");
        return i2;
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        int i = this.i;
        if (i != -1) {
            d(Collections.singleton(Integer.valueOf(i)));
        }
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        int i = 0;
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            if ((getChildAt(i2) instanceof MaterialButton) && c(i2)) {
                i++;
            }
        }
        accessibilityNodeInfo.setCollectionInfo(AccessibilityNodeInfo.CollectionInfo.obtain(1, i, false, this.g ? 1 : 2));
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        e();
        a();
        super.onMeasure(i, i2);
    }

    @Override // android.view.ViewGroup
    public final void onViewRemoved(View view) {
        super.onViewRemoved(view);
        if (view instanceof MaterialButton) {
            ((MaterialButton) view).f = null;
        }
        int iIndexOfChild = indexOfChild(view);
        if (iIndexOfChild >= 0) {
            this.a.remove(iIndexOfChild);
        }
        e();
        a();
    }

    @Override // android.view.View
    public final void setEnabled(boolean z) {
        super.setEnabled(z);
        for (int i = 0; i < getChildCount(); i++) {
            ((MaterialButton) getChildAt(i)).setEnabled(z);
        }
    }
}
