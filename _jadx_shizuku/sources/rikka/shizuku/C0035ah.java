package rikka.shizuku;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.AbsSavedState;

/* JADX INFO: renamed from: rikka.shizuku.ah, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0035ah extends C0038ak {
    public static final Parcelable.Creator<C0035ah> CREATOR = new J0(14);
    public String a;

    public C0035ah(Parcel parcel) {
        super(parcel);
        this.a = parcel.readString();
    }

    @Override // android.view.AbsSavedState, android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.a);
    }

    public C0035ah() {
        super(AbsSavedState.EMPTY_STATE);
    }
}
