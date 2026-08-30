package rikka.shizuku;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.AbsSavedState;

/* JADX INFO: loaded from: classes.dex */
public final class Ba extends C0038ak {
    public static final Parcelable.Creator<Ba> CREATOR = new J0(7);
    public String a;

    public Ba(Parcel parcel) {
        super(parcel);
        this.a = parcel.readString();
    }

    @Override // android.view.AbsSavedState, android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.a);
    }

    public Ba() {
        super(AbsSavedState.EMPTY_STATE);
    }
}
