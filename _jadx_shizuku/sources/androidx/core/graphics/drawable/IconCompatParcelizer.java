package androidx.core.graphics.drawable;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Parcel;
import android.os.Parcelable;
import java.nio.charset.Charset;
import rikka.html.text.style.BulletSpan;
import rikka.shizuku.AbstractC0508ps;
import rikka.shizuku.C0539qs;

/* JADX WARN: Classes with same name are omitted, all sources:
  assets/rish_shizuku.dex
  classes.dex
 */
/* JADX INFO: loaded from: classes.dex */
public class IconCompatParcelizer {
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static IconCompat read(AbstractC0508ps abstractC0508ps) {
        IconCompat iconCompat = new IconCompat();
        int i = iconCompat.a;
        if (abstractC0508ps.e(1)) {
            i = ((C0539qs) abstractC0508ps).e.readInt();
        }
        iconCompat.a = i;
        byte[] bArr = iconCompat.c;
        if (abstractC0508ps.e(2)) {
            Parcel parcel = ((C0539qs) abstractC0508ps).e;
            int i2 = parcel.readInt();
            if (i2 < 0) {
                bArr = null;
            } else {
                byte[] bArr2 = new byte[i2];
                parcel.readByteArray(bArr2);
                bArr = bArr2;
            }
        }
        iconCompat.c = bArr;
        iconCompat.d = abstractC0508ps.f(iconCompat.d, 3);
        int i3 = iconCompat.e;
        if (abstractC0508ps.e(4)) {
            i3 = ((C0539qs) abstractC0508ps).e.readInt();
        }
        iconCompat.e = i3;
        int i4 = iconCompat.f;
        if (abstractC0508ps.e(5)) {
            i4 = ((C0539qs) abstractC0508ps).e.readInt();
        }
        iconCompat.f = i4;
        iconCompat.g = (ColorStateList) abstractC0508ps.f(iconCompat.g, 6);
        String string = iconCompat.i;
        if (abstractC0508ps.e(7)) {
            string = ((C0539qs) abstractC0508ps).e.readString();
        }
        iconCompat.i = string;
        String string2 = iconCompat.j;
        if (abstractC0508ps.e(8)) {
            string2 = ((C0539qs) abstractC0508ps).e.readString();
        }
        iconCompat.j = string2;
        iconCompat.h = PorterDuff.Mode.valueOf(iconCompat.i);
        switch (iconCompat.a) {
            case -1:
                Parcelable parcelable = iconCompat.d;
                if (parcelable == null) {
                    throw new IllegalArgumentException("Invalid icon");
                }
                iconCompat.b = parcelable;
                return iconCompat;
            case 0:
            default:
                return iconCompat;
            case 1:
            case BulletSpan.STANDARD_BULLET_RADIUS /* 5 */:
                Parcelable parcelable2 = iconCompat.d;
                if (parcelable2 != null) {
                    iconCompat.b = parcelable2;
                    return iconCompat;
                }
                byte[] bArr3 = iconCompat.c;
                iconCompat.b = bArr3;
                iconCompat.a = 3;
                iconCompat.e = 0;
                iconCompat.f = bArr3.length;
                return iconCompat;
            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
            case 4:
            case 6:
                String str = new String(iconCompat.c, Charset.forName("UTF-16"));
                iconCompat.b = str;
                if (iconCompat.a == 2 && iconCompat.j == null) {
                    iconCompat.j = str.split(":", -1)[0];
                }
                return iconCompat;
            case 3:
                iconCompat.b = iconCompat.c;
                return iconCompat;
        }
    }

    public static void write(IconCompat iconCompat, AbstractC0508ps abstractC0508ps) {
        abstractC0508ps.getClass();
        iconCompat.i = iconCompat.h.name();
        switch (iconCompat.a) {
            case -1:
                iconCompat.d = (Parcelable) iconCompat.b;
                break;
            case 1:
            case BulletSpan.STANDARD_BULLET_RADIUS /* 5 */:
                iconCompat.d = (Parcelable) iconCompat.b;
                break;
            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                iconCompat.c = ((String) iconCompat.b).getBytes(Charset.forName("UTF-16"));
                break;
            case 3:
                iconCompat.c = (byte[]) iconCompat.b;
                break;
            case 4:
            case 6:
                iconCompat.c = iconCompat.b.toString().getBytes(Charset.forName("UTF-16"));
                break;
        }
        int i = iconCompat.a;
        if (-1 != i) {
            abstractC0508ps.h(1);
            ((C0539qs) abstractC0508ps).e.writeInt(i);
        }
        byte[] bArr = iconCompat.c;
        if (bArr != null) {
            abstractC0508ps.h(2);
            int length = bArr.length;
            Parcel parcel = ((C0539qs) abstractC0508ps).e;
            parcel.writeInt(length);
            parcel.writeByteArray(bArr);
        }
        Parcelable parcelable = iconCompat.d;
        if (parcelable != null) {
            abstractC0508ps.h(3);
            ((C0539qs) abstractC0508ps).e.writeParcelable(parcelable, 0);
        }
        int i2 = iconCompat.e;
        if (i2 != 0) {
            abstractC0508ps.h(4);
            ((C0539qs) abstractC0508ps).e.writeInt(i2);
        }
        int i3 = iconCompat.f;
        if (i3 != 0) {
            abstractC0508ps.h(5);
            ((C0539qs) abstractC0508ps).e.writeInt(i3);
        }
        ColorStateList colorStateList = iconCompat.g;
        if (colorStateList != null) {
            abstractC0508ps.h(6);
            ((C0539qs) abstractC0508ps).e.writeParcelable(colorStateList, 0);
        }
        String str = iconCompat.i;
        if (str != null) {
            abstractC0508ps.h(7);
            ((C0539qs) abstractC0508ps).e.writeString(str);
        }
        String str2 = iconCompat.j;
        if (str2 != null) {
            abstractC0508ps.h(8);
            ((C0539qs) abstractC0508ps).e.writeString(str2);
        }
    }
}
