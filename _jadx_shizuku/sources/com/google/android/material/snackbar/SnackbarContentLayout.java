package com.google.android.material.snackbar;

import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.WeakHashMap;
import moe.shizuku.privileged.api.R;
import rikka.shizuku.AbstractC0046as;
import rikka.shizuku.Hs;
import rikka.shizuku.V1;

/* JADX INFO: loaded from: classes.dex */
public class SnackbarContentLayout extends LinearLayout {
    public TextView a;

    public SnackbarContentLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        AbstractC0046as.P(context, R.attr._0_resource_name_obfuscated_res_0x7f04034b, V1.b);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.a = (TextView) findViewById(R.id._0_resource_name_obfuscated_res_0x7f090197);
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        boolean z = true;
        if (getOrientation() == 1) {
            return;
        }
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen._0_resource_name_obfuscated_res_0x7f070097);
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen._0_resource_name_obfuscated_res_0x7f070096);
        Layout layout = this.a.getLayout();
        if (layout == null || layout.getLineCount() <= 1) {
            dimensionPixelSize = dimensionPixelSize2;
        }
        boolean z2 = false;
        if (getOrientation() != 0) {
            setOrientation(0);
            z2 = true;
        }
        if (this.a.getPaddingTop() == dimensionPixelSize && this.a.getPaddingBottom() == dimensionPixelSize) {
            z = z2;
        } else {
            TextView textView = this.a;
            WeakHashMap weakHashMap = Hs.a;
            if (textView.isPaddingRelative()) {
                textView.setPaddingRelative(textView.getPaddingStart(), dimensionPixelSize, textView.getPaddingEnd(), dimensionPixelSize);
            } else {
                textView.setPadding(textView.getPaddingLeft(), dimensionPixelSize, textView.getPaddingRight(), dimensionPixelSize);
            }
        }
        if (z) {
            super.onMeasure(i, i2);
        }
    }
}
