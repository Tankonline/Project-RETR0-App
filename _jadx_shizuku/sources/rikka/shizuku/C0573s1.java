package rikka.shizuku;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.google.android.material.textfield.TextInputLayout;
import moe.shizuku.privileged.api.R;

/* JADX INFO: renamed from: rikka.shizuku.s1, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0573s1 extends U9 {
    public C0235h1 m0;
    public final Xn n0 = new Xn(new C0542r1(1, new C0542r1(0, this)), new C0419n1(0, this), Ts.class);

    @Override // rikka.shizuku.U9
    public final Dialog N() {
        Context contextH = H();
        View viewInflate = LayoutInflater.from(contextH).inflate(R.layout._0_resource_name_obfuscated_res_0x7f0c001e, (ViewGroup) null, false);
        int i = R.id._0_resource_name_obfuscated_res_0x7f09014c;
        TextInputLayout textInputLayout = (TextInputLayout) AbstractC0750xn.h(viewInflate, R.id._0_resource_name_obfuscated_res_0x7f09014c);
        if (textInputLayout != null) {
            i = R.id._0_resource_name_obfuscated_res_0x7f090159;
            TextInputLayout textInputLayout2 = (TextInputLayout) AbstractC0750xn.h(viewInflate, R.id._0_resource_name_obfuscated_res_0x7f090159);
            if (textInputLayout2 != null) {
                i = android.R.id.text1;
                TextView textView = (TextView) AbstractC0750xn.h(viewInflate, android.R.id.text1);
                if (textView != null) {
                    i = android.R.id.text2;
                    LinearLayout linearLayout = (LinearLayout) AbstractC0750xn.h(viewInflate, android.R.id.text2);
                    if (linearLayout != null) {
                        this.m0 = new C0235h1((ScrollView) viewInflate, textInputLayout, textInputLayout2, textView, linearLayout);
                        Ch ch = new Ch(contextH);
                        E1 e1 = (E1) ch.b;
                        e1.d = e1.a.getText(R.string._0_resource_name_obfuscated_res_0x7f110056);
                        C0235h1 c0235h1 = this.m0;
                        if (c0235h1 == null) {
                            c0235h1 = null;
                        }
                        e1.s = (ScrollView) c0235h1.a;
                        e1.i = e1.a.getText(android.R.string.cancel);
                        e1.j = null;
                        ch.g(android.R.string.ok, null);
                        e1.k = e1.a.getText(R.string._0_resource_name_obfuscated_res_0x7f11004e);
                        e1.l = null;
                        J1 j1B = ch.b();
                        j1B.setCanceledOnTouchOutside(false);
                        j1B.setOnShowListener(new W0(this, j1B, 1));
                        return j1B;
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(viewInflate.getResources().getResourceName(i)));
    }

    @Override // rikka.shizuku.U9, rikka.shizuku.Ac
    public final void s() {
        View decorView;
        Display display;
        boolean z = true;
        this.E = true;
        Context contextH = H();
        if (!G().isInMultiWindowMode()) {
            Window window = G().getWindow();
            if (((window == null || (decorView = window.getDecorView()) == null || (display = decorView.getDisplay()) == null) ? -1 : display.getDisplayId()) <= 0) {
                z = false;
            }
        }
        C0235h1 c0235h1 = this.m0;
        if (c0235h1 == null) {
            c0235h1 = null;
        }
        ((TextView) c0235h1.d).setVisibility(z ? 0 : 8);
        C0235h1 c0235h2 = this.m0;
        ((LinearLayout) (c0235h2 != null ? c0235h2 : null).e).setVisibility(z ? 8 : 0);
        if (z) {
            J1 j1 = (J1) this.h0;
            if (j1 != null) {
                j1.setTitle(R.string._0_resource_name_obfuscated_res_0x7f110053);
            }
        } else {
            J1 j2 = (J1) this.h0;
            if (j2 != null) {
                j2.setTitle(R.string._0_resource_name_obfuscated_res_0x7f110056);
            }
        }
        ((Ts) this.n0.getValue()).c.d(this, new C0512q1(new C0450o1(this, 0, contextH), 0));
    }
}
