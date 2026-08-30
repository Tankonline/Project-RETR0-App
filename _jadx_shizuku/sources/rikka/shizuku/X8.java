package rikka.shizuku;

import android.R;
import android.app.ContentProviderHolder;
import android.app.IActivityManager;
import android.content.Context;
import android.content.IContentProvider;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ParceledListSlice;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.permission.IPermissionManager;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.util.Xml;
import android.widget.EditText;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;
import rikka.widget.borderview.BorderRecyclerView;

/* JADX INFO: loaded from: classes.dex */
public abstract class X8 implements Xs {
    public static Method m;
    public static boolean n;
    public static Handler r;
    public static final int[] a = {R.attr.name, R.attr.tint, R.attr.height, R.attr.width, R.attr.alpha, R.attr.autoMirrored, R.attr.tintMode, R.attr.viewportWidth, R.attr.viewportHeight};
    public static final int[] b = {R.attr.name, R.attr.pivotX, R.attr.pivotY, R.attr.scaleX, R.attr.scaleY, R.attr.rotation, R.attr.translateX, R.attr.translateY};
    public static final int[] c = {R.attr.name, R.attr.fillColor, R.attr.pathData, R.attr.strokeColor, R.attr.strokeWidth, R.attr.trimPathStart, R.attr.trimPathEnd, R.attr.trimPathOffset, R.attr.strokeLineCap, R.attr.strokeLineJoin, R.attr.strokeMiterLimit, R.attr.strokeAlpha, R.attr.fillAlpha, R.attr.fillType};
    public static final int[] d = {R.attr.name, R.attr.pathData, R.attr.fillType};
    public static final int[] e = {R.attr.drawable};
    public static final int[] f = {R.attr.name, R.attr.animation};
    public static final C0589sh g = new C0589sh(1, "UNDEFINED");
    public static final C0589sh h = new C0589sh(1, "REUSABLE_CLAIMED");
    public static final Type[] i = new Type[0];
    public static final int[] j = {R.attr.theme, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f040502};
    public static final int[] k = {moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f040316};
    public static final Object l = new Object();
    public static final int[] o = {moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f040111};
    public static final int[] p = {moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f040118};
    public static final Aa q = new Aa();

    public static Type A(Type type, Class cls, Class cls2) {
        if (cls2 == cls) {
            return type;
        }
        if (cls2.isInterface()) {
            Class<?>[] interfaces = cls.getInterfaces();
            int length = interfaces.length;
            for (int i2 = 0; i2 < length; i2++) {
                Class<?> cls3 = interfaces[i2];
                if (cls3 == cls2) {
                    return cls.getGenericInterfaces()[i2];
                }
                if (cls2.isAssignableFrom(cls3)) {
                    return A(cls.getGenericInterfaces()[i2], interfaces[i2], cls2);
                }
            }
        }
        if (!cls.isInterface()) {
            while (cls != Object.class) {
                Class<?> superclass = cls.getSuperclass();
                if (superclass == cls2) {
                    return cls.getGenericSuperclass();
                }
                if (cls2.isAssignableFrom(superclass)) {
                    return A(cls.getGenericSuperclass(), superclass, cls2);
                }
                cls = superclass;
            }
        }
        return cls2;
    }

    public static final String B(Object obj) {
        return Integer.toHexString(System.identityHashCode(obj));
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public static final Class C(Yf yf) {
        Class clsA = ((InterfaceC0117d6) yf).a();
        if (clsA.isPrimitive()) {
            String name = clsA.getName();
            switch (name.hashCode()) {
                case -1325958191:
                    if (name.equals("double")) {
                        return Double.class;
                    }
                    break;
                case 104431:
                    if (name.equals("int")) {
                        return Integer.class;
                    }
                    break;
                case 3039496:
                    if (name.equals("byte")) {
                        return Byte.class;
                    }
                    break;
                case 3052374:
                    if (name.equals("char")) {
                        return Character.class;
                    }
                    break;
                case 3327612:
                    if (name.equals("long")) {
                        return Long.class;
                    }
                    break;
                case 3625364:
                    if (name.equals("void")) {
                        return Void.class;
                    }
                    break;
                case 64711720:
                    if (name.equals("boolean")) {
                        return Boolean.class;
                    }
                    break;
                case 97526364:
                    if (name.equals("float")) {
                        return Float.class;
                    }
                    break;
                case 109413500:
                    if (name.equals("short")) {
                        return Short.class;
                    }
                    break;
            }
        }
        return clsA;
    }

    public static ArrayList D() {
        ApplicationInfo applicationInfo;
        Bundle bundle;
        String[] strArr;
        List arrayList;
        ArrayList arrayList2 = new ArrayList();
        int i2 = 0;
        if (!Nn.i && (Nn.f() != 11 || Nn.e >= 3)) {
            Parcel parcelObtain = Parcel.obtain();
            Parcel parcelObtain2 = Parcel.obtain();
            try {
                parcelObtain.writeInterfaceToken("moe.shizuku.server.IShizukuService");
                parcelObtain.writeInt(-1);
                try {
                    Nn.a.transact(10001, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    ArrayList arrayList3 = Kj.CREATOR.createFromParcel(parcelObtain2).a;
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                    arrayList2.addAll(arrayList3);
                    return arrayList2;
                } catch (Throwable th) {
                    throw new RuntimeException(th);
                }
            } catch (Throwable th2) {
                parcelObtain2.recycle();
                parcelObtain.recycle();
                throw th2;
            }
        }
        ArrayList arrayList4 = new ArrayList();
        ArrayList arrayListC = Vn.c(false);
        int size = arrayListC.size();
        int i3 = 0;
        while (i3 < size) {
            Object obj = arrayListC.get(i3);
            i3++;
            Ur ur = (Ur) obj;
            try {
                ArrayList arrayList5 = Vn.a;
                int i4 = ur.a;
                if (Nn.h()) {
                    try {
                        ParceledListSlice installedPackages = Build.VERSION.SDK_INT >= 33 ? AbstractC0134dn.c.a().getInstalledPackages(4224L, i4) : AbstractC0134dn.c.a().getInstalledPackages((int) 4224, i4);
                        arrayList = installedPackages != null ? installedPackages.getList() : new ArrayList();
                    } catch (RemoteException e2) {
                        throw new RuntimeException(e2.getMessage(), e2);
                    }
                } else {
                    arrayList = new ArrayList();
                }
                arrayList4.addAll(arrayList);
            } catch (Throwable th3) {
                Locale locale = Locale.ENGLISH;
                Log.w("ShizukuManager", "getInstalledPackages", th3);
            }
        }
        int size2 = arrayList4.size();
        while (i2 < size2) {
            Object obj2 = arrayList4.get(i2);
            i2++;
            PackageInfo packageInfo = (PackageInfo) obj2;
            if (!"moe.shizuku.privileged.api".equals(packageInfo.packageName) && (applicationInfo = packageInfo.applicationInfo) != null && (bundle = applicationInfo.metaData) != null && bundle.getBoolean("moe.shizuku.client.V3_SUPPORT") && (strArr = packageInfo.requestedPermissions) != null && AbstractC0484p4.d0(strArr, "moe.shizuku.manager.permission.API_V23")) {
                arrayList2.add(packageInfo);
            }
        }
        return arrayList2;
    }

    public static Class E(Type type) {
        if (type instanceof Class) {
            return (Class) type;
        }
        if (type instanceof ParameterizedType) {
            Type rawType = ((ParameterizedType) type).getRawType();
            AbstractC0744xh.g(rawType instanceof Class);
            return (Class) rawType;
        }
        if (type instanceof GenericArrayType) {
            return Array.newInstance((Class<?>) E(((GenericArrayType) type).getGenericComponentType()), 0).getClass();
        }
        if (type instanceof TypeVariable) {
            return Object.class;
        }
        if (type instanceof WildcardType) {
            return E(((WildcardType) type).getUpperBounds()[0]);
        }
        throw new IllegalArgumentException("Expected a Class, ParameterizedType, or GenericArrayType, but <" + type + "> is of type " + (type == null ? "null" : type.getClass().getName()));
    }

    public static final Bundle F(String str, Bundle bundle) {
        Bundle bundle2 = bundle.getBundle(str);
        if (bundle2 != null) {
            return bundle2;
        }
        throw new IllegalArgumentException(AbstractC0535qo.d("No valid saved state was found for the key '", str, "'. It may be missing, null, or not of the expected type. This can occur if the value was saved with a different type or if the saved state was modified unexpectedly."));
    }

    public static void G(int i2, String str, String str2) {
        int i3 = Build.VERSION.SDK_INT;
        if (i3 < 34) {
            if (i3 >= 30) {
                AbstractC0134dn.d.a().grantRuntimePermission(str, str2, i2);
                return;
            } else {
                AbstractC0134dn.c.a().grantRuntimePermission(str, str2, i2);
                return;
            }
        }
        IPermissionManager iPermissionManagerA = AbstractC0134dn.d.a();
        try {
            iPermissionManagerA.grantRuntimePermission(str, str2, "default:0", i2);
        } catch (NoSuchMethodError unused) {
            try {
                iPermissionManagerA.grantRuntimePermission(str, str2, 0, i2);
            } catch (NoSuchMethodError unused2) {
                iPermissionManagerA.grantRuntimePermission(str, str2, i2);
            }
        }
    }

    public static boolean H(EditText editText) {
        return editText.getInputType() != 0;
    }

    public static boolean I(Context context) {
        return context.getResources().getConfiguration().fontScale >= 1.3f;
    }

    public static TypedArray J(Context context, AttributeSet attributeSet, int[] iArr, int i2, int i3, int... iArr2) {
        j(context, attributeSet, i2, i3);
        p(context, attributeSet, iArr, i2, i3, iArr2);
        return context.obtainStyledAttributes(attributeSet, iArr, i2, i3);
    }

    public static InterfaceC0492pc M(XmlResourceParser xmlResourceParser, Resources resources) throws XmlPullParserException, IOException {
        int next;
        do {
            next = xmlResourceParser.next();
            if (next == 2) {
                break;
            }
        } while (next != 1);
        if (next != 2) {
            throw new XmlPullParserException("No start tag found");
        }
        xmlResourceParser.require(2, null, "font-family");
        if (!xmlResourceParser.getName().equals("font-family")) {
            X(xmlResourceParser);
            return null;
        }
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(Xml.asAttributeSet(xmlResourceParser), Hk.b);
        String string = typedArrayObtainAttributes.getString(0);
        String string2 = typedArrayObtainAttributes.getString(5);
        String string3 = typedArrayObtainAttributes.getString(6);
        String string4 = typedArrayObtainAttributes.getString(2);
        int resourceId = typedArrayObtainAttributes.getResourceId(1, 0);
        int integer = typedArrayObtainAttributes.getInteger(3, 1);
        int integer2 = typedArrayObtainAttributes.getInteger(4, 500);
        String string5 = typedArrayObtainAttributes.getString(7);
        typedArrayObtainAttributes.recycle();
        if (string != null && string2 != null && string3 != null) {
            while (xmlResourceParser.next() != 3) {
                X(xmlResourceParser);
            }
            List listQ = Q(resources, resourceId);
            return new C0584sc(new C0277ic(string, string2, string3, listQ), string4 != null ? new C0277ic(string, string2, string4, listQ) : null, integer, integer2, string5);
        }
        ArrayList arrayList = new ArrayList();
        while (xmlResourceParser.next() != 3) {
            if (xmlResourceParser.getEventType() == 2) {
                if (xmlResourceParser.getName().equals("font")) {
                    TypedArray typedArrayObtainAttributes2 = resources.obtainAttributes(Xml.asAttributeSet(xmlResourceParser), Hk.c);
                    int i2 = typedArrayObtainAttributes2.getInt(typedArrayObtainAttributes2.hasValue(8) ? 8 : 1, 400);
                    boolean z = 1 == typedArrayObtainAttributes2.getInt(typedArrayObtainAttributes2.hasValue(6) ? 6 : 2, 0);
                    int i3 = typedArrayObtainAttributes2.hasValue(9) ? 9 : 3;
                    String string6 = typedArrayObtainAttributes2.getString(typedArrayObtainAttributes2.hasValue(7) ? 7 : 4);
                    int i4 = typedArrayObtainAttributes2.getInt(i3, 0);
                    int i5 = typedArrayObtainAttributes2.hasValue(5) ? 5 : 0;
                    int resourceId2 = typedArrayObtainAttributes2.getResourceId(i5, 0);
                    String string7 = typedArrayObtainAttributes2.getString(i5);
                    typedArrayObtainAttributes2.recycle();
                    while (xmlResourceParser.next() != 3) {
                        X(xmlResourceParser);
                    }
                    arrayList.add(new C0553rc(string7, i2, z, string6, i4, resourceId2));
                } else {
                    X(xmlResourceParser);
                }
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return new C0523qc((C0553rc[]) arrayList.toArray(new C0553rc[0]));
    }

    public static BigDecimal N(String str) {
        l(str);
        BigDecimal bigDecimal = new BigDecimal(str);
        if (Math.abs(bigDecimal.scale()) < 10000) {
            return bigDecimal;
        }
        throw new NumberFormatException("Number has unsupported scale: ".concat(str));
    }

    public static void O(Context context, String str) {
        if (str.equals("")) {
            context.deleteFile("androidx.appcompat.app.AppCompatDelegate.application_locales_record_file");
            return;
        }
        try {
            FileOutputStream fileOutputStreamOpenFileOutput = context.openFileOutput("androidx.appcompat.app.AppCompatDelegate.application_locales_record_file", 0);
            XmlSerializer xmlSerializerNewSerializer = Xml.newSerializer();
            try {
                try {
                    try {
                        xmlSerializerNewSerializer.setOutput(fileOutputStreamOpenFileOutput, null);
                        xmlSerializerNewSerializer.startDocument("UTF-8", Boolean.TRUE);
                        xmlSerializerNewSerializer.startTag(null, "locales");
                        xmlSerializerNewSerializer.attribute(null, "application_locales", str);
                        xmlSerializerNewSerializer.endTag(null, "locales");
                        xmlSerializerNewSerializer.endDocument();
                        if (fileOutputStreamOpenFileOutput != null) {
                            fileOutputStreamOpenFileOutput.close();
                        }
                    } catch (Exception e2) {
                        Log.w("AppLocalesStorageHelper", "Storing App Locales : Failed to persist app-locales: ".concat(str), e2);
                        if (fileOutputStreamOpenFileOutput != null) {
                            fileOutputStreamOpenFileOutput.close();
                        }
                    }
                } catch (Throwable th) {
                    if (fileOutputStreamOpenFileOutput != null) {
                        try {
                            fileOutputStreamOpenFileOutput.close();
                        } catch (IOException unused) {
                        }
                    }
                    throw th;
                }
            } catch (IOException unused2) {
            }
        } catch (FileNotFoundException unused3) {
            Log.w("AppLocalesStorageHelper", "Storing App Locales : FileNotFoundException: Cannot open file androidx.appcompat.app.AppCompatDelegate.application_locales_record_file for writing ");
        }
    }

    public static Bi P(MappedByteBuffer mappedByteBuffer) throws IOException {
        long j2;
        ByteBuffer byteBufferDuplicate = mappedByteBuffer.duplicate();
        byteBufferDuplicate.order(ByteOrder.BIG_ENDIAN);
        byteBufferDuplicate.position(byteBufferDuplicate.position() + 4);
        int i2 = byteBufferDuplicate.getShort() & 65535;
        if (i2 > 100) {
            throw new IOException("Cannot read metadata.");
        }
        byteBufferDuplicate.position(byteBufferDuplicate.position() + 6);
        int i3 = 0;
        while (true) {
            if (i3 >= i2) {
                j2 = -1;
                break;
            }
            int i4 = byteBufferDuplicate.getInt();
            byteBufferDuplicate.position(byteBufferDuplicate.position() + 4);
            j2 = ((long) byteBufferDuplicate.getInt()) & 4294967295L;
            byteBufferDuplicate.position(byteBufferDuplicate.position() + 4);
            if (1835365473 == i4) {
                break;
            }
            i3++;
        }
        if (j2 != -1) {
            byteBufferDuplicate.position(byteBufferDuplicate.position() + ((int) (j2 - ((long) byteBufferDuplicate.position()))));
            byteBufferDuplicate.position(byteBufferDuplicate.position() + 12);
            long j3 = ((long) byteBufferDuplicate.getInt()) & 4294967295L;
            for (int i5 = 0; i5 < j3; i5++) {
                int i6 = byteBufferDuplicate.getInt();
                long j4 = ((long) byteBufferDuplicate.getInt()) & 4294967295L;
                byteBufferDuplicate.getInt();
                if (1164798569 == i6 || 1701669481 == i6) {
                    byteBufferDuplicate.position((int) (j4 + j2));
                    Bi bi = new Bi();
                    byteBufferDuplicate.order(ByteOrder.LITTLE_ENDIAN);
                    int iPosition = byteBufferDuplicate.position() + byteBufferDuplicate.getInt(byteBufferDuplicate.position());
                    bi.d = byteBufferDuplicate;
                    bi.a = iPosition;
                    int i7 = iPosition - byteBufferDuplicate.getInt(iPosition);
                    bi.b = i7;
                    bi.c = ((ByteBuffer) bi.d).getShort(i7);
                    return bi;
                }
            }
        }
        throw new IOException("Cannot read metadata.");
    }

    public static List Q(Resources resources, int i2) {
        if (i2 == 0) {
            return Collections.EMPTY_LIST;
        }
        TypedArray typedArrayObtainTypedArray = resources.obtainTypedArray(i2);
        try {
            if (typedArrayObtainTypedArray.length() == 0) {
                return Collections.EMPTY_LIST;
            }
            ArrayList arrayList = new ArrayList();
            if (typedArrayObtainTypedArray.getType(0) == 1) {
                for (int i3 = 0; i3 < typedArrayObtainTypedArray.length(); i3++) {
                    int resourceId = typedArrayObtainTypedArray.getResourceId(i3, 0);
                    if (resourceId != 0) {
                        String[] stringArray = resources.getStringArray(resourceId);
                        ArrayList arrayList2 = new ArrayList();
                        for (String str : stringArray) {
                            arrayList2.add(Base64.decode(str, 0));
                        }
                        arrayList.add(arrayList2);
                    }
                }
            } else {
                String[] stringArray2 = resources.getStringArray(i2);
                ArrayList arrayList3 = new ArrayList();
                for (String str2 : stringArray2) {
                    arrayList3.add(Base64.decode(str2, 0));
                }
                arrayList.add(arrayList3);
            }
            return arrayList;
        } finally {
            typedArrayObtainTypedArray.recycle();
        }
    }

    /* JADX WARN: Code duplicated, block: B:45:0x0045 A[EXC_TOP_SPLITTER, PHI: r2
  0x0045: PHI (r2v2 java.lang.String) = (r2v0 java.lang.String), (r2v4 java.lang.String) binds: [B:25:0x004e, B:21:0x0043] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    public static String R(Context context) {
        String attributeValue = "";
        try {
            FileInputStream fileInputStreamOpenFileInput = context.openFileInput("androidx.appcompat.app.AppCompatDelegate.application_locales_record_file");
            try {
                try {
                    XmlPullParser xmlPullParserNewPullParser = Xml.newPullParser();
                    xmlPullParserNewPullParser.setInput(fileInputStreamOpenFileInput, "UTF-8");
                    int depth = xmlPullParserNewPullParser.getDepth();
                    while (true) {
                        int next = xmlPullParserNewPullParser.next();
                        if (next != 1 && (next != 3 || xmlPullParserNewPullParser.getDepth() > depth)) {
                            if (next != 3 && next != 4 && xmlPullParserNewPullParser.getName().equals("locales")) {
                                attributeValue = xmlPullParserNewPullParser.getAttributeValue(null, "application_locales");
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                    if (fileInputStreamOpenFileInput != null) {
                        try {
                            fileInputStreamOpenFileInput.close();
                        } catch (IOException unused) {
                        }
                    }
                } catch (IOException | XmlPullParserException unused2) {
                    Log.w("AppLocalesStorageHelper", "Reading app Locales : Unable to parse through file :androidx.appcompat.app.AppCompatDelegate.application_locales_record_file");
                    if (fileInputStreamOpenFileInput != null) {
                        fileInputStreamOpenFileInput.close();
                    }
                }
                if (attributeValue.isEmpty()) {
                    context.deleteFile("androidx.appcompat.app.AppCompatDelegate.application_locales_record_file");
                }
                return attributeValue;
            } catch (Throwable th) {
                if (fileInputStreamOpenFileInput != null) {
                    try {
                        fileInputStreamOpenFileInput.close();
                    } catch (IOException unused3) {
                    }
                }
                throw th;
            }
        } catch (FileNotFoundException unused4) {
            Log.w("AppLocalesStorageHelper", "Reading app Locales : Locales record file not found: androidx.appcompat.app.AppCompatDelegate.application_locales_record_file");
            return "";
        }
    }

    public static void S(String str) {
        AbstractC0134dn.a.a().removeContentProviderExternal(str, (IBinder) null);
    }

    /* JADX WARN: Code duplicated, block: B:29:0x0057  */
    /* JADX WARN: Code duplicated, block: B:41:0x0082  */
    /* JADX WARN: Code duplicated, block: B:43:0x0086  */
    /* JADX WARN: Code duplicated, block: B:46:0x0098  */
    /* JADX WARN: Code duplicated, block: B:47:0x009e  */
    /* JADX WARN: Code duplicated, block: B:49:0x00a2  */
    /* JADX WARN: Code duplicated, block: B:51:0x00b9  */
    /* JADX WARN: Code duplicated, block: B:53:0x00c7 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:54:0x00c9  */
    /* JADX WARN: Code duplicated, block: B:60:0x00e5  */
    /* JADX WARN: Code duplicated, block: B:62:0x00e9  */
    /* JADX WARN: Code duplicated, block: B:64:0x00f6  */
    /* JADX WARN: Code duplicated, block: B:66:0x0100  */
    /* JADX WARN: Code duplicated, block: B:68:0x0104  */
    /* JADX WARN: Code duplicated, block: B:69:0x010b  */
    /* JADX WARN: Code duplicated, block: B:71:0x011c  */
    /* JADX WARN: Code duplicated, block: B:77:0x012d  */
    /* JADX WARN: Code duplicated, block: B:78:0x0134  */
    /* JADX WARN: Code duplicated, block: B:93:0x00d3 A[SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r12v0, types: [java.lang.reflect.Type] */
    /* JADX WARN: Type inference failed for: r12v1, types: [java.lang.reflect.Type] */
    /* JADX WARN: Type inference failed for: r12v10, types: [java.lang.Object, java.lang.reflect.Type] */
    /* JADX WARN: Type inference failed for: r12v14 */
    /* JADX WARN: Type inference failed for: r12v15 */
    /* JADX WARN: Type inference failed for: r12v17, types: [java.lang.reflect.Type[]] */
    /* JADX WARN: Type inference failed for: r12v18 */
    /* JADX WARN: Type inference failed for: r12v2, types: [java.lang.reflect.WildcardType] */
    /* JADX WARN: Type inference failed for: r12v3, types: [rikka.shizuku.Vd] */
    /* JADX WARN: Type inference failed for: r12v4, types: [rikka.shizuku.Vd] */
    /* JADX WARN: Type inference failed for: r12v5, types: [java.lang.reflect.ParameterizedType] */
    /* JADX WARN: Type inference failed for: r12v6, types: [java.lang.reflect.GenericArrayType] */
    /* JADX WARN: Type inference failed for: r12v7 */
    /* JADX WARN: Type inference failed for: r12v9 */
    /* JADX WARN: Type inference failed for: r13v0, types: [java.util.HashMap] */
    /* JADX WARN: Type inference failed for: r2v10 */
    /* JADX WARN: Type inference failed for: r4v5 */
    public static Type T(Type type, Class cls, Type type2, HashMap map) {
        Type[] lowerBounds;
        Type[] upperBounds;
        Type typeT;
        Type[] upperBounds2;
        Type typeT2;
        Type[] lowerBounds2;
        boolean zEquals;
        int length;
        Type[] typeArr;
        boolean z;
        Type ud;
        Type typeT3;
        Type genericComponentType;
        Type typeT4;
        TypeVariable typeVariable;
        TypeVariable typeVariable2 = null;
        do {
            if (!(type2 instanceof TypeVariable)) {
                if (!(type2 instanceof Class)) {
                    if (type2 instanceof GenericArrayType) {
                        if (type2 instanceof ParameterizedType) {
                            if (type2 instanceof WildcardType) {
                                break;
                            }
                            type2 = (WildcardType) type2;
                            lowerBounds = type2.getLowerBounds();
                            upperBounds = type2.getUpperBounds();
                            if (lowerBounds.length == 1) {
                                if (upperBounds.length != 1) {
                                    break;
                                }
                                if (typeT instanceof WildcardType) {
                                    upperBounds2 = ((WildcardType) typeT).getUpperBounds();
                                } else {
                                    upperBounds2 = new Type[]{typeT};
                                }
                                type2 = new Vd(upperBounds2, i);
                                break;
                            }
                            typeT2 = T(type, cls, lowerBounds[0], map);
                            if (typeT2 != lowerBounds[0]) {
                                break;
                            }
                            if (typeT2 instanceof WildcardType) {
                                lowerBounds2 = ((WildcardType) typeT2).getLowerBounds();
                            } else {
                                lowerBounds2 = new Type[]{typeT2};
                            }
                            type2 = new Vd(new Type[]{Object.class}, lowerBounds2);
                            break;
                        }
                        type2 = (ParameterizedType) type2;
                        Type ownerType = type2.getOwnerType();
                        Type typeT5 = T(type, cls, ownerType, map);
                        zEquals = Objects.equals(typeT5, ownerType);
                        Type[] actualTypeArguments = type2.getActualTypeArguments();
                        length = actualTypeArguments.length;
                        typeArr = actualTypeArguments;
                        z = false;
                        for (int i2 = 0; i2 < length; i2++) {
                            typeT3 = T(type, cls, typeArr[i2], map);
                            if (Objects.equals(typeT3, typeArr[i2])) {
                                if (!z) {
                                    typeArr = (Type[]) typeArr.clone();
                                    z = true;
                                }
                                typeArr[i2] = typeT3;
                            }
                        }
                        if (!zEquals) {
                        }
                        ud = new Ud(typeT5, (Class) type2.getRawType(), typeArr);
                        type2 = ud;
                        break;
                    }
                    type2 = (GenericArrayType) type2;
                    genericComponentType = type2.getGenericComponentType();
                    typeT4 = T(type, cls, genericComponentType, map);
                    if (Objects.equals(genericComponentType, typeT4)) {
                        ud = new Td(typeT4);
                        type2 = ud;
                        break;
                    }
                    break;
                }
                Class cls2 = (Class) type2;
                if (!cls2.isArray()) {
                    if (type2 instanceof GenericArrayType) {
                        if (type2 instanceof ParameterizedType) {
                            if (type2 instanceof WildcardType) {
                                break;
                            }
                            type2 = (WildcardType) type2;
                            lowerBounds = type2.getLowerBounds();
                            upperBounds = type2.getUpperBounds();
                            if (lowerBounds.length == 1) {
                                if (upperBounds.length != 1 && (typeT = T(type, cls, upperBounds[0], map)) != upperBounds[0]) {
                                    if (typeT instanceof WildcardType) {
                                        upperBounds2 = ((WildcardType) typeT).getUpperBounds();
                                    } else {
                                        upperBounds2 = new Type[]{typeT};
                                    }
                                    type2 = new Vd(upperBounds2, i);
                                    break;
                                }
                                break;
                                break;
                            }
                            typeT2 = T(type, cls, lowerBounds[0], map);
                            if (typeT2 != lowerBounds[0]) {
                                break;
                            }
                            if (typeT2 instanceof WildcardType) {
                                lowerBounds2 = ((WildcardType) typeT2).getLowerBounds();
                            } else {
                                lowerBounds2 = new Type[]{typeT2};
                            }
                            type2 = new Vd(new Type[]{Object.class}, lowerBounds2);
                            break;
                        }
                        type2 = (ParameterizedType) type2;
                        Type ownerType2 = type2.getOwnerType();
                        Type typeT6 = T(type, cls, ownerType2, map);
                        zEquals = Objects.equals(typeT6, ownerType2);
                        Type[] actualTypeArguments2 = type2.getActualTypeArguments();
                        length = actualTypeArguments2.length;
                        typeArr = actualTypeArguments2;
                        z = false;
                        while (i2 < length) {
                            typeT3 = T(type, cls, typeArr[i2], map);
                            if (Objects.equals(typeT3, typeArr[i2])) {
                                if (!z) {
                                    typeArr = (Type[]) typeArr.clone();
                                    z = true;
                                }
                                typeArr[i2] = typeT3;
                            }
                        }
                        if (!zEquals && !z) {
                            break;
                        }
                        ud = new Ud(typeT6, (Class) type2.getRawType(), typeArr);
                        type2 = ud;
                        break;
                    }
                    type2 = (GenericArrayType) type2;
                    genericComponentType = type2.getGenericComponentType();
                    typeT4 = T(type, cls, genericComponentType, map);
                    if (Objects.equals(genericComponentType, typeT4)) {
                        break;
                    }
                    ud = new Td(typeT4);
                    type2 = ud;
                    break;
                }
                Class<?> componentType = cls2.getComponentType();
                Type typeT7 = T(type, cls, componentType, map);
                if (!Objects.equals(componentType, typeT7)) {
                    ud = new Td(typeT7);
                    type2 = ud;
                    break;
                }
                type2 = cls2;
                break;
            }
            typeVariable = (TypeVariable) type2;
            Type type3 = (Type) map.get(typeVariable);
            Class cls3 = Void.TYPE;
            if (type3 != null) {
                return type3 == cls3 ? type2 : type3;
            }
            map.put(typeVariable, cls3);
            if (typeVariable2 == null) {
                typeVariable2 = typeVariable;
            }
            GenericDeclaration genericDeclaration = typeVariable.getGenericDeclaration();
            Class cls4 = genericDeclaration instanceof Class ? (Class) genericDeclaration : null;
            if (cls4 == null) {
                type2 = typeVariable;
            } else {
                Type typeA = A(type, cls, cls4);
                if (typeA instanceof ParameterizedType) {
                    TypeVariable[] typeParameters = cls4.getTypeParameters();
                    int length2 = typeParameters.length;
                    int i3 = 0;
                    while (true) {
                        if (i3 >= length2) {
                            throw new NoSuchElementException();
                        }
                        if (typeVariable.equals(typeParameters[i3])) {
                            type2 = ((ParameterizedType) typeA).getActualTypeArguments()[i3];
                            break;
                        }
                        i3++;
                    }
                } else {
                    type2 = typeVariable;
                }
            }
        } while (type2 != typeVariable);
        if (typeVariable2 != null) {
            map.put(typeVariable2, type2);
        }
        return type2;
    }

    public static final void U(Object obj, G7 g7) throws Y9 {
        if (!(g7 instanceof Z9)) {
            g7.h(obj);
            return;
        }
        Z9 z9 = (Z9) g7;
        Throwable thA = AbstractC0102cm.a(obj);
        Object j6 = thA == null ? obj : new J6(thA);
        W7 w7 = z9.d;
        H7 h7 = z9.e;
        V7 v7 = h7.b;
        try {
            if (w7.e(v7)) {
                z9.f = j6;
                z9.c = 1;
                W(z9.d, v7, z9);
                return;
            }
            ThreadLocal threadLocal = Np.a;
            AbstractC0707wb c0177f5 = (AbstractC0707wb) threadLocal.get();
            if (c0177f5 == null) {
                c0177f5 = new C0177f5(Thread.currentThread());
                threadLocal.set(c0177f5);
            }
            long j2 = c0177f5.b;
            if (j2 >= 4294967296L) {
                z9.f = j6;
                z9.c = 1;
                C0084c4 c0084c4 = c0177f5.d;
                if (c0084c4 == null) {
                    c0084c4 = new C0084c4();
                    c0177f5.d = c0084c4;
                }
                c0084c4.addLast(z9);
                return;
            }
            c0177f5.b = 4294967296L + j2;
            try {
                Ef ef = (Ef) v7.g(Fe.a);
                if (ef == null || ef.a()) {
                    Object objO = AbstractC0599sr.O(v7, z9.g);
                    Gr grB0 = objO != AbstractC0599sr.r ? b0(h7, v7, objO) : null;
                    try {
                        h7.h(obj);
                        if (grB0 == null || grB0.N()) {
                            AbstractC0599sr.E(v7, objO);
                        }
                    } catch (Throwable th) {
                        if (grB0 == null || grB0.N()) {
                            AbstractC0599sr.E(v7, objO);
                        }
                        throw th;
                    }
                } else {
                    z9.h(new C0071bm(ef.c()));
                }
                while (c0177f5.n()) {
                }
            } catch (Throwable th2) {
                try {
                    z9.a(th2);
                } finally {
                    c0177f5.h();
                }
            }
        } catch (Throwable th3) {
            throw new Y9(th3, w7, v7);
        }
    }

    public static void V(int i2, String str) {
        int i3 = Build.VERSION.SDK_INT;
        if (i3 >= 34) {
            IPermissionManager iPermissionManagerA = AbstractC0134dn.d.a();
            try {
                iPermissionManagerA.revokeRuntimePermission(str, "moe.shizuku.manager.permission.API_V23", "default:0", i2, (String) null);
                return;
            } catch (NoSuchMethodError unused) {
                try {
                    iPermissionManagerA.revokeRuntimePermission(str, "moe.shizuku.manager.permission.API_V23", 0, i2, (String) null);
                    return;
                } catch (NoSuchMethodError unused2) {
                    iPermissionManagerA.revokeRuntimePermission(str, "moe.shizuku.manager.permission.API_V23", i2, (String) null);
                    return;
                }
            }
        }
        if (i3 < 30) {
            AbstractC0134dn.c.a().revokeRuntimePermission(str, "moe.shizuku.manager.permission.API_V23", i2);
            return;
        }
        IPermissionManager iPermissionManagerA2 = AbstractC0134dn.d.a();
        try {
            iPermissionManagerA2.revokeRuntimePermission(str, "moe.shizuku.manager.permission.API_V23", i2, (String) null);
        } catch (NoSuchMethodError unused3) {
            iPermissionManagerA2.revokeRuntimePermission(str, "moe.shizuku.manager.permission.API_V23", i2);
        }
    }

    public static final void W(W7 w7, V7 v7, Runnable runnable) throws Y9 {
        try {
            w7.d(v7, runnable);
        } catch (Throwable th) {
            throw new Y9(th, w7, v7);
        }
    }

    public static void X(XmlResourceParser xmlResourceParser) throws XmlPullParserException, IOException {
        int i2 = 1;
        while (i2 > 0) {
            int next = xmlResourceParser.next();
            if (next == 2) {
                i2++;
            } else if (next == 3) {
                i2--;
            }
        }
    }

    public static final int[] Y(BigInteger bigInteger) {
        int[] iArr = new int[64];
        BigInteger bigInteger2 = BigInteger.ZERO;
        BigInteger bit = bigInteger2.setBit(32);
        BigInteger bigIntegerAdd = bigInteger.add(bigInteger2);
        int i2 = 0;
        while (i2 < 64) {
            BigInteger[] bigIntegerArrDivideAndRemainder = bigIntegerAdd.divideAndRemainder(bit);
            BigInteger bigInteger3 = bigIntegerArrDivideAndRemainder[0];
            iArr[i2] = bigIntegerArrDivideAndRemainder[1].intValue();
            i2++;
            bigIntegerAdd = bigInteger3;
        }
        return iArr;
    }

    public static final String Z(H7 h7) {
        Object c0071bm;
        try {
            c0071bm = h7 + '@' + B(h7);
        } catch (Throwable th) {
            c0071bm = new C0071bm(th);
        }
        if (AbstractC0102cm.a(c0071bm) != null) {
            c0071bm = h7.getClass().getName() + '@' + B(h7);
        }
        return (String) c0071bm;
    }

    public static String a0(Type type) {
        return type instanceof Class ? ((Class) type).getName() : type.toString();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [rikka.shizuku.H7] */
    /* JADX WARN: Type inference failed for: r2v1, types: [rikka.shizuku.c8] */
    /* JADX WARN: Type inference failed for: r2v2, types: [rikka.shizuku.c8] */
    public static final Gr b0(H7 h7, V7 v7, Object obj) {
        Gr gr = null;
        if (h7 != 0 && v7.g(Hr.a) != null) {
            while (!(h7 instanceof C0028aa) && (h7 = h7.d()) != 0) {
                if (h7 instanceof Gr) {
                    gr = (Gr) h7;
                    break;
                }
            }
            if (gr != null) {
                gr.P(v7, obj);
            }
        }
        return gr;
    }

    public static Object c(Parcel parcel, Parcelable.Creator creator) {
        if (parcel.readInt() != 0) {
            return creator.createFromParcel(parcel);
        }
        return null;
    }

    public static Context c0(Context context, AttributeSet attributeSet, int i2, int i3) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, k, i2, i3);
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(0, 0);
        typedArrayObtainStyledAttributes.recycle();
        boolean z = (context instanceof F7) && ((F7) context).a == resourceId;
        if (resourceId == 0 || z) {
            return context;
        }
        F7 f7 = new F7(context, resourceId);
        TypedArray typedArrayObtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, j);
        int resourceId2 = typedArrayObtainStyledAttributes2.getResourceId(0, 0);
        int resourceId3 = typedArrayObtainStyledAttributes2.getResourceId(1, 0);
        typedArrayObtainStyledAttributes2.recycle();
        if (resourceId2 == 0) {
            resourceId2 = resourceId3;
        }
        if (resourceId2 != 0) {
            f7.getTheme().applyStyle(resourceId2, true);
        }
        return f7;
    }

    public static final C0799za e(BorderRecyclerView borderRecyclerView, float f2, float f3, float f4, float f5) {
        DisplayMetrics displayMetrics = borderRecyclerView.getContext().getResources().getDisplayMetrics();
        C0799za c0799za = new C0799za(AbstractC0046as.R(TypedValue.applyDimension(1, f2, displayMetrics)), AbstractC0046as.R(TypedValue.applyDimension(1, f3, displayMetrics)), AbstractC0046as.R(TypedValue.applyDimension(1, f4, displayMetrics)), AbstractC0046as.R(TypedValue.applyDimension(1, f5, displayMetrics)), 0);
        borderRecyclerView.i(c0799za);
        return c0799za;
    }

    /* JADX WARN: Code duplicated, block: B:178:0x026b  */
    /* JADX WARN: Code duplicated, block: B:195:0x02b5  */
    /* JADX WARN: Code duplicated, block: B:197:0x02b8  */
    /* JADX WARN: Code duplicated, block: B:199:0x02be  */
    /* JADX WARN: Code duplicated, block: B:397:0x068e  */
    /* JADX WARN: Code duplicated, block: B:398:0x0691  */
    /* JADX WARN: Code duplicated, block: B:401:0x0697  */
    /* JADX WARN: Code duplicated, block: B:402:0x069a  */
    /* JADX WARN: Code duplicated, block: B:404:0x069e  */
    /* JADX WARN: Code duplicated, block: B:406:0x06a6  */
    /* JADX WARN: Code duplicated, block: B:409:0x06ae  */
    /* JADX WARN: Code duplicated, block: B:411:0x06b2 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:420:0x06cd A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:65:0x00ea  */
    /* JADX WARN: Code duplicated, block: B:74:0x0108  */
    public static void g(C0456o7 c0456o7, Lg lg, int i2) {
        int i3;
        I5[] i5Arr;
        int i4;
        float f2;
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        Z6[] z6Arr;
        C0425n7 c0425n7;
        Lg lg2;
        C0565ro c0565ro;
        Z6 z6;
        C0565ro c0565ro2;
        int i5;
        Z6 z7;
        C0565ro c0565ro3;
        C0565ro c0565ro4;
        C0425n7 c0425n8;
        int i6;
        Z6[] z6Arr2;
        int i7;
        Z6 z8;
        Z6 z9;
        C0565ro c0565ro5;
        Z6 z10;
        C0565ro c0565ro6;
        int size;
        float f3;
        ArrayList arrayList;
        int i8;
        float f4;
        float f5;
        boolean z5;
        Z6 z11;
        C0425n7 c0425n9;
        int i9;
        C0425n7 c0425n10;
        C0456o7 c0456o8 = c0456o7;
        if (i2 == 0) {
            i3 = c0456o8.l0;
            i5Arr = c0456o8.o0;
            i4 = 0;
        } else {
            i3 = c0456o8.m0;
            i5Arr = c0456o8.n0;
            i4 = 2;
        }
        int i10 = i3;
        I5[] i5Arr2 = i5Arr;
        int i11 = 0;
        while (i11 < i10) {
            I5 i12 = i5Arr2[i11];
            boolean z12 = i12.q;
            C0425n7 c0425n11 = i12.a;
            int i13 = 3;
            int i14 = 8;
            if (z12) {
                f2 = 0.0f;
            } else {
                int i15 = i12.l;
                int i16 = i15 * 2;
                C0425n7 c0425n12 = c0425n11;
                C0425n7 c0425n13 = c0425n12;
                boolean z13 = false;
                f2 = 0.0f;
                while (!z13) {
                    i12.i++;
                    c0425n12.b0[i15] = null;
                    c0425n12.a0[i15] = null;
                    int i17 = c0425n12.V;
                    Z6[] z6Arr3 = c0425n12.F;
                    if (i17 != i14) {
                        c0425n12.h(i15);
                        z6Arr3[i16].c();
                        int i18 = i16 + 1;
                        z6Arr3[i18].c();
                        z6Arr3[i16].c();
                        z6Arr3[i18].c();
                        if (i12.b == null) {
                            i12.b = c0425n12;
                        }
                        i12.d = c0425n12;
                        int i19 = c0425n12.c0[i15];
                        if (i19 == i13) {
                            int i20 = c0425n12.l[i15];
                            if (i20 == 0 || i20 == i13 || i20 == 2) {
                                i12.j++;
                                float f6 = c0425n12.Z[i15];
                                if (f6 > 0.0f) {
                                    i12.k += f6;
                                }
                                if (c0425n12.V != 8 && i19 == 3 && (i20 == 0 || i20 == 3)) {
                                    if (f6 < 0.0f) {
                                        i12.n = true;
                                    } else {
                                        i12.o = true;
                                    }
                                    if (i12.h == null) {
                                        i12.h = new ArrayList();
                                    }
                                    i12.h.add(c0425n12);
                                }
                                if (i12.f == null) {
                                    i12.f = c0425n12;
                                }
                                C0425n7 c0425n14 = i12.g;
                                if (c0425n14 != null) {
                                    c0425n14.a0[i15] = c0425n12;
                                }
                                i12.g = c0425n12;
                            } else {
                                i15 = i15;
                            }
                            if (i15 == 0) {
                                if (c0425n12.j == 0 && c0425n12.m == 0) {
                                    int i21 = c0425n12.n;
                                }
                            } else if (c0425n12.k == 0 && c0425n12.p == 0) {
                                int i22 = c0425n12.q;
                            }
                        } else {
                            i15 = i15;
                        }
                    } else {
                        i15 = i15;
                    }
                    if (c0425n13 != c0425n12) {
                        c0425n13.b0[i15] = c0425n12;
                    }
                    Z6 z14 = z6Arr3[i16 + 1].d;
                    if (z14 != null) {
                        c0425n10 = z14.b;
                        Z6 z15 = c0425n10.F[i16].d;
                        if (z15 == null || z15.b != c0425n12) {
                            c0425n10 = null;
                        }
                    } else {
                        c0425n10 = null;
                    }
                    if (c0425n10 == null) {
                        c0425n10 = c0425n12;
                        z13 = true;
                    }
                    c0425n13 = c0425n12;
                    i13 = 3;
                    i14 = 8;
                    c0425n12 = c0425n10;
                    i15 = i15;
                }
                int i23 = i15;
                C0425n7 c0425n15 = i12.b;
                if (c0425n15 != null) {
                    c0425n15.F[i16].c();
                }
                C0425n7 c0425n16 = i12.d;
                if (c0425n16 != null) {
                    c0425n16.F[i16 + 1].c();
                }
                i12.c = c0425n12;
                if (i23 == 0 && i12.m) {
                    i12.e = c0425n12;
                } else {
                    i12.e = c0425n11;
                }
                i12.p = i12.o && i12.n;
            }
            i12.q = true;
            C0425n7 c0425n17 = i12.c;
            C0425n7 c0425n18 = i12.b;
            C0425n7 c0425n19 = i12.d;
            C0425n7 c0425n20 = i12.e;
            float f7 = i12.k;
            boolean z16 = c0456o8.c0[i2] == 2;
            if (i2 == 0) {
                int i24 = c0425n20.X;
                boolean z17 = i24 == 0;
                boolean z18 = i24 == 1;
                z = i24 == 2;
                z3 = z18;
                z2 = z17;
                z4 = false;
            } else {
                int i25 = c0425n20.Y;
                boolean z19 = i25 == 0;
                boolean z20 = i25 == 1;
                z = i25 == 2;
                z2 = z19;
                z3 = z20;
                z4 = false;
            }
            while (true) {
                z6Arr = c0456o8.F;
                if (z4) {
                    break;
                }
                Z6 z21 = c0425n11.F[i4];
                int i26 = z ? 1 : 4;
                int iC = z21.c();
                boolean z22 = z4;
                int[] iArr = c0425n11.c0;
                boolean z23 = z;
                boolean z24 = iArr[i2] == 3 && c0425n11.l[i2] == 0;
                Z6 z25 = z21.d;
                if (z25 != null && c0425n11 != c0425n11) {
                    iC = z25.c() + iC;
                }
                int i27 = iC;
                if (z23 && c0425n11 != c0425n11 && c0425n11 != c0425n18) {
                    i26 = 5;
                }
                boolean z26 = z24;
                Z6 z27 = z21.d;
                if (z27 != null) {
                    if (c0425n11 == c0425n18) {
                        lg.f(z21.g, z27.g, i27, 6);
                    } else {
                        lg.f(z21.g, z27.g, i27, 8);
                    }
                    lg.e(z21.g, z21.d.g, i27, (!z26 || z23) ? i26 : 5);
                } else {
                    i10 = i10;
                    i5Arr2 = i5Arr2;
                }
                Z6[] z6Arr4 = c0425n11.F;
                if (z16) {
                    if (c0425n11.V == 8 || iArr[i2] != 3) {
                        i9 = 0;
                    } else {
                        i9 = 0;
                        lg.f(z6Arr4[i4 + 1].g, z6Arr4[i4].g, 0, 5);
                    }
                    lg.f(z6Arr4[i4].g, z6Arr[i4].g, i9, 8);
                }
                Z6 z28 = z6Arr4[i4 + 1].d;
                if (z28 != null) {
                    c0425n9 = z28.b;
                    Z6 z29 = c0425n9.F[i4].d;
                    if (z29 == null || z29.b != c0425n11) {
                        c0425n9 = null;
                    }
                } else {
                    c0425n9 = null;
                }
                if (c0425n9 != null) {
                    c0425n11 = c0425n9;
                    z4 = z22;
                } else {
                    z4 = true;
                }
                z = z23;
                i10 = i10;
                i5Arr2 = i5Arr2;
            }
            boolean z30 = z;
            int i28 = i10;
            I5[] i5Arr3 = i5Arr2;
            if (c0425n19 != null) {
                int i29 = i4 + 1;
                if (c0425n17.F[i29].d != null) {
                    Z6 z31 = c0425n19.F[i29];
                    if (c0425n19.c0[i2] == 3 && c0425n19.l[i2] == 0 && !z30) {
                        Z6 z32 = z31.d;
                        if (z32.b == c0456o8) {
                            lg.e(z31.g, z32.g, -z31.c(), 5);
                        } else if (z30) {
                            z11 = z31.d;
                            if (z11.b == c0456o8) {
                                lg.e(z31.g, z11.g, -z31.c(), 4);
                            }
                        }
                    } else if (z30) {
                        z11 = z31.d;
                        if (z11.b == c0456o8) {
                            lg.e(z31.g, z11.g, -z31.c(), 4);
                        }
                    }
                    lg.g(z31.g, c0425n17.F[i29].d.g, -z31.c(), 6);
                }
            }
            if (z16 != 0) {
                int i30 = i4 + 1;
                C0565ro c0565ro7 = z6Arr[i30].g;
                Z6 z33 = c0425n17.F[i30];
                lg.f(c0565ro7, z33.g, z33.c(), 8);
            }
            ArrayList arrayList2 = i12.h;
            if (arrayList2 != null && (size = arrayList2.size()) > 1) {
                if (i12.n && !i12.p) {
                    f7 = i12.j;
                }
                C0425n7 c0425n21 = null;
                float f8 = f2;
                int i31 = 0;
                while (i31 < size) {
                    C0425n7 c0425n22 = (C0425n7) arrayList2.get(i31);
                    float f9 = c0425n22.Z[i2];
                    Z6[] z6Arr5 = c0425n22.F;
                    if (f9 < f2) {
                        if (i12.p) {
                            arrayList = arrayList2;
                            i8 = size;
                            lg.e(z6Arr5[i4 + 1].g, z6Arr5[i4].g, 0, 4);
                            z5 = false;
                        } else {
                            f3 = 1.0f;
                        }
                        i31 = i31;
                        f5 = f2;
                        i4 = i4;
                        i31++;
                        i4 = i4;
                        arrayList2 = arrayList;
                        size = i8;
                        f2 = f5;
                    } else {
                        f3 = f9;
                    }
                    arrayList = arrayList2;
                    i8 = size;
                    if (f3 == f2) {
                        z5 = false;
                        lg.e(z6Arr5[i4 + 1].g, z6Arr5[i4].g, 0, 8);
                        i31 = i31;
                        f5 = f2;
                        i4 = i4;
                    } else {
                        if (c0425n21 != null) {
                            Z6[] z6Arr6 = c0425n21.F;
                            C0565ro c0565ro8 = z6Arr6[i4].g;
                            int i32 = i4 + 1;
                            C0565ro c0565ro9 = z6Arr6[i32].g;
                            f4 = f3;
                            C0565ro c0565ro10 = z6Arr5[i4].g;
                            C0565ro c0565ro11 = z6Arr5[i32].g;
                            C0360l4 c0360l4K = lg.k();
                            float f10 = f2;
                            c0360l4K.b = f10;
                            f5 = f10;
                            if (f7 == f10 || f8 == f4) {
                                c0360l4K.d.b(c0565ro8, 1.0f);
                                c0360l4K.d.b(c0565ro9, -1.0f);
                                c0360l4K.d.b(c0565ro11, 1.0f);
                                c0360l4K.d.b(c0565ro10, -1.0f);
                            } else if (f8 == f5) {
                                c0360l4K.d.b(c0565ro8, 1.0f);
                                c0360l4K.d.b(c0565ro9, -1.0f);
                            } else if (f3 == f2) {
                                c0360l4K.d.b(c0565ro10, 1.0f);
                                c0360l4K.d.b(c0565ro11, -1.0f);
                            } else {
                                float f11 = (f8 / f7) / (f4 / f7);
                                c0360l4K.d.b(c0565ro8, 1.0f);
                                c0360l4K.d.b(c0565ro9, -1.0f);
                                c0360l4K.d.b(c0565ro11, f11);
                                c0360l4K.d.b(c0565ro10, -f11);
                            }
                            lg.c(c0360l4K);
                        } else {
                            f4 = f3;
                            f5 = f2;
                            i4 = i4;
                        }
                        c0425n21 = c0425n22;
                        f8 = f4;
                    }
                    i31++;
                    i4 = i4;
                    arrayList2 = arrayList;
                    size = i8;
                    f2 = f5;
                }
            }
            int i33 = i4;
            if (c0425n18 == null || !(c0425n18 == c0425n19 || z30)) {
                c0425n7 = c0425n19;
                if (!z2 || c0425n18 == null) {
                    if (z3 && c0425n18 != null) {
                        int i34 = i12.j;
                        boolean z34 = i34 > 0 && i12.i == i34;
                        C0425n7 c0425n23 = c0425n18;
                        C0425n7 c0425n24 = c0425n23;
                        while (c0425n23 != null) {
                            C0425n7 c0425n25 = c0425n23.b0[i2];
                            while (c0425n25 != null && c0425n25.V == 8) {
                                c0425n25 = c0425n25.b0[i2];
                            }
                            if (c0425n23 != c0425n18 && c0425n23 != c0425n7 && c0425n25 != null) {
                                if (c0425n25 == c0425n7) {
                                    c0425n25 = null;
                                }
                                Z6[] z6Arr7 = c0425n23.F;
                                Z6 z35 = z6Arr7[i33];
                                C0565ro c0565ro12 = z35.g;
                                int i35 = i33 + 1;
                                C0565ro c0565ro13 = c0425n24.F[i35].g;
                                int iC2 = z35.c();
                                int iC3 = z6Arr7[i35].c();
                                if (c0425n25 != null) {
                                    z6 = c0425n25.F[i33];
                                    c0565ro2 = z6.g;
                                    Z6 z36 = z6.d;
                                    c0565ro = z36 != null ? z36.g : null;
                                } else {
                                    Z6 z37 = c0425n7.F[i33];
                                    C0565ro c0565ro14 = z37 != null ? z37.g : null;
                                    c0565ro = z6Arr7[i35].g;
                                    z6 = z37;
                                    c0565ro2 = c0565ro14;
                                }
                                if (z6 != null) {
                                    iC3 += z6.c();
                                }
                                int iC4 = c0425n24.F[i35].c() + iC2;
                                C0565ro c0565ro15 = c0565ro2;
                                int i36 = iC3;
                                C0565ro c0565ro16 = c0565ro;
                                int i37 = z34 ? 8 : 4;
                                if (c0565ro12 != null && c0565ro13 != null && c0565ro15 != null && c0565ro16 != null) {
                                    lg.b(c0565ro12, c0565ro13, iC4, 0.5f, c0565ro15, c0565ro16, i36, i37);
                                }
                                c0425n25 = c0425n25;
                            }
                            if (c0425n23.V != 8) {
                                c0425n24 = c0425n23;
                            }
                            c0425n23 = c0425n25;
                        }
                        Z6 z38 = c0425n18.F[i33];
                        Z6 z39 = c0425n11.F[i33].d;
                        int i38 = i33 + 1;
                        Z6 z40 = c0425n7.F[i38];
                        Z6 z41 = c0425n17.F[i38].d;
                        if (z39 == null) {
                            lg2 = lg;
                        } else {
                            if (c0425n18 != c0425n7) {
                                lg.e(z38.g, z39.g, z38.c(), 5);
                            } else if (z41 != null) {
                                lg2 = lg;
                                lg2.b(z38.g, z39.g, z38.c(), 0.5f, z40.g, z41.g, z40.c(), 5);
                            }
                            lg2 = lg;
                        }
                        if (z41 != null && c0425n18 != c0425n7) {
                            lg2.e(z40.g, z41.g, -z40.c(), 5);
                        }
                    }
                    if ((z2 || z3) && c0425n18 != null && c0425n18 != c0425n7) {
                        z6Arr2 = c0425n18.F;
                        Z6 z42 = z6Arr2[i33];
                        i7 = i33 + 1;
                        z8 = c0425n7.F[i7];
                        z9 = z42.d;
                        if (z9 != null) {
                            c0565ro5 = z9.g;
                        } else {
                            c0565ro5 = null;
                        }
                        z10 = z8.d;
                        if (z10 != null) {
                            c0565ro6 = z10.g;
                        } else {
                            c0565ro6 = null;
                        }
                        if (c0425n17 != c0425n7) {
                            Z6 z43 = c0425n17.F[i7].d;
                            c0565ro6 = z43 != null ? z43.g : null;
                        }
                        if (c0425n18 == c0425n7) {
                            z8 = z6Arr2[i7];
                        }
                        if (c0565ro5 == null && c0565ro6 != null) {
                            lg2.b(z42.g, c0565ro5, z42.c(), 0.5f, c0565ro6, z8.g, c0425n7.F[i7].c(), 5);
                        }
                    }
                    i11++;
                    c0456o8 = c0456o7;
                    i4 = i33;
                    i10 = i28;
                    i5Arr2 = i5Arr3;
                } else {
                    int i39 = i12.j;
                    boolean z44 = i39 > 0 && i12.i == i39;
                    C0425n7 c0425n26 = c0425n18;
                    C0425n7 c0425n27 = c0425n26;
                    while (c0425n26 != null) {
                        C0425n7 c0425n28 = c0425n26.b0[i2];
                        while (true) {
                            if (c0425n28 == null) {
                                i5 = 8;
                                break;
                            }
                            i5 = 8;
                            if (c0425n28.V != 8) {
                                break;
                            } else {
                                c0425n28 = c0425n28.b0[i2];
                            }
                        }
                        if (c0425n28 != null || c0425n26 == c0425n7) {
                            Z6[] z6Arr8 = c0425n26.F;
                            Z6 z45 = z6Arr8[i33];
                            C0565ro c0565ro17 = z45.g;
                            Z6 z46 = z45.d;
                            C0565ro c0565ro18 = z46 != null ? z46.g : null;
                            if (c0425n27 != c0425n26) {
                                c0565ro18 = c0425n27.F[i33 + 1].g;
                            } else if (c0425n26 == c0425n18 && c0425n27 == c0425n26) {
                                Z6 z47 = c0425n11.F[i33].d;
                                c0565ro18 = z47 != null ? z47.g : null;
                            }
                            int iC5 = z45.c();
                            int i40 = i33 + 1;
                            int iC6 = z6Arr8[i40].c();
                            if (c0425n28 != null) {
                                z7 = c0425n28.F[i33];
                                c0565ro3 = z7.g;
                                c0565ro4 = z6Arr8[i40].g;
                            } else {
                                z7 = c0425n17.F[i40].d;
                                c0565ro3 = z7 != null ? z7.g : null;
                                c0565ro4 = z6Arr8[i40].g;
                            }
                            if (z7 != null) {
                                iC6 += z7.c();
                            }
                            if (c0425n27 != null) {
                                iC5 += c0425n27.F[i40].c();
                            }
                            if (c0565ro17 == null || c0565ro18 == null || c0565ro3 == null || c0565ro4 == null) {
                                c0425n8 = c0425n11;
                                i6 = 8;
                            } else {
                                if (c0425n26 == c0425n18) {
                                    iC5 = c0425n18.F[i33].c();
                                }
                                if (c0425n26 == c0425n7) {
                                    iC6 = c0425n7.F[i40].c();
                                }
                                c0425n8 = c0425n11;
                                i6 = 8;
                                lg.b(c0565ro17, c0565ro18, iC5, 0.5f, c0565ro3, c0565ro4, iC6, z44 ? 8 : 5);
                            }
                        } else {
                            c0425n8 = c0425n11;
                            i6 = i5;
                        }
                        if (c0425n26.V != i6) {
                            c0425n27 = c0425n26;
                        }
                        c0425n26 = c0425n28;
                        c0425n27 = c0425n27;
                        c0425n11 = c0425n8;
                    }
                }
            } else {
                Z6 z48 = c0425n11.F[i33];
                int i41 = i33 + 1;
                Z6 z49 = c0425n17.F[i41];
                Z6 z50 = z48.d;
                C0565ro c0565ro19 = z50 != null ? z50.g : null;
                Z6 z51 = z49.d;
                C0565ro c0565ro20 = z51 != null ? z51.g : null;
                Z6 z52 = c0425n18.F[i33];
                Z6 z53 = c0425n19.F[i41];
                if (c0565ro19 == null || c0565ro20 == null) {
                    c0425n7 = c0425n19;
                } else {
                    C0565ro c0565ro21 = c0565ro19;
                    c0425n7 = c0425n19;
                    lg.b(z52.g, c0565ro21, z52.c(), i2 == 0 ? c0425n20.S : c0425n20.T, c0565ro20, z53.g, z53.c(), 7);
                }
            }
            lg2 = lg;
            if (z2) {
                z6Arr2 = c0425n18.F;
                Z6 z410 = z6Arr2[i33];
                i7 = i33 + 1;
                z8 = c0425n7.F[i7];
                z9 = z410.d;
                if (z9 != null) {
                    c0565ro5 = z9.g;
                } else {
                    c0565ro5 = null;
                }
                z10 = z8.d;
                if (z10 != null) {
                    c0565ro6 = z10.g;
                } else {
                    c0565ro6 = null;
                }
                if (c0425n17 != c0425n7) {
                    Z6 z411 = c0425n17.F[i7].d;
                    c0565ro6 = z411 != null ? z411.g : null;
                }
                if (c0425n18 == c0425n7) {
                    z8 = z6Arr2[i7];
                }
                if (c0565ro5 == null) {
                }
            } else {
                z6Arr2 = c0425n18.F;
                Z6 z412 = z6Arr2[i33];
                i7 = i33 + 1;
                z8 = c0425n7.F[i7];
                z9 = z412.d;
                if (z9 != null) {
                    c0565ro5 = z9.g;
                } else {
                    c0565ro5 = null;
                }
                z10 = z8.d;
                if (z10 != null) {
                    c0565ro6 = z10.g;
                } else {
                    c0565ro6 = null;
                }
                if (c0425n17 != c0425n7) {
                    Z6 z413 = c0425n17.F[i7].d;
                    c0565ro6 = z413 != null ? z413.g : null;
                }
                if (c0425n18 == c0425n7) {
                    z8 = z6Arr2[i7];
                }
                if (c0565ro5 == null) {
                }
            }
            i11++;
            c0456o8 = c0456o7;
            i4 = i33;
            i10 = i28;
            i5Arr2 = i5Arr3;
        }
    }

    public static Type i(Type type) {
        if (type instanceof Class) {
            Class cls = (Class) type;
            return cls.isArray() ? new Td(i(cls.getComponentType())) : cls;
        }
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            return new Ud(parameterizedType.getOwnerType(), (Class) parameterizedType.getRawType(), parameterizedType.getActualTypeArguments());
        }
        if (type instanceof GenericArrayType) {
            return new Td(((GenericArrayType) type).getGenericComponentType());
        }
        if (!(type instanceof WildcardType)) {
            return type;
        }
        WildcardType wildcardType = (WildcardType) type;
        return new Vd(wildcardType.getUpperBounds(), wildcardType.getLowerBounds());
    }

    public static void j(Context context, AttributeSet attributeSet, int i2, int i3) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Lk.H, i2, i3);
        boolean z = typedArrayObtainStyledAttributes.getBoolean(1, false);
        typedArrayObtainStyledAttributes.recycle();
        if (z) {
            TypedValue typedValue = new TypedValue();
            if (!context.getTheme().resolveAttribute(moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f040259, typedValue, true) || (typedValue.type == 18 && typedValue.data == 0)) {
                q(context, p, "Theme.MaterialComponents");
            }
        }
        q(context, o, "Theme.AppCompat");
    }

    public static void k(Type type) {
        AbstractC0744xh.g(((type instanceof Class) && ((Class) type).isPrimitive()) ? false : true);
    }

    public static void l(String str) {
        if (str.length() <= 10000) {
            return;
        }
        throw new NumberFormatException("Number string too large: " + str.substring(0, 30) + "...");
    }

    public static final void m(int i2) {
        if (i2 < 1) {
            throw new IllegalArgumentException(AbstractC0535qo.b(i2, "Expected positive parallelism level, but got ").toString());
        }
    }

    public static int n(int i2, String str) {
        return Build.VERSION.SDK_INT != 30 ? AbstractC0134dn.c.a().checkUidPermission(str, i2) : AbstractC0134dn.d.a().checkUidPermission(str, i2);
    }

    public static void o(int i2, int i3, int i4) {
        if (i2 < 0 || i3 > i4) {
            throw new IndexOutOfBoundsException("fromIndex: " + i2 + ", toIndex: " + i3 + ", size: " + i4);
        }
        if (i2 <= i3) {
            return;
        }
        throw new IllegalArgumentException("fromIndex: " + i2 + " > toIndex: " + i3);
    }

    public static void p(Context context, AttributeSet attributeSet, int[] iArr, int i2, int i3, int... iArr2) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Lk.H, i2, i3);
        boolean z = false;
        if (!typedArrayObtainStyledAttributes.getBoolean(2, false)) {
            typedArrayObtainStyledAttributes.recycle();
            return;
        }
        if (iArr2.length != 0) {
            TypedArray typedArrayObtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, iArr, i2, i3);
            int length = iArr2.length;
            int i4 = 0;
            while (true) {
                if (i4 >= length) {
                    typedArrayObtainStyledAttributes2.recycle();
                    z = true;
                    break;
                } else {
                    if (typedArrayObtainStyledAttributes2.getResourceId(iArr2[i4], -1) == -1) {
                        typedArrayObtainStyledAttributes2.recycle();
                        break;
                    }
                    i4++;
                }
            }
        } else if (typedArrayObtainStyledAttributes.getResourceId(0, -1) != -1) {
            z = true;
            break;
        }
        typedArrayObtainStyledAttributes.recycle();
        if (!z) {
            throw new IllegalArgumentException("This component requires that you specify a valid TextAppearance attribute. Update your app theme to inherit from Theme.MaterialComponents (or a descendant).");
        }
    }

    public static void q(Context context, int[] iArr, String str) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(iArr);
        for (int i2 = 0; i2 < iArr.length; i2++) {
            if (!typedArrayObtainStyledAttributes.hasValue(i2)) {
                typedArrayObtainStyledAttributes.recycle();
                throw new IllegalArgumentException(AbstractC0535qo.d("The style on this component requires your app theme to be ", str, " (or a descendant)."));
            }
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    public static C0337kc r(Context context) {
        ProviderInfo providerInfo;
        C0277ic c0277ic;
        ApplicationInfo applicationInfo;
        Ge c0120d9 = Build.VERSION.SDK_INT >= 28 ? new C0120d9() : new Ge();
        PackageManager packageManager = context.getPackageManager();
        AbstractC0046as.j("Package manager required to locate emoji font provider", packageManager);
        Iterator<ResolveInfo> it = packageManager.queryIntentContentProviders(new Intent("androidx.content.action.LOAD_EMOJI_FONT"), 0).iterator();
        while (true) {
            if (!it.hasNext()) {
                providerInfo = null;
                break;
            }
            providerInfo = it.next().providerInfo;
            if (providerInfo != null && (applicationInfo = providerInfo.applicationInfo) != null && (applicationInfo.flags & 1) == 1) {
                break;
            }
        }
        if (providerInfo == null) {
            c0277ic = null;
        } else {
            try {
                String str = providerInfo.authority;
                String str2 = providerInfo.packageName;
                Signature[] signatureArrA = c0120d9.a(packageManager, str2);
                ArrayList arrayList = new ArrayList();
                for (Signature signature : signatureArrA) {
                    arrayList.add(signature.toByteArray());
                }
                c0277ic = new C0277ic(str, str2, "emojicompat-emoji-font", Collections.singletonList(arrayList));
            } catch (PackageManager.NameNotFoundException e2) {
                Log.wtf("emoji2.text.DefaultEmojiConfig", e2);
                c0277ic = null;
            }
        }
        if (c0277ic == null) {
            return null;
        }
        return new C0337kc(new C0306jc(context, c0277ic));
    }

    public static boolean s(Type type, Type type2) {
        if (type == type2) {
            return true;
        }
        if (type instanceof Class) {
            return type.equals(type2);
        }
        if (type instanceof ParameterizedType) {
            if (!(type2 instanceof ParameterizedType)) {
                return false;
            }
            ParameterizedType parameterizedType = (ParameterizedType) type;
            ParameterizedType parameterizedType2 = (ParameterizedType) type2;
            return Objects.equals(parameterizedType.getOwnerType(), parameterizedType2.getOwnerType()) && parameterizedType.getRawType().equals(parameterizedType2.getRawType()) && Arrays.equals(parameterizedType.getActualTypeArguments(), parameterizedType2.getActualTypeArguments());
        }
        if (type instanceof GenericArrayType) {
            if (type2 instanceof GenericArrayType) {
                return s(((GenericArrayType) type).getGenericComponentType(), ((GenericArrayType) type2).getGenericComponentType());
            }
            return false;
        }
        if (type instanceof WildcardType) {
            if (!(type2 instanceof WildcardType)) {
                return false;
            }
            WildcardType wildcardType = (WildcardType) type;
            WildcardType wildcardType2 = (WildcardType) type2;
            return Arrays.equals(wildcardType.getUpperBounds(), wildcardType2.getUpperBounds()) && Arrays.equals(wildcardType.getLowerBounds(), wildcardType2.getLowerBounds());
        }
        if (!(type instanceof TypeVariable) || !(type2 instanceof TypeVariable)) {
            return false;
        }
        TypeVariable typeVariable = (TypeVariable) type;
        TypeVariable typeVariable2 = (TypeVariable) type2;
        return Objects.equals(typeVariable.getGenericDeclaration(), typeVariable2.getGenericDeclaration()) && typeVariable.getName().equals(typeVariable2.getName());
    }

    public static void t(BorderRecyclerView borderRecyclerView) {
        Dj dj = new Dj();
        dj.a = true;
        borderRecyclerView.addOnLayoutChangeListener(dj);
        borderRecyclerView.setTag(moe.shizuku.privileged.api.R.id._0_resource_name_obfuscated_res_0x7f0901c3, dj);
        int i2 = Build.VERSION.SDK_INT;
        if (i2 < 31) {
            if (i2 != 30 || Build.VERSION.PREVIEW_SDK_INT <= 0) {
                borderRecyclerView.G = !borderRecyclerView.h ? new N1(0) : new Pe(3);
                borderRecyclerView.K = null;
                borderRecyclerView.I = null;
                borderRecyclerView.J = null;
                borderRecyclerView.H = null;
            }
        }
    }

    public static final V7 u(V7 v7, V7 v8, boolean z) {
        Boolean bool = Boolean.FALSE;
        boolean zBooleanValue = ((Boolean) v7.l(bool, new F6(4))).booleanValue();
        boolean zBooleanValue2 = ((Boolean) v8.l(bool, new F6(4))).booleanValue();
        if (!zBooleanValue && !zBooleanValue2) {
            return v7.b(v8);
        }
        C0152eb c0152eb = C0152eb.a;
        V7 v9 = (V7) v7.l(c0152eb, new F6(2));
        Object objL = v8;
        if (zBooleanValue2) {
            objL = v8.l(c0152eb, new F6(3));
        }
        return v9.b((V7) objL);
    }

    public static void v(int i2, String str) {
        try {
            AbstractC0134dn.a.a().forceStopPackage(str, i2);
        } catch (Exception unused) {
        }
    }

    public static ColorStateList w(Context context, TypedArray typedArray, int i2) {
        int resourceId;
        ColorStateList colorStateListO;
        return (!typedArray.hasValue(i2) || (resourceId = typedArray.getResourceId(i2, 0)) == 0 || (colorStateListO = AbstractC0599sr.o(context, resourceId)) == null) ? typedArray.getColorStateList(i2) : colorStateListO;
    }

    public static ColorStateList x(Context context, K3 k3, int i2) {
        int resourceId;
        ColorStateList colorStateListO;
        TypedArray typedArray = (TypedArray) k3.a;
        return (!typedArray.hasValue(i2) || (resourceId = typedArray.getResourceId(i2, 0)) == 0 || (colorStateListO = AbstractC0599sr.o(context, resourceId)) == null) ? k3.h(i2) : colorStateListO;
    }

    public static IContentProvider y(int i2, String str, String str2) {
        IActivityManager iActivityManagerA = AbstractC0134dn.a.a();
        int i3 = Build.VERSION.SDK_INT;
        if (i3 >= 29) {
            ContentProviderHolder contentProviderExternal = iActivityManagerA.getContentProviderExternal(str, i2, (IBinder) null, str2);
            if (contentProviderExternal != null) {
                return contentProviderExternal.provider;
            }
        } else {
            if (i3 < 26) {
                return iActivityManagerA.getContentProviderExternal(str, i2, (IBinder) null).provider;
            }
            ContentProviderHolder contentProviderExternal2 = iActivityManagerA.getContentProviderExternal(str, i2, (IBinder) null);
            if (contentProviderExternal2 != null) {
                return contentProviderExternal2.provider;
            }
        }
        return null;
    }

    public static Drawable z(Context context, TypedArray typedArray, int i2) {
        int resourceId;
        Drawable drawableV;
        return (!typedArray.hasValue(i2) || (resourceId = typedArray.getResourceId(i2, 0)) == 0 || (drawableV = AbstractC0744xh.v(context, resourceId)) == null) ? typedArray.getDrawable(i2) : drawableV;
    }

    public abstract void K(int i2);

    public abstract void L(Typeface typeface);

    public void h(int i2) {
        new Handler(Looper.getMainLooper()).post(new Wl(i2, 0, this));
    }
}
