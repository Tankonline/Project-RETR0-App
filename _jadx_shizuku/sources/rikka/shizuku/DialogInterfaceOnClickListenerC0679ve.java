package rikka.shizuku;

import android.content.DialogInterface;
import android.os.RemoteException;
import moe.shizuku.manager.MainActivity;

/* JADX INFO: renamed from: rikka.shizuku.ve, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class DialogInterfaceOnClickListenerC0679ve implements DialogInterface.OnClickListener {
    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        int i2 = MainActivity.I;
        try {
            try {
                Nn.k().b();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } catch (Throwable unused) {
        }
    }
}
