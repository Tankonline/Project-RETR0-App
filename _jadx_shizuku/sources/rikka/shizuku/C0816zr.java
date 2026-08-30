package rikka.shizuku;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.graphics.fonts.FontFamily;
import android.graphics.fonts.FontStyle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/* JADX INFO: renamed from: rikka.shizuku.zr, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0816zr extends AbstractC0688vn {
    public static Font C(FontFamily fontFamily, int i) {
        FontStyle fontStyle = new FontStyle((i & 1) != 0 ? 700 : 400, (i & 2) != 0 ? 1 : 0);
        Font font = fontFamily.getFont(0);
        int iE = E(fontStyle, font.getStyle());
        for (int i2 = 1; i2 < fontFamily.getSize(); i2++) {
            Font font2 = fontFamily.getFont(i2);
            int iE2 = E(fontStyle, font2.getStyle());
            if (iE2 < iE) {
                font = font2;
                iE = iE2;
            }
        }
        return font;
    }

    public static FontFamily D(C0615tc[] c0615tcArr, ContentResolver contentResolver) {
        FontFamily.Builder builder = null;
        for (C0615tc c0615tc : c0615tcArr) {
            try {
                ParcelFileDescriptor parcelFileDescriptorOpenFileDescriptor = contentResolver.openFileDescriptor(c0615tc.a, "r", null);
                if (parcelFileDescriptorOpenFileDescriptor == null) {
                    if (parcelFileDescriptorOpenFileDescriptor != null) {
                    }
                } else {
                    try {
                        Font fontBuild = new Font.Builder(parcelFileDescriptorOpenFileDescriptor).setWeight(c0615tc.c).setSlant(c0615tc.d ? 1 : 0).setTtcIndex(c0615tc.b).build();
                        if (builder == null) {
                            builder = new FontFamily.Builder(fontBuild);
                        } else {
                            builder.addFont(fontBuild);
                        }
                    } catch (Throwable th) {
                        try {
                            parcelFileDescriptorOpenFileDescriptor.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                }
                parcelFileDescriptorOpenFileDescriptor.close();
            } catch (IOException e) {
                Log.w("TypefaceCompatApi29Impl", "Font load failed", e);
            }
        }
        if (builder == null) {
            return null;
        }
        return builder.build();
    }

    public static int E(FontStyle fontStyle, FontStyle fontStyle2) {
        return (Math.abs(fontStyle.getWeight() - fontStyle2.getWeight()) / 100) + (fontStyle.getSlant() == fontStyle2.getSlant() ? 0 : 2);
    }

    @Override // rikka.shizuku.AbstractC0688vn
    public final Typeface f(Context context, C0523qc c0523qc, Resources resources, int i) {
        try {
            FontFamily.Builder builder = null;
            for (C0553rc c0553rc : c0523qc.a) {
                try {
                    Font fontBuild = new Font.Builder(resources, c0553rc.f).setWeight(c0553rc.b).setSlant(c0553rc.c ? 1 : 0).setTtcIndex(c0553rc.e).setFontVariationSettings(c0553rc.d).build();
                    if (builder == null) {
                        builder = new FontFamily.Builder(fontBuild);
                    } else {
                        builder.addFont(fontBuild);
                    }
                } catch (IOException unused) {
                }
            }
            if (builder == null) {
                return null;
            }
            FontFamily fontFamilyBuild = builder.build();
            return new Typeface.CustomFallbackBuilder(fontFamilyBuild).setStyle(C(fontFamilyBuild, i).getStyle()).build();
        } catch (Exception e) {
            Log.w("TypefaceCompatApi29Impl", "Font load failed", e);
            return null;
        }
    }

    @Override // rikka.shizuku.AbstractC0688vn
    public final Typeface g(Context context, C0615tc[] c0615tcArr, int i) {
        try {
            FontFamily fontFamilyD = D(c0615tcArr, context.getContentResolver());
            if (fontFamilyD == null) {
                return null;
            }
            return new Typeface.CustomFallbackBuilder(fontFamilyD).setStyle(C(fontFamilyD, i).getStyle()).build();
        } catch (Exception e) {
            Log.w("TypefaceCompatApi29Impl", "Font load failed", e);
            return null;
        }
    }

    @Override // rikka.shizuku.AbstractC0688vn
    public final Typeface h(Context context, List list, int i) {
        ContentResolver contentResolver = context.getContentResolver();
        try {
            FontFamily fontFamilyD = D((C0615tc[]) list.get(0), contentResolver);
            if (fontFamilyD == null) {
                return null;
            }
            Typeface.CustomFallbackBuilder customFallbackBuilder = new Typeface.CustomFallbackBuilder(fontFamilyD);
            for (int i2 = 1; i2 < list.size(); i2++) {
                FontFamily fontFamilyD2 = D((C0615tc[]) list.get(i2), contentResolver);
                if (fontFamilyD2 != null) {
                    customFallbackBuilder.addCustomFallback(fontFamilyD2);
                }
            }
            return customFallbackBuilder.setStyle(C(fontFamilyD, i).getStyle()).build();
        } catch (Exception e) {
            Log.w("TypefaceCompatApi29Impl", "Font load failed", e);
            return null;
        }
    }

    @Override // rikka.shizuku.AbstractC0688vn
    public final Typeface i(Context context, InputStream inputStream) {
        throw new RuntimeException("Do not use this function in API 29 or later.");
    }

    @Override // rikka.shizuku.AbstractC0688vn
    public final Typeface j(Context context, Resources resources, int i, String str, int i2) {
        try {
            Font fontBuild = new Font.Builder(resources, i).build();
            return new Typeface.CustomFallbackBuilder(new FontFamily.Builder(fontBuild).build()).setStyle(fontBuild.getStyle()).build();
        } catch (Exception e) {
            Log.w("TypefaceCompatApi29Impl", "Font load failed", e);
            return null;
        }
    }

    @Override // rikka.shizuku.AbstractC0688vn
    public final C0615tc k(C0615tc[] c0615tcArr, int i) {
        throw new RuntimeException("Do not use this function in API 29 or later.");
    }
}
