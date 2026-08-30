package rikka.shizuku;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import com.google.android.material.button.MaterialButton;

/* JADX INFO: loaded from: classes.dex */
public final class Gh {
    public final MaterialButton a;
    public C0317jn b;
    public int c;
    public int d;
    public int e;
    public int f;
    public int g;
    public PorterDuff.Mode h;
    public ColorStateList i;
    public ColorStateList j;
    public ColorStateList k;
    public Xh l;
    public boolean o;
    public RippleDrawable q;
    public int r;
    public boolean m = false;
    public boolean n = false;
    public boolean p = true;

    public Gh(MaterialButton materialButton, C0317jn c0317jn) {
        this.a = materialButton;
        this.b = c0317jn;
    }

    public final InterfaceC0657un a() {
        RippleDrawable rippleDrawable = this.q;
        if (rippleDrawable == null || rippleDrawable.getNumberOfLayers() <= 1) {
            return null;
        }
        return this.q.getNumberOfLayers() > 2 ? (InterfaceC0657un) this.q.getDrawable(2) : (InterfaceC0657un) this.q.getDrawable(1);
    }

    public final Xh b(boolean z) {
        RippleDrawable rippleDrawable = this.q;
        if (rippleDrawable == null || rippleDrawable.getNumberOfLayers() <= 0) {
            return null;
        }
        return (Xh) ((LayerDrawable) ((InsetDrawable) this.q.getDrawable(0)).getDrawable()).getDrawable(!z ? 1 : 0);
    }

    public final void c(C0317jn c0317jn) {
        this.b = c0317jn;
        if (b(false) != null) {
            b(false).a(c0317jn);
        }
        if (b(true) != null) {
            b(true).a(c0317jn);
        }
        if (a() != null) {
            a().a(c0317jn);
        }
    }
}
