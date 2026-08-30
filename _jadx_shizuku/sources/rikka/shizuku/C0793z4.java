package rikka.shizuku;

import java.io.IOException;

/* JADX INFO: renamed from: rikka.shizuku.z4, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0793z4 extends AbstractC0079c {
    public final int d;
    public final AbstractC0079c[] e;

    public C0793z4(byte[] bArr, int i) {
        super(bArr, i);
        this.e = null;
        this.d = 1000;
    }

    public static byte[] t(AbstractC0079c[] abstractC0079cArr) {
        int length = abstractC0079cArr.length;
        if (length == 0) {
            return new byte[]{0};
        }
        if (length == 1) {
            return abstractC0079cArr[0].a;
        }
        int i = length - 1;
        int length2 = 0;
        for (int i2 = 0; i2 < i; i2++) {
            byte[] bArr = abstractC0079cArr[i2].a;
            if (bArr[0] != 0) {
                throw new IllegalArgumentException("only the last nested bitstring can have padding");
            }
            length2 += bArr.length - 1;
        }
        byte[] bArr2 = abstractC0079cArr[i].a;
        byte b = bArr2[0];
        byte[] bArr3 = new byte[length2 + bArr2.length];
        bArr3[0] = b;
        int i3 = 1;
        for (AbstractC0079c abstractC0079c : abstractC0079cArr) {
            byte[] bArr4 = abstractC0079c.a;
            int length3 = bArr4.length - 1;
            System.arraycopy(bArr4, 1, bArr3, i3, length3);
            i3 += length3;
        }
        return bArr3;
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final void j(Ys ys, boolean z) throws IOException {
        boolean zK = k();
        byte[] bArr = this.a;
        if (!zK) {
            int length = bArr.length;
            ys.K(3, z);
            ys.F(length);
            ys.E(bArr, 0, length);
            return;
        }
        ys.K(35, z);
        ys.D(128);
        AbstractC0079c[] abstractC0079cArr = this.e;
        if (abstractC0079cArr != null) {
            ys.M(abstractC0079cArr);
        } else if (bArr.length >= 2) {
            byte b = bArr[0];
            int length2 = bArr.length;
            int i = length2 - 1;
            int i2 = this.d;
            int i3 = i2 - 1;
            while (i > i3) {
                ys.D(3);
                ys.F(i2);
                ys.D(0);
                ys.E(bArr, length2 - i, i3);
                i -= i3;
            }
            ys.D(3);
            ys.F(i + 1);
            ys.D(b);
            ys.E(bArr, length2 - i, i);
        }
        ys.D(0);
        ys.D(0);
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final boolean k() {
        return this.e != null || this.a.length > this.d;
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final int m(boolean z) {
        boolean zK = k();
        byte[] bArr = this.a;
        if (!zK) {
            return Ys.v(bArr.length, z);
        }
        int iM = z ? 4 : 3;
        AbstractC0079c[] abstractC0079cArr = this.e;
        if (abstractC0079cArr != null) {
            for (AbstractC0079c abstractC0079c : abstractC0079cArr) {
                iM += abstractC0079c.m(true);
            }
            return iM;
        }
        if (bArr.length < 2) {
            return iM;
        }
        int length = bArr.length - 2;
        int i = this.d;
        int i2 = i - 1;
        int i3 = length / i2;
        return Ys.v(bArr.length - (i2 * i3), true) + (Ys.v(i, true) * i3) + iM;
    }

    public C0793z4(AbstractC0079c[] abstractC0079cArr) {
        super(false, t(abstractC0079cArr));
        this.e = abstractC0079cArr;
        this.d = 1000;
    }
}
