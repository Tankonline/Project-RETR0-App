package rikka.shizuku;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.textfield.TextInputLayout;
import java.util.WeakHashMap;
import moe.shizuku.privileged.api.R;

/* JADX INFO: loaded from: classes.dex */
public final class Po extends LinearLayout {
    public final TextInputLayout a;
    public final C0606t3 b;
    public final CharSequence c;
    public final CheckableImageButton d;
    public final ColorStateList e;
    public final PorterDuff.Mode f;
    public final int g;
    public boolean h;

    public Po(TextInputLayout textInputLayout, K3 k3) {
        CharSequence text;
        super(textInputLayout.getContext());
        this.a = textInputLayout;
        setVisibility(8);
        setOrientation(0);
        setLayoutParams(new FrameLayout.LayoutParams(-2, -1, 8388611));
        CheckableImageButton checkableImageButton = (CheckableImageButton) LayoutInflater.from(getContext()).inflate(R.layout._0_resource_name_obfuscated_res_0x7f0c0038, (ViewGroup) this, false);
        this.d = checkableImageButton;
        C0606t3 c0606t3 = new C0606t3(getContext(), null);
        this.b = c0606t3;
        if (X8.I(getContext())) {
            ((ViewGroup.MarginLayoutParams) checkableImageButton.getLayoutParams()).setMarginEnd(0);
        }
        checkableImageButton.setOnClickListener(null);
        AbstractC0599sr.G(checkableImageButton);
        checkableImageButton.setOnLongClickListener(null);
        AbstractC0599sr.G(checkableImageButton);
        TypedArray typedArray = (TypedArray) k3.a;
        if (typedArray.hasValue(69)) {
            this.e = X8.x(getContext(), k3, 69);
        }
        if (typedArray.hasValue(70)) {
            this.f = AbstractC0750xn.q(typedArray.getInt(70, -1), null);
        }
        if (typedArray.hasValue(66)) {
            Drawable drawableI = k3.i(66);
            checkableImageButton.setImageDrawable(drawableI);
            if (drawableI != null) {
                AbstractC0599sr.a(textInputLayout, checkableImageButton, this.e, this.f);
                if (checkableImageButton.getVisibility() != 0) {
                    checkableImageButton.setVisibility(0);
                    b();
                    c();
                }
                AbstractC0599sr.D(textInputLayout, checkableImageButton, this.e);
            } else {
                if (checkableImageButton.getVisibility() == 0) {
                    checkableImageButton.setVisibility(8);
                    b();
                    c();
                }
                checkableImageButton.setOnClickListener(null);
                AbstractC0599sr.G(checkableImageButton);
                checkableImageButton.setOnLongClickListener(null);
                AbstractC0599sr.G(checkableImageButton);
                if (checkableImageButton.getContentDescription() != null) {
                    checkableImageButton.setContentDescription(null);
                }
            }
            if (typedArray.hasValue(65) && checkableImageButton.getContentDescription() != (text = typedArray.getText(65))) {
                checkableImageButton.setContentDescription(text);
            }
            boolean z = typedArray.getBoolean(64, true);
            if (checkableImageButton.e != z) {
                checkableImageButton.e = z;
                checkableImageButton.sendAccessibilityEvent(0);
            }
        }
        int dimensionPixelSize = typedArray.getDimensionPixelSize(67, getResources().getDimensionPixelSize(R.dimen._0_resource_name_obfuscated_res_0x7f0702de));
        if (dimensionPixelSize < 0) {
            throw new IllegalArgumentException("startIconSize cannot be less than 0");
        }
        if (dimensionPixelSize != this.g) {
            this.g = dimensionPixelSize;
            checkableImageButton.setMinimumWidth(dimensionPixelSize);
            checkableImageButton.setMinimumHeight(dimensionPixelSize);
        }
        if (typedArray.hasValue(68)) {
            checkableImageButton.setScaleType(AbstractC0599sr.h(typedArray.getInt(68, -1)));
        }
        c0606t3.setVisibility(8);
        c0606t3.setId(R.id._0_resource_name_obfuscated_res_0x7f0901db);
        c0606t3.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
        WeakHashMap weakHashMap = Hs.a;
        c0606t3.setAccessibilityLiveRegion(1);
        c0606t3.setTextAppearance(typedArray.getResourceId(60, 0));
        if (typedArray.hasValue(61)) {
            c0606t3.setTextColor(k3.h(61));
        }
        CharSequence text2 = typedArray.getText(59);
        this.c = TextUtils.isEmpty(text2) ? null : text2;
        c0606t3.setText(text2);
        c();
        addView(checkableImageButton);
        addView(c0606t3);
    }

    public final int a() {
        int marginEnd;
        CheckableImageButton checkableImageButton = this.d;
        if (checkableImageButton.getVisibility() == 0) {
            marginEnd = ((ViewGroup.MarginLayoutParams) checkableImageButton.getLayoutParams()).getMarginEnd() + checkableImageButton.getMeasuredWidth();
        } else {
            marginEnd = 0;
        }
        WeakHashMap weakHashMap = Hs.a;
        return this.b.getPaddingStart() + getPaddingStart() + marginEnd;
    }

    public final void b() {
        int paddingStart;
        EditText editText = this.a.d;
        if (editText == null) {
            return;
        }
        if (this.d.getVisibility() == 0) {
            paddingStart = 0;
        } else {
            WeakHashMap weakHashMap = Hs.a;
            paddingStart = editText.getPaddingStart();
        }
        int compoundPaddingTop = editText.getCompoundPaddingTop();
        int dimensionPixelSize = getContext().getResources().getDimensionPixelSize(R.dimen._0_resource_name_obfuscated_res_0x7f07025c);
        int compoundPaddingBottom = editText.getCompoundPaddingBottom();
        WeakHashMap weakHashMap2 = Hs.a;
        this.b.setPaddingRelative(paddingStart, compoundPaddingTop, dimensionPixelSize, compoundPaddingBottom);
    }

    public final void c() {
        int i = (this.c == null || this.h) ? 8 : 0;
        setVisibility((this.d.getVisibility() == 0 || i == 0) ? 0 : 8);
        this.b.setVisibility(i);
        this.a.v();
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        b();
    }
}
