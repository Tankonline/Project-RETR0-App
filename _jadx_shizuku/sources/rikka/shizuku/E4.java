package rikka.shizuku;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public final class E4 extends AbstractC0726x {
    @Override // rikka.shizuku.AbstractC0633u
    public final void j(Ys ys, boolean z) throws IOException {
        ys.I(z, 48, this.a);
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final int m(boolean z) {
        int iM = z ? 4 : 3;
        int length = this.a.length;
        for (int i = 0; i < length; i++) {
            iM += this.a[i].d().m(true);
        }
        return iM;
    }

    @Override // rikka.shizuku.AbstractC0726x
    public final AbstractC0079c w() {
        return new C0793z4(r());
    }

    @Override // rikka.shizuku.AbstractC0726x
    public final r x() {
        r[] rVarArrS = s();
        return new C4(C4.s(rVarArrS), rVarArrS);
    }

    @Override // rikka.shizuku.AbstractC0726x
    public final AbstractC0757y y() {
        return new G4(false, this.a);
    }
}
