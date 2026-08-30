package rikka.shizuku;

/* JADX INFO: loaded from: classes.dex */
public final class X2 extends AbstractViewOnTouchListenerC0708wc {
    public final /* synthetic */ C0206g3 j;
    public final /* synthetic */ C0297j3 k;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public X2(C0297j3 c0297j3, C0297j3 c0297j4, C0206g3 c0206g3) {
        super(c0297j4);
        this.k = c0297j3;
        this.j = c0206g3;
    }

    @Override // rikka.shizuku.AbstractViewOnTouchListenerC0708wc
    public final Yn b() {
        return this.j;
    }

    @Override // rikka.shizuku.AbstractViewOnTouchListenerC0708wc
    public final boolean c() {
        C0297j3 c0297j3 = this.k;
        if (c0297j3.f.a()) {
            return true;
        }
        c0297j3.f.d(AbstractC0021a3.b(c0297j3), AbstractC0021a3.a(c0297j3));
        return true;
    }
}
