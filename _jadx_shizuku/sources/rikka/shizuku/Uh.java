package rikka.shizuku;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.material.internal.CheckableImageButton;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.WeakHashMap;
import moe.shizuku.privileged.api.R;

/* JADX INFO: loaded from: classes.dex */
public final class Uh<S> extends U9 {
    public int A0;
    public CharSequence B0;
    public int C0;
    public CharSequence D0;
    public TextView E0;
    public CheckableImageButton F0;
    public Xh G0;
    public boolean H0;
    public CharSequence I0;
    public CharSequence J0;
    public final LinkedHashSet m0;
    public final LinkedHashSet n0;
    public int o0;
    public Tj p0;
    public C0608t5 q0;
    public Oh r0;
    public int s0;
    public CharSequence t0;
    public boolean u0;
    public int v0;
    public int w0;
    public CharSequence x0;
    public int y0;
    public CharSequence z0;

    public Uh() {
        new LinkedHashSet();
        new LinkedHashSet();
        this.m0 = new LinkedHashSet();
        this.n0 = new LinkedHashSet();
    }

    public static int R(Context context) {
        Resources resources = context.getResources();
        int dimensionPixelOffset = resources.getDimensionPixelOffset(R.dimen._0_resource_name_obfuscated_res_0x7f070291);
        Calendar calendarB = Zr.b();
        calendarB.set(5, 1);
        Calendar calendarA = Zr.a(calendarB);
        calendarA.get(2);
        calendarA.get(1);
        int maximum = calendarA.getMaximum(7);
        calendarA.getActualMaximum(5);
        calendarA.getTimeInMillis();
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen._0_resource_name_obfuscated_res_0x7f070297) * maximum;
        return ((maximum - 1) * resources.getDimensionPixelOffset(R.dimen._0_resource_name_obfuscated_res_0x7f0702a5)) + dimensionPixelSize + (dimensionPixelOffset * 2);
    }

    public static boolean S(Context context, int i) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(AbstractC0046as.Q(context, R.attr._0_resource_name_obfuscated_res_0x7f0402ff, Oh.class.getCanonicalName()).data, new int[]{i});
        boolean z = typedArrayObtainStyledAttributes.getBoolean(0, false);
        typedArrayObtainStyledAttributes.recycle();
        return z;
    }

    @Override // rikka.shizuku.U9, rikka.shizuku.Ac
    public final void A(Bundle bundle) {
        super.A(bundle);
        bundle.putInt("OVERRIDE_THEME_RES_ID", this.o0);
        bundle.putParcelable("DATE_SELECTOR_KEY", null);
        C0608t5 c0608t5 = this.q0;
        C0577s5 c0577s5 = new C0577s5();
        int i = C0577s5.b;
        int i2 = C0577s5.b;
        long j = c0608t5.a.f;
        long j2 = c0608t5.b.f;
        c0577s5.a = Long.valueOf(c0608t5.d.f);
        Oh oh = this.r0;
        Ei ei = oh == null ? null : oh.Z;
        if (ei != null) {
            c0577s5.a = Long.valueOf(ei.f);
        }
        Bundle bundle2 = new Bundle();
        bundle2.putParcelable("DEEP_COPY_VALIDATOR_KEY", c0608t5.c);
        Ei eiB = Ei.b(j);
        Ei eiB2 = Ei.b(j2);
        U8 u8 = (U8) bundle2.getParcelable("DEEP_COPY_VALIDATOR_KEY");
        Long l = c0577s5.a;
        bundle.putParcelable("CALENDAR_CONSTRAINTS_KEY", new C0608t5(eiB, eiB2, u8, l == null ? null : Ei.b(l.longValue()), c0608t5.e));
        bundle.putParcelable("DAY_VIEW_DECORATOR_KEY", null);
        bundle.putInt("TITLE_TEXT_RES_ID_KEY", this.s0);
        bundle.putCharSequence("TITLE_TEXT_KEY", this.t0);
        bundle.putInt("INPUT_MODE_KEY", this.v0);
        bundle.putInt("POSITIVE_BUTTON_TEXT_RES_ID_KEY", this.w0);
        bundle.putCharSequence("POSITIVE_BUTTON_TEXT_KEY", this.x0);
        bundle.putInt("POSITIVE_BUTTON_CONTENT_DESCRIPTION_RES_ID_KEY", this.y0);
        bundle.putCharSequence("POSITIVE_BUTTON_CONTENT_DESCRIPTION_KEY", this.z0);
        bundle.putInt("NEGATIVE_BUTTON_TEXT_RES_ID_KEY", this.A0);
        bundle.putCharSequence("NEGATIVE_BUTTON_TEXT_KEY", this.B0);
        bundle.putInt("NEGATIVE_BUTTON_CONTENT_DESCRIPTION_RES_ID_KEY", this.C0);
        bundle.putCharSequence("NEGATIVE_BUTTON_CONTENT_DESCRIPTION_KEY", this.D0);
    }

    @Override // rikka.shizuku.U9, rikka.shizuku.Ac
    public final void B() {
        Tj tj;
        AbstractC0688vn cuVar;
        AbstractC0688vn cuVar2;
        super.B();
        Window window = O().getWindow();
        if (this.u0) {
            window.setLayout(-1, -1);
            window.setBackgroundDrawable(this.G0);
            if (!this.H0) {
                View viewFindViewById = I().findViewById(R.id._0_resource_name_obfuscated_res_0x7f0900be);
                ColorStateList colorStateListU = AbstractC0046as.u(viewFindViewById.getBackground());
                Integer numValueOf = colorStateListU != null ? Integer.valueOf(colorStateListU.getDefaultColor()) : null;
                int i = Build.VERSION.SDK_INT;
                boolean z = false;
                boolean z2 = numValueOf == null || numValueOf.intValue() == 0;
                int iR = AbstractC0744xh.r(window.getContext(), android.R.attr.colorBackground, -16777216);
                if (z2) {
                    numValueOf = Integer.valueOf(iR);
                }
                if (i >= 35) {
                    AbstractC0049b0.d(window, false);
                } else if (i >= 30) {
                    AbstractC0049b0.c(window, false);
                } else {
                    View decorView = window.getDecorView();
                    decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() | 1792);
                }
                window.getContext();
                int iE = i < 27 ? E6.e(AbstractC0744xh.r(window.getContext(), android.R.attr.navigationBarColor, -16777216), 128) : 0;
                window.setStatusBarColor(0);
                window.setNavigationBarColor(iE);
                boolean z3 = AbstractC0744xh.E(0) || AbstractC0744xh.E(numValueOf.intValue());
                Ge ge = new Ge(window.getDecorView());
                int i2 = Build.VERSION.SDK_INT;
                if (i2 >= 35) {
                    fu fuVar = new fu(window.getInsetsController(), ge);
                    fuVar.c = window;
                    cuVar = fuVar;
                } else if (i2 >= 30) {
                    eu euVar = new eu(window.getInsetsController(), ge);
                    euVar.c = window;
                    cuVar = euVar;
                } else {
                    cuVar = i2 >= 26 ? new cu(window, ge) : new bu(window, ge);
                }
                cuVar.x(z3);
                boolean zE = AbstractC0744xh.E(iR);
                if (AbstractC0744xh.E(iE) || (iE == 0 && zE)) {
                    z = true;
                }
                Ge ge2 = new Ge(window.getDecorView());
                int i3 = Build.VERSION.SDK_INT;
                if (i3 >= 35) {
                    fu fuVar2 = new fu(window.getInsetsController(), ge2);
                    fuVar2.c = window;
                    cuVar2 = fuVar2;
                } else if (i3 >= 30) {
                    eu euVar2 = new eu(window.getInsetsController(), ge2);
                    euVar2.c = window;
                    cuVar2 = euVar2;
                } else {
                    cuVar2 = i3 >= 26 ? new cu(window, ge2) : new bu(window, ge2);
                }
                cuVar2.w(z);
                C0179f7 c0179f7 = new C0179f7(viewFindViewById, viewFindViewById.getLayoutParams().height, viewFindViewById.getPaddingTop());
                WeakHashMap weakHashMap = Hs.a;
                AbstractC0817zs.l(viewFindViewById, c0179f7);
                this.H0 = true;
            }
        } else {
            window.setLayout(-2, -2);
            int dimensionPixelOffset = m().getDimensionPixelOffset(R.dimen._0_resource_name_obfuscated_res_0x7f070299);
            Rect rect = new Rect(dimensionPixelOffset, dimensionPixelOffset, dimensionPixelOffset, dimensionPixelOffset);
            window.setBackgroundDrawable(new InsetDrawable((Drawable) this.G0, dimensionPixelOffset, dimensionPixelOffset, dimensionPixelOffset, dimensionPixelOffset));
            window.getDecorView().setOnTouchListener(new ViewOnTouchListenerC0556rf(O(), rect));
        }
        H();
        int i4 = this.o0;
        if (i4 == 0) {
            Q();
            throw null;
        }
        Q();
        C0608t5 c0608t5 = this.q0;
        Oh oh = new Oh();
        Bundle bundle = new Bundle();
        bundle.putInt("THEME_RES_ID_KEY", i4);
        bundle.putParcelable("GRID_SELECTOR_KEY", null);
        bundle.putParcelable("CALENDAR_CONSTRAINTS_KEY", c0608t5);
        bundle.putParcelable("DAY_VIEW_DECORATOR_KEY", null);
        bundle.putParcelable("CURRENT_MONTH_KEY", c0608t5.d);
        oh.K(bundle);
        this.r0 = oh;
        if (this.v0 == 1) {
            tj = oh;
            Q();
            C0608t5 c0608t6 = this.q0;
            Yh yh = new Yh();
            Bundle bundle2 = new Bundle();
            bundle2.putInt("THEME_RES_ID_KEY", i4);
            bundle2.putParcelable("DATE_SELECTOR_KEY", null);
            bundle2.putParcelable("CALENDAR_CONSTRAINTS_KEY", c0608t6);
            yh.K(bundle2);
            tj = yh;
        }
        tj = oh;
        this.p0 = tj;
        this.E0.setText((this.v0 == 1 && m().getConfiguration().orientation == 2) ? this.J0 : this.I0);
        Q();
        throw null;
    }

    @Override // rikka.shizuku.U9, rikka.shizuku.Ac
    public final void C() {
        this.p0.W.clear();
        super.C();
    }

    @Override // rikka.shizuku.U9
    public final Dialog N() {
        Context contextH = H();
        H();
        int i = this.o0;
        if (i == 0) {
            Q();
            throw null;
        }
        Dialog dialog = new Dialog(contextH, i);
        Context context = dialog.getContext();
        this.u0 = S(context, android.R.attr.windowFullscreen);
        this.G0 = new Xh(context, null, R.attr._0_resource_name_obfuscated_res_0x7f0402ff, R.style._0_resource_name_obfuscated_res_0x7f120486);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(null, Lk.p, R.attr._0_resource_name_obfuscated_res_0x7f0402ff, R.style._0_resource_name_obfuscated_res_0x7f120486);
        int color = typedArrayObtainStyledAttributes.getColor(1, 0);
        typedArrayObtainStyledAttributes.recycle();
        this.G0.j(context);
        this.G0.l(ColorStateList.valueOf(color));
        Xh xh = this.G0;
        View decorView = dialog.getWindow().getDecorView();
        WeakHashMap weakHashMap = Hs.a;
        xh.k(AbstractC0817zs.e(decorView));
        return dialog;
    }

    public final void Q() {
        if (this.f.getParcelable("DATE_SELECTOR_KEY") != null) {
            throw new ClassCastException();
        }
    }

    @Override // rikka.shizuku.U9, android.content.DialogInterface.OnCancelListener
    public final void onCancel(DialogInterface dialogInterface) {
        Iterator it = this.m0.iterator();
        while (it.hasNext()) {
            ((DialogInterface.OnCancelListener) it.next()).onCancel(dialogInterface);
        }
    }

    @Override // rikka.shizuku.U9, android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        Iterator it = this.n0.iterator();
        while (it.hasNext()) {
            ((DialogInterface.OnDismissListener) it.next()).onDismiss(dialogInterface);
        }
        ViewGroup viewGroup = (ViewGroup) this.G;
        if (viewGroup != null) {
            viewGroup.removeAllViews();
        }
        super.onDismiss(dialogInterface);
    }

    @Override // rikka.shizuku.U9, rikka.shizuku.Ac
    public final void v(Bundle bundle) {
        super.v(bundle);
        if (bundle == null) {
            bundle = this.f;
        }
        this.o0 = bundle.getInt("OVERRIDE_THEME_RES_ID");
        if (bundle.getParcelable("DATE_SELECTOR_KEY") != null) {
            throw new ClassCastException();
        }
        this.q0 = (C0608t5) bundle.getParcelable("CALENDAR_CONSTRAINTS_KEY");
        if (bundle.getParcelable("DAY_VIEW_DECORATOR_KEY") != null) {
            throw new ClassCastException();
        }
        this.s0 = bundle.getInt("TITLE_TEXT_RES_ID_KEY");
        this.t0 = bundle.getCharSequence("TITLE_TEXT_KEY");
        this.v0 = bundle.getInt("INPUT_MODE_KEY");
        this.w0 = bundle.getInt("POSITIVE_BUTTON_TEXT_RES_ID_KEY");
        this.x0 = bundle.getCharSequence("POSITIVE_BUTTON_TEXT_KEY");
        this.y0 = bundle.getInt("POSITIVE_BUTTON_CONTENT_DESCRIPTION_RES_ID_KEY");
        this.z0 = bundle.getCharSequence("POSITIVE_BUTTON_CONTENT_DESCRIPTION_KEY");
        this.A0 = bundle.getInt("NEGATIVE_BUTTON_TEXT_RES_ID_KEY");
        this.B0 = bundle.getCharSequence("NEGATIVE_BUTTON_TEXT_KEY");
        this.C0 = bundle.getInt("NEGATIVE_BUTTON_CONTENT_DESCRIPTION_RES_ID_KEY");
        this.D0 = bundle.getCharSequence("NEGATIVE_BUTTON_CONTENT_DESCRIPTION_KEY");
        CharSequence text = this.t0;
        if (text == null) {
            text = H().getResources().getText(this.s0);
        }
        this.I0 = text;
        if (text != null) {
            CharSequence[] charSequenceArrSplit = TextUtils.split(String.valueOf(text), "\n");
            if (charSequenceArrSplit.length > 1) {
                text = charSequenceArrSplit[0];
            }
        } else {
            text = null;
        }
        this.J0 = text;
    }

    @Override // rikka.shizuku.Ac
    public final View w(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(this.u0 ? R.layout._0_resource_name_obfuscated_res_0x7f0c0077 : R.layout._0_resource_name_obfuscated_res_0x7f0c0076, viewGroup);
        Context context = viewInflate.getContext();
        if (this.u0) {
            viewInflate.findViewById(R.id._0_resource_name_obfuscated_res_0x7f090111).setLayoutParams(new LinearLayout.LayoutParams(R(context), -2));
        } else {
            viewInflate.findViewById(R.id._0_resource_name_obfuscated_res_0x7f090112).setLayoutParams(new LinearLayout.LayoutParams(R(context), -1));
        }
        TextView textView = (TextView) viewInflate.findViewById(R.id._0_resource_name_obfuscated_res_0x7f09011d);
        WeakHashMap weakHashMap = Hs.a;
        textView.setAccessibilityLiveRegion(1);
        this.F0 = (CheckableImageButton) viewInflate.findViewById(R.id._0_resource_name_obfuscated_res_0x7f09011f);
        this.E0 = (TextView) viewInflate.findViewById(R.id._0_resource_name_obfuscated_res_0x7f090123);
        this.F0.setTag("TOGGLE_BUTTON_TAG");
        CheckableImageButton checkableImageButton = this.F0;
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_checked}, AbstractC0744xh.v(context, R.drawable._0_resource_name_obfuscated_res_0x7f0800e8));
        stateListDrawable.addState(new int[0], AbstractC0744xh.v(context, R.drawable._0_resource_name_obfuscated_res_0x7f0800ea));
        checkableImageButton.setImageDrawable(stateListDrawable);
        this.F0.setChecked(this.v0 != 0);
        Hs.m(this.F0, null);
        CheckableImageButton checkableImageButton2 = this.F0;
        this.F0.setContentDescription(this.v0 == 1 ? checkableImageButton2.getContext().getString(R.string._0_resource_name_obfuscated_res_0x7f1100e3) : checkableImageButton2.getContext().getString(R.string._0_resource_name_obfuscated_res_0x7f1100e5));
        this.F0.setOnClickListener(new Y0(4, this));
        Q();
        throw null;
    }
}
