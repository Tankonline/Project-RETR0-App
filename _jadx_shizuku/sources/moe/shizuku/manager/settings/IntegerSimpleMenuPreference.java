package moe.shizuku.manager.settings;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.AbsSavedState;
import android.view.ContextThemeWrapper;
import android.view.View;
import androidx.preference.Preference;
import moe.shizuku.privileged.api.R;
import rikka.shizuku.C0318jo;
import rikka.shizuku.C0500pk;
import rikka.shizuku.C0610t7;
import rikka.shizuku.C0711wf;
import rikka.shizuku.Hn;
import rikka.shizuku.Mk;

/* JADX INFO: loaded from: classes.dex */
@SuppressLint({"RestrictedApi"})
public class IntegerSimpleMenuPreference extends Preference {
    public final C0318jo N;
    public View O;
    public View P;
    public final CharSequence[] Q;
    public final int[] R;
    public int S;
    public String T;
    public boolean U;

    @SuppressLint({"RestrictedApi"})
    public IntegerSimpleMenuPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Mk.a, i, i2);
        CharSequence[] textArray = typedArrayObtainStyledAttributes.getTextArray(2);
        this.Q = textArray == null ? typedArrayObtainStyledAttributes.getTextArray(0) : textArray;
        this.R = typedArrayObtainStyledAttributes.getResources().getIntArray(typedArrayObtainStyledAttributes.getResourceId(3, typedArrayObtainStyledAttributes.getResourceId(1, 0)));
        typedArrayObtainStyledAttributes.recycle();
        TypedArray typedArrayObtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, Mk.b, i, i2);
        this.T = Hn.e(typedArrayObtainStyledAttributes2, 33, 7);
        typedArrayObtainStyledAttributes2.recycle();
        TypedArray typedArrayObtainStyledAttributes3 = context.obtainStyledAttributes(attributeSet, Mk.c, i, i2);
        int resourceId = typedArrayObtainStyledAttributes3.getResourceId(0, R.style._0_resource_name_obfuscated_res_0x7f1204ce);
        int resourceId2 = typedArrayObtainStyledAttributes3.getResourceId(1, R.style._0_resource_name_obfuscated_res_0x7f120342);
        C0318jo c0318jo = new C0318jo(resourceId2 != 0 ? new ContextThemeWrapper(context, resourceId2) : context, attributeSet, resourceId);
        this.N = c0318jo;
        c0318jo.k = new C0610t7(4, this);
        typedArrayObtainStyledAttributes3.recycle();
    }

    public final void B(int i) {
        boolean z = this.S != i;
        if (z || !this.U) {
            this.S = i;
            this.U = true;
            v(i);
            if (z) {
                i();
            }
        }
    }

    @Override // androidx.preference.Preference
    public final CharSequence g() {
        int length;
        CharSequence[] charSequenceArr;
        int i = this.S;
        int[] iArr = this.R;
        if (iArr == null) {
            length = -1;
            break;
        }
        length = iArr.length - 1;
        while (true) {
            if (length < 0) {
                length = -1;
                break;
            }
            if (iArr[length] == i) {
                break;
            }
            length--;
        }
        CharSequence charSequence = (length < 0 || (charSequenceArr = this.Q) == null) ? null : charSequenceArr[length];
        String str = this.T;
        if (str == null) {
            return super.g();
        }
        if (charSequence == null) {
            charSequence = "";
        }
        return String.format(str, charSequence);
    }

    @Override // androidx.preference.Preference
    public final void m(C0500pk c0500pk) {
        super.m(c0500pk);
        View view = c0500pk.a;
        this.P = view;
        View viewFindViewById = view.findViewById(android.R.id.empty);
        this.O = viewFindViewById;
        if (viewFindViewById == null) {
            throw new IllegalStateException("SimpleMenuPreference item layout must containa view id is android.R.id.empty to support iconSpaceReserved");
        }
    }

    @Override // androidx.preference.Preference
    public final void n() {
        C0318jo c0318jo;
        int length;
        CharSequence[] charSequenceArr = this.Q;
        if (charSequenceArr == null || charSequenceArr.length == 0 || (c0318jo = this.N) == null) {
            return;
        }
        c0318jo.l = charSequenceArr;
        int i = this.S;
        int[] iArr = this.R;
        if (iArr != null) {
            length = iArr.length - 1;
            while (length >= 0) {
                if (iArr[length] != i) {
                    length--;
                }
            }
            length = -1;
        } else {
            length = -1;
        }
        c0318jo.m = length;
        c0318jo.e(this.P, (View) this.P.getParent(), (int) this.O.getX());
    }

    @Override // androidx.preference.Preference
    public final Object q(TypedArray typedArray, int i) {
        return Integer.valueOf(typedArray.getInt(i, 1));
    }

    @Override // androidx.preference.Preference
    public final void r(Parcelable parcelable) {
        if (!parcelable.getClass().equals(C0711wf.class)) {
            super.r(parcelable);
            return;
        }
        C0711wf c0711wf = (C0711wf) parcelable;
        super.r(c0711wf.getSuperState());
        B(c0711wf.a);
    }

    @Override // androidx.preference.Preference
    public final Parcelable s() {
        super.s();
        AbsSavedState absSavedState = AbsSavedState.EMPTY_STATE;
        if (this.r) {
            return absSavedState;
        }
        C0711wf c0711wf = new C0711wf();
        c0711wf.a = this.S;
        return c0711wf;
    }

    @Override // androidx.preference.Preference
    public final void t(Object obj) {
        if (obj == null) {
            obj = 0;
        }
        B(e(((Integer) obj).intValue()));
    }

    @Override // androidx.preference.Preference
    public final void y(CharSequence charSequence) {
        super.y(charSequence);
        if (charSequence == null && this.T != null) {
            this.T = null;
        } else {
            if (charSequence == null || charSequence.equals(this.T)) {
                return;
            }
            this.T = ((String) charSequence).toString();
        }
    }

    public IntegerSimpleMenuPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, R.style._0_resource_name_obfuscated_res_0x7f120180);
    }

    public IntegerSimpleMenuPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr._0_resource_name_obfuscated_res_0x7f040440);
    }

    public IntegerSimpleMenuPreference(Context context) {
        this(context, null);
    }
}
