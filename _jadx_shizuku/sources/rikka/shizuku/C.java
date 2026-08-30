package rikka.shizuku;

import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public abstract class C {
    public final /* synthetic */ int a;
    public final Object b;

    public /* synthetic */ C(int i, Object obj) {
        this.a = i;
        this.b = obj;
    }

    public void a(AbstractC0633u abstractC0633u) {
        if (!((Class) this.b).isInstance(abstractC0633u)) {
            throw new IllegalStateException("unexpected object: ".concat(abstractC0633u.getClass().getName()));
        }
    }

    public AbstractC0633u b(AbstractC0726x abstractC0726x) {
        throw new IllegalStateException("unexpected implicit constructed encoding");
    }

    public AbstractC0633u c(A8 a8) {
        throw new IllegalStateException("unexpected implicit primitive encoding");
    }

    public AbstractC0633u d(H4 h4, boolean z) {
        AbstractC0726x e4;
        AbstractC0633u abstractC0633uB;
        if (128 != h4.b) {
            throw new IllegalStateException("this method only valid for CONTEXT_SPECIFIC tags");
        }
        InterfaceC0202g interfaceC0202g = h4.d;
        if (!z) {
            int i = h4.a;
            if (1 == i) {
                throw new IllegalStateException("object explicit - implicit expected.");
            }
            AbstractC0633u abstractC0633uD = interfaceC0202g.d();
            if (i == 3) {
                switch (h4.e) {
                    case 0:
                        e4 = new E4(abstractC0633uD);
                        break;
                    case 1:
                        D8 d8 = new D8(abstractC0633uD);
                        d8.c = -1;
                        e4 = d8;
                        break;
                    default:
                        Q8 q8 = new Q8(abstractC0633uD);
                        q8.c = -1;
                        e4 = q8;
                        break;
                }
                abstractC0633uB = b(e4);
            } else if (i != 4) {
                a(abstractC0633uD);
                abstractC0633uB = abstractC0633uD;
            } else {
                abstractC0633uB = abstractC0633uD instanceof AbstractC0726x ? b((AbstractC0726x) abstractC0633uD) : c((A8) abstractC0633uD);
            }
        } else {
            if (!h4.s()) {
                throw new IllegalStateException("object explicit - implicit expected.");
            }
            abstractC0633uB = interfaceC0202g.d();
            a(abstractC0633uB);
        }
        a(abstractC0633uB);
        return abstractC0633uB;
    }

    public boolean e() {
        int i;
        C0751xo c0751xo = (C0751xo) this.b;
        View view = c0751xo.c.G;
        if (view != null) {
            i = 4;
            if (view.getAlpha() != 0.0f || view.getVisibility() != 0) {
                int visibility = view.getVisibility();
                if (visibility == 0) {
                    i = 2;
                } else if (visibility != 4) {
                    if (visibility != 8) {
                        throw new IllegalArgumentException(AbstractC0535qo.b(visibility, "Unknown visibility "));
                    }
                    i = 3;
                }
            }
        } else {
            i = 0;
        }
        int i2 = c0751xo.a;
        if (i != i2) {
            return (i == 2 || i2 == 2) ? false : true;
        }
        return true;
    }

    public boolean equals(Object obj) {
        switch (this.a) {
            case 0:
                return this == obj;
            default:
                return super.equals(obj);
        }
    }
}
