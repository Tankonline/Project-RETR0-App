package androidx.appcompat.view.menu;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import rikka.shizuku.AbstractC0688vn;
import rikka.shizuku.C0221gi;
import rikka.shizuku.C0606t3;
import rikka.shizuku.C0727x0;
import rikka.shizuku.C0789z0;
import rikka.shizuku.D0;
import rikka.shizuku.InterfaceC0098ci;
import rikka.shizuku.InterfaceC0714wi;
import rikka.shizuku.Nk;

/* JADX INFO: loaded from: classes.dex */
public class ActionMenuItemView extends C0606t3 implements InterfaceC0714wi, View.OnClickListener, D0 {
    public C0221gi g;
    public CharSequence h;
    public Drawable i;
    public InterfaceC0098ci j;
    public C0727x0 k;
    public C0789z0 l;
    public boolean m;
    public final int n;
    public int o;
    public final int p;

    public ActionMenuItemView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
        Resources resources = context.getResources();
        this.m = h();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Nk.c, 0, 0);
        this.n = typedArrayObtainStyledAttributes.getDimensionPixelSize(0, 0);
        typedArrayObtainStyledAttributes.recycle();
        this.p = (int) ((resources.getDisplayMetrics().density * 32.0f) + 0.5f);
        setOnClickListener(this);
        this.o = -1;
        setSaveEnabled(false);
    }

    @Override // rikka.shizuku.D0
    public final boolean a() {
        return !TextUtils.isEmpty(getText());
    }

    @Override // rikka.shizuku.InterfaceC0714wi
    public final C0221gi b() {
        return this.g;
    }

    @Override // rikka.shizuku.D0
    public final boolean c() {
        return !TextUtils.isEmpty(getText()) && this.g.getIcon() == null;
    }

    @Override // rikka.shizuku.InterfaceC0714wi
    public final void e(C0221gi c0221gi) {
        this.g = c0221gi;
        Drawable icon = c0221gi.getIcon();
        this.i = icon;
        if (icon != null) {
            int intrinsicWidth = icon.getIntrinsicWidth();
            int intrinsicHeight = icon.getIntrinsicHeight();
            int i = this.p;
            if (intrinsicWidth > i) {
                intrinsicHeight = (int) (intrinsicHeight * (i / intrinsicWidth));
                intrinsicWidth = i;
            }
            if (intrinsicHeight > i) {
                intrinsicWidth = (int) (intrinsicWidth * (i / intrinsicHeight));
            } else {
                i = intrinsicHeight;
            }
            icon.setBounds(0, 0, intrinsicWidth, i);
        }
        setCompoundDrawables(icon, null, null, null);
        i();
        this.h = c0221gi.getTitleCondensed();
        i();
        setId(c0221gi.a);
        setVisibility(c0221gi.isVisible() ? 0 : 8);
        setEnabled(c0221gi.isEnabled());
        if (c0221gi.hasSubMenu() && this.k == null) {
            this.k = new C0727x0(this);
        }
    }

    @Override // android.widget.TextView, android.view.View
    public final CharSequence getAccessibilityClassName() {
        return Button.class.getName();
    }

    public final boolean h() {
        Configuration configuration = getContext().getResources().getConfiguration();
        int i = configuration.screenWidthDp;
        int i2 = configuration.screenHeightDp;
        if (i < 480) {
            return (i >= 640 && i2 >= 480) || configuration.orientation == 2;
        }
        return true;
    }

    public final void i() {
        boolean z = true;
        boolean z2 = !TextUtils.isEmpty(this.h);
        if (this.i != null && ((this.g.y & 4) != 4 || !this.m)) {
            z = false;
        }
        boolean z3 = z2 & z;
        setText(z3 ? this.h : null);
        CharSequence charSequence = this.g.q;
        if (TextUtils.isEmpty(charSequence)) {
            setContentDescription(z3 ? null : this.g.e);
        } else {
            setContentDescription(charSequence);
        }
        CharSequence charSequence2 = this.g.r;
        if (TextUtils.isEmpty(charSequence2)) {
            AbstractC0688vn.y(this, z3 ? null : this.g.e);
        } else {
            AbstractC0688vn.y(this, charSequence2);
        }
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        InterfaceC0098ci interfaceC0098ci = this.j;
        if (interfaceC0098ci != null) {
            interfaceC0098ci.c(this.g);
        }
    }

    @Override // android.widget.TextView, android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.m = h();
        i();
    }

    @Override // rikka.shizuku.C0606t3, android.widget.TextView, android.view.View
    public final void onMeasure(int i, int i2) {
        int i3;
        boolean zIsEmpty = TextUtils.isEmpty(getText());
        if (!zIsEmpty && (i3 = this.o) >= 0) {
            super.setPadding(i3, getPaddingTop(), getPaddingRight(), getPaddingBottom());
        }
        super.onMeasure(i, i2);
        int mode = View.MeasureSpec.getMode(i);
        int size = View.MeasureSpec.getSize(i);
        int measuredWidth = getMeasuredWidth();
        int i4 = this.n;
        int iMin = mode == Integer.MIN_VALUE ? Math.min(size, i4) : i4;
        if (mode != 1073741824 && i4 > 0 && measuredWidth < iMin) {
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(iMin, 1073741824), i2);
        }
        if (!zIsEmpty || this.i == null) {
            return;
        }
        super.setPadding((getMeasuredWidth() - this.i.getBounds().width()) / 2, getPaddingTop(), getPaddingRight(), getPaddingBottom());
    }

    @Override // android.widget.TextView, android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        super.onRestoreInstanceState(null);
    }

    @Override // android.widget.TextView, android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        C0727x0 c0727x0;
        if (this.g.hasSubMenu() && (c0727x0 = this.k) != null && c0727x0.onTouch(this, motionEvent)) {
            return true;
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override // android.widget.TextView, android.view.View
    public final void setPadding(int i, int i2, int i3, int i4) {
        this.o = i;
        super.setPadding(i, i2, i3, i4);
    }
}
