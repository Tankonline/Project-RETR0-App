package rikka.shizuku;

import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
public abstract class Vj {
    public static final Method a;

    static {
        Method method;
        Method[] methods = Throwable.class.getMethods();
        int length = methods.length;
        int i = 0;
        while (true) {
            method = null;
            if (i >= length) {
                break;
            }
            Method method2 = methods[i];
            if (AbstractC0046as.c(method2.getName(), "addSuppressed")) {
                Class<?>[] parameterTypes = method2.getParameterTypes();
                if (AbstractC0046as.c(parameterTypes.length == 1 ? parameterTypes[0] : null, Throwable.class)) {
                    method = method2;
                    break;
                }
            }
            i++;
        }
        a = method;
        int length2 = methods.length;
        for (int i2 = 0; i2 < length2 && !AbstractC0046as.c(methods[i2].getName(), "getSuppressed"); i2++) {
        }
    }
}
