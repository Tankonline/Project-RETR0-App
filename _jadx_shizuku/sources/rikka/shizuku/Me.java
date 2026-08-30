package rikka.shizuku;

import android.text.TextUtils;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import moe.shizuku.privileged.api.R;

/* JADX INFO: loaded from: classes.dex */
public final class Me implements InterfaceC0161ek {
    public static final Me a = new Me();
    public static Me b;

    @Override // rikka.shizuku.InterfaceC0161ek
    public CharSequence a(Preference preference) {
        CharSequence[] charSequenceArr;
        CharSequence[] charSequenceArr2;
        ListPreference listPreference = (ListPreference) preference;
        int iB = listPreference.B(listPreference.V);
        if (TextUtils.isEmpty((iB < 0 || (charSequenceArr2 = listPreference.T) == null) ? null : charSequenceArr2[iB])) {
            return listPreference.a.getString(R.string._0_resource_name_obfuscated_res_0x7f1100f1);
        }
        int iB2 = listPreference.B(listPreference.V);
        if (iB2 < 0 || (charSequenceArr = listPreference.T) == null) {
            return null;
        }
        return charSequenceArr[iB2];
    }
}
