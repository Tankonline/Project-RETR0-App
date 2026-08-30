package rikka.shizuku;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.RegionIterator;
import android.graphics.drawable.Drawable;
import java.nio.ByteBuffer;

/* JADX INFO: renamed from: rikka.shizuku.cf, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0095cf {
    public final int a;
    public final Bitmap b;
    public final Canvas c;
    public final Paint d;
    public final Paint e;
    public final byte[] f;
    public final RectF g;
    public float h;
    public final boolean i;
    public final float[] j;
    public final float[] k;
    public final Rect l;
    public final Path m;
    public final Matrix n;

    public C0095cf(int i, Context context, boolean z) {
        int i2 = i * 2;
        this.a = i2;
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i2, i2, Bitmap.Config.ALPHA_8);
        this.b = bitmapCreateBitmap;
        this.c = new Canvas(bitmapCreateBitmap);
        this.f = new byte[i2 * i2];
        this.j = new float[i2];
        this.k = new float[i2];
        this.l = new Rect();
        this.g = new RectF();
        Paint paint = new Paint();
        this.d = paint;
        paint.setColor(-65536);
        paint.setStyle(Paint.Style.FILL);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));
        Paint paint2 = new Paint();
        this.e = paint2;
        paint2.setStrokeWidth(context.getResources().getDisplayMetrics().density * 2.0f);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setColor(-16777216);
        paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        this.m = new Path();
        this.n = new Matrix();
        this.h = 0.0f;
        this.i = z;
    }

    public static void a(float[] fArr, int i, int i2, int i3) {
        float[] fArr2 = new float[fArr.length - 1];
        int i4 = -1;
        float f = Float.MAX_VALUE;
        for (int i5 = i2 + 1; i5 <= i3; i5++) {
            float f2 = fArr[i5];
            if (f2 > -1.0f) {
                if (f != Float.MAX_VALUE) {
                    float f3 = ((f2 - fArr[i4]) / (i5 - i4)) - f;
                    float f4 = i;
                    if (f3 * f4 < 0.0f) {
                        while (i4 > i2) {
                            i4--;
                            if ((((fArr[i5] - fArr[i4]) / (i5 - i4)) - fArr2[i4]) * f4 >= 0.0f) {
                                break;
                            }
                        }
                    }
                } else {
                    i4 = i2;
                }
                f = (fArr[i5] - fArr[i4]) / (i5 - i4);
                for (int i6 = i4; i6 < i5; i6++) {
                    fArr2[i6] = f;
                    fArr[i6] = ((i6 - i4) * f) + fArr[i4];
                }
                i4 = i5;
            }
        }
    }

    public static float b(float f, float f2, float f3) {
        float f4 = f / f2;
        float f5 = f4 < 0.7853982f ? 0.6597222f : ((1.0f - f4) * 0.040449437f) + 0.6510417f;
        float f6 = f / f3;
        if (f6 > f5) {
            return (float) Math.sqrt(f5 / f6);
        }
        return 1.0f;
    }

    public static float e(Drawable drawable, int i, RectF rectF) {
        Rect rect = new Rect(drawable.getBounds());
        int iHeight = 0;
        drawable.setBounds(0, 0, i, i);
        Path iconMask = AbstractC0555re.a(drawable).getIconMask();
        Region region = new Region();
        region.setPath(iconMask, new Region(0, 0, i, i));
        Rect bounds = region.getBounds();
        RegionIterator regionIterator = new RegionIterator(region);
        Rect rect2 = new Rect();
        while (regionIterator.next(rect2)) {
            iHeight += rect2.height() * rect2.width();
        }
        if (rectF != null) {
            float f = i;
            rectF.set(bounds.left / f, bounds.top / f, 1.0f - (bounds.right / f), 1.0f - (bounds.bottom / f));
        }
        drawable.setBounds(rect);
        float f2 = iHeight;
        return b(f2, f2, i * i);
    }

    public final synchronized float c(Drawable drawable, Path path, boolean[] zArr) {
        try {
            if (I3.n && V.z(drawable)) {
                if (this.h == 0.0f) {
                    this.h = e(drawable, this.a, this.g);
                }
                return this.h;
            }
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();
            if (intrinsicWidth <= 0 || intrinsicHeight <= 0) {
                if (intrinsicWidth <= 0 || intrinsicWidth > this.a) {
                    intrinsicWidth = this.a;
                }
                if (intrinsicHeight <= 0 || intrinsicHeight > this.a) {
                    intrinsicHeight = this.a;
                }
            } else {
                int i = this.a;
                if (intrinsicWidth > i || intrinsicHeight > i) {
                    int iMax = Math.max(intrinsicWidth, intrinsicHeight);
                    int i2 = this.a;
                    intrinsicWidth = (intrinsicWidth * i2) / iMax;
                    intrinsicHeight = (i2 * intrinsicHeight) / iMax;
                }
            }
            int i3 = 0;
            this.b.eraseColor(0);
            drawable.setBounds(0, 0, intrinsicWidth, intrinsicHeight);
            drawable.draw(this.c);
            ByteBuffer byteBufferWrap = ByteBuffer.wrap(this.f);
            byteBufferWrap.rewind();
            this.b.copyPixelsToBuffer(byteBufferWrap);
            int i4 = this.a;
            int i5 = i4 + 1;
            int i6 = i4 - intrinsicWidth;
            int i7 = 0;
            int i8 = 0;
            int i9 = -1;
            int iMax2 = -1;
            int i10 = -1;
            while (i7 < intrinsicHeight) {
                int i11 = i3;
                int i12 = -1;
                int i13 = -1;
                while (i11 < intrinsicWidth) {
                    int i14 = i3;
                    int i15 = i6;
                    if ((this.f[i8] & 255) > 40) {
                        if (i13 == -1) {
                            i13 = i11;
                        }
                        i12 = i11;
                    }
                    i8++;
                    i11++;
                    i6 = i15;
                    i3 = i14;
                }
                int i16 = i6;
                int i17 = i3;
                i8 += i16;
                this.j[i7] = i13;
                this.k[i7] = i12;
                if (i13 != -1) {
                    if (i9 == -1) {
                        i9 = i7;
                    }
                    int iMin = Math.min(i5, i13);
                    iMax2 = Math.max(iMax2, i12);
                    i5 = iMin;
                    i10 = i7;
                }
                i7++;
                i6 = i16;
                i3 = i17;
            }
            int i18 = i3;
            if (i9 != -1 && iMax2 != -1) {
                a(this.j, 1, i9, i10);
                a(this.k, -1, i9, i10);
                float f = 0.0f;
                for (int i19 = i18; i19 < intrinsicHeight; i19++) {
                    float f2 = this.j[i19];
                    if (f2 > -1.0f) {
                        f = (this.k[i19] - f2) + 1.0f + f;
                    }
                }
                Rect rect = this.l;
                rect.left = i5;
                rect.right = iMax2;
                rect.top = i9;
                rect.bottom = i10;
                if (zArr != null && this.i && zArr.length > 0) {
                    zArr[i18] = d(path);
                }
                return b(f, ((iMax2 + 1) - i5) * ((i10 + 1) - i9), intrinsicWidth * intrinsicHeight);
            }
            return 1.0f;
        } catch (Throwable th) {
            throw th;
        }
    }

    public final boolean d(Path path) {
        Rect rect = this.l;
        if (Math.abs((rect.width() / rect.height()) - 1.0f) > 0.05f) {
            return false;
        }
        Matrix matrix = this.n;
        matrix.reset();
        matrix.setScale(rect.width(), rect.height());
        matrix.postTranslate(rect.left, rect.top);
        Path path2 = this.m;
        path.transform(matrix, path2);
        Canvas canvas = this.c;
        canvas.drawPath(path2, this.d);
        canvas.drawPath(path2, this.e);
        byte[] bArr = this.f;
        ByteBuffer byteBufferWrap = ByteBuffer.wrap(bArr);
        byteBufferWrap.rewind();
        this.b.copyPixelsToBuffer(byteBufferWrap);
        int i = rect.top;
        int i2 = this.a;
        int i3 = i * i2;
        int i4 = i2 - rect.right;
        int i5 = 0;
        while (i < rect.bottom) {
            int i6 = rect.left;
            int i7 = i3 + i6;
            while (i6 < rect.right) {
                if ((bArr[i7] & 255) > 40) {
                    i5++;
                }
                i7++;
                i6++;
            }
            i3 = i7 + i4;
            i++;
        }
        return ((float) i5) / ((float) (rect.height() * rect.width())) < 0.005f;
    }
}
