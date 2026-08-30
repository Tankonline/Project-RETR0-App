package rikka.shizuku;

import android.graphics.Path;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public final class Pj {
    public char a;
    public final float[] b;

    public Pj(char c, float[] fArr) {
        this.a = c;
        this.b = fArr;
    }

    public static void a(Path path, float f, float f2, float f3, float f4, float f5, float f6, float f7, boolean z, boolean z2) {
        double d;
        double d2;
        double radians = Math.toRadians(f7);
        double dCos = Math.cos(radians);
        double dSin = Math.sin(radians);
        double d3 = f;
        double d4 = f2;
        double d5 = f5;
        double d6 = ((d4 * dSin) + (d3 * dCos)) / d5;
        double d7 = f6;
        double d8 = ((d4 * dCos) + (((double) (-f)) * dSin)) / d7;
        double d9 = f4;
        double d10 = ((d9 * dSin) + (((double) f3) * dCos)) / d5;
        double d11 = ((d9 * dCos) + (((double) (-f3)) * dSin)) / d7;
        double d12 = d6 - d10;
        double d13 = d8 - d11;
        double d14 = (d6 + d10) / 2.0d;
        double d15 = (d8 + d11) / 2.0d;
        double d16 = (d13 * d13) + (d12 * d12);
        if (d16 == 0.0d) {
            Log.w("PathParser", " Points are coincident");
            return;
        }
        double d17 = (1.0d / d16) - 0.25d;
        if (d17 < 0.0d) {
            Log.w("PathParser", "Points are too far apart " + d16);
            float fSqrt = (float) (Math.sqrt(d16) / 1.99999d);
            a(path, f, f2, f3, f4, f5 * fSqrt, fSqrt * f6, f7, z, z2);
            return;
        }
        double dSqrt = Math.sqrt(d17);
        double d18 = dSqrt * d12;
        double d19 = dSqrt * d13;
        if (z == z2) {
            d = d14 - d19;
            d2 = d15 + d18;
        } else {
            d = d14 + d19;
            d2 = d15 - d18;
        }
        double dAtan2 = Math.atan2(d8 - d2, d6 - d);
        double dAtan3 = Math.atan2(d11 - d2, d10 - d) - dAtan2;
        if (z2 != (dAtan3 >= 0.0d)) {
            dAtan3 = dAtan3 > 0.0d ? dAtan3 - 6.283185307179586d : dAtan3 + 6.283185307179586d;
        }
        double d20 = d * d5;
        double d21 = d2 * d7;
        double d22 = (d20 * dCos) - (d21 * dSin);
        double d23 = (d21 * dCos) + (d20 * dSin);
        int iCeil = (int) Math.ceil(Math.abs((dAtan3 * 4.0d) / 3.141592653589793d));
        double dCos2 = Math.cos(radians);
        double dSin2 = Math.sin(radians);
        double dCos3 = Math.cos(dAtan2);
        double dSin3 = Math.sin(dAtan2);
        double d24 = -d5;
        double d25 = d24 * dCos2;
        double d26 = d7 * dSin2;
        double d27 = (d25 * dSin3) - (d26 * dCos3);
        double d28 = d24 * dSin2;
        double d29 = d7 * dCos2;
        double d30 = dAtan3 / ((double) iCeil);
        double d31 = (dCos3 * d29) + (dSin3 * d28);
        double d32 = d3;
        double d33 = d4;
        int i = 0;
        double d34 = dAtan2;
        while (i < iCeil) {
            double d35 = d34 + d30;
            double dSin4 = Math.sin(d35);
            double dCos4 = Math.cos(d35);
            int i2 = iCeil;
            double d36 = (((d5 * dCos2) * dCos4) + d22) - (d26 * dSin4);
            double d37 = (d29 * dSin4) + (d5 * dSin2 * dCos4) + d23;
            double d38 = (d25 * dSin4) - (d26 * dCos4);
            double d39 = (dCos4 * d29) + (dSin4 * d28);
            double d40 = d35 - d34;
            double dTan = Math.tan(d40 / 2.0d);
            double dSqrt2 = ((Math.sqrt(((dTan * 3.0d) * dTan) + 4.0d) - 1.0d) * Math.sin(d40)) / 3.0d;
            path.rLineTo(0.0f, 0.0f);
            path.cubicTo((float) ((d27 * dSqrt2) + d32), (float) ((d31 * dSqrt2) + d33), (float) (d36 - (dSqrt2 * d38)), (float) (d37 - (dSqrt2 * d39)), (float) d36, (float) d37);
            i++;
            d33 = d37;
            dCos2 = dCos2;
            d28 = d28;
            d34 = d35;
            d31 = d39;
            d32 = d36;
            iCeil = i2;
            d27 = d38;
            d30 = d30;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void b(Pj[] pjArr, Path path) {
        int i;
        int i2;
        float f;
        float f2;
        float f3;
        float f4;
        float f5;
        float f6;
        float f7;
        float f8;
        float f9;
        float f10;
        float f11;
        float f12;
        float[] fArr = new float[6];
        int length = pjArr.length;
        char c = 'm';
        int i3 = 0;
        char c2 = 'm';
        int i4 = 0;
        while (i4 < length) {
            Pj pj = pjArr[i4];
            char c3 = pj.a;
            float f13 = fArr[i3];
            float f14 = fArr[1];
            float f15 = fArr[2];
            float f16 = fArr[3];
            float f17 = fArr[4];
            float f18 = fArr[5];
            switch (c3) {
                case 'A':
                case 'a':
                    i = 7;
                    break;
                case 'C':
                case 'c':
                    i = 6;
                    break;
                case 'H':
                case 'V':
                case 'h':
                case 'v':
                    i = 1;
                    break;
                case 'Q':
                case 'S':
                case 'q':
                case 's':
                    i = 4;
                    break;
                case 'Z':
                case 'z':
                    path.close();
                    path.moveTo(f17, f18);
                    f13 = f17;
                    f15 = f13;
                    f14 = f18;
                    f16 = f14;
                default:
                    i = 2;
                    break;
            }
            float f19 = f14;
            float f20 = f17;
            float f21 = f18;
            float f22 = f13;
            int i5 = i3;
            while (true) {
                float[] fArr2 = pj.b;
                if (i5 < fArr2.length) {
                    int i6 = i3;
                    if (c3 == 'A') {
                        i2 = i5;
                        pj = pj;
                        float f23 = f22;
                        float f24 = f19;
                        i4 = i4;
                        c3 = c3;
                        int i7 = i2 + 5;
                        int i8 = i2 + 6;
                        a(path, f23, f24, fArr2[i7], fArr2[i8], fArr2[i2], fArr2[i2 + 1], fArr2[i2 + 2], fArr2[i2 + 3] != 0.0f ? 1 : i6, fArr2[i2 + 4] != 0.0f ? 1 : i6);
                        f15 = fArr2[i7];
                        f = fArr2[i8];
                        f16 = f;
                        f2 = f15;
                    } else if (c3 == 'C') {
                        i2 = i5;
                        i4 = i4;
                        pj = pj;
                        c3 = c3;
                        int i9 = i2 + 2;
                        int i10 = i2 + 3;
                        int i11 = i2 + 4;
                        int i12 = i2 + 5;
                        path.cubicTo(fArr2[i2], fArr2[i2 + 1], fArr2[i9], fArr2[i10], fArr2[i11], fArr2[i12]);
                        float f25 = fArr2[i11];
                        float f26 = fArr2[i12];
                        f15 = fArr2[i9];
                        f16 = fArr2[i10];
                        f = f26;
                        f2 = f25;
                    } else if (c3 != 'H') {
                        if (c3 == 'Q') {
                            i2 = i5;
                            int i13 = i2 + 1;
                            int i14 = i2 + 2;
                            int i15 = i2 + 3;
                            path.quadTo(fArr2[i2], fArr2[i13], fArr2[i14], fArr2[i15]);
                            f3 = fArr2[i2];
                            float f27 = fArr2[i13];
                            f4 = fArr2[i14];
                            f16 = f27;
                            f = fArr2[i15];
                        } else if (c3 == 'V') {
                            i2 = i5;
                            i4 = i4;
                            pj = pj;
                            f2 = f22;
                            c3 = c3;
                            path.lineTo(f2, fArr2[i2]);
                            f = fArr2[i2];
                        } else if (c3 != 'a') {
                            if (c3 == 'c') {
                                i2 = i5;
                                int i16 = i2 + 2;
                                int i17 = i2 + 3;
                                int i18 = i2 + 4;
                                int i19 = i2 + 5;
                                path.rCubicTo(fArr2[i2], fArr2[i2 + 1], fArr2[i16], fArr2[i17], fArr2[i18], fArr2[i19]);
                                float f28 = fArr2[i16] + f22;
                                float f29 = f19 + fArr2[i17];
                                f22 += fArr2[i18];
                                f19 += fArr2[i19];
                                f15 = f28;
                                f16 = f29;
                            } else if (c3 != 'h') {
                                if (c3 != 'q') {
                                    if (c3 != 'v') {
                                        if (c3 != 'L') {
                                            if (c3 == 'M') {
                                                i2 = i5;
                                                f2 = fArr2[i2];
                                                f = fArr2[i2 + 1];
                                                if (i2 > 0) {
                                                    path.lineTo(f2, f);
                                                } else {
                                                    path.moveTo(f2, f);
                                                    f20 = f2;
                                                    f21 = f;
                                                }
                                            } else if (c3 == 'S') {
                                                i2 = i5;
                                                if (c2 == 'c' || c2 == 's' || c2 == 'C' || c2 == 'S') {
                                                    f22 = (f22 * 2.0f) - f15;
                                                    f19 = (f19 * 2.0f) - f16;
                                                }
                                                float f30 = f22;
                                                float f31 = f19;
                                                int i20 = i2 + 1;
                                                int i21 = i2 + 2;
                                                int i22 = i2 + 3;
                                                path.cubicTo(f30, f31, fArr2[i2], fArr2[i20], fArr2[i21], fArr2[i22]);
                                                f3 = fArr2[i2];
                                                float f32 = fArr2[i20];
                                                f4 = fArr2[i21];
                                                f16 = f32;
                                                f = fArr2[i22];
                                            } else if (c3 == 'T') {
                                                i2 = i5;
                                                if (c2 == 'q' || c2 == 't' || c2 == 'Q' || c2 == 'T') {
                                                    f22 = (f22 * 2.0f) - f15;
                                                    f19 = (f19 * 2.0f) - f16;
                                                }
                                                float f33 = f19;
                                                float f34 = fArr2[i2];
                                                int i23 = i2 + 1;
                                                path.quadTo(f22, f33, f34, fArr2[i23]);
                                                f16 = f33;
                                                f2 = fArr2[i2];
                                                f = fArr2[i23];
                                                pj = pj;
                                                f15 = f22;
                                            } else if (c3 == 'l') {
                                                i2 = i5;
                                                int i24 = i2 + 1;
                                                path.rLineTo(fArr2[i2], fArr2[i24]);
                                                f22 += fArr2[i2];
                                                f8 = fArr2[i24];
                                            } else if (c3 != c) {
                                                if (c3 == 's') {
                                                    if (c2 == 'c' || c2 == 's' || c2 == 'C' || c2 == 'S') {
                                                        f9 = f19 - f16;
                                                        f10 = f22 - f15;
                                                    } else {
                                                        f10 = 0.0f;
                                                        f9 = 0.0f;
                                                    }
                                                    int i25 = i5 + 1;
                                                    int i26 = i5 + 2;
                                                    int i27 = i5 + 3;
                                                    i2 = i5;
                                                    path.rCubicTo(f10, f9, fArr2[i5], fArr2[i25], fArr2[i26], fArr2[i27]);
                                                    f5 = fArr2[i2] + f22;
                                                    f6 = f19 + fArr2[i25];
                                                    f22 += fArr2[i26];
                                                    f7 = fArr2[i27];
                                                } else if (c3 != 't') {
                                                    i2 = i5;
                                                } else {
                                                    if (c2 == 'q' || c2 == 't' || c2 == 'Q' || c2 == 'T') {
                                                        f11 = f22 - f15;
                                                        f12 = f19 - f16;
                                                    } else {
                                                        f12 = 0.0f;
                                                        f11 = 0.0f;
                                                    }
                                                    int i28 = i5 + 1;
                                                    path.rQuadTo(f11, f12, fArr2[i5], fArr2[i28]);
                                                    float f35 = f11 + f22;
                                                    float f36 = f19 + f12;
                                                    float f37 = f22 + fArr2[i5];
                                                    f19 += fArr2[i28];
                                                    f16 = f36;
                                                    i2 = i5;
                                                    f2 = f37;
                                                    f15 = f35;
                                                }
                                                f = f19;
                                            } else {
                                                i2 = i5;
                                                float f38 = fArr2[i2];
                                                f22 += f38;
                                                float f39 = fArr2[i2 + 1];
                                                f19 += f39;
                                                if (i2 > 0) {
                                                    path.rLineTo(f38, f39);
                                                } else {
                                                    path.rMoveTo(f38, f39);
                                                    pj = pj;
                                                    f2 = f22;
                                                    f20 = f2;
                                                    f = f19;
                                                    f21 = f;
                                                }
                                            }
                                            c3 = c3;
                                        } else {
                                            i2 = i5;
                                            int i29 = i2 + 1;
                                            path.lineTo(fArr2[i2], fArr2[i29]);
                                            f2 = fArr2[i2];
                                            f = fArr2[i29];
                                        }
                                        pj = pj;
                                        c3 = c3;
                                    } else {
                                        i2 = i5;
                                        path.rLineTo(0.0f, fArr2[i2]);
                                        f8 = fArr2[i2];
                                    }
                                    f19 += f8;
                                } else {
                                    i2 = i5;
                                    int i30 = i2 + 1;
                                    int i31 = i2 + 2;
                                    int i32 = i2 + 3;
                                    path.rQuadTo(fArr2[i2], fArr2[i30], fArr2[i31], fArr2[i32]);
                                    f5 = fArr2[i2] + f22;
                                    f6 = f19 + fArr2[i30];
                                    f22 += fArr2[i31];
                                    f7 = fArr2[i32];
                                }
                                f19 += f7;
                                f15 = f5;
                                f16 = f6;
                            } else {
                                i2 = i5;
                                path.rLineTo(fArr2[i2], 0.0f);
                                f22 += fArr2[i2];
                            }
                            f2 = f22;
                            f = f19;
                            c3 = c3;
                        } else {
                            i2 = i5;
                            int i33 = i2 + 5;
                            float f40 = fArr2[i33] + f22;
                            int i34 = i2 + 6;
                            float f41 = fArr2[i34] + f19;
                            pj = pj;
                            float f42 = f22;
                            c3 = c3;
                            float f43 = f19;
                            i4 = i4;
                            a(path, f42, f43, f40, f41, fArr2[i2], fArr2[i2 + 1], fArr2[i2 + 2], fArr2[i2 + 3] != 0.0f ? 1 : i6, fArr2[i2 + 4] != 0.0f ? 1 : i6);
                            f2 = f42 + fArr2[i33];
                            f = f43 + fArr2[i34];
                            f15 = f2;
                            f16 = f;
                        }
                        f15 = f3;
                        f2 = f4;
                    } else {
                        i2 = i5;
                        pj = pj;
                        c3 = c3;
                        f = f19;
                        i4 = i4;
                        path.lineTo(fArr2[i2], f);
                        f2 = fArr2[i2];
                    }
                    c3 = c3;
                    pj = pj;
                    i4 = i4;
                    i3 = i6;
                    c = 'm';
                    f22 = f2;
                    f19 = f;
                    c2 = c3;
                    i5 = i2 + i;
                    path = path;
                }
            }
            int i35 = i3;
            fArr[i35] = f22;
            fArr[1] = f19;
            fArr[2] = f15;
            fArr[3] = f16;
            fArr[4] = f20;
            fArr[5] = f21;
            c2 = pj.a;
            i4++;
            i3 = i35;
            c = 'm';
        }
    }

    public Pj(Pj pj) {
        this.a = pj.a;
        float[] fArr = pj.b;
        this.b = AbstractC0046as.m(fArr, fArr.length);
    }
}
