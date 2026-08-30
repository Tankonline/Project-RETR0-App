package rikka.shizuku;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public abstract class T8 {
    public static final HashMap a = new HashMap();

    static {
        if ("en".equalsIgnoreCase(Locale.getDefault().getLanguage())) {
            Locale.getDefault();
            return;
        }
        Locale[] availableLocales = Locale.getAvailableLocales();
        for (int i = 0; i != availableLocales.length; i++) {
            if ("en".equalsIgnoreCase(availableLocales[i].getLanguage())) {
                Locale locale = availableLocales[i];
                return;
            }
        }
        Locale.getDefault();
    }

    public static Date a(Date date) {
        Locale locale = Locale.getDefault();
        if (locale == null) {
            return date;
        }
        HashMap map = a;
        synchronized (map) {
            try {
                Long lValueOf = (Long) map.get(locale);
                if (lValueOf == null) {
                    long time = new SimpleDateFormat("yyyyMMddHHmmssz").parse("19700101000000GMT+00:00").getTime();
                    lValueOf = time == 0 ? 0L : Long.valueOf(time);
                    map.put(locale, lValueOf);
                }
                if (lValueOf != 0L) {
                    return new Date(date.getTime() - lValueOf.longValue());
                }
                return date;
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
