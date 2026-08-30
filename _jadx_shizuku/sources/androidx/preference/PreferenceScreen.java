package androidx.preference;

import android.content.Context;
import android.util.AttributeSet;
import moe.shizuku.privileged.api.R;
import rikka.shizuku.Ac;
import rikka.shizuku.Hn;

/* JADX INFO: loaded from: classes.dex */
public final class PreferenceScreen extends PreferenceGroup {
    public final boolean T;

    public PreferenceScreen(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, Hn.c(context, R.attr._0_resource_name_obfuscated_res_0x7f0403f2, android.R.attr.preferenceScreenStyle));
        this.T = true;
    }

    @Override // androidx.preference.Preference
    public final void n() {
        Ac ac;
        if (this.m != null || this.n != null || this.O.size() == 0 || (ac = this.b.k) == null) {
            return;
        }
        for (ac = this.b.k; ac != null; ac = ac.w) {
        }
    }
}
