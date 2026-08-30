package rikka.shizuku;

import android.content.res.ColorStateList;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/* JADX INFO: loaded from: classes.dex */
public class Wh extends Drawable.ConstantState {
    public C0317jn a;
    public Ga b;
    public ColorStateList c;
    public ColorStateList d;
    public ColorStateList e;
    public PorterDuff.Mode f;
    public Rect g;
    public final float h;
    public float i;
    public float j;
    public int k;
    public float l;
    public float m;
    public int n;
    public int o;
    public final Paint.Style p;

    public Wh(C0317jn c0317jn) {
        this.c = null;
        this.d = null;
        this.e = null;
        this.f = PorterDuff.Mode.SRC_IN;
        this.g = null;
        this.h = 1.0f;
        this.i = 1.0f;
        this.k = 255;
        this.l = 0.0f;
        this.m = 0.0f;
        this.n = 0;
        this.o = 0;
        this.p = Paint.Style.FILL_AND_STROKE;
        this.a = c0317jn;
        this.b = null;
    }

    @Override // android.graphics.drawable.Drawable.ConstantState
    public final int getChangingConfigurations() {
        return 0;
    }

    @Override // android.graphics.drawable.Drawable.ConstantState
    public Drawable newDrawable() {
        Xh xh = new Xh(this);
        xh.e = true;
        return xh;
    }

    public Wh(Wh wh) {
        this.c = null;
        this.d = null;
        this.e = null;
        this.f = PorterDuff.Mode.SRC_IN;
        this.g = null;
        this.h = 1.0f;
        this.i = 1.0f;
        this.k = 255;
        this.l = 0.0f;
        this.m = 0.0f;
        this.n = 0;
        this.o = 0;
        this.p = Paint.Style.FILL_AND_STROKE;
        this.a = wh.a;
        this.b = wh.b;
        this.j = wh.j;
        this.c = wh.c;
        this.d = wh.d;
        this.f = wh.f;
        this.e = wh.e;
        this.k = wh.k;
        this.h = wh.h;
        this.o = wh.o;
        this.i = wh.i;
        this.l = wh.l;
        this.m = wh.m;
        this.n = wh.n;
        this.p = wh.p;
        if (wh.g != null) {
            this.g = new Rect(wh.g);
        }
    }
}
