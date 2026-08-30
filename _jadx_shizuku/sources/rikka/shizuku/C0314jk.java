package rikka.shizuku;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.AbsSavedState;

/* JADX INFO: renamed from: rikka.shizuku.jk, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0314jk extends C0038ak {
    public static final Parcelable.Creator<C0314jk> CREATOR = new J0(22);
    public final int a;

    public C0314jk(Parcel parcel) {
        super(parcel);
        this.a = parcel.readInt();
    }

    @Override // android.view.AbsSavedState, android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.a);
    }

    public C0314jk(int i) {
        super(AbsSavedState.EMPTY_STATE);
        this.a = i;
    }
}
