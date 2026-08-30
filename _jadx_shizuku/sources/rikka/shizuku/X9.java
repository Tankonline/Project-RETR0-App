package rikka.shizuku;

import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class X9 extends M9 {
    public int m;

    public X9(AbstractC0725wt abstractC0725wt) {
        super(abstractC0725wt);
        if (abstractC0725wt instanceof Ae) {
            this.e = 2;
        } else {
            this.e = 3;
        }
    }

    @Override // rikka.shizuku.M9
    public final void d(int i) {
        if (this.j) {
            return;
        }
        this.j = true;
        this.g = i;
        ArrayList arrayList = this.k;
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            K9 k9 = (K9) obj;
            k9.a(k9);
        }
    }
}
