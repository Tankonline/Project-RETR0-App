package rikka.shizuku;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.InflateException;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.SubMenu;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: rikka.shizuku.lp, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0381lp extends MenuInflater {
    public static final Class[] e;
    public static final Class[] f;
    public final Object[] a;
    public final Object[] b;
    public final Context c;
    public Object d;

    static {
        Class[] clsArr = {Context.class};
        e = clsArr;
        f = clsArr;
    }

    public C0381lp(Context context) {
        super(context);
        this.c = context;
        Object[] objArr = {context};
        this.a = objArr;
        this.b = objArr;
    }

    public static Object a(Object obj) {
        return (!(obj instanceof Activity) && (obj instanceof ContextWrapper)) ? a(((ContextWrapper) obj).getBaseContext()) : obj;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r16v0, types: [rikka.shizuku.lp] */
    /* JADX WARN: Type inference failed for: r3v15, types: [android.content.res.TypedArray] */
    /* JADX WARN: Type inference failed for: r4v0 */
    /* JADX WARN: Type inference failed for: r4v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r4v71 */
    /* JADX WARN: Type inference failed for: r8v1 */
    /* JADX WARN: Type inference failed for: r8v10 */
    /* JADX WARN: Type inference failed for: r8v14 */
    /* JADX WARN: Type inference failed for: r8v15 */
    /* JADX WARN: Type inference failed for: r8v16 */
    /* JADX WARN: Type inference failed for: r8v17 */
    /* JADX WARN: Type inference failed for: r8v18 */
    /* JADX WARN: Type inference failed for: r8v2 */
    /* JADX WARN: Type inference failed for: r8v20 */
    public final void b(XmlResourceParser xmlResourceParser, AttributeSet attributeSet, Menu menu) throws XmlPullParserException, IOException {
        ?? r4;
        int i;
        ?? r8;
        ColorStateList colorStateList;
        int resourceId;
        C0350kp c0350kp = new C0350kp(this, menu);
        int eventType = xmlResourceParser.getEventType();
        do {
            r4 = 1;
            i = 2;
            if (eventType == 2) {
                String name = xmlResourceParser.getName();
                if (!name.equals("menu")) {
                    throw new RuntimeException("Expecting menu, got ".concat(name));
                }
                eventType = xmlResourceParser.next();
                break;
            }
            eventType = xmlResourceParser.next();
        } while (eventType != 1);
        boolean z = false;
        boolean z2 = false;
        String str = null;
        while (!z) {
            if (eventType == r4) {
                throw new RuntimeException("Unexpected end of document");
            }
            if (eventType == i) {
                if (!z2) {
                    String name2 = xmlResourceParser.getName();
                    boolean zEquals = name2.equals("group");
                    C0381lp c0381lp = c0350kp.F;
                    if (zEquals) {
                        ?? ObtainStyledAttributes = c0381lp.c.obtainStyledAttributes(attributeSet, Nk.p);
                        c0350kp.b = ObtainStyledAttributes.getResourceId(r4, 0);
                        c0350kp.c = ObtainStyledAttributes.getInt(3, 0);
                        c0350kp.d = ObtainStyledAttributes.getInt(4, 0);
                        c0350kp.e = ObtainStyledAttributes.getInt(5, 0);
                        c0350kp.f = ObtainStyledAttributes.getBoolean(2, r4);
                        c0350kp.g = ObtainStyledAttributes.getBoolean(0, r4);
                        ObtainStyledAttributes.recycle();
                    } else if (name2.equals("item")) {
                        Context context = c0381lp.c;
                        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Nk.q);
                        c0350kp.i = typedArrayObtainStyledAttributes.getResourceId(2, 0);
                        c0350kp.j = (typedArrayObtainStyledAttributes.getInt(5, c0350kp.c) & (-65536)) | (typedArrayObtainStyledAttributes.getInt(6, c0350kp.d) & 65535);
                        c0350kp.k = typedArrayObtainStyledAttributes.getText(7);
                        c0350kp.l = typedArrayObtainStyledAttributes.getText(8);
                        c0350kp.m = typedArrayObtainStyledAttributes.getResourceId(0, 0);
                        String string = typedArrayObtainStyledAttributes.getString(9);
                        c0350kp.n = string == null ? (char) 0 : string.charAt(0);
                        c0350kp.o = typedArrayObtainStyledAttributes.getInt(21, 4096);
                        String string2 = typedArrayObtainStyledAttributes.getString(10);
                        c0350kp.p = string2 == null ? (char) 0 : string2.charAt(0);
                        c0350kp.q = typedArrayObtainStyledAttributes.getInt(25, 4096);
                        if (typedArrayObtainStyledAttributes.hasValue(11)) {
                            c0350kp.r = typedArrayObtainStyledAttributes.getBoolean(11, false) ? 1 : 0;
                        } else {
                            c0350kp.r = c0350kp.e;
                        }
                        c0350kp.s = typedArrayObtainStyledAttributes.getBoolean(3, false);
                        c0350kp.t = typedArrayObtainStyledAttributes.getBoolean(4, c0350kp.f);
                        c0350kp.u = typedArrayObtainStyledAttributes.getBoolean(1, c0350kp.g);
                        if (typedArrayObtainStyledAttributes.hasValue(14)) {
                            c0350kp.v = typedArrayObtainStyledAttributes.getInt(14, -1);
                        } else {
                            c0350kp.v = typedArrayObtainStyledAttributes.getInt(26, -1);
                        }
                        c0350kp.z = typedArrayObtainStyledAttributes.getString(12);
                        if (typedArrayObtainStyledAttributes.hasValue(15)) {
                            c0350kp.w = typedArrayObtainStyledAttributes.getResourceId(15, 0);
                        } else {
                            c0350kp.w = typedArrayObtainStyledAttributes.getResourceId(18, 0);
                        }
                        if (typedArrayObtainStyledAttributes.hasValue(16)) {
                            c0350kp.x = typedArrayObtainStyledAttributes.getString(16);
                        } else {
                            c0350kp.x = typedArrayObtainStyledAttributes.getString(20);
                        }
                        if (typedArrayObtainStyledAttributes.hasValue(17)) {
                            c0350kp.y = typedArrayObtainStyledAttributes.getString(17);
                        } else {
                            c0350kp.y = typedArrayObtainStyledAttributes.getString(19);
                        }
                        String str2 = c0350kp.y;
                        boolean z3 = str2 != null;
                        if (z3 && c0350kp.w == 0 && c0350kp.x == null) {
                            c0350kp.A = (ActionProviderVisibilityListenerC0252hi) c0350kp.a(str2, f, c0381lp.b);
                        } else {
                            if (z3) {
                                Log.w("SupportMenuInflater", "Ignoring attribute 'actionProviderClass'. Action view already specified.");
                            }
                            c0350kp.A = null;
                        }
                        if (typedArrayObtainStyledAttributes.hasValue(13)) {
                            c0350kp.B = typedArrayObtainStyledAttributes.getText(13);
                        } else {
                            c0350kp.B = typedArrayObtainStyledAttributes.getText(22);
                        }
                        c0350kp.C = typedArrayObtainStyledAttributes.getText(27);
                        if (typedArrayObtainStyledAttributes.hasValue(24)) {
                            c0350kp.E = AbstractC0304ja.c(typedArrayObtainStyledAttributes.getInt(24, -1), c0350kp.E);
                        } else {
                            c0350kp.E = null;
                        }
                        if (typedArrayObtainStyledAttributes.hasValue(23)) {
                            if (!typedArrayObtainStyledAttributes.hasValue(23) || (resourceId = typedArrayObtainStyledAttributes.getResourceId(23, 0)) == 0 || (colorStateList = AbstractC0599sr.o(context, resourceId)) == null) {
                                colorStateList = typedArrayObtainStyledAttributes.getColorStateList(23);
                            }
                            c0350kp.D = colorStateList;
                        } else {
                            c0350kp.D = null;
                        }
                        typedArrayObtainStyledAttributes.recycle();
                        c0350kp.h = false;
                        xmlResourceParser = xmlResourceParser;
                        r8 = 1;
                    } else if (name2.equals("menu")) {
                        r8 = 1;
                        c0350kp.h = true;
                        SubMenu subMenuAddSubMenu = c0350kp.a.addSubMenu(c0350kp.b, c0350kp.i, c0350kp.j, c0350kp.k);
                        c0350kp.b(subMenuAddSubMenu.getItem());
                        xmlResourceParser = xmlResourceParser;
                        b(xmlResourceParser, attributeSet, subMenuAddSubMenu);
                    } else {
                        xmlResourceParser = xmlResourceParser;
                        r8 = 1;
                        str = name2;
                        z2 = true;
                    }
                }
                r8 = r4;
                z = z;
            } else if (eventType != 3) {
                r8 = r4;
                z = z;
            } else {
                String name3 = xmlResourceParser.getName();
                if (z2 && name3.equals(str)) {
                    xmlResourceParser = xmlResourceParser;
                    r8 = r4;
                    z2 = false;
                    str = null;
                } else {
                    if (name3.equals("group")) {
                        c0350kp.b = 0;
                        c0350kp.c = 0;
                        c0350kp.d = 0;
                        c0350kp.e = 0;
                        c0350kp.f = r4;
                        c0350kp.g = r4;
                    } else if (name3.equals("item")) {
                        if (!c0350kp.h) {
                            ActionProviderVisibilityListenerC0252hi actionProviderVisibilityListenerC0252hi = c0350kp.A;
                            if (actionProviderVisibilityListenerC0252hi == null || !actionProviderVisibilityListenerC0252hi.a.hasSubMenu()) {
                                c0350kp.h = r4;
                                c0350kp.b(c0350kp.a.add(c0350kp.b, c0350kp.i, c0350kp.j, c0350kp.k));
                            } else {
                                c0350kp.h = r4;
                                c0350kp.b(c0350kp.a.addSubMenu(c0350kp.b, c0350kp.i, c0350kp.j, c0350kp.k).getItem());
                            }
                        }
                    } else if (name3.equals("menu")) {
                        ?? r9 = r4;
                        z = r9 == true ? 1 : 0;
                        r8 = r9;
                    }
                    r8 = r4;
                    z = z;
                }
            }
            eventType = xmlResourceParser.next();
            r4 = r8;
            i = 2;
            z = z;
            z2 = z2;
        }
    }

    @Override // android.view.MenuInflater
    public final void inflate(int i, Menu menu) {
        if (!(menu instanceof MenuC0129di)) {
            super.inflate(i, menu);
            return;
        }
        XmlResourceParser layout = null;
        try {
            try {
                try {
                    layout = this.c.getResources().getLayout(i);
                    b(layout, Xml.asAttributeSet(layout), menu);
                    layout.close();
                } catch (IOException e2) {
                    throw new InflateException("Error inflating menu XML", e2);
                }
            } catch (XmlPullParserException e3) {
                throw new InflateException("Error inflating menu XML", e3);
            }
        } catch (Throwable th) {
            if (layout != null) {
                layout.close();
            }
            throw th;
        }
    }
}
