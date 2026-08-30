package androidx.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.AbsSavedState;
import moe.shizuku.privileged.api.R;
import rikka.shizuku.Ba;
import rikka.shizuku.Hn;
import rikka.shizuku.Jk;
import rikka.shizuku.Ne;

/* JADX INFO: loaded from: classes.dex */
public class EditTextPreference extends DialogPreference {
    public String T;

    /* JADX WARN: Illegal instructions before constructor call */
    public EditTextPreference(Context context, AttributeSet attributeSet) {
        int iC = Hn.c(context, R.attr._0_resource_name_obfuscated_res_0x7f0401a8, android.R.attr.editTextPreferenceStyle);
        super(context, attributeSet, iC, 0);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Jk.d, iC, 0);
        if (typedArrayObtainStyledAttributes.getBoolean(0, typedArrayObtainStyledAttributes.getBoolean(0, false))) {
            if (Ne.b == null) {
                Ne.b = new Ne();
            }
            this.L = Ne.b;
            i();
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    public final void B(String str) {
        boolean z = z();
        this.T = str;
        w(str);
        boolean z2 = z();
        if (z2 != z) {
            j(z2);
        }
        i();
    }

    @Override // androidx.preference.Preference
    public final Object q(TypedArray typedArray, int i) {
        return typedArray.getString(i);
    }

    @Override // androidx.preference.Preference
    public final void r(Parcelable parcelable) {
        if (!parcelable.getClass().equals(Ba.class)) {
            super.r(parcelable);
            return;
        }
        Ba ba = (Ba) parcelable;
        super.r(ba.getSuperState());
        B(ba.a);
    }

    @Override // androidx.preference.Preference
    public final Parcelable s() {
        super.s();
        AbsSavedState absSavedState = AbsSavedState.EMPTY_STATE;
        if (this.r) {
            return absSavedState;
        }
        Ba ba = new Ba();
        ba.a = this.T;
        return ba;
    }

    @Override // androidx.preference.Preference
    public final void t(Object obj) {
        B(f((String) obj));
    }

    @Override // androidx.preference.Preference
    public final boolean z() {
        return TextUtils.isEmpty(this.T) || super.z();
    }
}
