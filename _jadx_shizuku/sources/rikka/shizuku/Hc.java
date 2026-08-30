package rikka.shizuku;

import android.util.Log;
import java.util.ArrayList;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class Hc implements L0 {
    public final /* synthetic */ int a;
    public final /* synthetic */ Qc b;

    public /* synthetic */ Hc(Qc qc, int i) {
        this.a = i;
        this.b = qc;
    }

    @Override // rikka.shizuku.L0
    public final void b(Object obj) {
        switch (this.a) {
            case 0:
                Map map = (Map) obj;
                ArrayList arrayList = new ArrayList(map.values());
                int[] iArr = new int[arrayList.size()];
                for (int i = 0; i < arrayList.size(); i++) {
                    iArr[i] = ((Boolean) arrayList.get(i)).booleanValue() ? 0 : -1;
                }
                Qc qc = this.b;
                Mc mc = (Mc) qc.F.pollFirst();
                if (mc == null) {
                    Log.w("FragmentManager", "No permissions were requested for " + this);
                } else {
                    Xn xn = qc.c;
                    String str = mc.a;
                    if (xn.i(str) == null) {
                        Log.w("FragmentManager", "Permission request result delivered for unknown Fragment " + str);
                    }
                }
                break;
            case 1:
                K0 k0 = (K0) obj;
                Qc qc2 = this.b;
                Mc mc2 = (Mc) qc2.F.pollLast();
                if (mc2 == null) {
                    Log.w("FragmentManager", "No Activities were started for result for " + this);
                } else {
                    Xn xn2 = qc2.c;
                    String str2 = mc2.a;
                    Ac acI = xn2.i(str2);
                    if (acI == null) {
                        Log.w("FragmentManager", "Activity result delivered for unknown Fragment " + str2);
                    } else {
                        acI.t(mc2.b, k0.a, k0.b);
                    }
                }
                break;
            default:
                K0 k1 = (K0) obj;
                Qc qc3 = this.b;
                Mc mc3 = (Mc) qc3.F.pollFirst();
                if (mc3 == null) {
                    Log.w("FragmentManager", "No IntentSenders were started for " + this);
                } else {
                    Xn xn3 = qc3.c;
                    String str3 = mc3.a;
                    Ac acI2 = xn3.i(str3);
                    if (acI2 == null) {
                        Log.w("FragmentManager", "Intent Sender result delivered for unknown Fragment " + str3);
                    } else {
                        acI2.t(mc3.b, k1.a, k1.b);
                    }
                }
                break;
        }
    }
}
