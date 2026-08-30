package rikka.shizuku;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import moe.shizuku.privileged.api.R;

/* JADX INFO: renamed from: rikka.shizuku.in, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0287in {
    public static final int[] i = new int[3];
    public static final float[] j = {0.0f, 0.5f, 1.0f};
    public static final int[] k = new int[4];
    public static final float[] l = {0.0f, 0.0f, 0.5f, 1.0f};
    public int a;
    public int b;
    public int c;
    public final Object d;
    public Object e;
    public Object f;
    public final Object g;
    public final Object h;

    /* JADX WARN: Multi-variable type inference failed */
    public C0287in(ViewGroup viewGroup, Context context, AttributeSet attributeSet) {
        this.d = viewGroup;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Dk.a, R.attr._0_resource_name_obfuscated_res_0x7f04007e, R.style._0_resource_name_obfuscated_res_0x7f12038f);
        this.g = typedArrayObtainStyledAttributes.getDrawable(3);
        this.h = typedArrayObtainStyledAttributes.getDrawable(0);
        int i2 = typedArrayObtainStyledAttributes.getInt(5, 0);
        if (i2 == 0) {
            this.a = 1;
        } else if (i2 == 1) {
            this.a = 2;
        } else if (i2 != 3) {
            this.a = 3;
        } else {
            this.a = 4;
        }
        int i3 = typedArrayObtainStyledAttributes.getInt(2, 0);
        if (i3 == 0) {
            this.b = 1;
        } else if (i3 == 1) {
            this.b = 2;
        } else if (i3 != 3) {
            this.b = 3;
        } else {
            this.b = 4;
        }
        if (typedArrayObtainStyledAttributes.getInt(4, 0) != 0) {
            this.c = 2;
        } else {
            this.c = 1;
        }
        typedArrayObtainStyledAttributes.getInt(1, 0);
        typedArrayObtainStyledAttributes.recycle();
        viewGroup.addOnAttachStateChangeListener(new ViewOnAttachStateChangeListenerC0511q0(1, new ViewOnLayoutChangeListenerC0270i5(0, this)));
    }

    public boolean a() {
        Boolean bool = (Boolean) this.f;
        if (bool != null) {
            return bool.booleanValue();
        }
        return this.b == 4;
    }

    public boolean b() {
        Boolean bool = (Boolean) this.e;
        if (bool != null) {
            return bool.booleanValue();
        }
        int i2 = this.a;
        return i2 == 2 || i2 == 4;
    }

    public void c(Canvas canvas) {
        Drawable drawable = (Drawable) this.h;
        Drawable drawable2 = (Drawable) this.g;
        if (drawable2 == null && drawable == null) {
            return;
        }
        int iSave = canvas.save();
        int i2 = this.c;
        ViewGroup viewGroup = (ViewGroup) this.d;
        if (drawable2 != null) {
            int scrollY = viewGroup.getScrollY();
            if (i2 == 1) {
                scrollY += viewGroup.getPaddingTop();
            }
            canvas.translate(0.0f, scrollY);
            if (b()) {
                drawable2.setBounds(0, 0, canvas.getWidth(), drawable2.getIntrinsicHeight());
                drawable2.draw(canvas);
            }
            canvas.translate(0.0f, -scrollY);
        }
        if (drawable != null) {
            int height = (canvas.getHeight() + viewGroup.getScrollY()) - drawable.getIntrinsicHeight();
            if (i2 == 1) {
                height -= viewGroup.getPaddingBottom();
            }
            canvas.translate(0.0f, height);
            if (a()) {
                drawable.setBounds(0, 0, canvas.getWidth(), drawable.getIntrinsicHeight());
                drawable.draw(canvas);
            }
        }
        canvas.restoreToCount(iSave);
    }

    public void d(int i2) {
        this.a = E6.e(i2, 68);
        this.b = E6.e(i2, 20);
        this.c = E6.e(i2, 0);
        ((Paint) this.d).setColor(this.a);
    }

    public C0287in() {
        this.h = new Path();
        Paint paint = new Paint();
        this.g = paint;
        this.d = new Paint();
        d(-16777216);
        paint.setColor(0);
        Paint paint2 = new Paint(4);
        this.e = paint2;
        paint2.setStyle(Paint.Style.FILL);
        this.f = new Paint(paint2);
    }
}
