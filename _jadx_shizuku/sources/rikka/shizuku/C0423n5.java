package rikka.shizuku;

import android.view.View;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.sidesheet.SideSheetBehavior;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

/* JADX INFO: renamed from: rikka.shizuku.n5, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0423n5 {
    public final /* synthetic */ int a;
    public int b;
    public boolean c;
    public final Runnable d;
    public final /* synthetic */ K7 e;

    public C0423n5(SideSheetBehavior sideSheetBehavior) {
        this.a = 1;
        this.e = sideSheetBehavior;
        this.d = new G0(19, this);
    }

    public final void a(int i) {
        Runnable runnable = this.d;
        K7 k7 = this.e;
        switch (this.a) {
            case 0:
                BottomSheetBehavior bottomSheetBehavior = (BottomSheetBehavior) k7;
                WeakReference weakReference = bottomSheetBehavior.U;
                if (weakReference != null && weakReference.get() != null) {
                    this.b = i;
                    if (!this.c) {
                        WeakHashMap weakHashMap = Hs.a;
                        ((View) bottomSheetBehavior.U.get()).postOnAnimation((RunnableC0607t4) runnable);
                        this.c = true;
                    }
                    break;
                }
                break;
            default:
                SideSheetBehavior sideSheetBehavior = (SideSheetBehavior) k7;
                WeakReference weakReference2 = sideSheetBehavior.p;
                if (weakReference2 != null && weakReference2.get() != null) {
                    this.b = i;
                    if (!this.c) {
                        WeakHashMap weakHashMap2 = Hs.a;
                        ((View) sideSheetBehavior.p.get()).postOnAnimation((G0) runnable);
                        this.c = true;
                    }
                    break;
                }
                break;
        }
    }

    public C0423n5(BottomSheetBehavior bottomSheetBehavior) {
        this.a = 0;
        this.e = bottomSheetBehavior;
        this.d = new RunnableC0607t4(1, this);
    }
}
