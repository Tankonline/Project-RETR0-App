package rikka.shizuku;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public abstract class Qt extends Wt {
    public static boolean i = false;
    public static Method j;
    public static Class k;
    public static Field l;
    public static Field m;
    public final WindowInsets c;
    public C0587sf[] d;
    public C0587sf e;
    public au f;
    public C0587sf g;
    public int h;

    public Qt(au auVar, WindowInsets windowInsets) {
        super(auVar);
        this.e = null;
        this.c = windowInsets;
    }

    @SuppressLint({"WrongConstant"})
    private C0587sf s(int i2, boolean z) {
        C0587sf c0587sfA = C0587sf.e;
        for (int i3 = 1; i3 <= 512; i3 <<= 1) {
            if ((i2 & i3) != 0) {
                c0587sfA = C0587sf.a(c0587sfA, t(i3, z));
            }
        }
        return c0587sfA;
    }

    private C0587sf u() {
        au auVar = this.f;
        return auVar != null ? auVar.a.h() : C0587sf.e;
    }

    private C0587sf v(View view) {
        if (Build.VERSION.SDK_INT >= 30) {
            throw new UnsupportedOperationException("getVisibleInsets() should not be called on API >= 30. Use WindowInsets.isVisible() instead.");
        }
        if (!i) {
            w();
        }
        Method method = j;
        if (method != null && k != null && l != null) {
            try {
                Object objInvoke = method.invoke(view, null);
                if (objInvoke == null) {
                    Log.w("WindowInsetsCompat", "Failed to get visible insets. getViewRootImpl() returned null from the provided view. This means that the view is either not attached or the method has been overridden", new NullPointerException());
                    return null;
                }
                Rect rect = (Rect) l.get(m.get(objInvoke));
                if (rect != null) {
                    return C0587sf.b(rect.left, rect.top, rect.right, rect.bottom);
                }
            } catch (ReflectiveOperationException e) {
                Log.e("WindowInsetsCompat", "Failed to get visible insets. (Reflection error). " + e.getMessage(), e);
            }
        }
        return null;
    }

    @SuppressLint({"PrivateApi"})
    private static void w() {
        try {
            j = View.class.getDeclaredMethod("getViewRootImpl", null);
            Class<?> cls = Class.forName("android.view.View$AttachInfo");
            k = cls;
            l = cls.getDeclaredField("mVisibleInsets");
            m = Class.forName("android.view.ViewRootImpl").getDeclaredField("mAttachInfo");
            l.setAccessible(true);
            m.setAccessible(true);
        } catch (ReflectiveOperationException e) {
            Log.e("WindowInsetsCompat", "Failed to get visible insets. (Reflection error). " + e.getMessage(), e);
        }
        i = true;
    }

    public static boolean y(int i2, int i3) {
        return (i2 & 6) == (i3 & 6);
    }

    @Override // rikka.shizuku.Wt
    public void d(View view) {
        C0587sf c0587sfV = v(view);
        if (c0587sfV == null) {
            c0587sfV = C0587sf.e;
        }
        x(c0587sfV);
    }

    @Override // rikka.shizuku.Wt
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        Qt qt = (Qt) obj;
        return Objects.equals(this.g, qt.g) && y(this.h, qt.h);
    }

    @Override // rikka.shizuku.Wt
    public C0587sf f(int i2) {
        return s(i2, false);
    }

    @Override // rikka.shizuku.Wt
    public final C0587sf j() {
        if (this.e == null) {
            WindowInsets windowInsets = this.c;
            this.e = C0587sf.b(windowInsets.getSystemWindowInsetLeft(), windowInsets.getSystemWindowInsetTop(), windowInsets.getSystemWindowInsetRight(), windowInsets.getSystemWindowInsetBottom());
        }
        return this.e;
    }

    @Override // rikka.shizuku.Wt
    public au l(int i2, int i3, int i4, int i5) {
        Pt mt;
        au auVarE = au.e(null, this.c);
        int i6 = Build.VERSION.SDK_INT;
        if (i6 >= 34) {
            mt = new Ot(auVarE);
        } else if (i6 >= 30) {
            mt = new Nt(auVarE);
        } else {
            mt = i6 >= 29 ? new Mt(auVarE) : new Kt(auVarE);
        }
        mt.g(au.c(j(), i2, i3, i4, i5));
        mt.e(au.c(h(), i2, i3, i4, i5));
        return mt.b();
    }

    @Override // rikka.shizuku.Wt
    public boolean n() {
        return this.c.isRound();
    }

    @Override // rikka.shizuku.Wt
    public void o(C0587sf[] c0587sfArr) {
        this.d = c0587sfArr;
    }

    @Override // rikka.shizuku.Wt
    public void p(au auVar) {
        this.f = auVar;
    }

    @Override // rikka.shizuku.Wt
    public void r(int i2) {
        this.h = i2;
    }

    public C0587sf t(int i2, boolean z) {
        C0587sf c0587sfH;
        int i3;
        C0587sf c0587sf = C0587sf.e;
        if (i2 != 1) {
            if (i2 != 2) {
                if (i2 == 8) {
                    C0587sf[] c0587sfArr = this.d;
                    c0587sfH = c0587sfArr != null ? c0587sfArr[Hn.h(8)] : null;
                    if (c0587sfH != null) {
                        return c0587sfH;
                    }
                    C0587sf c0587sfJ = j();
                    C0587sf c0587sfU = u();
                    int i4 = c0587sfJ.d;
                    if (i4 > c0587sfU.d) {
                        return C0587sf.b(0, 0, 0, i4);
                    }
                    C0587sf c0587sf2 = this.g;
                    if (c0587sf2 != null && !c0587sf2.equals(c0587sf) && (i3 = this.g.d) > c0587sfU.d) {
                        return C0587sf.b(0, 0, 0, i3);
                    }
                } else {
                    if (i2 == 16) {
                        return i();
                    }
                    if (i2 == 32) {
                        return g();
                    }
                    if (i2 == 64) {
                        return k();
                    }
                    if (i2 == 128) {
                        au auVar = this.f;
                        C0121da c0121daE = auVar != null ? auVar.a.e() : e();
                        if (c0121daE != null) {
                            int i5 = Build.VERSION.SDK_INT;
                            return C0587sf.b(i5 >= 28 ? G9.d(c0121daE.a) : 0, i5 >= 28 ? G9.f(c0121daE.a) : 0, i5 >= 28 ? G9.e(c0121daE.a) : 0, i5 >= 28 ? G9.c(c0121daE.a) : 0);
                        }
                    }
                }
            } else {
                if (z) {
                    C0587sf c0587sfU2 = u();
                    C0587sf c0587sfH2 = h();
                    return C0587sf.b(Math.max(c0587sfU2.a, c0587sfH2.a), 0, Math.max(c0587sfU2.c, c0587sfH2.c), Math.max(c0587sfU2.d, c0587sfH2.d));
                }
                if ((this.h & 2) == 0) {
                    C0587sf c0587sfJ2 = j();
                    au auVar2 = this.f;
                    c0587sfH = auVar2 != null ? auVar2.a.h() : null;
                    int iMin = c0587sfJ2.d;
                    if (c0587sfH != null) {
                        iMin = Math.min(iMin, c0587sfH.d);
                    }
                    return C0587sf.b(c0587sfJ2.a, 0, c0587sfJ2.c, iMin);
                }
            }
        } else {
            if (z) {
                return C0587sf.b(0, Math.max(u().b, j().b), 0, 0);
            }
            if ((this.h & 4) == 0) {
                return C0587sf.b(0, j().b, 0, 0);
            }
        }
        return c0587sf;
    }

    public void x(C0587sf c0587sf) {
        this.g = c0587sf;
    }
}
