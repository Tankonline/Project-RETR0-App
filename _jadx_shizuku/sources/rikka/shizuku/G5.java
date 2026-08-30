package rikka.shizuku;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.TextView;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.WeakHashMap;
import moe.shizuku.privileged.api.R;

/* JADX INFO: loaded from: classes.dex */
public final class G5 extends AbstractC0374li implements View.OnKeyListener, PopupWindow.OnDismissListener {
    public final Context b;
    public final int c;
    public final int d;
    public final boolean e;
    public final Handler f;
    public final Y2 i;
    public final ViewOnAttachStateChangeListenerC0511q0 j;
    public View n;
    public View o;
    public int p;
    public boolean q;
    public boolean r;
    public int s;
    public int t;
    public boolean v;
    public InterfaceC0652ui w;
    public ViewTreeObserver x;
    public C0405mi y;
    public boolean z;
    public final ArrayList g = new ArrayList();
    public final ArrayList h = new ArrayList();
    public final Ys k = new Ys(10, this);
    public int l = 0;
    public int m = 0;
    public boolean u = false;

    public G5(Context context, View view, int i, boolean z) {
        int i2 = 2;
        this.i = new Y2(i2, this);
        this.j = new ViewOnAttachStateChangeListenerC0511q0(i2, this);
        this.b = context;
        this.n = view;
        this.d = i;
        this.e = z;
        WeakHashMap weakHashMap = Hs.a;
        this.p = view.getLayoutDirection() != 1 ? 1 : 0;
        Resources resources = context.getResources();
        this.c = Math.max(resources.getDisplayMetrics().widthPixels / 2, resources.getDimensionPixelSize(R.dimen._0_resource_name_obfuscated_res_0x7f070017));
        this.f = new Handler();
    }

    @Override // rikka.shizuku.Yn
    public final boolean a() {
        ArrayList arrayList = this.h;
        return arrayList.size() > 0 && ((F5) arrayList.get(0)).a.z.isShowing();
    }

    @Override // rikka.shizuku.InterfaceC0683vi
    public final void b(MenuC0129di menuC0129di, boolean z) {
        ArrayList arrayList = this.h;
        int size = arrayList.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                i = -1;
                break;
            } else if (menuC0129di == ((F5) arrayList.get(i)).b) {
                break;
            } else {
                i++;
            }
        }
        if (i < 0) {
            return;
        }
        int i2 = i + 1;
        if (i2 < arrayList.size()) {
            ((F5) arrayList.get(i2)).b.c(false);
        }
        F5 f5 = (F5) arrayList.remove(i);
        f5.b.r(this);
        boolean z2 = this.z;
        C0621ti c0621ti = f5.a;
        if (z2) {
            AbstractC0498pi.b(c0621ti.z, null);
            c0621ti.z.setAnimationStyle(0);
        }
        c0621ti.dismiss();
        int size2 = arrayList.size();
        if (size2 > 0) {
            this.p = ((F5) arrayList.get(size2 - 1)).c;
        } else {
            View view = this.n;
            WeakHashMap weakHashMap = Hs.a;
            this.p = view.getLayoutDirection() == 1 ? 0 : 1;
        }
        if (size2 != 0) {
            if (z) {
                ((F5) arrayList.get(0)).b.c(false);
                return;
            }
            return;
        }
        dismiss();
        InterfaceC0652ui interfaceC0652ui = this.w;
        if (interfaceC0652ui != null) {
            interfaceC0652ui.b(menuC0129di, true);
        }
        ViewTreeObserver viewTreeObserver = this.x;
        if (viewTreeObserver != null) {
            if (viewTreeObserver.isAlive()) {
                this.x.removeGlobalOnLayoutListener(this.i);
            }
            this.x = null;
        }
        this.o.removeOnAttachStateChangeListener(this.j);
        this.y.onDismiss();
    }

    @Override // rikka.shizuku.InterfaceC0683vi
    public final void c(InterfaceC0652ui interfaceC0652ui) {
        this.w = interfaceC0652ui;
    }

    @Override // rikka.shizuku.Yn
    public final void dismiss() {
        ArrayList arrayList = this.h;
        int size = arrayList.size();
        if (size > 0) {
            F5[] f5Arr = (F5[]) arrayList.toArray(new F5[size]);
            for (int i = size - 1; i >= 0; i--) {
                F5 f5 = f5Arr[i];
                if (f5.a.z.isShowing()) {
                    f5.a.dismiss();
                }
            }
        }
    }

    @Override // rikka.shizuku.Yn
    public final void e() {
        if (a()) {
            return;
        }
        ArrayList arrayList = this.g;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            v((MenuC0129di) obj);
        }
        arrayList.clear();
        View view = this.n;
        this.o = view;
        if (view != null) {
            boolean z = this.x == null;
            ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
            this.x = viewTreeObserver;
            if (z) {
                viewTreeObserver.addOnGlobalLayoutListener(this.i);
            }
            this.o.addOnAttachStateChangeListener(this.j);
        }
    }

    @Override // rikka.shizuku.InterfaceC0683vi
    public final boolean f(SubMenuC0166ep subMenuC0166ep) {
        ArrayList arrayList = this.h;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            F5 f5 = (F5) obj;
            if (subMenuC0166ep == f5.b) {
                f5.a.c.requestFocus();
                return true;
            }
        }
        if (!subMenuC0166ep.hasVisibleItems()) {
            return false;
        }
        l(subMenuC0166ep);
        InterfaceC0652ui interfaceC0652ui = this.w;
        if (interfaceC0652ui != null) {
            interfaceC0652ui.c(subMenuC0166ep);
        }
        return true;
    }

    @Override // rikka.shizuku.InterfaceC0683vi
    public final boolean g() {
        return false;
    }

    @Override // rikka.shizuku.InterfaceC0683vi
    public final void i() {
        ArrayList arrayList = this.h;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ListAdapter adapter = ((F5) obj).a.c.getAdapter();
            if (adapter instanceof HeaderViewListAdapter) {
                adapter = ((HeaderViewListAdapter) adapter).getWrappedAdapter();
            }
            ((C0036ai) adapter).notifyDataSetChanged();
        }
    }

    @Override // rikka.shizuku.Yn
    public final C0490pa j() {
        ArrayList arrayList = this.h;
        if (arrayList.isEmpty()) {
            return null;
        }
        return ((F5) arrayList.get(arrayList.size() - 1)).a.c;
    }

    @Override // rikka.shizuku.AbstractC0374li
    public final void l(MenuC0129di menuC0129di) {
        menuC0129di.b(this, this.b);
        if (a()) {
            v(menuC0129di);
        } else {
            this.g.add(menuC0129di);
        }
    }

    @Override // rikka.shizuku.AbstractC0374li
    public final void n(View view) {
        if (this.n != view) {
            this.n = view;
            int i = this.l;
            WeakHashMap weakHashMap = Hs.a;
            this.m = Gravity.getAbsoluteGravity(i, view.getLayoutDirection());
        }
    }

    @Override // rikka.shizuku.AbstractC0374li
    public final void o(boolean z) {
        this.u = z;
    }

    @Override // android.widget.PopupWindow.OnDismissListener
    public final void onDismiss() {
        F5 f5;
        ArrayList arrayList = this.h;
        int size = arrayList.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                f5 = null;
                break;
            }
            f5 = (F5) arrayList.get(i);
            if (!f5.a.z.isShowing()) {
                break;
            } else {
                i++;
            }
        }
        if (f5 != null) {
            f5.b.c(false);
        }
    }

    @Override // android.view.View.OnKeyListener
    public final boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() != 1 || i != 82) {
            return false;
        }
        dismiss();
        return true;
    }

    @Override // rikka.shizuku.AbstractC0374li
    public final void p(int i) {
        if (this.l != i) {
            this.l = i;
            View view = this.n;
            WeakHashMap weakHashMap = Hs.a;
            this.m = Gravity.getAbsoluteGravity(i, view.getLayoutDirection());
        }
    }

    @Override // rikka.shizuku.AbstractC0374li
    public final void q(int i) {
        this.q = true;
        this.s = i;
    }

    @Override // rikka.shizuku.AbstractC0374li
    public final void r(PopupWindow.OnDismissListener onDismissListener) {
        this.y = (C0405mi) onDismissListener;
    }

    @Override // rikka.shizuku.AbstractC0374li
    public final void s(boolean z) {
        this.v = z;
    }

    @Override // rikka.shizuku.AbstractC0374li
    public final void t(int i) {
        this.r = true;
        this.t = i;
    }

    /* JADX WARN: Code duplicated, block: B:102:0x00fb A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:46:0x00ef  */
    /* JADX WARN: Code duplicated, block: B:48:0x00f7  */
    /* JADX WARN: Code duplicated, block: B:53:0x010d  */
    /* JADX WARN: Code duplicated, block: B:56:0x013c  */
    /* JADX WARN: Code duplicated, block: B:58:0x0148  */
    /* JADX WARN: Code duplicated, block: B:60:0x014b  */
    /* JADX WARN: Code duplicated, block: B:61:0x014d  */
    /* JADX WARN: Code duplicated, block: B:65:0x0155  */
    /* JADX WARN: Code duplicated, block: B:66:0x0157  */
    /* JADX WARN: Code duplicated, block: B:69:0x0161  */
    /* JADX WARN: Code duplicated, block: B:70:0x0166  */
    /* JADX WARN: Code duplicated, block: B:72:0x0179  */
    /* JADX WARN: Code duplicated, block: B:76:0x019e A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:77:0x01a0  */
    /* JADX WARN: Code duplicated, block: B:78:0x01a2  */
    /* JADX WARN: Code duplicated, block: B:79:0x01a6 A[PHI: r5
  0x01a6: PHI (r5v13 int) = (r5v5 int), (r5v14 int) binds: [B:80:0x01a8, B:78:0x01a2] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:80:0x01a8 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:81:0x01aa  */
    /* JADX WARN: Code duplicated, block: B:83:0x01ba  */
    /* JADX WARN: Code duplicated, block: B:85:0x01be  */
    /* JADX WARN: Code duplicated, block: B:88:0x01c6  */
    /* JADX WARN: Code duplicated, block: B:91:0x01cf  */
    /* JADX WARN: Code duplicated, block: B:92:0x01d5  */
    public final void v(MenuC0129di menuC0129di) {
        int i;
        F5 f5;
        View childAt;
        Rect rect;
        Rect rect2;
        int i2;
        Q2 q2;
        C0490pa c0490pa;
        int[] iArr;
        Rect rect3;
        int i3;
        boolean z;
        int[] iArr2;
        int[] iArr3;
        int i4;
        int i5;
        int width;
        Method method;
        MenuItem item;
        C0036ai c0036ai;
        int headersCount;
        int firstVisiblePosition;
        Context context = this.b;
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(context);
        C0036ai c0036ai2 = new C0036ai(menuC0129di, layoutInflaterFrom, this.e, R.layout._0_resource_name_obfuscated_res_0x7f0c000b);
        if (!a() && this.u) {
            c0036ai2.c = true;
        } else if (a()) {
            c0036ai2.c = AbstractC0374li.u(menuC0129di);
        }
        int iM = AbstractC0374li.m(c0036ai2, context, this.c);
        C0621ti c0621ti = new C0621ti(context, null, this.d);
        c0621ti.C = this.k;
        c0621ti.p = this;
        c0621ti.z.setOnDismissListener(this);
        c0621ti.o = this.n;
        c0621ti.l = this.m;
        c0621ti.y = true;
        c0621ti.z.setFocusable(true);
        c0621ti.z.setInputMethodMode(2);
        c0621ti.m(c0036ai2);
        c0621ti.p(iM);
        c0621ti.l = this.m;
        ArrayList arrayList = this.h;
        if (arrayList.size() > 0) {
            f5 = (F5) arrayList.get(arrayList.size() - 1);
            MenuC0129di menuC0129di2 = f5.b;
            int size = menuC0129di2.f.size();
            int i6 = 0;
            while (true) {
                if (i6 >= size) {
                    item = null;
                    break;
                }
                item = menuC0129di2.getItem(i6);
                if (item.hasSubMenu() && menuC0129di == item.getSubMenu()) {
                    break;
                } else {
                    i6++;
                }
            }
            if (item == null) {
                i = 1;
                childAt = null;
            } else {
                C0490pa c0490pa2 = f5.a.c;
                ListAdapter adapter = c0490pa2.getAdapter();
                if (adapter instanceof HeaderViewListAdapter) {
                    HeaderViewListAdapter headerViewListAdapter = (HeaderViewListAdapter) adapter;
                    headersCount = headerViewListAdapter.getHeadersCount();
                    c0036ai = (C0036ai) headerViewListAdapter.getWrappedAdapter();
                } else {
                    c0036ai = (C0036ai) adapter;
                    headersCount = 0;
                }
                int count = c0036ai.getCount();
                i = 1;
                int i7 = 0;
                while (true) {
                    if (i7 >= count) {
                        i7 = -1;
                        break;
                    } else if (item == c0036ai.getItem(i7)) {
                        break;
                    } else {
                        i7++;
                    }
                }
                if (i7 != -1 && (firstVisiblePosition = (i7 + headersCount) - c0490pa2.getFirstVisiblePosition()) >= 0 && firstVisiblePosition < c0490pa2.getChildCount()) {
                    childAt = c0490pa2.getChildAt(firstVisiblePosition);
                }
            }
            if (childAt != null) {
                i2 = Build.VERSION.SDK_INT;
                q2 = c0621ti.z;
                if (i2 <= 28) {
                    method = C0621ti.D;
                    if (method != null) {
                        try {
                            method.invoke(q2, Boolean.FALSE);
                        } catch (Exception unused) {
                            Log.i("MenuPopupWindow", "Could not invoke setTouchModal() on PopupWindow. Oh well.");
                        }
                    }
                } else {
                    AbstractC0529qi.a(q2, false);
                }
                AbstractC0498pi.a(c0621ti.z, null);
                c0490pa = ((F5) arrayList.get(arrayList.size() - 1)).a.c;
                iArr = new int[2];
                c0490pa.getLocationOnScreen(iArr);
                rect3 = new Rect();
                this.o.getWindowVisibleDisplayFrame(rect3);
                if (this.p == i) {
                    if (c0490pa.getWidth() + iArr[0] + iM > rect3.right) {
                        i3 = 0;
                    } else {
                        i3 = 1;
                    }
                } else if (iArr[0] - iM < 0) {
                    i3 = 1;
                } else {
                    i3 = 0;
                }
                if (i3 == 1) {
                    z = true;
                } else {
                    z = false;
                }
                this.p = i3;
                if (Build.VERSION.SDK_INT >= 26) {
                    c0621ti.o = childAt;
                    i5 = 0;
                    i4 = 0;
                } else {
                    iArr2 = new int[2];
                    this.n.getLocationOnScreen(iArr2);
                    iArr3 = new int[2];
                    childAt.getLocationOnScreen(iArr3);
                    if ((this.m & 7) == 5) {
                        iArr2[0] = this.n.getWidth() + iArr2[0];
                        iArr3[0] = childAt.getWidth() + iArr3[0];
                    }
                    i4 = iArr3[0] - iArr2[0];
                    i5 = iArr3[1] - iArr2[1];
                }
                if ((this.m & 5) == 5) {
                    if (z) {
                        width = i4 + iM;
                    } else {
                        iM = childAt.getWidth();
                        width = i4 - iM;
                    }
                } else if (z) {
                    width = i4 + childAt.getWidth();
                } else {
                    width = i4 - iM;
                }
                c0621ti.f = width;
                c0621ti.k = true;
                c0621ti.j = true;
                c0621ti.l(i5);
            } else {
                if (this.q) {
                    c0621ti.f = this.s;
                }
                if (this.r) {
                    c0621ti.l(this.t);
                }
                rect = this.a;
                if (rect != null) {
                    rect2 = new Rect(rect);
                } else {
                    rect2 = null;
                }
                c0621ti.x = rect2;
            }
            arrayList.add(new F5(c0621ti, menuC0129di, this.p));
            c0621ti.e();
            C0490pa c0490pa3 = c0621ti.c;
            c0490pa3.setOnKeyListener(this);
            if (f5 == null || !this.v || menuC0129di.m == null) {
                return;
            }
            FrameLayout frameLayout = (FrameLayout) layoutInflaterFrom.inflate(R.layout._0_resource_name_obfuscated_res_0x7f0c0012, (ViewGroup) c0490pa3, false);
            TextView textView = (TextView) frameLayout.findViewById(android.R.id.title);
            frameLayout.setEnabled(false);
            textView.setText(menuC0129di.m);
            c0490pa3.addHeaderView(frameLayout, null, false);
            c0621ti.e();
            return;
        }
        i = 1;
        f5 = null;
        childAt = null;
        if (childAt != null) {
            i2 = Build.VERSION.SDK_INT;
            q2 = c0621ti.z;
            if (i2 <= 28) {
                method = C0621ti.D;
                if (method != null) {
                    method.invoke(q2, Boolean.FALSE);
                }
            } else {
                AbstractC0529qi.a(q2, false);
            }
            AbstractC0498pi.a(c0621ti.z, null);
            c0490pa = ((F5) arrayList.get(arrayList.size() - 1)).a.c;
            iArr = new int[2];
            c0490pa.getLocationOnScreen(iArr);
            rect3 = new Rect();
            this.o.getWindowVisibleDisplayFrame(rect3);
            if (this.p == i) {
                if (c0490pa.getWidth() + iArr[0] + iM > rect3.right) {
                    i3 = 0;
                } else {
                    i3 = 1;
                }
            } else if (iArr[0] - iM < 0) {
                i3 = 1;
            } else {
                i3 = 0;
            }
            if (i3 == 1) {
                z = true;
            } else {
                z = false;
            }
            this.p = i3;
            if (Build.VERSION.SDK_INT >= 26) {
                c0621ti.o = childAt;
                i5 = 0;
                i4 = 0;
            } else {
                iArr2 = new int[2];
                this.n.getLocationOnScreen(iArr2);
                iArr3 = new int[2];
                childAt.getLocationOnScreen(iArr3);
                if ((this.m & 7) == 5) {
                    iArr2[0] = this.n.getWidth() + iArr2[0];
                    iArr3[0] = childAt.getWidth() + iArr3[0];
                }
                i4 = iArr3[0] - iArr2[0];
                i5 = iArr3[1] - iArr2[1];
            }
            if ((this.m & 5) == 5) {
                if (z) {
                    width = i4 + iM;
                } else {
                    iM = childAt.getWidth();
                    width = i4 - iM;
                }
            } else if (z) {
                width = i4 + childAt.getWidth();
            } else {
                width = i4 - iM;
            }
            c0621ti.f = width;
            c0621ti.k = true;
            c0621ti.j = true;
            c0621ti.l(i5);
        } else {
            if (this.q) {
                c0621ti.f = this.s;
            }
            if (this.r) {
                c0621ti.l(this.t);
            }
            rect = this.a;
            if (rect != null) {
                rect2 = new Rect(rect);
            } else {
                rect2 = null;
            }
            c0621ti.x = rect2;
        }
        arrayList.add(new F5(c0621ti, menuC0129di, this.p));
        c0621ti.e();
        C0490pa c0490pa4 = c0621ti.c;
        c0490pa4.setOnKeyListener(this);
        if (f5 == null) {
        }
    }
}
