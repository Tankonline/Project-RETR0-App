package rikka.shizuku;

/* JADX INFO: renamed from: rikka.shizuku.yg, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0774yg {
    public EnumC0588sg a;
    public InterfaceC0681vg b;

    public final void a(InterfaceC0743xg interfaceC0743xg, EnumC0557rg enumC0557rg) {
        EnumC0588sg enumC0588sgA = enumC0557rg.a();
        EnumC0588sg enumC0588sg = this.a;
        if (enumC0588sgA.compareTo(enumC0588sg) < 0) {
            enumC0588sg = enumC0588sgA;
        }
        this.a = enumC0588sg;
        this.b.a(interfaceC0743xg, enumC0557rg);
        this.a = enumC0588sgA;
    }
}
