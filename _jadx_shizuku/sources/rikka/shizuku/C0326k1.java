package rikka.shizuku;

import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.util.Iterator;

/* JADX INFO: renamed from: rikka.shizuku.k1, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0326k1 implements NsdManager.ResolveListener {
    public final C0357l1 a;

    public C0326k1(C0357l1 c0357l1) {
        this.a = c0357l1;
    }

    @Override // android.net.nsd.NsdManager.ResolveListener
    public final void onServiceResolved(NsdServiceInfo nsdServiceInfo) {
        C0357l1 c0357l1 = this.a;
        if (c0357l1.d) {
            Iterator it = Tm.a0(new C0764y6(NetworkInterface.getNetworkInterfaces())).iterator();
            while (it.hasNext()) {
                Iterator it2 = Tm.a0(new C0764y6(((NetworkInterface) it.next()).getInetAddresses())).iterator();
                while (it2.hasNext()) {
                    if (AbstractC0046as.c(nsdServiceInfo.getHost().getHostAddress(), ((InetAddress) it2.next()).getHostAddress())) {
                        int port = nsdServiceInfo.getPort();
                        try {
                            ServerSocket serverSocket = new ServerSocket();
                            try {
                                serverSocket.bind(new InetSocketAddress("127.0.0.1", port), 1);
                                serverSocket.close();
                                return;
                            } catch (Throwable th) {
                                try {
                                    throw th;
                                } catch (Throwable th2) {
                                    AbstractC0599sr.d(serverSocket, th);
                                    throw th2;
                                }
                            }
                        } catch (IOException unused) {
                            c0357l1.e = nsdServiceInfo.getServiceName();
                            c0357l1.b.b(Integer.valueOf(nsdServiceInfo.getPort()));
                            return;
                        }
                    }
                }
            }
        }
    }

    @Override // android.net.nsd.NsdManager.ResolveListener
    public final void onResolveFailed(NsdServiceInfo nsdServiceInfo, int i) {
    }
}
