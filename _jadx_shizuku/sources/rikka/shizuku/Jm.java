package rikka.shizuku;

import android.view.View;
import androidx.appcompat.widget.SearchView;

/* JADX INFO: loaded from: classes.dex */
public final class Jm implements View.OnFocusChangeListener {
    public final /* synthetic */ SearchView a;

    public Jm(SearchView searchView) {
        this.a = searchView;
    }

    @Override // android.view.View.OnFocusChangeListener
    public final void onFocusChange(View view, boolean z) {
        this.a.getClass();
    }
}
