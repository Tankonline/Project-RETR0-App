package rikka.shizuku;

import android.view.View;
import android.view.ViewPropertyAnimator;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public final class Vk implements Runnable {
    public final /* synthetic */ int a;
    public final /* synthetic */ RecyclerView b;

    public /* synthetic */ Vk(RecyclerView recyclerView, int i) {
        this.a = i;
        this.b = recyclerView;
    }

    @Override // java.lang.Runnable
    public final void run() {
        RecyclerView recyclerView;
        RecyclerView recyclerView2;
        RecyclerView recyclerView3 = this.b;
        int i = 1;
        switch (this.a) {
            case 0:
                if (recyclerView3.u && !recyclerView3.isLayoutRequested()) {
                    if (!recyclerView3.s) {
                        recyclerView3.requestLayout();
                    } else if (!recyclerView3.x) {
                        recyclerView3.p();
                    } else {
                        recyclerView3.w = true;
                    }
                    break;
                }
                break;
            default:
                C0458o9 c0458o9 = recyclerView3.L;
                if (c0458o9 != null) {
                    ArrayList arrayList = c0458o9.h;
                    boolean zIsEmpty = arrayList.isEmpty();
                    ArrayList arrayList2 = c0458o9.j;
                    boolean zIsEmpty2 = arrayList2.isEmpty();
                    ArrayList arrayList3 = c0458o9.k;
                    boolean zIsEmpty3 = arrayList3.isEmpty();
                    ArrayList arrayList4 = c0458o9.i;
                    boolean zIsEmpty4 = arrayList4.isEmpty();
                    if (zIsEmpty && zIsEmpty2 && zIsEmpty4 && zIsEmpty3) {
                        recyclerView2 = recyclerView3;
                    } else {
                        int size = arrayList.size();
                        recyclerView2 = recyclerView3;
                        int i2 = 0;
                        while (true) {
                            long j = c0458o9.d;
                            if (i2 < size) {
                                Object obj = arrayList.get(i2);
                                i2 += i;
                                AbstractC0470ol abstractC0470ol = (AbstractC0470ol) obj;
                                View view = abstractC0470ol.a;
                                ViewPropertyAnimator viewPropertyAnimatorAnimate = view.animate();
                                c0458o9.q.add(abstractC0470ol);
                                viewPropertyAnimatorAnimate.setDuration(j).alpha(0.0f).setListener(new C0303j9(c0458o9, abstractC0470ol, viewPropertyAnimatorAnimate, view)).start();
                                arrayList = arrayList;
                                i = 1;
                            } else {
                                arrayList.clear();
                                if (!zIsEmpty2) {
                                    ArrayList arrayList5 = new ArrayList();
                                    arrayList5.addAll(arrayList2);
                                    c0458o9.m.add(arrayList5);
                                    arrayList2.clear();
                                    RunnableC0274i9 runnableC0274i9 = new RunnableC0274i9(c0458o9, arrayList5, 0);
                                    if (zIsEmpty) {
                                        runnableC0274i9.run();
                                    } else {
                                        View view2 = ((C0427n9) arrayList5.get(0)).a.a;
                                        WeakHashMap weakHashMap = Hs.a;
                                        view2.postOnAnimationDelayed(runnableC0274i9, j);
                                    }
                                }
                                if (!zIsEmpty3) {
                                    ArrayList arrayList6 = new ArrayList();
                                    arrayList6.addAll(arrayList3);
                                    c0458o9.n.add(arrayList6);
                                    arrayList3.clear();
                                    RunnableC0274i9 runnableC0274i10 = new RunnableC0274i9(c0458o9, arrayList6, 1);
                                    if (zIsEmpty) {
                                        runnableC0274i10.run();
                                    } else {
                                        View view3 = ((C0396m9) arrayList6.get(0)).a.a;
                                        WeakHashMap weakHashMap2 = Hs.a;
                                        view3.postOnAnimationDelayed(runnableC0274i10, j);
                                    }
                                }
                                if (!zIsEmpty4) {
                                    ArrayList arrayList7 = new ArrayList();
                                    arrayList7.addAll(arrayList4);
                                    c0458o9.l.add(arrayList7);
                                    arrayList4.clear();
                                    RunnableC0274i9 runnableC0274i11 = new RunnableC0274i9(c0458o9, arrayList7, 2);
                                    if (zIsEmpty && zIsEmpty2 && zIsEmpty3) {
                                        runnableC0274i11.run();
                                    } else {
                                        if (zIsEmpty) {
                                            j = 0;
                                        }
                                        long jMax = Math.max(!zIsEmpty2 ? c0458o9.e : 0L, zIsEmpty3 ? 0L : c0458o9.f) + j;
                                        View view4 = ((AbstractC0470ol) arrayList7.get(0)).a;
                                        WeakHashMap weakHashMap3 = Hs.a;
                                        view4.postOnAnimationDelayed(runnableC0274i11, jMax);
                                    }
                                }
                            }
                        }
                    }
                    recyclerView = recyclerView2;
                } else {
                    recyclerView = recyclerView3;
                }
                recyclerView.k0 = false;
                break;
        }
    }
}
