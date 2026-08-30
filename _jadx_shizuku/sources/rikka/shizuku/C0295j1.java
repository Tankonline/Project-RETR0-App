package rikka.shizuku;

import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.util.Log;

/* JADX INFO: renamed from: rikka.shizuku.j1, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0295j1 implements NsdManager.DiscoveryListener {
    public final C0357l1 a;

    public C0295j1(C0357l1 c0357l1) {
        this.a = c0357l1;
    }

    @Override // android.net.nsd.NsdManager.DiscoveryListener
    public final void onDiscoveryStarted(String str) {
        Log.v("AdbMdns", "onDiscoveryStarted: ".concat(str));
        this.a.c = true;
    }

    @Override // android.net.nsd.NsdManager.DiscoveryListener
    public final void onDiscoveryStopped(String str) {
        Log.v("AdbMdns", "onDiscoveryStopped: ".concat(str));
        this.a.c = false;
    }

    @Override // android.net.nsd.NsdManager.DiscoveryListener
    public final void onServiceFound(NsdServiceInfo nsdServiceInfo) {
        Log.v("AdbMdns", "onServiceFound: " + nsdServiceInfo.getServiceName());
        C0357l1 c0357l1 = this.a;
        c0357l1.getClass();
        c0357l1.g.resolveService(nsdServiceInfo, new C0326k1(c0357l1));
    }

    @Override // android.net.nsd.NsdManager.DiscoveryListener
    public final void onServiceLost(NsdServiceInfo nsdServiceInfo) {
        Log.v("AdbMdns", "onServiceLost: " + nsdServiceInfo.getServiceName());
        C0357l1 c0357l1 = this.a;
        c0357l1.getClass();
        if (AbstractC0046as.c(nsdServiceInfo.getServiceName(), c0357l1.e)) {
            c0357l1.b.b(-1);
        }
    }

    @Override // android.net.nsd.NsdManager.DiscoveryListener
    public final void onStartDiscoveryFailed(String str, int i) {
        Log.v("AdbMdns", "onStartDiscoveryFailed: " + str + ", " + i);
    }

    @Override // android.net.nsd.NsdManager.DiscoveryListener
    public final void onStopDiscoveryFailed(String str, int i) {
        Log.v("AdbMdns", "onStopDiscoveryFailed: " + str + ", " + i);
    }
}
