package rikka.shizuku;

import android.view.View;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class Hg {
    public boolean a;
    public int b;
    public int c;
    public int d;
    public int e;
    public int f;
    public int g;
    public int h;
    public int i;
    public int j;
    public List k;
    public boolean l;

    public final void a(View view) {
        int iB;
        int size = this.k.size();
        View view2 = null;
        int i = Integer.MAX_VALUE;
        for (int i2 = 0; i2 < size; i2++) {
            View view3 = ((AbstractC0470ol) this.k.get(i2)).a;
            C0193fl c0193fl = (C0193fl) view3.getLayoutParams();
            if (view3 != view && !c0193fl.a.h() && (iB = (c0193fl.a.b() - this.d) * this.e) >= 0 && iB < i) {
                view2 = view3;
                if (iB == 0) {
                    break;
                } else {
                    i = iB;
                }
            }
        }
        if (view2 == null) {
            this.d = -1;
        } else {
            this.d = ((C0193fl) view2.getLayoutParams()).a.b();
        }
    }

    public final View b(C0285il c0285il) {
        List list = this.k;
        if (list == null) {
            View view = c0285il.k(this.d, Long.MAX_VALUE).a;
            this.d += this.e;
            return view;
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            View view2 = ((AbstractC0470ol) this.k.get(i)).a;
            C0193fl c0193fl = (C0193fl) view2.getLayoutParams();
            if (!c0193fl.a.h() && this.d == c0193fl.a.b()) {
                a(view2);
                return view2;
            }
        }
        return null;
    }
}
