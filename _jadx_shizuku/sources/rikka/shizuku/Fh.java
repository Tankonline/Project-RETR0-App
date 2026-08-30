package rikka.shizuku;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public final class Fh extends F {
    public static final Parcelable.Creator<Fh> CREATOR = new E(5);
    public boolean c;

    public Fh(Parcel parcel, ClassLoader classLoader) {
        super(parcel, classLoader);
        if (classLoader == null) {
            Fh.class.getClassLoader();
        }
        this.c = parcel.readInt() == 1;
    }

    @Override // rikka.shizuku.F, android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.c ? 1 : 0);
    }
}
