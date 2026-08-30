package moe.shizuku.manager.shell;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.material.card.MaterialCardView;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import moe.shizuku.privileged.api.R;
import rikka.shizuku.AbstractActivityC0113d2;
import rikka.shizuku.AbstractC0046as;
import rikka.shizuku.AbstractC0688vn;
import rikka.shizuku.AbstractC0744xh;
import rikka.shizuku.AbstractC0750xn;
import rikka.shizuku.An;
import rikka.shizuku.C0610t7;
import rikka.shizuku.C0805zg;
import rikka.shizuku.EnumC0588sg;
import rikka.shizuku.M0;
import rikka.shizuku.N0;
import rikka.shizuku.N6;
import rikka.shizuku.O0;
import rikka.shizuku.R0;
import rikka.shizuku.X0;
import rikka.shizuku.Y0;
import rikka.shizuku.au;
import rikka.shizuku.hu;
import rikka.widget.borderview.BorderNestedScrollView;

/* JADX INFO: loaded from: classes.dex */
public final class ShellTutorialActivity extends AbstractActivityC0113d2 {
    public static final /* synthetic */ int E = 0;
    public final O0 D;

    public ShellTutorialActivity() {
        M0 m0 = new M0(0);
        C0610t7 c0610t7 = new C0610t7(7, this);
        N6 n6 = this.k;
        String str = "activity_rq#" + this.j.getAndIncrement();
        n6.getClass();
        C0805zg c0805zg = this.d;
        if (c0805zg.c.a(EnumC0588sg.d)) {
            throw new IllegalStateException("LifecycleOwner " + this + " is attempting to register while current state is " + c0805zg.c + ". LifecycleOwners must call register before they are STARTED.");
        }
        n6.d(str);
        HashMap map = n6.c;
        R0 r0 = (R0) map.get(str);
        r0 = r0 == null ? new R0(c0805zg) : r0;
        N0 n0 = new N0(n6, str, c0610t7, m0);
        r0.a.a(n0);
        r0.b.add(n0);
        map.put(str, r0);
        this.D = new O0(n6, str, m0);
    }

    public static final void z(ShellTutorialActivity shellTutorialActivity, Uri uri, ContentResolver contentResolver, String str) {
        Uri uriCreateDocument = DocumentsContract.createDocument(shellTutorialActivity.getContentResolver(), uri, "application/octet-stream", str);
        if (uriCreateDocument != null) {
            try {
                OutputStream outputStreamOpenOutputStream = contentResolver.openOutputStream(uriCreateDocument);
                if (outputStreamOpenOutputStream != null) {
                    InputStream inputStreamOpen = shellTutorialActivity.getAssets().open(str);
                    byte[] bArr = new byte[8192];
                    for (int i = inputStreamOpen.read(bArr); i >= 0; i = inputStreamOpen.read(bArr)) {
                        outputStreamOpenOutputStream.write(bArr, 0, i);
                    }
                }
            } catch (Throwable unused) {
            }
        }
    }

    @Override // rikka.shizuku.AbstractActivityC0113d2, rikka.shizuku.AbstractActivityC0051b2, rikka.shizuku.T6, rikka.shizuku.S6, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        View viewInflate = getLayoutInflater().inflate(R.layout._0_resource_name_obfuscated_res_0x7f0c00a4, (ViewGroup) null, false);
        int i = android.R.id.button1;
        Button button = (Button) AbstractC0750xn.h(viewInflate, android.R.id.button1);
        if (button != null) {
            i = android.R.id.button2;
            MaterialCardView materialCardView = (MaterialCardView) AbstractC0750xn.h(viewInflate, android.R.id.button2);
            if (materialCardView != null) {
                i = android.R.id.content;
                LinearLayout linearLayout = (LinearLayout) AbstractC0750xn.h(viewInflate, android.R.id.content);
                if (linearLayout != null) {
                    BorderNestedScrollView borderNestedScrollView = (BorderNestedScrollView) viewInflate;
                    int i2 = android.R.id.summary;
                    TextView textView = (TextView) AbstractC0750xn.h(viewInflate, android.R.id.summary);
                    if (textView != null) {
                        i2 = R.id._0_resource_name_obfuscated_res_0x7f0901b4;
                        if (((TextView) AbstractC0750xn.h(viewInflate, R.id._0_resource_name_obfuscated_res_0x7f0901b4)) != null) {
                            i2 = R.id._0_resource_name_obfuscated_res_0x7f0901b5;
                            TextView textView2 = (TextView) AbstractC0750xn.h(viewInflate, R.id._0_resource_name_obfuscated_res_0x7f0901b5);
                            if (textView2 != null) {
                                i2 = R.id._0_resource_name_obfuscated_res_0x7f0901b6;
                                TextView textView3 = (TextView) AbstractC0750xn.h(viewInflate, R.id._0_resource_name_obfuscated_res_0x7f0901b6);
                                if (textView3 != null) {
                                    i2 = R.id._0_resource_name_obfuscated_res_0x7f0901cc;
                                    TextView textView4 = (TextView) AbstractC0750xn.h(viewInflate, R.id._0_resource_name_obfuscated_res_0x7f0901cc);
                                    if (textView4 != null) {
                                        i2 = R.id._0_resource_name_obfuscated_res_0x7f0901cd;
                                        TextView textView5 = (TextView) AbstractC0750xn.h(viewInflate, R.id._0_resource_name_obfuscated_res_0x7f0901cd);
                                        if (textView5 != null) {
                                            i2 = R.id._0_resource_name_obfuscated_res_0x7f0901ce;
                                            TextView textView6 = (TextView) AbstractC0750xn.h(viewInflate, R.id._0_resource_name_obfuscated_res_0x7f0901ce);
                                            if (textView6 != null) {
                                                setContentView(borderNestedScrollView);
                                                hu huVarF = An.f(linearLayout);
                                                int i3 = huVarF != null ? huVarF.e : 0;
                                                hu huVarF2 = An.f(linearLayout);
                                                int iR = AbstractC0046as.R(linearLayout.getResources().getDisplayMetrics().density * 8) + (huVarF2 != null ? huVarF2.f : 0);
                                                hu huVarF3 = An.f(linearLayout);
                                                int i4 = huVarF3 != null ? huVarF3.g : 0;
                                                hu huVarF4 = An.f(linearLayout);
                                                int i5 = huVarF4 != null ? huVarF4.h : 0;
                                                hu huVarF5 = An.f(linearLayout);
                                                if (huVarF5 != null) {
                                                    huVarF5.e = i3;
                                                    huVarF5.f = iR;
                                                    huVarF5.g = i4;
                                                    huVarF5.h = i5;
                                                    au auVar = huVarF5.p;
                                                    if (auVar != null) {
                                                        huVarF5.b(auVar);
                                                    }
                                                }
                                                AbstractC0744xh abstractC0744xhL = l();
                                                if (abstractC0744xhL != null) {
                                                    abstractC0744xhL.T(true);
                                                }
                                                textView.setText(AbstractC0688vn.A(getString(R.string._0_resource_name_obfuscated_res_0x7f11010e, "<font face=\"monospace\">rish</font>"), 512));
                                                textView4.setText(AbstractC0688vn.A(getString(R.string._0_resource_name_obfuscated_res_0x7f110127, "<font face=\"monospace\">rish</font>", "<font face=\"monospace\">rish_shizuku.dex</font>"), 0));
                                                textView5.setText(AbstractC0688vn.A(getString(R.string._0_resource_name_obfuscated_res_0x7f110129, "<font face=\"monospace\">rish</font>"), 0));
                                                textView2.setText(AbstractC0688vn.A(getString(R.string._0_resource_name_obfuscated_res_0x7f11012a, "Termux", "<font face=\"monospace\">PKG</font>", "<font face=\"monospace\">com.termux</font>", "<font face=\"monospace\">com.termux</font>"), 0));
                                                textView6.setText(AbstractC0688vn.A(getString(R.string._0_resource_name_obfuscated_res_0x7f11012b, "<font face=\"monospace\">sh rish</font>"), 0));
                                                textView3.setText(AbstractC0688vn.A(getString(R.string._0_resource_name_obfuscated_res_0x7f11012c, "<font face=\"monospace\">rish</font>", "<font face=\"monospace\">PATH</font>"), 0));
                                                button.setOnClickListener(new Y0(6, this));
                                                materialCardView.setOnClickListener(new X0(4));
                                                return;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    i = i2;
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(viewInflate.getResources().getResourceName(i)));
    }
}
