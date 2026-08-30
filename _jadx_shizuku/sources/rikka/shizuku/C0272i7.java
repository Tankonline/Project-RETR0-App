package rikka.shizuku;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import rikka.html.text.style.BulletSpan;

/* JADX INFO: renamed from: rikka.shizuku.i7, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0272i7 {
    public static final SparseIntArray j0;
    public int A;
    public int B;
    public int C;
    public int D;
    public int E;
    public int F;
    public int G;
    public int H;
    public int I;
    public int J;
    public int K;
    public int L;
    public int M;
    public int N;
    public float O;
    public float P;
    public int Q;
    public int R;
    public int S;
    public int T;
    public int U;
    public int V;
    public int W;
    public int X;
    public float Y;
    public float Z;
    public boolean a;
    public int a0;
    public int b;
    public int b0;
    public int c;
    public int c0;
    public int d;
    public int[] d0;
    public int e;
    public String e0;
    public float f;
    public String f0;
    public int g;
    public boolean g0;
    public int h;
    public boolean h0;
    public int i;
    public boolean i0;
    public int j;
    public int k;
    public int l;
    public int m;
    public int n;
    public int o;
    public int p;
    public int q;
    public int r;
    public int s;
    public float t;
    public float u;
    public String v;
    public int w;
    public int x;
    public float y;
    public int z;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        j0 = sparseIntArray;
        sparseIntArray.append(38, 24);
        sparseIntArray.append(39, 25);
        sparseIntArray.append(41, 28);
        sparseIntArray.append(42, 29);
        sparseIntArray.append(47, 35);
        sparseIntArray.append(46, 34);
        sparseIntArray.append(20, 4);
        sparseIntArray.append(19, 3);
        sparseIntArray.append(17, 1);
        sparseIntArray.append(55, 6);
        sparseIntArray.append(56, 7);
        sparseIntArray.append(27, 17);
        sparseIntArray.append(28, 18);
        sparseIntArray.append(29, 19);
        sparseIntArray.append(0, 26);
        sparseIntArray.append(43, 31);
        sparseIntArray.append(44, 32);
        sparseIntArray.append(26, 10);
        sparseIntArray.append(25, 9);
        sparseIntArray.append(59, 13);
        sparseIntArray.append(62, 16);
        sparseIntArray.append(60, 14);
        sparseIntArray.append(57, 11);
        sparseIntArray.append(61, 15);
        sparseIntArray.append(58, 12);
        sparseIntArray.append(50, 38);
        sparseIntArray.append(36, 37);
        sparseIntArray.append(35, 39);
        sparseIntArray.append(49, 40);
        sparseIntArray.append(34, 20);
        sparseIntArray.append(48, 36);
        sparseIntArray.append(24, 5);
        sparseIntArray.append(37, 76);
        sparseIntArray.append(45, 76);
        sparseIntArray.append(40, 76);
        sparseIntArray.append(18, 76);
        sparseIntArray.append(16, 76);
        sparseIntArray.append(3, 23);
        sparseIntArray.append(5, 27);
        sparseIntArray.append(7, 30);
        sparseIntArray.append(8, 8);
        sparseIntArray.append(4, 33);
        sparseIntArray.append(6, 2);
        sparseIntArray.append(1, 22);
        sparseIntArray.append(2, 21);
        sparseIntArray.append(21, 61);
        sparseIntArray.append(23, 62);
        sparseIntArray.append(22, 63);
        sparseIntArray.append(54, 69);
        sparseIntArray.append(33, 70);
        sparseIntArray.append(12, 71);
        sparseIntArray.append(10, 72);
        sparseIntArray.append(11, 73);
        sparseIntArray.append(13, 74);
        sparseIntArray.append(9, 75);
    }

    public final void a(Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Fk.d);
        int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = typedArrayObtainStyledAttributes.getIndex(i);
            SparseIntArray sparseIntArray = j0;
            int i2 = sparseIntArray.get(index);
            if (i2 == 80) {
                this.g0 = typedArrayObtainStyledAttributes.getBoolean(index, this.g0);
            } else if (i2 != 81) {
                switch (i2) {
                    case 1:
                        this.o = C0394m7.f(typedArrayObtainStyledAttributes, index, this.o);
                        break;
                    case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                        this.F = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.F);
                        break;
                    case 3:
                        this.n = C0394m7.f(typedArrayObtainStyledAttributes, index, this.n);
                        break;
                    case 4:
                        this.m = C0394m7.f(typedArrayObtainStyledAttributes, index, this.m);
                        break;
                    case BulletSpan.STANDARD_BULLET_RADIUS /* 5 */:
                        this.v = typedArrayObtainStyledAttributes.getString(index);
                        break;
                    case 6:
                        this.z = typedArrayObtainStyledAttributes.getDimensionPixelOffset(index, this.z);
                        break;
                    case 7:
                        this.A = typedArrayObtainStyledAttributes.getDimensionPixelOffset(index, this.A);
                        break;
                    case 8:
                        this.G = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.G);
                        break;
                    case 9:
                        this.s = C0394m7.f(typedArrayObtainStyledAttributes, index, this.s);
                        break;
                    case 10:
                        this.r = C0394m7.f(typedArrayObtainStyledAttributes, index, this.r);
                        break;
                    case 11:
                        this.L = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.L);
                        break;
                    case 12:
                        this.M = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.M);
                        break;
                    case 13:
                        this.I = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.I);
                        break;
                    case 14:
                        this.K = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.K);
                        break;
                    case 15:
                        this.N = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.N);
                        break;
                    case 16:
                        this.J = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.J);
                        break;
                    case 17:
                        this.d = typedArrayObtainStyledAttributes.getDimensionPixelOffset(index, this.d);
                        break;
                    case 18:
                        this.e = typedArrayObtainStyledAttributes.getDimensionPixelOffset(index, this.e);
                        break;
                    case 19:
                        this.f = typedArrayObtainStyledAttributes.getFloat(index, this.f);
                        break;
                    case 20:
                        this.t = typedArrayObtainStyledAttributes.getFloat(index, this.t);
                        break;
                    case 21:
                        this.c = typedArrayObtainStyledAttributes.getLayoutDimension(index, this.c);
                        break;
                    case 22:
                        this.b = typedArrayObtainStyledAttributes.getLayoutDimension(index, this.b);
                        break;
                    case 23:
                        this.C = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.C);
                        break;
                    case 24:
                        this.g = C0394m7.f(typedArrayObtainStyledAttributes, index, this.g);
                        break;
                    case 25:
                        this.h = C0394m7.f(typedArrayObtainStyledAttributes, index, this.h);
                        break;
                    case 26:
                        this.B = typedArrayObtainStyledAttributes.getInt(index, this.B);
                        break;
                    case 27:
                        this.D = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.D);
                        break;
                    case 28:
                        this.i = C0394m7.f(typedArrayObtainStyledAttributes, index, this.i);
                        break;
                    case 29:
                        this.j = C0394m7.f(typedArrayObtainStyledAttributes, index, this.j);
                        break;
                    case 30:
                        this.H = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.H);
                        break;
                    case 31:
                        this.p = C0394m7.f(typedArrayObtainStyledAttributes, index, this.p);
                        break;
                    case 32:
                        this.q = C0394m7.f(typedArrayObtainStyledAttributes, index, this.q);
                        break;
                    case 33:
                        this.E = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.E);
                        break;
                    case 34:
                        this.l = C0394m7.f(typedArrayObtainStyledAttributes, index, this.l);
                        break;
                    case 35:
                        this.k = C0394m7.f(typedArrayObtainStyledAttributes, index, this.k);
                        break;
                    case 36:
                        this.u = typedArrayObtainStyledAttributes.getFloat(index, this.u);
                        break;
                    case 37:
                        this.P = typedArrayObtainStyledAttributes.getFloat(index, this.P);
                        break;
                    case 38:
                        this.O = typedArrayObtainStyledAttributes.getFloat(index, this.O);
                        break;
                    case 39:
                        this.Q = typedArrayObtainStyledAttributes.getInt(index, this.Q);
                        break;
                    case 40:
                        this.R = typedArrayObtainStyledAttributes.getInt(index, this.R);
                        break;
                    default:
                        switch (i2) {
                            case 54:
                                this.S = typedArrayObtainStyledAttributes.getInt(index, this.S);
                                break;
                            case 55:
                                this.T = typedArrayObtainStyledAttributes.getInt(index, this.T);
                                break;
                            case 56:
                                this.U = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.U);
                                break;
                            case 57:
                                this.V = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.V);
                                break;
                            case 58:
                                this.W = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.W);
                                break;
                            case 59:
                                this.X = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.X);
                                break;
                            default:
                                switch (i2) {
                                    case 61:
                                        this.w = C0394m7.f(typedArrayObtainStyledAttributes, index, this.w);
                                        break;
                                    case 62:
                                        this.x = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.x);
                                        break;
                                    case 63:
                                        this.y = typedArrayObtainStyledAttributes.getFloat(index, this.y);
                                        break;
                                    default:
                                        switch (i2) {
                                            case 69:
                                                this.Y = typedArrayObtainStyledAttributes.getFloat(index, 1.0f);
                                                break;
                                            case 70:
                                                this.Z = typedArrayObtainStyledAttributes.getFloat(index, 1.0f);
                                                break;
                                            case 71:
                                                Log.e("ConstraintSet", "CURRENTLY UNSUPPORTED");
                                                break;
                                            case 72:
                                                this.a0 = typedArrayObtainStyledAttributes.getInt(index, this.a0);
                                                break;
                                            case 73:
                                                this.b0 = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.b0);
                                                break;
                                            case 74:
                                                this.e0 = typedArrayObtainStyledAttributes.getString(index);
                                                break;
                                            case 75:
                                                this.i0 = typedArrayObtainStyledAttributes.getBoolean(index, this.i0);
                                                break;
                                            case 76:
                                                Log.w("ConstraintSet", "unused attribute 0x" + Integer.toHexString(index) + "   " + sparseIntArray.get(index));
                                                break;
                                            case 77:
                                                this.f0 = typedArrayObtainStyledAttributes.getString(index);
                                                break;
                                            default:
                                                Log.w("ConstraintSet", "Unknown attribute 0x" + Integer.toHexString(index) + "   " + sparseIntArray.get(index));
                                                break;
                                        }
                                        break;
                                }
                                break;
                        }
                        break;
                }
            } else {
                this.h0 = typedArrayObtainStyledAttributes.getBoolean(index, this.h0);
            }
        }
        typedArrayObtainStyledAttributes.recycle();
    }
}
