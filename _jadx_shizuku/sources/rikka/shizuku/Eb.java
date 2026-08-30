package rikka.shizuku;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
public final class Eb extends AbstractC0417n {
    public Hashtable a;
    public Vector b;

    @Override // rikka.shizuku.InterfaceC0202g
    public final AbstractC0633u d() {
        Vector vector = this.b;
        C0233h c0233h = new C0233h(vector.size());
        Enumeration enumerationElements = vector.elements();
        while (enumerationElements.hasMoreElements()) {
            c0233h.d((Db) this.a.get((C0510q) enumerationElements.nextElement()));
        }
        D8 d8 = new D8(c0233h);
        d8.c = -1;
        return d8;
    }
}
