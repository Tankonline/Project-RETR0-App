package rikka.shizuku;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: renamed from: rikka.shizuku.c, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0079c extends AbstractC0633u implements A, InterfaceC0110d {
    public static final C0048b b = new C0048b(0, AbstractC0079c.class);
    public static final char[] c = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    public final byte[] a;

    public AbstractC0079c(boolean z, byte[] bArr) {
        if (z) {
            if (bArr == null) {
                throw new NullPointerException("'contents' cannot be null");
            }
            if (bArr.length < 1) {
                throw new IllegalArgumentException("'contents' cannot be empty");
            }
            int i = bArr[0] & 255;
            if (i > 0) {
                if (bArr.length < 2) {
                    throw new IllegalArgumentException("zero length data with non-zero pad bits");
                }
                if (i > 7) {
                    throw new IllegalArgumentException("pad bits cannot be greater than 7 or less than 0");
                }
            }
        }
        this.a = bArr;
    }

    public static AbstractC0079c r(byte[] bArr) {
        int length = bArr.length;
        if (length < 1) {
            throw new IllegalArgumentException("truncated BIT STRING detected");
        }
        int i = bArr[0] & 255;
        if (i > 0) {
            if (i > 7 || length < 2) {
                throw new IllegalArgumentException("invalid pad bits detected");
            }
            byte b2 = bArr[length - 1];
            if (b2 != ((byte) ((255 << i) & b2))) {
                return new M8(false, bArr);
            }
        }
        return new C0580s8(false, bArr);
    }

    public static AbstractC0079c s(InterfaceC0202g interfaceC0202g) {
        if (interfaceC0202g == null || (interfaceC0202g instanceof AbstractC0079c)) {
            return (AbstractC0079c) interfaceC0202g;
        }
        AbstractC0633u abstractC0633uD = interfaceC0202g.d();
        if (abstractC0633uD instanceof AbstractC0079c) {
            return (AbstractC0079c) abstractC0633uD;
        }
        throw new IllegalArgumentException("illegal object in getInstance: ".concat(interfaceC0202g.getClass().getName()));
    }

    @Override // rikka.shizuku.InterfaceC0110d
    public final InputStream b() {
        byte[] bArr = this.a;
        return new ByteArrayInputStream(bArr, 1, bArr.length - 1);
    }

    @Override // rikka.shizuku.A
    public final String e() {
        try {
            byte[] bArrG = g();
            StringBuffer stringBuffer = new StringBuffer((bArrG.length * 2) + 1);
            stringBuffer.append('#');
            for (int i = 0; i != bArrG.length; i++) {
                byte b2 = bArrG[i];
                char[] cArr = c;
                stringBuffer.append(cArr[(b2 >>> 4) & 15]);
                stringBuffer.append(cArr[b2 & 15]);
            }
            return stringBuffer.toString();
        } catch (IOException e) {
            throw new C0602t("Internal error encoding BitString: " + e.getMessage(), e);
        }
    }

    @Override // rikka.shizuku.InterfaceC0110d
    public final int f() {
        return this.a[0] & 255;
    }

    @Override // rikka.shizuku.AbstractC0633u, rikka.shizuku.AbstractC0417n
    public final int hashCode() {
        byte[] bArr = this.a;
        if (bArr.length < 2) {
            return 1;
        }
        int i = 0;
        int i2 = bArr[0] & 255;
        int length = bArr.length;
        int i3 = length - 1;
        byte b2 = (byte) ((255 << i2) & bArr[i3]);
        if (bArr != null) {
            i = length;
            while (true) {
                i3--;
                if (i3 < 0) {
                    break;
                }
                i = (i * 257) ^ bArr[i3];
            }
        }
        return (i * 257) ^ b2;
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final boolean i(AbstractC0633u abstractC0633u) {
        if (!(abstractC0633u instanceof AbstractC0079c)) {
            return false;
        }
        byte[] bArr = ((AbstractC0079c) abstractC0633u).a;
        byte[] bArr2 = this.a;
        int length = bArr2.length;
        if (bArr.length != length) {
            return false;
        }
        if (length == 1) {
            return true;
        }
        int i = length - 1;
        for (int i2 = 0; i2 < i; i2++) {
            if (bArr2[i2] != bArr[i2]) {
                return false;
            }
        }
        int i3 = 255 << (bArr2[0] & 255);
        return ((byte) (bArr2[i] & i3)) == ((byte) (bArr[i] & i3));
    }

    @Override // rikka.shizuku.AbstractC0633u
    public AbstractC0633u p() {
        return new C0580s8(false, this.a);
    }

    @Override // rikka.shizuku.AbstractC0633u
    public AbstractC0633u q() {
        return new M8(false, this.a);
    }

    public final String toString() {
        return e();
    }

    public AbstractC0079c(byte[] bArr, int i) {
        if (bArr == null) {
            throw new NullPointerException("'data' cannot be null");
        }
        if (bArr.length == 0 && i != 0) {
            throw new IllegalArgumentException("zero length data with non-zero pad bits");
        }
        if (i > 7 || i < 0) {
            throw new IllegalArgumentException("pad bits cannot be greater than 7 or less than 0");
        }
        int length = bArr.length;
        byte[] bArr2 = new byte[length + 1];
        System.arraycopy(bArr, 0, bArr2, 1, length);
        bArr2[0] = (byte) i;
        this.a = bArr2;
    }

    @Override // rikka.shizuku.InterfaceC0126df
    public final AbstractC0633u a() {
        return this;
    }
}
