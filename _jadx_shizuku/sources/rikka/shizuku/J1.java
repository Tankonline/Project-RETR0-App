package rikka.shizuku;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import androidx.appcompat.app.AlertController$RecycleListView;
import androidx.core.widget.NestedScrollView;
import java.util.WeakHashMap;
import moe.shizuku.privileged.api.R;

/* JADX INFO: loaded from: classes.dex */
public final class J1 extends U6 implements DialogInterface, InterfaceC0451o2 {
    public I2 d;
    public final J2 e;
    public final H1 f;

    /* JADX WARN: Type inference failed for: r2v2, types: [rikka.shizuku.J2] */
    public J1(ContextThemeWrapper contextThemeWrapper, int i) {
        int i2;
        int iH = h(contextThemeWrapper, i);
        if (iH == 0) {
            TypedValue typedValue = new TypedValue();
            contextThemeWrapper.getTheme().resolveAttribute(R.attr._0_resource_name_obfuscated_res_0x7f040183, typedValue, true);
            i2 = typedValue.resourceId;
        } else {
            i2 = iH;
        }
        super(contextThemeWrapper, i2);
        this.e = new InterfaceC0065bg() { // from class: rikka.shizuku.J2
            @Override // rikka.shizuku.InterfaceC0065bg
            public final boolean c(KeyEvent keyEvent) {
                return this.a.j(keyEvent);
            }
        };
        AbstractC0667v2 abstractC0667v2F = f();
        if (iH == 0) {
            TypedValue typedValue2 = new TypedValue();
            contextThemeWrapper.getTheme().resolveAttribute(R.attr._0_resource_name_obfuscated_res_0x7f040183, typedValue2, true);
            iH = typedValue2.resourceId;
        }
        ((I2) abstractC0667v2F).S = iH;
        abstractC0667v2F.f();
        this.f = new H1(getContext(), this, getWindow());
    }

    public static int h(Context context, int i) {
        if (((i >>> 24) & 255) >= 1) {
            return i;
        }
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr._0_resource_name_obfuscated_res_0x7f04002d, typedValue, true);
        return typedValue.resourceId;
    }

    @Override // rikka.shizuku.U6, android.app.Dialog
    public final void addContentView(View view, ViewGroup.LayoutParams layoutParams) {
        I2 i2 = (I2) f();
        i2.y();
        ((ViewGroup) i2.A.findViewById(android.R.id.content)).addView(view, layoutParams);
        i2.m.a(i2.l.getCallback());
    }

    public final Button d(int i) {
        H1 h1 = this.f;
        if (i == -3) {
            return h1.o;
        }
        if (i == -2) {
            return h1.l;
        }
        if (i == -1) {
            return h1.i;
        }
        h1.getClass();
        return null;
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public final void dismiss() {
        super.dismiss();
        f().g();
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return AbstractC0599sr.k(this.e, getWindow().getDecorView(), this, keyEvent);
    }

    public final AbstractC0667v2 f() {
        if (this.d == null) {
            O3 o3 = AbstractC0667v2.a;
            this.d = new I2(getContext(), getWindow(), this, this);
        }
        return this.d;
    }

    @Override // android.app.Dialog
    public final View findViewById(int i) {
        I2 i2 = (I2) f();
        i2.y();
        return i2.l.findViewById(i);
    }

    public final void g(Bundle bundle) {
        f().c();
        super.onCreate(bundle);
        f().f();
    }

    public final void i(CharSequence charSequence) {
        super.setTitle(charSequence);
        f().o(charSequence);
    }

    @Override // android.app.Dialog
    public final void invalidateOptionsMenu() {
        f().d();
    }

    public final boolean j(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent);
    }

    @Override // rikka.shizuku.U6, android.app.Dialog
    public final void onCreate(Bundle bundle) {
        int i;
        int i2;
        int i3;
        ListAdapter listAdapter;
        View viewFindViewById;
        g(bundle);
        H1 h1 = this.f;
        h1.b.setContentView(h1.z);
        Window window = h1.c;
        View viewFindViewById2 = window.findViewById(R.id._0_resource_name_obfuscated_res_0x7f09014f);
        View viewFindViewById3 = viewFindViewById2.findViewById(R.id._0_resource_name_obfuscated_res_0x7f0901e5);
        View viewFindViewById4 = viewFindViewById2.findViewById(R.id._0_resource_name_obfuscated_res_0x7f090077);
        View viewFindViewById5 = viewFindViewById2.findViewById(R.id._0_resource_name_obfuscated_res_0x7f090061);
        ViewGroup viewGroup = (ViewGroup) viewFindViewById2.findViewById(R.id._0_resource_name_obfuscated_res_0x7f09007e);
        View view = h1.g;
        if (view == null) {
            view = null;
        }
        boolean z = view != null;
        if (!z || !H1.a(view)) {
            window.setFlags(131072, 131072);
        }
        if (z) {
            i = 2;
            FrameLayout frameLayout = (FrameLayout) window.findViewById(R.id._0_resource_name_obfuscated_res_0x7f09007d);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
            if (h1.h) {
                layoutParams.leftMargin = 0;
                layoutParams.topMargin = 0;
                layoutParams.rightMargin = 0;
                layoutParams.bottomMargin = 0;
            }
            frameLayout.addView(view, layoutParams);
            if (h1.f != null) {
                ((LinearLayout.LayoutParams) ((Dg) viewGroup.getLayoutParams())).weight = 0.0f;
            }
        } else {
            i = 2;
            viewGroup.setVisibility(8);
        }
        View viewFindViewById6 = viewGroup.findViewById(R.id._0_resource_name_obfuscated_res_0x7f0901e5);
        View viewFindViewById7 = viewGroup.findViewById(R.id._0_resource_name_obfuscated_res_0x7f090077);
        View viewFindViewById8 = viewGroup.findViewById(R.id._0_resource_name_obfuscated_res_0x7f090061);
        ViewGroup viewGroupB = H1.b(viewFindViewById6, viewFindViewById3);
        ViewGroup viewGroupB2 = H1.b(viewFindViewById7, viewFindViewById4);
        ViewGroup viewGroupB3 = H1.b(viewFindViewById8, viewFindViewById5);
        NestedScrollView nestedScrollView = (NestedScrollView) window.findViewById(R.id._0_resource_name_obfuscated_res_0x7f090178);
        h1.r = nestedScrollView;
        nestedScrollView.setFocusable(false);
        h1.r.setNestedScrollingEnabled(false);
        TextView textView = (TextView) viewGroupB2.findViewById(android.R.id.message);
        h1.v = textView;
        if (textView != null) {
            CharSequence charSequence = h1.e;
            if (charSequence != null) {
                textView.setText(charSequence);
            } else {
                textView.setVisibility(8);
                h1.r.removeView(h1.v);
                if (h1.f != null) {
                    ViewGroup viewGroup2 = (ViewGroup) h1.r.getParent();
                    int iIndexOfChild = viewGroup2.indexOfChild(h1.r);
                    viewGroup2.removeViewAt(iIndexOfChild);
                    viewGroup2.addView(h1.f, iIndexOfChild, new ViewGroup.LayoutParams(-1, -1));
                } else {
                    viewGroupB2.setVisibility(8);
                }
            }
        }
        Button button = (Button) viewGroupB3.findViewById(android.R.id.button1);
        h1.i = button;
        ViewOnClickListenerC0572s0 viewOnClickListenerC0572s0 = h1.G;
        button.setOnClickListener(viewOnClickListenerC0572s0);
        if (TextUtils.isEmpty(h1.j)) {
            h1.i.setVisibility(8);
            i2 = 0;
        } else {
            h1.i.setText(h1.j);
            h1.i.setVisibility(0);
            i2 = 1;
        }
        Button button2 = (Button) viewGroupB3.findViewById(android.R.id.button2);
        h1.l = button2;
        button2.setOnClickListener(viewOnClickListenerC0572s0);
        if (TextUtils.isEmpty(h1.m)) {
            h1.l.setVisibility(8);
        } else {
            h1.l.setText(h1.m);
            h1.l.setVisibility(0);
            i2 |= 2;
        }
        Button button3 = (Button) viewGroupB3.findViewById(android.R.id.button3);
        h1.o = button3;
        button3.setOnClickListener(viewOnClickListenerC0572s0);
        if (TextUtils.isEmpty(h1.p)) {
            h1.o.setVisibility(8);
        } else {
            h1.o.setText(h1.p);
            h1.o.setVisibility(0);
            i2 |= 4;
        }
        TypedValue typedValue = new TypedValue();
        h1.a.getTheme().resolveAttribute(R.attr._0_resource_name_obfuscated_res_0x7f04002b, typedValue, true);
        if (typedValue.data == 0) {
            i3 = i;
        } else if (i2 == 1) {
            Button button4 = h1.i;
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) button4.getLayoutParams();
            layoutParams2.gravity = 1;
            layoutParams2.weight = 0.5f;
            button4.setLayoutParams(layoutParams2);
            i3 = i;
        } else {
            i3 = i;
            if (i2 == i3) {
                Button button5 = h1.l;
                LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) button5.getLayoutParams();
                layoutParams3.gravity = 1;
                layoutParams3.weight = 0.5f;
                button5.setLayoutParams(layoutParams3);
            } else if (i2 == 4) {
                Button button6 = h1.o;
                LinearLayout.LayoutParams layoutParams4 = (LinearLayout.LayoutParams) button6.getLayoutParams();
                layoutParams4.gravity = 1;
                layoutParams4.weight = 0.5f;
                button6.setLayoutParams(layoutParams4);
            }
        }
        if (i2 == 0) {
            viewGroupB3.setVisibility(8);
        }
        if (h1.w != null) {
            viewGroupB.addView(h1.w, 0, new ViewGroup.LayoutParams(-1, -2));
            window.findViewById(R.id._0_resource_name_obfuscated_res_0x7f0901e0).setVisibility(8);
        } else {
            h1.t = (ImageView) window.findViewById(android.R.id.icon);
            if (TextUtils.isEmpty(h1.d) || !h1.E) {
                window.findViewById(R.id._0_resource_name_obfuscated_res_0x7f0901e0).setVisibility(8);
                h1.t.setVisibility(8);
                viewGroupB.setVisibility(8);
            } else {
                TextView textView2 = (TextView) window.findViewById(R.id._0_resource_name_obfuscated_res_0x7f090047);
                h1.u = textView2;
                textView2.setText(h1.d);
                Drawable drawable = h1.s;
                if (drawable != null) {
                    h1.t.setImageDrawable(drawable);
                } else {
                    h1.u.setPadding(h1.t.getPaddingLeft(), h1.t.getPaddingTop(), h1.t.getPaddingRight(), h1.t.getPaddingBottom());
                    h1.t.setVisibility(8);
                }
            }
        }
        boolean z2 = viewGroup.getVisibility() != 8;
        int i4 = (viewGroupB == null || viewGroupB.getVisibility() == 8) ? 0 : 1;
        boolean z3 = viewGroupB3.getVisibility() != 8;
        if (!z3 && (viewFindViewById = viewGroupB2.findViewById(R.id._0_resource_name_obfuscated_res_0x7f0901d0)) != null) {
            viewFindViewById.setVisibility(0);
        }
        if (i4 != 0) {
            NestedScrollView nestedScrollView2 = h1.r;
            if (nestedScrollView2 != null) {
                nestedScrollView2.setClipToPadding(true);
            }
            View viewFindViewById9 = (h1.e == null && h1.f == null) ? null : viewGroupB.findViewById(R.id._0_resource_name_obfuscated_res_0x7f0901df);
            if (viewFindViewById9 != null) {
                viewFindViewById9.setVisibility(0);
            }
        } else {
            View viewFindViewById10 = viewGroupB2.findViewById(R.id._0_resource_name_obfuscated_res_0x7f0901d1);
            if (viewFindViewById10 != null) {
                viewFindViewById10.setVisibility(0);
            }
        }
        AlertController$RecycleListView alertController$RecycleListView = h1.f;
        if (alertController$RecycleListView != null && (!z3 || i4 == 0)) {
            alertController$RecycleListView.setPadding(alertController$RecycleListView.getPaddingLeft(), i4 != 0 ? alertController$RecycleListView.getPaddingTop() : alertController$RecycleListView.a, alertController$RecycleListView.getPaddingRight(), z3 ? alertController$RecycleListView.getPaddingBottom() : alertController$RecycleListView.b);
        }
        if (!z2) {
            View view2 = h1.f;
            if (view2 == null) {
                view2 = h1.r;
            }
            if (view2 != null) {
                int i5 = i4 | (z3 ? i3 : 0);
                View viewFindViewById11 = window.findViewById(R.id._0_resource_name_obfuscated_res_0x7f090177);
                View viewFindViewById12 = window.findViewById(R.id._0_resource_name_obfuscated_res_0x7f090176);
                WeakHashMap weakHashMap = Hs.a;
                As.b(view2, i5, 3);
                if (viewFindViewById11 != null) {
                    viewGroupB2.removeView(viewFindViewById11);
                }
                if (viewFindViewById12 != null) {
                    viewGroupB2.removeView(viewFindViewById12);
                }
            }
        }
        AlertController$RecycleListView alertController$RecycleListView2 = h1.f;
        if (alertController$RecycleListView2 == null || (listAdapter = h1.x) == null) {
            return;
        }
        alertController$RecycleListView2.setAdapter(listAdapter);
        int i6 = h1.y;
        if (i6 > -1) {
            alertController$RecycleListView2.setItemChecked(i6, true);
            alertController$RecycleListView2.setSelection(i6);
        }
    }

    @Override // android.app.Dialog, android.view.KeyEvent.Callback
    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        NestedScrollView nestedScrollView = this.f.r;
        if (nestedScrollView == null || !nestedScrollView.i(keyEvent)) {
            return super.onKeyDown(i, keyEvent);
        }
        return true;
    }

    @Override // android.app.Dialog, android.view.KeyEvent.Callback
    public final boolean onKeyUp(int i, KeyEvent keyEvent) {
        NestedScrollView nestedScrollView = this.f.r;
        if (nestedScrollView == null || !nestedScrollView.i(keyEvent)) {
            return super.onKeyUp(i, keyEvent);
        }
        return true;
    }

    @Override // rikka.shizuku.U6, android.app.Dialog
    public final void onStop() {
        super.onStop();
        I2 i2 = (I2) f();
        i2.C();
        AbstractC0744xh abstractC0744xh = i2.o;
        if (abstractC0744xh != null) {
            abstractC0744xh.V(false);
        }
    }

    @Override // rikka.shizuku.U6, android.app.Dialog
    public final void setContentView(int i) {
        f().j(i);
    }

    @Override // android.app.Dialog
    public final void setTitle(int i) {
        super.setTitle(i);
        f().o(getContext().getString(i));
    }

    @Override // rikka.shizuku.U6, android.app.Dialog
    public final void setContentView(View view) {
        f().k(view);
    }

    @Override // rikka.shizuku.U6, android.app.Dialog
    public final void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        f().l(view, layoutParams);
    }

    @Override // android.app.Dialog
    public final void setTitle(CharSequence charSequence) {
        i(charSequence);
        H1 h1 = this.f;
        h1.d = charSequence;
        TextView textView = h1.u;
        if (textView != null) {
            textView.setText(charSequence);
        }
    }
}
