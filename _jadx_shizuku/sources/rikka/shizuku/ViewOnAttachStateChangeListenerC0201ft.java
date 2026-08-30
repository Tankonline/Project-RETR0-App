package rikka.shizuku;

import android.view.View;
import java.util.WeakHashMap;

/* JADX INFO: renamed from: rikka.shizuku.ft, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class ViewOnAttachStateChangeListenerC0201ft implements View.OnAttachStateChangeListener {
    public final /* synthetic */ int a;

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewAttachedToWindow(View view) {
        switch (this.a) {
            case 0:
                view.removeOnAttachStateChangeListener(this);
                WeakHashMap weakHashMap = Hs.a;
                AbstractC0755xs.c(view);
                break;
            default:
                view.removeOnAttachStateChangeListener(this);
                view.requestApplyInsets();
                break;
        }
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewDetachedFromWindow(View view) {
        int i = this.a;
    }

    private final void a(View view) {
    }

    private final void b(View view) {
    }
}
