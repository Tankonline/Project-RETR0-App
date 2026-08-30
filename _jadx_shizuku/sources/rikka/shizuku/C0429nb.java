package rikka.shizuku;

import android.content.res.TypedArray;
import android.util.SparseArray;

/* JADX INFO: renamed from: rikka.shizuku.nb, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0429nb {
    public final SparseArray a = new SparseArray();
    public final C0460ob b;
    public final int c;
    public final int d;

    public C0429nb(C0460ob c0460ob, K3 k3) {
        this.b = c0460ob;
        TypedArray typedArray = (TypedArray) k3.a;
        this.c = typedArray.getResourceId(28, 0);
        this.d = typedArray.getResourceId(52, 0);
    }
}
