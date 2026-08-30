package com.google.android.material.timepicker;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.chip.Chip;
import moe.shizuku.privileged.api.R;
import rikka.shizuku.Yg;

/* JADX INFO: loaded from: classes.dex */
class TimePickerView extends ConstraintLayout {
    public static final /* synthetic */ int p = 0;
    public final Chip o;

    public TimePickerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
        e eVar = new e(this);
        LayoutInflater.from(context).inflate(R.layout._0_resource_name_obfuscated_res_0x7f0c005e, this);
        MaterialButtonToggleGroup materialButtonToggleGroup = (MaterialButtonToggleGroup) findViewById(R.id._0_resource_name_obfuscated_res_0x7f0900f5);
        materialButtonToggleGroup.c.add(new d());
        Chip chip = (Chip) findViewById(R.id._0_resource_name_obfuscated_res_0x7f0900fa);
        Chip chip2 = (Chip) findViewById(R.id._0_resource_name_obfuscated_res_0x7f0900f7);
        this.o = chip2;
        Yg yg = new Yg(1, new GestureDetector(getContext(), new f(this)));
        chip.setOnTouchListener(yg);
        chip2.setOnTouchListener(yg);
        chip.setTag(R.id._0_resource_name_obfuscated_res_0x7f090188, 12);
        chip2.setTag(R.id._0_resource_name_obfuscated_res_0x7f090188, 10);
        chip.setOnClickListener(eVar);
        chip2.setOnClickListener(eVar);
        chip.p = "android.view.View";
        chip2.p = "android.view.View";
    }

    @Override // android.view.View
    public final void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        if (view == this && i == 0) {
            this.o.sendAccessibilityEvent(8);
        }
    }
}
