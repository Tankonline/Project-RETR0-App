package rikka.shizuku;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageView;

/* JADX INFO: loaded from: classes.dex */
public class O2 extends ImageView {
    public final T0 a;
    public final I1 b;
    public boolean c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public O2(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Sp.a(context);
        this.c = false;
        Lp.a(this, getContext());
        T0 t0 = new T0(this);
        this.a = t0;
        t0.i(attributeSet, i);
        I1 i1 = new I1(this);
        this.b = i1;
        i1.d(attributeSet, i);
    }

    @Override // android.widget.ImageView, android.view.View
    public final void drawableStateChanged() {
        super.drawableStateChanged();
        T0 t0 = this.a;
        if (t0 != null) {
            t0.a();
        }
        I1 i1 = this.b;
        if (i1 != null) {
            i1.a();
        }
    }

    @Override // android.widget.ImageView, android.view.View
    public final boolean hasOverlappingRendering() {
        return !(((ImageView) this.b.b).getBackground() instanceof RippleDrawable) && super.hasOverlappingRendering();
    }

    @Override // android.view.View
    public final void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        T0 t0 = this.a;
        if (t0 != null) {
            t0.k();
        }
    }

    @Override // android.view.View
    public final void setBackgroundResource(int i) {
        super.setBackgroundResource(i);
        T0 t0 = this.a;
        if (t0 != null) {
            t0.l(i);
        }
    }

    @Override // android.widget.ImageView
    public final void setImageBitmap(Bitmap bitmap) {
        super.setImageBitmap(bitmap);
        I1 i1 = this.b;
        if (i1 != null) {
            i1.a();
        }
    }

    @Override // android.widget.ImageView
    public final void setImageDrawable(Drawable drawable) {
        I1 i1 = this.b;
        if (i1 != null && drawable != null && !this.c) {
            i1.a = drawable.getLevel();
        }
        super.setImageDrawable(drawable);
        if (i1 != null) {
            i1.a();
            if (this.c) {
                return;
            }
            ImageView imageView = (ImageView) i1.b;
            if (imageView.getDrawable() != null) {
                imageView.getDrawable().setLevel(i1.a);
            }
        }
    }

    @Override // android.widget.ImageView
    public final void setImageLevel(int i) {
        super.setImageLevel(i);
        this.c = true;
    }

    @Override // android.widget.ImageView
    public final void setImageResource(int i) {
        I1 i1 = this.b;
        if (i1 != null) {
            ImageView imageView = (ImageView) i1.b;
            if (i != 0) {
                Drawable drawableV = AbstractC0744xh.v(imageView.getContext(), i);
                if (drawableV != null) {
                    AbstractC0304ja.a(drawableV);
                }
                imageView.setImageDrawable(drawableV);
            } else {
                imageView.setImageDrawable(null);
            }
            i1.a();
        }
    }

    @Override // android.widget.ImageView
    public final void setImageURI(Uri uri) {
        super.setImageURI(uri);
        I1 i1 = this.b;
        if (i1 != null) {
            i1.a();
        }
    }
}
