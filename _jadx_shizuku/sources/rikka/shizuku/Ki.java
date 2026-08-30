package rikka.shizuku;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.AbsSavedState;
import java.util.Collections;
import java.util.HashSet;

/* JADX INFO: loaded from: classes.dex */
public final class Ki extends C0038ak {
    public static final Parcelable.Creator<Ki> CREATOR = new J0(17);
    public HashSet a;

    public Ki(Parcel parcel) {
        super(parcel);
        int i = parcel.readInt();
        this.a = new HashSet();
        String[] strArr = new String[i];
        parcel.readStringArray(strArr);
        Collections.addAll(this.a, strArr);
    }

    @Override // android.view.AbsSavedState, android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.a.size());
        HashSet hashSet = this.a;
        parcel.writeStringArray((String[]) hashSet.toArray(new String[hashSet.size()]));
    }

    public Ki() {
        super(AbsSavedState.EMPTY_STATE);
    }
}
