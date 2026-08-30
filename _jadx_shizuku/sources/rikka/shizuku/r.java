package rikka.shizuku;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public abstract class r extends AbstractC0633u implements InterfaceC0571s {
    public static final C0048b b = new C0048b(5, r.class);
    public static final byte[] c = new byte[0];
    public final byte[] a;

    public r(byte[] bArr) {
        if (bArr == null) {
            throw new NullPointerException("'string' cannot be null");
        }
        this.a = bArr;
    }

    public static r r(Object obj) {
        if (obj == null || (obj instanceof r)) {
            return (r) obj;
        }
        if (obj instanceof InterfaceC0202g) {
            AbstractC0633u abstractC0633uD = ((InterfaceC0202g) obj).d();
            if (abstractC0633uD instanceof r) {
                return (r) abstractC0633uD;
            }
        } else if (obj instanceof byte[]) {
            try {
                C0048b c0048b = b;
                AbstractC0633u abstractC0633uO = AbstractC0633u.o((byte[]) obj);
                c0048b.a(abstractC0633uO);
                return (r) abstractC0633uO;
            } catch (IOException e) {
                throw new IllegalArgumentException("failed to construct OCTET STRING from byte[]: " + e.getMessage());
            }
        }
        throw new IllegalArgumentException("illegal object in getInstance: ".concat(obj.getClass().getName()));
    }

    @Override // rikka.shizuku.InterfaceC0571s
    public final InputStream c() {
        return new ByteArrayInputStream(this.a);
    }

    @Override // rikka.shizuku.AbstractC0633u, rikka.shizuku.AbstractC0417n
    public final int hashCode() {
        return AbstractC0046as.D(this.a);
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final boolean i(AbstractC0633u abstractC0633u) {
        if (!(abstractC0633u instanceof r)) {
            return false;
        }
        return Arrays.equals(this.a, ((r) abstractC0633u).a);
    }

    @Override // rikka.shizuku.AbstractC0633u
    public AbstractC0633u p() {
        return new A8(this.a);
    }

    @Override // rikka.shizuku.AbstractC0633u
    public AbstractC0633u q() {
        return new A8(this.a);
    }

    public final String toString() {
        R2 r2 = AbstractC0525qe.a;
        byte[] bArr = this.a;
        return "#".concat(Wo.a(AbstractC0525qe.a(bArr, bArr.length)));
    }

    @Override // rikka.shizuku.InterfaceC0126df
    public final AbstractC0633u a() {
        return this;
    }
}
