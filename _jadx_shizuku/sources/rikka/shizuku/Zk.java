package rikka.shizuku;

import android.database.Observable;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.WeakHashMap;
import moe.shizuku.manager.management.ApplicationManagementActivity;

/* JADX INFO: loaded from: classes.dex */
public final class Zk extends Observable {
    public final boolean a() {
        return !((Observable) this).mObservers.isEmpty();
    }

    public final void b() {
        for (int size = ((Observable) this).mObservers.size() - 1; size >= 0; size--) {
            U3 u3 = (U3) ((Observable) this).mObservers.get(size);
            switch (u3.a) {
                case 1:
                    RecyclerView recyclerView = (RecyclerView) u3.b;
                    recyclerView.k(null);
                    recyclerView.g0.f = true;
                    recyclerView.Y(true);
                    if (!recyclerView.e.h()) {
                        recyclerView.requestLayout();
                    }
                    break;
            }
        }
    }

    public final void c(int i, int i2, Object obj) {
        for (int size = ((Observable) this).mObservers.size() - 1; size >= 0; size--) {
            U3 u3 = (U3) ((Observable) this).mObservers.get(size);
            switch (u3.a) {
                case 0:
                    int i3 = ApplicationManagementActivity.G;
                    Y3 y3 = (Y3) ((ApplicationManagementActivity) u3.b).D.getValue();
                    y3.getClass();
                    C0517q6 c0517q6A = AbstractC0744xh.A(y3);
                    C0550r9 c0550r9 = AbstractC0090ca.a;
                    ExecutorC0243h9 executorC0243h9 = ExecutorC0243h9.b;
                    AbstractC0599sr.z(c0517q6A, new X3(true, y3, null), 2);
                    break;
                default:
                    RecyclerView recyclerView = (RecyclerView) u3.b;
                    recyclerView.k(null);
                    T0 t0 = recyclerView.e;
                    if (i2 < 1) {
                        t0.getClass();
                    } else {
                        ArrayList arrayList = (ArrayList) t0.c;
                        arrayList.add(t0.j(obj, 4, i, i2));
                        t0.a = 4 | t0.a;
                        if (arrayList.size() == 1) {
                            RecyclerView recyclerView2 = (RecyclerView) u3.b;
                            if (recyclerView2.t && recyclerView2.s) {
                                WeakHashMap weakHashMap = Hs.a;
                                recyclerView2.postOnAnimation(recyclerView2.i);
                            } else {
                                recyclerView2.A = true;
                                recyclerView2.requestLayout();
                            }
                        }
                    }
                    break;
            }
        }
    }
}
