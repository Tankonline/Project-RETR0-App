package rikka.shizuku;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import androidx.appcompat.widget.SearchView;
import com.google.android.material.textfield.TextInputLayout;

/* JADX INFO: renamed from: rikka.shizuku.p1, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0481p1 implements TextWatcher {
    public final /* synthetic */ int a;
    public final /* synthetic */ Object b;

    public /* synthetic */ C0481p1(int i, Object obj) {
        this.a = i;
        this.b = obj;
    }

    @Override // android.text.TextWatcher
    public final void afterTextChanged(Editable editable) {
        switch (this.a) {
            case 0:
                C0235h1 c0235h1 = ((C0573s1) this.b).m0;
                if (c0235h1 == null) {
                    c0235h1 = null;
                }
                ((TextInputLayout) c0235h1.b).l(null);
                break;
        }
    }

    @Override // android.text.TextWatcher
    public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        int i4 = this.a;
    }

    @Override // android.text.TextWatcher
    public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        switch (this.a) {
            case 0:
                break;
            default:
                SearchView searchView = (SearchView) this.b;
                TextUtils.isEmpty(searchView.p.getText());
                searchView.u.setVisibility(8);
                searchView.w.setVisibility(8);
                searchView.n();
                searchView.s.setVisibility(8);
                charSequence.toString();
                break;
        }
    }

    private final void a(Editable editable) {
    }

    private final void b(int i, int i2, int i3, CharSequence charSequence) {
    }

    private final void c(int i, int i2, int i3, CharSequence charSequence) {
    }

    private final void d(int i, int i2, int i3, CharSequence charSequence) {
    }
}
