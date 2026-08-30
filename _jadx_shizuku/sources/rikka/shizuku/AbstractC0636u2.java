package rikka.shizuku;

import android.app.LocaleManager;
import android.os.LocaleList;

/* JADX INFO: renamed from: rikka.shizuku.u2, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0636u2 {
    public static LocaleList a(Object obj) {
        return ((LocaleManager) obj).getApplicationLocales();
    }

    public static void b(Object obj, LocaleList localeList) {
        ((LocaleManager) obj).setApplicationLocales(localeList);
    }
}
