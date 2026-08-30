package androidx.preference;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import moe.shizuku.privileged.api.R;
import rikka.shizuku.C0500pk;
import rikka.shizuku.C0521qa;

/* JADX INFO: loaded from: classes.dex */
public class DropDownPreference extends ListPreference {
    public final ArrayAdapter Y;
    public Spinner Z;
    public final C0521qa a0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DropDownPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, R.attr._0_resource_name_obfuscated_res_0x7f0401a2, 0);
        this.a0 = new C0521qa(0, this);
        ArrayAdapter arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item);
        this.Y = arrayAdapter;
        arrayAdapter.clear();
        CharSequence[] charSequenceArr = this.T;
        if (charSequenceArr != null) {
            for (CharSequence charSequence : charSequenceArr) {
                arrayAdapter.add(charSequence.toString());
            }
        }
    }

    @Override // androidx.preference.ListPreference
    public final void C(CharSequence[] charSequenceArr) {
        this.T = charSequenceArr;
        ArrayAdapter arrayAdapter = this.Y;
        arrayAdapter.clear();
        CharSequence[] charSequenceArr2 = this.T;
        if (charSequenceArr2 != null) {
            for (CharSequence charSequence : charSequenceArr2) {
                arrayAdapter.add(charSequence.toString());
            }
        }
    }

    @Override // androidx.preference.Preference
    public final void i() {
        super.i();
        ArrayAdapter arrayAdapter = this.Y;
        if (arrayAdapter != null) {
            arrayAdapter.notifyDataSetChanged();
        }
    }

    @Override // androidx.preference.Preference
    public final void m(C0500pk c0500pk) {
        int length;
        Spinner spinner = (Spinner) c0500pk.a.findViewById(R.id._0_resource_name_obfuscated_res_0x7f09019d);
        this.Z = spinner;
        spinner.setAdapter((SpinnerAdapter) this.Y);
        this.Z.setOnItemSelectedListener(this.a0);
        Spinner spinner2 = this.Z;
        String str = this.V;
        CharSequence[] charSequenceArr = this.U;
        if (str == null || charSequenceArr == null) {
            length = -1;
        } else {
            length = charSequenceArr.length - 1;
            while (length >= 0) {
                if (!TextUtils.equals(charSequenceArr[length].toString(), str)) {
                    length--;
                }
            }
            length = -1;
        }
        spinner2.setSelection(length);
        super.m(c0500pk);
    }

    @Override // androidx.preference.DialogPreference, androidx.preference.Preference
    public final void n() {
        this.Z.performClick();
    }
}
