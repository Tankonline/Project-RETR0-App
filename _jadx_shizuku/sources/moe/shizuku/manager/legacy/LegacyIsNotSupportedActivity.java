package moe.shizuku.manager.legacy;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import moe.shizuku.manager.legacy.LegacyIsNotSupportedActivity;
import moe.shizuku.privileged.api.R;
import rikka.shizuku.AbstractActivityC0051b2;
import rikka.shizuku.AbstractC0688vn;
import rikka.shizuku.Ch;
import rikka.shizuku.E1;
import rikka.shizuku.Oo;

/* JADX INFO: loaded from: classes.dex */
public final class LegacyIsNotSupportedActivity extends AbstractActivityC0051b2 {
    public static final /* synthetic */ int A = 0;

    @Override // rikka.shizuku.AbstractActivityC0051b2, rikka.shizuku.T6, rikka.shizuku.S6, android.app.Activity
    public final void onCreate(Bundle bundle) {
        CharSequence charSequenceLoadLabel;
        super.onCreate(bundle);
        ComponentName callingActivity = getCallingActivity();
        if (callingActivity == null) {
            setResult(0);
            finish();
            return;
        }
        try {
            ApplicationInfo applicationInfo = getPackageManager().getApplicationInfo(callingActivity.getPackageName(), 128);
            try {
                charSequenceLoadLabel = applicationInfo.loadLabel(getPackageManager());
            } catch (Exception unused) {
                charSequenceLoadLabel = applicationInfo.packageName;
            }
            Bundle bundle2 = applicationInfo.metaData;
            if (bundle2 == null || !bundle2.getBoolean("moe.shizuku.client.V3_SUPPORT")) {
                Ch ch = new Ch(this);
                String string = getString(R.string._0_resource_name_obfuscated_res_0x7f11005a, charSequenceLoadLabel);
                E1 e1 = (E1) ch.b;
                e1.d = string;
                e1.f = AbstractC0688vn.A(getString(R.string._0_resource_name_obfuscated_res_0x7f110059, charSequenceLoadLabel), 512);
                ch.g(android.R.string.ok, null);
                final int i = 1;
                e1.n = new DialogInterface.OnDismissListener(this) { // from class: rikka.shizuku.ng
                    public final /* synthetic */ LegacyIsNotSupportedActivity b;

                    {
                        this.b = this;
                    }

                    @Override // android.content.DialogInterface.OnDismissListener
                    public final void onDismiss(DialogInterface dialogInterface) {
                        LegacyIsNotSupportedActivity legacyIsNotSupportedActivity = this.b;
                        switch (i) {
                            case 0:
                                int i2 = LegacyIsNotSupportedActivity.A;
                                legacyIsNotSupportedActivity.setResult(1);
                                legacyIsNotSupportedActivity.finish();
                                break;
                            default:
                                int i3 = LegacyIsNotSupportedActivity.A;
                                legacyIsNotSupportedActivity.setResult(1);
                                legacyIsNotSupportedActivity.finish();
                                break;
                        }
                    }
                };
                e1.m = false;
                ch.f();
                return;
            }
            Ch ch2 = new Ch(this);
            String string2 = getString(R.string._0_resource_name_obfuscated_res_0x7f11005d, charSequenceLoadLabel);
            E1 e2 = (E1) ch2.b;
            e2.d = string2;
            e2.f = AbstractC0688vn.A(getString(R.string._0_resource_name_obfuscated_res_0x7f11005c, charSequenceLoadLabel), 512);
            ch2.g(android.R.string.ok, null);
            Oo oo = new Oo(this, 2);
            e2.k = e2.a.getText(R.string._0_resource_name_obfuscated_res_0x7f11005b);
            e2.l = oo;
            final int i2 = 0;
            e2.n = new DialogInterface.OnDismissListener(this) { // from class: rikka.shizuku.ng
                public final /* synthetic */ LegacyIsNotSupportedActivity b;

                {
                    this.b = this;
                }

                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    LegacyIsNotSupportedActivity legacyIsNotSupportedActivity = this.b;
                    switch (i2) {
                        case 0:
                            int i3 = LegacyIsNotSupportedActivity.A;
                            legacyIsNotSupportedActivity.setResult(1);
                            legacyIsNotSupportedActivity.finish();
                            break;
                        default:
                            int i4 = LegacyIsNotSupportedActivity.A;
                            legacyIsNotSupportedActivity.setResult(1);
                            legacyIsNotSupportedActivity.finish();
                            break;
                    }
                }
            };
            e2.m = false;
            ch2.f();
        } catch (Throwable unused2) {
            finish();
        }
    }
}
