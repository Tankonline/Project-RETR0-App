package rikka.shizuku;

import android.content.DialogInterface;

/* JADX INFO: renamed from: rikka.shizuku.bh, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class DialogInterfaceOnClickListenerC0066bh implements DialogInterface.OnClickListener {
    public final /* synthetic */ C0097ch a;

    public DialogInterfaceOnClickListenerC0066bh(C0097ch c0097ch) {
        this.a = c0097ch;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        C0097ch c0097ch = this.a;
        c0097ch.u0 = i;
        c0097ch.t0 = -1;
        dialogInterface.dismiss();
    }
}
