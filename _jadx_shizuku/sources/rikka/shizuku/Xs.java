package rikka.shizuku;

/* JADX INFO: loaded from: classes.dex */
public interface Xs {
    default Vs a(Class cls) {
        throw new UnsupportedOperationException("`Factory.create(String, CreationExtras)` is not implemented. You may need to override the method and provide a custom implementation. Note that using `Factory.create(String)` is not supported and considered an error.");
    }

    default Vs b(C0178f6 c0178f6, Oi oi) {
        return d(c0178f6.a(), oi);
    }

    default Vs d(Class cls, Oi oi) {
        return a(cls);
    }
}
