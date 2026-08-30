package rikka.shizuku;

import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;

/* JADX INFO: renamed from: rikka.shizuku.ei, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class DialogInterfaceOnKeyListenerC0159ei implements DialogInterface.OnKeyListener, DialogInterface.OnClickListener, DialogInterface.OnDismissListener, InterfaceC0652ui {
    public SubMenuC0166ep a;
    public J1 b;
    public Sg c;

    @Override // rikka.shizuku.InterfaceC0652ui
    public final void b(MenuC0129di menuC0129di, boolean z) {
        J1 j1;
        if ((z || menuC0129di == this.a) && (j1 = this.b) != null) {
            j1.dismiss();
        }
    }

    @Override // rikka.shizuku.InterfaceC0652ui
    public final boolean c(MenuC0129di menuC0129di) {
        return false;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        Sg sg = this.c;
        if (sg.f == null) {
            sg.f = new Rg(sg);
        }
        this.a.q(sg.f.getItem(i), null, 0);
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        this.c.b(this.a, true);
    }

    @Override // android.content.DialogInterface.OnKeyListener
    public final boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        Window window;
        View decorView;
        KeyEvent.DispatcherState keyDispatcherState;
        View decorView2;
        KeyEvent.DispatcherState keyDispatcherState2;
        SubMenuC0166ep subMenuC0166ep = this.a;
        if (i == 82 || i == 4) {
            if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
                Window window2 = this.b.getWindow();
                if (window2 != null && (decorView2 = window2.getDecorView()) != null && (keyDispatcherState2 = decorView2.getKeyDispatcherState()) != null) {
                    keyDispatcherState2.startTracking(keyEvent, this);
                    return true;
                }
            } else if (keyEvent.getAction() == 1 && !keyEvent.isCanceled() && (window = this.b.getWindow()) != null && (decorView = window.getDecorView()) != null && (keyDispatcherState = decorView.getKeyDispatcherState()) != null && keyDispatcherState.isTracking(keyEvent)) {
                subMenuC0166ep.c(true);
                dialogInterface.dismiss();
                return true;
            }
        }
        return subMenuC0166ep.performShortcut(i, keyEvent, 0);
    }
}
