package rikka.shizuku;

import android.os.Bundle;
import java.io.IOException;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public final class N0 implements InterfaceC0681vg {
    public final /* synthetic */ String a;
    public final /* synthetic */ C0610t7 b;
    public final /* synthetic */ M0 c;
    public final /* synthetic */ N6 d;

    public N0(N6 n6, String str, C0610t7 c0610t7, M0 m0) {
        this.d = n6;
        this.a = str;
        this.b = c0610t7;
        this.c = m0;
    }

    @Override // rikka.shizuku.InterfaceC0681vg
    public final void a(InterfaceC0743xg interfaceC0743xg, EnumC0557rg enumC0557rg) throws IOException {
        boolean zEquals = EnumC0557rg.ON_START.equals(enumC0557rg);
        String str = this.a;
        N6 n6 = this.d;
        if (!zEquals) {
            if (EnumC0557rg.ON_STOP.equals(enumC0557rg)) {
                n6.e.remove(str);
                return;
            } else {
                if (EnumC0557rg.ON_DESTROY.equals(enumC0557rg)) {
                    n6.e(str);
                    return;
                }
                return;
            }
        }
        HashMap map = n6.e;
        C0610t7 c0610t7 = this.b;
        M0 m0 = this.c;
        map.put(str, new Q0(c0610t7, m0));
        HashMap map2 = n6.f;
        if (map2.containsKey(str)) {
            Object obj = map2.get(str);
            map2.remove(str);
            c0610t7.b(obj);
        }
        Bundle bundle = n6.g;
        K0 k0 = (K0) bundle.getParcelable(str);
        if (k0 != null) {
            bundle.remove(str);
            c0610t7.b(m0.L(k0.a, k0.b));
        }
    }
}
