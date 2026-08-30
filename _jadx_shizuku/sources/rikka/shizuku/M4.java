package rikka.shizuku;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class M4 implements Parcelable {
    public static final Parcelable.Creator<M4> CREATOR = new J0(2);
    public final int[] a;
    public final ArrayList b;
    public final int[] c;
    public final int[] d;
    public final int e;
    public final String f;
    public final int g;
    public final int h;
    public final CharSequence i;
    public final int j;
    public final CharSequence k;
    public final ArrayList l;
    public final ArrayList m;
    public final boolean n;

    public M4(L4 l4) {
        int size = l4.a.size();
        this.a = new int[size * 6];
        if (!l4.g) {
            throw new IllegalStateException("Not on back stack");
        }
        this.b = new ArrayList(size);
        this.c = new int[size];
        this.d = new int[size];
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            C0062bd c0062bd = (C0062bd) l4.a.get(i2);
            int i3 = i + 1;
            this.a[i] = c0062bd.a;
            ArrayList arrayList = this.b;
            Ac ac = c0062bd.b;
            arrayList.add(ac != null ? ac.e : null);
            int[] iArr = this.a;
            iArr[i3] = c0062bd.c ? 1 : 0;
            iArr[i + 2] = c0062bd.d;
            iArr[i + 3] = c0062bd.e;
            int i4 = i + 5;
            iArr[i + 4] = c0062bd.f;
            i += 6;
            iArr[i4] = c0062bd.g;
            this.c[i2] = c0062bd.h.ordinal();
            this.d[i2] = c0062bd.i.ordinal();
        }
        this.e = l4.f;
        this.f = l4.i;
        this.g = l4.t;
        this.h = l4.j;
        this.i = l4.k;
        this.j = l4.l;
        this.k = l4.m;
        this.l = l4.n;
        this.m = l4.o;
        this.n = l4.p;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeIntArray(this.a);
        parcel.writeStringList(this.b);
        parcel.writeIntArray(this.c);
        parcel.writeIntArray(this.d);
        parcel.writeInt(this.e);
        parcel.writeString(this.f);
        parcel.writeInt(this.g);
        parcel.writeInt(this.h);
        TextUtils.writeToParcel(this.i, parcel, 0);
        parcel.writeInt(this.j);
        TextUtils.writeToParcel(this.k, parcel, 0);
        parcel.writeStringList(this.l);
        parcel.writeStringList(this.m);
        parcel.writeInt(this.n ? 1 : 0);
    }

    public M4(Parcel parcel) {
        this.a = parcel.createIntArray();
        this.b = parcel.createStringArrayList();
        this.c = parcel.createIntArray();
        this.d = parcel.createIntArray();
        this.e = parcel.readInt();
        this.f = parcel.readString();
        this.g = parcel.readInt();
        this.h = parcel.readInt();
        Parcelable.Creator creator = TextUtils.CHAR_SEQUENCE_CREATOR;
        this.i = (CharSequence) creator.createFromParcel(parcel);
        this.j = parcel.readInt();
        this.k = (CharSequence) creator.createFromParcel(parcel);
        this.l = parcel.createStringArrayList();
        this.m = parcel.createStringArrayList();
        this.n = parcel.readInt() != 0;
    }
}
