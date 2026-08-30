package rikka.shizuku;

import androidx.appcompat.widget.ActionBarOverlayLayout;

/* JADX INFO: renamed from: rikka.shizuku.u0, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class RunnableC0634u0 implements Runnable {
    public final /* synthetic */ int a;
    public final /* synthetic */ ActionBarOverlayLayout b;

    public /* synthetic */ RunnableC0634u0(ActionBarOverlayLayout actionBarOverlayLayout, int i) {
        this.a = i;
        this.b = actionBarOverlayLayout;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.a) {
            case 0:
                ActionBarOverlayLayout actionBarOverlayLayout = this.b;
                actionBarOverlayLayout.h();
                actionBarOverlayLayout.v = actionBarOverlayLayout.d.animate().translationY(0.0f).setListener(actionBarOverlayLayout.w);
                break;
            default:
                ActionBarOverlayLayout actionBarOverlayLayout2 = this.b;
                actionBarOverlayLayout2.h();
                actionBarOverlayLayout2.v = actionBarOverlayLayout2.d.animate().translationY(-actionBarOverlayLayout2.d.getHeight()).setListener(actionBarOverlayLayout2.w);
                break;
        }
    }
}
