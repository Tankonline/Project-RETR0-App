package rikka.shizuku;

/* JADX INFO: renamed from: rikka.shizuku.wb, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0707wb extends W7 {
    public long b;
    public boolean c;
    public C0084c4 d;

    public final void h() {
        long j = this.b - 4294967296L;
        this.b = j;
        if (j <= 0 && this.c) {
            o();
        }
    }

    public abstract Thread m();

    public final boolean n() {
        C0084c4 c0084c4 = this.d;
        if (c0084c4 == null) {
            return false;
        }
        AbstractC0059ba abstractC0059ba = (AbstractC0059ba) (c0084c4.isEmpty() ? null : c0084c4.removeFirst());
        if (abstractC0059ba == null) {
            return false;
        }
        abstractC0059ba.run();
        return true;
    }

    public abstract void o();
}
