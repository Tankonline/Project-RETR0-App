package rikka.shizuku;

import java.util.Arrays;

/* JADX INFO: renamed from: rikka.shizuku.vo, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0689vo implements Cloneable {
    public /* synthetic */ int[] a;
    public /* synthetic */ Object[] b;
    public /* synthetic */ int c;

    public C0689vo() {
        int i;
        int i2 = 4;
        while (true) {
            i = 40;
            if (i2 >= 32) {
                break;
            }
            int i3 = (1 << i2) - 12;
            if (40 <= i3) {
                i = i3;
                break;
            }
            i2++;
        }
        int i4 = i / 4;
        this.a = new int[i4];
        this.b = new Object[i4];
    }

    public final void a(int i, Object obj) {
        int i2 = this.c;
        if (i2 == 0 || i > this.a[i2 - 1]) {
            if (i2 >= this.a.length) {
                int i3 = (i2 + 1) * 4;
                for (int i4 = 4; i4 < 32; i4++) {
                    int i5 = (1 << i4) - 12;
                    if (i3 <= i5) {
                        i3 = i5;
                        break;
                    }
                }
                int i6 = i3 / 4;
                this.a = Arrays.copyOf(this.a, i6);
                this.b = Arrays.copyOf(this.b, i6);
            }
            this.a[i2] = i;
            this.b[i2] = obj;
            this.c = i2 + 1;
            return;
        }
        int iD = AbstractC0744xh.d(this.c, i, this.a);
        if (iD >= 0) {
            this.b[iD] = obj;
            return;
        }
        int i7 = ~iD;
        int i8 = this.c;
        if (i7 < i8) {
            Object[] objArr = this.b;
            if (objArr[i7] == AbstractC0744xh.i) {
                this.a[i7] = i;
                objArr[i7] = obj;
                return;
            }
        }
        if (i8 >= this.a.length) {
            int i9 = (i8 + 1) * 4;
            for (int i10 = 4; i10 < 32; i10++) {
                int i11 = (1 << i10) - 12;
                if (i9 <= i11) {
                    i9 = i11;
                    break;
                }
            }
            int i12 = i9 / 4;
            this.a = Arrays.copyOf(this.a, i12);
            this.b = Arrays.copyOf(this.b, i12);
        }
        int i13 = this.c;
        if (i13 - i7 != 0) {
            int[] iArr = this.a;
            int i14 = i7 + 1;
            AbstractC0484p4.e0(i14, i7, i13, iArr, iArr);
            Object[] objArr2 = this.b;
            AbstractC0484p4.f0(objArr2, objArr2, i14, i7, this.c);
        }
        this.a[i7] = i;
        this.b[i7] = obj;
        this.c++;
    }

    public final Object clone() {
        C0689vo c0689vo = (C0689vo) super.clone();
        c0689vo.a = (int[]) this.a.clone();
        c0689vo.b = (Object[]) this.b.clone();
        return c0689vo;
    }

    public final String toString() {
        int i = this.c;
        if (i <= 0) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(i * 28);
        sb.append('{');
        int i2 = this.c;
        for (int i3 = 0; i3 < i2; i3++) {
            if (i3 > 0) {
                sb.append(", ");
            }
            sb.append(this.a[i3]);
            sb.append('=');
            Object obj = this.b[i3];
            if (obj != this) {
                sb.append(obj);
            } else {
                sb.append("(this Map)");
            }
        }
        sb.append('}');
        return sb.toString();
    }
}
