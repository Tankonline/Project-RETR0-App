package rikka.shizuku;

import android.view.ContentInfo;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public abstract class Fs {
    public static String[] a(View view) {
        return view.getReceiveContentMimeTypes();
    }

    public static B7 b(View view, B7 b7) {
        ContentInfo contentInfoG = b7.a.g();
        ContentInfo contentInfoPerformReceiveContent = view.performReceiveContent(contentInfoG);
        if (contentInfoPerformReceiveContent == null) {
            return null;
        }
        return contentInfoPerformReceiveContent == contentInfoG ? b7 : new B7(new Ys(contentInfoPerformReceiveContent));
    }
}
