package rikka.shizuku;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public final class Rr implements InterfaceC0310jg, Serializable {
    public InterfaceC0124dd a;
    public Object b;

    @Override // rikka.shizuku.InterfaceC0310jg
    public final Object getValue() {
        if (this.b == Ne.a) {
            this.b = this.a.a();
            this.a = null;
        }
        return this.b;
    }

    public final String toString() {
        return this.b != Ne.a ? String.valueOf(getValue()) : "Lazy value not initialized yet.";
    }
}
