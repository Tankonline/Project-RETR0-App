package rikka.shizuku;

import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import rikka.html.text.style.BulletSpan;

/* JADX INFO: loaded from: classes.dex */
public class Vf implements Closeable, AutoCloseable {
    public final InputStreamReader a;
    public long h;
    public int i;
    public String j;
    public int[] k;
    public String[] m;
    public int[] n;
    public int o = 2;
    public final char[] b = new char[1024];
    public int c = 0;
    public int d = 0;
    public int e = 0;
    public int f = 0;
    public int g = 0;
    public int l = 1;

    static {
        Ge.b = new Ge();
    }

    public Vf(InputStreamReader inputStreamReader) {
        int[] iArr = new int[32];
        this.k = iArr;
        iArr[0] = 6;
        this.m = new String[32];
        this.n = new int[32];
        this.a = inputStreamReader;
    }

    public final void A() throws C0775yh {
        do {
            int i = 0;
            while (true) {
                int i2 = this.c;
                if (i2 + i < this.d) {
                    char c = this.b[i2 + i];
                    if (c != '\t' && c != '\n' && c != '\f' && c != '\r' && c != ' ') {
                        if (c != '#') {
                            if (c != ',') {
                                if (c != '/' && c != '=') {
                                    if (c != '{' && c != '}' && c != ':') {
                                        if (c != ';') {
                                            switch (c) {
                                                case '[':
                                                case ']':
                                                    break;
                                                case '\\':
                                                    break;
                                                default:
                                                    i++;
                                                    break;
                                            }
                                            return;
                                        }
                                    }
                                }
                            }
                        }
                        c();
                    }
                    this.c += i;
                    return;
                }
                this.c = i2 + i;
            }
        } while (g(1));
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final void B() throws IOException {
        int i = 0;
        do {
            int iD = this.g;
            if (iD == 0) {
                iD = d();
            }
            switch (iD) {
                case 1:
                    w(3);
                    i++;
                    this.g = 0;
                    break;
                case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                    if (i == 0) {
                        this.m[this.l - 1] = null;
                    }
                    this.l--;
                    i--;
                    this.g = 0;
                    break;
                case 3:
                    w(1);
                    i++;
                    this.g = 0;
                    break;
                case 4:
                    this.l--;
                    i--;
                    this.g = 0;
                    break;
                case BulletSpan.STANDARD_BULLET_RADIUS /* 5 */:
                case 6:
                case 7:
                case 11:
                case 15:
                default:
                    this.g = 0;
                    break;
                case 8:
                    y('\'');
                    this.g = 0;
                    break;
                case 9:
                    y('\"');
                    this.g = 0;
                    break;
                case 10:
                    A();
                    this.g = 0;
                    break;
                case 12:
                    y('\'');
                    if (i == 0) {
                        this.m[this.l - 1] = "<skipped>";
                    }
                    this.g = 0;
                    break;
                case 13:
                    y('\"');
                    if (i == 0) {
                        this.m[this.l - 1] = "<skipped>";
                    }
                    this.g = 0;
                    break;
                case 14:
                    A();
                    if (i == 0) {
                        this.m[this.l - 1] = "<skipped>";
                    }
                    this.g = 0;
                    break;
                case 16:
                    this.c += this.i;
                    this.g = 0;
                    break;
                case 17:
                    break;
            }
            return;
        } while (i > 0);
        int[] iArr = this.n;
        int i2 = this.l - 1;
        iArr[i2] = iArr[i2] + 1;
    }

    public final void C(String str) throws C0775yh {
        throw new C0775yh(str + k() + "\nSee " + "https://github.com/google/gson/blob/main/Troubleshooting.md#".concat("malformed-json"));
    }

    public final IllegalStateException D(String str) {
        String str2 = v() == 9 ? "adapter-not-null-safe" : "unexpected-json-structure";
        StringBuilder sbE = AbstractC0535qo.e("Expected ", str, " but was ");
        sbE.append(AbstractC0535qo.k(v()));
        sbE.append(k());
        sbE.append("\nSee ");
        sbE.append("https://github.com/google/gson/blob/main/Troubleshooting.md#".concat(str2));
        return new IllegalStateException(sbE.toString());
    }

    public final void a() {
        int iD = this.g;
        if (iD == 0) {
            iD = d();
        }
        if (iD != 3) {
            throw D("BEGIN_ARRAY");
        }
        w(1);
        this.n[this.l - 1] = 0;
        this.g = 0;
    }

    public final void b() {
        int iD = this.g;
        if (iD == 0) {
            iD = d();
        }
        if (iD != 1) {
            throw D("BEGIN_OBJECT");
        }
        w(3);
        this.g = 0;
    }

    public final void c() throws C0775yh {
        if (this.o == 1) {
            return;
        }
        C("Use JsonReader.setStrictness(Strictness.LENIENT) to accept malformed JSON");
        throw null;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() throws IOException {
        this.g = 0;
        this.k[0] = 8;
        this.l = 1;
        this.a.close();
    }

    /* JADX WARN: Code duplicated, block: B:101:0x0145  */
    /* JADX WARN: Code duplicated, block: B:104:0x014e  */
    /* JADX WARN: Code duplicated, block: B:112:0x016b  */
    /* JADX WARN: Code duplicated, block: B:114:0x0173  */
    /* JADX WARN: Code duplicated, block: B:119:0x0188 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:120:0x0189  */
    /* JADX WARN: Code duplicated, block: B:123:0x019b  */
    /* JADX WARN: Code duplicated, block: B:126:0x01a1  */
    /* JADX WARN: Code duplicated, block: B:129:0x01ac  */
    /* JADX WARN: Code duplicated, block: B:130:0x01b2 A[PHI: r4 r15
  0x01b2: PHI (r4v10 int) = (r4v9 int), (r4v15 int) binds: [B:122:0x0199, B:129:0x01ac] A[DONT_GENERATE, DONT_INLINE]
  0x01b2: PHI (r15v6 int) = (r15v5 int), (r15v7 int) binds: [B:122:0x0199, B:129:0x01ac] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:132:0x01ba  */
    /* JADX WARN: Code duplicated, block: B:134:0x01be  */
    /* JADX WARN: Code duplicated, block: B:172:0x021d  */
    /* JADX WARN: Code duplicated, block: B:173:0x021f  */
    /* JADX WARN: Code duplicated, block: B:185:0x0240 A[DONT_INVERT, PHI: r8
  0x0240: PHI (r8v22 int) = (r8v21 int), (r8v23 int) binds: [B:171:0x021b, B:177:0x0228] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:186:0x0242  */
    /* JADX WARN: Code duplicated, block: B:199:0x025f  */
    /* JADX WARN: Code duplicated, block: B:201:0x0262  */
    /* JADX WARN: Code duplicated, block: B:204:0x0267 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:208:0x0270 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:209:0x0271  */
    /* JADX WARN: Code duplicated, block: B:211:0x027b  */
    /* JADX WARN: Code duplicated, block: B:213:0x0281  */
    /* JADX WARN: Code duplicated, block: B:215:0x0287  */
    /* JADX WARN: Code duplicated, block: B:217:0x028a A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:218:0x028c  */
    /* JADX WARN: Code duplicated, block: B:220:0x0290  */
    /* JADX WARN: Code duplicated, block: B:230:0x02ab  */
    /* JADX WARN: Code duplicated, block: B:232:0x02b3  */
    /* JADX WARN: Code duplicated, block: B:273:0x019e A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:274:0x019e A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:275:0x01a9 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:285:0x0164 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:66:0x00ea A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:67:0x00ec  */
    /* JADX WARN: Code duplicated, block: B:71:0x00f4 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:72:0x00f6  */
    /* JADX WARN: Code duplicated, block: B:74:0x00fa  */
    /* JADX WARN: Code duplicated, block: B:92:0x012b  */
    /* JADX WARN: Code duplicated, block: B:95:0x0134  */
    /* JADX WARN: Code duplicated, block: B:96:0x0136  */
    /* JADX WARN: Code duplicated, block: B:99:0x013e  */
    public final int d() throws IOException {
        int iQ;
        int i;
        int iQ2;
        char c;
        String str;
        String str2;
        int i2;
        int i3;
        int length;
        int i4;
        char c2;
        int i5;
        int i6;
        int i7;
        int i8;
        boolean z;
        char c3;
        int i9;
        int i10;
        int[] iArr = this.k;
        int i11 = this.l - 1;
        int i12 = iArr[i11];
        char[] cArr = this.b;
        if (i12 == 1) {
            iArr[i11] = 2;
        } else if (i12 == 2) {
            int iQ3 = q(true);
            if (iQ3 != 44) {
                if (iQ3 != 59) {
                    if (iQ3 == 93) {
                        this.g = 4;
                        return 4;
                    }
                    C("Unterminated array");
                    throw null;
                }
                c();
            }
        } else {
            if (i12 == 3 || i12 == 5) {
                iArr[i11] = 4;
                if (i12 == 5 && (iQ = q(true)) != 44) {
                    if (iQ != 59) {
                        if (iQ == 125) {
                            this.g = 2;
                            return 2;
                        }
                        C("Unterminated object");
                        throw null;
                    }
                    c();
                }
                int iQ4 = q(true);
                if (iQ4 == 34) {
                    this.g = 13;
                    return 13;
                }
                if (iQ4 == 39) {
                    c();
                    this.g = 12;
                    return 12;
                }
                if (iQ4 == 125) {
                    if (i12 != 5) {
                        this.g = 2;
                        return 2;
                    }
                    C("Expected name");
                    throw null;
                }
                c();
                this.c--;
                if (j((char) iQ4)) {
                    this.g = 14;
                    return 14;
                }
                C("Expected name");
                throw null;
            }
            if (i12 != 4) {
                if (i12 == 6) {
                    if (this.o == 1) {
                        q(true);
                        int i13 = this.c;
                        this.c = i13 - 1;
                        if (i13 + 4 <= this.d || g(5)) {
                            int i14 = this.c;
                            if (cArr[i14] == ')' && cArr[i14 + 1] == ']' && cArr[i14 + 2] == '}' && cArr[i14 + 3] == '\'' && cArr[i14 + 4] == '\n') {
                                this.c = i14 + 5;
                            }
                        }
                    }
                    this.k[this.l - 1] = 7;
                } else if (i12 == 7) {
                    i = 0;
                    if (q(false) == -1) {
                        this.g = 17;
                        return 17;
                    }
                    c();
                    this.c--;
                } else {
                    i = 0;
                    if (i12 == 8) {
                        throw new IllegalStateException("JsonReader is closed");
                    }
                }
                iQ2 = q(true);
                if (iQ2 != 34) {
                    this.g = 9;
                    return 9;
                }
                if (iQ2 != 39) {
                    c();
                    this.g = 8;
                    return 8;
                }
                if (iQ2 != 44 && iQ2 != 59) {
                    if (iQ2 != 91) {
                        this.g = 3;
                        return 3;
                    }
                    if (iQ2 != 93) {
                        if (iQ2 != 123) {
                            this.g = 1;
                            return 1;
                        }
                        int i15 = this.c - 1;
                        this.c = i15;
                        c = cArr[i15];
                        if (c != 't' || c == 'T') {
                            str = "true";
                            str2 = "TRUE";
                            i2 = 5;
                        } else {
                            if (c != 'f' && c != 'F') {
                                if (c != 'n' && c != 'N') {
                                    i2 = i;
                                    break;
                                }
                                str = "null";
                                str2 = "NULL";
                                i2 = 7;
                                if (i2 != 0) {
                                    return i2;
                                }
                                int i16 = this.c;
                                i5 = this.d;
                                i6 = i;
                                i7 = i6;
                                int i17 = i7;
                                i8 = i16;
                                z = true;
                                long j = 0;
                                while (true) {
                                    if (i8 + i7 != i5) {
                                        c3 = cArr[i8 + i7];
                                        if (c3 != '+') {
                                            if (c3 != 'E' || c3 == 'e') {
                                                if (i6 != 2 || i6 == 4) {
                                                    i6 = 5;
                                                    i7++;
                                                }
                                            } else if (c3 == '-') {
                                                if (i6 == 0) {
                                                    i6 = 1;
                                                    i17 = 1;
                                                } else {
                                                    if (i6 != 5) {
                                                    }
                                                    i6 = 6;
                                                }
                                                i7++;
                                            } else if (c3 != '.') {
                                                if (c3 >= '0' && c3 <= '9') {
                                                    if (i6 == 1 || i6 == 0) {
                                                        j = -(c3 - '0');
                                                        i6 = 2;
                                                    } else if (i6 == 2) {
                                                        if (j != 0) {
                                                            long j2 = (10 * j) - ((long) (c3 - '0'));
                                                            z &= j > -922337203685477580L || (j == -922337203685477580L && j2 < j);
                                                            j = j2;
                                                        }
                                                    } else if (i6 == 3) {
                                                        i6 = 4;
                                                    } else if (i6 == 5 || i6 == 6) {
                                                        i6 = 7;
                                                    }
                                                    i7++;
                                                } else if (!j(c3)) {
                                                    i10 = 2;
                                                    if (i6 != 2) {
                                                        if (i6 != i10 || i6 == 4 || i6 == 7) {
                                                            this.i = i7;
                                                            i9 = 16;
                                                            this.g = 16;
                                                        }
                                                    } else if (z || ((j == Long.MIN_VALUE && i17 == 0) || (j == 0 && i17 != 0))) {
                                                        i10 = 2;
                                                        if (i6 != i10) {
                                                        }
                                                        this.i = i7;
                                                        i9 = 16;
                                                        this.g = 16;
                                                    } else {
                                                        if (i17 == 0) {
                                                            j = -j;
                                                        }
                                                        this.h = j;
                                                        this.c += i7;
                                                        i9 = 15;
                                                        this.g = 15;
                                                    }
                                                }
                                            } else if (i6 == 2) {
                                                i6 = 3;
                                                i7++;
                                            }
                                            if (i9 != 0) {
                                                return i9;
                                            }
                                            if (j(cArr[this.c])) {
                                                C("Expected value");
                                                throw null;
                                            }
                                            c();
                                            this.g = 10;
                                            return 10;
                                        }
                                        if (i6 != 5) {
                                        }
                                        i6 = 6;
                                        i7++;
                                    } else if (i7 != cArr.length) {
                                        if (g(i7 + 1)) {
                                            i8 = this.c;
                                            i5 = this.d;
                                            c3 = cArr[i8 + i7];
                                            if (c3 != '+') {
                                                if (c3 != 'E') {
                                                    if (i6 != 2) {
                                                    }
                                                    i6 = 5;
                                                    i7++;
                                                } else {
                                                    if (i6 != 2) {
                                                    }
                                                    i6 = 5;
                                                    i7++;
                                                }
                                                if (i9 != 0) {
                                                    return i9;
                                                }
                                                if (j(cArr[this.c])) {
                                                    C("Expected value");
                                                    throw null;
                                                }
                                                c();
                                                this.g = 10;
                                                return 10;
                                            }
                                            if (i6 != 5) {
                                            }
                                            i6 = 6;
                                            i7++;
                                        }
                                        i10 = 2;
                                        if (i6 != 2) {
                                            if (i6 != i10) {
                                            }
                                            this.i = i7;
                                            i9 = 16;
                                            this.g = 16;
                                        } else {
                                            if (z) {
                                            }
                                            i10 = 2;
                                            if (i6 != i10) {
                                            }
                                            this.i = i7;
                                            i9 = 16;
                                            this.g = 16;
                                        }
                                        if (i9 != 0) {
                                            return i9;
                                        }
                                        if (j(cArr[this.c])) {
                                            C("Expected value");
                                            throw null;
                                        }
                                        c();
                                        this.g = 10;
                                        return 10;
                                    }
                                    i9 = 0;
                                    if (i9 != 0) {
                                        return i9;
                                    }
                                    if (j(cArr[this.c])) {
                                        C("Expected value");
                                        throw null;
                                    }
                                    c();
                                    this.g = 10;
                                    return 10;
                                }
                            }
                            str = "false";
                            str2 = "FALSE";
                            i2 = 6;
                        }
                        if (this.o != 3) {
                            i3 = 1;
                        } else {
                            i3 = i;
                        }
                        length = str.length();
                        i4 = i;
                        while (true) {
                            if (i4 >= length) {
                                if ((this.c + length < this.d && !g(length + 1)) || !j(cArr[this.c + length])) {
                                    this.c += length;
                                    this.g = i2;
                                    break;
                                }
                                break;
                            }
                            if ((this.c + i4 >= this.d || g(i4 + 1)) && ((c2 = cArr[this.c + i4]) == str.charAt(i4) || (i3 != 0 && c2 == str2.charAt(i4)))) {
                            }
                            i2 = i;
                            break;
                        }
                        if (i2 != 0) {
                            return i2;
                        }
                        int i18 = this.c;
                        i5 = this.d;
                        i6 = i;
                        i7 = i6;
                        int i19 = i7;
                        i8 = i18;
                        z = true;
                        long j3 = 0;
                        while (true) {
                            if (i8 + i7 != i5) {
                                c3 = cArr[i8 + i7];
                                if (c3 != '+') {
                                    if (c3 != 'E') {
                                        if (i6 != 2) {
                                        }
                                        i6 = 5;
                                        i7++;
                                    } else {
                                        if (i6 != 2) {
                                        }
                                        i6 = 5;
                                        i7++;
                                    }
                                    if (i9 != 0) {
                                        return i9;
                                    }
                                    if (j(cArr[this.c])) {
                                        C("Expected value");
                                        throw null;
                                    }
                                    c();
                                    this.g = 10;
                                    return 10;
                                }
                                if (i6 != 5) {
                                }
                                i6 = 6;
                                i7++;
                            } else if (i7 != cArr.length) {
                                if (g(i7 + 1)) {
                                    i8 = this.c;
                                    i5 = this.d;
                                    c3 = cArr[i8 + i7];
                                    if (c3 != '+') {
                                        if (c3 != 'E') {
                                            if (i6 != 2) {
                                            }
                                            i6 = 5;
                                            i7++;
                                        } else {
                                            if (i6 != 2) {
                                            }
                                            i6 = 5;
                                            i7++;
                                        }
                                        if (i9 != 0) {
                                            return i9;
                                        }
                                        if (j(cArr[this.c])) {
                                            C("Expected value");
                                            throw null;
                                        }
                                        c();
                                        this.g = 10;
                                        return 10;
                                    }
                                    if (i6 != 5) {
                                    }
                                    i6 = 6;
                                    i7++;
                                }
                                i10 = 2;
                                if (i6 != 2) {
                                    if (i6 != i10) {
                                    }
                                    this.i = i7;
                                    i9 = 16;
                                    this.g = 16;
                                } else {
                                    if (z) {
                                    }
                                    i10 = 2;
                                    if (i6 != i10) {
                                    }
                                    this.i = i7;
                                    i9 = 16;
                                    this.g = 16;
                                }
                                if (i9 != 0) {
                                    return i9;
                                }
                                if (j(cArr[this.c])) {
                                    C("Expected value");
                                    throw null;
                                }
                                c();
                                this.g = 10;
                                return 10;
                            }
                            i9 = 0;
                            if (i9 != 0) {
                                return i9;
                            }
                            if (j(cArr[this.c])) {
                                C("Expected value");
                                throw null;
                            }
                            c();
                            this.g = 10;
                            return 10;
                        }
                    }
                    if (i12 == 1) {
                        this.g = 4;
                        return 4;
                    }
                }
                if (i12 == 1 && i12 != 2) {
                    C("Unexpected value");
                    throw null;
                }
                c();
                this.c--;
                this.g = 7;
                return 7;
            }
            iArr[i11] = 5;
            int iQ5 = q(true);
            if (iQ5 != 58) {
                if (iQ5 != 61) {
                    C("Expected ':'");
                    throw null;
                }
                c();
                if (this.c < this.d || g(1)) {
                    int i20 = this.c;
                    if (cArr[i20] == '>') {
                        this.c = i20 + 1;
                    }
                }
            }
        }
        i = 0;
        iQ2 = q(true);
        if (iQ2 != 34) {
            this.g = 9;
            return 9;
        }
        if (iQ2 != 39) {
            c();
            this.g = 8;
            return 8;
        }
        if (iQ2 != 44) {
            if (iQ2 != 91) {
                this.g = 3;
                return 3;
            }
            if (iQ2 != 93) {
                if (iQ2 != 123) {
                    this.g = 1;
                    return 1;
                }
                int i110 = this.c - 1;
                this.c = i110;
                c = cArr[i110];
                if (c != 't') {
                    str = "true";
                    str2 = "TRUE";
                    i2 = 5;
                    if (this.o != 3) {
                        i3 = 1;
                    } else {
                        i3 = i;
                    }
                    length = str.length();
                    i4 = i;
                    while (true) {
                        if (i4 >= length) {
                            if (this.c + length < this.d) {
                            }
                            this.c += length;
                            this.g = i2;
                            break;
                        }
                        i4 = this.c + i4 >= this.d ? i4 + 1 : i4 + 1;
                    }
                    if (i2 != 0) {
                        return i2;
                    }
                    int i111 = this.c;
                    i5 = this.d;
                    i6 = i;
                    i7 = i6;
                    int i112 = i7;
                    i8 = i111;
                    z = true;
                    long j4 = 0;
                    while (true) {
                        if (i8 + i7 != i5) {
                            c3 = cArr[i8 + i7];
                            if (c3 != '+') {
                                if (c3 != 'E') {
                                    if (i6 != 2) {
                                    }
                                    i6 = 5;
                                    i7++;
                                } else {
                                    if (i6 != 2) {
                                    }
                                    i6 = 5;
                                    i7++;
                                }
                                if (i9 != 0) {
                                    return i9;
                                }
                                if (j(cArr[this.c])) {
                                    C("Expected value");
                                    throw null;
                                }
                                c();
                                this.g = 10;
                                return 10;
                            }
                            if (i6 != 5) {
                            }
                            i6 = 6;
                            i7++;
                        } else if (i7 != cArr.length) {
                            if (g(i7 + 1)) {
                                i8 = this.c;
                                i5 = this.d;
                                c3 = cArr[i8 + i7];
                                if (c3 != '+') {
                                    if (c3 != 'E') {
                                        if (i6 != 2) {
                                        }
                                        i6 = 5;
                                        i7++;
                                    } else {
                                        if (i6 != 2) {
                                        }
                                        i6 = 5;
                                        i7++;
                                    }
                                    if (i9 != 0) {
                                        return i9;
                                    }
                                    if (j(cArr[this.c])) {
                                        C("Expected value");
                                        throw null;
                                    }
                                    c();
                                    this.g = 10;
                                    return 10;
                                }
                                if (i6 != 5) {
                                }
                                i6 = 6;
                                i7++;
                            }
                            i10 = 2;
                            if (i6 != 2) {
                                if (i6 != i10) {
                                }
                                this.i = i7;
                                i9 = 16;
                                this.g = 16;
                            } else {
                                if (z) {
                                }
                                i10 = 2;
                                if (i6 != i10) {
                                }
                                this.i = i7;
                                i9 = 16;
                                this.g = 16;
                            }
                            if (i9 != 0) {
                                return i9;
                            }
                            if (j(cArr[this.c])) {
                                C("Expected value");
                                throw null;
                            }
                            c();
                            this.g = 10;
                            return 10;
                        }
                        i9 = 0;
                        if (i9 != 0) {
                            return i9;
                        }
                        if (j(cArr[this.c])) {
                            C("Expected value");
                            throw null;
                        }
                        c();
                        this.g = 10;
                        return 10;
                    }
                }
                str = "true";
                str2 = "TRUE";
                i2 = 5;
                if (this.o != 3) {
                    i3 = 1;
                } else {
                    i3 = i;
                }
                length = str.length();
                i4 = i;
                while (true) {
                    if (i4 >= length) {
                        if (this.c + length < this.d) {
                        }
                        this.c += length;
                        this.g = i2;
                        break;
                    }
                    if (this.c + i4 >= this.d) {
                    }
                }
                if (i2 != 0) {
                    return i2;
                }
                int i113 = this.c;
                i5 = this.d;
                i6 = i;
                i7 = i6;
                int i114 = i7;
                i8 = i113;
                z = true;
                long j5 = 0;
                while (true) {
                    if (i8 + i7 != i5) {
                        c3 = cArr[i8 + i7];
                        if (c3 != '+') {
                            if (c3 != 'E') {
                                if (i6 != 2) {
                                }
                                i6 = 5;
                                i7++;
                            } else {
                                if (i6 != 2) {
                                }
                                i6 = 5;
                                i7++;
                            }
                            if (i9 != 0) {
                                return i9;
                            }
                            if (j(cArr[this.c])) {
                                C("Expected value");
                                throw null;
                            }
                            c();
                            this.g = 10;
                            return 10;
                        }
                        if (i6 != 5) {
                        }
                        i6 = 6;
                        i7++;
                    } else if (i7 != cArr.length) {
                        if (g(i7 + 1)) {
                            i8 = this.c;
                            i5 = this.d;
                            c3 = cArr[i8 + i7];
                            if (c3 != '+') {
                                if (c3 != 'E') {
                                    if (i6 != 2) {
                                    }
                                    i6 = 5;
                                    i7++;
                                } else {
                                    if (i6 != 2) {
                                    }
                                    i6 = 5;
                                    i7++;
                                }
                                if (i9 != 0) {
                                    return i9;
                                }
                                if (j(cArr[this.c])) {
                                    C("Expected value");
                                    throw null;
                                }
                                c();
                                this.g = 10;
                                return 10;
                            }
                            if (i6 != 5) {
                            }
                            i6 = 6;
                            i7++;
                        }
                        i10 = 2;
                        if (i6 != 2) {
                            if (i6 != i10) {
                            }
                            this.i = i7;
                            i9 = 16;
                            this.g = 16;
                        } else {
                            if (z) {
                            }
                            i10 = 2;
                            if (i6 != i10) {
                            }
                            this.i = i7;
                            i9 = 16;
                            this.g = 16;
                        }
                        if (i9 != 0) {
                            return i9;
                        }
                        if (j(cArr[this.c])) {
                            C("Expected value");
                            throw null;
                        }
                        c();
                        this.g = 10;
                        return 10;
                    }
                    i9 = 0;
                    if (i9 != 0) {
                        return i9;
                    }
                    if (j(cArr[this.c])) {
                        C("Expected value");
                        throw null;
                    }
                    c();
                    this.g = 10;
                    return 10;
                }
                i2 = i;
                if (i2 != 0) {
                    return i2;
                }
                int i115 = this.c;
                i5 = this.d;
                i6 = i;
                i7 = i6;
                int i116 = i7;
                i8 = i115;
                z = true;
                long j6 = 0;
                while (true) {
                    if (i8 + i7 != i5) {
                        c3 = cArr[i8 + i7];
                        if (c3 != '+') {
                            if (c3 != 'E') {
                                if (i6 != 2) {
                                }
                                i6 = 5;
                                i7++;
                            } else {
                                if (i6 != 2) {
                                }
                                i6 = 5;
                                i7++;
                            }
                            if (i9 != 0) {
                                return i9;
                            }
                            if (j(cArr[this.c])) {
                                C("Expected value");
                                throw null;
                            }
                            c();
                            this.g = 10;
                            return 10;
                        }
                        if (i6 != 5) {
                        }
                        i6 = 6;
                        i7++;
                    } else if (i7 != cArr.length) {
                        if (g(i7 + 1)) {
                            i8 = this.c;
                            i5 = this.d;
                            c3 = cArr[i8 + i7];
                            if (c3 != '+') {
                                if (c3 != 'E') {
                                    if (i6 != 2) {
                                    }
                                    i6 = 5;
                                    i7++;
                                } else {
                                    if (i6 != 2) {
                                    }
                                    i6 = 5;
                                    i7++;
                                }
                                if (i9 != 0) {
                                    return i9;
                                }
                                if (j(cArr[this.c])) {
                                    C("Expected value");
                                    throw null;
                                }
                                c();
                                this.g = 10;
                                return 10;
                            }
                            if (i6 != 5) {
                            }
                            i6 = 6;
                            i7++;
                        }
                        i10 = 2;
                        if (i6 != 2) {
                            if (i6 != i10) {
                            }
                            this.i = i7;
                            i9 = 16;
                            this.g = 16;
                        } else {
                            if (z) {
                            }
                            i10 = 2;
                            if (i6 != i10) {
                            }
                            this.i = i7;
                            i9 = 16;
                            this.g = 16;
                        }
                        if (i9 != 0) {
                            return i9;
                        }
                        if (j(cArr[this.c])) {
                            C("Expected value");
                            throw null;
                        }
                        c();
                        this.g = 10;
                        return 10;
                    }
                    i9 = 0;
                    if (i9 != 0) {
                        return i9;
                    }
                    if (j(cArr[this.c])) {
                        C("Expected value");
                        throw null;
                    }
                    c();
                    this.g = 10;
                    return 10;
                }
            }
            if (i12 == 1) {
                this.g = 4;
                return 4;
            }
        }
        if (i12 == 1) {
        }
        c();
        this.c--;
        this.g = 7;
        return 7;
    }

    public final void e() {
        int iD = this.g;
        if (iD == 0) {
            iD = d();
        }
        if (iD != 4) {
            throw D("END_ARRAY");
        }
        int i = this.l;
        this.l = i - 1;
        int[] iArr = this.n;
        int i2 = i - 2;
        iArr[i2] = iArr[i2] + 1;
        this.g = 0;
    }

    public final void f() {
        int iD = this.g;
        if (iD == 0) {
            iD = d();
        }
        if (iD != 2) {
            throw D("END_OBJECT");
        }
        int i = this.l;
        int i2 = i - 1;
        this.l = i2;
        this.m[i2] = null;
        int[] iArr = this.n;
        int i3 = i - 2;
        iArr[i3] = iArr[i3] + 1;
        this.g = 0;
    }

    public final boolean g(int i) throws IOException {
        int i2;
        int i3;
        int i4 = this.f;
        int i5 = this.c;
        this.f = i4 - i5;
        int i6 = this.d;
        char[] cArr = this.b;
        if (i6 != i5) {
            int i7 = i6 - i5;
            this.d = i7;
            System.arraycopy(cArr, i5, cArr, 0, i7);
        } else {
            this.d = 0;
        }
        this.c = 0;
        do {
            int i8 = this.d;
            int i9 = this.a.read(cArr, i8, cArr.length - i8);
            if (i9 == -1) {
                return false;
            }
            i2 = this.d + i9;
            this.d = i2;
            if (this.e == 0 && (i3 = this.f) == 0 && i2 > 0 && cArr[0] == 65279) {
                this.c++;
                this.f = i3 + 1;
                i++;
            }
        } while (i2 < i);
        return true;
    }

    public final String h(boolean z) {
        StringBuilder sb = new StringBuilder("$");
        int i = 0;
        while (true) {
            int i2 = this.l;
            if (i >= i2) {
                return sb.toString();
            }
            int i3 = this.k[i];
            switch (i3) {
                case 1:
                case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                    int i4 = this.n[i];
                    if (z && i4 > 0 && i == i2 - 1) {
                        i4--;
                    }
                    sb.append('[');
                    sb.append(i4);
                    sb.append(']');
                    break;
                case 3:
                case 4:
                case BulletSpan.STANDARD_BULLET_RADIUS /* 5 */:
                    sb.append('.');
                    String str = this.m[i];
                    if (str != null) {
                        sb.append(str);
                    }
                    break;
                case 6:
                case 7:
                case 8:
                    break;
                default:
                    throw new AssertionError(AbstractC0535qo.b(i3, "Unknown scope value: "));
            }
            i++;
        }
    }

    public final boolean i() throws IOException {
        int iD = this.g;
        if (iD == 0) {
            iD = d();
        }
        return (iD == 2 || iD == 4 || iD == 17) ? false : true;
    }

    public final boolean j(char c) throws C0775yh {
        if (c == '\t' || c == '\n' || c == '\f' || c == '\r' || c == ' ') {
            return false;
        }
        if (c != '#') {
            if (c == ',') {
                return false;
            }
            if (c != '/' && c != '=') {
                if (c == '{' || c == '}' || c == ':') {
                    return false;
                }
                if (c != ';') {
                    switch (c) {
                        case '[':
                        case ']':
                            return false;
                        case '\\':
                            break;
                        default:
                            return true;
                    }
                }
            }
        }
        c();
        return false;
    }

    public final String k() {
        return " at line " + (this.e + 1) + " column " + ((this.c - this.f) + 1) + " path " + h(false);
    }

    public final boolean l() throws IOException {
        int iD = this.g;
        if (iD == 0) {
            iD = d();
        }
        if (iD == 5) {
            this.g = 0;
            int[] iArr = this.n;
            int i = this.l - 1;
            iArr[i] = iArr[i] + 1;
            return true;
        }
        if (iD != 6) {
            throw D("a boolean");
        }
        this.g = 0;
        int[] iArr2 = this.n;
        int i2 = this.l - 1;
        iArr2[i2] = iArr2[i2] + 1;
        return false;
    }

    public final double m() throws IOException {
        int iD = this.g;
        if (iD == 0) {
            iD = d();
        }
        if (iD == 15) {
            this.g = 0;
            int[] iArr = this.n;
            int i = this.l - 1;
            iArr[i] = iArr[i] + 1;
            return this.h;
        }
        if (iD == 16) {
            this.j = new String(this.b, this.c, this.i);
            this.c += this.i;
        } else if (iD == 8 || iD == 9) {
            this.j = s(iD == 8 ? '\'' : '\"');
        } else if (iD == 10) {
            this.j = u();
        } else if (iD != 11) {
            throw D("a double");
        }
        this.g = 11;
        double d = Double.parseDouble(this.j);
        if (this.o != 1 && (Double.isNaN(d) || Double.isInfinite(d))) {
            C("JSON forbids NaN and infinities: " + d);
            throw null;
        }
        this.j = null;
        this.g = 0;
        int[] iArr2 = this.n;
        int i2 = this.l - 1;
        iArr2[i2] = iArr2[i2] + 1;
        return d;
    }

    public final int n() throws IOException {
        int iD = this.g;
        if (iD == 0) {
            iD = d();
        }
        if (iD == 15) {
            long j = this.h;
            int i = (int) j;
            if (j != i) {
                throw new NumberFormatException("Expected an int but was " + this.h + k());
            }
            this.g = 0;
            int[] iArr = this.n;
            int i2 = this.l - 1;
            iArr[i2] = iArr[i2] + 1;
            return i;
        }
        if (iD == 16) {
            this.j = new String(this.b, this.c, this.i);
            this.c += this.i;
        } else {
            if (iD != 8 && iD != 9 && iD != 10) {
                throw D("an int");
            }
            if (iD == 10) {
                this.j = u();
            } else {
                this.j = s(iD == 8 ? '\'' : '\"');
            }
            try {
                int i3 = Integer.parseInt(this.j);
                this.g = 0;
                int[] iArr2 = this.n;
                int i4 = this.l - 1;
                iArr2[i4] = iArr2[i4] + 1;
                return i3;
            } catch (NumberFormatException unused) {
            }
        }
        this.g = 11;
        double d = Double.parseDouble(this.j);
        int i5 = (int) d;
        if (i5 != d) {
            throw new NumberFormatException("Expected an int but was " + this.j + k());
        }
        this.j = null;
        this.g = 0;
        int[] iArr3 = this.n;
        int i6 = this.l - 1;
        iArr3[i6] = iArr3[i6] + 1;
        return i5;
    }

    public final long o() throws IOException {
        int iD = this.g;
        if (iD == 0) {
            iD = d();
        }
        if (iD == 15) {
            this.g = 0;
            int[] iArr = this.n;
            int i = this.l - 1;
            iArr[i] = iArr[i] + 1;
            return this.h;
        }
        if (iD == 16) {
            this.j = new String(this.b, this.c, this.i);
            this.c += this.i;
        } else {
            if (iD != 8 && iD != 9 && iD != 10) {
                throw D("a long");
            }
            if (iD == 10) {
                this.j = u();
            } else {
                this.j = s(iD == 8 ? '\'' : '\"');
            }
            try {
                long j = Long.parseLong(this.j);
                this.g = 0;
                int[] iArr2 = this.n;
                int i2 = this.l - 1;
                iArr2[i2] = iArr2[i2] + 1;
                return j;
            } catch (NumberFormatException unused) {
            }
        }
        this.g = 11;
        double d = Double.parseDouble(this.j);
        long j2 = (long) d;
        if (j2 != d) {
            throw new NumberFormatException("Expected a long but was " + this.j + k());
        }
        this.j = null;
        this.g = 0;
        int[] iArr3 = this.n;
        int i3 = this.l - 1;
        iArr3[i3] = iArr3[i3] + 1;
        return j2;
    }

    public final String p() throws IOException {
        String strS;
        int iD = this.g;
        if (iD == 0) {
            iD = d();
        }
        if (iD == 14) {
            strS = u();
        } else if (iD == 12) {
            strS = s('\'');
        } else {
            if (iD != 13) {
                throw D("a name");
            }
            strS = s('\"');
        }
        this.g = 0;
        this.m[this.l - 1] = strS;
        return strS;
    }

    public final int q(boolean z) throws IOException {
        int i = this.c;
        int i2 = this.d;
        while (true) {
            if (i == i2) {
                this.c = i;
                if (!g(1)) {
                    if (!z) {
                        return -1;
                    }
                    throw new EOFException("End of input" + k());
                }
                i = this.c;
                i2 = this.d;
            }
            int i3 = i + 1;
            char[] cArr = this.b;
            char c = cArr[i];
            if (c == '\n') {
                this.e++;
                this.f = i3;
            } else if (c != ' ' && c != '\r' && c != '\t') {
                if (c == '/') {
                    this.c = i3;
                    if (i3 == i2) {
                        this.c = i;
                        boolean zG = g(2);
                        this.c++;
                        if (!zG) {
                        }
                        return c;
                    }
                    c();
                    int i4 = this.c;
                    char c2 = cArr[i4];
                    if (c2 == '*') {
                        this.c = i4 + 1;
                        while (true) {
                            if (this.c + 2 > this.d && !g(2)) {
                                C("Unterminated comment");
                                throw null;
                            }
                            int i5 = this.c;
                            if (cArr[i5] != '\n') {
                                int i6 = 0;
                                while (true) {
                                    if (i6 >= 2) {
                                        i = this.c + 2;
                                        i2 = this.d;
                                        break;
                                    }
                                    if (cArr[this.c + i6] != "*/".charAt(i6)) {
                                        break;
                                    }
                                    i6++;
                                }
                            } else {
                                this.e++;
                                this.f = i5 + 1;
                            }
                            this.c++;
                        }
                    } else {
                        if (c2 != '/') {
                            return c;
                        }
                        this.c = i4 + 1;
                        z();
                        i = this.c;
                        i2 = this.d;
                    }
                } else {
                    if (c != '#') {
                        this.c = i3;
                        return c;
                    }
                    this.c = i3;
                    c();
                    z();
                    i = this.c;
                    i2 = this.d;
                }
            }
            i = i3;
        }
    }

    public final void r() {
        int iD = this.g;
        if (iD == 0) {
            iD = d();
        }
        if (iD != 7) {
            throw D("null");
        }
        this.g = 0;
        int[] iArr = this.n;
        int i = this.l - 1;
        iArr[i] = iArr[i] + 1;
    }

    public final String s(char c) throws C0775yh {
        int i;
        char[] cArr;
        StringBuilder sb = null;
        do {
            int i2 = this.c;
            int i3 = this.d;
            while (true) {
                int i4 = i3;
                i = i2;
                while (true) {
                    cArr = this.b;
                    if (i2 < i4) {
                        int i5 = i2 + 1;
                        char c2 = cArr[i2];
                        if (this.o == 3 && c2 < ' ') {
                            C("Unescaped control characters (\\u0000-\\u001F) are not allowed in strict mode");
                            throw null;
                        }
                        if (c2 == c) {
                            this.c = i5;
                            int i6 = (i5 - i) - 1;
                            if (sb == null) {
                                return new String(cArr, i, i6);
                            }
                            sb.append(cArr, i, i6);
                            return sb.toString();
                        }
                        if (c2 == '\\') {
                            this.c = i5;
                            int i7 = i5 - i;
                            int i8 = i7 - 1;
                            if (sb == null) {
                                sb = new StringBuilder(Math.max(i7 * 2, 16));
                            }
                            sb.append(cArr, i, i8);
                            sb.append(x());
                            i2 = this.c;
                            i3 = this.d;
                        } else {
                            if (c2 == '\n') {
                                this.e++;
                                this.f = i5;
                            }
                            i2 = i5;
                        }
                    }
                }
            }
            if (sb == null) {
                sb = new StringBuilder(Math.max((i2 - i) * 2, 16));
            }
            sb.append(cArr, i, i2 - i);
            this.c = i2;
        } while (g(1));
        C("Unterminated string");
        throw null;
    }

    public final String t() throws IOException {
        String str;
        int iD = this.g;
        if (iD == 0) {
            iD = d();
        }
        if (iD == 10) {
            str = u();
        } else if (iD == 8) {
            str = s('\'');
        } else if (iD == 9) {
            str = s('\"');
        } else if (iD == 11) {
            str = this.j;
            this.j = null;
        } else if (iD == 15) {
            str = Long.toString(this.h);
        } else {
            if (iD != 16) {
                throw D("a string");
            }
            str = new String(this.b, this.c, this.i);
            this.c += this.i;
        }
        this.g = 0;
        int[] iArr = this.n;
        int i = this.l - 1;
        iArr[i] = iArr[i] + 1;
        return str;
    }

    public final String toString() {
        return Vf.class.getSimpleName() + k();
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:32:0x0044. Please report as an issue. */
    public final String u() throws C0775yh {
        String string;
        StringBuilder sb = null;
        int i = 0;
        while (true) {
            int i2 = 0;
            while (true) {
                int i3 = this.c;
                int i4 = i3 + i2;
                int i5 = this.d;
                char[] cArr = this.b;
                if (i4 < i5) {
                    char c = cArr[i3 + i2];
                    if (c != '\t' && c != '\n' && c != '\f' && c != '\r' && c != ' ') {
                        if (c != '#') {
                            if (c != ',') {
                                if (c != '/' && c != '=') {
                                    if (c != '{' && c != '}' && c != ':') {
                                        if (c != ';') {
                                            switch (c) {
                                                case '[':
                                                case ']':
                                                    break;
                                                case '\\':
                                                    break;
                                                default:
                                                    i2++;
                                                    break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        c();
                    }
                    i = i2;
                } else if (i2 >= cArr.length) {
                    if (sb == null) {
                        sb = new StringBuilder(Math.max(i2, 16));
                    }
                    sb.append(cArr, this.c, i2);
                    this.c += i2;
                    if (!g(1)) {
                    }
                } else if (!g(i2 + 1)) {
                    i = i2;
                }
                if (sb == null) {
                    string = new String(cArr, this.c, i);
                } else {
                    sb.append(cArr, this.c, i);
                    string = sb.toString();
                }
                this.c += i;
                return string;
            }
        }
    }

    public final int v() {
        int iD = this.g;
        if (iD == 0) {
            iD = d();
        }
        switch (iD) {
            case 1:
                return 3;
            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                return 4;
            case 3:
                return 1;
            case 4:
                return 2;
            case BulletSpan.STANDARD_BULLET_RADIUS /* 5 */:
            case 6:
                return 8;
            case 7:
                return 9;
            case 8:
            case 9:
            case 10:
            case 11:
                return 6;
            case 12:
            case 13:
            case 14:
                return 5;
            case 15:
            case 16:
                return 7;
            case 17:
                return 10;
            default:
                throw new AssertionError();
        }
    }

    public final void w(int i) throws C0775yh {
        int i2 = this.l;
        if (i2 - 1 >= 255) {
            throw new C0775yh("Nesting limit 255 reached" + k());
        }
        int[] iArr = this.k;
        if (i2 == iArr.length) {
            int i3 = i2 * 2;
            this.k = Arrays.copyOf(iArr, i3);
            this.n = Arrays.copyOf(this.n, i3);
            this.m = (String[]) Arrays.copyOf(this.m, i3);
        }
        int[] iArr2 = this.k;
        int i4 = this.l;
        this.l = i4 + 1;
        iArr2[i4] = i;
    }

    public final char x() throws C0775yh {
        int i;
        if (this.c == this.d && !g(1)) {
            C("Unterminated escape sequence");
            throw null;
        }
        int i2 = this.c;
        int i3 = i2 + 1;
        this.c = i3;
        char[] cArr = this.b;
        char c = cArr[i2];
        if (c != '\n') {
            if (c != '\"') {
                if (c != '\'') {
                    if (c != '/' && c != '\\') {
                        if (c == 'b') {
                            return '\b';
                        }
                        if (c == 'f') {
                            return '\f';
                        }
                        if (c == 'n') {
                            return '\n';
                        }
                        if (c == 'r') {
                            return '\r';
                        }
                        if (c == 't') {
                            return '\t';
                        }
                        if (c != 'u') {
                            C("Invalid escape sequence");
                            throw null;
                        }
                        if (i2 + 5 > this.d && !g(4)) {
                            C("Unterminated escape sequence");
                            throw null;
                        }
                        int i4 = this.c;
                        int i5 = i4 + 4;
                        int i6 = 0;
                        while (i4 < i5) {
                            char c2 = cArr[i4];
                            int i7 = i6 << 4;
                            if (c2 >= '0' && c2 <= '9') {
                                i = c2 - '0';
                            } else if (c2 >= 'a' && c2 <= 'f') {
                                i = c2 - 'W';
                            } else {
                                if (c2 < 'A' || c2 > 'F') {
                                    C("Malformed Unicode escape \\u".concat(new String(cArr, this.c, 4)));
                                    throw null;
                                }
                                i = c2 - '7';
                            }
                            i6 = i + i7;
                            i4++;
                        }
                        this.c += 4;
                        return (char) i6;
                    }
                }
            }
            return c;
        }
        if (this.o == 3) {
            C("Cannot escape a newline character in strict mode");
            throw null;
        }
        this.e++;
        this.f = i3;
        if (this.o == 3) {
            C("Invalid escaped character \"'\" in strict mode");
            throw null;
        }
        return c;
    }

    public final void y(char c) throws C0775yh {
        do {
            int i = this.c;
            int i2 = this.d;
            while (i < i2) {
                int i3 = i + 1;
                char c2 = this.b[i];
                if (c2 == c) {
                    this.c = i3;
                    return;
                }
                if (c2 == '\\') {
                    this.c = i3;
                    x();
                    i = this.c;
                    i2 = this.d;
                } else {
                    if (c2 == '\n') {
                        this.e++;
                        this.f = i3;
                    }
                    i = i3;
                }
            }
            this.c = i;
        } while (g(1));
        C("Unterminated string");
        throw null;
    }

    public final void z() {
        char c;
        do {
            if (this.c >= this.d && !g(1)) {
                return;
            }
            int i = this.c;
            int i2 = i + 1;
            this.c = i2;
            c = this.b[i];
            if (c == '\n') {
                this.e++;
                this.f = i2;
                return;
            }
        } while (c != '\r');
    }
}
