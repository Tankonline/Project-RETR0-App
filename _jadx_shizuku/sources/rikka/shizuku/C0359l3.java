package rikka.shizuku;

import android.graphics.Typeface;
import android.os.Build;
import android.widget.TextView;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

/* JADX INFO: renamed from: rikka.shizuku.l3, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0359l3 extends X8 {
    public final /* synthetic */ int s;
    public final /* synthetic */ int t;
    public final /* synthetic */ WeakReference u;
    public final /* synthetic */ C0544r3 v;

    public C0359l3(C0544r3 c0544r3, int i, int i2, WeakReference weakReference) {
        this.v = c0544r3;
        this.s = i;
        this.t = i2;
        this.u = weakReference;
    }

    @Override // rikka.shizuku.X8
    public final void L(Typeface typeface) {
        int i;
        if (Build.VERSION.SDK_INT >= 28 && (i = this.s) != -1) {
            typeface = AbstractC0514q3.a(typeface, i, (this.t & 2) != 0);
        }
        C0544r3 c0544r3 = this.v;
        if (c0544r3.l) {
            c0544r3.k = typeface;
            TextView textView = (TextView) this.u.get();
            if (textView != null) {
                WeakHashMap weakHashMap = Hs.a;
                if (textView.isAttachedToWindow()) {
                    textView.post(new RunnableC0390m3(textView, typeface, c0544r3.i));
                } else {
                    textView.setTypeface(typeface, c0544r3.i);
                }
            }
        }
    }

    @Override // rikka.shizuku.X8
    public final void K(int i) {
    }
}
