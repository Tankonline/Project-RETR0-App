package rikka.shizuku;

import android.os.Trace;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public final class Hd implements Runnable {
    public static final ThreadLocal e = new ThreadLocal();
    public static final Q7 f = new Q7(1);
    public long b;
    public long c;
    public final ArrayList a = new ArrayList();
    public final ArrayList d = new ArrayList();

    public static AbstractC0470ol c(RecyclerView recyclerView, int i, long j) {
        int iH = recyclerView.f.h();
        for (int i2 = 0; i2 < iH; i2++) {
            AbstractC0470ol abstractC0470olK = RecyclerView.K(recyclerView.f.g(i2));
            if (abstractC0470olK.c == i && !abstractC0470olK.f()) {
                return null;
            }
        }
        C0285il c0285il = recyclerView.c;
        if (j == Long.MAX_VALUE) {
            try {
                if (AbstractC0537qq.a()) {
                    Trace.beginSection("RV Prefetch forced - needed next frame");
                }
            } finally {
                recyclerView.U(false);
                Trace.endSection();
            }
        }
        recyclerView.T();
        AbstractC0470ol abstractC0470olK2 = c0285il.k(i, j);
        if (abstractC0470olK2 != null) {
            if (!abstractC0470olK2.e() || abstractC0470olK2.f()) {
                c0285il.a(abstractC0470olK2, false);
            } else {
                c0285il.h(abstractC0470olK2.a);
            }
        }
        return abstractC0470olK2;
    }

    public final void a(RecyclerView recyclerView, int i, int i2) {
        if (recyclerView.s) {
            int[] iArr = RecyclerView.z0;
            if (this.b == 0) {
                this.b = RecyclerView.M();
                recyclerView.post(this);
            }
        }
        Fd fd = recyclerView.f0;
        fd.a = i;
        fd.b = i2;
    }

    /* JADX WARN: Code duplicated, block: B:46:0x00d0  */
    public final void b(long j) {
        Gd gd;
        RecyclerView recyclerView;
        RecyclerView recyclerView2;
        Gd gd2;
        ArrayList arrayList = this.a;
        int size = arrayList.size();
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            RecyclerView recyclerView3 = (RecyclerView) arrayList.get(i3);
            if (recyclerView3.getWindowVisibility() == 0) {
                Fd fd = recyclerView3.f0;
                fd.c(recyclerView3, false);
                i2 += fd.c;
            }
        }
        ArrayList arrayList2 = this.d;
        arrayList2.ensureCapacity(i2);
        int i4 = 0;
        int i5 = 0;
        while (i4 < size) {
            RecyclerView recyclerView4 = (RecyclerView) arrayList.get(i4);
            if (recyclerView4.getWindowVisibility() == 0) {
                Fd fd2 = recyclerView4.f0;
                int iAbs = Math.abs(fd2.b) + Math.abs(fd2.a);
                for (int i6 = i; i6 < fd2.c * 2; i6 += 2) {
                    if (i5 >= arrayList2.size()) {
                        gd2 = new Gd();
                        arrayList2.add(gd2);
                    } else {
                        gd2 = (Gd) arrayList2.get(i5);
                    }
                    int[] iArr = (int[]) fd2.d;
                    int i7 = iArr[i6 + 1];
                    gd2.a = i7 <= iAbs;
                    gd2.b = iAbs;
                    gd2.c = i7;
                    gd2.d = recyclerView4;
                    gd2.e = iArr[i6];
                    i5++;
                }
            }
            i4++;
            i = 0;
        }
        Collections.sort(arrayList2, f);
        for (int i8 = 0; i8 < arrayList2.size() && (recyclerView = (gd = (Gd) arrayList2.get(i8)).d) != null; i8++) {
            AbstractC0470ol abstractC0470olC = c(recyclerView, gd.e, gd.a ? Long.MAX_VALUE : j);
            if (abstractC0470olC != null && abstractC0470olC.b != null && abstractC0470olC.e() && !abstractC0470olC.f() && (recyclerView2 = (RecyclerView) abstractC0470olC.b.get()) != null) {
                if (recyclerView2.C && recyclerView2.f.h() != 0) {
                    C0458o9 c0458o9 = recyclerView2.L;
                    if (c0458o9 != null) {
                        c0458o9.h();
                    }
                    AbstractC0162el abstractC0162el = recyclerView2.n;
                    C0285il c0285il = recyclerView2.c;
                    if (abstractC0162el != null) {
                        abstractC0162el.j0(c0285il);
                        recyclerView2.n.k0(c0285il);
                    }
                    c0285il.a.clear();
                    c0285il.f();
                }
                Fd fd3 = recyclerView2.f0;
                fd3.c(recyclerView2, true);
                if (fd3.c != 0) {
                    try {
                        Trace.beginSection(j == Long.MAX_VALUE ? "RV Nested Prefetch" : "RV Nested Prefetch forced - needed next frame");
                        C0408ml c0408ml = recyclerView2.g0;
                        Yk yk = recyclerView2.m;
                        c0408ml.d = 1;
                        c0408ml.e = yk.a();
                        c0408ml.g = false;
                        c0408ml.h = false;
                        c0408ml.i = false;
                        for (int i9 = 0; i9 < fd3.c * 2; i9 += 2) {
                            c(recyclerView2, ((int[]) fd3.d)[i9], j);
                        }
                        Trace.endSection();
                    } catch (Throwable th) {
                        Trace.endSection();
                        throw th;
                    }
                }
            }
            gd.a = false;
            gd.b = 0;
            gd.c = 0;
            gd.d = null;
            gd.e = 0;
        }
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            Trace.beginSection("RV Prefetch");
            ArrayList arrayList = this.a;
            if (!arrayList.isEmpty()) {
                int size = arrayList.size();
                long jMax = 0;
                for (int i = 0; i < size; i++) {
                    RecyclerView recyclerView = (RecyclerView) arrayList.get(i);
                    if (recyclerView.getWindowVisibility() == 0) {
                        jMax = Math.max(recyclerView.getDrawingTime(), jMax);
                    }
                }
                if (jMax != 0) {
                    b(TimeUnit.MILLISECONDS.toNanos(jMax) + this.c);
                }
            }
        } finally {
            this.b = 0L;
            Trace.endSection();
        }
    }
}
