package rikka.shizuku;

import android.content.Context;
import android.content.pm.ApplicationInfo;

/* JADX INFO: loaded from: classes.dex */
public final class F3 extends AbstractC0443np implements InterfaceC0585sd {
    public final /* synthetic */ Context e;
    public final /* synthetic */ ApplicationInfo f;
    public final /* synthetic */ int g;
    public final /* synthetic */ int h;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public F3(Context context, ApplicationInfo applicationInfo, int i, int i2, G7 g7) {
        super(g7);
        this.e = context;
        this.f = applicationInfo;
        this.g = i;
        this.h = i2;
    }

    @Override // rikka.shizuku.InterfaceC0585sd
    public final Object f(Object obj, Object obj2) {
        return ((F3) i((InterfaceC0026a8) obj, (G7) obj2)).j(Pe.b);
    }

    @Override // rikka.shizuku.Q4
    public final G7 i(Object obj, G7 g7) {
        return new F3(this.e, this.f, this.g, this.h, g7);
    }

    @Override // rikka.shizuku.Q4
    public final Object j(Object obj) throws Throwable {
        AbstractC0046as.V(obj);
        H3 h3 = H3.a;
        return H3.b(this.e, this.f, this.g, this.h);
    }
}
