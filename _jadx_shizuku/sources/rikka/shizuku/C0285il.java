package rikka.shizuku;

import android.os.Trace;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import androidx.recyclerview.widget.RecyclerView;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;

/* JADX INFO: renamed from: rikka.shizuku.il, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0285il {
    public final ArrayList a;
    public ArrayList b;
    public final ArrayList c;
    public final List d;
    public final int e;
    public int f;
    public C0788z g;
    public final /* synthetic */ RecyclerView h;

    public C0285il(RecyclerView recyclerView) {
        this.h = recyclerView;
        ArrayList arrayList = new ArrayList();
        this.a = arrayList;
        this.b = null;
        this.c = new ArrayList();
        this.d = Collections.unmodifiableList(arrayList);
        this.e = 2;
        this.f = 2;
    }

    public final void a(AbstractC0470ol abstractC0470ol, boolean z) {
        RecyclerView.l(abstractC0470ol);
        RecyclerView recyclerView = this.h;
        C0532ql c0532ql = recyclerView.l0;
        View view = abstractC0470ol.a;
        if (c0532ql != null) {
            S sJ = c0532ql.j();
            Hs.m(view, sJ instanceof C0501pl ? (S) ((C0501pl) sJ).e.remove(view) : null);
        }
        if (z) {
            ArrayList arrayList = recyclerView.o;
            if (arrayList.size() > 0) {
                arrayList.get(0).getClass();
                throw new ClassCastException();
            }
            Yk yk = recyclerView.m;
            if (yk != null) {
                yk.i(abstractC0470ol);
            }
            if (recyclerView.g0 != null) {
                recyclerView.g.U(abstractC0470ol);
            }
        }
        abstractC0470ol.s = null;
        abstractC0470ol.r = null;
        C0788z c0788zC = c();
        c0788zC.getClass();
        int i = abstractC0470ol.f;
        ArrayList arrayList2 = c0788zC.b(i).a;
        if (((C0255hl) ((SparseArray) c0788zC.b).get(i)).b <= arrayList2.size()) {
            AbstractC0599sr.c(view);
        } else {
            abstractC0470ol.m();
            arrayList2.add(abstractC0470ol);
        }
    }

    public final int b(int i) {
        RecyclerView recyclerView = this.h;
        if (i >= 0 && i < recyclerView.g0.b()) {
            return !recyclerView.g0.g ? i : recyclerView.e.g(i, 0);
        }
        throw new IndexOutOfBoundsException("invalid position " + i + ". State item count is " + recyclerView.g0.b() + recyclerView.z());
    }

    public final C0788z c() {
        if (this.g == null) {
            C0788z c0788z = new C0788z();
            c0788z.b = new SparseArray();
            c0788z.a = 0;
            c0788z.c = Collections.newSetFromMap(new IdentityHashMap());
            this.g = c0788z;
            d();
        }
        return this.g;
    }

    public final void d() {
        RecyclerView recyclerView;
        Yk yk;
        C0788z c0788z = this.g;
        if (c0788z == null || (yk = (recyclerView = this.h).m) == null || !recyclerView.s) {
            return;
        }
        ((Set) c0788z.c).add(yk);
    }

    public final void e(Yk yk, boolean z) {
        C0788z c0788z = this.g;
        if (c0788z == null) {
            return;
        }
        Set set = (Set) c0788z.c;
        set.remove(yk);
        if (set.size() != 0 || z) {
            return;
        }
        int i = 0;
        while (true) {
            SparseArray sparseArray = (SparseArray) c0788z.b;
            if (i >= sparseArray.size()) {
                return;
            }
            ArrayList arrayList = ((C0255hl) sparseArray.get(sparseArray.keyAt(i))).a;
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                AbstractC0599sr.c(((AbstractC0470ol) arrayList.get(i2)).a);
            }
            i++;
        }
    }

    public final void f() {
        ArrayList arrayList = this.c;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            g(size);
        }
        arrayList.clear();
        if (RecyclerView.C0) {
            Fd fd = this.h.f0;
            int[] iArr = (int[]) fd.d;
            if (iArr != null) {
                Arrays.fill(iArr, -1);
            }
            fd.c = 0;
        }
    }

    public final void g(int i) {
        int[] iArr = RecyclerView.z0;
        ArrayList arrayList = this.c;
        a((AbstractC0470ol) arrayList.get(i), true);
        arrayList.remove(i);
    }

    public final void h(View view) {
        AbstractC0470ol abstractC0470olK = RecyclerView.K(view);
        boolean zJ = abstractC0470olK.j();
        RecyclerView recyclerView = this.h;
        if (zJ) {
            recyclerView.removeDetachedView(view, false);
        }
        if (abstractC0470olK.i()) {
            abstractC0470olK.n.l(abstractC0470olK);
        } else if (abstractC0470olK.p()) {
            abstractC0470olK.j &= -33;
        }
        i(abstractC0470olK);
        if (recyclerView.L == null || abstractC0470olK.g()) {
            return;
        }
        recyclerView.L.g(abstractC0470olK);
    }

    /* JADX WARN: Code duplicated, block: B:16:0x0030  */
    /* JADX WARN: Code duplicated, block: B:41:0x007e  */
    /* JADX WARN: Code duplicated, block: B:43:0x008e  */
    /* JADX WARN: Code duplicated, block: B:45:0x0095  */
    /* JADX WARN: Code duplicated, block: B:48:0x00a0 A[LOOP:2: B:44:0x0093->B:48:0x00a0, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:75:0x00a3 A[EDGE_INSN: B:75:0x00a3->B:49:0x00a3 BREAK  A[LOOP:1: B:40:0x007c->B:47:0x009d], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:76:0x00a3 A[EDGE_INSN: B:76:0x00a3->B:49:0x00a3 BREAK  A[LOOP:1: B:40:0x007c->B:47:0x009d, LOOP_LABEL: LOOP:1: B:40:0x007c->B:47:0x009d], SYNTHETIC] */
    public final void i(AbstractC0470ol abstractC0470ol) {
        boolean z;
        boolean z2;
        int i;
        int i2;
        Fd fd;
        int i3;
        int i4;
        boolean zI = abstractC0470ol.i();
        boolean z3 = false;
        boolean z4 = true;
        RecyclerView recyclerView = this.h;
        View view = abstractC0470ol.a;
        if (zI || view.getParent() != null) {
            StringBuilder sb = new StringBuilder("Scrapped or attached views may not be recycled. isScrap:");
            sb.append(abstractC0470ol.i());
            sb.append(" isAttached:");
            sb.append(view.getParent() != null);
            sb.append(recyclerView.z());
            throw new IllegalArgumentException(sb.toString());
        }
        if (abstractC0470ol.j()) {
            throw new IllegalArgumentException("Tmp detached view should be removed from RecyclerView before it can be recycled: " + abstractC0470ol + recyclerView.z());
        }
        if (abstractC0470ol.o()) {
            throw new IllegalArgumentException("Trying to recycle an ignored view holder. You should first call stopIgnoringView(view) before calling recycle." + recyclerView.z());
        }
        if ((abstractC0470ol.j & 16) == 0) {
            WeakHashMap weakHashMap = Hs.a;
            if (view.hasTransientState()) {
                z = true;
            } else {
                z = false;
            }
        } else {
            z = false;
        }
        Yk yk = recyclerView.m;
        int[] iArr = RecyclerView.z0;
        if (abstractC0470ol.g()) {
            if (this.f <= 0 || (abstractC0470ol.j & 526) != 0) {
                z2 = false;
            } else {
                ArrayList arrayList = this.c;
                int size = arrayList.size();
                if (size >= this.f && size > 0) {
                    g(0);
                    size--;
                }
                if (RecyclerView.C0 && size > 0) {
                    Fd fd2 = recyclerView.f0;
                    int i5 = abstractC0470ol.c;
                    if (((int[]) fd2.d) != null) {
                        int i6 = fd2.c * 2;
                        int i7 = 0;
                        while (true) {
                            if (i7 >= i6) {
                                i = size - 1;
                                loop1: while (i >= 0) {
                                    i2 = ((AbstractC0470ol) arrayList.get(i)).c;
                                    fd = recyclerView.f0;
                                    if (((int[]) fd.d) != null) {
                                        break;
                                    }
                                    i3 = fd.c * 2;
                                    i4 = 0;
                                    while (true) {
                                        if (i4 < i3) {
                                            break loop1;
                                        } else if (((int[]) fd.d)[i4] == i2) {
                                            break;
                                        } else {
                                            i4 += 2;
                                        }
                                    }
                                    i--;
                                }
                                size = i + 1;
                            } else if (((int[]) fd2.d)[i7] != i5) {
                                i7 += 2;
                            }
                        }
                    } else {
                        i = size - 1;
                        loop1: while (i >= 0) {
                            i2 = ((AbstractC0470ol) arrayList.get(i)).c;
                            fd = recyclerView.f0;
                            if (((int[]) fd.d) != null) {
                                break;
                                break;
                            }
                            i3 = fd.c * 2;
                            i4 = 0;
                            while (true) {
                                if (i4 < i3) {
                                    break loop1;
                                    break loop1;
                                } else if (((int[]) fd.d)[i4] == i2) {
                                    break;
                                } else {
                                    i4 += 2;
                                }
                            }
                            i--;
                        }
                        size = i + 1;
                    }
                }
                arrayList.add(size, abstractC0470ol);
                z2 = true;
            }
            if (z2) {
                z4 = false;
            } else {
                a(abstractC0470ol, true);
            }
            z3 = z2;
        } else {
            z4 = false;
        }
        recyclerView.g.U(abstractC0470ol);
        if (z3 || z4 || !z) {
            return;
        }
        AbstractC0599sr.c(view);
        abstractC0470ol.s = null;
        abstractC0470ol.r = null;
    }

    public final void j(View view) {
        C0458o9 c0458o9;
        AbstractC0470ol abstractC0470olK = RecyclerView.K(view);
        boolean z = (abstractC0470olK.j & 12) != 0;
        RecyclerView recyclerView = this.h;
        if (!z && abstractC0470olK.k() && (c0458o9 = recyclerView.L) != null && abstractC0470olK.c().isEmpty() && c0458o9.g && !abstractC0470olK.f()) {
            if (this.b == null) {
                this.b = new ArrayList();
            }
            abstractC0470olK.n = this;
            abstractC0470olK.o = true;
            this.b.add(abstractC0470olK);
            return;
        }
        if (abstractC0470olK.f() && !abstractC0470olK.h() && !recyclerView.m.b) {
            throw new IllegalArgumentException("Called scrap view with an invalid view. Invalid views cannot be reused from scrap, they should rebound from recycler pool." + recyclerView.z());
        }
        abstractC0470olK.n = this;
        abstractC0470olK.o = false;
        this.a.add(abstractC0470olK);
    }

    /* JADX WARN: Code duplicated, block: B:110:0x01d4  */
    /* JADX WARN: Code duplicated, block: B:178:0x0327 A[EDGE_INSN: B:178:0x0327->B:179:0x0328 BREAK  A[LOOP:3: B:173:0x030f->B:177:0x0324]] */
    /* JADX WARN: Code duplicated, block: B:256:0x047f  */
    /* JADX WARN: Code duplicated, block: B:258:0x0485  */
    /* JADX WARN: Code duplicated, block: B:259:0x0493  */
    /* JADX WARN: Code duplicated, block: B:262:0x049d  */
    /* JADX WARN: Code duplicated, block: B:263:0x04a0  */
    /* JADX WARN: Code duplicated, block: B:265:0x04a3  */
    /* JADX WARN: Code duplicated, block: B:267:0x04ab  */
    /* JADX WARN: Code duplicated, block: B:270:0x04bf  */
    /* JADX WARN: Code duplicated, block: B:271:0x04d3  */
    /* JADX WARN: Code duplicated, block: B:274:0x04e0  */
    /* JADX WARN: Code duplicated, block: B:276:0x04e4  */
    /* JADX WARN: Code duplicated, block: B:279:0x04f5  */
    /* JADX WARN: Code duplicated, block: B:282:0x0500  */
    /* JADX WARN: Code duplicated, block: B:286:0x0518  */
    /* JADX WARN: Code duplicated, block: B:292:0x052d  */
    /* JADX WARN: Code duplicated, block: B:294:0x0530  */
    /* JADX WARN: Code duplicated, block: B:296:0x0537  */
    /* JADX WARN: Code duplicated, block: B:300:0x053f  */
    /* JADX WARN: Code duplicated, block: B:302:0x0547  */
    /* JADX WARN: Code duplicated, block: B:305:0x0554  */
    /* JADX WARN: Code duplicated, block: B:307:0x0558  */
    /* JADX WARN: Code duplicated, block: B:308:0x055e  */
    /* JADX WARN: Code duplicated, block: B:313:0x0571  */
    /* JADX WARN: Code duplicated, block: B:316:0x0576  */
    /* JADX WARN: Code duplicated, block: B:320:0x057f  */
    /* JADX WARN: Code duplicated, block: B:321:0x0589  */
    /* JADX WARN: Code duplicated, block: B:323:0x058f  */
    /* JADX WARN: Code duplicated, block: B:324:0x0599  */
    /* JADX WARN: Code duplicated, block: B:327:0x059f A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:329:0x05a3  */
    /* JADX WARN: Code duplicated, block: B:35:0x007d A[EDGE_INSN: B:35:0x007d->B:36:0x007e BREAK  A[LOOP:0: B:14:0x0027->B:20:0x0041]] */
    /* JADX WARN: Code duplicated, block: B:74:0x011d  */
    public final AbstractC0470ol k(int i, long j) {
        boolean z;
        AbstractC0470ol abstractC0470olF;
        boolean z2;
        long j2;
        long j3;
        boolean z3;
        Yk yk;
        boolean z4;
        long j4;
        long jM;
        long j5;
        AccessibilityManager accessibilityManager;
        boolean z5;
        boolean z6;
        boolean z7;
        C0532ql c0532ql;
        S sJ;
        C0501pl c0501pl;
        View.AccessibilityDelegate accessibilityDelegateD;
        ArrayList arrayList;
        ViewGroup.LayoutParams layoutParams;
        ViewGroup.LayoutParams layoutParams2;
        C0193fl c0193fl;
        boolean z8;
        RecyclerView recyclerViewE;
        AbstractC0470ol abstractC0470ol;
        View view;
        int iB;
        boolean z9;
        int size;
        int iG;
        boolean z10 = true;
        RecyclerView recyclerView = this.h;
        if (i < 0 || i >= recyclerView.g0.b()) {
            throw new IndexOutOfBoundsException("Invalid item position " + i + "(" + i + "). Item count:" + recyclerView.g0.b() + recyclerView.z());
        }
        C0408ml c0408ml = recyclerView.g0;
        if (c0408ml.g) {
            ArrayList arrayList2 = this.b;
            if (arrayList2 != null && (size = arrayList2.size()) != 0) {
                int i2 = 0;
                while (true) {
                    if (i2 >= size) {
                        if (recyclerView.m.b && (iG = recyclerView.e.g(i, 0)) > 0 && iG < recyclerView.m.a()) {
                            long jB = recyclerView.m.b(iG);
                            int i3 = 0;
                            while (true) {
                                if (i3 >= size) {
                                    abstractC0470olF = null;
                                    break;
                                }
                                AbstractC0470ol abstractC0470ol2 = (AbstractC0470ol) this.b.get(i3);
                                if (!abstractC0470ol2.p() && abstractC0470ol2.e == jB) {
                                    abstractC0470ol2.a(32);
                                    abstractC0470olF = abstractC0470ol2;
                                    break;
                                }
                                i3++;
                            }
                        } else {
                            abstractC0470olF = null;
                            break;
                        }
                    } else {
                        abstractC0470olF = (AbstractC0470ol) this.b.get(i2);
                        if (!abstractC0470olF.p() && abstractC0470olF.b() == i) {
                            abstractC0470olF.a(32);
                            break;
                        }
                        i2++;
                    }
                }
            } else {
                abstractC0470olF = null;
                break;
            }
            z = abstractC0470olF != null;
        } else {
            z = false;
            abstractC0470olF = null;
        }
        ArrayList arrayList3 = this.c;
        ArrayList arrayList4 = this.a;
        if (abstractC0470olF == null) {
            int size2 = arrayList4.size();
            int i4 = 0;
            while (true) {
                if (i4 >= size2) {
                    ArrayList arrayList5 = recyclerView.f.c;
                    int size3 = arrayList5.size();
                    int i5 = 0;
                    while (true) {
                        if (i5 >= size3) {
                            z2 = z10;
                            view = null;
                            break;
                        }
                        view = (View) arrayList5.get(i5);
                        AbstractC0470ol abstractC0470olK = RecyclerView.K(view);
                        z2 = z10;
                        if (abstractC0470olK.b() == i && !abstractC0470olK.f() && !abstractC0470olK.h()) {
                            break;
                        }
                        i5++;
                        z10 = z2;
                    }
                    if (view == null) {
                        int size4 = arrayList3.size();
                        int i6 = 0;
                        while (true) {
                            if (i6 >= size4) {
                                abstractC0470olF = null;
                                break;
                            }
                            AbstractC0470ol abstractC0470ol3 = (AbstractC0470ol) arrayList3.get(i6);
                            if (!abstractC0470ol3.f() && abstractC0470ol3.b() == i && !abstractC0470ol3.d()) {
                                arrayList3.remove(i6);
                                int[] iArr = RecyclerView.z0;
                                abstractC0470olF = abstractC0470ol3;
                                break;
                            }
                            i6++;
                        }
                    } else {
                        AbstractC0470ol abstractC0470olK2 = RecyclerView.K(view);
                        W5 w5 = recyclerView.f;
                        int iIndexOfChild = w5.a.a.indexOfChild(view);
                        if (iIndexOfChild < 0) {
                            throw new IllegalArgumentException("view is not a child, cannot hide " + view);
                        }
                        V5 v5 = w5.b;
                        if (!v5.d(iIndexOfChild)) {
                            throw new RuntimeException("trying to unhide a view that was not hidden" + view);
                        }
                        v5.a(iIndexOfChild);
                        w5.j(view);
                        W5 w6 = recyclerView.f;
                        int iIndexOfChild2 = w6.a.a.indexOfChild(view);
                        if (iIndexOfChild2 == -1) {
                            iB = -1;
                        } else {
                            V5 v6 = w6.b;
                            if (v6.d(iIndexOfChild2)) {
                                iB = -1;
                            } else {
                                iB = iIndexOfChild2 - v6.b(iIndexOfChild2);
                            }
                        }
                        if (iB == -1) {
                            throw new IllegalStateException("layout index should not be -1 after unhiding a view:" + abstractC0470olK2 + recyclerView.z());
                        }
                        recyclerView.f.c(iB);
                        j(view);
                        abstractC0470olK2.a(8224);
                        abstractC0470olF = abstractC0470olK2;
                        break;
                    }
                } else {
                    AbstractC0470ol abstractC0470ol4 = (AbstractC0470ol) arrayList4.get(i4);
                    if (!abstractC0470ol4.p() && abstractC0470ol4.b() == i && !abstractC0470ol4.f() && (c0408ml.g || !abstractC0470ol4.h())) {
                        abstractC0470ol4.a(32);
                        z2 = true;
                        abstractC0470olF = abstractC0470ol4;
                        break;
                    }
                    i4++;
                }
            }
            if (abstractC0470olF != null) {
                if (abstractC0470olF.h()) {
                    int[] iArr2 = RecyclerView.z0;
                    z9 = c0408ml.g;
                } else {
                    int i7 = abstractC0470olF.c;
                    if (i7 < 0 || i7 >= recyclerView.m.a()) {
                        throw new IndexOutOfBoundsException("Inconsistency detected. Invalid view holder adapter position" + abstractC0470olF + recyclerView.z());
                    }
                    if (c0408ml.g || recyclerView.m.c(abstractC0470olF.c) == abstractC0470olF.f) {
                        Yk yk2 = recyclerView.m;
                        if (!yk2.b || abstractC0470olF.e == yk2.b(abstractC0470olF.c)) {
                            z9 = z2;
                        } else {
                            z9 = false;
                        }
                    } else {
                        z9 = false;
                    }
                }
                if (z9) {
                    z = z2;
                } else {
                    abstractC0470olF.a(4);
                    if (abstractC0470olF.i()) {
                        recyclerView.removeDetachedView(abstractC0470olF.a, false);
                        abstractC0470olF.n.l(abstractC0470olF);
                    } else if (abstractC0470olF.p()) {
                        abstractC0470olF.j &= -33;
                    }
                    i(abstractC0470olF);
                    abstractC0470olF = null;
                }
            }
        } else {
            z2 = true;
        }
        if (abstractC0470olF == null) {
            int iG2 = recyclerView.e.g(i, 0);
            if (iG2 < 0 || iG2 >= recyclerView.m.a()) {
                throw new IndexOutOfBoundsException("Inconsistency detected. Invalid item position " + i + "(offset:" + iG2 + ").state:" + c0408ml.b() + recyclerView.z());
            }
            int iC = recyclerView.m.c(iG2);
            j2 = 3;
            Yk yk3 = recyclerView.m;
            if (yk3.b) {
                long jB2 = yk3.b(iG2);
                int size5 = arrayList4.size() - 1;
                while (true) {
                    if (size5 < 0) {
                        j3 = 4;
                        int size6 = arrayList3.size() - 1;
                        while (true) {
                            if (size6 >= 0) {
                                AbstractC0470ol abstractC0470ol5 = (AbstractC0470ol) arrayList3.get(size6);
                                if (abstractC0470ol5.e != jB2 || abstractC0470ol5.d()) {
                                    size6--;
                                } else {
                                    if (iC == abstractC0470ol5.f) {
                                        arrayList3.remove(size6);
                                        abstractC0470olF = abstractC0470ol5;
                                        break;
                                    }
                                    g(size6);
                                }
                            }
                            abstractC0470olF = null;
                            break;
                        }
                    }
                    j3 = 4;
                    AbstractC0470ol abstractC0470ol6 = (AbstractC0470ol) arrayList4.get(size5);
                    if (abstractC0470ol6.e == jB2 && !abstractC0470ol6.p()) {
                        if (iC == abstractC0470ol6.f) {
                            abstractC0470ol6.a(32);
                            if (abstractC0470ol6.h() && !c0408ml.g) {
                                abstractC0470ol6.j = (abstractC0470ol6.j & (-15)) | 2;
                            }
                            abstractC0470olF = abstractC0470ol6;
                            break;
                        }
                        arrayList4.remove(size5);
                        View view2 = abstractC0470ol6.a;
                        recyclerView.removeDetachedView(view2, false);
                        AbstractC0470ol abstractC0470olK3 = RecyclerView.K(view2);
                        abstractC0470olK3.n = null;
                        abstractC0470olK3.o = false;
                        abstractC0470olK3.j &= -33;
                        i(abstractC0470olK3);
                    }
                    size5--;
                }
                if (abstractC0470olF != null) {
                    abstractC0470olF.c = iG2;
                    z = z2;
                }
            } else {
                j3 = 4;
            }
            if (abstractC0470olF == null) {
                int[] iArr3 = RecyclerView.z0;
                C0255hl c0255hl = (C0255hl) ((SparseArray) c().b).get(iC);
                if (c0255hl == null) {
                    abstractC0470ol = null;
                    break;
                }
                ArrayList arrayList6 = c0255hl.a;
                if (!arrayList6.isEmpty()) {
                    int size7 = arrayList6.size() - 1;
                    while (true) {
                        if (size7 < 0) {
                            abstractC0470ol = null;
                            break;
                        }
                        if (!((AbstractC0470ol) arrayList6.get(size7)).d()) {
                            abstractC0470ol = (AbstractC0470ol) arrayList6.remove(size7);
                            break;
                        }
                        size7--;
                    }
                } else {
                    abstractC0470ol = null;
                    break;
                }
                if (abstractC0470ol != null) {
                    abstractC0470ol.m();
                    int[] iArr4 = RecyclerView.z0;
                }
                abstractC0470olF = abstractC0470ol;
            }
            if (abstractC0470olF == null) {
                long jM2 = RecyclerView.M();
                if (j != Long.MAX_VALUE) {
                    long j6 = this.g.b(iC).c;
                    if (!((j6 == 0 || j6 + jM2 < j) ? z2 : false)) {
                        return null;
                    }
                }
                Yk yk4 = recyclerView.m;
                yk4.getClass();
                try {
                    if (AbstractC0537qq.a()) {
                        Trace.beginSection(String.format("RV onCreateViewHolder type=0x%X", Integer.valueOf(iC)));
                    }
                    abstractC0470olF = yk4.f(recyclerView, iC);
                    if (abstractC0470olF.a.getParent() != null) {
                        throw new IllegalStateException("ViewHolder views must not be attached when created. Ensure that you are not passing 'true' to the attachToRoot parameter of LayoutInflater.inflate(..., boolean attachToRoot)");
                    }
                    abstractC0470olF.f = iC;
                    Trace.endSection();
                    if (RecyclerView.C0 && (recyclerViewE = RecyclerView.E(abstractC0470olF.a)) != null) {
                        abstractC0470olF.b = new WeakReference(recyclerViewE);
                    }
                    long jM3 = RecyclerView.M() - jM2;
                    C0255hl c0255hlB = this.g.b(iC);
                    long j7 = c0255hlB.c;
                    if (j7 != 0) {
                        jM3 = (jM3 / j3) + ((j7 / j3) * 3);
                    }
                    c0255hlB.c = jM3;
                } catch (Throwable th) {
                    Trace.endSection();
                    throw th;
                }
            }
        } else {
            j2 = 3;
            j3 = 4;
        }
        if (z && !c0408ml.g) {
            int i8 = abstractC0470olF.j;
            if ((i8 & 8192) != 0 ? z2 : false) {
                abstractC0470olF.j = i8 & (-8193);
                if (c0408ml.j) {
                    C0458o9.c(abstractC0470olF);
                    C0458o9 c0458o9 = recyclerView.L;
                    abstractC0470olF.c();
                    c0458o9.getClass();
                    Yi yi = new Yi();
                    yi.a(abstractC0470olF);
                    recyclerView.Z(abstractC0470olF, yi);
                }
            }
        }
        boolean z11 = c0408ml.g;
        View view3 = abstractC0470olF.a;
        if (!z11 || !abstractC0470olF.e()) {
            if (abstractC0470olF.e()) {
                if (((abstractC0470olF.j & 2) != 0 ? z2 : false) || abstractC0470olF.f()) {
                }
                layoutParams2 = view3.getLayoutParams();
                if (layoutParams2 == null) {
                    c0193fl = (C0193fl) recyclerView.generateDefaultLayoutParams();
                    view3.setLayoutParams(c0193fl);
                } else if (recyclerView.checkLayoutParams(layoutParams2)) {
                    c0193fl = (C0193fl) layoutParams2;
                } else {
                    c0193fl = (C0193fl) recyclerView.generateLayoutParams(layoutParams2);
                    view3.setLayoutParams(c0193fl);
                }
                c0193fl.a = abstractC0470olF;
                if (z || !z7) {
                    z8 = false;
                } else {
                    z8 = z6;
                }
                c0193fl.d = z8;
                return abstractC0470olF;
            }
            int[] iArr5 = RecyclerView.z0;
            int iG3 = recyclerView.e.g(i, 0);
            S s = null;
            abstractC0470olF.s = null;
            abstractC0470olF.r = recyclerView;
            int i9 = abstractC0470olF.f;
            long jM4 = RecyclerView.M();
            if (j != Long.MAX_VALUE) {
                long j8 = this.g.b(i9).d;
                if (j8 == 0 || j8 + jM4 < j) {
                    if (abstractC0470olF.j()) {
                        recyclerView.attachViewToParent(view3, recyclerView.getChildCount(), view3.getLayoutParams());
                        z3 = z2;
                    } else {
                        z3 = false;
                    }
                    yk = recyclerView.m;
                    yk.getClass();
                    if (abstractC0470olF.s == null) {
                        z4 = z2;
                    } else {
                        z4 = false;
                    }
                    if (z4) {
                        abstractC0470olF.c = iG3;
                        j4 = jM4;
                        if (yk.b) {
                            abstractC0470olF.e = yk.b(iG3);
                        }
                        abstractC0470olF.j = (abstractC0470olF.j & (-520)) | 1;
                        if (AbstractC0537qq.a()) {
                            Trace.beginSection(String.format("RV onBindViewHolder type=0x%X", Integer.valueOf(abstractC0470olF.f)));
                        }
                    } else {
                        j4 = jM4;
                    }
                    abstractC0470olF.s = yk;
                    yk.e(abstractC0470olF, iG3, abstractC0470olF.c());
                    if (z4) {
                        arrayList = abstractC0470olF.k;
                        if (arrayList != null) {
                            arrayList.clear();
                        }
                        abstractC0470olF.j &= -1025;
                        layoutParams = view3.getLayoutParams();
                        if (layoutParams instanceof C0193fl) {
                            ((C0193fl) layoutParams).c = z2;
                        }
                        Trace.endSection();
                    }
                    if (z3) {
                        recyclerView.detachViewFromParent(view3);
                    }
                    jM = RecyclerView.M() - j4;
                    C0255hl c0255hlB2 = this.g.b(abstractC0470olF.f);
                    j5 = c0255hlB2.d;
                    if (j5 != 0) {
                        jM = (jM / j3) + ((j5 / j3) * j2);
                    }
                    c0255hlB2.d = jM;
                    accessibilityManager = recyclerView.B;
                    if (accessibilityManager == null && accessibilityManager.isEnabled()) {
                        z5 = true;
                    } else {
                        z5 = false;
                    }
                    if (z5) {
                        z6 = true;
                        if (view3.getImportantForAccessibility() == 0) {
                            view3.setImportantForAccessibility(1);
                        }
                        c0532ql = recyclerView.l0;
                        if (c0532ql != null) {
                            sJ = c0532ql.j();
                            if (sJ instanceof C0501pl) {
                                c0501pl = (C0501pl) sJ;
                                c0501pl.getClass();
                                accessibilityDelegateD = Hs.d(view3);
                                if (accessibilityDelegateD != null) {
                                    if (accessibilityDelegateD instanceof Q) {
                                        s = ((Q) accessibilityDelegateD).a;
                                    } else {
                                        s = new S(accessibilityDelegateD);
                                    }
                                }
                                if (s != null && s != c0501pl) {
                                    c0501pl.e.put(view3, s);
                                }
                            }
                            Hs.m(view3, sJ);
                        }
                    } else {
                        z6 = true;
                    }
                    if (c0408ml.g) {
                        abstractC0470olF.g = i;
                    }
                    z7 = z6;
                } else {
                    z7 = false;
                    z6 = z2;
                }
            } else {
                if (abstractC0470olF.j()) {
                    recyclerView.attachViewToParent(view3, recyclerView.getChildCount(), view3.getLayoutParams());
                    z3 = z2;
                } else {
                    z3 = false;
                }
                yk = recyclerView.m;
                yk.getClass();
                if (abstractC0470olF.s == null) {
                    z4 = z2;
                } else {
                    z4 = false;
                }
                if (z4) {
                    abstractC0470olF.c = iG3;
                    j4 = jM4;
                    if (yk.b) {
                        abstractC0470olF.e = yk.b(iG3);
                    }
                    abstractC0470olF.j = (abstractC0470olF.j & (-520)) | 1;
                    if (AbstractC0537qq.a()) {
                        Trace.beginSection(String.format("RV onBindViewHolder type=0x%X", Integer.valueOf(abstractC0470olF.f)));
                    }
                } else {
                    j4 = jM4;
                }
                abstractC0470olF.s = yk;
                yk.e(abstractC0470olF, iG3, abstractC0470olF.c());
                if (z4) {
                    arrayList = abstractC0470olF.k;
                    if (arrayList != null) {
                        arrayList.clear();
                    }
                    abstractC0470olF.j &= -1025;
                    layoutParams = view3.getLayoutParams();
                    if (layoutParams instanceof C0193fl) {
                        ((C0193fl) layoutParams).c = z2;
                    }
                    Trace.endSection();
                }
                if (z3) {
                    recyclerView.detachViewFromParent(view3);
                }
                jM = RecyclerView.M() - j4;
                C0255hl c0255hlB3 = this.g.b(abstractC0470olF.f);
                j5 = c0255hlB3.d;
                if (j5 != 0) {
                    jM = (jM / j3) + ((j5 / j3) * j2);
                }
                c0255hlB3.d = jM;
                accessibilityManager = recyclerView.B;
                if (accessibilityManager == null) {
                    z5 = false;
                } else {
                    z5 = false;
                }
                if (z5) {
                    z6 = true;
                    if (view3.getImportantForAccessibility() == 0) {
                        view3.setImportantForAccessibility(1);
                    }
                    c0532ql = recyclerView.l0;
                    if (c0532ql != null) {
                        sJ = c0532ql.j();
                        if (sJ instanceof C0501pl) {
                            c0501pl = (C0501pl) sJ;
                            c0501pl.getClass();
                            accessibilityDelegateD = Hs.d(view3);
                            if (accessibilityDelegateD != null) {
                                if (accessibilityDelegateD instanceof Q) {
                                    s = ((Q) accessibilityDelegateD).a;
                                } else {
                                    s = new S(accessibilityDelegateD);
                                }
                            }
                            if (s != null) {
                                c0501pl.e.put(view3, s);
                            }
                        }
                        Hs.m(view3, sJ);
                    }
                } else {
                    z6 = true;
                }
                if (c0408ml.g) {
                    abstractC0470olF.g = i;
                }
                z7 = z6;
            }
            layoutParams2 = view3.getLayoutParams();
            if (layoutParams2 == null) {
                c0193fl = (C0193fl) recyclerView.generateDefaultLayoutParams();
                view3.setLayoutParams(c0193fl);
            } else if (recyclerView.checkLayoutParams(layoutParams2)) {
                c0193fl = (C0193fl) recyclerView.generateLayoutParams(layoutParams2);
                view3.setLayoutParams(c0193fl);
            } else {
                c0193fl = (C0193fl) layoutParams2;
            }
            c0193fl.a = abstractC0470olF;
            if (z) {
                z8 = false;
            } else {
                z8 = false;
            }
            c0193fl.d = z8;
            return abstractC0470olF;
        }
        abstractC0470olF.g = i;
        z6 = z2;
        z7 = false;
        layoutParams2 = view3.getLayoutParams();
        if (layoutParams2 == null) {
            c0193fl = (C0193fl) recyclerView.generateDefaultLayoutParams();
            view3.setLayoutParams(c0193fl);
        } else if (recyclerView.checkLayoutParams(layoutParams2)) {
            c0193fl = (C0193fl) recyclerView.generateLayoutParams(layoutParams2);
            view3.setLayoutParams(c0193fl);
        } else {
            c0193fl = (C0193fl) layoutParams2;
        }
        c0193fl.a = abstractC0470olF;
        if (z) {
            z8 = false;
        } else {
            z8 = false;
        }
        c0193fl.d = z8;
        return abstractC0470olF;
    }

    public final void l(AbstractC0470ol abstractC0470ol) {
        if (abstractC0470ol.o) {
            this.b.remove(abstractC0470ol);
        } else {
            this.a.remove(abstractC0470ol);
        }
        abstractC0470ol.n = null;
        abstractC0470ol.o = false;
        abstractC0470ol.j &= -33;
    }

    public final void m() {
        AbstractC0162el abstractC0162el = this.h.n;
        this.f = this.e + (abstractC0162el != null ? abstractC0162el.j : 0);
        ArrayList arrayList = this.c;
        for (int size = arrayList.size() - 1; size >= 0 && arrayList.size() > this.f; size--) {
            g(size);
        }
    }
}
