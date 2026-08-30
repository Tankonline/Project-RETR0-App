package rikka.shizuku;

import android.view.View;
import android.view.ViewGroup;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.sidesheet.SideSheetBehavior;

/* JADX INFO: renamed from: rikka.shizuku.mg, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0403mg extends AbstractC0688vn {
    public final /* synthetic */ int b;
    public final SideSheetBehavior c;

    public /* synthetic */ C0403mg(SideSheetBehavior sideSheetBehavior, int i) {
        this.b = i;
        this.c = sideSheetBehavior;
    }

    @Override // rikka.shizuku.AbstractC0688vn
    public final void B(ViewGroup.MarginLayoutParams marginLayoutParams, int i, int i2) {
        switch (this.b) {
            case 0:
                if (i <= this.c.m) {
                    marginLayoutParams.leftMargin = i2;
                }
                break;
            default:
                int i3 = this.c.m;
                if (i <= i3) {
                    marginLayoutParams.rightMargin = i3 - i;
                }
                break;
        }
    }

    @Override // rikka.shizuku.AbstractC0688vn
    public final int d(ViewGroup.MarginLayoutParams marginLayoutParams) {
        switch (this.b) {
            case 0:
                return marginLayoutParams.leftMargin;
            default:
                return marginLayoutParams.rightMargin;
        }
    }

    @Override // rikka.shizuku.AbstractC0688vn
    public final float e(int i) {
        switch (this.b) {
            case 0:
                float fM = m();
                return (i - fM) / (l() - fM);
            default:
                float f = this.c.m;
                return (f - i) / (f - l());
        }
    }

    @Override // rikka.shizuku.AbstractC0688vn
    public final int l() {
        switch (this.b) {
            case 0:
                SideSheetBehavior sideSheetBehavior = this.c;
                return Math.max(0, sideSheetBehavior.n + sideSheetBehavior.o);
            default:
                SideSheetBehavior sideSheetBehavior2 = this.c;
                return Math.max(0, (sideSheetBehavior2.m - sideSheetBehavior2.l) - sideSheetBehavior2.o);
        }
    }

    @Override // rikka.shizuku.AbstractC0688vn
    public final int m() {
        switch (this.b) {
            case 0:
                SideSheetBehavior sideSheetBehavior = this.c;
                return (-sideSheetBehavior.l) - sideSheetBehavior.o;
            default:
                return this.c.m;
        }
    }

    @Override // rikka.shizuku.AbstractC0688vn
    public final int n() {
        switch (this.b) {
            case 0:
                return this.c.o;
            default:
                return this.c.m;
        }
    }

    @Override // rikka.shizuku.AbstractC0688vn
    public final int o() {
        switch (this.b) {
            case 0:
                return -this.c.l;
            default:
                return l();
        }
    }

    @Override // rikka.shizuku.AbstractC0688vn
    public final int p(View view) {
        switch (this.b) {
            case 0:
                return view.getRight() + this.c.o;
            default:
                return view.getLeft() - this.c.o;
        }
    }

    @Override // rikka.shizuku.AbstractC0688vn
    public final int q(CoordinatorLayout coordinatorLayout) {
        switch (this.b) {
            case 0:
                return coordinatorLayout.getLeft();
            default:
                return coordinatorLayout.getRight();
        }
    }

    @Override // rikka.shizuku.AbstractC0688vn
    public final int r() {
        switch (this.b) {
            case 0:
                return 1;
            default:
                return 0;
        }
    }

    @Override // rikka.shizuku.AbstractC0688vn
    public final boolean t(float f) {
        switch (this.b) {
            case 0:
                return f > 0.0f;
            default:
                return f < 0.0f;
        }
    }

    @Override // rikka.shizuku.AbstractC0688vn
    public final boolean u(View view) {
        switch (this.b) {
            case 0:
                return view.getRight() < (l() - m()) / 2;
            default:
                return view.getLeft() > (l() + this.c.m) / 2;
        }
    }

    @Override // rikka.shizuku.AbstractC0688vn
    public final boolean v(float f, float f2) {
        switch (this.b) {
            case 0:
                if (Math.abs(f) > Math.abs(f2)) {
                    float fAbs = Math.abs(f);
                    this.c.getClass();
                    if (fAbs > 500) {
                        return true;
                    }
                }
                return false;
            default:
                if (Math.abs(f) > Math.abs(f2)) {
                    float fAbs2 = Math.abs(f);
                    this.c.getClass();
                    if (fAbs2 > 500) {
                        return true;
                    }
                }
                return false;
        }
    }

    @Override // rikka.shizuku.AbstractC0688vn
    public final boolean z(View view, float f) {
        switch (this.b) {
            case 0:
                float left = view.getLeft();
                SideSheetBehavior sideSheetBehavior = this.c;
                float fAbs = Math.abs((f * sideSheetBehavior.k) + left);
                sideSheetBehavior.getClass();
                return fAbs > 0.5f;
            default:
                float right = view.getRight();
                SideSheetBehavior sideSheetBehavior2 = this.c;
                float fAbs2 = Math.abs((f * sideSheetBehavior2.k) + right);
                sideSheetBehavior2.getClass();
                return fAbs2 > 0.5f;
        }
    }
}
