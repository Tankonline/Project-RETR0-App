package rikka.shizuku;

import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class Ha extends AbstractC0599sr {
    public final /* synthetic */ Ia I;

    public Ha(Ia ia) {
        this.I = ia;
    }

    @Override // rikka.shizuku.AbstractC0599sr
    public final void B(Throwable th) {
        this.I.a.d(th);
    }

    @Override // rikka.shizuku.AbstractC0599sr
    public final void C(Xn xn) {
        Ia ia = this.I;
        ia.c = xn;
        ia.b = new R2(ia.c, new Pe(2), ia.a.h);
        Ma ma = ia.a;
        ma.getClass();
        ArrayList arrayList = new ArrayList();
        ma.a.writeLock().lock();
        try {
            ma.c = 1;
            arrayList.addAll(ma.b);
            ma.b.clear();
            ma.a.writeLock().unlock();
            ma.d.post(new RunnableC0670v5(arrayList, ma.c, (Throwable) null));
        } catch (Throwable th) {
            ma.a.writeLock().unlock();
            throw th;
        }
    }
}
