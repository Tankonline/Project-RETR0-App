package rikka.shizuku;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* JADX INFO: renamed from: rikka.shizuku.ps, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0508ps {
    public final C0298j4 a;
    public final C0298j4 b;
    public final C0298j4 c;

    public AbstractC0508ps(C0298j4 c0298j4, C0298j4 c0298j5, C0298j4 c0298j6) {
        this.a = c0298j4;
        this.b = c0298j5;
        this.c = c0298j6;
    }

    public abstract C0539qs a();

    public final Class b(Class cls) throws ClassNotFoundException {
        String name = cls.getName();
        C0298j4 c0298j4 = this.c;
        Class cls2 = (Class) c0298j4.get(name);
        if (cls2 != null) {
            return cls2;
        }
        Class<?> cls3 = Class.forName(cls.getPackage().getName() + "." + cls.getSimpleName() + "Parcelizer", false, cls.getClassLoader());
        c0298j4.put(cls.getName(), cls3);
        return cls3;
    }

    public final Method c(String str) throws NoSuchMethodException {
        C0298j4 c0298j4 = this.a;
        Method method = (Method) c0298j4.get(str);
        if (method != null) {
            return method;
        }
        System.currentTimeMillis();
        Method declaredMethod = Class.forName(str, true, AbstractC0508ps.class.getClassLoader()).getDeclaredMethod("read", AbstractC0508ps.class);
        c0298j4.put(str, declaredMethod);
        return declaredMethod;
    }

    public final Method d(Class cls) throws NoSuchMethodException, ClassNotFoundException {
        String name = cls.getName();
        C0298j4 c0298j4 = this.b;
        Method method = (Method) c0298j4.get(name);
        if (method != null) {
            return method;
        }
        Class clsB = b(cls);
        System.currentTimeMillis();
        Method declaredMethod = clsB.getDeclaredMethod("write", cls, AbstractC0508ps.class);
        c0298j4.put(cls.getName(), declaredMethod);
        return declaredMethod;
    }

    public abstract boolean e(int i);

    public final Parcelable f(Parcelable parcelable, int i) {
        if (!e(i)) {
            return parcelable;
        }
        return ((C0539qs) this).e.readParcelable(C0539qs.class.getClassLoader());
    }

    public final InterfaceC0569rs g() {
        String string = ((C0539qs) this).e.readString();
        if (string == null) {
            return null;
        }
        try {
            return (InterfaceC0569rs) c(string).invoke(null, a());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("VersionedParcel encountered ClassNotFoundException", e);
        } catch (IllegalAccessException e2) {
            throw new RuntimeException("VersionedParcel encountered IllegalAccessException", e2);
        } catch (NoSuchMethodException e3) {
            throw new RuntimeException("VersionedParcel encountered NoSuchMethodException", e3);
        } catch (InvocationTargetException e4) {
            if (e4.getCause() instanceof RuntimeException) {
                throw ((RuntimeException) e4.getCause());
            }
            throw new RuntimeException("VersionedParcel encountered InvocationTargetException", e4);
        }
    }

    public abstract void h(int i);

    public final void i(InterfaceC0569rs interfaceC0569rs) {
        if (interfaceC0569rs == null) {
            ((C0539qs) this).e.writeString(null);
            return;
        }
        try {
            ((C0539qs) this).e.writeString(b(interfaceC0569rs.getClass()).getName());
            C0539qs c0539qsA = a();
            try {
                d(interfaceC0569rs.getClass()).invoke(null, interfaceC0569rs, c0539qsA);
                int i = c0539qsA.i;
                if (i >= 0) {
                    int i2 = c0539qsA.d.get(i);
                    Parcel parcel = c0539qsA.e;
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
            throw new RuntimeException(interfaceC0569rs.getClass().getSimpleName().concat(" does not have a Parcelizer"), e5);
        }
    }
}
