package rikka.shizuku;

/* JADX INFO: loaded from: classes.dex */
public abstract class Sk {
    public static final O a;

    static {
        Integer num = Cf.a;
        a = (num == null || num.intValue() >= 34) ? new Wj() : new Ob();
    }
}
