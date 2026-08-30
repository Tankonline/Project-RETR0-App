package rikka.shizuku;

/* JADX INFO: renamed from: rikka.shizuku.sb, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0583sb implements Mq {
    @Override // rikka.shizuku.Mq
    public final Lq a(Rd rd, C0630tr c0630tr) {
        Class superclass = c0630tr.a;
        if (!Enum.class.isAssignableFrom(superclass) || superclass == Enum.class) {
            return null;
        }
        if (!superclass.isEnum()) {
            superclass = superclass.getSuperclass();
        }
        return new C0614tb(superclass);
    }
}
