package rikka.shizuku;

import android.os.Handler;
import android.os.Message;

/* JADX INFO: renamed from: rikka.shizuku.no, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0442no implements Handler.Callback {
    public final /* synthetic */ Hm a;

    public C0442no(Hm hm) {
        this.a = hm;
    }

    @Override // android.os.Handler.Callback
    public final boolean handleMessage(Message message) {
        if (message.what != 0) {
            return false;
        }
        Hm hm = this.a;
        if (message.obj != null) {
            throw new ClassCastException();
        }
        synchronized (hm.a) {
            throw null;
        }
    }
}
