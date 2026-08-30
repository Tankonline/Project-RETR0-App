package rikka.shizuku;

import android.text.InputFilter;
import android.text.method.TransformationMethod;
import android.widget.TextView;

/* JADX INFO: loaded from: classes.dex */
public final class Ya extends AbstractC0046as {
    public final Xa i;

    public Ya(TextView textView) {
        this.i = new Xa(textView);
    }

    @Override // rikka.shizuku.AbstractC0046as
    public final boolean E() {
        return this.i.k;
    }

    @Override // rikka.shizuku.AbstractC0046as
    public final void S(boolean z) {
        if (Ma.j != null) {
            this.i.S(z);
        }
    }

    @Override // rikka.shizuku.AbstractC0046as
    public final void T(boolean z) {
        boolean z2 = Ma.j != null;
        Xa xa = this.i;
        if (z2) {
            xa.T(z);
        } else {
            xa.k = z;
        }
    }

    @Override // rikka.shizuku.AbstractC0046as
    public final TransformationMethod Z(TransformationMethod transformationMethod) {
        return !(Ma.j != null) ? transformationMethod : this.i.Z(transformationMethod);
    }

    @Override // rikka.shizuku.AbstractC0046as
    public final InputFilter[] w(InputFilter[] inputFilterArr) {
        return !(Ma.j != null) ? inputFilterArr : this.i.w(inputFilterArr);
    }
}
