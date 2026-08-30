package rikka.shizuku;

import android.graphics.Typeface;
import com.google.android.material.button.MaterialButton;

/* JADX INFO: loaded from: classes.dex */
public final class Kb extends MaterialButton {
    public static final Jb t = Jb.a;

    @Override // android.widget.TextView
    public final void setTypeface(Typeface typeface) {
        t.getClass();
        super.setTypeface(Jb.a(typeface));
        Jb.b(this, typeface);
    }

    @Override // android.widget.TextView
    public final void setTypeface(Typeface typeface, int i) {
        t.getClass();
        super.setTypeface(Jb.a(typeface), i);
        Jb.b(this, typeface);
    }
}
