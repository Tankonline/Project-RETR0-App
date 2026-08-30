package rikka.shizuku;

import android.os.Bundle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

/* JADX INFO: renamed from: rikka.shizuku.k2, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0327k2 implements InterfaceC0780ym {
    public final /* synthetic */ int a;
    public final Object b;

    public C0327k2(R2 r2) {
        this.a = 1;
        this.b = new LinkedHashSet();
        r2.S("androidx.savedstate.Restarter", this);
    }

    @Override // rikka.shizuku.InterfaceC0780ym
    public final Bundle a() {
        switch (this.a) {
            case 0:
                Bundle bundle = new Bundle();
                ((AbstractActivityC0051b2) this.b).k().getClass();
                return bundle;
            default:
                Bundle bundleF = AbstractC0046as.f((Hj[]) Arrays.copyOf(new Hj[0], 0));
                List listD0 = AbstractC0671v6.d0((LinkedHashSet) this.b);
                bundleF.putStringArrayList("classes_to_restore", listD0 instanceof ArrayList ? (ArrayList) listD0 : new ArrayList<>(listD0));
                return bundleF;
        }
    }

    public C0327k2(AbstractActivityC0051b2 abstractActivityC0051b2) {
        this.a = 0;
        this.b = abstractActivityC0051b2;
    }
}
