package rikka.shizuku;

import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class Oc implements Nc {
    public final int a;
    public final /* synthetic */ Qc b;

    public Oc(Qc qc, int i) {
        this.b = qc;
        this.a = i;
    }

    @Override // rikka.shizuku.Nc
    public final boolean a(ArrayList arrayList, ArrayList arrayList2) {
        Qc qc = this.b;
        Ac ac = qc.z;
        int i = this.a;
        if (ac == null || i >= 0 || !ac.h().S(-1, 0)) {
            return qc.T(arrayList, arrayList2, i, 1);
        }
        return false;
    }
}
