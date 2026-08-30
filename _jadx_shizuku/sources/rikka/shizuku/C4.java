package rikka.shizuku;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public final class C4 extends r {
    public final int d;
    public final r[] e;

    public C4(byte[] bArr, r[] rVarArr) {
        super(bArr);
        this.e = rVarArr;
        this.d = 1000;
    }

    public static byte[] s(r[] rVarArr) {
        int length = rVarArr.length;
        if (length == 0) {
            return r.c;
        }
        if (length == 1) {
            return rVarArr[0].a;
        }
        int length2 = 0;
        for (r rVar : rVarArr) {
            length2 += rVar.a.length;
        }
        byte[] bArr = new byte[length2];
        int length3 = 0;
        for (r rVar2 : rVarArr) {
            byte[] bArr2 = rVar2.a;
            System.arraycopy(bArr2, 0, bArr, length3, bArr2.length);
            length3 += bArr2.length;
        }
        return bArr;
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final void j(Ys ys, boolean z) throws IOException {
        if (!k()) {
            byte[] bArr = this.a;
            int length = bArr.length;
            ys.K(4, z);
            ys.F(length);
            ys.E(bArr, 0, length);
            return;
        }
        ys.K(36, z);
        ys.D(128);
        r[] rVarArr = this.e;
        if (rVarArr == null) {
            int i = 0;
            while (true) {
                byte[] bArr2 = this.a;
                if (i >= bArr2.length) {
                    break;
                }
                int iMin = Math.min(bArr2.length - i, this.d);
                byte[] bArr3 = this.a;
                ys.K(4, true);
                ys.F(iMin);
                ys.E(bArr3, i, iMin);
                i += iMin;
            }
        } else {
            ys.M(rVarArr);
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
        r[] rVarArr = this.e;
        if (rVarArr != null) {
            for (r rVar : rVarArr) {
                iM += rVar.m(true);
            }
            return iM;
        }
        int length = bArr.length;
        int i = this.d;
        int i2 = length / i;
        int iV = (Ys.v(i, true) * i2) + iM;
        int length2 = bArr.length - (i2 * i);
        return length2 > 0 ? Ys.v(length2, true) + iV : iV;
    }
}
