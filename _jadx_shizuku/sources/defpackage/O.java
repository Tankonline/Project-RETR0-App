package defpackage;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: assets/rish_shizuku.dex */
public abstract class O {
    public final C0013n a;
    public final C0013n b;
    public final C0013n c;

    public O(C0013n c0013n, C0013n c0013n2, C0013n c0013n3) {
        this.a = c0013n;
        this.b = c0013n2;
        this.c = c0013n3;
    }

    public abstract P a();

    public final Class b(Class cls) throws ClassNotFoundException {
        String name = cls.getName();
        C0013n c0013n = this.c;
        Class cls2 = (Class) c0013n.d(name);
        if (cls2 != null) {
            return cls2;
        }
        Class<?> cls3 = Class.forName(cls.getPackage().getName() + "." + cls.getSimpleName() + "Parcelizer", false, cls.getClassLoader());
        c0013n.put(cls.getName(), cls3);
        return cls3;
    }

    public final Method c(String str) throws NoSuchMethodException {
        C0013n c0013n = this.a;
        Method method = (Method) c0013n.d(str);
        if (method != null) {
            return method;
        }
        System.currentTimeMillis();
        Method declaredMethod = Class.forName(str, true, O.class.getClassLoader()).getDeclaredMethod("read", O.class);
        c0013n.put(str, declaredMethod);
        return declaredMethod;
    }

    public final Method d(Class cls) throws NoSuchMethodException, ClassNotFoundException {
        String name = cls.getName();
        C0013n c0013n = this.b;
        Method method = (Method) c0013n.d(name);
        if (method != null) {
            return method;
        }
        Class clsB = b(cls);
        System.currentTimeMillis();
        Method declaredMethod = clsB.getDeclaredMethod("write", cls, O.class);
        c0013n.put(cls.getName(), declaredMethod);
        return declaredMethod;
    }

    public abstract boolean e(int i);

    public final Parcelable f(Parcelable parcelable, int i) {
        if (!e(i)) {
            return parcelable;
        }
        return ((P) this).e.readParcelable(P.class.getClassLoader());
    }

    public abstract void g(int i);

    public final void h(Q q) {
        if (q == null) {
            ((P) this).e.writeString(null);
            return;
        }
        try {
            ((P) this).e.writeString(b(q.getClass()).getName());
            P pA = a();
            try {
                d(q.getClass()).invoke(null, q, pA);
                int i = pA.i;
                if (i >= 0) {
                    int i2 = pA.d.get(i);
                    Parcel parcel = pA.e;
                    int iDataPosition = parcel.dataPosition();
                    parcel.setDataPosition(i2);
                    parcel.writeInt(iDataPosition - i2);
                    parcel.setDataPosition(iDataPosition);
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("VersionedParcel encountered ClassNotFoundException", e);
            } catch (IllegalAccessException e2) {
                throw new RuntimeException("VersionedParcel encountered IllegalAccessException", e2);
            } catch (NoSuchMethodException e3) {
                throw new RuntimeException("VersionedParcel encountered NoSuchMethodException", e3);
            } catch (InvocationTargetException e4) {
                if (!(e4.getCause() instanceof RuntimeException)) {
                    throw new RuntimeException("VersionedParcel encountered InvocationTargetException", e4);
                }
                throw ((RuntimeException) e4.getCause());
            }
        } catch (ClassNotFoundException e5) {
            throw new RuntimeException(q.getClass().getSimpleName().concat(" does not have a Parcelizer"), e5);
        }
    }
}
