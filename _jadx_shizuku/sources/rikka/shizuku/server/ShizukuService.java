package rikka.shizuku.server;

import android.content.IContentProvider;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.ddm.DdmHandleAppName;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import moe.shizuku.api.BinderContainer;
import rikka.shizuku.AbstractBinderC0041an;
import rikka.shizuku.AbstractC0134dn;
import rikka.shizuku.AbstractC0146e5;
import rikka.shizuku.AbstractC0484p4;
import rikka.shizuku.AbstractC0535qo;
import rikka.shizuku.AbstractC0599sr;
import rikka.shizuku.AbstractC0604t1;
import rikka.shizuku.AbstractC0688vn;
import rikka.shizuku.AbstractC0744xh;
import rikka.shizuku.Bj;
import rikka.shizuku.C0455o6;
import rikka.shizuku.Cj;
import rikka.shizuku.G0;
import rikka.shizuku.Kj;
import rikka.shizuku.Pn;
import rikka.shizuku.R2;
import rikka.shizuku.Rn;
import rikka.shizuku.Tn;
import rikka.shizuku.X8;
import rikka.shizuku.Xn;
import rikka.shizuku.Xr;
import rikka.shizuku.Ye;

/* JADX INFO: loaded from: classes.dex */
public class ShizukuService extends AbstractBinderC0041an {
    public static final /* synthetic */ int j = 0;
    public final R2 g;
    public final Rn h;
    public final int i;

    public ShizukuService() {
        Handler handler = new Handler(Looper.myLooper());
        X8.r = handler;
        Log.println(4, "Service", "starting server...");
        t("package");
        t("activity");
        t("user");
        t("appops");
        ApplicationInfo applicationInfoN = AbstractC0599sr.n(0, "moe.shizuku.privileged.api");
        if (applicationInfoN == null) {
            System.exit(50);
        }
        this.i = applicationInfoN.uid;
        this.h = this.b;
        this.g = this.c;
        AbstractC0599sr.L(applicationInfoN.sourceDir, new Tn());
        AbstractC0146e5.b(this);
        handler.post(new G0(18, this));
    }

    public static void main(String[] strArr) {
        DdmHandleAppName.setAppName("shizuku_server", 0);
        AbstractC0744xh.p = System.getProperty("shizuku.library.path");
        Looper.prepareMainLooper();
        new ShizukuService();
        Looper.loop();
    }

    /* JADX WARN: Code duplicated, block: B:123:0x028e A[DONT_GENERATE, EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:125:0x0136 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:130:0x0115 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:138:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:30:0x00fc A[Catch: all -> 0x0182, TRY_ENTER, TRY_LEAVE, TryCatch #0 {all -> 0x0182, blocks: (B:30:0x00fc, B:40:0x0140, B:42:0x0159, B:52:0x0194), top: B:107:0x00fa }] */
    /* JADX WARN: Code duplicated, block: B:40:0x0140 A[Catch: all -> 0x0182, TRY_ENTER, TryCatch #0 {all -> 0x0182, blocks: (B:30:0x00fc, B:40:0x0140, B:42:0x0159, B:52:0x0194), top: B:107:0x00fa }] */
    /* JADX WARN: Code duplicated, block: B:42:0x0159 A[Catch: all -> 0x0182, TRY_LEAVE, TryCatch #0 {all -> 0x0182, blocks: (B:30:0x00fc, B:40:0x0140, B:42:0x0159, B:52:0x0194), top: B:107:0x00fa }] */
    /* JADX WARN: Code duplicated, block: B:50:0x0190  */
    /* JADX WARN: Code duplicated, block: B:52:0x0194 A[Catch: all -> 0x0182, TRY_ENTER, TRY_LEAVE, TryCatch #0 {all -> 0x0182, blocks: (B:30:0x00fc, B:40:0x0140, B:42:0x0159, B:52:0x0194), top: B:107:0x00fa }] */
    /* JADX WARN: Code duplicated, block: B:56:0x01b2 A[Catch: all -> 0x024c, TRY_LEAVE, TryCatch #8 {all -> 0x024c, blocks: (B:54:0x01aa, B:56:0x01b2), top: B:121:0x01aa }] */
    /* JADX WARN: Code duplicated, block: B:60:0x01ce  */
    /* JADX WARN: Code duplicated, block: B:62:0x01d6  */
    /* JADX WARN: Code duplicated, block: B:67:0x01eb  */
    /* JADX WARN: Code duplicated, block: B:69:0x01ef A[Catch: all -> 0x01f6, TRY_ENTER, TRY_LEAVE, TryCatch #11 {all -> 0x01f6, blocks: (B:58:0x01c7, B:69:0x01ef), top: B:127:0x01b0 }] */
    /* JADX WARN: Code duplicated, block: B:73:0x01f8  */
    /* JADX WARN: Code duplicated, block: B:76:0x0208 A[Catch: all -> 0x0223, TryCatch #1 {all -> 0x0223, blocks: (B:76:0x0208, B:79:0x0227, B:74:0x0202), top: B:108:0x0202 }] */
    /* JADX WARN: Code duplicated, block: B:79:0x0227 A[Catch: all -> 0x0223, TRY_LEAVE, TryCatch #1 {all -> 0x0223, blocks: (B:76:0x0208, B:79:0x0227, B:74:0x0202), top: B:108:0x0202 }] */
    /* JADX WARN: Code duplicated, block: B:97:0x029a A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Instruction removed from duplicated block: B:30:0x00fc, please report this as an issue */
    /* JADX WARN: Instruction removed from duplicated block: B:40:0x0140, please report this as an issue */
    /* JADX WARN: Instruction removed from duplicated block: B:42:0x0159, please report this as an issue */
    /* JADX WARN: Instruction removed from duplicated block: B:76:0x0208, please report this as an issue */
    /* JADX WARN: Instruction removed from duplicated block: B:79:0x0227, please report this as an issue */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r16v0 */
    /* JADX WARN: Type inference failed for: r16v1 */
    /* JADX WARN: Type inference failed for: r16v14 */
    /* JADX WARN: Type inference failed for: r16v15 */
    /* JADX WARN: Type inference failed for: r16v16 */
    /* JADX WARN: Type inference failed for: r16v17 */
    /* JADX WARN: Type inference failed for: r16v2 */
    /* JADX WARN: Type inference failed for: r16v3 */
    /* JADX WARN: Type inference failed for: r16v4 */
    /* JADX WARN: Type inference failed for: r16v5 */
    /* JADX WARN: Type inference failed for: r5v6, types: [java.lang.String] */
    public static void s(Binder binder, String str, int i, boolean z) {
        int i2;
        String str2;
        ?? r16;
        String str3;
        int i3;
        String str4;
        char c;
        int i4;
        ?? r17;
        String strC;
        String str5;
        IContentProvider iContentProviderY;
        StringBuilder sb;
        Bundle bundle;
        IContentProvider iContentProvider;
        int i5;
        Bundle bundleCall;
        Object obj;
        Object obj2;
        String str6 = "failed send binder to user app ";
        String str7 = "failed to send binder to user app ";
        String str8 = "send binder to user app ";
        ?? r18 = 10;
        try {
            int i6 = Build.VERSION.SDK_INT;
            try {
                try {
                    try {
                        if (i6 < 31) {
                            str2 = "failed send binder to user app ";
                            obj2 = "failed to send binder to user app ";
                            str3 = "send binder to user app ";
                            i3 = 30;
                            str6 = ":";
                            str7 = " in user ";
                            str8 = "Service";
                            if (i6 >= 30) {
                                obj = obj2;
                                str4 = str;
                                i2 = i;
                                try {
                                    AbstractC0134dn.e.a().addPowerSaveTempWhitelistApp(str4, 30000L, i2, "shell");
                                    obj2 = obj2;
                                } catch (Throwable th) {
                                    th = th;
                                    r16 = obj2;
                                    StringBuilder sb2 = new StringBuilder();
                                    Locale locale = Locale.ENGLISH;
                                    sb2.append("Failed to add " + i2 + str6 + str4 + " to power save temp whitelist");
                                    c = '\n';
                                    sb2.append('\n');
                                    sb2.append(Log.getStackTraceString(th));
                                    String string = sb2.toString();
                                    i4 = 6;
                                    Log.println(6, str8, string);
                                    r17 = r16;
                                }
                            }
                            Locale locale2 = Locale.ENGLISH;
                            Log.println(2, str8, "Add " + i2 + str6 + str4 + " to power save temp whitelist for 30s");
                            i4 = 6;
                            c = '\n';
                            r17 = obj2;
                            strC = AbstractC0535qo.c(str4, ".shizuku");
                            iContentProviderY = X8.y(i2, strC, strC);
                            if (iContentProviderY == null) {
                                Locale locale3 = Locale.ENGLISH;
                                Log.println(i4, str8, "provider is null " + strC + " " + i2);
                                if (iContentProviderY == null) {
                                    return;
                                }
                                try {
                                    X8.S(strC);
                                    return;
                                } catch (Throwable th2) {
                                    th = th2;
                                    sb = new StringBuilder("removeContentProviderExternal\n");
                                }
                            } else {
                                try {
                                    if (iContentProviderY.asBinder().pingBinder()) {
                                        if (!z) {
                                            Log.println(i4, str8, "retry works");
                                        }
                                        bundle = new Bundle();
                                        bundle.putParcelable("moe.shizuku.privileged.api.intent.extra.BINDER", new BinderContainer(binder));
                                        try {
                                            i5 = Build.VERSION.SDK_INT;
                                            try {
                                                try {
                                                    if (i5 >= 31) {
                                                        str5 = strC;
                                                        if (i5 >= i3) {
                                                            try {
                                                                bundleCall = iContentProviderY.call((String) null, (String) null, str5, "sendBinder", (String) null, bundle);
                                                                str5 = str5;
                                                            } catch (Throwable th3) {
                                                                th = th3;
                                                                str5 = str5;
                                                                iContentProvider = iContentProviderY;
                                                                iContentProviderY = iContentProvider;
                                                                try {
                                                                    StringBuilder sb3 = new StringBuilder();
                                                                    Locale locale4 = Locale.ENGLISH;
                                                                    sb3.append(str2 + str4 + str7 + i2);
                                                                    sb3.append(c);
                                                                    sb3.append(Log.getStackTraceString(th));
                                                                    Log.println(i4, str8, sb3.toString());
                                                                    if (iContentProviderY == null) {
                                                                        return;
                                                                    }
                                                                    try {
                                                                        return;
                                                                    } catch (Throwable th4) {
                                                                        th = th4;
                                                                        sb = new StringBuilder("removeContentProviderExternal\n");
                                                                    }
                                                                } finally {
                                                                    if (iContentProviderY != null) {
                                                                        try {
                                                                            X8.S(str5);
                                                                        } catch (Throwable th5) {
                                                                            StringBuilder sb4 = new StringBuilder("removeContentProviderExternal\n");
                                                                            Locale locale5 = Locale.ENGLISH;
                                                                            sb4.append(Log.getStackTraceString(th5));
                                                                            Log.println(5, str8, sb4.toString());
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else if (i5 >= 29) {
                                                            bundleCall = iContentProviderY.call((String) null, str5, "sendBinder", (String) null, bundle);
                                                        } else {
                                                            iContentProvider = iContentProviderY;
                                                            try {
                                                                bundleCall = iContentProvider.call((String) null, "sendBinder", (String) null, bundle);
                                                            } catch (Throwable th6) {
                                                                th = th6;
                                                                iContentProviderY = iContentProvider;
                                                                StringBuilder sb5 = new StringBuilder();
                                                                Locale locale6 = Locale.ENGLISH;
                                                                sb5.append(str2 + str4 + str7 + i2);
                                                                sb5.append(c);
                                                                sb5.append(Log.getStackTraceString(th));
                                                                Log.println(i4, str8, sb5.toString());
                                                                if (iContentProviderY == null) {
                                                                    return;
                                                                } else {
                                                                    return;
                                                                }
                                                            }
                                                        }
                                                        if (bundleCall != null) {
                                                            Locale locale7 = Locale.ENGLISH;
                                                            Log.println(4, str8, str3 + str4 + str7 + i2);
                                                        } else {
                                                            Locale locale8 = Locale.ENGLISH;
                                                            Log.println(5, str8, ((String) r17) + str4 + str7 + i2);
                                                        }
                                                        X8.S(str5);
                                                        return;
                                                    }
                                                    AbstractC0604t1.l();
                                                    str5 = strC;
                                                    bundleCall = iContentProviderY.call(AbstractC0604t1.d(Bj.a).setPackageName(null).build(), str5, "sendBinder", (String) null, bundle);
                                                    X8.S(str5);
                                                    return;
                                                } catch (Throwable th7) {
                                                    th = th7;
                                                    sb = new StringBuilder("removeContentProviderExternal\n");
                                                }
                                                if (bundleCall != null) {
                                                    Locale locale9 = Locale.ENGLISH;
                                                    Log.println(4, str8, str3 + str4 + str7 + i2);
                                                } else {
                                                    Locale locale10 = Locale.ENGLISH;
                                                    Log.println(5, str8, ((String) r17) + str4 + str7 + i2);
                                                }
                                            } catch (Throwable th8) {
                                                th = th8;
                                            }
                                        } catch (Throwable th9) {
                                            th = th9;
                                            str5 = strC;
                                        }
                                    } else {
                                        Locale locale11 = Locale.ENGLISH;
                                        Log.println(i4, str8, "provider is dead " + strC + " " + i2);
                                        if (z) {
                                            X8.v(i2, str4);
                                            Log.println(i4, str8, "kill " + str4 + str7 + i2 + " and try again");
                                            Thread.sleep(1000L);
                                            s(binder, str4, i2, false);
                                        }
                                        try {
                                            X8.S(strC);
                                            return;
                                        } catch (Throwable th10) {
                                            th = th10;
                                            sb = new StringBuilder("removeContentProviderExternal\n");
                                        }
                                    }
                                } catch (Throwable th11) {
                                    th = th11;
                                    str5 = strC;
                                }
                            }
                            Locale locale12 = Locale.ENGLISH;
                            sb.append(Log.getStackTraceString(th));
                            String string2 = sb.toString();
                        }
                        try {
                            str2 = "failed send binder to user app ";
                            obj = "failed to send binder to user app ";
                            str3 = "send binder to user app ";
                            i3 = 30;
                            str6 = ":";
                            str7 = " in user ";
                            str8 = "Service";
                            AbstractC0134dn.e.a().addPowerSaveTempWhitelistApp(str, 30000L, i, 316, "shell");
                        } catch (Throwable th12) {
                            th = th12;
                            str2 = "failed send binder to user app ";
                            r18 = "failed to send binder to user app ";
                            str3 = "send binder to user app ";
                            i3 = 30;
                            str6 = ":";
                            str7 = " in user ";
                            str8 = "Service";
                            str4 = str;
                            i2 = i;
                            r16 = r18;
                            StringBuilder sb6 = new StringBuilder();
                            Locale locale13 = Locale.ENGLISH;
                            sb6.append("Failed to add " + i2 + str6 + str4 + " to power save temp whitelist");
                            c = '\n';
                            sb6.append('\n');
                            sb6.append(Log.getStackTraceString(th));
                            String string3 = sb6.toString();
                            i4 = 6;
                            Log.println(6, str8, string3);
                            r17 = r16;
                        }
                        obj = obj2;
                        str4 = str;
                        i2 = i;
                        obj2 = obj;
                        Locale locale14 = Locale.ENGLISH;
                        Log.println(2, str8, "Add " + i2 + str6 + str4 + " to power save temp whitelist for 30s");
                        i4 = 6;
                        c = '\n';
                        r17 = obj2;
                    } catch (Throwable th13) {
                        th = th13;
                    }
                    if (iContentProviderY == null) {
                        Locale locale15 = Locale.ENGLISH;
                        Log.println(i4, str8, "provider is null " + strC + " " + i2);
                        if (iContentProviderY == null) {
                            X8.S(strC);
                            return;
                        }
                        return;
                    }
                    if (iContentProviderY.asBinder().pingBinder()) {
                        Locale locale16 = Locale.ENGLISH;
                        Log.println(i4, str8, "provider is dead " + strC + " " + i2);
                        if (z) {
                            X8.v(i2, str4);
                            Log.println(i4, str8, "kill " + str4 + str7 + i2 + " and try again");
                            Thread.sleep(1000L);
                            s(binder, str4, i2, false);
                        }
                        X8.S(strC);
                        return;
                    }
                    if (!z) {
                        Log.println(i4, str8, "retry works");
                    }
                    bundle = new Bundle();
                    bundle.putParcelable("moe.shizuku.privileged.api.intent.extra.BINDER", new BinderContainer(binder));
                    i5 = Build.VERSION.SDK_INT;
                    if (i5 >= 31) {
                        str5 = strC;
                        if (i5 >= i3) {
                            bundleCall = iContentProviderY.call((String) null, (String) null, str5, "sendBinder", (String) null, bundle);
                            str5 = str5;
                        } else if (i5 >= 29) {
                            bundleCall = iContentProviderY.call((String) null, str5, "sendBinder", (String) null, bundle);
                        } else {
                            iContentProvider = iContentProviderY;
                            bundleCall = iContentProvider.call((String) null, "sendBinder", (String) null, bundle);
                        }
                        if (bundleCall != null) {
                            Locale locale17 = Locale.ENGLISH;
                            Log.println(4, str8, str3 + str4 + str7 + i2);
                        } else {
                            Locale locale18 = Locale.ENGLISH;
                            Log.println(5, str8, ((String) r17) + str4 + str7 + i2);
                        }
                        X8.S(str5);
                        return;
                    }
                    AbstractC0604t1.l();
                    str5 = strC;
                    bundleCall = iContentProviderY.call(AbstractC0604t1.d(Bj.a).setPackageName(null).build(), str5, "sendBinder", (String) null, bundle);
                    if (bundleCall != null) {
                        Locale locale19 = Locale.ENGLISH;
                        Log.println(4, str8, str3 + str4 + str7 + i2);
                    } else {
                        Locale locale110 = Locale.ENGLISH;
                        Log.println(5, str8, ((String) r17) + str4 + str7 + i2);
                    }
                    X8.S(str5);
                    return;
                } catch (Throwable th14) {
                    th = th14;
                    str5 = strC;
                }
                iContentProviderY = X8.y(i2, strC, strC);
            } catch (Throwable th15) {
                th = th15;
                str5 = strC;
                iContentProviderY = null;
            }
        } catch (Throwable th16) {
            th = th16;
            i2 = i;
            str2 = "failed send binder to user app ";
            r16 = "failed to send binder to user app ";
            str3 = "send binder to user app ";
            i3 = 30;
            str6 = ":";
            str7 = " in user ";
            str8 = "Service";
            str4 = str;
        }
        strC = AbstractC0535qo.c(str4, ".shizuku");
        Locale locale111 = Locale.ENGLISH;
        sb.append(Log.getStackTraceString(th));
        String string4 = sb.toString();
    }

    public static void t(String str) {
        while (ServiceManager.getService(str) == null) {
            try {
                Log.println(4, "Service", "service " + str + " is not started, wait 1s.");
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                Log.println(5, "Service", e.getMessage() + '\n' + Log.getStackTraceString(e));
            }
        }
    }

    @Override // rikka.shizuku.InterfaceC0033af
    public final void a(IBinder iBinder, Bundle bundle) {
        p("func");
        Xn xn = this.a;
        xn.getClass();
        String string = bundle.getString("shizuku:user-service-arg-token");
        synchronized (xn) {
            xn.v(iBinder, string);
        }
    }

    @Override // rikka.shizuku.InterfaceC0033af
    public final void b() {
        p("exit");
        Log.println(4, "Service", "exit");
        System.exit(0);
    }

    @Override // rikka.shizuku.InterfaceC0033af
    public final int c(int i, int i2) {
        String[] strArr;
        if (Binder.getCallingUid() % 100000 != this.i) {
            Log.println(5, "Service", "updateFlagsForUid is allowed to be called only from the manager");
            return 0;
        }
        Pn pnD0 = this.h.d0(i);
        if (pnD0 != null) {
            return pnD0.b & i2;
        }
        if ((i2 & 6) == 0) {
            return 0;
        }
        int i3 = i / 100000;
        ArrayList arrayListS = AbstractC0599sr.s(i);
        int size = arrayListS.size();
        int i4 = 0;
        while (i4 < size) {
            Object obj = arrayListS.get(i4);
            i4++;
            PackageInfo packageInfoR = AbstractC0599sr.r((String) obj, 4096L, i3);
            if (packageInfoR != null && (strArr = packageInfoR.requestedPermissions) != null && AbstractC0484p4.d0(strArr, "moe.shizuku.manager.permission.API_V23")) {
                try {
                    if (X8.n(i, "moe.shizuku.manager.permission.API_V23") == 0) {
                        return 2;
                    }
                } catch (Throwable unused) {
                    Log.println(5, "Service", "getFlagsForUid");
                }
            }
        }
        return 0;
    }

    @Override // rikka.shizuku.InterfaceC0033af
    public final void d(int i, int i2, int i3) {
        String[] strArr;
        int i4 = 100000;
        if (Binder.getCallingUid() % 100000 != this.i) {
            Log.println(5, "Service", "updateFlagsForUid is allowed to be called only from the manager");
            return;
        }
        int i5 = i / 100000;
        if ((i2 & 6) != 0) {
            boolean z = true;
            int i6 = 0;
            boolean z2 = (i3 & 2) != 0;
            ArrayList arrayListZ = this.g.z(i);
            int size = arrayListZ.size();
            int i7 = 0;
            while (i7 < size) {
                Object obj = arrayListZ.get(i7);
                i7++;
                C0455o6 c0455o6 = (C0455o6) obj;
                if (z2) {
                    c0455o6.f = z;
                } else {
                    c0455o6.f = false;
                    int i8 = c0455o6.a / i4;
                    String str = c0455o6.d;
                    X8.v(i8, str);
                    Map map = (Map) this.a.c;
                    List<Xr> list = (List) map.get(str);
                    if (list != null) {
                        for (Xr xr : list) {
                            xr.b();
                            Locale locale = Locale.ENGLISH;
                            Log.println(4, "UserServiceManager", "Remove user service " + xr.d + " for package " + str);
                        }
                        map.remove(str);
                    }
                }
                i4 = 100000;
                z = true;
            }
            ArrayList arrayListS = AbstractC0599sr.s(i);
            int size2 = arrayListS.size();
            while (i6 < size2) {
                Object obj2 = arrayListS.get(i6);
                i6++;
                String str2 = (String) obj2;
                PackageInfo packageInfoR = AbstractC0599sr.r(str2, 4096L, i5);
                if (packageInfoR != null && (strArr = packageInfoR.requestedPermissions) != null && AbstractC0484p4.d0(strArr, "moe.shizuku.manager.permission.API_V23")) {
                    if (z2) {
                        X8.G(i5, str2, "moe.shizuku.manager.permission.API_V23");
                    } else {
                        X8.V(i5, str2);
                    }
                }
            }
        }
        Rn rn = this.h;
        synchronized (rn) {
            rn.f0(i, null, i2, i3);
        }
    }

    @Override // rikka.shizuku.InterfaceC0033af
    public final void m(int i, int i2, int i3, Bundle bundle) {
        String[] strArr;
        if (Binder.getCallingUid() % 100000 != this.i) {
            Log.println(5, "Service", "dispatchPermissionConfirmationResult called not from the manager package");
            return;
        }
        if (bundle == null) {
            return;
        }
        boolean z = bundle.getBoolean("shizuku:request-permission-reply-allowed");
        boolean z2 = bundle.getBoolean("shizuku:request-permission-reply-is-onetime");
        String string = Boolean.toString(z);
        String string2 = Boolean.toString(z2);
        Locale locale = Locale.ENGLISH;
        Log.println(4, "Service", "dispatchPermissionConfirmationResult: uid=" + i + ", pid=" + i2 + ", requestCode=" + i3 + ", allowed=" + string + ", onetime=" + string2);
        ArrayList arrayListZ = this.g.z(i);
        ArrayList arrayList = new ArrayList();
        int i4 = 0;
        if (arrayListZ.isEmpty()) {
            Log.println(5, "Service", "dispatchPermissionConfirmationResult: no client for uid " + i + " was found");
        } else {
            int size = arrayListZ.size();
            int i5 = 0;
            while (i5 < size) {
                Object obj = arrayListZ.get(i5);
                i5++;
                C0455o6 c0455o6 = (C0455o6) obj;
                arrayList.add(c0455o6.d);
                c0455o6.f = z;
                if (c0455o6.b == i2) {
                    c0455o6.a(i3, z);
                }
            }
        }
        if (!z2) {
            int i6 = z ? 2 : 4;
            Rn rn = this.h;
            synchronized (rn) {
                rn.f0(i, arrayList, 6, i6);
            }
        }
        if (z2 || !z) {
            return;
        }
        int i7 = i / 100000;
        ArrayList arrayListS = AbstractC0599sr.s(i);
        int size2 = arrayListS.size();
        while (i4 < size2) {
            Object obj2 = arrayListS.get(i4);
            i4++;
            String str = (String) obj2;
            PackageInfo packageInfoR = AbstractC0599sr.r(str, 4096L, i7);
            if (packageInfoR != null && (strArr = packageInfoR.requestedPermissions) != null && AbstractC0484p4.d0(strArr, "moe.shizuku.manager.permission.API_V23")) {
                if (z) {
                    X8.G(i7, str, "moe.shizuku.manager.permission.API_V23");
                } else {
                    X8.V(i7, str);
                }
            }
        }
    }

    @Override // rikka.shizuku.AbstractBinderC0041an, android.os.Binder
    public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        ApplicationInfo applicationInfo;
        int i3;
        String[] strArr;
        if (i != 10001) {
            return super.onTransact(i, parcel, parcel2, i2);
        }
        parcel.enforceInterface("moe.shizuku.server.IShizukuService");
        int i4 = parcel.readInt();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        if (i4 == -1) {
            arrayList2.addAll(AbstractC0688vn.s());
        } else {
            arrayList2.add(Integer.valueOf(i4));
        }
        int size = arrayList2.size();
        int i5 = 0;
        while (i5 < size) {
            Object obj = arrayList2.get(i5);
            i5++;
            for (PackageInfo packageInfo : AbstractC0599sr.q(((Integer) obj).intValue(), 4224L)) {
                if (!"moe.shizuku.privileged.api".equals(packageInfo.packageName) && (applicationInfo = packageInfo.applicationInfo) != null) {
                    Pn pnD0 = this.h.d0(applicationInfo.uid);
                    if (pnD0 != null) {
                        List<String> list = pnD0.c;
                        if (list == null || list.contains(packageInfo.packageName)) {
                            i3 = pnD0.b & 6;
                        }
                    } else {
                        i3 = 0;
                    }
                    if (i3 != 0) {
                        arrayList.add(packageInfo);
                    } else {
                        Bundle bundle = packageInfo.applicationInfo.metaData;
                        if (bundle != null && bundle.getBoolean("moe.shizuku.client.V3_SUPPORT", false) && (strArr = packageInfo.requestedPermissions) != null && AbstractC0484p4.d0(strArr, "moe.shizuku.manager.permission.API_V23")) {
                            arrayList.add(packageInfo);
                        }
                    }
                }
            }
        }
        Kj kj = new Kj(arrayList);
        parcel2.writeNoException();
        kj.writeToParcel(parcel2, 1);
        return true;
    }

    public final void r(Ye ye, Bundle bundle) {
        String string;
        Ye ye2;
        C0455o6 c0455o6A;
        if (ye == null || bundle == null || (string = bundle.getString("shizuku:attach-package-name")) == null) {
            return;
        }
        int i = bundle.getInt("shizuku:attach-api-version", -1);
        int callingPid = Binder.getCallingPid();
        int callingUid = Binder.getCallingUid();
        if (!AbstractC0599sr.s(callingUid).contains(string)) {
            Log.println(5, "Service", "Request package " + string + "does not belong to uid " + callingUid);
            throw new SecurityException("Request package " + string + "does not belong to uid " + callingUid);
        }
        boolean zEquals = "moe.shizuku.privileged.api".equals(string);
        if (this.g.y(callingUid, callingPid) == null) {
            synchronized (this) {
                ye2 = ye;
                c0455o6A = this.g.a(callingUid, callingPid, ye2, string, i);
            }
            if (c0455o6A == null) {
                Log.println(5, "Service", "Add client failed");
                return;
            }
        } else {
            ye2 = ye;
            c0455o6A = null;
        }
        int i2 = i == -1 ? 12 : 13;
        Bundle bundle2 = new Bundle();
        bundle2.putInt("shizuku:attach-reply-uid", Cj.a);
        bundle2.putInt("shizuku:attach-reply-version", i2);
        bundle2.putString("shizuku:attach-reply-secontext", Cj.b);
        bundle2.putInt("shizuku:attach-reply-patch-version", 5);
        if (zEquals) {
            try {
                X8.G(callingUid / 100000, "moe.shizuku.privileged.api", "android.permission.WRITE_SECURE_SETTINGS");
            } catch (RemoteException e) {
                StringBuilder sb = new StringBuilder("grant WRITE_SECURE_SETTINGS\n");
                Locale locale = Locale.ENGLISH;
                sb.append(Log.getStackTraceString(e));
                Log.println(5, "Service", sb.toString());
            }
        } else {
            bundle2.putBoolean("shizuku:attach-reply-permission-granted", c0455o6A.f);
            bundle2.putBoolean("shizuku:attach-reply-should-show-request-permission-rationale", false);
        }
        try {
            ye2.l(bundle2);
        } catch (Throwable th) {
            StringBuilder sb2 = new StringBuilder("attachApplication\n");
            Locale locale2 = Locale.ENGLISH;
            sb2.append(Log.getStackTraceString(th));
            Log.println(5, "Service", sb2.toString());
        }
    }
}
