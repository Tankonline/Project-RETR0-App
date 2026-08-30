package rikka.shizuku;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: renamed from: rikka.shizuku.g6, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0209g6 {
    public final HashMap a = new HashMap();
    public final HashMap b;

    public C0209g6(HashMap map) {
        this.b = map;
        for (Map.Entry entry : map.entrySet()) {
            EnumC0557rg enumC0557rg = (EnumC0557rg) entry.getValue();
            List arrayList = (List) this.a.get(enumC0557rg);
            if (arrayList == null) {
                arrayList = new ArrayList();
                this.a.put(enumC0557rg, arrayList);
            }
            arrayList.add((C0240h6) entry.getKey());
        }
    }

    public static void a(List list, InterfaceC0743xg interfaceC0743xg, EnumC0557rg enumC0557rg, InterfaceC0712wg interfaceC0712wg) {
        if (list != null) {
            for (int size = list.size() - 1; size >= 0; size--) {
                C0240h6 c0240h6 = (C0240h6) list.get(size);
                c0240h6.getClass();
                try {
                    int i = c0240h6.a;
                    Method method = c0240h6.b;
                    if (i == 0) {
                        method.invoke(interfaceC0712wg, null);
                    } else if (i == 1) {
                        method.invoke(interfaceC0712wg, interfaceC0743xg);
                    } else if (i == 2) {
                        method.invoke(interfaceC0712wg, interfaceC0743xg, enumC0557rg);
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e2) {
                    throw new RuntimeException("Failed to call observer method", e2.getCause());
                }
            }
        }
    }
}
