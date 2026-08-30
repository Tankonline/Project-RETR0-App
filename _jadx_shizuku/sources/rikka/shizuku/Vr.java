package rikka.shizuku;

import android.app.ActivityThread;
import android.app.Application;
import android.app.ContextImpl;
import android.app.Instrumentation;
import android.content.Context;
import android.ddm.DdmHandleAppName;
import android.os.IBinder;
import android.os.UserHandle;
import android.util.Log;
import android.util.Pair;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/* JADX INFO: loaded from: classes.dex */
public abstract class Vr {
    public static String a;

    public static Pair a(String[] strArr) {
        Constructor<?> constructor;
        int i = -1;
        String strSubstring = null;
        String strSubstring2 = null;
        String strSubstring3 = null;
        String strSubstring4 = null;
        for (String str : strArr) {
            if (str.startsWith("--debug-name=")) {
                strSubstring3 = str.substring(13);
            } else if (str.startsWith("--token=")) {
                strSubstring4 = str.substring(8);
            } else if (str.startsWith("--package=")) {
                strSubstring = str.substring(10);
            } else if (str.startsWith("--class=")) {
                strSubstring2 = str.substring(8);
            } else if (str.startsWith("--uid=")) {
                i = Integer.parseInt(str.substring(6));
            }
        }
        int i2 = i / 100000;
        Log.i(a, "starting service " + strSubstring + "/" + strSubstring2 + "...");
        try {
            ActivityThread activityThreadSystemMain = ActivityThread.systemMain();
            ContextImpl systemContext = activityThreadSystemMain.getSystemContext();
            if (strSubstring3 == null) {
                strSubstring3 = strSubstring + ":user_service";
            }
            DdmHandleAppName.setAppName(strSubstring3, i2);
            Context contextCreatePackageContextAsUser = systemContext.createPackageContextAsUser(strSubstring, 3, UserHandle.of(i2));
            Field declaredField = contextCreatePackageContextAsUser.getClass().getDeclaredField("mPackageInfo");
            declaredField.setAccessible(true);
            Object obj = declaredField.get(contextCreatePackageContextAsUser);
            Application application = (Application) obj.getClass().getDeclaredMethod("makeApplication", Boolean.TYPE, Instrumentation.class).invoke(obj, Boolean.TRUE, null);
            Field declaredField2 = activityThreadSystemMain.getClass().getDeclaredField("mInitialApplication");
            declaredField2.setAccessible(true);
            declaredField2.set(activityThreadSystemMain, application);
            Class<?> clsLoadClass = application.getClassLoader().loadClass(strSubstring2);
            try {
                constructor = clsLoadClass.getConstructor(Context.class);
            } catch (NoSuchMethodException | SecurityException unused) {
                constructor = null;
            }
            return new Pair(constructor != null ? (IBinder) constructor.newInstance(application) : (IBinder) clsLoadClass.newInstance(), strSubstring4);
        } catch (Throwable th) {
            Log.w(a, "unable to start service " + strSubstring + "/" + strSubstring2 + "...", th);
            return null;
        }
    }
}
