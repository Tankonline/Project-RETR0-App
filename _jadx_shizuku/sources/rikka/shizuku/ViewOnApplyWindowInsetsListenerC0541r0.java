package rikka.shizuku;

import android.view.View;
import android.view.WindowInsets;
import androidx.appcompat.widget.ActionBarContextView;

/* JADX INFO: renamed from: rikka.shizuku.r0, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class ViewOnApplyWindowInsetsListenerC0541r0 implements View.OnApplyWindowInsetsListener {
    public final /* synthetic */ ActionBarContextView a;

    public ViewOnApplyWindowInsetsListenerC0541r0(ActionBarContextView actionBarContextView) {
        this.a = actionBarContextView;
    }

    @Override // android.view.View.OnApplyWindowInsetsListener
    public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
        ActionBarContextView.a(this.a, windowInsets);
        return windowInsets;
    }
}
