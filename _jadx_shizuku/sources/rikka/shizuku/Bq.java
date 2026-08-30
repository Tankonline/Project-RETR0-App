package rikka.shizuku;

import rikka.html.text.style.BulletSpan;

/* JADX INFO: loaded from: classes.dex */
public final class Bq extends AbstractC0753xq {
    public final /* synthetic */ int a;
    public AbstractC0722wq b;

    public /* synthetic */ Bq() {
        this.a = 1;
    }

    @Override // rikka.shizuku.AbstractC0753xq, rikka.shizuku.InterfaceC0691vq
    public void b(AbstractC0722wq abstractC0722wq) {
        switch (this.a) {
            case 1:
                C0638u4 c0638u4 = (C0638u4) this.b;
                int i = c0638u4.E - 1;
                c0638u4.E = i;
                if (i == 0) {
                    c0638u4.F = false;
                    c0638u4.m();
                }
                abstractC0722wq.z(this);
                break;
            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                this.b.B();
                abstractC0722wq.z(this);
                break;
        }
    }

    @Override // rikka.shizuku.AbstractC0753xq, rikka.shizuku.InterfaceC0691vq
    public void e(AbstractC0722wq abstractC0722wq) {
        switch (this.a) {
            case 0:
                C0638u4 c0638u4 = (C0638u4) this.b;
                c0638u4.C.remove(abstractC0722wq);
                if (!c0638u4.s()) {
                    c0638u4.w(c0638u4, C0579s7.d, false);
                    c0638u4.r = true;
                    c0638u4.w(c0638u4, C0579s7.c, false);
                }
                break;
        }
    }

    @Override // rikka.shizuku.AbstractC0753xq, rikka.shizuku.InterfaceC0691vq
    public void f(AbstractC0722wq abstractC0722wq) {
        switch (this.a) {
            case 1:
                C0638u4 c0638u4 = (C0638u4) this.b;
                if (!c0638u4.F) {
                    c0638u4.J();
                    c0638u4.F = true;
                }
                break;
        }
    }

    public /* synthetic */ Bq(AbstractC0722wq abstractC0722wq, int i) {
        this.a = i;
        this.b = abstractC0722wq;
    }
}
