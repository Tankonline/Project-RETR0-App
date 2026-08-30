package rikka.shizuku;

import android.content.Context;
import android.content.ContextWrapper;

/* JADX INFO: loaded from: classes.dex */
public abstract class Sp extends ContextWrapper {
    public static final Object a = null;

    public static void a(Context context) {
        if (context.getResources() instanceof Up) {
            return;
        }
        context.getResources();
        int i = AbstractC0415ms.a;
    }
}
