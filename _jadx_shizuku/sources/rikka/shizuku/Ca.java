package rikka.shizuku;

import android.R;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import androidx.preference.EditTextPreference;

/* JADX INFO: loaded from: classes.dex */
public class Ca extends AbstractDialogInterfaceOnClickListenerC0223gk {
    public EditText u0;
    public CharSequence v0;
    public final RunnableC0607t4 w0 = new RunnableC0607t4(5, this);
    public long x0 = -1;

    @Override // rikka.shizuku.AbstractDialogInterfaceOnClickListenerC0223gk, rikka.shizuku.U9, rikka.shizuku.Ac
    public final void A(Bundle bundle) {
        super.A(bundle);
        bundle.putCharSequence("EditTextPreferenceDialogFragment.text", this.v0);
    }

    @Override // rikka.shizuku.AbstractDialogInterfaceOnClickListenerC0223gk
    public final void R(View view) {
        super.R(view);
        EditText editText = (EditText) view.findViewById(R.id.edit);
        this.u0 = editText;
        if (editText == null) {
            throw new IllegalStateException("Dialog view must contain an EditText with id @android:id/edit");
        }
        editText.requestFocus();
        this.u0.setText(this.v0);
        EditText editText2 = this.u0;
        editText2.setSelection(editText2.getText().length());
        ((EditTextPreference) Q()).getClass();
    }

    @Override // rikka.shizuku.AbstractDialogInterfaceOnClickListenerC0223gk
    public final void S(boolean z) {
        if (z) {
            String string = this.u0.getText().toString();
            EditTextPreference editTextPreference = (EditTextPreference) Q();
            if (editTextPreference.a(string)) {
                editTextPreference.B(string);
            }
        }
    }

    public final void U() {
        long j = this.x0;
        if (j == -1 || j + 1000 <= SystemClock.currentThreadTimeMillis()) {
            return;
        }
        EditText editText = this.u0;
        if (editText == null || !editText.isFocused()) {
            this.x0 = -1L;
            return;
        }
        if (((InputMethodManager) this.u0.getContext().getSystemService("input_method")).showSoftInput(this.u0, 0)) {
            this.x0 = -1L;
            return;
        }
        EditText editText2 = this.u0;
        RunnableC0607t4 runnableC0607t4 = this.w0;
        editText2.removeCallbacks(runnableC0607t4);
        this.u0.postDelayed(runnableC0607t4, 50L);
    }

    @Override // rikka.shizuku.AbstractDialogInterfaceOnClickListenerC0223gk, rikka.shizuku.U9, rikka.shizuku.Ac
    public final void v(Bundle bundle) {
        super.v(bundle);
        if (bundle == null) {
            this.v0 = ((EditTextPreference) Q()).T;
        } else {
            this.v0 = bundle.getCharSequence("EditTextPreferenceDialogFragment.text");
        }
    }
}
