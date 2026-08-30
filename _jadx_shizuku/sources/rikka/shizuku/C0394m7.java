package rikka.shizuku;

import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.util.Xml;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import org.xmlpull.v1.XmlPullParserException;
import rikka.html.text.style.BulletSpan;

/* JADX INFO: renamed from: rikka.shizuku.m7, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0394m7 {
    public static final int[] d = {0, 4, 8};
    public static final SparseIntArray e;
    public final HashMap a = new HashMap();
    public final boolean b = true;
    public final HashMap c = new HashMap();

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        e = sparseIntArray;
        sparseIntArray.append(76, 25);
        sparseIntArray.append(77, 26);
        sparseIntArray.append(79, 29);
        sparseIntArray.append(80, 30);
        sparseIntArray.append(86, 36);
        sparseIntArray.append(85, 35);
        sparseIntArray.append(58, 4);
        sparseIntArray.append(57, 3);
        sparseIntArray.append(55, 1);
        sparseIntArray.append(94, 6);
        sparseIntArray.append(95, 7);
        sparseIntArray.append(65, 17);
        sparseIntArray.append(66, 18);
        sparseIntArray.append(67, 19);
        sparseIntArray.append(0, 27);
        sparseIntArray.append(81, 32);
        sparseIntArray.append(82, 33);
        sparseIntArray.append(64, 10);
        sparseIntArray.append(63, 9);
        sparseIntArray.append(98, 13);
        sparseIntArray.append(101, 16);
        sparseIntArray.append(99, 14);
        sparseIntArray.append(96, 11);
        sparseIntArray.append(100, 15);
        sparseIntArray.append(97, 12);
        sparseIntArray.append(89, 40);
        sparseIntArray.append(74, 39);
        sparseIntArray.append(73, 41);
        sparseIntArray.append(88, 42);
        sparseIntArray.append(72, 20);
        sparseIntArray.append(87, 37);
        sparseIntArray.append(62, 5);
        sparseIntArray.append(75, 82);
        sparseIntArray.append(84, 82);
        sparseIntArray.append(78, 82);
        sparseIntArray.append(56, 82);
        sparseIntArray.append(54, 82);
        sparseIntArray.append(5, 24);
        sparseIntArray.append(7, 28);
        sparseIntArray.append(23, 31);
        sparseIntArray.append(24, 8);
        sparseIntArray.append(6, 34);
        sparseIntArray.append(8, 2);
        sparseIntArray.append(3, 23);
        sparseIntArray.append(4, 21);
        sparseIntArray.append(2, 22);
        sparseIntArray.append(13, 43);
        sparseIntArray.append(26, 44);
        sparseIntArray.append(21, 45);
        sparseIntArray.append(22, 46);
        sparseIntArray.append(20, 60);
        sparseIntArray.append(18, 47);
        sparseIntArray.append(19, 48);
        sparseIntArray.append(14, 49);
        sparseIntArray.append(15, 50);
        sparseIntArray.append(16, 51);
        sparseIntArray.append(17, 52);
        sparseIntArray.append(25, 53);
        sparseIntArray.append(90, 54);
        sparseIntArray.append(68, 55);
        sparseIntArray.append(91, 56);
        sparseIntArray.append(69, 57);
        sparseIntArray.append(92, 58);
        sparseIntArray.append(70, 59);
        sparseIntArray.append(59, 61);
        sparseIntArray.append(61, 62);
        sparseIntArray.append(60, 63);
        sparseIntArray.append(27, 64);
        sparseIntArray.append(106, 65);
        sparseIntArray.append(33, 66);
        sparseIntArray.append(107, 67);
        sparseIntArray.append(103, 79);
        sparseIntArray.append(1, 38);
        sparseIntArray.append(102, 68);
        sparseIntArray.append(93, 69);
        sparseIntArray.append(71, 70);
        sparseIntArray.append(31, 71);
        sparseIntArray.append(29, 72);
        sparseIntArray.append(30, 73);
        sparseIntArray.append(32, 74);
        sparseIntArray.append(28, 75);
        sparseIntArray.append(104, 76);
        sparseIntArray.append(83, 77);
        sparseIntArray.append(108, 78);
        sparseIntArray.append(53, 80);
        sparseIntArray.append(52, 81);
    }

    public static int[] c(O4 o4, String str) {
        int iIntValue;
        String[] strArrSplit = str.split(",");
        Context context = o4.getContext();
        int[] iArr = new int[strArrSplit.length];
        int i = 0;
        int i2 = 0;
        while (i < strArrSplit.length) {
            String strTrim = strArrSplit[i].trim();
            Object obj = null;
            try {
                iIntValue = Ak.class.getField(strTrim).getInt(null);
            } catch (Exception unused) {
                iIntValue = 0;
            }
            if (iIntValue == 0) {
                iIntValue = context.getResources().getIdentifier(strTrim, "id", context.getPackageName());
            }
            if (iIntValue == 0 && o4.isInEditMode() && (o4.getParent() instanceof ConstraintLayout)) {
                ConstraintLayout constraintLayout = (ConstraintLayout) o4.getParent();
                if (strTrim != null) {
                    HashMap map = constraintLayout.l;
                    if (map != null && map.containsKey(strTrim)) {
                        obj = constraintLayout.l.get(strTrim);
                    }
                } else {
                    constraintLayout.getClass();
                }
                if (obj != null && (obj instanceof Integer)) {
                    iIntValue = ((Integer) obj).intValue();
                }
            }
            iArr[i2] = iIntValue;
            i++;
            i2++;
        }
        return i2 != strArrSplit.length ? Arrays.copyOf(iArr, i2) : iArr;
    }

    public static C0241h7 d(Context context, AttributeSet attributeSet) {
        C0241h7 c0241h7 = new C0241h7();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Fk.a);
        int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = typedArrayObtainStyledAttributes.getIndex(i);
            C0332k7 c0332k7 = c0241h7.b;
            C0301j7 c0301j7 = c0241h7.c;
            C0363l7 c0363l7 = c0241h7.e;
            C0272i7 c0272i7 = c0241h7.d;
            if (index != 1 && 23 != index && 24 != index) {
                c0301j7.getClass();
                c0272i7.getClass();
                c0363l7.getClass();
            }
            SparseIntArray sparseIntArray = e;
            switch (sparseIntArray.get(index)) {
                case 1:
                    c0272i7.o = f(typedArrayObtainStyledAttributes, index, c0272i7.o);
                    break;
                case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                    c0272i7.F = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, c0272i7.F);
                    break;
                case 3:
                    c0272i7.n = f(typedArrayObtainStyledAttributes, index, c0272i7.n);
                    break;
                case 4:
                    c0272i7.m = f(typedArrayObtainStyledAttributes, index, c0272i7.m);
                    break;
                case BulletSpan.STANDARD_BULLET_RADIUS /* 5 */:
                    c0272i7.v = typedArrayObtainStyledAttributes.getString(index);
                    break;
                case 6:
                    c0272i7.z = typedArrayObtainStyledAttributes.getDimensionPixelOffset(index, c0272i7.z);
                    break;
                case 7:
                    c0272i7.A = typedArrayObtainStyledAttributes.getDimensionPixelOffset(index, c0272i7.A);
                    break;
                case 8:
                    c0272i7.G = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, c0272i7.G);
                    break;
                case 9:
                    c0272i7.s = f(typedArrayObtainStyledAttributes, index, c0272i7.s);
                    break;
                case 10:
                    c0272i7.r = f(typedArrayObtainStyledAttributes, index, c0272i7.r);
                    break;
                case 11:
                    c0272i7.L = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, c0272i7.L);
                    break;
                case 12:
                    c0272i7.M = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, c0272i7.M);
                    break;
                case 13:
                    c0272i7.I = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, c0272i7.I);
                    break;
                case 14:
                    c0272i7.K = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, c0272i7.K);
                    break;
                case 15:
                    c0272i7.N = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, c0272i7.N);
                    break;
                case 16:
                    c0272i7.J = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, c0272i7.J);
                    break;
                case 17:
                    c0272i7.d = typedArrayObtainStyledAttributes.getDimensionPixelOffset(index, c0272i7.d);
                    break;
                case 18:
                    c0272i7.e = typedArrayObtainStyledAttributes.getDimensionPixelOffset(index, c0272i7.e);
                    break;
                case 19:
                    c0272i7.f = typedArrayObtainStyledAttributes.getFloat(index, c0272i7.f);
                    break;
                case 20:
                    c0272i7.t = typedArrayObtainStyledAttributes.getFloat(index, c0272i7.t);
                    break;
                case 21:
                    c0272i7.c = typedArrayObtainStyledAttributes.getLayoutDimension(index, c0272i7.c);
                    break;
                case 22:
                    int i2 = typedArrayObtainStyledAttributes.getInt(index, c0332k7.a);
                    c0332k7.a = i2;
                    c0332k7.a = d[i2];
                    break;
                case 23:
                    c0272i7.b = typedArrayObtainStyledAttributes.getLayoutDimension(index, c0272i7.b);
                    break;
                case 24:
                    c0272i7.C = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, c0272i7.C);
                    break;
                case 25:
                    c0272i7.g = f(typedArrayObtainStyledAttributes, index, c0272i7.g);
                    break;
                case 26:
                    c0272i7.h = f(typedArrayObtainStyledAttributes, index, c0272i7.h);
                    break;
                case 27:
                    c0272i7.B = typedArrayObtainStyledAttributes.getInt(index, c0272i7.B);
                    break;
                case 28:
                    c0272i7.D = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, c0272i7.D);
                    break;
                case 29:
                    c0272i7.i = f(typedArrayObtainStyledAttributes, index, c0272i7.i);
                    break;
                case 30:
                    c0272i7.j = f(typedArrayObtainStyledAttributes, index, c0272i7.j);
                    break;
                case 31:
                    c0272i7.H = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, c0272i7.H);
                    break;
                case 32:
                    c0272i7.p = f(typedArrayObtainStyledAttributes, index, c0272i7.p);
                    break;
                case 33:
                    c0272i7.q = f(typedArrayObtainStyledAttributes, index, c0272i7.q);
                    break;
                case 34:
                    c0272i7.E = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, c0272i7.E);
                    break;
                case 35:
                    c0272i7.l = f(typedArrayObtainStyledAttributes, index, c0272i7.l);
                    break;
                case 36:
                    c0272i7.k = f(typedArrayObtainStyledAttributes, index, c0272i7.k);
                    break;
                case 37:
                    c0272i7.u = typedArrayObtainStyledAttributes.getFloat(index, c0272i7.u);
                    break;
                case 38:
                    c0241h7.a = typedArrayObtainStyledAttributes.getResourceId(index, c0241h7.a);
                    break;
                case 39:
                    c0272i7.P = typedArrayObtainStyledAttributes.getFloat(index, c0272i7.P);
                    break;
                case 40:
                    c0272i7.O = typedArrayObtainStyledAttributes.getFloat(index, c0272i7.O);
                    break;
                case 41:
                    c0272i7.Q = typedArrayObtainStyledAttributes.getInt(index, c0272i7.Q);
                    break;
                case 42:
                    c0272i7.R = typedArrayObtainStyledAttributes.getInt(index, c0272i7.R);
                    break;
                case 43:
                    c0332k7.c = typedArrayObtainStyledAttributes.getFloat(index, c0332k7.c);
                    break;
                case 44:
                    c0363l7.k = true;
                    c0363l7.l = typedArrayObtainStyledAttributes.getDimension(index, c0363l7.l);
                    break;
                case 45:
                    c0363l7.b = typedArrayObtainStyledAttributes.getFloat(index, c0363l7.b);
                    break;
                case 46:
                    c0363l7.c = typedArrayObtainStyledAttributes.getFloat(index, c0363l7.c);
                    break;
                case 47:
                    c0363l7.d = typedArrayObtainStyledAttributes.getFloat(index, c0363l7.d);
                    break;
                case 48:
                    c0363l7.e = typedArrayObtainStyledAttributes.getFloat(index, c0363l7.e);
                    break;
                case 49:
                    c0363l7.f = typedArrayObtainStyledAttributes.getDimension(index, c0363l7.f);
                    break;
                case 50:
                    c0363l7.g = typedArrayObtainStyledAttributes.getDimension(index, c0363l7.g);
                    break;
                case 51:
                    c0363l7.h = typedArrayObtainStyledAttributes.getDimension(index, c0363l7.h);
                    break;
                case 52:
                    c0363l7.i = typedArrayObtainStyledAttributes.getDimension(index, c0363l7.i);
                    break;
                case 53:
                    c0363l7.j = typedArrayObtainStyledAttributes.getDimension(index, c0363l7.j);
                    break;
                case 54:
                    c0272i7.S = typedArrayObtainStyledAttributes.getInt(index, c0272i7.S);
                    break;
                case 55:
                    c0272i7.T = typedArrayObtainStyledAttributes.getInt(index, c0272i7.T);
                    break;
                case 56:
                    c0272i7.U = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, c0272i7.U);
                    break;
                case 57:
                    c0272i7.V = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, c0272i7.V);
                    break;
                case 58:
                    c0272i7.W = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, c0272i7.W);
                    break;
                case 59:
                    c0272i7.X = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, c0272i7.X);
                    break;
                case 60:
                    c0363l7.a = typedArrayObtainStyledAttributes.getFloat(index, c0363l7.a);
                    break;
                case 61:
                    c0272i7.w = f(typedArrayObtainStyledAttributes, index, c0272i7.w);
                    break;
                case 62:
                    c0272i7.x = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, c0272i7.x);
                    break;
                case 63:
                    c0272i7.y = typedArrayObtainStyledAttributes.getFloat(index, c0272i7.y);
                    break;
                case 64:
                    c0301j7.a = f(typedArrayObtainStyledAttributes, index, c0301j7.a);
                    break;
                case 65:
                    if (typedArrayObtainStyledAttributes.peekValue(index).type == 3) {
                        typedArrayObtainStyledAttributes.getString(index);
                        c0301j7.getClass();
                    } else {
                        String str = AbstractC0599sr.e[typedArrayObtainStyledAttributes.getInteger(index, 0)];
                        c0301j7.getClass();
                    }
                    break;
                case 66:
                    typedArrayObtainStyledAttributes.getInt(index, 0);
                    c0301j7.getClass();
                    break;
                case 67:
                    c0301j7.d = typedArrayObtainStyledAttributes.getFloat(index, c0301j7.d);
                    break;
                case 68:
                    c0332k7.d = typedArrayObtainStyledAttributes.getFloat(index, c0332k7.d);
                    break;
                case 69:
                    c0272i7.Y = typedArrayObtainStyledAttributes.getFloat(index, 1.0f);
                    break;
                case 70:
                    c0272i7.Z = typedArrayObtainStyledAttributes.getFloat(index, 1.0f);
                    break;
                case 71:
                    Log.e("ConstraintSet", "CURRENTLY UNSUPPORTED");
                    break;
                case 72:
                    c0272i7.a0 = typedArrayObtainStyledAttributes.getInt(index, c0272i7.a0);
                    break;
                case 73:
                    c0272i7.b0 = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, c0272i7.b0);
                    break;
                case 74:
                    c0272i7.e0 = typedArrayObtainStyledAttributes.getString(index);
                    break;
                case 75:
                    c0272i7.i0 = typedArrayObtainStyledAttributes.getBoolean(index, c0272i7.i0);
                    break;
                case 76:
                    c0301j7.b = typedArrayObtainStyledAttributes.getInt(index, c0301j7.b);
                    break;
                case 77:
                    c0272i7.f0 = typedArrayObtainStyledAttributes.getString(index);
                    break;
                case 78:
                    c0332k7.b = typedArrayObtainStyledAttributes.getInt(index, c0332k7.b);
                    break;
                case 79:
                    c0301j7.c = typedArrayObtainStyledAttributes.getFloat(index, c0301j7.c);
                    break;
                case 80:
                    c0272i7.g0 = typedArrayObtainStyledAttributes.getBoolean(index, c0272i7.g0);
                    break;
                case 81:
                    c0272i7.h0 = typedArrayObtainStyledAttributes.getBoolean(index, c0272i7.h0);
                    break;
                case 82:
                    Log.w("ConstraintSet", "unused attribute 0x" + Integer.toHexString(index) + "   " + sparseIntArray.get(index));
                    break;
                default:
                    Log.w("ConstraintSet", "Unknown attribute 0x" + Integer.toHexString(index) + "   " + sparseIntArray.get(index));
                    break;
            }
        }
        typedArrayObtainStyledAttributes.recycle();
        return c0241h7;
    }

    public static int f(TypedArray typedArray, int i, int i2) {
        int resourceId = typedArray.getResourceId(i, i2);
        return resourceId == -1 ? typedArray.getInt(i, -1) : resourceId;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final void a(ConstraintLayout constraintLayout) {
        int i;
        HashSet hashSet;
        HashSet hashSet2;
        String resourceEntryName;
        C0394m7 c0394m7 = this;
        int childCount = constraintLayout.getChildCount();
        HashMap map = c0394m7.c;
        HashSet<Integer> hashSet3 = new HashSet(map.keySet());
        int i2 = 0;
        while (i2 < childCount) {
            View childAt = constraintLayout.getChildAt(i2);
            int id = childAt.getId();
            if (!map.containsKey(Integer.valueOf(id))) {
                StringBuilder sb = new StringBuilder("id unknown ");
                try {
                    resourceEntryName = childAt.getContext().getResources().getResourceEntryName(childAt.getId());
                } catch (Exception unused) {
                    resourceEntryName = "UNKNOWN";
                }
                sb.append(resourceEntryName);
                Log.w("ConstraintSet", sb.toString());
            } else {
                if (c0394m7.b && id == -1) {
                    throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
                }
                if (id != -1) {
                    if (map.containsKey(Integer.valueOf(id))) {
                        hashSet3.remove(Integer.valueOf(id));
                        C0241h7 c0241h7 = (C0241h7) map.get(Integer.valueOf(id));
                        if (childAt instanceof O4) {
                            c0241h7.d.c0 = 1;
                        }
                        int i3 = c0241h7.d.c0;
                        if (i3 != -1 && i3 == 1) {
                            O4 o4 = (O4) childAt;
                            o4.setId(id);
                            C0272i7 c0272i7 = c0241h7.d;
                            o4.g = c0272i7.a0;
                            int i4 = c0272i7.b0;
                            P4 p4 = o4.i;
                            p4.h0 = i4;
                            p4.g0 = c0272i7.i0;
                            int[] iArr = c0272i7.d0;
                            if (iArr != null) {
                                o4.g(iArr);
                            } else {
                                String str = c0272i7.e0;
                                if (str != null) {
                                    int[] iArrC = c(o4, str);
                                    c0272i7.d0 = iArrC;
                                    o4.g(iArrC);
                                }
                            }
                        }
                        C0118d7 c0118d7 = (C0118d7) childAt.getLayoutParams();
                        c0118d7.a();
                        c0241h7.a(c0118d7);
                        HashMap map2 = c0241h7.f;
                        Class<?> cls = childAt.getClass();
                        for (String str2 : map2.keySet()) {
                            C0025a7 c0025a7 = (C0025a7) map2.get(str2);
                            int i5 = childCount;
                            String str3 = "set" + str2;
                            try {
                                int iJ = AbstractC0535qo.j(c0025a7.a);
                                Class cls2 = Integer.TYPE;
                                Class cls3 = Float.TYPE;
                                switch (iJ) {
                                    case 0:
                                        hashSet2 = hashSet3;
                                        cls.getMethod(str3, cls2).invoke(childAt, Integer.valueOf(c0025a7.b));
                                        break;
                                    case 1:
                                        hashSet2 = hashSet3;
                                        cls.getMethod(str3, cls3).invoke(childAt, Float.valueOf(c0025a7.c));
                                        break;
                                    case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                                        hashSet2 = hashSet3;
                                        cls.getMethod(str3, cls2).invoke(childAt, Integer.valueOf(c0025a7.f));
                                        break;
                                    case 3:
                                        Method method = cls.getMethod(str3, Drawable.class);
                                        hashSet2 = hashSet3;
                                        try {
                                            ColorDrawable colorDrawable = new ColorDrawable();
                                            colorDrawable.setColor(c0025a7.f);
                                            method.invoke(childAt, colorDrawable);
                                        } catch (IllegalAccessException e2) {
                                            e = e2;
                                            StringBuilder sbE = AbstractC0535qo.e(" Custom Attribute \"", str2, "\" not found on ");
                                            sbE.append(cls.getName());
                                            Log.e("TransitionLayout", sbE.toString());
                                            e.printStackTrace();
                                        } catch (NoSuchMethodException e3) {
                                            e = e3;
                                            Log.e("TransitionLayout", e.getMessage());
                                            Log.e("TransitionLayout", " Custom Attribute \"" + str2 + "\" not found on " + cls.getName());
                                            Log.e("TransitionLayout", cls.getName() + " must have a method " + str3);
                                        } catch (InvocationTargetException e4) {
                                            e = e4;
                                            StringBuilder sbE2 = AbstractC0535qo.e(" Custom Attribute \"", str2, "\" not found on ");
                                            sbE2.append(cls.getName());
                                            Log.e("TransitionLayout", sbE2.toString());
                                            e.printStackTrace();
                                        }
                                        break;
                                    case 4:
                                        cls.getMethod(str3, CharSequence.class).invoke(childAt, c0025a7.d);
                                        hashSet2 = hashSet3;
                                        break;
                                    case BulletSpan.STANDARD_BULLET_RADIUS /* 5 */:
                                        cls.getMethod(str3, Boolean.TYPE).invoke(childAt, Boolean.valueOf(c0025a7.e));
                                        hashSet2 = hashSet3;
                                        break;
                                    case 6:
                                        cls.getMethod(str3, cls3).invoke(childAt, Float.valueOf(c0025a7.c));
                                        hashSet2 = hashSet3;
                                        break;
                                    default:
                                        hashSet2 = hashSet3;
                                        break;
                                }
                            } catch (IllegalAccessException e5) {
                                e = e5;
                                hashSet2 = hashSet3;
                            } catch (NoSuchMethodException e6) {
                                e = e6;
                                hashSet2 = hashSet3;
                            } catch (InvocationTargetException e7) {
                                e = e7;
                                hashSet2 = hashSet3;
                            }
                            childCount = i5;
                            hashSet3 = hashSet2;
                        }
                        i = childCount;
                        hashSet = hashSet3;
                        childAt.setLayoutParams(c0118d7);
                        C0332k7 c0332k7 = c0241h7.b;
                        if (c0332k7.b == 0) {
                            childAt.setVisibility(c0332k7.a);
                        }
                        childAt.setAlpha(c0332k7.c);
                        C0363l7 c0363l7 = c0241h7.e;
                        childAt.setRotation(c0363l7.a);
                        childAt.setRotationX(c0363l7.b);
                        childAt.setRotationY(c0363l7.c);
                        childAt.setScaleX(c0363l7.d);
                        childAt.setScaleY(c0363l7.e);
                        if (!Float.isNaN(c0363l7.f)) {
                            childAt.setPivotX(c0363l7.f);
                        }
                        if (!Float.isNaN(c0363l7.g)) {
                            childAt.setPivotY(c0363l7.g);
                        }
                        childAt.setTranslationX(c0363l7.h);
                        childAt.setTranslationY(c0363l7.i);
                        childAt.setTranslationZ(c0363l7.j);
                        if (c0363l7.k) {
                            childAt.setElevation(c0363l7.l);
                        }
                    } else {
                        i = childCount;
                        hashSet = hashSet3;
                        Log.v("ConstraintSet", "WARNING NO CONSTRAINTS for view " + id);
                    }
                }
                i2++;
                c0394m7 = this;
                childCount = i;
                hashSet3 = hashSet;
            }
            i = childCount;
            hashSet = hashSet3;
            i2++;
            c0394m7 = this;
            childCount = i;
            hashSet3 = hashSet;
        }
        for (Integer num : hashSet3) {
            C0241h7 c0241h8 = (C0241h7) map.get(num);
            C0272i7 c0272i8 = c0241h8.d;
            int i6 = c0272i8.c0;
            if (i6 != -1 && i6 == 1) {
                Context context = constraintLayout.getContext();
                O4 o5 = new O4(context);
                o5.a = new int[32];
                o5.f = new HashMap();
                o5.c = context;
                P4 p5 = new P4();
                p5.f0 = 0;
                p5.g0 = true;
                p5.h0 = 0;
                o5.i = p5;
                o5.d = p5;
                o5.h();
                o5.setVisibility(8);
                o5.setId(num.intValue());
                int[] iArr2 = c0272i8.d0;
                if (iArr2 != null) {
                    o5.g(iArr2);
                } else {
                    String str4 = c0272i8.e0;
                    if (str4 != null) {
                        int[] iArrC2 = c(o5, str4);
                        c0272i8.d0 = iArrC2;
                        o5.g(iArrC2);
                    }
                }
                o5.g = c0272i8.a0;
                o5.i.h0 = c0272i8.b0;
                C0118d7 c0118d7A = ConstraintLayout.a();
                o5.h();
                c0241h8.a(c0118d7A);
                constraintLayout.addView(o5, c0118d7A);
            }
            if (c0272i8.a) {
                View wd = new Wd(constraintLayout.getContext());
                wd.setId(num.intValue());
                C0118d7 c0118d7A2 = ConstraintLayout.a();
                c0241h8.a(c0118d7A2);
                constraintLayout.addView(wd, c0118d7A2);
            }
        }
    }

    public final void b(ConstraintLayout constraintLayout) {
        C0394m7 c0394m7 = this;
        int childCount = constraintLayout.getChildCount();
        HashMap map = c0394m7.c;
        map.clear();
        int i = 0;
        while (i < childCount) {
            View childAt = constraintLayout.getChildAt(i);
            C0118d7 c0118d7 = (C0118d7) childAt.getLayoutParams();
            int id = childAt.getId();
            if (c0394m7.b && id == -1) {
                throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
            }
            if (!map.containsKey(Integer.valueOf(id))) {
                map.put(Integer.valueOf(id), new C0241h7());
            }
            C0241h7 c0241h7 = (C0241h7) map.get(Integer.valueOf(id));
            HashMap map2 = c0394m7.a;
            HashMap map3 = new HashMap();
            Class<?> cls = childAt.getClass();
            for (String str : map2.keySet()) {
                C0025a7 c0025a7 = (C0025a7) map2.get(str);
                try {
                    if (str.equals("BackgroundColor")) {
                        map3.put(str, new C0025a7(c0025a7, Integer.valueOf(((ColorDrawable) childAt.getBackground()).getColor())));
                    } else {
                        map3.put(str, new C0025a7(c0025a7, cls.getMethod("getMap" + str, null).invoke(childAt, null)));
                    }
                } catch (IllegalAccessException e2) {
                    e2.printStackTrace();
                } catch (NoSuchMethodException e3) {
                    e3.printStackTrace();
                } catch (InvocationTargetException e4) {
                    e4.printStackTrace();
                }
            }
            c0241h7.f = map3;
            c0241h7.a = id;
            int i2 = c0118d7.d;
            C0272i7 c0272i7 = c0241h7.d;
            c0272i7.g = i2;
            c0272i7.h = c0118d7.e;
            c0272i7.i = c0118d7.f;
            c0272i7.j = c0118d7.g;
            c0272i7.k = c0118d7.h;
            c0272i7.l = c0118d7.i;
            c0272i7.m = c0118d7.j;
            c0272i7.n = c0118d7.k;
            c0272i7.o = c0118d7.l;
            c0272i7.p = c0118d7.p;
            c0272i7.q = c0118d7.q;
            c0272i7.r = c0118d7.r;
            c0272i7.s = c0118d7.s;
            c0272i7.t = c0118d7.z;
            c0272i7.u = c0118d7.A;
            c0272i7.v = c0118d7.B;
            c0272i7.w = c0118d7.m;
            c0272i7.x = c0118d7.n;
            c0272i7.y = c0118d7.o;
            c0272i7.z = c0118d7.P;
            c0272i7.A = c0118d7.Q;
            c0272i7.B = c0118d7.R;
            c0272i7.f = c0118d7.c;
            c0272i7.d = c0118d7.a;
            c0272i7.e = c0118d7.b;
            c0272i7.b = ((ViewGroup.MarginLayoutParams) c0118d7).width;
            c0272i7.c = ((ViewGroup.MarginLayoutParams) c0118d7).height;
            c0272i7.C = ((ViewGroup.MarginLayoutParams) c0118d7).leftMargin;
            c0272i7.D = ((ViewGroup.MarginLayoutParams) c0118d7).rightMargin;
            c0272i7.E = ((ViewGroup.MarginLayoutParams) c0118d7).topMargin;
            c0272i7.F = ((ViewGroup.MarginLayoutParams) c0118d7).bottomMargin;
            c0272i7.O = c0118d7.E;
            c0272i7.P = c0118d7.D;
            c0272i7.R = c0118d7.G;
            c0272i7.Q = c0118d7.F;
            c0272i7.g0 = c0118d7.S;
            c0272i7.h0 = c0118d7.T;
            c0272i7.S = c0118d7.H;
            c0272i7.T = c0118d7.I;
            c0272i7.U = c0118d7.L;
            c0272i7.V = c0118d7.M;
            c0272i7.W = c0118d7.J;
            c0272i7.X = c0118d7.K;
            c0272i7.Y = c0118d7.N;
            c0272i7.Z = c0118d7.O;
            c0272i7.f0 = c0118d7.U;
            c0272i7.J = c0118d7.u;
            c0272i7.L = c0118d7.w;
            c0272i7.I = c0118d7.t;
            c0272i7.K = c0118d7.v;
            c0272i7.N = c0118d7.x;
            c0272i7.M = c0118d7.y;
            c0272i7.G = c0118d7.getMarginEnd();
            c0272i7.H = c0118d7.getMarginStart();
            int visibility = childAt.getVisibility();
            C0332k7 c0332k7 = c0241h7.b;
            c0332k7.a = visibility;
            c0332k7.c = childAt.getAlpha();
            float rotation = childAt.getRotation();
            C0363l7 c0363l7 = c0241h7.e;
            c0363l7.a = rotation;
            c0363l7.b = childAt.getRotationX();
            c0363l7.c = childAt.getRotationY();
            c0363l7.d = childAt.getScaleX();
            c0363l7.e = childAt.getScaleY();
            float pivotX = childAt.getPivotX();
            float pivotY = childAt.getPivotY();
            if (pivotX != 0.0d || pivotY != 0.0d) {
                c0363l7.f = pivotX;
                c0363l7.g = pivotY;
            }
            c0363l7.h = childAt.getTranslationX();
            c0363l7.i = childAt.getTranslationY();
            c0363l7.j = childAt.getTranslationZ();
            if (c0363l7.k) {
                c0363l7.l = childAt.getElevation();
            }
            if (childAt instanceof O4) {
                O4 o4 = (O4) childAt;
                c0272i7.i0 = o4.i.g0;
                c0272i7.d0 = Arrays.copyOf(o4.a, o4.b);
                c0272i7.a0 = o4.g;
                c0272i7.b0 = o4.i.h0;
            }
            i++;
            c0394m7 = this;
        }
    }

    public final void e(Context context, int i) {
        XmlResourceParser xml = context.getResources().getXml(i);
        try {
            for (int eventType = xml.getEventType(); eventType != 1; eventType = xml.next()) {
                if (eventType == 0) {
                    xml.getName();
                } else if (eventType == 2) {
                    String name = xml.getName();
                    C0241h7 c0241h7D = d(context, Xml.asAttributeSet(xml));
                    if (name.equalsIgnoreCase("Guideline")) {
                        c0241h7D.d.a = true;
                    }
                    this.c.put(Integer.valueOf(c0241h7D.a), c0241h7D);
                }
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        } catch (XmlPullParserException e3) {
            e3.printStackTrace();
        }
    }
}
