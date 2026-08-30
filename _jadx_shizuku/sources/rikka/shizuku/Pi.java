package rikka.shizuku;

import android.os.Looper;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class Pi {
    public static final Object k = new Object();
    public final Object a = new Object();
    public final C0502pm b = new C0502pm();
    public int c = 0;
    public boolean d;
    public volatile Object e;
    public volatile Object f;
    public int g;
    public boolean h;
    public boolean i;
    public final RunnableC0607t4 j;

    public Pi() {
        Object obj = k;
        this.f = obj;
        this.j = new RunnableC0607t4(9, this);
        this.e = obj;
        this.g = -1;
    }

    public static void a(String str) {
        C0022a4.C().b.getClass();
        if (Looper.getMainLooper().getThread() != Thread.currentThread()) {
            throw new IllegalStateException(AbstractC0535qo.d("Cannot invoke ", str, " on a background thread"));
        }
    }

    public final void b(AbstractC0220gh abstractC0220gh) {
        if (abstractC0220gh.b) {
            if (!abstractC0220gh.e()) {
                abstractC0220gh.b(false);
                return;
            }
            int i = abstractC0220gh.c;
            int i2 = this.g;
            if (i >= i2) {
                return;
            }
            abstractC0220gh.c = i2;
            abstractC0220gh.a.b(this.e);
        }
    }

    public final void c(AbstractC0220gh abstractC0220gh) {
        if (this.h) {
            this.i = true;
            return;
        }
        this.h = true;
        do {
            this.i = false;
            if (abstractC0220gh != null) {
                b(abstractC0220gh);
                abstractC0220gh = null;
            } else {
                C0502pm c0502pm = this.b;
                c0502pm.getClass();
                C0440nm c0440nm = new C0440nm(c0502pm);
                c0502pm.c.put(c0440nm, Boolean.FALSE);
                while (c0440nm.hasNext()) {
                    b((AbstractC0220gh) ((Map.Entry) c0440nm.next()).getValue());
                    if (this.i) {
                        break;
                    }
                }
            }
        } while (this.i);
        this.h = false;
    }

    public final void d(InterfaceC0743xg interfaceC0743xg, InterfaceC0313jj interfaceC0313jj) {
        Object obj;
        a("observe");
        if (interfaceC0743xg.e().c == EnumC0588sg.a) {
            return;
        }
        C0189fh c0189fh = new C0189fh(this, interfaceC0743xg, interfaceC0313jj);
        C0502pm c0502pm = this.b;
        C0409mm c0409mmA = c0502pm.a(interfaceC0313jj);
        if (c0409mmA != null) {
            obj = c0409mmA.b;
        } else {
            C0409mm c0409mm = new C0409mm(interfaceC0313jj, c0189fh);
            c0502pm.d++;
            C0409mm c0409mm2 = c0502pm.b;
            if (c0409mm2 == null) {
                c0502pm.a = c0409mm;
                c0502pm.b = c0409mm;
            } else {
                c0409mm2.c = c0409mm;
                c0409mm.d = c0409mm2;
                c0502pm.b = c0409mm;
            }
            obj = null;
        }
        AbstractC0220gh abstractC0220gh = (AbstractC0220gh) obj;
        if (abstractC0220gh != null && !abstractC0220gh.d(interfaceC0743xg)) {
            throw new IllegalArgumentException("Cannot add the same observer with different lifecycles");
        }
        if (abstractC0220gh != null) {
            return;
        }
        interfaceC0743xg.e().a(c0189fh);
    }

    public final void e(Object obj) {
        boolean z;
        synchronized (this.a) {
            z = this.f == k;
            this.f = obj;
        }
        if (z) {
            C0022a4 c0022a4C = C0022a4.C();
            RunnableC0607t4 runnableC0607t4 = this.j;
            H9 h9 = c0022a4C.b;
            if (h9.c == null) {
                synchronized (h9.b) {
                    try {
                        if (h9.c == null) {
                            h9.c = H9.C(Looper.getMainLooper());
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
            h9.c.post(runnableC0607t4);
        }
    }

    public final void f(Object obj) {
        a("setValue");
        this.g++;
        this.e = obj;
        c(null);
    }
}
