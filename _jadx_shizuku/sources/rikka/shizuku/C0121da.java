package rikka.shizuku;

import android.view.DisplayCutout;
import java.util.Objects;

/* JADX INFO: renamed from: rikka.shizuku.da, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0121da {
    public final DisplayCutout a;

    public C0121da(DisplayCutout displayCutout) {
        this.a = displayCutout;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || C0121da.class != obj.getClass()) {
            return false;
        }
        return Objects.equals(this.a, ((C0121da) obj).a);
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    public final String toString() {
        return "DisplayCutoutCompat{" + this.a + "}";
    }
}
