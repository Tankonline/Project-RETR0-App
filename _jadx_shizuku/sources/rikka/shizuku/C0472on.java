package rikka.shizuku;

import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

/* JADX INFO: renamed from: rikka.shizuku.on, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0472on extends AbstractC0595sn {
    public final C0534qn c;
    public final float d;
    public final float e;

    public C0472on(C0534qn c0534qn, float f, float f2) {
        this.c = c0534qn;
        this.d = f;
        this.e = f2;
    }

    @Override // rikka.shizuku.AbstractC0595sn
    public final void a(Matrix matrix, C0287in c0287in, int i, Canvas canvas) {
        C0534qn c0534qn = this.c;
        float f = c0534qn.c;
        float f2 = this.e;
        float f3 = c0534qn.b;
        float f4 = this.d;
        RectF rectF = new RectF(0.0f, 0.0f, (float) Math.hypot(f - f2, f3 - f4), 0.0f);
        Matrix matrix2 = this.a;
        matrix2.set(matrix);
        matrix2.preTranslate(f4, f2);
        matrix2.preRotate(b());
        c0287in.getClass();
        rectF.bottom += i;
        rectF.offset(0.0f, -i);
        int[] iArr = C0287in.i;
        iArr[0] = c0287in.c;
        iArr[1] = c0287in.b;
        iArr[2] = c0287in.a;
        Paint paint = (Paint) c0287in.f;
        float f5 = rectF.left;
        paint.setShader(new LinearGradient(f5, rectF.top, f5, rectF.bottom, iArr, C0287in.j, Shader.TileMode.CLAMP));
        canvas.save();
        canvas.concat(matrix2);
        canvas.drawRect(rectF, paint);
        canvas.restore();
    }

    public final float b() {
        C0534qn c0534qn = this.c;
        return (float) Math.toDegrees(Math.atan((c0534qn.c - this.e) / (c0534qn.b - this.d)));
    }
}
