package rikka.shizuku;

import android.view.View;
import android.view.ViewTreeObserver;
import java.util.ArrayList;
import java.util.WeakHashMap;
import rikka.html.text.style.BulletSpan;

/* JADX INFO: loaded from: classes.dex */
public final class Y2 implements ViewTreeObserver.OnGlobalLayoutListener {
    public final /* synthetic */ int a;
    public final /* synthetic */ Object b;

    public /* synthetic */ Y2(int i, Object obj) {
        this.a = i;
        this.b = obj;
    }

    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
    public final void onGlobalLayout() {
        Object obj = this.b;
        switch (this.a) {
            case 0:
                C0297j3 c0297j3 = (C0297j3) obj;
                if (!c0297j3.f.a()) {
                    c0297j3.f.d(AbstractC0021a3.b(c0297j3), AbstractC0021a3.a(c0297j3));
                }
                ViewTreeObserver viewTreeObserver = c0297j3.getViewTreeObserver();
                if (viewTreeObserver != null) {
                    Z2.a(viewTreeObserver, this);
                }
                break;
            case 1:
                C0206g3 c0206g3 = (C0206g3) obj;
                C0297j3 c0297j4 = c0206g3.G;
                c0206g3.getClass();
                WeakHashMap weakHashMap = Hs.a;
                if (c0297j4.isAttachedToWindow() && c0297j4.getGlobalVisibleRect(c0206g3.E)) {
                    c0206g3.q();
                    c0206g3.e();
                } else {
                    c0206g3.dismiss();
                }
                break;
            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                G5 g5 = (G5) obj;
                if (g5.a()) {
                    ArrayList arrayList = g5.h;
                    if (arrayList.size() > 0) {
                        int i = 0;
                        if (!((F5) arrayList.get(0)).a.y) {
                            View view = g5.o;
                            if (view != null && view.isShown()) {
                                int size = arrayList.size();
                                while (i < size) {
                                    Object obj2 = arrayList.get(i);
                                    i++;
                                    ((F5) obj2).a.e();
                                }
                            } else {
                                g5.dismiss();
                            }
                        }
                    }
                }
                break;
            default:
                No no = (No) obj;
                if (no.a()) {
                    C0621ti c0621ti = no.h;
                    if (!c0621ti.y) {
                        View view2 = no.m;
                        if (view2 != null && view2.isShown()) {
                            c0621ti.e();
                        } else {
                            no.dismiss();
                        }
                    }
                }
                break;
        }
    }
}
