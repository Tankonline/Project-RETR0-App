package rikka.shizuku;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;

/* JADX INFO: renamed from: rikka.shizuku.wr, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0723wr extends AbstractC0688vn {
    public static final Class b;
    public static final Constructor c;
    public static final Method d;
    public static final Method e;

    static {
        Class<?> cls;
        Method method;
        Method method2;
        Constructor<?> constructor = null;
        try {
            cls = Class.forName("android.graphics.FontFamily");
            Constructor<?> constructor2 = cls.getConstructor(null);
            Class cls2 = Integer.TYPE;
            method2 = cls.getMethod("addFontWeightStyle", ByteBuffer.class, cls2, List.class, cls2, Boolean.TYPE);
            method = Typeface.class.getMethod("createFromFamiliesWithDefault", Array.newInstance(cls, 1).getClass());
            constructor = constructor2;
        } catch (ClassNotFoundException | NoSuchMethodException e2) {
            Log.e("TypefaceCompatApi24Impl", e2.getClass().getName(), e2);
            cls = null;
            method = null;
            method2 = null;
        }
        c = constructor;
        b = cls;
        d = method2;
        e = method;
    }

    public static boolean C(Object obj, ByteBuffer byteBuffer, int i, int i2, boolean z) {
        try {
            return ((Boolean) d.invoke(obj, byteBuffer, Integer.valueOf(i), null, Integer.valueOf(i2), Boolean.valueOf(z))).booleanValue();
        } catch (IllegalAccessException | InvocationTargetException unused) {
            return false;
        }
    }

    public static Typeface D(Object obj) {
        try {
            Object objNewInstance = Array.newInstance((Class<?>) b, 1);
            Array.set(objNewInstance, 0, obj);
            return (Typeface) e.invoke(null, objNewInstance);
        } catch (IllegalAccessException | InvocationTargetException unused) {
            return null;
        }
    }

    /* JADX WARN: Code duplicated, block: B:33:0x005b  */
    /* JADX WARN: Code duplicated, block: B:36:0x0068 A[LOOP:0: B:9:0x0014->B:36:0x0068, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:54:0x0067 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:55:0x0067 A[SYNTHETIC] */
    @Override // rikka.shizuku.AbstractC0688vn
    public final Typeface f(Context context, C0523qc c0523qc, Resources resources, int i) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Object objNewInstance;
        MappedByteBuffer map;
        try {
            objNewInstance = c.newInstance(null);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException unused) {
            objNewInstance = null;
        }
        if (objNewInstance != null) {
            for (C0553rc c0553rc : c0523qc.a) {
                int i2 = c0553rc.f;
                File fileK = AbstractC0750xn.k(context);
                if (fileK != null) {
                    try {
                        if (AbstractC0750xn.f(fileK, resources, i2)) {
                            try {
                                FileInputStream fileInputStream = new FileInputStream(fileK);
                                try {
                                    FileChannel channel = fileInputStream.getChannel();
                                    map = channel.map(FileChannel.MapMode.READ_ONLY, 0L, channel.size());
                                    fileInputStream.close();
                                    fileK.delete();
                                } catch (Throwable th) {
                                    try {
                                        fileInputStream.close();
                                    } catch (Throwable th2) {
                                        th.addSuppressed(th2);
                                    }
                                    throw th;
                                }
                            } catch (IOException unused2) {
                                map = null;
                            }
                        } else {
                            fileK.delete();
                        }
                        if (map == null) {
                            if (!C(objNewInstance, map, c0553rc.e, c0553rc.b, c0553rc.c)) {
                            }
                        }
                    } catch (Throwable th3) {
                        fileK.delete();
                        throw th3;
                    }
                }
                map = null;
                if (map == null) {
                    if (!C(objNewInstance, map, c0553rc.e, c0553rc.b, c0553rc.c)) {
                    }
                }
            }
            return D(objNewInstance);
        }
        return null;
    }

    @Override // rikka.shizuku.AbstractC0688vn
    public final Typeface g(Context context, C0615tc[] c0615tcArr, int i) {
        Object objNewInstance;
        try {
            objNewInstance = c.newInstance(null);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException unused) {
            objNewInstance = null;
        }
        if (objNewInstance != null) {
            C0104co c0104co = new C0104co(0);
            for (C0615tc c0615tc : c0615tcArr) {
                Uri uri = c0615tc.a;
                ByteBuffer byteBufferN = (ByteBuffer) c0104co.get(uri);
                if (byteBufferN == null) {
                    byteBufferN = AbstractC0750xn.n(context, uri);
                    c0104co.put(uri, byteBufferN);
                }
                if (byteBufferN != null) {
                    if (C(objNewInstance, byteBufferN, c0615tc.b, c0615tc.c, c0615tc.d)) {
                    }
                }
            }
            Typeface typefaceD = D(objNewInstance);
            if (typefaceD != null) {
                return Typeface.create(typefaceD, i);
            }
        }
        return null;
    }
}
