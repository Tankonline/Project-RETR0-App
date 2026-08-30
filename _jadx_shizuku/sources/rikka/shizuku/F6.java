package rikka.shizuku;

import rikka.html.text.style.BulletSpan;

/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class F6 implements InterfaceC0585sd {
    public final /* synthetic */ int a;

    public /* synthetic */ F6(int i) {
        this.a = i;
    }

    @Override // rikka.shizuku.InterfaceC0585sd
    public final Object f(Object obj, Object obj2) {
        G6 g6;
        switch (this.a) {
            case 0:
                String str = (String) obj;
                T7 t7 = (T7) obj2;
                if (str.length() == 0) {
                    return t7.toString();
                }
                return str + ", " + t7;
            case 1:
                T7 t8 = (T7) obj2;
                V7 v7J = ((V7) obj).j(t8.getKey());
                C0152eb c0152eb = C0152eb.a;
                if (v7J == c0152eb) {
                    return t8;
                }
                Aa aa = Aa.a;
                I7 i7 = (I7) v7J.g(aa);
                if (i7 == null) {
                    g6 = new G6(v7J, t8);
                } else {
                    V7 v7J2 = v7J.j(aa);
                    if (v7J2 == c0152eb) {
                        return new G6(t8, i7);
                    }
                    g6 = new G6(new G6(v7J2, t8), i7);
                }
                return g6;
            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                return ((V7) obj).b((T7) obj2);
            case 3:
                return ((V7) obj).b((T7) obj2);
            case 4:
                Boolean bool = (Boolean) obj;
                bool.getClass();
                return bool;
            case BulletSpan.STANDARD_BULLET_RADIUS /* 5 */:
                return obj;
            case 6:
                AbstractC0535qo.h(obj);
                return null;
            default:
                return (Pp) obj;
        }
    }
}
