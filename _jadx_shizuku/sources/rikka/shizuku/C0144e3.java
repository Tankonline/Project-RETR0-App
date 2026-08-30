package rikka.shizuku;

import android.view.View;
import android.widget.AdapterView;
import androidx.appcompat.widget.SearchView;

/* JADX INFO: renamed from: rikka.shizuku.e3, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0144e3 implements AdapterView.OnItemClickListener {
    public final /* synthetic */ int a;
    public final /* synthetic */ Object b;

    public /* synthetic */ C0144e3(int i, Object obj) {
        this.a = i;
        this.b = obj;
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
        Object item;
        switch (this.a) {
            case 0:
                C0206g3 c0206g3 = (C0206g3) this.b;
                c0206g3.G.setSelection(i);
                if (c0206g3.G.getOnItemClickListener() != null) {
                    c0206g3.G.performItemClick(view, i, c0206g3.D.getItemId(i));
                }
                c0206g3.dismiss();
                return;
            case 1:
                Eh eh = (Eh) this.b;
                if (i < 0) {
                    Zg zg = eh.e;
                    item = !zg.z.isShowing() ? null : zg.c.getSelectedItem();
                } else {
                    item = eh.getAdapter().getItem(i);
                }
                Eh.a(eh, item);
                AdapterView.OnItemClickListener onItemClickListener = eh.getOnItemClickListener();
                Zg zg2 = eh.e;
                if (onItemClickListener != null) {
                    if (view == null || i < 0) {
                        view = zg2.z.isShowing() ? zg2.c.getSelectedView() : null;
                        i = !zg2.z.isShowing() ? -1 : zg2.c.getSelectedItemPosition();
                        j = !zg2.z.isShowing() ? Long.MIN_VALUE : zg2.c.getSelectedItemId();
                    }
                    onItemClickListener.onItemClick(zg2.c, view, i, j);
                }
                zg2.dismiss();
                return;
            default:
                ((SearchView) this.b).getClass();
                throw null;
        }
    }
}
