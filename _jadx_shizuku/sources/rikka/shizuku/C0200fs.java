package rikka.shizuku;

import android.graphics.Matrix;
import android.graphics.Paint;
import java.util.ArrayList;

/* JADX INFO: renamed from: rikka.shizuku.fs, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0200fs extends AbstractC0231gs {
    public final Matrix a;
    public final ArrayList b;
    public float c;
    public float d;
    public float e;
    public float f;
    public float g;
    public float h;
    public float i;
    public final Matrix j;
    public String k;

    public C0200fs() {
        this.a = new Matrix();
        this.b = new ArrayList();
        this.c = 0.0f;
        this.d = 0.0f;
        this.e = 0.0f;
        this.f = 1.0f;
        this.g = 1.0f;
        this.h = 0.0f;
        this.i = 0.0f;
        this.j = new Matrix();
        this.k = null;
    }

    @Override // rikka.shizuku.AbstractC0231gs
    public final boolean a() {
        int i = 0;
        while (true) {
            ArrayList arrayList = this.b;
            if (i >= arrayList.size()) {
                return false;
            }
            if (((AbstractC0231gs) arrayList.get(i)).a()) {
                return true;
            }
            i++;
        }
    }

    @Override // rikka.shizuku.AbstractC0231gs
    public final boolean b(int[] iArr) {
        int i = 0;
        boolean zB = false;
        while (true) {
            ArrayList arrayList = this.b;
            if (i >= arrayList.size()) {
                return zB;
            }
            zB |= ((AbstractC0231gs) arrayList.get(i)).b(iArr);
            i++;
        }
    }

    public final void c() {
        Matrix matrix = this.j;
        matrix.reset();
        matrix.postTranslate(-this.d, -this.e);
        matrix.postScale(this.f, this.g);
        matrix.postRotate(this.c, 0.0f, 0.0f);
        matrix.postTranslate(this.h + this.d, this.i + this.e);
    }

    public String getGroupName() {
        return this.k;
    }

    public Matrix getLocalMatrix() {
        return this.j;
    }

    public float getPivotX() {
        return this.d;
    }

    public float getPivotY() {
        return this.e;
    }

    public float getRotation() {
        return this.c;
    }

    public float getScaleX() {
        return this.f;
    }

    public float getScaleY() {
        return this.g;
    }

    public float getTranslateX() {
        return this.h;
    }

    public float getTranslateY() {
        return this.i;
    }

    public void setPivotX(float f) {
        if (f != this.d) {
            this.d = f;
            c();
        }
    }

    public void setPivotY(float f) {
        if (f != this.e) {
            this.e = f;
            c();
        }
    }

    public void setRotation(float f) {
        if (f != this.c) {
            this.c = f;
            c();
        }
    }

    public void setScaleX(float f) {
        if (f != this.f) {
            this.f = f;
            c();
        }
    }

    public void setScaleY(float f) {
        if (f != this.g) {
            this.g = f;
            c();
        }
    }

    public void setTranslateX(float f) {
        if (f != this.h) {
            this.h = f;
            c();
        }
    }

    public void setTranslateY(float f) {
        if (f != this.i) {
            this.i = f;
            c();
        }
    }

    public C0200fs(C0200fs c0200fs, C0298j4 c0298j4) {
        AbstractC0262hs c0138ds;
        this.a = new Matrix();
        this.b = new ArrayList();
        this.c = 0.0f;
        this.d = 0.0f;
        this.e = 0.0f;
        this.f = 1.0f;
        this.g = 1.0f;
        this.h = 0.0f;
        this.i = 0.0f;
        Matrix matrix = new Matrix();
        this.j = matrix;
        this.k = null;
        this.c = c0200fs.c;
        this.d = c0200fs.d;
        this.e = c0200fs.e;
        this.f = c0200fs.f;
        this.g = c0200fs.g;
        this.h = c0200fs.h;
        this.i = c0200fs.i;
        String str = c0200fs.k;
        this.k = str;
        if (str != null) {
            c0298j4.put(str, this);
        }
        matrix.set(c0200fs.j);
        ArrayList arrayList = c0200fs.b;
        for (int i = 0; i < arrayList.size(); i++) {
            Object obj = arrayList.get(i);
            if (obj instanceof C0200fs) {
                this.b.add(new C0200fs((C0200fs) obj, c0298j4));
            } else {
                if (obj instanceof C0169es) {
                    C0169es c0169es = (C0169es) obj;
                    C0169es c0169es2 = new C0169es(c0169es);
                    c0169es2.e = 0.0f;
                    c0169es2.g = 1.0f;
                    c0169es2.h = 1.0f;
                    c0169es2.i = 0.0f;
                    c0169es2.j = 1.0f;
                    c0169es2.k = 0.0f;
                    c0169es2.l = Paint.Cap.BUTT;
                    c0169es2.m = Paint.Join.MITER;
                    c0169es2.n = 4.0f;
                    c0169es2.d = c0169es.d;
                    c0169es2.e = c0169es.e;
                    c0169es2.g = c0169es.g;
                    c0169es2.f = c0169es.f;
                    c0169es2.c = c0169es.c;
                    c0169es2.h = c0169es.h;
                    c0169es2.i = c0169es.i;
                    c0169es2.j = c0169es.j;
                    c0169es2.k = c0169es.k;
                    c0169es2.l = c0169es.l;
                    c0169es2.m = c0169es.m;
                    c0169es2.n = c0169es.n;
                    c0138ds = c0169es2;
                } else if (obj instanceof C0138ds) {
                    c0138ds = new C0138ds((C0138ds) obj);
                } else {
                    throw new IllegalStateException("Unknown object in the tree!");
                }
                this.b.add(c0138ds);
                Object obj2 = c0138ds.b;
                if (obj2 != null) {
                    c0298j4.put(obj2, c0138ds);
                }
            }
        }
    }
}
