package rikka.shizuku;

import android.os.LocaleList;
import android.widget.TextView;

/* JADX INFO: renamed from: rikka.shizuku.o3, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0452o3 {
    public static LocaleList a(String str) {
        return LocaleList.forLanguageTags(str);
    }

    public static void b(TextView textView, LocaleList localeList) {
        textView.setTextLocales(localeList);
    }
}
