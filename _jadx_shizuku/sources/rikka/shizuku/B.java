package rikka.shizuku;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public class B extends AbstractC0633u {
    public final byte[] a;

    public B(String str) {
        this.a = Wo.b(str);
        try {
            T8.a(new SimpleDateFormat("yyMMddHHmmssz").parse(r()));
        } catch (ParseException e) {
            throw new IllegalArgumentException("invalid date string: " + e.getMessage());
        }
    }

    @Override // rikka.shizuku.AbstractC0633u, rikka.shizuku.AbstractC0417n
    public final int hashCode() {
        return AbstractC0046as.D(this.a);
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final boolean i(AbstractC0633u abstractC0633u) {
        if (!(abstractC0633u instanceof B)) {
            return false;
        }
        return Arrays.equals(this.a, ((B) abstractC0633u).a);
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final void j(Ys ys, boolean z) throws IOException {
        ys.H(z, 23, this.a);
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final boolean k() {
        return false;
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final int m(boolean z) {
        return Ys.v(this.a.length, z);
    }

    public final String r() {
        StringBuilder sb;
        String strSubstring;
        String strA = Wo.a(this.a);
        if (strA.indexOf(45) >= 0 || strA.indexOf(43) >= 0) {
            int iIndexOf = strA.indexOf(45);
            if (iIndexOf < 0) {
                iIndexOf = strA.indexOf(43);
            }
            if (iIndexOf == strA.length() - 3) {
                strA = strA.concat("00");
            }
            if (iIndexOf == 10) {
                sb = new StringBuilder();
                sb.append(strA.substring(0, 10));
                sb.append("00GMT");
                sb.append(strA.substring(10, 13));
                sb.append(":");
                strSubstring = strA.substring(13, 15);
            } else {
                sb = new StringBuilder();
                sb.append(strA.substring(0, 12));
                sb.append("GMT");
                sb.append(strA.substring(12, 15));
                sb.append(":");
                strSubstring = strA.substring(15, 17);
            }
        } else if (strA.length() == 11) {
            sb = new StringBuilder();
            sb.append(strA.substring(0, 10));
            strSubstring = "00GMT+00:00";
        } else {
            sb = new StringBuilder();
            sb.append(strA.substring(0, 12));
            strSubstring = "GMT+00:00";
        }
        sb.append(strSubstring);
        return sb.toString();
    }

    public final String toString() {
        return Wo.a(this.a);
    }

    public B(byte[] bArr) {
        byte b;
        byte b2;
        if (bArr.length < 2) {
            throw new IllegalArgumentException("UTCTime string too short");
        }
        this.a = bArr;
        if (bArr.length <= 0 || (b = bArr[0]) < 48 || b > 57 || bArr.length <= 1 || (b2 = bArr[1]) < 48 || b2 > 57) {
            throw new IllegalArgumentException("illegal characters in UTCTime string");
        }
    }
}
