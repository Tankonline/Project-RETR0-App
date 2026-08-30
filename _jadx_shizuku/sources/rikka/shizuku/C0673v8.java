package rikka.shizuku;

import java.io.IOException;

/* JADX INFO: renamed from: rikka.shizuku.v8, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0673v8 extends C0324k {
    public final byte[] A() {
        byte[] bArr = this.a;
        if (bArr[bArr.length - 1] == 90) {
            if (!w()) {
                byte[] bArr2 = new byte[bArr.length + 4];
                System.arraycopy(bArr, 0, bArr2, 0, bArr.length - 1);
                System.arraycopy(Wo.b("0000Z"), 0, bArr2, bArr.length - 1, 5);
                return bArr2;
            }
            if (!x()) {
                byte[] bArr3 = new byte[bArr.length + 2];
                System.arraycopy(bArr, 0, bArr3, 0, bArr.length - 1);
                System.arraycopy(Wo.b("00Z"), 0, bArr3, bArr.length - 1, 3);
                return bArr3;
            }
            if (v()) {
                int length = bArr.length - 2;
                while (length > 0 && bArr[length] == 48) {
                    length--;
                }
                if (bArr[length] == 46) {
                    byte[] bArr4 = new byte[length + 1];
                    System.arraycopy(bArr, 0, bArr4, 0, length);
                    bArr4[length] = 90;
                    return bArr4;
                }
                byte[] bArr5 = new byte[length + 2];
                int i = length + 1;
                System.arraycopy(bArr, 0, bArr5, 0, i);
                bArr5[i] = 90;
                return bArr5;
            }
        }
        return bArr;
    }

    @Override // rikka.shizuku.C0324k, rikka.shizuku.AbstractC0633u
    public final void j(Ys ys, boolean z) throws IOException {
        ys.H(z, 24, A());
    }

    @Override // rikka.shizuku.C0324k, rikka.shizuku.AbstractC0633u
    public final int m(boolean z) {
        return Ys.v(A().length, z);
    }

    @Override // rikka.shizuku.C0324k, rikka.shizuku.AbstractC0633u
    public final AbstractC0633u p() {
        return this;
    }

    @Override // rikka.shizuku.C0324k, rikka.shizuku.AbstractC0633u
    public final AbstractC0633u q() {
        return this;
    }
}
