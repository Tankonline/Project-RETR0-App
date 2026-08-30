package rikka.shizuku;

import java.util.Arrays;

/* JADX INFO: renamed from: rikka.shizuku.m1, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0388m1 {
    public final int a;
    public final int b;
    public final int c;
    public final int d;
    public final int e;
    public final int f;
    public final byte[] g;

    public C0388m1(int i, int i2, int i3, int i4, int i5, int i6, byte[] bArr) {
        this.a = i;
        this.b = i2;
        this.c = i3;
        this.d = i4;
        this.e = i5;
        this.f = i6;
        this.g = bArr;
    }

    public final String a() {
        String strValueOf;
        int i = this.a;
        switch (i) {
            case 1129208147:
                strValueOf = "A_SYNC";
                break;
            case 1163086915:
                strValueOf = "A_CLSE";
                break;
            case 1163154007:
                strValueOf = "A_WRTE";
                break;
            case 1213486401:
                strValueOf = "A_AUTH";
                break;
            case 1313165391:
                strValueOf = "A_OPEN";
                break;
            case 1314410051:
                strValueOf = "A_CNXN";
                break;
            case 1397511251:
                strValueOf = "A_STLS";
                break;
            case 1497451343:
                strValueOf = "A_OKAY";
                break;
            default:
                strValueOf = String.valueOf(i);
                break;
        }
        byte[] bArr = this.g;
        return "command=" + strValueOf + ", arg0=" + this.b + ", arg1=" + this.c + ", data_length=" + this.d + ", data_crc32=" + this.e + ", magic=" + this.f + ", data=" + (bArr != null ? Arrays.toString(bArr) : null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!C0388m1.class.equals(obj != null ? obj.getClass() : null)) {
            return false;
        }
        C0388m1 c0388m1 = (C0388m1) obj;
        if (this.a != c0388m1.a || this.b != c0388m1.b || this.c != c0388m1.c || this.d != c0388m1.d || this.e != c0388m1.e || this.f != c0388m1.f) {
            return false;
        }
        byte[] bArr = this.g;
        if (bArr != null) {
            byte[] bArr2 = c0388m1.g;
            if (bArr2 == null || !Arrays.equals(bArr, bArr2)) {
                return false;
            }
        } else if (c0388m1.g != null) {
            return false;
        }
        return true;
    }

    public final int hashCode() {
        int i = ((((((((((this.a * 31) + this.b) * 31) + this.c) * 31) + this.d) * 31) + this.e) * 31) + this.f) * 31;
        byte[] bArr = this.g;
        return i + (bArr != null ? Arrays.hashCode(bArr) : 0);
    }

    public final String toString() {
        return AbstractC0535qo.d("AdbMessage(", a(), ")");
    }

    public C0388m1(int i, int i2, int i3, byte[] bArr) {
        this(i, i2, i3, bArr != null ? bArr.length : 0, Se.a(bArr), (int) (((long) i) ^ 4294967295L), bArr);
    }
}
