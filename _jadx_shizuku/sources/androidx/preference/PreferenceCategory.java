package androidx.preference;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import moe.shizuku.privileged.api.R;
import rikka.shizuku.C0500pk;
import rikka.shizuku.Hn;

/* JADX INFO: loaded from: classes.dex */
public class PreferenceCategory extends PreferenceGroup {
    public PreferenceCategory(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, Hn.c(context, R.attr._0_resource_name_obfuscated_res_0x7f0403eb, android.R.attr.preferenceCategoryStyle));
    }

    @Override // androidx.preference.Preference
    public final boolean h() {
        return false;
    }

    @Override // androidx.preference.Preference
    public final void m(C0500pk c0500pk) {
        super.m(c0500pk);
        if (Build.VERSION.SDK_INT >= 28) {
            c0500pk.a.setAccessibilityHeading(true);
        }
    }

    @Override // androidx.preference.Preference
    public final boolean z() {
        return !super.h();
    }
}
