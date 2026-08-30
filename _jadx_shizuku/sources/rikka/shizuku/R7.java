package rikka.shizuku;

import dalvik.system.PathClassLoader;

/* JADX INFO: loaded from: classes.dex */
public final class R7 extends PathClassLoader {
    @Override // java.lang.ClassLoader
    public final Class loadClass(String str) {
        if (Object.class.getName().equals(str)) {
            return Object.class;
        }
        try {
            return findClass(str);
        } catch (ClassNotFoundException unused) {
            if (V.l().getName().equals(str)) {
                return C0279ie.class;
            }
            if (V.C().getName().equals(str)) {
                return C0339ke.class;
            }
            return Class.class.getName().equals(str) ? C0248he.class : super.loadClass(str);
        }
    }
}
