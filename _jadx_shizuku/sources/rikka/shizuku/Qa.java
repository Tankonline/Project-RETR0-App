package rikka.shizuku;

import android.text.Editable;

/* JADX INFO: loaded from: classes.dex */
public final class Qa extends Editable.Factory {
    public static final Object a = new Object();
    public static volatile Qa b;
    public static Class c;

    @Override // android.text.Editable.Factory
    public final Editable newEditable(CharSequence charSequence) {
        Class cls = c;
        return cls != null ? new C0658uo(cls, charSequence) : super.newEditable(charSequence);
    }
}
