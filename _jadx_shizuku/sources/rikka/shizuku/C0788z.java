package rikka.shizuku;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.LinearGradient;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.Xml;
import java.io.IOException;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: rikka.shizuku.z, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0788z {
    public int a;
    public Object b;
    public Object c;

    public C0788z(Cg cg, int i, byte[][] bArr) {
        this.b = cg;
        this.a = i;
        this.c = bArr;
    }

    public static C0788z a(Resources resources, int i, Resources.Theme theme) throws XmlPullParserException, IOException {
        int next;
        float f;
        float f2;
        Shader.TileMode tileMode;
        Shader radialGradient;
        Shader.TileMode tileMode2;
        XmlResourceParser xml = resources.getXml(i);
        AttributeSet attributeSetAsAttributeSet = Xml.asAttributeSet(xml);
        do {
            next = xml.next();
            if (next == 2) {
                break;
            }
        } while (next != 1);
        if (next != 2) {
            throw new XmlPullParserException("No start tag found");
        }
        String name = xml.getName();
        name.getClass();
        if (!name.equals("gradient")) {
            if (name.equals("selector")) {
                ColorStateList colorStateListB = D6.b(resources, xml, attributeSetAsAttributeSet, theme);
                return new C0788z((Shader) null, colorStateListB, colorStateListB.getDefaultColor());
            }
            throw new XmlPullParserException(xml.getPositionDescription() + ": unsupported complex color tag " + name);
        }
        String name2 = xml.getName();
        if (!name2.equals("gradient")) {
            throw new XmlPullParserException(xml.getPositionDescription() + ": invalid gradient color tag " + name2);
        }
        TypedArray typedArrayJ = Hn.j(resources, theme, attributeSetAsAttributeSet, Hk.d);
        float f3 = !(xml.getAttributeValue("http://schemas.android.com/apk/res/android", "startX") != null) ? 0.0f : typedArrayJ.getFloat(8, 0.0f);
        float f4 = xml.getAttributeValue("http://schemas.android.com/apk/res/android", "startY") != null ? typedArrayJ.getFloat(9, 0.0f) : 0.0f;
        float f5 = xml.getAttributeValue("http://schemas.android.com/apk/res/android", "endX") != null ? typedArrayJ.getFloat(10, 0.0f) : 0.0f;
        float f6 = xml.getAttributeValue("http://schemas.android.com/apk/res/android", "endY") != null ? typedArrayJ.getFloat(11, 0.0f) : 0.0f;
        float f7 = !(xml.getAttributeValue("http://schemas.android.com/apk/res/android", "centerX") != null) ? 0.0f : typedArrayJ.getFloat(3, 0.0f);
        float f8 = xml.getAttributeValue("http://schemas.android.com/apk/res/android", "centerY") != null ? typedArrayJ.getFloat(4, 0.0f) : 0.0f;
        int i2 = !(xml.getAttributeValue("http://schemas.android.com/apk/res/android", "type") != null) ? 0 : typedArrayJ.getInt(2, 0);
        int color = xml.getAttributeValue("http://schemas.android.com/apk/res/android", "startColor") != null ? typedArrayJ.getColor(0, 0) : 0;
        boolean z = xml.getAttributeValue("http://schemas.android.com/apk/res/android", "centerColor") != null;
        int color2 = xml.getAttributeValue("http://schemas.android.com/apk/res/android", "centerColor") != null ? typedArrayJ.getColor(7, 0) : 0;
        int color3 = xml.getAttributeValue("http://schemas.android.com/apk/res/android", "endColor") != null ? typedArrayJ.getColor(1, 0) : 0;
        int i3 = xml.getAttributeValue("http://schemas.android.com/apk/res/android", "tileMode") != null ? typedArrayJ.getInt(6, 0) : 0;
        float f9 = xml.getAttributeValue("http://schemas.android.com/apk/res/android", "gradientRadius") != null ? typedArrayJ.getFloat(5, 0.0f) : 0.0f;
        typedArrayJ.recycle();
        int depth = xml.getDepth() + 1;
        ArrayList arrayList = new ArrayList(20);
        float f10 = f9;
        ArrayList arrayList2 = new ArrayList(20);
        while (true) {
            int next2 = xml.next();
            f = f4;
            if (next2 == 1) {
                f2 = f5;
                break;
            }
            int depth2 = xml.getDepth();
            f2 = f5;
            if (depth2 < depth && next2 == 3) {
                break;
            }
            if (next2 == 2 && depth2 <= depth && xml.getName().equals("item")) {
                TypedArray typedArrayJ2 = Hn.j(resources, theme, attributeSetAsAttributeSet, Hk.e);
                boolean zHasValue = typedArrayJ2.hasValue(0);
                boolean zHasValue2 = typedArrayJ2.hasValue(1);
                if (!zHasValue || !zHasValue2) {
                    throw new XmlPullParserException(xml.getPositionDescription() + ": <item> tag requires a 'color' attribute and a 'offset' attribute!");
                }
                int color4 = typedArrayJ2.getColor(0, 0);
                float f11 = typedArrayJ2.getFloat(1, 0.0f);
                typedArrayJ2.recycle();
                arrayList2.add(Integer.valueOf(color4));
                arrayList.add(Float.valueOf(f11));
            }
            f4 = f;
            f5 = f2;
        }
        R2 r2 = arrayList2.size() > 0 ? new R2(arrayList2, arrayList) : null;
        if (r2 == null) {
            r2 = z ? new R2(color, color2, color3) : new R2(color, color3);
        }
        if (i2 != 1) {
            if (i2 != 2) {
                if (i3 != 1) {
                    tileMode2 = i3 != 2 ? Shader.TileMode.CLAMP : Shader.TileMode.MIRROR;
                } else {
                    tileMode2 = Shader.TileMode.REPEAT;
                }
                radialGradient = new LinearGradient(f3, f, f2, f6, (int[]) r2.b, (float[]) r2.c, tileMode2);
            } else {
                radialGradient = new SweepGradient(f7, f8, (int[]) r2.b, (float[]) r2.c);
            }
        } else {
            if (f10 <= 0.0f) {
                throw new XmlPullParserException("<gradient> tag requires 'gradientRadius' attribute with radial type");
            }
            if (i3 != 1) {
                tileMode = i3 != 2 ? Shader.TileMode.CLAMP : Shader.TileMode.MIRROR;
            } else {
                tileMode = Shader.TileMode.REPEAT;
            }
            radialGradient = new RadialGradient(f7, f8, f10, (int[]) r2.b, (float[]) r2.c, tileMode);
        }
        return new C0788z(radialGradient, (ColorStateList) null, 0);
    }

    public C0255hl b(int i) {
        SparseArray sparseArray = (SparseArray) this.b;
        C0255hl c0255hl = (C0255hl) sparseArray.get(i);
        if (c0255hl != null) {
            return c0255hl;
        }
        C0255hl c0255hl2 = new C0255hl();
        sparseArray.put(i, c0255hl2);
        return c0255hl2;
    }

    public InterfaceC0202g c(int i) throws IOException {
        Object l8;
        H4 h4;
        Cg cg = (Cg) this.b;
        if (cg instanceof C0249hf) {
            C0249hf c0249hf = (C0249hf) cg;
            c0249hf.f = false;
            c0249hf.b();
        }
        int iF = C0355l.f(cg, i);
        int i2 = 4;
        boolean z = iF == 3 || iF == 4 || iF == 16 || iF == 17 || iF == 8;
        int i3 = this.a;
        int iD = C0355l.d(cg, i3, z);
        byte[][] bArr = (byte[][]) this.c;
        if (iD < 0) {
            if ((i & 32) == 0) {
                throw new IOException("indefinite-length primitive encoding encountered");
            }
            C0788z c0788z = new C0788z(new C0249hf(cg, i3), i3, bArr);
            int i4 = i & 192;
            if (i4 != 0) {
                return 64 == i4 ? new C0762y4(64, iF, c0788z) : new I4(i4, iF, c0788z);
            }
            if (iF == 3) {
                return new A4(c0788z);
            }
            if (iF == 4) {
                return new D4(c0788z);
            }
            if (iF == 8) {
                return new F4(c0788z);
            }
            if (iF == 16) {
                F4 f4 = new F4(0);
                f4.b = c0788z;
                return f4;
            }
            if (iF == 17) {
                F4 f5 = new F4(1);
                f5.b = c0788z;
                return f5;
            }
            throw new C0293j("unknown BER object encountered: 0x" + Integer.toHexString(iF));
        }
        I9 i9 = new I9(cg, iD, i3);
        if ((i & 224) == 0) {
            if (iF == 3) {
                return new N8(i9);
            }
            if (iF == 4) {
                D4 d4 = new D4();
                d4.b = i9;
                return d4;
            }
            if (iF == 8) {
                throw new C0293j("externals must use constructed encoding (see X.690 8.18)");
            }
            if (iF == 16) {
                throw new C0293j("sets must use constructed encoding (see X.690 8.11.1/8.12.1)");
            }
            if (iF == 17) {
                throw new C0293j("sequences must use constructed encoding (see X.690 8.9.1/8.10.1)");
            }
            try {
                return C0355l.b(iF, i9, bArr);
            } catch (IllegalArgumentException e) {
                throw new C0293j("corrupted stream detected", e);
            }
        }
        C0788z c0788z2 = new C0788z(i9, i9.b, bArr);
        int i5 = i & 192;
        if (i5 != 0) {
            boolean z2 = (i & 32) != 0;
            if (64 != i5) {
                return new S8(i5, iF, z2, c0788z2);
            }
            if (z2) {
                l8 = H4.r(i5, iF, c0788z2.f());
            } else {
                h4 = new H4(i2, i5, iF, new A8(i9.b()), 2);
                if (i5 == 64) {
                    l8 = h4;
                    l8 = new L8(h4);
                }
            }
            l8 = h4;
            return (L8) l8;
        }
        if (iF == 3) {
            return new A4(c0788z2);
        }
        if (iF == 4) {
            return new D4(c0788z2);
        }
        if (iF == 8) {
            return new F4(c0788z2);
        }
        if (iF == 16) {
            F4 f6 = new F4(3);
            f6.b = c0788z2;
            return f6;
        }
        if (iF == 17) {
            F4 f7 = new F4(4);
            f7.b = c0788z2;
            return f7;
        }
        throw new C0293j("unknown DL object encountered: 0x" + Integer.toHexString(iF));
    }

    public boolean d() {
        ColorStateList colorStateList;
        return ((Shader) this.b) == null && (colorStateList = (ColorStateList) this.c) != null && colorStateList.isStateful();
    }

    public AbstractC0633u e(int i, int i2) throws IOException {
        int i3;
        H4 h4;
        int i4 = 0;
        C0233h c0233hF = f();
        int i5 = c0233hF.b;
        if (i5 == 1) {
            i3 = i;
            h4 = new H4(3, i3, i2, c0233hF.f(0), 0);
        } else {
            E4 e4 = B4.a;
            i3 = i;
            h4 = new H4(4, i, i2, i5 < 1 ? B4.a : new E4(c0233hF), 0);
        }
        return i3 != 64 ? h4 : new C0731x4(h4, i4);
    }

    public C0233h f() throws IOException {
        Cg cg = (Cg) this.b;
        int i = cg.read();
        if (i < 0) {
            return new C0233h(0);
        }
        C0233h c0233h = new C0233h();
        do {
            InterfaceC0202g interfaceC0202gC = c(i);
            c0233h.d(interfaceC0202gC instanceof InterfaceC0126df ? ((InterfaceC0126df) interfaceC0202gC).a() : interfaceC0202gC.d());
            i = cg.read();
        } while (i >= 0);
        return c0233h;
    }

    public C0788z(Shader shader, ColorStateList colorStateList, int i) {
        this.b = shader;
        this.c = colorStateList;
        this.a = i;
    }
}
