package rikka.shizuku;

import java.util.ArrayList;

/* JADX INFO: renamed from: rikka.shizuku.u9, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class RunnableC0643u9 implements Runnable {
    public final /* synthetic */ int a;
    public final /* synthetic */ E9 b;
    public final /* synthetic */ C0751xo c;

    public /* synthetic */ RunnableC0643u9(E9 e9, C0751xo c0751xo, int i) {
        this.a = i;
        this.b = e9;
        this.c = c0751xo;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.a) {
            case 0:
                this.b.a(this.c);
                break;
            case 1:
                E9 e9 = this.b;
                ArrayList arrayList = e9.b;
                C0751xo c0751xo = this.c;
                if (arrayList.contains(c0751xo)) {
                    AbstractC0535qo.a(c0751xo.a, c0751xo.c.G, e9.a);
                }
                break;
            default:
                E9 e10 = this.b;
                ArrayList arrayList2 = e10.b;
                C0751xo c0751xo2 = this.c;
                arrayList2.remove(c0751xo2);
                e10.c.remove(c0751xo2);
                break;
        }
    }
}
