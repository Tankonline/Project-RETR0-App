package rikka.shizuku;

import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;

/* JADX INFO: loaded from: classes.dex */
public final class C1 implements AdapterView.OnItemClickListener {
    public final /* synthetic */ H1 a;
    public final /* synthetic */ E1 b;

    public C1(E1 e1, H1 h1) {
        this.b = e1;
        this.a = h1;
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
        E1 e1 = this.b;
        DialogInterface.OnClickListener onClickListener = e1.r;
        H1 h1 = this.a;
        onClickListener.onClick(h1.b, i);
        if (e1.v) {
            return;
        }
        h1.b.dismiss();
    }
}
