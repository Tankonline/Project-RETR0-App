package rikka.shizuku;

import android.text.Editable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.TextView;
import androidx.appcompat.widget.SearchView;

/* JADX INFO: loaded from: classes.dex */
public final class Lm implements TextView.OnEditorActionListener {
    public final /* synthetic */ SearchView a;

    public Lm(SearchView searchView) {
        this.a = searchView;
    }

    @Override // android.widget.TextView.OnEditorActionListener
    public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        SearchView.SearchAutoComplete searchAutoComplete = this.a.p;
        Editable text = searchAutoComplete.getText();
        if (text == null || TextUtils.getTrimmedLength(text) <= 0) {
            return true;
        }
        searchAutoComplete.a(false);
        searchAutoComplete.dismissDropDown();
        return true;
    }
}
