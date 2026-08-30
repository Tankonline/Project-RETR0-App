package rikka.shizuku;

import android.app.Dialog;
import android.content.DialogInterface;

/* JADX INFO: loaded from: classes.dex */
public final class R9 implements DialogInterface.OnDismissListener {
    public final /* synthetic */ U9 a;

    public R9(U9 u9) {
        this.a = u9;
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        U9 u9 = this.a;
        Dialog dialog = u9.h0;
        if (dialog != null) {
            u9.onDismiss(dialog);
        }
    }
}
