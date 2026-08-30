package rikka.shizuku.shell;

import android.app.ActivityManagerNative;
import android.app.IActivityManager;
import android.app.IApplicationThread;
import android.app.ProfilerInfo;
import android.content.IIntentReceiver;
import android.content.Intent;
import android.content.pm.IPackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.ServiceManager;
import android.system.Os;
import android.text.TextUtils;
import dalvik.system.BaseDexClassLoader;
import dalvik.system.VMRuntime;
import defpackage.K;
import defpackage.M;
import defpackage.t;
import defpackage.z;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

/* JADX INFO: loaded from: assets/rish_shizuku.dex */
public class ShizukuShellLoader {
    public static String[] a;
    public static String b;
    public static Handler c;
    public static final K d = new K();

    public static void a(IBinder iBinder, String str) {
        String str2 = str.substring(0, str.lastIndexOf(47)) + "/lib/" + VMRuntime.getRuntime().vmInstructionSet();
        String property = System.getProperty("java.library.path");
        if (!TextUtils.isEmpty(property)) {
            str2 = str2 + File.pathSeparatorChar + property;
        }
        try {
            new BaseDexClassLoader(str, null, str2, ClassLoader.getSystemClassLoader()).loadClass("moe.shizuku.manager.shell.Shell").getDeclaredMethod("main", String[].class, String.class, IBinder.class, Handler.class).invoke(null, a, b, iBinder, c);
        } catch (ClassNotFoundException unused) {
            System.err.println("Class not found");
            System.err.println("Make sure you have Shizuku v12.0.0 or above installed");
            System.err.flush();
            System.exit(1);
        } catch (Throwable th) {
            th.printStackTrace(System.err);
            System.err.flush();
            System.exit(1);
        }
    }

    public static void b() {
        Bundle bundle = new Bundle();
        bundle.putBinder("binder", d);
        Intent intentPutExtra = new Intent("rikka.shizuku.intent.action.REQUEST_BINDER").setPackage("moe.shizuku.privileged.api").addFlags(32).putExtra("data", bundle);
        IBinder service = ServiceManager.getService("activity");
        IActivityManager iActivityManagerAsInterface = Build.VERSION.SDK_INT >= 26 ? IActivityManager.Stub.asInterface(service) : ActivityManagerNative.asInterface(service);
        try {
            iActivityManagerAsInterface.broadcastIntent((IApplicationThread) null, intentPutExtra, (String) null, (IIntentReceiver) null, 0, (String) null, (Bundle) null, (String[]) null, -1, (Bundle) null, true, false, 0);
        } catch (Throwable th) {
            if ((Build.VERSION.SDK_INT != 26 && Build.VERSION.SDK_INT != 27) || !Objects.equals(th.getMessage(), "Calling application did not provide package name")) {
                throw th;
            }
            System.err.println("broadcastIntent fails on Android 8.0 or 8.1, fallback to startActivity");
            System.err.flush();
            iActivityManagerAsInterface.startActivityAsUser((IApplicationThread) null, b, Intent.createChooser(new Intent("rikka.shizuku.intent.action.REQUEST_BINDER").addFlags(67108864).addFlags(268435456).addFlags(524288).putExtra("data", bundle), "Request binder from Shizuku"), (String) null, (IBinder) null, (String) null, 0, 0, (ProfilerInfo) null, (Bundle) null, Os.getuid() / 100000);
        }
    }

    public static void main(String[] strArr) {
        String str;
        a = strArr;
        int i = Os.getuid();
        ArrayList arrayList = new ArrayList();
        try {
            M m = t.m;
            IPackageManager iPackageManagerAsInterface = m.b;
            if (iPackageManagerAsInterface == null) {
                IBinder iBinderA = m.a();
                if (iBinderA == null) {
                    iPackageManagerAsInterface = null;
                } else {
                    iPackageManagerAsInterface = IPackageManager.Stub.asInterface(iBinderA);
                    m.b = iPackageManagerAsInterface;
                }
            }
            String[] packagesForUid = iPackageManagerAsInterface.getPackagesForUid(i);
            if (packagesForUid != null) {
                for (String str2 : packagesForUid) {
                    if (str2 != null) {
                        arrayList.add(str2);
                    }
                }
            }
        } catch (Throwable unused) {
        }
        if (arrayList.size() == 1) {
            str = (String) arrayList.get(0);
        } else {
            str = System.getenv("RISH_APPLICATION_ID");
            if (TextUtils.isEmpty(str) || "PKG".equals(str)) {
                System.err.println("RISH_APPLICATION_ID is not set, set this environment variable to the id of current application (package name)");
                System.err.flush();
                System.exit(1);
                System.exit(1);
            }
        }
        b = str;
        if (Looper.getMainLooper() == null) {
            Looper.prepareMainLooper();
        }
        c = new Handler(Looper.getMainLooper());
        try {
            b();
        } catch (Throwable th) {
            th.printStackTrace(System.err);
            System.err.flush();
            System.exit(1);
        }
        c.postDelayed(new z(3, str), 5000L);
        Looper.loop();
        System.exit(0);
    }
}
