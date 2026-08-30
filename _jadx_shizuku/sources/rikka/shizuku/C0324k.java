package rikka.shizuku;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

/* JADX INFO: renamed from: rikka.shizuku.k, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0324k extends AbstractC0633u {
    public final byte[] a;

    public C0324k(String str) {
        this.a = Wo.b(str);
        try {
            t();
        } catch (ParseException e) {
            throw new IllegalArgumentException("invalid date string: " + e.getMessage());
        }
    }

    public static String s(int i) {
        return i < 10 ? AbstractC0535qo.b(i, "0") : Integer.toString(i);
    }

    public static String z(String str) {
        String str2;
        StringBuilder sb;
        char cCharAt;
        String strSubstring = str.substring(14);
        int i = 1;
        while (i < strSubstring.length() && '0' <= (cCharAt = strSubstring.charAt(i)) && cCharAt <= '9') {
            i++;
        }
        int i2 = i - 1;
        if (i2 > 3) {
            str2 = strSubstring.substring(0, 4) + strSubstring.substring(i);
            sb = new StringBuilder();
        } else if (i2 == 1) {
            str2 = strSubstring.substring(0, i) + "00" + strSubstring.substring(i);
            sb = new StringBuilder();
        } else {
            if (i2 != 2) {
                return str;
            }
            str2 = strSubstring.substring(0, i) + "0" + strSubstring.substring(i);
            sb = new StringBuilder();
        }
        sb.append(str.substring(0, 14));
        sb.append(str2);
        return sb.toString();
    }

    @Override // rikka.shizuku.AbstractC0633u, rikka.shizuku.AbstractC0417n
    public final int hashCode() {
        return AbstractC0046as.D(this.a);
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final boolean i(AbstractC0633u abstractC0633u) {
        if (!(abstractC0633u instanceof C0324k)) {
            return false;
        }
        return Arrays.equals(this.a, ((C0324k) abstractC0633u).a);
    }

    @Override // rikka.shizuku.AbstractC0633u
    public void j(Ys ys, boolean z) throws IOException {
        ys.H(z, 24, this.a);
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final boolean k() {
        return false;
    }

    @Override // rikka.shizuku.AbstractC0633u
    public int m(boolean z) {
        return Ys.v(this.a.length, z);
    }

    @Override // rikka.shizuku.AbstractC0633u
    public AbstractC0633u p() {
        return new C0673v8(this.a);
    }

    @Override // rikka.shizuku.AbstractC0633u
    public AbstractC0633u q() {
        return new C0673v8(this.a);
    }

    public final SimpleDateFormat r() {
        SimpleDateFormat simpleDateFormat;
        if (v()) {
            simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss.SSSz");
        } else if (x()) {
            simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssz");
        } else {
            simpleDateFormat = w() ? new SimpleDateFormat("yyyyMMddHHmmz") : new SimpleDateFormat("yyyyMMddHHz");
        }
        simpleDateFormat.setTimeZone(new SimpleTimeZone(0, "Z"));
        return simpleDateFormat;
    }

    public final Date t() {
        SimpleDateFormat simpleDateFormatR;
        SimpleDateFormat simpleDateFormat;
        String strA = Wo.a(this.a);
        if (strA.endsWith("Z")) {
            if (v()) {
                simpleDateFormatR = new SimpleDateFormat("yyyyMMddHHmmss.SSS'Z'");
            } else if (x()) {
                simpleDateFormatR = new SimpleDateFormat("yyyyMMddHHmmss'Z'");
            } else {
                simpleDateFormatR = w() ? new SimpleDateFormat("yyyyMMddHHmm'Z'") : new SimpleDateFormat("yyyyMMddHH'Z'");
            }
            simpleDateFormatR.setTimeZone(new SimpleTimeZone(0, "Z"));
        } else if (strA.indexOf(45) > 0 || strA.indexOf(43) > 0) {
            strA = u();
            simpleDateFormatR = r();
        } else {
            if (v()) {
                simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss.SSS");
            } else if (x()) {
                simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            } else {
                simpleDateFormat = w() ? new SimpleDateFormat("yyyyMMddHHmm") : new SimpleDateFormat("yyyyMMddHH");
            }
            simpleDateFormatR = simpleDateFormat;
            simpleDateFormatR.setTimeZone(new SimpleTimeZone(0, TimeZone.getDefault().getID()));
        }
        if (v()) {
            strA = z(strA);
        }
        return T8.a(simpleDateFormatR.parse(strA));
    }

    public final String u() {
        String str;
        String strA = Wo.a(this.a);
        if (strA.charAt(strA.length() - 1) == 'Z') {
            return strA.substring(0, strA.length() - 1) + "GMT+00:00";
        }
        int length = strA.length();
        char cCharAt = strA.charAt(length - 6);
        if ((cCharAt == '-' || cCharAt == '+') && strA.indexOf("GMT") == length - 9) {
            return strA;
        }
        int length2 = strA.length();
        int i = length2 - 5;
        char cCharAt2 = strA.charAt(i);
        if (cCharAt2 == '-' || cCharAt2 == '+') {
            StringBuilder sb = new StringBuilder();
            sb.append(strA.substring(0, i));
            sb.append("GMT");
            int i2 = length2 - 2;
            sb.append(strA.substring(i, i2));
            sb.append(":");
            sb.append(strA.substring(i2));
            return sb.toString();
        }
        int length3 = strA.length() - 3;
        char cCharAt3 = strA.charAt(length3);
        if (cCharAt3 == '-' || cCharAt3 == '+') {
            return strA.substring(0, length3) + "GMT" + strA.substring(length3) + ":00";
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(strA);
        TimeZone timeZone = TimeZone.getDefault();
        int rawOffset = timeZone.getRawOffset();
        if (rawOffset < 0) {
            rawOffset = -rawOffset;
            str = "-";
        } else {
            str = "+";
        }
        int i3 = rawOffset / 3600000;
        int i4 = (rawOffset - (3600000 * i3)) / 60000;
        try {
            if (timeZone.useDaylightTime()) {
                if (v()) {
                    strA = z(strA);
                }
                if (timeZone.inDaylightTime(r().parse(strA + "GMT" + str + s(i3) + ":" + s(i4)))) {
                    i3 += str.equals("+") ? 1 : -1;
                }
            }
        } catch (ParseException unused) {
        }
        sb2.append("GMT" + str + s(i3) + ":" + s(i4));
        return sb2.toString();
    }

    public final boolean v() {
        int i = 0;
        while (true) {
            byte[] bArr = this.a;
            if (i == bArr.length) {
                return false;
            }
            if (bArr[i] == 46 && i == 14) {
                return true;
            }
            i++;
        }
    }

    public final boolean w() {
        return y(10) && y(11);
    }

    public final boolean x() {
        return y(12) && y(13);
    }

    public final boolean y(int i) {
        byte b;
        byte[] bArr = this.a;
        return bArr.length > i && (b = bArr[i]) >= 48 && b <= 57;
    }

    public C0324k(byte[] bArr) {
        if (bArr.length < 4) {
            throw new IllegalArgumentException("GeneralizedTime string too short");
        }
        this.a = bArr;
        if (!y(0) || !y(1) || !y(2) || !y(3)) {
            throw new IllegalArgumentException("illegal characters in GeneralizedTime string");
        }
    }
}
