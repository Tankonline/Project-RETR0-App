package rikka.shizuku;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.fonts.FontVariationAxis;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: renamed from: rikka.shizuku.xr, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0754xr extends C0692vr {
    public final Class g;
    public final Constructor h;
    public final Method i;
    public final Method j;
    public final Method k;
    public final Method l;
    public final Method m;

    public C0754xr() throws NoSuchMethodException {
        Method methodK;
        Constructor<?> constructor;
        Method methodJ;
        Method method;
        Method method2;
        Method method3;
        Class<?> cls = null;
        try {
            Class<?> cls2 = Class.forName("android.graphics.FontFamily");
            constructor = cls2.getConstructor(null);
            methodJ = J(cls2);
            Class cls3 = Integer.TYPE;
            method = cls2.getMethod("addFontFromBuffer", ByteBuffer.class, cls3, FontVariationAxis[].class, cls3, cls3);
            method2 = cls2.getMethod("freeze", null);
            method3 = cls2.getMethod("abortCreation", null);
            methodK = K(cls2);
            cls = cls2;
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            Log.e("TypefaceCompatApi26Impl", "Unable to collect necessary methods for class ".concat(e.getClass().getName()), e);
            methodK = null;
            constructor = null;
            methodJ = null;
            method = null;
            method2 = null;
            method3 = null;
        }
        this.g = cls;
        this.h = constructor;
        this.i = methodJ;
        this.j = method;
        this.k = method2;
        this.l = method3;
        this.m = methodK;
    }

    public static Method J(Class cls) {
        Class cls2 = Integer.TYPE;
        return cls.getMethod("addFontFromAssetManager", AssetManager.class, String.class, cls2, Boolean.TYPE, cls2, cls2, cls2, FontVariationAxis[].class);
    }

    public final void E(Object obj) {
        try {
            this.l.invoke(obj, null);
        } catch (IllegalAccessException | InvocationTargetException unused) {
        }
    }

    public final boolean F(Context context, Object obj, String str, int i, int i2, int i3, FontVariationAxis[] fontVariationAxisArr) {
        try {
            return ((Boolean) this.i.invoke(obj, context.getAssets(), str, 0, Boolean.FALSE, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), fontVariationAxisArr)).booleanValue();
        } catch (IllegalAccessException | InvocationTargetException unused) {
            return false;
        }
    }

    public Typeface G(Object obj) {
        try {
            Object objNewInstance = Array.newInstance((Class<?>) this.g, 1);
            Array.set(objNewInstance, 0, obj);
            return (Typeface) this.m.invoke(null, objNewInstance, -1, -1);
        } catch (IllegalAccessException | InvocationTargetException unused) {
            return null;
        }
    }

    public final boolean H(Object obj) {
        try {
            return ((Boolean) this.k.invoke(obj, null)).booleanValue();
        } catch (IllegalAccessException | InvocationTargetException unused) {
            return false;
        }
    }

    public final Object I() {
        try {
            return this.h.newInstance(null);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException unused) {
            return null;
        }
    }

    public Method K(Class cls) throws NoSuchMethodException {
        Class<?> cls2 = Array.newInstance((Class<?>) cls, 1).getClass();
        Class cls3 = Integer.TYPE;
        Method declaredMethod = Typeface.class.getDeclaredMethod("createFromFamiliesWithDefault", cls2, cls3, cls3);
        declaredMethod.setAccessible(true);
        return declaredMethod;
    }

    @Override // rikka.shizuku.C0692vr, rikka.shizuku.AbstractC0688vn
    public final Typeface f(Context context, C0523qc c0523qc, Resources resources, int i) {
        Method method = this.i;
        if (method == null) {
            Log.w("TypefaceCompatApi26Impl", "Unable to collect necessary private methods. Fallback to legacy implementation.");
        }
        if (method == null) {
            return super.f(context, c0523qc, resources, i);
        }
        Object objI = I();
        if (objI != null) {
            C0553rc[] c0553rcArr = c0523qc.a;
            int length = c0553rcArr.length;
            int i2 = 0;
            while (i2 < length) {
                C0553rc c0553rc = c0553rcArr[i2];
                String str = c0553rc.a;
                FontVariationAxis[] fontVariationAxisArrFromFontVariationSettings = FontVariationAxis.fromFontVariationSettings(c0553rc.d);
                Context context2 = context;
                if (!F(context2, objI, str, c0553rc.e, c0553rc.b, c0553rc.c ? 1 : 0, fontVariationAxisArrFromFontVariationSettings)) {
                    E(objI);
                    return null;
                }
                i2++;
                context = context2;
            }
            if (H(objI)) {
                return G(objI);
            }
        }
        return null;
    }

    @Override // rikka.shizuku.C0692vr, rikka.shizuku.AbstractC0688vn
    public final Typeface g(Context context, C0615tc[] c0615tcArr, int i) {
        Typeface typefaceG;
        boolean zBooleanValue;
        if (c0615tcArr.length >= 1) {
            Method method = this.i;
            if (method == null) {
                Log.w("TypefaceCompatApi26Impl", "Unable to collect necessary private methods. Fallback to legacy implementation.");
            }
            if (method != null) {
                HashMap map = new HashMap();
                for (C0615tc c0615tc : c0615tcArr) {
                    if (c0615tc.e == 0) {
                        Uri uri = c0615tc.a;
                        if (!map.containsKey(uri)) {
                            map.put(uri, AbstractC0750xn.n(context, uri));
                        }
                    }
                }
                Map mapUnmodifiableMap = Collections.unmodifiableMap(map);
                Object objI = I();
                if (objI != null) {
                    int length = c0615tcArr.length;
                    int i2 = 0;
                    boolean z = false;
                    while (i2 < length) {
                        C0615tc c0615tc2 = c0615tcArr[i2];
                        ByteBuffer byteBuffer = (ByteBuffer) mapUnmodifiableMap.get(c0615tc2.a);
                        if (byteBuffer != null) {
                            try {
                                zBooleanValue = ((Boolean) this.j.invoke(objI, byteBuffer, Integer.valueOf(c0615tc2.b), null, Integer.valueOf(c0615tc2.c), Integer.valueOf(c0615tc2.d ? 1 : 0))).booleanValue();
                            } catch (IllegalAccessException | InvocationTargetException unused) {
                                zBooleanValue = false;
                            }
                            if (!zBooleanValue) {
                                E(objI);
                                return null;
                            }
                            z = true;
                        }
                        i2++;
                        z = z;
                    }
                    if (!z) {
                        E(objI);
                        return null;
                    }
                    if (H(objI) && (typefaceG = G(objI)) != null) {
                        return Typeface.create(typefaceG, i);
                    }
                }
            } else {
                C0615tc c0615tcK = k(c0615tcArr, i);
                try {
                    ParcelFileDescriptor parcelFileDescriptorOpenFileDescriptor = context.getContentResolver().openFileDescriptor(c0615tcK.a, "r", null);
                    if (parcelFileDescriptorOpenFileDescriptor != null) {
                        try {
                            Typeface typefaceBuild = new Typeface.Builder(parcelFileDescriptorOpenFileDescriptor.getFileDescriptor()).setWeight(c0615tcK.c).setItalic(c0615tcK.d).build();
                            parcelFileDescriptorOpenFileDescriptor.close();
                            return typefaceBuild;
                        } catch (Throwable th) {
                            try {
                                parcelFileDescriptorOpenFileDescriptor.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                            throw th;
                        }
                    }
                    if (parcelFileDescriptorOpenFileDescriptor != null) {
                        parcelFileDescriptorOpenFileDescriptor.close();
                        return null;
                    }
                } catch (IOException unused2) {
                }
            }
        }
        return null;
    }

    @Override // rikka.shizuku.AbstractC0688vn
    public final Typeface j(Context context, Resources resources, int i, String str, int i2) {
        Method method = this.i;
        if (method == null) {
            Log.w("TypefaceCompatApi26Impl", "Unable to collect necessary private methods. Fallback to legacy implementation.");
        }
        if (method == null) {
            return super.j(context, resources, i, str, i2);
        }
        Object objI = I();
        if (objI != null) {
            if (!F(context, objI, str, 0, -1, -1, null)) {
                E(objI);
                return null;
            }
            if (H(objI)) {
                return G(objI);
            }
        }
        return null;
    }
}
