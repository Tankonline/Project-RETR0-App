package rikka.shizuku;

import android.text.TextUtils;
import androidx.preference.Preference;

/* JADX INFO: renamed from: rikka.shizuku.kk, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0345kk {
    public final int a;
    public final int b;
    public final String c;

    public C0345kk(Preference preference) {
        this.c = preference.getClass().getName();
        this.a = preference.E;
        this.b = preference.F;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof C0345kk)) {
            return false;
        }
        C0345kk c0345kk = (C0345kk) obj;
        return this.a == c0345kk.a && this.b == c0345kk.b && TextUtils.equals(this.c, c0345kk.c);
    }

    public final int hashCode() {
        return this.c.hashCode() + ((((527 + this.a) * 31) + this.b) * 31);
    }
}
