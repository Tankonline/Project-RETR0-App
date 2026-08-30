package rikka.shizuku;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/* JADX INFO: loaded from: classes.dex */
public final class Z9 extends AbstractC0059ba implements InterfaceC0088c8, G7 {
    public static final /* synthetic */ AtomicReferenceFieldUpdater h = AtomicReferenceFieldUpdater.newUpdater(Z9.class, Object.class, "_reusableCancellableContinuation$volatile");
    private volatile /* synthetic */ Object _reusableCancellableContinuation$volatile;
    public final W7 d;
    public final H7 e;
    public Object f;
    public final Object g;

    public Z9(W7 w7, H7 h7) {
        super(0L, false);
        this.c = -1;
        this.d = w7;
        this.e = h7;
        this.f = X8.g;
        this.g = h7.b.l(0, AbstractC0599sr.s);
    }

    @Override // rikka.shizuku.InterfaceC0088c8
    public final InterfaceC0088c8 d() {
        return this.e;
    }

    @Override // rikka.shizuku.G7
    public final V7 e() {
        return this.e.b;
    }

    @Override // rikka.shizuku.G7
    public final void h(Object obj) throws Y9 {
        Throwable thA = AbstractC0102cm.a(obj);
        Object j6 = thA == null ? obj : new J6(thA);
        H7 h7 = this.e;
        V7 v7 = h7.b;
        W7 w7 = this.d;
        try {
            if (w7.e(v7)) {
                this.f = j6;
                this.c = 0;
                X8.W(w7, v7, this);
                return;
            }
            ThreadLocal threadLocal = Np.a;
            AbstractC0707wb c0177f5 = (AbstractC0707wb) threadLocal.get();
            if (c0177f5 == null) {
                c0177f5 = new C0177f5(Thread.currentThread());
                threadLocal.set(c0177f5);
            }
            long j = c0177f5.b;
            if (j >= 4294967296L) {
                this.f = j6;
                this.c = 0;
                C0084c4 c0084c4 = c0177f5.d;
                if (c0084c4 == null) {
                    c0084c4 = new C0084c4();
                    c0177f5.d = c0084c4;
                }
                c0084c4.addLast(this);
                return;
            }
            c0177f5.b = 4294967296L + j;
            try {
                Object objO = AbstractC0599sr.O(v7, this.g);
                try {
                    h7.h(obj);
                    AbstractC0599sr.E(v7, objO);
                    while (c0177f5.n()) {
                    }
                } catch (Throwable th) {
                    AbstractC0599sr.E(v7, objO);
                    throw th;
                }
            } catch (Throwable th2) {
                try {
                    a(th2);
                } finally {
                    c0177f5.h();
                }
            }
        } catch (Throwable th3) {
            throw new Y9(th3, w7, v7);
        }
    }

    public final String toString() {
        return "DispatchedContinuation[" + this.d + ", " + X8.Z(this.e) + ']';
    }
}
