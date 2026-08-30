package rikka.shizuku;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.SystemProperties;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import moe.shizuku.manager.starter.StarterActivity;
import moe.shizuku.privileged.api.R;

/* JADX INFO: renamed from: rikka.shizuku.b1, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0050b1 extends U9 {
    public Ys m0;
    public C0357l1 n0;
    public final Pi o0 = new Pi();

    @Override // rikka.shizuku.U9
    public final Dialog N() {
        Context contextH = H();
        View viewInflate = j().inflate(R.layout._0_resource_name_obfuscated_res_0x7f0c001d, (ViewGroup) null, false);
        if (viewInflate == null) {
            throw new NullPointerException("rootView");
        }
        this.m0 = new Ys(5, (ScrollView) viewInflate);
        this.n0 = new C0357l1(contextH, "_adb-tls-connect._tcp", new V0(0, this));
        int i = SystemProperties.getInt("service.adb.tcp.port", -1);
        if (i == -1) {
            i = SystemProperties.getInt("persist.adb.tcp.port", -1);
        }
        Ch ch = new Ch(contextH);
        E1 e1 = (E1) ch.b;
        e1.d = e1.a.getText(R.string._0_resource_name_obfuscated_res_0x7f11004f);
        Ys ys = this.m0;
        if (ys == null) {
            ys = null;
        }
        e1.s = (ScrollView) ys.b;
        e1.i = e1.a.getText(android.R.string.cancel);
        e1.j = null;
        ch.g(R.string._0_resource_name_obfuscated_res_0x7f11004e, null);
        if (i != -1) {
            e1.k = String.valueOf(i);
            e1.l = null;
        }
        J1 j1B = ch.b();
        j1B.setCanceledOnTouchOutside(false);
        j1B.setOnShowListener(new W0(this, j1B, 0));
        return j1B;
    }

    public final void Q(int i) {
        Intent intent = new Intent(i(), (Class<?>) StarterActivity.class);
        intent.putExtra("moe.shizuku.manager.extra.IS_ROOT", false);
        intent.putExtra("moe.shizuku.manager.extra.HOST", "127.0.0.1");
        intent.putExtra("moe.shizuku.manager.extra.PORT", i);
        H().startActivity(intent);
        M(true, false);
    }

    @Override // rikka.shizuku.U9, android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);
        C0357l1 c0357l1 = this.n0;
        if (c0357l1 == null) {
            c0357l1 = null;
        }
        c0357l1.b();
    }
}
