package rikka.shizuku;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.SeekBar;
import androidx.appcompat.widget.SearchView;
import androidx.preference.SeekBarPreference;

/* JADX INFO: loaded from: classes.dex */
public final class Km implements View.OnKeyListener {
    public final /* synthetic */ int a;
    public final /* synthetic */ Object b;

    public /* synthetic */ Km(int i, Object obj) {
        this.a = i;
        this.b = obj;
    }

    @Override // android.view.View.OnKeyListener
    public final boolean onKey(View view, int i, KeyEvent keyEvent) {
        switch (this.a) {
            case 0:
                ((SearchView) this.b).getClass();
                return false;
            default:
                if (keyEvent.getAction() != 0) {
                    return false;
                }
                SeekBarPreference seekBarPreference = (SeekBarPreference) this.b;
                if ((!seekBarPreference.U && (i == 21 || i == 22)) || i == 23 || i == 66) {
                    return false;
                }
                SeekBar seekBar = seekBarPreference.S;
                if (seekBar != null) {
                    return seekBar.onKeyDown(i, keyEvent);
                }
                Log.e("SeekBarPreference", "SeekBar view is null and hence cannot be adjusted.");
                return false;
        }
    }
}
