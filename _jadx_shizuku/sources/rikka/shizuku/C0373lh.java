package rikka.shizuku;

import android.os.LocaleList;

/* JADX INFO: renamed from: rikka.shizuku.lh, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0373lh {
    public final LocaleList a;

    public C0373lh(LocaleList localeList) {
        this.a = localeList;
    }

    public final boolean equals(Object obj) {
        return this.a.equals(((C0373lh) obj).a);
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    public final String toString() {
        return this.a.toString();
    }
}
