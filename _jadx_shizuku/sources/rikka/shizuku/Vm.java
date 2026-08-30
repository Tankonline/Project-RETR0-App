package rikka.shizuku;

import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public final class Vm implements Rm {
    public final /* synthetic */ Iterator a;

    public Vm(Iterator it) {
        this.a = it;
    }

    @Override // rikka.shizuku.Rm
    public final Iterator iterator() {
        return this.a;
    }
}
