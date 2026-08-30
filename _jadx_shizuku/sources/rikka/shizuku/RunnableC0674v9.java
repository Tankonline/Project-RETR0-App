package rikka.shizuku;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import java.util.concurrent.ThreadPoolExecutor;

/* JADX INFO: renamed from: rikka.shizuku.v9, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class RunnableC0674v9 implements Runnable {
    public final /* synthetic */ int a;
    public final /* synthetic */ Object b;
    public final /* synthetic */ Object c;
    public final /* synthetic */ Object d;

    public /* synthetic */ RunnableC0674v9(Object obj, Object obj2, Object obj3, int i) {
        this.a = i;
        this.b = obj;
        this.c = obj2;
        this.d = obj3;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.a) {
            case 0:
                ((ViewGroup) this.b).endViewTransition((View) this.c);
                C0736x9 c0736x9 = (C0736x9) this.d;
                ((C0751xo) c0736x9.c.b).c(c0736x9);
                return;
            default:
                Ys ys = (Ys) this.b;
                AbstractC0599sr abstractC0599sr = (AbstractC0599sr) this.c;
                ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) this.d;
                ys.getClass();
                try {
                    C0337kc c0337kcR = X8.r((Context) ys.b);
                    if (c0337kcR == null) {
                        throw new RuntimeException("EmojiCompat font provider not available on this device.");
                    }
                    C0306jc c0306jc = (C0306jc) ((La) c0337kcR.b);
                    synchronized (c0306jc.d) {
                        c0306jc.f = threadPoolExecutor;
                        break;
                    }
                    ((La) c0337kcR.b).k(new Oa(abstractC0599sr, threadPoolExecutor));
                    return;
                } catch (Throwable th) {
                    abstractC0599sr.B(th);
                    threadPoolExecutor.shutdown();
                    return;
                }
        }
    }
}
