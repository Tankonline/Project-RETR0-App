package rikka.shizuku;

import java.util.LinkedHashMap;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public final class Ji extends LinkedHashMap {
    public final String a() {
        Locale localeI = AbstractC0750xn.i();
        if (size() <= 0) {
            return null;
        }
        String language = localeI.getLanguage();
        Locale locale = new Locale(language, localeI.getCountry());
        for (String str : keySet()) {
            if (locale.toString().equals(str.replace('-', '_'))) {
                return (String) get(str);
            }
        }
        Locale locale2 = new Locale(language);
        for (Object obj : keySet()) {
            if (locale2.toString().equals(obj)) {
                return (String) get(obj);
            }
        }
        for (String str2 : keySet()) {
            if (str2.startsWith(locale2.toString())) {
                return (String) get(str2);
            }
        }
        if (containsKey("en")) {
            return (String) get("en");
        }
        if (containsKey("default")) {
            return (String) get("default");
        }
        for (Object obj2 : keySet()) {
            if (!"overwrite_default".equals(obj2)) {
                return (String) get(obj2);
            }
        }
        return null;
    }
}
