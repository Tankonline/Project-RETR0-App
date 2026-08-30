package rikka.shizuku;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public final class G4 extends AbstractC0757y {
    @Override // rikka.shizuku.AbstractC0633u
    public final void j(Ys ys, boolean z) throws IOException {
        ys.I(z, 49, this.a);
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final int m(boolean z) {
        int iM = z ? 4 : 3;
        for (InterfaceC0202g interfaceC0202g : this.a) {
            iM += interfaceC0202g.d().m(true);
        }
        return iM;
    }
}
