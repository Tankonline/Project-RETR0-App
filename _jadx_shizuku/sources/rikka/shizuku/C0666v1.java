package rikka.shizuku;

import moe.shizuku.manager.adb.AdbPairingClient;
import moe.shizuku.manager.adb.AdbPairingService;

/* JADX INFO: renamed from: rikka.shizuku.v1, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0666v1 extends AbstractC0443np implements InterfaceC0585sd {
    public final /* synthetic */ int e;
    public final /* synthetic */ String f;
    public final /* synthetic */ AdbPairingService g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0666v1(int i, String str, AdbPairingService adbPairingService, G7 g7) {
        super(g7);
        this.e = i;
        this.f = str;
        this.g = adbPairingService;
    }

    @Override // rikka.shizuku.InterfaceC0585sd
    public final Object f(Object obj, Object obj2) throws Throwable {
        C0666v1 c0666v1 = (C0666v1) i((InterfaceC0026a8) obj, (G7) obj2);
        Pe pe = Pe.b;
        c0666v1.j(pe);
        return pe;
    }

    @Override // rikka.shizuku.Q4
    public final G7 i(Object obj, G7 g7) {
        return new C0666v1(this.e, this.f, this.g, g7);
    }

    @Override // rikka.shizuku.Q4
    public final Object j(Object obj) throws Throwable {
        Object c0071bm;
        Pe pe = Pe.b;
        AbstractC0046as.V(obj);
        try {
            try {
                c0071bm = Boolean.valueOf(new AdbPairingClient(this.e, this.f, new C0235h1(new Ys(28, AbstractC0750xn.a))).b());
            } catch (Throwable th) {
                c0071bm = new C0071bm(th);
            }
            Throwable thA = AbstractC0102cm.a(c0071bm);
            AdbPairingService adbPairingService = this.g;
            if (thA != null) {
                AdbPairingService.a(adbPairingService, false, thA);
            }
            if (!(c0071bm instanceof C0071bm)) {
                AdbPairingService.a(adbPairingService, ((Boolean) c0071bm).booleanValue(), null);
            }
            return pe;
        } catch (Throwable th2) {
            th2.printStackTrace();
            return pe;
        }
    }
}
