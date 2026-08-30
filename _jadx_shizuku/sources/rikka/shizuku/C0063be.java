package rikka.shizuku;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.CancellationException;

/* JADX INFO: renamed from: rikka.shizuku.be, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0063be extends W7 implements J9 {
    public final Handler b;
    public final boolean c;
    public final C0063be d;

    public C0063be(Handler handler, boolean z) {
        this.b = handler;
        this.c = z;
        this.d = z ? this : new C0063be(handler, true);
    }

    @Override // rikka.shizuku.W7
    public final void d(V7 v7, Runnable runnable) {
        if (this.b.post(runnable)) {
            return;
        }
        CancellationException cancellationException = new CancellationException("The task was rejected, the handler underlying the dispatcher '" + this + "' was closed");
        Ef ef = (Ef) v7.g(Fe.a);
        if (ef != null) {
            ef.i(cancellationException);
        }
        C0550r9 c0550r9 = AbstractC0090ca.a;
        ExecutorC0243h9.b.d(v7, runnable);
    }

    @Override // rikka.shizuku.W7
    public final boolean e(V7 v7) {
        return (this.c && AbstractC0046as.c(Looper.myLooper(), this.b.getLooper())) ? false : true;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof C0063be)) {
            return false;
        }
        C0063be c0063be = (C0063be) obj;
        return c0063be.b == this.b && c0063be.c == this.c;
    }

    public final int hashCode() {
        return System.identityHashCode(this.b) ^ (this.c ? 1231 : 1237);
    }

    @Override // rikka.shizuku.W7
    public final String toString() {
        C0063be c0063be;
        String str;
        C0550r9 c0550r9 = AbstractC0090ca.a;
        C0063be c0063be2 = AbstractC0713wh.a;
        if (this == c0063be2) {
            str = "Dispatchers.Main";
        } else {
            try {
                c0063be = c0063be2.d;
            } catch (UnsupportedOperationException unused) {
                c0063be = null;
            }
            str = this == c0063be ? "Dispatchers.Main.immediate" : null;
        }
        if (str != null) {
            return str;
        }
        String string = this.b.toString();
        return this.c ? AbstractC0535qo.c(string, ".immediate") : string;
    }
}
