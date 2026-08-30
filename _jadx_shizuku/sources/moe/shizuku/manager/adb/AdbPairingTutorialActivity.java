package moe.shizuku.manager.adb;

import android.app.AppOpsManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import moe.shizuku.manager.adb.AdbPairingTutorialActivity;
import moe.shizuku.privileged.api.R;
import rikka.shizuku.AbstractActivityC0113d2;
import rikka.shizuku.AbstractC0046as;
import rikka.shizuku.AbstractC0604t1;
import rikka.shizuku.AbstractC0744xh;
import rikka.shizuku.AbstractC0750xn;
import rikka.shizuku.C0728x1;
import rikka.shizuku.O9;
import rikka.shizuku.Qr;
import rikka.widget.borderview.BorderNestedScrollView;

/* JADX INFO: loaded from: classes.dex */
public final class AdbPairingTutorialActivity extends AbstractActivityC0113d2 {
    public static final /* synthetic */ int F = 0;
    public C0728x1 D;
    public boolean E;

    public final void A() {
        int i = AdbPairingService.h;
        Intent action = new Intent(this, (Class<?>) AdbPairingService.class).setAction("start");
        try {
            startForegroundService(action);
        } catch (Throwable th) {
            Log.e("ShizukuManager", "startForegroundService", th);
            if (Build.VERSION.SDK_INT < 31 || !AbstractC0604t1.p(th)) {
                return;
            }
            if (((AppOpsManager) getSystemService(AppOpsManager.class)).noteOpNoThrow("android:start_foreground", Process.myUid(), getPackageName(), null, null) == 2) {
                Toast.makeText(this, "OP_START_FOREGROUND is denied. What are you doing?", 1).show();
            }
            startService(action);
        }
    }

    public final void B() {
        C0728x1 c0728x1 = this.D;
        if (c0728x1 == null) {
            c0728x1 = null;
        }
        c0728x1.g.setVisibility(this.E ? 0 : 8);
        c0728x1.h.setVisibility(this.E ? 0 : 8);
        c0728x1.i.setVisibility(this.E ? 0 : 8);
        c0728x1.c.setVisibility(this.E ? 0 : 8);
        c0728x1.d.setVisibility(this.E ? 0 : 8);
        c0728x1.e.setVisibility(this.E ? 8 : 0);
    }

    @Override // rikka.shizuku.AbstractActivityC0113d2, rikka.shizuku.AbstractActivityC0051b2, rikka.shizuku.T6, rikka.shizuku.S6, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        View viewInflate = getLayoutInflater().inflate(R.layout._0_resource_name_obfuscated_res_0x7f0c001f, (ViewGroup) null, false);
        int i = R.id._0_resource_name_obfuscated_res_0x7f09008f;
        Button button = (Button) AbstractC0750xn.h(viewInflate, R.id._0_resource_name_obfuscated_res_0x7f09008f);
        if (button != null) {
            i = R.id._0_resource_name_obfuscated_res_0x7f090106;
            MaterialCardView materialCardView = (MaterialCardView) AbstractC0750xn.h(viewInflate, R.id._0_resource_name_obfuscated_res_0x7f090106);
            if (materialCardView != null) {
                i = R.id._0_resource_name_obfuscated_res_0x7f09012d;
                MaterialCardView materialCardView2 = (MaterialCardView) AbstractC0750xn.h(viewInflate, R.id._0_resource_name_obfuscated_res_0x7f09012d);
                if (materialCardView2 != null) {
                    i = R.id._0_resource_name_obfuscated_res_0x7f090132;
                    MaterialCardView materialCardView3 = (MaterialCardView) AbstractC0750xn.h(viewInflate, R.id._0_resource_name_obfuscated_res_0x7f090132);
                    if (materialCardView3 != null) {
                        i = R.id._0_resource_name_obfuscated_res_0x7f090134;
                        MaterialCardView materialCardView4 = (MaterialCardView) AbstractC0750xn.h(viewInflate, R.id._0_resource_name_obfuscated_res_0x7f090134);
                        if (materialCardView4 != null) {
                            i = R.id._0_resource_name_obfuscated_res_0x7f090137;
                            MaterialButton materialButton = (MaterialButton) AbstractC0750xn.h(viewInflate, R.id._0_resource_name_obfuscated_res_0x7f090137);
                            if (materialButton != null) {
                                BorderNestedScrollView borderNestedScrollView = (BorderNestedScrollView) viewInflate;
                                i = R.id._0_resource_name_obfuscated_res_0x7f0901ad;
                                LinearLayout linearLayout = (LinearLayout) AbstractC0750xn.h(viewInflate, R.id._0_resource_name_obfuscated_res_0x7f0901ad);
                                if (linearLayout != null) {
                                    i = R.id._0_resource_name_obfuscated_res_0x7f0901ae;
                                    LinearLayout linearLayout2 = (LinearLayout) AbstractC0750xn.h(viewInflate, R.id._0_resource_name_obfuscated_res_0x7f0901ae);
                                    if (linearLayout2 != null) {
                                        i = R.id._0_resource_name_obfuscated_res_0x7f0901af;
                                        LinearLayout linearLayout3 = (LinearLayout) AbstractC0750xn.h(viewInflate, R.id._0_resource_name_obfuscated_res_0x7f0901af);
                                        if (linearLayout3 != null) {
                                            this.D = new C0728x1(borderNestedScrollView, button, materialCardView, materialCardView2, materialCardView3, materialCardView4, materialButton, linearLayout, linearLayout2, linearLayout3);
                                            setContentView(borderNestedScrollView);
                                            AbstractC0744xh abstractC0744xhL = l();
                                            if (abstractC0744xhL != null) {
                                                abstractC0744xhL.T(true);
                                            }
                                            boolean z = z();
                                            this.E = z;
                                            if (z) {
                                                A();
                                            }
                                            C0728x1 c0728x1 = this.D;
                                            C0728x1 c0728x2 = c0728x1 != null ? c0728x1 : null;
                                            B();
                                            Qr qr = O9.a;
                                            if (qr.a == AbstractC0046as.d) {
                                                qr.a = Boolean.valueOf(true ^ TextUtils.isEmpty(SystemProperties.get("ro.miui.ui.version.name")));
                                            }
                                            if (((Boolean) qr.a).booleanValue()) {
                                                c0728x2.b.setVisibility(0);
                                            }
                                            final int i2 = 0;
                                            c0728x2.a.setOnClickListener(new View.OnClickListener(this) { // from class: rikka.shizuku.w1
                                                public final /* synthetic */ AdbPairingTutorialActivity b;

                                                {
                                                    this.b = this;
                                                }

                                                @Override // android.view.View.OnClickListener
                                                public final void onClick(View view) {
                                                    AdbPairingTutorialActivity adbPairingTutorialActivity = this.b;
                                                    switch (i2) {
                                                        case 0:
                                                            int i3 = AdbPairingTutorialActivity.F;
                                                            Intent intent = new Intent("android.settings.APPLICATION_DEVELOPMENT_SETTINGS");
                                                            intent.setFlags(268468224);
                                                            intent.putExtra(":settings:fragment_args_key", "toggle_adb_wireless");
                                                            try {
                                                                adbPairingTutorialActivity.startActivity(intent);
                                                            } catch (ActivityNotFoundException unused) {
                                                                return;
                                                            }
                                                            break;
                                                        default:
                                                            int i4 = AdbPairingTutorialActivity.F;
                                                            Intent intent2 = new Intent("android.settings.APP_NOTIFICATION_SETTINGS");
                                                            intent2.putExtra("android.provider.extra.APP_PACKAGE", adbPairingTutorialActivity.getPackageName());
                                                            try {
                                                                adbPairingTutorialActivity.startActivity(intent2);
                                                            } catch (ActivityNotFoundException unused2) {
                                                                return;
                                                            }
                                                            break;
                                                    }
                                                }
                                            });
                                            MaterialButton materialButton2 = c0728x2.f;
                                            final int i3 = 1;
                                            materialButton2.setOnClickListener(new View.OnClickListener(this) { // from class: rikka.shizuku.w1
                                                public final /* synthetic */ AdbPairingTutorialActivity b;

                                                {
                                                    this.b = this;
                                                }

                                                @Override // android.view.View.OnClickListener
                                                public final void onClick(View view) {
                                                    AdbPairingTutorialActivity adbPairingTutorialActivity = this.b;
                                                    switch (i3) {
                                                        case 0:
                                                            int i4 = AdbPairingTutorialActivity.F;
                                                            Intent intent = new Intent("android.settings.APPLICATION_DEVELOPMENT_SETTINGS");
                                                            intent.setFlags(268468224);
                                                            intent.putExtra(":settings:fragment_args_key", "toggle_adb_wireless");
                                                            try {
                                                                adbPairingTutorialActivity.startActivity(intent);
                                                            } catch (ActivityNotFoundException unused) {
                                                                return;
                                                            }
                                                            break;
                                                        default:
                                                            int i5 = AdbPairingTutorialActivity.F;
                                                            Intent intent2 = new Intent("android.settings.APP_NOTIFICATION_SETTINGS");
                                                            intent2.putExtra("android.provider.extra.APP_PACKAGE", adbPairingTutorialActivity.getPackageName());
                                                            try {
                                                                adbPairingTutorialActivity.startActivity(intent2);
                                                            } catch (ActivityNotFoundException unused2) {
                                                                return;
                                                            }
                                                            break;
                                                    }
                                                }
                                            });
                                            return;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(viewInflate.getResources().getResourceName(i)));
    }

    @Override // rikka.shizuku.AbstractActivityC0051b2, android.app.Activity
    public final void onResume() {
        super.onResume();
        boolean z = z();
        if (z != this.E) {
            this.E = z;
            B();
            if (z) {
                A();
            }
        }
    }

    public final boolean z() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
        NotificationChannel notificationChannel = notificationManager.getNotificationChannel("adb_pairing");
        if (notificationManager.areNotificationsEnabled()) {
            return notificationChannel == null || notificationChannel.getImportance() != 0;
        }
        return false;
    }
}
