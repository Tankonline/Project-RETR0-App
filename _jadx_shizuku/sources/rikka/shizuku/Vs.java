package rikka.shizuku;

import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public abstract class Vs {
    public final Ws a = new Ws();

    public final void a() {
        Ws ws = this.a;
        if (ws != null && !ws.d) {
            ws.d = true;
            synchronized (ws.a) {
                try {
                    Iterator it = ws.b.values().iterator();
                    while (it.hasNext()) {
                        Ws.a((AutoCloseable) it.next());
                    }
                    Iterator it2 = ws.c.iterator();
                    while (it2.hasNext()) {
                        Ws.a((AutoCloseable) it2.next());
                    }
                    ws.c.clear();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        c();
    }

    public final AutoCloseable b(String str) {
        AutoCloseable autoCloseable;
        Ws ws = this.a;
        if (ws == null) {
            return null;
        }
        synchronized (ws.a) {
            autoCloseable = (AutoCloseable) ws.b.get(str);
        }
        return autoCloseable;
    }

    public void c() {
    }
}
