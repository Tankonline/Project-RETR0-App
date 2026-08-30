package rikka.shizuku;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import java.lang.reflect.Constructor;

/* JADX INFO: renamed from: rikka.shizuku.gg, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0219gg {
    public static final Class[] a = {Context.class, AttributeSet.class};
    public static final String[] b = {"android.widget.", "android.view.", "android.webkit."};
    public static final C0104co c = new C0104co(0);

    public static View a(Context context, String str, AttributeSet attributeSet, String str2) {
        String strConcat;
        C0104co c0104co = c;
        Constructor constructor = (Constructor) c0104co.get(str);
        if (constructor == null) {
            if (str2 != null) {
                try {
                    strConcat = str2.concat(str);
                } catch (Exception unused) {
                    return null;
                }
            } else {
                strConcat = str;
            }
            constructor = Class.forName(strConcat, false, context.getClassLoader()).asSubclass(View.class).getConstructor(a);
            c0104co.put(str, constructor);
        }
        constructor.setAccessible(true);
        return (View) constructor.newInstance(context, attributeSet);
    }
}
