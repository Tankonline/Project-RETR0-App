package rikka.shizuku;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import java.util.HashMap;
import java.util.List;
import rikka.html.text.style.BulletSpan;

/* JADX INFO: renamed from: rikka.shizuku.q9, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0520q9 implements InterfaceC0681vg {
    public final /* synthetic */ int a;
    public final Object b;
    public final Object c;

    public /* synthetic */ C0520q9(Object obj, int i, Object obj2) {
        this.a = i;
        this.b = obj;
        this.c = obj2;
    }

    @Override // rikka.shizuku.InterfaceC0681vg
    public final void a(InterfaceC0743xg interfaceC0743xg, EnumC0557rg enumC0557rg) {
        switch (this.a) {
            case 0:
                int i = AbstractC0489p9.a[enumC0557rg.ordinal()];
                Na na = (Na) this.b;
                switch (i) {
                    case 1:
                    case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                    case 4:
                    case BulletSpan.STANDARD_BULLET_RADIUS /* 5 */:
                    case 6:
                        break;
                    case 3:
                        (Build.VERSION.SDK_INT >= 28 ? W6.a(Looper.getMainLooper()) : new Handler(Looper.getMainLooper())).postDelayed(new Pa(), 500L);
                        na.a.f(na);
                        break;
                    case 7:
                        throw new IllegalArgumentException("ON_ANY must not been send by anybody");
                    default:
                        throw new K6();
                }
                InterfaceC0681vg interfaceC0681vg = (InterfaceC0681vg) this.c;
                if (interfaceC0681vg != null) {
                    interfaceC0681vg.a(interfaceC0743xg, enumC0557rg);
                    return;
                }
                return;
            case 1:
                if (enumC0557rg == EnumC0557rg.ON_START) {
                    ((C0805zg) this.b).f(this);
                    ((R2) this.c).W();
                    return;
                }
                return;
            default:
                HashMap map = ((C0209g6) this.c).a;
                List list = (List) map.get(enumC0557rg);
                InterfaceC0712wg interfaceC0712wg = (InterfaceC0712wg) this.b;
                C0209g6.a(list, interfaceC0743xg, enumC0557rg, interfaceC0712wg);
                C0209g6.a((List) map.get(EnumC0557rg.ON_ANY), interfaceC0743xg, enumC0557rg, interfaceC0712wg);
                return;
        }
    }

    public C0520q9(InterfaceC0712wg interfaceC0712wg) {
        this.a = 2;
        this.b = interfaceC0712wg;
        C0271i6 c0271i6 = C0271i6.c;
        Class<?> cls = interfaceC0712wg.getClass();
        C0209g6 c0209g6 = (C0209g6) c0271i6.a.get(cls);
        this.c = c0209g6 == null ? c0271i6.a(cls, null) : c0209g6;
    }
}
