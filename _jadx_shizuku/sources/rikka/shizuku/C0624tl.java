package rikka.shizuku;

/* JADX INFO: renamed from: rikka.shizuku.tl, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0624tl {
    public static String a(Cd cd) {
        String string = cd.getClass().getGenericInterfaces()[0].toString();
        return string.startsWith("kotlin.jvm.functions.") ? string.substring(21) : string;
    }
}
