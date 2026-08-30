package rikka.shizuku;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.UserHandle;
import android.util.SparseArray;
import java.util.Arrays;
import me.zhanghai.android.appiconloader.iconloaderlib.FixedScaleDrawable;
import moe.shizuku.privileged.api.R;

/* JADX INFO: loaded from: classes.dex */
public final class I3 implements AutoCloseable {
    public static final boolean n;
    public static final int o;
    public final Rect a;
    public final Context b;
    public final Canvas c;
    public final PackageManager d;
    public final Xn e;
    public boolean f;
    public final int g;
    public C0095cf h;
    public C0257hn i;
    public final boolean j;
    public Drawable k;
    public int l;
    public final float[] m;

    static {
        n = Build.VERSION.SDK_INT >= 26;
        o = Color.rgb(245, 245, 245);
    }

    public I3(Context context, int i) {
        int i2 = context.getResources().getConfiguration().densityDpi;
        this.a = new Rect();
        this.l = -1;
        Paint paint = new Paint(3);
        Context applicationContext = context.getApplicationContext();
        this.b = applicationContext;
        this.j = true;
        this.g = i;
        this.d = applicationContext.getPackageManager();
        this.e = new Xn(2);
        Canvas canvas = new Canvas();
        this.c = canvas;
        canvas.setDrawFilter(new PaintFlagsDrawFilter(4, 2));
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(o);
        paint.setTextSize(context.getResources().getDisplayMetrics().density * 20.0f);
        this.l = -1;
        this.f = false;
        this.m = new float[1];
        this.f = true;
    }

    public final Ys a(Drawable drawable, UserHandle userHandle, boolean z) {
        float fC;
        Drawable drawable2 = drawable;
        float[] fArr = this.m;
        char c = 1;
        if (fArr == null) {
            fArr = new float[1];
        }
        int i = 0;
        if (drawable2 == null) {
            drawable2 = null;
        } else {
            if (z && n) {
                if (this.k == null) {
                    this.k = this.b.getDrawable(R.drawable._0_resource_name_obfuscated_res_0x7f08007a).mutate();
                }
                AdaptiveIconDrawable adaptiveIconDrawableI = V.i(this.k);
                adaptiveIconDrawableI.setBounds(0, 0, 1, 1);
                boolean[] zArr = new boolean[1];
                fC = c().c(drawable2, adaptiveIconDrawableI.getIconMask(), zArr);
                if (!V.z(drawable2) && !zArr[0]) {
                    FixedScaleDrawable fixedScaleDrawable = (FixedScaleDrawable) adaptiveIconDrawableI.getForeground();
                    fixedScaleDrawable.setDrawable(drawable2);
                    float intrinsicHeight = fixedScaleDrawable.getIntrinsicHeight();
                    float intrinsicWidth = fixedScaleDrawable.getIntrinsicWidth();
                    float f = fC * 0.46669f;
                    fixedScaleDrawable.a = f;
                    fixedScaleDrawable.b = f;
                    if (intrinsicHeight > intrinsicWidth && intrinsicWidth > 0.0f) {
                        fixedScaleDrawable.a = (intrinsicWidth / intrinsicHeight) * f;
                    } else if (intrinsicWidth > intrinsicHeight && intrinsicHeight > 0.0f) {
                        fixedScaleDrawable.b = (intrinsicHeight / intrinsicWidth) * f;
                    }
                    fC = c().c(adaptiveIconDrawableI, null, null);
                    ((ColorDrawable) adaptiveIconDrawableI.getBackground()).setColor(this.l);
                    drawable2 = adaptiveIconDrawableI;
                }
            } else {
                fC = c().c(drawable2, null, null);
            }
            fArr[0] = fC;
        }
        Bitmap bitmapB = b(drawable2, fArr[0]);
        if (n && V.z(drawable2)) {
            this.c.setBitmap(bitmapB);
            if (this.i == null) {
                this.i = new C0257hn(this.g);
            }
            C0257hn c0257hn = this.i;
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmapB);
            Canvas canvas = this.c;
            synchronized (c0257hn) {
                c0257hn.a(bitmapCreateBitmap, c0257hn.d, canvas);
            }
            this.c.setBitmap(null);
        }
        if (userHandle != null) {
            Drawable userBadgedIcon = this.d.getUserBadgedIcon(new R4(null, bitmapB), userHandle);
            bitmapB = userBadgedIcon instanceof BitmapDrawable ? ((BitmapDrawable) userBadgedIcon).getBitmap() : b(userBadgedIcon, 1.0f);
        }
        if (!this.f) {
            Xn xn = this.e;
            xn.getClass();
            int height = bitmapB.getHeight();
            int width = bitmapB.getWidth();
            int iSqrt = (int) Math.sqrt((height * width) / 20);
            if (iSqrt < 1) {
                iSqrt = 1;
            }
            float[] fArr2 = (float[]) xn.a;
            Arrays.fill(fArr2, 0.0f);
            float[] fArr3 = (float[]) xn.b;
            Arrays.fill(fArr3, 0.0f);
            int[] iArr = (int[]) xn.c;
            Arrays.fill(iArr, 0);
            int i2 = -1;
            int i3 = 0;
            int i4 = 0;
            float f2 = -1.0f;
            while (i3 < height) {
                char c2 = c;
                int i5 = i;
                while (i5 < width) {
                    int pixel = bitmapB.getPixel(i5, i3);
                    int i6 = i;
                    if (((pixel >> 24) & 255) >= 128) {
                        int i7 = pixel | (-16777216);
                        Color.colorToHSV(i7, fArr2);
                        int i8 = (int) fArr2[i6];
                        if (i8 >= 0 && i8 < fArr3.length) {
                            if (i4 < 20) {
                                iArr[i4] = i7;
                                i4++;
                            }
                            float f3 = fArr3[i8] + (fArr2[c2] * fArr2[2]);
                            fArr3[i8] = f3;
                            if (f3 > f2) {
                                i2 = i8;
                                f2 = f3;
                            }
                        }
                    }
                    i5 += iSqrt;
                    i = i6;
                }
                i3 += iSqrt;
                c = c2;
            }
            char c3 = c;
            int i9 = i;
            SparseArray sparseArray = (SparseArray) xn.d;
            sparseArray.clear();
            float f4 = -1.0f;
            for (int i10 = i9; i10 < i4; i10++) {
                Color.colorToHSV(iArr[i10], fArr2);
                if (((int) fArr2[i9]) == i2) {
                    float f5 = fArr2[c3];
                    float f6 = fArr2[2];
                    int i11 = ((int) (100.0f * f5)) + ((int) (10000.0f * f6));
                    float fFloatValue = f5 * f6;
                    Float f7 = (Float) sparseArray.get(i11);
                    if (f7 != null) {
                        fFloatValue += f7.floatValue();
                    }
                    sparseArray.put(i11, Float.valueOf(fFloatValue));
                    if (fFloatValue > f4) {
                        f4 = fFloatValue;
                    }
                }
            }
        }
        return new Ys(9, bitmapB);
    }

    /* JADX WARN: Code duplicated, block: B:25:0x008f  */
    public final Bitmap b(Drawable drawable, float f) {
        int i;
        int i2;
        Bitmap.Config config = Bitmap.Config.ARGB_8888;
        int i3 = this.g;
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i3, i3, config);
        if (drawable == null) {
            return bitmapCreateBitmap;
        }
        Canvas canvas = this.c;
        canvas.setBitmap(bitmapCreateBitmap);
        Rect bounds = drawable.getBounds();
        Rect rect = this.a;
        rect.set(bounds);
        if (n && V.z(drawable)) {
            float f2 = i3;
            int iMax = Math.max((int) Math.ceil(0.010416667f * f2), Math.round(((1.0f - f) * f2) / 2.0f));
            int i4 = i3 - (iMax * 2);
            drawable.setBounds(0, 0, i4, i4);
            float f3 = iMax;
            canvas.translate(f3, f3);
            drawable.draw(canvas);
            float f4 = -iMax;
            canvas.translate(f4, f4);
        } else {
            if (drawable instanceof BitmapDrawable) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                Bitmap bitmap = bitmapDrawable.getBitmap();
                if (bitmapCreateBitmap != null && bitmap.getDensity() == 0) {
                    bitmapDrawable.setTargetDensity(this.b.getResources().getDisplayMetrics());
                }
            }
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();
            if (intrinsicWidth <= 0 || intrinsicHeight <= 0) {
                i = i3;
                i2 = i;
            } else {
                float f5 = intrinsicWidth / intrinsicHeight;
                if (intrinsicWidth > intrinsicHeight) {
                    i2 = (int) (i3 / f5);
                    i = i3;
                } else if (intrinsicHeight > intrinsicWidth) {
                    i = (int) (i3 * f5);
                    i2 = i3;
                } else {
                    i = i3;
                    i2 = i;
                }
            }
            int i5 = (i3 - i) / 2;
            int i6 = (i3 - i2) / 2;
            drawable.setBounds(i5, i6, i + i5, i2 + i6);
            canvas.save();
            float f6 = i3 / 2;
            canvas.scale(f, f, f6, f6);
            drawable.draw(canvas);
            canvas.restore();
        }
        drawable.setBounds(rect);
        canvas.setBitmap(null);
        return bitmapCreateBitmap;
    }

    public final C0095cf c() {
        if (this.h == null) {
            boolean z = this.j;
            this.h = new C0095cf(this.g, this.b, z);
        }
        return this.h;
    }

    @Override // java.lang.AutoCloseable
    public final void close() {
        this.l = -1;
        this.f = false;
    }
}
