package rikka.shizuku;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;

/* JADX INFO: renamed from: rikka.shizuku.ks, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0353ks extends Drawable.ConstantState {
    public final Drawable.ConstantState a;

    public C0353ks(Drawable.ConstantState constantState) {
        this.a = constantState;
    }

    @Override // android.graphics.drawable.Drawable.ConstantState
    public final boolean canApplyTheme() {
        return this.a.canApplyTheme();
    }

    @Override // android.graphics.drawable.Drawable.ConstantState
    public int getChangingConfigurations() {
        return this.a.getChangingConfigurations();
    }

    @Override // android.graphics.drawable.Drawable.ConstantState
    public final Drawable newDrawable() {
        C0384ls c0384ls = new C0384ls();
        c0384ls.a = (VectorDrawable) this.a.newDrawable();
        return c0384ls;
    }

    @Override // android.graphics.drawable.Drawable.ConstantState
    public final Drawable newDrawable(Resources resources) {
        C0384ls c0384ls = new C0384ls();
        c0384ls.a = (VectorDrawable) this.a.newDrawable(resources);
        return c0384ls;
    }

    @Override // android.graphics.drawable.Drawable.ConstantState
    public final Drawable newDrawable(Resources resources, Resources.Theme theme) {
        C0384ls c0384ls = new C0384ls();
        c0384ls.a = (VectorDrawable) this.a.newDrawable(resources, theme);
        return c0384ls;
    }
}
