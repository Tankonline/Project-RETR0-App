package rikka.shizuku;

import android.view.View;
import android.view.ViewParent;
import com.google.android.material.behavior.SwipeDismissBehavior;
import java.util.WeakHashMap;

/* JADX INFO: renamed from: rikka.shizuku.op, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0474op extends An {
    public int e;
    public int f = -1;
    public final /* synthetic */ SwipeDismissBehavior g;

    public C0474op(SwipeDismissBehavior swipeDismissBehavior) {
        this.g = swipeDismissBehavior;
    }

    @Override // rikka.shizuku.An
    public final int a(View view, int i) {
        int width;
        int width2;
        int width3;
        WeakHashMap weakHashMap = Hs.a;
        boolean z = view.getLayoutDirection() == 1;
        int i2 = this.g.d;
        if (i2 == 0) {
            if (z) {
                width = this.e - view.getWidth();
                width2 = this.e;
            } else {
                width = this.e;
                width3 = view.getWidth();
                width2 = width3 + width;
            }
        } else if (i2 != 1) {
            width = this.e - view.getWidth();
            width2 = view.getWidth() + this.e;
        } else if (z) {
            width = this.e;
            width3 = view.getWidth();
            width2 = width3 + width;
        } else {
            width = this.e - view.getWidth();
            width2 = this.e;
        }
        return Math.min(Math.max(width, i), width2);
    }

    @Override // rikka.shizuku.An
    public final int b(View view, int i) {
        return view.getTop();
    }

    @Override // rikka.shizuku.An
    public final int d(View view) {
        return view.getWidth();
    }

    @Override // rikka.shizuku.An
    public final void i(View view, int i) {
        this.f = i;
        this.e = view.getLeft();
        ViewParent parent = view.getParent();
        if (parent != null) {
            SwipeDismissBehavior swipeDismissBehavior = this.g;
            swipeDismissBehavior.c = true;
            parent.requestDisallowInterceptTouchEvent(true);
            swipeDismissBehavior.c = false;
        }
    }

    @Override // rikka.shizuku.An
    public final void k(View view, int i, int i2) {
        float width = view.getWidth();
        SwipeDismissBehavior swipeDismissBehavior = this.g;
        float f = width * swipeDismissBehavior.e;
        float width2 = view.getWidth() * swipeDismissBehavior.f;
        float fAbs = Math.abs(i - this.e);
        if (fAbs <= f) {
            view.setAlpha(1.0f);
        } else if (fAbs >= width2) {
            view.setAlpha(0.0f);
        } else {
            view.setAlpha(Math.min(Math.max(0.0f, 1.0f - ((fAbs - f) / (width2 - f))), 1.0f));
        }
    }

    /* JADX WARN: Code duplicated, block: B:27:0x0052  */
    /* JADX WARN: Code duplicated, block: B:29:0x0056  */
    /* JADX WARN: Code duplicated, block: B:32:0x005f  */
    /* JADX WARN: Code duplicated, block: B:33:0x0061  */
    /* JADX WARN: Code duplicated, block: B:34:0x0066  */
    @Override // rikka.shizuku.An
    public final void l(View view, float f, float f2) {
        int i;
        int left;
        int i2;
        this.f = -1;
        int width = view.getWidth();
        SwipeDismissBehavior swipeDismissBehavior = this.g;
        boolean z = true;
        if (f != 0.0f) {
            WeakHashMap weakHashMap = Hs.a;
            boolean z2 = view.getLayoutDirection() == 1;
            int i3 = swipeDismissBehavior.d;
            if (i3 != 2 && (i3 != 0 ? i3 != 1 || (!z2 ? f < 0.0f : f > 0.0f) : !z2 ? f > 0.0f : f < 0.0f)) {
                i = this.e;
                z = false;
            } else if (f >= 0.0f) {
                left = view.getLeft();
                i2 = this.e;
                if (left < i2) {
                    i = this.e - width;
                } else {
                    i = i2 + width;
                }
            } else {
                i = this.e - width;
            }
        } else {
            if (Math.abs(view.getLeft() - this.e) < Math.round(view.getWidth() * 0.5f)) {
                i = this.e;
                z = false;
            } else if (f >= 0.0f) {
                left = view.getLeft();
                i2 = this.e;
                if (left < i2) {
                    i = this.e - width;
                } else {
                    i = i2 + width;
                }
            } else {
                i = this.e - width;
            }
        }
        if (swipeDismissBehavior.a.o(i, view.getTop())) {
            A0 a0 = new A0(swipeDismissBehavior, view, z);
            WeakHashMap weakHashMap2 = Hs.a;
            view.postOnAnimation(a0);
        }
    }

    @Override // rikka.shizuku.An
    public final boolean s(View view, int i) {
        int i2 = this.f;
        return (i2 == -1 || i2 == i) && this.g.s(view);
    }

    @Override // rikka.shizuku.An
    public final void j(int i) {
    }
}
