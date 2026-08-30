package rikka.shizuku;

import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;

/* JADX INFO: renamed from: rikka.shizuku.hn, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0257hn {
    public final int a;
    public final Paint b = new Paint(3);
    public final Paint c = new Paint(3);
    public final BlurMaskFilter d;

    public C0257hn(int i) {
        this.a = i;
        this.d = new BlurMaskFilter(i * 0.010416667f, BlurMaskFilter.Blur.NORMAL);
    }

    public final synchronized void a(Bitmap bitmap, BlurMaskFilter blurMaskFilter, Canvas canvas) {
        int[] iArr = new int[2];
        this.b.setMaskFilter(blurMaskFilter);
        Bitmap bitmapExtractAlpha = bitmap.extractAlpha(this.b, iArr);
        this.c.setAlpha(30);
        canvas.drawBitmap(bitmapExtractAlpha, iArr[0], iArr[1], this.c);
        this.c.setAlpha(61);
        canvas.drawBitmap(bitmapExtractAlpha, iArr[0], (this.a * 0.020833334f) + iArr[1], this.c);
        this.c.setAlpha(255);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, this.c);
    }
}
