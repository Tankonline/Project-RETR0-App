package rikka.shizuku;

import java.util.ArrayList;

/* JADX INFO: renamed from: rikka.shizuku.yq, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0784yq extends AbstractC0753xq {
    public final /* synthetic */ C0298j4 a;
    public final /* synthetic */ ViewTreeObserverOnPreDrawListenerC0815zq b;

    public C0784yq(ViewTreeObserverOnPreDrawListenerC0815zq viewTreeObserverOnPreDrawListenerC0815zq, C0298j4 c0298j4) {
        this.b = viewTreeObserverOnPreDrawListenerC0815zq;
        this.a = c0298j4;
    }

    @Override // rikka.shizuku.AbstractC0753xq, rikka.shizuku.InterfaceC0691vq
    public final void b(AbstractC0722wq abstractC0722wq) {
        ((ArrayList) this.a.get(this.b.b)).remove(abstractC0722wq);
        abstractC0722wq.z(this);
    }
}
