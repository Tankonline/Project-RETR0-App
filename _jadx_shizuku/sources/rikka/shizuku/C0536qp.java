package rikka.shizuku;

import java.io.Serializable;

/* JADX INFO: renamed from: rikka.shizuku.qp, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0536qp implements InterfaceC0310jg, Serializable {
    public InterfaceC0124dd a;
    public volatile Object b = Ne.a;
    public final Object c = this;

    public C0536qp(InterfaceC0124dd interfaceC0124dd) {
        this.a = interfaceC0124dd;
    }

    @Override // rikka.shizuku.InterfaceC0310jg
    public final Object getValue() {
        Object objA;
        Object obj = this.b;
        Ne ne = Ne.a;
        if (obj != ne) {
            return obj;
        }
        synchronized (this.c) {
            objA = this.b;
            if (objA == ne) {
                objA = this.a.a();
                this.b = objA;
                this.a = null;
            }
        }
        return objA;
    }

    public final String toString() {
        return this.b != Ne.a ? String.valueOf(getValue()) : "Lazy value not initialized yet.";
    }
}
