package rikka.shizuku;

import android.content.DialogInterface;

/* JADX INFO: loaded from: classes.dex */
public final class Li implements DialogInterface.OnMultiChoiceClickListener {
    public final /* synthetic */ Mi a;

    public Li(Mi mi) {
        this.a = mi;
    }

    @Override // android.content.DialogInterface.OnMultiChoiceClickListener
    public final void onClick(DialogInterface dialogInterface, int i, boolean z) {
        Mi mi = this.a;
        if (z) {
            mi.v0 = mi.u0.add(mi.x0[i].toString()) | mi.v0;
        } else {
            mi.v0 = mi.u0.remove(mi.x0[i].toString()) | mi.v0;
        }
    }
}
