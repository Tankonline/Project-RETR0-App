package androidx.versionedparcelable;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;
import rikka.shizuku.C0539qs;
import rikka.shizuku.InterfaceC0569rs;
import rikka.shizuku.J0;

/* JADX WARN: Classes with same name are omitted, all sources:
  assets/rish_shizuku.dex
  classes.dex
 */
/* JADX INFO: loaded from: classes.dex */
@SuppressLint({"BanParcelableUsage"})
public class ParcelImpl implements Parcelable {
    public static final Parcelable.Creator<ParcelImpl> CREATOR = new J0(19);
    public final InterfaceC0569rs a;

    public ParcelImpl(Parcel parcel) {
        this.a = new C0539qs(parcel).g();
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        new C0539qs(parcel).i(this.a);
    }
}
