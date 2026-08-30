package rikka.shizuku;

import android.graphics.Typeface;

/* JADX INFO: loaded from: classes.dex */
public final class Lb extends Zh {
    public static final Jb g = Jb.a;

    @Override // android.widget.TextView
    public final void setTypeface(Typeface typeface) {
        g.getClass();
        super.setTypeface(Jb.a(typeface));
        Jb.b(this, typeface);
    }

    @Override // rikka.shizuku.C0606t3, android.widget.TextView
    public final void setTypeface(Typeface typeface, int i) {
        g.getClass();
        super.setTypeface(Jb.a(typeface), i);
        Jb.b(this, typeface);
    }
}
