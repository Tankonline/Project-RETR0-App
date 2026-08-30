package androidx.appcompat.view.menu;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import java.util.WeakHashMap;
import moe.shizuku.privileged.api.R;
import rikka.shizuku.C0221gi;
import rikka.shizuku.Hs;
import rikka.shizuku.InterfaceC0714wi;
import rikka.shizuku.K3;
import rikka.shizuku.MenuC0129di;
import rikka.shizuku.Nk;

/* JADX INFO: loaded from: classes.dex */
public class ListMenuItemView extends LinearLayout implements InterfaceC0714wi, AbsListView.SelectionBoundsAdjuster {
    public C0221gi a;
    public ImageView b;
    public RadioButton c;
    public TextView d;
    public CheckBox e;
    public TextView f;
    public ImageView g;
    public ImageView h;
    public LinearLayout i;
    public final Drawable j;
    public final int k;
    public final Context l;
    public boolean m;
    public final Drawable n;
    public final boolean o;
    public LayoutInflater p;
    public boolean q;

    public ListMenuItemView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        K3 k3N = K3.n(getContext(), attributeSet, Nk.r, R.attr._0_resource_name_obfuscated_res_0x7f0402d8);
        this.j = k3N.i(5);
        TypedArray typedArray = (TypedArray) k3N.a;
        this.k = typedArray.getResourceId(1, -1);
        this.m = typedArray.getBoolean(7, false);
        this.l = context;
        this.n = k3N.i(8);
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(null, new int[]{android.R.attr.divider}, R.attr._0_resource_name_obfuscated_res_0x7f0401a0, 0);
        this.o = typedArrayObtainStyledAttributes.hasValue(0);
        k3N.o();
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // android.widget.AbsListView.SelectionBoundsAdjuster
    public final void adjustListItemSelectionBounds(Rect rect) {
        ImageView imageView = this.h;
        if (imageView == null || imageView.getVisibility() != 0) {
            return;
        }
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.h.getLayoutParams();
        rect.top = this.h.getHeight() + layoutParams.topMargin + layoutParams.bottomMargin + rect.top;
    }

    @Override // rikka.shizuku.InterfaceC0714wi
    public final C0221gi b() {
        return this.a;
    }

    /* JADX WARN: Code duplicated, block: B:69:0x00f8  */
    /* JADX WARN: Code duplicated, block: B:80:0x011b  */
    /* JADX WARN: Code duplicated, block: B:83:0x0120  */
    @Override // rikka.shizuku.InterfaceC0714wi
    public final void e(C0221gi c0221gi) {
        CompoundButton compoundButton;
        View view;
        boolean z;
        int i;
        ImageView imageView;
        String string;
        boolean z2;
        this.a = c0221gi;
        setVisibility(c0221gi.isVisible() ? 0 : 8);
        CharSequence charSequence = c0221gi.e;
        if (charSequence != null) {
            this.d.setText(charSequence);
            if (this.d.getVisibility() != 0) {
                this.d.setVisibility(0);
            }
        } else if (this.d.getVisibility() != 8) {
            this.d.setVisibility(8);
        }
        boolean zIsCheckable = c0221gi.isCheckable();
        if (zIsCheckable || this.c != null || this.e != null) {
            if ((this.a.x & 4) != 0) {
                if (this.c == null) {
                    if (this.p == null) {
                        this.p = LayoutInflater.from(getContext());
                    }
                    RadioButton radioButton = (RadioButton) this.p.inflate(R.layout._0_resource_name_obfuscated_res_0x7f0c0011, (ViewGroup) this, false);
                    this.c = radioButton;
                    LinearLayout linearLayout = this.i;
                    if (linearLayout != null) {
                        linearLayout.addView(radioButton, -1);
                    } else {
                        addView(radioButton, -1);
                    }
                }
                compoundButton = this.c;
                view = this.e;
            } else {
                if (this.e == null) {
                    if (this.p == null) {
                        this.p = LayoutInflater.from(getContext());
                    }
                    CheckBox checkBox = (CheckBox) this.p.inflate(R.layout._0_resource_name_obfuscated_res_0x7f0c000e, (ViewGroup) this, false);
                    this.e = checkBox;
                    LinearLayout linearLayout2 = this.i;
                    if (linearLayout2 != null) {
                        linearLayout2.addView(checkBox, -1);
                    } else {
                        addView(checkBox, -1);
                    }
                }
                compoundButton = this.e;
                view = this.c;
            }
            if (zIsCheckable) {
                compoundButton.setChecked(this.a.isChecked());
                if (compoundButton.getVisibility() != 0) {
                    compoundButton.setVisibility(0);
                }
                if (view != null && view.getVisibility() != 8) {
                    view.setVisibility(8);
                }
            } else {
                CheckBox checkBox2 = this.e;
                if (checkBox2 != null) {
                    checkBox2.setVisibility(8);
                }
                RadioButton radioButton2 = this.c;
                if (radioButton2 != null) {
                    radioButton2.setVisibility(8);
                }
            }
        }
        if (c0221gi.n.o()) {
            if ((c0221gi.n.n() ? c0221gi.j : c0221gi.h) != 0) {
                z = true;
            } else {
                z = false;
            }
        } else {
            z = false;
        }
        c0221gi.n.n();
        if (z) {
            C0221gi c0221gi2 = this.a;
            if (c0221gi2.n.o()) {
                if ((c0221gi2.n.n() ? c0221gi2.j : c0221gi2.h) != 0) {
                    z2 = true;
                } else {
                    z2 = false;
                }
            } else {
                z2 = false;
            }
            if (z2) {
                i = 0;
            } else {
                i = 8;
            }
        } else {
            i = 8;
        }
        if (i == 0) {
            TextView textView = this.f;
            C0221gi c0221gi3 = this.a;
            char c = c0221gi3.n.n() ? c0221gi3.j : c0221gi3.h;
            if (c == 0) {
                string = "";
            } else {
                MenuC0129di menuC0129di = c0221gi3.n;
                Resources resources = menuC0129di.a.getResources();
                StringBuilder sb = new StringBuilder();
                if (ViewConfiguration.get(menuC0129di.a).hasPermanentMenuKey()) {
                    sb.append(resources.getString(R.string._0_resource_name_obfuscated_res_0x7f110011));
                }
                int i2 = menuC0129di.n() ? c0221gi3.k : c0221gi3.i;
                C0221gi.c(sb, i2, 65536, resources.getString(R.string._0_resource_name_obfuscated_res_0x7f11000d));
                C0221gi.c(sb, i2, 4096, resources.getString(R.string._0_resource_name_obfuscated_res_0x7f110009));
                C0221gi.c(sb, i2, 2, resources.getString(R.string._0_resource_name_obfuscated_res_0x7f110008));
                C0221gi.c(sb, i2, 1, resources.getString(R.string._0_resource_name_obfuscated_res_0x7f11000e));
                C0221gi.c(sb, i2, 4, resources.getString(R.string._0_resource_name_obfuscated_res_0x7f110010));
                C0221gi.c(sb, i2, 8, resources.getString(R.string._0_resource_name_obfuscated_res_0x7f11000c));
                if (c == '\b') {
                    sb.append(resources.getString(R.string._0_resource_name_obfuscated_res_0x7f11000a));
                } else if (c == '\n') {
                    sb.append(resources.getString(R.string._0_resource_name_obfuscated_res_0x7f11000b));
                } else if (c != ' ') {
                    sb.append(c);
                } else {
                    sb.append(resources.getString(R.string._0_resource_name_obfuscated_res_0x7f11000f));
                }
                string = sb.toString();
            }
            textView.setText(string);
        }
        if (this.f.getVisibility() != i) {
            this.f.setVisibility(i);
        }
        Drawable icon = c0221gi.getIcon();
        this.a.n.getClass();
        boolean z3 = this.q;
        if ((z3 || this.m) && ((imageView = this.b) != null || icon != null || this.m)) {
            if (imageView == null) {
                if (this.p == null) {
                    this.p = LayoutInflater.from(getContext());
                }
                ImageView imageView2 = (ImageView) this.p.inflate(R.layout._0_resource_name_obfuscated_res_0x7f0c000f, (ViewGroup) this, false);
                this.b = imageView2;
                LinearLayout linearLayout3 = this.i;
                if (linearLayout3 != null) {
                    linearLayout3.addView(imageView2, 0);
                } else {
                    addView(imageView2, 0);
                }
            }
            if (icon != null || this.m) {
                ImageView imageView3 = this.b;
                if (!z3) {
                    icon = null;
                }
                imageView3.setImageDrawable(icon);
                if (this.b.getVisibility() != 0) {
                    this.b.setVisibility(0);
                }
            } else {
                this.b.setVisibility(8);
            }
        }
        setEnabled(c0221gi.isEnabled());
        boolean zHasSubMenu = c0221gi.hasSubMenu();
        ImageView imageView4 = this.g;
        if (imageView4 != null) {
            imageView4.setVisibility(zHasSubMenu ? 0 : 8);
        }
        setContentDescription(c0221gi.q);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        WeakHashMap weakHashMap = Hs.a;
        setBackground(this.j);
        TextView textView = (TextView) findViewById(R.id._0_resource_name_obfuscated_res_0x7f0901de);
        this.d = textView;
        int i = this.k;
        if (i != -1) {
            textView.setTextAppearance(this.l, i);
        }
        this.f = (TextView) findViewById(R.id._0_resource_name_obfuscated_res_0x7f09018f);
        ImageView imageView = (ImageView) findViewById(R.id._0_resource_name_obfuscated_res_0x7f0901b2);
        this.g = imageView;
        if (imageView != null) {
            imageView.setImageDrawable(this.n);
        }
        this.h = (ImageView) findViewById(R.id._0_resource_name_obfuscated_res_0x7f0900c4);
        this.i = (LinearLayout) findViewById(R.id._0_resource_name_obfuscated_res_0x7f090076);
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        if (this.b != null && this.m) {
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.b.getLayoutParams();
            int i3 = layoutParams.height;
            if (i3 > 0 && layoutParams2.width <= 0) {
                layoutParams2.width = i3;
            }
        }
        super.onMeasure(i, i2);
    }
}
