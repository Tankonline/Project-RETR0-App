package rikka.shizuku;

/* JADX INFO: renamed from: rikka.shizuku.dp, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0135dp extends AbstractC0105cp {
    public static final int r(CharSequence charSequence) {
        return charSequence.length() - 1;
    }

    public static final boolean s(CharSequence charSequence, int i, String str, int i2, int i3) {
        if (i2 < 0 || i < 0 || i > charSequence.length() - i3 || i2 > str.length() - i3) {
            return false;
        }
        for (int i4 = 0; i4 < i3; i4++) {
            if (charSequence.charAt(i + i4) != str.charAt(i2 + i4)) {
                return false;
            }
        }
        return true;
    }
}
