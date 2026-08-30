package rikka.shizuku;

import java.util.ArrayList;
import java.util.List;
import rikka.html.text.style.BulletSpan;

/* JADX INFO: renamed from: rikka.shizuku.v5, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class RunnableC0670v5 implements Runnable {
    public final /* synthetic */ int a;
    public final int b;
    public final Object c;

    public /* synthetic */ RunnableC0670v5(int i, int i2, Object obj) {
        this.a = i2;
        this.c = obj;
        this.b = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.a) {
            case 0:
                X8 x8 = (X8) ((Hm) this.c).a;
                if (x8 != null) {
                    x8.K(this.b);
                }
                break;
            case 1:
                N6 n6 = (N6) this.c;
                String str = (String) n6.a.get(Integer.valueOf(this.b));
                if (str != null) {
                    Q0 q0 = (Q0) n6.e.get(str);
                    if (q0 == null) {
                        n6.g.remove(str);
                        n6.f.put(str, null);
                    } else {
                        L0 l0 = q0.a;
                        if (n6.d.remove(str)) {
                            l0.b(null);
                        }
                    }
                    break;
                }
                break;
            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                ArrayList arrayList = (ArrayList) this.c;
                int size = arrayList.size();
                int i = 0;
                if (this.b == 1) {
                    while (i < size) {
                        ((Ka) arrayList.get(i)).b();
                        i++;
                    }
                } else {
                    while (i < size) {
                        ((Ka) arrayList.get(i)).a();
                        i++;
                    }
                }
                break;
            default:
                ((Oh) this.c).d0.m0(this.b);
                break;
        }
    }

    public RunnableC0670v5(N6 n6, int i, AbstractC0599sr abstractC0599sr) {
        this.a = 1;
        this.c = n6;
        this.b = i;
    }

    public RunnableC0670v5(List list, int i, Throwable th) {
        this.a = 2;
        AbstractC0046as.j("initCallbacks cannot be null", list);
        this.c = new ArrayList(list);
        this.b = i;
    }
}
