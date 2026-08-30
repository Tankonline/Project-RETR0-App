package rikka.shizuku;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.os.SystemProperties;
import android.view.Display;
import android.view.View;
import moe.shizuku.manager.adb.AdbPairingTutorialActivity;
import moe.shizuku.manager.shell.ShellTutorialActivity;
import moe.shizuku.manager.starter.StarterActivity;
import moe.shizuku.privileged.api.R;
import rikka.html.text.style.BulletSpan;

/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class X0 implements View.OnClickListener {
    public final /* synthetic */ int a;

    public /* synthetic */ X0(int i) {
        this.a = i;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        Activity activity;
        Activity activity2;
        Activity activity3;
        int i = 0;
        switch (this.a) {
            case 0:
                Intent intent = new Intent("android.settings.APPLICATION_DEVELOPMENT_SETTINGS");
                intent.setFlags(268468224);
                intent.putExtra(":settings:fragment_args_key", "toggle_adb_wireless");
                try {
                    view.getContext().startActivity(intent);
                    return;
                } catch (ActivityNotFoundException unused) {
                    return;
                }
            case 1:
                Intent intent2 = new Intent("android.settings.APPLICATION_DEVELOPMENT_SETTINGS");
                intent2.setFlags(268468224);
                intent2.putExtra(":settings:fragment_args_key", "toggle_adb_wireless");
                try {
                    view.getContext().startActivity(intent2);
                    return;
                } catch (ActivityNotFoundException unused2) {
                    return;
                }
            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                AbstractC0744xh.G(view.getContext(), AbstractC0494pe.f.a());
                return;
            case 3:
                AbstractC0744xh.G(view.getContext(), AbstractC0494pe.c.a());
                return;
            case 4:
                int i2 = ShellTutorialActivity.E;
                AbstractC0744xh.G(view.getContext(), AbstractC0494pe.e.a());
                return;
            case BulletSpan.STANDARD_BULLET_RADIUS /* 5 */:
                Context context = view.getContext();
                Ch ch = new Ch(context);
                E1 e1 = (E1) ch.b;
                e1.d = e1.a.getText(R.string._0_resource_name_obfuscated_res_0x7f11006d);
                e1.f = AbstractC0599sr.m(0, context.getString(R.string._0_resource_name_obfuscated_res_0x7f110072, Qo.a));
                ch.g(R.string._0_resource_name_obfuscated_res_0x7f110071, new Oo(context, i));
                e1.i = e1.a.getText(android.R.string.cancel);
                e1.j = null;
                Oo oo = new Oo(context, 1);
                e1.k = e1.a.getText(R.string._0_resource_name_obfuscated_res_0x7f110070);
                e1.l = oo;
                ch.f();
                return;
            case 6:
                Context context2 = view.getContext();
                if (Build.VERSION.SDK_INT >= 30) {
                    C0050b1 c0050b1 = new C0050b1();
                    if (context2 instanceof AbstractActivityC0051b2) {
                        activity2 = (Activity) context2;
                    } else {
                        Context baseContext = context2;
                        do {
                            if (!(baseContext instanceof ContextWrapper)) {
                                throw new ClassCastException("Context instance " + context2 + " is not Activity");
                            }
                            baseContext = ((ContextWrapper) baseContext).getBaseContext();
                        } while (!(baseContext instanceof AbstractActivityC0051b2));
                        activity2 = (Activity) baseContext;
                    }
                    Qc qc = ((Cc) ((AbstractActivityC0051b2) activity2).s.b).l;
                    if (qc.O()) {
                        return;
                    }
                    c0050b1.P(qc, C0050b1.class.getSimpleName());
                    return;
                }
                int i3 = SystemProperties.getInt("service.adb.tcp.port", -1);
                if (i3 == -1) {
                    i3 = SystemProperties.getInt("persist.adb.tcp.port", -1);
                }
                if (i3 > 0) {
                    Intent intent3 = new Intent(context2, (Class<?>) StarterActivity.class);
                    intent3.putExtra("moe.shizuku.manager.extra.IS_ROOT", false);
                    intent3.putExtra("moe.shizuku.manager.extra.HOST", "127.0.0.1");
                    intent3.putExtra("moe.shizuku.manager.extra.PORT", i3);
                    context2.startActivity(intent3);
                    return;
                }
                C0694vt c0694vt = new C0694vt();
                if (context2 instanceof AbstractActivityC0051b2) {
                    activity = (Activity) context2;
                } else {
                    Context baseContext2 = context2;
                    do {
                        if (!(baseContext2 instanceof ContextWrapper)) {
                            throw new ClassCastException("Context instance " + context2 + " is not Activity");
                        }
                        baseContext2 = ((ContextWrapper) baseContext2).getBaseContext();
                    } while (!(baseContext2 instanceof AbstractActivityC0051b2));
                    activity = (Activity) baseContext2;
                }
                Qc qc2 = ((Cc) ((AbstractActivityC0051b2) activity).s.b).l;
                if (qc2.O()) {
                    return;
                }
                c0694vt.P(qc2, C0694vt.class.getSimpleName());
                return;
            case 7:
                AbstractC0744xh.G(view.getContext(), AbstractC0494pe.b.a());
                return;
            default:
                Context context3 = view.getContext();
                Display display = context3.getDisplay();
                if ((display != null ? display.getDisplayId() : -1) <= 0) {
                    context3.startActivity(new Intent(context3, (Class<?>) AdbPairingTutorialActivity.class));
                    return;
                }
                C0573s1 c0573s1 = new C0573s1();
                if (context3 instanceof AbstractActivityC0051b2) {
                    activity3 = (Activity) context3;
                } else {
                    Context baseContext3 = context3;
                    do {
                        if (!(baseContext3 instanceof ContextWrapper)) {
                            throw new ClassCastException("Context instance " + context3 + " is not Activity");
                        }
                        baseContext3 = ((ContextWrapper) baseContext3).getBaseContext();
                    } while (!(baseContext3 instanceof AbstractActivityC0051b2));
                    activity3 = (Activity) baseContext3;
                }
                Qc qc3 = ((Cc) ((AbstractActivityC0051b2) activity3).s.b).l;
                if (qc3.O()) {
                    return;
                }
                c0573s1.P(qc3, C0573s1.class.getSimpleName());
                return;
        }
    }

    public /* synthetic */ X0(C0790z1 c0790z1, int i) {
        this.a = i;
    }
}
