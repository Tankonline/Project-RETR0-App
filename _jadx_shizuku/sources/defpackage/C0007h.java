package defpackage;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Trace;
import android.util.Log;
import androidx.profileinstaller.ProfileInstallerInitializer;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import moe.shizuku.privileged.api.R;

/* JADX INFO: renamed from: h, reason: case insensitive filesystem */
/* JADX INFO: loaded from: assets/rish_shizuku.dex */
public final class C0007h {
    public static volatile C0007h d;
    public static final Object e = new Object();
    public final Context c;
    public final HashSet b = new HashSet();
    public final HashMap a = new HashMap();

    public C0007h(Context context) {
        this.c = context.getApplicationContext();
    }

    public final void a(Bundle bundle) {
        HashSet hashSet;
        String string = this.c.getString(R.drawable._0_resource_name_obfuscated_res_0x7f080000);
        if (bundle != null) {
            try {
                HashSet hashSet2 = new HashSet();
                Iterator<String> it = bundle.keySet().iterator();
                while (true) {
                    boolean zHasNext = it.hasNext();
                    hashSet = this.b;
                    if (!zHasNext) {
                        break;
                    }
                    String next = it.next();
                    if (string.equals(bundle.getString(next, null))) {
                        Class<?> cls = Class.forName(next);
                        if (ProfileInstallerInitializer.class.isAssignableFrom(cls)) {
                            hashSet.add(cls);
                        }
                    }
                }
                Iterator it2 = hashSet.iterator();
                while (it2.hasNext()) {
                    b((Class) it2.next(), hashSet2);
                }
            } catch (ClassNotFoundException e2) {
                throw new L(e2);
            }
        }
    }

    public final void b(Class cls, HashSet hashSet) {
        boolean zBooleanValue;
        if (Build.VERSION.SDK_INT >= 29) {
            zBooleanValue = N.a();
        } else {
            try {
                if (t.o == null) {
                    t.n = Trace.class.getField("TRACE_TAG_APP").getLong(null);
                    t.o = Trace.class.getMethod("isTagEnabled", Long.TYPE);
                }
                zBooleanValue = ((Boolean) t.o.invoke(null, Long.valueOf(t.n))).booleanValue();
            } catch (Exception e2) {
                if (e2 instanceof InvocationTargetException) {
                    Throwable cause = e2.getCause();
                    if (!(cause instanceof RuntimeException)) {
                        throw new RuntimeException(cause);
                    }
                    throw ((RuntimeException) cause);
                }
                Log.v("Trace", "Unable to call isTagEnabled via reflection", e2);
                zBooleanValue = false;
            }
        }
        if (zBooleanValue) {
            try {
                String simpleName = cls.getSimpleName();
                if (simpleName.length() > 127) {
                    simpleName = simpleName.substring(0, 127);
                }
                Trace.beginSection(simpleName);
            } catch (Throwable th) {
                Trace.endSection();
                throw th;
            }
        }
        if (hashSet.contains(cls)) {
            throw new IllegalStateException("Cannot initialize " + cls.getName() + ". Cycle detected.");
        }
        HashMap map = this.a;
        if (map.containsKey(cls)) {
            map.get(cls);
        } else {
            hashSet.add(cls);
            try {
                ProfileInstallerInitializer profileInstallerInitializer = (ProfileInstallerInitializer) cls.getDeclaredConstructor(null).newInstance(null);
                profileInstallerInitializer.getClass();
                List<Class> list = Collections.EMPTY_LIST;
                if (!list.isEmpty()) {
                    for (Class cls2 : list) {
                        if (!map.containsKey(cls2)) {
                            b(cls2, hashSet);
                        }
                    }
                }
                B.a(new z(profileInstallerInitializer, this.c.getApplicationContext()));
                x xVar = new x(2);
                hashSet.remove(cls);
                map.put(cls, xVar);
            } catch (Throwable th2) {
                throw new L(th2);
            }
        }
        Trace.endSection();
    }
}
