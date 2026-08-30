package rikka.shizuku;

import android.view.View;
import java.lang.ref.WeakReference;

/* JADX INFO: renamed from: rikka.shizuku.ct, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0109ct {
    public final WeakReference a;

    public C0109ct(View view) {
        this.a = new WeakReference(view);
    }

    public final void a(float f) {
        View view = (View) this.a.get();
        if (view != null) {
            view.animate().alpha(f);
        }
    }

    public final void b() {
        View view = (View) this.a.get();
        if (view != null) {
            view.animate().cancel();
        }
    }

    public final void c(long j) {
        View view = (View) this.a.get();
        if (view != null) {
            view.animate().setDuration(j);
        }
    }

    public final void d(InterfaceC0170et interfaceC0170et) {
        View view = (View) this.a.get();
        if (view != null) {
            if (interfaceC0170et != null) {
                view.animate().setListener(new C0603t0(interfaceC0170et, view));
            } else {
                view.animate().setListener(null);
            }
        }
    }

    public final void e(float f) {
        View view = (View) this.a.get();
        if (view != null) {
            view.animate().translationY(f);
        }
    }
}
