package defpackage;

import java.util.concurrent.CancellationException;

/* JADX INFO: renamed from: a, reason: case insensitive filesystem */
/* JADX INFO: loaded from: assets/rish_shizuku.dex */
public final class C0000a {
    public static final C0000a b;
    public static final C0000a c;
    public final CancellationException a;

    static {
        if (AbstractFutureC0006g.d) {
            c = null;
            b = null;
        } else {
            c = new C0000a(false, null);
            b = new C0000a(true, null);
        }
    }

    public C0000a(boolean z, CancellationException cancellationException) {
        this.a = cancellationException;
    }
}
