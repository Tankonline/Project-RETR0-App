package rikka.shizuku;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;

/* JADX INFO: renamed from: rikka.shizuku.jn, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0317jn {
    public AbstractC0046as a;
    public AbstractC0046as b;
    public AbstractC0046as c;
    public AbstractC0046as d;
    public S7 e;
    public S7 f;
    public S7 g;
    public S7 h;
    public Aa i;
    public Aa j;
    public Aa k;
    public Aa l;

    public C0317jn(int i) {
        switch (i) {
            case 1:
                this.a = new C0256hm();
                this.b = new C0256hm();
                this.c = new C0256hm();
                this.d = new C0256hm();
                this.e = new G(0.0f);
                this.f = new G(0.0f);
                this.g = new G(0.0f);
                this.h = new G(0.0f);
                this.i = new Aa();
                this.j = new Aa();
                this.k = new Aa();
                this.l = new Aa();
                break;
            default:
                this.a = new C0256hm();
                this.b = new C0256hm();
                this.c = new C0256hm();
                this.d = new C0256hm();
                this.e = new G(0.0f);
                this.f = new G(0.0f);
                this.g = new G(0.0f);
                this.h = new G(0.0f);
                this.i = new Aa();
                this.j = new Aa();
                this.k = new Aa();
                this.l = new Aa();
                break;
        }
    }

    public static C0317jn b(Context context, int i, int i2, G g) {
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, i);
        if (i2 != 0) {
            contextThemeWrapper = new ContextThemeWrapper(contextThemeWrapper, i2);
        }
        TypedArray typedArrayObtainStyledAttributes = contextThemeWrapper.obtainStyledAttributes(Lk.B);
        try {
            int i3 = typedArrayObtainStyledAttributes.getInt(0, 0);
            int i4 = typedArrayObtainStyledAttributes.getInt(3, i3);
            int i5 = typedArrayObtainStyledAttributes.getInt(4, i3);
            int i6 = typedArrayObtainStyledAttributes.getInt(2, i3);
            int i7 = typedArrayObtainStyledAttributes.getInt(1, i3);
            S7 s7E = e(typedArrayObtainStyledAttributes, 5, g);
            S7 s7E2 = e(typedArrayObtainStyledAttributes, 8, s7E);
            S7 s7E3 = e(typedArrayObtainStyledAttributes, 9, s7E);
            S7 s7E4 = e(typedArrayObtainStyledAttributes, 7, s7E);
            S7 s7E5 = e(typedArrayObtainStyledAttributes, 6, s7E);
            C0317jn c0317jn = new C0317jn(0);
            AbstractC0046as abstractC0046asI = AbstractC0599sr.i(i4);
            c0317jn.a = abstractC0046asI;
            d(abstractC0046asI);
            c0317jn.e = s7E2;
            AbstractC0046as abstractC0046asI2 = AbstractC0599sr.i(i5);
            c0317jn.b = abstractC0046asI2;
            d(abstractC0046asI2);
            c0317jn.f = s7E3;
            AbstractC0046as abstractC0046asI3 = AbstractC0599sr.i(i6);
            c0317jn.c = abstractC0046asI3;
            d(abstractC0046asI3);
            c0317jn.g = s7E4;
            AbstractC0046as abstractC0046asI4 = AbstractC0599sr.i(i7);
            c0317jn.d = abstractC0046asI4;
            d(abstractC0046asI4);
            c0317jn.h = s7E5;
            return c0317jn;
        } finally {
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    public static C0317jn c(Context context, AttributeSet attributeSet, int i, int i2) {
        G g = new G(0);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Lk.u, i, i2);
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(0, 0);
        int resourceId2 = typedArrayObtainStyledAttributes.getResourceId(1, 0);
        typedArrayObtainStyledAttributes.recycle();
        return b(context, resourceId, resourceId2, g);
    }

    public static void d(AbstractC0046as abstractC0046as) {
        if (abstractC0046as instanceof C0256hm) {
            ((C0256hm) abstractC0046as).getClass();
        } else if (abstractC0046as instanceof C0457o8) {
            ((C0457o8) abstractC0046as).getClass();
        }
    }

    public static S7 e(TypedArray typedArray, int i, S7 s7) {
        TypedValue typedValuePeekValue = typedArray.peekValue(i);
        if (typedValuePeekValue != null) {
            int i2 = typedValuePeekValue.type;
            if (i2 == 5) {
                return new G(TypedValue.complexToDimensionPixelSize(typedValuePeekValue.data, typedArray.getResources().getDisplayMetrics()));
            }
            if (i2 == 6) {
                return new Dl(typedValuePeekValue.getFraction(1.0f, 1.0f));
            }
        }
        return s7;
    }

    public C0317jn a() {
        C0317jn c0317jn = new C0317jn();
        c0317jn.a = this.a;
        c0317jn.b = this.b;
        c0317jn.c = this.c;
        c0317jn.d = this.d;
        c0317jn.e = this.e;
        c0317jn.f = this.f;
        c0317jn.g = this.g;
        c0317jn.h = this.h;
        c0317jn.i = this.i;
        c0317jn.j = this.j;
        c0317jn.k = this.k;
        c0317jn.l = this.l;
        return c0317jn;
    }

    public boolean f(RectF rectF) {
        boolean z = this.l.getClass().equals(Aa.class) && this.j.getClass().equals(Aa.class) && this.i.getClass().equals(Aa.class) && this.k.getClass().equals(Aa.class);
        float fA = this.e.a(rectF);
        return z && ((this.f.a(rectF) > fA ? 1 : (this.f.a(rectF) == fA ? 0 : -1)) == 0 && (this.h.a(rectF) > fA ? 1 : (this.h.a(rectF) == fA ? 0 : -1)) == 0 && (this.g.a(rectF) > fA ? 1 : (this.g.a(rectF) == fA ? 0 : -1)) == 0) && ((this.b instanceof C0256hm) && (this.a instanceof C0256hm) && (this.c instanceof C0256hm) && (this.d instanceof C0256hm));
    }

    public C0317jn g() {
        C0317jn c0317jn = new C0317jn();
        c0317jn.a = this.a;
        c0317jn.b = this.b;
        c0317jn.c = this.c;
        c0317jn.d = this.d;
        c0317jn.e = this.e;
        c0317jn.f = this.f;
        c0317jn.g = this.g;
        c0317jn.h = this.h;
        c0317jn.i = this.i;
        c0317jn.j = this.j;
        c0317jn.k = this.k;
        c0317jn.l = this.l;
        return c0317jn;
    }
}
