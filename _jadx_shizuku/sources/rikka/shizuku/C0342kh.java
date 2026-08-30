package rikka.shizuku;

import android.os.LocaleList;
import java.util.Locale;

/* JADX INFO: renamed from: rikka.shizuku.kh, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0342kh {
    public static final C0342kh b = new C0342kh(new C0373lh(new LocaleList(new Locale[0])));
    public final C0373lh a;

    public C0342kh(C0373lh c0373lh) {
        this.a = c0373lh;
    }

    public static C0342kh a(String str) {
        if (str == null || str.isEmpty()) {
            return b;
        }
        String[] strArrSplit = str.split(",", -1);
        int length = strArrSplit.length;
        Locale[] localeArr = new Locale[length];
        for (int i = 0; i < length; i++) {
            String str2 = strArrSplit[i];
            int i2 = AbstractC0311jh.a;
            localeArr[i] = Locale.forLanguageTag(str2);
        }
        return new C0342kh(new C0373lh(new LocaleList(localeArr)));
    }

    public final boolean equals(Object obj) {
        if (obj instanceof C0342kh) {
            return this.a.equals(((C0342kh) obj).a);
        }
        return false;
    }

    public final int hashCode() {
        return this.a.a.hashCode();
    }

    public final String toString() {
        return this.a.a.toString();
    }
}
