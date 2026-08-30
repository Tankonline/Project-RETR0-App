package rikka.shizuku;

import java.io.IOException;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public final class H8 extends AbstractC0633u implements A {
    public final byte[] a;

    public H8(byte[] bArr) {
        this.a = bArr;
    }

    @Override // rikka.shizuku.A
    public final String e() {
        int i = Wo.a;
        byte[] bArr = this.a;
        int length = bArr.length;
        char[] cArr = new char[length];
        short[] sArr = Br.a;
        int i2 = 0;
        int i3 = 0;
        loop0: while (i2 < bArr.length) {
            int i4 = i2 + 1;
            byte b = bArr[i2];
            if (b < 0) {
                short s = Br.a[b & 127];
                int i5 = s >>> 8;
                byte b2 = (byte) s;
                while (true) {
                    if (b2 >= 0) {
                        if (i4 < bArr.length) {
                            int i6 = i4 + 1;
                            byte b3 = bArr[i4];
                            i5 = (i5 << 6) | (b3 & 63);
                            b2 = Br.b[b2 + ((b3 & 255) >>> 4)];
                            i4 = i6;
                        }
                    } else if (b2 != -2) {
                        if (i5 <= 65535) {
                            if (i3 < length) {
                                cArr[i3] = (char) i5;
                                i3++;
                                i2 = i4;
                            }
                        } else if (i3 < length - 1) {
                            int i7 = i3 + 1;
                            cArr[i3] = (char) ((i5 >>> 10) + 55232);
                            i3 += 2;
                            cArr[i7] = (char) ((i5 & 1023) | 56320);
                            i2 = i4;
                        }
                    }
                    i3 = -1;
                    break;
                }
            }
            if (i3 >= length) {
                i3 = -1;
                break;
            }
            cArr[i3] = (char) b;
            i2 = i4;
            i3++;
        }
        if (i3 >= 0) {
            return new String(cArr, 0, i3);
        }
        throw new IllegalArgumentException("Invalid UTF-8 input");
    }

    @Override // rikka.shizuku.AbstractC0633u, rikka.shizuku.AbstractC0417n
    public final int hashCode() {
        return AbstractC0046as.D(this.a);
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final boolean i(AbstractC0633u abstractC0633u) {
        if (!(abstractC0633u instanceof H8)) {
            return false;
        }
        return Arrays.equals(this.a, ((H8) abstractC0633u).a);
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final void j(Ys ys, boolean z) throws IOException {
        ys.H(z, 12, this.a);
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final boolean k() {
        return false;
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final int m(boolean z) {
        return Ys.v(this.a.length, z);
    }

    public final String toString() {
        return e();
    }
}
