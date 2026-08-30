package rikka.shizuku;

import java.util.concurrent.ThreadPoolExecutor;

/* JADX INFO: loaded from: classes.dex */
public final class Oa extends AbstractC0599sr {
    public final /* synthetic */ AbstractC0599sr I;
    public final /* synthetic */ ThreadPoolExecutor J;

    public Oa(AbstractC0599sr abstractC0599sr, ThreadPoolExecutor threadPoolExecutor) {
        this.I = abstractC0599sr;
        this.J = threadPoolExecutor;
    }

    @Override // rikka.shizuku.AbstractC0599sr
    public final void B(Throwable th) {
        ThreadPoolExecutor threadPoolExecutor = this.J;
        try {
            this.I.B(th);
        } finally {
            threadPoolExecutor.shutdown();
        }
    }

    @Override // rikka.shizuku.AbstractC0599sr
    public final void C(Xn xn) {
        ThreadPoolExecutor threadPoolExecutor = this.J;
        try {
            this.I.C(xn);
        } finally {
            threadPoolExecutor.shutdown();
        }
    }
}
