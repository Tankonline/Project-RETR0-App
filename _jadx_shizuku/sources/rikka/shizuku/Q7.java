package rikka.shizuku;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Comparator;
import java.util.WeakHashMap;
import rikka.html.text.style.BulletSpan;

/* JADX INFO: loaded from: classes.dex */
public final class Q7 implements Comparator {
    public final /* synthetic */ int a;

    public /* synthetic */ Q7(int i) {
        this.a = i;
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        switch (this.a) {
            case 0:
                WeakHashMap weakHashMap = Hs.a;
                float fG = AbstractC0817zs.g((View) obj);
                float fG2 = AbstractC0817zs.g((View) obj2);
                if (fG > fG2) {
                    return -1;
                }
                return fG < fG2 ? 1 : 0;
            case 1:
                Gd gd = (Gd) obj;
                Gd gd2 = (Gd) obj2;
                RecyclerView recyclerView = gd.d;
                if ((recyclerView == null) == (gd2.d == null)) {
                    boolean z = gd.a;
                    if (z == gd2.a) {
                        int i = gd2.b - gd.b;
                        if (i != 0) {
                            return i;
                        }
                        int i2 = gd.c - gd2.c;
                        if (i2 != 0) {
                            return i2;
                        }
                        return 0;
                    }
                    if (z) {
                        return -1;
                    }
                } else if (recyclerView != null) {
                    return -1;
                }
                return 1;
            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                return ((Comparable) obj).compareTo((Comparable) obj2);
            case 3:
                return ((C0565ro) obj).b - ((C0565ro) obj2).b;
            default:
                return ((View) obj).getTop() - ((View) obj2).getTop();
        }
    }
}
