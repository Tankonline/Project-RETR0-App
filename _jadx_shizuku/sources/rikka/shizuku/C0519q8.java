package rikka.shizuku;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.os.Build;

/* JADX INFO: renamed from: rikka.shizuku.q8, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0519q8 extends Xh {
    public static final /* synthetic */ int z = 0;
    public C0488p8 y;

    @Override // rikka.shizuku.Xh
    public final void f(Canvas canvas) {
        if (this.y.q.isEmpty()) {
            super.f(canvas);
            return;
        }
        canvas.save();
        if (Build.VERSION.SDK_INT >= 26) {
            canvas.clipOutRect(this.y.q);
        } else {
            canvas.clipRect(this.y.q, Region.Op.DIFFERENCE);
        }
        super.f(canvas);
        canvas.restore();
    }

    @Override // rikka.shizuku.Xh, android.graphics.drawable.Drawable
    public final Drawable mutate() {
        this.y = new C0488p8(this.y);
        return this;
    }

    public final void q(float f, float f2, float f3, float f4) {
        RectF rectF = this.y.q;
        if (f == rectF.left && f2 == rectF.top && f3 == rectF.right && f4 == rectF.bottom) {
            return;
        }
        rectF.set(f, f2, f3, f4);
        invalidateSelf();
    }
}
