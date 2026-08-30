package rikka.shizuku;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/* JADX INFO: renamed from: rikka.shizuku.vl, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0686vl extends AbstractC0046as {
    public final Method i = Class.class.getMethod("isRecord", null);
    public final Method j = Class.class.getMethod("getRecordComponents", null);
    public final Method k;
    public final Method l;

    public C0686vl() throws ClassNotFoundException {
        Class<?> cls = Class.forName("java.lang.reflect.RecordComponent");
        this.k = cls.getMethod("getName", null);
        this.l = cls.getMethod("getType", null);
    }

    @Override // rikka.shizuku.AbstractC0046as
    public final boolean G(Class cls) {
        try {
            return ((Boolean) this.i.invoke(cls, null)).booleanValue();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Unexpected ReflectiveOperationException occurred (Gson 2.13.1). To support Java records, reflection is utilized to read out information about records. All these invocations happens after it is established that records exist in the JVM. This exception is unexpected behavior.", e);
        }
    }

    @Override // rikka.shizuku.AbstractC0046as
    public final Method r(Class cls, Field field) {
        try {
            return cls.getMethod(field.getName(), null);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Unexpected ReflectiveOperationException occurred (Gson 2.13.1). To support Java records, reflection is utilized to read out information about records. All these invocations happens after it is established that records exist in the JVM. This exception is unexpected behavior.", e);
        }
    }

    @Override // rikka.shizuku.AbstractC0046as
    public final Constructor s(Class cls) {
        try {
            Object[] objArr = (Object[]) this.j.invoke(cls, null);
            Class<?>[] clsArr = new Class[objArr.length];
            for (int i = 0; i < objArr.length; i++) {
                clsArr[i] = (Class) this.l.invoke(objArr[i], null);
            }
            return cls.getDeclaredConstructor(clsArr);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Unexpected ReflectiveOperationException occurred (Gson 2.13.1). To support Java records, reflection is utilized to read out information about records. All these invocations happens after it is established that records exist in the JVM. This exception is unexpected behavior.", e);
        }
    }

    @Override // rikka.shizuku.AbstractC0046as
    public final String[] y(Class cls) {
        try {
            Object[] objArr = (Object[]) this.j.invoke(cls, null);
            String[] strArr = new String[objArr.length];
            for (int i = 0; i < objArr.length; i++) {
                strArr[i] = (String) this.k.invoke(objArr[i], null);
            }
            return strArr;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Unexpected ReflectiveOperationException occurred (Gson 2.13.1). To support Java records, reflection is utilized to read out information about records. All these invocations happens after it is established that records exist in the JVM. This exception is unexpected behavior.", e);
        }
    }
}
