package rikka.shizuku;

import java.net.Socket;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.util.Arrays;
import javax.net.ssl.X509ExtendedKeyManager;

/* JADX INFO: renamed from: rikka.shizuku.f1, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0173f1 extends X509ExtendedKeyManager {
    public final String a = "key";
    public final /* synthetic */ C0235h1 b;

    public C0173f1(C0235h1 c0235h1) {
        this.b = c0235h1;
    }

    @Override // javax.net.ssl.X509KeyManager
    public final String chooseClientAlias(String[] strArr, Principal[] principalArr, Socket socket) {
        Arrays.toString(strArr);
        if (principalArr != null) {
            Arrays.toString(principalArr);
        }
        for (String str : strArr) {
            if (AbstractC0046as.c(str, "RSA")) {
                return this.a;
            }
        }
        return null;
    }

    @Override // javax.net.ssl.X509KeyManager
    public final String chooseServerAlias(String str, Principal[] principalArr, Socket socket) {
        return null;
    }

    @Override // javax.net.ssl.X509KeyManager
    public final X509Certificate[] getCertificateChain(String str) {
        if (AbstractC0046as.c(str, this.a)) {
            return new X509Certificate[]{(X509Certificate) this.b.c};
        }
        return null;
    }

    @Override // javax.net.ssl.X509KeyManager
    public final String[] getClientAliases(String str, Principal[] principalArr) {
        return null;
    }

    @Override // javax.net.ssl.X509KeyManager
    public final PrivateKey getPrivateKey(String str) {
        if (AbstractC0046as.c(str, this.a)) {
            return (RSAPrivateKey) this.b.a;
        }
        return null;
    }

    @Override // javax.net.ssl.X509KeyManager
    public final String[] getServerAliases(String str, Principal[] principalArr) {
        return null;
    }
}
