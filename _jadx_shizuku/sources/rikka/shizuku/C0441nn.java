package rikka.shizuku;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Shader;

/* JADX INFO: renamed from: rikka.shizuku.nn, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0441nn extends AbstractC0595sn {
    public final C0503pn c;

    public C0441nn(C0503pn c0503pn) {
        this.c = c0503pn;
    }

    @Override // rikka.shizuku.AbstractC0595sn
    public final void a(Matrix matrix, C0287in c0287in, int i, Canvas canvas) {
        C0503pn c0503pn = this.c;
        float f = c0503pn.f;
        float f2 = c0503pn.g;
        RectF rectF = new RectF(c0503pn.b, c0503pn.c, c0503pn.d, c0503pn.e);
        c0287in.getClass();
        boolean z = f2 < 0.0f;
        Path path = (Path) c0287in.h;
        int[] iArr = C0287in.k;
        if (z) {
            iArr[0] = 0;
            iArr[1] = c0287in.c;
            iArr[2] = c0287in.b;
            iArr[3] = c0287in.a;
        } else {
            path.rewind();
            path.moveTo(rectF.centerX(), rectF.centerY());
            path.arcTo(rectF, f, f2);
            path.close();
            float f3 = -i;
            rectF.inset(f3, f3);
            iArr[0] = 0;
            iArr[1] = c0287in.a;
            iArr[2] = c0287in.b;
            iArr[3] = c0287in.c;
        }
        float fWidth = rectF.width() / 2.0f;
        if (fWidth <= 0.0f) {
            return;
        }
        float f4 = 1.0f - (i / fWidth);
        float[] fArr = C0287in.l;
        fArr[1] = f4;
        fArr[2] = ((1.0f - f4) / 2.0f) + f4;
        RadialGradient radialGradient = new RadialGradient(rectF.centerX(), rectF.centerY(), fWidth, iArr, fArr, Shader.TileMode.CLAMP);
        Paint paint = (Paint) c0287in.e;
        paint.setShader(radialGradient);
        canvas.save();
        canvas.concat(matrix);
        canvas.scale(1.0f, rectF.height() / rectF.width());
        if (!z) {
            canvas.clipPath(path, Region.Op.DIFFERENCE);
            canvas.drawPath(path, (Paint) c0287in.g);
        }
        canvas.drawArc(rectF, f, f2, true, paint);
        canvas.restore();
    }
}
