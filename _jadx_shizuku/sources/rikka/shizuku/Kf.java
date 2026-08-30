package rikka.shizuku;

import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/* JADX INFO: loaded from: classes.dex */
public class Kf implements Ef, Lj {
    public static final /* synthetic */ AtomicReferenceFieldUpdater a = AtomicReferenceFieldUpdater.newUpdater(Kf.class, Object.class, "_state$volatile");
    public static final /* synthetic */ AtomicReferenceFieldUpdater b = AtomicReferenceFieldUpdater.newUpdater(Kf.class, Object.class, "_parentHandle$volatile");
    private volatile /* synthetic */ Object _parentHandle$volatile;
    private volatile /* synthetic */ Object _state$volatile;

    public Kf(boolean z) {
        this._state$volatile = z ? AbstractC0599sr.l : AbstractC0599sr.k;
    }

    public static U5 D(C0435nh c0435nh) {
        while (c0435nh.i()) {
            C0435nh c0435nhF = c0435nh.f();
            if (c0435nhF == null) {
                AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = C0435nh.b;
                Object obj = atomicReferenceFieldUpdater.get(c0435nh);
                while (true) {
                    c0435nh = (C0435nh) obj;
                    if (!c0435nh.i()) {
                        break;
                    }
                    obj = atomicReferenceFieldUpdater.get(c0435nh);
                }
            } else {
                c0435nh = c0435nhF;
            }
        }
        while (true) {
            c0435nh = c0435nh.h();
            if (!c0435nh.i()) {
                if (c0435nh instanceof U5) {
                    return (U5) c0435nh;
                }
                if (c0435nh instanceof C0037aj) {
                    return null;
                }
            }
        }
    }

    public static String J(Object obj) {
        if (!(obj instanceof Jf)) {
            if (obj instanceof InterfaceC0187ff) {
                return ((InterfaceC0187ff) obj).a() ? "Active" : "New";
            }
            return obj instanceof J6 ? "Cancelled" : "Completed";
        }
        Jf jf = (Jf) obj;
        if (jf.e()) {
            return "Cancelling";
        }
        return Jf.b.get(jf) == 1 ? "Completing" : "Active";
    }

    public final void A(Ef ef) {
        C0099cj c0099cj = C0099cj.a;
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = b;
        if (ef == null) {
            atomicReferenceFieldUpdater.set(this, c0099cj);
            return;
        }
        ef.start();
        T5 t5K = ef.k(this);
        atomicReferenceFieldUpdater.set(this, t5K);
        if (a.get(this) instanceof InterfaceC0187ff) {
            return;
        }
        t5K.b();
        atomicReferenceFieldUpdater.set(this, c0099cj);
    }

    public boolean B() {
        return false;
    }

    public final Object C(Object obj) {
        Object objK;
        do {
            objK = K(a.get(this), obj);
            if (objK == AbstractC0599sr.f) {
                String str = "Job " + this + " is already complete or completing, but is being completed with " + obj;
                J6 j6 = obj instanceof J6 ? (J6) obj : null;
                throw new IllegalStateException(str, j6 != null ? j6.a : null);
            }
        } while (objK == AbstractC0599sr.h);
        return objK;
    }

    public final void E(C0037aj c0037aj, Throwable th) {
        c0037aj.e(new Qg(4), 4);
        K6 k6 = null;
        for (C0435nh c0435nhH = (C0435nh) C0435nh.a.get(c0037aj); !AbstractC0046as.c(c0435nhH, c0037aj); c0435nhH = c0435nhH.h()) {
            if ((c0435nhH instanceof Hf) && ((Hf) c0435nhH).j()) {
                try {
                    ((Hf) c0435nhH).k(th);
                } catch (Throwable th2) {
                    if (k6 != null) {
                        AbstractC0744xh.a(k6, th2);
                    } else {
                        k6 = new K6("Exception in completion handler " + c0435nhH + " for " + this, th2);
                    }
                }
            }
        }
        if (k6 != null) {
            z(k6);
        }
        p(th);
    }

    public final void H(C0091cb c0091cb) {
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater;
        C0037aj c0037aj = new C0037aj();
        Object c0156ef = c0037aj;
        if (!c0091cb.a) {
            c0156ef = new C0156ef(c0037aj);
        }
        do {
            atomicReferenceFieldUpdater = a;
            if (atomicReferenceFieldUpdater.compareAndSet(this, c0091cb, c0156ef)) {
                return;
            }
        } while (atomicReferenceFieldUpdater.get(this) == c0091cb);
    }

    public final void I(Hf hf) {
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater;
        C0037aj c0037aj = new C0037aj();
        hf.getClass();
        C0435nh.b.set(c0037aj, hf);
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater2 = C0435nh.a;
        atomicReferenceFieldUpdater2.set(c0037aj, hf);
        loop0: while (atomicReferenceFieldUpdater2.get(hf) == hf) {
            do {
                if (atomicReferenceFieldUpdater2.compareAndSet(hf, hf, c0037aj)) {
                    c0037aj.g(hf);
                    break loop0;
                }
            } while (atomicReferenceFieldUpdater2.get(hf) == hf);
        }
        C0435nh c0435nhH = hf.h();
        do {
            atomicReferenceFieldUpdater = a;
            if (atomicReferenceFieldUpdater.compareAndSet(this, hf, c0435nhH)) {
                return;
            }
        } while (atomicReferenceFieldUpdater.get(this) == hf);
    }

    public final Object K(Object obj, Object obj2) {
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater;
        if (!(obj instanceof InterfaceC0187ff)) {
            return AbstractC0599sr.f;
        }
        if (((obj instanceof C0091cb) || (obj instanceof Hf)) && !(obj instanceof U5) && !(obj2 instanceof J6)) {
            InterfaceC0187ff interfaceC0187ff = (InterfaceC0187ff) obj;
            Object c0218gf = obj2 instanceof InterfaceC0187ff ? new C0218gf((InterfaceC0187ff) obj2) : obj2;
            do {
                atomicReferenceFieldUpdater = a;
                if (atomicReferenceFieldUpdater.compareAndSet(this, interfaceC0187ff, c0218gf)) {
                    F(obj2);
                    s(interfaceC0187ff, obj2);
                    return obj2;
                }
            } while (atomicReferenceFieldUpdater.get(this) == interfaceC0187ff);
            return AbstractC0599sr.h;
        }
        InterfaceC0187ff interfaceC0187ff2 = (InterfaceC0187ff) obj;
        C0037aj c0037ajX = x(interfaceC0187ff2);
        if (c0037ajX == null) {
            return AbstractC0599sr.h;
        }
        Jf jf = interfaceC0187ff2 instanceof Jf ? (Jf) interfaceC0187ff2 : null;
        if (jf == null) {
            jf = new Jf(c0037ajX, null);
        }
        synchronized (jf) {
            try {
                AtomicIntegerFieldUpdater atomicIntegerFieldUpdater = Jf.b;
                if (atomicIntegerFieldUpdater.get(jf) == 1) {
                    return AbstractC0599sr.f;
                }
                atomicIntegerFieldUpdater.set(jf, 1);
                if (jf != interfaceC0187ff2) {
                    AtomicReferenceFieldUpdater atomicReferenceFieldUpdater2 = a;
                    while (!atomicReferenceFieldUpdater2.compareAndSet(this, interfaceC0187ff2, jf)) {
                        if (atomicReferenceFieldUpdater2.get(this) != interfaceC0187ff2) {
                            return AbstractC0599sr.h;
                        }
                    }
                }
                boolean zE = jf.e();
                J6 j6 = obj2 instanceof J6 ? (J6) obj2 : null;
                if (j6 != null) {
                    jf.b(j6.a);
                }
                Throwable thC = zE ? null : jf.c();
                if (thC != null) {
                    E(c0037ajX, thC);
                }
                U5 u5D = D(c0037ajX);
                if (u5D != null && L(jf, u5D, obj2)) {
                    return AbstractC0599sr.g;
                }
                c0037ajX.e(new Qg(2), 2);
                U5 u5D2 = D(c0037ajX);
                return (u5D2 == null || !L(jf, u5D2, obj2)) ? u(jf, obj2) : AbstractC0599sr.g;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:29:0x0062  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [java.lang.Object, rikka.shizuku.Hf, rikka.shizuku.If, rikka.shizuku.nh] */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v2 */
    /* JADX WARN: Type inference failed for: r4v5, types: [rikka.shizuku.aj, rikka.shizuku.nh] */
    public final boolean L(Jf jf, U5 u5, Object obj) {
        boolean zE;
        do {
            Kf kf = u5.e;
            ?? r1 = new If(this, jf, u5, obj);
            kf.getClass();
            r1.d = kf;
            while (true) {
                AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = a;
                Object obj2 = atomicReferenceFieldUpdater.get(kf);
                boolean z = obj2 instanceof C0091cb;
                C0099cj c0099cj = C0099cj.a;
                boolean z2 = true;
                if (z) {
                    C0091cb c0091cb = (C0091cb) obj2;
                    if (c0091cb.a) {
                        while (true) {
                            if (atomicReferenceFieldUpdater.compareAndSet(kf, obj2, r1)) {
                                if (!z2) {
                                    r1 = c0099cj;
                                }
                            } else if (atomicReferenceFieldUpdater.get(kf) != obj2) {
                            }
                        }
                    } else {
                        kf.H(c0091cb);
                    }
                } else {
                    if (obj2 instanceof InterfaceC0187ff) {
                        InterfaceC0187ff interfaceC0187ff = (InterfaceC0187ff) obj2;
                        ?? D = interfaceC0187ff.d();
                        if (D == 0) {
                            kf.I((Hf) obj2);
                        } else {
                            if (r1.j()) {
                                Jf jf2 = interfaceC0187ff instanceof Jf ? (Jf) interfaceC0187ff : null;
                                if ((jf2 != null ? jf2.c() : null) == null) {
                                    zE = D.e(r1, 5);
                                }
                                r1 = c0099cj;
                            } else {
                                zE = D.e(r1, 1);
                            }
                            if (zE) {
                            }
                        }
                    } else {
                        z2 = false;
                    }
                    if (!z2) {
                        r1 = c0099cj;
                    }
                }
                if (r1 != C0099cj.a) {
                    return true;
                }
                u5 = D(u5);
            }
        } while (u5 != null);
        return false;
    }

    @Override // rikka.shizuku.Ef
    public boolean a() {
        Object obj = a.get(this);
        return (obj instanceof InterfaceC0187ff) && ((InterfaceC0187ff) obj).a();
    }

    @Override // rikka.shizuku.V7
    public final V7 b(V7 v7) {
        return AbstractC0744xh.P(this, v7);
    }

    @Override // rikka.shizuku.Ef
    public final CancellationException c() {
        CancellationException ff;
        Object obj = a.get(this);
        if (!(obj instanceof Jf)) {
            if (obj instanceof InterfaceC0187ff) {
                throw new IllegalStateException(("Job is still new or active: " + this).toString());
            }
            if (!(obj instanceof J6)) {
                return new Ff(getClass().getSimpleName().concat(" has completed normally"), null, this);
            }
            Throwable th = ((J6) obj).a;
            ff = th instanceof CancellationException ? (CancellationException) th : null;
            return ff == null ? new Ff(q(), th, this) : ff;
        }
        Throwable thC = ((Jf) obj).c();
        if (thC == null) {
            throw new IllegalStateException(("Job is still new or active: " + this).toString());
        }
        String strConcat = getClass().getSimpleName().concat(" is cancelling");
        ff = thC instanceof CancellationException ? (CancellationException) thC : null;
        if (ff == null) {
            if (strConcat == null) {
                strConcat = q();
            }
            ff = new Ff(strConcat, thC, this);
        }
        return ff;
    }

    @Override // rikka.shizuku.V7
    public final T7 g(U7 u7) {
        if (AbstractC0046as.c(Fe.a, u7)) {
            return this;
        }
        return null;
    }

    @Override // rikka.shizuku.T7
    public final U7 getKey() {
        return Fe.a;
    }

    @Override // rikka.shizuku.Ef
    public final void i(CancellationException cancellationException) {
        if (cancellationException == null) {
            cancellationException = new Ff(q(), null, this);
        }
        o(cancellationException);
    }

    @Override // rikka.shizuku.V7
    public final V7 j(U7 u7) {
        return AbstractC0046as.c(Fe.a, u7) ? C0152eb.a : this;
    }

    @Override // rikka.shizuku.Ef
    public final T5 k(Kf kf) {
        U5 u5 = new U5(kf);
        u5.d = this;
        loop0: while (true) {
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = a;
            Object obj = atomicReferenceFieldUpdater.get(this);
            if (obj instanceof C0091cb) {
                C0091cb c0091cb = (C0091cb) obj;
                if (c0091cb.a) {
                    while (!atomicReferenceFieldUpdater.compareAndSet(this, obj, u5)) {
                        if (atomicReferenceFieldUpdater.get(this) != obj) {
                        }
                    }
                    break loop0;
                }
                H(c0091cb);
            } else {
                boolean z = obj instanceof InterfaceC0187ff;
                C0099cj c0099cj = C0099cj.a;
                Throwable thC = null;
                if (!z) {
                    Object obj2 = atomicReferenceFieldUpdater.get(this);
                    J6 j6 = obj2 instanceof J6 ? (J6) obj2 : null;
                    u5.k(j6 != null ? j6.a : null);
                    return c0099cj;
                }
                C0037aj c0037ajD = ((InterfaceC0187ff) obj).d();
                if (c0037ajD != null) {
                    if (c0037ajD.e(u5, 7)) {
                        break;
                    }
                    boolean zE = c0037ajD.e(u5, 3);
                    Object obj3 = atomicReferenceFieldUpdater.get(this);
                    if (obj3 instanceof Jf) {
                        thC = ((Jf) obj3).c();
                    } else {
                        J6 j7 = obj3 instanceof J6 ? (J6) obj3 : null;
                        if (j7 != null) {
                            thC = j7.a;
                        }
                    }
                    u5.k(thC);
                    if (zE) {
                        break;
                    }
                    return c0099cj;
                }
                I((Hf) obj);
            }
        }
        return u5;
    }

    @Override // rikka.shizuku.V7
    public final Object l(Object obj, InterfaceC0585sd interfaceC0585sd) {
        return interfaceC0585sd.f(obj, this);
    }

    public void n(Object obj) {
        m(obj);
    }

    /* JADX WARN: Code duplicated, block: B:100:0x0047 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:101:? A[LOOP:2: B:59:0x00bf->B:101:?, LOOP_END, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:18:0x0040 A[PHI: r0
  0x0040: PHI (r0v1 java.lang.Object) = (r0v0 java.lang.Object), (r0v13 java.lang.Object) binds: [B:3:0x0007, B:16:0x003c] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:20:0x0045  */
    /* JADX WARN: Code duplicated, block: B:26:0x0062  */
    /* JADX WARN: Code duplicated, block: B:27:0x0064  */
    /* JADX WARN: Code duplicated, block: B:29:0x0067 A[Catch: all -> 0x007f, TRY_LEAVE, TryCatch #0 {all -> 0x007f, blocks: (B:24:0x0052, B:29:0x0067, B:32:0x006d, B:40:0x0087, B:36:0x007a, B:39:0x0081), top: B:84:0x0052 }] */
    /* JADX WARN: Code duplicated, block: B:32:0x006d A[Catch: all -> 0x007f, TRY_ENTER, TryCatch #0 {all -> 0x007f, blocks: (B:24:0x0052, B:29:0x0067, B:32:0x006d, B:40:0x0087, B:36:0x007a, B:39:0x0081), top: B:84:0x0052 }] */
    /* JADX WARN: Code duplicated, block: B:35:0x0078 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:36:0x007a A[Catch: all -> 0x007f, TryCatch #0 {all -> 0x007f, blocks: (B:24:0x0052, B:29:0x0067, B:32:0x006d, B:40:0x0087, B:36:0x007a, B:39:0x0081), top: B:84:0x0052 }] */
    /* JADX WARN: Code duplicated, block: B:42:0x0090  */
    /* JADX WARN: Code duplicated, block: B:45:0x0094  */
    /* JADX WARN: Code duplicated, block: B:49:0x00a0  */
    /* JADX WARN: Code duplicated, block: B:51:0x00a4 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:52:0x00a6  */
    /* JADX WARN: Code duplicated, block: B:62:0x00cd  */
    /* JADX WARN: Code duplicated, block: B:67:0x00e2  */
    /* JADX WARN: Code duplicated, block: B:81:0x0112 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:82:0x0113  */
    /* JADX WARN: Code duplicated, block: B:84:0x0052 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:89:0x0100 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:90:0x00d5 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:91:0x0051 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:92:0x00ba A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:93:0x00c7 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:94:0x00e8 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:95:0x00e6 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:96:0x00b3 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:98:0x0047 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:99:0x0047 A[SYNTHETIC] */
    /* JADX WARN: Instruction removed from duplicated block: B:20:0x0045, please report this as an issue */
    public final boolean o(Object obj) {
        Throwable thT;
        Object obj2;
        Jf jf;
        boolean z;
        Throwable thC;
        C0589sh c0589sh;
        InterfaceC0187ff interfaceC0187ff;
        C0037aj c0037ajX;
        Jf jf2;
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater;
        Object objK;
        Object objK2 = AbstractC0599sr.f;
        if (w()) {
            do {
                Object obj3 = a.get(this);
                if (obj3 instanceof InterfaceC0187ff) {
                    if (obj3 instanceof Jf) {
                        Jf jf3 = (Jf) obj3;
                        jf3.getClass();
                        if (Jf.b.get(jf3) == 1) {
                        }
                    }
                    objK2 = K(obj3, new J6(t(obj)));
                }
                objK2 = AbstractC0599sr.f;
                break;
            } while (objK2 == AbstractC0599sr.h);
            if (objK2 != AbstractC0599sr.g) {
                if (objK2 == AbstractC0599sr.f) {
                    thT = null;
                    loop1: while (true) {
                        obj2 = a.get(this);
                        if (obj2 instanceof Jf) {
                            synchronized (obj2) {
                                try {
                                    jf = (Jf) obj2;
                                    jf.getClass();
                                    if (Jf.d.get(jf) == AbstractC0599sr.j) {
                                        z = true;
                                    } else {
                                        z = false;
                                    }
                                    if (z) {
                                        c0589sh = AbstractC0599sr.i;
                                    } else {
                                        boolean zE = ((Jf) obj2).e();
                                        if (obj == null || !zE) {
                                            if (thT == null) {
                                                thT = t(obj);
                                            }
                                            ((Jf) obj2).b(thT);
                                        }
                                        thC = zE ? null : ((Jf) obj2).c();
                                        if (thC != null) {
                                            E(((Jf) obj2).a, thC);
                                        }
                                        c0589sh = AbstractC0599sr.f;
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        } else if (obj2 instanceof InterfaceC0187ff) {
                            if (thT == null) {
                                thT = t(obj);
                            }
                            interfaceC0187ff = (InterfaceC0187ff) obj2;
                            if (interfaceC0187ff.a()) {
                                c0037ajX = x(interfaceC0187ff);
                                if (c0037ajX == null) {
                                    continue;
                                } else {
                                    jf2 = new Jf(c0037ajX, thT);
                                    while (true) {
                                        atomicReferenceFieldUpdater = a;
                                        if (atomicReferenceFieldUpdater.compareAndSet(this, interfaceC0187ff, jf2)) {
                                            E(c0037ajX, thT);
                                            c0589sh = AbstractC0599sr.f;
                                        } else if (atomicReferenceFieldUpdater.get(this) != interfaceC0187ff) {
                                        }
                                    }
                                }
                            } else {
                                objK = K(obj2, new J6(thT));
                                if (objK != AbstractC0599sr.f) {
                                    throw new IllegalStateException(("Cannot happen in " + obj2).toString());
                                }
                                if (objK != AbstractC0599sr.h) {
                                    objK2 = objK;
                                    break;
                                }
                            }
                        } else {
                            c0589sh = AbstractC0599sr.i;
                        }
                        objK2 = c0589sh;
                        break;
                    }
                }
                if (objK2 != AbstractC0599sr.f && objK2 != AbstractC0599sr.g) {
                    if (objK2 == AbstractC0599sr.i) {
                        return false;
                    }
                    m(objK2);
                    return true;
                }
            }
        } else {
            if (objK2 == AbstractC0599sr.f) {
                thT = null;
                loop1: while (true) {
                    obj2 = a.get(this);
                    if (obj2 instanceof Jf) {
                        synchronized (obj2) {
                            jf = (Jf) obj2;
                            jf.getClass();
                            if (Jf.d.get(jf) == AbstractC0599sr.j) {
                                z = true;
                            } else {
                                z = false;
                            }
                            if (z) {
                                c0589sh = AbstractC0599sr.i;
                            } else {
                                boolean zE2 = ((Jf) obj2).e();
                                if (obj == null) {
                                    if (thT == null) {
                                        thT = t(obj);
                                    }
                                    ((Jf) obj2).b(thT);
                                } else {
                                    if (thT == null) {
                                        thT = t(obj);
                                    }
                                    ((Jf) obj2).b(thT);
                                }
                                if (zE2) {
                                }
                                if (thC != null) {
                                    E(((Jf) obj2).a, thC);
                                }
                                c0589sh = AbstractC0599sr.f;
                            }
                        }
                    } else if (obj2 instanceof InterfaceC0187ff) {
                        if (thT == null) {
                            thT = t(obj);
                        }
                        interfaceC0187ff = (InterfaceC0187ff) obj2;
                        if (interfaceC0187ff.a()) {
                            c0037ajX = x(interfaceC0187ff);
                            if (c0037ajX == null) {
                                continue;
                            } else {
                                jf2 = new Jf(c0037ajX, thT);
                                while (true) {
                                    atomicReferenceFieldUpdater = a;
                                    if (atomicReferenceFieldUpdater.compareAndSet(this, interfaceC0187ff, jf2)) {
                                        E(c0037ajX, thT);
                                        c0589sh = AbstractC0599sr.f;
                                    } else if (atomicReferenceFieldUpdater.get(this) != interfaceC0187ff) {
                                    }
                                }
                            }
                        } else {
                            objK = K(obj2, new J6(thT));
                            if (objK != AbstractC0599sr.f) {
                                throw new IllegalStateException(("Cannot happen in " + obj2).toString());
                            }
                            if (objK != AbstractC0599sr.h) {
                                objK2 = objK;
                                break;
                            }
                        }
                    } else {
                        c0589sh = AbstractC0599sr.i;
                    }
                    objK2 = c0589sh;
                    break;
                }
            }
            if (objK2 != AbstractC0599sr.f) {
                if (objK2 == AbstractC0599sr.i) {
                    return false;
                }
                m(objK2);
                return true;
            }
        }
        return true;
    }

    public final boolean p(Throwable th) {
        if (B()) {
            return true;
        }
        boolean z = th instanceof CancellationException;
        T5 t5 = (T5) b.get(this);
        if (t5 == null || t5 == C0099cj.a) {
            return z;
        }
        return t5.c(th) || z;
    }

    public String q() {
        return "Job was cancelled";
    }

    public boolean r(Throwable th) {
        if (th instanceof CancellationException) {
            return true;
        }
        return o(th) && v();
    }

    public final void s(InterfaceC0187ff interfaceC0187ff, Object obj) {
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = b;
        T5 t5 = (T5) atomicReferenceFieldUpdater.get(this);
        if (t5 != null) {
            t5.b();
            atomicReferenceFieldUpdater.set(this, C0099cj.a);
        }
        K6 k6 = null;
        J6 j6 = obj instanceof J6 ? (J6) obj : null;
        Throwable th = j6 != null ? j6.a : null;
        if (interfaceC0187ff instanceof Hf) {
            try {
                ((Hf) interfaceC0187ff).k(th);
                return;
            } catch (Throwable th2) {
                z(new K6("Exception in completion handler " + interfaceC0187ff + " for " + this, th2));
                return;
            }
        }
        C0037aj c0037ajD = interfaceC0187ff.d();
        if (c0037ajD != null) {
            c0037ajD.e(new Qg(1), 1);
            for (C0435nh c0435nhH = (C0435nh) C0435nh.a.get(c0037ajD); !AbstractC0046as.c(c0435nhH, c0037ajD); c0435nhH = c0435nhH.h()) {
                if (c0435nhH instanceof Hf) {
                    try {
                        ((Hf) c0435nhH).k(th);
                    } catch (Throwable th3) {
                        if (k6 != null) {
                            AbstractC0744xh.a(k6, th3);
                        } else {
                            k6 = new K6("Exception in completion handler " + c0435nhH + " for " + this, th3);
                        }
                    }
                }
            }
            if (k6 != null) {
                z(k6);
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:6:0x0014  */
    @Override // rikka.shizuku.Ef
    public final boolean start() {
        byte b2;
        do {
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = a;
            Object obj = atomicReferenceFieldUpdater.get(this);
            b2 = -1;
            if (obj instanceof C0091cb) {
                if (((C0091cb) obj).a) {
                    b2 = 0;
                } else {
                    C0091cb c0091cb = AbstractC0599sr.l;
                    do {
                        if (atomicReferenceFieldUpdater.compareAndSet(this, obj, c0091cb)) {
                            b2 = 1;
                            break;
                        }
                    } while (atomicReferenceFieldUpdater.get(this) == obj);
                }
            } else if (obj instanceof C0156ef) {
                C0037aj c0037aj = ((C0156ef) obj).a;
                do {
                    if (atomicReferenceFieldUpdater.compareAndSet(this, obj, c0037aj)) {
                        b2 = 1;
                        break;
                    }
                } while (atomicReferenceFieldUpdater.get(this) == obj);
            } else {
                b2 = 0;
            }
            if (b2 == 0) {
                return false;
            }
        } while (b2 != 1);
        return true;
    }

    public final Throwable t(Object obj) {
        Throwable thC;
        if (obj == null ? true : obj instanceof Throwable) {
            Throwable th = (Throwable) obj;
            return th == null ? new Ff(q(), null, this) : th;
        }
        Kf kf = (Kf) ((Lj) obj);
        kf.getClass();
        Object obj2 = a.get(kf);
        if (obj2 instanceof Jf) {
            thC = ((Jf) obj2).c();
        } else if (obj2 instanceof J6) {
            thC = ((J6) obj2).a;
        } else {
            if (obj2 instanceof InterfaceC0187ff) {
                throw new IllegalStateException(("Cannot be cancelling child in this state: " + obj2).toString());
            }
            thC = null;
        }
        CancellationException cancellationException = thC instanceof CancellationException ? (CancellationException) thC : null;
        return cancellationException == null ? new Ff("Parent job is ".concat(J(obj2)), thC, kf) : cancellationException;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName() + '{' + J(a.get(this)) + '}');
        sb.append('@');
        sb.append(X8.B(this));
        return sb.toString();
    }

    public final Object u(Jf jf, Object obj) {
        Object obj2 = null;
        Throwable ff = null;
        J6 j6 = obj instanceof J6 ? (J6) obj : null;
        Throwable th = j6 != null ? j6.a : null;
        synchronized (jf) {
            jf.e();
            ArrayList arrayListF = jf.f(th);
            if (!arrayListF.isEmpty()) {
                int size = arrayListF.size();
                int i = 0;
                while (i < size) {
                    Object obj3 = arrayListF.get(i);
                    i++;
                    if (!(((Throwable) obj3) instanceof CancellationException)) {
                        obj2 = obj3;
                        break;
                    }
                }
                ff = (Throwable) obj2;
                if (ff == null) {
                    ff = (Throwable) arrayListF.get(0);
                }
            } else if (jf.e()) {
                ff = new Ff(q(), null, this);
            }
            if (ff != null && arrayListF.size() > 1) {
                Set setNewSetFromMap = Collections.newSetFromMap(new IdentityHashMap(arrayListF.size()));
                int size2 = arrayListF.size();
                int i2 = 0;
                while (i2 < size2) {
                    Object obj4 = arrayListF.get(i2);
                    i2++;
                    Throwable th2 = (Throwable) obj4;
                    if (th2 != ff && th2 != ff && !(th2 instanceof CancellationException) && setNewSetFromMap.add(th2)) {
                        AbstractC0744xh.a(ff, th2);
                    }
                }
            }
        }
        if (ff != null && ff != th) {
            obj = new J6(ff);
        }
        if (ff != null && (p(ff) || y(ff))) {
            J6 j7 = (J6) obj;
            j7.getClass();
            J6.b.compareAndSet(j7, 0, 1);
        }
        F(obj);
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = a;
        Object c0218gf = obj instanceof InterfaceC0187ff ? new C0218gf((InterfaceC0187ff) obj) : obj;
        while (!atomicReferenceFieldUpdater.compareAndSet(this, jf, c0218gf) && atomicReferenceFieldUpdater.get(this) == jf) {
        }
        s(jf, obj);
        return obj;
    }

    public boolean v() {
        return true;
    }

    public boolean w() {
        return false;
    }

    public final C0037aj x(InterfaceC0187ff interfaceC0187ff) {
        C0037aj c0037ajD = interfaceC0187ff.d();
        if (c0037ajD != null) {
            return c0037ajD;
        }
        if (interfaceC0187ff instanceof C0091cb) {
            return new C0037aj();
        }
        if (interfaceC0187ff instanceof Hf) {
            I((Hf) interfaceC0187ff);
            return null;
        }
        throw new IllegalStateException(("State should have list: " + interfaceC0187ff).toString());
    }

    public boolean y(Throwable th) {
        return false;
    }

    public void G() {
    }

    public void F(Object obj) {
    }

    public void m(Object obj) {
    }

    public void z(K6 k6) {
        throw k6;
    }
}
