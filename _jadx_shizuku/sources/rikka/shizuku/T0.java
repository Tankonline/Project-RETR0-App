package rikka.shizuku;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public final class T0 {
    public int a;
    public final Object b;
    public final Object c;
    public Object d;
    public Object e;
    public Object f;

    public T0(View view) {
        this.a = -1;
        this.b = view;
        this.c = L2.a();
    }

    public void a() {
        View view = (View) this.b;
        Drawable background = view.getBackground();
        if (background != null) {
            if (((Tp) this.d) != null) {
                if (((Tp) this.f) == null) {
                    this.f = new Tp();
                }
                Tp tp = (Tp) this.f;
                tp.a = null;
                tp.d = false;
                tp.b = null;
                tp.c = false;
                WeakHashMap weakHashMap = Hs.a;
                ColorStateList colorStateListC = AbstractC0817zs.c(view);
                if (colorStateListC != null) {
                    tp.d = true;
                    tp.a = colorStateListC;
                }
                PorterDuff.Mode modeD = AbstractC0817zs.d(view);
                if (modeD != null) {
                    tp.c = true;
                    tp.b = modeD;
                }
                if (tp.d || tp.c) {
                    L2.e(background, tp, view.getDrawableState());
                    return;
                }
            }
            Tp tp2 = (Tp) this.e;
            if (tp2 != null) {
                L2.e(background, tp2, view.getDrawableState());
                return;
            }
            Tp tp3 = (Tp) this.d;
            if (tp3 != null) {
                L2.e(background, tp3, view.getDrawableState());
            }
        }
    }

    public boolean b(int i) {
        ArrayList arrayList = (ArrayList) this.d;
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            S0 s0 = (S0) arrayList.get(i2);
            int i3 = s0.a;
            if (i3 != 8) {
                if (i3 == 1) {
                    int i4 = s0.b;
                    int i5 = s0.d + i4;
                    while (i4 < i5) {
                        if (g(i4, i2 + 1) == i) {
                            return true;
                        }
                        i4++;
                    }
                } else {
                    continue;
                }
            } else {
                if (g(s0.d, i2 + 1) == i) {
                    return true;
                }
            }
        }
        return false;
    }

    public void c() {
        ArrayList arrayList = (ArrayList) this.d;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            ((Xk) this.e).a((S0) arrayList.get(i));
        }
        o(arrayList);
        this.a = 0;
    }

    public void d() {
        c();
        ArrayList arrayList = (ArrayList) this.c;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            S0 s0 = (S0) arrayList.get(i);
            int i2 = s0.a;
            Xk xk = (Xk) this.e;
            if (i2 == 1) {
                xk.a(s0);
                xk.e(s0.b, s0.d);
            } else if (i2 == 2) {
                xk.a(s0);
                int i3 = s0.b;
                int i4 = s0.d;
                RecyclerView recyclerView = xk.a;
                recyclerView.S(i3, i4, true);
                recyclerView.i0 = true;
                recyclerView.g0.c += i4;
            } else if (i2 == 4) {
                xk.a(s0);
                xk.d(s0.b, s0.d, s0.c);
            } else if (i2 == 8) {
                xk.a(s0);
                xk.f(s0.b, s0.d);
            }
        }
        o(arrayList);
        this.a = 0;
    }

    public void e(S0 s0) {
        int i;
        Yj yj;
        int i2 = s0.a;
        if (i2 == 1 || i2 == 8) {
            throw new IllegalArgumentException("should not dispatch add or move for pre layout");
        }
        int iQ = q(s0.b, i2);
        int i3 = s0.b;
        int i4 = s0.a;
        if (i4 == 2) {
            i = 0;
        } else {
            if (i4 != 4) {
                throw new IllegalArgumentException("op should be remove or update." + s0);
            }
            i = 1;
        }
        int i5 = 1;
        int i6 = 1;
        while (true) {
            int i7 = s0.d;
            yj = (Yj) this.b;
            if (i5 >= i7) {
                break;
            }
            int iQ2 = q((i * i5) + s0.b, s0.a);
            int i8 = s0.a;
            if (i8 == 2 ? iQ2 != iQ : !(i8 == 4 && iQ2 == iQ + 1)) {
                S0 s0J = j(s0.c, i8, iQ, i6);
                f(s0J, i3);
                s0J.c = null;
                yj.c(s0J);
                if (s0.a == 4) {
                    i3 += i6;
                }
                i6 = 1;
                iQ = iQ2;
            } else {
                i6++;
            }
            i5++;
        }
        Object obj = s0.c;
        s0.c = null;
        yj.c(s0);
        if (i6 > 0) {
            S0 s0J2 = j(obj, s0.a, iQ, i6);
            f(s0J2, i3);
            s0J2.c = null;
            yj.c(s0J2);
        }
    }

    public void f(S0 s0, int i) {
        Xk xk = (Xk) this.e;
        xk.a(s0);
        int i2 = s0.a;
        if (i2 != 2) {
            if (i2 != 4) {
                throw new IllegalArgumentException("only remove and update ops can be dispatched in first pass");
            }
            xk.d(i, s0.d, s0.c);
        } else {
            int i3 = s0.d;
            RecyclerView recyclerView = xk.a;
            recyclerView.S(i, i3, true);
            recyclerView.i0 = true;
            recyclerView.g0.c += i3;
        }
    }

    public int g(int i, int i2) {
        ArrayList arrayList = (ArrayList) this.d;
        int size = arrayList.size();
        while (i2 < size) {
            S0 s0 = (S0) arrayList.get(i2);
            int i3 = s0.a;
            if (i3 == 8) {
                int i4 = s0.b;
                if (i4 == i) {
                    i = s0.d;
                } else {
                    if (i4 < i) {
                        i--;
                    }
                    if (s0.d <= i) {
                        i++;
                    }
                }
            } else {
                int i5 = s0.b;
                if (i5 > i) {
                    continue;
                } else if (i3 == 2) {
                    int i6 = s0.d;
                    if (i < i5 + i6) {
                        return -1;
                    }
                    i -= i6;
                } else if (i3 == 1) {
                    i += s0.d;
                }
            }
            i2++;
        }
        return i;
    }

    public boolean h() {
        return ((ArrayList) this.c).size() > 0;
    }

    public void i(AttributeSet attributeSet, int i) {
        ColorStateList colorStateListF;
        View view = (View) this.b;
        Context context = view.getContext();
        int[] iArr = Nk.A;
        K3 k3N = K3.n(context, attributeSet, iArr, i);
        TypedArray typedArray = (TypedArray) k3N.a;
        View view2 = (View) this.b;
        Hs.l(view2, view2.getContext(), iArr, attributeSet, (TypedArray) k3N.a, i);
        try {
            if (typedArray.hasValue(0)) {
                this.a = typedArray.getResourceId(0, -1);
                L2 l2 = (L2) this.c;
                Context context2 = view.getContext();
                int i2 = this.a;
                synchronized (l2) {
                    colorStateListF = l2.a.f(context2, i2);
                }
                if (colorStateListF != null) {
                    p(colorStateListF);
                }
            }
            if (typedArray.hasValue(1)) {
                AbstractC0817zs.i(view, k3N.h(1));
            }
            if (typedArray.hasValue(2)) {
                AbstractC0817zs.j(view, AbstractC0304ja.c(typedArray.getInt(2, -1), null));
            }
            k3N.o();
        } catch (Throwable th) {
            k3N.o();
            throw th;
        }
    }

    public S0 j(Object obj, int i, int i2, int i3) {
        S0 s0 = (S0) ((Yj) this.b).a();
        if (s0 != null) {
            s0.a = i;
            s0.b = i2;
            s0.d = i3;
            s0.c = obj;
            return s0;
        }
        S0 s1 = new S0();
        s1.a = i;
        s1.b = i2;
        s1.d = i3;
        s1.c = obj;
        return s1;
    }

    public void k() {
        this.a = -1;
        p(null);
        a();
    }

    public void l(int i) {
        ColorStateList colorStateListF;
        this.a = i;
        L2 l2 = (L2) this.c;
        if (l2 != null) {
            Context context = ((View) this.b).getContext();
            synchronized (l2) {
                colorStateListF = l2.a.f(context, i);
            }
        } else {
            colorStateListF = null;
        }
        p(colorStateListF);
        a();
    }

    public void m(S0 s0) {
        ((ArrayList) this.d).add(s0);
        int i = s0.a;
        Xk xk = (Xk) this.e;
        if (i == 1) {
            xk.e(s0.b, s0.d);
            return;
        }
        if (i == 2) {
            int i2 = s0.b;
            int i3 = s0.d;
            RecyclerView recyclerView = xk.a;
            recyclerView.S(i2, i3, false);
            recyclerView.i0 = true;
            return;
        }
        if (i == 4) {
            xk.d(s0.b, s0.d, s0.c);
        } else if (i == 8) {
            xk.f(s0.b, s0.d);
        } else {
            throw new IllegalArgumentException("Unknown update op type for " + s0);
        }
    }

    /* JADX WARN: Code duplicated, block: B:101:0x018f  */
    /* JADX WARN: Code duplicated, block: B:104:0x019d  */
    /* JADX WARN: Code duplicated, block: B:105:0x01a1  */
    /* JADX WARN: Code duplicated, block: B:184:0x00aa A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:185:0x0135 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:188:0x0121 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:189:0x01a6 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:198:0x000d A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:202:0x000d A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:30:0x006f  */
    /* JADX WARN: Code duplicated, block: B:31:0x0074  */
    /* JADX WARN: Code duplicated, block: B:33:0x0079  */
    /* JADX WARN: Code duplicated, block: B:37:0x0090  */
    /* JADX WARN: Code duplicated, block: B:38:0x0094  */
    /* JADX WARN: Code duplicated, block: B:40:0x00a5  */
    /* JADX WARN: Code duplicated, block: B:76:0x0137  */
    /* JADX WARN: Code duplicated, block: B:77:0x0139  */
    /* JADX WARN: Code duplicated, block: B:79:0x013f  */
    /* JADX WARN: Code duplicated, block: B:82:0x014a  */
    /* JADX WARN: Code duplicated, block: B:85:0x0155  */
    /* JADX WARN: Code duplicated, block: B:88:0x0160  */
    /* JADX WARN: Code duplicated, block: B:89:0x0166  */
    /* JADX WARN: Code duplicated, block: B:90:0x0168  */
    /* JADX WARN: Code duplicated, block: B:92:0x016e  */
    /* JADX WARN: Code duplicated, block: B:95:0x0179  */
    /* JADX WARN: Code duplicated, block: B:98:0x0184  */
    public void n() {
        byte b;
        int i;
        boolean z;
        byte b2;
        S0 s0J;
        int i2;
        int i3;
        int i4;
        S0 s0J2;
        boolean z2;
        boolean z3;
        S0 s0J3;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        ArrayList arrayList = (ArrayList) this.c;
        Ys ys = (Ys) this.f;
        ys.getClass();
        while (true) {
            int size = arrayList.size() - 1;
            boolean z4 = false;
            while (true) {
                b = -1;
                i = 8;
                if (size < 0) {
                    size = -1;
                    break;
                }
                if (((S0) arrayList.get(size)).a != 8) {
                    z4 = true;
                } else if (z4) {
                    break;
                }
                size--;
            }
            if (size == -1) {
                break;
            }
            int i13 = size + 1;
            S0 s0 = (S0) arrayList.get(size);
            S0 s1 = (S0) arrayList.get(i13);
            int i14 = s1.a;
            if (i14 != 1) {
                T0 t0 = (T0) ys.b;
                if (i14 == 2) {
                    int i15 = s0.b;
                    int i16 = s0.d;
                    if (i15 < i16) {
                        z3 = s1.b == i15 && s1.d == i16 - i15;
                        z2 = false;
                    } else if (s1.b == i16 + 1 && s1.d == i15 - i16) {
                        z3 = true;
                        z2 = true;
                    } else {
                        z2 = true;
                        z3 = false;
                    }
                    int i17 = s1.b;
                    if (i16 < i17) {
                        s1.b = i17 - 1;
                    } else {
                        int i18 = s1.d;
                        if (i16 < i17 + i18) {
                            s1.d = i18 - 1;
                            s0.a = 2;
                            s0.d = 1;
                            if (s1.d == 0) {
                                arrayList.remove(i13);
                                t0.getClass();
                                s1.c = null;
                                ((Yj) t0.b).c(s1);
                            }
                        }
                    }
                    int i19 = s0.b;
                    int i20 = s1.b;
                    if (i19 <= i20) {
                        s1.b = i20 + 1;
                    } else {
                        int i21 = i20 + s1.d;
                        if (i19 < i21) {
                            s0J3 = t0.j(null, 2, i19 + 1, i21 - i19);
                            s1.d = s0.b - s1.b;
                        }
                        if (z3) {
                            arrayList.set(size, s1);
                            arrayList.remove(i13);
                            t0.getClass();
                            s0.c = null;
                            ((Yj) t0.b).c(s0);
                        } else {
                            if (z2) {
                                if (s0J3 != null) {
                                    i11 = s0.b;
                                    if (i11 > s0J3.b) {
                                        s0.b = i11 - s0J3.d;
                                    }
                                    i12 = s0.d;
                                    if (i12 > s0J3.b) {
                                        s0.d = i12 - s0J3.d;
                                    }
                                }
                                i9 = s0.b;
                                if (i9 > s1.b) {
                                    s0.b = i9 - s1.d;
                                }
                                i10 = s0.d;
                                if (i10 > s1.b) {
                                    s0.d = i10 - s1.d;
                                }
                            } else {
                                if (s0J3 != null) {
                                    i7 = s0.b;
                                    if (i7 >= s0J3.b) {
                                        s0.b = i7 - s0J3.d;
                                    }
                                    i8 = s0.d;
                                    if (i8 >= s0J3.b) {
                                        s0.d = i8 - s0J3.d;
                                    }
                                }
                                i5 = s0.b;
                                if (i5 >= s1.b) {
                                    s0.b = i5 - s1.d;
                                }
                                i6 = s0.d;
                                if (i6 >= s1.b) {
                                    s0.d = i6 - s1.d;
                                }
                            }
                            arrayList.set(size, s1);
                            if (s0.b != s0.d) {
                                arrayList.set(i13, s0);
                            } else {
                                arrayList.remove(i13);
                            }
                            if (s0J3 != null) {
                                arrayList.add(size, s0J3);
                            }
                        }
                    }
                    s0J3 = null;
                    if (z3) {
                        arrayList.set(size, s1);
                        arrayList.remove(i13);
                        t0.getClass();
                        s0.c = null;
                        ((Yj) t0.b).c(s0);
                    } else {
                        if (z2) {
                            if (s0J3 != null) {
                                i11 = s0.b;
                                if (i11 > s0J3.b) {
                                    s0.b = i11 - s0J3.d;
                                }
                                i12 = s0.d;
                                if (i12 > s0J3.b) {
                                    s0.d = i12 - s0J3.d;
                                }
                            }
                            i9 = s0.b;
                            if (i9 > s1.b) {
                                s0.b = i9 - s1.d;
                            }
                            i10 = s0.d;
                            if (i10 > s1.b) {
                                s0.d = i10 - s1.d;
                            }
                        } else {
                            if (s0J3 != null) {
                                i7 = s0.b;
                                if (i7 >= s0J3.b) {
                                    s0.b = i7 - s0J3.d;
                                }
                                i8 = s0.d;
                                if (i8 >= s0J3.b) {
                                    s0.d = i8 - s0J3.d;
                                }
                            }
                            i5 = s0.b;
                            if (i5 >= s1.b) {
                                s0.b = i5 - s1.d;
                            }
                            i6 = s0.d;
                            if (i6 >= s1.b) {
                                s0.d = i6 - s1.d;
                            }
                        }
                        arrayList.set(size, s1);
                        if (s0.b != s0.d) {
                            arrayList.set(i13, s0);
                        } else {
                            arrayList.remove(i13);
                        }
                        if (s0J3 != null) {
                            arrayList.add(size, s0J3);
                        }
                    }
                } else if (i14 == 4) {
                    int i22 = s0.d;
                    int i23 = s1.b;
                    if (i22 < i23) {
                        s1.b = i23 - 1;
                    } else {
                        int i24 = s1.d;
                        if (i22 < i23 + i24) {
                            s1.d = i24 - 1;
                            s0J = t0.j(s1.c, 4, s0.b, 1);
                        }
                        i2 = s0.b;
                        i3 = s1.b;
                        if (i2 <= i3) {
                            s1.b = i3 + 1;
                        } else {
                            i4 = i3 + s1.d;
                            if (i2 < i4) {
                                int i25 = i4 - i2;
                                s0J2 = t0.j(s1.c, 4, i2 + 1, i25);
                                s1.d -= i25;
                            }
                            arrayList.set(i13, s0);
                            if (s1.d > 0) {
                                arrayList.set(size, s1);
                            } else {
                                arrayList.remove(size);
                                t0.getClass();
                                s1.c = null;
                                ((Yj) t0.b).c(s1);
                            }
                            if (s0J != null) {
                                arrayList.add(size, s0J);
                            }
                            if (s0J2 != null) {
                                arrayList.add(size, s0J2);
                            }
                        }
                        s0J2 = null;
                        arrayList.set(i13, s0);
                        if (s1.d > 0) {
                            arrayList.set(size, s1);
                        } else {
                            arrayList.remove(size);
                            t0.getClass();
                            s1.c = null;
                            ((Yj) t0.b).c(s1);
                        }
                        if (s0J != null) {
                            arrayList.add(size, s0J);
                        }
                        if (s0J2 != null) {
                            arrayList.add(size, s0J2);
                        }
                    }
                    s0J = null;
                    i2 = s0.b;
                    i3 = s1.b;
                    if (i2 <= i3) {
                        s1.b = i3 + 1;
                    } else {
                        i4 = i3 + s1.d;
                        if (i2 < i4) {
                            int i26 = i4 - i2;
                            s0J2 = t0.j(s1.c, 4, i2 + 1, i26);
                            s1.d -= i26;
                        }
                        arrayList.set(i13, s0);
                        if (s1.d > 0) {
                            arrayList.set(size, s1);
                        } else {
                            arrayList.remove(size);
                            t0.getClass();
                            s1.c = null;
                            ((Yj) t0.b).c(s1);
                        }
                        if (s0J != null) {
                            arrayList.add(size, s0J);
                        }
                        if (s0J2 != null) {
                            arrayList.add(size, s0J2);
                        }
                    }
                    s0J2 = null;
                    arrayList.set(i13, s0);
                    if (s1.d > 0) {
                        arrayList.set(size, s1);
                    } else {
                        arrayList.remove(size);
                        t0.getClass();
                        s1.c = null;
                        ((Yj) t0.b).c(s1);
                    }
                    if (s0J != null) {
                        arrayList.add(size, s0J);
                    }
                    if (s0J2 != null) {
                        arrayList.add(size, s0J2);
                    }
                }
            } else {
                int i27 = s0.d;
                int i28 = s1.b;
                int i29 = i27 < i28 ? -1 : 0;
                int i30 = s0.b;
                if (i30 < i28) {
                    i29++;
                }
                if (i28 <= i30) {
                    s0.b = i30 + s1.d;
                }
                int i31 = s1.b;
                if (i31 <= i27) {
                    s0.d = i27 + s1.d;
                }
                s1.b = i31 + i29;
                arrayList.set(size, s1);
                arrayList.set(i13, s0);
            }
        }
        int size2 = arrayList.size();
        int i32 = 0;
        while (i32 < size2) {
            S0 s0J4 = (S0) arrayList.get(i32);
            int i33 = s0J4.a;
            if (i33 != 1) {
                Yj yj = (Yj) this.b;
                Xk xk = (Xk) this.e;
                if (i33 == 2) {
                    int i34 = s0J4.b;
                    int i35 = s0J4.d + i34;
                    int i36 = i34;
                    int i37 = 0;
                    byte b3 = -1;
                    while (i36 < i35) {
                        if (xk.c(i36) != null || b(i36)) {
                            if (b3 == 0) {
                                e(j(null, 2, i34, i37));
                                z = true;
                            } else {
                                z = false;
                            }
                            b2 = 1;
                        } else {
                            if (b3 == 1) {
                                m(j(null, 2, i34, i37));
                                z = true;
                            } else {
                                z = false;
                            }
                            b2 = 0;
                        }
                        if (z) {
                            i36 -= i37;
                            i35 -= i37;
                            i37 = 1;
                        } else {
                            i37++;
                        }
                        i36++;
                        b3 = b2;
                    }
                    if (i37 != s0J4.d) {
                        s0J4.c = null;
                        yj.c(s0J4);
                        s0J4 = j(null, 2, i34, i37);
                    }
                    if (b3 == 0) {
                        e(s0J4);
                    } else {
                        m(s0J4);
                    }
                } else if (i33 == 4) {
                    int i38 = s0J4.b;
                    int i39 = s0J4.d + i38;
                    byte b4 = b;
                    int i40 = i38;
                    int i41 = 0;
                    while (i38 < i39) {
                        if (xk.c(i38) != null || b(i38)) {
                            if (b4 == 0) {
                                e(j(s0J4.c, 4, i40, i41));
                                i40 = i38;
                                i41 = 0;
                            }
                            b4 = 1;
                        } else {
                            if (b4 == 1) {
                                m(j(s0J4.c, 4, i40, i41));
                                i40 = i38;
                                i41 = 0;
                            }
                            b4 = 0;
                        }
                        i41++;
                        i38++;
                    }
                    if (i41 != s0J4.d) {
                        Object obj = s0J4.c;
                        s0J4.c = null;
                        yj.c(s0J4);
                        s0J4 = j(obj, 4, i40, i41);
                    }
                    if (b4 == 0) {
                        e(s0J4);
                    } else {
                        m(s0J4);
                    }
                } else if (i33 == i) {
                    m(s0J4);
                }
            } else {
                m(s0J4);
            }
            i32++;
            b = -1;
            i = 8;
        }
        arrayList.clear();
    }

    public void o(ArrayList arrayList) {
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            S0 s0 = (S0) arrayList.get(i);
            s0.c = null;
            ((Yj) this.b).c(s0);
        }
        arrayList.clear();
    }

    public void p(ColorStateList colorStateList) {
        if (colorStateList != null) {
            if (((Tp) this.d) == null) {
                this.d = new Tp();
            }
            Tp tp = (Tp) this.d;
            tp.a = colorStateList;
            tp.d = true;
        } else {
            this.d = null;
        }
        a();
    }

    public int q(int i, int i2) {
        int i3;
        int i4;
        ArrayList arrayList = (ArrayList) this.d;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            S0 s0 = (S0) arrayList.get(size);
            int i5 = s0.a;
            if (i5 == 8) {
                int i6 = s0.b;
                int i7 = s0.d;
                if (i6 < i7) {
                    i4 = i6;
                    i3 = i7;
                } else {
                    i3 = i6;
                    i4 = i7;
                }
                if (i < i4 || i > i3) {
                    if (i < i6) {
                        if (i2 == 1) {
                            s0.b = i6 + 1;
                            s0.d = i7 + 1;
                        } else if (i2 == 2) {
                            s0.b = i6 - 1;
                            s0.d = i7 - 1;
                        }
                    }
                } else if (i4 == i6) {
                    if (i2 == 1) {
                        s0.d = i7 + 1;
                    } else if (i2 == 2) {
                        s0.d = i7 - 1;
                    }
                    i++;
                } else {
                    if (i2 == 1) {
                        s0.b = i6 + 1;
                    } else if (i2 == 2) {
                        s0.b = i6 - 1;
                    }
                    i--;
                }
            } else {
                int i8 = s0.b;
                if (i8 <= i) {
                    if (i5 == 1) {
                        i -= s0.d;
                    } else if (i5 == 2) {
                        i += s0.d;
                    }
                } else if (i2 == 1) {
                    s0.b = i8 + 1;
                } else if (i2 == 2) {
                    s0.b = i8 - 1;
                }
            }
        }
        for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
            S0 s1 = (S0) arrayList.get(size2);
            int i9 = s1.a;
            Yj yj = (Yj) this.b;
            if (i9 == 8) {
                int i10 = s1.d;
                if (i10 == s1.b || i10 < 0) {
                    arrayList.remove(size2);
                    s1.c = null;
                    yj.c(s1);
                }
            } else if (s1.d <= 0) {
                arrayList.remove(size2);
                s1.c = null;
                yj.c(s1);
            }
        }
        return i;
    }

    public T0(Xk xk) {
        this.b = new Yj(30);
        this.c = new ArrayList();
        this.d = new ArrayList();
        this.a = 0;
        this.e = xk;
        this.f = new Ys(27, this);
    }
}
