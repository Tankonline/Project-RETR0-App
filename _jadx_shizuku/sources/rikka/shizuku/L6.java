package rikka.shizuku;

import android.os.Bundle;
import java.util.ArrayList;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class L6 implements InterfaceC0780ym {
    public final /* synthetic */ int a;
    public final /* synthetic */ Object b;

    public /* synthetic */ L6(int i, Object obj) {
        this.a = i;
        this.b = obj;
    }

    @Override // rikka.shizuku.InterfaceC0780ym
    public final Bundle a() {
        switch (this.a) {
            case 0:
                AbstractActivityC0051b2 abstractActivityC0051b2 = (AbstractActivityC0051b2) this.b;
                Bundle bundle = new Bundle();
                N6 n6 = abstractActivityC0051b2.k;
                n6.getClass();
                HashMap map = n6.b;
                bundle.putIntegerArrayList("KEY_COMPONENT_ACTIVITY_REGISTERED_RCS", new ArrayList<>(map.values()));
                bundle.putStringArrayList("KEY_COMPONENT_ACTIVITY_REGISTERED_KEYS", new ArrayList<>(map.keySet()));
                bundle.putStringArrayList("KEY_COMPONENT_ACTIVITY_LAUNCHED_KEYS", new ArrayList<>(n6.d));
                bundle.putBundle("KEY_COMPONENT_ACTIVITY_PENDING_RESULT", (Bundle) n6.g.clone());
                return bundle;
            case 1:
                AbstractActivityC0051b2 abstractActivityC0051b3 = (AbstractActivityC0051b2) this.b;
                while (AbstractActivityC0051b2.n(((Cc) abstractActivityC0051b3.s.b).l)) {
                }
                abstractActivityC0051b3.t.d(EnumC0557rg.ON_STOP);
                return new Bundle();
            default:
                return ((Qc) this.b).X();
        }
    }
}
