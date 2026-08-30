package rikka.shizuku;

/* JADX INFO: loaded from: classes.dex */
public final class mu {
    public final String a;
    public final char c;
    public final StringBuffer d = new StringBuffer();
    public int b = -1;

    public mu(String str, char c) {
        this.a = str;
        this.c = c;
    }

    public final boolean a() {
        return this.b != this.a.length();
    }

    public final String b() {
        int i = this.b;
        String str = this.a;
        if (i == str.length()) {
            return null;
        }
        int i2 = this.b + 1;
        StringBuffer stringBuffer = this.d;
        stringBuffer.setLength(0);
        boolean z = false;
        boolean z2 = false;
        while (i2 != str.length()) {
            char cCharAt = str.charAt(i2);
            if (cCharAt != '\"') {
                if (!z && !z2) {
                    if (cCharAt == '\\') {
                        stringBuffer.append(cCharAt);
                        z = true;
                    } else {
                        if (cCharAt == this.c) {
                            break;
                        }
                        stringBuffer.append(cCharAt);
                    }
                }
                i2++;
            } else if (!z) {
                z2 = !z2;
            }
            stringBuffer.append(cCharAt);
            z = false;
            i2++;
        }
        this.b = i2;
        return stringBuffer.toString();
    }
}
