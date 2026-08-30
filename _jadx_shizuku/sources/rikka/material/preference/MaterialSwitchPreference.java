package rikka.material.preference;

import android.content.Context;
import android.util.AttributeSet;
import androidx.preference.SwitchPreferenceCompat;
import moe.shizuku.privileged.api.R;

/* JADX INFO: loaded from: classes.dex */
public class MaterialSwitchPreference extends SwitchPreferenceCompat {
    public MaterialSwitchPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    public MaterialSwitchPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, R.style._0_resource_name_obfuscated_res_0x7f120179);
    }

    public MaterialSwitchPreference(Context context) {
        this(context, null);
    }

    public MaterialSwitchPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, R.attr._0_resource_name_obfuscated_res_0x7f040314, 0);
    }
}
