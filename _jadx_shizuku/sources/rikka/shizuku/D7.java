package rikka.shizuku;

/* JADX INFO: loaded from: classes.dex */
public final class D7 implements InterfaceC0026a8 {
    public final V7 a;

    public D7(V7 v7) {
        this.a = v7;
    }

    @Override // rikka.shizuku.InterfaceC0026a8
    public final V7 f() {
        return this.a;
    }

    public final String toString() {
        return "CoroutineScope(coroutineContext=" + this.a + ')';
    }
}
