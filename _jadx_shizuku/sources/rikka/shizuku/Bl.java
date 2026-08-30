package rikka.shizuku;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public final class Bl extends AbstractC0779yl {
    public static final HashMap e;
    public final Constructor b;
    public final Object[] c;
    public final HashMap d;

    static {
        HashMap map = new HashMap();
        map.put(Byte.TYPE, (byte) 0);
        map.put(Short.TYPE, (short) 0);
        map.put(Integer.TYPE, 0);
        map.put(Long.TYPE, 0L);
        map.put(Float.TYPE, Float.valueOf(0.0f));
        map.put(Double.TYPE, Double.valueOf(0.0d));
        map.put(Character.TYPE, (char) 0);
        map.put(Boolean.TYPE, Boolean.FALSE);
        e = map;
    }

    public Bl(Class cls, Al al) {
        super(al);
        this.d = new HashMap();
        AbstractC0046as abstractC0046as = AbstractC0717wl.a;
        Constructor constructorS = abstractC0046as.s(cls);
        this.b = constructorS;
        AbstractC0717wl.f(constructorS);
        String[] strArrY = abstractC0046as.y(cls);
        for (int i = 0; i < strArrY.length; i++) {
            this.d.put(strArrY[i], Integer.valueOf(i));
        }
        Class<?>[] parameterTypes = this.b.getParameterTypes();
        this.c = new Object[parameterTypes.length];
        for (int i2 = 0; i2 < parameterTypes.length; i2++) {
            this.c[i2] = e.get(parameterTypes[i2]);
        }
    }

    @Override // rikka.shizuku.AbstractC0779yl
    public final Object d() {
        return (Object[]) this.c.clone();
    }

    @Override // rikka.shizuku.AbstractC0779yl
    public final Object e(Object obj) {
        Object[] objArr = (Object[]) obj;
        Constructor constructor = this.b;
        try {
            return constructor.newInstance(objArr);
        } catch (IllegalAccessException e2) {
            AbstractC0046as abstractC0046as = AbstractC0717wl.a;
            throw new RuntimeException("Unexpected IllegalAccessException occurred (Gson 2.13.1). Certain ReflectionAccessFilter features require Java >= 9 to work correctly. If you are not using ReflectionAccessFilter, report this to the Gson maintainers.", e2);
        } catch (IllegalArgumentException e3) {
            e = e3;
            throw new RuntimeException("Failed to invoke constructor '" + AbstractC0717wl.b(constructor) + "' with args " + Arrays.toString(objArr), e);
        } catch (InstantiationException e4) {
            e = e4;
            throw new RuntimeException("Failed to invoke constructor '" + AbstractC0717wl.b(constructor) + "' with args " + Arrays.toString(objArr), e);
        } catch (InvocationTargetException e5) {
            throw new RuntimeException("Failed to invoke constructor '" + AbstractC0717wl.b(constructor) + "' with args " + Arrays.toString(objArr), e5.getCause());
        }
    }

    @Override // rikka.shizuku.AbstractC0779yl
    public final void f(Object obj, Vf vf, C0748xl c0748xl) {
        Object[] objArr = (Object[]) obj;
        HashMap map = this.d;
        String str = c0748xl.c;
        Integer num = (Integer) map.get(str);
        if (num == null) {
            throw new IllegalStateException("Could not find the index in the constructor '" + AbstractC0717wl.b(this.b) + "' for field with name '" + str + "', unable to determine which argument in the constructor the field corresponds to. This is unexpected behavior, as we expect the RecordComponents to have the same names as the fields in the Java class, and that the order of the RecordComponents is the same as the order of the canonical constructor parameters.");
        }
        int iIntValue = num.intValue();
        Object objB = c0748xl.f.b(vf);
        if (objB != null || !c0748xl.g) {
            objArr[iIntValue] = objB;
        } else {
            StringBuilder sbE = AbstractC0535qo.e("null is not allowed as value for record component '", str, "' of primitive type; at path ");
            sbE.append(vf.h(false));
            throw new K6(sbE.toString());
        }
    }
}
