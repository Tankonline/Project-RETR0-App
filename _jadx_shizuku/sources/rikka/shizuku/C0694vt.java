package rikka.shizuku;

import android.app.Dialog;
import moe.shizuku.privileged.api.R;

/* JADX INFO: renamed from: rikka.shizuku.vt, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0694vt extends U9 {
    @Override // rikka.shizuku.U9
    public final Dialog N() {
        Ch ch = new Ch(H());
        E1 e1 = (E1) ch.b;
        e1.f = e1.a.getText(R.string._0_resource_name_obfuscated_res_0x7f11005f);
        ch.g(android.R.string.ok, null);
        return ch.b();
    }
}
