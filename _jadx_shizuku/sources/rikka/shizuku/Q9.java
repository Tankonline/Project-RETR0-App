package rikka.shizuku;

import android.app.Dialog;
import android.content.DialogInterface;

/* JADX INFO: loaded from: classes.dex */
public final class Q9 implements DialogInterface.OnCancelListener {
    public final /* synthetic */ U9 a;

    public Q9(U9 u9) {
        this.a = u9;
    }

    @Override // android.content.DialogInterface.OnCancelListener
    public final void onCancel(DialogInterface dialogInterface) {
        U9 u9 = this.a;
        Dialog dialog = u9.h0;
        if (dialog != null) {
            u9.onCancel(dialog);
        }
    }
}
