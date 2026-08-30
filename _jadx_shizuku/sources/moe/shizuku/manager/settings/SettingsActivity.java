package moe.shizuku.manager.settings;

import android.content.res.Resources;
import android.os.Bundle;
import moe.shizuku.privileged.api.R;
import rikka.shizuku.AbstractActivityC0113d2;
import rikka.shizuku.AbstractC0744xh;
import rikka.shizuku.C0226gn;
import rikka.shizuku.Cc;
import rikka.shizuku.L4;
import rikka.shizuku.Qc;

/* JADX INFO: loaded from: classes.dex */
public final class SettingsActivity extends AbstractActivityC0113d2 {
    @Override // rikka.shizuku.AbstractActivityC0113d2, rikka.shizuku.AbstractActivityC0051b2, rikka.shizuku.T6, rikka.shizuku.S6, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        AbstractC0744xh abstractC0744xhL = l();
        if (abstractC0744xhL != null) {
            abstractC0744xhL.T(true);
        }
        if (bundle == null) {
            Qc qc = ((Cc) this.s.b).l;
            qc.getClass();
            L4 l4 = new L4(qc);
            l4.f(R.id._0_resource_name_obfuscated_res_0x7f0900bc, new C0226gn(), null, 2);
            l4.e(false, true);
        }
    }

    @Override // rikka.shizuku.AbstractActivityC0051b2
    public final void p(Resources.Theme theme, boolean z) {
        super.p(theme, z);
        theme.applyStyle(R.style._0_resource_name_obfuscated_res_0x7f120344, true);
    }

    @Override // rikka.shizuku.AbstractActivityC0113d2
    public final int y() {
        return R.layout._0_resource_name_obfuscated_res_0x7f0c0024;
    }
}
