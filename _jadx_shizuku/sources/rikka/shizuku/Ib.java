package rikka.shizuku;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import moe.shizuku.privileged.api.R;

/* JADX INFO: loaded from: classes.dex */
public final class Ib extends C0513q2 {
    public static final Jb e = Jb.a;

    public Ib(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, R.attr._0_resource_name_obfuscated_res_0x7f0400be);
    }

    @Override // android.widget.TextView
    public final void setTypeface(Typeface typeface) {
        e.getClass();
        super.setTypeface(Jb.a(typeface));
        Jb.b(this, typeface);
    }

    @Override // android.widget.TextView
    public final void setTypeface(Typeface typeface, int i) {
        e.getClass();
        super.setTypeface(Jb.a(typeface), i);
        Jb.b(this, typeface);
    }
}
