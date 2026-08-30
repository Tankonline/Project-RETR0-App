package rikka.shizuku;

import java.util.ArrayList;

/* JADX INFO: renamed from: rikka.shizuku.e6, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0147e6 implements InterfaceC0242h8 {
    public final ArrayList a = new ArrayList();
    public final ArrayList b = new ArrayList();

    @Override // rikka.shizuku.InterfaceC0242h8
    public final int a(T4 t4, int i) {
        Object obj = t4.d.get(i);
        int i2 = 0;
        while (true) {
            ArrayList arrayList = this.a;
            if (i2 >= arrayList.size()) {
                return -1;
            }
            if (((Class) arrayList.get(i2)).isAssignableFrom(obj.getClass())) {
                return i2;
            }
            i2++;
        }
    }

    @Override // rikka.shizuku.InterfaceC0242h8
    public final C0759y1 b(int i) {
        return (C0759y1) this.b.get(i);
    }

    public final void c(Class cls, C0759y1 c0759y1) {
        ArrayList arrayList = this.a;
        int iIndexOf = arrayList.indexOf(cls);
        ArrayList arrayList2 = this.b;
        if (iIndexOf != -1) {
            arrayList2.set(iIndexOf, c0759y1);
        } else {
            arrayList.add(cls);
            arrayList2.add(c0759y1);
        }
    }
}
