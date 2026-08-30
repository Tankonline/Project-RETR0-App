package rikka.shizuku;

import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* JADX INFO: renamed from: rikka.shizuku.aq, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class RunnableC0044aq implements Runnable {
    public final /* synthetic */ int a;
    public final /* synthetic */ Toolbar b;

    public /* synthetic */ RunnableC0044aq(Toolbar toolbar, int i) {
        this.a = i;
        this.b = toolbar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.a) {
            case 0:
                C0136dq c0136dq = this.b.M;
                C0221gi c0221gi = c0136dq == null ? null : c0136dq.b;
                if (c0221gi != null) {
                    c0221gi.collapseActionView();
                }
                break;
            default:
                Toolbar toolbar = this.b;
                ArrayList arrayList = toolbar.H;
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    toolbar.m().removeItem(((MenuItem) obj).getItemId());
                }
                toolbar.m();
                ArrayList arrayListK = toolbar.k();
                new C0381lp(toolbar.getContext());
                Iterator it = ((CopyOnWriteArrayList) toolbar.G.c).iterator();
                while (it.hasNext()) {
                    ((Jc) it.next()).a.k();
                }
                ArrayList arrayListK2 = toolbar.k();
                arrayListK2.removeAll(arrayListK);
                toolbar.H = arrayListK2;
                break;
        }
    }
}
