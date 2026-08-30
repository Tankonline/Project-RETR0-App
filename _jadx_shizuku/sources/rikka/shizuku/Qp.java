package rikka.shizuku;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;

/* JADX INFO: loaded from: classes.dex */
public final class Qp extends AbstractC0417n implements InterfaceC0171f {
    public final AbstractC0633u a;

    public Qp(Date date) {
        Locale locale = Locale.ROOT;
        SimpleTimeZone simpleTimeZone = new SimpleTimeZone(0, "Z");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss", locale);
        simpleDateFormat.setTimeZone(simpleTimeZone);
        String str = simpleDateFormat.format(date) + "Z";
        int i = Integer.parseInt(str.substring(0, 4));
        if (i < 1950 || i > 2049) {
            this.a = new C0673v8(str);
        } else {
            this.a = new G8(str.substring(2));
        }
    }

    public static Qp i(InterfaceC0202g interfaceC0202g) {
        if (interfaceC0202g == null || (interfaceC0202g instanceof Qp)) {
            return (Qp) interfaceC0202g;
        }
        if (interfaceC0202g instanceof B) {
            return new Qp((B) interfaceC0202g);
        }
        if (interfaceC0202g instanceof C0324k) {
            return new Qp((C0324k) interfaceC0202g);
        }
        throw new IllegalArgumentException("unknown object in factory: ".concat(interfaceC0202g.getClass().getName()));
    }

    @Override // rikka.shizuku.InterfaceC0202g
    public final AbstractC0633u d() {
        return this.a;
    }

    public final String toString() {
        AbstractC0633u abstractC0633u = this.a;
        if (!(abstractC0633u instanceof B)) {
            return ((C0324k) abstractC0633u).u();
        }
        String strR = ((B) abstractC0633u).r();
        return strR.charAt(0) < '5' ? "20".concat(strR) : "19".concat(strR);
    }

    public Qp(AbstractC0633u abstractC0633u) {
        if (!(abstractC0633u instanceof B) && !(abstractC0633u instanceof C0324k)) {
            throw new IllegalArgumentException("unknown object passed to Time");
        }
        this.a = abstractC0633u;
    }
}
