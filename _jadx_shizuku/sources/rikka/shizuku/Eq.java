package rikka.shizuku;

import android.view.View;
import java.util.ArrayList;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public final class Eq {
    public final View b;
    public final HashMap a = new HashMap();
    public final ArrayList c = new ArrayList();

    public Eq(View view) {
        this.b = view;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof Eq)) {
            return false;
        }
        Eq eq = (Eq) obj;
        return this.b == eq.b && this.a.equals(eq.a);
    }

    public final int hashCode() {
        return this.a.hashCode() + (this.b.hashCode() * 31);
    }

    public final String toString() {
        String strC = AbstractC0535qo.c(("TransitionValues@" + Integer.toHexString(hashCode()) + ":\n") + "    view = " + this.b + "\n", "    values:");
        HashMap map = this.a;
        for (String str : map.keySet()) {
            strC = strC + "    " + str + ": " + map.get(str) + "\n";
        }
        return strC;
    }
}
