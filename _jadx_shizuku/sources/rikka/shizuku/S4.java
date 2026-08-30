package rikka.shizuku;

import android.os.Binder;
import android.os.Parcel;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public final class S4 extends Binder {
    public final /* synthetic */ Iterator a;
    public final /* synthetic */ int b;

    public S4(Kj kj, Iterator it, int i) {
        this.a = it;
        this.b = i;
    }

    @Override // android.os.Binder
    public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        if (i != 1 || parcel2 == null) {
            return false;
        }
        Kj.a(this.a, parcel2, this.b);
        return true;
    }
}
