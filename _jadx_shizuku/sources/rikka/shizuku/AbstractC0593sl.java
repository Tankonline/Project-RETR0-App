package rikka.shizuku;

/* JADX INFO: renamed from: rikka.shizuku.sl, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0593sl {
    public static final C0624tl a;

    static {
        C0624tl c0624tl = null;
        try {
            c0624tl = (C0624tl) Class.forName("kotlin.reflect.jvm.internal.ReflectionFactoryImpl").newInstance();
        } catch (ClassCastException | ClassNotFoundException | IllegalAccessException | InstantiationException unused) {
        }
        if (c0624tl == null) {
            c0624tl = new C0624tl();
        }
        a = c0624tl;
    }

    public static C0178f6 a(Class cls) {
        a.getClass();
        return new C0178f6(cls);
    }
}
