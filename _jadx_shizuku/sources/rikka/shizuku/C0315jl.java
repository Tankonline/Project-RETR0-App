package rikka.shizuku;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: renamed from: rikka.shizuku.jl, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0315jl extends F {
    public static final Parcelable.Creator<C0315jl> CREATOR = new E(6);
    public Parcelable c;

    public C0315jl(Parcel parcel, ClassLoader classLoader) {
        super(parcel, classLoader);
        this.c = parcel.readParcelable(classLoader == null ? AbstractC0162el.class.getClassLoader() : classLoader);
    }

    @Override // rikka.shizuku.F, android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeParcelable(this.c, 0);
    }
}
