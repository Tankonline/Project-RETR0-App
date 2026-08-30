package rikka.shizuku;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes.dex */
public final class Uo implements Callable {
    public final InputStream a;
    public final List b;
    public final /* synthetic */ int c;

    public Uo(InputStream inputStream, List list, int i) {
        this.c = i;
        this.a = inputStream;
        this.b = list;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x0031  */
    public final String a(boolean z) throws IOException {
        boolean z2;
        List list;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.a, StandardCharsets.UTF_8));
        do {
            String line = bufferedReader.readLine();
            z2 = false;
            if (line != null) {
                int length = line.length();
                int i = length - 36;
                boolean zStartsWith = line.startsWith(Sj.k, i);
                if (!zStartsWith) {
                    list = this.b;
                    if (list != null) {
                        list.add(line);
                    }
                    z2 = !zStartsWith;
                } else if (length != 36) {
                    line = line.substring(0, i);
                    list = this.b;
                    if (list != null) {
                        list.add(line);
                    }
                    z2 = !zStartsWith;
                }
            }
        } while (z2);
        if (z) {
            return bufferedReader.readLine();
        }
        return null;
    }

    @Override // java.util.concurrent.Callable
    public final Object call() throws IOException {
        int i;
        switch (this.c) {
            case 0:
                a(false);
                return null;
            default:
                String strA = a(true);
                if (strA == null) {
                    i = 1;
                } else {
                    try {
                        i = Integer.parseInt(strA);
                    } catch (NumberFormatException unused) {
                        return 1;
                    }
                }
                return Integer.valueOf(i);
        }
    }
}
