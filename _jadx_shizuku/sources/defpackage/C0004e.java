package defpackage;

/* JADX INFO: renamed from: e, reason: case insensitive filesystem */
/* JADX INFO: loaded from: assets/rish_shizuku.dex */
public final class C0004e extends t {
    @Override // defpackage.t
    public final boolean c(AbstractFutureC0006g abstractFutureC0006g, C0002c c0002c) {
        C0002c c0002c2 = C0002c.b;
        synchronized (abstractFutureC0006g) {
            try {
                if (abstractFutureC0006g.b != c0002c) {
                    return false;
                }
                abstractFutureC0006g.b = c0002c2;
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // defpackage.t
    public final boolean d(AbstractFutureC0006g abstractFutureC0006g, Object obj, Object obj2) {
        synchronized (abstractFutureC0006g) {
            try {
                if (abstractFutureC0006g.a != obj) {
                    return false;
                }
                abstractFutureC0006g.a = obj2;
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // defpackage.t
    public final boolean e(AbstractFutureC0006g abstractFutureC0006g, C0005f c0005f, C0005f c0005f2) {
        synchronized (abstractFutureC0006g) {
            try {
                if (abstractFutureC0006g.c != c0005f) {
                    return false;
                }
                abstractFutureC0006g.c = c0005f2;
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // defpackage.t
    public final void l(C0005f c0005f, C0005f c0005f2) {
        c0005f.b = c0005f2;
    }

    @Override // defpackage.t
    public final void m(C0005f c0005f, Thread thread) {
        c0005f.a = thread;
    }
}
