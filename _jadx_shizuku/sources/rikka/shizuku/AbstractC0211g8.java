package rikka.shizuku;

import java.util.LinkedHashMap;

/* JADX INFO: renamed from: rikka.shizuku.g8, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0211g8 {
    public final LinkedHashMap a = new LinkedHashMap();

    public final boolean equals(Object obj) {
        return (obj instanceof AbstractC0211g8) && AbstractC0046as.c(this.a, ((AbstractC0211g8) obj).a);
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    public final String toString() {
        return "CreationExtras(extras=" + this.a + ')';
    }
}
