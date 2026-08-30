package rikka.shizuku;

import org.xml.sax.Attributes;

/* JADX INFO: renamed from: rikka.shizuku.r4, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0545r4 implements Attributes {
    public int a = 0;
    public String[] b = null;

    public final void a(int i) {
        int i2;
        if (i < 0 || i >= (i2 = this.a)) {
            StringBuffer stringBuffer = new StringBuffer("Attempt to modify attribute at illegal index: ");
            stringBuffer.append(i);
            throw new ArrayIndexOutOfBoundsException(stringBuffer.toString());
        }
        if (i < i2 - 1) {
            String[] strArr = this.b;
            System.arraycopy(strArr, (i + 1) * 5, strArr, i * 5, ((i2 - i) - 1) * 5);
        }
        int i3 = this.a - 1;
        int i4 = i3 * 5;
        String[] strArr2 = this.b;
        strArr2[i4] = null;
        strArr2[i4 + 1] = null;
        strArr2[i4 + 2] = null;
        strArr2[i4 + 3] = null;
        strArr2[i4 + 4] = null;
        this.a = i3;
    }

    @Override // org.xml.sax.Attributes
    public final int getIndex(String str, String str2) {
        int i = this.a * 5;
        for (int i2 = 0; i2 < i; i2 += 5) {
            if (this.b[i2].equals(str) && this.b[i2 + 1].equals(str2)) {
                return i2 / 5;
            }
        }
        return -1;
    }

    @Override // org.xml.sax.Attributes
    public final int getLength() {
        return this.a;
    }

    @Override // org.xml.sax.Attributes
    public final String getLocalName(int i) {
        if (i < 0 || i >= this.a) {
            return null;
        }
        return this.b[(i * 5) + 1];
    }

    @Override // org.xml.sax.Attributes
    public final String getQName(int i) {
        if (i < 0 || i >= this.a) {
            return null;
        }
        return this.b[(i * 5) + 2];
    }

    @Override // org.xml.sax.Attributes
    public final String getType(int i) {
        if (i < 0 || i >= this.a) {
            return null;
        }
        return this.b[(i * 5) + 3];
    }

    @Override // org.xml.sax.Attributes
    public final String getURI(int i) {
        if (i < 0 || i >= this.a) {
            return null;
        }
        return this.b[i * 5];
    }

    @Override // org.xml.sax.Attributes
    public final String getValue(int i) {
        if (i < 0 || i >= this.a) {
            return null;
        }
        return this.b[(i * 5) + 4];
    }

    @Override // org.xml.sax.Attributes
    public final String getType(String str, String str2) {
        int i = this.a * 5;
        for (int i2 = 0; i2 < i; i2 += 5) {
            if (this.b[i2].equals(str) && this.b[i2 + 1].equals(str2)) {
                return this.b[i2 + 3];
            }
        }
        return null;
    }

    @Override // org.xml.sax.Attributes
    public final String getValue(String str, String str2) {
        int i = this.a * 5;
        for (int i2 = 0; i2 < i; i2 += 5) {
            if (this.b[i2].equals(str) && this.b[i2 + 1].equals(str2)) {
                return this.b[i2 + 4];
            }
        }
        return null;
    }

    @Override // org.xml.sax.Attributes
    public final int getIndex(String str) {
        int i = this.a * 5;
        for (int i2 = 0; i2 < i; i2 += 5) {
            if (this.b[i2 + 2].equals(str)) {
                return i2 / 5;
            }
        }
        return -1;
    }

    @Override // org.xml.sax.Attributes
    public final String getType(String str) {
        int i = this.a * 5;
        for (int i2 = 0; i2 < i; i2 += 5) {
            if (this.b[i2 + 2].equals(str)) {
                return this.b[i2 + 3];
            }
        }
        return null;
    }

    @Override // org.xml.sax.Attributes
    public final String getValue(String str) {
        int i = this.a * 5;
        for (int i2 = 0; i2 < i; i2 += 5) {
            if (this.b[i2 + 2].equals(str)) {
                return this.b[i2 + 4];
            }
        }
        return null;
    }
}
