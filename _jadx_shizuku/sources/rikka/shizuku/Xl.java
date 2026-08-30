package rikka.shizuku;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.TypedValue;
import java.io.IOException;
import java.util.WeakHashMap;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes.dex */
public abstract class Xl {
    public static final ThreadLocal a = new ThreadLocal();
    public static final WeakHashMap b = new WeakHashMap(0);
    public static final Object c = new Object();

    public static Typeface a(Context context, int i) {
        if (context.isRestricted()) {
            return null;
        }
        return b(context, i, new TypedValue(), 0, null, false, false);
    }

    /* JADX WARN: Code duplicated, block: B:39:0x00c9  */
    public static Typeface b(Context context, int i, TypedValue typedValue, int i2, X8 x8, boolean z, boolean z2) {
        Resources resources = context.getResources();
        resources.getValue(i, typedValue, true);
        CharSequence charSequence = typedValue.string;
        if (charSequence == null) {
            throw new Resources.NotFoundException("Resource \"" + resources.getResourceName(i) + "\" (" + Integer.toHexString(i) + ") is not a Font: " + typedValue);
        }
        String string = charSequence.toString();
        Typeface typefaceA = null;
        if (string.startsWith("res/")) {
            int i3 = typedValue.assetCookie;
            C0682vh c0682vh = AbstractC0661ur.b;
            Typeface typeface = (Typeface) c0682vh.f(AbstractC0661ur.b(resources, i, string, i3, i2));
            if (typeface != null) {
                if (x8 != null) {
                    new Handler(Looper.getMainLooper()).post(new N3(x8, 3, typeface));
                }
                typefaceA = typeface;
            } else if (!z2) {
                try {
                    if (string.toLowerCase().endsWith(".xml")) {
                        InterfaceC0492pc interfaceC0492pcM = X8.M(resources.getXml(i), resources);
                        if (interfaceC0492pcM == null) {
                            Log.e("ResourcesCompat", "Failed to find font-family tag");
                            if (x8 != null) {
                                x8.h(-3);
                            }
                        } else {
                            typefaceA = AbstractC0661ur.a(context, interfaceC0492pcM, resources, i, string, typedValue.assetCookie, i2, x8, z);
                        }
                    } else {
                        int i4 = typedValue.assetCookie;
                        Typeface typefaceJ = AbstractC0661ur.a.j(context, resources, i, string, i2);
                        if (typefaceJ != null) {
                            c0682vh.j(AbstractC0661ur.b(resources, i, string, i4, i2), typefaceJ);
                        }
                        if (x8 != null) {
                            if (typefaceJ != null) {
                                new Handler(Looper.getMainLooper()).post(new N3(x8, 3, typefaceJ));
                            } else {
                                x8.h(-3);
                            }
                        }
                        typefaceA = typefaceJ;
                    }
                } catch (IOException e) {
                    Log.e("ResourcesCompat", "Failed to read xml resource ".concat(string), e);
                    if (x8 != null) {
                        x8.h(-3);
                    }
                } catch (XmlPullParserException e2) {
                    Log.e("ResourcesCompat", "Failed to parse xml resource ".concat(string), e2);
                    if (x8 != null) {
                        x8.h(-3);
                    }
                }
            }
        } else if (x8 != null) {
            x8.h(-3);
        }
        if (typefaceA != null || x8 != null || z2) {
            return typefaceA;
        }
        throw new Resources.NotFoundException("Font resource ID #0x" + Integer.toHexString(i) + " could not be retrieved.");
    }
}
