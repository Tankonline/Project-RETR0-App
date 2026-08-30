package rikka.shizuku;

import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: classes.dex */
public final class Nf implements Mq {
    public static final Mf c;
    public final C0672v7 a;
    public final ConcurrentHashMap b = new ConcurrentHashMap();

    static {
        int i = 0;
        c = new Mf(i);
        new Mf(i);
    }

    public Nf(C0672v7 c0672v7) {
        this.a = c0672v7;
    }

    @Override // rikka.shizuku.Mq
    public final Lq a(Rd rd, C0630tr c0630tr) {
        Lf lf = (Lf) c0630tr.a.getAnnotation(Lf.class);
        if (lf == null) {
            return null;
        }
        return b(this.a, rd, c0630tr, lf, true);
    }

    public final Lq b(C0672v7 c0672v7, Rd rd, C0630tr c0630tr, Lf lf, boolean z) {
        Lq lqA;
        Mq mq;
        Object objD = c0672v7.b(new C0630tr(lf.value()), true).d();
        boolean zNullSafe = lf.nullSafe();
        if (objD instanceof Lq) {
            lqA = (Lq) objD;
        } else {
            if (!(objD instanceof Mq)) {
                throw new IllegalArgumentException("Invalid attempt to bind an instance of " + objD.getClass().getName() + " as a @JsonAdapter for " + X8.a0(c0630tr.b) + ". @JsonAdapter value must be a TypeAdapter, TypeAdapterFactory, JsonSerializer or JsonDeserializer.");
            }
            Mq mq2 = (Mq) objD;
            if (z && (mq = (Mq) this.b.putIfAbsent(c0630tr.a, mq2)) != null) {
                mq2 = mq;
            }
            lqA = mq2.a(rd, c0630tr);
        }
        return (lqA == null || !zNullSafe) ? lqA : lqA.a();
    }
}
