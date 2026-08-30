package androidx.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.AbsSavedState;
import moe.shizuku.privileged.api.R;
import rikka.shizuku.C0035ah;
import rikka.shizuku.Hn;
import rikka.shizuku.InterfaceC0161ek;
import rikka.shizuku.Jk;
import rikka.shizuku.Me;

/* JADX INFO: loaded from: classes.dex */
public class ListPreference extends DialogPreference {
    public CharSequence[] T;
    public CharSequence[] U;
    public String V;
    public String W;
    public boolean X;

    public ListPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Jk.e, i, i2);
        CharSequence[] textArray = typedArrayObtainStyledAttributes.getTextArray(2);
        this.T = textArray == null ? typedArrayObtainStyledAttributes.getTextArray(0) : textArray;
        CharSequence[] textArray2 = typedArrayObtainStyledAttributes.getTextArray(3);
        this.U = textArray2 == null ? typedArrayObtainStyledAttributes.getTextArray(1) : textArray2;
        if (typedArrayObtainStyledAttributes.getBoolean(4, typedArrayObtainStyledAttributes.getBoolean(4, false))) {
            if (Me.b == null) {
                Me.b = new Me();
            }
            this.L = Me.b;
            i();
        }
        typedArrayObtainStyledAttributes.recycle();
        TypedArray typedArrayObtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, Jk.g, i, i2);
        this.W = Hn.e(typedArrayObtainStyledAttributes2, 33, 7);
        typedArrayObtainStyledAttributes2.recycle();
    }

    public final int B(String str) {
        CharSequence[] charSequenceArr;
        if (str == null || (charSequenceArr = this.U) == null) {
            return -1;
        }
        for (int length = charSequenceArr.length - 1; length >= 0; length--) {
            if (TextUtils.equals(this.U[length].toString(), str)) {
                return length;
            }
        }
        return -1;
    }

    public void C(CharSequence[] charSequenceArr) {
        this.T = charSequenceArr;
    }

    public void D(String str) {
        boolean zEquals = TextUtils.equals(this.V, str);
        if (zEquals && this.X) {
            return;
        }
        this.V = str;
        this.X = true;
        w(str);
        if (zEquals) {
            return;
        }
        i();
    }

    @Override // androidx.preference.Preference
    public final CharSequence g() {
        CharSequence[] charSequenceArr;
        InterfaceC0161ek interfaceC0161ek = this.L;
        if (interfaceC0161ek != null) {
            return interfaceC0161ek.a(this);
        }
        int iB = B(this.V);
        CharSequence charSequence = (iB < 0 || (charSequenceArr = this.T) == null) ? null : charSequenceArr[iB];
        CharSequence charSequenceG = super.g();
        String str = this.W;
        if (str != null) {
            if (charSequence == null) {
                charSequence = "";
            }
            String str2 = String.format(str, charSequence);
            if (!TextUtils.equals(str2, charSequenceG)) {
                Log.w("ListPreference", "Setting a summary with a String formatting marker is no longer supported. You should use a SummaryProvider instead.");
                return str2;
            }
        }
        return charSequenceG;
    }

    @Override // androidx.preference.Preference
    public final Object q(TypedArray typedArray, int i) {
        return typedArray.getString(i);
    }

    @Override // androidx.preference.Preference
    public final void r(Parcelable parcelable) {
        if (!parcelable.getClass().equals(C0035ah.class)) {
            super.r(parcelable);
            return;
        }
        C0035ah c0035ah = (C0035ah) parcelable;
        super.r(c0035ah.getSuperState());
        D(c0035ah.a);
    }

    @Override // androidx.preference.Preference
    public final Parcelable s() {
        super.s();
        AbsSavedState absSavedState = AbsSavedState.EMPTY_STATE;
        if (this.r) {
            return absSavedState;
        }
        C0035ah c0035ah = new C0035ah();
        c0035ah.a = this.V;
        return c0035ah;
    }

    @Override // androidx.preference.Preference
    public final void t(Object obj) {
        D(f((String) obj));
    }

    @Override // androidx.preference.Preference
    public final void y(CharSequence charSequence) {
        super.y(charSequence);
        if (charSequence == null) {
            this.W = null;
        } else {
            this.W = ((String) charSequence).toString();
        }
    }

    public ListPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, Hn.c(context, R.attr._0_resource_name_obfuscated_res_0x7f040181, android.R.attr.dialogPreferenceStyle), 0);
    }
}
