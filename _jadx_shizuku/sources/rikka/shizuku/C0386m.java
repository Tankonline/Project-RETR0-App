package rikka.shizuku;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;

/* JADX INFO: renamed from: rikka.shizuku.m, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0386m extends AbstractC0633u {
    public static final C0048b c = new C0048b(3, C0386m.class);
    public final byte[] a;
    public final int b;

    public C0386m(long j) {
        this.a = BigInteger.valueOf(j).toByteArray();
        this.b = 0;
    }

    public static C0386m r(Object obj) {
        if (obj == null || (obj instanceof C0386m)) {
            return (C0386m) obj;
        }
        if (!(obj instanceof byte[])) {
            throw new IllegalArgumentException("illegal object in getInstance: ".concat(obj.getClass().getName()));
        }
        try {
            C0048b c0048b = c;
            AbstractC0633u abstractC0633uO = AbstractC0633u.o((byte[]) obj);
            c0048b.a(abstractC0633uO);
            return (C0386m) abstractC0633uO;
        } catch (Exception e) {
            throw new IllegalArgumentException("encoding error in getInstance: " + e.toString());
        }
    }

    public static boolean t(byte[] bArr) {
        int length = bArr.length;
        if (length != 0) {
            return (length == 1 || bArr[0] != (bArr[1] >> 7) || AbstractC0747xk.b("org.bouncycastle.asn1.allow_unsafe_integer")) ? false : true;
        }
        return true;
    }

    @Override // rikka.shizuku.AbstractC0633u, rikka.shizuku.AbstractC0417n
    public final int hashCode() {
        return AbstractC0046as.D(this.a);
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final boolean i(AbstractC0633u abstractC0633u) {
        if (!(abstractC0633u instanceof C0386m)) {
            return false;
        }
        return Arrays.equals(this.a, ((C0386m) abstractC0633u).a);
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final void j(Ys ys, boolean z) throws IOException {
        ys.H(z, 2, this.a);
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final boolean k() {
        return false;
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final int m(boolean z) {
        return Ys.v(this.a.length, z);
    }

    public final boolean s(int i) {
        byte[] bArr = this.a;
        int length = bArr.length;
        int i2 = this.b;
        if (length - i2 > 4) {
            return false;
        }
        int length2 = bArr.length;
        int iMax = Math.max(i2, length2 - 4);
        int i3 = bArr[iMax];
        while (true) {
            iMax++;
            if (iMax >= length2) {
                break;
            }
            i3 = (i3 << 8) | (bArr[iMax] & 255);
        }
        return i3 == i;
    }

    public final String toString() {
        return new BigInteger(this.a).toString();
    }

    public C0386m(BigInteger bigInteger) {
        this.a = bigInteger.toByteArray();
        this.b = 0;
    }

    public C0386m(byte[] bArr) {
        if (t(bArr)) {
            throw new IllegalArgumentException("malformed integer");
        }
        this.a = bArr;
        int length = bArr.length - 1;
        int i = 0;
        while (i < length) {
            int i2 = i + 1;
            if (bArr[i] != (bArr[i2] >> 7)) {
                break;
            } else {
                i = i2;
            }
        }
        this.b = i;
    }
}
