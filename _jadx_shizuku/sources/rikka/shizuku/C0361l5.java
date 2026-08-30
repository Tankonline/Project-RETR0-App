package rikka.shizuku;

import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.sidesheet.SideSheetBehavior;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.LinkedHashSet;

/* JADX INFO: renamed from: rikka.shizuku.l5, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0361l5 extends An {
    public final /* synthetic */ int e;
    public final /* synthetic */ K7 f;

    public /* synthetic */ C0361l5(K7 k7, int i) {
        this.e = i;
        this.f = k7;
    }

    @Override // rikka.shizuku.An
    public final int a(View view, int i) {
        switch (this.e) {
            case 0:
                return view.getLeft();
            default:
                SideSheetBehavior sideSheetBehavior = (SideSheetBehavior) this.f;
                return AbstractC0744xh.i(i, sideSheetBehavior.a.o(), sideSheetBehavior.a.n());
        }
    }

    @Override // rikka.shizuku.An
    public final int b(View view, int i) {
        switch (this.e) {
            case 0:
                return AbstractC0744xh.i(i, ((BottomSheetBehavior) this.f).y(), e());
            default:
                return view.getTop();
        }
    }

    @Override // rikka.shizuku.An
    public int d(View view) {
        switch (this.e) {
            case 1:
                SideSheetBehavior sideSheetBehavior = (SideSheetBehavior) this.f;
                return sideSheetBehavior.l + sideSheetBehavior.o;
            default:
                return super.d(view);
        }
    }

    @Override // rikka.shizuku.An
    public int e() {
        switch (this.e) {
            case 0:
                BottomSheetBehavior bottomSheetBehavior = (BottomSheetBehavior) this.f;
                return bottomSheetBehavior.I ? bottomSheetBehavior.T : bottomSheetBehavior.G;
            default:
                return super.e();
        }
    }

    @Override // rikka.shizuku.An
    public final void j(int i) {
        switch (this.e) {
            case 0:
                if (i == 1) {
                    BottomSheetBehavior bottomSheetBehavior = (BottomSheetBehavior) this.f;
                    if (bottomSheetBehavior.K) {
                        bottomSheetBehavior.D(1);
                    }
                }
                break;
            default:
                if (i == 1) {
                    SideSheetBehavior sideSheetBehavior = (SideSheetBehavior) this.f;
                    if (sideSheetBehavior.g) {
                        sideSheetBehavior.s(1);
                    }
                }
                break;
        }
    }

    @Override // rikka.shizuku.An
    public final void k(View view, int i, int i2) {
        ViewGroup.MarginLayoutParams marginLayoutParams;
        switch (this.e) {
            case 0:
                ((BottomSheetBehavior) this.f).v(i2);
                return;
            default:
                SideSheetBehavior sideSheetBehavior = (SideSheetBehavior) this.f;
                WeakReference weakReference = sideSheetBehavior.q;
                View view2 = weakReference != null ? (View) weakReference.get() : null;
                if (view2 != null && (marginLayoutParams = (ViewGroup.MarginLayoutParams) view2.getLayoutParams()) != null) {
                    sideSheetBehavior.a.B(marginLayoutParams, view.getLeft(), view.getRight());
                    view2.setLayoutParams(marginLayoutParams);
                }
                LinkedHashSet linkedHashSet = sideSheetBehavior.u;
                if (linkedHashSet.isEmpty()) {
                    return;
                }
                sideSheetBehavior.a.e(i);
                Iterator it = linkedHashSet.iterator();
                if (it.hasNext()) {
                    it.next().getClass();
                    throw new ClassCastException();
                }
                return;
        }
    }

    /* JADX WARN: Code duplicated, block: B:21:0x005a  */
    /* JADX WARN: Code duplicated, block: B:29:0x0071  */
    /* JADX WARN: Code duplicated, block: B:43:0x00b0  */
    /* JADX WARN: Code duplicated, block: B:57:0x00e9  */
    @Override // rikka.shizuku.An
    public final void l(View view, float f, float f2) {
        int i;
        switch (this.e) {
            case 0:
                int i2 = 6;
                BottomSheetBehavior bottomSheetBehavior = (BottomSheetBehavior) this.f;
                if (f2 < 0.0f) {
                    if (bottomSheetBehavior.b) {
                        i2 = 3;
                    } else {
                        int top = view.getTop();
                        System.currentTimeMillis();
                        bottomSheetBehavior.getClass();
                        if (top <= bottomSheetBehavior.E) {
                            i2 = 3;
                        }
                    }
                } else if (bottomSheetBehavior.I && bottomSheetBehavior.E(view, f2)) {
                    if (Math.abs(f) >= Math.abs(f2) || f2 <= bottomSheetBehavior.d) {
                        if (view.getTop() > (bottomSheetBehavior.y() + bottomSheetBehavior.T) / 2) {
                            i2 = 5;
                        } else if (bottomSheetBehavior.b || Math.abs(view.getTop() - bottomSheetBehavior.y()) < Math.abs(view.getTop() - bottomSheetBehavior.E)) {
                            i2 = 3;
                        }
                    } else {
                        i2 = 5;
                    }
                } else if (f2 == 0.0f || Math.abs(f) > Math.abs(f2)) {
                    int top2 = view.getTop();
                    if (!bottomSheetBehavior.b) {
                        int i3 = bottomSheetBehavior.E;
                        if (top2 < i3) {
                            if (top2 < Math.abs(top2 - bottomSheetBehavior.G)) {
                                i2 = 3;
                            } else {
                                bottomSheetBehavior.getClass();
                            }
                        } else if (Math.abs(top2 - i3) < Math.abs(top2 - bottomSheetBehavior.G)) {
                            bottomSheetBehavior.getClass();
                        } else {
                            i2 = 4;
                        }
                    } else if (Math.abs(top2 - bottomSheetBehavior.D) < Math.abs(top2 - bottomSheetBehavior.G)) {
                        i2 = 3;
                    } else {
                        i2 = 4;
                    }
                } else if (bottomSheetBehavior.b) {
                    i2 = 4;
                } else {
                    int top3 = view.getTop();
                    if (Math.abs(top3 - bottomSheetBehavior.E) < Math.abs(top3 - bottomSheetBehavior.G)) {
                        bottomSheetBehavior.getClass();
                    } else {
                        i2 = 4;
                    }
                }
                bottomSheetBehavior.getClass();
                bottomSheetBehavior.F(view, i2, true);
                break;
            default:
                SideSheetBehavior sideSheetBehavior = (SideSheetBehavior) this.f;
                if (!sideSheetBehavior.a.t(f)) {
                    if (!sideSheetBehavior.a.z(view, f)) {
                        if (f == 0.0f || Math.abs(f) <= Math.abs(f2)) {
                            int left = view.getLeft();
                            i = Math.abs(left - sideSheetBehavior.a.l()) < Math.abs(left - sideSheetBehavior.a.m()) ? 3 : 5;
                        }
                    } else if (sideSheetBehavior.a.v(f, f2) || sideSheetBehavior.a.u(view)) {
                    }
                }
                sideSheetBehavior.u(view, i, true);
                break;
        }
    }

    /* JADX WARN: Code duplicated, block: B:31:0x0048  */
    @Override // rikka.shizuku.An
    public final boolean s(View view, int i) {
        WeakReference weakReference;
        WeakReference weakReference2;
        switch (this.e) {
            case 0:
                BottomSheetBehavior bottomSheetBehavior = (BottomSheetBehavior) this.f;
                int i2 = bottomSheetBehavior.L;
                if (i2 != 1 && !bottomSheetBehavior.a0) {
                    if (i2 == 3 && bottomSheetBehavior.Y == i) {
                        WeakReference weakReference3 = bottomSheetBehavior.V;
                        View view2 = weakReference3 != null ? (View) weakReference3.get() : null;
                        if (view2 == null || !view2.canScrollVertically(-1)) {
                            System.currentTimeMillis();
                            weakReference = bottomSheetBehavior.U;
                            if (weakReference == null) {
                            }
                        }
                    } else {
                        System.currentTimeMillis();
                        weakReference = bottomSheetBehavior.U;
                        if (weakReference == null && weakReference.get() == view) {
                            return true;
                        }
                    }
                }
                return false;
            default:
                SideSheetBehavior sideSheetBehavior = (SideSheetBehavior) this.f;
                return (sideSheetBehavior.h == 1 || (weakReference2 = sideSheetBehavior.p) == null || weakReference2.get() != view) ? false : true;
        }
    }
}
