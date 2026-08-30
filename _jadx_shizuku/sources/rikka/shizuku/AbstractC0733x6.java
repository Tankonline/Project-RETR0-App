package rikka.shizuku;

import java.util.Collection;

/* JADX INFO: renamed from: rikka.shizuku.x6, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0733x6 extends AbstractC0702w6 {
    public static int b0(Iterable iterable) {
        if (iterable instanceof Collection) {
            return ((Collection) iterable).size();
        }
        return 10;
    }
}
