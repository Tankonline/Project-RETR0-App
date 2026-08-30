package rikka.shizuku;

import android.graphics.Rect;

/* JADX INFO: loaded from: classes.dex */
public final class V3 implements InterfaceC0678vd {
    public final Rect a;

    public V3(Rect rect) {
        this.a = rect;
    }

    @Override // rikka.shizuku.InterfaceC0678vd
    public final Object c(C0587sf c0587sf, Boolean bool, Boolean bool2, Boolean bool3, Boolean bool4) {
        boolean zBooleanValue = bool.booleanValue();
        boolean zBooleanValue2 = bool2.booleanValue();
        boolean zBooleanValue3 = bool3.booleanValue();
        boolean zBooleanValue4 = bool4.booleanValue();
        Rect rect = this.a;
        rect.left += zBooleanValue ? c0587sf.a : 0;
        rect.top += zBooleanValue2 ? c0587sf.b : 0;
        rect.right += zBooleanValue3 ? c0587sf.c : 0;
        rect.bottom += zBooleanValue4 ? c0587sf.d : 0;
        return Pe.b;
    }
}
