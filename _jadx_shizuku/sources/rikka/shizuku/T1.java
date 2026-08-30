package rikka.shizuku;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

/* JADX INFO: loaded from: classes.dex */
public final class T1 extends Drawable.ConstantState {
    public final Drawable.ConstantState a;

    public T1(Drawable.ConstantState constantState) {
        this.a = constantState;
    }

    @Override // android.graphics.drawable.Drawable.ConstantState
    public final boolean canApplyTheme() {
        return this.a.canApplyTheme();
    }

    @Override // android.graphics.drawable.Drawable.ConstantState
    public final int getChangingConfigurations() {
        return this.a.getChangingConfigurations();
    }

    @Override // android.graphics.drawable.Drawable.ConstantState
    public final Drawable newDrawable() {
        U1 u1 = new U1(null);
        Drawable drawableNewDrawable = this.a.newDrawable();
        u1.a = drawableNewDrawable;
        drawableNewDrawable.setCallback(u1.f);
        return u1;
    }

    @Override // android.graphics.drawable.Drawable.ConstantState
    public final Drawable newDrawable(Resources resources) {
        U1 u1 = new U1(null);
        Drawable drawableNewDrawable = this.a.newDrawable(resources);
        u1.a = drawableNewDrawable;
        drawableNewDrawable.setCallback(u1.f);
        return u1;
    }

    @Override // android.graphics.drawable.Drawable.ConstantState
    public final Drawable newDrawable(Resources resources, Resources.Theme theme) {
        U1 u1 = new U1(null);
        Drawable drawableNewDrawable = this.a.newDrawable(resources, theme);
        u1.a = drawableNewDrawable;
        drawableNewDrawable.setCallback(u1.f);
        return u1;
    }
}
