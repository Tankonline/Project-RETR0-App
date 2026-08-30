package rikka.shizuku;

import android.view.ViewGroup;
import java.util.WeakHashMap;

/* JADX INFO: renamed from: rikka.shizuku.w2, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class RunnableC0698w2 implements Runnable {
    public final /* synthetic */ int a;
    public final /* synthetic */ I2 b;

    public /* synthetic */ RunnableC0698w2(I2 i2, int i) {
        this.a = i;
        this.b = i2;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x002a  */
    @Override // java.lang.Runnable
    public final void run() {
        boolean z;
        ViewGroup viewGroup;
        I2 i2 = this.b;
        switch (this.a) {
            case 0:
                if ((1 & i2.Y) != 0) {
                    i2.x(0);
                }
                if ((i2.Y & 4096) != 0) {
                    i2.x(108);
                }
                i2.X = false;
                i2.Y = 0;
                break;
            default:
                i2.w.showAtLocation(i2.v, 55, 0, 0);
                C0109ct c0109ct = i2.y;
                if (c0109ct != null) {
                    c0109ct.b();
                }
                if (i2.z && (viewGroup = i2.A) != null) {
                    WeakHashMap weakHashMap = Hs.a;
                    z = viewGroup.isLaidOut();
                }
                if (!z) {
                    i2.v.setAlpha(1.0f);
                    i2.v.setVisibility(0);
                } else {
                    i2.v.setAlpha(0.0f);
                    C0109ct c0109ctA = Hs.a(i2.v);
                    c0109ctA.a(1.0f);
                    i2.y = c0109ctA;
                    c0109ctA.d(new C0760y2(0, this));
                }
                break;
        }
    }
}
