package rikka.shizuku;

import android.os.Trace;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
public final class Pa implements Runnable {
    @Override // java.lang.Runnable
    public final void run() {
        try {
            Method method = AbstractC0537qq.b;
            Trace.beginSection("EmojiCompat.EmojiCompatInitializer.run");
            if (Ma.j != null) {
                Ma.a().c();
            }
        } finally {
            Method method2 = AbstractC0537qq.b;
            Trace.endSection();
        }
    }
}
