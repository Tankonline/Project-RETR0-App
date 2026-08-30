package rikka.shizuku;

import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.TypedValue;
import android.util.Xml;
import java.util.HashMap;
import rikka.html.text.style.BulletSpan;

/* JADX INFO: renamed from: rikka.shizuku.a7, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0025a7 {
    public int a;
    public int b;
    public float c;
    public String d;
    public boolean e;
    public int f;

    public C0025a7(C0025a7 c0025a7, Object obj) {
        c0025a7.getClass();
        this.a = c0025a7.a;
        b(obj);
    }

    public static void a(Context context, XmlResourceParser xmlResourceParser, HashMap map) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(xmlResourceParser), Fk.c);
        int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
        String string = null;
        int i = 0;
        Object string2 = null;
        for (int i2 = 0; i2 < indexCount; i2++) {
            int index = typedArrayObtainStyledAttributes.getIndex(i2);
            if (index == 0) {
                string = typedArrayObtainStyledAttributes.getString(index);
                if (string != null && string.length() > 0) {
                    string = Character.toUpperCase(string.charAt(0)) + string.substring(1);
                }
            } else if (index == 1) {
                string2 = Boolean.valueOf(typedArrayObtainStyledAttributes.getBoolean(index, false));
                i = 6;
            } else {
                int i3 = 3;
                if (index == 3) {
                    string2 = Integer.valueOf(typedArrayObtainStyledAttributes.getColor(index, 0));
                } else {
                    i3 = 4;
                    if (index == 2) {
                        string2 = Integer.valueOf(typedArrayObtainStyledAttributes.getColor(index, 0));
                    } else {
                        if (index == 7) {
                            string2 = Float.valueOf(TypedValue.applyDimension(1, typedArrayObtainStyledAttributes.getDimension(index, 0.0f), context.getResources().getDisplayMetrics()));
                        } else if (index == 4) {
                            string2 = Float.valueOf(typedArrayObtainStyledAttributes.getDimension(index, 0.0f));
                        } else {
                            i3 = 5;
                            if (index == 5) {
                                string2 = Float.valueOf(typedArrayObtainStyledAttributes.getFloat(index, Float.NaN));
                                i = 2;
                            } else if (index == 6) {
                                string2 = Integer.valueOf(typedArrayObtainStyledAttributes.getInteger(index, -1));
                                i = 1;
                            } else if (index == 8) {
                                string2 = typedArrayObtainStyledAttributes.getString(index);
                            }
                        }
                        i = 7;
                    }
                }
                i = i3;
            }
        }
        if (string != null && string2 != null) {
            C0025a7 c0025a7 = new C0025a7();
            c0025a7.a = i;
            c0025a7.b(string2);
            map.put(string, c0025a7);
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    public final void b(Object obj) {
        switch (AbstractC0535qo.j(this.a)) {
            case 0:
                this.b = ((Integer) obj).intValue();
                break;
            case 1:
                this.c = ((Float) obj).floatValue();
                break;
            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
            case 3:
                this.f = ((Integer) obj).intValue();
                break;
            case 4:
                this.d = (String) obj;
                break;
            case BulletSpan.STANDARD_BULLET_RADIUS /* 5 */:
                this.e = ((Boolean) obj).booleanValue();
                break;
            case 6:
                this.c = ((Float) obj).floatValue();
                break;
        }
    }
}
