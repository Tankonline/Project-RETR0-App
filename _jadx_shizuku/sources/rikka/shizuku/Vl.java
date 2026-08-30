package rikka.shizuku;

import android.content.res.Resources;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public final class Vl {
    public final Resources a;
    public final Resources.Theme b;

    public Vl(Resources resources, Resources.Theme theme) {
        this.a = resources;
        this.b = theme;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && Vl.class == obj.getClass()) {
            Vl vl = (Vl) obj;
            if (this.a.equals(vl.a) && Objects.equals(this.b, vl.b)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hash(this.a, this.b);
    }
}
