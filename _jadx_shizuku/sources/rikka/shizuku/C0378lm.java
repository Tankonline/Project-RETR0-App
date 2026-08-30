package rikka.shizuku;

import java.util.Iterator;

/* JADX INFO: renamed from: rikka.shizuku.lm, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0378lm extends AbstractC0471om implements Iterator {
    public C0409mm a;
    public C0409mm b;
    public final /* synthetic */ int c;

    public C0378lm(C0409mm c0409mm, C0409mm c0409mm2, int i) {
        this.c = i;
        this.a = c0409mm2;
        this.b = c0409mm;
    }

    @Override // rikka.shizuku.AbstractC0471om
    public final void a(C0409mm c0409mm) {
        C0409mm c0409mm2;
        C0409mm c0409mmB = null;
        if (this.a == c0409mm && c0409mm == this.b) {
            this.b = null;
            this.a = null;
        }
        C0409mm c0409mm3 = this.a;
        if (c0409mm3 == c0409mm) {
            switch (this.c) {
                case 0:
                    c0409mm2 = c0409mm3.d;
                    break;
                default:
                    c0409mm2 = c0409mm3.c;
                    break;
            }
            this.a = c0409mm2;
        }
        C0409mm c0409mm4 = this.b;
        if (c0409mm4 == c0409mm) {
            C0409mm c0409mm5 = this.a;
            if (c0409mm4 != c0409mm5 && c0409mm5 != null) {
                c0409mmB = b(c0409mm4);
            }
            this.b = c0409mmB;
        }
    }

    public final C0409mm b(C0409mm c0409mm) {
        switch (this.c) {
            case 0:
                return c0409mm.c;
            default:
                return c0409mm.d;
        }
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.b != null;
    }

    @Override // java.util.Iterator
    public final Object next() {
        C0409mm c0409mm = this.b;
        C0409mm c0409mm2 = this.a;
        this.b = (c0409mm == c0409mm2 || c0409mm2 == null) ? null : b(c0409mm);
        return c0409mm;
    }
}
