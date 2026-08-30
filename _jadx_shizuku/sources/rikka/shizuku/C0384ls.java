package rikka.shizuku;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import java.io.IOException;
import java.util.ArrayDeque;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: rikka.shizuku.ls, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0384ls extends AbstractC0108cs {
    public static final PorterDuff.Mode j = PorterDuff.Mode.SRC_IN;
    public C0322js b;
    public PorterDuffColorFilter c;
    public ColorFilter d;
    public boolean e;
    public boolean f;
    public final float[] g;
    public final Matrix h;
    public final Rect i;

    public C0384ls() {
        this.f = true;
        this.g = new float[9];
        this.h = new Matrix();
        this.i = new Rect();
        C0322js c0322js = new C0322js();
        c0322js.c = null;
        c0322js.d = j;
        c0322js.b = new C0292is();
        this.b = c0322js;
    }

    public final PorterDuffColorFilter a(ColorStateList colorStateList, PorterDuff.Mode mode) {
        if (colorStateList == null || mode == null) {
            return null;
        }
        return new PorterDuffColorFilter(colorStateList.getColorForState(getState(), 0), mode);
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean canApplyTheme() {
        Drawable drawable = this.a;
        if (drawable == null) {
            return false;
        }
        drawable.canApplyTheme();
        return false;
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        Paint paint;
        Drawable drawable = this.a;
        if (drawable != null) {
            drawable.draw(canvas);
            return;
        }
        Rect rect = this.i;
        copyBounds(rect);
        if (rect.width() <= 0 || rect.height() <= 0) {
            return;
        }
        ColorFilter colorFilter = this.d;
        if (colorFilter == null) {
            colorFilter = this.c;
        }
        Matrix matrix = this.h;
        canvas.getMatrix(matrix);
        float[] fArr = this.g;
        matrix.getValues(fArr);
        float fAbs = Math.abs(fArr[0]);
        float fAbs2 = Math.abs(fArr[4]);
        float fAbs3 = Math.abs(fArr[1]);
        float fAbs4 = Math.abs(fArr[3]);
        if (fAbs3 != 0.0f || fAbs4 != 0.0f) {
            fAbs = 1.0f;
            fAbs2 = 1.0f;
        }
        int iWidth = (int) (rect.width() * fAbs);
        int iHeight = (int) (rect.height() * fAbs2);
        int iMin = Math.min(2048, iWidth);
        int iMin2 = Math.min(2048, iHeight);
        if (iMin <= 0 || iMin2 <= 0) {
            return;
        }
        int iSave = canvas.save();
        canvas.translate(rect.left, rect.top);
        if (isAutoMirrored() && getLayoutDirection() == 1) {
            canvas.translate(rect.width(), 0.0f);
            canvas.scale(-1.0f, 1.0f);
        }
        rect.offsetTo(0, 0);
        C0322js c0322js = this.b;
        Bitmap bitmap = c0322js.f;
        if (bitmap == null || iMin != bitmap.getWidth() || iMin2 != c0322js.f.getHeight()) {
            c0322js.f = Bitmap.createBitmap(iMin, iMin2, Bitmap.Config.ARGB_8888);
            c0322js.k = true;
        }
        if (this.f) {
            C0322js c0322js2 = this.b;
            if (c0322js2.k || c0322js2.g != c0322js2.c || c0322js2.h != c0322js2.d || c0322js2.j != c0322js2.e || c0322js2.i != c0322js2.b.getRootAlpha()) {
                C0322js c0322js3 = this.b;
                c0322js3.f.eraseColor(0);
                Canvas canvas2 = new Canvas(c0322js3.f);
                C0292is c0292is = c0322js3.b;
                c0292is.a(c0292is.g, C0292is.p, canvas2, iMin, iMin2);
                C0322js c0322js4 = this.b;
                c0322js4.g = c0322js4.c;
                c0322js4.h = c0322js4.d;
                c0322js4.i = c0322js4.b.getRootAlpha();
                c0322js4.j = c0322js4.e;
                c0322js4.k = false;
            }
        } else {
            C0322js c0322js5 = this.b;
            c0322js5.f.eraseColor(0);
            Canvas canvas3 = new Canvas(c0322js5.f);
            C0292is c0292is2 = c0322js5.b;
            c0292is2.a(c0292is2.g, C0292is.p, canvas3, iMin, iMin2);
        }
        C0322js c0322js6 = this.b;
        if (c0322js6.b.getRootAlpha() >= 255 && colorFilter == null) {
            paint = null;
        } else {
            if (c0322js6.l == null) {
                Paint paint2 = new Paint();
                c0322js6.l = paint2;
                paint2.setFilterBitmap(true);
            }
            c0322js6.l.setAlpha(c0322js6.b.getRootAlpha());
            c0322js6.l.setColorFilter(colorFilter);
            paint = c0322js6.l;
        }
        canvas.drawBitmap(c0322js6.f, (Rect) null, rect, paint);
        canvas.restoreToCount(iSave);
    }

    @Override // android.graphics.drawable.Drawable
    public final int getAlpha() {
        Drawable drawable = this.a;
        return drawable != null ? drawable.getAlpha() : this.b.b.getRootAlpha();
    }

    @Override // android.graphics.drawable.Drawable
    public final int getChangingConfigurations() {
        Drawable drawable = this.a;
        return drawable != null ? drawable.getChangingConfigurations() : super.getChangingConfigurations() | this.b.getChangingConfigurations();
    }

    @Override // android.graphics.drawable.Drawable
    public final ColorFilter getColorFilter() {
        Drawable drawable = this.a;
        return drawable != null ? drawable.getColorFilter() : this.d;
    }

    @Override // android.graphics.drawable.Drawable
    public final Drawable.ConstantState getConstantState() {
        if (this.a != null) {
            return new C0353ks(this.a.getConstantState());
        }
        this.b.a = getChangingConfigurations();
        return this.b;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicHeight() {
        Drawable drawable = this.a;
        return drawable != null ? drawable.getIntrinsicHeight() : (int) this.b.b.i;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicWidth() {
        Drawable drawable = this.a;
        return drawable != null ? drawable.getIntrinsicWidth() : (int) this.b.b.h;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        Drawable drawable = this.a;
        if (drawable != null) {
            return drawable.getOpacity();
        }
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public final void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet) throws XmlPullParserException, IOException {
        Drawable drawable = this.a;
        if (drawable != null) {
            drawable.inflate(resources, xmlPullParser, attributeSet);
        } else {
            inflate(resources, xmlPullParser, attributeSet, null);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void invalidateSelf() {
        Drawable drawable = this.a;
        if (drawable != null) {
            drawable.invalidateSelf();
        } else {
            super.invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean isAutoMirrored() {
        Drawable drawable = this.a;
        return drawable != null ? drawable.isAutoMirrored() : this.b.e;
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean isStateful() {
        Drawable drawable = this.a;
        if (drawable != null) {
            return drawable.isStateful();
        }
        if (super.isStateful()) {
            return true;
        }
        C0322js c0322js = this.b;
        if (c0322js == null) {
            return false;
        }
        C0292is c0292is = c0322js.b;
        if (c0292is.n == null) {
            c0292is.n = Boolean.valueOf(c0292is.g.a());
        }
        if (c0292is.n.booleanValue()) {
            return true;
        }
        ColorStateList colorStateList = this.b.c;
        return colorStateList != null && colorStateList.isStateful();
    }

    @Override // android.graphics.drawable.Drawable
    public final Drawable mutate() {
        Drawable drawable = this.a;
        if (drawable != null) {
            drawable.mutate();
            return this;
        }
        if (!this.e && super.mutate() == this) {
            C0322js c0322js = this.b;
            C0322js c0322js2 = new C0322js();
            c0322js2.c = null;
            c0322js2.d = j;
            if (c0322js != null) {
                c0322js2.a = c0322js.a;
                C0292is c0292is = new C0292is(c0322js.b);
                c0322js2.b = c0292is;
                if (c0322js.b.e != null) {
                    c0292is.e = new Paint(c0322js.b.e);
                }
                if (c0322js.b.d != null) {
                    c0322js2.b.d = new Paint(c0322js.b.d);
                }
                c0322js2.c = c0322js.c;
                c0322js2.d = c0322js.d;
                c0322js2.e = c0322js.e;
            }
            this.b = c0322js2;
            this.e = true;
        }
        return this;
    }

    @Override // android.graphics.drawable.Drawable
    public final void onBoundsChange(Rect rect) {
        Drawable drawable = this.a;
        if (drawable != null) {
            drawable.setBounds(rect);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean onStateChange(int[] iArr) {
        boolean z;
        PorterDuff.Mode mode;
        Drawable drawable = this.a;
        if (drawable != null) {
            return drawable.setState(iArr);
        }
        C0322js c0322js = this.b;
        ColorStateList colorStateList = c0322js.c;
        if (colorStateList == null || (mode = c0322js.d) == null) {
            z = false;
        } else {
            this.c = a(colorStateList, mode);
            invalidateSelf();
            z = true;
        }
        C0292is c0292is = c0322js.b;
        if (c0292is.n == null) {
            c0292is.n = Boolean.valueOf(c0292is.g.a());
        }
        if (c0292is.n.booleanValue()) {
            boolean zB = c0322js.b.g.b(iArr);
            c0322js.k |= zB;
            if (zB) {
                invalidateSelf();
                return true;
            }
        }
        return z;
    }

    @Override // android.graphics.drawable.Drawable
    public final void scheduleSelf(Runnable runnable, long j2) {
        Drawable drawable = this.a;
        if (drawable != null) {
            drawable.scheduleSelf(runnable, j2);
        } else {
            super.scheduleSelf(runnable, j2);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i) {
        Drawable drawable = this.a;
        if (drawable != null) {
            drawable.setAlpha(i);
        } else if (this.b.b.getRootAlpha() != i) {
            this.b.b.setRootAlpha(i);
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAutoMirrored(boolean z) {
        Drawable drawable = this.a;
        if (drawable != null) {
            drawable.setAutoMirrored(z);
        } else {
            this.b.e = z;
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
        Drawable drawable = this.a;
        if (drawable != null) {
            drawable.setColorFilter(colorFilter);
        } else {
            this.d = colorFilter;
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void setTint(int i) {
        Drawable drawable = this.a;
        if (drawable != null) {
            AbstractC0599sr.I(drawable, i);
        } else {
            setTintList(ColorStateList.valueOf(i));
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void setTintList(ColorStateList colorStateList) {
        Drawable drawable = this.a;
        if (drawable != null) {
            drawable.setTintList(colorStateList);
            return;
        }
        C0322js c0322js = this.b;
        if (c0322js.c != colorStateList) {
            c0322js.c = colorStateList;
            this.c = a(colorStateList, c0322js.d);
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void setTintMode(PorterDuff.Mode mode) {
        Drawable drawable = this.a;
        if (drawable != null) {
            drawable.setTintMode(mode);
            return;
        }
        C0322js c0322js = this.b;
        if (c0322js.d != mode) {
            c0322js.d = mode;
            this.c = a(c0322js.c, mode);
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean setVisible(boolean z, boolean z2) {
        Drawable drawable = this.a;
        return drawable != null ? drawable.setVisible(z, z2) : super.setVisible(z, z2);
    }

    @Override // android.graphics.drawable.Drawable
    public final void unscheduleSelf(Runnable runnable) {
        Drawable drawable = this.a;
        if (drawable != null) {
            drawable.unscheduleSelf(runnable);
        } else {
            super.unscheduleSelf(runnable);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        C0292is c0292is;
        int i;
        boolean z;
        char c;
        int i2;
        Paint.Cap cap;
        Paint.Join join;
        Drawable drawable = this.a;
        if (drawable != null) {
            drawable.inflate(resources, xmlPullParser, attributeSet, theme);
            return;
        }
        C0322js c0322js = this.b;
        c0322js.b = new C0292is();
        TypedArray typedArrayJ = Hn.j(resources, theme, attributeSet, X8.a);
        C0322js c0322js2 = this.b;
        C0292is c0292is2 = c0322js2.b;
        int i3 = !Hn.g(xmlPullParser, "tintMode") ? -1 : typedArrayJ.getInt(6, -1);
        PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
        if (i3 == 3) {
            mode = PorterDuff.Mode.SRC_OVER;
        } else if (i3 != 5) {
            if (i3 != 9) {
                switch (i3) {
                    case 14:
                        mode = PorterDuff.Mode.MULTIPLY;
                        break;
                    case 15:
                        mode = PorterDuff.Mode.SCREEN;
                        break;
                    case 16:
                        mode = PorterDuff.Mode.ADD;
                        break;
                }
            } else {
                mode = PorterDuff.Mode.SRC_ATOP;
            }
        }
        c0322js2.d = mode;
        int i4 = 1;
        ColorStateList colorStateListA = null;
        if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "tint") != null) {
            TypedValue typedValue = new TypedValue();
            typedArrayJ.getValue(1, typedValue);
            int i5 = typedValue.type;
            if (i5 == 2) {
                throw new UnsupportedOperationException("Failed to resolve attribute at index 1: " + typedValue);
            }
            if (i5 >= 28 && i5 <= 31) {
                colorStateListA = ColorStateList.valueOf(typedValue.data);
            } else {
                Resources resources2 = typedArrayJ.getResources();
                int resourceId = typedArrayJ.getResourceId(1, 0);
                ThreadLocal threadLocal = D6.a;
                try {
                    colorStateListA = D6.a(resources2, resources2.getXml(resourceId), theme);
                } catch (Exception e) {
                    Log.e("CSLCompat", "Failed to inflate ColorStateList.", e);
                }
            }
        }
        ColorStateList colorStateList = colorStateListA;
        if (colorStateList != null) {
            c0322js2.c = colorStateList;
        }
        boolean z2 = c0322js2.e;
        if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "autoMirrored") != null) {
            z2 = typedArrayJ.getBoolean(5, z2);
        }
        c0322js2.e = z2;
        float f = c0292is2.j;
        if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "viewportWidth") != null) {
            f = typedArrayJ.getFloat(7, f);
        }
        c0292is2.j = f;
        float f2 = c0292is2.k;
        if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "viewportHeight") != null) {
            f2 = typedArrayJ.getFloat(8, f2);
        }
        c0292is2.k = f2;
        boolean z3 = false;
        if (c0292is2.j <= 0.0f) {
            throw new XmlPullParserException(typedArrayJ.getPositionDescription() + "<vector> tag requires viewportWidth > 0");
        }
        if (f2 > 0.0f) {
            c0292is2.h = typedArrayJ.getDimension(3, c0292is2.h);
            float dimension = typedArrayJ.getDimension(2, c0292is2.i);
            c0292is2.i = dimension;
            if (c0292is2.h <= 0.0f) {
                throw new XmlPullParserException(typedArrayJ.getPositionDescription() + "<vector> tag requires width > 0");
            }
            if (dimension > 0.0f) {
                float alpha = c0292is2.getAlpha();
                if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "alpha") != null) {
                    alpha = typedArrayJ.getFloat(4, alpha);
                }
                c0292is2.setAlpha(alpha);
                String string = typedArrayJ.getString(0);
                if (string != null) {
                    c0292is2.m = string;
                    c0292is2.o.put(string, c0292is2);
                }
                typedArrayJ.recycle();
                c0322js.a = getChangingConfigurations();
                c0322js.k = true;
                C0322js c0322js3 = this.b;
                C0292is c0292is3 = c0322js3.b;
                ArrayDeque arrayDeque = new ArrayDeque();
                arrayDeque.push(c0292is3.g);
                int eventType = xmlPullParser.getEventType();
                int depth = xmlPullParser.getDepth() + 1;
                boolean z4 = true;
                while (eventType != i4 && (xmlPullParser.getDepth() >= depth || eventType != 3)) {
                    if (eventType == 2) {
                        String name = xmlPullParser.getName();
                        C0200fs c0200fs = (C0200fs) arrayDeque.peek();
                        boolean zEquals = "path".equals(name);
                        i = depth;
                        C0298j4 c0298j4 = c0292is3.o;
                        if (zEquals) {
                            C0169es c0169es = new C0169es();
                            c0169es.e = 0.0f;
                            c0169es.g = 1.0f;
                            c0169es.h = 1.0f;
                            c0292is = c0292is3;
                            c0169es.i = 0.0f;
                            c0169es.j = 1.0f;
                            c0169es.k = 0.0f;
                            Paint.Cap cap2 = Paint.Cap.BUTT;
                            c0169es.l = cap2;
                            Paint.Join join2 = Paint.Join.MITER;
                            c0169es.m = join2;
                            c0169es.n = 4.0f;
                            TypedArray typedArrayJ2 = Hn.j(resources, theme, attributeSet, X8.c);
                            if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "pathData") != null) {
                                String string2 = typedArrayJ2.getString(0);
                                if (string2 != null) {
                                    c0169es.b = string2;
                                }
                                String string3 = typedArrayJ2.getString(2);
                                if (string3 != null) {
                                    c0169es.a = AbstractC0046as.o(string3);
                                }
                                c0169es.f = Hn.d(typedArrayJ2, xmlPullParser, theme, "fillColor", 1);
                                float f3 = c0169es.h;
                                if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "fillAlpha") != null) {
                                    f3 = typedArrayJ2.getFloat(12, f3);
                                }
                                c0169es.h = f3;
                                int i6 = xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "strokeLineCap") != null ? typedArrayJ2.getInt(8, -1) : -1;
                                Paint.Cap cap3 = c0169es.l;
                                if (i6 == 0) {
                                    cap = cap2;
                                } else if (i6 != 1) {
                                    cap = i6 != 2 ? cap3 : Paint.Cap.SQUARE;
                                } else {
                                    cap = Paint.Cap.ROUND;
                                }
                                c0169es.l = cap;
                                int i7 = xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "strokeLineJoin") != null ? typedArrayJ2.getInt(9, -1) : -1;
                                Paint.Join join3 = c0169es.m;
                                if (i7 == 0) {
                                    join = join2;
                                } else if (i7 != 1) {
                                    join = i7 != 2 ? join3 : Paint.Join.BEVEL;
                                } else {
                                    join = Paint.Join.ROUND;
                                }
                                c0169es.m = join;
                                float f4 = c0169es.n;
                                if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "strokeMiterLimit") != null) {
                                    f4 = typedArrayJ2.getFloat(10, f4);
                                }
                                c0169es.n = f4;
                                c0169es.d = Hn.d(typedArrayJ2, xmlPullParser, theme, "strokeColor", 3);
                                float f5 = c0169es.g;
                                if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "strokeAlpha") != null) {
                                    f5 = typedArrayJ2.getFloat(11, f5);
                                }
                                c0169es.g = f5;
                                float f6 = c0169es.e;
                                if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "strokeWidth") != null) {
                                    f6 = typedArrayJ2.getFloat(4, f6);
                                }
                                c0169es.e = f6;
                                float f7 = c0169es.j;
                                if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "trimPathEnd") != null) {
                                    f7 = typedArrayJ2.getFloat(6, f7);
                                }
                                c0169es.j = f7;
                                float f8 = c0169es.k;
                                if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "trimPathOffset") != null) {
                                    f8 = typedArrayJ2.getFloat(7, f8);
                                }
                                c0169es.k = f8;
                                float f9 = c0169es.i;
                                if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "trimPathStart") != null) {
                                    f9 = typedArrayJ2.getFloat(5, f9);
                                }
                                c0169es.i = f9;
                                int i8 = c0169es.c;
                                if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "fillType") != null) {
                                    i8 = typedArrayJ2.getInt(13, i8);
                                }
                                c0169es.c = i8;
                            }
                            typedArrayJ2.recycle();
                            c0200fs.b.add(c0169es);
                            if (c0169es.getPathName() != null) {
                                c0298j4.put(c0169es.getPathName(), c0169es);
                            }
                            c0322js3.a = c0322js3.a;
                            z4 = false;
                            c = '\b';
                            z = false;
                        } else {
                            c0292is = c0292is3;
                            c = '\b';
                            z = false;
                            if ("clip-path".equals(name)) {
                                C0138ds c0138ds = new C0138ds();
                                if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "pathData") != null) {
                                    TypedArray typedArrayJ3 = Hn.j(resources, theme, attributeSet, X8.d);
                                    String string4 = typedArrayJ3.getString(0);
                                    if (string4 != null) {
                                        c0138ds.b = string4;
                                    }
                                    String string5 = typedArrayJ3.getString(1);
                                    if (string5 != null) {
                                        c0138ds.a = AbstractC0046as.o(string5);
                                    }
                                    c0138ds.c = !Hn.g(xmlPullParser, "fillType") ? 0 : typedArrayJ3.getInt(2, 0);
                                    typedArrayJ3.recycle();
                                }
                                c0200fs.b.add(c0138ds);
                                if (c0138ds.getPathName() != null) {
                                    c0298j4.put(c0138ds.getPathName(), c0138ds);
                                }
                                c0322js3.a = c0322js3.a;
                            } else if ("group".equals(name)) {
                                C0200fs c0200fs2 = new C0200fs();
                                TypedArray typedArrayJ4 = Hn.j(resources, theme, attributeSet, X8.b);
                                float f10 = c0200fs2.c;
                                if (Hn.g(xmlPullParser, "rotation")) {
                                    f10 = typedArrayJ4.getFloat(5, f10);
                                }
                                c0200fs2.c = f10;
                                c0200fs2.d = typedArrayJ4.getFloat(1, c0200fs2.d);
                                c0200fs2.e = typedArrayJ4.getFloat(2, c0200fs2.e);
                                float f11 = c0200fs2.f;
                                if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "scaleX") != null) {
                                    f11 = typedArrayJ4.getFloat(3, f11);
                                }
                                c0200fs2.f = f11;
                                float f12 = c0200fs2.g;
                                if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "scaleY") != null) {
                                    f12 = typedArrayJ4.getFloat(4, f12);
                                }
                                c0200fs2.g = f12;
                                float f13 = c0200fs2.h;
                                if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "translateX") != null) {
                                    f13 = typedArrayJ4.getFloat(6, f13);
                                }
                                c0200fs2.h = f13;
                                float f14 = c0200fs2.i;
                                if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "translateY") != null) {
                                    f14 = typedArrayJ4.getFloat(7, f14);
                                }
                                c0200fs2.i = f14;
                                String string6 = typedArrayJ4.getString(0);
                                if (string6 != null) {
                                    c0200fs2.k = string6;
                                }
                                c0200fs2.c();
                                typedArrayJ4.recycle();
                                c0200fs.b.add(c0200fs2);
                                arrayDeque.push(c0200fs2);
                                if (c0200fs2.getGroupName() != null) {
                                    c0298j4.put(c0200fs2.getGroupName(), c0200fs2);
                                }
                                c0322js3.a = c0322js3.a;
                            }
                        }
                        i2 = 1;
                    } else {
                        c0292is = c0292is3;
                        i = depth;
                        z = z3;
                        c = '\b';
                        i2 = 1;
                        if (eventType == 3 && "group".equals(xmlPullParser.getName())) {
                            arrayDeque.pop();
                        }
                    }
                    eventType = xmlPullParser.next();
                    i4 = i2;
                    z3 = z;
                    c0292is3 = c0292is;
                    depth = i;
                }
                if (!z4) {
                    this.c = a(c0322js.c, c0322js.d);
                    return;
                }
                throw new XmlPullParserException("no path defined");
            }
            throw new XmlPullParserException(typedArrayJ.getPositionDescription() + "<vector> tag requires height > 0");
        }
        throw new XmlPullParserException(typedArrayJ.getPositionDescription() + "<vector> tag requires viewportHeight > 0");
    }

    public C0384ls(C0322js c0322js) {
        this.f = true;
        this.g = new float[9];
        this.h = new Matrix();
        this.i = new Rect();
        this.b = c0322js;
        this.c = a(c0322js.c, c0322js.d);
    }
}
