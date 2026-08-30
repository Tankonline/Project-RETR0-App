package moe.shizuku.starter;

import android.content.IContentProvider;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.system.Os;
import android.util.Log;
import android.util.Pair;
import moe.shizuku.api.BinderContainer;
import rikka.shizuku.AbstractC0604t1;
import rikka.shizuku.C0072bn;
import rikka.shizuku.Vr;
import rikka.shizuku.X8;

/* JADX INFO: loaded from: classes.dex */
public class ServiceStarter {
    public static final String a;

    static {
        int i = Build.VERSION.SDK_INT;
        if (i >= 30) {
            a = "-Xcompiler-option --debuggable -XjdwpProvider:adbconnection -XjdwpOptions:suspend=n,server=y";
        } else if (i >= 28) {
            a = "-Xcompiler-option --debuggable -XjdwpProvider:internal -XjdwpOptions:transport=dt_android_adb,suspend=n,server=y";
        } else {
            a = "-Xcompiler-option --debuggable -agentlib:jdwp=transport=dt_android_adb,suspend=n,server=y";
        }
    }

    /* JADX WARN: Code duplicated, block: B:45:0x00ec A[Catch: all -> 0x002f, TryCatch #8 {all -> 0x002f, blocks: (B:5:0x0011, B:13:0x0033, B:15:0x003d, B:17:0x0050, B:26:0x007d, B:27:0x0082, B:30:0x009f, B:45:0x00ec, B:47:0x010e, B:49:0x0112, B:51:0x0118, B:58:0x012e, B:42:0x00e1, B:43:0x00e6), top: B:91:0x000f }] */
    public static boolean a(IBinder iBinder, String str, boolean z) {
        Throwable th;
        IContentProvider iContentProviderY;
        Bundle bundleCall;
        BinderContainer binderContainer;
        IBinder iBinder2;
        String str2 = "moe.shizuku.privileged.api.shizuku";
        try {
            try {
                iContentProviderY = X8.y(0, "moe.shizuku.privileged.api.shizuku", "moe.shizuku.privileged.api.shizuku");
                try {
                    if (iContentProviderY == null) {
                        Log.e("ShizukuServiceStarter", String.format("provider is null %s %d", "moe.shizuku.privileged.api.shizuku", 0));
                        if (iContentProviderY != null) {
                            X8.S("moe.shizuku.privileged.api.shizuku");
                            return false;
                        }
                    } else {
                        if (!iContentProviderY.asBinder().pingBinder()) {
                            Log.e("ShizukuServiceStarter", String.format("provider is dead %s %d", "moe.shizuku.privileged.api.shizuku", 0));
                            if (!z) {
                                X8.S("moe.shizuku.privileged.api.shizuku");
                                return false;
                            }
                            X8.v(0, "moe.shizuku.privileged.api");
                            Log.e("ShizukuServiceStarter", String.format("kill %s in user %d and try again", "moe.shizuku.privileged.api", 0));
                            Thread.sleep(1000L);
                            boolean zA = a(iBinder, str, false);
                            try {
                                X8.S("moe.shizuku.privileged.api.shizuku");
                                return zA;
                            } catch (Throwable th2) {
                                Log.w("ShizukuServiceStarter", "removeContentProviderExternal", th2);
                                return zA;
                            }
                        }
                        if (!z) {
                            Log.e("ShizukuServiceStarter", "retry works");
                        }
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("moe.shizuku.privileged.api.intent.extra.BINDER", new BinderContainer(iBinder));
                        bundle.putString("shizuku:user-service-arg-token", str);
                        String str3 = "sendUserService";
                        int i = Build.VERSION.SDK_INT;
                        try {
                            if (i >= 31) {
                                try {
                                    AbstractC0604t1.l();
                                    bundleCall = iContentProviderY.call(AbstractC0604t1.d(Os.getuid()).setAttributionTag(null).setPackageName(null).build(), "moe.shizuku.privileged.api.shizuku", "sendUserService", (String) null, bundle);
                                } catch (LinkageError unused) {
                                    str3 = "moe.shizuku.privileged.api.shizuku";
                                    bundleCall = iContentProviderY.call((String) null, (String) null, str3, "sendUserService", (String) null, bundle);
                                    str2 = str3;
                                }
                            } else {
                                if (i >= 30) {
                                    str3 = "moe.shizuku.privileged.api.shizuku";
                                    bundleCall = iContentProviderY.call((String) null, (String) null, str3, "sendUserService", (String) null, bundle);
                                    str2 = str3;
                                } else {
                                    str2 = "moe.shizuku.privileged.api.shizuku";
                                    bundleCall = i >= 29 ? iContentProviderY.call((String) null, str2, "sendUserService", (String) null, bundle) : iContentProviderY.call((String) null, "sendUserService", (String) null, bundle);
                                }
                                if (bundleCall != null) {
                                    bundleCall.setClassLoader(BinderContainer.class.getClassLoader());
                                    Log.i("ShizukuServiceStarter", String.format("send binder to %s in user %d", "moe.shizuku.privileged.api", 0));
                                    binderContainer = (BinderContainer) bundleCall.getParcelable("moe.shizuku.privileged.api.intent.extra.BINDER");
                                    if (binderContainer == null && (iBinder2 = binderContainer.a) != null && iBinder2.pingBinder()) {
                                        binderContainer.a.linkToDeath(new C0072bn(0), 0);
                                        try {
                                            X8.S(str2);
                                            return true;
                                        } catch (Throwable th3) {
                                            Log.w("ShizukuServiceStarter", "removeContentProviderExternal", th3);
                                            return true;
                                        }
                                    }
                                    Log.w("ShizukuServiceStarter", "server binder not received");
                                }
                                X8.S(str2);
                            }
                            if (bundleCall != null) {
                                bundleCall.setClassLoader(BinderContainer.class.getClassLoader());
                                Log.i("ShizukuServiceStarter", String.format("send binder to %s in user %d", "moe.shizuku.privileged.api", 0));
                                binderContainer = (BinderContainer) bundleCall.getParcelable("moe.shizuku.privileged.api.intent.extra.BINDER");
                                if (binderContainer == null) {
                                }
                                Log.w("ShizukuServiceStarter", "server binder not received");
                            }
                            X8.S(str2);
                        } catch (Throwable th4) {
                            th = th4;
                            str2 = str3;
                            th = th;
                            try {
                                Log.e("ShizukuServiceStarter", String.format("failed send binder to %s in user %d", "moe.shizuku.privileged.api", 0), th);
                                if (iContentProviderY != null) {
                                    X8.S(str2);
                                }
                            } catch (Throwable th5) {
                                if (iContentProviderY == null) {
                                    throw th5;
                                }
                                try {
                                    X8.S(str2);
                                    throw th5;
                                } catch (Throwable th6) {
                                    Log.w("ShizukuServiceStarter", "removeContentProviderExternal", th6);
                                    throw th5;
                                }
                            }
                        }
                    }
                } catch (Throwable th7) {
                    th = th7;
                }
            } catch (Throwable th8) {
                th = th8;
                iContentProviderY = null;
            }
        } catch (Throwable th9) {
            Log.w("ShizukuServiceStarter", "removeContentProviderExternal", th9);
        }
        return false;
    }

    public static void main(String[] strArr) {
        if (Looper.getMainLooper() == null) {
            Looper.prepareMainLooper();
        }
        Vr.a = "ShizukuServiceStarter";
        Pair pairA = Vr.a(strArr);
        if (pairA == null) {
            System.exit(1);
            return;
        }
        if (!a((IBinder) pairA.first, (String) pairA.second, true)) {
            System.exit(1);
        }
        Looper.loop();
        System.exit(0);
        Log.i("ShizukuServiceStarter", "service exited");
    }
}
