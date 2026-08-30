package rikka.shizuku;

import java.util.Iterator;

/* JADX INFO: renamed from: rikka.shizuku.nm, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0440nm extends AbstractC0471om implements Iterator {
    public C0409mm a;
    public boolean b = true;
    public final /* synthetic */ C0502pm c;

    public C0440nm(C0502pm c0502pm) {
        this.c = c0502pm;
    }

    @Override // rikka.shizuku.AbstractC0471om
    public final void a(C0409mm c0409mm) {
        C0409mm c0409mm2 = this.a;
        if (c0409mm == c0409mm2) {
            C0409mm c0409mm3 = c0409mm2.d;
            this.a = c0409mm3;
            this.b = c0409mm3 == null;
        }
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        if (this.b) {
            return this.c.a != null;
        }
        C0409mm c0409mm = this.a;
        return (c0409mm == null || c0409mm.c == null) ? false : true;
    }

    @Override // java.util.Iterator
    public final Object next() {
        if (this.b) {
            this.b = false;
            this.a = this.c.a;
        } else {
            C0409mm c0409mm = this.a;
            this.a = c0409mm != null ? c0409mm.c : null;
        }
        return this.a;
    }
}
