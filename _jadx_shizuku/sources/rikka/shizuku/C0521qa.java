package rikka.shizuku;

import android.view.View;
import android.widget.AdapterView;
import androidx.appcompat.widget.SearchView;
import androidx.preference.DropDownPreference;

/* JADX INFO: renamed from: rikka.shizuku.qa, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0521qa implements AdapterView.OnItemSelectedListener {
    public final /* synthetic */ int a;
    public final /* synthetic */ Object b;

    public /* synthetic */ C0521qa(int i, Object obj) {
        this.a = i;
        this.b = obj;
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public final void onItemSelected(AdapterView adapterView, View view, int i, long j) {
        C0490pa c0490pa;
        switch (this.a) {
            case 0:
                if (i >= 0) {
                    DropDownPreference dropDownPreference = (DropDownPreference) this.b;
                    String string = dropDownPreference.U[i].toString();
                    if (string.equals(dropDownPreference.V) || !dropDownPreference.a(string)) {
                        return;
                    }
                    dropDownPreference.D(string);
                    return;
                }
                return;
            case 1:
                if (i == -1 || (c0490pa = ((Zg) this.b).c) == null) {
                    return;
                }
                c0490pa.h = false;
                return;
            default:
                ((SearchView) this.b).p.getText();
                throw null;
        }
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public final void onNothingSelected(AdapterView adapterView) {
        int i = this.a;
    }

    private final void a(AdapterView adapterView) {
    }

    private final void b(AdapterView adapterView) {
    }

    private final void c(AdapterView adapterView) {
    }
}
