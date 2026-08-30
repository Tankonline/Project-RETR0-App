package rikka.shizuku;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityManager;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import com.google.android.material.textfield.TextInputLayout;
import java.util.List;
import java.util.Locale;
import moe.shizuku.privileged.api.R;

/* JADX INFO: loaded from: classes.dex */
public final class Eh extends C0389m2 {
    public final Zg e;
    public final AccessibilityManager f;
    public final Rect g;
    public final float h;
    public final ColorStateList i;
    public final int j;
    public final ColorStateList k;

    public Eh(Context context, AttributeSet attributeSet) {
        super(X8.c0(context, attributeSet, R.attr._0_resource_name_obfuscated_res_0x7f040043, 0), attributeSet);
        this.g = new Rect();
        Context context2 = getContext();
        TypedArray typedArrayJ = X8.J(context2, attributeSet, Lk.m, R.attr._0_resource_name_obfuscated_res_0x7f040043, R.style._0_resource_name_obfuscated_res_0x7f120350, new int[0]);
        if (typedArrayJ.hasValue(0) && typedArrayJ.getInt(0, 0) == 0) {
            setKeyListener(null);
        }
        int resourceId = typedArrayJ.getResourceId(3, R.layout._0_resource_name_obfuscated_res_0x7f0c0067);
        this.h = typedArrayJ.getDimensionPixelOffset(1, R.dimen._0_resource_name_obfuscated_res_0x7f0702bf);
        if (typedArrayJ.hasValue(2)) {
            this.i = ColorStateList.valueOf(typedArrayJ.getColor(2, 0));
        }
        this.j = typedArrayJ.getColor(4, 0);
        this.k = X8.w(context2, typedArrayJ, 5);
        this.f = (AccessibilityManager) context2.getSystemService("accessibility");
        Zg zg = new Zg(context2, null, R.attr._0_resource_name_obfuscated_res_0x7f0402d9);
        this.e = zg;
        zg.y = true;
        zg.z.setFocusable(true);
        zg.o = this;
        zg.z.setInputMethodMode(2);
        zg.m(getAdapter());
        zg.p = new C0144e3(1, this);
        if (typedArrayJ.hasValue(6)) {
            setAdapter(new Dh(this, getContext(), resourceId, getResources().getStringArray(typedArrayJ.getResourceId(6, 0))));
        }
        typedArrayJ.recycle();
    }

    public static void a(Eh eh, Object obj) {
        eh.setText(eh.convertSelectionToString(obj), false);
    }

    public final TextInputLayout b() {
        for (ViewParent parent = getParent(); parent != null; parent = parent.getParent()) {
            if (parent instanceof TextInputLayout) {
                return (TextInputLayout) parent;
            }
        }
        return null;
    }

    public final boolean c() {
        List<AccessibilityServiceInfo> enabledAccessibilityServiceList;
        AccessibilityManager accessibilityManager = this.f;
        if (accessibilityManager != null && accessibilityManager.isTouchExplorationEnabled()) {
            return true;
        }
        if (accessibilityManager == null || !accessibilityManager.isEnabled() || (enabledAccessibilityServiceList = accessibilityManager.getEnabledAccessibilityServiceList(16)) == null) {
            return false;
        }
        for (AccessibilityServiceInfo accessibilityServiceInfo : enabledAccessibilityServiceList) {
            if (accessibilityServiceInfo.getSettingsActivityName() != null && accessibilityServiceInfo.getSettingsActivityName().contains("SwitchAccess")) {
                return true;
            }
        }
        return false;
    }

    @Override // android.widget.AutoCompleteTextView
    public final void dismissDropDown() {
        if (c()) {
            this.e.dismiss();
        } else {
            super.dismissDropDown();
        }
    }

    @Override // android.widget.TextView
    public final CharSequence getHint() {
        TextInputLayout textInputLayoutB = b();
        if (textInputLayoutB == null || !textInputLayoutB.E) {
            return super.getHint();
        }
        if (textInputLayoutB.C) {
            return textInputLayoutB.D;
        }
        return null;
    }

    @Override // android.widget.AutoCompleteTextView, android.widget.TextView, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        TextInputLayout textInputLayoutB = b();
        if (textInputLayoutB != null && textInputLayoutB.E && super.getHint() == null) {
            String str = Build.MANUFACTURER;
            if ((str != null ? str.toLowerCase(Locale.ENGLISH) : "").equals("meizu")) {
                setHint("");
            }
        }
    }

    @Override // android.widget.AutoCompleteTextView, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.e.dismiss();
    }

    @Override // android.widget.TextView, android.view.View
    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (View.MeasureSpec.getMode(i) == Integer.MIN_VALUE) {
            int measuredWidth = getMeasuredWidth();
            ListAdapter adapter = getAdapter();
            TextInputLayout textInputLayoutB = b();
            int measuredWidth2 = 0;
            if (adapter != null && textInputLayoutB != null) {
                int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 0);
                int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 0);
                Zg zg = this.e;
                int iMin = Math.min(adapter.getCount(), Math.max(0, !zg.z.isShowing() ? -1 : zg.c.getSelectedItemPosition()) + 15);
                View view = null;
                int iMax = 0;
                for (int iMax2 = Math.max(0, iMin - 15); iMax2 < iMin; iMax2++) {
                    int itemViewType = adapter.getItemViewType(iMax2);
                    if (itemViewType != measuredWidth2) {
                        view = null;
                        measuredWidth2 = itemViewType;
                    }
                    view = adapter.getView(iMax2, view, textInputLayoutB);
                    if (view.getLayoutParams() == null) {
                        view.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
                    }
                    view.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
                    iMax = Math.max(iMax, view.getMeasuredWidth());
                }
                Drawable background = zg.z.getBackground();
                if (background != null) {
                    Rect rect = this.g;
                    background.getPadding(rect);
                    iMax += rect.left + rect.right;
                }
                measuredWidth2 = textInputLayoutB.c.f.getMeasuredWidth() + iMax;
            }
            setMeasuredDimension(Math.min(Math.max(measuredWidth, measuredWidth2), View.MeasureSpec.getSize(i)), getMeasuredHeight());
        }
    }

    @Override // android.widget.AutoCompleteTextView, android.widget.TextView, android.view.View
    public final void onWindowFocusChanged(boolean z) {
        if (c()) {
            return;
        }
        super.onWindowFocusChanged(z);
    }

    @Override // android.widget.AutoCompleteTextView
    public final void setAdapter(ListAdapter listAdapter) {
        super.setAdapter(listAdapter);
        this.e.m(getAdapter());
    }

    @Override // android.widget.AutoCompleteTextView
    public final void setDropDownBackgroundDrawable(Drawable drawable) {
        super.setDropDownBackgroundDrawable(drawable);
        Zg zg = this.e;
        if (zg != null) {
            zg.k(drawable);
        }
    }

    @Override // android.widget.AutoCompleteTextView
    public final void setOnItemSelectedListener(AdapterView.OnItemSelectedListener onItemSelectedListener) {
        super.setOnItemSelectedListener(onItemSelectedListener);
        this.e.q = getOnItemSelectedListener();
    }

    @Override // android.widget.TextView
    public final void setRawInputType(int i) {
        super.setRawInputType(i);
        TextInputLayout textInputLayoutB = b();
        if (textInputLayoutB != null) {
            textInputLayoutB.x();
        }
    }

    @Override // android.widget.AutoCompleteTextView
    public final void showDropDown() {
        if (c()) {
            this.e.e();
        } else {
            super.showDropDown();
        }
    }
}
