package rikka.shizuku;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: renamed from: rikka.shizuku.za, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0799za extends AbstractC0070bl {
    public final /* synthetic */ int a;
    public final int b;
    public final int c;
    public final int d;
    public final int e;

    public /* synthetic */ C0799za(int i, int i2, int i3, int i4, int i5) {
        this.a = i5;
        this.b = i;
        this.c = i2;
        this.d = i3;
        this.e = i4;
    }

    @Override // rikka.shizuku.AbstractC0070bl
    public final void a(Rect rect, View view, RecyclerView recyclerView) {
        boolean z;
        switch (this.a) {
            case 0:
                Yk yk = recyclerView.m;
                if (yk != null) {
                    AbstractC0470ol abstractC0470olK = RecyclerView.K(view);
                    int iB = abstractC0470olK != null ? abstractC0470olK.b() : -1;
                    int iA = yk.a();
                    if (iB == 0) {
                        rect.top = this.c;
                    }
                    boolean z2 = true;
                    if (iB == iA - 1) {
                        rect.bottom = this.e;
                    }
                    AbstractC0162el abstractC0162el = recyclerView.n;
                    ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                    if ((abstractC0162el instanceof GridLayoutManager) && (layoutParams instanceof Md)) {
                        int i = ((GridLayoutManager) abstractC0162el).F;
                        int i2 = ((Md) layoutParams).e;
                        boolean z3 = i2 == 0;
                        z = i2 == i - 1;
                        z2 = z3;
                    } else {
                        z = true;
                    }
                    if (z2) {
                        rect.left = this.b;
                    }
                    if (z) {
                        rect.right = this.d;
                    }
                    break;
                }
                break;
            default:
                if (recyclerView.m != null) {
                    rect.left = this.b;
                    rect.top = this.c;
                    rect.right = this.d;
                    rect.bottom = this.e;
                    break;
                }
                break;
        }
    }
}
