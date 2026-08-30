package rikka.shizuku;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public final class Kj implements Parcelable {
    public static final Parcelable.Creator<Kj> CREATOR = new J0(20);
    public final ArrayList a;

    public Kj(ArrayList arrayList) {
        this.a = arrayList;
    }

    public static void a(Iterator it, Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        int i2 = 0;
        parcel.writeInt(0);
        while (it.hasNext() && parcel.dataSize() < 65536) {
            parcel.writeParcelable((Parcelable) it.next(), i);
            i2++;
        }
        int iDataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(iDataPosition);
        parcel.writeInt(i2);
        parcel.setDataPosition(iDataPosition2);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        int i = 0;
        int iDescribeContents = 0;
        while (true) {
            ArrayList arrayList = this.a;
            if (i >= arrayList.size()) {
                return iDescribeContents;
            }
            iDescribeContents |= ((Parcelable) arrayList.get(i)).describeContents();
            i++;
        }
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        ArrayList arrayList = this.a;
        if (arrayList == null) {
            parcel.writeInt(-1);
            return;
        }
        parcel.writeInt(arrayList.size());
        Iterator it = arrayList.iterator();
        a(it, parcel, i);
        if (it.hasNext()) {
            parcel.writeStrongBinder(new S4(this, it, i));
        }
    }

    public Kj(Parcel parcel) {
        int i = parcel.readInt();
        if (i == -1) {
            this.a = null;
            return;
        }
        ArrayList arrayList = new ArrayList(i);
        this.a = arrayList;
        int i2 = parcel.readInt();
        for (int i3 = 0; i3 < i2; i3++) {
            arrayList.add(parcel.readParcelable(Kj.class.getClassLoader()));
        }
        if (arrayList.size() >= i) {
            return;
        }
        IBinder strongBinder = parcel.readStrongBinder();
        do {
            Parcel parcelObtain = Parcel.obtain();
            Parcel parcelObtain2 = Parcel.obtain();
            try {
                try {
                    strongBinder.transact(1, parcelObtain, parcelObtain2, 0);
                    ArrayList arrayList2 = this.a;
                    int i4 = parcelObtain2.readInt();
                    for (int i5 = 0; i5 < i4; i5++) {
                        arrayList2.add(parcelObtain2.readParcelable(Kj.class.getClassLoader()));
                    }
                    parcelObtain.recycle();
                    parcelObtain2.recycle();
                } catch (RemoteException e) {
                    Log.w("ParcelableListSlice", "Failure retrieving array; only received " + this.a.size() + " of " + i, e);
                    parcelObtain.recycle();
                    parcelObtain2.recycle();
                    return;
                }
            } catch (Throwable th) {
                parcelObtain.recycle();
                parcelObtain2.recycle();
                throw th;
            }
        } while (this.a.size() < i);
    }
}
