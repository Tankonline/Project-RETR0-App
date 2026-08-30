package rikka.shizuku;

import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
public abstract class Cg extends InputStream {
    public final InputStream a;
    public final int b;

    public Cg(InputStream inputStream, int i) {
        this.a = inputStream;
        this.b = i;
    }

    public final void a() {
        InputStream inputStream = this.a;
        if (inputStream instanceof C0249hf) {
            C0249hf c0249hf = (C0249hf) inputStream;
            c0249hf.f = true;
            c0249hf.b();
        }
    }
}
