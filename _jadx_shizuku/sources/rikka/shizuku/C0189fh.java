package rikka.shizuku;

/* JADX INFO: renamed from: rikka.shizuku.fh, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0189fh extends AbstractC0220gh implements InterfaceC0681vg {
    public final InterfaceC0743xg e;
    public final /* synthetic */ Pi f;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0189fh(Pi pi, InterfaceC0743xg interfaceC0743xg, InterfaceC0313jj interfaceC0313jj) {
        super(pi, interfaceC0313jj);
        this.f = pi;
        this.e = interfaceC0743xg;
    }

    @Override // rikka.shizuku.InterfaceC0681vg
    public final void a(InterfaceC0743xg interfaceC0743xg, EnumC0557rg enumC0557rg) {
        InterfaceC0743xg interfaceC0743xg2 = this.e;
        EnumC0588sg enumC0588sg = interfaceC0743xg2.e().c;
        if (enumC0588sg != EnumC0588sg.a) {
            EnumC0588sg enumC0588sg2 = null;
            while (enumC0588sg2 != enumC0588sg) {
                b(e());
                enumC0588sg2 = enumC0588sg;
                enumC0588sg = interfaceC0743xg2.e().c;
            }
            return;
        }
        Pi pi = this.f;
        pi.getClass();
        Pi.a("removeObserver");
        AbstractC0220gh abstractC0220gh = (AbstractC0220gh) pi.b.b(this.a);
        if (abstractC0220gh == null) {
            return;
        }
        abstractC0220gh.c();
        abstractC0220gh.b(false);
    }

    @Override // rikka.shizuku.AbstractC0220gh
    public final void c() {
        this.e.e().f(this);
    }

    @Override // rikka.shizuku.AbstractC0220gh
    public final boolean d(InterfaceC0743xg interfaceC0743xg) {
        return this.e == interfaceC0743xg;
    }

    @Override // rikka.shizuku.AbstractC0220gh
    public final boolean e() {
        return this.e.e().c.a(EnumC0588sg.d);
    }
}
