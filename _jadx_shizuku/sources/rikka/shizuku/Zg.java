package rikka.shizuku;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import java.lang.reflect.Method;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public class Zg implements Yn {
    public static final Method A;
    public static final Method B;
    public final Context a;
    public ListAdapter b;
    public C0490pa c;
    public int f;
    public int g;
    public boolean i;
    public boolean j;
    public boolean k;
    public Wg n;
    public View o;
    public AdapterView.OnItemClickListener p;
    public AdapterView.OnItemSelectedListener q;
    public final Handler v;
    public Rect x;
    public boolean y;
    public final Q2 z;
    public final int d = -2;
    public int e = -2;
    public final int h = 1002;
    public int l = 0;
    public final int m = Integer.MAX_VALUE;
    public final Vg r = new Vg(this, 1);
    public final Yg s = new Yg(0, this);
    public final Xg t = new Xg(this);
    public final Vg u = new Vg(this, 0);
    public final Rect w = new Rect();

    static {
        if (Build.VERSION.SDK_INT <= 28) {
            try {
                A = PopupWindow.class.getDeclaredMethod("setClipToScreenEnabled", Boolean.TYPE);
            } catch (NoSuchMethodException unused) {
                Log.i("ListPopupWindow", "Could not find method setClipToScreenEnabled() on PopupWindow. Oh well.");
            }
            try {
                B = PopupWindow.class.getDeclaredMethod("setEpicenterBounds", Rect.class);
            } catch (NoSuchMethodException unused2) {
                Log.i("ListPopupWindow", "Could not find method setEpicenterBounds(Rect) on PopupWindow. Oh well.");
            }
        }
    }

    public Zg(Context context, AttributeSet attributeSet, int i) {
        int resourceId;
        this.a = context;
        this.v = new Handler(context.getMainLooper());
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Nk.o, i, 0);
        this.f = typedArrayObtainStyledAttributes.getDimensionPixelOffset(0, 0);
        int dimensionPixelOffset = typedArrayObtainStyledAttributes.getDimensionPixelOffset(1, 0);
        this.g = dimensionPixelOffset;
        if (dimensionPixelOffset != 0) {
            this.i = true;
        }
        typedArrayObtainStyledAttributes.recycle();
        Q2 q2 = new Q2(context, attributeSet, i, 0);
        TypedArray typedArrayObtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, Nk.s, i, 0);
        if (typedArrayObtainStyledAttributes2.hasValue(2)) {
            q2.setOverlapAnchor(typedArrayObtainStyledAttributes2.getBoolean(2, false));
        }
        q2.setBackgroundDrawable((!typedArrayObtainStyledAttributes2.hasValue(0) || (resourceId = typedArrayObtainStyledAttributes2.getResourceId(0, 0)) == 0) ? typedArrayObtainStyledAttributes2.getDrawable(0) : AbstractC0744xh.v(context, resourceId));
        typedArrayObtainStyledAttributes2.recycle();
        this.z = q2;
        q2.setInputMethodMode(1);
    }

    @Override // rikka.shizuku.Yn
    public final boolean a() {
        return this.z.isShowing();
    }

    public final void b(int i) {
        this.f = i;
    }

    public final int c() {
        return this.f;
    }

    @Override // rikka.shizuku.Yn
    public final void dismiss() {
        Q2 q2 = this.z;
        q2.dismiss();
        q2.setContentView(null);
        this.c = null;
        this.v.removeCallbacks(this.r);
    }

    @Override // rikka.shizuku.Yn
    public final void e() {
        int i;
        int iMakeMeasureSpec;
        int paddingBottom;
        C0490pa c0490pa;
        int i2 = 1;
        C0490pa c0490pa2 = this.c;
        Q2 q2 = this.z;
        Context context = this.a;
        if (c0490pa2 == null) {
            C0490pa c0490paO = o(context, !this.y);
            this.c = c0490paO;
            c0490paO.setAdapter(this.b);
            this.c.setOnItemClickListener(this.p);
            this.c.setFocusable(true);
            this.c.setFocusableInTouchMode(true);
            this.c.setOnItemSelectedListener(new C0521qa(i2, this));
            this.c.setOnScrollListener(this.t);
            AdapterView.OnItemSelectedListener onItemSelectedListener = this.q;
            if (onItemSelectedListener != null) {
                this.c.setOnItemSelectedListener(onItemSelectedListener);
            }
            q2.setContentView(this.c);
        }
        Drawable background = q2.getBackground();
        Rect rect = this.w;
        if (background != null) {
            background.getPadding(rect);
            int i3 = rect.top;
            i = rect.bottom + i3;
            if (!this.i) {
                this.g = -i3;
            }
        } else {
            rect.setEmpty();
            i = 0;
        }
        int iA = Tg.a(q2, this.o, this.g, q2.getInputMethodMode() == 2);
        int i4 = this.d;
        if (i4 == -1) {
            paddingBottom = iA + i;
        } else {
            int i5 = this.e;
            if (i5 != -2) {
                iMakeMeasureSpec = i5 != -1 ? View.MeasureSpec.makeMeasureSpec(i5, 1073741824) : View.MeasureSpec.makeMeasureSpec(context.getResources().getDisplayMetrics().widthPixels - (rect.left + rect.right), 1073741824);
            } else {
                iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(context.getResources().getDisplayMetrics().widthPixels - (rect.left + rect.right), Integer.MIN_VALUE);
            }
            int iA2 = this.c.a(iMakeMeasureSpec, iA);
            paddingBottom = iA2 + (iA2 > 0 ? this.c.getPaddingBottom() + this.c.getPaddingTop() + i : 0);
        }
        boolean z = this.z.getInputMethodMode() == 2;
        q2.setWindowLayoutType(this.h);
        if (q2.isShowing()) {
            View view = this.o;
            WeakHashMap weakHashMap = Hs.a;
            if (view.isAttachedToWindow()) {
                int width = this.e;
                if (width == -1) {
                    width = -1;
                } else if (width == -2) {
                    width = this.o.getWidth();
                }
                if (i4 == -1) {
                    i4 = z ? paddingBottom : -1;
                    if (z) {
                        q2.setWidth(this.e == -1 ? -1 : 0);
                        q2.setHeight(0);
                    } else {
                        q2.setWidth(this.e == -1 ? -1 : 0);
                        q2.setHeight(-1);
                    }
                } else if (i4 == -2) {
                    i4 = paddingBottom;
                }
                q2.setOutsideTouchable(true);
                int i6 = width;
                q2.update(this.o, this.f, this.g, i6 < 0 ? -1 : i6, i4 < 0 ? -1 : i4);
                return;
            }
            return;
        }
        int width2 = this.e;
        if (width2 == -1) {
            width2 = -1;
        } else if (width2 == -2) {
            width2 = this.o.getWidth();
        }
        if (i4 == -1) {
            i4 = -1;
        } else if (i4 == -2) {
            i4 = paddingBottom;
        }
        q2.setWidth(width2);
        q2.setHeight(i4);
        if (Build.VERSION.SDK_INT <= 28) {
            Method method = A;
            if (method != null) {
                try {
                    method.invoke(q2, Boolean.TRUE);
                } catch (Exception unused) {
                    Log.i("ListPopupWindow", "Could not call setClipToScreenEnabled() on PopupWindow. Oh well.");
                }
            }
        } else {
            Ug.b(q2, true);
        }
        q2.setOutsideTouchable(true);
        q2.setTouchInterceptor(this.s);
        if (this.k) {
            q2.setOverlapAnchor(this.j);
        }
        if (Build.VERSION.SDK_INT <= 28) {
            Method method2 = B;
            if (method2 != null) {
                try {
                    method2.invoke(q2, this.x);
                } catch (Exception e) {
                    Log.e("ListPopupWindow", "Could not invoke setEpicenterBounds on PopupWindow", e);
                }
            }
        } else {
            Ug.a(q2, this.x);
        }
        q2.showAsDropDown(this.o, this.f, this.g, this.l);
        this.c.setSelection(-1);
        if ((!this.y || this.c.isInTouchMode()) && (c0490pa = this.c) != null) {
            c0490pa.h = true;
            c0490pa.requestLayout();
        }
        if (this.y) {
            return;
        }
        this.v.post(this.u);
    }

    public final int f() {
        if (this.i) {
            return this.g;
        }
        return 0;
    }

    public final Drawable g() {
        return this.z.getBackground();
    }

    @Override // rikka.shizuku.Yn
    public final C0490pa j() {
        return this.c;
    }

    public final void k(Drawable drawable) {
        this.z.setBackgroundDrawable(drawable);
    }

    public final void l(int i) {
        this.g = i;
        this.i = true;
    }

    public void m(ListAdapter listAdapter) {
        Wg wg = this.n;
        if (wg == null) {
            this.n = new Wg(this);
        } else {
            ListAdapter listAdapter2 = this.b;
            if (listAdapter2 != null) {
                listAdapter2.unregisterDataSetObserver(wg);
            }
        }
        this.b = listAdapter;
        if (listAdapter != null) {
            listAdapter.registerDataSetObserver(this.n);
        }
        C0490pa c0490pa = this.c;
        if (c0490pa != null) {
            c0490pa.setAdapter(this.b);
        }
    }

    public C0490pa o(Context context, boolean z) {
        return new C0490pa(context, z);
    }

    public final void p(int i) {
        Drawable background = this.z.getBackground();
        if (background == null) {
            this.e = i;
            return;
        }
        Rect rect = this.w;
        background.getPadding(rect);
        this.e = rect.left + rect.right + i;
    }
}
