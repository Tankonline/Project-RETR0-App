package rikka.shizuku;

import java.util.ArrayList;

/* JADX INFO: renamed from: rikka.shizuku.if, reason: invalid class name */
/* JADX INFO: loaded from: classes.dex */
public final class Cif implements InterfaceC0242h8 {
    public final ArrayList a = new ArrayList();
    public final ArrayList b = new ArrayList();

    @Override // rikka.shizuku.InterfaceC0242h8
    public final int a(T4 t4, int i) {
        return ((Integer) this.b.get(i)).intValue();
    }

    @Override // rikka.shizuku.InterfaceC0242h8
    public final C0759y1 b(int i) {
        return (C0759y1) this.a.get(i);
    }
}
