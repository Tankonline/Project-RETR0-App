package rikka.shizuku;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.AbsSavedState;

/* JADX INFO: renamed from: rikka.shizuku.wf, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0711wf extends C0038ak {
    public static final Parcelable.Creator<C0711wf> CREATOR = new J0(11);
    public int a;

    public C0711wf(Parcel parcel) {
        super(parcel);
        this.a = parcel.readInt();
    }

    @Override // android.view.AbsSavedState, android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.a);
    }

    public C0711wf() {
        super(AbsSavedState.EMPTY_STATE);
    }
}
