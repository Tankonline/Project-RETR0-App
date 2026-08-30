package rikka.shizuku;

import java.util.ArrayList;

/* JADX INFO: renamed from: rikka.shizuku.jm, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0316jm {
    public AbstractC0725wt a;
    public ArrayList b;

    public static long a(M9 m9, long j) {
        AbstractC0725wt abstractC0725wt = m9.d;
        if (abstractC0725wt instanceof C0432ne) {
            return j;
        }
        ArrayList arrayList = m9.k;
        int size = arrayList.size();
        long jMin = j;
        for (int i = 0; i < size; i++) {
            K9 k9 = (K9) arrayList.get(i);
            if (k9 instanceof M9) {
                M9 m10 = (M9) k9;
                if (m10.d != abstractC0725wt) {
                    jMin = Math.min(jMin, a(m10, ((long) m10.f) + j));
                }
            }
        }
        if (m9 != abstractC0725wt.i) {
            return jMin;
        }
        long j2 = abstractC0725wt.j();
        M9 m11 = abstractC0725wt.h;
        long j3 = j - j2;
        return Math.min(Math.min(jMin, a(m11, j3)), j3 - ((long) m11.f));
    }

    public static long b(M9 m9, long j) {
        AbstractC0725wt abstractC0725wt = m9.d;
        if (abstractC0725wt instanceof C0432ne) {
            return j;
        }
        ArrayList arrayList = m9.k;
        int size = arrayList.size();
        long jMax = j;
        for (int i = 0; i < size; i++) {
            K9 k9 = (K9) arrayList.get(i);
            if (k9 instanceof M9) {
                M9 m10 = (M9) k9;
                if (m10.d != abstractC0725wt) {
                    jMax = Math.max(jMax, b(m10, ((long) m10.f) + j));
                }
            }
        }
        if (m9 != abstractC0725wt.h) {
            return jMax;
        }
        long j2 = abstractC0725wt.j();
        M9 m11 = abstractC0725wt.i;
        long j3 = j + j2;
        return Math.max(Math.max(jMax, b(m11, j3)), j3 - ((long) m11.f));
    }
}
