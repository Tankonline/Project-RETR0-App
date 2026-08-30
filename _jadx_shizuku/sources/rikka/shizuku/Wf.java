package rikka.shizuku;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
public class Wf implements Closeable, Flushable, AutoCloseable {
    public static final Pattern l = Pattern.compile("-?(?:0|[1-9][0-9]*)(?:\\.[0-9]+)?(?:[eE][-+]?[0-9]+)?");
    public static final String[] m = new String[128];
    public static final String[] n;
    public final Writer a;
    public int[] b;
    public int c;
    public C0646uc d;
    public String e;
    public String f;
    public boolean g;
    public int h;
    public boolean i;
    public String j;
    public boolean k;

    static {
        for (int i = 0; i <= 31; i++) {
            m[i] = String.format("\\u%04x", Integer.valueOf(i));
        }
        String[] strArr = m;
        strArr[34] = "\\\"";
        strArr[92] = "\\\\";
        strArr[9] = "\\t";
        strArr[8] = "\\b";
        strArr[10] = "\\n";
        strArr[13] = "\\r";
        strArr[12] = "\\f";
        String[] strArr2 = (String[]) strArr.clone();
        n = strArr2;
        strArr2[60] = "\\u003c";
        strArr2[62] = "\\u003e";
        strArr2[38] = "\\u0026";
        strArr2[61] = "\\u003d";
        strArr2[39] = "\\u0027";
    }

    public Wf(Writer writer) {
        int[] iArr = new int[32];
        this.b = iArr;
        this.c = 0;
        if (iArr.length == 0) {
            this.b = Arrays.copyOf(iArr, 0);
        }
        int[] iArr2 = this.b;
        int i = this.c;
        this.c = i + 1;
        iArr2[i] = 6;
        this.h = 2;
        this.k = true;
        this.a = writer;
        k(C0646uc.d);
    }

    public final void a() throws IOException {
        int iJ = j();
        if (iJ == 1) {
            this.b[this.c - 1] = 2;
            h();
            return;
        }
        Writer writer = this.a;
        if (iJ == 2) {
            writer.append((CharSequence) this.f);
            h();
        } else {
            if (iJ == 4) {
                writer.append((CharSequence) this.e);
                this.b[this.c - 1] = 5;
                return;
            }
            if (iJ != 6) {
                if (iJ != 7) {
                    throw new IllegalStateException("Nesting problem.");
                }
                if (this.h != 1) {
                    throw new IllegalStateException("JSON must have only one top-level value.");
                }
            }
            this.b[this.c - 1] = 7;
        }
    }

    public void b() throws IOException {
        r();
        a();
        int i = this.c;
        int[] iArr = this.b;
        if (i == iArr.length) {
            this.b = Arrays.copyOf(iArr, i * 2);
        }
        int[] iArr2 = this.b;
        int i2 = this.c;
        this.c = i2 + 1;
        iArr2[i2] = 1;
        this.a.write(91);
    }

    public void c() throws IOException {
        r();
        a();
        int i = this.c;
        int[] iArr = this.b;
        if (i == iArr.length) {
            this.b = Arrays.copyOf(iArr, i * 2);
        }
        int[] iArr2 = this.b;
        int i2 = this.c;
        this.c = i2 + 1;
        iArr2[i2] = 3;
        this.a.write(123);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.a.close();
        int i = this.c;
        if (i > 1 || (i == 1 && this.b[i - 1] != 7)) {
            throw new IOException("Incomplete document");
        }
        this.c = 0;
    }

    public final void d(int i, int i2, char c) throws IOException {
        int iJ = j();
        if (iJ != i2 && iJ != i) {
            throw new IllegalStateException("Nesting problem.");
        }
        if (this.j != null) {
            throw new IllegalStateException("Dangling name: " + this.j);
        }
        this.c--;
        if (iJ == i2) {
            h();
        }
        this.a.write(c);
    }

    public void e() throws IOException {
        d(1, 2, ']');
    }

    public void f() throws IOException {
        d(3, 5, '}');
    }

    @Override // java.io.Flushable
    public void flush() throws IOException {
        if (this.c == 0) {
            throw new IllegalStateException("JsonWriter is closed.");
        }
        this.a.flush();
    }

    public void g(String str) {
        if (this.j != null) {
            throw new IllegalStateException("Already wrote a name, expecting a value.");
        }
        int iJ = j();
        if (iJ != 3 && iJ != 5) {
            throw new IllegalStateException("Please begin an object before writing a name.");
        }
        this.j = str;
    }

    public final void h() throws IOException {
        if (this.g) {
            return;
        }
        String str = this.d.a;
        Writer writer = this.a;
        writer.write(str);
        int i = this.c;
        for (int i2 = 1; i2 < i; i2++) {
            writer.write(this.d.b);
        }
    }

    public Wf i() throws IOException {
        if (this.j != null) {
            if (!this.k) {
                this.j = null;
                return this;
            }
            r();
        }
        a();
        this.a.write("null");
        return this;
    }

    public final int j() {
        int i = this.c;
        if (i != 0) {
            return this.b[i - 1];
        }
        throw new IllegalStateException("JsonWriter is closed.");
    }

    public final void k(C0646uc c0646uc) {
        this.d = c0646uc;
        this.f = ",";
        if (c0646uc.c) {
            this.e = ": ";
            if (c0646uc.a.isEmpty()) {
                this.f = ", ";
            }
        } else {
            this.e = ":";
        }
        this.g = this.d.a.isEmpty() && this.d.b.isEmpty();
    }

    /* JADX WARN: Code duplicated, block: B:20:0x0034  */
    public final void l(String str) throws IOException {
        String str2;
        String[] strArr = this.i ? n : m;
        Writer writer = this.a;
        writer.write(34);
        int length = str.length();
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            char cCharAt = str.charAt(i2);
            if (cCharAt < 128) {
                str2 = strArr[cCharAt];
                if (str2 != null) {
                    if (i < i2) {
                        writer.write(str, i, i2 - i);
                    }
                    writer.write(str2);
                    i = i2 + 1;
                }
            } else {
                if (cCharAt == 8232) {
                    str2 = "\\u2028";
                } else if (cCharAt == 8233) {
                    str2 = "\\u2029";
                }
                if (i < i2) {
                    writer.write(str, i, i2 - i);
                }
                writer.write(str2);
                i = i2 + 1;
            }
        }
        if (i < length) {
            writer.write(str, i, length - i);
        }
        writer.write(34);
    }

    public void m(double d) throws IOException {
        r();
        if (this.h == 1 || !(Double.isNaN(d) || Double.isInfinite(d))) {
            a();
            this.a.append((CharSequence) Double.toString(d));
        } else {
            throw new IllegalArgumentException("Numeric values must be finite, but was " + d);
        }
    }

    public void n(long j) throws IOException {
        r();
        a();
        this.a.write(Long.toString(j));
    }

    public void o(Number number) throws IOException {
        if (number == null) {
            i();
            return;
        }
        r();
        String string = number.toString();
        Class<?> cls = number.getClass();
        if (cls != Integer.class && cls != Long.class && cls != Byte.class && cls != Short.class && cls != BigDecimal.class && cls != BigInteger.class && cls != AtomicInteger.class && cls != AtomicLong.class) {
            if (string.equals("-Infinity") || string.equals("Infinity") || string.equals("NaN")) {
                if (this.h != 1) {
                    throw new IllegalArgumentException("Numeric values must be finite, but was ".concat(string));
                }
            } else if (cls != Float.class && cls != Double.class && !l.matcher(string).matches()) {
                throw new IllegalArgumentException("String created by " + cls + " is not a valid JSON number: " + string);
            }
        }
        a();
        this.a.append((CharSequence) string);
    }

    public void p(String str) throws IOException {
        if (str == null) {
            i();
            return;
        }
        r();
        a();
        l(str);
    }

    public void q(boolean z) throws IOException {
        r();
        a();
        this.a.write(z ? "true" : "false");
    }

    public final void r() throws IOException {
        if (this.j != null) {
            int iJ = j();
            if (iJ == 5) {
                this.a.write(this.f);
            } else if (iJ != 3) {
                throw new IllegalStateException("Nesting problem.");
            }
            h();
            this.b[this.c - 1] = 4;
            l(this.j);
            this.j = null;
        }
    }
}
