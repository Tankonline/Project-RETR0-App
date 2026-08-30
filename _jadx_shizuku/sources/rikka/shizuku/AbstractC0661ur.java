package rikka.shizuku;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Trace;
import android.util.Log;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* JADX INFO: renamed from: rikka.shizuku.ur, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0661ur {
    public static final AbstractC0688vn a;
    public static final C0682vh b;

    static {
        AbstractC0750xn.d("TypefaceCompat static init");
        int i = Build.VERSION.SDK_INT;
        if (i >= 29) {
            a = new C0816zr();
        } else if (i >= 28) {
            a = new C0785yr();
        } else if (i >= 26) {
            a = new C0754xr();
        } else {
            Method method = C0723wr.d;
            if (method == null) {
                Log.w("TypefaceCompatApi24Impl", "Unable to collect necessary private methods.Fallback to legacy implementation.");
            }
            if (method != null) {
                a = new C0723wr();
            } else {
                a = new C0692vr();
            }
        }
        b = new C0682vh(16);
        Trace.endSection();
    }

    /* JADX WARN: Code duplicated, block: B:14:0x0031  */
    /* JADX WARN: Multi-variable type inference failed */
    public static Typeface a(Context context, InterfaceC0492pc interfaceC0492pc, Resources resources, int i, String str, int i2, int i3, X8 x8, boolean z) {
        Typeface typefaceF;
        Typeface typefaceCreate;
        List listUnmodifiableList;
        int i4 = 4;
        int i5 = 1;
        boolean z2 = false;
        Object[] objArr = 0;
        Object[] objArr2 = 0;
        Object[] objArr3 = 0;
        Object[] objArr4 = 0;
        int i6 = -3;
        if (interfaceC0492pc instanceof C0584sc) {
            C0584sc c0584sc = (C0584sc) interfaceC0492pc;
            String str2 = c0584sc.e;
            if (str2 == null || str2.isEmpty()) {
                typefaceCreate = null;
            } else {
                typefaceCreate = Typeface.create(str2, 0);
                Typeface typefaceCreate2 = Typeface.create(Typeface.DEFAULT, 0);
                if (typefaceCreate == null || typefaceCreate.equals(typefaceCreate2)) {
                    typefaceCreate = null;
                }
            }
            if (typefaceCreate != null) {
                if (x8 != null) {
                    new Handler(Looper.getMainLooper()).post(new N3(x8, 3, typefaceCreate));
                }
                return typefaceCreate;
            }
            Object[] objArr5 = !z ? x8 != null : c0584sc.d != 0;
            int i7 = z ? c0584sc.c : -1;
            Handler handler = new Handler(Looper.getMainLooper());
            Hm hm = new Hm();
            hm.a = x8;
            C0277ic c0277ic = c0584sc.b;
            if (c0277ic != null) {
                Object[] objArr6 = {c0584sc.a, c0277ic};
                ArrayList arrayList = new ArrayList(2);
                for (int i8 = 0; i8 < 2; i8++) {
                    arrayList.add(objArr6[i8]);
                }
                listUnmodifiableList = Collections.unmodifiableList(arrayList);
            } else {
                Object[] objArr7 = {c0584sc.a};
                ArrayList arrayList2 = new ArrayList(1);
                arrayList2.add(objArr7[0]);
                listUnmodifiableList = Collections.unmodifiableList(arrayList2);
            }
            Ml ml = new Ml(handler);
            R2 r2 = new R2(hm, ml, 8, z2);
            if (objArr5 != true) {
                String strA = AbstractC0461oc.a(listUnmodifiableList, i3);
                Typeface typeface = (Typeface) AbstractC0461oc.a.f(strA);
                if (typeface != null) {
                    ml.execute(new A0(hm, typeface, i4, objArr2 == true ? 1 : 0));
                    typefaceF = typeface;
                } else {
                    C0399mc c0399mc = new C0399mc(objArr == true ? 1 : 0, r2);
                    synchronized (AbstractC0461oc.c) {
                        try {
                            C0104co c0104co = AbstractC0461oc.d;
                            ArrayList arrayList3 = (ArrayList) c0104co.get(strA);
                            if (arrayList3 != null) {
                                arrayList3.add(c0399mc);
                            } else {
                                ArrayList arrayList4 = new ArrayList();
                                arrayList4.add(c0399mc);
                                c0104co.put(strA, arrayList4);
                                CallableC0368lc callableC0368lc = new CallableC0368lc(strA, context, listUnmodifiableList, i3, 1);
                                ThreadPoolExecutor threadPoolExecutor = AbstractC0461oc.b;
                                C0399mc c0399mc2 = new C0399mc(i5, strA);
                                Handler handler2 = Looper.myLooper() == null ? new Handler(Looper.getMainLooper()) : new Handler();
                                RunnableC0186fe runnableC0186fe = new RunnableC0186fe();
                                runnableC0186fe.b = callableC0368lc;
                                runnableC0186fe.c = c0399mc2;
                                runnableC0186fe.d = handler2;
                                threadPoolExecutor.execute(runnableC0186fe);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    typefaceF = null;
                }
            } else {
                if (listUnmodifiableList.size() > 1) {
                    throw new IllegalArgumentException("Fallbacks with blocking fetches are not supported for performance reasons");
                }
                C0277ic c0277ic2 = (C0277ic) listUnmodifiableList.get(0);
                C0682vh c0682vh = AbstractC0461oc.a;
                ArrayList arrayList5 = new ArrayList(1);
                arrayList5.add(new Object[]{c0277ic2}[0]);
                String strA2 = AbstractC0461oc.a(Collections.unmodifiableList(arrayList5), i3);
                Typeface typeface2 = (Typeface) AbstractC0461oc.a.f(strA2);
                if (typeface2 != null) {
                    ml.execute(new A0(hm, typeface2, i4, objArr4 == true ? 1 : 0));
                    typefaceF = typeface2;
                } else if (i7 == -1) {
                    ArrayList arrayList6 = new ArrayList(1);
                    arrayList6.add(new Object[]{c0277ic2}[0]);
                    C0430nc c0430ncB = AbstractC0461oc.b(strA2, context, Collections.unmodifiableList(arrayList6), i3);
                    r2.N(c0430ncB);
                    typefaceF = c0430ncB.a;
                } else {
                    try {
                        try {
                            try {
                                C0430nc c0430nc = (C0430nc) AbstractC0461oc.b.submit(new CallableC0368lc(strA2, context, c0277ic2, i3, 0)).get(i7, TimeUnit.MILLISECONDS);
                                r2.N(c0430nc);
                                typefaceF = c0430nc.a;
                            } catch (InterruptedException e) {
                                throw e;
                            }
                        } catch (ExecutionException e2) {
                            throw new RuntimeException(e2);
                        } catch (TimeoutException unused) {
                            throw new InterruptedException("timeout");
                        }
                    } catch (InterruptedException unused2) {
                        ((Ml) r2.c).execute(new RunnableC0670v5(i6, (int) (objArr3 == true ? 1 : 0), r2.b));
                        typefaceF = null;
                    }
                }
            }
        } else {
            typefaceF = a.f(context, (C0523qc) interfaceC0492pc, resources, i3);
            if (x8 != null) {
                if (typefaceF != null) {
                    new Handler(Looper.getMainLooper()).post(new N3(x8, 3, typefaceF));
                } else {
                    x8.h(-3);
                }
            }
        }
        if (typefaceF != null) {
            b.j(b(resources, i, str, i2, i3), typefaceF);
        }
        return typefaceF;
    }

    public static String b(Resources resources, int i, String str, int i2, int i3) {
        return resources.getResourcePackageName(i) + '-' + str + '-' + i2 + '-' + i + '-' + i3;
    }
}
