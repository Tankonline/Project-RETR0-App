package rikka.shizuku;

import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import moe.shizuku.privileged.api.R;

/* JADX INFO: loaded from: classes.dex */
public final class Oj extends AbstractC0491pb {
    public final int e;
    public EditText f;
    public final Y0 g;

    public Oj(C0460ob c0460ob, int i) {
        super(c0460ob);
        this.e = R.drawable._0_resource_name_obfuscated_res_0x7f080090;
        this.g = new Y0(5, this);
        if (i != 0) {
            this.e = i;
        }
    }

    @Override // rikka.shizuku.AbstractC0491pb
    public final void b() {
        q();
    }

    @Override // rikka.shizuku.AbstractC0491pb
    public final int c() {
        return R.string._0_resource_name_obfuscated_res_0x7f110103;
    }

    @Override // rikka.shizuku.AbstractC0491pb
    public final int d() {
        return this.e;
    }

    @Override // rikka.shizuku.AbstractC0491pb
    public final View.OnClickListener f() {
        return this.g;
    }

    @Override // rikka.shizuku.AbstractC0491pb
    public final boolean k() {
        return true;
    }

    @Override // rikka.shizuku.AbstractC0491pb
    public final boolean l() {
        EditText editText = this.f;
        return !(editText != null && (editText.getTransformationMethod() instanceof PasswordTransformationMethod));
    }

    @Override // rikka.shizuku.AbstractC0491pb
    public final void m(EditText editText) {
        this.f = editText;
        q();
    }

    @Override // rikka.shizuku.AbstractC0491pb
    public final void r() {
        EditText editText = this.f;
        if (editText != null) {
            if (editText.getInputType() == 16 || editText.getInputType() == 128 || editText.getInputType() == 144 || editText.getInputType() == 224) {
                this.f.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    }

    @Override // rikka.shizuku.AbstractC0491pb
    public final void s() {
        EditText editText = this.f;
        if (editText != null) {
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }
}
