package moe.shizuku.manager.adb;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.app.Service;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import java.net.ConnectException;
import moe.shizuku.manager.adb.AdbPairingService;
import moe.shizuku.privileged.api.R;
import rikka.html.text.style.BulletSpan;
import rikka.shizuku.AbstractC0046as;
import rikka.shizuku.AbstractC0090ca;
import rikka.shizuku.AbstractC0599sr;
import rikka.shizuku.AbstractC0604t1;
import rikka.shizuku.C0112d1;
import rikka.shizuku.C0266i1;
import rikka.shizuku.C0357l1;
import rikka.shizuku.C0550r9;
import rikka.shizuku.C0666v1;
import rikka.shizuku.ExecutorC0243h9;
import rikka.shizuku.InterfaceC0124dd;
import rikka.shizuku.Ld;
import rikka.shizuku.V;
import rikka.shizuku.V0;

/* JADX INFO: loaded from: classes.dex */
@TargetApi(30)
public final class AdbPairingService extends Service {
    public static final /* synthetic */ int h = 0;
    public C0357l1 a;
    public final V0 b = new V0(1, this);
    public boolean c;
    public final Object d;
    public final Object e;
    public final Object f;
    public final Object g;

    public AdbPairingService() {
        final int i = 0;
        this.d = AbstractC0046as.H(new InterfaceC0124dd(this) { // from class: rikka.shizuku.u1
            public final /* synthetic */ AdbPairingService b;

            {
                this.b = this;
            }

            /* JADX WARN: Type inference failed for: r1v11, types: [java.lang.Object, rikka.shizuku.jg] */
            @Override // rikka.shizuku.InterfaceC0124dd
            public final Object a() {
                AdbPairingService adbPairingService = this.b;
                switch (i) {
                    case 0:
                        int i2 = AdbPairingService.h;
                        return new Notification.Action.Builder((Icon) null, adbPairingService.getString(R.string._0_resource_name_obfuscated_res_0x7f1100f7), PendingIntent.getService(adbPairingService, 2, new Intent(adbPairingService, (Class<?>) AdbPairingService.class).setAction("stop"), Build.VERSION.SDK_INT >= 31 ? 67108864 : 0)).build();
                    case 1:
                        int i3 = AdbPairingService.h;
                        return new Notification.Action.Builder((Icon) null, adbPairingService.getString(R.string._0_resource_name_obfuscated_res_0x7f1100f4), PendingIntent.getService(adbPairingService, 3, new Intent(adbPairingService, (Class<?>) AdbPairingService.class).setAction("start"), Build.VERSION.SDK_INT >= 31 ? 67108864 : 0)).build();
                    case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                        int i4 = AdbPairingService.h;
                        RemoteInput.Builder builder = new RemoteInput.Builder("paring_code");
                        builder.setLabel(adbPairingService.getString(R.string._0_resource_name_obfuscated_res_0x7f110055));
                        return new Notification.Action.Builder((Icon) null, adbPairingService.getString(R.string._0_resource_name_obfuscated_res_0x7f1100f3), PendingIntent.getForegroundService(adbPairingService, 1, new Intent(adbPairingService, (Class<?>) AdbPairingService.class).setAction("reply").putExtra("paring_code", -1), Build.VERSION.SDK_INT >= 31 ? 167772160 : 134217728)).addRemoteInput(builder.build()).build();
                    case 3:
                        int i5 = AdbPairingService.h;
                        return V.c(adbPairingService).setColor(adbPairingService.getColor(R.color._0_resource_name_obfuscated_res_0x7f06056e)).setSmallIcon(R.drawable._0_resource_name_obfuscated_res_0x7f0800cc).setContentTitle(adbPairingService.getString(R.string._0_resource_name_obfuscated_res_0x7f1100f5)).addAction((Notification.Action) adbPairingService.d.getValue()).build();
                    default:
                        int i6 = AdbPairingService.h;
                        return V.c(adbPairingService).setColor(adbPairingService.getColor(R.color._0_resource_name_obfuscated_res_0x7f06056e)).setContentTitle(adbPairingService.getString(R.string._0_resource_name_obfuscated_res_0x7f1100fa)).setSmallIcon(R.drawable._0_resource_name_obfuscated_res_0x7f0800cc).build();
                }
            }
        });
        final int i2 = 1;
        AbstractC0046as.H(new InterfaceC0124dd(this) { // from class: rikka.shizuku.u1
            public final /* synthetic */ AdbPairingService b;

            {
                this.b = this;
            }

            /* JADX WARN: Type inference failed for: r1v11, types: [java.lang.Object, rikka.shizuku.jg] */
            @Override // rikka.shizuku.InterfaceC0124dd
            public final Object a() {
                AdbPairingService adbPairingService = this.b;
                switch (i2) {
                    case 0:
                        int i3 = AdbPairingService.h;
                        return new Notification.Action.Builder((Icon) null, adbPairingService.getString(R.string._0_resource_name_obfuscated_res_0x7f1100f7), PendingIntent.getService(adbPairingService, 2, new Intent(adbPairingService, (Class<?>) AdbPairingService.class).setAction("stop"), Build.VERSION.SDK_INT >= 31 ? 67108864 : 0)).build();
                    case 1:
                        int i4 = AdbPairingService.h;
                        return new Notification.Action.Builder((Icon) null, adbPairingService.getString(R.string._0_resource_name_obfuscated_res_0x7f1100f4), PendingIntent.getService(adbPairingService, 3, new Intent(adbPairingService, (Class<?>) AdbPairingService.class).setAction("start"), Build.VERSION.SDK_INT >= 31 ? 67108864 : 0)).build();
                    case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                        int i5 = AdbPairingService.h;
                        RemoteInput.Builder builder = new RemoteInput.Builder("paring_code");
                        builder.setLabel(adbPairingService.getString(R.string._0_resource_name_obfuscated_res_0x7f110055));
                        return new Notification.Action.Builder((Icon) null, adbPairingService.getString(R.string._0_resource_name_obfuscated_res_0x7f1100f3), PendingIntent.getForegroundService(adbPairingService, 1, new Intent(adbPairingService, (Class<?>) AdbPairingService.class).setAction("reply").putExtra("paring_code", -1), Build.VERSION.SDK_INT >= 31 ? 167772160 : 134217728)).addRemoteInput(builder.build()).build();
                    case 3:
                        int i6 = AdbPairingService.h;
                        return V.c(adbPairingService).setColor(adbPairingService.getColor(R.color._0_resource_name_obfuscated_res_0x7f06056e)).setSmallIcon(R.drawable._0_resource_name_obfuscated_res_0x7f0800cc).setContentTitle(adbPairingService.getString(R.string._0_resource_name_obfuscated_res_0x7f1100f5)).addAction((Notification.Action) adbPairingService.d.getValue()).build();
                    default:
                        int i7 = AdbPairingService.h;
                        return V.c(adbPairingService).setColor(adbPairingService.getColor(R.color._0_resource_name_obfuscated_res_0x7f06056e)).setContentTitle(adbPairingService.getString(R.string._0_resource_name_obfuscated_res_0x7f1100fa)).setSmallIcon(R.drawable._0_resource_name_obfuscated_res_0x7f0800cc).build();
                }
            }
        });
        final int i3 = 2;
        this.e = AbstractC0046as.H(new InterfaceC0124dd(this) { // from class: rikka.shizuku.u1
            public final /* synthetic */ AdbPairingService b;

            {
                this.b = this;
            }

            /* JADX WARN: Type inference failed for: r1v11, types: [java.lang.Object, rikka.shizuku.jg] */
            @Override // rikka.shizuku.InterfaceC0124dd
            public final Object a() {
                AdbPairingService adbPairingService = this.b;
                switch (i3) {
                    case 0:
                        int i4 = AdbPairingService.h;
                        return new Notification.Action.Builder((Icon) null, adbPairingService.getString(R.string._0_resource_name_obfuscated_res_0x7f1100f7), PendingIntent.getService(adbPairingService, 2, new Intent(adbPairingService, (Class<?>) AdbPairingService.class).setAction("stop"), Build.VERSION.SDK_INT >= 31 ? 67108864 : 0)).build();
                    case 1:
                        int i5 = AdbPairingService.h;
                        return new Notification.Action.Builder((Icon) null, adbPairingService.getString(R.string._0_resource_name_obfuscated_res_0x7f1100f4), PendingIntent.getService(adbPairingService, 3, new Intent(adbPairingService, (Class<?>) AdbPairingService.class).setAction("start"), Build.VERSION.SDK_INT >= 31 ? 67108864 : 0)).build();
                    case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                        int i6 = AdbPairingService.h;
                        RemoteInput.Builder builder = new RemoteInput.Builder("paring_code");
                        builder.setLabel(adbPairingService.getString(R.string._0_resource_name_obfuscated_res_0x7f110055));
                        return new Notification.Action.Builder((Icon) null, adbPairingService.getString(R.string._0_resource_name_obfuscated_res_0x7f1100f3), PendingIntent.getForegroundService(adbPairingService, 1, new Intent(adbPairingService, (Class<?>) AdbPairingService.class).setAction("reply").putExtra("paring_code", -1), Build.VERSION.SDK_INT >= 31 ? 167772160 : 134217728)).addRemoteInput(builder.build()).build();
                    case 3:
                        int i7 = AdbPairingService.h;
                        return V.c(adbPairingService).setColor(adbPairingService.getColor(R.color._0_resource_name_obfuscated_res_0x7f06056e)).setSmallIcon(R.drawable._0_resource_name_obfuscated_res_0x7f0800cc).setContentTitle(adbPairingService.getString(R.string._0_resource_name_obfuscated_res_0x7f1100f5)).addAction((Notification.Action) adbPairingService.d.getValue()).build();
                    default:
                        int i8 = AdbPairingService.h;
                        return V.c(adbPairingService).setColor(adbPairingService.getColor(R.color._0_resource_name_obfuscated_res_0x7f06056e)).setContentTitle(adbPairingService.getString(R.string._0_resource_name_obfuscated_res_0x7f1100fa)).setSmallIcon(R.drawable._0_resource_name_obfuscated_res_0x7f0800cc).build();
                }
            }
        });
        final int i4 = 3;
        this.f = AbstractC0046as.H(new InterfaceC0124dd(this) { // from class: rikka.shizuku.u1
            public final /* synthetic */ AdbPairingService b;

            {
                this.b = this;
            }

            /* JADX WARN: Type inference failed for: r1v11, types: [java.lang.Object, rikka.shizuku.jg] */
            @Override // rikka.shizuku.InterfaceC0124dd
            public final Object a() {
                AdbPairingService adbPairingService = this.b;
                switch (i4) {
                    case 0:
                        int i5 = AdbPairingService.h;
                        return new Notification.Action.Builder((Icon) null, adbPairingService.getString(R.string._0_resource_name_obfuscated_res_0x7f1100f7), PendingIntent.getService(adbPairingService, 2, new Intent(adbPairingService, (Class<?>) AdbPairingService.class).setAction("stop"), Build.VERSION.SDK_INT >= 31 ? 67108864 : 0)).build();
                    case 1:
                        int i6 = AdbPairingService.h;
                        return new Notification.Action.Builder((Icon) null, adbPairingService.getString(R.string._0_resource_name_obfuscated_res_0x7f1100f4), PendingIntent.getService(adbPairingService, 3, new Intent(adbPairingService, (Class<?>) AdbPairingService.class).setAction("start"), Build.VERSION.SDK_INT >= 31 ? 67108864 : 0)).build();
                    case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                        int i7 = AdbPairingService.h;
                        RemoteInput.Builder builder = new RemoteInput.Builder("paring_code");
                        builder.setLabel(adbPairingService.getString(R.string._0_resource_name_obfuscated_res_0x7f110055));
                        return new Notification.Action.Builder((Icon) null, adbPairingService.getString(R.string._0_resource_name_obfuscated_res_0x7f1100f3), PendingIntent.getForegroundService(adbPairingService, 1, new Intent(adbPairingService, (Class<?>) AdbPairingService.class).setAction("reply").putExtra("paring_code", -1), Build.VERSION.SDK_INT >= 31 ? 167772160 : 134217728)).addRemoteInput(builder.build()).build();
                    case 3:
                        int i8 = AdbPairingService.h;
                        return V.c(adbPairingService).setColor(adbPairingService.getColor(R.color._0_resource_name_obfuscated_res_0x7f06056e)).setSmallIcon(R.drawable._0_resource_name_obfuscated_res_0x7f0800cc).setContentTitle(adbPairingService.getString(R.string._0_resource_name_obfuscated_res_0x7f1100f5)).addAction((Notification.Action) adbPairingService.d.getValue()).build();
                    default:
                        int i9 = AdbPairingService.h;
                        return V.c(adbPairingService).setColor(adbPairingService.getColor(R.color._0_resource_name_obfuscated_res_0x7f06056e)).setContentTitle(adbPairingService.getString(R.string._0_resource_name_obfuscated_res_0x7f1100fa)).setSmallIcon(R.drawable._0_resource_name_obfuscated_res_0x7f0800cc).build();
                }
            }
        });
        final int i5 = 4;
        this.g = AbstractC0046as.H(new InterfaceC0124dd(this) { // from class: rikka.shizuku.u1
            public final /* synthetic */ AdbPairingService b;

            {
                this.b = this;
            }

            /* JADX WARN: Type inference failed for: r1v11, types: [java.lang.Object, rikka.shizuku.jg] */
            @Override // rikka.shizuku.InterfaceC0124dd
            public final Object a() {
                AdbPairingService adbPairingService = this.b;
                switch (i5) {
                    case 0:
                        int i6 = AdbPairingService.h;
                        return new Notification.Action.Builder((Icon) null, adbPairingService.getString(R.string._0_resource_name_obfuscated_res_0x7f1100f7), PendingIntent.getService(adbPairingService, 2, new Intent(adbPairingService, (Class<?>) AdbPairingService.class).setAction("stop"), Build.VERSION.SDK_INT >= 31 ? 67108864 : 0)).build();
                    case 1:
                        int i7 = AdbPairingService.h;
                        return new Notification.Action.Builder((Icon) null, adbPairingService.getString(R.string._0_resource_name_obfuscated_res_0x7f1100f4), PendingIntent.getService(adbPairingService, 3, new Intent(adbPairingService, (Class<?>) AdbPairingService.class).setAction("start"), Build.VERSION.SDK_INT >= 31 ? 67108864 : 0)).build();
                    case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                        int i8 = AdbPairingService.h;
                        RemoteInput.Builder builder = new RemoteInput.Builder("paring_code");
                        builder.setLabel(adbPairingService.getString(R.string._0_resource_name_obfuscated_res_0x7f110055));
                        return new Notification.Action.Builder((Icon) null, adbPairingService.getString(R.string._0_resource_name_obfuscated_res_0x7f1100f3), PendingIntent.getForegroundService(adbPairingService, 1, new Intent(adbPairingService, (Class<?>) AdbPairingService.class).setAction("reply").putExtra("paring_code", -1), Build.VERSION.SDK_INT >= 31 ? 167772160 : 134217728)).addRemoteInput(builder.build()).build();
                    case 3:
                        int i9 = AdbPairingService.h;
                        return V.c(adbPairingService).setColor(adbPairingService.getColor(R.color._0_resource_name_obfuscated_res_0x7f06056e)).setSmallIcon(R.drawable._0_resource_name_obfuscated_res_0x7f0800cc).setContentTitle(adbPairingService.getString(R.string._0_resource_name_obfuscated_res_0x7f1100f5)).addAction((Notification.Action) adbPairingService.d.getValue()).build();
                    default:
                        int i10 = AdbPairingService.h;
                        return V.c(adbPairingService).setColor(adbPairingService.getColor(R.color._0_resource_name_obfuscated_res_0x7f06056e)).setContentTitle(adbPairingService.getString(R.string._0_resource_name_obfuscated_res_0x7f1100fa)).setSmallIcon(R.drawable._0_resource_name_obfuscated_res_0x7f0800cc).build();
                }
            }
        });
    }

    public static final void a(AdbPairingService adbPairingService, boolean z, Throwable th) {
        String string;
        String stackTraceString;
        String string2;
        adbPairingService.stopForeground(1);
        if (z) {
            Log.i("AdbPairingService", "Pair succeed");
            string = adbPairingService.getString(R.string._0_resource_name_obfuscated_res_0x7f1100f9);
            string2 = adbPairingService.getString(R.string._0_resource_name_obfuscated_res_0x7f1100f8);
            if (adbPairingService.c) {
                adbPairingService.c = false;
                C0357l1 c0357l1 = adbPairingService.a;
                if (c0357l1 != null) {
                    c0357l1.b();
                }
            }
        } else {
            string = adbPairingService.getString(R.string._0_resource_name_obfuscated_res_0x7f1100f2);
            if (th instanceof ConnectException) {
                stackTraceString = adbPairingService.getString(R.string._0_resource_name_obfuscated_res_0x7f110041);
            } else if (th instanceof C0112d1) {
                stackTraceString = adbPairingService.getString(R.string._0_resource_name_obfuscated_res_0x7f110102);
            } else if (th instanceof C0266i1) {
                stackTraceString = adbPairingService.getString(R.string._0_resource_name_obfuscated_res_0x7f11001e);
            } else {
                stackTraceString = th != null ? Log.getStackTraceString(th) : null;
            }
            if (th != null) {
                Log.w("AdbPairingService", "Pair failed", th);
            } else {
                Log.w("AdbPairingService", "Pair failed");
            }
            string2 = stackTraceString;
        }
        ((NotificationManager) adbPairingService.getSystemService(NotificationManager.class)).notify(1, V.d(adbPairingService).setColor(adbPairingService.getColor(R.color._0_resource_name_obfuscated_res_0x7f06056e)).setSmallIcon(R.drawable._0_resource_name_obfuscated_res_0x7f0800cc).setContentTitle(string).setContentText(string2).build());
        adbPairingService.stopSelf();
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Object, rikka.shizuku.jg] */
    public final Notification b() {
        if (!this.c) {
            this.c = true;
            C0357l1 c0357l1 = new C0357l1(this, "_adb-tls-pairing._tcp", this.b);
            c0357l1.a();
            this.a = c0357l1;
        }
        return (Notification) this.f.getValue();
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public final void onCreate() {
        super.onCreate();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
        V.o();
        NotificationChannel notificationChannelF = V.f(getString(R.string._0_resource_name_obfuscated_res_0x7f1100fb));
        notificationChannelF.setSound(null, null);
        notificationChannelF.setShowBadge(false);
        notificationChannelF.setAllowBubbles(false);
        notificationManager.createNotificationChannel(notificationChannelF);
    }

    @Override // android.app.Service
    public final void onDestroy() {
        super.onDestroy();
        if (this.c) {
            this.c = false;
            C0357l1 c0357l1 = this.a;
            if (c0357l1 != null) {
                c0357l1.b();
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:47:0x0084 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:49:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r5v14, types: [java.lang.Object, rikka.shizuku.jg] */
    @Override // android.app.Service
    public final int onStartCommand(Intent intent, int i, int i2) {
        CharSequence charSequence;
        Notification notificationB = null;
        String action = intent != null ? intent.getAction() : null;
        if (action != null) {
            int iHashCode = action.hashCode();
            if (iHashCode != 3540994) {
                if (iHashCode != 108401386) {
                    if (iHashCode == 109757538 && action.equals("start")) {
                        notificationB = b();
                        if (notificationB == null) {
                            return 3;
                        }
                        try {
                            startForeground(1, notificationB, -1);
                            return 3;
                        } catch (Throwable th) {
                            Log.e("AdbPairingService", "startForeground failed", th);
                            if (Build.VERSION.SDK_INT < 31 || !AbstractC0604t1.p(th)) {
                                return 3;
                            }
                            ((NotificationManager) getSystemService(NotificationManager.class)).notify(1, notificationB);
                            return 3;
                        }
                    }
                } else if (action.equals("reply")) {
                    Bundle resultsFromIntent = RemoteInput.getResultsFromIntent(intent);
                    if (resultsFromIntent == null || (charSequence = resultsFromIntent.getCharSequence("paring_code")) == null) {
                        charSequence = "";
                    }
                    int intExtra = intent.getIntExtra("paring_code", -1);
                    if (intExtra != -1) {
                        String string = charSequence.toString();
                        Ld ld = Ld.a;
                        C0550r9 c0550r9 = AbstractC0090ca.a;
                        ExecutorC0243h9 executorC0243h9 = ExecutorC0243h9.b;
                        AbstractC0599sr.z(ld, new C0666v1(intExtra, string, this, null), 2);
                        notificationB = (Notification) this.g.getValue();
                    } else {
                        notificationB = b();
                    }
                    if (notificationB == null) {
                        return 3;
                    }
                    startForeground(1, notificationB, -1);
                    return 3;
                }
            } else if (action.equals("stop")) {
                stopForeground(1);
                stopSelf();
                if (notificationB == null) {
                    return 3;
                }
                startForeground(1, notificationB, -1);
                return 3;
            }
        }
        return 2;
    }
}
