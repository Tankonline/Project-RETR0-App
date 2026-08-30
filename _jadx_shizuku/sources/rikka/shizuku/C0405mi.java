package rikka.shizuku;

import android.widget.PopupWindow;

/* JADX INFO: renamed from: rikka.shizuku.mi, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0405mi implements PopupWindow.OnDismissListener {
    public final /* synthetic */ C0467oi a;

    public C0405mi(C0467oi c0467oi) {
        this.a = c0467oi;
    }

    @Override // android.widget.PopupWindow.OnDismissListener
    public final void onDismiss() {
        this.a.c();
    }
}
