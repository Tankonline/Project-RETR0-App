package rikka.shizuku;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.window.OnBackInvokedDispatcher;
import moe.shizuku.privileged.api.R;

/* JADX INFO: loaded from: classes.dex */
public class U6 extends Dialog implements InterfaceC0743xg, Bm {
    public C0805zg a;
    public final R2 b;
    public final C0622tj c;

    public U6(Context context, int i) {
        super(context, i);
        this.b = new R2(new Am(this, new C0419n1(4, this)));
        this.c = new C0622tj(new G0(6, this));
    }

    public static void a(U6 u6) {
        super.onBackPressed();
    }

    @Override // android.app.Dialog
    public void addContentView(View view, ViewGroup.LayoutParams layoutParams) {
        c();
        super.addContentView(view, layoutParams);
    }

    @Override // rikka.shizuku.Bm
    public final R2 b() {
        return (R2) this.b.c;
    }

    public final void c() {
        getWindow().getDecorView().setTag(R.id._0_resource_name_obfuscated_res_0x7f0901fc, this);
        getWindow().getDecorView().setTag(R.id._0_resource_name_obfuscated_res_0x7f0901fd, this);
        getWindow().getDecorView().setTag(R.id._0_resource_name_obfuscated_res_0x7f0901fe, this);
    }

    @Override // rikka.shizuku.InterfaceC0743xg
    public final C0805zg e() {
        C0805zg c0805zg = this.a;
        if (c0805zg != null) {
            return c0805zg;
        }
        C0805zg c0805zg2 = new C0805zg(this);
        this.a = c0805zg2;
        return c0805zg2;
    }

    @Override // android.app.Dialog
    public final void onBackPressed() {
        this.c.a();
    }

    @Override // android.app.Dialog
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (Build.VERSION.SDK_INT >= 33) {
            OnBackInvokedDispatcher onBackInvokedDispatcher = getOnBackInvokedDispatcher();
            C0622tj c0622tj = this.c;
            c0622tj.e = onBackInvokedDispatcher;
            c0622tj.b(c0622tj.g);
        }
        Am am = (Am) this.b.b;
        if (!am.e) {
            am.a();
        }
        Bm bm = am.a;
        if (bm.e().c.a(EnumC0588sg.d)) {
            throw new IllegalStateException(("performRestore cannot be called when owner is " + bm.e().c).toString());
        }
        if (am.g) {
            throw new IllegalStateException("SavedStateRegistry was already restored.");
        }
        Bundle bundleF = null;
        if (bundle != null && bundle.containsKey("androidx.lifecycle.BundlableSavedStateRegistry.key")) {
            bundleF = X8.F("androidx.lifecycle.BundlableSavedStateRegistry.key", bundle);
        }
        am.f = bundleF;
        am.g = true;
        C0805zg c0805zg = this.a;
        if (c0805zg == null) {
            c0805zg = new C0805zg(this);
            this.a = c0805zg;
        }
        c0805zg.d(EnumC0557rg.ON_CREATE);
    }

    @Override // android.app.Dialog
    public final Bundle onSaveInstanceState() {
        Bundle bundleOnSaveInstanceState = super.onSaveInstanceState();
        this.b.Q(bundleOnSaveInstanceState);
        return bundleOnSaveInstanceState;
    }

    @Override // android.app.Dialog
    public final void onStart() {
        super.onStart();
        C0805zg c0805zg = this.a;
        if (c0805zg == null) {
            c0805zg = new C0805zg(this);
            this.a = c0805zg;
        }
        c0805zg.d(EnumC0557rg.ON_RESUME);
    }

    @Override // android.app.Dialog
    public void onStop() {
        C0805zg c0805zg = this.a;
        if (c0805zg == null) {
            c0805zg = new C0805zg(this);
            this.a = c0805zg;
        }
        c0805zg.d(EnumC0557rg.ON_DESTROY);
        this.a = null;
        super.onStop();
    }

    @Override // android.app.Dialog
    public void setContentView(int i) {
        c();
        super.setContentView(i);
    }

    @Override // android.app.Dialog
    public void setContentView(View view) {
        c();
        super.setContentView(view);
    }

    @Override // android.app.Dialog
    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        c();
        super.setContentView(view, layoutParams);
    }
}
