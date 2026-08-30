package rikka.shizuku;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;

/* JADX INFO: renamed from: rikka.shizuku.u, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0633u extends AbstractC0417n {
    public static AbstractC0633u o(byte[] bArr) throws IOException {
        C0355l c0355l = new C0355l(new ByteArrayInputStream(bArr), bArr.length, false);
        try {
            AbstractC0633u abstractC0633uE = c0355l.e();
            if (c0355l.available() == 0) {
                return abstractC0633uE;
            }
            throw new IOException("Extra data detected in stream");
        } catch (ClassCastException unused) {
            throw new IOException("cannot recognise object in stream");
        }
    }

    @Override // rikka.shizuku.AbstractC0417n
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof InterfaceC0202g) && i(((InterfaceC0202g) obj).d());
    }

    @Override // rikka.shizuku.AbstractC0417n
    public abstract int hashCode();

    public abstract boolean i(AbstractC0633u abstractC0633u);

    public abstract void j(Ys ys, boolean z);

    public abstract boolean k();

    public final void l(OutputStream outputStream) {
        new B8(1, outputStream).L(this);
    }

    public abstract int m(boolean z);

    public final boolean n(AbstractC0633u abstractC0633u) {
        return this == abstractC0633u || i(abstractC0633u);
    }

    @Override // rikka.shizuku.InterfaceC0202g
    public final AbstractC0633u d() {
        return this;
    }

    public AbstractC0633u p() {
        return this;
    }

    public AbstractC0633u q() {
        return this;
    }
}
