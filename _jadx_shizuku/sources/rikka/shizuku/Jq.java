package rikka.shizuku;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.AbsSavedState;

/* JADX INFO: loaded from: classes.dex */
public final class Jq extends C0038ak {
    public static final Parcelable.Creator<Jq> CREATOR = new J0(26);
    public boolean a;

    public Jq(Parcel parcel) {
        super(parcel);
        this.a = parcel.readInt() == 1;
    }

    @Override // android.view.AbsSavedState, android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.a ? 1 : 0);
    }

    public Jq() {
        super(AbsSavedState.EMPTY_STATE);
    }
}
