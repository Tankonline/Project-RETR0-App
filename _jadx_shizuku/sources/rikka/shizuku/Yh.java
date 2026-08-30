package rikka.shizuku;

import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/* JADX INFO: loaded from: classes.dex */
public final class Yh<S> extends Tj {
    public int X;
    public C0608t5 Y;

    @Override // rikka.shizuku.Ac
    public final void A(Bundle bundle) {
        bundle.putInt("THEME_RES_ID_KEY", this.X);
        bundle.putParcelable("DATE_SELECTOR_KEY", null);
        bundle.putParcelable("CALENDAR_CONSTRAINTS_KEY", this.Y);
    }

    @Override // rikka.shizuku.Ac
    public final void v(Bundle bundle) {
        super.v(bundle);
        if (bundle == null) {
            bundle = this.f;
        }
        this.X = bundle.getInt("THEME_RES_ID_KEY");
        if (bundle.getParcelable("DATE_SELECTOR_KEY") != null) {
            throw new ClassCastException();
        }
        this.Y = (C0608t5) bundle.getParcelable("CALENDAR_CONSTRAINTS_KEY");
    }

    @Override // rikka.shizuku.Ac
    public final View w(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layoutInflater.cloneInContext(new ContextThemeWrapper(i(), this.X));
        throw null;
    }
}
