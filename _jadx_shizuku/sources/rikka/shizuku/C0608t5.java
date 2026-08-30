package rikka.shizuku;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;

/* JADX INFO: renamed from: rikka.shizuku.t5, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0608t5 implements Parcelable {
    public static final Parcelable.Creator<C0608t5> CREATOR = new J0(5);
    public final Ei a;
    public final Ei b;
    public final U8 c;
    public final Ei d;
    public final int e;
    public final int f;
    public final int g;

    public C0608t5(Ei ei, Ei ei2, U8 u8, Ei ei3, int i) {
        this.a = ei;
        this.b = ei2;
        this.d = ei3;
        this.e = i;
        this.c = u8;
        if (ei3 != null && ei.a.compareTo(ei3.a) > 0) {
            throw new IllegalArgumentException("start Month cannot be after current Month");
        }
        if (ei3 != null && ei3.a.compareTo(ei2.a) > 0) {
            throw new IllegalArgumentException("current Month cannot be after end Month");
        }
        if (i < 0 || i > Zr.c(null).getMaximum(7)) {
            throw new IllegalArgumentException("firstDayOfWeek is not valid");
        }
        this.g = ei.d(ei2) + 1;
        this.f = (ei2.c - ei.c) + 1;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C0608t5)) {
            return false;
        }
        C0608t5 c0608t5 = (C0608t5) obj;
        return this.a.equals(c0608t5.a) && this.b.equals(c0608t5.b) && Objects.equals(this.d, c0608t5.d) && this.e == c0608t5.e && this.c.equals(c0608t5.c);
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.a, this.b, this.d, Integer.valueOf(this.e), this.c});
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.a, 0);
        parcel.writeParcelable(this.b, 0);
        parcel.writeParcelable(this.d, 0);
        parcel.writeParcelable(this.c, 0);
        parcel.writeInt(this.e);
    }
}
