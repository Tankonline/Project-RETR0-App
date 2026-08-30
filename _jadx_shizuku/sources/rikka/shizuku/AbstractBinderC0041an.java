package rikka.shizuku;

import android.app.IApplicationThread;
import android.app.ProfilerInfo;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.UserInfo;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.SELinux;
import android.os.SystemProperties;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import moe.shizuku.starter.ServiceStarter;
import rikka.rish.RishHost;
import rikka.shizuku.server.ShizukuService;

/* JADX INFO: renamed from: rikka.shizuku.an, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractBinderC0041an extends Binder implements InterfaceC0033af {
    public static final /* synthetic */ int e = 0;
    public static final C0589sh f = new C0589sh(0, "Service");
    public final Xn a;
    public final Rn b;
    public final R2 c;
    public final Zm d;

    public AbstractBinderC0041an() {
        attachInterface(this, "moe.shizuku.server.IShizukuService");
        AbstractC0744xh.n = "moe.shizuku.server.IShizukuService";
        AbstractC0744xh.o = 30000;
        AbstractC0744xh.I();
        this.a = new Xn(0);
        this.b = new Rn();
        ShizukuService shizukuService = (ShizukuService) this;
        this.c = new R2(shizukuService.b);
        this.d = new Zm(shizukuService);
    }

    @Override // rikka.shizuku.InterfaceC0033af
    public final int e(String str) {
        o("checkPermission");
        return X8.n(android.system.Os.getuid(), str);
    }

    @Override // rikka.shizuku.InterfaceC0033af
    public final int f() {
        o("getUid");
        return android.system.Os.getuid();
    }

    @Override // rikka.shizuku.InterfaceC0033af
    public final boolean h() {
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        if (callingUid == Bj.a || callingPid == Bj.b) {
            return true;
        }
        return this.c.V(callingUid, callingPid).f;
    }

    @Override // rikka.shizuku.InterfaceC0033af
    public final void i(int i) {
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        int i2 = callingUid / 100000;
        if (callingUid == Bj.a || callingPid == Bj.b) {
            return;
        }
        C0455o6 c0455o6V = this.c.V(callingUid, callingPid);
        boolean zEquals = true;
        if (c0455o6V.f) {
            c0455o6V.a(i, true);
            return;
        }
        Pn pnD0 = this.b.d0(callingUid);
        if (pnD0 != null && (pnD0.b & 4) != 0) {
            c0455o6V.a(i, false);
            return;
        }
        ApplicationInfo applicationInfoN = AbstractC0599sr.n(i2, c0455o6V.d);
        if (applicationInfoN == null) {
            return;
        }
        PackageInfo packageInfoR = AbstractC0599sr.r("moe.shizuku.privileged.api", 0L, i2);
        UserInfo userInfo = AbstractC0134dn.b.a().getUserInfo(i2);
        if (AbstractC0485p5.a >= 30) {
            zEquals = "android.os.usertype.profile.MANAGED".equals(userInfo.userType);
        } else if ((userInfo.flags & 32) == 0) {
            zEquals = false;
        }
        if (packageInfoR != null || zEquals) {
            try {
                AbstractC0134dn.a.a().startActivityAsUser((IApplicationThread) null, android.system.Os.getuid() == 2000 ? "com.android.shell" : null, new Intent("moe.shizuku.privileged.api.intent.action.REQUEST_PERMISSION").setPackage("moe.shizuku.privileged.api").addFlags(268959744).putExtra("uid", callingUid).putExtra("pid", callingPid).putExtra("requestCode", i).putExtra("applicationInfo", applicationInfoN), (String) null, (IBinder) null, (String) null, 0, 0, (ProfilerInfo) null, (Bundle) null, zEquals ? 0 : i2);
                return;
            } catch (Throwable unused) {
                return;
            }
        }
        Locale locale = Locale.ENGLISH;
        Log.println(5, "Service", "Manager not found in non work profile user " + i2 + ". Revoke permission");
        c0455o6V.a(i, false);
    }

    @Override // rikka.shizuku.InterfaceC0033af
    public final String j() {
        o("getSELinuxContext");
        try {
            return SELinux.getContext();
        } catch (Throwable th) {
            throw new IllegalStateException(th.getMessage());
        }
    }

    @Override // rikka.shizuku.InterfaceC0033af
    public final int k() {
        o("getVersion");
        return 13;
    }

    @Override // rikka.shizuku.InterfaceC0033af
    public final boolean n() {
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        if (callingUid == Bj.a || callingPid == Bj.b) {
            return true;
        }
        this.c.V(callingUid, callingPid);
        Pn pnD0 = this.b.d0(callingUid);
        return (pnD0 == null || (pnD0.b & 4) == 0) ? false : true;
    }

    /* JADX WARN: Code duplicated, block: B:15:0x0058  */
    public final void o(String str) {
        int iCheckPermission;
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        if (callingUid == Bj.a) {
            return;
        }
        C0455o6 c0455o6Y = this.c.y(callingUid, callingPid);
        boolean z = true;
        if (callingUid % 100000 != ((ShizukuService) this).i) {
            if (c0455o6Y == null) {
                try {
                    iCheckPermission = AbstractC0134dn.a.a().checkPermission("moe.shizuku.manager.permission.API_V23", Binder.getCallingPid(), Binder.getCallingUid());
                } catch (Throwable th) {
                    StringBuilder sb = new StringBuilder("checkCallingPermission\n");
                    Locale locale = Locale.ENGLISH;
                    sb.append(Log.getStackTraceString(th));
                    Log.println(5, "Service", sb.toString());
                    iCheckPermission = -1;
                }
                if (iCheckPermission != 0) {
                    z = false;
                }
            } else {
                z = false;
            }
        }
        if (z) {
            return;
        }
        C0589sh c0589sh = f;
        if (c0455o6Y == null) {
            StringBuilder sbE = AbstractC0535qo.e("Permission Denial: ", str, " from pid=");
            sbE.append(Binder.getCallingPid());
            sbE.append(" is not an attached client");
            String string = sbE.toString();
            c0589sh.b(5, string);
            throw new SecurityException(string);
        }
        if (c0455o6Y.f) {
            return;
        }
        StringBuilder sbE2 = AbstractC0535qo.e("Permission Denial: ", str, " from pid=");
        sbE2.append(Binder.getCallingPid());
        sbE2.append(" requires permission");
        String string2 = sbE2.toString();
        c0589sh.b(5, string2);
        throw new SecurityException(string2);
    }

    /* JADX WARN: Code duplicated, block: B:78:0x01a0  */
    /* JADX WARN: Code duplicated, block: B:87:? A[RETURN, SYNTHETIC] */
    @Override // android.os.Binder
    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4 = true;
        if (i == 1) {
            parcel.enforceInterface("moe.shizuku.server.IShizukuService");
            o("transactRemote");
            IBinder strongBinder = parcel.readStrongBinder();
            int i3 = parcel.readInt();
            C0455o6 c0455o6Y = this.c.y(Binder.getCallingUid(), Binder.getCallingPid());
            int i4 = (c0455o6Y == null || c0455o6Y.e < 13) ? i2 : parcel.readInt();
            Binder.getCallingUid();
            strongBinder.getInterfaceDescriptor();
            Parcel parcelObtain = Parcel.obtain();
            try {
                parcelObtain.appendFrom(parcel, parcel.dataPosition(), parcel.dataAvail());
                try {
                    long jClearCallingIdentity = Binder.clearCallingIdentity();
                    strongBinder.transact(i3, parcelObtain, parcel2, i4);
                    Binder.restoreCallingIdentity(jClearCallingIdentity);
                    return true;
                } finally {
                    parcelObtain.recycle();
                }
            } catch (Throwable th) {
                f.c(th, "appendFrom", new Object[0]);
                return true;
            }
        }
        Ye ye = null;
        if (i == 14) {
            parcel.enforceInterface("moe.shizuku.server.IShizukuService");
            IBinder strongBinder2 = parcel.readStrongBinder();
            String string = parcel.readString();
            Bundle bundle = new Bundle();
            bundle.putString("shizuku:attach-package-name", string);
            bundle.putInt("shizuku:attach-api-version", -1);
            if (strongBinder2 != null) {
                IInterface iInterfaceQueryLocalInterface = strongBinder2.queryLocalInterface("moe.shizuku.server.IShizukuApplication");
                if (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof Ye)) {
                    Xe xe = new Xe();
                    xe.a = strongBinder2;
                    ye = xe;
                } else {
                    ye = (Ye) iInterfaceQueryLocalInterface;
                }
            }
            ((ShizukuService) this).r(ye, bundle);
            parcel2.writeNoException();
            return true;
        }
        int i5 = AbstractC0744xh.o;
        HashMap map = Zm.b;
        Zm zm = this.d;
        if (i == i5) {
            zm.a.o("createHost");
            if (parcel2 == null || (i2 & 1) != 0) {
                z = true;
            } else {
                parcel.enforceInterface(AbstractC0744xh.n);
                byte b = parcel.readByte();
                ParcelFileDescriptor fileDescriptor = parcel.readFileDescriptor();
                ParcelFileDescriptor fileDescriptor2 = parcel.readFileDescriptor();
                ParcelFileDescriptor fileDescriptor3 = (b & 4) == 0 ? parcel.readFileDescriptor() : null;
                String[] strArrCreateStringArray = parcel.createStringArray();
                String[] strArrCreateStringArray2 = parcel.createStringArray();
                String string2 = parcel.readString();
                int callingPid = Binder.getCallingPid();
                int length = strArrCreateStringArray2.length;
                int i6 = 0;
                while (true) {
                    if (i6 >= length) {
                        z = z4;
                        z3 = Zm.c;
                        break;
                    }
                    String str = strArrCreateStringArray2[i6];
                    z = z4;
                    if ("RISH_PRESERVE_ENV=1".equals(str)) {
                        z3 = z;
                        break;
                    }
                    if ("RISH_PRESERVE_ENV=0".equals(str)) {
                        z3 = false;
                        break;
                    }
                    i6++;
                    z4 = z;
                }
                RishHost rishHost = new RishHost(strArrCreateStringArray, !z3 ? null : strArrCreateStringArray2, string2, b, fileDescriptor, fileDescriptor2, fileDescriptor3);
                rishHost.d();
                map.put(Integer.valueOf(callingPid), rishHost);
                parcel2.writeNoException();
            }
        } else {
            z = true;
            if (i != i5 + 1) {
                if (i == i5 + 2) {
                    zm.a.o("getExitCode");
                    parcel.enforceInterface(AbstractC0744xh.n);
                    RishHost rishHost2 = (RishHost) map.get(Integer.valueOf(Binder.getCallingPid()));
                    int i7 = rishHost2 != null ? rishHost2.j : -1;
                    if (parcel2 != null) {
                        parcel2.writeNoException();
                        parcel2.writeInt(i7);
                    }
                } else {
                    zm.getClass();
                    z2 = false;
                }
                if (z2) {
                    return z;
                }
                return q(i, parcel, parcel2, i2);
            }
            zm.a.o("setWindowSize");
            parcel.enforceInterface(AbstractC0744xh.n);
            long j = parcel.readLong();
            RishHost rishHost3 = (RishHost) map.get(Integer.valueOf(Binder.getCallingPid()));
            if (rishHost3 != null) {
                rishHost3.c(j);
            }
            if (parcel2 != null) {
                parcel2.writeNoException();
            }
        }
        z2 = z;
        if (z2) {
            return z;
        }
        return q(i, parcel, parcel2, i2);
    }

    public final void p(String str) {
        int callingUid = Binder.getCallingUid();
        if (Binder.getCallingPid() == android.system.Os.getpid() || callingUid % 100000 == ((ShizukuService) this).i) {
            return;
        }
        StringBuilder sbE = AbstractC0535qo.e("Permission Denial: ", str, " from pid=");
        sbE.append(Binder.getCallingPid());
        sbE.append(" is not manager ");
        String string = sbE.toString();
        f.b(5, string);
        throw new SecurityException(string);
    }

    /* JADX WARN: Code duplicated, block: B:120:0x0277 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:121:0x0279 A[Catch: all -> 0x0272, DONT_GENERATE, TryCatch #1 {all -> 0x0272, blocks: (B:104:0x024b, B:107:0x025a, B:109:0x0263, B:111:0x0269, B:113:0x026e, B:114:0x0270, B:118:0x0274, B:121:0x0279, B:123:0x027c, B:125:0x0280, B:127:0x028e, B:129:0x0294, B:141:0x02bb, B:130:0x0298, B:132:0x029c, B:136:0x02af, B:137:0x02b6, B:145:0x02c4), top: B:186:0x024b }] */
    /* JADX WARN: Code duplicated, block: B:123:0x027c A[Catch: all -> 0x0272, DONT_GENERATE, TryCatch #1 {all -> 0x0272, blocks: (B:104:0x024b, B:107:0x025a, B:109:0x0263, B:111:0x0269, B:113:0x026e, B:114:0x0270, B:118:0x0274, B:121:0x0279, B:123:0x027c, B:125:0x0280, B:127:0x028e, B:129:0x0294, B:141:0x02bb, B:130:0x0298, B:132:0x029c, B:136:0x02af, B:137:0x02b6, B:145:0x02c4), top: B:186:0x024b }] */
    public final boolean q(int i, Parcel parcel, Parcel parcel2, int i2) throws Throwable {
        int i3;
        if (i >= 1 && i <= 16777215) {
            parcel.enforceInterface("moe.shizuku.server.IShizukuService");
        }
        if (i == 1598968902) {
            parcel2.writeString("moe.shizuku.server.IShizukuService");
            return true;
        }
        if (i == 3) {
            k();
            parcel2.writeNoException();
            parcel2.writeInt(13);
            return true;
        }
        if (i == 4) {
            int iF = f();
            parcel2.writeNoException();
            parcel2.writeInt(iF);
            return true;
        }
        if (i == 5) {
            int iE = e(parcel.readString());
            parcel2.writeNoException();
            parcel2.writeInt(iE);
            return true;
        }
        Ye ye = null;
        C0064bf c0064bf = null;
        C0064bf c0064bf2 = null;
        int i4 = 0;
        switch (i) {
            case 8:
                String[] strArrCreateStringArray = parcel.createStringArray();
                String[] strArrCreateStringArray2 = parcel.createStringArray();
                String string = parcel.readString();
                o("newProcess");
                Binder.getCallingUid();
                Arrays.toString(strArrCreateStringArray);
                Arrays.toString(strArrCreateStringArray2);
                try {
                    Process processExec = Runtime.getRuntime().exec(strArrCreateStringArray, strArrCreateStringArray2, string != null ? new File(string) : null);
                    C0455o6 c0455o6Y = this.c.y(Binder.getCallingUid(), Binder.getCallingPid());
                    Fl fl = new Fl(processExec, c0455o6Y != null ? c0455o6Y.c.asBinder() : null);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(fl);
                    return true;
                } catch (IOException e2) {
                    throw new IllegalStateException(e2.getMessage());
                }
            case 9:
                String strJ = j();
                parcel2.writeNoException();
                parcel2.writeString(strJ);
                return true;
            case 10:
                String string2 = parcel.readString();
                String string3 = parcel.readString();
                o("getSystemProperty");
                try {
                    String str = SystemProperties.get(string2, string3);
                    parcel2.writeNoException();
                    parcel2.writeString(str);
                    return true;
                } catch (Throwable th) {
                    throw new IllegalStateException(th.getMessage());
                }
            case 11:
                String string4 = parcel.readString();
                String string5 = parcel.readString();
                o("setSystemProperty");
                try {
                    SystemProperties.set(string4, string5);
                    parcel2.writeNoException();
                    return true;
                } catch (Throwable th2) {
                    throw new IllegalStateException(th2.getMessage());
                }
            case 12:
                IBinder strongBinder = parcel.readStrongBinder();
                if (strongBinder != null) {
                    IInterface iInterfaceQueryLocalInterface = strongBinder.queryLocalInterface("moe.shizuku.server.IShizukuServiceConnection");
                    if (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof C0064bf)) {
                        c0064bf2 = new C0064bf();
                        c0064bf2.a = strongBinder;
                    } else {
                        c0064bf2 = (C0064bf) iInterfaceQueryLocalInterface;
                    }
                }
                Bundle bundle = (Bundle) X8.c(parcel, Bundle.CREATOR);
                o("addUserService");
                C0455o6 c0455o6Y2 = this.c.y(Binder.getCallingUid(), Binder.getCallingPid());
                int i5 = c0455o6Y2 == null ? 13 : c0455o6Y2.e;
                final Xn xn = this.a;
                xn.getClass();
                final int callingUid = Binder.getCallingUid();
                ComponentName componentName = (ComponentName) bundle.getParcelable("shizuku:user-service-arg-component");
                final String packageName = componentName.getPackageName();
                PackageInfo packageInfoF = Xn.f(packageName, callingUid % 100000, callingUid / 100000);
                final String className = componentName.getClassName();
                String str2 = packageInfoF.applicationInfo.sourceDir;
                int i6 = bundle.getInt("shizuku:user-service-arg-version-code", 1);
                String string6 = bundle.getString("shizuku:user-service-arg-tag");
                final String string7 = bundle.getString("shizuku:user-service-arg-process-name");
                final boolean z = bundle.getBoolean("shizuku:user-service-arg-debuggable", false);
                boolean z2 = bundle.getBoolean("shizuku:user-service-arg-no-create", false);
                boolean z3 = bundle.getBoolean("shizuku:user-service-arg-daemon", true);
                final boolean z4 = bundle.getBoolean("shizuku:user-service-arg-use-32-bit-app-process", false);
                StringBuilder sb = new StringBuilder();
                sb.append(packageName);
                sb.append(":");
                if (string6 == null) {
                    string6 = className;
                }
                sb.append(string6);
                final String string8 = sb.toString();
                synchronized (xn) {
                    try {
                        Xr xr = (Xr) ((Map) xn.b).get(string8);
                        if (!z2) {
                            final Xr xrC = xn.c(xr, string8, i6, z3, packageInfoF);
                            xrC.f.register(c0064bf2);
                            IBinder iBinder = xrC.e;
                            if (iBinder != null && iBinder.pingBinder()) {
                                xrC.a();
                            } else if (!xrC.h) {
                                xrC.c();
                                try {
                                    ((ExecutorService) xn.a).execute(new Runnable() { // from class: rikka.shizuku.Wr
                                        /* JADX WARN: Code duplicated, block: B:16:0x005b  */
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            String str3;
                                            String str4;
                                            xn.getClass();
                                            String str5 = xrC.d;
                                            Locale locale = Locale.ENGLISH;
                                            Log.println(2, "UserServiceManager", "Starting process for service record " + string8 + " (" + str5 + ")...");
                                            if (z4) {
                                                if (AbstractC0744xh.k == null) {
                                                    AbstractC0744xh.k = Boolean.valueOf(Build.SUPPORTED_32_BIT_ABIS.length > 0);
                                                }
                                                if (AbstractC0744xh.k.booleanValue()) {
                                                    str3 = new File("/system/bin/app_process32").exists() ? "/system/bin/app_process32" : "/system/bin/app_process";
                                                }
                                            }
                                            int i7 = ShizukuService.j;
                                            String str6 = AbstractC0599sr.n(0, "moe.shizuku.privileged.api").sourceDir;
                                            String str7 = ServiceStarter.a;
                                            StringBuilder sb2 = new StringBuilder();
                                            String str8 = packageName;
                                            sb2.append(str8);
                                            sb2.append(":");
                                            sb2.append(string7);
                                            String string9 = sb2.toString();
                                            boolean z5 = z;
                                            if (z5) {
                                                str4 = " " + ServiceStarter.a;
                                            } else {
                                                str4 = "";
                                            }
                                            String str9 = "(CLASSPATH='" + str6 + "' " + str3 + str4 + " /system/bin --nice-name='" + string9 + "' moe.shizuku.starter.ServiceStarter --token='" + str5 + "' --package='" + str8 + "' --class='" + className + "' --uid=" + callingUid + (z5 ? " --debug-name=".concat(string9) : "") + ")&";
                                            try {
                                                Process processExec2 = Runtime.getRuntime().exec("sh");
                                                OutputStream outputStream = processExec2.getOutputStream();
                                                outputStream.write(str9.getBytes());
                                                outputStream.flush();
                                                outputStream.close();
                                                int iWaitFor = processExec2.waitFor();
                                                if (iWaitFor != 0) {
                                                    throw new IllegalStateException(AbstractC0535qo.b(iWaitFor, "sh exited with "));
                                                }
                                            } catch (Throwable th3) {
                                                throw new IllegalStateException(th3.getMessage());
                                            }
                                        }
                                    });
                                } catch (Throwable th3) {
                                    th = th3;
                                    xn = xn;
                                    throw th;
                                }
                            }
                        } else if (xr != null) {
                            xr.f.register(c0064bf2);
                            IBinder iBinder2 = xr.e;
                            if (iBinder2 == null || !iBinder2.pingBinder()) {
                                i3 = i5 >= 13 ? -1 : 1;
                            } else {
                                xr.a();
                                if (i5 >= 13) {
                                    i3 = xr.c;
                                }
                            }
                        } else if (i5 >= 13) {
                        }
                        i3 = 0;
                    } catch (Throwable th4) {
                        th = th4;
                    }
                }
                parcel2.writeNoException();
                parcel2.writeInt(i3);
                return true;
            case 13:
                IBinder strongBinder2 = parcel.readStrongBinder();
                if (strongBinder2 != null) {
                    IInterface iInterfaceQueryLocalInterface2 = strongBinder2.queryLocalInterface("moe.shizuku.server.IShizukuServiceConnection");
                    if (iInterfaceQueryLocalInterface2 == null || !(iInterfaceQueryLocalInterface2 instanceof C0064bf)) {
                        c0064bf = new C0064bf();
                        c0064bf.a = strongBinder2;
                    } else {
                        c0064bf = (C0064bf) iInterfaceQueryLocalInterface2;
                    }
                }
                Bundle bundle2 = (Bundle) X8.c(parcel, Bundle.CREATOR);
                o("removeUserService");
                Xn xn2 = this.a;
                xn2.getClass();
                ComponentName componentName2 = (ComponentName) bundle2.getParcelable("shizuku:user-service-arg-component");
                int callingUid2 = Binder.getCallingUid();
                String packageName2 = componentName2.getPackageName();
                Xn.f(packageName2, callingUid2 % 100000, callingUid2 / 100000);
                String className2 = componentName2.getClassName();
                String string9 = bundle2.getString("shizuku:user-service-arg-tag");
                StringBuilder sb2 = new StringBuilder();
                sb2.append(packageName2);
                sb2.append(":");
                if (string9 != null) {
                    className2 = string9;
                }
                sb2.append(className2);
                String string10 = sb2.toString();
                boolean z5 = bundle2.containsKey("shizuku:user-service-remove") ? bundle2.getBoolean("shizuku:user-service-remove") : true;
                synchronized (xn2) {
                    try {
                        Xr xr2 = (Xr) ((Map) xn2.b).get(string10);
                        if (xr2 == null) {
                            i4 = 1;
                        } else if (z5) {
                            xn2.u(xr2);
                        } else {
                            xr2.f.unregister(c0064bf);
                        }
                    } catch (Throwable th5) {
                        throw th5;
                    }
                }
                parcel2.writeNoException();
                parcel2.writeInt(i4);
                return true;
            default:
                switch (i) {
                    case 15:
                        i(parcel.readInt());
                        parcel2.writeNoException();
                        return true;
                    case 16:
                        boolean zH = h();
                        parcel2.writeNoException();
                        parcel2.writeInt(zH ? 1 : 0);
                        return true;
                    case 17:
                        boolean zN = n();
                        parcel2.writeNoException();
                        parcel2.writeInt(zN ? 1 : 0);
                        return true;
                    case 18:
                        IBinder strongBinder3 = parcel.readStrongBinder();
                        if (strongBinder3 != null) {
                            IInterface iInterfaceQueryLocalInterface3 = strongBinder3.queryLocalInterface("moe.shizuku.server.IShizukuApplication");
                            if (iInterfaceQueryLocalInterface3 == null || !(iInterfaceQueryLocalInterface3 instanceof Ye)) {
                                Xe xe = new Xe();
                                xe.a = strongBinder3;
                                ye = xe;
                            } else {
                                ye = (Ye) iInterfaceQueryLocalInterface3;
                            }
                        }
                        ((ShizukuService) this).r(ye, (Bundle) X8.c(parcel, Bundle.CREATOR));
                        parcel2.writeNoException();
                        return true;
                    default:
                        switch (i) {
                            case 101:
                                ((ShizukuService) this).b();
                                parcel2.writeNoException();
                                return true;
                            case 102:
                                ((ShizukuService) this).a(parcel.readStrongBinder(), (Bundle) X8.c(parcel, Bundle.CREATOR));
                                parcel2.writeNoException();
                                return true;
                            case 103:
                                return true;
                            case 104:
                                parcel.readInt();
                                parcel2.writeNoException();
                                parcel2.writeInt(0);
                                return true;
                            case 105:
                                ((ShizukuService) this).m(parcel.readInt(), parcel.readInt(), parcel.readInt(), (Bundle) X8.c(parcel, Bundle.CREATOR));
                                return true;
                            case 106:
                                int iC = ((ShizukuService) this).c(parcel.readInt(), parcel.readInt());
                                parcel2.writeNoException();
                                parcel2.writeInt(iC);
                                return true;
                            case 107:
                                ((ShizukuService) this).d(parcel.readInt(), parcel.readInt(), parcel.readInt());
                                parcel2.writeNoException();
                                return true;
                            default:
                                return super.onTransact(i, parcel, parcel2, i2);
                        }
                }
        }
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this;
    }
}
