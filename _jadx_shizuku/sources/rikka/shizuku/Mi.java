package rikka.shizuku;

import android.os.Bundle;
import androidx.preference.MultiSelectListPreference;
import java.util.ArrayList;
import java.util.HashSet;

/* JADX INFO: loaded from: classes.dex */
public class Mi extends AbstractDialogInterfaceOnClickListenerC0223gk {
    public final HashSet u0 = new HashSet();
    public boolean v0;
    public CharSequence[] w0;
    public CharSequence[] x0;

    @Override // rikka.shizuku.AbstractDialogInterfaceOnClickListenerC0223gk, rikka.shizuku.U9, rikka.shizuku.Ac
    public final void A(Bundle bundle) {
        super.A(bundle);
        bundle.putStringArrayList("MultiSelectListPreferenceDialogFragmentCompat.values", new ArrayList<>(this.u0));
        bundle.putBoolean("MultiSelectListPreferenceDialogFragmentCompat.changed", this.v0);
        bundle.putCharSequenceArray("MultiSelectListPreferenceDialogFragmentCompat.entries", this.w0);
        bundle.putCharSequenceArray("MultiSelectListPreferenceDialogFragmentCompat.entryValues", this.x0);
    }

    @Override // rikka.shizuku.AbstractDialogInterfaceOnClickListenerC0223gk
    public final void S(boolean z) {
        if (z && this.v0) {
            MultiSelectListPreference multiSelectListPreference = (MultiSelectListPreference) Q();
            HashSet hashSet = this.u0;
            if (multiSelectListPreference.a(hashSet)) {
                multiSelectListPreference.B(hashSet);
            }
        }
        this.v0 = false;
    }

    @Override // rikka.shizuku.AbstractDialogInterfaceOnClickListenerC0223gk
    public final void T(I1 i1) {
        int length = this.x0.length;
        boolean[] zArr = new boolean[length];
        for (int i = 0; i < length; i++) {
            zArr[i] = this.u0.contains(this.x0[i].toString());
        }
        CharSequence[] charSequenceArr = this.w0;
        Li li = new Li(this);
        E1 e1 = (E1) i1.b;
        e1.p = charSequenceArr;
        e1.x = li;
        e1.t = zArr;
        e1.u = true;
    }

    @Override // rikka.shizuku.AbstractDialogInterfaceOnClickListenerC0223gk, rikka.shizuku.U9, rikka.shizuku.Ac
    public final void v(Bundle bundle) {
        CharSequence[] charSequenceArr;
        super.v(bundle);
        HashSet hashSet = this.u0;
        if (bundle != null) {
            hashSet.clear();
            hashSet.addAll(bundle.getStringArrayList("MultiSelectListPreferenceDialogFragmentCompat.values"));
            this.v0 = bundle.getBoolean("MultiSelectListPreferenceDialogFragmentCompat.changed", false);
            this.w0 = bundle.getCharSequenceArray("MultiSelectListPreferenceDialogFragmentCompat.entries");
            this.x0 = bundle.getCharSequenceArray("MultiSelectListPreferenceDialogFragmentCompat.entryValues");
            return;
        }
        MultiSelectListPreference multiSelectListPreference = (MultiSelectListPreference) Q();
        if (multiSelectListPreference.T == null || (charSequenceArr = multiSelectListPreference.U) == null) {
            throw new IllegalStateException("MultiSelectListPreference requires an entries array and an entryValues array.");
        }
        hashSet.clear();
        hashSet.addAll(multiSelectListPreference.V);
        this.v0 = false;
        this.w0 = multiSelectListPreference.T;
        this.x0 = charSequenceArr;
    }
}
