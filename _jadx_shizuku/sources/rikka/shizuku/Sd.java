package rikka.shizuku;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public final class Sd {
    public C0769yb a = C0769yb.d;
    public final int b = 1;
    public final Ub c = EnumC0061bc.a;
    public final HashMap d = new HashMap();
    public final ArrayList e = new ArrayList();
    public final ArrayList f = new ArrayList();
    public final int g;
    public final int h;
    public final boolean i;
    public final C0646uc j;
    public final boolean k;
    public final Vp l;
    public final Wp m;
    public final ArrayDeque n;

    public Sd() {
        C0646uc c0646uc = Rd.h;
        this.g = 2;
        this.h = 2;
        this.i = true;
        this.j = Rd.h;
        this.k = true;
        this.l = Rd.i;
        this.m = Rd.j;
        this.n = new ArrayDeque();
    }

    public final Rd a() {
        C0261hr c0261hr;
        C0261hr c0261hr2;
        ArrayList arrayList = this.e;
        int size = arrayList.size();
        ArrayList arrayList2 = this.f;
        ArrayList arrayList3 = new ArrayList(arrayList2.size() + size + 3);
        arrayList3.addAll(arrayList);
        Collections.reverse(arrayList3);
        ArrayList arrayList4 = new ArrayList(arrayList2);
        Collections.reverse(arrayList4);
        arrayList3.addAll(arrayList4);
        boolean z = Go.a;
        C0027a9 c0027a9 = AbstractC0058b9.b;
        int i = this.g;
        int i2 = this.h;
        if (i != 2 || i2 != 2) {
            C0089c9 c0089c9 = new C0089c9(c0027a9, i, i2);
            C0261hr c0261hr3 = AbstractC0568rr.a;
            C0261hr c0261hr4 = new C0261hr(Date.class, c0089c9, 0);
            if (z) {
                Fo fo = Go.c;
                fo.getClass();
                c0261hr = new C0261hr(fo.a, new C0089c9(fo, i, i2), 0);
                Fo fo2 = Go.b;
                fo2.getClass();
                c0261hr2 = new C0261hr(fo2.a, new C0089c9(fo2, i, i2), 0);
            } else {
                c0261hr = null;
                c0261hr2 = null;
            }
            arrayList3.add(c0261hr4);
            if (z) {
                arrayList3.add(c0261hr);
                arrayList3.add(c0261hr2);
            }
        }
        C0769yb c0769yb = this.a;
        Ub ub = this.c;
        HashMap map = new HashMap(this.d);
        int i3 = this.b;
        new ArrayList(arrayList);
        new ArrayList(arrayList2);
        return new Rd(c0769yb, ub, map, this.i, this.j, this.k, i3, arrayList3, this.l, this.m, new ArrayList(this.n));
    }
}
