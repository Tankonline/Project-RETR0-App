package rikka.shizuku;

/* JADX INFO: renamed from: rikka.shizuku.wn, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0719wn {
    public final /* synthetic */ N3 a;

    public C0719wn(N3 n3) {
        this.a = n3;
    }

    public final void a(int i) {
        synchronized (Nn.m) {
            Nn.o.removeIf(new In(2, this));
        }
        if (i == 0) {
            this.a.run();
            return;
        }
        System.err.println("Permission denied");
        System.err.flush();
        System.exit(1);
    }
}
