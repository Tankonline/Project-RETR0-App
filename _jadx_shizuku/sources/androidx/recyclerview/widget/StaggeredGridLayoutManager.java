package androidx.recyclerview.widget;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.WeakHashMap;
import rikka.shizuku.AbstractC0162el;
import rikka.shizuku.AbstractC0599sr;
import rikka.shizuku.C0132dl;
import rikka.shizuku.C0141e0;
import rikka.shizuku.C0193fl;
import rikka.shizuku.C0250hg;
import rikka.shizuku.C0285il;
import rikka.shizuku.C0408ml;
import rikka.shizuku.C0682vh;
import rikka.shizuku.Fd;
import rikka.shizuku.Ho;
import rikka.shizuku.Hs;
import rikka.shizuku.InterfaceC0377ll;
import rikka.shizuku.Io;
import rikka.shizuku.Ja;
import rikka.shizuku.Jg;
import rikka.shizuku.Jo;
import rikka.shizuku.Ko;
import rikka.shizuku.R2;
import rikka.shizuku.RunnableC0607t4;
import rikka.shizuku.Ys;

/* JADX INFO: loaded from: classes.dex */
public class StaggeredGridLayoutManager extends AbstractC0162el implements InterfaceC0377ll {
    public final R2 B;
    public final int C;
    public boolean D;
    public boolean E;
    public Ko F;
    public final Rect G;
    public final Ho H;
    public final boolean I;
    public int[] J;
    public final RunnableC0607t4 K;
    public final int p;
    public final C0682vh[] q;
    public final Ja r;
    public final Ja s;
    public final int t;
    public int u;
    public final C0250hg v;
    public boolean w;
    public final BitSet y;
    public boolean x = false;
    public int z = -1;
    public int A = Integer.MIN_VALUE;

    public StaggeredGridLayoutManager(Context context, AttributeSet attributeSet, int i, int i2) {
        this.p = -1;
        this.w = false;
        R2 r2 = new R2(22, false);
        this.B = r2;
        this.C = 2;
        this.G = new Rect();
        this.H = new Ho(this);
        this.I = true;
        this.K = new RunnableC0607t4(14, this);
        C0132dl c0132dlH = AbstractC0162el.H(context, attributeSet, i, i2);
        int i3 = c0132dlH.a;
        if (i3 != 0 && i3 != 1) {
            throw new IllegalArgumentException("invalid orientation.");
        }
        c(null);
        if (i3 != this.t) {
            this.t = i3;
            Ja ja = this.r;
            this.r = this.s;
            this.s = ja;
            o0();
        }
        int i4 = c0132dlH.b;
        c(null);
        if (i4 != this.p) {
            r2.e();
            o0();
            this.p = i4;
            this.y = new BitSet(this.p);
            this.q = new C0682vh[this.p];
            for (int i5 = 0; i5 < this.p; i5++) {
                this.q[i5] = new C0682vh(this, i5);
            }
            o0();
        }
        boolean z = c0132dlH.c;
        c(null);
        Ko ko = this.F;
        if (ko != null && ko.h != z) {
            ko.h = z;
        }
        this.w = z;
        o0();
        C0250hg c0250hg = new C0250hg();
        c0250hg.a = true;
        c0250hg.f = 0;
        c0250hg.g = 0;
        this.v = c0250hg;
        this.r = Ja.a(this, this.t);
        this.s = Ja.a(this, 1 - this.t);
    }

    public static int f1(int i, int i2, int i3) {
        int mode;
        return (!(i2 == 0 && i3 == 0) && ((mode = View.MeasureSpec.getMode(i)) == Integer.MIN_VALUE || mode == 1073741824)) ? View.MeasureSpec.makeMeasureSpec(Math.max(0, (View.MeasureSpec.getSize(i) - i2) - i3), mode) : i;
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final void A0(RecyclerView recyclerView, int i) {
        Jg jg = new Jg(recyclerView.getContext());
        jg.a = i;
        B0(jg);
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final boolean C0() {
        return this.F == null;
    }

    public final boolean D0() {
        int iM0;
        if (v() != 0 && this.C != 0 && this.g) {
            if (this.x) {
                iM0 = N0();
                M0();
            } else {
                iM0 = M0();
                N0();
            }
            R2 r2 = this.B;
            if (iM0 == 0 && R0() != null) {
                r2.e();
                this.f = true;
                o0();
                return true;
            }
        }
        return false;
    }

    public final int E0(C0408ml c0408ml) {
        if (v() == 0) {
            return 0;
        }
        Ja ja = this.r;
        boolean z = !this.I;
        return AbstractC0599sr.e(c0408ml, ja, J0(z), I0(z), this, this.I);
    }

    public final int F0(C0408ml c0408ml) {
        if (v() == 0) {
            return 0;
        }
        Ja ja = this.r;
        boolean z = !this.I;
        return AbstractC0599sr.f(c0408ml, ja, J0(z), I0(z), this, this.I, this.x);
    }

    public final int G0(C0408ml c0408ml) {
        if (v() == 0) {
            return 0;
        }
        Ja ja = this.r;
        boolean z = !this.I;
        return AbstractC0599sr.g(c0408ml, ja, J0(z), I0(z), this, this.I);
    }

    /* JADX WARN: Type inference failed for: r6v20 */
    /* JADX WARN: Type inference failed for: r6v3 */
    /* JADX WARN: Type inference failed for: r6v4, types: [boolean, int] */
    public final int H0(C0285il c0285il, C0250hg c0250hg, C0408ml c0408ml) {
        C0682vh c0682vh;
        ?? r6;
        int i;
        int i2;
        int iC;
        int iK;
        int iC2;
        int i3;
        int i4;
        int i5;
        int i6 = 0;
        int i7 = 1;
        this.y.set(0, this.p, true);
        C0250hg c0250hg2 = this.v;
        int i8 = c0250hg2.i ? c0250hg.e == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE : c0250hg.e == 1 ? c0250hg.g + c0250hg.b : c0250hg.f - c0250hg.b;
        int i9 = c0250hg.e;
        for (int i10 = 0; i10 < this.p; i10++) {
            if (!((ArrayList) this.q[i10].f).isEmpty()) {
                e1(this.q[i10], i9, i8);
            }
        }
        int iG = this.x ? this.r.g() : this.r.k();
        boolean z = false;
        while (true) {
            int i11 = c0250hg.c;
            if (((i11 < 0 || i11 >= c0408ml.b()) ? i6 : i7) == 0 || (!c0250hg2.i && this.y.isEmpty())) {
                break;
            }
            View view = c0285il.k(c0250hg.c, Long.MAX_VALUE).a;
            c0250hg.c += c0250hg.d;
            Io io = (Io) view.getLayoutParams();
            int iB = io.a.b();
            R2 r2 = this.B;
            int[] iArr = (int[]) r2.b;
            int i12 = (iArr == null || iB >= iArr.length) ? -1 : iArr[iB];
            if (i12 == -1) {
                if (V0(c0250hg.e)) {
                    i5 = this.p - i7;
                    i4 = -1;
                    i3 = -1;
                } else {
                    i3 = i7;
                    i4 = this.p;
                    i5 = i6;
                }
                C0682vh c0682vh2 = null;
                if (c0250hg.e == i7) {
                    int iK2 = this.r.k();
                    int i13 = Integer.MAX_VALUE;
                    while (i5 != i4) {
                        C0682vh c0682vh3 = this.q[i5];
                        int iG2 = c0682vh3.g(iK2);
                        if (iG2 < i13) {
                            i13 = iG2;
                            c0682vh2 = c0682vh3;
                        }
                        i5 += i3;
                    }
                } else {
                    int iG3 = this.r.g();
                    int i14 = Integer.MIN_VALUE;
                    while (i5 != i4) {
                        C0682vh c0682vh4 = this.q[i5];
                        int i15 = c0682vh4.i(iG3);
                        if (i15 > i14) {
                            c0682vh2 = c0682vh4;
                            i14 = i15;
                        }
                        i5 += i3;
                    }
                }
                c0682vh = c0682vh2;
                r2.x(iB);
                ((int[]) r2.b)[iB] = c0682vh.e;
            } else {
                c0682vh = this.q[i12];
            }
            io.e = c0682vh;
            if (c0250hg.e == 1) {
                r6 = 0;
                b(view, -1, false);
            } else {
                r6 = 0;
                b(view, 0, false);
            }
            if (this.t == 1) {
                i = 1;
                T0(view, AbstractC0162el.w(r6, this.u, this.l, r6, ((ViewGroup.MarginLayoutParams) io).width), AbstractC0162el.w(true, this.o, this.m, C() + F(), ((ViewGroup.MarginLayoutParams) io).height));
            } else {
                i = 1;
                T0(view, AbstractC0162el.w(true, this.n, this.l, E() + D(), ((ViewGroup.MarginLayoutParams) io).width), AbstractC0162el.w(false, this.u, this.m, 0, ((ViewGroup.MarginLayoutParams) io).height));
            }
            if (c0250hg.e == i) {
                iC = c0682vh.g(iG);
                i2 = this.r.c(view) + iC;
            } else {
                i2 = c0682vh.i(iG);
                iC = i2 - this.r.c(view);
            }
            if (c0250hg.e == 1) {
                C0682vh c0682vh5 = io.e;
                c0682vh5.getClass();
                Io io2 = (Io) view.getLayoutParams();
                io2.e = c0682vh5;
                ArrayList arrayList = (ArrayList) c0682vh5.f;
                arrayList.add(view);
                c0682vh5.c = Integer.MIN_VALUE;
                if (arrayList.size() == 1) {
                    c0682vh5.b = Integer.MIN_VALUE;
                }
                if (io2.a.h() || io2.a.k()) {
                    c0682vh5.d = ((StaggeredGridLayoutManager) c0682vh5.g).r.c(view) + c0682vh5.d;
                }
            } else {
                C0682vh c0682vh6 = io.e;
                c0682vh6.getClass();
                Io io3 = (Io) view.getLayoutParams();
                io3.e = c0682vh6;
                ArrayList arrayList2 = (ArrayList) c0682vh6.f;
                arrayList2.add(0, view);
                c0682vh6.b = Integer.MIN_VALUE;
                if (arrayList2.size() == 1) {
                    c0682vh6.c = Integer.MIN_VALUE;
                }
                if (io3.a.h() || io3.a.k()) {
                    c0682vh6.d = ((StaggeredGridLayoutManager) c0682vh6.g).r.c(view) + c0682vh6.d;
                }
            }
            if (S0() && this.t == 1) {
                iC2 = this.s.g() - (((this.p - 1) - c0682vh.e) * this.u);
                iK = iC2 - this.s.c(view);
            } else {
                iK = this.s.k() + (c0682vh.e * this.u);
                iC2 = this.s.c(view) + iK;
            }
            if (this.t == 1) {
                AbstractC0162el.N(view, iK, iC, iC2, i2);
            } else {
                AbstractC0162el.N(view, iC, iK, i2, iC2);
            }
            e1(c0682vh, c0250hg2.e, i8);
            X0(c0285il, c0250hg2);
            if (c0250hg2.h && view.hasFocusable()) {
                this.y.set(c0682vh.e, false);
            }
            i7 = 1;
            z = true;
            i6 = 0;
        }
        if (!z) {
            X0(c0285il, c0250hg2);
        }
        int iK3 = c0250hg2.e == -1 ? this.r.k() - P0(this.r.k()) : O0(this.r.g()) - this.r.g();
        if (iK3 > 0) {
            return Math.min(c0250hg.b, iK3);
        }
        return 0;
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final int I(C0285il c0285il, C0408ml c0408ml) {
        if (this.t == 0) {
            return Math.min(this.p, c0408ml.b());
        }
        return -1;
    }

    public final View I0(boolean z) {
        int iK = this.r.k();
        int iG = this.r.g();
        View view = null;
        for (int iV = v() - 1; iV >= 0; iV--) {
            View viewU = u(iV);
            int iE = this.r.e(viewU);
            int iB = this.r.b(viewU);
            if (iB > iK && iE < iG) {
                if (iB <= iG || !z) {
                    return viewU;
                }
                if (view == null) {
                    view = viewU;
                }
            }
        }
        return view;
    }

    public final View J0(boolean z) {
        int iK = this.r.k();
        int iG = this.r.g();
        int iV = v();
        View view = null;
        for (int i = 0; i < iV; i++) {
            View viewU = u(i);
            int iE = this.r.e(viewU);
            if (this.r.b(viewU) > iK && iE < iG) {
                if (iE >= iK || !z) {
                    return viewU;
                }
                if (view == null) {
                    view = viewU;
                }
            }
        }
        return view;
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final boolean K() {
        return this.C != 0;
    }

    public final void K0(C0285il c0285il, C0408ml c0408ml, boolean z) {
        int iG;
        int iO0 = O0(Integer.MIN_VALUE);
        if (iO0 != Integer.MIN_VALUE && (iG = this.r.g() - iO0) > 0) {
            int i = iG - (-b1(-iG, c0285il, c0408ml));
            if (!z || i <= 0) {
                return;
            }
            this.r.o(i);
        }
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final boolean L() {
        return this.w;
    }

    public final void L0(C0285il c0285il, C0408ml c0408ml, boolean z) {
        int iK;
        int iP0 = P0(Integer.MAX_VALUE);
        if (iP0 != Integer.MAX_VALUE && (iK = iP0 - this.r.k()) > 0) {
            int iB1 = iK - b1(iK, c0285il, c0408ml);
            if (!z || iB1 <= 0) {
                return;
            }
            this.r.o(-iB1);
        }
    }

    public final int M0() {
        if (v() == 0) {
            return 0;
        }
        return AbstractC0162el.G(u(0));
    }

    public final int N0() {
        int iV = v();
        if (iV == 0) {
            return 0;
        }
        return AbstractC0162el.G(u(iV - 1));
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final void O(int i) {
        super.O(i);
        for (int i2 = 0; i2 < this.p; i2++) {
            C0682vh c0682vh = this.q[i2];
            int i3 = c0682vh.b;
            if (i3 != Integer.MIN_VALUE) {
                c0682vh.b = i3 + i;
            }
            int i4 = c0682vh.c;
            if (i4 != Integer.MIN_VALUE) {
                c0682vh.c = i4 + i;
            }
        }
    }

    public final int O0(int i) {
        int iG = this.q[0].g(i);
        for (int i2 = 1; i2 < this.p; i2++) {
            int iG2 = this.q[i2].g(i);
            if (iG2 > iG) {
                iG = iG2;
            }
        }
        return iG;
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final void P(int i) {
        super.P(i);
        for (int i2 = 0; i2 < this.p; i2++) {
            C0682vh c0682vh = this.q[i2];
            int i3 = c0682vh.b;
            if (i3 != Integer.MIN_VALUE) {
                c0682vh.b = i3 + i;
            }
            int i4 = c0682vh.c;
            if (i4 != Integer.MIN_VALUE) {
                c0682vh.c = i4 + i;
            }
        }
    }

    public final int P0(int i) {
        int i2 = this.q[0].i(i);
        for (int i3 = 1; i3 < this.p; i3++) {
            int i4 = this.q[i3].i(i);
            if (i4 < i2) {
                i2 = i4;
            }
        }
        return i2;
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final void Q() {
        this.B.e();
        for (int i = 0; i < this.p; i++) {
            this.q[i].b();
        }
    }

    /* JADX WARN: Code duplicated, block: B:21:0x0034  */
    /* JADX WARN: Code duplicated, block: B:22:0x0036 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:24:0x0039  */
    /* JADX WARN: Code duplicated, block: B:26:0x0041  */
    /* JADX WARN: Code duplicated, block: B:29:0x0050 A[LOOP:0: B:25:0x003f->B:29:0x0050, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:30:0x0053 A[EDGE_INSN: B:30:0x0053->B:31:0x0054 BREAK  A[LOOP:0: B:25:0x003f->B:29:0x0050]] */
    /* JADX WARN: Code duplicated, block: B:32:0x0056  */
    /* JADX WARN: Code duplicated, block: B:35:0x0068  */
    /* JADX WARN: Code duplicated, block: B:38:0x0077 A[LOOP:1: B:34:0x0066->B:38:0x0077, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:41:0x007d  */
    /* JADX WARN: Code duplicated, block: B:43:0x0092  */
    /* JADX WARN: Code duplicated, block: B:44:0x00a0  */
    /* JADX WARN: Code duplicated, block: B:47:0x00b5  */
    /* JADX WARN: Code duplicated, block: B:49:0x00b8 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:51:0x00bb  */
    /* JADX WARN: Code duplicated, block: B:52:0x00c2  */
    /* JADX WARN: Code duplicated, block: B:53:0x00c6  */
    /* JADX WARN: Code duplicated, block: B:56:0x00cc  */
    /* JADX WARN: Code duplicated, block: B:58:0x00d0  */
    /* JADX WARN: Code duplicated, block: B:59:0x00d5  */
    /* JADX WARN: Code duplicated, block: B:61:0x00db  */
    /* JADX WARN: Code duplicated, block: B:63:0x0053 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:64:0x0054 A[EDGE_INSN: B:64:0x0054->B:31:0x0054 BREAK  A[LOOP:0: B:25:0x003f->B:29:0x0050], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:65:0x007a A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:66:0x007b A[EDGE_INSN: B:66:0x007b->B:40:0x007b BREAK  A[LOOP:1: B:34:0x0066->B:38:0x0077], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:67:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:68:? A[RETURN, SYNTHETIC] */
    public final void Q0(int i, int i2, int i3) {
        int i4;
        int i5;
        R2 r2;
        int[] iArr;
        int iN0;
        ArrayList arrayList;
        Jo jo;
        int size;
        int i6;
        int i7;
        int size2;
        int iN1 = this.x ? N0() : M0();
        if (i3 == 8) {
            if (i < i2) {
                i4 = i2 + 1;
            } else {
                i4 = i + 1;
                i5 = i2;
            }
            r2 = this.B;
            iArr = (int[]) r2.b;
            if (iArr != null && i5 < iArr.length) {
                arrayList = (ArrayList) r2.c;
                if (arrayList != null) {
                    if (arrayList == null) {
                        size2 = arrayList.size() - 1;
                        while (true) {
                            if (size2 >= 0) {
                                jo = null;
                                break;
                            }
                            jo = (Jo) ((ArrayList) r2.c).get(size2);
                            if (jo.a == i5) {
                                break;
                            } else {
                                size2--;
                            }
                        }
                    } else {
                        jo = null;
                        break;
                    }
                    if (jo != null) {
                        ((ArrayList) r2.c).remove(jo);
                    }
                    size = ((ArrayList) r2.c).size();
                    i6 = 0;
                    while (true) {
                        if (i6 < size) {
                            i6 = -1;
                            break;
                        } else if (((Jo) ((ArrayList) r2.c).get(i6)).a >= i5) {
                            break;
                        } else {
                            i6++;
                        }
                    }
                    if (i6 != -1) {
                        Jo jo2 = (Jo) ((ArrayList) r2.c).get(i6);
                        ((ArrayList) r2.c).remove(i6);
                        i7 = jo2.a;
                    } else {
                        i7 = -1;
                    }
                } else {
                    i7 = -1;
                }
                if (i7 == -1) {
                    int[] iArr2 = (int[]) r2.b;
                    Arrays.fill(iArr2, i5, iArr2.length, -1);
                    int length = ((int[]) r2.b).length;
                } else {
                    Arrays.fill((int[]) r2.b, i5, Math.min(i7 + 1, ((int[]) r2.b).length), -1);
                }
            }
            if (i3 != 1) {
                r2.I(i, i2);
            } else if (i3 != 2) {
                r2.J(i, i2);
            } else if (i3 == 8) {
                r2.J(i, 1);
                r2.I(i2, 1);
            }
            if (i4 <= iN1) {
                return;
            }
            if (this.x) {
                iN0 = M0();
            } else {
                iN0 = N0();
            }
            if (i5 <= iN0) {
                o0();
            }
        }
        i4 = i + i2;
        i5 = i;
        r2 = this.B;
        iArr = (int[]) r2.b;
        if (iArr != null) {
            arrayList = (ArrayList) r2.c;
            if (arrayList != null) {
                if (arrayList == null) {
                    size2 = arrayList.size() - 1;
                    while (true) {
                        if (size2 >= 0) {
                            jo = null;
                            break;
                        }
                        jo = (Jo) ((ArrayList) r2.c).get(size2);
                        if (jo.a == i5) {
                            break;
                            break;
                        }
                        size2--;
                    }
                } else {
                    jo = null;
                    break;
                }
                if (jo != null) {
                    ((ArrayList) r2.c).remove(jo);
                }
                size = ((ArrayList) r2.c).size();
                i6 = 0;
                while (true) {
                    if (i6 < size) {
                        i6 = -1;
                        break;
                    } else {
                        if (((Jo) ((ArrayList) r2.c).get(i6)).a >= i5) {
                            break;
                            break;
                        }
                        i6++;
                    }
                }
                if (i6 != -1) {
                    Jo jo3 = (Jo) ((ArrayList) r2.c).get(i6);
                    ((ArrayList) r2.c).remove(i6);
                    i7 = jo3.a;
                } else {
                    i7 = -1;
                }
            } else {
                i7 = -1;
            }
            if (i7 == -1) {
                int[] iArr3 = (int[]) r2.b;
                Arrays.fill(iArr3, i5, iArr3.length, -1);
                int length2 = ((int[]) r2.b).length;
            } else {
                Arrays.fill((int[]) r2.b, i5, Math.min(i7 + 1, ((int[]) r2.b).length), -1);
            }
        }
        if (i3 != 1) {
            r2.I(i, i2);
        } else if (i3 != 2) {
            r2.J(i, i2);
        } else if (i3 == 8) {
            r2.J(i, 1);
            r2.I(i2, 1);
        }
        if (i4 <= iN1) {
            return;
        }
        if (this.x) {
            iN0 = M0();
        } else {
            iN0 = N0();
        }
        if (i5 <= iN0) {
            o0();
        }
    }

    /* JADX WARN: Code duplicated, block: B:51:0x00fd  */
    /* JADX WARN: Code duplicated, block: B:52:0x00ff  */
    /* JADX WARN: Code duplicated, block: B:54:0x0102  */
    /* JADX WARN: Code duplicated, block: B:55:0x0104  */
    /* JADX WARN: Code duplicated, block: B:68:0x0107 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:74:0x002c A[SYNTHETIC] */
    public final View R0() {
        boolean z;
        boolean z2;
        int iV = v();
        int i = iV - 1;
        BitSet bitSet = new BitSet(this.p);
        bitSet.set(0, this.p, true);
        byte b = (this.t == 1 && S0()) ? (byte) 1 : (byte) -1;
        if (this.x) {
            iV = -1;
        } else {
            i = 0;
        }
        int i2 = i < iV ? 1 : -1;
        while (i != iV) {
            View viewU = u(i);
            Io io = (Io) viewU.getLayoutParams();
            if (bitSet.get(io.e.e)) {
                C0682vh c0682vh = io.e;
                if (this.x) {
                    int i3 = c0682vh.c;
                    if (i3 == Integer.MIN_VALUE) {
                        c0682vh.a();
                        i3 = c0682vh.c;
                    }
                    if (i3 < this.r.g()) {
                        ArrayList arrayList = (ArrayList) c0682vh.f;
                        ((Io) ((View) arrayList.get(arrayList.size() - 1)).getLayoutParams()).getClass();
                        return viewU;
                    }
                } else {
                    int i4 = c0682vh.b;
                    if (i4 == Integer.MIN_VALUE) {
                        View view = (View) ((ArrayList) c0682vh.f).get(0);
                        Io io2 = (Io) view.getLayoutParams();
                        c0682vh.b = ((StaggeredGridLayoutManager) c0682vh.g).r.e(view);
                        io2.getClass();
                        i4 = c0682vh.b;
                    }
                    if (i4 > this.r.k()) {
                        ((Io) ((View) ((ArrayList) c0682vh.f).get(0)).getLayoutParams()).getClass();
                        return viewU;
                    }
                }
                bitSet.clear(io.e.e);
            }
            i += i2;
            if (i != iV) {
                View viewU2 = u(i);
                if (this.x) {
                    int iB = this.r.b(viewU);
                    int iB2 = this.r.b(viewU2);
                    if (iB >= iB2) {
                        if (iB == iB2) {
                            if (io.e.e - ((Io) viewU2.getLayoutParams()).e.e < 0) {
                                z = true;
                            } else {
                                z = false;
                            }
                            if (b < 0) {
                                z2 = true;
                            } else {
                                z2 = false;
                            }
                            if (z != z2) {
                            }
                        } else {
                            continue;
                        }
                    }
                    return viewU;
                }
                int iE = this.r.e(viewU);
                int iE2 = this.r.e(viewU2);
                if (iE <= iE2) {
                    if (iE == iE2) {
                        if (io.e.e - ((Io) viewU2.getLayoutParams()).e.e < 0) {
                            z = true;
                        } else {
                            z = false;
                        }
                        if (b < 0) {
                            z2 = true;
                        } else {
                            z2 = false;
                        }
                        if (z != z2) {
                        }
                    } else {
                        continue;
                    }
                }
                return viewU;
            }
        }
        return null;
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final void S(RecyclerView recyclerView) {
        RecyclerView recyclerView2 = this.b;
        if (recyclerView2 != null) {
            recyclerView2.removeCallbacks(this.K);
        }
        for (int i = 0; i < this.p; i++) {
            this.q[i].b();
        }
        recyclerView.requestLayout();
    }

    public final boolean S0() {
        return this.b.getLayoutDirection() == 1;
    }

    /* JADX WARN: Code duplicated, block: B:31:0x0046  */
    /* JADX WARN: Code duplicated, block: B:37:0x0051  */
    @Override // rikka.shizuku.AbstractC0162el
    public final View T(View view, int i, C0285il c0285il, C0408ml c0408ml) {
        View viewB;
        int i2;
        if (v() != 0) {
            RecyclerView recyclerView = this.b;
            if (recyclerView == null || (viewB = recyclerView.B(view)) == null || this.a.c.contains(viewB)) {
                viewB = null;
            }
            if (viewB != null) {
                a1();
                if (i != 1) {
                    if (i != 2) {
                        if (i != 17) {
                            if (i != 33) {
                                if (i == 66 ? this.t == 0 : !(i != 130 || this.t != 1)) {
                                    i2 = 1;
                                }
                            } else if (this.t == 1) {
                                i2 = -1;
                            }
                            i2 = Integer.MIN_VALUE;
                        } else if (this.t == 0) {
                            i2 = -1;
                        } else {
                            i2 = Integer.MIN_VALUE;
                        }
                    } else if (this.t != 1 && S0()) {
                        i2 = -1;
                    } else {
                        i2 = 1;
                    }
                } else if (this.t != 1 && S0()) {
                    i2 = 1;
                } else {
                    i2 = -1;
                }
                if (i2 != Integer.MIN_VALUE) {
                    Io io = (Io) viewB.getLayoutParams();
                    io.getClass();
                    C0682vh c0682vh = io.e;
                    int iN0 = i2 == 1 ? N0() : M0();
                    d1(iN0, c0408ml);
                    c1(i2);
                    C0250hg c0250hg = this.v;
                    c0250hg.c = c0250hg.d + iN0;
                    c0250hg.b = (int) (this.r.l() * 0.33333334f);
                    c0250hg.h = true;
                    c0250hg.a = false;
                    H0(c0285il, c0250hg, c0408ml);
                    this.D = this.x;
                    View viewH = c0682vh.h(iN0, i2);
                    if (viewH != null && viewH != viewB) {
                        return viewH;
                    }
                    if (V0(i2)) {
                        for (int i3 = this.p - 1; i3 >= 0; i3--) {
                            View viewH2 = this.q[i3].h(iN0, i2);
                            if (viewH2 != null && viewH2 != viewB) {
                                return viewH2;
                            }
                        }
                    } else {
                        for (int i4 = 0; i4 < this.p; i4++) {
                            View viewH3 = this.q[i4].h(iN0, i2);
                            if (viewH3 != null && viewH3 != viewB) {
                                return viewH3;
                            }
                        }
                    }
                    boolean z = (this.w ^ true) == (i2 == -1);
                    View viewQ = q(z ? c0682vh.c() : c0682vh.d());
                    if (viewQ != null && viewQ != viewB) {
                        return viewQ;
                    }
                    if (V0(i2)) {
                        for (int i5 = this.p - 1; i5 >= 0; i5--) {
                            if (i5 != c0682vh.e) {
                                View viewQ2 = q(z ? this.q[i5].c() : this.q[i5].d());
                                if (viewQ2 != null && viewQ2 != viewB) {
                                    return viewQ2;
                                }
                            }
                        }
                    } else {
                        for (int i6 = 0; i6 < this.p; i6++) {
                            View viewQ3 = q(z ? this.q[i6].c() : this.q[i6].d());
                            if (viewQ3 != null && viewQ3 != viewB) {
                                return viewQ3;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public final void T0(View view, int i, int i2) {
        RecyclerView recyclerView = this.b;
        Rect rect = this.G;
        if (recyclerView == null) {
            rect.set(0, 0, 0, 0);
        } else {
            rect.set(recyclerView.L(view));
        }
        Io io = (Io) view.getLayoutParams();
        int iF1 = f1(i, ((ViewGroup.MarginLayoutParams) io).leftMargin + rect.left, ((ViewGroup.MarginLayoutParams) io).rightMargin + rect.right);
        int iF2 = f1(i2, ((ViewGroup.MarginLayoutParams) io).topMargin + rect.top, ((ViewGroup.MarginLayoutParams) io).bottomMargin + rect.bottom);
        if (x0(view, iF1, iF2, io)) {
            view.measure(iF1, iF2);
        }
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final void U(AccessibilityEvent accessibilityEvent) {
        super.U(accessibilityEvent);
        if (v() > 0) {
            View viewJ0 = J0(false);
            View viewI0 = I0(false);
            if (viewJ0 == null || viewI0 == null) {
                return;
            }
            int iG = AbstractC0162el.G(viewJ0);
            int iG2 = AbstractC0162el.G(viewI0);
            if (iG < iG2) {
                accessibilityEvent.setFromIndex(iG);
                accessibilityEvent.setToIndex(iG2);
            } else {
                accessibilityEvent.setFromIndex(iG2);
                accessibilityEvent.setToIndex(iG);
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:108:0x01a8  */
    /* JADX WARN: Code duplicated, block: B:109:0x01aa  */
    /* JADX WARN: Code duplicated, block: B:123:0x01e1  */
    /* JADX WARN: Code duplicated, block: B:125:0x01ec  */
    /* JADX WARN: Code duplicated, block: B:131:0x01fe  */
    /* JADX WARN: Code duplicated, block: B:133:0x0209  */
    /* JADX WARN: Code duplicated, block: B:255:0x0419  */
    /* JADX WARN: Code duplicated, block: B:266:0x01fc A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:270:0x01fc A[SYNTHETIC] */
    public final void U0(C0285il c0285il, C0408ml c0408ml, boolean z) {
        boolean z2;
        Ko ko;
        int iV;
        int i;
        int iG;
        int iG2;
        int iV2;
        int i2;
        boolean z3;
        Ko ko2 = this.F;
        Ho ho = this.H;
        if (!(ko2 == null && this.z == -1) && c0408ml.b() == 0) {
            j0(c0285il);
            ho.a();
            return;
        }
        boolean z4 = (ho.e && this.z == -1 && this.F == null) ? false : true;
        R2 r2 = this.B;
        StaggeredGridLayoutManager staggeredGridLayoutManager = ho.g;
        if (z4) {
            ho.a();
            Ko ko3 = this.F;
            if (ko3 != null) {
                int i3 = ko3.c;
                if (i3 > 0) {
                    if (i3 == this.p) {
                        for (int i4 = 0; i4 < this.p; i4++) {
                            this.q[i4].b();
                            Ko ko4 = this.F;
                            int iG3 = ko4.d[i4];
                            if (iG3 != Integer.MIN_VALUE) {
                                iG3 += ko4.i ? this.r.g() : this.r.k();
                            }
                            C0682vh c0682vh = this.q[i4];
                            c0682vh.b = iG3;
                            c0682vh.c = iG3;
                        }
                    } else {
                        ko3.d = null;
                        ko3.c = 0;
                        ko3.e = 0;
                        ko3.f = null;
                        ko3.g = null;
                        ko3.a = ko3.b;
                    }
                }
                Ko ko5 = this.F;
                this.E = ko5.j;
                boolean z5 = ko5.h;
                c(null);
                Ko ko6 = this.F;
                if (ko6 != null && ko6.h != z5) {
                    ko6.h = z5;
                }
                this.w = z5;
                o0();
                a1();
                Ko ko7 = this.F;
                int i5 = ko7.a;
                if (i5 != -1) {
                    this.z = i5;
                    ho.c = ko7.i;
                } else {
                    ho.c = this.x;
                }
                if (ko7.e > 1) {
                    r2.b = ko7.f;
                    r2.c = ko7.g;
                }
            } else {
                a1();
                ho.c = this.x;
            }
            if (c0408ml.g || (i2 = this.z) == -1) {
                if (this.D) {
                    int iB = c0408ml.b();
                    iV2 = v() - 1;
                    while (true) {
                        if (iV2 < 0) {
                            iG2 = 0;
                            break;
                        }
                        iG2 = AbstractC0162el.G(u(iV2));
                        if (iG2 < 0 && iG2 < iB) {
                            break;
                        } else {
                            iV2--;
                        }
                    }
                } else {
                    int iB2 = c0408ml.b();
                    iV = v();
                    i = 0;
                    while (true) {
                        if (i >= iV) {
                            iG2 = 0;
                            break;
                        }
                        iG = AbstractC0162el.G(u(i));
                        if (iG < 0 && iG < iB2) {
                            iG2 = iG;
                            break;
                        }
                        i++;
                    }
                }
                ho.a = iG2;
                ho.b = Integer.MIN_VALUE;
            } else if (i2 < 0 || i2 >= c0408ml.b()) {
                this.z = -1;
                this.A = Integer.MIN_VALUE;
                if (this.D) {
                    int iB3 = c0408ml.b();
                    iV2 = v() - 1;
                    while (true) {
                        if (iV2 < 0) {
                            iG2 = 0;
                            break;
                        } else {
                            iG2 = AbstractC0162el.G(u(iV2));
                            if (iG2 < 0) {
                            }
                            iV2--;
                        }
                    }
                } else {
                    int iB4 = c0408ml.b();
                    iV = v();
                    i = 0;
                    while (true) {
                        if (i >= iV) {
                            iG2 = 0;
                            break;
                        } else {
                            iG = AbstractC0162el.G(u(i));
                            if (iG < 0) {
                            }
                            i++;
                        }
                    }
                }
                ho.a = iG2;
                ho.b = Integer.MIN_VALUE;
            } else {
                Ko ko8 = this.F;
                if (ko8 == null || ko8.a == -1 || ko8.c < 1) {
                    View viewQ = q(this.z);
                    if (viewQ != null) {
                        ho.a = this.x ? N0() : M0();
                        if (this.A != Integer.MIN_VALUE) {
                            if (ho.c) {
                                ho.b = (this.r.g() - this.A) - this.r.b(viewQ);
                            } else {
                                ho.b = (this.r.k() + this.A) - this.r.e(viewQ);
                            }
                        } else if (this.r.c(viewQ) > this.r.l()) {
                            ho.b = ho.c ? this.r.g() : this.r.k();
                        } else {
                            int iE = this.r.e(viewQ) - this.r.k();
                            if (iE < 0) {
                                ho.b = -iE;
                            } else {
                                int iG4 = this.r.g() - this.r.b(viewQ);
                                if (iG4 < 0) {
                                    ho.b = iG4;
                                } else {
                                    ho.b = Integer.MIN_VALUE;
                                }
                            }
                        }
                    } else {
                        int i6 = this.z;
                        ho.a = i6;
                        int i7 = this.A;
                        if (i7 == Integer.MIN_VALUE) {
                            if (v() != 0) {
                                if ((i6 < M0()) != this.x) {
                                    z3 = false;
                                } else {
                                    z3 = true;
                                }
                            } else if (this.x) {
                                z3 = true;
                            } else {
                                z3 = false;
                            }
                            ho.c = z3;
                            ho.b = z3 ? staggeredGridLayoutManager.r.g() : staggeredGridLayoutManager.r.k();
                        } else if (ho.c) {
                            ho.b = staggeredGridLayoutManager.r.g() - i7;
                        } else {
                            ho.b = staggeredGridLayoutManager.r.k() + i7;
                        }
                        ho.d = true;
                    }
                } else {
                    ho.b = Integer.MIN_VALUE;
                    ho.a = this.z;
                }
            }
            ho.e = true;
        }
        if (this.F == null && this.z == -1 && (ho.c != this.D || S0() != this.E)) {
            r2.e();
            ho.d = true;
        }
        if (v() > 0 && ((ko = this.F) == null || ko.c < 1)) {
            if (ho.d) {
                for (int i8 = 0; i8 < this.p; i8++) {
                    this.q[i8].b();
                    int i9 = ho.b;
                    if (i9 != Integer.MIN_VALUE) {
                        C0682vh c0682vh2 = this.q[i8];
                        c0682vh2.b = i9;
                        c0682vh2.c = i9;
                    }
                }
            } else if (z4 || ho.f == null) {
                for (int i10 = 0; i10 < this.p; i10++) {
                    C0682vh c0682vh3 = this.q[i10];
                    boolean z6 = this.x;
                    int i11 = ho.b;
                    int iG5 = z6 ? c0682vh3.g(Integer.MIN_VALUE) : c0682vh3.i(Integer.MIN_VALUE);
                    c0682vh3.b();
                    if (iG5 != Integer.MIN_VALUE) {
                        StaggeredGridLayoutManager staggeredGridLayoutManager2 = (StaggeredGridLayoutManager) c0682vh3.g;
                        if ((!z6 || iG5 >= staggeredGridLayoutManager2.r.g()) && (z6 || iG5 <= staggeredGridLayoutManager2.r.k())) {
                            if (i11 != Integer.MIN_VALUE) {
                                iG5 += i11;
                            }
                            c0682vh3.c = iG5;
                            c0682vh3.b = iG5;
                        }
                    }
                }
                C0682vh[] c0682vhArr = this.q;
                int length = c0682vhArr.length;
                int[] iArr = ho.f;
                if (iArr == null || iArr.length < length) {
                    ho.f = new int[staggeredGridLayoutManager.q.length];
                }
                for (int i12 = 0; i12 < length; i12++) {
                    ho.f[i12] = c0682vhArr[i12].i(Integer.MIN_VALUE);
                }
            } else {
                for (int i13 = 0; i13 < this.p; i13++) {
                    C0682vh c0682vh4 = this.q[i13];
                    c0682vh4.b();
                    int i14 = ho.f[i13];
                    c0682vh4.b = i14;
                    c0682vh4.c = i14;
                }
            }
        }
        p(c0285il);
        C0250hg c0250hg = this.v;
        c0250hg.a = false;
        int iL = this.s.l();
        this.u = iL / this.p;
        View.MeasureSpec.makeMeasureSpec(iL, this.s.i());
        d1(ho.a, c0408ml);
        if (ho.c) {
            c1(-1);
            H0(c0285il, c0250hg, c0408ml);
            c1(1);
            c0250hg.c = ho.a + c0250hg.d;
            H0(c0285il, c0250hg, c0408ml);
        } else {
            c1(1);
            H0(c0285il, c0250hg, c0408ml);
            c1(-1);
            c0250hg.c = ho.a + c0250hg.d;
            H0(c0285il, c0250hg, c0408ml);
        }
        if (this.s.i() != 1073741824) {
            int iV3 = v();
            float fMax = 0.0f;
            for (int i15 = 0; i15 < iV3; i15++) {
                View viewU = u(i15);
                float fC = this.s.c(viewU);
                if (fC >= fMax) {
                    ((Io) viewU.getLayoutParams()).getClass();
                    fMax = Math.max(fMax, fC);
                }
            }
            int i16 = this.u;
            int iRound = Math.round(fMax * this.p);
            if (this.s.i() == Integer.MIN_VALUE) {
                iRound = Math.min(iRound, this.s.l());
            }
            this.u = iRound / this.p;
            View.MeasureSpec.makeMeasureSpec(iRound, this.s.i());
            if (this.u != i16) {
                for (int i17 = 0; i17 < iV3; i17++) {
                    View viewU2 = u(i17);
                    Io io = (Io) viewU2.getLayoutParams();
                    io.getClass();
                    if (S0() && this.t == 1) {
                        int i18 = -((this.p - 1) - io.e.e);
                        viewU2.offsetLeftAndRight((this.u * i18) - (i18 * i16));
                    } else {
                        int i19 = io.e.e;
                        int i20 = this.u * i19;
                        int i21 = i19 * i16;
                        if (this.t == 1) {
                            viewU2.offsetLeftAndRight(i20 - i21);
                        } else {
                            viewU2.offsetTopAndBottom(i20 - i21);
                        }
                    }
                }
            }
        }
        if (v() > 0) {
            if (this.x) {
                K0(c0285il, c0408ml, true);
                L0(c0285il, c0408ml, false);
            } else {
                L0(c0285il, c0408ml, true);
                K0(c0285il, c0408ml, false);
            }
        }
        if (z && !c0408ml.g && this.C != 0 && v() > 0 && R0() != null) {
            RecyclerView recyclerView = this.b;
            if (recyclerView != null) {
                recyclerView.removeCallbacks(this.K);
            }
            z2 = D0();
        }
        if (c0408ml.g) {
            ho.a();
        }
        this.D = ho.c;
        this.E = S0();
        if (z2) {
            ho.a();
            U0(c0285il, c0408ml, false);
        }
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final void V(C0285il c0285il, C0408ml c0408ml, C0141e0 c0141e0) {
        super.V(c0285il, c0408ml, c0141e0);
        c0141e0.i("androidx.recyclerview.widget.StaggeredGridLayoutManager");
    }

    public final boolean V0(int i) {
        if (this.t == 0) {
            return (i == -1) != this.x;
        }
        return ((i == -1) == this.x) == S0();
    }

    public final void W0(int i, C0408ml c0408ml) {
        int iM0;
        int i2;
        if (i > 0) {
            iM0 = N0();
            i2 = 1;
        } else {
            iM0 = M0();
            i2 = -1;
        }
        C0250hg c0250hg = this.v;
        c0250hg.a = true;
        d1(iM0, c0408ml);
        c1(i2);
        c0250hg.c = iM0 + c0250hg.d;
        c0250hg.b = Math.abs(i);
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final void X(C0285il c0285il, C0408ml c0408ml, View view, C0141e0 c0141e0) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (!(layoutParams instanceof Io)) {
            W(view, c0141e0);
            return;
        }
        Io io = (Io) layoutParams;
        if (this.t == 0) {
            C0682vh c0682vh = io.e;
            c0141e0.j(Ys.z(false, c0682vh == null ? -1 : c0682vh.e, 1, -1, -1));
        } else {
            C0682vh c0682vh2 = io.e;
            c0141e0.j(Ys.z(false, -1, -1, c0682vh2 == null ? -1 : c0682vh2.e, 1));
        }
    }

    public final void X0(C0285il c0285il, C0250hg c0250hg) {
        int iMin;
        if (!c0250hg.a || c0250hg.i) {
            return;
        }
        if (c0250hg.b == 0) {
            if (c0250hg.e == -1) {
                Y0(c0285il, c0250hg.g);
                return;
            } else {
                Z0(c0285il, c0250hg.f);
                return;
            }
        }
        int i = 1;
        if (c0250hg.e == -1) {
            int i2 = c0250hg.f;
            int i3 = this.q[0].i(i2);
            while (i < this.p) {
                int i4 = this.q[i].i(i2);
                if (i4 > i3) {
                    i3 = i4;
                }
                i++;
            }
            int i5 = i2 - i3;
            Y0(c0285il, i5 < 0 ? c0250hg.g : c0250hg.g - Math.min(i5, c0250hg.b));
            return;
        }
        int i6 = c0250hg.g;
        int iG = this.q[0].g(i6);
        while (i < this.p) {
            int iG2 = this.q[i].g(i6);
            if (iG2 < iG) {
                iG = iG2;
            }
            i++;
        }
        int i7 = iG - c0250hg.g;
        if (i7 < 0) {
            iMin = c0250hg.f;
        } else {
            iMin = Math.min(i7, c0250hg.b) + c0250hg.f;
        }
        Z0(c0285il, iMin);
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final void Y(int i, int i2) {
        Q0(i, i2, 1);
    }

    public final void Y0(C0285il c0285il, int i) {
        for (int iV = v() - 1; iV >= 0; iV--) {
            View viewU = u(iV);
            if (this.r.e(viewU) < i || this.r.n(viewU) < i) {
                return;
            }
            Io io = (Io) viewU.getLayoutParams();
            io.getClass();
            if (((ArrayList) io.e.f).size() == 1) {
                return;
            }
            C0682vh c0682vh = io.e;
            ArrayList arrayList = (ArrayList) c0682vh.f;
            int size = arrayList.size();
            View view = (View) arrayList.remove(size - 1);
            Io io2 = (Io) view.getLayoutParams();
            io2.e = null;
            if (io2.a.h() || io2.a.k()) {
                c0682vh.d -= ((StaggeredGridLayoutManager) c0682vh.g).r.c(view);
            }
            if (size == 1) {
                c0682vh.b = Integer.MIN_VALUE;
            }
            c0682vh.c = Integer.MIN_VALUE;
            l0(viewU, c0285il);
        }
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final void Z() {
        this.B.e();
        o0();
    }

    public final void Z0(C0285il c0285il, int i) {
        while (v() > 0) {
            View viewU = u(0);
            if (this.r.b(viewU) > i || this.r.m(viewU) > i) {
                return;
            }
            Io io = (Io) viewU.getLayoutParams();
            io.getClass();
            if (((ArrayList) io.e.f).size() == 1) {
                return;
            }
            C0682vh c0682vh = io.e;
            ArrayList arrayList = (ArrayList) c0682vh.f;
            View view = (View) arrayList.remove(0);
            Io io2 = (Io) view.getLayoutParams();
            io2.e = null;
            if (arrayList.size() == 0) {
                c0682vh.c = Integer.MIN_VALUE;
            }
            if (io2.a.h() || io2.a.k()) {
                c0682vh.d -= ((StaggeredGridLayoutManager) c0682vh.g).r.c(view);
            }
            c0682vh.b = Integer.MIN_VALUE;
            l0(viewU, c0285il);
        }
    }

    /* JADX WARN: Code duplicated, block: B:6:0x000c  */
    @Override // rikka.shizuku.InterfaceC0377ll
    public final PointF a(int i) {
        int i2 = -1;
        if (v() != 0) {
            if ((i < M0()) == this.x) {
                i2 = 1;
            }
        } else if (this.x) {
            i2 = 1;
        }
        PointF pointF = new PointF();
        if (i2 == 0) {
            return null;
        }
        if (this.t == 0) {
            pointF.x = i2;
            pointF.y = 0.0f;
            return pointF;
        }
        pointF.x = 0.0f;
        pointF.y = i2;
        return pointF;
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final void a0(int i, int i2) {
        Q0(i, i2, 8);
    }

    public final void a1() {
        if (this.t == 1 || !S0()) {
            this.x = this.w;
        } else {
            this.x = !this.w;
        }
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final void b0(int i, int i2) {
        Q0(i, i2, 2);
    }

    public final int b1(int i, C0285il c0285il, C0408ml c0408ml) {
        if (v() == 0 || i == 0) {
            return 0;
        }
        W0(i, c0408ml);
        C0250hg c0250hg = this.v;
        int iH0 = H0(c0285il, c0250hg, c0408ml);
        if (c0250hg.b >= iH0) {
            i = i < 0 ? -iH0 : iH0;
        }
        this.r.o(-i);
        this.D = this.x;
        c0250hg.b = 0;
        X0(c0285il, c0250hg);
        return i;
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final void c(String str) {
        if (this.F == null) {
            super.c(str);
        }
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final void c0(int i, int i2) {
        Q0(i, i2, 4);
    }

    public final void c1(int i) {
        C0250hg c0250hg = this.v;
        c0250hg.e = i;
        c0250hg.d = this.x != (i == -1) ? -1 : 1;
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final boolean d() {
        return this.t == 0;
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final void d0(C0285il c0285il, C0408ml c0408ml) {
        U0(c0285il, c0408ml, true);
    }

    public final void d1(int i, C0408ml c0408ml) {
        int iL;
        int iL2;
        int i2;
        C0250hg c0250hg = this.v;
        boolean z = false;
        c0250hg.b = 0;
        c0250hg.c = i;
        Jg jg = this.e;
        if (!(jg != null && jg.e) || (i2 = c0408ml.a) == -1) {
            iL = 0;
            iL2 = 0;
        } else {
            if (this.x == (i2 < i)) {
                iL = this.r.l();
                iL2 = 0;
            } else {
                iL2 = this.r.l();
                iL = 0;
            }
        }
        RecyclerView recyclerView = this.b;
        if (recyclerView == null || !recyclerView.h) {
            c0250hg.g = this.r.f() + iL;
            c0250hg.f = -iL2;
        } else {
            c0250hg.f = this.r.k() - iL2;
            c0250hg.g = this.r.g() + iL;
        }
        c0250hg.h = false;
        c0250hg.a = true;
        if (this.r.i() == 0 && this.r.f() == 0) {
            z = true;
        }
        c0250hg.i = z;
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final boolean e() {
        return this.t == 1;
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final void e0(C0408ml c0408ml) {
        this.z = -1;
        this.A = Integer.MIN_VALUE;
        this.F = null;
        this.H.a();
    }

    public final void e1(C0682vh c0682vh, int i, int i2) {
        int i3 = c0682vh.d;
        int i4 = c0682vh.e;
        if (i != -1) {
            int i5 = c0682vh.c;
            if (i5 == Integer.MIN_VALUE) {
                c0682vh.a();
                i5 = c0682vh.c;
            }
            if (i5 - i3 >= i2) {
                this.y.set(i4, false);
                return;
            }
            return;
        }
        int i6 = c0682vh.b;
        if (i6 == Integer.MIN_VALUE) {
            View view = (View) ((ArrayList) c0682vh.f).get(0);
            Io io = (Io) view.getLayoutParams();
            c0682vh.b = ((StaggeredGridLayoutManager) c0682vh.g).r.e(view);
            io.getClass();
            i6 = c0682vh.b;
        }
        if (i6 + i3 <= i2) {
            this.y.set(i4, false);
        }
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final boolean f(C0193fl c0193fl) {
        return c0193fl instanceof Io;
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final void f0(Parcelable parcelable) {
        if (parcelable instanceof Ko) {
            Ko ko = (Ko) parcelable;
            this.F = ko;
            if (this.z != -1) {
                ko.d = null;
                ko.c = 0;
                ko.a = -1;
                ko.b = -1;
                ko.d = null;
                ko.c = 0;
                ko.e = 0;
                ko.f = null;
                ko.g = null;
            }
            o0();
        }
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final Parcelable g0() {
        int i;
        int iK;
        int[] iArr;
        Ko ko = this.F;
        if (ko != null) {
            Ko ko2 = new Ko();
            ko2.c = ko.c;
            ko2.a = ko.a;
            ko2.b = ko.b;
            ko2.d = ko.d;
            ko2.e = ko.e;
            ko2.f = ko.f;
            ko2.h = ko.h;
            ko2.i = ko.i;
            ko2.j = ko.j;
            ko2.g = ko.g;
            return ko2;
        }
        Ko ko3 = new Ko();
        ko3.h = this.w;
        ko3.i = this.D;
        ko3.j = this.E;
        R2 r2 = this.B;
        if (r2 == null || (iArr = (int[]) r2.b) == null) {
            ko3.e = 0;
        } else {
            ko3.f = iArr;
            ko3.e = iArr.length;
            ko3.g = (ArrayList) r2.c;
        }
        if (v() <= 0) {
            ko3.a = -1;
            ko3.b = -1;
            ko3.c = 0;
            return ko3;
        }
        ko3.a = this.D ? N0() : M0();
        View viewI0 = this.x ? I0(true) : J0(true);
        ko3.b = viewI0 != null ? AbstractC0162el.G(viewI0) : -1;
        int i2 = this.p;
        ko3.c = i2;
        ko3.d = new int[i2];
        for (int i3 = 0; i3 < this.p; i3++) {
            if (this.D) {
                i = this.q[i3].g(Integer.MIN_VALUE);
                if (i != Integer.MIN_VALUE) {
                    iK = this.r.g();
                    i -= iK;
                }
            } else {
                i = this.q[i3].i(Integer.MIN_VALUE);
                if (i != Integer.MIN_VALUE) {
                    iK = this.r.k();
                    i -= iK;
                }
            }
            ko3.d[i3] = i;
        }
        return ko3;
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final void h(int i, int i2, C0408ml c0408ml, Fd fd) {
        C0250hg c0250hg;
        int iG;
        int i3;
        if (this.t != 0) {
            i = i2;
        }
        if (v() == 0 || i == 0) {
            return;
        }
        W0(i, c0408ml);
        int[] iArr = this.J;
        if (iArr == null || iArr.length < this.p) {
            this.J = new int[this.p];
        }
        int i4 = 0;
        int i5 = 0;
        while (true) {
            int i6 = this.p;
            c0250hg = this.v;
            if (i4 >= i6) {
                break;
            }
            if (c0250hg.d == -1) {
                iG = c0250hg.f;
                i3 = this.q[i4].i(iG);
            } else {
                iG = this.q[i4].g(c0250hg.g);
                i3 = c0250hg.g;
            }
            int i7 = iG - i3;
            if (i7 >= 0) {
                this.J[i5] = i7;
                i5++;
            }
            i4++;
        }
        Arrays.sort(this.J, 0, i5);
        for (int i8 = 0; i8 < i5; i8++) {
            int i9 = c0250hg.c;
            if (i9 < 0 || i9 >= c0408ml.b()) {
                return;
            }
            fd.a(c0250hg.c, this.J[i8]);
            c0250hg.c += c0250hg.d;
        }
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final void h0(int i) {
        if (i == 0) {
            D0();
        }
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final int j(C0408ml c0408ml) {
        return E0(c0408ml);
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final int k(C0408ml c0408ml) {
        return F0(c0408ml);
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final int l(C0408ml c0408ml) {
        return G0(c0408ml);
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final int m(C0408ml c0408ml) {
        return E0(c0408ml);
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final int n(C0408ml c0408ml) {
        return F0(c0408ml);
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final int o(C0408ml c0408ml) {
        return G0(c0408ml);
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final int p0(int i, C0285il c0285il, C0408ml c0408ml) {
        return b1(i, c0285il, c0408ml);
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final void q0(int i) {
        Ko ko = this.F;
        if (ko != null && ko.a != i) {
            ko.d = null;
            ko.c = 0;
            ko.a = -1;
            ko.b = -1;
        }
        this.z = i;
        this.A = Integer.MIN_VALUE;
        o0();
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final C0193fl r() {
        return this.t == 0 ? new Io(-2, -1) : new Io(-1, -2);
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final int r0(int i, C0285il c0285il, C0408ml c0408ml) {
        return b1(i, c0285il, c0408ml);
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final C0193fl s(Context context, AttributeSet attributeSet) {
        return new Io(context, attributeSet);
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final C0193fl t(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof ViewGroup.MarginLayoutParams ? new Io((ViewGroup.MarginLayoutParams) layoutParams) : new Io(layoutParams);
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final void u0(Rect rect, int i, int i2) {
        int iG;
        int iG2;
        int i3 = this.p;
        int iE = E() + D();
        int iC = C() + F();
        if (this.t == 1) {
            int iHeight = rect.height() + iC;
            RecyclerView recyclerView = this.b;
            WeakHashMap weakHashMap = Hs.a;
            iG2 = AbstractC0162el.g(i2, iHeight, recyclerView.getMinimumHeight());
            iG = AbstractC0162el.g(i, (this.u * i3) + iE, this.b.getMinimumWidth());
        } else {
            int iWidth = rect.width() + iE;
            RecyclerView recyclerView2 = this.b;
            WeakHashMap weakHashMap2 = Hs.a;
            iG = AbstractC0162el.g(i, iWidth, recyclerView2.getMinimumWidth());
            iG2 = AbstractC0162el.g(i2, (this.u * i3) + iC, this.b.getMinimumHeight());
        }
        this.b.setMeasuredDimension(iG, iG2);
    }

    @Override // rikka.shizuku.AbstractC0162el
    public final int x(C0285il c0285il, C0408ml c0408ml) {
        if (this.t == 1) {
            return Math.min(this.p, c0408ml.b());
        }
        return -1;
    }
}
