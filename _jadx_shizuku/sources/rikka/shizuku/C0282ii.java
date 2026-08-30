package rikka.shizuku;

import android.view.CollapsibleActionView;
import android.view.View;
import android.widget.FrameLayout;

/* JADX INFO: renamed from: rikka.shizuku.ii, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0282ii extends FrameLayout implements InterfaceC0547r6 {
    public final CollapsibleActionView a;

    /* JADX WARN: Multi-variable type inference failed */
    public C0282ii(View view) {
        super(view.getContext());
        this.a = (CollapsibleActionView) view;
        addView(view);
    }

    @Override // rikka.shizuku.InterfaceC0547r6
    public final void d() {
        this.a.onActionViewExpanded();
    }

    @Override // rikka.shizuku.InterfaceC0547r6
    public final void f() {
        this.a.onActionViewCollapsed();
    }
}
