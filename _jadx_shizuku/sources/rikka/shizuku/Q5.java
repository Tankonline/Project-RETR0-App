package rikka.shizuku;

import android.widget.CompoundButton;
import androidx.preference.CheckBoxPreference;
import androidx.preference.SwitchPreference;
import androidx.preference.SwitchPreferenceCompat;
import androidx.preference.TwoStatePreference;

/* JADX INFO: loaded from: classes.dex */
public final class Q5 implements CompoundButton.OnCheckedChangeListener {
    public final /* synthetic */ int a;
    public final /* synthetic */ TwoStatePreference b;

    public /* synthetic */ Q5(TwoStatePreference twoStatePreference, int i) {
        this.a = i;
        this.b = twoStatePreference;
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        switch (this.a) {
            case 0:
                Boolean boolValueOf = Boolean.valueOf(z);
                CheckBoxPreference checkBoxPreference = (CheckBoxPreference) this.b;
                if (!checkBoxPreference.a(boolValueOf)) {
                    compoundButton.setChecked(!z);
                } else {
                    checkBoxPreference.B(z);
                }
                break;
            case 1:
                Boolean boolValueOf2 = Boolean.valueOf(z);
                SwitchPreference switchPreference = (SwitchPreference) this.b;
                if (!switchPreference.a(boolValueOf2)) {
                    compoundButton.setChecked(!z);
                } else {
                    switchPreference.B(z);
                }
                break;
            default:
                Boolean boolValueOf3 = Boolean.valueOf(z);
                SwitchPreferenceCompat switchPreferenceCompat = (SwitchPreferenceCompat) this.b;
                if (!switchPreferenceCompat.a(boolValueOf3)) {
                    compoundButton.setChecked(!z);
                } else {
                    switchPreferenceCompat.B(z);
                }
                break;
        }
    }
}
