package rikka.shizuku;

import android.text.TextUtils;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import moe.shizuku.privileged.api.R;

/* JADX INFO: loaded from: classes.dex */
public final class Ne implements InterfaceC0161ek {
    public static final Ne a = new Ne();
    public static Ne b;

    @Override // rikka.shizuku.InterfaceC0161ek
    public CharSequence a(Preference preference) {
        EditTextPreference editTextPreference = (EditTextPreference) preference;
        return TextUtils.isEmpty(editTextPreference.T) ? editTextPreference.a.getString(R.string._0_resource_name_obfuscated_res_0x7f1100f1) : editTextPreference.T;
    }
}
