package rikka.shizuku;

import android.app.UiModeManager;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewGroup;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.xmlpull.v1.XmlPullParser;

/* JADX INFO: loaded from: classes.dex */
public abstract class Hn {
    public static boolean a = true;

    public static void a(InputStream inputStream) {
        while (inputStream.available() != 0) {
            try {
                inputStream.skip(inputStream.available());
            } catch (IOException unused) {
                return;
            }
        }
    }

    public static H5 b(C0659up c0659up, K1 k1, byte[] bArr) {
        C0233h c0233h = new C0233h();
        c0233h.d(c0659up);
        c0233h.d(k1);
        c0233h.d(new C0580s8(bArr, 0));
        D8 d8 = new D8(c0233h);
        d8.c = -1;
        AbstractC0726x abstractC0726xT = AbstractC0726x.t(d8);
        H5 h5 = new H5();
        h5.a = abstractC0726xT;
        if (abstractC0726xT.size() != 3) {
            throw new IllegalArgumentException("sequence wrong size for a certificate");
        }
        h5.b = C0659up.i(abstractC0726xT.u(0));
        K1.i(abstractC0726xT.u(1));
        AbstractC0079c.s(abstractC0726xT.u(2));
        return h5;
    }

    public static int c(Context context, int i, int i2) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(i, typedValue, true);
        return typedValue.resourceId != 0 ? i : i2;
    }

    public static C0788z d(TypedArray typedArray, XmlPullParser xmlPullParser, Resources.Theme theme, String str, int i) {
        C0788z c0788zA;
        if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", str) != null) {
            TypedValue typedValue = new TypedValue();
            typedArray.getValue(i, typedValue);
            int i2 = typedValue.type;
            if (i2 >= 28 && i2 <= 31) {
                return new C0788z((Shader) null, (ColorStateList) null, typedValue.data);
            }
            try {
                c0788zA = C0788z.a(typedArray.getResources(), typedArray.getResourceId(i, 0), theme);
            } catch (Exception e) {
                Log.e("ComplexColorCompat", "Failed to inflate ComplexColor.", e);
                c0788zA = null;
            }
            if (c0788zA != null) {
                return c0788zA;
            }
        }
        return new C0788z((Shader) null, (ColorStateList) null, 0);
    }

    public static String e(TypedArray typedArray, int i, int i2) {
        String string = typedArray.getString(i);
        return string == null ? typedArray.getString(i2) : string;
    }

    public static String f(AbstractActivityC0051b2 abstractActivityC0051b2) {
        return (!AbstractC0750xn.a.getBoolean("black_night_theme", ((UiModeManager) abstractActivityC0051b2.getSystemService(UiModeManager.class)).getCurrentModeType() == 6) || (abstractActivityC0051b2.getResources().getConfiguration().uiMode & 32) <= 0) ? AbstractC0750xn.a.getString("light_theme", "DEFAULT") : "BLACK";
    }

    public static boolean g(XmlPullParser xmlPullParser, String str) {
        return xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", str) != null;
    }

    public static int h(int i) {
        if (i == 1) {
            return 0;
        }
        if (i == 2) {
            return 1;
        }
        if (i == 4) {
            return 2;
        }
        if (i == 8) {
            return 3;
        }
        if (i == 16) {
            return 4;
        }
        if (i == 32) {
            return 5;
        }
        if (i == 64) {
            return 6;
        }
        if (i == 128) {
            return 7;
        }
        if (i == 256) {
            return 8;
        }
        if (i == 512) {
            return 9;
        }
        throw new IllegalArgumentException(AbstractC0535qo.b(i, "type needs to be >= FIRST and <= LAST, type="));
    }

    public static boolean i() {
        return Build.VERSION.SDK_INT >= 31 && AbstractC0750xn.a.getBoolean("use_system_color", true);
    }

    public static TypedArray j(Resources resources, Resources.Theme theme, AttributeSet attributeSet, int[] iArr) {
        return theme == null ? resources.obtainAttributes(attributeSet, iArr) : theme.obtainStyledAttributes(attributeSet, iArr, 0, 0);
    }

    public static byte[] k(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[4096];
        while (true) {
            int i = inputStream.read(bArr, 0, 4096);
            if (i < 0) {
                return byteArrayOutputStream.toByteArray();
            }
            byteArrayOutputStream.write(bArr, 0, i);
        }
    }

    public static int l(InputStream inputStream, byte[] bArr, int i) throws IOException {
        int i2 = 0;
        while (i2 < i) {
            int i3 = inputStream.read(bArr, i2, i - i2);
            if (i3 < 0) {
                break;
            }
            i2 += i3;
        }
        return i2;
    }

    public static final Object m(Em em, Em em2, F3 f3) throws Throwable {
        Object j6;
        Object objK;
        Object objC;
        InterfaceC0187ff interfaceC0187ff;
        C0589sh c0589sh = AbstractC0599sr.g;
        try {
            AbstractC0599sr.b(f3);
            j6 = f3.f(em2, em);
        } catch (Y9 e) {
            Throwable th = e.a;
            J6 j7 = new J6(th);
            do {
                objK = em.K(Kf.a.get(em), j7);
                if (objK == AbstractC0599sr.f || objK == c0589sh) {
                }
                throw th;
            } while (objK == AbstractC0599sr.h);
            em.m(objK);
            throw th;
        } catch (Throwable th2) {
            j6 = new J6(th2);
        }
        EnumC0057b8 enumC0057b8 = EnumC0057b8.a;
        if (j6 == enumC0057b8 || (objC = em.C(j6)) == c0589sh) {
            return enumC0057b8;
        }
        em.M();
        if (objC instanceof J6) {
            throw ((J6) objC).a;
        }
        C0218gf c0218gf = objC instanceof C0218gf ? (C0218gf) objC : null;
        return (c0218gf == null || (interfaceC0187ff = c0218gf.a) == null) ? objC : interfaceC0187ff;
    }

    public static void n(ViewGroup viewGroup, boolean z) {
        if (Build.VERSION.SDK_INT >= 29) {
            Ls.b(viewGroup, z);
        } else if (a) {
            try {
                Ls.b(viewGroup, z);
            } catch (NoSuchMethodError unused) {
                a = false;
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:25:0x0047  */
    /* JADX WARN: Code duplicated, block: B:28:0x005b  */
    /* JADX WARN: Code duplicated, block: B:30:0x005f  */
    /* JADX WARN: Code duplicated, block: B:32:0x0063  */
    /* JADX WARN: Code duplicated, block: B:35:0x006e  */
    /* JADX WARN: Code duplicated, block: B:39:0x007c A[LOOP:0: B:24:0x0045->B:39:0x007c, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:42:0x008b  */
    /* JADX WARN: Code duplicated, block: B:56:0x007a A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:57:0x007a A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:58:0x0084 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:59:0x007a A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:60:0x007a A[SYNTHETIC] */
    public static final long o(String str, long j, long j2, long j3) {
        String property;
        Long lValueOf;
        int i;
        boolean z;
        long j4;
        long j5;
        boolean z2;
        int iDigit;
        long j6;
        long j7;
        int i2 = AbstractC0566rp.a;
        try {
            property = System.getProperty(str);
        } catch (SecurityException unused) {
            property = null;
        }
        if (property == null) {
            return j;
        }
        int length = property.length();
        if (length != 0) {
            int i3 = 0;
            char cCharAt = property.charAt(0);
            long j8 = -9223372036854775807L;
            if (cCharAt < '0') {
                if (length != 1) {
                    if (cCharAt == '+') {
                        i = 0;
                        i3 = 1;
                        z = true;
                        j4 = 0;
                        j5 = -256204778801521550L;
                        while (true) {
                            if (i3 >= length) {
                                if (i == 0) {
                                    j4 = -j4;
                                }
                                lValueOf = Long.valueOf(j4);
                                break;
                            }
                            z2 = z;
                            iDigit = Character.digit((int) property.charAt(i3), 10);
                            if (iDigit >= 0) {
                                if (j4 < j5) {
                                    if (j5 == -256204778801521550L) {
                                        j5 = j8 / ((long) 10);
                                        if (j4 < j5) {
                                        }
                                    }
                                }
                                j6 = j4 * ((long) 10);
                                j7 = iDigit;
                                if (j6 >= j8 + j7) {
                                    j4 = j6 - j7;
                                    i3++;
                                    z = z2;
                                    length = length;
                                }
                            }
                        }
                    } else if (cCharAt == '-') {
                        j8 = Long.MIN_VALUE;
                        i3 = 1;
                        i = i3;
                        z = true;
                        j4 = 0;
                        j5 = -256204778801521550L;
                        while (true) {
                            if (i3 >= length) {
                                if (i == 0) {
                                    j4 = -j4;
                                }
                                lValueOf = Long.valueOf(j4);
                                break;
                            }
                            z2 = z;
                            iDigit = Character.digit((int) property.charAt(i3), 10);
                            if (iDigit >= 0) {
                                if (j4 < j5) {
                                    if (j5 == -256204778801521550L) {
                                        j5 = j8 / ((long) 10);
                                        if (j4 < j5) {
                                        }
                                    }
                                }
                                j6 = j4 * ((long) 10);
                                j7 = iDigit;
                                if (j6 >= j8 + j7) {
                                    j4 = j6 - j7;
                                    i3++;
                                    z = z2;
                                    length = length;
                                }
                            }
                        }
                    } else {
                        break;
                    }
                }
            } else {
                i = i3;
                z = true;
                j4 = 0;
                j5 = -256204778801521550L;
                while (true) {
                    if (i3 >= length) {
                        if (i == 0) {
                            j4 = -j4;
                        }
                        lValueOf = Long.valueOf(j4);
                        break;
                    }
                    z2 = z;
                    iDigit = Character.digit((int) property.charAt(i3), 10);
                    if (iDigit >= 0) {
                        if (j4 < j5) {
                            if (j5 == -256204778801521550L) {
                                j5 = j8 / ((long) 10);
                                if (j4 < j5) {
                                }
                            }
                        }
                        j6 = j4 * ((long) 10);
                        j7 = iDigit;
                        if (j6 >= j8 + j7) {
                            j4 = j6 - j7;
                            i3++;
                            z = z2;
                            length = length;
                        }
                    }
                }
            }
            lValueOf = null;
            break;
        } else {
            lValueOf = null;
            break;
        }
        if (lValueOf == null) {
            throw new IllegalStateException(("System property '" + str + "' has unrecognized value '" + property + '\'').toString());
        }
        long jLongValue = lValueOf.longValue();
        if (j2 <= jLongValue && jLongValue <= j3) {
            return jLongValue;
        }
        throw new IllegalStateException(("System property '" + str + "' should be in range " + j2 + ".." + j3 + ", but is '" + jLongValue + '\'').toString());
    }

    public static int p(String str, int i, int i2) {
        return (int) o(str, i, 1, (i2 & 8) != 0 ? Integer.MAX_VALUE : 2097150);
    }
}
