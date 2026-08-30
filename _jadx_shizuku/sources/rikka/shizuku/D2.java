package rikka.shizuku;

import android.content.Context;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationManager;
import android.os.PowerManager;
import android.util.Log;
import java.util.Calendar;

/* JADX INFO: loaded from: classes.dex */
public final class D2 extends F2 {
    public final /* synthetic */ int c = 0;
    public final /* synthetic */ I2 d;
    public final Object e;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public D2(I2 i2, K3 k3) {
        super(i2);
        this.d = i2;
        this.e = k3;
    }

    @Override // rikka.shizuku.F2
    public final IntentFilter d() {
        switch (this.c) {
            case 0:
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.os.action.POWER_SAVE_MODE_CHANGED");
                return intentFilter;
            default:
                IntentFilter intentFilter2 = new IntentFilter();
                intentFilter2.addAction("android.intent.action.TIME_SET");
                intentFilter2.addAction("android.intent.action.TIMEZONE_CHANGED");
                intentFilter2.addAction("android.intent.action.TIME_TICK");
                return intentFilter2;
        }
    }

    /* JADX WARN: Code duplicated, block: B:14:0x003c  */
    @Override // rikka.shizuku.F2
    public final int e() {
        Location lastKnownLocation;
        boolean z;
        long j;
        switch (this.c) {
            case 0:
                return ((PowerManager) this.e).isPowerSaveMode() ? 2 : 1;
            default:
                K3 k3 = (K3) this.e;
                Iq iq = (Iq) k3.b;
                if (iq.b > System.currentTimeMillis()) {
                    z = iq.a;
                } else {
                    Context context = (Context) k3.c;
                    int iH = AbstractC0744xh.h(context, "android.permission.ACCESS_COARSE_LOCATION");
                    Location lastKnownLocation2 = null;
                    LocationManager locationManager = (LocationManager) k3.a;
                    if (iH == 0) {
                        try {
                            if (locationManager.isProviderEnabled("network")) {
                                lastKnownLocation = locationManager.getLastKnownLocation("network");
                            } else {
                                lastKnownLocation = null;
                            }
                            break;
                        } catch (Exception unused) {
                        }
                    } else {
                        lastKnownLocation = null;
                    }
                    if (AbstractC0744xh.h(context, "android.permission.ACCESS_FINE_LOCATION") == 0) {
                        try {
                            if (locationManager.isProviderEnabled("gps")) {
                                lastKnownLocation2 = locationManager.getLastKnownLocation("gps");
                            }
                            break;
                        } catch (Exception unused2) {
                        }
                    }
                    if (lastKnownLocation2 == null || lastKnownLocation == null ? lastKnownLocation2 != null : lastKnownLocation2.getTime() > lastKnownLocation.getTime()) {
                        lastKnownLocation = lastKnownLocation2;
                    }
                    z = false;
                    if (lastKnownLocation != null) {
                        long jCurrentTimeMillis = System.currentTimeMillis();
                        if (Hq.d == null) {
                            Hq.d = new Hq();
                        }
                        Hq hq = Hq.d;
                        hq.a(jCurrentTimeMillis - 86400000, lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                        hq.a(jCurrentTimeMillis, lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                        z = hq.c == 1;
                        long j2 = hq.b;
                        long j3 = hq.a;
                        hq.a(jCurrentTimeMillis + 86400000, lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                        long j4 = hq.b;
                        if (j2 == -1 || j3 == -1) {
                            j = jCurrentTimeMillis + 43200000;
                        } else {
                            if (jCurrentTimeMillis <= j3) {
                                j4 = jCurrentTimeMillis > j2 ? j3 : j2;
                            }
                            j = j4 + 60000;
                        }
                        iq.a = z;
                        iq.b = j;
                    } else {
                        Log.i("TwilightManager", "Could not get last known location. This is probably because the app does not have any location permissions. Falling back to hardcoded sunrise/sunset values.");
                        int i = Calendar.getInstance().get(11);
                        if (i < 6 || i >= 22) {
                            z = true;
                        }
                    }
                }
                return z ? 2 : 1;
        }
    }

    @Override // rikka.shizuku.F2
    public final void g() {
        switch (this.c) {
            case 0:
                this.d.p(true, true);
                break;
            default:
                this.d.p(true, true);
                break;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public D2(I2 i2, Context context) {
        super(i2);
        this.d = i2;
        this.e = (PowerManager) context.getApplicationContext().getSystemService("power");
    }
}
