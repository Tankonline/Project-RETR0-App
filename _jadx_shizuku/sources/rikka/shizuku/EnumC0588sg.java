package rikka.shizuku;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* JADX INFO: renamed from: rikka.shizuku.sg, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class EnumC0588sg {
    public static final EnumC0588sg a;
    public static final EnumC0588sg b;
    public static final EnumC0588sg c;
    public static final EnumC0588sg d;
    public static final EnumC0588sg e;
    public static final /* synthetic */ EnumC0588sg[] f;

    static {
        EnumC0588sg enumC0588sg = new EnumC0588sg("DESTROYED", 0);
        a = enumC0588sg;
        EnumC0588sg enumC0588sg2 = new EnumC0588sg("INITIALIZED", 1);
        b = enumC0588sg2;
        EnumC0588sg enumC0588sg3 = new EnumC0588sg("CREATED", 2);
        c = enumC0588sg3;
        EnumC0588sg enumC0588sg4 = new EnumC0588sg("STARTED", 3);
        d = enumC0588sg4;
        EnumC0588sg enumC0588sg5 = new EnumC0588sg("RESUMED", 4);
        e = enumC0588sg5;
        f = new EnumC0588sg[]{enumC0588sg, enumC0588sg2, enumC0588sg3, enumC0588sg4, enumC0588sg5};
    }

    public static EnumC0588sg[] b() {
        return (EnumC0588sg[]) f.clone();
    }

    public final boolean a(EnumC0588sg enumC0588sg) {
        return compareTo(enumC0588sg) >= 0;
    }
}
