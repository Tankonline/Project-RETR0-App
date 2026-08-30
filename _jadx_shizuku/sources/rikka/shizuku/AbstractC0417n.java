package rikka.shizuku;

import java.io.ByteArrayOutputStream;

/* JADX INFO: renamed from: rikka.shizuku.n, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0417n implements InterfaceC0202g {
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof InterfaceC0202g) {
            return d().n(((InterfaceC0202g) obj).d());
        }
        return false;
    }

    public final byte[] g() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        AbstractC0633u abstractC0633uD = d();
        abstractC0633uD.getClass();
        new Ys(1, byteArrayOutputStream).L(abstractC0633uD);
        return byteArrayOutputStream.toByteArray();
    }

    public final byte[] h() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        d().l(byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public int hashCode() {
        return d().hashCode();
    }
}
