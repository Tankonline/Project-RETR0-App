package rikka.shizuku;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
public abstract class Q4 implements G7, InterfaceC0088c8, Serializable {
    public final G7 a;

    public Q4(G7 g7) {
        this.a = g7;
    }

    @Override // rikka.shizuku.InterfaceC0088c8
    public final InterfaceC0088c8 d() {
        G7 g7 = this.a;
        if (g7 instanceof InterfaceC0088c8) {
            return (InterfaceC0088c8) g7;
        }
        return null;
    }

    @Override // rikka.shizuku.G7
    public final void h(Object obj) {
        G7 g7 = this;
        while (true) {
            Q4 q4 = (Q4) g7;
            G7 g8 = q4.a;
            try {
                obj = q4.j(obj);
                if (obj == EnumC0057b8.a) {
                    return;
                }
            } catch (Throwable th) {
                obj = new C0071bm(th);
            }
            q4.k();
            if (!(g8 instanceof Q4)) {
                g8.h(obj);
                return;
            }
            g7 = g8;
        }
    }

    public G7 i(Object obj, G7 g7) {
        throw new UnsupportedOperationException("create(Any?;Continuation) has not been overridden");
    }

    public abstract Object j(Object obj);

    public String toString() {
        int iIntValue;
        String strC;
        Method method;
        Object objInvoke;
        Method method2;
        Object objInvoke2;
        StringBuilder sb = new StringBuilder("Continuation at ");
        W8 w8 = (W8) getClass().getAnnotation(W8.class);
        Object name = null;
        str = null;
        str = null;
        str = null;
        str = null;
        str = null;
        String str = null;
        if (w8 != null) {
            int iV = w8.v();
            if (iV > 1) {
                throw new IllegalStateException(("Debug metadata version mismatch. Expected: 1, got " + iV + ". Please update the Kotlin standard library.").toString());
            }
            try {
                Field declaredField = getClass().getDeclaredField("label");
                declaredField.setAccessible(true);
                Object obj = declaredField.get(this);
                Integer num = obj instanceof Integer ? (Integer) obj : null;
                iIntValue = (num != null ? num.intValue() : 0) - 1;
            } catch (Exception unused) {
                iIntValue = -1;
            }
            int i = iIntValue >= 0 ? w8.l()[iIntValue] : -1;
            Di di = AbstractC0599sr.n;
            Di di2 = AbstractC0599sr.m;
            if (di == null) {
                try {
                    Di di3 = new Di(Class.class.getDeclaredMethod("getModule", null), getClass().getClassLoader().loadClass("java.lang.Module").getDeclaredMethod("getDescriptor", null), getClass().getClassLoader().loadClass("java.lang.module.ModuleDescriptor").getDeclaredMethod("name", null));
                    AbstractC0599sr.n = di3;
                    di = di3;
                } catch (Exception unused2) {
                    AbstractC0599sr.n = di2;
                    di = di2;
                }
            }
            if (di != di2 && (method = di.a) != null && (objInvoke = method.invoke(getClass(), null)) != null && (method2 = di.b) != null && (objInvoke2 = method2.invoke(objInvoke, null)) != null) {
                Method method3 = di.c;
                Object objInvoke3 = method3 != null ? method3.invoke(objInvoke2, null) : null;
                if (objInvoke3 instanceof String) {
                    str = (String) objInvoke3;
                }
            }
            if (str == null) {
                strC = w8.c();
            } else {
                strC = str + '/' + w8.c();
            }
            name = new StackTraceElement(strC, w8.m(), w8.f(), i);
        }
        if (name == null) {
            name = getClass().getName();
        }
        sb.append(name);
        return sb.toString();
    }

    public void k() {
    }
}
