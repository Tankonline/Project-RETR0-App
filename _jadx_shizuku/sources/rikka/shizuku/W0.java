package rikka.shizuku;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.provider.Settings;
import android.widget.Button;
import com.google.android.material.textfield.TextInputLayout;

/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class W0 implements DialogInterface.OnShowListener {
    public final /* synthetic */ int a;
    public final /* synthetic */ J1 b;
    public final /* synthetic */ U9 c;

    public /* synthetic */ W0(U9 u9, J1 j1, int i) {
        this.a = i;
        this.c = u9;
        this.b = j1;
    }

    @Override // android.content.DialogInterface.OnShowListener
    public final void onShow(DialogInterface dialogInterface) {
        switch (this.a) {
            case 0:
                C0050b1 c0050b1 = (C0050b1) this.c;
                C0357l1 c0357l1 = c0050b1.n0;
                if (c0357l1 == null) {
                    c0357l1 = null;
                }
                c0357l1.a();
                J1 j1 = this.b;
                Context context = j1.getContext();
                if (context.checkSelfPermission("android.permission.WRITE_SECURE_SETTINGS") == 0) {
                    ContentResolver contentResolver = context.getContentResolver();
                    Settings.Global.putInt(contentResolver, "adb_wifi_enabled", 1);
                    Settings.Global.putInt(contentResolver, "adb_enabled", 1);
                    Settings.Global.putLong(contentResolver, "adb_allowed_connection_time", 0L);
                }
                j1.d(-1).setOnClickListener(new X0(0));
                Button buttonD = j1.d(-3);
                if (buttonD != null) {
                    buttonD.setOnClickListener(new Y0(0, c0050b1));
                }
                c0050b1.o0.d(c0050b1, new C0019a1(new Z0(0, c0050b1), 0));
                break;
            default:
                C0573s1 c0573s1 = (C0573s1) this.c;
                C0235h1 c0235h1 = c0573s1.m0;
                if (c0235h1 == null) {
                    c0235h1 = null;
                }
                ((TextInputLayout) c0235h1.b).d.addTextChangedListener(new C0481p1(0, c0573s1));
                C0235h1 c0235h2 = c0573s1.m0;
                if (c0235h2 == null) {
                    c0235h2 = null;
                }
                ((TextInputLayout) c0235h2.b).l(null);
                J1 j2 = this.b;
                j2.d(-1).setVisibility(8);
                j2.d(-3).setOnClickListener(new X0(1));
                j2.d(-1).setOnClickListener(new Y0(1, c0573s1));
                ((Ts) c0573s1.n0.getValue()).e.d(c0573s1, new C0512q1(new C0450o1(j2, c0573s1), 0));
                break;
        }
    }
}
