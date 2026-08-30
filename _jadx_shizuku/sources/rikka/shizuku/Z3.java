package rikka.shizuku;

import android.os.Handler;
import android.os.HandlerThread;
import java.util.Locale;
import moe.shizuku.manager.MainActivity;
import rikka.html.text.style.BulletSpan;

/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class Z3 implements InterfaceC0124dd {
    public final /* synthetic */ int a;

    public /* synthetic */ Z3(int i) {
        this.a = i;
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.Object, rikka.shizuku.jg] */
    @Override // rikka.shizuku.InterfaceC0124dd
    public final Object a() {
        switch (this.a) {
            case 0:
                return new Y3();
            case 1:
                HandlerThread handlerThread = new HandlerThread("Worker");
                handlerThread.start();
                return handlerThread;
            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                return new Handler(((HandlerThread) AbstractC0125de.a.getValue()).getLooper());
            case 3:
                int i = MainActivity.I;
                return new C0803ze();
            default:
                C0281ih c0281ih = new C0281ih();
                c0281ih.a = Locale.getDefault();
                return c0281ih;
        }
    }

    public /* synthetic */ Z3(AbstractActivityC0113d2 abstractActivityC0113d2) {
        this.a = 0;
    }
}
