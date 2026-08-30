package rikka.shizuku;

import android.os.Bundle;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: renamed from: rikka.shizuku.vm, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0687vm implements InterfaceC0780ym {
    public final R2 a;
    public boolean b;
    public Bundle c;
    public final C0536qp d;

    public C0687vm(R2 r2, InterfaceC0047at interfaceC0047at) {
        this.a = r2;
        this.d = new C0536qp(new C0419n1(3, interfaceC0047at));
    }

    @Override // rikka.shizuku.InterfaceC0780ym
    public final Bundle a() {
        Bundle bundleF = AbstractC0046as.f((Hj[]) Arrays.copyOf(new Hj[0], 0));
        Bundle bundle = this.c;
        if (bundle != null) {
            bundleF.putAll(bundle);
        }
        Iterator it = b().b.entrySet().iterator();
        if (!it.hasNext()) {
            this.b = false;
            return bundleF;
        }
        Map.Entry entry = (Map.Entry) it.next();
        ((AbstractC0563rm) entry.getValue()).getClass();
        throw null;
    }

    public final C0718wm b() {
        return (C0718wm) this.d.getValue();
    }
}
