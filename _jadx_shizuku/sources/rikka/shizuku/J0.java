package rikka.shizuku;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.versionedparcelable.ParcelImpl;
import java.util.ArrayList;
import moe.shizuku.api.BinderContainer;
import rikka.html.text.style.BulletSpan;

/* JADX INFO: loaded from: classes.dex */
public final class J0 implements Parcelable.Creator {
    public final /* synthetic */ int a;

    @Override // android.os.Parcelable.Creator
    public final Object createFromParcel(Parcel parcel) {
        switch (this.a) {
            case 0:
                return new K0(parcel);
            case 1:
                C0237h3 c0237h3 = new C0237h3(parcel);
                c0237h3.a = parcel.readByte() != 0;
                return c0237h3;
            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                return new M4(parcel);
            case 3:
                return new N4(parcel);
            case 4:
                BinderContainer binderContainer = new BinderContainer();
                binderContainer.a = parcel.readStrongBinder();
                return binderContainer;
            case BulletSpan.STANDARD_BULLET_RADIUS /* 5 */:
                return new C0608t5((Ei) parcel.readParcelable(Ei.class.getClassLoader()), (Ei) parcel.readParcelable(Ei.class.getClassLoader()), (U8) parcel.readParcelable(U8.class.getClassLoader()), (Ei) parcel.readParcelable(Ei.class.getClassLoader()), parcel.readInt());
            case 6:
                return new U8(parcel.readLong());
            case 7:
                return new Ba(parcel);
            case 8:
                Mc mc = new Mc();
                mc.a = parcel.readString();
                mc.b = parcel.readInt();
                return mc;
            case 9:
                Rc rc = new Rc();
                rc.e = null;
                rc.f = new ArrayList();
                rc.g = new ArrayList();
                rc.a = parcel.createStringArrayList();
                rc.b = parcel.createStringArrayList();
                rc.c = (M4[]) parcel.createTypedArray(M4.CREATOR);
                rc.d = parcel.readInt();
                rc.e = parcel.readString();
                rc.f = parcel.createStringArrayList();
                rc.g = parcel.createTypedArrayList(N4.CREATOR);
                rc.h = parcel.createTypedArrayList(Mc.CREATOR);
                return rc;
            case 10:
                return new Wc(parcel);
            case 11:
                return new C0711wf(parcel);
            case 12:
                return new C0742xf((IntentSender) parcel.readParcelable(IntentSender.class.getClassLoader()), (Intent) parcel.readParcelable(Intent.class.getClassLoader()), parcel.readInt(), parcel.readInt());
            case 13:
                Ig ig = new Ig();
                ig.a = parcel.readInt();
                ig.b = parcel.readInt();
                ig.c = parcel.readInt() == 1;
                return ig;
            case 14:
                return new C0035ah(parcel);
            case 15:
                Sh sh = new Sh(parcel);
                sh.a = ((Integer) parcel.readValue(Sh.class.getClassLoader())).intValue();
                return sh;
            case 16:
                return Ei.a(parcel.readInt(), parcel.readInt());
            case 17:
                return new Ki(parcel);
            case 18:
                Ti ti = new Ti(parcel);
                ti.a = parcel.readInt();
                return ti;
            case 19:
                return new ParcelImpl(parcel);
            case 20:
                return new Kj(parcel);
            case 21:
                return new C0038ak(parcel);
            case 22:
                return new C0314jk(parcel);
            case 23:
                return new Qm(parcel);
            case 24:
                Jo jo = new Jo();
                jo.a = parcel.readInt();
                jo.b = parcel.readInt();
                jo.d = parcel.readInt() == 1;
                int i = parcel.readInt();
                if (i > 0) {
                    int[] iArr = new int[i];
                    jo.c = iArr;
                    parcel.readIntArray(iArr);
                }
                return jo;
            case 25:
                Ko ko = new Ko();
                ko.a = parcel.readInt();
                ko.b = parcel.readInt();
                int i2 = parcel.readInt();
                ko.c = i2;
                if (i2 > 0) {
                    int[] iArr2 = new int[i2];
                    ko.d = iArr2;
                    parcel.readIntArray(iArr2);
                }
                int i3 = parcel.readInt();
                ko.e = i3;
                if (i3 > 0) {
                    int[] iArr3 = new int[i3];
                    ko.f = iArr3;
                    parcel.readIntArray(iArr3);
                }
                ko.h = parcel.readInt() == 1;
                ko.i = parcel.readInt() == 1;
                ko.j = parcel.readInt() == 1;
                ko.g = parcel.readArrayList(Jo.class.getClassLoader());
                return ko;
            default:
                return new Jq(parcel);
        }
    }

    @Override // android.os.Parcelable.Creator
    public final Object[] newArray(int i) {
        switch (this.a) {
            case 0:
                return new K0[i];
            case 1:
                return new C0237h3[i];
            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                return new M4[i];
            case 3:
                return new N4[i];
            case 4:
                return new BinderContainer[i];
            case BulletSpan.STANDARD_BULLET_RADIUS /* 5 */:
                return new C0608t5[i];
            case 6:
                return new U8[i];
            case 7:
                return new Ba[i];
            case 8:
                return new Mc[i];
            case 9:
                return new Rc[i];
            case 10:
                return new Wc[i];
            case 11:
                return new C0711wf[i];
            case 12:
                return new C0742xf[i];
            case 13:
                return new Ig[i];
            case 14:
                return new C0035ah[i];
            case 15:
                return new Sh[i];
            case 16:
                return new Ei[i];
            case 17:
                return new Ki[i];
            case 18:
                return new Ti[i];
            case 19:
                return new ParcelImpl[i];
            case 20:
                return new Kj[i];
            case 21:
                return new C0038ak[i];
            case 22:
                return new C0314jk[i];
            case 23:
                return new Qm[i];
            case 24:
                return new Jo[i];
            case 25:
                return new Ko[i];
            default:
                return new Jq[i];
        }
    }
}
