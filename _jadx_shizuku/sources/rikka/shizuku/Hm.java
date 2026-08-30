package rikka.shizuku;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import androidx.core.widget.NestedScrollView;
import com.google.android.material.behavior.SwipeDismissBehavior;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public final class Hm implements InterfaceC0449o0, Xs {
    public static Hm b;
    public Object a;

    public /* synthetic */ Hm(Object obj) {
        this.a = obj;
    }

    @Override // rikka.shizuku.Xs
    public Vs a(Class cls) {
        return (Vs) ((InterfaceC0124dd) ((Xn) this.a).b).a();
    }

    @Override // rikka.shizuku.InterfaceC0449o0
    public boolean c(View view) {
        SwipeDismissBehavior swipeDismissBehavior = (SwipeDismissBehavior) this.a;
        if (!swipeDismissBehavior.s(view)) {
            return false;
        }
        WeakHashMap weakHashMap = Hs.a;
        boolean z = view.getLayoutDirection() == 1;
        int i = swipeDismissBehavior.d;
        view.offsetLeftAndRight((!(i == 0 && z) && (i != 1 || z)) ? view.getWidth() : -view.getWidth());
        view.setAlpha(0.0f);
        return true;
    }

    public Hm(NestedScrollView nestedScrollView) {
        if (Build.VERSION.SDK_INT >= 35) {
            this.a = new Fm(nestedScrollView);
        } else {
            this.a = new Ee();
        }
    }

    public Hm() {
        this.a = new Object();
        new Handler(Looper.getMainLooper(), new C0442no(this));
    }
}
