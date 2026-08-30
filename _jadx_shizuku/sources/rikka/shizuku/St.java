package rikka.shizuku;

import android.view.DisplayCutout;
import android.view.WindowInsets;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class St extends Rt {
    public St(au auVar, WindowInsets windowInsets) {
        super(auVar, windowInsets);
    }

    @Override // rikka.shizuku.Wt
    public au a() {
        return au.e(null, this.c.consumeDisplayCutout());
    }

    @Override // rikka.shizuku.Wt
    public C0121da e() {
        DisplayCutout displayCutout = this.c.getDisplayCutout();
        if (displayCutout == null) {
            return null;
        }
        return new C0121da(displayCutout);
    }

    @Override // rikka.shizuku.Qt, rikka.shizuku.Wt
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof St)) {
            return false;
        }
        St st = (St) obj;
        return Objects.equals(this.c, st.c) && Objects.equals(this.g, st.g) && Qt.y(this.h, st.h);
    }

    @Override // rikka.shizuku.Wt
    public int hashCode() {
        return this.c.hashCode();
    }
}
