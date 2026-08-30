package androidx.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import moe.shizuku.privileged.api.R;
import rikka.shizuku.AbstractC0284ik;
import rikka.shizuku.Ac;
import rikka.shizuku.C0097ch;
import rikka.shizuku.Ca;
import rikka.shizuku.Hn;
import rikka.shizuku.Jk;
import rikka.shizuku.Mi;
import rikka.shizuku.U9;

/* JADX INFO: loaded from: classes.dex */
public abstract class DialogPreference extends Preference {
    public final CharSequence N;
    public final String O;
    public final Drawable P;
    public final String Q;
    public final String R;
    public final int S;

    public DialogPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Jk.c, i, i2);
        String strE = Hn.e(typedArrayObtainStyledAttributes, 9, 0);
        this.N = strE;
        if (strE == null) {
            this.N = this.h;
        }
        String string = typedArrayObtainStyledAttributes.getString(8);
        this.O = string == null ? typedArrayObtainStyledAttributes.getString(1) : string;
        Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(6);
        this.P = drawable == null ? typedArrayObtainStyledAttributes.getDrawable(2) : drawable;
        String string2 = typedArrayObtainStyledAttributes.getString(11);
        this.Q = string2 == null ? typedArrayObtainStyledAttributes.getString(3) : string2;
        String string3 = typedArrayObtainStyledAttributes.getString(10);
        this.R = string3 == null ? typedArrayObtainStyledAttributes.getString(4) : string3;
        this.S = typedArrayObtainStyledAttributes.getResourceId(7, typedArrayObtainStyledAttributes.getResourceId(5, 0));
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // androidx.preference.Preference
    public void n() {
        U9 mi;
        AbstractC0284ik abstractC0284ik = this.b.j;
        if (abstractC0284ik != null) {
            for (Ac ac = abstractC0284ik; ac != null; ac = ac.w) {
            }
            if (abstractC0284ik.l().C("androidx.preference.PreferenceFragment.DIALOG") != null) {
                return;
            }
            if (this instanceof EditTextPreference) {
                mi = new Ca();
                Bundle bundle = new Bundle(1);
                bundle.putString("key", this.l);
                mi.K(bundle);
            } else if (this instanceof ListPreference) {
                mi = new C0097ch();
                Bundle bundle2 = new Bundle(1);
                bundle2.putString("key", this.l);
                mi.K(bundle2);
            } else {
                if (!(this instanceof MultiSelectListPreference)) {
                    throw new IllegalArgumentException("Cannot display dialog for an unknown Preference type: " + getClass().getSimpleName() + ". Make sure to implement onPreferenceDisplayDialog() to handle displaying a custom dialog for this Preference.");
                }
                mi = new Mi();
                Bundle bundle3 = new Bundle(1);
                bundle3.putString("key", this.l);
                mi.K(bundle3);
            }
            mi.L(abstractC0284ik);
            mi.P(abstractC0284ik.l(), "androidx.preference.PreferenceFragment.DIALOG");
        }
    }

    public DialogPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, Hn.c(context, R.attr._0_resource_name_obfuscated_res_0x7f040181, android.R.attr.dialogPreferenceStyle), 0);
    }
}
