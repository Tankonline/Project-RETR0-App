package rikka.shizuku;

import android.os.Build;
import android.view.View;
import android.view.WindowInsets;
import java.util.WeakHashMap;

/* JADX INFO: renamed from: rikka.shizuku.ys, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class ViewOnApplyWindowInsetsListenerC0786ys implements View.OnApplyWindowInsetsListener {
    public au a = null;
    public final /* synthetic */ View b;
    public final /* synthetic */ InterfaceC0344kj c;

    public ViewOnApplyWindowInsetsListenerC0786ys(View view, InterfaceC0344kj interfaceC0344kj) {
        this.b = view;
        this.c = interfaceC0344kj;
    }

    @Override // android.view.View.OnApplyWindowInsetsListener
    public WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
        au auVarE = au.e(view, windowInsets);
        int i = Build.VERSION.SDK_INT;
        InterfaceC0344kj interfaceC0344kj = this.c;
        if (i < 30) {
            AbstractC0817zs.a(windowInsets, this.b);
            if (auVarE.equals(this.a)) {
                return interfaceC0344kj.f(view, auVarE).d();
            }
        }
        this.a = auVarE;
        au auVarF = interfaceC0344kj.f(view, auVarE);
        if (i >= 30) {
            return auVarF.d();
        }
        WeakHashMap weakHashMap = Hs.a;
        AbstractC0755xs.c(view);
        return auVarF.d();
    }
}
