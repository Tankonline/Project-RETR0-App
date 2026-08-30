package rikka.shizuku;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import java.util.BitSet;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class Xh extends Drawable implements InterfaceC0657un {
    public static final Paint x;
    public Wh a;
    public final AbstractC0595sn[] b;
    public final AbstractC0595sn[] c;
    public final BitSet d;
    public boolean e;
    public final Matrix f;
    public final Path g;
    public final Path h;
    public final RectF i;
    public final RectF j;
    public final Region k;
    public final Region l;
    public C0317jn m;
    public final Paint n;
    public final Paint o;
    public final C0287in p;
    public final Ys q;
    public final C0379ln r;
    public PorterDuffColorFilter s;
    public PorterDuffColorFilter t;
    public int u;
    public final RectF v;
    public boolean w;

    static {
        Paint paint = new Paint(1);
        x = paint;
        paint.setColor(-1);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
    }

    public Xh() {
        this(new C0317jn(1));
    }

    @Override // rikka.shizuku.InterfaceC0657un
    public final void a(C0317jn c0317jn) {
        this.a.a = c0317jn;
        invalidateSelf();
    }

    public final void b(RectF rectF, Path path) {
        Wh wh = this.a;
        this.r.a(wh.a, wh.i, rectF, this.q, path);
        if (this.a.h != 1.0f) {
            Matrix matrix = this.f;
            matrix.reset();
            float f = this.a.h;
            matrix.setScale(f, f, rectF.width() / 2.0f, rectF.height() / 2.0f);
            path.transform(matrix);
        }
        path.computeBounds(this.v, true);
    }

    public final int c(int i) {
        int i2;
        Wh wh = this.a;
        float f = wh.m + 0.0f + wh.l;
        Ga ga = wh.b;
        if (ga == null || !ga.a || E6.e(i, 255) != ga.d) {
            return i;
        }
        float f2 = ga.e;
        float fMin = (f2 <= 0.0f || f <= 0.0f) ? 0.0f : Math.min(((((float) Math.log1p(f / f2)) * 4.5f) + 2.0f) / 100.0f, 1.0f);
        int iAlpha = Color.alpha(i);
        int iH = AbstractC0744xh.H(E6.e(i, 255), ga.b, fMin);
        if (fMin > 0.0f && (i2 = ga.c) != 0) {
            iH = E6.c(E6.e(i2, Ga.f), iH);
        }
        return E6.e(iH, iAlpha);
    }

    public final void d(Canvas canvas) {
        if (this.d.cardinality() > 0) {
            Log.w("Xh", "Compatibility shadow requested but can't be drawn for all operations in this shape.");
        }
        int i = this.a.o;
        Path path = this.g;
        C0287in c0287in = this.p;
        if (i != 0) {
            canvas.drawPath(path, (Paint) c0287in.d);
        }
        for (int i2 = 0; i2 < 4; i2++) {
            AbstractC0595sn abstractC0595sn = this.b[i2];
            int i3 = this.a.n;
            Matrix matrix = AbstractC0595sn.b;
            abstractC0595sn.a(matrix, c0287in, i3, canvas);
            this.c[i2].a(matrix, c0287in, this.a.n, canvas);
        }
        if (this.w) {
            double d = 0;
            int iSin = (int) (Math.sin(Math.toRadians(d)) * ((double) this.a.o));
            int iCos = (int) (Math.cos(Math.toRadians(d)) * ((double) this.a.o));
            canvas.translate(-iSin, -iCos);
            canvas.drawPath(path, x);
            canvas.translate(iSin, iCos);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        Paint paint = this.n;
        paint.setColorFilter(this.s);
        int alpha = paint.getAlpha();
        int i = this.a.k;
        paint.setAlpha(((i + (i >>> 7)) * alpha) >>> 8);
        Paint paint2 = this.o;
        paint2.setColorFilter(this.t);
        paint2.setStrokeWidth(this.a.j);
        int alpha2 = paint2.getAlpha();
        int i2 = this.a.k;
        paint2.setAlpha(((i2 + (i2 >>> 7)) * alpha2) >>> 8);
        boolean z = this.e;
        Path path = this.g;
        if (z) {
            float f = -(i() ? paint2.getStrokeWidth() / 2.0f : 0.0f);
            C0317jn c0317jn = this.a.a;
            C0317jn c0317jnG = c0317jn.g();
            S7 a1 = c0317jn.e;
            if (!(a1 instanceof Dl)) {
                a1 = new A1(f, a1);
            }
            c0317jnG.e = a1;
            S7 a2 = c0317jn.f;
            if (!(a2 instanceof Dl)) {
                a2 = new A1(f, a2);
            }
            c0317jnG.f = a2;
            S7 a3 = c0317jn.h;
            if (!(a3 instanceof Dl)) {
                a3 = new A1(f, a3);
            }
            c0317jnG.h = a3;
            S7 a4 = c0317jn.g;
            if (!(a4 instanceof Dl)) {
                a4 = new A1(f, a4);
            }
            c0317jnG.g = a4;
            C0317jn c0317jnA = c0317jnG.a();
            this.m = c0317jnA;
            float f2 = this.a.i;
            RectF rectF = this.j;
            rectF.set(g());
            float strokeWidth = i() ? paint2.getStrokeWidth() / 2.0f : 0.0f;
            rectF.inset(strokeWidth, strokeWidth);
            this.r.a(c0317jnA, f2, rectF, null, this.h);
            b(g(), path);
            this.e = false;
        }
        Wh wh = this.a;
        wh.getClass();
        if (wh.n > 0) {
            int i3 = Build.VERSION.SDK_INT;
            if (!this.a.a.f(g()) && !path.isConvex() && i3 < 29) {
                canvas.save();
                double d = 0;
                canvas.translate((int) (Math.sin(Math.toRadians(d)) * ((double) this.a.o)), (int) (Math.cos(Math.toRadians(d)) * ((double) this.a.o)));
                if (this.w) {
                    RectF rectF2 = this.v;
                    int iWidth = (int) (rectF2.width() - getBounds().width());
                    int iHeight = (int) (rectF2.height() - getBounds().height());
                    if (iWidth < 0 || iHeight < 0) {
                        throw new IllegalStateException("Invalid shadow bounds. Check that the treatments result in a valid path.");
                    }
                    Bitmap bitmapCreateBitmap = Bitmap.createBitmap((this.a.n * 2) + ((int) rectF2.width()) + iWidth, (this.a.n * 2) + ((int) rectF2.height()) + iHeight, Bitmap.Config.ARGB_8888);
                    Canvas canvas2 = new Canvas(bitmapCreateBitmap);
                    float f3 = (getBounds().left - this.a.n) - iWidth;
                    float f4 = (getBounds().top - this.a.n) - iHeight;
                    canvas2.translate(-f3, -f4);
                    d(canvas2);
                    canvas.drawBitmap(bitmapCreateBitmap, f3, f4, (Paint) null);
                    bitmapCreateBitmap.recycle();
                    canvas.restore();
                } else {
                    d(canvas);
                    canvas.restore();
                }
            }
        }
        Wh wh2 = this.a;
        Paint.Style style = wh2.p;
        if (style == Paint.Style.FILL_AND_STROKE || style == Paint.Style.FILL) {
            e(canvas, paint, path, wh2.a, g());
        }
        if (i()) {
            f(canvas);
        }
        paint.setAlpha(alpha);
        paint2.setAlpha(alpha2);
    }

    public final void e(Canvas canvas, Paint paint, Path path, C0317jn c0317jn, RectF rectF) {
        if (!c0317jn.f(rectF)) {
            canvas.drawPath(path, paint);
        } else {
            float fA = c0317jn.f.a(rectF) * this.a.i;
            canvas.drawRoundRect(rectF, fA, fA, paint);
        }
    }

    public void f(Canvas canvas) {
        Paint paint = this.o;
        Path path = this.h;
        C0317jn c0317jn = this.m;
        RectF rectF = this.j;
        rectF.set(g());
        float strokeWidth = i() ? paint.getStrokeWidth() / 2.0f : 0.0f;
        rectF.inset(strokeWidth, strokeWidth);
        e(canvas, paint, path, c0317jn, rectF);
    }

    public final RectF g() {
        RectF rectF = this.i;
        rectF.set(getBounds());
        return rectF;
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.a.k;
    }

    @Override // android.graphics.drawable.Drawable
    public final Drawable.ConstantState getConstantState() {
        return this.a;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public void getOutline(Outline outline) {
        this.a.getClass();
        if (this.a.a.f(g())) {
            outline.setRoundRect(getBounds(), h() * this.a.i);
            return;
        }
        RectF rectFG = g();
        Path path = this.g;
        b(rectFG, path);
        int i = Build.VERSION.SDK_INT;
        if (i >= 30) {
            AbstractC0275ia.a(outline, path);
            return;
        }
        if (i >= 29) {
            try {
                AbstractC0244ha.a(outline, path);
            } catch (IllegalArgumentException unused) {
            }
        } else if (path.isConvex()) {
            AbstractC0244ha.a(outline, path);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean getPadding(Rect rect) {
        Rect rect2 = this.a.g;
        if (rect2 == null) {
            return super.getPadding(rect);
        }
        rect.set(rect2);
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public final Region getTransparentRegion() {
        Rect bounds = getBounds();
        Region region = this.k;
        region.set(bounds);
        RectF rectFG = g();
        Path path = this.g;
        b(rectFG, path);
        Region region2 = this.l;
        region2.setPath(path, region);
        region.op(region2, Region.Op.DIFFERENCE);
        return region;
    }

    public final float h() {
        return this.a.a.e.a(g());
    }

    public final boolean i() {
        Paint.Style style = this.a.p;
        return (style == Paint.Style.FILL_AND_STROKE || style == Paint.Style.STROKE) && this.o.getStrokeWidth() > 0.0f;
    }

    @Override // android.graphics.drawable.Drawable
    public final void invalidateSelf() {
        this.e = true;
        super.invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        if (super.isStateful()) {
            return true;
        }
        ColorStateList colorStateList = this.a.e;
        if (colorStateList != null && colorStateList.isStateful()) {
            return true;
        }
        this.a.getClass();
        ColorStateList colorStateList2 = this.a.d;
        if (colorStateList2 != null && colorStateList2.isStateful()) {
            return true;
        }
        ColorStateList colorStateList3 = this.a.c;
        return colorStateList3 != null && colorStateList3.isStateful();
    }

    public final void j(Context context) {
        this.a.b = new Ga(context);
        p();
    }

    public final void k(float f) {
        Wh wh = this.a;
        if (wh.m != f) {
            wh.m = f;
            p();
        }
    }

    public final void l(ColorStateList colorStateList) {
        Wh wh = this.a;
        if (wh.c != colorStateList) {
            wh.c = colorStateList;
            onStateChange(getState());
        }
    }

    public final void m() {
        this.p.d(-12303292);
        this.a.getClass();
        super.invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable mutate() {
        this.a = new Wh(this.a);
        return this;
    }

    public final boolean n(int[] iArr) {
        boolean z;
        Paint paint;
        int color;
        int colorForState;
        Paint paint2;
        int color2;
        int colorForState2;
        if (this.a.c == null || color2 == (colorForState2 = this.a.c.getColorForState(iArr, (color2 = (paint2 = this.n).getColor())))) {
            z = false;
        } else {
            paint2.setColor(colorForState2);
            z = true;
        }
        if (this.a.d == null || color == (colorForState = this.a.d.getColorForState(iArr, (color = (paint = this.o).getColor())))) {
            return z;
        }
        paint.setColor(colorForState);
        return true;
    }

    public final boolean o() {
        PorterDuffColorFilter porterDuffColorFilter;
        PorterDuffColorFilter porterDuffColorFilter2 = this.s;
        PorterDuffColorFilter porterDuffColorFilter3 = this.t;
        Wh wh = this.a;
        ColorStateList colorStateList = wh.e;
        PorterDuff.Mode mode = wh.f;
        Paint paint = this.n;
        if (colorStateList == null || mode == null) {
            int color = paint.getColor();
            int iC = c(color);
            this.u = iC;
            porterDuffColorFilter = iC != color ? new PorterDuffColorFilter(iC, PorterDuff.Mode.SRC_IN) : null;
        } else {
            int iC2 = c(colorStateList.getColorForState(getState(), 0));
            this.u = iC2;
            porterDuffColorFilter = new PorterDuffColorFilter(iC2, mode);
        }
        this.s = porterDuffColorFilter;
        this.a.getClass();
        this.t = null;
        this.a.getClass();
        return (Objects.equals(porterDuffColorFilter2, this.s) && Objects.equals(porterDuffColorFilter3, this.t)) ? false : true;
    }

    @Override // android.graphics.drawable.Drawable
    public final void onBoundsChange(Rect rect) {
        this.e = true;
        super.onBoundsChange(rect);
    }

    @Override // android.graphics.drawable.Drawable
    public boolean onStateChange(int[] iArr) {
        boolean z = n(iArr) || o();
        if (z) {
            invalidateSelf();
        }
        return z;
    }

    public final void p() {
        Wh wh = this.a;
        float f = wh.m + 0.0f;
        wh.n = (int) Math.ceil(0.75f * f);
        this.a.o = (int) Math.ceil(f * 0.25f);
        o();
        super.invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        Wh wh = this.a;
        if (wh.k != i) {
            wh.k = i;
            super.invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.a.getClass();
        super.invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public final void setTint(int i) {
        setTintList(ColorStateList.valueOf(i));
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintList(ColorStateList colorStateList) {
        this.a.e = colorStateList;
        o();
        super.invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintMode(PorterDuff.Mode mode) {
        Wh wh = this.a;
        if (wh.f != mode) {
            wh.f = mode;
            o();
            super.invalidateSelf();
        }
    }

    public Xh(Context context, AttributeSet attributeSet, int i, int i2) {
        this(C0317jn.c(context, attributeSet, i, i2).a());
    }

    public Xh(C0317jn c0317jn) {
        this(new Wh(c0317jn));
    }

    public Xh(Wh wh) {
        C0379ln c0379ln;
        this.b = new AbstractC0595sn[4];
        this.c = new AbstractC0595sn[4];
        this.d = new BitSet(8);
        this.f = new Matrix();
        this.g = new Path();
        this.h = new Path();
        this.i = new RectF();
        this.j = new RectF();
        this.k = new Region();
        this.l = new Region();
        Paint paint = new Paint(1);
        this.n = paint;
        Paint paint2 = new Paint(1);
        this.o = paint2;
        this.p = new C0287in();
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            c0379ln = AbstractC0348kn.a;
        } else {
            c0379ln = new C0379ln();
        }
        this.r = c0379ln;
        this.v = new RectF();
        this.w = true;
        this.a = wh;
        paint2.setStyle(Paint.Style.STROKE);
        paint.setStyle(Paint.Style.FILL);
        o();
        n(getState());
        this.q = new Ys(24, this);
    }
}
