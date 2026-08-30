package rikka.shizuku;

import android.view.ViewTreeObserver;
import android.widget.PopupWindow;

/* JADX INFO: renamed from: rikka.shizuku.f3, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0175f3 implements PopupWindow.OnDismissListener {
    public final /* synthetic */ Y2 a;
    public final /* synthetic */ C0206g3 b;

    public C0175f3(C0206g3 c0206g3, Y2 y2) {
        this.b = c0206g3;
        this.a = y2;
    }

    @Override // android.widget.PopupWindow.OnDismissListener
    public final void onDismiss() {
        ViewTreeObserver viewTreeObserver = this.b.G.getViewTreeObserver();
        if (viewTreeObserver != null) {
            viewTreeObserver.removeGlobalOnLayoutListener(this.a);
        }
    }
}
