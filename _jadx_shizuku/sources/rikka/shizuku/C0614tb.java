package rikka.shizuku;

import java.io.IOException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;

/* JADX INFO: renamed from: rikka.shizuku.tb, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0614tb extends Lq {
    public static final C0583sb d = new C0583sb();
    public final HashMap a = new HashMap();
    public final HashMap b = new HashMap();
    public final HashMap c = new HashMap();

    public C0614tb(Class cls) {
        try {
            Field[] declaredFields = cls.getDeclaredFields();
            int i = 0;
            for (Field field : declaredFields) {
                if (field.isEnumConstant()) {
                    declaredFields[i] = field;
                    i++;
                }
            }
            Field[] fieldArr = (Field[]) Arrays.copyOf(declaredFields, i);
            AccessibleObject.setAccessible(fieldArr, true);
            for (Field field2 : fieldArr) {
                Enum r4 = (Enum) field2.get(null);
                String strName = r4.name();
                String string = r4.toString();
                Xm xm = (Xm) field2.getAnnotation(Xm.class);
                if (xm != null) {
                    strName = xm.value();
                    for (String str : xm.alternate()) {
                        this.a.put(str, r4);
                    }
                }
                this.a.put(strName, r4);
                this.b.put(string, r4);
                this.c.put(r4, strName);
            }
        } catch (IllegalAccessException e) {
            throw new AssertionError(e);
        }
    }

    @Override // rikka.shizuku.Lq
    public final Object b(Vf vf) throws IOException {
        if (vf.v() == 9) {
            vf.r();
            return null;
        }
        String strT = vf.t();
        Enum r0 = (Enum) this.a.get(strT);
        return r0 == null ? (Enum) this.b.get(strT) : r0;
    }

    @Override // rikka.shizuku.Lq
    public final void c(Wf wf, Object obj) throws IOException {
        Enum r3 = (Enum) obj;
        wf.p(r3 == null ? null : (String) this.c.get(r3));
    }
}
