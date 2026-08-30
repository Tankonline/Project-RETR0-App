package rikka.shizuku;

import android.view.ViewGroup;

/* JADX INFO: loaded from: classes.dex */
public final class M5 extends AbstractC0753xq {
    public boolean a = false;
    public final ViewGroup b;

    public M5(ViewGroup viewGroup) {
        this.b = viewGroup;
    }

    @Override // rikka.shizuku.AbstractC0753xq, rikka.shizuku.InterfaceC0691vq
    public final void b(AbstractC0722wq abstractC0722wq) {
        if (!this.a) {
            Hn.n(this.b, false);
        }
        abstractC0722wq.z(this);
    }

    @Override // rikka.shizuku.AbstractC0753xq, rikka.shizuku.InterfaceC0691vq
    public final void c() {
        Hn.n(this.b, false);
    }

    @Override // rikka.shizuku.AbstractC0753xq, rikka.shizuku.InterfaceC0691vq
    public final void d() {
        Hn.n(this.b, true);
    }

    @Override // rikka.shizuku.AbstractC0753xq, rikka.shizuku.InterfaceC0691vq
    public final void e(AbstractC0722wq abstractC0722wq) {
        Hn.n(this.b, false);
        this.a = true;
    }
}
