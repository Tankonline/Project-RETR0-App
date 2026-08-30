package androidx.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Checkable;
import android.widget.CompoundButton;
import moe.shizuku.privileged.api.R;
import rikka.shizuku.C0500pk;
import rikka.shizuku.Hn;
import rikka.shizuku.Jk;
import rikka.shizuku.Q5;

/* JADX INFO: loaded from: classes.dex */
public class CheckBoxPreference extends TwoStatePreference {
    public final Q5 S;

    /* JADX WARN: Illegal instructions before constructor call */
    public CheckBoxPreference(Context context, AttributeSet attributeSet) {
        int iC = Hn.c(context, R.attr._0_resource_name_obfuscated_res_0x7f0400af, android.R.attr.checkBoxPreferenceStyle);
        super(context, attributeSet, iC, 0);
        this.S = new Q5(this, 0);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Jk.b, iC, 0);
        this.O = Hn.e(typedArrayObtainStyledAttributes, 5, 0);
        if (this.N) {
            i();
        }
        String string = typedArrayObtainStyledAttributes.getString(4);
        this.P = string == null ? typedArrayObtainStyledAttributes.getString(1) : string;
        if (!this.N) {
            i();
        }
        this.R = typedArrayObtainStyledAttributes.getBoolean(3, typedArrayObtainStyledAttributes.getBoolean(2, false));
        typedArrayObtainStyledAttributes.recycle();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void D(View view) {
        boolean z = view instanceof CompoundButton;
        if (z) {
            ((CompoundButton) view).setOnCheckedChangeListener(null);
        }
        if (view instanceof Checkable) {
            ((Checkable) view).setChecked(this.N);
        }
        if (z) {
            ((CompoundButton) view).setOnCheckedChangeListener(this.S);
        }
    }

    @Override // androidx.preference.Preference
    public final void m(C0500pk c0500pk) {
        super.m(c0500pk);
        D(c0500pk.q(android.R.id.checkbox));
        C(c0500pk.q(android.R.id.summary));
    }

    @Override // androidx.preference.Preference
    public final void u(View view) {
        super.u(view);
        if (((AccessibilityManager) this.a.getSystemService("accessibility")).isEnabled()) {
            D(view.findViewById(android.R.id.checkbox));
            C(view.findViewById(android.R.id.summary));
        }
    }
}
