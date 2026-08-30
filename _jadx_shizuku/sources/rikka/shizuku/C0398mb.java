package rikka.shizuku;

import android.widget.EditText;
import com.google.android.material.textfield.TextInputLayout;

/* JADX INFO: renamed from: rikka.shizuku.mb, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0398mb {
    public final /* synthetic */ C0460ob a;

    public C0398mb(C0460ob c0460ob) {
        this.a = c0460ob;
    }

    public final void a(TextInputLayout textInputLayout) {
        C0460ob c0460ob = this.a;
        EditText editText = c0460ob.p;
        if (editText == textInputLayout.d) {
            return;
        }
        C0367lb c0367lb = c0460ob.s;
        if (editText != null) {
            editText.removeTextChangedListener(c0367lb);
            if (c0460ob.p.getOnFocusChangeListener() == c0460ob.b().e()) {
                c0460ob.p.setOnFocusChangeListener(null);
            }
        }
        EditText editText2 = textInputLayout.d;
        c0460ob.p = editText2;
        if (editText2 != null) {
            editText2.addTextChangedListener(c0367lb);
        }
        c0460ob.b().m(c0460ob.p);
        c0460ob.i(c0460ob.b());
    }
}
