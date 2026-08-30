package rikka.shizuku;

/* JADX INFO: renamed from: rikka.shizuku.uc, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0646uc {
    public static final C0646uc d = new C0646uc("", "", false);
    public final String a;
    public final String b;
    public final boolean c;

    static {
        new C0646uc("\n", "  ", true);
    }

    public C0646uc(String str, String str2, boolean z) {
        if (!str.matches("[\r\n]*")) {
            throw new IllegalArgumentException("Only combinations of \\n and \\r are allowed in newline.");
        }
        if (!str2.matches("[ \t]*")) {
            throw new IllegalArgumentException("Only combinations of spaces and tabs are allowed in indent.");
        }
        this.a = str;
        this.b = str2;
        this.c = z;
    }
}
