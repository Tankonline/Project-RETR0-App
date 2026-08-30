package rikka.shizuku;

import android.graphics.drawable.Drawable;

/* JADX INFO: renamed from: rikka.shizuku.r2, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0543r2 {
    public final C0513q2 a;
    public final boolean b = false;
    public final boolean c = false;
    public boolean d;

    public C0543r2(C0513q2 c0513q2) {
        this.a = c0513q2;
    }

    public final void a() {
        C0513q2 c0513q2 = this.a;
        Drawable checkMarkDrawable = c0513q2.getCheckMarkDrawable();
        if (checkMarkDrawable != null) {
            if (this.b || this.c) {
                Drawable drawableMutate = checkMarkDrawable.mutate();
                if (this.b) {
                    drawableMutate.setTintList(null);
                }
                if (this.c) {
                    drawableMutate.setTintMode(null);
                }
                if (drawableMutate.isStateful()) {
                    drawableMutate.setState(c0513q2.getDrawableState());
                }
                c0513q2.setCheckMarkDrawable(drawableMutate);
            }
        }
    }
}
