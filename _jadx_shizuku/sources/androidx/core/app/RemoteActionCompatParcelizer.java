package androidx.core.app;

import android.app.PendingIntent;
import android.os.Parcel;
import android.text.TextUtils;
import androidx.core.graphics.drawable.IconCompat;
import rikka.shizuku.AbstractC0508ps;
import rikka.shizuku.C0539qs;
import rikka.shizuku.InterfaceC0569rs;

/* JADX WARN: Classes with same name are omitted, all sources:
  assets/rish_shizuku.dex
  classes.dex
 */
/* JADX INFO: loaded from: classes.dex */
public class RemoteActionCompatParcelizer {
    public static RemoteActionCompat read(AbstractC0508ps abstractC0508ps) {
        RemoteActionCompat remoteActionCompat = new RemoteActionCompat();
        InterfaceC0569rs interfaceC0569rsG = remoteActionCompat.a;
        boolean z = true;
        if (abstractC0508ps.e(1)) {
            interfaceC0569rsG = abstractC0508ps.g();
        }
        remoteActionCompat.a = (IconCompat) interfaceC0569rsG;
        CharSequence charSequence = remoteActionCompat.b;
        if (abstractC0508ps.e(2)) {
            charSequence = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(((C0539qs) abstractC0508ps).e);
        }
        remoteActionCompat.b = charSequence;
        CharSequence charSequence2 = remoteActionCompat.c;
        if (abstractC0508ps.e(3)) {
            charSequence2 = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(((C0539qs) abstractC0508ps).e);
        }
        remoteActionCompat.c = charSequence2;
        remoteActionCompat.d = (PendingIntent) abstractC0508ps.f(remoteActionCompat.d, 4);
        boolean z2 = remoteActionCompat.e;
        if (abstractC0508ps.e(5)) {
            z2 = ((C0539qs) abstractC0508ps).e.readInt() != 0;
        }
        remoteActionCompat.e = z2;
        boolean z3 = remoteActionCompat.f;
        if (!abstractC0508ps.e(6)) {
            z = z3;
        } else if (((C0539qs) abstractC0508ps).e.readInt() == 0) {
            z = false;
        }
        remoteActionCompat.f = z;
        return remoteActionCompat;
    }

    public static void write(RemoteActionCompat remoteActionCompat, AbstractC0508ps abstractC0508ps) {
        abstractC0508ps.getClass();
        IconCompat iconCompat = remoteActionCompat.a;
        abstractC0508ps.h(1);
        abstractC0508ps.i(iconCompat);
        CharSequence charSequence = remoteActionCompat.b;
        abstractC0508ps.h(2);
        Parcel parcel = ((C0539qs) abstractC0508ps).e;
        TextUtils.writeToParcel(charSequence, parcel, 0);
        CharSequence charSequence2 = remoteActionCompat.c;
        abstractC0508ps.h(3);
        TextUtils.writeToParcel(charSequence2, parcel, 0);
        PendingIntent pendingIntent = remoteActionCompat.d;
        abstractC0508ps.h(4);
        parcel.writeParcelable(pendingIntent, 0);
        boolean z = remoteActionCompat.e;
        abstractC0508ps.h(5);
        parcel.writeInt(z ? 1 : 0);
        boolean z2 = remoteActionCompat.f;
        abstractC0508ps.h(6);
        parcel.writeInt(z2 ? 1 : 0);
    }
}
