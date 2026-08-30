package androidx.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.AbsSavedState;
import android.view.View;
import android.widget.TextView;
import rikka.shizuku.Jq;

/* JADX INFO: loaded from: classes.dex */
public abstract class TwoStatePreference extends Preference {
    public boolean N;
    public String O;
    public String P;
    public boolean Q;
    public boolean R;

    public TwoStatePreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0, 0);
    }

    public final void B(boolean z) {
        boolean z2 = this.N != z;
        if (z2 || !this.Q) {
            this.N = z;
            this.Q = true;
            if (A()) {
                boolean z3 = !z;
                if (A()) {
                    z3 = this.b.b().getBoolean(this.l, z3);
                }
                if (z != z3) {
                    SharedPreferences.Editor editorA = this.b.a();
                    editorA.putBoolean(this.l, z);
                    if (!this.b.e) {
                        editorA.apply();
                    }
                }
            }
            if (z2) {
                j(z());
                i();
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:18:0x0030  */
    /* JADX WARN: Code duplicated, block: B:20:0x003a  */
    /* JADX WARN: Code duplicated, block: B:23:0x0041  */
    /* JADX WARN: Code duplicated, block: B:26:0x0049  */
    /* JADX WARN: Code duplicated, block: B:28:? A[RETURN, SYNTHETIC] */
    public final void C(View view) {
        boolean z;
        int i;
        CharSequence charSequenceG;
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            if (!this.N || TextUtils.isEmpty(this.O)) {
                if (this.N || TextUtils.isEmpty(this.P)) {
                    z = true;
                } else {
                    textView.setText(this.P);
                }
                if (z) {
                    charSequenceG = g();
                    if (!TextUtils.isEmpty(charSequenceG)) {
                        textView.setText(charSequenceG);
                        z = false;
                    }
                }
                i = z ? 8 : 0;
                if (i != textView.getVisibility()) {
                    textView.setVisibility(i);
                }
            }
            textView.setText(this.O);
            z = false;
            if (z) {
                charSequenceG = g();
                if (!TextUtils.isEmpty(charSequenceG)) {
                    textView.setText(charSequenceG);
                    z = false;
                }
            }
            if (z) {
            }
            if (i != textView.getVisibility()) {
                textView.setVisibility(i);
            }
        }
    }

    @Override // androidx.preference.Preference
    public final void n() {
        boolean z = !this.N;
        if (a(Boolean.valueOf(z))) {
            B(z);
        }
    }

    @Override // androidx.preference.Preference
    public final Object q(TypedArray typedArray, int i) {
        return Boolean.valueOf(typedArray.getBoolean(i, false));
    }

    @Override // androidx.preference.Preference
    public final void r(Parcelable parcelable) {
        if (!parcelable.getClass().equals(Jq.class)) {
            super.r(parcelable);
            return;
        }
        Jq jq = (Jq) parcelable;
        super.r(jq.getSuperState());
        B(jq.a);
    }

    @Override // androidx.preference.Preference
    public final Parcelable s() {
        super.s();
        AbsSavedState absSavedState = AbsSavedState.EMPTY_STATE;
        if (this.r) {
            return absSavedState;
        }
        Jq jq = new Jq();
        jq.a = this.N;
        return jq;
    }

    @Override // androidx.preference.Preference
    public final void t(Object obj) {
        if (obj == null) {
            obj = Boolean.FALSE;
        }
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        if (A()) {
            zBooleanValue = this.b.b().getBoolean(this.l, zBooleanValue);
        }
        B(zBooleanValue);
    }

    @Override // androidx.preference.Preference
    public final boolean z() {
        boolean z;
        if (this.R) {
            z = this.N;
        } else {
            z = !this.N;
        }
        return z || super.z();
    }
}
