package rikka.shizuku;

import android.view.View;
import android.view.ViewPropertyAnimator;
import java.util.ArrayList;

/* JADX INFO: renamed from: rikka.shizuku.i9, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class RunnableC0274i9 implements Runnable {
    public final /* synthetic */ int a;
    public final /* synthetic */ ArrayList b;
    public final /* synthetic */ C0458o9 c;

    public /* synthetic */ RunnableC0274i9(C0458o9 c0458o9, ArrayList arrayList, int i) {
        this.a = i;
        this.c = c0458o9;
        this.b = arrayList;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.a) {
            case 0:
                ArrayList arrayList = this.b;
                int size = arrayList.size();
                int i = 0;
                while (true) {
                    C0458o9 c0458o9 = this.c;
                    if (i >= size) {
                        arrayList.clear();
                        c0458o9.m.remove(arrayList);
                    } else {
                        Object obj = arrayList.get(i);
                        i++;
                        C0427n9 c0427n9 = (C0427n9) obj;
                        AbstractC0470ol abstractC0470ol = c0427n9.a;
                        c0458o9.getClass();
                        View view = abstractC0470ol.a;
                        int i2 = c0427n9.d - c0427n9.b;
                        int i3 = c0427n9.e - c0427n9.c;
                        if (i2 != 0) {
                            view.animate().translationX(0.0f);
                        }
                        if (i3 != 0) {
                            view.animate().translationY(0.0f);
                        }
                        ViewPropertyAnimator viewPropertyAnimatorAnimate = view.animate();
                        c0458o9.p.add(abstractC0470ol);
                        viewPropertyAnimatorAnimate.setDuration(c0458o9.e).setListener(new C0334k9(c0458o9, abstractC0470ol, i2, view, i3, viewPropertyAnimatorAnimate)).start();
                    }
                    break;
                }
                break;
            case 1:
                ArrayList arrayList2 = this.b;
                int size2 = arrayList2.size();
                int i4 = 0;
                while (true) {
                    C0458o9 c0458o10 = this.c;
                    if (i4 >= size2) {
                        arrayList2.clear();
                        c0458o10.n.remove(arrayList2);
                        break;
                    } else {
                        Object obj2 = arrayList2.get(i4);
                        i4++;
                        C0396m9 c0396m9 = (C0396m9) obj2;
                        c0458o10.getClass();
                        AbstractC0470ol abstractC0470ol2 = c0396m9.a;
                        View view2 = abstractC0470ol2 == null ? null : abstractC0470ol2.a;
                        AbstractC0470ol abstractC0470ol3 = c0396m9.b;
                        View view3 = abstractC0470ol3 != null ? abstractC0470ol3.a : null;
                        ArrayList arrayList3 = c0458o10.r;
                        long j = c0458o10.f;
                        if (view2 != null) {
                            ViewPropertyAnimator duration = view2.animate().setDuration(j);
                            arrayList3.add(c0396m9.a);
                            duration.translationX(c0396m9.e - c0396m9.c);
                            duration.translationY(c0396m9.f - c0396m9.d);
                            duration.alpha(0.0f).setListener(new C0365l9(c0458o10, c0396m9, duration, view2, 0)).start();
                        }
                        if (view3 != null) {
                            ViewPropertyAnimator viewPropertyAnimatorAnimate2 = view3.animate();
                            arrayList3.add(c0396m9.b);
                            viewPropertyAnimatorAnimate2.translationX(0.0f).translationY(0.0f).setDuration(j).alpha(1.0f).setListener(new C0365l9(c0458o10, c0396m9, viewPropertyAnimatorAnimate2, view3, 1)).start();
                        }
                    }
                }
                break;
            default:
                ArrayList arrayList4 = this.b;
                int size3 = arrayList4.size();
                int i5 = 0;
                while (true) {
                    C0458o9 c0458o11 = this.c;
                    if (i5 >= size3) {
                        arrayList4.clear();
                        c0458o11.l.remove(arrayList4);
                    } else {
                        Object obj3 = arrayList4.get(i5);
                        i5++;
                        AbstractC0470ol abstractC0470ol4 = (AbstractC0470ol) obj3;
                        c0458o11.getClass();
                        View view4 = abstractC0470ol4.a;
                        ViewPropertyAnimator viewPropertyAnimatorAnimate3 = view4.animate();
                        c0458o11.o.add(abstractC0470ol4);
                        viewPropertyAnimatorAnimate3.alpha(1.0f).setDuration(c0458o11.c).setListener(new C0303j9(c0458o11, abstractC0470ol4, view4, viewPropertyAnimatorAnimate3)).start();
                    }
                    break;
                }
                break;
        }
    }
}
