package rikka.shizuku;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;

/* JADX INFO: renamed from: rikka.shizuku.pn, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0503pn extends AbstractC0564rn {
    public static final RectF h = new RectF();
    public final float b;
    public final float c;
    public final float d;
    public final float e;
    public float f;
    public float g;

    public C0503pn(float f, float f2, float f3, float f4) {
        this.b = f;
        this.c = f2;
        this.d = f3;
        this.e = f4;
    }

    @Override // rikka.shizuku.AbstractC0564rn
    public final void a(Matrix matrix, Path path) {
        Matrix matrix2 = this.a;
        matrix.invert(matrix2);
        path.transform(matrix2);
        RectF rectF = h;
        rectF.set(this.b, this.c, this.d, this.e);
        path.arcTo(rectF, this.f, this.g, false);
        path.transform(matrix);
    }
}
