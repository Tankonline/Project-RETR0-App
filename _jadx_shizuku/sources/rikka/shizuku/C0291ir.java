package rikka.shizuku;

import java.io.IOException;
import java.util.BitSet;

/* JADX INFO: renamed from: rikka.shizuku.ir, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0291ir extends Lq {
    @Override // rikka.shizuku.Lq
    public final Object b(Vf vf) throws IOException {
        boolean zL;
        BitSet bitSet = new BitSet();
        vf.a();
        int iV = vf.v();
        int i = 0;
        while (iV != 2) {
            int iJ = AbstractC0535qo.j(iV);
            if (iJ == 5 || iJ == 6) {
                int iN = vf.n();
                if (iN == 0) {
                    zL = false;
                } else {
                    if (iN != 1) {
                        throw new Rf("Invalid bitset value " + iN + ", expected 0 or 1; at path " + vf.h(true));
                    }
                    zL = true;
                }
            } else {
                if (iJ != 7) {
                    throw new Rf("Invalid bitset value type: " + AbstractC0535qo.k(iV) + "; at path " + vf.h(false));
                }
                zL = vf.l();
            }
            if (zL) {
                bitSet.set(i);
            }
            i++;
            iV = vf.v();
        }
        vf.e();
        return bitSet;
    }

    @Override // rikka.shizuku.Lq
    public final void c(Wf wf, Object obj) throws IOException {
        BitSet bitSet = (BitSet) obj;
        wf.b();
        int length = bitSet.length();
        for (int i = 0; i < length; i++) {
            wf.n(bitSet.get(i) ? 1L : 0L);
        }
        wf.e();
    }
}
