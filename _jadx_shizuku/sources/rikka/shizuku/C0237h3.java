package rikka.shizuku;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

/* JADX INFO: renamed from: rikka.shizuku.h3, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0237h3 extends View.BaseSavedState {
    public static final Parcelable.Creator<C0237h3> CREATOR = new J0(1);
    public boolean a;

    @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeByte(this.a ? (byte) 1 : (byte) 0);
    }
}
