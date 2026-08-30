package rikka.shizuku;

import android.os.Parcel;
import android.os.Parcelable;
import rikka.html.text.style.BulletSpan;

/* JADX INFO: loaded from: classes.dex */
public final class E implements Parcelable.ClassLoaderCreator {
    public final /* synthetic */ int a;

    public /* synthetic */ E(int i) {
        this.a = i;
    }

    @Override // android.os.Parcelable.ClassLoaderCreator
    public final Object createFromParcel(Parcel parcel, ClassLoader classLoader) {
        switch (this.a) {
            case 0:
                if (parcel.readParcelable(classLoader) == null) {
                    return F.b;
                }
                throw new IllegalStateException("superState must be null");
            case 1:
                return new C0267i2(parcel, classLoader);
            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                return new C0392m5(parcel, classLoader);
            case 3:
                return new S5(parcel, classLoader);
            case 4:
                return new P7(parcel, classLoader);
            case BulletSpan.STANDARD_BULLET_RADIUS /* 5 */:
                return new Fh(parcel, classLoader);
            case 6:
                return new C0315jl(parcel, classLoader);
            case 7:
                return new Nm(parcel, classLoader);
            case 8:
                return new C0042ao(parcel, classLoader);
            case 9:
                return new Hp(parcel, classLoader);
            default:
                return new C0198fq(parcel, classLoader);
        }
    }

    @Override // android.os.Parcelable.Creator
    public final Object[] newArray(int i) {
        switch (this.a) {
            case 0:
                return new F[i];
            case 1:
                return new C0267i2[i];
            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                return new C0392m5[i];
            case 3:
                return new S5[i];
            case 4:
                return new P7[i];
            case BulletSpan.STANDARD_BULLET_RADIUS /* 5 */:
                return new Fh[i];
            case 6:
                return new C0315jl[i];
            case 7:
                return new Nm[i];
            case 8:
                return new C0042ao[i];
            case 9:
                return new Hp[i];
            default:
                return new C0198fq[i];
        }
    }

    @Override // android.os.Parcelable.Creator
    public final Object createFromParcel(Parcel parcel) {
        switch (this.a) {
            case 0:
                if (parcel.readParcelable(null) == null) {
                    return F.b;
                }
                throw new IllegalStateException("superState must be null");
            case 1:
                return new C0267i2(parcel, null);
            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                return new C0392m5(parcel, null);
            case 3:
                return new S5(parcel, null);
            case 4:
                return new P7(parcel, null);
            case BulletSpan.STANDARD_BULLET_RADIUS /* 5 */:
                return new Fh(parcel, null);
            case 6:
                return new C0315jl(parcel, null);
            case 7:
                return new Nm(parcel, null);
            case 8:
                return new C0042ao(parcel, null);
            case 9:
                return new Hp(parcel, null);
            default:
                return new C0198fq(parcel, null);
        }
    }
}
