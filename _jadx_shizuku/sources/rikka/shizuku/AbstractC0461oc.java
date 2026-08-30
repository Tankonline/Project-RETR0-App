package rikka.shizuku;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Trace;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* JADX INFO: renamed from: rikka.shizuku.oc, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0461oc {
    public static final C0682vh a = new C0682vh(16);
    public static final ThreadPoolExecutor b;
    public static final Object c;
    public static final C0104co d;

    static {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, 1, 10000, TimeUnit.MILLISECONDS, new LinkedBlockingDeque(), new Ll());
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        b = threadPoolExecutor;
        c = new Object();
        d = new C0104co(0);
    }

    public static String a(List list, int i) {
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < list.size(); i2++) {
            sb.append(((C0277ic) list.get(i2)).e);
            sb.append("-");
            sb.append(i);
            if (i2 < list.size() - 1) {
                sb.append(";");
            }
        }
        return sb.toString();
    }

    public static C0430nc b(String str, Context context, List list, int i) {
        int i2;
        Typeface typefaceG;
        AbstractC0750xn.d("getFontSync");
        C0682vh c0682vh = a;
        try {
            Typeface typeface = (Typeface) c0682vh.f(str);
            if (typeface != null) {
                C0430nc c0430nc = new C0430nc(typeface);
                Trace.endSection();
                return c0430nc;
            }
            try {
                I1 i1A = AbstractC0246hc.a(context, list);
                int i3 = i1A.a;
                List list2 = (List) i1A.b;
                if (i3 == 0) {
                    C0615tc[] c0615tcArr = (C0615tc[]) list2.get(0);
                    if (c0615tcArr == null || c0615tcArr.length == 0) {
                        i2 = 1;
                    } else {
                        int length = c0615tcArr.length;
                        int i4 = 0;
                        while (true) {
                            if (i4 >= length) {
                                i2 = 0;
                                break;
                            }
                            int i5 = c0615tcArr[i4].e;
                            if (i5 != 0) {
                                if (i5 >= 0) {
                                    i2 = i5;
                                    break;
                                }
                                i2 = -3;
                                break;
                            }
                            i4++;
                        }
                    }
                } else {
                    if (i3 != 1) {
                        i2 = -3;
                        break;
                    }
                    i2 = -2;
                }
                if (i2 != 0) {
                    C0430nc c0430nc2 = new C0430nc(i2);
                    Trace.endSection();
                    return c0430nc2;
                }
                if (list2.size() <= 1 || Build.VERSION.SDK_INT < 29) {
                    C0615tc[] c0615tcArr2 = (C0615tc[]) list2.get(0);
                    AbstractC0688vn abstractC0688vn = AbstractC0661ur.a;
                    AbstractC0750xn.d("TypefaceCompat.createFromFontInfo");
                    try {
                        typefaceG = AbstractC0661ur.a.g(context, c0615tcArr2, i);
                        Trace.endSection();
                    } catch (Throwable th) {
                        Trace.endSection();
                        throw th;
                    }
                } else {
                    AbstractC0688vn abstractC0688vn2 = AbstractC0661ur.a;
                    AbstractC0750xn.d("TypefaceCompat.createFromFontInfoWithFallback");
                    try {
                        typefaceG = AbstractC0661ur.a.h(context, list2, i);
                        Trace.endSection();
                    } catch (Throwable th2) {
                        Trace.endSection();
                        throw th2;
                    }
                }
                if (typefaceG == null) {
                    C0430nc c0430nc3 = new C0430nc(-3);
                    Trace.endSection();
                    return c0430nc3;
                }
                c0682vh.j(str, typefaceG);
                C0430nc c0430nc4 = new C0430nc(typefaceG);
                Trace.endSection();
                return c0430nc4;
            } catch (PackageManager.NameNotFoundException unused) {
                C0430nc c0430nc5 = new C0430nc(-1);
                Trace.endSection();
                return c0430nc5;
            }
        } catch (Throwable th3) {
            Trace.endSection();
            throw th3;
        }
    }
}
