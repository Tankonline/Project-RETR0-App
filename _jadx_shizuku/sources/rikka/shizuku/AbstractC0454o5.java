package rikka.shizuku;

import android.os.Build;
import java.util.Locale;

/* JADX INFO: renamed from: rikka.shizuku.o5, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0454o5 {
    static {
        int i = Build.VERSION.SDK_INT;
        if (i >= 30) {
            AbstractC0049b0.a(30);
        }
        if (i >= 30) {
            AbstractC0049b0.a(31);
        }
        if (i >= 30) {
            AbstractC0049b0.a(33);
        }
        if (i >= 30) {
            AbstractC0049b0.a(1000000);
        }
    }

    public static final boolean a() {
        int i = Build.VERSION.SDK_INT;
        if (i >= 33) {
            return true;
        }
        if (i >= 32) {
            String str = Build.VERSION.CODENAME;
            if (!"REL".equals(str)) {
                Locale locale = Locale.ROOT;
                Integer num = str.toUpperCase(locale).equals("BAKLAVA") ? 0 : null;
                Integer num2 = "Tiramisu".toUpperCase(locale).equals("BAKLAVA") ? 0 : null;
                if (num == null || num2 == null) {
                    if (num == null && num2 == null) {
                        if (str.toUpperCase(locale).compareTo("Tiramisu".toUpperCase(locale)) >= 0) {
                            return true;
                        }
                    } else if (num != null) {
                        return true;
                    }
                } else if (num.intValue() >= num2.intValue()) {
                    return true;
                }
            }
        }
        return false;
    }
}
