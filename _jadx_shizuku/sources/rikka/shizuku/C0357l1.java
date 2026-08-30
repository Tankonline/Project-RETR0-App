package rikka.shizuku;

import android.content.Context;
import android.net.nsd.NsdManager;

/* JADX INFO: renamed from: rikka.shizuku.l1, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0357l1 {
    public final String a;
    public final InterfaceC0313jj b;
    public boolean c;
    public boolean d;
    public String e;
    public final C0295j1 f = new C0295j1(this);
    public final NsdManager g;

    public C0357l1(Context context, String str, InterfaceC0313jj interfaceC0313jj) {
        this.a = str;
        this.b = interfaceC0313jj;
        this.g = (NsdManager) context.getSystemService(NsdManager.class);
    }

    public final void a() {
        if (this.d) {
            return;
        }
        this.d = true;
        if (this.c) {
            return;
        }
        this.g.discoverServices(this.a, 1, this.f);
    }

    public final void b() {
        if (this.d) {
            this.d = false;
            if (this.c) {
                this.g.stopServiceDiscovery(this.f);
            }
        }
    }
}
