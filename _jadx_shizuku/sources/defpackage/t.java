package defpackage;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Build;
import android.util.Log;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Executor;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;

/* JADX INFO: loaded from: assets/rish_shizuku.dex */
public abstract class t {
    public static final int[] a = new int[0];
    public static final Object[] b = new Object[0];
    public static final x c = new x(0);
    public static final byte[] d = {112, 114, 111, 0};
    public static final byte[] e = {112, 114, 109, 0};
    public static final byte[] f = {48, 49, 53, 0};
    public static final byte[] g = {48, 49, 48, 0};
    public static final byte[] h = {48, 48, 57, 0};
    public static final byte[] i = {48, 48, 53, 0};
    public static final byte[] j = {48, 48, 49, 0};
    public static final byte[] k = {48, 48, 49, 0};
    public static final byte[] l = {48, 48, 50, 0};
    public static final M m = new M();
    public static long n;
    public static Method o;

    /* JADX WARN: Code duplicated, block: B:101:0x0180 A[Catch: all -> 0x017d, TRY_ENTER, TryCatch #12 {all -> 0x017d, blocks: (B:88:0x015c, B:90:0x0168, B:101:0x0180, B:102:0x0185), top: B:230:0x015c }] */
    /* JADX WARN: Code duplicated, block: B:108:0x018f A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:109:0x0191 A[Catch: IllegalStateException -> 0x0177, IOException -> 0x0179, FileNotFoundException -> 0x017b, TRY_LEAVE, TryCatch #27 {FileNotFoundException -> 0x017b, IOException -> 0x0179, IllegalStateException -> 0x0177, blocks: (B:86:0x0154, B:91:0x0172, B:109:0x0191, B:107:0x018e, B:106:0x018b), top: B:252:0x0154 }] */
    /* JADX WARN: Code duplicated, block: B:116:0x01a7  */
    /* JADX WARN: Code duplicated, block: B:127:0x01ca A[Catch: all -> 0x01d8, TRY_LEAVE, TryCatch #29 {all -> 0x01d8, blocks: (B:125:0x01be, B:127:0x01ca, B:136:0x01db), top: B:247:0x01be }] */
    /* JADX WARN: Code duplicated, block: B:136:0x01db A[Catch: all -> 0x01d8, TRY_ENTER, TRY_LEAVE, TryCatch #29 {all -> 0x01d8, blocks: (B:125:0x01be, B:127:0x01ca, B:136:0x01db), top: B:247:0x01be }] */
    /* JADX WARN: Code duplicated, block: B:147:0x01f8  */
    /* JADX WARN: Code duplicated, block: B:151:0x0202  */
    /* JADX WARN: Code duplicated, block: B:152:0x0206  */
    /* JADX WARN: Code duplicated, block: B:160:0x0220 A[Catch: all -> 0x0242, TRY_LEAVE, TryCatch #16 {all -> 0x0242, blocks: (B:157:0x0218, B:158:0x021a, B:160:0x0220), top: B:229:0x0218 }] */
    /* JADX WARN: Code duplicated, block: B:201:0x026f  */
    /* JADX WARN: Code duplicated, block: B:206:0x0279  */
    /* JADX WARN: Code duplicated, block: B:211:0x0283  */
    /* JADX WARN: Code duplicated, block: B:230:0x015c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:248:0x020a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:251:0x01b9 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:252:0x0154 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:255:0x0225 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:90:0x0168 A[Catch: all -> 0x017d, TRY_LEAVE, TryCatch #12 {all -> 0x017d, blocks: (B:88:0x015c, B:90:0x0168, B:101:0x0180, B:102:0x0185), top: B:230:0x015c }] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v6, types: [java.io.ByteArrayOutputStream, java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r7v12 */
    /* JADX WARN: Type inference failed for: r7v13 */
    /* JADX WARN: Type inference failed for: r7v14 */
    /* JADX WARN: Type inference failed for: r7v15 */
    /* JADX WARN: Type inference failed for: r7v16 */
    /* JADX WARN: Type inference failed for: r7v20 */
    /* JADX WARN: Type inference failed for: r7v21 */
    /* JADX WARN: Type inference failed for: r7v22 */
    /* JADX WARN: Type inference failed for: r7v23 */
    /* JADX WARN: Type inference failed for: r7v4, types: [byte[]] */
    /* JADX WARN: Type inference failed for: r7v5 */
    /* JADX WARN: Type inference failed for: r7v6 */
    /* JADX WARN: Type inference failed for: r7v7 */
    /* JADX WARN: Type inference failed for: r7v8 */
    /* JADX WARN: Type inference failed for: r7v9 */
    /* JADX WARN: Type inference failed for: r9v10, types: [boolean] */
    /* JADX WARN: Type inference failed for: r9v11 */
    /* JADX WARN: Type inference failed for: r9v9 */
    public static void A(Context context, Executor executor, y yVar, boolean z) {
        FileInputStream fileInputStreamA;
        C0016q[] c0016qArrT;
        y yVar2;
        C0016q[] c0016qArr;
        byte[] bArr;
        ?? r7;
        boolean z2;
        ByteArrayInputStream byteArrayInputStream;
        FileOutputStream fileOutputStream;
        Throwable th;
        byte[] bArr2;
        int i2;
        ?? r8;
        boolean z3;
        byte[] bArr3;
        ?? byteArrayOutputStream;
        int i3;
        C0015p c0015p;
        FileInputStream fileInputStreamA2;
        ?? r9;
        ?? r10;
        boolean z4;
        Context applicationContext = context.getApplicationContext();
        String packageName = applicationContext.getPackageName();
        ApplicationInfo applicationInfo = applicationContext.getApplicationInfo();
        AssetManager assets = applicationContext.getAssets();
        String name = new File(applicationInfo.sourceDir).getName();
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
            File filesDir = context.getFilesDir();
            if (!z) {
                File file = new File(filesDir, "profileinstaller_profileWrittenFor_lastUpdateTime.dat");
                if (file.exists()) {
                    try {
                        DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file));
                        try {
                            long j2 = dataInputStream.readLong();
                            dataInputStream.close();
                            z4 = j2 == packageInfo.lastUpdateTime;
                            if (z4) {
                                yVar.b(2, null);
                            }
                        } catch (Throwable th2) {
                            try {
                                dataInputStream.close();
                                throw th2;
                            } catch (Throwable th3) {
                                th2.addSuppressed(th3);
                                throw th2;
                            }
                        }
                    } catch (IOException unused) {
                        z4 = false;
                    }
                } else {
                    z4 = false;
                }
                if (z4) {
                    Log.d("ProfileInstaller", "Skipping profile installation for " + context.getPackageName());
                    G.c(context, false);
                    return;
                }
            }
            Log.d("ProfileInstaller", "Installing profile for " + context.getPackageName());
            int i4 = Build.VERSION.SDK_INT;
            File file2 = new File(new File("/data/misc/profiles/cur/0", packageName), "primary.prof");
            C0015p c0015p2 = new C0015p(assets, executor, yVar, name, file2);
            byte[] bArr4 = c0015p2.c;
            if (bArr4 != null) {
                if (file2.canWrite()) {
                    c0015p2.f = true;
                    ?? r11 = d;
                    try {
                        fileInputStreamA = c0015p2.a(assets, "dexopt/baseline.prof");
                    } catch (FileNotFoundException e2) {
                        yVar.b(6, e2);
                        fileInputStreamA = null;
                    } catch (IOException e3) {
                        yVar.b(7, e3);
                        fileInputStreamA = null;
                    }
                    try {
                        try {
                            if (fileInputStreamA != null) {
                                try {
                                    if (!Arrays.equals((byte[]) r11, n(fileInputStreamA, 4))) {
                                        throw new IllegalStateException("Invalid magic");
                                    }
                                    c0016qArrT = t(fileInputStreamA, n(fileInputStreamA, 4), c0015p2.e);
                                    try {
                                        fileInputStreamA.close();
                                    } catch (IOException e4) {
                                        yVar.b(7, e4);
                                    }
                                    c0015p2.g = c0016qArrT;
                                } catch (IOException e5) {
                                    yVar.b(7, e5);
                                    fileInputStreamA.close();
                                    c0016qArrT = null;
                                } catch (IllegalStateException e6) {
                                    yVar.b(8, e6);
                                    fileInputStreamA.close();
                                    c0016qArrT = null;
                                }
                            }
                        } catch (Throwable th4) {
                            try {
                                fileInputStreamA.close();
                                throw th4;
                            } catch (IOException e7) {
                                yVar.b(7, e7);
                                throw th4;
                            }
                        }
                    } catch (IOException e8) {
                        yVar.b(7, e8);
                    }
                    C0016q[] c0016qArr2 = c0015p2.g;
                    if (c0016qArr2 != null && (i3 = Build.VERSION.SDK_INT) <= 33) {
                        if (i3 != 24 && i3 != 25) {
                            switch (i3) {
                                case 31:
                                case 32:
                                case 33:
                                    fileInputStreamA2 = c0015p2.a(assets, "dexopt/baseline.profm");
                                    if (fileInputStreamA2 == null) {
                                        if (fileInputStreamA2 != null) {
                                            fileInputStreamA2.close();
                                        }
                                        c0015p = null;
                                    } else {
                                        if (Arrays.equals(e, n(fileInputStreamA2, 4))) {
                                            throw new IllegalStateException("Invalid magic");
                                        }
                                        c0015p2.g = q(fileInputStreamA2, n(fileInputStreamA2, 4), bArr4, c0016qArr2);
                                        fileInputStreamA2.close();
                                        c0015p = c0015p2;
                                    }
                                    if (c0015p != null) {
                                        c0015p2 = c0015p;
                                        break;
                                    }
                                default:
                                    yVar2 = c0015p2.b;
                                    c0016qArr = c0015p2.g;
                                    if (c0016qArr != null && (bArr3 = c0015p2.c) != null) {
                                        if (c0015p2.f) {
                                            throw new IllegalStateException("This device doesn't support aot. Did you call deviceSupportsAotProfile()?");
                                        }
                                        try {
                                            byteArrayOutputStream = new ByteArrayOutputStream();
                                            try {
                                                byteArrayOutputStream.write(r11);
                                                byteArrayOutputStream.write(bArr3);
                                                if (w(byteArrayOutputStream, bArr3, c0016qArr)) {
                                                    c0015p2.h = byteArrayOutputStream.toByteArray();
                                                    byteArrayOutputStream.close();
                                                    c0015p2.g = null;
                                                } else {
                                                    yVar2.b(5, null);
                                                    c0015p2.g = null;
                                                    byteArrayOutputStream.close();
                                                }
                                            } catch (Throwable th5) {
                                                try {
                                                    byteArrayOutputStream.close();
                                                    throw th5;
                                                } catch (Throwable th6) {
                                                    th5.addSuppressed(th6);
                                                    throw th5;
                                                }
                                            }
                                        } catch (IOException e9) {
                                            yVar2.b(7, e9);
                                        } catch (IllegalStateException e10) {
                                            yVar2.b(8, e10);
                                        }
                                    }
                                    bArr = c0015p2.h;
                                    if (bArr != null) {
                                        z2 = false;
                                        r8 = 1;
                                    } else {
                                        try {
                                            if (c0015p2.f) {
                                                throw new IllegalStateException("This device doesn't support aot. Did you call deviceSupportsAotProfile()?");
                                            }
                                            try {
                                                try {
                                                    byteArrayInputStream = new ByteArrayInputStream(bArr);
                                                    try {
                                                        fileOutputStream = new FileOutputStream(c0015p2.d);
                                                        try {
                                                            try {
                                                                bArr2 = new byte[512];
                                                                while (true) {
                                                                    i2 = byteArrayInputStream.read(bArr2);
                                                                    if (i2 > 0) {
                                                                        fileOutputStream.write(bArr2, 0, i2);
                                                                    } else {
                                                                        r8 = 1;
                                                                        try {
                                                                            c0015p2.b(1, null);
                                                                            fileOutputStream.close();
                                                                            byteArrayInputStream.close();
                                                                            c0015p2.h = null;
                                                                            c0015p2.g = null;
                                                                            z2 = true;
                                                                        } catch (Throwable th7) {
                                                                            th = th7;
                                                                        }
                                                                    }
                                                                    th = th;
                                                                    try {
                                                                        fileOutputStream.close();
                                                                        throw th;
                                                                    } catch (Throwable th8) {
                                                                        th.addSuppressed(th8);
                                                                        throw th;
                                                                    }
                                                                }
                                                            } catch (Throwable th9) {
                                                                th = th9;
                                                                Throwable th10 = th;
                                                                try {
                                                                    byteArrayInputStream.close();
                                                                    throw th10;
                                                                } catch (Throwable th11) {
                                                                    th10.addSuppressed(th11);
                                                                    throw th10;
                                                                }
                                                            }
                                                        } catch (Throwable th12) {
                                                            th = th12;
                                                        }
                                                    } catch (Throwable th13) {
                                                        th = th13;
                                                    }
                                                } catch (FileNotFoundException e11) {
                                                    e = e11;
                                                    c0015p2.b(6, e);
                                                    r7 = r11;
                                                    c0015p2.h = null;
                                                    c0015p2.g = null;
                                                    z2 = false;
                                                    r8 = r7;
                                                } catch (IOException e12) {
                                                    e = e12;
                                                    c0015p2.b(7, e);
                                                    r7 = r11;
                                                    c0015p2.h = null;
                                                    c0015p2.g = null;
                                                    z2 = false;
                                                    r8 = r7;
                                                }
                                            } catch (FileNotFoundException e13) {
                                                e = e13;
                                                r11 = 1;
                                                c0015p2.b(6, e);
                                                r7 = r11;
                                                c0015p2.h = null;
                                                c0015p2.g = null;
                                                z2 = false;
                                                r8 = r7;
                                            } catch (IOException e14) {
                                                e = e14;
                                                r11 = 1;
                                                c0015p2.b(7, e);
                                                r7 = r11;
                                                c0015p2.h = null;
                                                c0015p2.g = null;
                                                z2 = false;
                                                r8 = r7;
                                            }
                                        } catch (Throwable th14) {
                                            c0015p2.h = null;
                                            c0015p2.g = null;
                                            throw th14;
                                        }
                                    }
                                    if (z2) {
                                        k(packageInfo, filesDir);
                                    }
                                    z3 = z2;
                                    r9 = r8;
                                    break;
                            }
                        } else {
                            try {
                                fileInputStreamA2 = c0015p2.a(assets, "dexopt/baseline.profm");
                                if (fileInputStreamA2 == null) {
                                    try {
                                        if (Arrays.equals(e, n(fileInputStreamA2, 4))) {
                                            throw new IllegalStateException("Invalid magic");
                                        }
                                        c0015p2.g = q(fileInputStreamA2, n(fileInputStreamA2, 4), bArr4, c0016qArr2);
                                        fileInputStreamA2.close();
                                        c0015p = c0015p2;
                                    } catch (Throwable th15) {
                                        try {
                                            fileInputStreamA2.close();
                                            throw th15;
                                        } catch (Throwable th16) {
                                            th15.addSuppressed(th16);
                                            throw th15;
                                        }
                                    }
                                } else {
                                    if (fileInputStreamA2 != null) {
                                        fileInputStreamA2.close();
                                    }
                                    c0015p = null;
                                }
                            } catch (FileNotFoundException e15) {
                                yVar.b(9, e15);
                            } catch (IOException e16) {
                                yVar.b(7, e16);
                            } catch (IllegalStateException e17) {
                                c0015p2.g = null;
                                yVar.b(8, e17);
                            }
                            if (c0015p != null) {
                                c0015p2 = c0015p;
                            }
                        }
                    }
                    yVar2 = c0015p2.b;
                    c0016qArr = c0015p2.g;
                    if (c0016qArr != null) {
                        if (c0015p2.f) {
                            throw new IllegalStateException("This device doesn't support aot. Did you call deviceSupportsAotProfile()?");
                        }
                        byteArrayOutputStream = new ByteArrayOutputStream();
                        byteArrayOutputStream.write(r11);
                        byteArrayOutputStream.write(bArr3);
                        if (w(byteArrayOutputStream, bArr3, c0016qArr)) {
                            yVar2.b(5, null);
                            c0015p2.g = null;
                            byteArrayOutputStream.close();
                        } else {
                            c0015p2.h = byteArrayOutputStream.toByteArray();
                            byteArrayOutputStream.close();
                            c0015p2.g = null;
                        }
                    }
                    bArr = c0015p2.h;
                    if (bArr != null) {
                        if (c0015p2.f) {
                            throw new IllegalStateException("This device doesn't support aot. Did you call deviceSupportsAotProfile()?");
                        }
                        byteArrayInputStream = new ByteArrayInputStream(bArr);
                        fileOutputStream = new FileOutputStream(c0015p2.d);
                        bArr2 = new byte[512];
                        while (true) {
                            i2 = byteArrayInputStream.read(bArr2);
                            if (i2 > 0) {
                                fileOutputStream.write(bArr2, 0, i2);
                            } else {
                                r8 = 1;
                                c0015p2.b(1, null);
                                fileOutputStream.close();
                                byteArrayInputStream.close();
                                c0015p2.h = null;
                                c0015p2.g = null;
                                z2 = true;
                            }
                            th = th;
                            fileOutputStream.close();
                            throw th;
                        }
                    }
                    z2 = false;
                    r8 = 1;
                    if (z2) {
                        k(packageInfo, filesDir);
                    }
                    z3 = z2;
                    r9 = r8;
                } else {
                    c0015p2.b(4, null);
                }
                if (z3 || !z) {
                    r10 = 0;
                } else {
                    r10 = r9;
                }
                G.c(context, r10);
            }
            c0015p2.b(3, Integer.valueOf(i4));
            z3 = false;
            r9 = 1;
            if (z3) {
                r10 = 0;
            } else {
                r10 = 0;
            }
            G.c(context, r10);
        } catch (PackageManager.NameNotFoundException e18) {
            yVar.b(7, e18);
            G.c(context, false);
        }
    }

    public static void B(ByteArrayOutputStream byteArrayOutputStream, long j2, int i2) throws IOException {
        byte[] bArr = new byte[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            bArr[i3] = (byte) ((j2 >> (i3 * 8)) & 255);
        }
        byteArrayOutputStream.write(bArr);
    }

    public static void C(ByteArrayOutputStream byteArrayOutputStream, int i2) throws IOException {
        B(byteArrayOutputStream, i2, 2);
    }

    public static boolean a(Object obj, Object obj2) {
        if (obj == null) {
            return obj2 == null;
        }
        return obj.equals(obj2);
    }

    public static final int b(int[] iArr, int i2, int i3) {
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

    public static void f(Serializable serializable, String str) {
        if (serializable != null) {
            return;
        }
        NullPointerException nullPointerException = new NullPointerException(str.concat(" must not be null"));
        String name = t.class.getName();
        StackTraceElement[] stackTrace = nullPointerException.getStackTrace();
        int length = stackTrace.length;
        int i2 = -1;
        for (int i3 = 0; i3 < length; i3++) {
            if (name.equals(stackTrace[i3].getClassName())) {
                i2 = i3;
            }
        }
        nullPointerException.setStackTrace((StackTraceElement[]) Arrays.copyOfRange(stackTrace, i2 + 1, length));
        throw nullPointerException;
    }

    public static byte[] g(byte[] bArr) {
        Deflater deflater = new Deflater(1);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(byteArrayOutputStream, deflater);
            try {
                deflaterOutputStream.write(bArr);
                deflaterOutputStream.close();
                deflater.end();
                return byteArrayOutputStream.toByteArray();
            } catch (Throwable th) {
                try {
                    deflaterOutputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (Throwable th3) {
            deflater.end();
            throw th3;
        }
    }

    public static byte[] h(C0016q[] c0016qArr, byte[] bArr) throws IOException {
        int length = 0;
        for (C0016q c0016q : c0016qArr) {
            length += ((((c0016q.g * 2) + 7) & (-8)) / 8) + (c0016q.e * 2) + j(c0016q.a, c0016q.b, bArr).getBytes(StandardCharsets.UTF_8).length + 16 + c0016q.f;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(length);
        if (Arrays.equals(bArr, h)) {
            for (C0016q c0016q2 : c0016qArr) {
                x(byteArrayOutputStream, c0016q2, j(c0016q2.a, c0016q2.b, bArr));
                z(byteArrayOutputStream, c0016q2);
                int[] iArr = c0016q2.h;
                int length2 = iArr.length;
                int i2 = 0;
                int i3 = 0;
                while (i2 < length2) {
                    int i4 = iArr[i2];
                    C(byteArrayOutputStream, i4 - i3);
                    i2++;
                    i3 = i4;
                }
                y(byteArrayOutputStream, c0016q2);
            }
        } else {
            for (C0016q c0016q3 : c0016qArr) {
                x(byteArrayOutputStream, c0016q3, j(c0016q3.a, c0016q3.b, bArr));
            }
            for (C0016q c0016q4 : c0016qArr) {
                z(byteArrayOutputStream, c0016q4);
                int[] iArr2 = c0016q4.h;
                int length3 = iArr2.length;
                int i5 = 0;
                int i6 = 0;
                while (i5 < length3) {
                    int i7 = iArr2[i5];
                    C(byteArrayOutputStream, i7 - i6);
                    i5++;
                    i6 = i7;
                }
                y(byteArrayOutputStream, c0016q4);
            }
        }
        if (byteArrayOutputStream.size() == length) {
            return byteArrayOutputStream.toByteArray();
        }
        throw new IllegalStateException("The bytes saved do not match expectation. actual=" + byteArrayOutputStream.size() + " expected=" + length);
    }

    public static boolean i(File file) {
        if (!file.isDirectory()) {
            file.delete();
            return true;
        }
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles == null) {
            return false;
        }
        boolean z = true;
        for (File file2 : fileArrListFiles) {
            z = i(file2) && z;
        }
        return z;
    }

    public static String j(String str, String str2, byte[] bArr) {
        byte[] bArr2 = j;
        boolean zEquals = Arrays.equals(bArr, bArr2);
        byte[] bArr3 = i;
        Object obj = (zEquals || Arrays.equals(bArr, bArr3)) ? ":" : "!";
        if (str.length() <= 0) {
            if ("!".equals(obj)) {
                return str2.replace(":", "!");
            }
            if (":".equals(obj)) {
                return str2.replace("!", ":");
            }
        } else {
            if (str2.equals("classes.dex")) {
                return str;
            }
            if (str2.contains("!") || str2.contains(":")) {
                if ("!".equals(obj)) {
                    return str2.replace(":", "!");
                }
                if (":".equals(obj)) {
                    return str2.replace("!", ":");
                }
            } else if (!str2.endsWith(".apk")) {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append((Arrays.equals(bArr, bArr2) || Arrays.equals(bArr, bArr3)) ? ":" : "!");
                sb.append(str2);
                return sb.toString();
            }
        }
        return str2;
    }

    public static void k(PackageInfo packageInfo, File file) {
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(new File(file, "profileinstaller_profileWrittenFor_lastUpdateTime.dat")));
            try {
                dataOutputStream.writeLong(packageInfo.lastUpdateTime);
                dataOutputStream.close();
            } catch (Throwable th) {
                try {
                    dataOutputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (IOException unused) {
        }
    }

    public static byte[] n(InputStream inputStream, int i2) throws IOException {
        byte[] bArr = new byte[i2];
        int i3 = 0;
        while (i3 < i2) {
            int i4 = inputStream.read(bArr, i3, i2 - i3);
            if (i4 < 0) {
                throw new IllegalStateException("Not enough bytes to read: " + i2);
            }
            i3 += i4;
        }
        return bArr;
    }

    public static int[] o(ByteArrayInputStream byteArrayInputStream, int i2) {
        int[] iArr = new int[i2];
        int iU = 0;
        for (int i3 = 0; i3 < i2; i3++) {
            iU += (int) u(byteArrayInputStream, 2);
            iArr[i3] = iU;
        }
        return iArr;
    }

    public static byte[] p(FileInputStream fileInputStream, int i2, int i3) {
        Inflater inflater = new Inflater();
        try {
            byte[] bArr = new byte[i3];
            byte[] bArr2 = new byte[2048];
            int i4 = 0;
            int iInflate = 0;
            while (!inflater.finished() && !inflater.needsDictionary() && i4 < i2) {
                int i5 = fileInputStream.read(bArr2);
                if (i5 < 0) {
                    throw new IllegalStateException("Invalid zip data. Stream ended after $totalBytesRead bytes. Expected " + i2 + " bytes");
                }
                inflater.setInput(bArr2, 0, i5);
                try {
                    iInflate += inflater.inflate(bArr, iInflate, i3 - iInflate);
                    i4 += i5;
                } catch (DataFormatException e2) {
                    throw new IllegalStateException(e2.getMessage());
                }
            }
            if (i4 == i2) {
                if (!inflater.finished()) {
                    throw new IllegalStateException("Inflater did not finish");
                }
                inflater.end();
                return bArr;
            }
            throw new IllegalStateException("Didn't read enough bytes during decompression. expected=" + i2 + " actual=" + i4);
        } catch (Throwable th) {
            inflater.end();
            throw th;
        }
    }

    public static C0016q[] q(FileInputStream fileInputStream, byte[] bArr, byte[] bArr2, C0016q[] c0016qArr) throws IOException {
        byte[] bArr3 = k;
        if (!Arrays.equals(bArr, bArr3)) {
            if (!Arrays.equals(bArr, l)) {
                throw new IllegalStateException("Unsupported meta version");
            }
            int iU = (int) u(fileInputStream, 2);
            byte[] bArrP = p(fileInputStream, (int) u(fileInputStream, 4), (int) u(fileInputStream, 4));
            if (fileInputStream.read() > 0) {
                throw new IllegalStateException("Content found after the end of file");
            }
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArrP);
            try {
                C0016q[] c0016qArrS = s(byteArrayInputStream, bArr2, iU, c0016qArr);
                byteArrayInputStream.close();
                return c0016qArrS;
            } catch (Throwable th) {
                try {
                    byteArrayInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        if (Arrays.equals(f, bArr2)) {
            throw new IllegalStateException("Requires new Baseline Profile Metadata. Please rebuild the APK with Android Gradle Plugin 7.2 Canary 7 or higher");
        }
        if (!Arrays.equals(bArr, bArr3)) {
            throw new IllegalStateException("Unsupported meta version");
        }
        int iU2 = (int) u(fileInputStream, 1);
        byte[] bArrP2 = p(fileInputStream, (int) u(fileInputStream, 4), (int) u(fileInputStream, 4));
        if (fileInputStream.read() > 0) {
            throw new IllegalStateException("Content found after the end of file");
        }
        ByteArrayInputStream byteArrayInputStream2 = new ByteArrayInputStream(bArrP2);
        try {
            C0016q[] c0016qArrR = r(byteArrayInputStream2, iU2, c0016qArr);
            byteArrayInputStream2.close();
            return c0016qArrR;
        } catch (Throwable th3) {
            try {
                byteArrayInputStream2.close();
            } catch (Throwable th4) {
                th3.addSuppressed(th4);
            }
            throw th3;
        }
    }

    public static C0016q[] r(ByteArrayInputStream byteArrayInputStream, int i2, C0016q[] c0016qArr) {
        if (byteArrayInputStream.available() == 0) {
            return new C0016q[0];
        }
        if (i2 != c0016qArr.length) {
            throw new IllegalStateException("Mismatched number of dex files found in metadata");
        }
        String[] strArr = new String[i2];
        int[] iArr = new int[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            int iU = (int) u(byteArrayInputStream, 2);
            iArr[i3] = (int) u(byteArrayInputStream, 2);
            strArr[i3] = new String(n(byteArrayInputStream, iU), StandardCharsets.UTF_8);
        }
        for (int i4 = 0; i4 < i2; i4++) {
            C0016q c0016q = c0016qArr[i4];
            if (!c0016q.b.equals(strArr[i4])) {
                throw new IllegalStateException("Order of dexfiles in metadata did not match baseline");
            }
            int i5 = iArr[i4];
            c0016q.e = i5;
            c0016q.h = o(byteArrayInputStream, i5);
        }
        return c0016qArr;
    }

    public static C0016q[] s(ByteArrayInputStream byteArrayInputStream, byte[] bArr, int i2, C0016q[] c0016qArr) throws IOException {
        if (byteArrayInputStream.available() == 0) {
            return new C0016q[0];
        }
        if (i2 != c0016qArr.length) {
            throw new IllegalStateException("Mismatched number of dex files found in metadata");
        }
        for (int i3 = 0; i3 < i2; i3++) {
            u(byteArrayInputStream, 2);
            String str = new String(n(byteArrayInputStream, (int) u(byteArrayInputStream, 2)), StandardCharsets.UTF_8);
            long jU = u(byteArrayInputStream, 4);
            int iU = (int) u(byteArrayInputStream, 2);
            C0016q c0016q = null;
            if (c0016qArr.length > 0) {
                int iIndexOf = str.indexOf("!");
                if (iIndexOf < 0) {
                    iIndexOf = str.indexOf(":");
                }
                String strSubstring = iIndexOf > 0 ? str.substring(iIndexOf + 1) : str;
                for (int i4 = 0; i4 < c0016qArr.length; i4++) {
                    if (c0016qArr[i4].b.equals(strSubstring)) {
                        c0016q = c0016qArr[i4];
                        break;
                    }
                }
            }
            if (c0016q == null) {
                throw new IllegalStateException("Missing profile key: ".concat(str));
            }
            c0016q.d = jU;
            int[] iArrO = o(byteArrayInputStream, iU);
            if (Arrays.equals(bArr, j)) {
                c0016q.e = iU;
                c0016q.h = iArrO;
            }
        }
        return c0016qArr;
    }

    public static C0016q[] t(FileInputStream fileInputStream, byte[] bArr, String str) throws IOException {
        if (!Arrays.equals(bArr, g)) {
            throw new IllegalStateException("Unsupported version");
        }
        int iU = (int) u(fileInputStream, 1);
        byte[] bArrP = p(fileInputStream, (int) u(fileInputStream, 4), (int) u(fileInputStream, 4));
        if (fileInputStream.read() > 0) {
            throw new IllegalStateException("Content found after the end of file");
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArrP);
        try {
            C0016q[] c0016qArrV = v(byteArrayInputStream, str, iU);
            byteArrayInputStream.close();
            return c0016qArrV;
        } catch (Throwable th) {
            try {
                byteArrayInputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static long u(InputStream inputStream, int i2) throws IOException {
        byte[] bArrN = n(inputStream, i2);
        long j2 = 0;
        for (int i3 = 0; i3 < i2; i3++) {
            j2 += ((long) (bArrN[i3] & 255)) << (i3 * 8);
        }
        return j2;
    }

    public static C0016q[] v(ByteArrayInputStream byteArrayInputStream, String str, int i2) throws IOException {
        TreeMap treeMap;
        if (byteArrayInputStream.available() == 0) {
            return new C0016q[0];
        }
        C0016q[] c0016qArr = new C0016q[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            int iU = (int) u(byteArrayInputStream, 2);
            int iU2 = (int) u(byteArrayInputStream, 2);
            c0016qArr[i3] = new C0016q(str, new String(n(byteArrayInputStream, iU), StandardCharsets.UTF_8), u(byteArrayInputStream, 4), iU2, (int) u(byteArrayInputStream, 4), (int) u(byteArrayInputStream, 4), new int[iU2], new TreeMap());
        }
        for (int i4 = 0; i4 < i2; i4++) {
            C0016q c0016q = c0016qArr[i4];
            int iAvailable = byteArrayInputStream.available() - c0016q.f;
            int iU3 = 0;
            while (true) {
                int iAvailable2 = byteArrayInputStream.available();
                treeMap = c0016q.i;
                if (iAvailable2 <= iAvailable) {
                    break;
                }
                iU3 += (int) u(byteArrayInputStream, 2);
                treeMap.put(Integer.valueOf(iU3), 1);
                for (int iU4 = (int) u(byteArrayInputStream, 2); iU4 > 0; iU4--) {
                    u(byteArrayInputStream, 2);
                    int iU5 = (int) u(byteArrayInputStream, 1);
                    if (iU5 != 6 && iU5 != 7) {
                        while (iU5 > 0) {
                            u(byteArrayInputStream, 1);
                            for (int iU6 = (int) u(byteArrayInputStream, 1); iU6 > 0; iU6--) {
                                u(byteArrayInputStream, 2);
                            }
                            iU5--;
                        }
                    }
                }
            }
            if (byteArrayInputStream.available() != iAvailable) {
                throw new IllegalStateException("Read too much data during profile line parse");
            }
            c0016q.h = o(byteArrayInputStream, c0016q.e);
            int i5 = c0016q.g;
            BitSet bitSetValueOf = BitSet.valueOf(n(byteArrayInputStream, (((i5 * 2) + 7) & (-8)) / 8));
            for (int i6 = 0; i6 < i5; i6++) {
                int i7 = bitSetValueOf.get(i6) ? 2 : 0;
                if (bitSetValueOf.get(i6 + i5)) {
                    i7 |= 4;
                }
                if (i7 != 0) {
                    Integer num = (Integer) treeMap.get(Integer.valueOf(i6));
                    if (num == null) {
                        num = 0;
                    }
                    treeMap.put(Integer.valueOf(i6), Integer.valueOf(i7 | num.intValue()));
                }
            }
        }
        return c0016qArr;
    }

    public static boolean w(ByteArrayOutputStream byteArrayOutputStream, byte[] bArr, C0016q[] c0016qArr) throws IOException {
        long j2;
        ArrayList arrayList;
        int length;
        byte[] bArr2 = f;
        int i2 = 0;
        if (!Arrays.equals(bArr, bArr2)) {
            byte[] bArr3 = g;
            if (Arrays.equals(bArr, bArr3)) {
                byte[] bArrH = h(c0016qArr, bArr3);
                B(byteArrayOutputStream, c0016qArr.length, 1);
                B(byteArrayOutputStream, bArrH.length, 4);
                byte[] bArrG = g(bArrH);
                B(byteArrayOutputStream, bArrG.length, 4);
                byteArrayOutputStream.write(bArrG);
                return true;
            }
            byte[] bArr4 = i;
            if (Arrays.equals(bArr, bArr4)) {
                B(byteArrayOutputStream, c0016qArr.length, 1);
                for (C0016q c0016q : c0016qArr) {
                    int size = c0016q.i.size() * 4;
                    String strJ = j(c0016q.a, c0016q.b, bArr4);
                    Charset charset = StandardCharsets.UTF_8;
                    C(byteArrayOutputStream, strJ.getBytes(charset).length);
                    C(byteArrayOutputStream, c0016q.h.length);
                    B(byteArrayOutputStream, size, 4);
                    B(byteArrayOutputStream, c0016q.c, 4);
                    byteArrayOutputStream.write(strJ.getBytes(charset));
                    Iterator it = c0016q.i.keySet().iterator();
                    while (it.hasNext()) {
                        C(byteArrayOutputStream, ((Integer) it.next()).intValue());
                        C(byteArrayOutputStream, 0);
                    }
                    for (int i3 : c0016q.h) {
                        C(byteArrayOutputStream, i3);
                    }
                }
                return true;
            }
            byte[] bArr5 = h;
            if (Arrays.equals(bArr, bArr5)) {
                byte[] bArrH2 = h(c0016qArr, bArr5);
                B(byteArrayOutputStream, c0016qArr.length, 1);
                B(byteArrayOutputStream, bArrH2.length, 4);
                byte[] bArrG2 = g(bArrH2);
                B(byteArrayOutputStream, bArrG2.length, 4);
                byteArrayOutputStream.write(bArrG2);
                return true;
            }
            byte[] bArr6 = j;
            if (!Arrays.equals(bArr, bArr6)) {
                return false;
            }
            C(byteArrayOutputStream, c0016qArr.length);
            for (C0016q c0016q2 : c0016qArr) {
                String strJ2 = j(c0016q2.a, c0016q2.b, bArr6);
                Charset charset2 = StandardCharsets.UTF_8;
                C(byteArrayOutputStream, strJ2.getBytes(charset2).length);
                TreeMap treeMap = c0016q2.i;
                C(byteArrayOutputStream, treeMap.size());
                C(byteArrayOutputStream, c0016q2.h.length);
                B(byteArrayOutputStream, c0016q2.c, 4);
                byteArrayOutputStream.write(strJ2.getBytes(charset2));
                Iterator it2 = treeMap.keySet().iterator();
                while (it2.hasNext()) {
                    C(byteArrayOutputStream, ((Integer) it2.next()).intValue());
                }
                for (int i4 : c0016q2.h) {
                    C(byteArrayOutputStream, i4);
                }
            }
            return true;
        }
        ArrayList arrayList2 = new ArrayList(3);
        ArrayList arrayList3 = new ArrayList(3);
        ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
        try {
            C(byteArrayOutputStream2, c0016qArr.length);
            int i5 = 2;
            int i6 = 2;
            for (C0016q c0016q3 : c0016qArr) {
                B(byteArrayOutputStream2, c0016q3.c, 4);
                B(byteArrayOutputStream2, c0016q3.d, 4);
                B(byteArrayOutputStream2, c0016q3.g, 4);
                String strJ3 = j(c0016q3.a, c0016q3.b, bArr2);
                Charset charset3 = StandardCharsets.UTF_8;
                int length2 = strJ3.getBytes(charset3).length;
                C(byteArrayOutputStream2, length2);
                i6 = i6 + 14 + length2;
                byteArrayOutputStream2.write(strJ3.getBytes(charset3));
            }
            byte[] byteArray = byteArrayOutputStream2.toByteArray();
            if (i6 != byteArray.length) {
                throw new IllegalStateException("Expected size " + i6 + ", does not match actual size " + byteArray.length);
            }
            S s = new S(1, byteArray, false);
            byteArrayOutputStream2.close();
            arrayList2.add(s);
            ByteArrayOutputStream byteArrayOutputStream3 = new ByteArrayOutputStream();
            int i7 = 0;
            int i8 = 0;
            while (i7 < c0016qArr.length) {
                try {
                    C0016q c0016q4 = c0016qArr[i7];
                    C(byteArrayOutputStream3, i7);
                    C(byteArrayOutputStream3, c0016q4.e);
                    i8 = i8 + 4 + (c0016q4.e * i5);
                    int[] iArr = c0016q4.h;
                    int length3 = iArr.length;
                    int i9 = i2;
                    int i10 = i5;
                    int i11 = i9;
                    while (i11 < length3) {
                        int i12 = iArr[i11];
                        C(byteArrayOutputStream3, i12 - i9);
                        i11++;
                        i9 = i12;
                    }
                    i7++;
                    i5 = i10;
                    i2 = 0;
                } catch (Throwable th) {
                    try {
                        byteArrayOutputStream3.close();
                        throw th;
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                        throw th;
                    }
                }
            }
            byte[] byteArray2 = byteArrayOutputStream3.toByteArray();
            if (i8 != byteArray2.length) {
                throw new IllegalStateException("Expected size " + i8 + ", does not match actual size " + byteArray2.length);
            }
            S s2 = new S(3, byteArray2, true);
            byteArrayOutputStream3.close();
            arrayList2.add(s2);
            ByteArrayOutputStream byteArrayOutputStream4 = new ByteArrayOutputStream();
            int i13 = 0;
            int i14 = 0;
            while (i13 < c0016qArr.length) {
                try {
                    C0016q c0016q5 = c0016qArr[i13];
                    Iterator it3 = c0016q5.i.entrySet().iterator();
                    int iIntValue = 0;
                    while (it3.hasNext()) {
                        iIntValue |= ((Integer) ((Map.Entry) it3.next()).getValue()).intValue();
                    }
                    ByteArrayOutputStream byteArrayOutputStream5 = new ByteArrayOutputStream();
                    try {
                        y(byteArrayOutputStream5, c0016q5);
                        byte[] byteArray3 = byteArrayOutputStream5.toByteArray();
                        byteArrayOutputStream5.close();
                        ByteArrayOutputStream byteArrayOutputStream6 = new ByteArrayOutputStream();
                        try {
                            z(byteArrayOutputStream6, c0016q5);
                            byte[] byteArray4 = byteArrayOutputStream6.toByteArray();
                            byteArrayOutputStream6.close();
                            C(byteArrayOutputStream4, i13);
                            int length4 = byteArray3.length + 2 + byteArray4.length;
                            int i15 = i14 + 6;
                            ArrayList arrayList4 = arrayList3;
                            B(byteArrayOutputStream4, length4, 4);
                            C(byteArrayOutputStream4, iIntValue);
                            byteArrayOutputStream4.write(byteArray3);
                            byteArrayOutputStream4.write(byteArray4);
                            i14 = i15 + length4;
                            i13++;
                            arrayList3 = arrayList4;
                        } catch (Throwable th3) {
                            try {
                                byteArrayOutputStream6.close();
                                throw th3;
                            } catch (Throwable th4) {
                                th3.addSuppressed(th4);
                                throw th3;
                            }
                        }
                    } catch (Throwable th5) {
                        try {
                            byteArrayOutputStream5.close();
                            throw th5;
                        } catch (Throwable th6) {
                            th5.addSuppressed(th6);
                            throw th5;
                        }
                    }
                } catch (Throwable th7) {
                    try {
                        byteArrayOutputStream4.close();
                        throw th7;
                    } catch (Throwable th8) {
                        th7.addSuppressed(th8);
                        throw th7;
                    }
                }
            }
            ArrayList arrayList5 = arrayList3;
            byte[] byteArray5 = byteArrayOutputStream4.toByteArray();
            if (i14 != byteArray5.length) {
                throw new IllegalStateException("Expected size " + i14 + ", does not match actual size " + byteArray5.length);
            }
            S s3 = new S(4, byteArray5, true);
            byteArrayOutputStream4.close();
            arrayList2.add(s3);
            long j3 = 4;
            long size2 = j3 + j3 + 4 + ((long) (arrayList2.size() * 16));
            B(byteArrayOutputStream, arrayList2.size(), 4);
            int i16 = 0;
            while (i16 < arrayList2.size()) {
                S s4 = (S) arrayList2.get(i16);
                int i17 = s4.a;
                if (i17 == 1) {
                    j2 = 0;
                } else if (i17 == 2) {
                    j2 = 1;
                } else if (i17 == 3) {
                    j2 = 2;
                } else if (i17 == 4) {
                    j2 = 3;
                } else {
                    if (i17 != 5) {
                        throw null;
                    }
                    j2 = 4;
                }
                B(byteArrayOutputStream, j2, 4);
                B(byteArrayOutputStream, size2, 4);
                byte[] bArr7 = s4.b;
                if (s4.c) {
                    long length5 = bArr7.length;
                    byte[] bArrG3 = g(bArr7);
                    arrayList = arrayList5;
                    arrayList.add(bArrG3);
                    B(byteArrayOutputStream, bArrG3.length, 4);
                    B(byteArrayOutputStream, length5, 4);
                    length = bArrG3.length;
                } else {
                    arrayList = arrayList5;
                    arrayList.add(bArr7);
                    B(byteArrayOutputStream, bArr7.length, 4);
                    B(byteArrayOutputStream, 0L, 4);
                    length = bArr7.length;
                }
                size2 += (long) length;
                i16++;
                arrayList5 = arrayList;
            }
            ArrayList arrayList6 = arrayList5;
            for (int i18 = 0; i18 < arrayList6.size(); i18++) {
                byteArrayOutputStream.write((byte[]) arrayList6.get(i18));
            }
            return true;
        } catch (Throwable th9) {
            try {
                byteArrayOutputStream2.close();
                throw th9;
            } catch (Throwable th10) {
                th9.addSuppressed(th10);
                throw th9;
            }
        }
    }

    public static void x(ByteArrayOutputStream byteArrayOutputStream, C0016q c0016q, String str) throws IOException {
        Charset charset = StandardCharsets.UTF_8;
        C(byteArrayOutputStream, str.getBytes(charset).length);
        C(byteArrayOutputStream, c0016q.e);
        B(byteArrayOutputStream, c0016q.f, 4);
        B(byteArrayOutputStream, c0016q.c, 4);
        B(byteArrayOutputStream, c0016q.g, 4);
        byteArrayOutputStream.write(str.getBytes(charset));
    }

    public static void y(ByteArrayOutputStream byteArrayOutputStream, C0016q c0016q) throws IOException {
        byte[] bArr = new byte[(((c0016q.g * 2) + 7) & (-8)) / 8];
        for (Map.Entry entry : c0016q.i.entrySet()) {
            int iIntValue = ((Integer) entry.getKey()).intValue();
            int iIntValue2 = ((Integer) entry.getValue()).intValue();
            if ((iIntValue2 & 2) != 0) {
                int i2 = iIntValue / 8;
                bArr[i2] = (byte) (bArr[i2] | (1 << (iIntValue % 8)));
            }
            if ((iIntValue2 & 4) != 0) {
                int i3 = iIntValue + c0016q.g;
                int i4 = i3 / 8;
                bArr[i4] = (byte) ((1 << (i3 % 8)) | bArr[i4]);
            }
        }
        byteArrayOutputStream.write(bArr);
    }

    public static void z(ByteArrayOutputStream byteArrayOutputStream, C0016q c0016q) throws IOException {
        int i2 = 0;
        for (Map.Entry entry : c0016q.i.entrySet()) {
            int iIntValue = ((Integer) entry.getKey()).intValue();
            if ((((Integer) entry.getValue()).intValue() & 1) != 0) {
                C(byteArrayOutputStream, iIntValue - i2);
                C(byteArrayOutputStream, 0);
                i2 = iIntValue;
            }
        }
    }

    public abstract boolean c(AbstractFutureC0006g abstractFutureC0006g, C0002c c0002c);

    public abstract boolean d(AbstractFutureC0006g abstractFutureC0006g, Object obj, Object obj2);

    public abstract boolean e(AbstractFutureC0006g abstractFutureC0006g, C0005f c0005f, C0005f c0005f2);

    public abstract void l(C0005f c0005f, C0005f c0005f2);

    public abstract void m(C0005f c0005f, Thread thread);
}
