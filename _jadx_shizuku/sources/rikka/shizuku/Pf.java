package rikka.shizuku;

import java.io.IOException;
import java.io.StringWriter;

/* JADX INFO: loaded from: classes.dex */
public abstract class Pf {
    public final String toString() {
        try {
            StringWriter stringWriter = new StringWriter();
            Wf wf = new Wf(stringWriter);
            wf.h = 1;
            AbstractC0568rr.z.getClass();
            Qf.e(wf, this);
            return stringWriter.toString();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
