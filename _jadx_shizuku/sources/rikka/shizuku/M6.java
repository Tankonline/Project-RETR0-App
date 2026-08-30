package rikka.shizuku;

import android.os.Bundle;
import java.util.ArrayList;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class M6 implements InterfaceC0653uj {
    public final /* synthetic */ int a;
    public final /* synthetic */ AbstractActivityC0051b2 b;

    public /* synthetic */ M6(AbstractActivityC0051b2 abstractActivityC0051b2, int i) {
        this.a = i;
        this.b = abstractActivityC0051b2;
    }

    @Override // rikka.shizuku.InterfaceC0653uj
    public final void a() {
        switch (this.a) {
            case 0:
                AbstractActivityC0051b2 abstractActivityC0051b2 = this.b;
                Bundle bundleG = ((R2) abstractActivityC0051b2.e.c).g("android:support:activity-result");
                if (bundleG != null) {
                    N6 n6 = abstractActivityC0051b2.k;
                    n6.getClass();
                    ArrayList<Integer> integerArrayList = bundleG.getIntegerArrayList("KEY_COMPONENT_ACTIVITY_REGISTERED_RCS");
                    ArrayList<String> stringArrayList = bundleG.getStringArrayList("KEY_COMPONENT_ACTIVITY_REGISTERED_KEYS");
                    if (stringArrayList != null && integerArrayList != null) {
                        n6.d = bundleG.getStringArrayList("KEY_COMPONENT_ACTIVITY_LAUNCHED_KEYS");
                        Bundle bundle = bundleG.getBundle("KEY_COMPONENT_ACTIVITY_PENDING_RESULT");
                        Bundle bundle2 = n6.g;
                        bundle2.putAll(bundle);
                        for (int i = 0; i < stringArrayList.size(); i++) {
                            String str = stringArrayList.get(i);
                            HashMap map = n6.b;
                            boolean zContainsKey = map.containsKey(str);
                            HashMap map2 = n6.a;
                            if (zContainsKey) {
                                Integer num = (Integer) map.remove(str);
                                if (!bundle2.containsKey(str)) {
                                    map2.remove(num);
                                }
                            }
                            Integer num2 = integerArrayList.get(i);
                            num2.intValue();
                            String str2 = stringArrayList.get(i);
                            map2.put(num2, str2);
                            map.put(str2, num2);
                        }
                        break;
                    }
                }
                break;
            default:
                Cc cc = (Cc) this.b.s.b;
                cc.l.b(cc, cc, null);
                break;
        }
    }
}
