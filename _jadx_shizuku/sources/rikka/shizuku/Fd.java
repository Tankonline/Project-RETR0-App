package rikka.shizuku;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Arrays;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public final class Fd {
    public int a;
    public int b;
    public int c;
    public Object d;

    public void a(int i, int i2) {
        if (i < 0) {
            throw new IllegalArgumentException("Layout positions must be non-negative");
        }
        if (i2 < 0) {
            throw new IllegalArgumentException("Pixel distance must be non-negative");
        }
        int i3 = this.c;
        int i4 = i3 * 2;
        int[] iArr = (int[]) this.d;
        if (iArr == null) {
            int[] iArr2 = new int[4];
            this.d = iArr2;
            Arrays.fill(iArr2, -1);
        } else if (i4 >= iArr.length) {
            int[] iArr3 = new int[i3 * 4];
            this.d = iArr3;
            System.arraycopy(iArr, 0, iArr3, 0, iArr.length);
        }
        int[] iArr4 = (int[]) this.d;
        iArr4[i4] = i;
        iArr4[i4 + 1] = i2;
        this.c++;
    }

    public void b() {
        int i = this.c;
        View view = (View) this.d;
        int top = i - (view.getTop() - this.a);
        WeakHashMap weakHashMap = Hs.a;
        view.offsetTopAndBottom(top);
        view.offsetLeftAndRight(0 - (view.getLeft() - this.b));
    }

    public void c(RecyclerView recyclerView, boolean z) {
        this.c = 0;
        int[] iArr = (int[]) this.d;
        if (iArr != null) {
            Arrays.fill(iArr, -1);
        }
        AbstractC0162el abstractC0162el = recyclerView.n;
        if (recyclerView.m == null || abstractC0162el == null || !abstractC0162el.i) {
            return;
        }
        if (z) {
            if (!recyclerView.e.h()) {
                abstractC0162el.i(recyclerView.m.a(), this);
            }
        } else if (!recyclerView.O()) {
            abstractC0162el.h(this.a, this.b, recyclerView.g0, this);
        }
        int i = this.c;
        if (i > abstractC0162el.j) {
            abstractC0162el.j = i;
            abstractC0162el.k = z;
            recyclerView.c.m();
        }
    }
}
