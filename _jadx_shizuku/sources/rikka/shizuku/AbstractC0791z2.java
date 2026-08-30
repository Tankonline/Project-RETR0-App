package rikka.shizuku;

import android.content.res.Configuration;
import android.os.LocaleList;

/* JADX INFO: renamed from: rikka.shizuku.z2, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0791z2 {
    public static void a(Configuration configuration, Configuration configuration2, Configuration configuration3) {
        LocaleList locales = configuration.getLocales();
        LocaleList locales2 = configuration2.getLocales();
        if (locales.equals(locales2)) {
            return;
        }
        configuration3.setLocales(locales2);
        configuration3.locale = configuration2.locale;
    }

    public static C0342kh b(Configuration configuration) {
        return C0342kh.a(configuration.getLocales().toLanguageTags());
    }

    public static void c(C0342kh c0342kh) {
        LocaleList.setDefault(LocaleList.forLanguageTags(c0342kh.a.a.toLanguageTags()));
    }

    public static void d(Configuration configuration, C0342kh c0342kh) {
        configuration.setLocales(LocaleList.forLanguageTags(c0342kh.a.a.toLanguageTags()));
    }
}
