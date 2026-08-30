package rikka.shizuku;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class I4 implements InterfaceC0202g, InterfaceC0126df {
    public final int a;
    public final int b;
    public final C0788z c;

    public I4(int i, int i2, C0788z c0788z) {
        this.a = i;
        this.b = i2;
        this.c = c0788z;
    }

    @Override // rikka.shizuku.InterfaceC0126df
    public AbstractC0633u a() {
        return this.c.e(this.a, this.b);
    }

    @Override // rikka.shizuku.InterfaceC0202g
    public final AbstractC0633u d() {
        try {
            return a();
        } catch (IOException e) {
            throw new C0602t(0, e.getMessage());
        }
    }
}
