package rikka.shizuku;

/* JADX INFO: loaded from: classes.dex */
public final class Hr implements T7, U7 {
    public static final Hr a = new Hr();

    @Override // rikka.shizuku.V7
    public final V7 b(V7 v7) {
        return AbstractC0744xh.P(this, v7);
    }

    @Override // rikka.shizuku.V7
    public final T7 g(U7 u7) {
        if (AbstractC0046as.c(this, u7)) {
            return this;
        }
        return null;
    }

    @Override // rikka.shizuku.V7
    public final V7 j(U7 u7) {
        return AbstractC0046as.c(this, u7) ? C0152eb.a : this;
    }

    @Override // rikka.shizuku.V7
    public final Object l(Object obj, InterfaceC0585sd interfaceC0585sd) {
        return interfaceC0585sd.f(obj, this);
    }

    @Override // rikka.shizuku.T7
    public final U7 getKey() {
        return this;
    }
}
