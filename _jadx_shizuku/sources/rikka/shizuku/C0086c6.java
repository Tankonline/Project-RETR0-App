package rikka.shizuku;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import com.google.android.material.chip.Chip;
import java.lang.ref.WeakReference;
import java.util.Arrays;

/* JADX INFO: renamed from: rikka.shizuku.c6, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0086c6 extends Xh implements Drawable.Callback, Ep {
    public static final int[] D0 = {R.attr.state_enabled};
    public static final ShapeDrawable E0 = new ShapeDrawable(new OvalShape());
    public float A;
    public boolean A0;
    public float B;
    public int B0;
    public ColorStateList C;
    public boolean C0;
    public float D;
    public ColorStateList E;
    public CharSequence F;
    public boolean G;
    public Drawable H;
    public ColorStateList I;
    public float J;
    public boolean K;
    public boolean L;
    public Drawable M;
    public RippleDrawable N;
    public ColorStateList O;
    public float P;
    public boolean Q;
    public boolean R;
    public Drawable S;
    public ColorStateList T;
    public float U;
    public float V;
    public float W;
    public float X;
    public float Y;
    public float Z;
    public float a0;
    public float b0;
    public final Context c0;
    public final Paint d0;
    public final Paint.FontMetrics e0;
    public final RectF f0;
    public final PointF g0;
    public final Path h0;
    public final Fp i0;
    public int j0;
    public int k0;
    public int l0;
    public int m0;
    public int n0;
    public int o0;
    public boolean p0;
    public int q0;
    public int r0;
    public ColorFilter s0;
    public PorterDuffColorFilter t0;
    public ColorStateList u0;
    public PorterDuff.Mode v0;
    public int[] w0;
    public ColorStateList x0;
    public ColorStateList y;
    public WeakReference y0;
    public ColorStateList z;
    public TextUtils.TruncateAt z0;

    public C0086c6(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f0400d1, moe.shizuku.privileged.api.R.style._0_resource_name_obfuscated_res_0x7f120473);
        this.B = -1.0f;
        this.d0 = new Paint(1);
        this.e0 = new Paint.FontMetrics();
        this.f0 = new RectF();
        this.g0 = new PointF();
        this.h0 = new Path();
        this.r0 = 255;
        this.v0 = PorterDuff.Mode.SRC_IN;
        this.y0 = new WeakReference(null);
        j(context);
        this.c0 = context;
        Fp fp = new Fp(this);
        this.i0 = fp;
        this.F = "";
        fp.a.density = context.getResources().getDisplayMetrics().density;
        int[] iArr = D0;
        setState(iArr);
        if (!Arrays.equals(this.w0, iArr)) {
            this.w0 = iArr;
            if (F()) {
                y(getState(), iArr);
            }
        }
        this.A0 = true;
        int[] iArr2 = AbstractC0133dm.a;
        E0.setTint(-1);
    }

    public static void G(Drawable drawable) {
        if (drawable != null) {
            drawable.setCallback(null);
        }
    }

    public static boolean v(ColorStateList colorStateList) {
        return colorStateList != null && colorStateList.isStateful();
    }

    public static boolean w(Drawable drawable) {
        return drawable != null && drawable.isStateful();
    }

    public final void A(boolean z) {
        if (this.G != z) {
            boolean zE = E();
            this.G = z;
            boolean zE2 = E();
            if (zE != zE2) {
                if (zE2) {
                    q(this.H);
                } else {
                    G(this.H);
                }
                invalidateSelf();
                x();
            }
        }
    }

    public final void B(boolean z) {
        if (this.L != z) {
            boolean zF = F();
            this.L = z;
            boolean zF2 = F();
            if (zF != zF2) {
                if (zF2) {
                    q(this.M);
                } else {
                    G(this.M);
                }
                invalidateSelf();
                x();
            }
        }
    }

    public final void C(Cp cp) {
        Fp fp = this.i0;
        if (fp.f != cp) {
            fp.f = cp;
            TextPaint textPaint = fp.a;
            Context context = this.c0;
            Y5 y5 = fp.b;
            cp.f(context, textPaint, y5);
            Ep ep = (Ep) fp.e.get();
            if (ep != null) {
                textPaint.drawableState = ep.getState();
            }
            cp.e(context, textPaint, y5);
            fp.d = true;
            Ep ep2 = (Ep) fp.e.get();
            if (ep2 != null) {
                C0086c6 c0086c6 = (C0086c6) ep2;
                c0086c6.x();
                c0086c6.invalidateSelf();
                c0086c6.onStateChange(ep2.getState());
            }
        }
    }

    public final boolean D() {
        return this.R && this.S != null && this.p0;
    }

    public final boolean E() {
        return this.G && this.H != null;
    }

    public final boolean F() {
        return this.L && this.M != null;
    }

    @Override // rikka.shizuku.Xh, android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        int i;
        Canvas canvas2;
        int iSaveLayerAlpha;
        int i2;
        Rect bounds = getBounds();
        if (bounds.isEmpty() || (i = this.r0) == 0) {
            return;
        }
        if (i < 255) {
            canvas2 = canvas;
            iSaveLayerAlpha = canvas2.saveLayerAlpha(bounds.left, bounds.top, bounds.right, bounds.bottom, i);
        } else {
            canvas2 = canvas;
            iSaveLayerAlpha = 0;
        }
        boolean z = this.C0;
        Paint paint = this.d0;
        RectF rectF = this.f0;
        if (!z) {
            paint.setColor(this.j0);
            paint.setStyle(Paint.Style.FILL);
            rectF.set(bounds);
            canvas2.drawRoundRect(rectF, u(), u(), paint);
        }
        if (!this.C0) {
            paint.setColor(this.k0);
            paint.setStyle(Paint.Style.FILL);
            ColorFilter colorFilter = this.s0;
            if (colorFilter == null) {
                colorFilter = this.t0;
            }
            paint.setColorFilter(colorFilter);
            rectF.set(bounds);
            canvas2.drawRoundRect(rectF, u(), u(), paint);
        }
        if (this.C0) {
            super.draw(canvas);
        }
        if (this.D > 0.0f && !this.C0) {
            paint.setColor(this.m0);
            paint.setStyle(Paint.Style.STROKE);
            if (!this.C0) {
                ColorFilter colorFilter2 = this.s0;
                if (colorFilter2 == null) {
                    colorFilter2 = this.t0;
                }
                paint.setColorFilter(colorFilter2);
            }
            float f = bounds.left;
            float f2 = this.D / 2.0f;
            rectF.set(f + f2, bounds.top + f2, bounds.right - f2, bounds.bottom - f2);
            float f3 = this.B - (this.D / 2.0f);
            canvas2.drawRoundRect(rectF, f3, f3, paint);
        }
        paint.setColor(this.n0);
        paint.setStyle(Paint.Style.FILL);
        rectF.set(bounds);
        if (this.C0) {
            RectF rectF2 = new RectF(bounds);
            Path path = this.h0;
            Wh wh = this.a;
            this.r.a(wh.a, wh.i, rectF2, this.q, path);
            e(canvas2, paint, path, this.a.a, g());
        } else {
            canvas2.drawRoundRect(rectF, u(), u(), paint);
        }
        if (E()) {
            r(bounds, rectF);
            float f4 = rectF.left;
            float f5 = rectF.top;
            canvas2.translate(f4, f5);
            this.H.setBounds(0, 0, (int) rectF.width(), (int) rectF.height());
            this.H.draw(canvas2);
            canvas2.translate(-f4, -f5);
        }
        if (D()) {
            r(bounds, rectF);
            float f6 = rectF.left;
            float f7 = rectF.top;
            canvas2.translate(f6, f7);
            this.S.setBounds(0, 0, (int) rectF.width(), (int) rectF.height());
            this.S.draw(canvas2);
            canvas2.translate(-f6, -f7);
        }
        if (this.A0 && this.F != null) {
            PointF pointF = this.g0;
            pointF.set(0.0f, 0.0f);
            Paint.Align align = Paint.Align.LEFT;
            CharSequence charSequence = this.F;
            Fp fp = this.i0;
            if (charSequence != null) {
                float fS = s() + this.U + this.X;
                if (getLayoutDirection() == 0) {
                    pointF.x = bounds.left + fS;
                } else {
                    pointF.x = bounds.right - fS;
                    align = Paint.Align.RIGHT;
                }
                float fCenterY = bounds.centerY();
                TextPaint textPaint = fp.a;
                Paint.FontMetrics fontMetrics = this.e0;
                textPaint.getFontMetrics(fontMetrics);
                pointF.y = fCenterY - ((fontMetrics.descent + fontMetrics.ascent) / 2.0f);
            }
            rectF.setEmpty();
            if (this.F != null) {
                float fS2 = s() + this.U + this.X;
                float fT = t() + this.b0 + this.Y;
                if (getLayoutDirection() == 0) {
                    rectF.left = bounds.left + fS2;
                    rectF.right = bounds.right - fT;
                } else {
                    rectF.left = bounds.left + fT;
                    rectF.right = bounds.right - fS2;
                }
                rectF.top = bounds.top;
                rectF.bottom = bounds.bottom;
            }
            Cp cp = fp.f;
            TextPaint textPaint2 = fp.a;
            if (cp != null) {
                textPaint2.drawableState = getState();
                fp.f.e(this.c0, textPaint2, fp.b);
            }
            textPaint2.setTextAlign(align);
            boolean z2 = Math.round(fp.a(this.F.toString())) > Math.round(rectF.width());
            if (z2) {
                int iSave = canvas2.save();
                canvas2.clipRect(rectF);
                i2 = iSave;
            } else {
                i2 = 0;
            }
            CharSequence charSequenceEllipsize = this.F;
            if (z2 && this.z0 != null) {
                charSequenceEllipsize = TextUtils.ellipsize(charSequenceEllipsize, textPaint2, rectF.width(), this.z0);
            }
            canvas.drawText(charSequenceEllipsize, 0, charSequenceEllipsize.length(), pointF.x, pointF.y, textPaint2);
            canvas2 = canvas;
            if (z2) {
                canvas2.restoreToCount(i2);
            }
        }
        if (F()) {
            rectF.setEmpty();
            if (F()) {
                float f8 = this.b0 + this.a0;
                if (getLayoutDirection() == 0) {
                    float f9 = bounds.right - f8;
                    rectF.right = f9;
                    rectF.left = f9 - this.P;
                } else {
                    float f10 = bounds.left + f8;
                    rectF.left = f10;
                    rectF.right = f10 + this.P;
                }
                float fExactCenterY = bounds.exactCenterY();
                float f11 = this.P;
                float f12 = fExactCenterY - (f11 / 2.0f);
                rectF.top = f12;
                rectF.bottom = f12 + f11;
            }
            float f13 = rectF.left;
            float f14 = rectF.top;
            canvas2.translate(f13, f14);
            this.M.setBounds(0, 0, (int) rectF.width(), (int) rectF.height());
            int[] iArr = AbstractC0133dm.a;
            this.N.setBounds(this.M.getBounds());
            this.N.jumpToCurrentState();
            this.N.draw(canvas2);
            canvas2.translate(-f13, -f14);
        }
        if (this.r0 < 255) {
            canvas2.restoreToCount(iSaveLayerAlpha);
        }
    }

    @Override // rikka.shizuku.Xh, android.graphics.drawable.Drawable
    public final int getAlpha() {
        return this.r0;
    }

    @Override // android.graphics.drawable.Drawable
    public final ColorFilter getColorFilter() {
        return this.s0;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicHeight() {
        return (int) this.A;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicWidth() {
        return Math.min(Math.round(t() + this.i0.a(this.F.toString()) + s() + this.U + this.X + this.Y + this.b0), this.B0);
    }

    @Override // rikka.shizuku.Xh, android.graphics.drawable.Drawable
    public final int getOpacity() {
        return -3;
    }

    @Override // rikka.shizuku.Xh, android.graphics.drawable.Drawable
    public final void getOutline(Outline outline) {
        Outline outline2;
        if (this.C0) {
            super.getOutline(outline);
            return;
        }
        Rect bounds = getBounds();
        if (bounds.isEmpty()) {
            outline2 = outline;
            outline2.setRoundRect(0, 0, getIntrinsicWidth(), (int) this.A, this.B);
        } else {
            outline.setRoundRect(bounds, this.B);
            outline2 = outline;
        }
        outline2.setAlpha(this.r0 / 255.0f);
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public final void invalidateDrawable(Drawable drawable) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.invalidateDrawable(this);
        }
    }

    @Override // rikka.shizuku.Xh, android.graphics.drawable.Drawable
    public final boolean isStateful() {
        ColorStateList colorStateList;
        if (v(this.y) || v(this.z) || v(this.C)) {
            return true;
        }
        Cp cp = this.i0.f;
        if (cp == null || (colorStateList = cp.j) == null || !colorStateList.isStateful()) {
            return (this.R && this.S != null && this.Q) || w(this.H) || w(this.S) || v(this.u0);
        }
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean onLayoutDirectionChanged(int i) {
        boolean zOnLayoutDirectionChanged = super.onLayoutDirectionChanged(i);
        if (E()) {
            zOnLayoutDirectionChanged |= this.H.setLayoutDirection(i);
        }
        if (D()) {
            zOnLayoutDirectionChanged |= this.S.setLayoutDirection(i);
        }
        if (F()) {
            zOnLayoutDirectionChanged |= this.M.setLayoutDirection(i);
        }
        if (!zOnLayoutDirectionChanged) {
            return true;
        }
        invalidateSelf();
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean onLevelChange(int i) {
        boolean zOnLevelChange = super.onLevelChange(i);
        if (E()) {
            zOnLevelChange |= this.H.setLevel(i);
        }
        if (D()) {
            zOnLevelChange |= this.S.setLevel(i);
        }
        if (F()) {
            zOnLevelChange |= this.M.setLevel(i);
        }
        if (zOnLevelChange) {
            invalidateSelf();
        }
        return zOnLevelChange;
    }

    @Override // rikka.shizuku.Xh, android.graphics.drawable.Drawable
    public final boolean onStateChange(int[] iArr) {
        if (this.C0) {
            super.onStateChange(iArr);
        }
        return y(iArr, this.w0);
    }

    public final void q(Drawable drawable) {
        if (drawable == null) {
            return;
        }
        drawable.setCallback(this);
        drawable.setLayoutDirection(getLayoutDirection());
        drawable.setLevel(getLevel());
        drawable.setVisible(isVisible(), false);
        if (drawable == this.M) {
            if (drawable.isStateful()) {
                drawable.setState(this.w0);
            }
            drawable.setTintList(this.O);
            return;
        }
        Drawable drawable2 = this.H;
        if (drawable == drawable2 && this.K) {
            drawable2.setTintList(this.I);
        }
        if (drawable.isStateful()) {
            drawable.setState(getState());
        }
    }

    public final void r(Rect rect, RectF rectF) {
        rectF.setEmpty();
        if (E() || D()) {
            float f = this.U + this.V;
            Drawable drawable = this.p0 ? this.S : this.H;
            float intrinsicWidth = this.J;
            if (intrinsicWidth <= 0.0f && drawable != null) {
                intrinsicWidth = drawable.getIntrinsicWidth();
            }
            if (getLayoutDirection() == 0) {
                float f2 = rect.left + f;
                rectF.left = f2;
                rectF.right = f2 + intrinsicWidth;
            } else {
                float f3 = rect.right - f;
                rectF.right = f3;
                rectF.left = f3 - intrinsicWidth;
            }
            Drawable drawable2 = this.p0 ? this.S : this.H;
            float fCeil = this.J;
            if (fCeil <= 0.0f && drawable2 != null) {
                fCeil = (float) Math.ceil(TypedValue.applyDimension(1, 24, this.c0.getResources().getDisplayMetrics()));
                if (drawable2.getIntrinsicHeight() <= fCeil) {
                    fCeil = drawable2.getIntrinsicHeight();
                }
            }
            float fExactCenterY = rect.exactCenterY() - (fCeil / 2.0f);
            rectF.top = fExactCenterY;
            rectF.bottom = fExactCenterY + fCeil;
        }
    }

    public final float s() {
        if (!E() && !D()) {
            return 0.0f;
        }
        float f = this.V;
        Drawable drawable = this.p0 ? this.S : this.H;
        float intrinsicWidth = this.J;
        if (intrinsicWidth <= 0.0f && drawable != null) {
            intrinsicWidth = drawable.getIntrinsicWidth();
        }
        return intrinsicWidth + f + this.W;
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public final void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.scheduleDrawable(this, runnable, j);
        }
    }

    @Override // rikka.shizuku.Xh, android.graphics.drawable.Drawable
    public final void setAlpha(int i) {
        if (this.r0 != i) {
            this.r0 = i;
            invalidateSelf();
        }
    }

    @Override // rikka.shizuku.Xh, android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
        if (this.s0 != colorFilter) {
            this.s0 = colorFilter;
            invalidateSelf();
        }
    }

    @Override // rikka.shizuku.Xh, android.graphics.drawable.Drawable
    public final void setTintList(ColorStateList colorStateList) {
        if (this.u0 != colorStateList) {
            this.u0 = colorStateList;
            onStateChange(getState());
        }
    }

    @Override // rikka.shizuku.Xh, android.graphics.drawable.Drawable
    public final void setTintMode(PorterDuff.Mode mode) {
        if (this.v0 != mode) {
            this.v0 = mode;
            ColorStateList colorStateList = this.u0;
            this.t0 = (colorStateList == null || mode == null) ? null : new PorterDuffColorFilter(colorStateList.getColorForState(getState(), 0), mode);
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean setVisible(boolean z, boolean z2) {
        boolean visible = super.setVisible(z, z2);
        if (E()) {
            visible |= this.H.setVisible(z, z2);
        }
        if (D()) {
            visible |= this.S.setVisible(z, z2);
        }
        if (F()) {
            visible |= this.M.setVisible(z, z2);
        }
        if (visible) {
            invalidateSelf();
        }
        return visible;
    }

    public final float t() {
        if (F()) {
            return this.Z + this.P + this.a0;
        }
        return 0.0f;
    }

    public final float u() {
        return this.C0 ? h() : this.B;
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public final void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.unscheduleDrawable(this, runnable);
        }
    }

    public final void x() {
        InterfaceC0055b6 interfaceC0055b6 = (InterfaceC0055b6) this.y0.get();
        if (interfaceC0055b6 != null) {
            Chip chip = (Chip) interfaceC0055b6;
            chip.b(chip.o);
            chip.requestLayout();
            chip.invalidateOutline();
        }
    }

    public final boolean y(int[] iArr, int[] iArr2) {
        boolean z;
        boolean z2;
        ColorStateList colorStateList;
        boolean state = true;
        boolean zOnStateChange = super.onStateChange(iArr);
        ColorStateList colorStateList2 = this.y;
        int iC = c(colorStateList2 != null ? colorStateList2.getColorForState(iArr, this.j0) : 0);
        if (this.j0 != iC) {
            this.j0 = iC;
            zOnStateChange = true;
        }
        ColorStateList colorStateList3 = this.z;
        int iC2 = c(colorStateList3 != null ? colorStateList3.getColorForState(iArr, this.k0) : 0);
        if (this.k0 != iC2) {
            this.k0 = iC2;
            zOnStateChange = true;
        }
        int iC3 = E6.c(iC2, iC);
        if ((this.l0 != iC3) | (this.a.c == null)) {
            this.l0 = iC3;
            l(ColorStateList.valueOf(iC3));
            zOnStateChange = true;
        }
        ColorStateList colorStateList4 = this.C;
        int colorForState = colorStateList4 != null ? colorStateList4.getColorForState(iArr, this.m0) : 0;
        if (this.m0 != colorForState) {
            this.m0 = colorForState;
            zOnStateChange = true;
        }
        int colorForState2 = (this.x0 == null || !AbstractC0133dm.b(iArr)) ? 0 : this.x0.getColorForState(iArr, this.n0);
        if (this.n0 != colorForState2) {
            this.n0 = colorForState2;
        }
        Cp cp = this.i0.f;
        int colorForState3 = (cp == null || (colorStateList = cp.j) == null) ? 0 : colorStateList.getColorForState(iArr, this.o0);
        if (this.o0 != colorForState3) {
            this.o0 = colorForState3;
            zOnStateChange = true;
        }
        int[] state2 = getState();
        if (state2 != null) {
            int length = state2.length;
            int i = 0;
            while (true) {
                if (i < length) {
                    if (state2[i] != 16842912) {
                        i++;
                    } else if (this.Q) {
                        z = true;
                        break;
                    }
                }
                z = false;
                break;
            }
        } else {
            z = false;
            break;
        }
        if (this.p0 == z || this.S == null) {
            z2 = false;
        } else {
            float fS = s();
            this.p0 = z;
            if (fS != s()) {
                zOnStateChange = true;
                z2 = true;
            } else {
                zOnStateChange = true;
                z2 = false;
            }
        }
        ColorStateList colorStateList5 = this.u0;
        int colorForState4 = colorStateList5 != null ? colorStateList5.getColorForState(iArr, this.q0) : 0;
        if (this.q0 != colorForState4) {
            this.q0 = colorForState4;
            ColorStateList colorStateList6 = this.u0;
            PorterDuff.Mode mode = this.v0;
            this.t0 = (colorStateList6 == null || mode == null) ? null : new PorterDuffColorFilter(colorStateList6.getColorForState(getState(), 0), mode);
        } else {
            state = zOnStateChange;
        }
        if (w(this.H)) {
            state |= this.H.setState(iArr);
        }
        if (w(this.S)) {
            state |= this.S.setState(iArr);
        }
        if (w(this.M)) {
            int[] iArr3 = new int[iArr.length + iArr2.length];
            System.arraycopy(iArr, 0, iArr3, 0, iArr.length);
            System.arraycopy(iArr2, 0, iArr3, iArr.length, iArr2.length);
            state |= this.M.setState(iArr3);
        }
        int[] iArr4 = AbstractC0133dm.a;
        if (w(this.N)) {
            state |= this.N.setState(iArr2);
        }
        if (state) {
            invalidateSelf();
        }
        if (z2) {
            x();
        }
        return state;
    }

    public final void z(boolean z) {
        if (this.R != z) {
            boolean zD = D();
            this.R = z;
            boolean zD2 = D();
            if (zD != zD2) {
                if (zD2) {
                    q(this.S);
                } else {
                    G(this.S);
                }
                invalidateSelf();
                x();
            }
        }
    }
}
