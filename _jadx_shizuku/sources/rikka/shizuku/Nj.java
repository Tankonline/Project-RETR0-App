package rikka.shizuku;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PushbackReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.DefaultHandler;
import rikka.html.text.style.BulletSpan;

/* JADX INFO: loaded from: classes.dex */
public final class Nj extends DefaultHandler implements XMLReader, LexicalHandler {
    public static final char[] D = {'<', '/', '>'};
    public Ea A;
    public Ea B;
    public boolean C;
    public ContentHandler a;
    public LexicalHandler b;
    public DTDHandler c;
    public ErrorHandler d;
    public EntityResolver e;
    public C0032ae f;
    public Zd g;
    public Mj h;
    public boolean i;
    public boolean j;
    public boolean k;
    public boolean l;
    public boolean m;
    public boolean n;
    public boolean o;
    public boolean p;
    public boolean q;
    public HashMap r;
    public Ea s;
    public String t;
    public boolean u;
    public String v;
    public String w;
    public String x;
    public String y;
    public Ea z;

    public static String k(String str) {
        int iIndexOf = str.indexOf(58);
        return iIndexOf != -1 ? str.substring(0, iIndexOf) : "";
    }

    public static String q(String str) {
        int length;
        char cCharAt;
        if (str == null || (length = str.length()) == 0 || (cCharAt = str.charAt(0)) != str.charAt(length - 1)) {
            return str;
        }
        return (cCharAt == '\'' || cCharAt == '\"') ? str.substring(1, str.length() - 1) : str;
    }

    public final void a() {
        String str;
        Ea ea = this.s;
        if (ea == null || (str = this.t) == null) {
            return;
        }
        ea.a.c(ea.b, str, null, str);
        this.t = null;
    }

    public final void b(char[] cArr, int i) {
        if (this.s == null || this.t == null) {
            return;
        }
        String str = new String(cArr, 0, i);
        int length = str.length();
        char[] cArr2 = new char[length];
        int i2 = 0;
        int i3 = -1;
        for (int i4 = 0; i4 < length; i4++) {
            char cCharAt = str.charAt(i4);
            int i5 = i2 + 1;
            cArr2[i2] = cCharAt;
            if (cCharAt == '&' && i3 == -1) {
                i2 = i5;
                i3 = i2;
            } else if (i3 == -1 || Character.isLetter(cCharAt) || Character.isDigit(cCharAt) || cCharAt == '#') {
                i2 = i5;
            } else if (cCharAt == ';') {
                int iF = f(cArr2, i3, (i5 - i3) - 1);
                if (iF > 65535) {
                    int i6 = iF - 65536;
                    cArr2[i3 - 1] = (char) ((i6 >> 10) + 55296);
                    cArr2[i3] = (char) ((i6 & 1023) + 56320);
                    i3++;
                } else if (iF != 0) {
                    cArr2[i3 - 1] = (char) iF;
                } else {
                    i3 = i5;
                }
                i2 = i3;
                i3 = -1;
            } else {
                i3 = -1;
                i2 = i5;
            }
        }
        String str2 = new String(cArr2, 0, i2);
        Ea ea = this.s;
        ea.a.c(ea.b, this.t, null, str2);
        this.t = null;
    }

    public final void c(char[] cArr, int i) throws SAXException {
        String str;
        Ea ea;
        this.s = null;
        if (i != 0) {
            Fa faD = this.f.d(g(cArr, i));
            if (faD == null) {
                return;
            } else {
                str = faD.a;
            }
        } else {
            str = this.z.a.a;
        }
        Ea ea2 = this.z;
        boolean z = false;
        while (ea2 != null) {
            Fa fa = ea2.a;
            if (fa.a.equals(str)) {
                break;
            }
            if ((fa.f & 4) != 0) {
                z = true;
            }
            ea2 = ea2.c;
        }
        if (ea2 == null || (ea = ea2.c) == null || ea.c == null) {
            return;
        }
        if (z) {
            ea2.d = true;
        } else {
            while (this.z != ea2) {
                o();
            }
            j();
        }
        while (this.z.d) {
            j();
        }
        n(null);
    }

    public final boolean d(String str, String str2) {
        return (str.equals("") || str2.equals("") || str2.equals(this.f.c)) ? false : true;
    }

    public final void e(char[] cArr, int i) {
        String strG;
        if (this.s == null && (strG = g(cArr, i)) != null) {
            Fa faD = this.f.d(strG);
            if (faD == null) {
                if (this.j) {
                    return;
                }
                this.f.b(strG, this.k ? 0 : -1, this.l ? -1 : Integer.MAX_VALUE, 0);
                if (!this.l) {
                    C0032ae c0032ae = this.f;
                    c0032ae.e(strG, c0032ae.e.a);
                }
                faD = this.f.d(strG);
            }
            this.s = new Ea(faD, this.m);
        }
    }

    public final int f(char[] cArr, int i, int i2) {
        char c;
        if (i2 < 1) {
            return 0;
        }
        if (cArr[i] != '#') {
            Integer num = (Integer) this.f.a.get(new String(cArr, i, i2));
            if (num == null) {
                return 0;
            }
            return num.intValue();
        }
        if (i2 <= 1 || !((c = cArr[i + 1]) == 'x' || c == 'X')) {
            try {
                return Integer.parseInt(new String(cArr, i + 1, i2 - 1), 10);
            } catch (NumberFormatException unused) {
                return 0;
            }
        }
        try {
            return Integer.parseInt(new String(cArr, i + 2, i2 - 2), 16);
        } catch (NumberFormatException unused2) {
            return 0;
        }
    }

    public final String g(char[] cArr, int i) {
        StringBuffer stringBuffer = new StringBuffer(i + 2);
        int i2 = 0;
        boolean z = false;
        boolean z2 = true;
        while (true) {
            int i3 = i - 1;
            if (i <= 0) {
                break;
            }
            char c = cArr[i2];
            if (Character.isLetter(c) || c == '_') {
                stringBuffer.append(c);
            } else {
                if (Character.isDigit(c) || c == '-' || c == '.') {
                    if (z2) {
                        stringBuffer.append('_');
                    }
                    stringBuffer.append(c);
                } else if (c == ':' && !z) {
                    if (z2) {
                        stringBuffer.append('_');
                    }
                    stringBuffer.append(this.n ? '_' : c);
                    z2 = true;
                    z = true;
                }
                i2++;
                i = i3;
            }
            z2 = false;
            i2++;
            i = i3;
        }
        int length = stringBuffer.length();
        if (length == 0 || stringBuffer.charAt(length - 1) == ':') {
            stringBuffer.append('_');
        }
        return stringBuffer.toString().intern();
    }

    @Override // org.xml.sax.XMLReader
    public final ContentHandler getContentHandler() {
        ContentHandler contentHandler = this.a;
        if (contentHandler == this) {
            return null;
        }
        return contentHandler;
    }

    @Override // org.xml.sax.XMLReader
    public final DTDHandler getDTDHandler() {
        DTDHandler dTDHandler = this.c;
        if (dTDHandler == this) {
            return null;
        }
        return dTDHandler;
    }

    @Override // org.xml.sax.XMLReader
    public final EntityResolver getEntityResolver() {
        EntityResolver entityResolver = this.e;
        if (entityResolver == this) {
            return null;
        }
        return entityResolver;
    }

    @Override // org.xml.sax.XMLReader
    public final ErrorHandler getErrorHandler() {
        ErrorHandler errorHandler = this.d;
        if (errorHandler == this) {
            return null;
        }
        return errorHandler;
    }

    @Override // org.xml.sax.XMLReader
    public final boolean getFeature(String str) throws SAXNotRecognizedException {
        Boolean bool = (Boolean) this.r.get(str);
        if (bool != null) {
            return bool.booleanValue();
        }
        StringBuffer stringBuffer = new StringBuffer("Unknown feature ");
        stringBuffer.append(str);
        throw new SAXNotRecognizedException(stringBuffer.toString());
    }

    @Override // org.xml.sax.XMLReader
    public final Object getProperty(String str) throws SAXNotRecognizedException {
        if (str.equals("http://xml.org/sax/properties/lexical-handler")) {
            LexicalHandler lexicalHandler = this.b;
            if (lexicalHandler == this) {
                return null;
            }
            return lexicalHandler;
        }
        if (str.equals("http://www.ccil.org/~cowan/tagsoup/properties/scanner")) {
            return this.g;
        }
        if (str.equals("http://www.ccil.org/~cowan/tagsoup/properties/schema")) {
            return this.f;
        }
        if (str.equals("http://www.ccil.org/~cowan/tagsoup/properties/auto-detector")) {
            return this.h;
        }
        throw new SAXNotRecognizedException("Unknown property ".concat(str));
    }

    public final void h(char[] cArr, int i) {
        if (i == 0) {
            return;
        }
        boolean z = true;
        for (int i2 = 0; i2 < i; i2++) {
            if (!Character.isWhitespace(cArr[i2])) {
                z = false;
            }
        }
        if (!z || this.z.a(this.B)) {
            m(this.B);
            this.a.characters(cArr, 0, i);
        } else if (this.p) {
            this.a.ignorableWhitespace(cArr, 0, i);
        }
    }

    public final void i(char[] cArr, int i) throws SAXException {
        String str;
        if (this.s != null || (str = this.y) == null || "xml".equalsIgnoreCase(str)) {
            return;
        }
        if (i > 0 && cArr[i - 1] == '?') {
            i--;
        }
        this.a.processingInstruction(this.y, new String(cArr, 0, i));
        this.y = null;
    }

    public final void j() throws SAXException {
        Ea ea = this.z;
        if (ea == null) {
            return;
        }
        Fa fa = ea.a;
        String str = fa.a;
        String str2 = fa.c;
        String str3 = fa.b;
        String strK = k(str);
        if (!this.i) {
            str2 = "";
            str3 = "";
        }
        this.a.endElement(str3, str2, str);
        if (d(strK, str3)) {
            this.a.endPrefixMapping(strK);
        }
        C0545r4 c0545r4 = this.z.b;
        for (int i = c0545r4.a - 1; i >= 0; i--) {
            String uri = c0545r4.getURI(i);
            String strK2 = k(c0545r4.getQName(i));
            if (d(strK2, uri)) {
                this.a.endPrefixMapping(strK2);
            }
        }
        this.z = this.z.c;
    }

    public final void l(Ea ea) throws SAXException {
        Fa fa = ea.a;
        String str = fa.a;
        String str2 = fa.c;
        String str3 = fa.b;
        String strK = k(str);
        C0545r4 c0545r4 = ea.b;
        for (int i = c0545r4.a - 1; i >= 0; i--) {
            String localName = c0545r4.getLocalName(i);
            if (c0545r4.getValue(i) == null || localName == null || localName.length() == 0) {
                c0545r4.a(i);
            }
        }
        if (!this.i) {
            str2 = "";
            str3 = "";
        }
        if (this.C && str2.equalsIgnoreCase(this.x)) {
            try {
                this.e.resolveEntity(this.v, this.w);
            } catch (IOException unused) {
            }
        }
        if (d(strK, str3)) {
            this.a.startPrefixMapping(strK, str3);
        }
        int i2 = c0545r4.a;
        for (int i3 = 0; i3 < i2; i3++) {
            String uri = c0545r4.getURI(i3);
            String strK2 = k(c0545r4.getQName(i3));
            if (d(strK2, uri)) {
                this.a.startPrefixMapping(strK2, uri);
            }
        }
        this.a.startElement(str3, str2, str, c0545r4);
        ea.c = this.z;
        this.z = ea;
        this.C = false;
        if (!this.q || (ea.a.f & 2) == 0) {
            return;
        }
        this.g.h = 10;
    }

    public final void m(Ea ea) throws SAXException {
        Ea ea2;
        Ea ea3;
        Fa fa;
        while (true) {
            ea2 = this.z;
            while (ea2 != null && !ea2.a(ea)) {
                ea2 = ea2.c;
            }
            if (ea2 != null || (fa = ea.a.h) == null) {
                break;
            }
            Ea ea4 = new Ea(fa, this.m);
            ea4.c = ea;
            ea = ea4;
        }
        if (ea2 == null) {
            return;
        }
        while (true) {
            Ea ea5 = this.z;
            if (ea5 == ea2 || ea5 == null || (ea3 = ea5.c) == null || ea3.c == null) {
                break;
            } else {
                o();
            }
        }
        while (ea != null) {
            Ea ea6 = ea.c;
            if (!ea.a.a.equals("<pcdata>")) {
                l(ea);
            }
            n(ea6);
            ea = ea6;
        }
        this.s = null;
    }

    public final void n(Ea ea) throws SAXException {
        while (true) {
            Ea ea2 = this.A;
            if (ea2 == null || !this.z.a(ea2)) {
                return;
            }
            if (ea != null && !this.A.a(ea)) {
                return;
            }
            Ea ea3 = this.A;
            Ea ea4 = ea3.c;
            l(ea3);
            this.A = ea4;
        }
    }

    public final void o() throws SAXException {
        Ea ea = this.z;
        j();
        if (!this.o || (ea.a.f & 1) == 0) {
            return;
        }
        C0545r4 c0545r4 = ea.b;
        for (int i = c0545r4.a - 1; i >= 0; i--) {
            if (c0545r4.getType(i).equals("ID") || c0545r4.getQName(i).equals("name")) {
                c0545r4.a(i);
            }
        }
        ea.c = this.A;
        this.A = ea;
    }

    public final void p(char[] cArr, int i) throws SAXException {
        Ea ea = this.s;
        if (ea == null) {
            return;
        }
        m(ea);
        if (this.z.a.d == 0) {
            c(cArr, i);
        }
    }

    @Override // org.xml.sax.XMLReader
    public final void parse(String str) throws SAXException, IOException {
        parse(new InputSource(str));
    }

    @Override // org.xml.sax.XMLReader
    public final void setContentHandler(ContentHandler contentHandler) {
        if (contentHandler == null) {
            contentHandler = this;
        }
        this.a = contentHandler;
    }

    @Override // org.xml.sax.XMLReader
    public final void setDTDHandler(DTDHandler dTDHandler) {
        if (dTDHandler == null) {
            dTDHandler = this;
        }
        this.c = dTDHandler;
    }

    @Override // org.xml.sax.XMLReader
    public final void setEntityResolver(EntityResolver entityResolver) {
        if (entityResolver == null) {
            entityResolver = this;
        }
        this.e = entityResolver;
    }

    @Override // org.xml.sax.XMLReader
    public final void setErrorHandler(ErrorHandler errorHandler) {
        if (errorHandler == null) {
            errorHandler = this;
        }
        this.d = errorHandler;
    }

    @Override // org.xml.sax.XMLReader
    public final void setFeature(String str, boolean z) throws SAXNotRecognizedException {
        HashMap map = this.r;
        if (((Boolean) map.get(str)) == null) {
            StringBuffer stringBuffer = new StringBuffer("Unknown feature ");
            stringBuffer.append(str);
            throw new SAXNotRecognizedException(stringBuffer.toString());
        }
        if (z) {
            map.put(str, Boolean.TRUE);
        } else {
            map.put(str, Boolean.FALSE);
        }
        if (str.equals("http://xml.org/sax/features/namespaces")) {
            this.i = z;
            return;
        }
        if (str.equals("http://www.ccil.org/~cowan/tagsoup/features/ignore-bogons")) {
            this.j = z;
            return;
        }
        if (str.equals("http://www.ccil.org/~cowan/tagsoup/features/bogons-empty")) {
            this.k = z;
            return;
        }
        if (str.equals("http://www.ccil.org/~cowan/tagsoup/features/root-bogons")) {
            this.l = z;
            return;
        }
        if (str.equals("http://www.ccil.org/~cowan/tagsoup/features/default-attributes")) {
            this.m = z;
            return;
        }
        if (str.equals("http://www.ccil.org/~cowan/tagsoup/features/translate-colons")) {
            this.n = z;
            return;
        }
        if (str.equals("http://www.ccil.org/~cowan/tagsoup/features/restart-elements")) {
            this.o = z;
        } else if (str.equals("http://www.ccil.org/~cowan/tagsoup/features/ignorable-whitespace")) {
            this.p = z;
        } else if (str.equals("http://www.ccil.org/~cowan/tagsoup/features/cdata-elements")) {
            this.q = z;
        }
    }

    @Override // org.xml.sax.XMLReader
    public final void setProperty(String str, Object obj) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (str.equals("http://xml.org/sax/properties/lexical-handler")) {
            if (obj == null) {
                this.b = this;
                return;
            } else {
                if (!(obj instanceof LexicalHandler)) {
                    throw new SAXNotSupportedException("Your lexical handler is not a LexicalHandler");
                }
                this.b = (LexicalHandler) obj;
                return;
            }
        }
        if (str.equals("http://www.ccil.org/~cowan/tagsoup/properties/scanner")) {
            if (!(obj instanceof Zd)) {
                throw new SAXNotSupportedException("Your scanner is not a Scanner");
            }
            this.g = (Zd) obj;
        } else if (str.equals("http://www.ccil.org/~cowan/tagsoup/properties/schema")) {
            if (!(obj instanceof C0032ae)) {
                throw new SAXNotSupportedException("Your schema is not a Schema");
            }
            this.f = (C0032ae) obj;
        } else {
            if (!str.equals("http://www.ccil.org/~cowan/tagsoup/properties/auto-detector")) {
                throw new SAXNotRecognizedException("Unknown property ".concat(str));
            }
            if (!(obj instanceof Mj)) {
                throw new SAXNotSupportedException("Your auto-detector is not an AutoDetector");
            }
            this.h = (Mj) obj;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:100:0x0236  */
    /* JADX WARN: Code duplicated, block: B:101:0x0243  */
    /* JADX WARN: Code duplicated, block: B:102:0x0250  */
    /* JADX WARN: Code duplicated, block: B:103:0x0257  */
    /* JADX WARN: Code duplicated, block: B:104:0x025d  */
    /* JADX WARN: Code duplicated, block: B:105:0x0265  */
    /* JADX WARN: Code duplicated, block: B:106:0x0276  */
    /* JADX WARN: Code duplicated, block: B:107:0x0281  */
    /* JADX WARN: Code duplicated, block: B:108:0x0291  */
    /* JADX WARN: Code duplicated, block: B:109:0x029c  */
    /* JADX WARN: Code duplicated, block: B:111:0x02aa  */
    /* JADX WARN: Code duplicated, block: B:126:0x02ec  */
    /* JADX WARN: Code duplicated, block: B:129:0x02f0  */
    /* JADX WARN: Code duplicated, block: B:131:0x02f7  */
    /* JADX WARN: Code duplicated, block: B:132:0x0305  */
    /* JADX WARN: Code duplicated, block: B:134:0x0313 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:138:0x0320  */
    /* JADX WARN: Code duplicated, block: B:143:0x032f A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:144:0x0331  */
    /* JADX WARN: Code duplicated, block: B:147:0x033c  */
    /* JADX WARN: Code duplicated, block: B:149:0x0340  */
    /* JADX WARN: Code duplicated, block: B:152:0x034b  */
    /* JADX WARN: Code duplicated, block: B:154:0x034f  */
    /* JADX WARN: Code duplicated, block: B:161:0x036d  */
    /* JADX WARN: Code duplicated, block: B:163:0x0373  */
    /* JADX WARN: Code duplicated, block: B:167:0x037c  */
    /* JADX WARN: Code duplicated, block: B:168:0x037f  */
    /* JADX WARN: Code duplicated, block: B:175:0x038f  */
    /* JADX WARN: Code duplicated, block: B:176:0x0393  */
    /* JADX WARN: Code duplicated, block: B:179:0x03ab A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:180:0x03ad  */
    /* JADX WARN: Code duplicated, block: B:183:0x03b8  */
    /* JADX WARN: Code duplicated, block: B:185:0x03be  */
    /* JADX WARN: Code duplicated, block: B:189:0x03cd  */
    /* JADX WARN: Code duplicated, block: B:191:0x03dc  */
    /* JADX WARN: Code duplicated, block: B:195:0x03ea  */
    /* JADX WARN: Code duplicated, block: B:196:0x03f1  */
    /* JADX WARN: Code duplicated, block: B:198:0x040c  */
    /* JADX WARN: Code duplicated, block: B:199:0x0412  */
    /* JADX WARN: Code duplicated, block: B:201:0x0423  */
    /* JADX WARN: Code duplicated, block: B:203:0x042d  */
    /* JADX WARN: Code duplicated, block: B:208:0x0437 A[PHI: r15 r18
  0x0437: PHI (r15v8 boolean) = (r15v13 boolean), (r15v14 boolean), (r15v15 boolean) binds: [B:224:0x045d, B:214:0x0443, B:207:0x0435] A[DONT_GENERATE, DONT_INLINE]
  0x0437: PHI (r18v6 boolean) = (r18v12 boolean), (r18v13 boolean), (r18v14 boolean) binds: [B:224:0x045d, B:214:0x0443, B:207:0x0435] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:209:0x0439 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:210:0x043b  */
    /* JADX WARN: Code duplicated, block: B:216:0x0446 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:217:0x0448 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:230:0x047e  */
    /* JADX WARN: Code duplicated, block: B:252:0x04cc  */
    /* JADX WARN: Code duplicated, block: B:255:0x04dd A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:256:0x04df  */
    /* JADX WARN: Code duplicated, block: B:257:0x04e2  */
    /* JADX WARN: Code duplicated, block: B:259:0x04ef  */
    /* JADX WARN: Code duplicated, block: B:261:0x04fc  */
    /* JADX WARN: Code duplicated, block: B:263:0x0503 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:265:0x0506  */
    /* JADX WARN: Code duplicated, block: B:270:0x052d  */
    /* JADX WARN: Code duplicated, block: B:274:0x054c  */
    /* JADX WARN: Code duplicated, block: B:275:0x0560  */
    /* JADX WARN: Code duplicated, block: B:277:0x056d  */
    /* JADX WARN: Code duplicated, block: B:279:0x057c  */
    /* JADX WARN: Code duplicated, block: B:280:0x0592  */
    /* JADX WARN: Code duplicated, block: B:281:0x05a3  */
    /* JADX WARN: Code duplicated, block: B:284:0x05b2  */
    /* JADX WARN: Code duplicated, block: B:286:0x05ca  */
    /* JADX WARN: Code duplicated, block: B:289:0x05d9  */
    /* JADX WARN: Code duplicated, block: B:291:0x05e9  */
    /* JADX WARN: Code duplicated, block: B:294:0x05f8  */
    /* JADX WARN: Code duplicated, block: B:296:0x0605  */
    /* JADX WARN: Code duplicated, block: B:297:0x0616  */
    /* JADX WARN: Code duplicated, block: B:298:0x0625  */
    /* JADX WARN: Code duplicated, block: B:323:0x01bc A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:324:0x0640 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:342:0x0460 A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:78:0x01d0  */
    /* JADX WARN: Code duplicated, block: B:80:0x01d4  */
    /* JADX WARN: Code duplicated, block: B:82:0x01e0  */
    /* JADX WARN: Code duplicated, block: B:84:0x01e8 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:85:0x01ea  */
    /* JADX WARN: Code duplicated, block: B:87:0x01f3  */
    /* JADX WARN: Code duplicated, block: B:88:0x01fd  */
    /* JADX WARN: Code duplicated, block: B:89:0x0201  */
    /* JADX WARN: Code duplicated, block: B:90:0x0205  */
    /* JADX WARN: Code duplicated, block: B:93:0x020e  */
    /* JADX WARN: Code duplicated, block: B:95:0x0220  */
    /* JADX WARN: Code duplicated, block: B:98:0x0229  */
    /* JADX WARN: Type inference failed for: r1v57, types: [boolean] */
    @Override // org.xml.sax.XMLReader
    public final void parse(InputSource inputSource) throws SAXException, IOException {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        char[] cArr;
        int i6;
        char[] cArr2;
        int i7;
        char[] cArr3;
        int i8;
        int i9;
        String strTrim;
        int length;
        char c;
        int i10;
        boolean z;
        boolean z2;
        int i11;
        String[] strArr;
        String str;
        String str2;
        String str3;
        String strQ;
        String strQ2;
        int length2;
        StringBuffer stringBuffer;
        int i12;
        boolean z3;
        String strTrim2;
        char cCharAt;
        Zd zd;
        int i13;
        char[] cArr4;
        Ea ea;
        int i14;
        int iF;
        char[] cArr5;
        int i15;
        int i16;
        char[] cArr6;
        int i17;
        char[] cArr7;
        int i18;
        int i19;
        int i20 = 10;
        int i21 = 128;
        int i22 = 2;
        int i23 = 1;
        if (this.f == null) {
            this.f = new C0032ae();
        }
        if (this.g == null) {
            Zd zd2 = new Zd();
            zd2.i = new char[200];
            zd2.k = new int[]{8364, 65533, 8218, 402, 8222, 8230, 8224, 8225, 710, 8240, 352, 8249, 338, 65533, 381, 65533, 65533, 8216, 8217, 8220, 8221, 8226, 8211, 8212, 732, 8482, 353, 8250, 339, 65533, 382, 376};
            this.g = zd2;
        }
        if (this.h == null) {
            this.h = new Mj();
        }
        this.z = new Ea(this.f.d("<root>"), this.m);
        this.B = new Ea(this.f.d("<pcdata>"), this.m);
        this.s = null;
        this.t = null;
        this.y = null;
        this.A = null;
        this.C = true;
        this.w = null;
        this.v = null;
        this.x = null;
        Reader characterStream = inputSource.getCharacterStream();
        InputStream byteStream = inputSource.getByteStream();
        String encoding = inputSource.getEncoding();
        inputSource.getPublicId();
        String systemId = inputSource.getSystemId();
        if (characterStream == null) {
            if (byteStream == null) {
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append(System.getProperty("user.dir"));
                stringBuffer2.append("/.");
                byteStream = new URL(new URL("file", "", stringBuffer2.toString()), systemId).openConnection().getInputStream();
            }
            if (encoding == null) {
                this.h.getClass();
                characterStream = new InputStreamReader(byteStream);
            } else {
                try {
                    characterStream = new InputStreamReader(byteStream, encoding);
                } catch (UnsupportedEncodingException unused) {
                    characterStream = new InputStreamReader(byteStream);
                }
            }
        }
        this.a.startDocument();
        Zd zd3 = this.g;
        String publicId = inputSource.getPublicId();
        String systemId2 = inputSource.getSystemId();
        zd3.a = publicId;
        zd3.b = systemId2;
        zd3.f = 0;
        zd3.e = 0;
        zd3.d = 0;
        zd3.c = 0;
        Zd zd4 = this.g;
        if (zd4 instanceof Locator) {
            this.a.setDocumentLocator(zd4);
        }
        if (!this.f.c.equals("")) {
            ContentHandler contentHandler = this.a;
            C0032ae c0032ae = this.f;
            contentHandler.startPrefixMapping(c0032ae.d, c0032ae.c);
        }
        Zd zd5 = this.g;
        zd5.g = 28;
        PushbackReader pushbackReader = characterStream instanceof PushbackReader ? (PushbackReader) characterStream : characterStream instanceof BufferedReader ? new PushbackReader(characterStream) : new PushbackReader(new BufferedReader(characterStream));
        int i24 = pushbackReader.read();
        int i25 = -1;
        if (i24 != 65279 && i24 != -1) {
            pushbackReader.unread(i24);
        }
        while (zd5.g != 21) {
            int i26 = pushbackReader.read();
            int[] iArr = zd5.k;
            if (i26 >= i21 && i26 <= 159) {
                i26 = iArr[i26 - i21];
            }
            if (i26 == 13 && (i26 = pushbackReader.read()) != i20) {
                if (i26 != i25) {
                    pushbackReader.unread(i26);
                }
                i26 = i20;
            }
            if (i26 == i20) {
                zd5.e += i23;
                zd5.f = 0;
            } else {
                zd5.f += i23;
            }
            if (i26 >= 32 || i26 == i20 || i26 == 9 || i26 == i25) {
                int i27 = 0;
                for (int i28 = 0; i28 < 592; i28 += 4) {
                    int i29 = zd5.g;
                    int[] iArr2 = Zd.l;
                    if (i29 == iArr2[i28]) {
                        int i30 = iArr2[i28 + 1];
                        if (i30 == 0) {
                            i27 = iArr2[i28 + 2];
                            zd5.h = iArr2[i28 + 3];
                        } else if (i30 == i26) {
                            i27 = iArr2[i28 + 2];
                            zd5.h = iArr2[i28 + 3];
                            i = i27;
                            switch (i) {
                                case 0:
                                    StringBuffer stringBuffer3 = new StringBuffer("HTMLScanner can't cope with ");
                                    stringBuffer3.append(Integer.toString(i26));
                                    stringBuffer3.append(" in state ");
                                    stringBuffer3.append(Integer.toString(zd5.g));
                                    throw new Error(stringBuffer3.toString());
                                case 1:
                                    i2 = i22;
                                    i3 = i23;
                                    i4 = i25;
                                    i5 = 10;
                                    a();
                                    zd5.j = 0;
                                    zd5.g = zd5.h;
                                    i25 = i4;
                                    i22 = i2;
                                    i21 = 128;
                                    i23 = i3;
                                    i20 = i5;
                                    break;
                                case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                                    i2 = i22;
                                    i3 = i23;
                                    i4 = i25;
                                    i5 = 10;
                                    a();
                                    zd5.j = 0;
                                    zd5.b(i26, this);
                                    zd5.g = zd5.h;
                                    i25 = i4;
                                    i22 = i2;
                                    i21 = 128;
                                    i23 = i3;
                                    i20 = i5;
                                    break;
                                case 3:
                                    i2 = i22;
                                    i3 = i23;
                                    i4 = i25;
                                    i5 = 10;
                                    a();
                                    zd5.j = 0;
                                    p(zd5.i, 0);
                                    zd5.g = zd5.h;
                                    i25 = i4;
                                    i22 = i2;
                                    i21 = 128;
                                    i23 = i3;
                                    i20 = i5;
                                    break;
                                case 4:
                                    i2 = i22;
                                    i3 = i23;
                                    i4 = i25;
                                    i5 = 10;
                                    cArr = zd5.i;
                                    i6 = zd5.j;
                                    if (this.s != null) {
                                        this.t = g(cArr, i6).toLowerCase();
                                    }
                                    zd5.j = 0;
                                    zd5.g = zd5.h;
                                    i25 = i4;
                                    i22 = i2;
                                    i21 = 128;
                                    i23 = i3;
                                    i20 = i5;
                                    break;
                                case BulletSpan.STANDARD_BULLET_RADIUS /* 5 */:
                                    i2 = i22;
                                    i3 = i23;
                                    i4 = i25;
                                    i5 = 10;
                                    cArr2 = zd5.i;
                                    i7 = zd5.j;
                                    if (this.s != null) {
                                        this.t = g(cArr2, i7).toLowerCase();
                                    }
                                    zd5.j = 0;
                                    a();
                                    zd5.g = zd5.h;
                                    i25 = i4;
                                    i22 = i2;
                                    i21 = 128;
                                    i23 = i3;
                                    i20 = i5;
                                    break;
                                case 6:
                                    i2 = i22;
                                    i3 = i23;
                                    i4 = i25;
                                    i5 = 10;
                                    cArr3 = zd5.i;
                                    i8 = zd5.j;
                                    if (this.s != null) {
                                        this.t = g(cArr3, i8).toLowerCase();
                                    }
                                    zd5.j = 0;
                                    a();
                                    p(zd5.i, zd5.j);
                                    zd5.g = zd5.h;
                                    i25 = i4;
                                    i22 = i2;
                                    i21 = 128;
                                    i23 = i3;
                                    i20 = i5;
                                    break;
                                case 7:
                                    i2 = i22;
                                    i3 = i23;
                                    i4 = i25;
                                    i5 = 10;
                                    b(zd5.i, zd5.j);
                                    zd5.j = 0;
                                    zd5.g = zd5.h;
                                    i25 = i4;
                                    i22 = i2;
                                    i21 = 128;
                                    i23 = i3;
                                    i20 = i5;
                                    break;
                                case 8:
                                    i2 = i22;
                                    i3 = i23;
                                    i4 = i25;
                                    i5 = 10;
                                    b(zd5.i, zd5.j);
                                    zd5.j = 0;
                                    p(zd5.i, 0);
                                    zd5.g = zd5.h;
                                    i25 = i4;
                                    i22 = i2;
                                    i21 = 128;
                                    i23 = i3;
                                    i20 = i5;
                                    break;
                                case 9:
                                    i2 = i22;
                                    i4 = i25;
                                    i5 = 10;
                                    zd5.a();
                                    i9 = zd5.j;
                                    i3 = 1;
                                    if (i9 > 1) {
                                        zd5.j = i9 - 2;
                                    }
                                    h(zd5.i, zd5.j);
                                    zd5.j = 0;
                                    zd5.g = zd5.h;
                                    i25 = i4;
                                    i22 = i2;
                                    i21 = 128;
                                    i23 = i3;
                                    i20 = i5;
                                    break;
                                case 10:
                                    i2 = i22;
                                    i4 = i25;
                                    i5 = 10;
                                    zd5.a();
                                    this.b.comment(zd5.i, 0, zd5.j);
                                    zd5.j = 0;
                                    i3 = 1;
                                    zd5.g = zd5.h;
                                    i25 = i4;
                                    i22 = i2;
                                    i21 = 128;
                                    i23 = i3;
                                    i20 = i5;
                                    break;
                                case 11:
                                    i2 = i22;
                                    i5 = 10;
                                    strTrim = new String(zd5.i, 0, zd5.j).trim();
                                    if (strTrim.length() == 0) {
                                        strArr = new String[0];
                                        i11 = i23;
                                    } else {
                                        ArrayList arrayList = new ArrayList();
                                        length = strTrim.length();
                                        c = 0;
                                        i10 = 0;
                                        int i31 = 0;
                                        z = false;
                                        z2 = false;
                                        while (i10 < length) {
                                            char cCharAt2 = strTrim.charAt(i10);
                                            int i32 = i23;
                                            if (!z) {
                                                if (!z2) {
                                                    if (z2) {
                                                    }
                                                } else if (z2) {
                                                }
                                            } else if (!z2) {
                                                if (z2) {
                                                }
                                            } else if (z2) {
                                            }
                                            i10++;
                                            c = cCharAt2;
                                            i23 = i32;
                                            z = z;
                                            z2 = z2;
                                        }
                                        i11 = i23;
                                        arrayList.add(strTrim.substring(i31, i10));
                                        strArr = (String[]) arrayList.toArray(new String[0]);
                                    }
                                    if (strArr.length > 0) {
                                        str = null;
                                        str2 = null;
                                        str3 = null;
                                        strQ = q(str);
                                        strQ2 = q(str3);
                                        if (str2 != null) {
                                            if (strQ == null) {
                                                strTrim2 = null;
                                                i4 = -1;
                                            } else {
                                                length2 = strQ.length();
                                                stringBuffer = new StringBuffer(length2);
                                                z3 = true;
                                                while (i12 < length2) {
                                                    cCharAt = strQ.charAt(i12);
                                                    if ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-'()+,./:=?;!*#@$_%".indexOf(cCharAt) != -1) {
                                                        stringBuffer.append(cCharAt);
                                                        z3 = false;
                                                    } else if (!z3) {
                                                        stringBuffer.append(' ');
                                                        z3 = true;
                                                    }
                                                }
                                                i4 = -1;
                                                strTrim2 = stringBuffer.toString().trim();
                                            }
                                            this.b.startDTD(str2, strTrim2, strQ2);
                                            this.b.endDTD();
                                            this.x = str2;
                                            this.v = strTrim2;
                                            zd = this.g;
                                            if (zd instanceof Locator) {
                                                this.w = zd.getSystemId();
                                                this.w = new URL(new URL(this.w), strQ2).toString();
                                            }
                                        }
                                        zd5.j = 0;
                                        i3 = 1;
                                        zd5.g = zd5.h;
                                        i25 = i4;
                                        i22 = i2;
                                        i21 = 128;
                                        i23 = i3;
                                        i20 = i5;
                                    } else {
                                        str = null;
                                        str2 = null;
                                        str3 = null;
                                        strQ = q(str);
                                        strQ2 = q(str3);
                                        if (str2 != null) {
                                            if (strQ == null) {
                                                strTrim2 = null;
                                                i4 = -1;
                                            } else {
                                                length2 = strQ.length();
                                                stringBuffer = new StringBuffer(length2);
                                                z3 = true;
                                                for (i12 = 0; i12 < length2; i12++) {
                                                    cCharAt = strQ.charAt(i12);
                                                    if ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-'()+,./:=?;!*#@$_%".indexOf(cCharAt) != -1) {
                                                        stringBuffer.append(cCharAt);
                                                        z3 = false;
                                                    } else if (!z3) {
                                                        stringBuffer.append(' ');
                                                        z3 = true;
                                                    }
                                                }
                                                i4 = -1;
                                                strTrim2 = stringBuffer.toString().trim();
                                            }
                                            this.b.startDTD(str2, strTrim2, strQ2);
                                            this.b.endDTD();
                                            this.x = str2;
                                            this.v = strTrim2;
                                            zd = this.g;
                                            if (zd instanceof Locator) {
                                                this.w = zd.getSystemId();
                                                this.w = new URL(new URL(this.w), strQ2).toString();
                                            }
                                        }
                                        zd5.j = 0;
                                        i3 = 1;
                                        zd5.g = zd5.h;
                                        i25 = i4;
                                        i22 = i2;
                                        i21 = 128;
                                        i23 = i3;
                                        i20 = i5;
                                    }
                                    i4 = -1;
                                    zd5.j = 0;
                                    i3 = 1;
                                    zd5.g = zd5.h;
                                    i25 = i4;
                                    i22 = i2;
                                    i21 = 128;
                                    i23 = i3;
                                    i20 = i5;
                                    break;
                                case 12:
                                    i2 = i22;
                                    i5 = 10;
                                    zd5.a();
                                    i13 = zd5.j;
                                    if (i13 > 0) {
                                        e(zd5.i, i13);
                                    }
                                    zd5.j = 0;
                                    cArr4 = zd5.i;
                                    ea = this.s;
                                    if (ea != null) {
                                        m(ea);
                                        c(cArr4, 0);
                                    }
                                    i3 = i23;
                                    i4 = i25;
                                    zd5.g = zd5.h;
                                    i25 = i4;
                                    i22 = i2;
                                    i21 = 128;
                                    i23 = i3;
                                    i20 = i5;
                                    break;
                                case 13:
                                    zd5.a();
                                    char c2 = (char) i26;
                                    i14 = zd5.g;
                                    if (i14 == 23) {
                                    }
                                    i2 = i22;
                                    if (i14 != 27) {
                                        if (i14 != 23) {
                                            if (zd5.g != 27) {
                                                if (zd5.g == 35) {
                                                }
                                                iF = f(zd5.i, i23, zd5.j - i23);
                                                if (iF != 0) {
                                                    zd5.j = 0;
                                                    if (iF >= 128) {
                                                        iF = iArr[iF - 128];
                                                    }
                                                    if (iF >= 32) {
                                                        i5 = 10;
                                                    } else if (iF <= 65535) {
                                                        zd5.b(iF, this);
                                                        i5 = 10;
                                                    } else {
                                                        int i33 = iF - 65536;
                                                        i5 = 10;
                                                        zd5.b((i33 >> 10) + 55296, this);
                                                        zd5.b((i33 & 1023) + 56320, this);
                                                    }
                                                    if (i26 != 59) {
                                                        if (i26 != i25) {
                                                            pushbackReader.unread(i26);
                                                        }
                                                        zd5.f -= i23;
                                                    }
                                                } else {
                                                    i5 = 10;
                                                    if (i26 != i25) {
                                                        pushbackReader.unread(i26);
                                                    }
                                                    zd5.f -= i23;
                                                }
                                                zd5.h = 28;
                                                i3 = i23;
                                                i4 = i25;
                                                zd5.g = zd5.h;
                                                i25 = i4;
                                                i22 = i2;
                                                i21 = 128;
                                                i23 = i3;
                                                i20 = i5;
                                            } else {
                                                if (zd5.g == 35) {
                                                }
                                                iF = f(zd5.i, i23, zd5.j - i23);
                                                if (iF != 0) {
                                                    zd5.j = 0;
                                                    if (iF >= 128) {
                                                        iF = iArr[iF - 128];
                                                    }
                                                    if (iF >= 32) {
                                                        i5 = 10;
                                                    } else if (iF <= 65535) {
                                                        zd5.b(iF, this);
                                                        i5 = 10;
                                                    } else {
                                                        int i34 = iF - 65536;
                                                        i5 = 10;
                                                        zd5.b((i34 >> 10) + 55296, this);
                                                        zd5.b((i34 & 1023) + 56320, this);
                                                    }
                                                    if (i26 != 59) {
                                                        if (i26 != i25) {
                                                            pushbackReader.unread(i26);
                                                        }
                                                        zd5.f -= i23;
                                                    }
                                                } else {
                                                    i5 = 10;
                                                    if (i26 != i25) {
                                                        pushbackReader.unread(i26);
                                                    }
                                                    zd5.f -= i23;
                                                }
                                                zd5.h = 28;
                                                i3 = i23;
                                                i4 = i25;
                                                zd5.g = zd5.h;
                                                i25 = i4;
                                                i22 = i2;
                                                i21 = 128;
                                                i23 = i3;
                                                i20 = i5;
                                            }
                                        } else if (zd5.g != 27) {
                                            if (zd5.g == 35) {
                                            }
                                            iF = f(zd5.i, i23, zd5.j - i23);
                                            if (iF != 0) {
                                                zd5.j = 0;
                                                if (iF >= 128) {
                                                    iF = iArr[iF - 128];
                                                }
                                                if (iF >= 32) {
                                                    i5 = 10;
                                                } else if (iF <= 65535) {
                                                    zd5.b(iF, this);
                                                    i5 = 10;
                                                } else {
                                                    int i35 = iF - 65536;
                                                    i5 = 10;
                                                    zd5.b((i35 >> 10) + 55296, this);
                                                    zd5.b((i35 & 1023) + 56320, this);
                                                }
                                                if (i26 != 59) {
                                                    if (i26 != i25) {
                                                        pushbackReader.unread(i26);
                                                    }
                                                    zd5.f -= i23;
                                                }
                                            } else {
                                                i5 = 10;
                                                if (i26 != i25) {
                                                    pushbackReader.unread(i26);
                                                }
                                                zd5.f -= i23;
                                            }
                                            zd5.h = 28;
                                            i3 = i23;
                                            i4 = i25;
                                            zd5.g = zd5.h;
                                            i25 = i4;
                                            i22 = i2;
                                            i21 = 128;
                                            i23 = i3;
                                            i20 = i5;
                                        } else {
                                            if (zd5.g == 35) {
                                            }
                                            iF = f(zd5.i, i23, zd5.j - i23);
                                            if (iF != 0) {
                                                zd5.j = 0;
                                                if (iF >= 128) {
                                                    iF = iArr[iF - 128];
                                                }
                                                if (iF >= 32) {
                                                    i5 = 10;
                                                } else if (iF <= 65535) {
                                                    zd5.b(iF, this);
                                                    i5 = 10;
                                                } else {
                                                    int i36 = iF - 65536;
                                                    i5 = 10;
                                                    zd5.b((i36 >> 10) + 55296, this);
                                                    zd5.b((i36 & 1023) + 56320, this);
                                                }
                                                if (i26 != 59) {
                                                    if (i26 != i25) {
                                                        pushbackReader.unread(i26);
                                                    }
                                                    zd5.f -= i23;
                                                }
                                            } else {
                                                i5 = 10;
                                                if (i26 != i25) {
                                                    pushbackReader.unread(i26);
                                                }
                                                zd5.f -= i23;
                                            }
                                            zd5.h = 28;
                                            i3 = i23;
                                            i4 = i25;
                                            zd5.g = zd5.h;
                                            i25 = i4;
                                            i22 = i2;
                                            i21 = 128;
                                            i23 = i3;
                                            i20 = i5;
                                        }
                                    } else if (i14 != 23) {
                                        if (zd5.g != 27) {
                                            if (zd5.g == 35) {
                                            }
                                            iF = f(zd5.i, i23, zd5.j - i23);
                                            if (iF != 0) {
                                                zd5.j = 0;
                                                if (iF >= 128) {
                                                    iF = iArr[iF - 128];
                                                }
                                                if (iF >= 32) {
                                                    i5 = 10;
                                                } else if (iF <= 65535) {
                                                    zd5.b(iF, this);
                                                    i5 = 10;
                                                } else {
                                                    int i37 = iF - 65536;
                                                    i5 = 10;
                                                    zd5.b((i37 >> 10) + 55296, this);
                                                    zd5.b((i37 & 1023) + 56320, this);
                                                }
                                                if (i26 != 59) {
                                                    if (i26 != i25) {
                                                        pushbackReader.unread(i26);
                                                    }
                                                    zd5.f -= i23;
                                                }
                                            } else {
                                                i5 = 10;
                                                if (i26 != i25) {
                                                    pushbackReader.unread(i26);
                                                }
                                                zd5.f -= i23;
                                            }
                                            zd5.h = 28;
                                            i3 = i23;
                                            i4 = i25;
                                            zd5.g = zd5.h;
                                            i25 = i4;
                                            i22 = i2;
                                            i21 = 128;
                                            i23 = i3;
                                            i20 = i5;
                                        } else {
                                            if (zd5.g == 35) {
                                            }
                                            iF = f(zd5.i, i23, zd5.j - i23);
                                            if (iF != 0) {
                                                zd5.j = 0;
                                                if (iF >= 128) {
                                                    iF = iArr[iF - 128];
                                                }
                                                if (iF >= 32) {
                                                    i5 = 10;
                                                } else if (iF <= 65535) {
                                                    zd5.b(iF, this);
                                                    i5 = 10;
                                                } else {
                                                    int i38 = iF - 65536;
                                                    i5 = 10;
                                                    zd5.b((i38 >> 10) + 55296, this);
                                                    zd5.b((i38 & 1023) + 56320, this);
                                                }
                                                if (i26 != 59) {
                                                    if (i26 != i25) {
                                                        pushbackReader.unread(i26);
                                                    }
                                                    zd5.f -= i23;
                                                }
                                            } else {
                                                i5 = 10;
                                                if (i26 != i25) {
                                                    pushbackReader.unread(i26);
                                                }
                                                zd5.f -= i23;
                                            }
                                            zd5.h = 28;
                                            i3 = i23;
                                            i4 = i25;
                                            zd5.g = zd5.h;
                                            i25 = i4;
                                            i22 = i2;
                                            i21 = 128;
                                            i23 = i3;
                                            i20 = i5;
                                        }
                                    } else if (zd5.g != 27) {
                                        if (zd5.g == 35) {
                                        }
                                        iF = f(zd5.i, i23, zd5.j - i23);
                                        if (iF != 0) {
                                            zd5.j = 0;
                                            if (iF >= 128) {
                                                iF = iArr[iF - 128];
                                            }
                                            if (iF >= 32) {
                                                i5 = 10;
                                            } else if (iF <= 65535) {
                                                zd5.b(iF, this);
                                                i5 = 10;
                                            } else {
                                                int i39 = iF - 65536;
                                                i5 = 10;
                                                zd5.b((i39 >> 10) + 55296, this);
                                                zd5.b((i39 & 1023) + 56320, this);
                                            }
                                            if (i26 != 59) {
                                                if (i26 != i25) {
                                                    pushbackReader.unread(i26);
                                                }
                                                zd5.f -= i23;
                                            }
                                        } else {
                                            i5 = 10;
                                            if (i26 != i25) {
                                                pushbackReader.unread(i26);
                                            }
                                            zd5.f -= i23;
                                        }
                                        zd5.h = 28;
                                        i3 = i23;
                                        i4 = i25;
                                        zd5.g = zd5.h;
                                        i25 = i4;
                                        i22 = i2;
                                        i21 = 128;
                                        i23 = i3;
                                        i20 = i5;
                                    } else {
                                        if (zd5.g == 35) {
                                        }
                                        iF = f(zd5.i, i23, zd5.j - i23);
                                        if (iF != 0) {
                                            zd5.j = 0;
                                            if (iF >= 128) {
                                                iF = iArr[iF - 128];
                                            }
                                            if (iF >= 32) {
                                                i5 = 10;
                                            } else if (iF <= 65535) {
                                                zd5.b(iF, this);
                                                i5 = 10;
                                            } else {
                                                int i310 = iF - 65536;
                                                i5 = 10;
                                                zd5.b((i310 >> 10) + 55296, this);
                                                zd5.b((i310 & 1023) + 56320, this);
                                            }
                                            if (i26 != 59) {
                                                if (i26 != i25) {
                                                    pushbackReader.unread(i26);
                                                }
                                                zd5.f -= i23;
                                            }
                                        } else {
                                            i5 = 10;
                                            if (i26 != i25) {
                                                pushbackReader.unread(i26);
                                            }
                                            zd5.f -= i23;
                                        }
                                        zd5.h = 28;
                                        i3 = i23;
                                        i4 = i25;
                                        zd5.g = zd5.h;
                                        i25 = i4;
                                        i22 = i2;
                                        i21 = 128;
                                        i23 = i3;
                                        i20 = i5;
                                    }
                                    i3 = i23;
                                    i4 = i25;
                                    i5 = 10;
                                    zd5.g = zd5.h;
                                    i25 = i4;
                                    i22 = i2;
                                    i21 = 128;
                                    i23 = i3;
                                    i20 = i5;
                                    break;
                                case 14:
                                    h(zd5.i, zd5.j);
                                    zd5.j = 0;
                                    zd5.b(i26, this);
                                    i2 = i22;
                                    i3 = i23;
                                    i4 = i25;
                                    i5 = 10;
                                    zd5.g = zd5.h;
                                    i25 = i4;
                                    i22 = i2;
                                    i21 = 128;
                                    i23 = i3;
                                    i20 = i5;
                                    break;
                                case 15:
                                    cArr5 = zd5.i;
                                    i15 = zd5.j;
                                    Fa fa = this.z.a;
                                    String str4 = fa.a;
                                    if (this.q) {
                                        i16 = 0;
                                    } else {
                                        i16 = 0;
                                    }
                                    if (i16 == 0) {
                                        c(cArr5, i15);
                                    }
                                    zd5.j = 0;
                                    i2 = i22;
                                    i3 = i23;
                                    i4 = i25;
                                    i5 = 10;
                                    zd5.g = zd5.h;
                                    i25 = i4;
                                    i22 = i2;
                                    i21 = 128;
                                    i23 = i3;
                                    i20 = i5;
                                    break;
                                case 16:
                                    e(zd5.i, zd5.j);
                                    zd5.j = 0;
                                    i2 = i22;
                                    i3 = i23;
                                    i4 = i25;
                                    i5 = 10;
                                    zd5.g = zd5.h;
                                    i25 = i4;
                                    i22 = i2;
                                    i21 = 128;
                                    i23 = i3;
                                    i20 = i5;
                                    break;
                                case 17:
                                    e(zd5.i, zd5.j);
                                    zd5.j = 0;
                                    p(zd5.i, 0);
                                    i2 = i22;
                                    i3 = i23;
                                    i4 = i25;
                                    i5 = 10;
                                    zd5.g = zd5.h;
                                    i25 = i4;
                                    i22 = i2;
                                    i21 = 128;
                                    i23 = i3;
                                    i20 = i5;
                                    break;
                                case 18:
                                    zd5.a();
                                    zd5.b(60, this);
                                    zd5.b(i26, this);
                                    i2 = i22;
                                    i3 = i23;
                                    i4 = i25;
                                    i5 = 10;
                                    zd5.g = zd5.h;
                                    i25 = i4;
                                    i22 = i2;
                                    i21 = 128;
                                    i23 = i3;
                                    i20 = i5;
                                    break;
                                case 19:
                                    zd5.a();
                                    zd5.b(60, this);
                                    h(zd5.i, zd5.j);
                                    zd5.j = 0;
                                    i2 = i22;
                                    i3 = i23;
                                    i4 = i25;
                                    i5 = 10;
                                    zd5.g = zd5.h;
                                    i25 = i4;
                                    i22 = i2;
                                    i21 = 128;
                                    i23 = i3;
                                    i20 = i5;
                                    break;
                                case 20:
                                    zd5.b(45, this);
                                    zd5.b(i26, this);
                                    i2 = i22;
                                    i3 = i23;
                                    i4 = i25;
                                    i5 = 10;
                                    zd5.g = zd5.h;
                                    i25 = i4;
                                    i22 = i2;
                                    i21 = 128;
                                    i23 = i3;
                                    i20 = i5;
                                    break;
                                case 21:
                                    zd5.b(45, this);
                                    zd5.b(32, this);
                                    zd5.b(45, this);
                                    zd5.b(i26, this);
                                    i2 = i22;
                                    i3 = i23;
                                    i4 = i25;
                                    i5 = 10;
                                    zd5.g = zd5.h;
                                    i25 = i4;
                                    i22 = i2;
                                    i21 = 128;
                                    i23 = i3;
                                    i20 = i5;
                                    break;
                                case 22:
                                    zd5.b(45, this);
                                    zd5.b(32, this);
                                    i2 = i22;
                                    i3 = i23;
                                    i4 = i25;
                                    i5 = 10;
                                    zd5.g = zd5.h;
                                    i25 = i4;
                                    i22 = i2;
                                    i21 = 128;
                                    i23 = i3;
                                    i20 = i5;
                                    break;
                                case 23:
                                    zd5.a();
                                    h(zd5.i, zd5.j);
                                    zd5.j = 0;
                                    i2 = i22;
                                    i3 = i23;
                                    i4 = i25;
                                    i5 = 10;
                                    zd5.g = zd5.h;
                                    i25 = i4;
                                    i22 = i2;
                                    i21 = 128;
                                    i23 = i3;
                                    i20 = i5;
                                    break;
                                case 24:
                                    zd5.a();
                                    i(zd5.i, zd5.j);
                                    zd5.j = 0;
                                    i2 = i22;
                                    i3 = i23;
                                    i4 = i25;
                                    i5 = 10;
                                    zd5.g = zd5.h;
                                    i25 = i4;
                                    i22 = i2;
                                    i21 = 128;
                                    i23 = i3;
                                    i20 = i5;
                                    break;
                                case 25:
                                    cArr6 = zd5.i;
                                    i17 = zd5.j;
                                    if (this.s == null) {
                                        this.y = g(cArr6, i17).replace(':', '_');
                                    }
                                    zd5.j = 0;
                                    i2 = i22;
                                    i3 = i23;
                                    i4 = i25;
                                    i5 = 10;
                                    zd5.g = zd5.h;
                                    i25 = i4;
                                    i22 = i2;
                                    i21 = 128;
                                    i23 = i3;
                                    i20 = i5;
                                    break;
                                case 26:
                                    cArr7 = zd5.i;
                                    i18 = zd5.j;
                                    if (this.s == null) {
                                        this.y = g(cArr7, i18).replace(':', '_');
                                    }
                                    zd5.j = 0;
                                    i(zd5.i, 0);
                                    i2 = i22;
                                    i3 = i23;
                                    i4 = i25;
                                    i5 = 10;
                                    zd5.g = zd5.h;
                                    i25 = i4;
                                    i22 = i2;
                                    i21 = 128;
                                    i23 = i3;
                                    i20 = i5;
                                    break;
                                case 27:
                                    zd5.b(i26, this);
                                    i2 = i22;
                                    i3 = i23;
                                    i4 = i25;
                                    i5 = 10;
                                    zd5.g = zd5.h;
                                    i25 = i4;
                                    i22 = i2;
                                    i21 = 128;
                                    i23 = i3;
                                    i20 = i5;
                                    break;
                                case 28:
                                    i2 = i22;
                                    i3 = i23;
                                    i4 = i25;
                                    i5 = 10;
                                    zd5.g = zd5.h;
                                    i25 = i4;
                                    i22 = i2;
                                    i21 = 128;
                                    i23 = i3;
                                    i20 = i5;
                                    break;
                                case 29:
                                    zd5.b(32, this);
                                    i2 = i22;
                                    i3 = i23;
                                    i4 = i25;
                                    i5 = 10;
                                    zd5.g = zd5.h;
                                    i25 = i4;
                                    i22 = i2;
                                    i21 = 128;
                                    i23 = i3;
                                    i20 = i5;
                                    break;
                                case 30:
                                    p(zd5.i, zd5.j);
                                    zd5.j = 0;
                                    i2 = i22;
                                    i3 = i23;
                                    i4 = i25;
                                    i5 = 10;
                                    zd5.g = zd5.h;
                                    i25 = i4;
                                    i22 = i2;
                                    i21 = 128;
                                    i23 = i3;
                                    i20 = i5;
                                    break;
                                case 31:
                                    if (i26 != i25) {
                                        pushbackReader.unread(i26);
                                    }
                                    zd5.f -= i23;
                                    i2 = i22;
                                    i3 = i23;
                                    i4 = i25;
                                    i5 = 10;
                                    zd5.g = zd5.h;
                                    i25 = i4;
                                    i22 = i2;
                                    i21 = 128;
                                    i23 = i3;
                                    i20 = i5;
                                    break;
                                case 32:
                                    i19 = zd5.j;
                                    if (i19 > 0) {
                                        zd5.j = i19 - i23;
                                    }
                                    h(zd5.i, zd5.j);
                                    zd5.j = 0;
                                    i2 = i22;
                                    i3 = i23;
                                    i4 = i25;
                                    i5 = 10;
                                    zd5.g = zd5.h;
                                    i25 = i4;
                                    i22 = i2;
                                    i21 = 128;
                                    i23 = i3;
                                    i20 = i5;
                                    break;
                                default:
                                    StringBuffer stringBuffer4 = new StringBuffer("Can't process state ");
                                    stringBuffer4.append(i);
                                    throw new Error(stringBuffer4.toString());
                            }
                        }
                    } else if (i27 != 0) {
                        i = i27;
                        switch (i) {
                            case 0:
                                StringBuffer stringBuffer5 = new StringBuffer("HTMLScanner can't cope with ");
                                stringBuffer5.append(Integer.toString(i26));
                                stringBuffer5.append(" in state ");
                                stringBuffer5.append(Integer.toString(zd5.g));
                                throw new Error(stringBuffer5.toString());
                            case 1:
                                i2 = i22;
                                i3 = i23;
                                i4 = i25;
                                i5 = 10;
                                a();
                                zd5.j = 0;
                                zd5.g = zd5.h;
                                i25 = i4;
                                i22 = i2;
                                i21 = 128;
                                i23 = i3;
                                i20 = i5;
                                break;
                            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                                i2 = i22;
                                i3 = i23;
                                i4 = i25;
                                i5 = 10;
                                a();
                                zd5.j = 0;
                                zd5.b(i26, this);
                                zd5.g = zd5.h;
                                i25 = i4;
                                i22 = i2;
                                i21 = 128;
                                i23 = i3;
                                i20 = i5;
                                break;
                            case 3:
                                i2 = i22;
                                i3 = i23;
                                i4 = i25;
                                i5 = 10;
                                a();
                                zd5.j = 0;
                                p(zd5.i, 0);
                                zd5.g = zd5.h;
                                i25 = i4;
                                i22 = i2;
                                i21 = 128;
                                i23 = i3;
                                i20 = i5;
                                break;
                            case 4:
                                i2 = i22;
                                i3 = i23;
                                i4 = i25;
                                i5 = 10;
                                cArr = zd5.i;
                                i6 = zd5.j;
                                if (this.s != null) {
                                    this.t = g(cArr, i6).toLowerCase();
                                }
                                zd5.j = 0;
                                zd5.g = zd5.h;
                                i25 = i4;
                                i22 = i2;
                                i21 = 128;
                                i23 = i3;
                                i20 = i5;
                                break;
                            case BulletSpan.STANDARD_BULLET_RADIUS /* 5 */:
                                i2 = i22;
                                i3 = i23;
                                i4 = i25;
                                i5 = 10;
                                cArr2 = zd5.i;
                                i7 = zd5.j;
                                if (this.s != null) {
                                    this.t = g(cArr2, i7).toLowerCase();
                                }
                                zd5.j = 0;
                                a();
                                zd5.g = zd5.h;
                                i25 = i4;
                                i22 = i2;
                                i21 = 128;
                                i23 = i3;
                                i20 = i5;
                                break;
                            case 6:
                                i2 = i22;
                                i3 = i23;
                                i4 = i25;
                                i5 = 10;
                                cArr3 = zd5.i;
                                i8 = zd5.j;
                                if (this.s != null) {
                                    this.t = g(cArr3, i8).toLowerCase();
                                }
                                zd5.j = 0;
                                a();
                                p(zd5.i, zd5.j);
                                zd5.g = zd5.h;
                                i25 = i4;
                                i22 = i2;
                                i21 = 128;
                                i23 = i3;
                                i20 = i5;
                                break;
                            case 7:
                                i2 = i22;
                                i3 = i23;
                                i4 = i25;
                                i5 = 10;
                                b(zd5.i, zd5.j);
                                zd5.j = 0;
                                zd5.g = zd5.h;
                                i25 = i4;
                                i22 = i2;
                                i21 = 128;
                                i23 = i3;
                                i20 = i5;
                                break;
                            case 8:
                                i2 = i22;
                                i3 = i23;
                                i4 = i25;
                                i5 = 10;
                                b(zd5.i, zd5.j);
                                zd5.j = 0;
                                p(zd5.i, 0);
                                zd5.g = zd5.h;
                                i25 = i4;
                                i22 = i2;
                                i21 = 128;
                                i23 = i3;
                                i20 = i5;
                                break;
                            case 9:
                                i2 = i22;
                                i4 = i25;
                                i5 = 10;
                                zd5.a();
                                i9 = zd5.j;
                                i3 = 1;
                                if (i9 > 1) {
                                    zd5.j = i9 - 2;
                                }
                                h(zd5.i, zd5.j);
                                zd5.j = 0;
                                zd5.g = zd5.h;
                                i25 = i4;
                                i22 = i2;
                                i21 = 128;
                                i23 = i3;
                                i20 = i5;
                                break;
                            case 10:
                                i2 = i22;
                                i4 = i25;
                                i5 = 10;
                                zd5.a();
                                this.b.comment(zd5.i, 0, zd5.j);
                                zd5.j = 0;
                                i3 = 1;
                                zd5.g = zd5.h;
                                i25 = i4;
                                i22 = i2;
                                i21 = 128;
                                i23 = i3;
                                i20 = i5;
                                break;
                            case 11:
                                i2 = i22;
                                i5 = 10;
                                strTrim = new String(zd5.i, 0, zd5.j).trim();
                                if (strTrim.length() == 0) {
                                    strArr = new String[0];
                                    i11 = i23;
                                } else {
                                    ArrayList arrayList2 = new ArrayList();
                                    length = strTrim.length();
                                    c = 0;
                                    i10 = 0;
                                    int i311 = 0;
                                    z = false;
                                    z2 = false;
                                    while (i10 < length) {
                                        char cCharAt3 = strTrim.charAt(i10);
                                        int i312 = i23;
                                        if (!z && cCharAt3 == '\'' && c != '\\') {
                                            z2 = !z2;
                                            if (i311 < 0) {
                                                z = z;
                                                z2 = z2;
                                                z = z;
                                                z2 = z2;
                                                i311 = i10;
                                            } else {
                                                z = z;
                                                z2 = z2;
                                            }
                                        } else if (!z2 && cCharAt3 == '\"' && c != '\\') {
                                            z = !z;
                                            if (i311 < 0) {
                                                z = z;
                                                z2 = z2;
                                                z = z;
                                                z2 = z2;
                                                i311 = i10;
                                            } else {
                                                z = z;
                                                z2 = z2;
                                            }
                                        } else if (z2 && !z) {
                                            if (Character.isWhitespace(cCharAt3)) {
                                                if (i311 >= 0) {
                                                    arrayList2.add(strTrim.substring(i311, i10));
                                                }
                                                i311 = -1;
                                            } else if (i311 < 0 && cCharAt3 != ' ') {
                                                z = z;
                                                z2 = z2;
                                                z = z;
                                                z2 = z2;
                                                i311 = i10;
                                            }
                                        }
                                        i10++;
                                        c = cCharAt3;
                                        i23 = i312;
                                        z = z;
                                        z2 = z2;
                                    }
                                    i11 = i23;
                                    arrayList2.add(strTrim.substring(i311, i10));
                                    strArr = (String[]) arrayList2.toArray(new String[0]);
                                }
                                if (strArr.length > 0 || !"DOCTYPE".equals(strArr[0])) {
                                    str = null;
                                    str2 = null;
                                    str3 = null;
                                    strQ = q(str);
                                    strQ2 = q(str3);
                                    if (str2 != null) {
                                        if (strQ == null) {
                                            strTrim2 = null;
                                            i4 = -1;
                                        } else {
                                            length2 = strQ.length();
                                            stringBuffer = new StringBuffer(length2);
                                            z3 = true;
                                            while (i12 < length2) {
                                                cCharAt = strQ.charAt(i12);
                                                if ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-'()+,./:=?;!*#@$_%".indexOf(cCharAt) != -1) {
                                                    stringBuffer.append(cCharAt);
                                                    z3 = false;
                                                } else if (!z3) {
                                                    stringBuffer.append(' ');
                                                    z3 = true;
                                                }
                                            }
                                            i4 = -1;
                                            strTrim2 = stringBuffer.toString().trim();
                                        }
                                        this.b.startDTD(str2, strTrim2, strQ2);
                                        this.b.endDTD();
                                        this.x = str2;
                                        this.v = strTrim2;
                                        zd = this.g;
                                        if (zd instanceof Locator) {
                                            this.w = zd.getSystemId();
                                            try {
                                                this.w = new URL(new URL(this.w), strQ2).toString();
                                                break;
                                            } catch (Exception unused2) {
                                            }
                                        }
                                    }
                                    zd5.j = 0;
                                    i3 = 1;
                                    zd5.g = zd5.h;
                                    i25 = i4;
                                    i22 = i2;
                                    i21 = 128;
                                    i23 = i3;
                                    i20 = i5;
                                } else {
                                    if (!this.u) {
                                        ?? r1 = i11;
                                        this.u = r1;
                                        if (strArr.length > r1) {
                                            str2 = strArr[r1 == true ? 1 : 0];
                                            if (strArr.length > 3 && "SYSTEM".equals(strArr[i2])) {
                                                str3 = strArr[3];
                                                str = null;
                                            } else if (strArr.length <= 3 || !"PUBLIC".equals(strArr[i2])) {
                                                str = null;
                                            } else {
                                                str = strArr[3];
                                                str3 = strArr.length > 4 ? strArr[4] : "";
                                            }
                                            strQ = q(str);
                                            strQ2 = q(str3);
                                            if (str2 != null) {
                                                if (strQ == null) {
                                                    strTrim2 = null;
                                                    i4 = -1;
                                                } else {
                                                    length2 = strQ.length();
                                                    stringBuffer = new StringBuffer(length2);
                                                    z3 = true;
                                                    while (i12 < length2) {
                                                        cCharAt = strQ.charAt(i12);
                                                        if ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-'()+,./:=?;!*#@$_%".indexOf(cCharAt) != -1) {
                                                            stringBuffer.append(cCharAt);
                                                            z3 = false;
                                                        } else if (!z3) {
                                                            stringBuffer.append(' ');
                                                            z3 = true;
                                                        }
                                                    }
                                                    i4 = -1;
                                                    strTrim2 = stringBuffer.toString().trim();
                                                }
                                                this.b.startDTD(str2, strTrim2, strQ2);
                                                this.b.endDTD();
                                                this.x = str2;
                                                this.v = strTrim2;
                                                zd = this.g;
                                                if (zd instanceof Locator) {
                                                    this.w = zd.getSystemId();
                                                    this.w = new URL(new URL(this.w), strQ2).toString();
                                                }
                                            }
                                        } else {
                                            str = null;
                                            str2 = null;
                                        }
                                        str3 = null;
                                        strQ = q(str);
                                        strQ2 = q(str3);
                                        if (str2 != null) {
                                            if (strQ == null) {
                                                strTrim2 = null;
                                                i4 = -1;
                                            } else {
                                                length2 = strQ.length();
                                                stringBuffer = new StringBuffer(length2);
                                                z3 = true;
                                                while (i12 < length2) {
                                                    cCharAt = strQ.charAt(i12);
                                                    if ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-'()+,./:=?;!*#@$_%".indexOf(cCharAt) != -1) {
                                                        stringBuffer.append(cCharAt);
                                                        z3 = false;
                                                    } else if (!z3) {
                                                        stringBuffer.append(' ');
                                                        z3 = true;
                                                    }
                                                }
                                                i4 = -1;
                                                strTrim2 = stringBuffer.toString().trim();
                                            }
                                            this.b.startDTD(str2, strTrim2, strQ2);
                                            this.b.endDTD();
                                            this.x = str2;
                                            this.v = strTrim2;
                                            zd = this.g;
                                            if (zd instanceof Locator) {
                                                this.w = zd.getSystemId();
                                                this.w = new URL(new URL(this.w), strQ2).toString();
                                            }
                                        }
                                        break;
                                    }
                                    zd5.j = 0;
                                    i3 = 1;
                                    zd5.g = zd5.h;
                                    i25 = i4;
                                    i22 = i2;
                                    i21 = 128;
                                    i23 = i3;
                                    i20 = i5;
                                }
                                i4 = -1;
                                zd5.j = 0;
                                i3 = 1;
                                zd5.g = zd5.h;
                                i25 = i4;
                                i22 = i2;
                                i21 = 128;
                                i23 = i3;
                                i20 = i5;
                                break;
                            case 12:
                                i2 = i22;
                                i5 = 10;
                                zd5.a();
                                i13 = zd5.j;
                                if (i13 > 0) {
                                    e(zd5.i, i13);
                                }
                                zd5.j = 0;
                                cArr4 = zd5.i;
                                ea = this.s;
                                if (ea != null) {
                                    m(ea);
                                    c(cArr4, 0);
                                }
                                i3 = i23;
                                i4 = i25;
                                zd5.g = zd5.h;
                                i25 = i4;
                                i22 = i2;
                                i21 = 128;
                                i23 = i3;
                                i20 = i5;
                                break;
                            case 13:
                                zd5.a();
                                char c3 = (char) i26;
                                i14 = zd5.g;
                                if (i14 == 23 || c3 != '#') {
                                    i2 = i22;
                                    if (i14 != 27 && (c3 == 'x' || c3 == 'X')) {
                                        zd5.h = 35;
                                        zd5.b(i26, this);
                                    } else if (i14 != 23 && Character.isLetterOrDigit(c3)) {
                                        zd5.b(i26, this);
                                    } else if (zd5.g != 27 && Character.isDigit(c3)) {
                                        zd5.b(i26, this);
                                    } else if (zd5.g == 35 || (!Character.isDigit(c3) && "abcdefABCDEF".indexOf(c3) == i25)) {
                                        iF = f(zd5.i, i23, zd5.j - i23);
                                        if (iF != 0) {
                                            zd5.j = 0;
                                            if (iF >= 128 && iF <= 159) {
                                                iF = iArr[iF - 128];
                                            }
                                            if (iF >= 32 || (iF >= 55296 && iF <= 57343)) {
                                                i5 = 10;
                                            } else if (iF <= 65535) {
                                                zd5.b(iF, this);
                                                i5 = 10;
                                            } else {
                                                int i313 = iF - 65536;
                                                i5 = 10;
                                                zd5.b((i313 >> 10) + 55296, this);
                                                zd5.b((i313 & 1023) + 56320, this);
                                            }
                                            if (i26 != 59) {
                                                if (i26 != i25) {
                                                    pushbackReader.unread(i26);
                                                }
                                                zd5.f -= i23;
                                            }
                                        } else {
                                            i5 = 10;
                                            if (i26 != i25) {
                                                pushbackReader.unread(i26);
                                            }
                                            zd5.f -= i23;
                                        }
                                        zd5.h = 28;
                                        i3 = i23;
                                        i4 = i25;
                                        zd5.g = zd5.h;
                                        i25 = i4;
                                        i22 = i2;
                                        i21 = 128;
                                        i23 = i3;
                                        i20 = i5;
                                    } else {
                                        zd5.b(i26, this);
                                    }
                                } else {
                                    zd5.h = 27;
                                    zd5.b(i26, this);
                                    i2 = i22;
                                }
                                i3 = i23;
                                i4 = i25;
                                i5 = 10;
                                zd5.g = zd5.h;
                                i25 = i4;
                                i22 = i2;
                                i21 = 128;
                                i23 = i3;
                                i20 = i5;
                                break;
                            case 14:
                                h(zd5.i, zd5.j);
                                zd5.j = 0;
                                zd5.b(i26, this);
                                i2 = i22;
                                i3 = i23;
                                i4 = i25;
                                i5 = 10;
                                zd5.g = zd5.h;
                                i25 = i4;
                                i22 = i2;
                                i21 = 128;
                                i23 = i3;
                                i20 = i5;
                                break;
                            case 15:
                                cArr5 = zd5.i;
                                i15 = zd5.j;
                                Fa fa2 = this.z.a;
                                String str5 = fa2.a;
                                if (this.q || (fa2.f & i22) == 0) {
                                    i16 = 0;
                                } else {
                                    int i40 = i15 == str5.length() ? i23 : 0;
                                    if (i40 != 0) {
                                        for (int i41 = 0; i41 < i15; i41 += i23) {
                                            if (Character.toLowerCase(cArr5[i41]) != Character.toLowerCase(str5.charAt(i41))) {
                                                i40 = 0;
                                            }
                                        }
                                    }
                                    if (i40 == 0) {
                                        ContentHandler contentHandler2 = this.a;
                                        char[] cArr8 = D;
                                        contentHandler2.characters(cArr8, 0, i22);
                                        this.a.characters(cArr5, 0, i15);
                                        this.a.characters(cArr8, i22, i23);
                                        this.g.h = 10;
                                        i16 = i23;
                                    } else {
                                        i16 = 0;
                                    }
                                }
                                if (i16 == 0) {
                                    c(cArr5, i15);
                                }
                                zd5.j = 0;
                                i2 = i22;
                                i3 = i23;
                                i4 = i25;
                                i5 = 10;
                                zd5.g = zd5.h;
                                i25 = i4;
                                i22 = i2;
                                i21 = 128;
                                i23 = i3;
                                i20 = i5;
                                break;
                            case 16:
                                e(zd5.i, zd5.j);
                                zd5.j = 0;
                                i2 = i22;
                                i3 = i23;
                                i4 = i25;
                                i5 = 10;
                                zd5.g = zd5.h;
                                i25 = i4;
                                i22 = i2;
                                i21 = 128;
                                i23 = i3;
                                i20 = i5;
                                break;
                            case 17:
                                e(zd5.i, zd5.j);
                                zd5.j = 0;
                                p(zd5.i, 0);
                                i2 = i22;
                                i3 = i23;
                                i4 = i25;
                                i5 = 10;
                                zd5.g = zd5.h;
                                i25 = i4;
                                i22 = i2;
                                i21 = 128;
                                i23 = i3;
                                i20 = i5;
                                break;
                            case 18:
                                zd5.a();
                                zd5.b(60, this);
                                zd5.b(i26, this);
                                i2 = i22;
                                i3 = i23;
                                i4 = i25;
                                i5 = 10;
                                zd5.g = zd5.h;
                                i25 = i4;
                                i22 = i2;
                                i21 = 128;
                                i23 = i3;
                                i20 = i5;
                                break;
                            case 19:
                                zd5.a();
                                zd5.b(60, this);
                                h(zd5.i, zd5.j);
                                zd5.j = 0;
                                i2 = i22;
                                i3 = i23;
                                i4 = i25;
                                i5 = 10;
                                zd5.g = zd5.h;
                                i25 = i4;
                                i22 = i2;
                                i21 = 128;
                                i23 = i3;
                                i20 = i5;
                                break;
                            case 20:
                                zd5.b(45, this);
                                zd5.b(i26, this);
                                i2 = i22;
                                i3 = i23;
                                i4 = i25;
                                i5 = 10;
                                zd5.g = zd5.h;
                                i25 = i4;
                                i22 = i2;
                                i21 = 128;
                                i23 = i3;
                                i20 = i5;
                                break;
                            case 21:
                                zd5.b(45, this);
                                zd5.b(32, this);
                                zd5.b(45, this);
                                zd5.b(i26, this);
                                i2 = i22;
                                i3 = i23;
                                i4 = i25;
                                i5 = 10;
                                zd5.g = zd5.h;
                                i25 = i4;
                                i22 = i2;
                                i21 = 128;
                                i23 = i3;
                                i20 = i5;
                                break;
                            case 22:
                                zd5.b(45, this);
                                zd5.b(32, this);
                                i2 = i22;
                                i3 = i23;
                                i4 = i25;
                                i5 = 10;
                                zd5.g = zd5.h;
                                i25 = i4;
                                i22 = i2;
                                i21 = 128;
                                i23 = i3;
                                i20 = i5;
                                break;
                            case 23:
                                zd5.a();
                                h(zd5.i, zd5.j);
                                zd5.j = 0;
                                i2 = i22;
                                i3 = i23;
                                i4 = i25;
                                i5 = 10;
                                zd5.g = zd5.h;
                                i25 = i4;
                                i22 = i2;
                                i21 = 128;
                                i23 = i3;
                                i20 = i5;
                                break;
                            case 24:
                                zd5.a();
                                i(zd5.i, zd5.j);
                                zd5.j = 0;
                                i2 = i22;
                                i3 = i23;
                                i4 = i25;
                                i5 = 10;
                                zd5.g = zd5.h;
                                i25 = i4;
                                i22 = i2;
                                i21 = 128;
                                i23 = i3;
                                i20 = i5;
                                break;
                            case 25:
                                cArr6 = zd5.i;
                                i17 = zd5.j;
                                if (this.s == null) {
                                    this.y = g(cArr6, i17).replace(':', '_');
                                }
                                zd5.j = 0;
                                i2 = i22;
                                i3 = i23;
                                i4 = i25;
                                i5 = 10;
                                zd5.g = zd5.h;
                                i25 = i4;
                                i22 = i2;
                                i21 = 128;
                                i23 = i3;
                                i20 = i5;
                                break;
                            case 26:
                                cArr7 = zd5.i;
                                i18 = zd5.j;
                                if (this.s == null) {
                                    this.y = g(cArr7, i18).replace(':', '_');
                                }
                                zd5.j = 0;
                                i(zd5.i, 0);
                                i2 = i22;
                                i3 = i23;
                                i4 = i25;
                                i5 = 10;
                                zd5.g = zd5.h;
                                i25 = i4;
                                i22 = i2;
                                i21 = 128;
                                i23 = i3;
                                i20 = i5;
                                break;
                            case 27:
                                zd5.b(i26, this);
                                i2 = i22;
                                i3 = i23;
                                i4 = i25;
                                i5 = 10;
                                zd5.g = zd5.h;
                                i25 = i4;
                                i22 = i2;
                                i21 = 128;
                                i23 = i3;
                                i20 = i5;
                                break;
                            case 28:
                                i2 = i22;
                                i3 = i23;
                                i4 = i25;
                                i5 = 10;
                                zd5.g = zd5.h;
                                i25 = i4;
                                i22 = i2;
                                i21 = 128;
                                i23 = i3;
                                i20 = i5;
                                break;
                            case 29:
                                zd5.b(32, this);
                                i2 = i22;
                                i3 = i23;
                                i4 = i25;
                                i5 = 10;
                                zd5.g = zd5.h;
                                i25 = i4;
                                i22 = i2;
                                i21 = 128;
                                i23 = i3;
                                i20 = i5;
                                break;
                            case 30:
                                p(zd5.i, zd5.j);
                                zd5.j = 0;
                                i2 = i22;
                                i3 = i23;
                                i4 = i25;
                                i5 = 10;
                                zd5.g = zd5.h;
                                i25 = i4;
                                i22 = i2;
                                i21 = 128;
                                i23 = i3;
                                i20 = i5;
                                break;
                            case 31:
                                if (i26 != i25) {
                                    pushbackReader.unread(i26);
                                }
                                zd5.f -= i23;
                                i2 = i22;
                                i3 = i23;
                                i4 = i25;
                                i5 = 10;
                                zd5.g = zd5.h;
                                i25 = i4;
                                i22 = i2;
                                i21 = 128;
                                i23 = i3;
                                i20 = i5;
                                break;
                            case 32:
                                i19 = zd5.j;
                                if (i19 > 0) {
                                    zd5.j = i19 - i23;
                                }
                                h(zd5.i, zd5.j);
                                zd5.j = 0;
                                i2 = i22;
                                i3 = i23;
                                i4 = i25;
                                i5 = 10;
                                zd5.g = zd5.h;
                                i25 = i4;
                                i22 = i2;
                                i21 = 128;
                                i23 = i3;
                                i20 = i5;
                                break;
                            default:
                                StringBuffer stringBuffer6 = new StringBuffer("Can't process state ");
                                stringBuffer6.append(i);
                                throw new Error(stringBuffer6.toString());
                        }
                    }
                }
                i = i27;
                switch (i) {
                    case 0:
                        StringBuffer stringBuffer7 = new StringBuffer("HTMLScanner can't cope with ");
                        stringBuffer7.append(Integer.toString(i26));
                        stringBuffer7.append(" in state ");
                        stringBuffer7.append(Integer.toString(zd5.g));
                        throw new Error(stringBuffer7.toString());
                    case 1:
                        i2 = i22;
                        i3 = i23;
                        i4 = i25;
                        i5 = 10;
                        a();
                        zd5.j = 0;
                        zd5.g = zd5.h;
                        i25 = i4;
                        i22 = i2;
                        i21 = 128;
                        i23 = i3;
                        i20 = i5;
                        break;
                    case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                        i2 = i22;
                        i3 = i23;
                        i4 = i25;
                        i5 = 10;
                        a();
                        zd5.j = 0;
                        zd5.b(i26, this);
                        zd5.g = zd5.h;
                        i25 = i4;
                        i22 = i2;
                        i21 = 128;
                        i23 = i3;
                        i20 = i5;
                        break;
                    case 3:
                        i2 = i22;
                        i3 = i23;
                        i4 = i25;
                        i5 = 10;
                        a();
                        zd5.j = 0;
                        p(zd5.i, 0);
                        zd5.g = zd5.h;
                        i25 = i4;
                        i22 = i2;
                        i21 = 128;
                        i23 = i3;
                        i20 = i5;
                        break;
                    case 4:
                        i2 = i22;
                        i3 = i23;
                        i4 = i25;
                        i5 = 10;
                        cArr = zd5.i;
                        i6 = zd5.j;
                        if (this.s != null) {
                            this.t = g(cArr, i6).toLowerCase();
                        }
                        zd5.j = 0;
                        zd5.g = zd5.h;
                        i25 = i4;
                        i22 = i2;
                        i21 = 128;
                        i23 = i3;
                        i20 = i5;
                        break;
                    case BulletSpan.STANDARD_BULLET_RADIUS /* 5 */:
                        i2 = i22;
                        i3 = i23;
                        i4 = i25;
                        i5 = 10;
                        cArr2 = zd5.i;
                        i7 = zd5.j;
                        if (this.s != null) {
                            this.t = g(cArr2, i7).toLowerCase();
                        }
                        zd5.j = 0;
                        a();
                        zd5.g = zd5.h;
                        i25 = i4;
                        i22 = i2;
                        i21 = 128;
                        i23 = i3;
                        i20 = i5;
                        break;
                    case 6:
                        i2 = i22;
                        i3 = i23;
                        i4 = i25;
                        i5 = 10;
                        cArr3 = zd5.i;
                        i8 = zd5.j;
                        if (this.s != null) {
                            this.t = g(cArr3, i8).toLowerCase();
                        }
                        zd5.j = 0;
                        a();
                        p(zd5.i, zd5.j);
                        zd5.g = zd5.h;
                        i25 = i4;
                        i22 = i2;
                        i21 = 128;
                        i23 = i3;
                        i20 = i5;
                        break;
                    case 7:
                        i2 = i22;
                        i3 = i23;
                        i4 = i25;
                        i5 = 10;
                        b(zd5.i, zd5.j);
                        zd5.j = 0;
                        zd5.g = zd5.h;
                        i25 = i4;
                        i22 = i2;
                        i21 = 128;
                        i23 = i3;
                        i20 = i5;
                        break;
                    case 8:
                        i2 = i22;
                        i3 = i23;
                        i4 = i25;
                        i5 = 10;
                        b(zd5.i, zd5.j);
                        zd5.j = 0;
                        p(zd5.i, 0);
                        zd5.g = zd5.h;
                        i25 = i4;
                        i22 = i2;
                        i21 = 128;
                        i23 = i3;
                        i20 = i5;
                        break;
                    case 9:
                        i2 = i22;
                        i4 = i25;
                        i5 = 10;
                        zd5.a();
                        i9 = zd5.j;
                        i3 = 1;
                        if (i9 > 1) {
                            zd5.j = i9 - 2;
                        }
                        h(zd5.i, zd5.j);
                        zd5.j = 0;
                        zd5.g = zd5.h;
                        i25 = i4;
                        i22 = i2;
                        i21 = 128;
                        i23 = i3;
                        i20 = i5;
                        break;
                    case 10:
                        i2 = i22;
                        i4 = i25;
                        i5 = 10;
                        zd5.a();
                        this.b.comment(zd5.i, 0, zd5.j);
                        zd5.j = 0;
                        i3 = 1;
                        zd5.g = zd5.h;
                        i25 = i4;
                        i22 = i2;
                        i21 = 128;
                        i23 = i3;
                        i20 = i5;
                        break;
                    case 11:
                        i2 = i22;
                        i5 = 10;
                        strTrim = new String(zd5.i, 0, zd5.j).trim();
                        if (strTrim.length() == 0) {
                            strArr = new String[0];
                            i11 = i23;
                        } else {
                            ArrayList arrayList3 = new ArrayList();
                            length = strTrim.length();
                            c = 0;
                            i10 = 0;
                            int i314 = 0;
                            z = false;
                            z2 = false;
                            while (i10 < length) {
                                char cCharAt4 = strTrim.charAt(i10);
                                int i315 = i23;
                                if (!z) {
                                    if (!z2) {
                                        if (z2) {
                                        }
                                    } else if (z2) {
                                    }
                                } else if (!z2) {
                                    if (z2) {
                                    }
                                } else if (z2) {
                                }
                                i10++;
                                c = cCharAt4;
                                i23 = i315;
                                z = z;
                                z2 = z2;
                            }
                            i11 = i23;
                            arrayList3.add(strTrim.substring(i314, i10));
                            strArr = (String[]) arrayList3.toArray(new String[0]);
                        }
                        if (strArr.length > 0) {
                            str = null;
                            str2 = null;
                            str3 = null;
                            strQ = q(str);
                            strQ2 = q(str3);
                            if (str2 != null) {
                                if (strQ == null) {
                                    strTrim2 = null;
                                    i4 = -1;
                                } else {
                                    length2 = strQ.length();
                                    stringBuffer = new StringBuffer(length2);
                                    z3 = true;
                                    while (i12 < length2) {
                                        cCharAt = strQ.charAt(i12);
                                        if ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-'()+,./:=?;!*#@$_%".indexOf(cCharAt) != -1) {
                                            stringBuffer.append(cCharAt);
                                            z3 = false;
                                        } else if (!z3) {
                                            stringBuffer.append(' ');
                                            z3 = true;
                                        }
                                    }
                                    i4 = -1;
                                    strTrim2 = stringBuffer.toString().trim();
                                }
                                this.b.startDTD(str2, strTrim2, strQ2);
                                this.b.endDTD();
                                this.x = str2;
                                this.v = strTrim2;
                                zd = this.g;
                                if (zd instanceof Locator) {
                                    this.w = zd.getSystemId();
                                    this.w = new URL(new URL(this.w), strQ2).toString();
                                }
                            }
                            zd5.j = 0;
                            i3 = 1;
                            zd5.g = zd5.h;
                            i25 = i4;
                            i22 = i2;
                            i21 = 128;
                            i23 = i3;
                            i20 = i5;
                        } else {
                            str = null;
                            str2 = null;
                            str3 = null;
                            strQ = q(str);
                            strQ2 = q(str3);
                            if (str2 != null) {
                                if (strQ == null) {
                                    strTrim2 = null;
                                    i4 = -1;
                                } else {
                                    length2 = strQ.length();
                                    stringBuffer = new StringBuffer(length2);
                                    z3 = true;
                                    while (i12 < length2) {
                                        cCharAt = strQ.charAt(i12);
                                        if ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-'()+,./:=?;!*#@$_%".indexOf(cCharAt) != -1) {
                                            stringBuffer.append(cCharAt);
                                            z3 = false;
                                        } else if (!z3) {
                                            stringBuffer.append(' ');
                                            z3 = true;
                                        }
                                    }
                                    i4 = -1;
                                    strTrim2 = stringBuffer.toString().trim();
                                }
                                this.b.startDTD(str2, strTrim2, strQ2);
                                this.b.endDTD();
                                this.x = str2;
                                this.v = strTrim2;
                                zd = this.g;
                                if (zd instanceof Locator) {
                                    this.w = zd.getSystemId();
                                    this.w = new URL(new URL(this.w), strQ2).toString();
                                }
                            }
                            zd5.j = 0;
                            i3 = 1;
                            zd5.g = zd5.h;
                            i25 = i4;
                            i22 = i2;
                            i21 = 128;
                            i23 = i3;
                            i20 = i5;
                        }
                        i4 = -1;
                        zd5.j = 0;
                        i3 = 1;
                        zd5.g = zd5.h;
                        i25 = i4;
                        i22 = i2;
                        i21 = 128;
                        i23 = i3;
                        i20 = i5;
                        break;
                    case 12:
                        i2 = i22;
                        i5 = 10;
                        zd5.a();
                        i13 = zd5.j;
                        if (i13 > 0) {
                            e(zd5.i, i13);
                        }
                        zd5.j = 0;
                        cArr4 = zd5.i;
                        ea = this.s;
                        if (ea != null) {
                            m(ea);
                            c(cArr4, 0);
                        }
                        i3 = i23;
                        i4 = i25;
                        zd5.g = zd5.h;
                        i25 = i4;
                        i22 = i2;
                        i21 = 128;
                        i23 = i3;
                        i20 = i5;
                        break;
                    case 13:
                        zd5.a();
                        char c4 = (char) i26;
                        i14 = zd5.g;
                        if (i14 == 23) {
                        }
                        i2 = i22;
                        if (i14 != 27) {
                            if (i14 != 23) {
                                if (zd5.g != 27) {
                                    if (zd5.g == 35) {
                                    }
                                    iF = f(zd5.i, i23, zd5.j - i23);
                                    if (iF != 0) {
                                        zd5.j = 0;
                                        if (iF >= 128) {
                                            iF = iArr[iF - 128];
                                        }
                                        if (iF >= 32) {
                                            i5 = 10;
                                        } else if (iF <= 65535) {
                                            zd5.b(iF, this);
                                            i5 = 10;
                                        } else {
                                            int i316 = iF - 65536;
                                            i5 = 10;
                                            zd5.b((i316 >> 10) + 55296, this);
                                            zd5.b((i316 & 1023) + 56320, this);
                                        }
                                        if (i26 != 59) {
                                            if (i26 != i25) {
                                                pushbackReader.unread(i26);
                                            }
                                            zd5.f -= i23;
                                        }
                                    } else {
                                        i5 = 10;
                                        if (i26 != i25) {
                                            pushbackReader.unread(i26);
                                        }
                                        zd5.f -= i23;
                                    }
                                    zd5.h = 28;
                                    i3 = i23;
                                    i4 = i25;
                                    zd5.g = zd5.h;
                                    i25 = i4;
                                    i22 = i2;
                                    i21 = 128;
                                    i23 = i3;
                                    i20 = i5;
                                } else {
                                    if (zd5.g == 35) {
                                    }
                                    iF = f(zd5.i, i23, zd5.j - i23);
                                    if (iF != 0) {
                                        zd5.j = 0;
                                        if (iF >= 128) {
                                            iF = iArr[iF - 128];
                                        }
                                        if (iF >= 32) {
                                            i5 = 10;
                                        } else if (iF <= 65535) {
                                            zd5.b(iF, this);
                                            i5 = 10;
                                        } else {
                                            int i317 = iF - 65536;
                                            i5 = 10;
                                            zd5.b((i317 >> 10) + 55296, this);
                                            zd5.b((i317 & 1023) + 56320, this);
                                        }
                                        if (i26 != 59) {
                                            if (i26 != i25) {
                                                pushbackReader.unread(i26);
                                            }
                                            zd5.f -= i23;
                                        }
                                    } else {
                                        i5 = 10;
                                        if (i26 != i25) {
                                            pushbackReader.unread(i26);
                                        }
                                        zd5.f -= i23;
                                    }
                                    zd5.h = 28;
                                    i3 = i23;
                                    i4 = i25;
                                    zd5.g = zd5.h;
                                    i25 = i4;
                                    i22 = i2;
                                    i21 = 128;
                                    i23 = i3;
                                    i20 = i5;
                                }
                            } else if (zd5.g != 27) {
                                if (zd5.g == 35) {
                                }
                                iF = f(zd5.i, i23, zd5.j - i23);
                                if (iF != 0) {
                                    zd5.j = 0;
                                    if (iF >= 128) {
                                        iF = iArr[iF - 128];
                                    }
                                    if (iF >= 32) {
                                        i5 = 10;
                                    } else if (iF <= 65535) {
                                        zd5.b(iF, this);
                                        i5 = 10;
                                    } else {
                                        int i318 = iF - 65536;
                                        i5 = 10;
                                        zd5.b((i318 >> 10) + 55296, this);
                                        zd5.b((i318 & 1023) + 56320, this);
                                    }
                                    if (i26 != 59) {
                                        if (i26 != i25) {
                                            pushbackReader.unread(i26);
                                        }
                                        zd5.f -= i23;
                                    }
                                } else {
                                    i5 = 10;
                                    if (i26 != i25) {
                                        pushbackReader.unread(i26);
                                    }
                                    zd5.f -= i23;
                                }
                                zd5.h = 28;
                                i3 = i23;
                                i4 = i25;
                                zd5.g = zd5.h;
                                i25 = i4;
                                i22 = i2;
                                i21 = 128;
                                i23 = i3;
                                i20 = i5;
                            } else {
                                if (zd5.g == 35) {
                                }
                                iF = f(zd5.i, i23, zd5.j - i23);
                                if (iF != 0) {
                                    zd5.j = 0;
                                    if (iF >= 128) {
                                        iF = iArr[iF - 128];
                                    }
                                    if (iF >= 32) {
                                        i5 = 10;
                                    } else if (iF <= 65535) {
                                        zd5.b(iF, this);
                                        i5 = 10;
                                    } else {
                                        int i319 = iF - 65536;
                                        i5 = 10;
                                        zd5.b((i319 >> 10) + 55296, this);
                                        zd5.b((i319 & 1023) + 56320, this);
                                    }
                                    if (i26 != 59) {
                                        if (i26 != i25) {
                                            pushbackReader.unread(i26);
                                        }
                                        zd5.f -= i23;
                                    }
                                } else {
                                    i5 = 10;
                                    if (i26 != i25) {
                                        pushbackReader.unread(i26);
                                    }
                                    zd5.f -= i23;
                                }
                                zd5.h = 28;
                                i3 = i23;
                                i4 = i25;
                                zd5.g = zd5.h;
                                i25 = i4;
                                i22 = i2;
                                i21 = 128;
                                i23 = i3;
                                i20 = i5;
                            }
                        } else if (i14 != 23) {
                            if (zd5.g != 27) {
                                if (zd5.g == 35) {
                                }
                                iF = f(zd5.i, i23, zd5.j - i23);
                                if (iF != 0) {
                                    zd5.j = 0;
                                    if (iF >= 128) {
                                        iF = iArr[iF - 128];
                                    }
                                    if (iF >= 32) {
                                        i5 = 10;
                                    } else if (iF <= 65535) {
                                        zd5.b(iF, this);
                                        i5 = 10;
                                    } else {
                                        int i3110 = iF - 65536;
                                        i5 = 10;
                                        zd5.b((i3110 >> 10) + 55296, this);
                                        zd5.b((i3110 & 1023) + 56320, this);
                                    }
                                    if (i26 != 59) {
                                        if (i26 != i25) {
                                            pushbackReader.unread(i26);
                                        }
                                        zd5.f -= i23;
                                    }
                                } else {
                                    i5 = 10;
                                    if (i26 != i25) {
                                        pushbackReader.unread(i26);
                                    }
                                    zd5.f -= i23;
                                }
                                zd5.h = 28;
                                i3 = i23;
                                i4 = i25;
                                zd5.g = zd5.h;
                                i25 = i4;
                                i22 = i2;
                                i21 = 128;
                                i23 = i3;
                                i20 = i5;
                            } else {
                                if (zd5.g == 35) {
                                }
                                iF = f(zd5.i, i23, zd5.j - i23);
                                if (iF != 0) {
                                    zd5.j = 0;
                                    if (iF >= 128) {
                                        iF = iArr[iF - 128];
                                    }
                                    if (iF >= 32) {
                                        i5 = 10;
                                    } else if (iF <= 65535) {
                                        zd5.b(iF, this);
                                        i5 = 10;
                                    } else {
                                        int i3111 = iF - 65536;
                                        i5 = 10;
                                        zd5.b((i3111 >> 10) + 55296, this);
                                        zd5.b((i3111 & 1023) + 56320, this);
                                    }
                                    if (i26 != 59) {
                                        if (i26 != i25) {
                                            pushbackReader.unread(i26);
                                        }
                                        zd5.f -= i23;
                                    }
                                } else {
                                    i5 = 10;
                                    if (i26 != i25) {
                                        pushbackReader.unread(i26);
                                    }
                                    zd5.f -= i23;
                                }
                                zd5.h = 28;
                                i3 = i23;
                                i4 = i25;
                                zd5.g = zd5.h;
                                i25 = i4;
                                i22 = i2;
                                i21 = 128;
                                i23 = i3;
                                i20 = i5;
                            }
                        } else if (zd5.g != 27) {
                            if (zd5.g == 35) {
                            }
                            iF = f(zd5.i, i23, zd5.j - i23);
                            if (iF != 0) {
                                zd5.j = 0;
                                if (iF >= 128) {
                                    iF = iArr[iF - 128];
                                }
                                if (iF >= 32) {
                                    i5 = 10;
                                } else if (iF <= 65535) {
                                    zd5.b(iF, this);
                                    i5 = 10;
                                } else {
                                    int i3112 = iF - 65536;
                                    i5 = 10;
                                    zd5.b((i3112 >> 10) + 55296, this);
                                    zd5.b((i3112 & 1023) + 56320, this);
                                }
                                if (i26 != 59) {
                                    if (i26 != i25) {
                                        pushbackReader.unread(i26);
                                    }
                                    zd5.f -= i23;
                                }
                            } else {
                                i5 = 10;
                                if (i26 != i25) {
                                    pushbackReader.unread(i26);
                                }
                                zd5.f -= i23;
                            }
                            zd5.h = 28;
                            i3 = i23;
                            i4 = i25;
                            zd5.g = zd5.h;
                            i25 = i4;
                            i22 = i2;
                            i21 = 128;
                            i23 = i3;
                            i20 = i5;
                        } else {
                            if (zd5.g == 35) {
                            }
                            iF = f(zd5.i, i23, zd5.j - i23);
                            if (iF != 0) {
                                zd5.j = 0;
                                if (iF >= 128) {
                                    iF = iArr[iF - 128];
                                }
                                if (iF >= 32) {
                                    i5 = 10;
                                } else if (iF <= 65535) {
                                    zd5.b(iF, this);
                                    i5 = 10;
                                } else {
                                    int i3113 = iF - 65536;
                                    i5 = 10;
                                    zd5.b((i3113 >> 10) + 55296, this);
                                    zd5.b((i3113 & 1023) + 56320, this);
                                }
                                if (i26 != 59) {
                                    if (i26 != i25) {
                                        pushbackReader.unread(i26);
                                    }
                                    zd5.f -= i23;
                                }
                            } else {
                                i5 = 10;
                                if (i26 != i25) {
                                    pushbackReader.unread(i26);
                                }
                                zd5.f -= i23;
                            }
                            zd5.h = 28;
                            i3 = i23;
                            i4 = i25;
                            zd5.g = zd5.h;
                            i25 = i4;
                            i22 = i2;
                            i21 = 128;
                            i23 = i3;
                            i20 = i5;
                        }
                        i3 = i23;
                        i4 = i25;
                        i5 = 10;
                        zd5.g = zd5.h;
                        i25 = i4;
                        i22 = i2;
                        i21 = 128;
                        i23 = i3;
                        i20 = i5;
                        break;
                    case 14:
                        h(zd5.i, zd5.j);
                        zd5.j = 0;
                        zd5.b(i26, this);
                        i2 = i22;
                        i3 = i23;
                        i4 = i25;
                        i5 = 10;
                        zd5.g = zd5.h;
                        i25 = i4;
                        i22 = i2;
                        i21 = 128;
                        i23 = i3;
                        i20 = i5;
                        break;
                    case 15:
                        cArr5 = zd5.i;
                        i15 = zd5.j;
                        Fa fa3 = this.z.a;
                        String str6 = fa3.a;
                        if (this.q) {
                            i16 = 0;
                        } else {
                            i16 = 0;
                        }
                        if (i16 == 0) {
                            c(cArr5, i15);
                        }
                        zd5.j = 0;
                        i2 = i22;
                        i3 = i23;
                        i4 = i25;
                        i5 = 10;
                        zd5.g = zd5.h;
                        i25 = i4;
                        i22 = i2;
                        i21 = 128;
                        i23 = i3;
                        i20 = i5;
                        break;
                    case 16:
                        e(zd5.i, zd5.j);
                        zd5.j = 0;
                        i2 = i22;
                        i3 = i23;
                        i4 = i25;
                        i5 = 10;
                        zd5.g = zd5.h;
                        i25 = i4;
                        i22 = i2;
                        i21 = 128;
                        i23 = i3;
                        i20 = i5;
                        break;
                    case 17:
                        e(zd5.i, zd5.j);
                        zd5.j = 0;
                        p(zd5.i, 0);
                        i2 = i22;
                        i3 = i23;
                        i4 = i25;
                        i5 = 10;
                        zd5.g = zd5.h;
                        i25 = i4;
                        i22 = i2;
                        i21 = 128;
                        i23 = i3;
                        i20 = i5;
                        break;
                    case 18:
                        zd5.a();
                        zd5.b(60, this);
                        zd5.b(i26, this);
                        i2 = i22;
                        i3 = i23;
                        i4 = i25;
                        i5 = 10;
                        zd5.g = zd5.h;
                        i25 = i4;
                        i22 = i2;
                        i21 = 128;
                        i23 = i3;
                        i20 = i5;
                        break;
                    case 19:
                        zd5.a();
                        zd5.b(60, this);
                        h(zd5.i, zd5.j);
                        zd5.j = 0;
                        i2 = i22;
                        i3 = i23;
                        i4 = i25;
                        i5 = 10;
                        zd5.g = zd5.h;
                        i25 = i4;
                        i22 = i2;
                        i21 = 128;
                        i23 = i3;
                        i20 = i5;
                        break;
                    case 20:
                        zd5.b(45, this);
                        zd5.b(i26, this);
                        i2 = i22;
                        i3 = i23;
                        i4 = i25;
                        i5 = 10;
                        zd5.g = zd5.h;
                        i25 = i4;
                        i22 = i2;
                        i21 = 128;
                        i23 = i3;
                        i20 = i5;
                        break;
                    case 21:
                        zd5.b(45, this);
                        zd5.b(32, this);
                        zd5.b(45, this);
                        zd5.b(i26, this);
                        i2 = i22;
                        i3 = i23;
                        i4 = i25;
                        i5 = 10;
                        zd5.g = zd5.h;
                        i25 = i4;
                        i22 = i2;
                        i21 = 128;
                        i23 = i3;
                        i20 = i5;
                        break;
                    case 22:
                        zd5.b(45, this);
                        zd5.b(32, this);
                        i2 = i22;
                        i3 = i23;
                        i4 = i25;
                        i5 = 10;
                        zd5.g = zd5.h;
                        i25 = i4;
                        i22 = i2;
                        i21 = 128;
                        i23 = i3;
                        i20 = i5;
                        break;
                    case 23:
                        zd5.a();
                        h(zd5.i, zd5.j);
                        zd5.j = 0;
                        i2 = i22;
                        i3 = i23;
                        i4 = i25;
                        i5 = 10;
                        zd5.g = zd5.h;
                        i25 = i4;
                        i22 = i2;
                        i21 = 128;
                        i23 = i3;
                        i20 = i5;
                        break;
                    case 24:
                        zd5.a();
                        i(zd5.i, zd5.j);
                        zd5.j = 0;
                        i2 = i22;
                        i3 = i23;
                        i4 = i25;
                        i5 = 10;
                        zd5.g = zd5.h;
                        i25 = i4;
                        i22 = i2;
                        i21 = 128;
                        i23 = i3;
                        i20 = i5;
                        break;
                    case 25:
                        cArr6 = zd5.i;
                        i17 = zd5.j;
                        if (this.s == null) {
                            this.y = g(cArr6, i17).replace(':', '_');
                        }
                        zd5.j = 0;
                        i2 = i22;
                        i3 = i23;
                        i4 = i25;
                        i5 = 10;
                        zd5.g = zd5.h;
                        i25 = i4;
                        i22 = i2;
                        i21 = 128;
                        i23 = i3;
                        i20 = i5;
                        break;
                    case 26:
                        cArr7 = zd5.i;
                        i18 = zd5.j;
                        if (this.s == null) {
                            this.y = g(cArr7, i18).replace(':', '_');
                        }
                        zd5.j = 0;
                        i(zd5.i, 0);
                        i2 = i22;
                        i3 = i23;
                        i4 = i25;
                        i5 = 10;
                        zd5.g = zd5.h;
                        i25 = i4;
                        i22 = i2;
                        i21 = 128;
                        i23 = i3;
                        i20 = i5;
                        break;
                    case 27:
                        zd5.b(i26, this);
                        i2 = i22;
                        i3 = i23;
                        i4 = i25;
                        i5 = 10;
                        zd5.g = zd5.h;
                        i25 = i4;
                        i22 = i2;
                        i21 = 128;
                        i23 = i3;
                        i20 = i5;
                        break;
                    case 28:
                        i2 = i22;
                        i3 = i23;
                        i4 = i25;
                        i5 = 10;
                        zd5.g = zd5.h;
                        i25 = i4;
                        i22 = i2;
                        i21 = 128;
                        i23 = i3;
                        i20 = i5;
                        break;
                    case 29:
                        zd5.b(32, this);
                        i2 = i22;
                        i3 = i23;
                        i4 = i25;
                        i5 = 10;
                        zd5.g = zd5.h;
                        i25 = i4;
                        i22 = i2;
                        i21 = 128;
                        i23 = i3;
                        i20 = i5;
                        break;
                    case 30:
                        p(zd5.i, zd5.j);
                        zd5.j = 0;
                        i2 = i22;
                        i3 = i23;
                        i4 = i25;
                        i5 = 10;
                        zd5.g = zd5.h;
                        i25 = i4;
                        i22 = i2;
                        i21 = 128;
                        i23 = i3;
                        i20 = i5;
                        break;
                    case 31:
                        if (i26 != i25) {
                            pushbackReader.unread(i26);
                        }
                        zd5.f -= i23;
                        i2 = i22;
                        i3 = i23;
                        i4 = i25;
                        i5 = 10;
                        zd5.g = zd5.h;
                        i25 = i4;
                        i22 = i2;
                        i21 = 128;
                        i23 = i3;
                        i20 = i5;
                        break;
                    case 32:
                        i19 = zd5.j;
                        if (i19 > 0) {
                            zd5.j = i19 - i23;
                        }
                        h(zd5.i, zd5.j);
                        zd5.j = 0;
                        i2 = i22;
                        i3 = i23;
                        i4 = i25;
                        i5 = 10;
                        zd5.g = zd5.h;
                        i25 = i4;
                        i22 = i2;
                        i21 = 128;
                        i23 = i3;
                        i20 = i5;
                        break;
                    default:
                        StringBuffer stringBuffer8 = new StringBuffer("Can't process state ");
                        stringBuffer8.append(i);
                        throw new Error(stringBuffer8.toString());
                }
            }
        }
        if (this.C) {
            m(this.B);
        }
        while (this.z.c != null) {
            j();
        }
        if (!this.f.c.equals("")) {
            this.a.endPrefixMapping(this.f.d);
        }
        this.a.endDocument();
    }

    @Override // org.xml.sax.ext.LexicalHandler
    public final void endCDATA() {
    }

    @Override // org.xml.sax.ext.LexicalHandler
    public final void endDTD() {
    }

    @Override // org.xml.sax.ext.LexicalHandler
    public final void startCDATA() {
    }

    @Override // org.xml.sax.ext.LexicalHandler
    public final void endEntity(String str) {
    }

    @Override // org.xml.sax.ext.LexicalHandler
    public final void startEntity(String str) {
    }

    @Override // org.xml.sax.ext.LexicalHandler
    public final void comment(char[] cArr, int i, int i2) {
    }

    @Override // org.xml.sax.ext.LexicalHandler
    public final void startDTD(String str, String str2, String str3) {
    }
}
