package rikka.shizuku;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import java.util.ArrayList;

/* JADX INFO: renamed from: rikka.shizuku.is, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0292is {
    public static final Matrix p = new Matrix();
    public final Path a;
    public final Path b;
    public final Matrix c;
    public Paint d;
    public Paint e;
    public PathMeasure f;
    public final C0200fs g;
    public float h;
    public float i;
    public float j;
    public float k;
    public int l;
    public String m;
    public Boolean n;
    public final C0298j4 o;

    public C0292is() {
        this.c = new Matrix();
        this.h = 0.0f;
        this.i = 0.0f;
        this.j = 0.0f;
        this.k = 0.0f;
        this.l = 255;
        this.m = null;
        this.n = null;
        this.o = new C0298j4(0);
        this.g = new C0200fs();
        this.a = new Path();
        this.b = new Path();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void a(C0200fs c0200fs, Matrix matrix, Canvas canvas, int i, int i2) {
        char c;
        float f;
        int i3;
        C0200fs c0200fs2 = c0200fs;
        char c2 = 1;
        c0200fs2.a.set(matrix);
        Matrix matrix2 = c0200fs2.a;
        matrix2.preConcat(c0200fs2.j);
        canvas.save();
        char c3 = 0;
        int i4 = 0;
        while (true) {
            ArrayList arrayList = c0200fs2.b;
            if (i4 >= arrayList.size()) {
                canvas.restore();
                return;
            }
            AbstractC0231gs abstractC0231gs = (AbstractC0231gs) arrayList.get(i4);
            if (abstractC0231gs instanceof C0200fs) {
                a((C0200fs) abstractC0231gs, matrix2, canvas, i, i2);
            } else {
                if (abstractC0231gs instanceof AbstractC0262hs) {
                    AbstractC0262hs abstractC0262hs = (AbstractC0262hs) abstractC0231gs;
                    float f2 = i / this.j;
                    float f3 = i2 / this.k;
                    float fMin = Math.min(f2, f3);
                    Matrix matrix3 = this.c;
                    matrix3.set(matrix2);
                    matrix3.postScale(f2, f3);
                    float[] fArr = {0.0f, 1.0f, 1.0f, 0.0f};
                    matrix2.mapVectors(fArr);
                    float fHypot = (float) Math.hypot(fArr[c3], fArr[c2]);
                    boolean z = c2;
                    boolean z2 = c3;
                    float fHypot2 = (float) Math.hypot(fArr[2], fArr[3]);
                    float f4 = (fArr[z2 ? 1 : 0] * fArr[3]) - (fArr[z ? 1 : 0] * fArr[2]);
                    float fMax = Math.max(fHypot, fHypot2);
                    float fAbs = fMax > 0.0f ? Math.abs(f4) / fMax : 0.0f;
                    if (fAbs != 0.0f) {
                        abstractC0262hs.getClass();
                        Path path = this.a;
                        path.reset();
                        Pj[] pjArr = abstractC0262hs.a;
                        if (pjArr != null) {
                            Pj.b(pjArr, path);
                        }
                        Path path2 = this.b;
                        path2.reset();
                        if (abstractC0262hs instanceof C0138ds) {
                            path2.setFillType(abstractC0262hs.c == 0 ? Path.FillType.WINDING : Path.FillType.EVEN_ODD);
                            path2.addPath(path, matrix3);
                            canvas.clipPath(path2);
                        } else {
                            C0169es c0169es = (C0169es) abstractC0262hs;
                            float f5 = c0169es.i;
                            if (f5 != 0.0f || c0169es.j != 1.0f) {
                                float f6 = c0169es.k;
                                float f7 = (f5 + f6) % 1.0f;
                                float f8 = (c0169es.j + f6) % 1.0f;
                                if (this.f == null) {
                                    this.f = new PathMeasure();
                                }
                                this.f.setPath(path, z2);
                                float length = this.f.getLength();
                                float f9 = f7 * length;
                                float f10 = f8 * length;
                                path.reset();
                                if (f9 > f10) {
                                    this.f.getSegment(f9, length, path, z);
                                    f = 0.0f;
                                    this.f.getSegment(0.0f, f10, path, z);
                                } else {
                                    f = 0.0f;
                                    this.f.getSegment(f9, f10, path, z);
                                }
                                path.rLineTo(f, f);
                            }
                            path2.addPath(path, matrix3);
                            C0788z c0788z = c0169es.f;
                            float f11 = 255.0f;
                            if ((((Shader) c0788z.b) == null && c0788z.a == 0) ? false : true) {
                                if (this.e == null) {
                                    i3 = 16777215;
                                    Paint paint = new Paint(1);
                                    this.e = paint;
                                    paint.setStyle(Paint.Style.FILL);
                                } else {
                                    i3 = 16777215;
                                }
                                Paint paint2 = this.e;
                                Shader shader = (Shader) c0788z.b;
                                if (shader != null) {
                                    shader.setLocalMatrix(matrix3);
                                    paint2.setShader(shader);
                                    paint2.setAlpha(Math.round(c0169es.h * 255.0f));
                                } else {
                                    paint2.setShader(null);
                                    paint2.setAlpha(255);
                                    int i5 = c0788z.a;
                                    float f12 = c0169es.h;
                                    PorterDuff.Mode mode = C0384ls.j;
                                    paint2.setColor((i5 & i3) | (((int) (Color.alpha(i5) * f12)) << 24));
                                }
                                paint2.setColorFilter(null);
                                path2.setFillType(c0169es.c == 0 ? Path.FillType.WINDING : Path.FillType.EVEN_ODD);
                                canvas.drawPath(path2, paint2);
                            } else {
                                f11 = 255.0f;
                                i3 = 16777215;
                            }
                            C0788z c0788z2 = c0169es.d;
                            if (((Shader) c0788z2.b) != null || c0788z2.a != 0) {
                                if (this.d == null) {
                                    Paint paint3 = new Paint(1);
                                    this.d = paint3;
                                    paint3.setStyle(Paint.Style.STROKE);
                                }
                                Paint paint4 = this.d;
                                Paint.Join join = c0169es.m;
                                if (join != null) {
                                    paint4.setStrokeJoin(join);
                                }
                                Paint.Cap cap = c0169es.l;
                                if (cap != null) {
                                    paint4.setStrokeCap(cap);
                                }
                                paint4.setStrokeMiter(c0169es.n);
                                Shader shader2 = (Shader) c0788z2.b;
                                if (shader2 != null) {
                                    shader2.setLocalMatrix(matrix3);
                                    paint4.setShader(shader2);
                                    paint4.setAlpha(Math.round(c0169es.g * f11));
                                } else {
                                    paint4.setShader(null);
                                    paint4.setAlpha(255);
                                    int i6 = c0788z2.a;
                                    float f13 = c0169es.g;
                                    PorterDuff.Mode mode2 = C0384ls.j;
                                    paint4.setColor((i6 & i3) | (((int) (Color.alpha(i6) * f13)) << 24));
                                }
                                paint4.setColorFilter(null);
                                paint4.setStrokeWidth(c0169es.e * fMin * fAbs);
                                canvas.drawPath(path2, paint4);
                            }
                        }
                    }
                    c = 1;
                }
                i4++;
                c0200fs2 = c0200fs;
                c2 = c;
                c3 = 0;
            }
            c = c2;
            i4++;
            c0200fs2 = c0200fs;
            c2 = c;
            c3 = 0;
        }
    }

    public float getAlpha() {
        return getRootAlpha() / 255.0f;
    }

    public int getRootAlpha() {
        return this.l;
    }

    public void setAlpha(float f) {
        setRootAlpha((int) (f * 255.0f));
    }

    public void setRootAlpha(int i) {
        this.l = i;
    }

    public C0292is(C0292is c0292is) {
        this.c = new Matrix();
        this.h = 0.0f;
        this.i = 0.0f;
        this.j = 0.0f;
        this.k = 0.0f;
        this.l = 255;
        this.m = null;
        this.n = null;
        C0298j4 c0298j4 = new C0298j4(0);
        this.o = c0298j4;
        this.g = new C0200fs(c0292is.g, c0298j4);
        this.a = new Path(c0292is.a);
        this.b = new Path(c0292is.b);
        this.h = c0292is.h;
        this.i = c0292is.i;
        this.j = c0292is.j;
        this.k = c0292is.k;
        this.l = c0292is.l;
        this.m = c0292is.m;
        String str = c0292is.m;
        if (str != null) {
            c0298j4.put(str, this);
        }
        this.n = c0292is.n;
    }
}
