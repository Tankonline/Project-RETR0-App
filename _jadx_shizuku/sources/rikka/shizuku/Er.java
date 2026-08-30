package rikka.shizuku;

import android.app.IUidObserver;
import android.os.Parcel;

/* JADX INFO: loaded from: classes.dex */
public class Er extends IUidObserver.Stub {
    /* JADX WARN: Multi-variable type inference failed */
    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        try {
            return super/*android.os.Binder*/.onTransact(i, parcel, parcel2, i2);
        } catch (Throwable unused) {
            return true;
        }
    }

    public final void onUidGone(int i) {
        onUidGone(i, false);
    }

    public final void onUidIdle(int i) {
        onUidIdle(i, false);
    }

    public final void onUidStateChanged(int i, int i2) {
        onUidStateChanged(i, i2, 0L);
    }

    public void onUidGone(int i, boolean z) {
    }

    public void onUidIdle(int i, boolean z) {
    }

    public final void onUidStateChanged(int i, int i2, long j) {
        onUidStateChanged(i, i2, j, 0);
    }

    public void onUidStateChanged(int i, int i2, long j, int i3) {
    }

    public void onUidActive(int i) {
    }

    public void onUidCachedChanged(int i, boolean z) {
    }
}
