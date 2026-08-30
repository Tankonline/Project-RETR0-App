package rikka.shizuku;

import android.graphics.Matrix;
import android.view.View;

/* JADX INFO: renamed from: rikka.shizuku.pt, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0509pt extends C0478ot {
    @Override // rikka.shizuku.An
    public final float c(View view) {
        return view.getTransitionAlpha();
    }

    @Override // rikka.shizuku.An
    public final void p(View view, float f) {
        view.setTransitionAlpha(f);
    }

    @Override // rikka.shizuku.C0478ot, rikka.shizuku.An
    public final void q(View view, int i) {
        view.setTransitionVisibility(i);
    }

    @Override // rikka.shizuku.C0478ot
    public final void v(View view, int i, int i2, int i3, int i4) {
        view.setLeftTopRightBottom(i, i2, i3, i4);
    }

    @Override // rikka.shizuku.C0478ot
    public final void w(View view, Matrix matrix) {
        view.transformMatrixToGlobal(matrix);
    }

    @Override // rikka.shizuku.C0478ot
    public final void x(View view, Matrix matrix) {
        view.transformMatrixToLocal(matrix);
    }
}
