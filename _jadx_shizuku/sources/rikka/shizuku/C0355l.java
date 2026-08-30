package rikka.shizuku;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;
import rikka.html.text.style.BulletSpan;

/* JADX INFO: renamed from: rikka.shizuku.l, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0355l extends FilterInputStream {
    public final int a;
    public final boolean b;
    public final byte[][] c;

    public C0355l(ByteArrayInputStream byteArrayInputStream, int i, boolean z) {
        this(byteArrayInputStream, i, z, new byte[11][]);
    }

    public static AbstractC0633u b(int i, I9 i9, byte[][] bArr) throws IOException {
        int i2 = 0;
        switch (i) {
            case 1:
                return C0140e.r(c(i9, bArr));
            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                return new C0386m(i9.b());
            case 3:
                return AbstractC0079c.r(i9.b());
            case 4:
                return new A8(i9.b());
            case BulletSpan.STANDARD_BULLET_RADIUS /* 5 */:
                if (i9.b().length == 0) {
                    return C0766y8.a;
                }
                throw new IllegalStateException("malformed NULL encoding encountered");
            case 6:
                byte[] bArrC = c(i9, bArr);
                ConcurrentHashMap concurrentHashMap = C0510q.c;
                C0510q c0510q = (C0510q) C0510q.c.get(new C0479p(bArrC));
                return c0510q == null ? new C0510q(true, bArrC) : c0510q;
            case 7:
                return new C0448o(new C0704w8(i9.b()));
            case 8:
            case 9:
            case 11:
            case 14:
            case 15:
            case 16:
            case 17:
            case 29:
            default:
                throw new IOException("unknown tag " + i + " encountered");
            case 10:
                byte[] bArrC2 = c(i9, bArr);
                if (bArrC2.length > 1) {
                    return new C0264i(true, bArrC2);
                }
                if (bArrC2.length == 0) {
                    throw new IllegalArgumentException("ENUMERATED has zero length");
                }
                int i3 = bArrC2[0] & 255;
                if (i3 >= 12) {
                    return new C0264i(true, bArrC2);
                }
                C0264i[] c0264iArr = C0264i.b;
                C0264i c0264i = c0264iArr[i3];
                if (c0264i == null) {
                    c0264i = new C0264i(true, bArrC2);
                    c0264iArr[i3] = c0264i;
                }
                return c0264i;
            case 12:
                return new H8(i9.b());
            case 13:
                return new C0664v(i9.b());
            case 18:
                return new C0797z8(i9.b());
            case 19:
                return new C8(i9.b());
            case 20:
                return new F8(i9.b());
            case 21:
                return new J8(i9.b());
            case 22:
                return new C0735x8(i9.b());
            case 23:
                return new B(i9.b());
            case 24:
                return new C0324k(i9.b());
            case 25:
                return new C0704w8(i9.b());
            case 26:
                return new K8(i9.b());
            case 27:
                return new C0642u8(i9.b());
            case 28:
                return new I8(i9.b());
            case 30:
                int i4 = i9.d;
                if ((i4 & 1) != 0) {
                    throw new IOException("malformed BMPString encoding encountered");
                }
                int i5 = i4 / 2;
                char[] cArr = new char[i5];
                byte[] bArr2 = new byte[8];
                int i6 = 0;
                while (i4 >= 8) {
                    if (Hn.l(i9, bArr2, 8) != 8) {
                        throw new EOFException("EOF encountered in middle of BMPString");
                    }
                    cArr[i6] = (char) ((bArr2[0] << 8) | (bArr2[1] & 255));
                    cArr[i6 + 1] = (char) ((bArr2[2] << 8) | (bArr2[3] & 255));
                    cArr[i6 + 2] = (char) ((bArr2[4] << 8) | (bArr2[5] & 255));
                    cArr[i6 + 3] = (char) ((bArr2[6] << 8) | (bArr2[7] & 255));
                    i6 += 4;
                    i4 -= 8;
                }
                if (i4 > 0) {
                    if (Hn.l(i9, bArr2, i4) != i4) {
                        throw new EOFException("EOF encountered in middle of BMPString");
                    }
                    do {
                        int i7 = i2 + 1;
                        int i8 = bArr2[i2] << 8;
                        i2 += 2;
                        cArr[i6] = (char) ((bArr2[i7] & 255) | i8);
                        i6++;
                    } while (i2 < i4);
                }
                if (i9.d == 0 && i5 == i6) {
                    return new C0549r8(cArr);
                }
                throw new IllegalStateException();
        }
    }

    public static byte[] c(I9 i9, byte[][] bArr) throws IOException {
        int i = i9.d;
        if (i >= bArr.length) {
            return i9.b();
        }
        byte[] bArr2 = bArr[i];
        if (bArr2 == null) {
            bArr2 = new byte[i];
            bArr[i] = bArr2;
        }
        if (i != bArr2.length) {
            throw new IllegalArgumentException("buffer length not right for data");
        }
        if (i == 0) {
            return bArr2;
        }
        int i2 = i9.b;
        if (i >= i2) {
            throw new IOException("corrupted stream - out of bounds length found: " + i9.d + " >= " + i2);
        }
        int iL = i - Hn.l(i9.a, bArr2, bArr2.length);
        i9.d = iL;
        if (iL == 0) {
            i9.a();
            return bArr2;
        }
        throw new EOFException("DEF length " + i9.c + " object truncated by " + i9.d);
    }

    public static int d(InputStream inputStream, int i, boolean z) {
        int i2 = inputStream.read();
        if ((i2 >>> 7) == 0) {
            return i2;
        }
        if (128 == i2) {
            return -1;
        }
        if (i2 < 0) {
            throw new EOFException("EOF found when length expected");
        }
        if (255 == i2) {
            throw new IOException("invalid long form definite-length 0xFF");
        }
        int i3 = i2 & 127;
        int i4 = 0;
        int i5 = 0;
        do {
            int i6 = inputStream.read();
            if (i6 < 0) {
                throw new EOFException("EOF found reading length");
            }
            if ((i4 >>> 23) != 0) {
                throw new IOException("long form definite-length more than 31 bits");
            }
            i4 = (i4 << 8) + i6;
            i5++;
        } while (i5 < i3);
        if (i4 < i || z) {
            return i4;
        }
        throw new IOException("corrupted stream - out of bounds length found: " + i4 + " >= " + i);
    }

    public static int f(InputStream inputStream, int i) {
        int i2 = i & 31;
        if (i2 != 31) {
            return i2;
        }
        int i3 = inputStream.read();
        if (i3 < 31) {
            if (i3 < 0) {
                throw new EOFException("EOF found inside tag value.");
            }
            throw new IOException("corrupted stream - high tag number < 31 found");
        }
        if ((i3 & 127) == 0) {
            throw new IOException("corrupted stream - invalid high tag number found");
        }
        int i4 = 0;
        while ((i3 & 128) != 0) {
            if ((i4 >>> 24) != 0) {
                throw new IOException("Tag number more than 31 bits");
            }
            i4 = ((i3 & 127) | i4) << 7;
            i3 = inputStream.read();
            if (i3 < 0) {
                throw new EOFException("EOF found inside tag value.");
            }
        }
        return (i3 & 127) | i4;
    }

    public final AbstractC0633u a(int i, int i2, int i3) throws IOException {
        I9 i9 = new I9(this, i3, this.a);
        if ((i & 224) == 0) {
            return b(i2, i9, this.c);
        }
        int i4 = i & 192;
        int i5 = 4;
        if (i4 != 0) {
            if ((i & 32) != 0) {
                return H4.r(i4, i2, h(i9));
            }
            H4 h4 = new H4(i5, i4, i2, new A8(i9.b()), 2);
            return i4 != 64 ? h4 : new L8(h4);
        }
        int i6 = 0;
        if (i2 == 3) {
            C0233h c0233hH = h(i9);
            int i7 = c0233hH.b;
            AbstractC0079c[] abstractC0079cArr = new AbstractC0079c[i7];
            while (i6 != i7) {
                InterfaceC0202g interfaceC0202gF = c0233hH.f(i6);
                if (!(interfaceC0202gF instanceof AbstractC0079c)) {
                    throw new C0293j("unknown object encountered in constructed BIT STRING: " + interfaceC0202gF.getClass());
                }
                abstractC0079cArr[i6] = (AbstractC0079c) interfaceC0202gF;
                i6++;
            }
            return new C0793z4(abstractC0079cArr);
        }
        if (i2 == 4) {
            C0233h c0233hH2 = h(i9);
            int i8 = c0233hH2.b;
            r[] rVarArr = new r[i8];
            while (i6 != i8) {
                InterfaceC0202g interfaceC0202gF2 = c0233hH2.f(i6);
                if (!(interfaceC0202gF2 instanceof r)) {
                    throw new C0293j("unknown object encountered in constructed OCTET STRING: " + interfaceC0202gF2.getClass());
                }
                rVarArr[i6] = (r) interfaceC0202gF2;
                i6++;
            }
            return new C4(C4.s(rVarArr), rVarArr);
        }
        if (i2 == 8) {
            Q8 q8A = O8.a(h(i9));
            q8A.getClass();
            return new C0611t8(q8A);
        }
        if (i2 == 16) {
            if (i9.d < 1) {
                return O8.a;
            }
            if (!this.b) {
                return O8.a(h(i9));
            }
            byte[] bArrB = i9.b();
            C0372lg c0372lg = new C0372lg();
            c0372lg.c = bArrB;
            return c0372lg;
        }
        if (i2 != 17) {
            throw new IOException("unknown tag " + i2 + " encountered");
        }
        C0233h c0233hH3 = h(i9);
        Q8 q8 = O8.a;
        if (c0233hH3.b < 1) {
            return O8.b;
        }
        R8 r8 = new R8(c0233hH3);
        r8.c = -1;
        return r8;
    }

    public final AbstractC0633u e() {
        int i = read();
        if (i <= 0) {
            if (i != 0) {
                return null;
            }
            throw new IOException("unexpected end-of-contents marker");
        }
        int iF = f(this, i);
        int i2 = this.a;
        int iD = d(this, i2, false);
        if (iD >= 0) {
            try {
                return a(i, iF, iD);
            } catch (IllegalArgumentException e) {
                throw new C0293j("corrupted stream detected", e);
            }
        }
        if ((i & 32) == 0) {
            throw new IOException("indefinite-length primitive encoding encountered");
        }
        C0788z c0788z = new C0788z(new C0249hf(this, i2), i2, this.c);
        int i3 = i & 192;
        if (i3 != 0) {
            return c0788z.e(i3, iF);
        }
        if (iF == 3) {
            return A4.e(c0788z);
        }
        if (iF == 4) {
            return D4.e(c0788z);
        }
        if (iF == 8) {
            return F4.e(c0788z);
        }
        if (iF == 16) {
            return new E4(c0788z.f());
        }
        if (iF == 17) {
            return new G4(c0788z.f());
        }
        throw new IOException("unknown BER object encountered");
    }

    public final C0233h g() {
        AbstractC0633u abstractC0633uE = e();
        if (abstractC0633uE == null) {
            return new C0233h(0);
        }
        C0233h c0233h = new C0233h();
        do {
            c0233h.d(abstractC0633uE);
            abstractC0633uE = e();
        } while (abstractC0633uE != null);
        return c0233h;
    }

    public final C0233h h(I9 i9) {
        int i = i9.d;
        return i < 1 ? new C0233h(0) : new C0355l(i9, i, this.b, this.c).g();
    }

    public C0355l(InputStream inputStream, int i, boolean z, byte[][] bArr) {
        super(inputStream);
        this.a = i;
        this.b = z;
        this.c = bArr;
    }

    public C0355l(byte[] bArr) {
        this(new ByteArrayInputStream(bArr), bArr.length, true);
    }
}
