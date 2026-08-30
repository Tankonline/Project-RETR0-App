package rikka.shizuku;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.util.StateSet;
import android.util.TypedValue;
import android.util.Xml;
import java.lang.reflect.Array;
import moe.shizuku.privileged.api.R;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes.dex */
public abstract class D6 {
    public static final ThreadLocal a = new ThreadLocal();

    public static ColorStateList a(Resources resources, XmlResourceParser xmlResourceParser, Resources.Theme theme) {
        int next;
        AttributeSet attributeSetAsAttributeSet = Xml.asAttributeSet(xmlResourceParser);
        do {
            next = xmlResourceParser.next();
            if (next == 2) {
                break;
            }
        } while (next != 1);
        if (next == 2) {
            return b(resources, xmlResourceParser, attributeSetAsAttributeSet, theme);
        }
        throw new XmlPullParserException("No start tag found");
    }

    /* JADX WARN: Code duplicated, block: B:33:0x0092  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v2, types: [android.content.res.Resources] */
    /* JADX WARN: Type inference failed for: r16v0 */
    /* JADX WARN: Type inference failed for: r16v1 */
    /* JADX WARN: Type inference failed for: r36v0, types: [android.content.res.Resources] */
    /* JADX WARN: Type inference failed for: r4v2 */
    /* JADX WARN: Type inference failed for: r4v3, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r4v4 */
    /* JADX WARN: Type inference failed for: r4v5 */
    /* JADX WARN: Type inference failed for: r4v9 */
    /* JADX WARN: Type inference failed for: r9v18 */
    /* JADX WARN: Type inference failed for: r9v19 */
    /* JADX WARN: Type inference failed for: r9v5, types: [android.content.res.TypedArray] */
    public static ColorStateList b(Resources resources, XmlResourceParser xmlResourceParser, AttributeSet attributeSet, Resources.Theme theme) {
        int depth;
        int color;
        float f;
        int iW;
        TypedValue typedValue;
        resources = resources;
        attributeSet = attributeSet;
        theme = theme;
        String name = xmlResourceParser.getName();
        if (!name.equals("selector")) {
            throw new XmlPullParserException(xmlResourceParser.getPositionDescription() + ": invalid color state list tag " + name);
        }
        ?? r4 = 1;
        int depth2 = xmlResourceParser.getDepth() + 1;
        Object[] objArr = new int[20][];
        int[] iArr = new int[20];
        int i = 0;
        int i2 = 0;
        while (true) {
            int next = xmlResourceParser.next();
            if (next == r4 || ((depth = xmlResourceParser.getDepth()) < depth2 && next == 3)) {
                break;
            }
            if (next == 2 && depth <= depth2 && xmlResourceParser.getName().equals("item")) {
                int[] iArr2 = Hk.a;
                ?? ObtainAttributes = theme == null ? resources.obtainAttributes(attributeSet, iArr2) : theme.obtainStyledAttributes(attributeSet, iArr2, i, i);
                int resourceId = ObtainAttributes.getResourceId(i, -1);
                if (resourceId != -1) {
                    ThreadLocal threadLocal = a;
                    TypedValue typedValue2 = (TypedValue) threadLocal.get();
                    if (typedValue2 == null) {
                        typedValue = new TypedValue();
                        threadLocal.set(typedValue);
                    } else {
                        typedValue = typedValue2;
                    }
                    resources.getValue(resourceId, typedValue, r4);
                    int i3 = typedValue.type;
                    if (i3 < 28 || i3 > 31) {
                        try {
                            color = a(resources, resources.getXml(resourceId), theme).getDefaultColor();
                        } catch (Exception unused) {
                            color = ObtainAttributes.getColor(i, -65281);
                        }
                    } else {
                        color = ObtainAttributes.getColor(i, -65281);
                    }
                } else {
                    color = ObtainAttributes.getColor(i, -65281);
                }
                if (ObtainAttributes.hasValue(r4)) {
                    f = ObtainAttributes.getFloat(r4, 1.0f);
                } else {
                    f = ObtainAttributes.hasValue(3) ? ObtainAttributes.getFloat(3, 1.0f) : 1.0f;
                }
                ?? r16 = r4;
                float f2 = (Build.VERSION.SDK_INT < 31 || !ObtainAttributes.hasValue(2)) ? ObtainAttributes.getFloat(4, -1.0f) : ObtainAttributes.getFloat(2, -1.0f);
                ObtainAttributes.recycle();
                int attributeCount = attributeSet.getAttributeCount();
                int[] iArr3 = new int[attributeCount];
                int i4 = i;
                int i5 = i4;
                while (i4 < attributeCount) {
                    int attributeNameResource = attributeSet.getAttributeNameResource(i4);
                    if (attributeNameResource != 16843173 && attributeNameResource != 16843551 && attributeNameResource != R.attr._0_resource_name_obfuscated_res_0x7f040032 && attributeNameResource != R.attr._0_resource_name_obfuscated_res_0x7f04027d) {
                        int i6 = i5 + 1;
                        if (!attributeSet.getAttributeBooleanValue(i4, false)) {
                            attributeNameResource = -attributeNameResource;
                        }
                        iArr3[i5] = attributeNameResource;
                        i5 = i6;
                    }
                    i4++;
                }
                int[] iArrTrimStateSet = StateSet.trimStateSet(iArr3, i5);
                float f3 = 100.0f;
                boolean z = (f2 < 0.0f || f2 > 100.0f) ? false : r16 == true ? 1 : 0;
                if (f != 1.0f || z) {
                    int i7 = AbstractC0744xh.i((int) ((Color.alpha(color) * f) + 0.5f), 0, 255);
                    if (z) {
                        C0701w5 c0701w5A = C0701w5.a(color);
                        C0540qt c0540qt = C0540qt.k;
                        float f4 = c0701w5A.b;
                        if (f4 >= 1.0d && Math.round(f2) > 0.0d && Math.round(f2) < 100.0d) {
                            float f5 = c0701w5A.a;
                            float fMin = f5 < 0.0f ? 0.0f : Math.min(360.0f, f5);
                            float f6 = 0.0f;
                            float f7 = f4;
                            boolean z2 = r16 == true ? 1 : 0;
                            C0701w5 c0701w5 = null;
                            while (true) {
                                if (Math.abs(f6 - f4) < 0.4f) {
                                    iArrTrimStateSet = iArrTrimStateSet;
                                    depth2 = depth2;
                                    if (c0701w5 != null) {
                                        iW = c0701w5.c(c0540qt);
                                        break;
                                    }
                                    iW = AbstractC0599sr.w(f2);
                                    break;
                                }
                                float f8 = 1000.0f;
                                float f9 = f3;
                                float f10 = 0.0f;
                                float f11 = 1000.0f;
                                C0701w5 c0701w6 = null;
                                while (true) {
                                    if (Math.abs(f10 - f9) <= 0.01f) {
                                        iArrTrimStateSet = iArrTrimStateSet;
                                        depth2 = depth2;
                                        f3 = f3;
                                        break;
                                    }
                                    f3 = f3;
                                    float f12 = ((f9 - f10) / 2.0f) + f10;
                                    iArrTrimStateSet = iArrTrimStateSet;
                                    int iC = C0701w5.b(f12, f7, fMin).c(C0540qt.k);
                                    float fA = AbstractC0599sr.A(Color.red(iC));
                                    float fA2 = AbstractC0599sr.A(Color.green(iC));
                                    float fA3 = AbstractC0599sr.A(Color.blue(iC));
                                    float[] fArr = AbstractC0599sr.d[r16 == true ? 1 : 0];
                                    float f13 = ((fA3 * fArr[2]) + ((fA2 * fArr[r16 == true ? 1 : 0]) + (fA * fArr[0]))) / f3;
                                    float fCbrt = f13 <= 0.008856452f ? f13 * 903.2963f : (((float) Math.cbrt(f13)) * 116.0f) - 16.0f;
                                    float fAbs = Math.abs(f2 - fCbrt);
                                    if (fAbs < 0.2f) {
                                        C0701w5 c0701w5A2 = C0701w5.a(iC);
                                        C0701w5 c0701w5B = C0701w5.b(c0701w5A2.c, c0701w5A2.b, fMin);
                                        float f14 = c0701w5A2.d - c0701w5B.d;
                                        float f15 = c0701w5A2.e - c0701w5B.e;
                                        float f16 = c0701w5A2.f - c0701w5B.f;
                                        depth2 = depth2;
                                        float fPow = (float) (Math.pow(Math.sqrt((f16 * f16) + (f15 * f15) + (f14 * f14)), 0.63d) * 1.41d);
                                        if (fPow <= 1.0f) {
                                            f11 = fPow;
                                            f8 = fAbs;
                                            c0701w6 = c0701w5A2;
                                        }
                                    } else {
                                        depth2 = depth2;
                                    }
                                    if (f8 == 0.0f && f11 == 0.0f) {
                                        break;
                                    }
                                    if (fCbrt < f2) {
                                        f10 = f12;
                                    } else {
                                        f9 = f12;
                                    }
                                    f3 = f3;
                                    iArrTrimStateSet = iArrTrimStateSet;
                                    depth2 = depth2;
                                }
                                C0701w5 c0701w7 = c0701w6;
                                if (!z2) {
                                    if (c0701w7 == null) {
                                        f4 = f7;
                                    } else {
                                        c0701w5 = c0701w7;
                                        f6 = f7;
                                    }
                                    f7 = ((f4 - f6) / 2.0f) + f6;
                                } else {
                                    if (c0701w7 != null) {
                                        iW = c0701w7.c(c0540qt);
                                        break;
                                    }
                                    f7 = ((f4 - f6) / 2.0f) + f6;
                                    z2 = false;
                                }
                            }
                        } else {
                            iArrTrimStateSet = iArrTrimStateSet;
                            depth2 = depth2;
                            iW = AbstractC0599sr.w(f2);
                        }
                        color = iW;
                    } else {
                        iArrTrimStateSet = iArrTrimStateSet;
                        depth2 = depth2;
                    }
                    color = (16777215 & color) | (i7 << 24);
                } else {
                    iArrTrimStateSet = iArrTrimStateSet;
                    depth2 = depth2;
                }
                int i8 = i2 + 1;
                if (i8 > iArr.length) {
                    int[] iArr4 = new int[i2 <= 4 ? 8 : i2 * 2];
                    System.arraycopy(iArr, 0, iArr4, 0, i2);
                    iArr = iArr4;
                }
                iArr[i2] = color;
                if (i8 > objArr.length) {
                    Object[] objArr2 = (Object[]) Array.newInstance(objArr.getClass().getComponentType(), i2 > 4 ? i2 * 2 : 8);
                    System.arraycopy(objArr, 0, objArr2, 0, i2);
                    objArr = objArr2;
                }
                objArr[i2] = iArrTrimStateSet;
                objArr = (int[][]) objArr;
                i2 = i8;
                r4 = r16 == true ? 1 : 0;
                depth2 = depth2;
                i = 0;
            } else {
                int i9 = depth2;
                r4 = r4 == true ? 1 : 0;
                depth2 = i9;
                i = 0;
            }
        }
        int[] iArr5 = new int[i2];
        int[][] iArr6 = new int[i2][];
        System.arraycopy(iArr, 0, iArr5, 0, i2);
        System.arraycopy(objArr, 0, iArr6, 0, i2);
        return new ColorStateList(iArr6, iArr5);
    }
}
