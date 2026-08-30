package rikka.shizuku;

import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.appbar.AppBarLayout;
import java.util.WeakHashMap;

/* JADX INFO: renamed from: rikka.shizuku.h2, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0236h2 extends S {
    public final /* synthetic */ AppBarLayout d;
    public final /* synthetic */ CoordinatorLayout e;
    public final /* synthetic */ AppBarLayout.BaseBehavior f;

    public C0236h2(CoordinatorLayout coordinatorLayout, AppBarLayout.BaseBehavior baseBehavior, AppBarLayout appBarLayout) {
        this.f = baseBehavior;
        this.d = appBarLayout;
        this.e = coordinatorLayout;
    }

    @Override // rikka.shizuku.S
    public final void d(View view, C0141e0 c0141e0) {
        AppBarLayout.BaseBehavior baseBehavior;
        View viewU;
        this.a.onInitializeAccessibilityNodeInfo(view, c0141e0.a);
        c0141e0.i(ScrollView.class.getName());
        AppBarLayout appBarLayout = this.d;
        if (appBarLayout.f() == 0 || (viewU = AppBarLayout.BaseBehavior.u((baseBehavior = this.f), this.e)) == null) {
            return;
        }
        int childCount = appBarLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (((C0296j2) appBarLayout.getChildAt(i).getLayoutParams()).a != 0) {
                if (baseBehavior.x() != (-appBarLayout.f())) {
                    c0141e0.b(C0018a0.f);
                    c0141e0.l(true);
                }
                if (baseBehavior.x() != 0) {
                    if (!viewU.canScrollVertically(-1)) {
                        c0141e0.b(C0018a0.g);
                        c0141e0.l(true);
                        return;
                    } else {
                        if ((-appBarLayout.c()) != 0) {
                            c0141e0.b(C0018a0.g);
                            c0141e0.l(true);
                            return;
                        }
                        return;
                    }
                }
                return;
            }
        }
    }

    @Override // rikka.shizuku.S
    public final boolean g(View view, int i, Bundle bundle) {
        AppBarLayout appBarLayout = this.d;
        if (i == 4096) {
            appBarLayout.getClass();
            WeakHashMap weakHashMap = Hs.a;
            appBarLayout.h(false, appBarLayout.isLaidOut(), true);
            return true;
        }
        if (i != 8192) {
            return super.g(view, i, bundle);
        }
        AppBarLayout.BaseBehavior baseBehavior = this.f;
        if (baseBehavior.x() != 0) {
            View viewU = AppBarLayout.BaseBehavior.u(baseBehavior, this.e);
            if (!viewU.canScrollVertically(-1)) {
                appBarLayout.getClass();
                WeakHashMap weakHashMap2 = Hs.a;
                appBarLayout.h(true, appBarLayout.isLaidOut(), true);
                return true;
            }
            int i2 = -appBarLayout.c();
            if (i2 != 0) {
                CoordinatorLayout coordinatorLayout = this.e;
                AppBarLayout appBarLayout2 = this.d;
                this.f.y(coordinatorLayout, appBarLayout2, viewU, i2, new int[]{0, 0});
                return true;
            }
        }
        return false;
    }
}
