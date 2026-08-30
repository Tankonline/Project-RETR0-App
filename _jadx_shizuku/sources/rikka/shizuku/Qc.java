package rikka.shizuku;

import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentContainerView;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import moe.shizuku.privileged.api.R;
import rikka.html.text.style.BulletSpan;

/* JADX INFO: loaded from: classes.dex */
public final class Qc {
    public final Kc A;
    public final Fe B;
    public P0 C;
    public P0 D;
    public P0 E;
    public ArrayDeque F;
    public boolean G;
    public boolean H;
    public boolean I;
    public boolean J;
    public boolean K;
    public ArrayList L;
    public ArrayList M;
    public ArrayList N;
    public Tc O;
    public final RunnableC0607t4 P;
    public boolean b;
    public ArrayList e;
    public C0622tj g;
    public final ArrayList n;
    public final R2 o;
    public final CopyOnWriteArrayList p;
    public final Gc q;
    public final Gc r;
    public final Gc s;
    public final Gc t;
    public final Jc u;
    public int v;
    public Cc w;
    public AbstractC0046as x;
    public Ac y;
    public Ac z;
    public final ArrayList a = new ArrayList();
    public final Xn c = new Xn(4);
    public ArrayList d = new ArrayList();
    public final Fc f = new Fc(this);
    public L4 h = null;
    public boolean i = false;
    public final Ic j = new Ic(this);
    public final AtomicInteger k = new AtomicInteger();
    public final Map l = Collections.synchronizedMap(new HashMap());
    public final Map m = Collections.synchronizedMap(new HashMap());

    /* JADX WARN: Type inference failed for: r0v16, types: [rikka.shizuku.Gc] */
    /* JADX WARN: Type inference failed for: r0v17, types: [rikka.shizuku.Gc] */
    /* JADX WARN: Type inference failed for: r0v18, types: [rikka.shizuku.Gc] */
    /* JADX WARN: Type inference failed for: r0v19, types: [rikka.shizuku.Gc] */
    public Qc() {
        Collections.synchronizedMap(new HashMap());
        this.n = new ArrayList();
        this.o = new R2(this);
        this.p = new CopyOnWriteArrayList();
        final int i = 0;
        this.q = new InterfaceC0734x7(this) { // from class: rikka.shizuku.Gc
            public final /* synthetic */ Qc b;

            {
                this.b = this;
            }

            @Override // rikka.shizuku.InterfaceC0734x7
            public final void a(Object obj) {
                switch (i) {
                    case 0:
                        Qc qc = this.b;
                        if (qc.L()) {
                            qc.i(false);
                        }
                        break;
                    case 1:
                        Integer num = (Integer) obj;
                        Qc qc2 = this.b;
                        if (qc2.L() && num.intValue() == 80) {
                            qc2.m(false);
                            break;
                        }
                        break;
                    case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                        Ni ni = (Ni) obj;
                        Qc qc3 = this.b;
                        if (qc3.L()) {
                            boolean z = ni.a;
                            qc3.n(false);
                        }
                        break;
                    default:
                        Uj uj = (Uj) obj;
                        Qc qc4 = this.b;
                        if (qc4.L()) {
                            boolean z2 = uj.a;
                            qc4.s(false);
                        }
                        break;
                }
            }
        };
        final int i2 = 1;
        this.r = new InterfaceC0734x7(this) { // from class: rikka.shizuku.Gc
            public final /* synthetic */ Qc b;

            {
                this.b = this;
            }

            @Override // rikka.shizuku.InterfaceC0734x7
            public final void a(Object obj) {
                switch (i2) {
                    case 0:
                        Qc qc = this.b;
                        if (qc.L()) {
                            qc.i(false);
                        }
                        break;
                    case 1:
                        Integer num = (Integer) obj;
                        Qc qc2 = this.b;
                        if (qc2.L() && num.intValue() == 80) {
                            qc2.m(false);
                            break;
                        }
                        break;
                    case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                        Ni ni = (Ni) obj;
                        Qc qc3 = this.b;
                        if (qc3.L()) {
                            boolean z = ni.a;
                            qc3.n(false);
                        }
                        break;
                    default:
                        Uj uj = (Uj) obj;
                        Qc qc4 = this.b;
                        if (qc4.L()) {
                            boolean z2 = uj.a;
                            qc4.s(false);
                        }
                        break;
                }
            }
        };
        final int i3 = 2;
        this.s = new InterfaceC0734x7(this) { // from class: rikka.shizuku.Gc
            public final /* synthetic */ Qc b;

            {
                this.b = this;
            }

            @Override // rikka.shizuku.InterfaceC0734x7
            public final void a(Object obj) {
                switch (i3) {
                    case 0:
                        Qc qc = this.b;
                        if (qc.L()) {
                            qc.i(false);
                        }
                        break;
                    case 1:
                        Integer num = (Integer) obj;
                        Qc qc2 = this.b;
                        if (qc2.L() && num.intValue() == 80) {
                            qc2.m(false);
                            break;
                        }
                        break;
                    case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                        Ni ni = (Ni) obj;
                        Qc qc3 = this.b;
                        if (qc3.L()) {
                            boolean z = ni.a;
                            qc3.n(false);
                        }
                        break;
                    default:
                        Uj uj = (Uj) obj;
                        Qc qc4 = this.b;
                        if (qc4.L()) {
                            boolean z2 = uj.a;
                            qc4.s(false);
                        }
                        break;
                }
            }
        };
        final int i4 = 3;
        this.t = new InterfaceC0734x7(this) { // from class: rikka.shizuku.Gc
            public final /* synthetic */ Qc b;

            {
                this.b = this;
            }

            @Override // rikka.shizuku.InterfaceC0734x7
            public final void a(Object obj) {
                switch (i4) {
                    case 0:
                        Qc qc = this.b;
                        if (qc.L()) {
                            qc.i(false);
                        }
                        break;
                    case 1:
                        Integer num = (Integer) obj;
                        Qc qc2 = this.b;
                        if (qc2.L() && num.intValue() == 80) {
                            qc2.m(false);
                            break;
                        }
                        break;
                    case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                        Ni ni = (Ni) obj;
                        Qc qc3 = this.b;
                        if (qc3.L()) {
                            boolean z = ni.a;
                            qc3.n(false);
                        }
                        break;
                    default:
                        Uj uj = (Uj) obj;
                        Qc qc4 = this.b;
                        if (qc4.L()) {
                            boolean z2 = uj.a;
                            qc4.s(false);
                        }
                        break;
                }
            }
        };
        this.u = new Jc(this);
        this.v = -1;
        this.A = new Kc(this);
        this.B = new Fe();
        this.F = new ArrayDeque();
        this.P = new RunnableC0607t4(8, this);
    }

    public static HashSet E(L4 l4) {
        HashSet hashSet = new HashSet();
        for (int i = 0; i < l4.a.size(); i++) {
            Ac ac = ((C0062bd) l4.a.get(i)).b;
            if (ac != null && l4.g) {
                hashSet.add(ac);
            }
        }
        return hashSet;
    }

    public static boolean J(int i) {
        return Log.isLoggable("FragmentManager", i);
    }

    public static boolean K(Ac ac) {
        ac.getClass();
        ArrayList arrayListL = ac.v.c.l();
        int size = arrayListL.size();
        boolean zK = false;
        int i = 0;
        while (i < size) {
            Object obj = arrayListL.get(i);
            i++;
            Ac ac2 = (Ac) obj;
            if (ac2 != null) {
                zK = K(ac2);
            }
            if (zK) {
                return true;
            }
        }
        return false;
    }

    public static boolean M(Ac ac) {
        if (ac == null) {
            return true;
        }
        if (ac.D) {
            return ac.t == null || M(ac.w);
        }
        return false;
    }

    public static boolean N(Ac ac) {
        if (ac == null) {
            return true;
        }
        Qc qc = ac.t;
        return ac.equals(qc.z) && N(qc.y);
    }

    public static void d0(Ac ac) {
        if (J(2)) {
            Log.v("FragmentManager", "show: " + ac);
        }
        if (ac.A) {
            ac.A = false;
            ac.K = !ac.K;
        }
    }

    /* JADX WARN: Code duplicated, block: B:113:0x021d A[PHI: r14
  0x021d: PHI (r14v15 int) = (r14v14 int), (r14v16 int) binds: [B:106:0x020d, B:111:0x0219] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:66:0x016e  */
    public final void A(ArrayList arrayList, ArrayList arrayList2, int i, int i2) {
        Object objPrevious;
        boolean z;
        int i3;
        boolean z2;
        int i4;
        int i5;
        int i6;
        int i7 = i;
        boolean z3 = ((L4) arrayList.get(i7)).p;
        ArrayList arrayList3 = this.N;
        if (arrayList3 == null) {
            this.N = new ArrayList();
        } else {
            arrayList3.clear();
        }
        ArrayList arrayList4 = this.N;
        Xn xn = this.c;
        arrayList4.addAll(xn.m());
        Ac ac = this.z;
        int i8 = i7;
        boolean z4 = false;
        while (true) {
            int i9 = 1;
            if (i8 >= i2) {
                boolean z5 = z3;
                boolean z6 = z4;
                this.N.clear();
                if (!z5 && this.v >= 1) {
                    for (int i10 = i7; i10 < i2; i10++) {
                        ArrayList arrayList5 = ((L4) arrayList.get(i10)).a;
                        int size = arrayList5.size();
                        int i11 = 0;
                        while (i11 < size) {
                            Object obj = arrayList5.get(i11);
                            i11++;
                            Ac ac2 = ((C0062bd) obj).b;
                            if (ac2 != null && ac2.t != null) {
                                xn.q(g(ac2));
                            }
                        }
                    }
                }
                int i12 = i7;
                while (i12 < i2) {
                    L4 l4 = (L4) arrayList.get(i12);
                    if (((Boolean) arrayList2.get(i12)).booleanValue()) {
                        l4.c(-1);
                        ArrayList arrayList6 = l4.a;
                        boolean z7 = true;
                        for (int size2 = arrayList6.size() - 1; size2 >= 0; size2--) {
                            C0062bd c0062bd = (C0062bd) arrayList6.get(size2);
                            Ac ac3 = c0062bd.b;
                            if (ac3 != null) {
                                if (ac3.J != null) {
                                    ac3.f().a = z7;
                                }
                                int i13 = l4.f;
                                int i14 = 8194;
                                int i15 = 4097;
                                if (i13 != 4097) {
                                    if (i13 != 8194) {
                                        i14 = 4100;
                                        i15 = 8197;
                                        if (i13 != 8197) {
                                            if (i13 == 4099) {
                                                i14 = 4099;
                                            } else if (i13 != 4100) {
                                                i14 = 0;
                                            } else {
                                                i14 = i15;
                                            }
                                        }
                                    } else {
                                        i14 = i15;
                                    }
                                }
                                if (ac3.J != null || i14 != 0) {
                                    ac3.f();
                                    ac3.J.f = i14;
                                }
                                ac3.f();
                                ac3.J.getClass();
                            }
                            int i16 = c0062bd.a;
                            Qc qc = l4.r;
                            switch (i16) {
                                case 1:
                                    ac3.J(c0062bd.d, c0062bd.e, c0062bd.f, c0062bd.g);
                                    z7 = true;
                                    qc.Z(ac3, true);
                                    qc.U(ac3);
                                    break;
                                case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                                default:
                                    throw new IllegalArgumentException("Unknown cmd: " + c0062bd.a);
                                case 3:
                                    ac3.J(c0062bd.d, c0062bd.e, c0062bd.f, c0062bd.g);
                                    qc.a(ac3);
                                    z7 = true;
                                    break;
                                case 4:
                                    ac3.J(c0062bd.d, c0062bd.e, c0062bd.f, c0062bd.g);
                                    qc.getClass();
                                    d0(ac3);
                                    z7 = true;
                                    break;
                                case BulletSpan.STANDARD_BULLET_RADIUS /* 5 */:
                                    ac3.J(c0062bd.d, c0062bd.e, c0062bd.f, c0062bd.g);
                                    qc.Z(ac3, true);
                                    qc.I(ac3);
                                    z7 = true;
                                    break;
                                case 6:
                                    ac3.J(c0062bd.d, c0062bd.e, c0062bd.f, c0062bd.g);
                                    qc.c(ac3);
                                    z7 = true;
                                    break;
                                case 7:
                                    ac3.J(c0062bd.d, c0062bd.e, c0062bd.f, c0062bd.g);
                                    qc.Z(ac3, true);
                                    qc.h(ac3);
                                    z7 = true;
                                    break;
                                case 8:
                                    qc.b0(null);
                                    z7 = true;
                                    break;
                                case 9:
                                    qc.b0(ac3);
                                    z7 = true;
                                    break;
                                case 10:
                                    qc.a0(ac3, c0062bd.h);
                                    z7 = true;
                                    break;
                            }
                        }
                    } else {
                        l4.c(1);
                        ArrayList arrayList7 = l4.a;
                        int size3 = arrayList7.size();
                        int i17 = 0;
                        while (i17 < size3) {
                            C0062bd c0062bd2 = (C0062bd) arrayList7.get(i17);
                            Ac ac4 = c0062bd2.b;
                            if (ac4 != null) {
                                if (ac4.J != null) {
                                    ac4.f().a = false;
                                }
                                int i18 = l4.f;
                                if (ac4.J != null || i18 != 0) {
                                    ac4.f();
                                    ac4.J.f = i18;
                                }
                                ac4.f();
                                ac4.J.getClass();
                            }
                            int i19 = c0062bd2.a;
                            Qc qc2 = l4.r;
                            switch (i19) {
                                case 1:
                                    ac4.J(c0062bd2.d, c0062bd2.e, c0062bd2.f, c0062bd2.g);
                                    qc2.Z(ac4, false);
                                    qc2.a(ac4);
                                    i17++;
                                    i12 = i12;
                                    break;
                                case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                                default:
                                    throw new IllegalArgumentException("Unknown cmd: " + c0062bd2.a);
                                case 3:
                                    ac4.J(c0062bd2.d, c0062bd2.e, c0062bd2.f, c0062bd2.g);
                                    qc2.U(ac4);
                                    i17++;
                                    i12 = i12;
                                    break;
                                case 4:
                                    ac4.J(c0062bd2.d, c0062bd2.e, c0062bd2.f, c0062bd2.g);
                                    qc2.I(ac4);
                                    i17++;
                                    i12 = i12;
                                    break;
                                case BulletSpan.STANDARD_BULLET_RADIUS /* 5 */:
                                    ac4.J(c0062bd2.d, c0062bd2.e, c0062bd2.f, c0062bd2.g);
                                    qc2.Z(ac4, false);
                                    d0(ac4);
                                    i17++;
                                    i12 = i12;
                                    break;
                                case 6:
                                    ac4.J(c0062bd2.d, c0062bd2.e, c0062bd2.f, c0062bd2.g);
                                    qc2.h(ac4);
                                    i17++;
                                    i12 = i12;
                                    break;
                                case 7:
                                    ac4.J(c0062bd2.d, c0062bd2.e, c0062bd2.f, c0062bd2.g);
                                    qc2.Z(ac4, false);
                                    qc2.c(ac4);
                                    i17++;
                                    i12 = i12;
                                    break;
                                case 8:
                                    qc2.b0(ac4);
                                    i17++;
                                    i12 = i12;
                                    break;
                                case 9:
                                    qc2.b0(null);
                                    i17++;
                                    i12 = i12;
                                    break;
                                case 10:
                                    qc2.a0(ac4, c0062bd2.i);
                                    i17++;
                                    i12 = i12;
                                    break;
                            }
                        }
                    }
                    i12++;
                }
                boolean zBooleanValue = ((Boolean) arrayList2.get(i2 - 1)).booleanValue();
                ArrayList arrayList8 = this.n;
                if (z6 && !arrayList8.isEmpty()) {
                    LinkedHashSet linkedHashSet = new LinkedHashSet();
                    int size4 = arrayList.size();
                    int i20 = 0;
                    while (i20 < size4) {
                        Object obj2 = arrayList.get(i20);
                        i20++;
                        linkedHashSet.addAll(E((L4) obj2));
                    }
                    if (this.h == null) {
                        int size5 = arrayList8.size();
                        int i21 = 0;
                        while (i21 < size5) {
                            Object obj3 = arrayList8.get(i21);
                            i21++;
                            if (obj3 != null) {
                                throw new ClassCastException();
                            }
                            Iterator it = linkedHashSet.iterator();
                            if (it.hasNext()) {
                                throw null;
                            }
                        }
                        int size6 = arrayList8.size();
                        int i22 = 0;
                        while (i22 < size6) {
                            Object obj4 = arrayList8.get(i22);
                            i22++;
                            if (obj4 != null) {
                                throw new ClassCastException();
                            }
                            Iterator it2 = linkedHashSet.iterator();
                            if (it2.hasNext()) {
                                throw null;
                            }
                        }
                    }
                }
                for (int i23 = i7; i23 < i2; i23++) {
                    L4 l5 = (L4) arrayList.get(i23);
                    if (zBooleanValue) {
                        for (int size7 = l5.a.size() - 1; size7 >= 0; size7--) {
                            Ac ac5 = ((C0062bd) l5.a.get(size7)).b;
                            if (ac5 != null) {
                                g(ac5).k();
                            }
                        }
                    } else {
                        ArrayList arrayList9 = l5.a;
                        int size8 = arrayList9.size();
                        int i24 = 0;
                        while (i24 < size8) {
                            Object obj5 = arrayList9.get(i24);
                            i24++;
                            Ac ac6 = ((C0062bd) obj5).b;
                            if (ac6 != null) {
                                g(ac6).k();
                            }
                        }
                    }
                }
                P(this.v, true);
                for (E9 e9 : f(arrayList, i7, i2)) {
                    e9.e = zBooleanValue;
                    synchronized (e9.b) {
                        try {
                            e9.k();
                            ArrayList arrayList10 = e9.b;
                            ListIterator listIterator = arrayList10.listIterator(arrayList10.size());
                            while (true) {
                                if (listIterator.hasPrevious()) {
                                    objPrevious = listIterator.previous();
                                    C0751xo c0751xo = (C0751xo) objPrevious;
                                    View view = c0751xo.c.G;
                                    char c = 4;
                                    if (view.getAlpha() != 0.0f || view.getVisibility() != 0) {
                                        int visibility = view.getVisibility();
                                        if (visibility == 0) {
                                            c = 2;
                                        } else if (visibility != 4) {
                                            if (visibility != 8) {
                                                throw new IllegalArgumentException("Unknown visibility " + visibility);
                                            }
                                            c = 3;
                                        }
                                    }
                                    if (c0751xo.a != 2 || c == 2) {
                                    }
                                } else {
                                    objPrevious = null;
                                }
                            }
                            e9.f = false;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    e9.e();
                }
                while (i7 < i2) {
                    L4 l6 = (L4) arrayList.get(i7);
                    if (((Boolean) arrayList2.get(i7)).booleanValue() && l6.t >= 0) {
                        l6.t = -1;
                    }
                    if (l6.q != null) {
                        for (int i25 = 0; i25 < l6.q.size(); i25++) {
                            ((Runnable) l6.q.get(i25)).run();
                        }
                        l6.q = null;
                    }
                    i7++;
                }
                if (!z6 || arrayList8.size() <= 0) {
                    return;
                }
                arrayList8.get(0).getClass();
                throw new ClassCastException();
            }
            L4 l7 = (L4) arrayList.get(i8);
            if (((Boolean) arrayList2.get(i8)).booleanValue()) {
                z = z3;
                i3 = i8;
                z2 = z4;
                int i26 = 1;
                ArrayList arrayList11 = this.N;
                ArrayList arrayList12 = l7.a;
                int size9 = arrayList12.size() - 1;
                while (size9 >= 0) {
                    C0062bd c0062bd3 = (C0062bd) arrayList12.get(size9);
                    int i27 = c0062bd3.a;
                    if (i27 != i26) {
                        if (i27 != 3) {
                            switch (i27) {
                                case 6:
                                    arrayList11.add(c0062bd3.b);
                                    break;
                                case 8:
                                    ac = null;
                                    break;
                                case 9:
                                    ac = c0062bd3.b;
                                    break;
                                case 10:
                                    c0062bd3.i = c0062bd3.h;
                                    break;
                            }
                        } else {
                            arrayList11.add(c0062bd3.b);
                        }
                        size9--;
                        i26 = 1;
                    }
                    arrayList11.remove(c0062bd3.b);
                    size9--;
                    i26 = 1;
                }
            } else {
                ArrayList arrayList13 = this.N;
                int i28 = 0;
                while (true) {
                    ArrayList arrayList14 = l7.a;
                    if (i28 < arrayList14.size()) {
                        C0062bd c0062bd4 = (C0062bd) arrayList14.get(i28);
                        boolean z8 = z3;
                        int i29 = c0062bd4.a;
                        if (i29 != i9) {
                            i4 = i8;
                            if (i29 != 2) {
                                if (i29 == 3 || i29 == 6) {
                                    arrayList13.remove(c0062bd4.b);
                                    Ac ac7 = c0062bd4.b;
                                    if (ac7 == ac) {
                                        arrayList14.add(i28, new C0062bd(9, ac7));
                                        i28++;
                                        ac = null;
                                    }
                                    i5 = 1;
                                } else if (i29 == 7) {
                                    i5 = 1;
                                } else if (i29 == 8) {
                                    arrayList14.add(i28, new C0062bd(9, ac, 0));
                                    c0062bd4.c = true;
                                    i28++;
                                    ac = c0062bd4.b;
                                }
                                i5 = 1;
                            } else {
                                Ac ac8 = c0062bd4.b;
                                int i30 = ac8.y;
                                int size10 = arrayList13.size() - 1;
                                boolean z9 = false;
                                while (size10 >= 0) {
                                    int i31 = size10;
                                    Ac ac9 = (Ac) arrayList13.get(size10);
                                    boolean z10 = z4;
                                    if (ac9.y != i30) {
                                        i30 = i30;
                                    } else if (ac9 == ac8) {
                                        i30 = i30;
                                        z9 = true;
                                    } else {
                                        if (ac9 == ac) {
                                            i6 = 0;
                                            arrayList14.add(i28, new C0062bd(9, ac9, 0));
                                            i28++;
                                            ac = null;
                                        } else {
                                            i6 = 0;
                                        }
                                        C0062bd c0062bd5 = new C0062bd(3, ac9, i6);
                                        c0062bd5.d = c0062bd4.d;
                                        c0062bd5.f = c0062bd4.f;
                                        c0062bd5.e = c0062bd4.e;
                                        c0062bd5.g = c0062bd4.g;
                                        arrayList14.add(i28, c0062bd5);
                                        arrayList13.remove(ac9);
                                        i28++;
                                        ac = ac;
                                    }
                                    size10 = i31 - 1;
                                    i30 = i30;
                                    z4 = z10;
                                }
                                z4 = z4;
                                i5 = 1;
                                if (z9) {
                                    arrayList14.remove(i28);
                                    i28--;
                                } else {
                                    c0062bd4.a = 1;
                                    c0062bd4.c = true;
                                    arrayList13.add(ac8);
                                }
                            }
                            i28 += i5;
                            i9 = i5;
                            z3 = z8;
                            i8 = i4;
                            z4 = z4;
                        } else {
                            i4 = i8;
                            i5 = i9;
                        }
                        z4 = z4;
                        arrayList13.add(c0062bd4.b);
                        i28 += i5;
                        i9 = i5;
                        z3 = z8;
                        i8 = i4;
                        z4 = z4;
                    } else {
                        z = z3;
                        i3 = i8;
                        z2 = z4;
                    }
                }
            }
            z4 = z2 || l7.g;
            i8 = i3 + 1;
            z3 = z;
        }
    }

    public final Ac B(int i) {
        Xn xn = this.c;
        ArrayList arrayList = (ArrayList) xn.a;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            Ac ac = (Ac) arrayList.get(size);
            if (ac != null && ac.x == i) {
                return ac;
            }
        }
        for (Xc xc : ((HashMap) xn.b).values()) {
            if (xc != null) {
                Ac ac2 = xc.c;
                if (ac2.x == i) {
                    return ac2;
                }
            }
        }
        return null;
    }

    public final Ac C(String str) {
        Xn xn = this.c;
        ArrayList arrayList = (ArrayList) xn.a;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            Ac ac = (Ac) arrayList.get(size);
            if (ac != null && str.equals(ac.z)) {
                return ac;
            }
        }
        for (Xc xc : ((HashMap) xn.b).values()) {
            if (xc != null) {
                Ac ac2 = xc.c;
                if (str.equals(ac2.z)) {
                    return ac2;
                }
            }
        }
        return null;
    }

    public final void D() {
        for (E9 e9 : e()) {
            if (e9.f) {
                if (J(2)) {
                    Log.v("FragmentManager", "SpecialEffectsController: Forcing postponed operations");
                }
                e9.f = false;
                e9.e();
            }
        }
    }

    public final ViewGroup F(Ac ac) {
        ViewGroup viewGroup = ac.F;
        if (viewGroup != null) {
            return viewGroup;
        }
        if (ac.y <= 0 || !this.x.K()) {
            return null;
        }
        View viewJ = this.x.J(ac.y);
        if (viewJ instanceof ViewGroup) {
            return (ViewGroup) viewJ;
        }
        return null;
    }

    public final Kc G() {
        Ac ac = this.y;
        return ac != null ? ac.t.G() : this.A;
    }

    public final Fe H() {
        Ac ac = this.y;
        return ac != null ? ac.t.H() : this.B;
    }

    public final void I(Ac ac) {
        if (J(2)) {
            Log.v("FragmentManager", "hide: " + ac);
        }
        if (ac.A) {
            return;
        }
        ac.A = true;
        ac.K = true ^ ac.K;
        c0(ac);
    }

    public final boolean L() {
        Ac ac = this.y;
        if (ac == null) {
            return true;
        }
        return ac.u != null && ac.k && ac.l().L();
    }

    public final boolean O() {
        return this.H || this.I;
    }

    public final void P(int i, boolean z) {
        HashMap map;
        Cc cc;
        if (this.w == null && i != -1) {
            throw new IllegalStateException("No activity");
        }
        if (z || i != this.v) {
            this.v = i;
            Xn xn = this.c;
            ArrayList arrayList = (ArrayList) xn.a;
            int size = arrayList.size();
            int i2 = 0;
            while (true) {
                map = (HashMap) xn.b;
                if (i2 >= size) {
                    break;
                }
                Object obj = arrayList.get(i2);
                i2++;
                Xc xc = (Xc) map.get(((Ac) obj).e);
                if (xc != null) {
                    xc.k();
                }
            }
            for (Xc xc2 : map.values()) {
                if (xc2 != null) {
                    xc2.k();
                    Ac ac = xc2.c;
                    if (ac.l && !ac.r()) {
                        xn.r(xc2);
                    }
                }
            }
            e0();
            if (this.G && (cc = this.w) != null && this.v == 7) {
                cc.m.invalidateOptionsMenu();
                this.G = false;
            }
        }
    }

    public final void Q() {
        if (this.w == null) {
            return;
        }
        this.H = false;
        this.I = false;
        this.O.g = false;
        for (Ac ac : this.c.m()) {
            if (ac != null) {
                ac.v.Q();
            }
        }
    }

    public final boolean R() {
        return S(-1, 0);
    }

    public final boolean S(int i, int i2) {
        z(false);
        y(true);
        Ac ac = this.z;
        if (ac != null && i < 0 && ac.h().R()) {
            return true;
        }
        boolean zT = T(this.L, this.M, i, i2);
        if (zT) {
            this.b = true;
            try {
                V(this.L, this.M);
                d();
            } catch (Throwable th) {
                d();
                throw th;
            }
        }
        g0();
        if (this.K) {
            this.K = false;
            e0();
        }
        ((HashMap) this.c.b).values().removeAll(Collections.singleton(null));
        return zT;
    }

    public final boolean T(ArrayList arrayList, ArrayList arrayList2, int i, int i2) {
        boolean z = (i2 & 1) != 0;
        int size = -1;
        if (!this.d.isEmpty()) {
            if (i < 0) {
                size = z ? 0 : this.d.size() - 1;
            } else {
                int size2 = this.d.size() - 1;
                while (size2 >= 0) {
                    L4 l4 = (L4) this.d.get(size2);
                    if (i >= 0 && i == l4.t) {
                        break;
                    }
                    size2--;
                }
                if (size2 < 0) {
                    size = size2;
                } else if (z) {
                    size = size2;
                    while (size > 0) {
                        L4 l5 = (L4) this.d.get(size - 1);
                        if (i < 0 || i != l5.t) {
                            break;
                        }
                        size--;
                    }
                } else if (size2 != this.d.size() - 1) {
                    size = size2 + 1;
                }
            }
        }
        if (size < 0) {
            return false;
        }
        for (int size3 = this.d.size() - 1; size3 >= size; size3--) {
            arrayList.add((L4) this.d.remove(size3));
            arrayList2.add(Boolean.TRUE);
        }
        return true;
    }

    public final void U(Ac ac) {
        if (J(2)) {
            Log.v("FragmentManager", "remove: " + ac + " nesting=" + ac.s);
        }
        boolean zR = ac.r();
        if (ac.B && zR) {
            return;
        }
        Xn xn = this.c;
        synchronized (((ArrayList) xn.a)) {
            ((ArrayList) xn.a).remove(ac);
        }
        ac.k = false;
        if (K(ac)) {
            this.G = true;
        }
        ac.l = true;
        c0(ac);
    }

    public final void V(ArrayList arrayList, ArrayList arrayList2) {
        if (arrayList.isEmpty()) {
            return;
        }
        if (arrayList.size() != arrayList2.size()) {
            throw new IllegalStateException("Internal error with the back stack records");
        }
        int size = arrayList.size();
        int i = 0;
        int i2 = 0;
        while (i < size) {
            if (!((L4) arrayList.get(i)).p) {
                if (i2 != i) {
                    A(arrayList, arrayList2, i2, i);
                }
                i2 = i + 1;
                if (((Boolean) arrayList2.get(i)).booleanValue()) {
                    while (i2 < size && ((Boolean) arrayList2.get(i2)).booleanValue() && !((L4) arrayList.get(i2)).p) {
                        i2++;
                    }
                }
                A(arrayList, arrayList2, i, i2);
                i = i2 - 1;
            }
            i++;
        }
        if (i2 != size) {
            A(arrayList, arrayList2, i2, size);
        }
    }

    public final void W(Bundle bundle) {
        R2 r2;
        int i;
        Xc xc;
        Bundle bundle2;
        Bundle bundle3;
        Bundle bundle4;
        for (String str : bundle.keySet()) {
            if (str.startsWith("result_") && (bundle4 = bundle.getBundle(str)) != null) {
                bundle4.setClassLoader(this.w.j.getClassLoader());
                this.m.put(str.substring(7), bundle4);
            }
        }
        HashMap map = new HashMap();
        for (String str2 : bundle.keySet()) {
            if (str2.startsWith("fragment_") && (bundle3 = bundle.getBundle(str2)) != null) {
                bundle3.setClassLoader(this.w.j.getClassLoader());
                map.put(str2.substring(9), bundle3);
            }
        }
        Xn xn = this.c;
        HashMap map2 = (HashMap) xn.c;
        map2.clear();
        map2.putAll(map);
        Rc rc = (Rc) bundle.getParcelable("state");
        if (rc == null) {
            return;
        }
        HashMap map3 = (HashMap) xn.b;
        map3.clear();
        ArrayList arrayList = rc.a;
        int size = arrayList.size();
        int i2 = 0;
        while (true) {
            int i3 = 2;
            r2 = this.o;
            if (i2 >= size) {
                break;
            }
            Object obj = arrayList.get(i2);
            i2++;
            Bundle bundleW = xn.w((String) obj, null);
            if (bundleW != null) {
                Ac ac = (Ac) this.O.b.get(((Wc) bundleW.getParcelable("state")).b);
                if (ac != null) {
                    if (J(2)) {
                        Log.v("FragmentManager", "restoreSaveState: re-attaching retained " + ac);
                    }
                    xc = new Xc(r2, xn, ac, bundleW);
                    bundle2 = bundleW;
                } else {
                    i3 = 2;
                    xc = new Xc(this.o, this.c, this.w.j.getClassLoader(), G(), bundleW);
                    bundle2 = bundleW;
                }
                Ac ac2 = xc.c;
                ac2.b = bundle2;
                ac2.t = this;
                if (J(i3)) {
                    Log.v("FragmentManager", "restoreSaveState: active (" + ac2.e + "): " + ac2);
                }
                xc.m(this.w.j.getClassLoader());
                xn.q(xc);
                xc.e = this.v;
            }
        }
        Tc tc = this.O;
        tc.getClass();
        ArrayList arrayList2 = new ArrayList(tc.b.values());
        int size2 = arrayList2.size();
        int i4 = 0;
        while (i4 < size2) {
            Object obj2 = arrayList2.get(i4);
            i4++;
            Ac ac3 = (Ac) obj2;
            if (map3.get(ac3.e) == null) {
                if (J(2)) {
                    Log.v("FragmentManager", "Discarding retained Fragment " + ac3 + " that was not found in the set of active Fragments " + rc.a);
                }
                this.O.f(ac3);
                ac3.t = this;
                Xc xc2 = new Xc(r2, xn, ac3);
                xc2.e = 1;
                xc2.k();
                ac3.l = true;
                xc2.k();
            }
        }
        ArrayList arrayList3 = rc.b;
        ((ArrayList) xn.a).clear();
        if (arrayList3 != null) {
            int size3 = arrayList3.size();
            int i5 = 0;
            while (i5 < size3) {
                Object obj3 = arrayList3.get(i5);
                i5++;
                String str3 = (String) obj3;
                Ac acG = xn.g(str3);
                if (acG == null) {
                    throw new IllegalStateException(AbstractC0535qo.d("No instantiated fragment for (", str3, ")"));
                }
                if (J(2)) {
                    Log.v("FragmentManager", "restoreSaveState: added (" + str3 + "): " + acG);
                }
                xn.a(acG);
            }
        }
        if (rc.c != null) {
            this.d = new ArrayList(rc.c.length);
            int i6 = 0;
            while (true) {
                M4[] m4Arr = rc.c;
                if (i6 >= m4Arr.length) {
                    break;
                }
                M4 m4 = m4Arr[i6];
                m4.getClass();
                L4 l4 = new L4(this);
                int i7 = 0;
                int i8 = 0;
                while (true) {
                    int[] iArr = m4.a;
                    if (i7 >= iArr.length) {
                        break;
                    }
                    C0062bd c0062bd = new C0062bd();
                    int i9 = i7 + 1;
                    c0062bd.a = iArr[i7];
                    if (J(2)) {
                        Log.v("FragmentManager", "Instantiate " + l4 + " op #" + i8 + " base fragment #" + iArr[i9]);
                    }
                    c0062bd.h = EnumC0588sg.b()[m4.c[i8]];
                    c0062bd.i = EnumC0588sg.b()[m4.d[i8]];
                    int i10 = i7 + 2;
                    c0062bd.c = iArr[i9] != 0;
                    int i11 = iArr[i10];
                    c0062bd.d = i11;
                    int i12 = iArr[i7 + 3];
                    c0062bd.e = i12;
                    int i13 = i7 + 5;
                    int i14 = iArr[i7 + 4];
                    c0062bd.f = i14;
                    i7 += 6;
                    int i15 = iArr[i13];
                    c0062bd.g = i15;
                    l4.b = i11;
                    l4.c = i12;
                    l4.d = i14;
                    l4.e = i15;
                    l4.b(c0062bd);
                    i8++;
                }
                l4.f = m4.e;
                l4.i = m4.f;
                l4.g = true;
                l4.j = m4.h;
                l4.k = m4.i;
                l4.l = m4.j;
                l4.m = m4.k;
                l4.n = m4.l;
                l4.o = m4.m;
                l4.p = m4.n;
                l4.t = m4.g;
                int i16 = 0;
                while (true) {
                    ArrayList arrayList4 = m4.b;
                    if (i16 >= arrayList4.size()) {
                        break;
                    }
                    String str4 = (String) arrayList4.get(i16);
                    if (str4 != null) {
                        ((C0062bd) l4.a.get(i16)).b = xn.g(str4);
                    }
                    i16++;
                }
                l4.c(1);
                if (J(2)) {
                    Log.v("FragmentManager", "restoreAllState: back stack #" + i6 + " (index " + l4.t + "): " + l4);
                    PrintWriter printWriter = new PrintWriter(new C0558rh());
                    l4.g("  ", printWriter, false);
                    printWriter.close();
                }
                this.d.add(l4);
                i6++;
            }
            i = 0;
        } else {
            i = 0;
            this.d = new ArrayList();
        }
        this.k.set(rc.d);
        String str5 = rc.e;
        if (str5 != null) {
            Ac acG2 = xn.g(str5);
            this.z = acG2;
            r(acG2);
        }
        ArrayList arrayList5 = rc.f;
        if (arrayList5 != null) {
            while (i < arrayList5.size()) {
                this.l.put((String) arrayList5.get(i), (N4) rc.g.get(i));
                i++;
            }
        }
        this.F = new ArrayDeque(rc.h);
    }

    public final Bundle X() {
        int i;
        ArrayList arrayList;
        M4[] m4Arr;
        Bundle bundle;
        Bundle bundle2 = new Bundle();
        D();
        w();
        z(true);
        this.H = true;
        this.O.g = true;
        Xn xn = this.c;
        xn.getClass();
        HashMap map = (HashMap) xn.b;
        ArrayList arrayList2 = new ArrayList(map.size());
        Iterator it = map.values().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Xc xc = (Xc) it.next();
            if (xc != null) {
                Ac ac = xc.c;
                String str = ac.e;
                Bundle bundle3 = new Bundle();
                Ac ac2 = xc.c;
                if (ac2.a == -1 && (bundle = ac2.b) != null) {
                    bundle3.putAll(bundle);
                }
                bundle3.putParcelable("state", new Wc(ac2));
                if (ac2.a > -1) {
                    Bundle bundle4 = new Bundle();
                    ac2.A(bundle4);
                    if (!bundle4.isEmpty()) {
                        bundle3.putBundle("savedInstanceState", bundle4);
                    }
                    xc.a.s(ac2, bundle4, false);
                    Bundle bundle5 = new Bundle();
                    ac2.S.Q(bundle5);
                    if (!bundle5.isEmpty()) {
                        bundle3.putBundle("registryState", bundle5);
                    }
                    Bundle bundleX = ac2.v.X();
                    if (!bundleX.isEmpty()) {
                        bundle3.putBundle("childFragmentManager", bundleX);
                    }
                    if (ac2.G != null) {
                        xc.o();
                    }
                    SparseArray<? extends Parcelable> sparseArray = ac2.c;
                    if (sparseArray != null) {
                        bundle3.putSparseParcelableArray("viewState", sparseArray);
                    }
                    Bundle bundle6 = ac2.d;
                    if (bundle6 != null) {
                        bundle3.putBundle("viewRegistryState", bundle6);
                    }
                }
                Bundle bundle7 = ac2.f;
                if (bundle7 != null) {
                    bundle3.putBundle("arguments", bundle7);
                }
                xn.w(str, bundle3);
                arrayList2.add(ac.e);
                if (J(2)) {
                    Log.v("FragmentManager", "Saved state of " + ac + ": " + ac.b);
                }
            }
        }
        HashMap map2 = (HashMap) this.c.c;
        if (!map2.isEmpty()) {
            Xn xn2 = this.c;
            synchronized (((ArrayList) xn2.a)) {
                try {
                    if (((ArrayList) xn2.a).isEmpty()) {
                        arrayList = null;
                    } else {
                        arrayList = new ArrayList(((ArrayList) xn2.a).size());
                        ArrayList arrayList3 = (ArrayList) xn2.a;
                        int size = arrayList3.size();
                        int i2 = 0;
                        while (i2 < size) {
                            Object obj = arrayList3.get(i2);
                            i2++;
                            Ac ac3 = (Ac) obj;
                            arrayList.add(ac3.e);
                            if (J(2)) {
                                Log.v("FragmentManager", "saveAllState: adding fragment (" + ac3.e + "): " + ac3);
                            }
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            int size2 = this.d.size();
            if (size2 > 0) {
                m4Arr = new M4[size2];
                for (i = 0; i < size2; i++) {
                    m4Arr[i] = new M4((L4) this.d.get(i));
                    if (J(2)) {
                        Log.v("FragmentManager", "saveAllState: adding back stack #" + i + ": " + this.d.get(i));
                    }
                }
            } else {
                m4Arr = null;
            }
            Rc rc = new Rc();
            rc.e = null;
            ArrayList arrayList4 = new ArrayList();
            rc.f = arrayList4;
            ArrayList arrayList5 = new ArrayList();
            rc.g = arrayList5;
            rc.a = arrayList2;
            rc.b = arrayList;
            rc.c = m4Arr;
            rc.d = this.k.get();
            Ac ac4 = this.z;
            if (ac4 != null) {
                rc.e = ac4.e;
            }
            arrayList4.addAll(this.l.keySet());
            arrayList5.addAll(this.l.values());
            rc.h = new ArrayList(this.F);
            bundle2.putParcelable("state", rc);
            for (String str2 : this.m.keySet()) {
                bundle2.putBundle("result_" + str2, (Bundle) this.m.get(str2));
            }
            for (String str3 : map2.keySet()) {
                bundle2.putBundle("fragment_" + str3, (Bundle) map2.get(str3));
            }
        } else if (J(2)) {
            Log.v("FragmentManager", "saveAllState: no fragments!");
            return bundle2;
        }
        return bundle2;
    }

    public final void Y() {
        synchronized (this.a) {
            try {
                if (this.a.size() == 1) {
                    this.w.k.removeCallbacks(this.P);
                    this.w.k.post(this.P);
                    g0();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void Z(Ac ac, boolean z) {
        ViewGroup viewGroupF = F(ac);
        if (viewGroupF == null || !(viewGroupF instanceof FragmentContainerView)) {
            return;
        }
        ((FragmentContainerView) viewGroupF).d = !z;
    }

    public final Xc a(Ac ac) {
        String str = ac.N;
        if (str != null) {
            AbstractC0031ad.c(ac, str);
        }
        if (J(2)) {
            Log.v("FragmentManager", "add: " + ac);
        }
        Xc xcG = g(ac);
        ac.t = this;
        Xn xn = this.c;
        xn.q(xcG);
        if (!ac.B) {
            xn.a(ac);
            ac.l = false;
            if (ac.G == null) {
                ac.K = false;
            }
            if (K(ac)) {
                this.G = true;
            }
        }
        return xcG;
    }

    public final void a0(Ac ac, EnumC0588sg enumC0588sg) {
        if (ac.equals(this.c.g(ac.e)) && (ac.u == null || ac.t == this)) {
            ac.O = enumC0588sg;
            return;
        }
        throw new IllegalArgumentException("Fragment " + ac + " is not an active fragment of FragmentManager " + this);
    }

    public final void b(Cc cc, AbstractC0046as abstractC0046as, Ac ac) {
        String str;
        if (this.w != null) {
            throw new IllegalStateException("Already attached");
        }
        this.w = cc;
        this.x = abstractC0046as;
        this.y = ac;
        CopyOnWriteArrayList copyOnWriteArrayList = this.p;
        if (ac != null) {
            copyOnWriteArrayList.add(new Lc(ac));
        } else if (cc != null) {
            copyOnWriteArrayList.add(cc);
        }
        if (this.y != null) {
            g0();
        }
        if (cc != null) {
            C0622tj c0622tjI = cc.m.i();
            this.g = c0622tjI;
            InterfaceC0743xg interfaceC0743xg = ac != null ? ac : cc;
            c0622tjI.getClass();
            C0805zg c0805zgE = interfaceC0743xg.e();
            if (c0805zgE.c != EnumC0588sg.a) {
                Ic ic = this.j;
                ic.b.add(new C0530qj(c0622tjI, c0805zgE, ic));
                c0622tjI.c();
                ic.c = new C0591sj(0, c0622tjI, C0622tj.class, "updateEnabledCallbacks", "updateEnabledCallbacks()V", 0);
            }
        }
        if (ac != null) {
            Tc tc = ac.t.O;
            HashMap map = tc.c;
            Tc tc2 = (Tc) map.get(ac.e);
            if (tc2 == null) {
                tc2 = new Tc(tc.e);
                map.put(ac.e, tc2);
            }
            this.O = tc2;
        } else if (cc != null) {
            this.O = (Tc) new Ys(cc.m.d(), Tc.h).r(Tc.class);
        } else {
            this.O = new Tc(false);
        }
        this.O.g = O();
        this.c.d = this.O;
        Cc cc2 = this.w;
        if (cc2 != null && ac == null) {
            R2 r2B = cc2.b();
            r2B.S("android:support:fragments", new L6(2, this));
            Bundle bundleG = r2B.g("android:support:fragments");
            if (bundleG != null) {
                W(bundleG);
            }
        }
        Cc cc3 = this.w;
        if (cc3 != null) {
            AbstractActivityC0051b2 abstractActivityC0051b2 = cc3.m;
            if (ac != null) {
                str = ac.e + ":";
            } else {
                str = "";
            }
            String str2 = "FragmentManager:" + str;
            String strC = AbstractC0535qo.c(str2, "StartActivityForResult");
            M0 m0 = new M0(2);
            Hc hc = new Hc(this, 1);
            N6 n6 = abstractActivityC0051b2.k;
            this.C = n6.c(strC, m0, hc);
            this.D = n6.c(AbstractC0535qo.c(str2, "StartIntentSenderForResult"), new M0(3), new Hc(this, 2));
            this.E = n6.c(AbstractC0535qo.c(str2, "RequestPermissions"), new M0(1), new Hc(this, 0));
        }
        Cc cc4 = this.w;
        if (cc4 != null) {
            cc4.m.g(this.q);
        }
        Cc cc5 = this.w;
        if (cc5 != null) {
            cc5.m.m.add(this.r);
        }
        Cc cc6 = this.w;
        if (cc6 != null) {
            cc6.m.o.add(this.s);
        }
        Cc cc7 = this.w;
        if (cc7 != null) {
            cc7.m.p.add(this.t);
        }
        Cc cc8 = this.w;
        if (cc8 == null || ac != null) {
            return;
        }
        AbstractActivityC0051b2 abstractActivityC0051b3 = cc8.m;
        Jc jc = this.u;
        K3 k3 = abstractActivityC0051b3.c;
        ((CopyOnWriteArrayList) k3.c).add(jc);
        ((Runnable) k3.b).run();
    }

    public final void b0(Ac ac) {
        if (ac != null) {
            if (!ac.equals(this.c.g(ac.e)) || (ac.u != null && ac.t != this)) {
                throw new IllegalArgumentException("Fragment " + ac + " is not an active fragment of FragmentManager " + this);
            }
        }
        Ac ac2 = this.z;
        this.z = ac;
        r(ac2);
        r(this.z);
    }

    public final void c(Ac ac) {
        if (J(2)) {
            Log.v("FragmentManager", "attach: " + ac);
        }
        if (ac.B) {
            ac.B = false;
            if (ac.k) {
                return;
            }
            this.c.a(ac);
            if (J(2)) {
                Log.v("FragmentManager", "add from attach: " + ac);
            }
            if (K(ac)) {
                this.G = true;
            }
        }
    }

    public final void c0(Ac ac) {
        ViewGroup viewGroupF = F(ac);
        if (viewGroupF != null) {
            C0801zc c0801zc = ac.J;
            if ((c0801zc == null ? 0 : c0801zc.e) + (c0801zc == null ? 0 : c0801zc.d) + (c0801zc == null ? 0 : c0801zc.c) + (c0801zc == null ? 0 : c0801zc.b) > 0) {
                if (viewGroupF.getTag(R.id._0_resource_name_obfuscated_res_0x7f090201) == null) {
                    viewGroupF.setTag(R.id._0_resource_name_obfuscated_res_0x7f090201, ac);
                }
                Ac ac2 = (Ac) viewGroupF.getTag(R.id._0_resource_name_obfuscated_res_0x7f090201);
                C0801zc c0801zc2 = ac.J;
                boolean z = c0801zc2 != null ? c0801zc2.a : false;
                if (ac2.J == null) {
                    return;
                }
                ac2.f().a = z;
            }
        }
    }

    public final void d() {
        this.b = false;
        this.M.clear();
        this.L.clear();
    }

    public final HashSet e() {
        E9 e9;
        HashSet hashSet = new HashSet();
        ArrayList arrayListK = this.c.k();
        int size = arrayListK.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayListK.get(i);
            i++;
            ViewGroup viewGroup = ((Xc) obj).c.F;
            if (viewGroup != null) {
                H();
                Object tag = viewGroup.getTag(R.id._0_resource_name_obfuscated_res_0x7f09019c);
                if (tag instanceof E9) {
                    e9 = (E9) tag;
                } else {
                    e9 = new E9(viewGroup);
                    viewGroup.setTag(R.id._0_resource_name_obfuscated_res_0x7f09019c, e9);
                }
                hashSet.add(e9);
            }
        }
        return hashSet;
    }

    public final void e0() {
        ArrayList arrayListK = this.c.k();
        int size = arrayListK.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayListK.get(i);
            i++;
            Xc xc = (Xc) obj;
            Ac ac = xc.c;
            if (ac.H) {
                if (this.b) {
                    this.K = true;
                } else {
                    ac.H = false;
                    xc.k();
                }
            }
        }
    }

    public final HashSet f(ArrayList arrayList, int i, int i2) {
        ViewGroup viewGroup;
        E9 e9;
        HashSet hashSet = new HashSet();
        while (i < i2) {
            ArrayList arrayList2 = ((L4) arrayList.get(i)).a;
            int size = arrayList2.size();
            int i3 = 0;
            while (i3 < size) {
                Object obj = arrayList2.get(i3);
                i3++;
                Ac ac = ((C0062bd) obj).b;
                if (ac != null && (viewGroup = ac.F) != null) {
                    H();
                    Object tag = viewGroup.getTag(R.id._0_resource_name_obfuscated_res_0x7f09019c);
                    if (tag instanceof E9) {
                        e9 = (E9) tag;
                    } else {
                        e9 = new E9(viewGroup);
                        viewGroup.setTag(R.id._0_resource_name_obfuscated_res_0x7f09019c, e9);
                    }
                    hashSet.add(e9);
                }
            }
            i++;
        }
        return hashSet;
    }

    public final void f0(IllegalStateException illegalStateException) {
        Log.e("FragmentManager", illegalStateException.getMessage());
        Log.e("FragmentManager", "Activity state:");
        PrintWriter printWriter = new PrintWriter(new C0558rh());
        Cc cc = this.w;
        if (cc == null) {
            try {
                v("  ", null, printWriter, new String[0]);
                throw illegalStateException;
            } catch (Exception e) {
                Log.e("FragmentManager", "Failed dumping state", e);
                throw illegalStateException;
            }
        }
        try {
            cc.m.dump("  ", null, printWriter, new String[0]);
            throw illegalStateException;
        } catch (Exception e2) {
            Log.e("FragmentManager", "Failed dumping state", e2);
            throw illegalStateException;
        }
    }

    public final Xc g(Ac ac) {
        String str = ac.e;
        Xn xn = this.c;
        Xc xc = (Xc) ((HashMap) xn.b).get(str);
        if (xc != null) {
            return xc;
        }
        Xc xc2 = new Xc(this.o, xn, ac);
        xc2.m(this.w.j.getClassLoader());
        xc2.e = this.v;
        return xc2;
    }

    public final void g0() {
        synchronized (this.a) {
            try {
                if (!this.a.isEmpty()) {
                    Ic ic = this.j;
                    ic.a = true;
                    C0591sj c0591sj = ic.c;
                    if (c0591sj != null) {
                        c0591sj.a();
                    }
                    if (J(3)) {
                        toString();
                    }
                    return;
                }
                boolean z = this.d.size() + (this.h != null ? 1 : 0) > 0 && N(this.y);
                if (J(3)) {
                    toString();
                }
                Ic ic2 = this.j;
                ic2.a = z;
                C0591sj c0591sj2 = ic2.c;
                if (c0591sj2 != null) {
                    c0591sj2.a();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void h(Ac ac) {
        if (J(2)) {
            Log.v("FragmentManager", "detach: " + ac);
        }
        if (ac.B) {
            return;
        }
        ac.B = true;
        if (ac.k) {
            if (J(2)) {
                Log.v("FragmentManager", "remove from detach: " + ac);
            }
            Xn xn = this.c;
            synchronized (((ArrayList) xn.a)) {
                ((ArrayList) xn.a).remove(ac);
            }
            ac.k = false;
            if (K(ac)) {
                this.G = true;
            }
            c0(ac);
        }
    }

    public final void i(boolean z) {
        if (z && this.w != null) {
            f0(new IllegalStateException("Do not call dispatchConfigurationChanged() on host. Host implements OnConfigurationChangedProvider and automatically dispatches configuration changes to fragments."));
            throw null;
        }
        for (Ac ac : this.c.m()) {
            if (ac != null) {
                ac.E = true;
                if (z) {
                    ac.v.i(true);
                }
            }
        }
    }

    public final boolean j() {
        if (this.v >= 1) {
            for (Ac ac : this.c.m()) {
                if (ac != null) {
                    if (!ac.A ? ac.v.j() : false) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public final boolean k() {
        if (this.v < 1) {
            return false;
        }
        ArrayList arrayList = null;
        boolean z = false;
        for (Ac ac : this.c.m()) {
            if (ac != null && M(ac)) {
                if (!ac.A ? ac.v.k() : false) {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add(ac);
                    z = true;
                }
            }
        }
        if (this.e != null) {
            for (int i = 0; i < this.e.size(); i++) {
                Ac ac2 = (Ac) this.e.get(i);
                if (arrayList == null || !arrayList.contains(ac2)) {
                    ac2.getClass();
                }
            }
        }
        this.e = arrayList;
        return z;
    }

    public final void l() {
        boolean zIsChangingConfigurations = true;
        this.J = true;
        z(true);
        w();
        Cc cc = this.w;
        Xn xn = this.c;
        if (cc != null) {
            zIsChangingConfigurations = ((Tc) xn.d).f;
        } else {
            AbstractActivityC0051b2 abstractActivityC0051b2 = cc.j;
            if (abstractActivityC0051b2 != null) {
                zIsChangingConfigurations = true ^ abstractActivityC0051b2.isChangingConfigurations();
            }
        }
        if (zIsChangingConfigurations) {
            Iterator it = this.l.values().iterator();
            while (it.hasNext()) {
                ArrayList arrayList = ((N4) it.next()).a;
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    ((Tc) xn.d).d((String) obj, false);
                }
            }
        }
        u(-1);
        Cc cc2 = this.w;
        if (cc2 != null) {
            cc2.m.m.remove(this.r);
        }
        Cc cc3 = this.w;
        if (cc3 != null) {
            cc3.m.l.remove(this.q);
        }
        Cc cc4 = this.w;
        if (cc4 != null) {
            cc4.m.o.remove(this.s);
        }
        Cc cc5 = this.w;
        if (cc5 != null) {
            cc5.m.p.remove(this.t);
        }
        Cc cc6 = this.w;
        if (cc6 != null && this.y == null) {
            AbstractActivityC0051b2 abstractActivityC0051b3 = cc6.m;
            Jc jc = this.u;
            K3 k3 = abstractActivityC0051b3.c;
            ((CopyOnWriteArrayList) k3.c).remove(jc);
            if (((HashMap) k3.a).remove(jc) != null) {
                throw new ClassCastException();
            }
            ((Runnable) k3.b).run();
        }
        this.w = null;
        this.x = null;
        this.y = null;
        if (this.g != null) {
            Iterator it2 = this.j.b.iterator();
            while (it2.hasNext()) {
                ((InterfaceC0763y5) it2.next()).cancel();
            }
            this.g = null;
        }
        P0 p0 = this.C;
        if (p0 != null) {
            p0.d0();
            this.D.d0();
            this.E.d0();
        }
    }

    public final void m(boolean z) {
        if (z && this.w != null) {
            f0(new IllegalStateException("Do not call dispatchLowMemory() on host. Host implements OnTrimMemoryProvider and automatically dispatches low memory callbacks to fragments."));
            throw null;
        }
        for (Ac ac : this.c.m()) {
            if (ac != null) {
                ac.E = true;
                if (z) {
                    ac.v.m(true);
                }
            }
        }
    }

    public final void n(boolean z) {
        if (z && this.w != null) {
            f0(new IllegalStateException("Do not call dispatchMultiWindowModeChanged() on host. Host implements OnMultiWindowModeChangedProvider and automatically dispatches multi-window mode changes to fragments."));
            throw null;
        }
        for (Ac ac : this.c.m()) {
            if (ac != null && z) {
                ac.v.n(true);
            }
        }
    }

    public final void o() {
        ArrayList arrayListL = this.c.l();
        int size = arrayListL.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayListL.get(i);
            i++;
            Ac ac = (Ac) obj;
            if (ac != null) {
                ac.q();
                ac.v.o();
            }
        }
    }

    public final boolean p() {
        if (this.v >= 1) {
            for (Ac ac : this.c.m()) {
                if (ac != null) {
                    if (!ac.A ? ac.v.p() : false) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public final void q() {
        if (this.v < 1) {
            return;
        }
        for (Ac ac : this.c.m()) {
            if (ac != null && !ac.A) {
                ac.v.q();
            }
        }
    }

    public final void r(Ac ac) {
        if (ac != null) {
            if (ac.equals(this.c.g(ac.e))) {
                ac.t.getClass();
                boolean zN = N(ac);
                Boolean bool = ac.j;
                if (bool == null || bool.booleanValue() != zN) {
                    ac.j = Boolean.valueOf(zN);
                    Qc qc = ac.v;
                    qc.g0();
                    qc.r(qc.z);
                }
            }
        }
    }

    public final void s(boolean z) {
        if (z && this.w != null) {
            f0(new IllegalStateException("Do not call dispatchPictureInPictureModeChanged() on host. Host implements OnPictureInPictureModeChangedProvider and automatically dispatches picture-in-picture mode changes to fragments."));
            throw null;
        }
        for (Ac ac : this.c.m()) {
            if (ac != null && z) {
                ac.v.s(true);
            }
        }
    }

    public final boolean t() {
        if (this.v < 1) {
            return false;
        }
        boolean z = false;
        for (Ac ac : this.c.m()) {
            if (ac != null && M(ac)) {
                if (!ac.A ? ac.v.t() : false) {
                    z = true;
                }
            }
        }
        return z;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("FragmentManager{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" in ");
        Ac ac = this.y;
        if (ac != null) {
            sb.append(ac.getClass().getSimpleName());
            sb.append("{");
            sb.append(Integer.toHexString(System.identityHashCode(this.y)));
            sb.append("}");
        } else {
            Cc cc = this.w;
            if (cc != null) {
                sb.append(cc.getClass().getSimpleName());
                sb.append("{");
                sb.append(Integer.toHexString(System.identityHashCode(this.w)));
                sb.append("}");
            } else {
                sb.append("null");
            }
        }
        sb.append("}}");
        return sb.toString();
    }

    public final void u(int i) {
        try {
            this.b = true;
            for (Xc xc : ((HashMap) this.c.b).values()) {
                if (xc != null) {
                    xc.e = i;
                }
            }
            P(i, false);
            Iterator it = e().iterator();
            while (it.hasNext()) {
                ((E9) it.next()).h();
            }
            this.b = false;
            z(true);
        } catch (Throwable th) {
            this.b = false;
            throw th;
        }
    }

    public final void v(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        int size;
        String strC = AbstractC0535qo.c(str, "    ");
        Xn xn = this.c;
        xn.getClass();
        String str2 = str + "    ";
        HashMap map = (HashMap) xn.b;
        if (!map.isEmpty()) {
            printWriter.print(str);
            printWriter.println("Active Fragments:");
            for (Xc xc : map.values()) {
                printWriter.print(str);
                if (xc != null) {
                    Ac ac = xc.c;
                    printWriter.println(ac);
                    ac.getClass();
                    printWriter.print(str2);
                    printWriter.print("mFragmentId=#");
                    printWriter.print(Integer.toHexString(ac.x));
                    printWriter.print(" mContainerId=#");
                    printWriter.print(Integer.toHexString(ac.y));
                    printWriter.print(" mTag=");
                    printWriter.println(ac.z);
                    printWriter.print(str2);
                    printWriter.print("mState=");
                    printWriter.print(ac.a);
                    printWriter.print(" mWho=");
                    printWriter.print(ac.e);
                    printWriter.print(" mBackStackNesting=");
                    printWriter.println(ac.s);
                    printWriter.print(str2);
                    printWriter.print("mAdded=");
                    printWriter.print(ac.k);
                    printWriter.print(" mRemoving=");
                    printWriter.print(ac.l);
                    printWriter.print(" mFromLayout=");
                    printWriter.print(ac.n);
                    printWriter.print(" mInLayout=");
                    printWriter.println(ac.o);
                    printWriter.print(str2);
                    printWriter.print("mHidden=");
                    printWriter.print(ac.A);
                    printWriter.print(" mDetached=");
                    printWriter.print(ac.B);
                    printWriter.print(" mMenuVisible=");
                    printWriter.print(ac.D);
                    printWriter.print(" mHasMenu=");
                    printWriter.println(false);
                    printWriter.print(str2);
                    printWriter.print("mRetainInstance=");
                    printWriter.print(ac.C);
                    printWriter.print(" mUserVisibleHint=");
                    printWriter.println(ac.I);
                    if (ac.t != null) {
                        printWriter.print(str2);
                        printWriter.print("mFragmentManager=");
                        printWriter.println(ac.t);
                    }
                    if (ac.u != null) {
                        printWriter.print(str2);
                        printWriter.print("mHost=");
                        printWriter.println(ac.u);
                    }
                    if (ac.w != null) {
                        printWriter.print(str2);
                        printWriter.print("mParentFragment=");
                        printWriter.println(ac.w);
                    }
                    if (ac.f != null) {
                        printWriter.print(str2);
                        printWriter.print("mArguments=");
                        printWriter.println(ac.f);
                    }
                    if (ac.b != null) {
                        printWriter.print(str2);
                        printWriter.print("mSavedFragmentState=");
                        printWriter.println(ac.b);
                    }
                    if (ac.c != null) {
                        printWriter.print(str2);
                        printWriter.print("mSavedViewState=");
                        printWriter.println(ac.c);
                    }
                    if (ac.d != null) {
                        printWriter.print(str2);
                        printWriter.print("mSavedViewRegistryState=");
                        printWriter.println(ac.d);
                    }
                    Object objN = ac.n(false);
                    if (objN != null) {
                        printWriter.print(str2);
                        printWriter.print("mTarget=");
                        printWriter.print(objN);
                        printWriter.print(" mTargetRequestCode=");
                        printWriter.println(ac.i);
                    }
                    printWriter.print(str2);
                    printWriter.print("mPopDirection=");
                    C0801zc c0801zc = ac.J;
                    printWriter.println(c0801zc == null ? false : c0801zc.a);
                    C0801zc c0801zc2 = ac.J;
                    if ((c0801zc2 == null ? 0 : c0801zc2.b) != 0) {
                        printWriter.print(str2);
                        printWriter.print("getEnterAnim=");
                        C0801zc c0801zc3 = ac.J;
                        printWriter.println(c0801zc3 == null ? 0 : c0801zc3.b);
                    }
                    C0801zc c0801zc4 = ac.J;
                    if ((c0801zc4 == null ? 0 : c0801zc4.c) != 0) {
                        printWriter.print(str2);
                        printWriter.print("getExitAnim=");
                        C0801zc c0801zc5 = ac.J;
                        printWriter.println(c0801zc5 == null ? 0 : c0801zc5.c);
                    }
                    C0801zc c0801zc6 = ac.J;
                    if ((c0801zc6 == null ? 0 : c0801zc6.d) != 0) {
                        printWriter.print(str2);
                        printWriter.print("getPopEnterAnim=");
                        C0801zc c0801zc7 = ac.J;
                        printWriter.println(c0801zc7 == null ? 0 : c0801zc7.d);
                    }
                    C0801zc c0801zc8 = ac.J;
                    if ((c0801zc8 == null ? 0 : c0801zc8.e) != 0) {
                        printWriter.print(str2);
                        printWriter.print("getPopExitAnim=");
                        C0801zc c0801zc9 = ac.J;
                        printWriter.println(c0801zc9 == null ? 0 : c0801zc9.e);
                    }
                    if (ac.F != null) {
                        printWriter.print(str2);
                        printWriter.print("mContainer=");
                        printWriter.println(ac.F);
                    }
                    if (ac.G != null) {
                        printWriter.print(str2);
                        printWriter.print("mView=");
                        printWriter.println(ac.G);
                    }
                    if (ac.i() != null) {
                        C0689vo c0689vo = ((C0251hh) new Ys(ac.d(), C0251hh.c).r(C0251hh.class)).b;
                        if (c0689vo.c > 0) {
                            printWriter.print(str2);
                            printWriter.println("Loaders:");
                            if (c0689vo.c > 0) {
                                if (c0689vo.b[0] != null) {
                                    throw new ClassCastException();
                                }
                                printWriter.print(str2);
                                printWriter.print("  #");
                                printWriter.print(c0689vo.a[0]);
                                printWriter.print(": ");
                                throw null;
                            }
                        }
                    }
                    printWriter.print(str2);
                    printWriter.println("Child " + ac.v + ":");
                    ac.v.v(AbstractC0535qo.c(str2, "  "), fileDescriptor, printWriter, strArr);
                } else {
                    printWriter.println("null");
                }
            }
        }
        ArrayList arrayList = (ArrayList) xn.a;
        int size2 = arrayList.size();
        if (size2 > 0) {
            printWriter.print(str);
            printWriter.println("Added Fragments:");
            for (int i = 0; i < size2; i++) {
                Ac ac2 = (Ac) arrayList.get(i);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i);
                printWriter.print(": ");
                printWriter.println(ac2.toString());
            }
        }
        ArrayList arrayList2 = this.e;
        if (arrayList2 != null && (size = arrayList2.size()) > 0) {
            printWriter.print(str);
            printWriter.println("Fragments Created Menus:");
            for (int i2 = 0; i2 < size; i2++) {
                Ac ac3 = (Ac) this.e.get(i2);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i2);
                printWriter.print(": ");
                printWriter.println(ac3.toString());
            }
        }
        int size3 = this.d.size();
        if (size3 > 0) {
            printWriter.print(str);
            printWriter.println("Back Stack:");
            for (int i3 = 0; i3 < size3; i3++) {
                L4 l4 = (L4) this.d.get(i3);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i3);
                printWriter.print(": ");
                printWriter.println(l4.toString());
                l4.g(strC, printWriter, true);
            }
        }
        printWriter.print(str);
        printWriter.println("Back Stack Index: " + this.k.get());
        synchronized (this.a) {
            try {
                int size4 = this.a.size();
                if (size4 > 0) {
                    printWriter.print(str);
                    printWriter.println("Pending Actions:");
                    for (int i4 = 0; i4 < size4; i4++) {
                        Object obj = (Nc) this.a.get(i4);
                        printWriter.print(str);
                        printWriter.print("  #");
                        printWriter.print(i4);
                        printWriter.print(": ");
                        printWriter.println(obj);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        printWriter.print(str);
        printWriter.println("FragmentManager misc state:");
        printWriter.print(str);
        printWriter.print("  mHost=");
        printWriter.println(this.w);
        printWriter.print(str);
        printWriter.print("  mContainer=");
        printWriter.println(this.x);
        if (this.y != null) {
            printWriter.print(str);
            printWriter.print("  mParent=");
            printWriter.println(this.y);
        }
        printWriter.print(str);
        printWriter.print("  mCurState=");
        printWriter.print(this.v);
        printWriter.print(" mStateSaved=");
        printWriter.print(this.H);
        printWriter.print(" mStopped=");
        printWriter.print(this.I);
        printWriter.print(" mDestroyed=");
        printWriter.println(this.J);
        if (this.G) {
            printWriter.print(str);
            printWriter.print("  mNeedMenuInvalidate=");
            printWriter.println(this.G);
        }
    }

    public final void w() {
        Iterator it = e().iterator();
        while (it.hasNext()) {
            ((E9) it.next()).h();
        }
    }

    public final void x(Nc nc, boolean z) {
        if (!z) {
            if (this.w == null) {
                if (!this.J) {
                    throw new IllegalStateException("FragmentManager has not been attached to a host.");
                }
                throw new IllegalStateException("FragmentManager has been destroyed");
            }
            if (O()) {
                throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
            }
        }
        synchronized (this.a) {
            try {
                if (this.w == null) {
                    if (!z) {
                        throw new IllegalStateException("Activity has been destroyed");
                    }
                } else {
                    this.a.add(nc);
                    Y();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void y(boolean z) {
        if (this.b) {
            throw new IllegalStateException("FragmentManager is already executing transactions");
        }
        if (this.w == null) {
            if (!this.J) {
                throw new IllegalStateException("FragmentManager has not been attached to a host.");
            }
            throw new IllegalStateException("FragmentManager has been destroyed");
        }
        if (Looper.myLooper() != this.w.k.getLooper()) {
            throw new IllegalStateException("Must be called from main thread of fragment host");
        }
        if (!z && O()) {
            throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
        }
        if (this.L == null) {
            this.L = new ArrayList();
            this.M = new ArrayList();
        }
    }

    public final boolean z(boolean z) {
        boolean zA;
        L4 l4;
        y(z);
        if (!this.i && (l4 = this.h) != null) {
            l4.s = false;
            l4.d();
            if (J(3)) {
                Objects.toString(this.h);
                Objects.toString(this.a);
            }
            this.h.e(false, false);
            this.a.add(0, this.h);
            ArrayList arrayList = this.h.a;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                Ac ac = ((C0062bd) obj).b;
                if (ac != null) {
                    ac.m = false;
                }
            }
            this.h = null;
        }
        boolean z2 = false;
        while (true) {
            ArrayList arrayList2 = this.L;
            ArrayList arrayList3 = this.M;
            synchronized (this.a) {
                if (this.a.isEmpty()) {
                    zA = false;
                } else {
                    try {
                        int size2 = this.a.size();
                        zA = false;
                        for (int i2 = 0; i2 < size2; i2++) {
                            zA |= ((Nc) this.a.get(i2)).a(arrayList2, arrayList3);
                        }
                        this.a.clear();
                        this.w.k.removeCallbacks(this.P);
                    } catch (Throwable th) {
                        this.a.clear();
                        this.w.k.removeCallbacks(this.P);
                        throw th;
                    }
                }
            }
            if (!zA) {
                break;
            }
            z2 = true;
            this.b = true;
            try {
                V(this.L, this.M);
                d();
            } catch (Throwable th2) {
                d();
                throw th2;
            }
        }
        g0();
        if (this.K) {
            this.K = false;
            e0();
        }
        ((HashMap) this.c.b).values().removeAll(Collections.singleton(null));
        return z2;
    }
}
