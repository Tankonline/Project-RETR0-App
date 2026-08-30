package rikka.shizuku;

import android.graphics.Matrix;
import android.graphics.Path;

/* JADX INFO: renamed from: rikka.shizuku.qn, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0534qn extends AbstractC0564rn {
    public float b;
    public float c;

    @Override // rikka.shizuku.AbstractC0564rn
    public final void a(Matrix matrix, Path path) {
        Matrix matrix2 = this.a;
        matrix.invert(matrix2);
        path.transform(matrix2);
        path.lineTo(this.b, this.c);
        path.transform(matrix);
    }
}
