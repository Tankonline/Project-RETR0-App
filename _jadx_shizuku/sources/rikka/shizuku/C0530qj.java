package rikka.shizuku;

/* JADX INFO: renamed from: rikka.shizuku.qj, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0530qj implements InterfaceC0681vg, InterfaceC0763y5 {
    public final C0805zg a;
    public final Ic b;
    public C0560rj c;
    public final /* synthetic */ C0622tj d;

    public C0530qj(C0622tj c0622tj, C0805zg c0805zg, Ic ic) {
        this.d = c0622tj;
        this.a = c0805zg;
        this.b = ic;
        c0805zg.a(this);
    }

    @Override // rikka.shizuku.InterfaceC0681vg
    public final void a(InterfaceC0743xg interfaceC0743xg, EnumC0557rg enumC0557rg) {
        if (enumC0557rg == EnumC0557rg.ON_START) {
            C0622tj c0622tj = this.d;
            C0084c4 c0084c4 = c0622tj.b;
            Ic ic = this.b;
            c0084c4.addLast(ic);
            C0560rj c0560rj = new C0560rj(c0622tj, ic);
            ic.b.add(c0560rj);
            c0622tj.c();
            ic.c = new C0591sj(0, c0622tj, C0622tj.class, "updateEnabledCallbacks", "updateEnabledCallbacks()V", 1);
            this.c = c0560rj;
            return;
        }
        if (enumC0557rg != EnumC0557rg.ON_STOP) {
            if (enumC0557rg == EnumC0557rg.ON_DESTROY) {
                cancel();
            }
        } else {
            C0560rj c0560rj2 = this.c;
            if (c0560rj2 != null) {
                c0560rj2.cancel();
            }
        }
    }

    @Override // rikka.shizuku.InterfaceC0763y5
    public final void cancel() {
        this.a.f(this);
        this.b.b.remove(this);
        C0560rj c0560rj = this.c;
        if (c0560rj != null) {
            c0560rj.cancel();
        }
        this.c = null;
    }
}
