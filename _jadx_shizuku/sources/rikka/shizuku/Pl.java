package rikka.shizuku;

import android.view.View;
import android.view.Window;
import java.util.concurrent.CountDownLatch;
import moe.shizuku.manager.starter.StarterActivity;

/* JADX INFO: loaded from: classes.dex */
public final class Pl implements Mn {
    public final /* synthetic */ int a;
    public final /* synthetic */ Object b;

    public /* synthetic */ Pl(int i, Object obj) {
        this.a = i;
        this.b = obj;
    }

    @Override // rikka.shizuku.Mn
    public final void a() {
        View decorView;
        Object obj = this.b;
        switch (this.a) {
            case 0:
                ((CountDownLatch) obj).countDown();
                Nn.j(this);
                break;
            default:
                Nn.j(this);
                int i = StarterActivity.E;
                StarterActivity starterActivity = (StarterActivity) obj;
                Us us = (Us) starterActivity.D.getValue();
                StringBuilder sb = us.b;
                sb.append("Service started, this window will be automatically closed in 3 seconds");
                sb.append('\n');
                us.d(null);
                Window window = starterActivity.getWindow();
                if (window != null && (decorView = window.getDecorView()) != null) {
                    decorView.postDelayed(new G0(20, starterActivity), 3000L);
                    break;
                }
                break;
        }
    }
}
