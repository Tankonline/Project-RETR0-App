package rikka.shizuku;

import android.os.Parcel;
import android.util.SparseIntArray;

/* JADX INFO: renamed from: rikka.shizuku.qs, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0539qs extends AbstractC0508ps {
    public final SparseIntArray d;
    public final Parcel e;
    public final int f;
    public final int g;
    public final String h;
    public int i;
    public int j;
    public int k;

    public C0539qs(Parcel parcel) {
        this(parcel, parcel.dataPosition(), parcel.dataSize(), "", new C0298j4(0), new C0298j4(0), new C0298j4(0));
    }

    @Override // rikka.shizuku.AbstractC0508ps
    public final C0539qs a() {
        Parcel parcel = this.e;
        int iDataPosition = parcel.dataPosition();
        int i = this.j;
        if (i == this.f) {
            i = this.g;
        }
        return new C0539qs(parcel, iDataPosition, i, this.h + "  ", this.a, this.b, this.c);
    }

    @Override // rikka.shizuku.AbstractC0508ps
    public final boolean e(int i) {
        while (this.j < this.g) {
            int i2 = this.k;
            if (i2 == i) {
                return true;
            }
            if (String.valueOf(i2).compareTo(String.valueOf(i)) > 0) {
                return false;
            }
            int i3 = this.j;
            Parcel parcel = this.e;
            parcel.setDataPosition(i3);
            int i4 = parcel.readInt();
            this.k = parcel.readInt();
            this.j += i4;
        }
        return this.k == i;
    }

    @Override // rikka.shizuku.AbstractC0508ps
    public final void h(int i) {
        int i2 = this.i;
        SparseIntArray sparseIntArray = this.d;
        Parcel parcel = this.e;
        if (i2 >= 0) {
            int i3 = sparseIntArray.get(i2);
            int iDataPosition = parcel.dataPosition();
            parcel.setDataPosition(i3);
            parcel.writeInt(iDataPosition - i3);
            parcel.setDataPosition(iDataPosition);
        }
        this.i = i;
        sparseIntArray.put(i, parcel.dataPosition());
        parcel.writeInt(0);
        parcel.writeInt(i);
    }

    public C0539qs(Parcel parcel, int i, int i2, String str, C0298j4 c0298j4, C0298j4 c0298j5, C0298j4 c0298j6) {
        super(c0298j4, c0298j5, c0298j6);
        this.d = new SparseIntArray();
        this.i = -1;
        this.k = -1;
        this.e = parcel;
        this.f = i;
        this.g = i2;
        this.j = i;
        this.h = str;
    }
}
