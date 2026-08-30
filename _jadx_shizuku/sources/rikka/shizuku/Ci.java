package rikka.shizuku;

import android.util.SparseArray;

/* JADX INFO: loaded from: classes.dex */
public final class Ci {
    public final SparseArray a;
    public Va b;

    public Ci(int i) {
        this.a = new SparseArray(i);
    }

    public final void a(Va va, int i, int i2) {
        int iA = va.a(i);
        SparseArray sparseArray = this.a;
        Ci ci = sparseArray == null ? null : (Ci) sparseArray.get(iA);
        if (ci == null) {
            ci = new Ci(1);
            sparseArray.put(va.a(i), ci);
        }
        if (i2 > i) {
            ci.a(va, i + 1, i2);
        } else {
            ci.b = va;
        }
    }
}
