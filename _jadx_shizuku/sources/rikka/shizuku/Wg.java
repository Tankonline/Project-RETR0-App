package rikka.shizuku;

import android.database.DataSetObserver;

/* JADX INFO: loaded from: classes.dex */
public final class Wg extends DataSetObserver {
    public final /* synthetic */ Zg a;

    public Wg(Zg zg) {
        this.a = zg;
    }

    @Override // android.database.DataSetObserver
    public final void onChanged() {
        Zg zg = this.a;
        if (zg.z.isShowing()) {
            zg.e();
        }
    }

    @Override // android.database.DataSetObserver
    public final void onInvalidated() {
        this.a.dismiss();
    }
}
