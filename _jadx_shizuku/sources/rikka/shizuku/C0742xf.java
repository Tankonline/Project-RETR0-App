package rikka.shizuku;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: renamed from: rikka.shizuku.xf, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0742xf implements Parcelable {
    public static final Parcelable.Creator<C0742xf> CREATOR = new J0(12);
    public final IntentSender a;
    public final Intent b;
    public final int c;
    public final int d;

    public C0742xf(IntentSender intentSender, Intent intent, int i, int i2) {
        this.a = intentSender;
        this.b = intent;
        this.c = i;
        this.d = i2;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.a, i);
        parcel.writeParcelable(this.b, i);
        parcel.writeInt(this.c);
        parcel.writeInt(this.d);
    }
}
