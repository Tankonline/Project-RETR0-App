package rikka.shizuku;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import moe.shizuku.privileged.api.R;

/* JADX INFO: loaded from: classes.dex */
public class U9 extends Ac implements DialogInterface.OnCancelListener, DialogInterface.OnDismissListener {
    public Handler W;
    public boolean f0;
    public Dialog h0;
    public boolean i0;
    public boolean j0;
    public boolean k0;
    public final RunnableC0607t4 X = new RunnableC0607t4(3, this);
    public final Q9 Y = new Q9(this);
    public final R9 Z = new R9(this);
    public int a0 = 0;
    public int b0 = 0;
    public boolean c0 = true;
    public boolean d0 = true;
    public int e0 = -1;
    public final S9 g0 = new S9(this);
    public boolean l0 = false;

    @Override // rikka.shizuku.Ac
    public void A(Bundle bundle) {
        Dialog dialog = this.h0;
        if (dialog != null) {
            Bundle bundleOnSaveInstanceState = dialog.onSaveInstanceState();
            bundleOnSaveInstanceState.putBoolean("android:dialogShowing", false);
            bundle.putBundle("android:savedDialogState", bundleOnSaveInstanceState);
        }
        int i = this.a0;
        if (i != 0) {
            bundle.putInt("android:style", i);
        }
        int i2 = this.b0;
        if (i2 != 0) {
            bundle.putInt("android:theme", i2);
        }
        boolean z = this.c0;
        if (!z) {
            bundle.putBoolean("android:cancelable", z);
        }
        boolean z2 = this.d0;
        if (!z2) {
            bundle.putBoolean("android:showsDialog", z2);
        }
        int i3 = this.e0;
        if (i3 != -1) {
            bundle.putInt("android:backStackId", i3);
        }
    }

    @Override // rikka.shizuku.Ac
    public void B() {
        this.E = true;
        Dialog dialog = this.h0;
        if (dialog != null) {
            this.i0 = false;
            dialog.show();
            View decorView = this.h0.getWindow().getDecorView();
            decorView.setTag(R.id._0_resource_name_obfuscated_res_0x7f0901fc, this);
            decorView.setTag(R.id._0_resource_name_obfuscated_res_0x7f0901ff, this);
            decorView.setTag(R.id._0_resource_name_obfuscated_res_0x7f0901fe, this);
        }
    }

    @Override // rikka.shizuku.Ac
    public void C() {
        this.E = true;
        Dialog dialog = this.h0;
        if (dialog != null) {
            dialog.hide();
        }
    }

    @Override // rikka.shizuku.Ac
    public final void E(Bundle bundle) {
        Bundle bundle2;
        this.E = true;
        if (this.h0 == null || bundle == null || (bundle2 = bundle.getBundle("android:savedDialogState")) == null) {
            return;
        }
        this.h0.onRestoreInstanceState(bundle2);
    }

    @Override // rikka.shizuku.Ac
    public final void F(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Bundle bundle2;
        super.F(layoutInflater, viewGroup, bundle);
        if (this.G != null || this.h0 == null || bundle == null || (bundle2 = bundle.getBundle("android:savedDialogState")) == null) {
            return;
        }
        this.h0.onRestoreInstanceState(bundle2);
    }

    public final void M(boolean z, boolean z2) {
        if (this.j0) {
            return;
        }
        this.j0 = true;
        this.k0 = false;
        Dialog dialog = this.h0;
        if (dialog != null) {
            dialog.setOnDismissListener(null);
            this.h0.dismiss();
            if (!z2) {
                if (Looper.myLooper() == this.W.getLooper()) {
                    onDismiss(this.h0);
                } else {
                    this.W.post(this.X);
                }
            }
        }
        this.i0 = true;
        if (this.e0 >= 0) {
            Qc qcL = l();
            int i = this.e0;
            if (i < 0) {
                throw new IllegalArgumentException(AbstractC0535qo.b(i, "Bad id: "));
            }
            qcL.x(new Oc(qcL, i), z);
            this.e0 = -1;
            return;
        }
        L4 l4 = new L4(l());
        l4.p = true;
        Qc qc = this.t;
        if (qc != null && qc != l4.r) {
            throw new IllegalStateException("Cannot remove Fragment attached to a different FragmentManager. Fragment " + toString() + " is already attached to a FragmentManager.");
        }
        l4.b(new C0062bd(3, this));
        if (z) {
            l4.e(true, true);
        } else {
            l4.e(false, true);
        }
    }

    public Dialog N() {
        if (Qc.J(3)) {
            toString();
        }
        return new U6(H(), this.b0);
    }

    public final Dialog O() {
        Dialog dialog = this.h0;
        if (dialog != null) {
            return dialog;
        }
        throw new IllegalStateException("DialogFragment " + this + " does not have a Dialog.");
    }

    public final void P(Qc qc, String str) {
        this.j0 = false;
        this.k0 = true;
        L4 l4 = new L4(qc);
        l4.p = true;
        l4.f(0, this, str, 1);
        l4.e(false, true);
    }

    @Override // rikka.shizuku.Ac
    public final AbstractC0046as c() {
        return new T9(this, new C0770yc(this));
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public void onDismiss(DialogInterface dialogInterface) {
        if (this.i0) {
            return;
        }
        if (Qc.J(3)) {
            toString();
        }
        M(true, true);
    }

    @Override // rikka.shizuku.Ac
    public void s() {
        this.E = true;
    }

    @Override // rikka.shizuku.Ac
    public final void u(AbstractActivityC0051b2 abstractActivityC0051b2) {
        Object obj;
        super.u(abstractActivityC0051b2);
        Pi pi = this.R;
        pi.getClass();
        Pi.a("observeForever");
        S9 s9 = this.g0;
        C0158eh c0158eh = new C0158eh(pi, s9);
        C0502pm c0502pm = pi.b;
        C0409mm c0409mmA = c0502pm.a(s9);
        if (c0409mmA != null) {
            obj = c0409mmA.b;
        } else {
            C0409mm c0409mm = new C0409mm(s9, c0158eh);
            c0502pm.d++;
            C0409mm c0409mm2 = c0502pm.b;
            if (c0409mm2 == null) {
                c0502pm.a = c0409mm;
                c0502pm.b = c0409mm;
            } else {
                c0409mm2.c = c0409mm;
                c0409mm.d = c0409mm2;
                c0502pm.b = c0409mm;
            }
            obj = null;
        }
        AbstractC0220gh abstractC0220gh = (AbstractC0220gh) obj;
        if (abstractC0220gh instanceof C0189fh) {
            throw new IllegalArgumentException("Cannot add the same observer with different lifecycles");
        }
        if (abstractC0220gh == null) {
            c0158eh.b(true);
        }
        if (this.k0) {
            return;
        }
        this.j0 = false;
    }

    @Override // rikka.shizuku.Ac
    public void v(Bundle bundle) {
        super.v(bundle);
        this.W = new Handler();
        this.d0 = this.y == 0;
        if (bundle != null) {
            this.a0 = bundle.getInt("android:style", 0);
            this.b0 = bundle.getInt("android:theme", 0);
            this.c0 = bundle.getBoolean("android:cancelable", true);
            this.d0 = bundle.getBoolean("android:showsDialog", this.d0);
            this.e0 = bundle.getInt("android:backStackId", -1);
        }
    }

    @Override // rikka.shizuku.Ac
    public final void x() {
        this.E = true;
        Dialog dialog = this.h0;
        if (dialog != null) {
            this.i0 = true;
            dialog.setOnDismissListener(null);
            this.h0.dismiss();
            if (!this.j0) {
                onDismiss(this.h0);
            }
            this.h0 = null;
            this.l0 = false;
        }
    }

    @Override // rikka.shizuku.Ac
    public final void y() {
        this.E = true;
        if (!this.k0 && !this.j0) {
            this.j0 = true;
        }
        S9 s9 = this.g0;
        Pi pi = this.R;
        pi.getClass();
        Pi.a("removeObserver");
        AbstractC0220gh abstractC0220gh = (AbstractC0220gh) pi.b.b(s9);
        if (abstractC0220gh == null) {
            return;
        }
        abstractC0220gh.c();
        abstractC0220gh.b(false);
    }

    @Override // rikka.shizuku.Ac
    public final LayoutInflater z(Bundle bundle) {
        LayoutInflater layoutInflaterZ = super.z(bundle);
        boolean z = this.d0;
        if (z && !this.f0) {
            if (z && !this.l0) {
                try {
                    this.f0 = true;
                    Dialog dialogN = N();
                    this.h0 = dialogN;
                    if (this.d0) {
                        int i = this.a0;
                        if (i == 1 || i == 2) {
                            dialogN.requestWindowFeature(1);
                        } else if (i == 3) {
                            Window window = dialogN.getWindow();
                            if (window != null) {
                                window.addFlags(24);
                            }
                            dialogN.requestWindowFeature(1);
                        }
                        Context contextI = i();
                        if (contextI != null) {
                            this.h0.setOwnerActivity((Activity) contextI);
                        }
                        this.h0.setCancelable(this.c0);
                        this.h0.setOnCancelListener(this.Y);
                        this.h0.setOnDismissListener(this.Z);
                        this.l0 = true;
                    } else {
                        this.h0 = null;
                    }
                    this.f0 = false;
                } catch (Throwable th) {
                    this.f0 = false;
                    throw th;
                }
            }
            if (Qc.J(2)) {
                toString();
            }
            Dialog dialog = this.h0;
            if (dialog != null) {
                return layoutInflaterZ.cloneInContext(dialog.getContext());
            }
        } else if (Qc.J(2)) {
            toString();
        }
        return layoutInflaterZ;
    }

    @Override // android.content.DialogInterface.OnCancelListener
    public void onCancel(DialogInterface dialogInterface) {
    }
}
