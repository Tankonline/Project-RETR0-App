package rikka.shizuku;

import android.view.ActionProvider;
import android.view.View;

/* JADX INFO: renamed from: rikka.shizuku.hi, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class ActionProviderVisibilityListenerC0252hi implements ActionProvider.VisibilityListener {
    public final ActionProvider a;
    public final /* synthetic */ MenuItemC0343ki b;
    public Ys c;

    public ActionProviderVisibilityListenerC0252hi(MenuItemC0343ki menuItemC0343ki, ActionProvider actionProvider) {
        this.b = menuItemC0343ki;
        this.a = actionProvider;
    }

    public final View a(C0221gi c0221gi) {
        return this.a.onCreateActionView(c0221gi);
    }

    @Override // android.view.ActionProvider.VisibilityListener
    public final void onActionProviderVisibilityChanged(boolean z) {
        Ys ys = this.c;
        if (ys != null) {
            MenuC0129di menuC0129di = ((C0221gi) ys.b).n;
            menuC0129di.h = true;
            menuC0129di.p(true);
        }
    }
}
