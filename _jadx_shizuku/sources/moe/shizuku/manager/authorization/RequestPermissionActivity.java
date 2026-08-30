package moe.shizuku.manager.authorization;

import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import moe.shizuku.manager.authorization.RequestPermissionActivity;
import moe.shizuku.privileged.api.R;
import rikka.shizuku.AbstractActivityC0051b2;
import rikka.shizuku.AbstractC0125de;
import rikka.shizuku.AbstractC0494pe;
import rikka.shizuku.AbstractC0599sr;
import rikka.shizuku.AbstractC0688vn;
import rikka.shizuku.AbstractC0750xn;
import rikka.shizuku.Ch;
import rikka.shizuku.E1;
import rikka.shizuku.J1;
import rikka.shizuku.Nn;
import rikka.shizuku.Pl;
import rikka.shizuku.R3;

/* JADX INFO: loaded from: classes.dex */
public final class RequestPermissionActivity extends AbstractActivityC0051b2 {
    public static final /* synthetic */ int B = 0;
    public J1 A;

    public static void y(int i, int i2, int i3, boolean z, boolean z2) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("shizuku:request-permission-reply-allowed", z);
        bundle.putBoolean("shizuku:request-permission-reply-is-onetime", z2);
        try {
            try {
                Nn.k().m(i, i2, i3, bundle);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } catch (Throwable unused) {
            Log.e("ShizukuManager", "dispatchPermissionConfirmationResult");
        }
    }

    @Override // rikka.shizuku.AbstractActivityC0051b2, rikka.shizuku.T6, rikka.shizuku.S6, android.app.Activity
    public final void onCreate(Bundle bundle) throws InterruptedException {
        int iE;
        CharSequence charSequenceLoadLabel;
        super.onCreate(bundle);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Nn.b(new Pl(0, countDownLatch), true, (Handler) AbstractC0125de.b.getValue());
        try {
            countDownLatch.await(5L, TimeUnit.SECONDS);
            final int intExtra = getIntent().getIntExtra("uid", -1);
            final int intExtra2 = getIntent().getIntExtra("pid", -1);
            final int intExtra3 = getIntent().getIntExtra("requestCode", -1);
            ApplicationInfo applicationInfo = (ApplicationInfo) getIntent().getParcelableExtra("applicationInfo");
            if (intExtra == -1 || intExtra2 == -1 || applicationInfo == null) {
                finish();
                return;
            }
            if (Nn.c == 0) {
                iE = 0;
            } else {
                try {
                    iE = Nn.k().e("android.permission.GRANT_RUNTIME_PERMISSIONS");
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
            if (iE != 0) {
                Drawable drawable = getDrawable(R.drawable._0_resource_name_obfuscated_res_0x7f0800cc);
                if (drawable != null) {
                    TypedArray typedArrayObtainStyledAttributes = getTheme().obtainStyledAttributes(new int[]{android.R.attr.colorAccent});
                    int color = typedArrayObtainStyledAttributes.getColor(0, 0);
                    typedArrayObtainStyledAttributes.recycle();
                    drawable.setTint(color);
                }
                Ch ch = new Ch(this);
                E1 e1 = (E1) ch.b;
                e1.c = drawable;
                e1.d = "Shizuku: " + getString(R.string._0_resource_name_obfuscated_res_0x7f110030);
                e1.f = AbstractC0688vn.A(getString(R.string._0_resource_name_obfuscated_res_0x7f11002f, AbstractC0494pe.a.a()), 512);
                ch.g(android.R.string.ok, null);
                final int i = 1;
                e1.n = new DialogInterface.OnDismissListener(this) { // from class: rikka.shizuku.Ol
                    public final /* synthetic */ RequestPermissionActivity b;

                    {
                        this.b = this;
                    }

                    @Override // android.content.DialogInterface.OnDismissListener
                    public final void onDismiss(DialogInterface dialogInterface) {
                        RequestPermissionActivity requestPermissionActivity = this.b;
                        switch (i) {
                            case 0:
                                int i2 = RequestPermissionActivity.B;
                                requestPermissionActivity.finish();
                                break;
                            default:
                                int i3 = RequestPermissionActivity.B;
                                requestPermissionActivity.finish();
                                break;
                        }
                    }
                };
                J1 j1B = ch.b();
                j1B.setOnShowListener(new R3(1));
                try {
                    j1B.show();
                } catch (Throwable unused) {
                }
                y(intExtra, intExtra2, intExtra3, false, true);
                return;
            }
            try {
                charSequenceLoadLabel = applicationInfo.loadLabel(getPackageManager());
            } catch (Exception unused2) {
                charSequenceLoadLabel = applicationInfo.packageName;
            }
            View viewInflate = getLayoutInflater().inflate(R.layout._0_resource_name_obfuscated_res_0x7f0c0028, (ViewGroup) null, false);
            int i2 = android.R.id.button1;
            TextView textView = (TextView) AbstractC0750xn.h(viewInflate, android.R.id.button1);
            if (textView != null) {
                i2 = android.R.id.button3;
                TextView textView2 = (TextView) AbstractC0750xn.h(viewInflate, android.R.id.button3);
                if (textView2 != null) {
                    i2 = android.R.id.title;
                    TextView textView3 = (TextView) AbstractC0750xn.h(viewInflate, android.R.id.title);
                    if (textView3 != null) {
                        final int i3 = 0;
                        textView.setOnClickListener(new View.OnClickListener(this) { // from class: rikka.shizuku.Nl
                            public final /* synthetic */ RequestPermissionActivity b;

                            {
                                this.b = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                int i4 = intExtra;
                                int i5 = intExtra3;
                                int i6 = intExtra2;
                                RequestPermissionActivity requestPermissionActivity = this.b;
                                switch (i3) {
                                    case 0:
                                        int i7 = RequestPermissionActivity.B;
                                        RequestPermissionActivity.y(i4, i6, i5, true, false);
                                        J1 j1 = requestPermissionActivity.A;
                                        (j1 != null ? j1 : null).dismiss();
                                        break;
                                    default:
                                        int i8 = RequestPermissionActivity.B;
                                        RequestPermissionActivity.y(i4, i6, i5, false, true);
                                        J1 j2 = requestPermissionActivity.A;
                                        (j2 != null ? j2 : null).dismiss();
                                        break;
                                }
                            }
                        });
                        final int i4 = 1;
                        textView2.setOnClickListener(new View.OnClickListener(this) { // from class: rikka.shizuku.Nl
                            public final /* synthetic */ RequestPermissionActivity b;

                            {
                                this.b = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                int i5 = intExtra;
                                int i6 = intExtra3;
                                int i7 = intExtra2;
                                RequestPermissionActivity requestPermissionActivity = this.b;
                                switch (i4) {
                                    case 0:
                                        int i8 = RequestPermissionActivity.B;
                                        RequestPermissionActivity.y(i5, i7, i6, true, false);
                                        J1 j1 = requestPermissionActivity.A;
                                        (j1 != null ? j1 : null).dismiss();
                                        break;
                                    default:
                                        int i9 = RequestPermissionActivity.B;
                                        RequestPermissionActivity.y(i5, i7, i6, false, true);
                                        J1 j2 = requestPermissionActivity.A;
                                        (j2 != null ? j2 : null).dismiss();
                                        break;
                                }
                            }
                        });
                        textView3.setText(AbstractC0599sr.m(0, getString(R.string._0_resource_name_obfuscated_res_0x7f11010c, charSequenceLoadLabel, getString(R.string._0_resource_name_obfuscated_res_0x7f110109))));
                        Ch ch2 = new Ch(this);
                        E1 e2 = (E1) ch2.b;
                        e2.s = (LinearLayout) viewInflate;
                        e2.m = false;
                        final int i5 = 0;
                        e2.n = new DialogInterface.OnDismissListener(this) { // from class: rikka.shizuku.Ol
                            public final /* synthetic */ RequestPermissionActivity b;

                            {
                                this.b = this;
                            }

                            @Override // android.content.DialogInterface.OnDismissListener
                            public final void onDismiss(DialogInterface dialogInterface) {
                                RequestPermissionActivity requestPermissionActivity = this.b;
                                switch (i5) {
                                    case 0:
                                        int i6 = RequestPermissionActivity.B;
                                        requestPermissionActivity.finish();
                                        break;
                                    default:
                                        int i7 = RequestPermissionActivity.B;
                                        requestPermissionActivity.finish();
                                        break;
                                }
                            }
                        };
                        J1 j1B2 = ch2.b();
                        this.A = j1B2;
                        j1B2.setCanceledOnTouchOutside(false);
                        J1 j1 = this.A;
                        (j1 != null ? j1 : null).show();
                        return;
                    }
                }
            }
            throw new NullPointerException("Missing required view with ID: ".concat(viewInflate.getResources().getResourceName(i2)));
        } catch (TimeoutException e3) {
            Locale locale = Locale.ENGLISH;
            Log.e("ShizukuManager", "Binder not received in 5s", e3);
            finish();
        }
    }
}
