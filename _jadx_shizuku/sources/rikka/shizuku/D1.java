package rikka.shizuku;

import android.view.View;
import android.widget.AdapterView;
import androidx.appcompat.app.AlertController$RecycleListView;

/* JADX INFO: loaded from: classes.dex */
public final class D1 implements AdapterView.OnItemClickListener {
    public final /* synthetic */ AlertController$RecycleListView a;
    public final /* synthetic */ H1 b;
    public final /* synthetic */ E1 c;

    public D1(E1 e1, AlertController$RecycleListView alertController$RecycleListView, H1 h1) {
        this.c = e1;
        this.a = alertController$RecycleListView;
        this.b = h1;
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
        E1 e1 = this.c;
        boolean[] zArr = e1.t;
        AlertController$RecycleListView alertController$RecycleListView = this.a;
        if (zArr != null) {
            zArr[i] = alertController$RecycleListView.isItemChecked(i);
        }
        e1.x.onClick(this.b.b, i, alertController$RecycleListView.isItemChecked(i));
    }
}
