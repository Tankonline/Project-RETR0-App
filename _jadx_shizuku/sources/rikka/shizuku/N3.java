package rikka.shizuku;

import android.graphics.Typeface;
import moe.shizuku.manager.shell.Shell;
import rikka.html.text.style.BulletSpan;

/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class N3 implements Runnable {
    public final /* synthetic */ int a;
    public final /* synthetic */ Object b;
    public final /* synthetic */ Object c;

    public /* synthetic */ N3(Object obj, int i, Object obj2) {
        this.a = i;
        this.b = obj;
        this.c = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.a) {
            case 0:
                Runnable runnable = (Runnable) this.c;
                O3 o3 = (O3) this.b;
                o3.getClass();
                try {
                    runnable.run();
                    return;
                } finally {
                    o3.a();
                }
            case 1:
                Rs rs = (Rs) this.b;
                String str = (String) this.c;
                Us us = rs.b;
                StringBuilder sb = us.b;
                sb.append(str);
                sb.append('\n');
                us.d(null);
                return;
            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                ((Sj) this.b).h.a((C0516q5) this.c);
                return;
            case 3:
                ((X8) this.b).L((Typeface) this.c);
                return;
            default:
                ((Shell) this.b).a((String[]) this.c, true);
                return;
        }
    }
}
