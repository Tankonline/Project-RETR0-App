package rikka.shizuku;

/* JADX INFO: renamed from: rikka.shizuku.a5, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0023a5 {
    public static final byte[] e = new byte[1792];
    public final String a;
    public final int b;
    public int c;
    public char d;

    static {
        for (int i = 0; i < 1792; i++) {
            e[i] = Character.getDirectionality(i);
        }
    }

    public C0023a5(String str) {
        this.a = str;
        this.b = str.length();
    }

    public final byte a() {
        int i = this.c - 1;
        String str = this.a;
        char cCharAt = str.charAt(i);
        this.d = cCharAt;
        if (Character.isLowSurrogate(cCharAt)) {
            int iCodePointBefore = Character.codePointBefore(str, this.c);
            this.c -= Character.charCount(iCodePointBefore);
            return Character.getDirectionality(iCodePointBefore);
        }
        this.c--;
        char c = this.d;
        return c < 1792 ? e[c] : Character.getDirectionality(c);
    }
}
