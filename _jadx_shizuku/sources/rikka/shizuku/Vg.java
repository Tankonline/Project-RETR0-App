package rikka.shizuku;

import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public final class Vg implements Runnable {
    public final /* synthetic */ int a;
    public final /* synthetic */ Zg b;

    public /* synthetic */ Vg(Zg zg, int i) {
        this.a = i;
        this.b = zg;
    }

    @Override // java.lang.Runnable
    public final void run() {
        Zg zg = this.b;
        switch (this.a) {
            case 0:
                C0490pa c0490pa = zg.c;
                if (c0490pa != null) {
                    c0490pa.h = true;
                    c0490pa.requestLayout();
                }
                break;
            default:
                C0490pa c0490pa2 = zg.c;
                if (c0490pa2 != null) {
                    WeakHashMap weakHashMap = Hs.a;
                    if (c0490pa2.isAttachedToWindow() && zg.c.getCount() > zg.c.getChildCount() && zg.c.getChildCount() <= zg.m) {
                        zg.z.setInputMethodMode(2);
                        zg.e();
                        break;
                    }
                }
                break;
        }
    }
}
