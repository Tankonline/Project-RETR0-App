package rikka.shizuku;

import android.content.ClipDescription;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Trace;
import android.util.AttributeSet;
import android.util.TypedValue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import moe.shizuku.privileged.api.R;

/* JADX INFO: loaded from: classes.dex */
public final class K3 implements InterfaceC0526qf {
    public static volatile K3 d;
    public static final Object e = new Object();
    public static K3 f;
    public final Object a;
    public Object b;
    public final Object c;

    public /* synthetic */ K3(Object obj, Object obj2, Object obj3) {
        this.a = obj;
        this.b = obj2;
        this.c = obj3;
    }

    public static K3 l(Context context) {
        if (d == null) {
            synchronized (e) {
                try {
                    if (d == null) {
                        d = new K3(context);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        return d;
    }

    public static K3 n(Context context, AttributeSet attributeSet, int[] iArr, int i) {
        return new K3(context, context.obtainStyledAttributes(attributeSet, iArr, i, 0));
    }

    @Override // rikka.shizuku.InterfaceC0526qf
    public Uri b() {
        return (Uri) this.c;
    }

    @Override // rikka.shizuku.InterfaceC0526qf
    public ClipDescription c() {
        return (ClipDescription) this.b;
    }

    @Override // rikka.shizuku.InterfaceC0526qf
    public Object d() {
        return null;
    }

    @Override // rikka.shizuku.InterfaceC0526qf
    public Uri e() {
        return (Uri) this.a;
    }

    public void f(Bundle bundle) {
        HashSet hashSet;
        String string = ((Context) this.c).getString(R.string._0_resource_name_obfuscated_res_0x7f11002e);
        if (bundle != null) {
            try {
                HashSet hashSet2 = new HashSet();
                Iterator<String> it = bundle.keySet().iterator();
                while (true) {
                    boolean zHasNext = it.hasNext();
                    hashSet = (HashSet) this.b;
                    if (!zHasNext) {
                        break;
                    }
                    String next = it.next();
                    if (string.equals(bundle.getString(next, null))) {
                        Class<?> cls = Class.forName(next);
                        if (InterfaceC0402mf.class.isAssignableFrom(cls)) {
                            hashSet.add(cls);
                        }
                    }
                }
                Iterator it2 = hashSet.iterator();
                while (it2.hasNext()) {
                    g((Class) it2.next(), hashSet2);
                }
            } catch (ClassNotFoundException e2) {
                throw new K6(e2);
            }
        }
    }

    public Object g(Class cls, HashSet hashSet) {
        Object objB;
        if (AbstractC0750xn.l()) {
            try {
                AbstractC0750xn.d(cls.getSimpleName());
            } catch (Throwable th) {
                Trace.endSection();
                throw th;
            }
        }
        if (hashSet.contains(cls)) {
            throw new IllegalStateException("Cannot initialize " + cls.getName() + ". Cycle detected.");
        }
        HashMap map = (HashMap) this.a;
        if (map.containsKey(cls)) {
            objB = map.get(cls);
        } else {
            hashSet.add(cls);
            try {
                InterfaceC0402mf interfaceC0402mf = (InterfaceC0402mf) cls.getDeclaredConstructor(null).newInstance(null);
                List<Class> listA = interfaceC0402mf.a();
                if (!listA.isEmpty()) {
                    for (Class cls2 : listA) {
                        if (!map.containsKey(cls2)) {
                            g(cls2, hashSet);
                        }
                    }
                }
                objB = interfaceC0402mf.b((Context) this.c);
                hashSet.remove(cls);
                map.put(cls, objB);
            } catch (Throwable th2) {
                throw new K6(th2);
            }
        }
        Trace.endSection();
        return objB;
    }

    public ColorStateList h(int i) {
        int resourceId;
        ColorStateList colorStateListO;
        TypedArray typedArray = (TypedArray) this.a;
        return (!typedArray.hasValue(i) || (resourceId = typedArray.getResourceId(i, 0)) == 0 || (colorStateListO = AbstractC0599sr.o((Context) this.c, resourceId)) == null) ? typedArray.getColorStateList(i) : colorStateListO;
    }

    public Drawable i(int i) {
        int resourceId;
        TypedArray typedArray = (TypedArray) this.a;
        return (!typedArray.hasValue(i) || (resourceId = typedArray.getResourceId(i, 0)) == 0) ? typedArray.getDrawable(i) : AbstractC0744xh.v((Context) this.c, resourceId);
    }

    public Drawable j(int i) {
        int resourceId;
        Drawable drawableC;
        if (!((TypedArray) this.a).hasValue(i) || (resourceId = ((TypedArray) this.a).getResourceId(i, 0)) == 0) {
            return null;
        }
        L2 l2A = L2.a();
        Context context = (Context) this.c;
        synchronized (l2A) {
            drawableC = l2A.a.c(resourceId, context, true);
        }
        return drawableC;
    }

    public Typeface k(int i, int i2, C0359l3 c0359l3) {
        int resourceId = ((TypedArray) this.a).getResourceId(i, 0);
        if (resourceId == 0) {
            return null;
        }
        if (((TypedValue) this.b) == null) {
            this.b = new TypedValue();
        }
        TypedValue typedValue = (TypedValue) this.b;
        ThreadLocal threadLocal = Xl.a;
        Context context = (Context) this.c;
        if (context.isRestricted()) {
            return null;
        }
        return Xl.b(context, resourceId, typedValue, i2, c0359l3, true, false);
    }

    public boolean m(C0148e7 c0148e7, C0425n7 c0425n7, boolean z) {
        int[] iArr = c0425n7.c0;
        int i = iArr[0];
        Z4 z4 = (Z4) this.b;
        z4.a = i;
        z4.b = iArr[1];
        z4.c = c0425n7.l();
        z4.d = c0425n7.i();
        z4.i = false;
        z4.j = z;
        boolean z2 = z4.a == 3;
        boolean z3 = z4.b == 3;
        boolean z5 = z2 && c0425n7.L > 0.0f;
        boolean z6 = z3 && c0425n7.L > 0.0f;
        int[] iArr2 = c0425n7.l;
        if (z5 && iArr2[0] == 4) {
            z4.a = 1;
        }
        if (z6 && iArr2[1] == 4) {
            z4.b = 1;
        }
        c0148e7.a(c0425n7, z4);
        c0425n7.y(z4.e);
        c0425n7.v(z4.f);
        c0425n7.w = z4.h;
        int i2 = z4.g;
        c0425n7.P = i2;
        c0425n7.w = i2 > 0;
        z4.j = false;
        return z4.i;
    }

    public void o() {
        ((TypedArray) this.a).recycle();
    }

    public void p(C0456o7 c0456o7, int i, int i2) {
        int i3 = c0456o7.Q;
        int i4 = c0456o7.R;
        c0456o7.Q = 0;
        c0456o7.R = 0;
        c0456o7.y(i);
        c0456o7.v(i2);
        if (i3 < 0) {
            c0456o7.Q = 0;
        } else {
            c0456o7.Q = i3;
        }
        if (i4 < 0) {
            c0456o7.R = 0;
        } else {
            c0456o7.R = i4;
        }
        ((C0456o7) this.c).E();
    }

    public K3(Runnable runnable) {
        this.c = new CopyOnWriteArrayList();
        this.a = new HashMap();
        this.b = runnable;
    }

    public K3(Context context, TypedArray typedArray) {
        this.c = context;
        this.a = typedArray;
    }

    public K3(Context context, LocationManager locationManager) {
        this.b = new Iq();
        this.c = context;
        this.a = locationManager;
    }

    public K3(Context context) {
        this.c = context.getApplicationContext();
        this.b = new HashSet();
        this.a = new HashMap();
    }

    public K3(C0456o7 c0456o7) {
        this.a = new ArrayList();
        this.b = new Z4();
        this.c = c0456o7;
    }

    @Override // rikka.shizuku.InterfaceC0526qf
    public void a() {
    }
}
