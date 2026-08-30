package rikka.shizuku;

import java.nio.charset.Charset;

/* JADX INFO: loaded from: classes.dex */
public abstract class P5 {
    public static final Charset a = Charset.forName("UTF-8");

    static {
        Charset.forName("UTF-16");
        Charset.forName("UTF-16BE");
        Charset.forName("UTF-16LE");
        Charset.forName("US-ASCII");
        Charset.forName("ISO-8859-1");
    }
}
