package rikka.shizuku;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;

/* JADX INFO: loaded from: classes.dex */
public final class Rd {
    public static final C0646uc h = C0646uc.d;
    public static final Vp i = Zp.a;
    public static final Wp j = Zp.b;
    public final ThreadLocal a = new ThreadLocal();
    public final ConcurrentHashMap b = new ConcurrentHashMap();
    public final C0672v7 c;
    public final Nf d;
    public final List e;
    public final boolean f;
    public final C0646uc g;

    public Rd(C0769yb c0769yb, EnumC0061bc enumC0061bc, HashMap map, boolean z, C0646uc c0646uc, boolean z2, int i2, ArrayList arrayList, Zp zp, Zp zp2, ArrayList arrayList2) {
        C0672v7 c0672v7 = new C0672v7(map, z2, arrayList2);
        this.c = c0672v7;
        this.f = z;
        this.g = c0646uc;
        ArrayList arrayList3 = new ArrayList();
        arrayList3.add(AbstractC0568rr.A);
        arrayList3.add(zp == Zp.a ? C0283ij.c : new C0160ej(1, zp));
        arrayList3.add(c0769yb);
        arrayList3.addAll(arrayList);
        arrayList3.add(AbstractC0568rr.p);
        arrayList3.add(AbstractC0568rr.g);
        arrayList3.add(AbstractC0568rr.d);
        arrayList3.add(AbstractC0568rr.e);
        arrayList3.add(AbstractC0568rr.f);
        Lq od = i2 == 1 ? AbstractC0568rr.k : new Od();
        arrayList3.add(new C0321jr(Long.TYPE, Long.class, od));
        arrayList3.add(new C0321jr(Double.TYPE, Double.class, new Nd(0)));
        arrayList3.add(new C0321jr(Float.TYPE, Float.class, new Nd(1)));
        arrayList3.add(zp2 == Zp.b ? Pd.c : new C0160ej(0, new Pd(2, zp2)));
        arrayList3.add(AbstractC0568rr.h);
        arrayList3.add(AbstractC0568rr.i);
        arrayList3.add(new C0261hr(AtomicLong.class, new Pd(0, od).a(), 0));
        arrayList3.add(new C0261hr(AtomicLongArray.class, new Pd(1, od).a(), 0));
        arrayList3.add(AbstractC0568rr.j);
        arrayList3.add(AbstractC0568rr.l);
        arrayList3.add(AbstractC0568rr.q);
        arrayList3.add(AbstractC0568rr.r);
        arrayList3.add(new C0261hr(BigDecimal.class, AbstractC0568rr.m, 0));
        arrayList3.add(new C0261hr(BigInteger.class, AbstractC0568rr.n, 0));
        arrayList3.add(new C0261hr(C0280ig.class, AbstractC0568rr.o, 0));
        arrayList3.add(AbstractC0568rr.s);
        arrayList3.add(AbstractC0568rr.t);
        arrayList3.add(AbstractC0568rr.v);
        arrayList3.add(AbstractC0568rr.w);
        arrayList3.add(AbstractC0568rr.y);
        arrayList3.add(AbstractC0568rr.u);
        arrayList3.add(AbstractC0568rr.b);
        arrayList3.add(C0089c9.c);
        arrayList3.add(AbstractC0568rr.x);
        if (Go.a) {
            arrayList3.add(Go.e);
            arrayList3.add(Go.d);
            arrayList3.add(Go.f);
        }
        arrayList3.add(C0453o4.c);
        arrayList3.add(AbstractC0568rr.a);
        arrayList3.add(new C0640u6(c0672v7, 0));
        arrayList3.add(new C0640u6(c0672v7, 1));
        Nf nf = new Nf(c0672v7);
        this.d = nf;
        arrayList3.add(nf);
        arrayList3.add(AbstractC0568rr.B);
        arrayList3.add(new Cl(c0672v7, enumC0061bc, c0769yb, nf, arrayList2));
        this.e = Collections.unmodifiableList(arrayList3);
    }

    public static void a(double d) {
        if (Double.isNaN(d) || Double.isInfinite(d)) {
            throw new IllegalArgumentException(d + " is not a valid double value as per JSON specification. To override this behavior, use GsonBuilder.serializeSpecialFloatingPointValues() method.");
        }
    }

    public final Object b(InputStreamReader inputStreamReader) {
        Object objB;
        C0630tr c0630tr = new C0630tr(Qn.class);
        Vf vf = new Vf(inputStreamReader);
        boolean z = true;
        vf.o = 1;
        try {
            try {
                try {
                    vf.v();
                    z = false;
                    Lq lqC = c(c0630tr);
                    Class cls = c0630tr.a;
                    objB = lqC.b(vf);
                    Class clsC0 = AbstractC0744xh.c0(cls);
                    if (objB != null && !clsC0.isInstance(objB)) {
                        throw new ClassCastException("Type adapter '" + lqC + "' returned wrong type; requested " + cls + " but got instance of " + objB.getClass() + "\nVerify that the adapter was registered for the correct type.");
                    }
                    vf.o = 2;
                } catch (Throwable th) {
                    vf.o = 2;
                    throw th;
                }
            } catch (EOFException e) {
                if (!z) {
                    throw new Rf(e);
                }
                vf.o = 2;
                objB = null;
            } catch (IllegalStateException e2) {
                throw new Rf(e2);
            }
            if (objB != null) {
                try {
                    if (vf.v() != 10) {
                        throw new Rf("JSON document was not fully consumed.");
                    }
                } catch (C0775yh e3) {
                    throw new Rf(e3);
                } catch (IOException e4) {
                    throw new Rf(e4);
                }
            }
            return objB;
        } catch (IOException e5) {
            throw new Rf(e5);
        } catch (AssertionError e6) {
            throw new AssertionError("AssertionError (GSON 2.13.1): " + e6.getMessage(), e6);
        }
    }

    public final Lq c(C0630tr c0630tr) {
        boolean z;
        ConcurrentHashMap concurrentHashMap = this.b;
        Lq lq = (Lq) concurrentHashMap.get(c0630tr);
        if (lq != null) {
            return lq;
        }
        ThreadLocal threadLocal = this.a;
        Map map = (Map) threadLocal.get();
        if (map == null) {
            map = new HashMap();
            threadLocal.set(map);
            z = true;
        } else {
            Lq lq2 = (Lq) map.get(c0630tr);
            if (lq2 != null) {
                return lq2;
            }
            z = false;
        }
        try {
            Qd qd = new Qd();
            map.put(c0630tr, qd);
            Iterator it = this.e.iterator();
            Lq lqA = null;
            while (it.hasNext()) {
                lqA = ((Mq) it.next()).a(this, c0630tr);
                if (lqA != null) {
                    if (qd.a != null) {
                        throw new AssertionError("Delegate is already set");
                    }
                    qd.a = lqA;
                    map.put(c0630tr, lqA);
                    break;
                }
            }
            if (z) {
                threadLocal.remove();
            }
            if (lqA != null) {
                if (z) {
                    concurrentHashMap.putAll(map);
                }
                return lqA;
            }
            throw new IllegalArgumentException("GSON (2.13.1) cannot handle " + c0630tr);
        } catch (Throwable th) {
            if (z) {
                threadLocal.remove();
            }
            throw th;
        }
    }

    public final Wf d(Writer writer) {
        Wf wf = new Wf(writer);
        wf.k(this.g);
        wf.i = this.f;
        wf.h = 2;
        wf.k = false;
        return wf;
    }

    public final String e(Object obj) {
        if (obj == null) {
            StringWriter stringWriter = new StringWriter();
            try {
                g(d(stringWriter));
                return stringWriter.toString();
            } catch (IOException e) {
                throw new Rf(e);
            }
        }
        Class cls = obj.getClass();
        StringWriter stringWriter2 = new StringWriter();
        try {
            f(obj, cls, d(stringWriter2));
            return stringWriter2.toString();
        } catch (IOException e2) {
            throw new Rf(e2);
        }
    }

    public final void f(Object obj, Class cls, Wf wf) {
        Lq lqC = c(new C0630tr(cls));
        int i2 = wf.h;
        if (i2 == 2) {
            wf.h = 1;
        }
        boolean z = wf.i;
        boolean z2 = wf.k;
        wf.i = this.f;
        wf.k = false;
        try {
            try {
                lqC.c(wf, obj);
                wf.h = i2;
                wf.i = z;
                wf.k = z2;
            } catch (IOException e) {
                throw new Rf(e);
            } catch (AssertionError e2) {
                throw new AssertionError("AssertionError (GSON 2.13.1): " + e2.getMessage(), e2);
            }
        } catch (Throwable th) {
            wf.h = i2;
            wf.i = z;
            wf.k = z2;
            throw th;
        }
    }

    public final void g(Wf wf) {
        Sf sf = Sf.a;
        int i2 = wf.h;
        boolean z = wf.i;
        boolean z2 = wf.k;
        wf.i = this.f;
        wf.k = false;
        if (i2 == 2) {
            wf.h = 1;
        }
        try {
            try {
                AbstractC0568rr.z.getClass();
                Qf.e(wf, sf);
                wf.h = i2;
                wf.i = z;
                wf.k = z2;
            } catch (IOException e) {
                throw new Rf(e);
            } catch (AssertionError e2) {
                throw new AssertionError("AssertionError (GSON 2.13.1): " + e2.getMessage(), e2);
            }
        } catch (Throwable th) {
            wf.h = i2;
            wf.i = z;
            wf.k = z2;
            throw th;
        }
    }

    public final String toString() {
        return "{serializeNulls:false,factories:" + this.e + ",instanceCreators:" + this.c + "}";
    }
}
