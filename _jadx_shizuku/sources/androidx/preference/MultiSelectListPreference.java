package androidx.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.AbsSavedState;
import java.util.HashSet;
import java.util.Set;
import moe.shizuku.privileged.api.R;
import rikka.shizuku.Hn;
import rikka.shizuku.Jk;
import rikka.shizuku.Ki;

/* JADX INFO: loaded from: classes.dex */
public class MultiSelectListPreference extends DialogPreference {
    public final CharSequence[] T;
    public final CharSequence[] U;
    public final HashSet V;

    /* JADX WARN: Illegal instructions before constructor call */
    public MultiSelectListPreference(Context context, AttributeSet attributeSet) {
        int iC = Hn.c(context, R.attr._0_resource_name_obfuscated_res_0x7f040181, android.R.attr.dialogPreferenceStyle);
        super(context, attributeSet, iC, 0);
        this.V = new HashSet();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Jk.f, iC, 0);
        CharSequence[] textArray = typedArrayObtainStyledAttributes.getTextArray(2);
        this.T = textArray == null ? typedArrayObtainStyledAttributes.getTextArray(0) : textArray;
        CharSequence[] textArray2 = typedArrayObtainStyledAttributes.getTextArray(3);
        this.U = textArray2 == null ? typedArrayObtainStyledAttributes.getTextArray(1) : textArray2;
        typedArrayObtainStyledAttributes.recycle();
    }

    public final void B(Set set) {
        HashSet hashSet = this.V;
        hashSet.clear();
        hashSet.addAll(set);
        if (A()) {
            if (!set.equals(A() ? this.b.b().getStringSet(this.l, null) : null)) {
                SharedPreferences.Editor editorA = this.b.a();
                editorA.putStringSet(this.l, set);
                if (!this.b.e) {
                    editorA.apply();
                }
            }
        }
        i();
    }

    @Override // androidx.preference.Preference
    public final Object q(TypedArray typedArray, int i) {
        CharSequence[] textArray = typedArray.getTextArray(i);
        HashSet hashSet = new HashSet();
        for (CharSequence charSequence : textArray) {
            hashSet.add(charSequence.toString());
        }
        return hashSet;
    }

    @Override // androidx.preference.Preference
    public final void r(Parcelable parcelable) {
        if (!parcelable.getClass().equals(Ki.class)) {
            super.r(parcelable);
            return;
        }
        Ki ki = (Ki) parcelable;
        super.r(ki.getSuperState());
        B(ki.a);
    }

    @Override // androidx.preference.Preference
    public final Parcelable s() {
        super.s();
        AbsSavedState absSavedState = AbsSavedState.EMPTY_STATE;
        if (this.r) {
            return absSavedState;
        }
        Ki ki = new Ki();
        ki.a = this.V;
        return ki;
    }

    @Override // androidx.preference.Preference
    public final void t(Object obj) {
        Set<String> stringSet = (Set) obj;
        if (A()) {
            stringSet = this.b.b().getStringSet(this.l, stringSet);
        }
        B(stringSet);
    }
}
