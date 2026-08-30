package rikka.shizuku;

import android.util.Log;
import dalvik.system.VMRuntime;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import sun.misc.Unsafe;

/* JADX INFO: renamed from: rikka.shizuku.se, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0586se {
    public static final Unsafe a;
    public static final long b;
    public static final long c;
    public static final long d;
    public static final long e;
    public static final long f;

    static {
        long jObjectFieldOffset;
        try {
            Unsafe unsafe = (Unsafe) Unsafe.class.getDeclaredMethod("getUnsafe", null).invoke(null, null);
            a = unsafe;
            R7 r7 = new R7(System.getProperty("java.boot.class.path", "").split(":", 2)[0], null);
            Class clsLoadClass = r7.loadClass(V.l().getName());
            Class clsLoadClass2 = r7.loadClass(V.C().getName());
            Class clsLoadClass3 = r7.loadClass(Class.class.getName());
            b = unsafe.objectFieldOffset(clsLoadClass.getDeclaredField("artMethod"));
            unsafe.objectFieldOffset(clsLoadClass.getDeclaredField("declaringClass"));
            c = unsafe.objectFieldOffset(clsLoadClass2.getDeclaredField("artFieldOrMethod"));
            try {
                jObjectFieldOffset = unsafe.objectFieldOffset(clsLoadClass3.getDeclaredField("fields"));
            } catch (NoSuchFieldException unused) {
                Unsafe unsafe2 = a;
                jObjectFieldOffset = unsafe2.objectFieldOffset(clsLoadClass3.getDeclaredField("iFields"));
                unsafe2.objectFieldOffset(clsLoadClass3.getDeclaredField("sFields"));
            }
            Unsafe unsafe3 = a;
            long jObjectFieldOffset2 = unsafe3.objectFieldOffset(clsLoadClass3.getDeclaredField("methods"));
            d = jObjectFieldOffset2;
            Method declaredMethod = C0370le.class.getDeclaredMethod("a", null);
            Method declaredMethod2 = C0370le.class.getDeclaredMethod("b", null);
            declaredMethod.setAccessible(true);
            declaredMethod2.setAccessible(true);
            MethodHandle methodHandleUnreflect = MethodHandles.lookup().unreflect(declaredMethod);
            MethodHandle methodHandleUnreflect2 = MethodHandles.lookup().unreflect(declaredMethod2);
            long j = c;
            long j2 = unsafe3.getLong(methodHandleUnreflect, j);
            long j3 = unsafe3.getLong(methodHandleUnreflect2, j);
            long j4 = unsafe3.getLong(C0370le.class, jObjectFieldOffset2);
            long j5 = j3 - j2;
            e = j5;
            f = (j2 - j4) - j5;
            Field declaredField = C0370le.class.getDeclaredField("i");
            Field declaredField2 = C0370le.class.getDeclaredField("j");
            declaredField.setAccessible(true);
            declaredField2.setAccessible(true);
            MethodHandle methodHandleUnreflectGetter = MethodHandles.lookup().unreflectGetter(declaredField);
            MethodHandle methodHandleUnreflectGetter2 = MethodHandles.lookup().unreflectGetter(declaredField2);
            unsafe3.getLong(methodHandleUnreflectGetter, j);
            unsafe3.getLong(methodHandleUnreflectGetter2, j);
            unsafe3.getLong(C0370le.class, jObjectFieldOffset);
        } catch (ReflectiveOperationException e2) {
            Log.e("HiddenApiBypass", "Initialize error", e2);
            throw new ExceptionInInitializerError(e2);
        }
    }

    public static Object a(Object obj, String str, Object... objArr) throws NoSuchMethodException {
        if (obj != null && !VMRuntime.class.isInstance(obj)) {
            throw new IllegalArgumentException("this object is not an instance of the given class");
        }
        Method declaredMethod = C0308je.class.getDeclaredMethod("invoke", Object[].class);
        declaredMethod.setAccessible(true);
        Unsafe unsafe = a;
        long j = unsafe.getLong(VMRuntime.class, d);
        if (j == 0) {
            throw new NoSuchMethodException("Cannot find matching method");
        }
        int i = unsafe.getInt(j);
        for (int i2 = 0; i2 < i; i2++) {
            a.putLong(declaredMethod, b, f + (((long) i2) * e) + j);
            if (str.equals(declaredMethod.getName())) {
                Class<?>[] parameterTypes = declaredMethod.getParameterTypes();
                int i3 = AbstractC0401me.a;
                if (parameterTypes.length == objArr.length) {
                    for (int i4 = 0; i4 < parameterTypes.length; i4++) {
                        if (parameterTypes[i4].isPrimitive()) {
                            Class<?> cls = parameterTypes[i4];
                            if ((cls != Integer.TYPE || (objArr[i4] instanceof Integer)) && ((cls != Byte.TYPE || (objArr[i4] instanceof Byte)) && ((cls != Character.TYPE || (objArr[i4] instanceof Character)) && ((cls != Boolean.TYPE || (objArr[i4] instanceof Boolean)) && ((cls != Double.TYPE || (objArr[i4] instanceof Double)) && ((cls != Float.TYPE || (objArr[i4] instanceof Float)) && ((cls != Long.TYPE || (objArr[i4] instanceof Long)) && (cls != Short.TYPE || (objArr[i4] instanceof Short))))))))) {
                            }
                        } else {
                            Object obj2 = objArr[i4];
                            if (obj2 == null || parameterTypes[i4].isInstance(obj2)) {
                            }
                        }
                    }
                    return declaredMethod.invoke(obj, objArr);
                }
                continue;
            }
        }
        throw new NoSuchMethodException("Cannot find matching method");
    }
}
