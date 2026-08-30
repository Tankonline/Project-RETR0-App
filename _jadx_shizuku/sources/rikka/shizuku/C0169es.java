package rikka.shizuku;

import android.content.res.ColorStateList;
import android.graphics.Paint;

/* JADX INFO: renamed from: rikka.shizuku.es, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0169es extends AbstractC0262hs {
    public C0788z d;
    public float e;
    public C0788z f;
    public float g;
    public float h;
    public float i;
    public float j;
    public float k;
    public Paint.Cap l;
    public Paint.Join m;
    public float n;

    @Override // rikka.shizuku.AbstractC0231gs
    public final boolean a() {
        return this.f.d() || this.d.d();
    }

    /* JADX WARN: Code duplicated, block: B:7:0x001e  */
    @Override // rikka.shizuku.AbstractC0231gs
    public final boolean b(int[] iArr) {
        boolean z;
        C0788z c0788z = this.f;
        boolean z2 = false;
        if (c0788z.d()) {
            ColorStateList colorStateList = (ColorStateList) c0788z.c;
            int colorForState = colorStateList.getColorForState(iArr, colorStateList.getDefaultColor());
            if (colorForState != c0788z.a) {
                c0788z.a = colorForState;
                z = true;
            } else {
                z = false;
            }
        } else {
            z = false;
        }
        C0788z c0788z2 = this.d;
        if (c0788z2.d()) {
            ColorStateList colorStateList2 = (ColorStateList) c0788z2.c;
            int colorForState2 = colorStateList2.getColorForState(iArr, colorStateList2.getDefaultColor());
            if (colorForState2 != c0788z2.a) {
                c0788z2.a = colorForState2;
                z2 = true;
            }
        }
        return z | z2;
    }

    public float getFillAlpha() {
        return this.h;
    }

    public int getFillColor() {
        return this.f.a;
    }

    public float getStrokeAlpha() {
        return this.g;
    }

    public int getStrokeColor() {
        return this.d.a;
    }

    public float getStrokeWidth() {
        return this.e;
    }

    public float getTrimPathEnd() {
        return this.j;
    }

    public float getTrimPathOffset() {
        return this.k;
    }

    public float getTrimPathStart() {
        return this.i;
    }

    public void setFillAlpha(float f) {
        this.h = f;
    }

    public void setFillColor(int i) {
        this.f.a = i;
    }

    public void setStrokeAlpha(float f) {
        this.g = f;
    }

    public void setStrokeColor(int i) {
        this.d.a = i;
    }

    public void setStrokeWidth(float f) {
        this.e = f;
    }

    public void setTrimPathEnd(float f) {
        this.j = f;
    }

    public void setTrimPathOffset(float f) {
        this.k = f;
    }

    public void setTrimPathStart(float f) {
        this.i = f;
    }
}
