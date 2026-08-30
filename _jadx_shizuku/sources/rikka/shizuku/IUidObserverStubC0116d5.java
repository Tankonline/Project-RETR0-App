package rikka.shizuku;

import android.util.Log;
import java.util.ArrayList;
import java.util.Locale;

/* JADX INFO: renamed from: rikka.shizuku.d5, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class IUidObserverStubC0116d5 extends Er {
    public static final ArrayList a = new ArrayList();

    public static void a(int i) {
        ArrayList arrayList = a;
        synchronized (arrayList) {
            try {
                if (arrayList.contains(Integer.valueOf(i))) {
                    Locale locale = Locale.ENGLISH;
                    Log.println(2, "BinderSender", "Uid " + i + " already starts");
                    return;
                }
                arrayList.add(Integer.valueOf(i));
                Locale locale2 = Locale.ENGLISH;
                Log.println(2, "BinderSender", "Uid " + i + " starts");
                AbstractC0146e5.a(i, -1);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // rikka.shizuku.Er
    public final void onUidActive(int i) {
        a(i);
    }

    @Override // rikka.shizuku.Er
    public final void onUidCachedChanged(int i, boolean z) {
        Boolean.toString(z);
        if (z) {
            return;
        }
        a(i);
    }

    @Override // rikka.shizuku.Er
    public final void onUidGone(int i, boolean z) {
        Boolean.toString(z);
        ArrayList arrayList = a;
        synchronized (arrayList) {
            int iIndexOf = arrayList.indexOf(Integer.valueOf(i));
            if (iIndexOf != -1) {
                arrayList.remove(iIndexOf);
                Locale locale = Locale.ENGLISH;
                Log.println(2, "BinderSender", "Uid " + i + " dead");
            }
        }
    }

    @Override // rikka.shizuku.Er
    public final void onUidIdle(int i, boolean z) {
        Boolean.toString(z);
        a(i);
    }
}
