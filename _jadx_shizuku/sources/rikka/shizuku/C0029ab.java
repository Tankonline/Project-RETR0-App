package rikka.shizuku;

import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.widget.EditText;

/* JADX INFO: renamed from: rikka.shizuku.ab, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0029ab implements TextWatcher {
    public final EditText a;
    public Za b;
    public boolean c = true;

    public C0029ab(EditText editText) {
        this.a = editText;
    }

    public static void a(EditText editText, int i) {
        int length;
        if (i == 1 && editText != null && editText.isAttachedToWindow()) {
            Editable editableText = editText.getEditableText();
            int selectionStart = Selection.getSelectionStart(editableText);
            int selectionEnd = Selection.getSelectionEnd(editableText);
            Ma maA = Ma.a();
            if (editableText == null) {
                length = 0;
            } else {
                maA.getClass();
                length = editableText.length();
            }
            maA.e(editableText, 0, length);
            if (selectionStart >= 0 && selectionEnd >= 0) {
                Selection.setSelection(editableText, selectionStart, selectionEnd);
            } else if (selectionStart >= 0) {
                Selection.setSelection(editableText, selectionStart);
            } else if (selectionEnd >= 0) {
                Selection.setSelection(editableText, selectionEnd);
            }
        }
    }

    @Override // android.text.TextWatcher
    public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        EditText editText = this.a;
        if (editText.isInEditMode() || !this.c || Ma.j == null || i2 > i3 || !(charSequence instanceof Spannable)) {
            return;
        }
        int iB = Ma.a().b();
        if (iB != 0) {
            if (iB == 1) {
                Ma.a().e((Spannable) charSequence, i, i3 + i);
                return;
            } else if (iB != 3) {
                return;
            }
        }
        Ma maA = Ma.a();
        if (this.b == null) {
            this.b = new Za(editText);
        }
        maA.f(this.b);
    }

    @Override // android.text.TextWatcher
    public final void afterTextChanged(Editable editable) {
    }

    @Override // android.text.TextWatcher
    public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }
}
