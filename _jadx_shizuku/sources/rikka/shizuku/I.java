package rikka.shizuku;

/* JADX INFO: loaded from: classes.dex */
public abstract class I implements T7 {
    public final U7 a;

    public I(U7 u7) {
        this.a = u7;
    }

    @Override // rikka.shizuku.V7
    public final V7 b(V7 v7) {
        return AbstractC0744xh.P(this, v7);
    }

    @Override // rikka.shizuku.V7
    public T7 g(U7 u7) {
        if (AbstractC0046as.c(getKey(), u7)) {
            return this;
        }
        return null;
    }

    @Override // rikka.shizuku.T7
    public final U7 getKey() {
        return this.a;
    }

    @Override // rikka.shizuku.V7
    public V7 j(U7 u7) {
        return AbstractC0046as.c(getKey(), u7) ? C0152eb.a : this;
    }

    @Override // rikka.shizuku.V7
    public final Object l(Object obj, InterfaceC0585sd interfaceC0585sd) {
        return interfaceC0585sd.f(obj, this);
    }
}
