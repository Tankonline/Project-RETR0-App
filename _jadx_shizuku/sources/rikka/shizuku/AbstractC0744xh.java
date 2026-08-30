package rikka.shizuku;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AppOpsManager;
import android.app.FragmentManager;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EdgeEffect;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Objects;
import moe.shizuku.privileged.api.R;

/* JADX INFO: renamed from: rikka.shizuku.xh, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0744xh {
    public static boolean b;
    public static C0516q5 c;
    public static Boolean k;
    public static Boolean l;
    public static IBinder m;
    public static String n;
    public static int o;
    public static String p;
    public static final Gn[] a = new Gn[1];
    public static final int[] d = new int[0];
    public static final Object[] e = new Object[0];
    public static final C0589sh f = new C0589sh(1, "CLOSED_EMPTY");
    public static final Object g = new Object();
    public static final boolean[] h = new boolean[3];
    public static final Object i = new Object();
    public static final Ne j = new Ne();

    public static final C0517q6 A(Vs vs) {
        C0517q6 c0517q6;
        V7 v7;
        AutoCloseable autoCloseable;
        synchronized (j) {
            c0517q6 = (C0517q6) vs.b("androidx.lifecycle.viewmodel.internal.ViewModelCoroutineScope.JOB_KEY");
            if (c0517q6 == null) {
                try {
                    C0550r9 c0550r9 = AbstractC0090ca.a;
                    v7 = AbstractC0713wh.a.d;
                } catch (IllegalStateException unused) {
                    v7 = C0152eb.a;
                }
                C0517q6 c0517q7 = new C0517q6(v7.b(new C0259hp()));
                Ws ws = vs.a;
                if (ws != null) {
                    if (ws.d) {
                        Ws.a(c0517q7);
                    } else {
                        synchronized (ws.a) {
                            autoCloseable = (AutoCloseable) ws.b.put("androidx.lifecycle.viewmodel.internal.ViewModelCoroutineScope.JOB_KEY", c0517q7);
                        }
                        Ws.a(autoCloseable);
                    }
                }
                c0517q6 = c0517q7;
            }
        }
        return c0517q6;
    }

    public static void B(Activity activity) {
        if (Build.VERSION.SDK_INT >= 29) {
            Il.Companion.getClass();
            activity.registerActivityLifecycleCallbacks(new Il());
        }
        FragmentManager fragmentManager = activity.getFragmentManager();
        if (fragmentManager.findFragmentByTag("androidx.lifecycle.LifecycleDispatcher.report_fragment_tag") == null) {
            fragmentManager.beginTransaction().add(new Jl(), "androidx.lifecycle.LifecycleDispatcher.report_fragment_tag").commit();
            fragmentManager.executePendingTransactions();
        }
    }

    public static G7 C(G7 g7) {
        H7 h7 = g7 instanceof H7 ? (H7) g7 : null;
        if (h7 == null || (g7 = h7.c) != null) {
            return g7;
        }
        I7 i7 = (I7) h7.b.g(Aa.a);
        G7 z9 = i7 != null ? new Z9((W7) i7, h7) : h7;
        h7.c = z9;
        return z9;
    }

    public static boolean E(int i2) {
        if (i2 == 0) {
            return false;
        }
        ThreadLocal threadLocal = E6.a;
        double[] dArr = (double[]) threadLocal.get();
        if (dArr == null) {
            dArr = new double[3];
            threadLocal.set(dArr);
        }
        int iRed = Color.red(i2);
        int iGreen = Color.green(i2);
        int iBlue = Color.blue(i2);
        if (dArr.length != 3) {
            throw new IllegalArgumentException("outXyz must have a length of 3.");
        }
        double d2 = ((double) iRed) / 255.0d;
        double dPow = d2 < 0.04045d ? d2 / 12.92d : Math.pow((d2 + 0.055d) / 1.055d, 2.4d);
        double d3 = ((double) iGreen) / 255.0d;
        double dPow2 = d3 < 0.04045d ? d3 / 12.92d : Math.pow((d3 + 0.055d) / 1.055d, 2.4d);
        double d4 = ((double) iBlue) / 255.0d;
        double dPow3 = d4 < 0.04045d ? d4 / 12.92d : Math.pow((d4 + 0.055d) / 1.055d, 2.4d);
        dArr[0] = ((0.1805d * dPow3) + (0.3576d * dPow2) + (0.4124d * dPow)) * 100.0d;
        double d5 = ((0.0722d * dPow3) + (0.7152d * dPow2) + (0.2126d * dPow)) * 100.0d;
        dArr[1] = d5;
        dArr[2] = ((dPow3 * 0.9505d) + (dPow2 * 0.1192d) + (dPow * 0.0193d)) * 100.0d;
        return d5 / 100.0d > 0.5d;
    }

    public static boolean F() {
        String language = C0281ih.b.getLanguage();
        if (!language.equals("zh") && !language.equals("ja") && !language.equals("ko")) {
            return false;
        }
        if (l == null) {
            Bitmap.Config config = Bitmap.Config.ARGB_8888;
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(40, 40, config);
            Bitmap bitmapCreateBitmap2 = Bitmap.createBitmap(40, 40, config);
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            Canvas canvas2 = new Canvas(bitmapCreateBitmap2);
            TextPaint textPaint = new TextPaint();
            textPaint.setTextSize(20.0f);
            textPaint.setTextLocale(Locale.CHINESE);
            textPaint.setAntiAlias(false);
            textPaint.setSubpixelText(false);
            textPaint.setFakeBoldText(false);
            textPaint.setTypeface(Typeface.create("sans-serif", 0));
            canvas.drawText("好", 20.0f, 20.0f, textPaint);
            textPaint.setTypeface(Typeface.create("sans-serif-medium", 0));
            canvas2.drawText("好", 20.0f, 20.0f, textPaint);
            l = Boolean.valueOf(bitmapCreateBitmap.sameAs(bitmapCreateBitmap2));
        }
        return l.booleanValue();
    }

    public static void G(Context context, String str) {
        ActivityOptions activityOptionsA;
        Uri uri = Uri.parse(str);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.putExtra("android.support.customtabs.extra.TITLE_VISIBILITY", 1);
        Uri.Builder builderBuildUpon = uri.buildUpon();
        if ((context.getResources().getConfiguration().uiMode & 32) > 0) {
            builderBuildUpon.appendQueryParameter("night", "1");
        }
        if (!intent.hasExtra("android.support.customtabs.extra.SESSION")) {
            Bundle bundle = new Bundle();
            bundle.putBinder("android.support.customtabs.extra.SESSION", null);
            intent.putExtras(bundle);
        }
        intent.putExtra("android.support.customtabs.extra.EXTRA_ENABLE_INSTANT_APPS", true);
        intent.putExtras(new Bundle());
        intent.putExtra("androidx.browser.customtabs.extra.SHARE_STATE", 0);
        int i2 = Build.VERSION.SDK_INT;
        String strA = AbstractC0395m8.a();
        if (!TextUtils.isEmpty(strA)) {
            Bundle bundleExtra = intent.hasExtra("com.android.browser.headers") ? intent.getBundleExtra("com.android.browser.headers") : new Bundle();
            if (!bundleExtra.containsKey("Accept-Language")) {
                bundleExtra.putString("Accept-Language", strA);
                intent.putExtra("com.android.browser.headers", bundleExtra);
            }
        }
        if (i2 >= 34) {
            activityOptionsA = AbstractC0364l8.a();
            AbstractC0426n8.a(activityOptionsA, false);
        } else {
            activityOptionsA = null;
        }
        Bundle bundle2 = activityOptionsA != null ? activityOptionsA.toBundle() : null;
        try {
            intent.setData(builderBuildUpon.build());
            context.startActivity(intent, bundle2);
        } catch (ActivityNotFoundException unused) {
            Intent intent2 = new Intent("android.intent.action.VIEW");
            intent2.setData(uri);
            try {
                try {
                    context.startActivity(intent2);
                } catch (Throwable unused2) {
                }
            } catch (Throwable unused3) {
                try {
                    ((ClipboardManager) context.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("label", str));
                } catch (Exception unused4) {
                }
                Ch ch = new Ch(context);
                E1 e1 = (E1) ch.b;
                e1.d = e1.a.getText(R.string._0_resource_name_obfuscated_res_0x7f110058);
                e1.f = AbstractC0599sr.m(0, context.getString(R.string._0_resource_name_obfuscated_res_0x7f11012e, str));
                ch.g(android.R.string.ok, null);
                ch.f();
            }
        }
    }

    public static int H(int i2, int i3, float f2) {
        return E6.c(E6.e(i3, Math.round(Color.alpha(i3) * f2)), i2);
    }

    public static void I() {
        if (p == null) {
            System.loadLibrary("rish");
            return;
        }
        System.load(p + "/librish.so");
    }

    public static float N(EdgeEffect edgeEffect, float f2, float f3) {
        if (Build.VERSION.SDK_INT >= 31) {
            return AbstractC0768ya.c(edgeEffect, f2, f3);
        }
        AbstractC0737xa.a(edgeEffect, f2, f3);
        return f2;
    }

    public static V7 P(V7 v7, V7 v8) {
        return v8 == C0152eb.a ? v7 : (V7) v8.l(v7, new F6(1));
    }

    public static boolean Q(Ok ok, Ok ok2) {
        if (ok.a.a.length == ok2.a.a.length) {
            C0515q4[] c0515q4ArrJ = ok.j();
            C0515q4[] c0515q4ArrJ2 = ok2.j();
            if (c0515q4ArrJ.length == c0515q4ArrJ2.length) {
                for (int i2 = 0; i2 != c0515q4ArrJ.length; i2++) {
                    C0515q4 c0515q4 = c0515q4ArrJ[i2];
                    C0515q4 c0515q5 = c0515q4ArrJ2[i2];
                    if (c0515q4 == c0515q5 || (c0515q4 != null && c0515q5 != null && c0515q4.a.n(c0515q5.a) && f(c0515q4.b).equals(f(c0515q5.b)))) {
                    }
                }
                return true;
            }
        }
        return false;
    }

    public static final Object R(Object obj) {
        return obj instanceof J6 ? new C0071bm(((J6) obj).a) : obj;
    }

    public static AbstractC0633u Y(C0510q c0510q, String str) {
        if (str.length() != 0 && str.charAt(0) == '#') {
            try {
                int length = (str.length() - 1) / 2;
                byte[] bArr = new byte[length];
                for (int i2 = 0; i2 != length; i2++) {
                    int i3 = i2 * 2;
                    char cCharAt = str.charAt(i3 + 1);
                    bArr[i2] = (byte) (l(str.charAt(i3 + 2)) | (l(cCharAt) << 4));
                }
                return AbstractC0633u.o(bArr);
            } catch (IOException unused) {
                throw new C0602t(0, "can't recode value for oid " + c0510q.a);
            }
        }
        if (str.length() != 0 && str.charAt(0) == '\\') {
            str = str.substring(1);
        }
        if (c0510q.n(C0700w4.x) || c0510q.n(C0700w4.y)) {
            return new C0735x8(str);
        }
        if (c0510q.n(C0700w4.v)) {
            return new C0324k(str);
        }
        if (c0510q.n(C0700w4.s) || c0510q.n(C0700w4.t) || c0510q.n(C0700w4.u) || c0510q.n(C0700w4.w)) {
            return new C8(str);
        }
        int i4 = Wo.a;
        char[] charArray = str.toCharArray();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            Wo.d(charArray, byteArrayOutputStream);
            return new H8(byteArrayOutputStream.toByteArray());
        } catch (IOException unused2) {
            throw new IllegalStateException("cannot encode string to byte array!");
        }
    }

    public static void Z(R2 r2, C0805zg c0805zg) {
        EnumC0588sg enumC0588sg = c0805zg.c;
        if (enumC0588sg == EnumC0588sg.b || enumC0588sg.a(EnumC0588sg.d)) {
            r2.W();
        } else {
            c0805zg.a(new C0520q9(c0805zg, 1, r2));
        }
    }

    public static void a(Throwable th, Throwable th2) {
        if (th != th2) {
            Integer num = Bf.a;
            if (num == null || num.intValue() >= 19) {
                th.addSuppressed(th2);
                return;
            }
            Method method = Vj.a;
            if (method != null) {
                method.invoke(th, th2);
            }
        }
    }

    public static String a0(String str) {
        int i2;
        if (str.length() == 0 || (str.indexOf(92) < 0 && str.indexOf(34) < 0)) {
            return str.trim();
        }
        char[] charArray = str.toCharArray();
        StringBuffer stringBuffer = new StringBuffer(str.length());
        if (charArray[0] == '\\' && charArray[1] == '#') {
            stringBuffer.append("\\#");
            i2 = 2;
        } else {
            i2 = 0;
        }
        boolean z = false;
        int length = 0;
        boolean z2 = false;
        boolean z3 = false;
        char c2 = 0;
        while (i2 != charArray.length) {
            char c3 = charArray[i2];
            if (c3 != ' ') {
                z3 = true;
            }
            if (c3 != '\"') {
                if (c3 == '\\' && !z && !z2) {
                    length = stringBuffer.length();
                    z = true;
                } else if (c3 != ' ' || z || z3) {
                    if (!z || (('0' > c3 || c3 > '9') && (('a' > c3 || c3 > 'f') && ('A' > c3 || c3 > 'F')))) {
                        stringBuffer.append(c3);
                    } else if (c2 != 0) {
                        stringBuffer.append((char) (l(c3) + (l(c2) * 16)));
                        z = false;
                        c2 = 0;
                    } else {
                        c2 = c3;
                    }
                }
                i2++;
            } else if (z) {
                stringBuffer.append(c3);
            } else {
                z2 = !z2;
            }
            z = false;
            i2++;
        }
        if (stringBuffer.length() > 0) {
            while (stringBuffer.charAt(stringBuffer.length() - 1) == ' ' && length != stringBuffer.length() - 1) {
                stringBuffer.setLength(stringBuffer.length() - 1);
            }
        }
        return stringBuffer.toString();
    }

    public static void b(StringBuffer stringBuffer, C0515q4 c0515q4, Hashtable hashtable) {
        String str = (String) hashtable.get(c0515q4.a);
        if (str == null) {
            str = c0515q4.a.a;
        }
        stringBuffer.append(str);
        stringBuffer.append('=');
        stringBuffer.append(b0(c0515q4.b));
    }

    public static String b0(InterfaceC0202g interfaceC0202g) {
        StringBuffer stringBuffer = new StringBuffer();
        if (!(interfaceC0202g instanceof A) || (interfaceC0202g instanceof I8)) {
            try {
                stringBuffer.append('#');
                byte[] bArrH = interfaceC0202g.d().h();
                R2 r2 = AbstractC0525qe.a;
                stringBuffer.append(Wo.a(AbstractC0525qe.a(bArrH, bArrH.length)));
            } catch (IOException unused) {
                throw new IllegalArgumentException("Other value has no encoded form");
            }
        } else {
            String strE = ((A) interfaceC0202g).e();
            if (strE.length() > 0 && strE.charAt(0) == '#') {
                stringBuffer.append('\\');
            }
            stringBuffer.append(strE);
        }
        int length = stringBuffer.length();
        int i2 = (stringBuffer.length() >= 2 && stringBuffer.charAt(0) == '\\' && stringBuffer.charAt(1) == '#') ? 2 : 0;
        while (i2 != length) {
            char cCharAt = stringBuffer.charAt(i2);
            if (cCharAt != '\"' && cCharAt != '\\' && cCharAt != '+' && cCharAt != ',') {
                switch (cCharAt) {
                    case ';':
                    case '<':
                    case '=':
                    case '>':
                        break;
                    default:
                        i2++;
                        break;
                }
            }
            stringBuffer.insert(i2, "\\");
            i2 += 2;
            length++;
        }
        if (stringBuffer.length() > 0) {
            for (int i3 = 0; stringBuffer.length() > i3 && stringBuffer.charAt(i3) == ' '; i3 += 2) {
                stringBuffer.insert(i3, "\\");
            }
        }
        for (int length2 = stringBuffer.length() - 1; length2 >= 0 && stringBuffer.charAt(length2) == ' '; length2--) {
            stringBuffer.insert(length2, '\\');
        }
        return stringBuffer.toString();
    }

    public static boolean c(lu luVar, lu luVar2) {
        Ok[] okArr = (Ok[]) luVar.d.clone();
        Ok[] okArr2 = (Ok[]) luVar2.d.clone();
        if (okArr.length == okArr2.length) {
            boolean z = (okArr[0].i() == null || okArr2[0].i() == null) ? false : !okArr[0].i().a.n(okArr2[0].i().a);
            for (int i2 = 0; i2 != okArr.length; i2++) {
                Ok ok = okArr[i2];
                if (z) {
                    for (int length = okArr2.length - 1; length >= 0; length--) {
                        Ok ok2 = okArr2[length];
                        if (ok2 != null && Q(ok, ok2)) {
                            okArr2[length] = null;
                        }
                    }
                } else {
                    for (int i3 = 0; i3 != okArr2.length; i3++) {
                        Ok ok3 = okArr2[i3];
                        if (ok3 != null && Q(ok, ok3)) {
                            okArr2[i3] = null;
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    public static Class c0(Class cls) {
        if (cls == Integer.TYPE) {
            return Integer.class;
        }
        if (cls == Float.TYPE) {
            return Float.class;
        }
        if (cls == Byte.TYPE) {
            return Byte.class;
        }
        if (cls == Double.TYPE) {
            return Double.class;
        }
        if (cls == Long.TYPE) {
            return Long.class;
        }
        if (cls == Character.TYPE) {
            return Character.class;
        }
        if (cls == Boolean.TYPE) {
            return Boolean.class;
        }
        if (cls == Short.TYPE) {
            return Short.class;
        }
        return cls == Void.TYPE ? Void.class : cls;
    }

    public static final int d(int i2, int i3, int[] iArr) {
        int i4 = i2 - 1;
        int i5 = 0;
        while (i5 <= i4) {
            int i6 = (i5 + i4) >>> 1;
            int i7 = iArr[i6];
            if (i7 < i3) {
                i5 = i6 + 1;
            } else {
                if (i7 <= i3) {
                    return i6;
                }
                i4 = i6 - 1;
            }
        }
        return ~i5;
    }

    public static final int e(long[] jArr, int i2, long j2) {
        int i3 = i2 - 1;
        int i4 = 0;
        while (i4 <= i3) {
            int i5 = (i4 + i3) >>> 1;
            long j3 = jArr[i5];
            if (j3 < j2) {
                i4 = i5 + 1;
            } else {
                if (j3 <= j2) {
                    return i5;
                }
                i3 = i5 - 1;
            }
        }
        return ~i4;
    }

    public static String f(InterfaceC0202g interfaceC0202g) {
        String strB0 = b0(interfaceC0202g);
        if (strB0.length() > 0 && strB0.charAt(0) == '#') {
            try {
                try {
                    Object objO = AbstractC0633u.o(AbstractC0525qe.a.h(strB0.length() - 1, strB0));
                    if (objO instanceof A) {
                        strB0 = ((A) objO).e();
                    }
                } catch (Exception e2) {
                    C0602t c0602t = new C0602t(1, "exception decoding Hex string: " + e2.getMessage());
                    c0602t.b = e2;
                    throw c0602t;
                }
            } catch (IOException e3) {
                throw new IllegalStateException("unknown encoding in name: " + e3);
            }
        }
        String strC = Wo.c(strB0);
        int length = strC.length();
        if (length < 2) {
            return strC;
        }
        int i2 = length - 1;
        int i3 = 0;
        while (i3 < i2 && strC.charAt(i3) == '\\' && strC.charAt(i3 + 1) == ' ') {
            i3 += 2;
        }
        int i4 = i3 + 1;
        int i5 = i2;
        while (i5 > i4 && strC.charAt(i5 - 1) == '\\' && strC.charAt(i5) == ' ') {
            i5 -= 2;
        }
        if (i3 > 0 || i5 < i2) {
            strC = strC.substring(i3, i5 + 1);
        }
        if (strC.indexOf("  ") < 0) {
            return strC;
        }
        StringBuffer stringBuffer = new StringBuffer();
        char cCharAt = strC.charAt(0);
        stringBuffer.append(cCharAt);
        for (int i6 = 1; i6 < strC.length(); i6++) {
            char cCharAt2 = strC.charAt(i6);
            if (cCharAt != ' ' || cCharAt2 != ' ') {
                stringBuffer.append(cCharAt2);
                cCharAt = cCharAt2;
            }
        }
        return stringBuffer.toString();
    }

    public static void g(boolean z) {
        if (!z) {
            throw new IllegalArgumentException();
        }
    }

    public static int h(Context context, String str) {
        int iNoteProxyOpNoThrow;
        int iMyPid = Process.myPid();
        int iMyUid = Process.myUid();
        String packageName = context.getPackageName();
        if (context.checkPermission(str, iMyPid, iMyUid) != -1) {
            String strPermissionToOp = AppOpsManager.permissionToOp(str);
            if (strPermissionToOp != null) {
                if (packageName == null) {
                    String[] packagesForUid = context.getPackageManager().getPackagesForUid(iMyUid);
                    if (packagesForUid != null && packagesForUid.length > 0) {
                        packageName = packagesForUid[0];
                    }
                }
                int iMyUid2 = Process.myUid();
                String packageName2 = context.getPackageName();
                if (iMyUid2 == iMyUid && Objects.equals(packageName2, packageName) && Build.VERSION.SDK_INT >= 29) {
                    AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(AppOpsManager.class);
                    iNoteProxyOpNoThrow = appOpsManager == null ? 1 : appOpsManager.checkOpNoThrow(strPermissionToOp, Binder.getCallingUid(), packageName);
                    if (iNoteProxyOpNoThrow == 0) {
                        iNoteProxyOpNoThrow = appOpsManager != null ? appOpsManager.checkOpNoThrow(strPermissionToOp, iMyUid, Q3.a(context)) : 1;
                    }
                } else {
                    iNoteProxyOpNoThrow = ((AppOpsManager) context.getSystemService(AppOpsManager.class)).noteProxyOpNoThrow(strPermissionToOp, packageName);
                }
                if (iNoteProxyOpNoThrow != 0) {
                    return -2;
                }
            }
            return 0;
        }
        return -1;
    }

    public static int i(int i2, int i3, int i4) {
        if (i2 < i3) {
            return i3;
        }
        return i2 > i4 ? i4 : i2;
    }

    public static int l(char c2) {
        if ('0' > c2 || c2 > '9') {
            return ('a' > c2 || c2 > 'f') ? c2 - '7' : c2 - 'W';
        }
        return c2 - '0';
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static G7 m(G7 g7, G7 g8, InterfaceC0585sd interfaceC0585sd) {
        if (interfaceC0585sd instanceof Q4) {
            return ((Q4) interfaceC0585sd).i(g7, g8);
        }
        V7 v7E = g8.e();
        return v7E == C0152eb.a ? new C0773yf(g8, g7, interfaceC0585sd) : new C0804zf(g8, v7E, interfaceC0585sd, g7);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void n(Activity activity, EnumC0557rg enumC0557rg) {
        C0805zg c0805zgE;
        if (!(activity instanceof InterfaceC0743xg) || (c0805zgE = ((InterfaceC0743xg) activity).e()) == null) {
            return;
        }
        c0805zgE.d(enumC0557rg);
    }

    public static synchronized Gn p() {
        Gn gnQ;
        try {
            gnQ = q();
            if (gnQ == null) {
                if (b) {
                    throw new Zi("The main shell died during initialization");
                }
                b = true;
                if (c == null) {
                    c = new C0516q5(0);
                }
                C0516q5 c0516q5 = c;
                c0516q5.getClass();
                Gn gn = null;
                try {
                    Gn gnA = c0516q5.a("su");
                    try {
                        if (gnA.a >= 1) {
                            gn = gnA;
                        }
                    } catch (Zi unused) {
                    }
                } catch (Zi unused2) {
                }
                if (gn == null) {
                    synchronized (AbstractC0046as.class) {
                    }
                    gnQ = c0516q5.a("sh");
                } else {
                    gnQ = gn;
                }
                b = false;
            }
        } catch (Throwable th) {
            throw th;
        }
        return gnQ;
    }

    public static Gn q() {
        Gn gn;
        Gn[] gnArr = a;
        synchronized (gnArr) {
            try {
                gn = gnArr[0];
                if (gn != null && gn.a < 0) {
                    gn = null;
                    gnArr[0] = null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return gn;
    }

    public static int r(Context context, int i2, int i3) {
        Integer numValueOf;
        TypedValue typedValueM = AbstractC0046as.M(context, i2);
        if (typedValueM != null) {
            int i4 = typedValueM.resourceId;
            numValueOf = Integer.valueOf(i4 != 0 ? context.getColor(i4) : typedValueM.data);
        } else {
            numValueOf = null;
        }
        return numValueOf != null ? numValueOf.intValue() : i3;
    }

    public static int s(View view, int i2) {
        Context context = view.getContext();
        TypedValue typedValueQ = AbstractC0046as.Q(view.getContext(), i2, view.getClass().getCanonicalName());
        int i3 = typedValueQ.resourceId;
        return i3 != 0 ? context.getColor(i3) : typedValueQ.data;
    }

    public static float u(EdgeEffect edgeEffect) {
        if (Build.VERSION.SDK_INT >= 31) {
            return AbstractC0768ya.b(edgeEffect);
        }
        return 0.0f;
    }

    public static Drawable v(Context context, int i2) {
        return Tl.b().d(context, i2);
    }

    public static Intent w(AbstractActivityC0051b2 abstractActivityC0051b2) {
        Intent parentActivityIntent = abstractActivityC0051b2.getParentActivityIntent();
        if (parentActivityIntent != null) {
            return parentActivityIntent;
        }
        try {
            String strY = y(abstractActivityC0051b2, abstractActivityC0051b2.getComponentName());
            if (strY == null) {
                return null;
            }
            ComponentName componentName = new ComponentName(abstractActivityC0051b2, strY);
            try {
                return y(abstractActivityC0051b2, componentName) == null ? Intent.makeMainActivity(componentName) : new Intent().setComponent(componentName);
            } catch (PackageManager.NameNotFoundException unused) {
                Log.e("NavUtils", "getParentActivityIntent: bad parentActivityName '" + strY + "' in manifest");
                return null;
            }
        } catch (PackageManager.NameNotFoundException e2) {
            throw new IllegalArgumentException(e2);
        }
    }

    public static Intent x(AbstractActivityC0051b2 abstractActivityC0051b2, ComponentName componentName) throws PackageManager.NameNotFoundException {
        String strY = y(abstractActivityC0051b2, componentName);
        if (strY == null) {
            return null;
        }
        ComponentName componentName2 = new ComponentName(componentName.getPackageName(), strY);
        return y(abstractActivityC0051b2, componentName2) == null ? Intent.makeMainActivity(componentName2) : new Intent().setComponent(componentName2);
    }

    public static String y(Activity activity, ComponentName componentName) throws PackageManager.NameNotFoundException {
        String string;
        ActivityInfo activityInfo = activity.getPackageManager().getActivityInfo(componentName, Build.VERSION.SDK_INT >= 29 ? 269222528 : 787072);
        String str = activityInfo.parentActivityName;
        if (str != null) {
            return str;
        }
        Bundle bundle = activityInfo.metaData;
        if (bundle == null || (string = bundle.getString("android.support.PARENT_ACTIVITY")) == null) {
            return null;
        }
        if (string.charAt(0) != '.') {
            return string;
        }
        return activity.getPackageName() + string;
    }

    public boolean D() {
        return false;
    }

    public abstract void J();

    public abstract boolean L(int i2, KeyEvent keyEvent);

    public boolean M(KeyEvent keyEvent) {
        return false;
    }

    public boolean O() {
        return false;
    }

    public abstract void S(boolean z);

    public abstract void T(boolean z);

    public abstract void U();

    public abstract void V(boolean z);

    public abstract void W(CharSequence charSequence);

    public F0 X(R2 r2) {
        return null;
    }

    public boolean j() {
        return false;
    }

    public abstract boolean k();

    public abstract void o(boolean z);

    public abstract int t();

    public abstract Context z();

    public void K() {
    }
}
