package rikka.shizuku;

/* JADX INFO: renamed from: rikka.shizuku.yf, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0773yf extends AbstractC0040am {
    public int b;
    public final /* synthetic */ InterfaceC0585sd c;
    public final /* synthetic */ G7 d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0773yf(G7 g7, G7 g8, InterfaceC0585sd interfaceC0585sd) {
        super(g7);
        this.c = interfaceC0585sd;
        this.d = g8;
    }

    @Override // rikka.shizuku.Q4
    public final Object j(Object obj) throws Throwable {
        int i = this.b;
        if (i != 0) {
            if (i != 1) {
                throw new IllegalStateException("This coroutine had already completed");
            }
            this.b = 2;
            AbstractC0046as.V(obj);
            return obj;
        }
        this.b = 1;
        AbstractC0046as.V(obj);
        InterfaceC0585sd interfaceC0585sd = this.c;
        AbstractC0599sr.b(interfaceC0585sd);
        return interfaceC0585sd.f(this.d, this);
    }
}
