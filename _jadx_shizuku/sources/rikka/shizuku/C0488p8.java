package rikka.shizuku;

import android.graphics.RectF;
import android.graphics.drawable.Drawable;

/* JADX INFO: renamed from: rikka.shizuku.p8, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0488p8 extends Wh {
    public final RectF q;

    public C0488p8(C0317jn c0317jn, RectF rectF) {
        super(c0317jn);
        this.q = rectF;
    }

    @Override // rikka.shizuku.Wh, android.graphics.drawable.Drawable.ConstantState
    public final Drawable newDrawable() {
        C0519q8 c0519q8 = new C0519q8(this);
        c0519q8.y = this;
        c0519q8.invalidateSelf();
        return c0519q8;
    }

    public C0488p8(C0488p8 c0488p8) {
        super(c0488p8);
        this.q = c0488p8.q;
    }
}
