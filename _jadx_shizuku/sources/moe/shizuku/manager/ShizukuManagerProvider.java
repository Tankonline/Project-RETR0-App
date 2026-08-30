package moe.shizuku.manager;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcel;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import moe.shizuku.api.BinderContainer;
import rikka.shizuku.AbstractC0125de;
import rikka.shizuku.AbstractC0628tp;
import rikka.shizuku.An;
import rikka.shizuku.C0562rl;
import rikka.shizuku.Nn;
import rikka.shizuku.Sn;

/* JADX INFO: loaded from: classes.dex */
public final class ShizukuManagerProvider extends ContentProvider {
    public static boolean a = true;

    public final Bundle a(String str, String str2, Bundle bundle) {
        BinderContainer binderContainer;
        IBinder iBinder;
        if (An.a) {
            Log.w("ShizukuProvider", "Provider called when Sui is available. Are you using Shizuku and Sui at the same time?");
            return new Bundle();
        }
        if (bundle == null) {
            return null;
        }
        bundle.setClassLoader(BinderContainer.class.getClassLoader());
        Bundle bundle2 = new Bundle();
        str.getClass();
        if (str.equals("sendBinder")) {
            if (!Nn.h() && (binderContainer = (BinderContainer) bundle.getParcelable("moe.shizuku.privileged.api.intent.extra.BINDER")) != null && (iBinder = binderContainer.a) != null) {
                Nn.g(iBinder, getContext().getPackageName());
            }
        } else if (str.equals("getBinder")) {
            IBinder iBinder2 = Nn.a;
            if (iBinder2 == null || !iBinder2.pingBinder()) {
                return null;
            }
            bundle2.putParcelable("moe.shizuku.privileged.api.intent.extra.BINDER", new BinderContainer(iBinder2));
            return bundle2;
        }
        return bundle2;
    }

    @Override // android.content.ContentProvider
    public final void attachInfo(Context context, ProviderInfo providerInfo) {
        super.attachInfo(context, providerInfo);
        if (providerInfo.multiprocess) {
            throw new IllegalStateException("android:multiprocess must be false");
        }
        if (!providerInfo.exported) {
            throw new IllegalStateException("android:exported must be true");
        }
    }

    public final boolean b() {
        if (a && !An.a) {
            String packageName = getContext().getPackageName();
            HashMap map = AbstractC0628tp.a;
            IBinder iBinder = (IBinder) map.get("activity");
            IBinder iBinder2 = null;
            if (iBinder == null) {
                try {
                    iBinder = (IBinder) AbstractC0628tp.b.invoke(null, "activity");
                } catch (IllegalAccessException | InvocationTargetException e) {
                    Log.w("SystemServiceHelper", Log.getStackTraceString(e));
                }
                map.put("activity", iBinder);
            }
            if (iBinder != null) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.app.IActivityManager");
                    parcelObtain.writeInt(2);
                    iBinder.transact(1599296841, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    IBinder strongBinder = parcelObtain2.readStrongBinder();
                    if (strongBinder != null) {
                        parcelObtain.recycle();
                        parcelObtain2.recycle();
                        iBinder2 = strongBinder;
                    } else {
                        parcelObtain.recycle();
                        parcelObtain2.recycle();
                    }
                } catch (Throwable th) {
                    try {
                        th.printStackTrace();
                    } catch (Throwable th2) {
                        parcelObtain.recycle();
                        parcelObtain2.recycle();
                        throw th2;
                    }
                }
            }
            if (iBinder2 != null) {
                Nn.g(iBinder2, packageName);
                An.a = true;
            } else {
                An.a = false;
            }
        }
        return true;
    }

    @Override // android.content.ContentProvider
    public final Bundle call(String str, String str2, Bundle bundle) {
        BinderContainer binderContainer;
        IBinder iBinder;
        if (bundle == null) {
            return null;
        }
        if (!str.equals("sendUserService")) {
            return a(str, str2, bundle);
        }
        try {
            bundle.setClassLoader(BinderContainer.class.getClassLoader());
            String string = bundle.getString("shizuku:user-service-arg-token");
            if (string != null && (binderContainer = (BinderContainer) bundle.getParcelable("moe.shizuku.privileged.api.intent.extra.BINDER")) != null && (iBinder = binderContainer.a) != null) {
                CountDownLatch countDownLatch = new CountDownLatch(1);
                C0562rl c0562rl = new C0562rl();
                c0562rl.a = new Bundle();
                Nn.b(new Sn(iBinder, string, c0562rl, countDownLatch), true, (Handler) AbstractC0125de.b.getValue());
                try {
                    countDownLatch.await(5L, TimeUnit.SECONDS);
                    return (Bundle) c0562rl.a;
                } catch (TimeoutException e) {
                    Locale locale = Locale.ENGLISH;
                    Log.e("ShizukuManager", "Binder not received in 5s", e);
                    return null;
                }
            }
            return null;
        } catch (Throwable th) {
            Locale locale2 = Locale.ENGLISH;
            Log.e("ShizukuManager", "sendUserService", th);
            return null;
        }
    }

    @Override // android.content.ContentProvider
    public final int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    @Override // android.content.ContentProvider
    public final String getType(Uri uri) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final boolean onCreate() {
        a = false;
        b();
        return true;
    }

    @Override // android.content.ContentProvider
    public final Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }
}
