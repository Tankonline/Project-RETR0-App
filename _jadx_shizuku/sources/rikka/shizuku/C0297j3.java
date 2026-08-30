package rikka.shizuku;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.ThemedSpinnerAdapter;

/* JADX INFO: renamed from: rikka.shizuku.j3, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0297j3 extends Spinner {
    public static final int[] i = {R.attr.spinnerMode};
    public final T0 a;
    public final Context b;
    public final X2 c;
    public SpinnerAdapter d;
    public final boolean e;
    public final InterfaceC0268i3 f;
    public int g;
    public final Rect h;

    /* JADX WARN: Code duplicated, block: B:26:0x0067 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:28:0x006a  */
    /* JADX WARN: Code duplicated, block: B:29:0x009d  */
    /* JADX WARN: Code duplicated, block: B:32:0x00b0  */
    /* JADX WARN: Code duplicated, block: B:35:0x00ca  */
    /* JADX WARN: Code duplicated, block: B:39:0x00d7  */
    public C0297j3(Context context, AttributeSet attributeSet) throws Throwable {
        TypedArray typedArrayObtainStyledAttributes;
        CharSequence[] textArray;
        SpinnerAdapter spinnerAdapter;
        super(context, attributeSet, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f04044d);
        this.h = new Rect();
        Lp.a(this, getContext());
        int[] iArr = Nk.v;
        K3 k3N = K3.n(context, attributeSet, iArr, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f04044d);
        this.a = new T0(this);
        TypedArray typedArray = (TypedArray) k3N.a;
        int resourceId = typedArray.getResourceId(4, 0);
        if (resourceId != 0) {
            this.b = new F7(context, resourceId);
        } else {
            this.b = context;
        }
        int i2 = -1;
        TypedArray typedArray2 = null;
        try {
            typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, i, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f04044d, 0);
            try {
                try {
                    if (typedArrayObtainStyledAttributes.hasValue(0)) {
                        i2 = typedArrayObtainStyledAttributes.getInt(0, 0);
                    }
                } catch (Exception e) {
                    e = e;
                    Log.i("AppCompatSpinner", "Could not read android:spinnerMode", e);
                    if (typedArrayObtainStyledAttributes != null) {
                    }
                    if (i2 != 0) {
                        DialogInterfaceOnClickListenerC0083c3 dialogInterfaceOnClickListenerC0083c3 = new DialogInterfaceOnClickListenerC0083c3(this);
                        this.f = dialogInterfaceOnClickListenerC0083c3;
                        dialogInterfaceOnClickListenerC0083c3.c = typedArray.getString(2);
                    } else if (i2 == 1) {
                        C0206g3 c0206g3 = new C0206g3(this, this.b, attributeSet);
                        K3 k3N2 = K3.n(this.b, attributeSet, iArr, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f04044d);
                        this.g = ((TypedArray) k3N2.a).getLayoutDimension(3, -2);
                        c0206g3.k(k3N2.i(1));
                        c0206g3.C = typedArray.getString(2);
                        k3N2.o();
                        this.f = c0206g3;
                        this.c = new X2(this, this, c0206g3);
                    }
                    textArray = typedArray.getTextArray(0);
                    if (textArray != null) {
                        ArrayAdapter arrayAdapter = new ArrayAdapter(context, R.layout.simple_spinner_item, textArray);
                        arrayAdapter.setDropDownViewResource(moe.shizuku.privileged.api.R.layout._0_resource_name_obfuscated_res_0x7f0c00a2);
                        setAdapter((SpinnerAdapter) arrayAdapter);
                    }
                    k3N.o();
                    this.e = true;
                    spinnerAdapter = this.d;
                    if (spinnerAdapter != null) {
                        setAdapter(spinnerAdapter);
                        this.d = null;
                    }
                    this.a.i(attributeSet, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f04044d);
                }
            } catch (Throwable th) {
                th = th;
                typedArray2 = typedArrayObtainStyledAttributes;
                if (typedArray2 != null) {
                    typedArray2.recycle();
                }
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            typedArrayObtainStyledAttributes = null;
        } catch (Throwable th2) {
            th = th2;
            if (typedArray2 != null) {
                typedArray2.recycle();
            }
            throw th;
        }
        typedArrayObtainStyledAttributes.recycle();
        if (i2 != 0) {
            DialogInterfaceOnClickListenerC0083c3 dialogInterfaceOnClickListenerC0083c4 = new DialogInterfaceOnClickListenerC0083c3(this);
            this.f = dialogInterfaceOnClickListenerC0083c4;
            dialogInterfaceOnClickListenerC0083c4.c = typedArray.getString(2);
        } else if (i2 == 1) {
            C0206g3 c0206g4 = new C0206g3(this, this.b, attributeSet);
            K3 k3N3 = K3.n(this.b, attributeSet, iArr, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f04044d);
            this.g = ((TypedArray) k3N3.a).getLayoutDimension(3, -2);
            c0206g4.k(k3N3.i(1));
            c0206g4.C = typedArray.getString(2);
            k3N3.o();
            this.f = c0206g4;
            this.c = new X2(this, this, c0206g4);
        }
        textArray = typedArray.getTextArray(0);
        if (textArray != null) {
            ArrayAdapter arrayAdapter2 = new ArrayAdapter(context, R.layout.simple_spinner_item, textArray);
            arrayAdapter2.setDropDownViewResource(moe.shizuku.privileged.api.R.layout._0_resource_name_obfuscated_res_0x7f0c00a2);
            setAdapter((SpinnerAdapter) arrayAdapter2);
        }
        k3N.o();
        this.e = true;
        spinnerAdapter = this.d;
        if (spinnerAdapter != null) {
            setAdapter(spinnerAdapter);
            this.d = null;
        }
        this.a.i(attributeSet, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f04044d);
    }

    public final int a(SpinnerAdapter spinnerAdapter, Drawable drawable) {
        int i2 = 0;
        if (spinnerAdapter == null) {
            return 0;
        }
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 0);
        int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 0);
        int iMax = Math.max(0, getSelectedItemPosition());
        int iMin = Math.min(spinnerAdapter.getCount(), iMax + 15);
        View view = null;
        int iMax2 = 0;
        for (int iMax3 = Math.max(0, iMax - (15 - (iMin - iMax))); iMax3 < iMin; iMax3++) {
            int itemViewType = spinnerAdapter.getItemViewType(iMax3);
            if (itemViewType != i2) {
                view = null;
                i2 = itemViewType;
            }
            view = spinnerAdapter.getView(iMax3, view, this);
            if (view.getLayoutParams() == null) {
                view.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
            }
            view.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
            iMax2 = Math.max(iMax2, view.getMeasuredWidth());
        }
        if (drawable == null) {
            return iMax2;
        }
        Rect rect = this.h;
        drawable.getPadding(rect);
        return rect.left + rect.right + iMax2;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void drawableStateChanged() {
        super.drawableStateChanged();
        T0 t0 = this.a;
        if (t0 != null) {
            t0.a();
        }
    }

    @Override // android.widget.Spinner
    public final int getDropDownHorizontalOffset() {
        InterfaceC0268i3 interfaceC0268i3 = this.f;
        return interfaceC0268i3 != null ? interfaceC0268i3.c() : super.getDropDownHorizontalOffset();
    }

    @Override // android.widget.Spinner
    public final int getDropDownVerticalOffset() {
        InterfaceC0268i3 interfaceC0268i3 = this.f;
        return interfaceC0268i3 != null ? interfaceC0268i3.f() : super.getDropDownVerticalOffset();
    }

    @Override // android.widget.Spinner
    public final int getDropDownWidth() {
        return this.f != null ? this.g : super.getDropDownWidth();
    }

    @Override // android.widget.Spinner
    public final Drawable getPopupBackground() {
        InterfaceC0268i3 interfaceC0268i3 = this.f;
        return interfaceC0268i3 != null ? interfaceC0268i3.g() : super.getPopupBackground();
    }

    @Override // android.widget.Spinner
    public final Context getPopupContext() {
        return this.b;
    }

    @Override // android.widget.Spinner
    public final CharSequence getPrompt() {
        InterfaceC0268i3 interfaceC0268i3 = this.f;
        return interfaceC0268i3 != null ? interfaceC0268i3.h() : super.getPrompt();
    }

    @Override // android.widget.Spinner, android.widget.AdapterView, android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        InterfaceC0268i3 interfaceC0268i3 = this.f;
        if (interfaceC0268i3 == null || !interfaceC0268i3.a()) {
            return;
        }
        interfaceC0268i3.dismiss();
    }

    @Override // android.widget.Spinner, android.widget.AbsSpinner, android.view.View
    public final void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
        if (this.f == null || View.MeasureSpec.getMode(i2) != Integer.MIN_VALUE) {
            return;
        }
        setMeasuredDimension(Math.min(Math.max(getMeasuredWidth(), a(getAdapter(), getBackground())), View.MeasureSpec.getSize(i2)), getMeasuredHeight());
    }

    @Override // android.widget.Spinner, android.widget.AbsSpinner, android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        ViewTreeObserver viewTreeObserver;
        C0237h3 c0237h3 = (C0237h3) parcelable;
        super.onRestoreInstanceState(c0237h3.getSuperState());
        if (!c0237h3.a || (viewTreeObserver = getViewTreeObserver()) == null) {
            return;
        }
        viewTreeObserver.addOnGlobalLayoutListener(new Y2(0, this));
    }

    @Override // android.widget.Spinner, android.widget.AbsSpinner, android.view.View
    public final Parcelable onSaveInstanceState() {
        C0237h3 c0237h3 = new C0237h3(super.onSaveInstanceState());
        InterfaceC0268i3 interfaceC0268i3 = this.f;
        c0237h3.a = interfaceC0268i3 != null && interfaceC0268i3.a();
        return c0237h3;
    }

    @Override // android.widget.Spinner, android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        X2 x2 = this.c;
        if (x2 == null || !x2.onTouch(this, motionEvent)) {
            return super.onTouchEvent(motionEvent);
        }
        return true;
    }

    @Override // android.widget.Spinner, android.view.View
    public final boolean performClick() {
        InterfaceC0268i3 interfaceC0268i3 = this.f;
        if (interfaceC0268i3 == null) {
            return super.performClick();
        }
        if (interfaceC0268i3.a()) {
            return true;
        }
        this.f.d(AbstractC0021a3.b(this), AbstractC0021a3.a(this));
        return true;
    }

    @Override // android.view.View
    public final void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        T0 t0 = this.a;
        if (t0 != null) {
            t0.k();
        }
    }

    @Override // android.view.View
    public final void setBackgroundResource(int i2) {
        super.setBackgroundResource(i2);
        T0 t0 = this.a;
        if (t0 != null) {
            t0.l(i2);
        }
    }

    @Override // android.widget.Spinner
    public final void setDropDownHorizontalOffset(int i2) {
        InterfaceC0268i3 interfaceC0268i3 = this.f;
        if (interfaceC0268i3 == null) {
            super.setDropDownHorizontalOffset(i2);
        } else {
            interfaceC0268i3.n(i2);
            interfaceC0268i3.b(i2);
        }
    }

    @Override // android.widget.Spinner
    public final void setDropDownVerticalOffset(int i2) {
        InterfaceC0268i3 interfaceC0268i3 = this.f;
        if (interfaceC0268i3 != null) {
            interfaceC0268i3.l(i2);
        } else {
            super.setDropDownVerticalOffset(i2);
        }
    }

    @Override // android.widget.Spinner
    public final void setDropDownWidth(int i2) {
        if (this.f != null) {
            this.g = i2;
        } else {
            super.setDropDownWidth(i2);
        }
    }

    @Override // android.widget.Spinner
    public final void setPopupBackgroundDrawable(Drawable drawable) {
        InterfaceC0268i3 interfaceC0268i3 = this.f;
        if (interfaceC0268i3 != null) {
            interfaceC0268i3.k(drawable);
        } else {
            super.setPopupBackgroundDrawable(drawable);
        }
    }

    @Override // android.widget.Spinner
    public final void setPopupBackgroundResource(int i2) {
        setPopupBackgroundDrawable(AbstractC0744xh.v(this.b, i2));
    }

    @Override // android.widget.Spinner
    public final void setPrompt(CharSequence charSequence) {
        InterfaceC0268i3 interfaceC0268i3 = this.f;
        if (interfaceC0268i3 != null) {
            interfaceC0268i3.i(charSequence);
        } else {
            super.setPrompt(charSequence);
        }
    }

    @Override // android.widget.AdapterView
    public final void setAdapter(SpinnerAdapter spinnerAdapter) {
        if (!this.e) {
            this.d = spinnerAdapter;
            return;
        }
        super.setAdapter(spinnerAdapter);
        InterfaceC0268i3 interfaceC0268i3 = this.f;
        if (interfaceC0268i3 != null) {
            Context context = this.b;
            if (context == null) {
                context = getContext();
            }
            Resources.Theme theme = context.getTheme();
            C0114d3 c0114d3 = new C0114d3();
            c0114d3.a = spinnerAdapter;
            if (spinnerAdapter instanceof ListAdapter) {
                c0114d3.b = (ListAdapter) spinnerAdapter;
            }
            if (theme != null && (spinnerAdapter instanceof ThemedSpinnerAdapter)) {
                AbstractC0052b3.a((ThemedSpinnerAdapter) spinnerAdapter, theme);
            }
            interfaceC0268i3.m(c0114d3);
        }
    }
}
