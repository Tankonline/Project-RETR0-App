package rikka.shizuku;

import java.util.ArrayList;

/* JADX INFO: renamed from: rikka.shizuku.c5, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class IProcessObserverStubC0085c5 extends IProcessObserverStubC0685vk {
    public static final ArrayList a = new ArrayList();

    @Override // rikka.shizuku.IProcessObserverStubC0685vk
    public final void onForegroundActivitiesChanged(int i, int i2, boolean z) {
        ArrayList arrayList = a;
        synchronized (arrayList) {
            if (!arrayList.contains(Integer.valueOf(i)) && z) {
                arrayList.add(Integer.valueOf(i));
                AbstractC0146e5.a(i2, i);
            }
        }
    }

    @Override // rikka.shizuku.IProcessObserverStubC0685vk
    public final void onProcessDied(int i, int i2) {
        ArrayList arrayList = a;
        synchronized (arrayList) {
            int iIndexOf = arrayList.indexOf(Integer.valueOf(i));
            if (iIndexOf != -1) {
                arrayList.remove(iIndexOf);
            }
        }
    }

    @Override // rikka.shizuku.IProcessObserverStubC0685vk
    public final void onProcessStateChanged(int i, int i2, int i3) {
        ArrayList arrayList = a;
        synchronized (arrayList) {
            try {
                if (arrayList.contains(Integer.valueOf(i))) {
                    return;
                }
                arrayList.add(Integer.valueOf(i));
                AbstractC0146e5.a(i2, i);
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
