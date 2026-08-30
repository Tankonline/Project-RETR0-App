package rikka.shizuku;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: renamed from: rikka.shizuku.i2, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0267i2 extends F {
    public static final Parcelable.Creator<C0267i2> CREATOR = new E(1);
    public boolean c;
    public boolean d;
    public int e;
    public float f;
    public boolean g;

    public C0267i2(Parcel parcel, ClassLoader classLoader) {
        super(parcel, classLoader);
        this.c = parcel.readByte() != 0;
        this.d = parcel.readByte() != 0;
        this.e = parcel.readInt();
        this.f = parcel.readFloat();
        this.g = parcel.readByte() != 0;
    }

    @Override // rikka.shizuku.F, android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeByte(this.c ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.d ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.e);
        parcel.writeFloat(this.f);
        parcel.writeByte(this.g ? (byte) 1 : (byte) 0);
    }
}
