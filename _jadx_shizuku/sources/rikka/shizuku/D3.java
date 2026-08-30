package rikka.shizuku;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import java.lang.reflect.Constructor;

/* JADX INFO: loaded from: classes.dex */
public class D3 {
    public static final Class[] b = {Context.class, AttributeSet.class};
    public static final int[] c = {R.attr.onClick};
    public static final int[] d = {R.attr.accessibilityHeading};
    public static final int[] e = {R.attr.accessibilityPaneTitle};
    public static final int[] f = {R.attr.screenReaderFocusable};
    public static final String[] g = {"android.widget.", "android.view.", "android.webkit."};
    public static final C0104co h = new C0104co(0);
    public final Object[] a = new Object[2];

    public C0389m2 a(Context context, AttributeSet attributeSet) {
        return new C0389m2(context, attributeSet);
    }

    public C0420n2 b(Context context, AttributeSet attributeSet) {
        return new C0420n2(context, attributeSet, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f04009f);
    }

    public C0482p2 c(Context context, AttributeSet attributeSet) {
        return new C0482p2(context, attributeSet, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f0400b3);
    }

    public C0513q2 d(Context context, AttributeSet attributeSet) {
        return new C0513q2(context, attributeSet, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f0400be);
    }

    public S2 e(Context context, AttributeSet attributeSet) {
        return new S2(context, attributeSet);
    }

    public C0606t3 f(Context context, AttributeSet attributeSet) {
        return new C0606t3(context, attributeSet);
    }

    public final View g(Context context, String str, String str2) {
        String strConcat;
        C0104co c0104co = h;
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
            constructor = Class.forName(strConcat, false, context.getClassLoader()).asSubclass(View.class).getConstructor(b);
            c0104co.put(str, constructor);
        }
        constructor.setAccessible(true);
        return (View) constructor.newInstance(this.a);
    }
}
