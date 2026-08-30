package rikka.shizuku;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
public final class Dn extends FilterInputStream implements AutoCloseable {
    public Dn(InputStream inputStream) {
        super(inputStream);
    }

    public final void a() throws IOException {
        ((FilterInputStream) this).in.close();
    }

    @Override // java.io.FilterInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
    }
}
