package rikka.shizuku;

import java.io.FileDescriptor;
import java.lang.reflect.Method;

/* JADX INFO: renamed from: rikka.shizuku.cc, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0092cc {
    public static final Method a;

    static {
        try {
            Method declaredMethod = FileDescriptor.class.getDeclaredMethod("getInt$", null);
            a = declaredMethod;
            declaredMethod.setAccessible(true);
            FileDescriptor.class.getDeclaredMethod("setInt$", Integer.TYPE).setAccessible(true);
        } catch (ReflectiveOperationException unused) {
        }
    }
}
