package rikka.shizuku;

import android.os.Handler;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public final class Ln {
    public final Object a;
    public final Handler b;

    public Ln(Object obj, Handler handler) {
        this.a = obj;
        this.b = handler;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && Ln.class == obj.getClass()) {
            Ln ln = (Ln) obj;
            if (Objects.equals(this.a, ln.a) && Objects.equals(this.b, ln.b)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hash(this.a, this.b);
    }
}
