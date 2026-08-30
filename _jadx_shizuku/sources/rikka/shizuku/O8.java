package rikka.shizuku;

/* JADX INFO: loaded from: classes.dex */
public abstract class O8 {
    public static final Q8 a;
    public static final R8 b;

    static {
        Q8 q8 = new Q8();
        q8.c = -1;
        a = q8;
        R8 r8 = new R8();
        r8.c = -1;
        b = r8;
    }

    public static Q8 a(C0233h c0233h) {
        if (c0233h.b < 1) {
            return a;
        }
        Q8 q8 = new Q8(c0233h);
        q8.c = -1;
        return q8;
    }
}
