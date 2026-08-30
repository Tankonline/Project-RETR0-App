package rikka.shizuku;

import android.os.Bundle;
import androidx.preference.ListPreference;

/* JADX INFO: renamed from: rikka.shizuku.ch, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0097ch extends AbstractDialogInterfaceOnClickListenerC0223gk {
    public int u0;
    public CharSequence[] v0;
    public CharSequence[] w0;

    @Override // rikka.shizuku.AbstractDialogInterfaceOnClickListenerC0223gk, rikka.shizuku.U9, rikka.shizuku.Ac
    public final void A(Bundle bundle) {
        super.A(bundle);
        bundle.putInt("ListPreferenceDialogFragment.index", this.u0);
        bundle.putCharSequenceArray("ListPreferenceDialogFragment.entries", this.v0);
        bundle.putCharSequenceArray("ListPreferenceDialogFragment.entryValues", this.w0);
    }

    @Override // rikka.shizuku.AbstractDialogInterfaceOnClickListenerC0223gk
    public final void S(boolean z) {
        int i;
        if (!z || (i = this.u0) < 0) {
            return;
        }
        String string = this.w0[i].toString();
        ListPreference listPreference = (ListPreference) Q();
        if (listPreference.a(string)) {
            listPreference.D(string);
        }
    }

    @Override // rikka.shizuku.AbstractDialogInterfaceOnClickListenerC0223gk
    public final void T(I1 i1) {
        CharSequence[] charSequenceArr = this.v0;
        int i = this.u0;
        DialogInterfaceOnClickListenerC0066bh dialogInterfaceOnClickListenerC0066bh = new DialogInterfaceOnClickListenerC0066bh(this);
        E1 e1 = (E1) i1.b;
        e1.p = charSequenceArr;
        e1.r = dialogInterfaceOnClickListenerC0066bh;
        e1.w = i;
        e1.v = true;
        i1.e(null, null);
    }

    @Override // rikka.shizuku.AbstractDialogInterfaceOnClickListenerC0223gk, rikka.shizuku.U9, rikka.shizuku.Ac
    public final void v(Bundle bundle) {
        super.v(bundle);
        if (bundle != null) {
            this.u0 = bundle.getInt("ListPreferenceDialogFragment.index", 0);
            this.v0 = bundle.getCharSequenceArray("ListPreferenceDialogFragment.entries");
            this.w0 = bundle.getCharSequenceArray("ListPreferenceDialogFragment.entryValues");
            return;
        }
        ListPreference listPreference = (ListPreference) Q();
        if (listPreference.T == null || listPreference.U == null) {
            throw new IllegalStateException("ListPreference requires an entries array and an entryValues array.");
        }
        this.u0 = listPreference.B(listPreference.V);
        this.v0 = listPreference.T;
        this.w0 = listPreference.U;
    }
}
