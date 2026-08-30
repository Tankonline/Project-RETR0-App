package rikka.shizuku;

import android.view.View;
import android.widget.CheckedTextView;
import androidx.recyclerview.widget.RecyclerView;
import moe.shizuku.manager.settings.IntegerSimpleMenuPreference;
import rikka.preference.SimpleMenuPreference;

/* JADX INFO: renamed from: rikka.shizuku.fo, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class ViewOnClickListenerC0196fo extends AbstractC0470ol implements View.OnClickListener {
    public CheckedTextView u;
    public C0318jo v;

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        RecyclerView recyclerView;
        Yk yk;
        int iH;
        C0610t7 c0610t7 = this.v.k;
        if (c0610t7 != null) {
            int i = -1;
            if (this.s != null && (recyclerView = this.r) != null && (yk = recyclerView.m) != null && (iH = recyclerView.H(this)) != -1 && this.s == yk) {
                i = iH;
            }
            switch (c0610t7.a) {
                case 4:
                    IntegerSimpleMenuPreference integerSimpleMenuPreference = (IntegerSimpleMenuPreference) c0610t7.b;
                    int i2 = integerSimpleMenuPreference.R[i];
                    if (integerSimpleMenuPreference.a(Integer.valueOf(i2))) {
                        integerSimpleMenuPreference.B(i2);
                    }
                    break;
                default:
                    SimpleMenuPreference.E((SimpleMenuPreference) c0610t7.b, i);
                    break;
            }
        }
        if (this.v.isShowing()) {
            this.v.dismiss();
        }
    }
}
