package androidx.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Checkable;
import androidx.appcompat.widget.SwitchCompat;
import java.util.WeakHashMap;
import moe.shizuku.privileged.api.R;
import rikka.shizuku.C0500pk;
import rikka.shizuku.C0693vs;
import rikka.shizuku.Hn;
import rikka.shizuku.Hs;
import rikka.shizuku.Jk;
import rikka.shizuku.Q5;

/* JADX INFO: loaded from: classes.dex */
public class SwitchPreferenceCompat extends TwoStatePreference {
    public final Q5 S;
    public final String T;
    public final String U;

    public SwitchPreferenceCompat(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.S = new Q5(this, 2);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Jk.m, i, i2);
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
        boolean z = view instanceof SwitchCompat;
        if (z) {
            ((SwitchCompat) view).setOnCheckedChangeListener(null);
        }
        if (view instanceof Checkable) {
            ((Checkable) view).setChecked(this.N);
        }
        if (z) {
            SwitchCompat switchCompat = (SwitchCompat) view;
            switchCompat.g(this.T);
            switchCompat.requestLayout();
            if (switchCompat.isChecked() && Build.VERSION.SDK_INT >= 30) {
                Object string = switchCompat.o;
                if (string == null) {
                    string = switchCompat.getResources().getString(R.string._0_resource_name_obfuscated_res_0x7f110007);
                }
                WeakHashMap weakHashMap = Hs.a;
                new C0693vs(R.id._0_resource_name_obfuscated_res_0x7f0901c5, CharSequence.class, 64, 30, 2).d(switchCompat, string);
            }
            switchCompat.f(this.U);
            switchCompat.requestLayout();
            if (!switchCompat.isChecked() && Build.VERSION.SDK_INT >= 30) {
                Object string2 = switchCompat.q;
                if (string2 == null) {
                    string2 = switchCompat.getResources().getString(R.string._0_resource_name_obfuscated_res_0x7f110006);
                }
                WeakHashMap weakHashMap2 = Hs.a;
                new C0693vs(R.id._0_resource_name_obfuscated_res_0x7f0901c5, CharSequence.class, 64, 30, 2).d(switchCompat, string2);
            }
            switchCompat.setOnCheckedChangeListener(this.S);
        }
    }

    @Override // androidx.preference.Preference
    public final void m(C0500pk c0500pk) {
        super.m(c0500pk);
        D(c0500pk.q(R.id._0_resource_name_obfuscated_res_0x7f0901b7));
        C(c0500pk.q(android.R.id.summary));
    }

    @Override // androidx.preference.Preference
    public final void u(View view) {
        super.u(view);
        if (((AccessibilityManager) this.a.getSystemService("accessibility")).isEnabled()) {
            D(view.findViewById(R.id._0_resource_name_obfuscated_res_0x7f0901b7));
            C(view.findViewById(android.R.id.summary));
        }
    }

    public SwitchPreferenceCompat(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr._0_resource_name_obfuscated_res_0x7f040487, 0);
    }
}
