package rikka.shizuku;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/* JADX INFO: loaded from: classes.dex */
public final class H6 implements Closeable, AutoCloseable {
    public final String[] a;

    public H6(String[] strArr) {
        this.a = strArr;
    }

    public final void a(OutputStream outputStream) throws IOException {
        for (String str : this.a) {
            outputStream.write(str.getBytes(StandardCharsets.UTF_8));
            outputStream.write(10);
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
    }
}
