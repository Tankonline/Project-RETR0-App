package rikka.shizuku;

/* JADX INFO: renamed from: rikka.shizuku.zf, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0804zf extends H7 {
    public int d;
    public final /* synthetic */ InterfaceC0585sd e;
    public final /* synthetic */ G7 f;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0804zf(G7 g7, V7 v7, InterfaceC0585sd interfaceC0585sd, G7 g8) {
        super(g7, v7);
        this.e = interfaceC0585sd;
        this.f = g8;
    }

    @Override // rikka.shizuku.Q4
    public final Object j(Object obj) throws Throwable {
        int i = this.d;
        if (i != 0) {
            if (i != 1) {
                throw new IllegalStateException("This coroutine had already completed");
            }
            this.d = 2;
            AbstractC0046as.V(obj);
            return obj;
        }
        this.d = 1;
        AbstractC0046as.V(obj);
        InterfaceC0585sd interfaceC0585sd = this.e;
        AbstractC0599sr.b(interfaceC0585sd);
        return interfaceC0585sd.f(this.f, this);
    }
}
