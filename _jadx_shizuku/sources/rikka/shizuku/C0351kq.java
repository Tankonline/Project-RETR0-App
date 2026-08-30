package rikka.shizuku;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import androidx.appcompat.widget.Toolbar;
import moe.shizuku.privileged.api.R;

/* JADX INFO: renamed from: rikka.shizuku.kq, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0351kq {
    public final Toolbar a;
    public int b;
    public final View c;
    public final Drawable d;
    public final Drawable e;
    public Drawable f;
    public final boolean g;
    public CharSequence h;
    public final CharSequence i;
    public final CharSequence j;
    public Window.Callback k;
    public boolean l;
    public C0 m;
    public final int n;
    public final Drawable o;

    public C0351kq(Toolbar toolbar, boolean z) {
        Drawable drawable;
        this.n = 0;
        this.a = toolbar;
        CharSequence charSequence = toolbar.x;
        this.h = charSequence;
        this.i = toolbar.y;
        this.g = charSequence != null;
        this.f = toolbar.n();
        K3 k3N = K3.n(toolbar.getContext(), null, Nk.a, R.attr._0_resource_name_obfuscated_res_0x7f040005);
        int i = 15;
        this.o = k3N.i(15);
        if (z) {
            TypedArray typedArray = (TypedArray) k3N.a;
            CharSequence text = typedArray.getText(27);
            if (!TextUtils.isEmpty(text)) {
                this.g = true;
                this.h = text;
                if ((this.b & 8) != 0) {
                    Toolbar toolbar2 = this.a;
                    toolbar2.A(text);
                    if (this.g) {
                        Hs.n(toolbar2.getRootView(), text);
                    }
                }
            }
            CharSequence text2 = typedArray.getText(25);
            if (!TextUtils.isEmpty(text2)) {
                this.i = text2;
                if ((this.b & 8) != 0) {
                    toolbar.z(text2);
                }
            }
            Drawable drawableI = k3N.i(20);
            if (drawableI != null) {
                this.e = drawableI;
                c();
            }
            Drawable drawableI2 = k3N.i(17);
            if (drawableI2 != null) {
                this.d = drawableI2;
                c();
            }
            if (this.f == null && (drawable = this.o) != null) {
                this.f = drawable;
                int i2 = this.b & 4;
                Toolbar toolbar3 = this.a;
                if (i2 != 0) {
                    toolbar3.y(drawable);
                } else {
                    toolbar3.y(null);
                }
            }
            a(typedArray.getInt(10, 0));
            int resourceId = typedArray.getResourceId(9, 0);
            if (resourceId != 0) {
                View viewInflate = LayoutInflater.from(toolbar.getContext()).inflate(resourceId, (ViewGroup) toolbar, false);
                View view = this.c;
                if (view != null && (this.b & 16) != 0) {
                    toolbar.removeView(view);
                }
                this.c = viewInflate;
                if (viewInflate != null && (this.b & 16) != 0) {
                    toolbar.addView(viewInflate);
                }
                a(this.b | 16);
            }
            int layoutDimension = typedArray.getLayoutDimension(13, 0);
            if (layoutDimension > 0) {
                ViewGroup.LayoutParams layoutParams = toolbar.getLayoutParams();
                layoutParams.height = layoutDimension;
                toolbar.setLayoutParams(layoutParams);
            }
            int dimensionPixelOffset = typedArray.getDimensionPixelOffset(7, -1);
            int dimensionPixelOffset2 = typedArray.getDimensionPixelOffset(3, -1);
            if (dimensionPixelOffset >= 0 || dimensionPixelOffset2 >= 0) {
                int iMax = Math.max(dimensionPixelOffset, 0);
                int iMax2 = Math.max(dimensionPixelOffset2, 0);
                toolbar.c();
                toolbar.t.a(iMax, iMax2);
            }
            int resourceId2 = typedArray.getResourceId(28, 0);
            if (resourceId2 != 0) {
                Context context = toolbar.getContext();
                toolbar.l = resourceId2;
                C0606t3 c0606t3 = toolbar.b;
                if (c0606t3 != null) {
                    c0606t3.setTextAppearance(context, resourceId2);
                }
            }
            int resourceId3 = typedArray.getResourceId(26, 0);
            if (resourceId3 != 0) {
                Context context2 = toolbar.getContext();
                toolbar.m = resourceId3;
                C0606t3 c0606t4 = toolbar.c;
                if (c0606t4 != null) {
                    c0606t4.setTextAppearance(context2, resourceId3);
                }
            }
            int resourceId4 = typedArray.getResourceId(22, 0);
            if (resourceId4 != 0 && toolbar.k != resourceId4) {
                toolbar.k = resourceId4;
                if (resourceId4 == 0) {
                    toolbar.j = toolbar.getContext();
                } else {
                    toolbar.j = new ContextThemeWrapper(toolbar.getContext(), resourceId4);
                }
            }
        } else {
            if (toolbar.n() != null) {
                this.o = toolbar.n();
            } else {
                i = 11;
            }
            this.b = i;
        }
        k3N.o();
        if (R.string._0_resource_name_obfuscated_res_0x7f110001 != this.n) {
            this.n = R.string._0_resource_name_obfuscated_res_0x7f110001;
            N2 n2 = toolbar.d;
            if (TextUtils.isEmpty(n2 != null ? n2.getContentDescription() : null)) {
                int i3 = this.n;
                this.j = i3 == 0 ? null : toolbar.getContext().getString(i3);
                b();
            }
        }
        N2 n3 = toolbar.d;
        this.j = n3 != null ? n3.getContentDescription() : null;
        Jh jh = new Jh(this);
        toolbar.e();
        toolbar.d.setOnClickListener(jh);
    }

    public final void a(int i) {
        View view;
        int i2 = this.b ^ i;
        this.b = i;
        if (i2 != 0) {
            if ((i2 & 4) != 0) {
                if ((i & 4) != 0) {
                    b();
                }
                int i3 = this.b & 4;
                Toolbar toolbar = this.a;
                if (i3 != 0) {
                    Drawable drawable = this.f;
                    if (drawable == null) {
                        drawable = this.o;
                    }
                    toolbar.y(drawable);
                } else {
                    toolbar.y(null);
                }
            }
            if ((i2 & 3) != 0) {
                c();
            }
            int i4 = i2 & 8;
            Toolbar toolbar2 = this.a;
            if (i4 != 0) {
                if ((i & 8) != 0) {
                    toolbar2.A(this.h);
                    toolbar2.z(this.i);
                } else {
                    toolbar2.A(null);
                    toolbar2.z(null);
                }
            }
            if ((i2 & 16) == 0 || (view = this.c) == null) {
                return;
            }
            if ((i & 16) != 0) {
                toolbar2.addView(view);
            } else {
                toolbar2.removeView(view);
            }
        }
    }

    public final void b() {
        if ((this.b & 4) != 0) {
            boolean zIsEmpty = TextUtils.isEmpty(this.j);
            Toolbar toolbar = this.a;
            if (!zIsEmpty) {
                toolbar.x(this.j);
            } else {
                int i = this.n;
                toolbar.x(i != 0 ? toolbar.getContext().getText(i) : null);
            }
        }
    }

    public final void c() {
        Drawable drawable;
        int i = this.b;
        if ((i & 2) == 0) {
            drawable = null;
        } else if ((i & 1) == 0 || (drawable = this.e) == null) {
            drawable = this.d;
        }
        this.a.w(drawable);
    }
}
