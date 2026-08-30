package rikka.shizuku;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: renamed from: rikka.shizuku.fq, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0198fq extends F {
    public static final Parcelable.Creator<C0198fq> CREATOR = new E(10);
    public int c;
    public boolean d;

    public C0198fq(Parcel parcel, ClassLoader classLoader) {
        super(parcel, classLoader);
        this.c = parcel.readInt();
        this.d = parcel.readInt() != 0;
    }

    @Override // rikka.shizuku.F, android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.c);
        parcel.writeInt(this.d ? 1 : 0);
    }
}
