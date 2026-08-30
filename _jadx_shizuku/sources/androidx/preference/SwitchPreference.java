package androidx.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Checkable;
import android.widget.Switch;
import moe.shizuku.privileged.api.R;
import rikka.shizuku.C0500pk;
import rikka.shizuku.Hn;
import rikka.shizuku.Jk;
import rikka.shizuku.Q5;

/* JADX INFO: loaded from: classes.dex */
public class SwitchPreference extends TwoStatePreference {
    public final Q5 S;
    public final String T;
    public final String U;

    /* JADX WARN: Illegal instructions before constructor call */
    public SwitchPreference(Context context, AttributeSet attributeSet) {
        int iC = Hn.c(context, R.attr._0_resource_name_obfuscated_res_0x7f040488, android.R.attr.switchPreferenceStyle);
        super(context, attributeSet, iC, 0);
        this.S = new Q5(this, 1);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Jk.l, iC, 0);
        this.O = Hn.e(typedArrayObtainStyledAttributes, 7, 0);
        if (this.N) {
            i();
        }
        String string = typedArrayObtainStyledAttributes.getString(6);
        this.P = string == null ? typedArrayObtainStyledAttributes.getString(1) : string;
        if (!this.N) {
            i();
        }
        String string2 = typedArrayObtainStyledAttributes.getString(9);
        this.T = string2 == null ? typedArrayObtainStyledAttributes.getString(3) : string2;
        i();
        String string3 = typedArrayObtainStyledAttributes.getString(8);
        this.U = string3 == null ? typedArrayObtainStyledAttributes.getString(4) : string3;
        i();
        this.R = typedArrayObtainStyledAttributes.getBoolean(5, typedArrayObtainStyledAttributes.getBoolean(2, false));
        typedArrayObtainStyledAttributes.recycle();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void D(View view) {
        boolean z = view instanceof Switch;
        if (z) {
            ((Switch) view).setOnCheckedChangeListener(null);
        }
        if (view instanceof Checkable) {
            ((Checkable) view).setChecked(this.N);
        }
        if (z) {
            Switch r4 = (Switch) view;
            r4.setTextOn(this.T);
            r4.setTextOff(this.U);
            r4.setOnCheckedChangeListener(this.S);
        }
    }

    @Override // androidx.preference.Preference
    public final void m(C0500pk c0500pk) {
        super.m(c0500pk);
        D(c0500pk.q(android.R.id.switch_widget));
        C(c0500pk.q(android.R.id.summary));
    }

    @Override // androidx.preference.Preference
    public final void u(View view) {
        super.u(view);
        if (((AccessibilityManager) this.a.getSystemService("accessibility")).isEnabled()) {
            D(view.findViewById(android.R.id.switch_widget));
            C(view.findViewById(android.R.id.summary));
        }
    }
}
