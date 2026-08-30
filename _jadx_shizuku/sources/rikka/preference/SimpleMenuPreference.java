package rikka.preference;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.View;
import androidx.preference.ListPreference;
import rikka.shizuku.C0318jo;
import rikka.shizuku.C0500pk;
import rikka.shizuku.C0610t7;
import rikka.shizuku.Ck;

/* JADX INFO: loaded from: classes.dex */
public class SimpleMenuPreference extends ListPreference {
    public View Y;
    public View Z;
    public final C0318jo a0;

    public SimpleMenuPreference(Context context) {
        this(context, null);
    }

    public static void E(SimpleMenuPreference simpleMenuPreference, int i) {
        String string = simpleMenuPreference.U[i].toString();
        if (simpleMenuPreference.a(string)) {
            super.D(string);
        }
    }

    @Override // androidx.preference.ListPreference
    public final void C(CharSequence[] charSequenceArr) {
        this.T = charSequenceArr;
        this.a0.i = true;
    }

    @Override // androidx.preference.Preference
    public final void m(C0500pk c0500pk) {
        super.m(c0500pk);
        View view = c0500pk.a;
        this.Z = view;
        View viewFindViewById = view.findViewById(R.id.empty);
        this.Y = viewFindViewById;
        if (viewFindViewById == null) {
            throw new IllegalStateException("SimpleMenuPreference item layout must containa view id is android.R.id.empty to support iconSpaceReserved");
        }
    }

    @Override // androidx.preference.DialogPreference, androidx.preference.Preference
    public final void n() {
        C0318jo c0318jo;
        CharSequence[] charSequenceArr = this.T;
        if (charSequenceArr == null || charSequenceArr.length == 0 || (c0318jo = this.a0) == null) {
            return;
        }
        c0318jo.l = charSequenceArr;
        c0318jo.m = B(this.V);
        c0318jo.e(this.Z, (View) this.Z.getParent(), (int) this.Y.getX());
    }

    public SimpleMenuPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f040440);
    }

    public SimpleMenuPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, moe.shizuku.privileged.api.R.style._0_resource_name_obfuscated_res_0x7f120180);
    }

    public SimpleMenuPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Ck.c, i, i2);
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(0, moe.shizuku.privileged.api.R.style._0_resource_name_obfuscated_res_0x7f1204ce);
        int resourceId2 = typedArrayObtainStyledAttributes.getResourceId(1, moe.shizuku.privileged.api.R.style._0_resource_name_obfuscated_res_0x7f120342);
        C0318jo c0318jo = new C0318jo(resourceId2 != 0 ? new ContextThemeWrapper(context, resourceId2) : context, attributeSet, resourceId);
        this.a0 = c0318jo;
        c0318jo.k = new C0610t7(8, this);
        typedArrayObtainStyledAttributes.recycle();
    }
}
