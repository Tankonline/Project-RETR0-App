package androidx.emoji2.text;

import android.content.Context;
import androidx.lifecycle.ProcessLifecycleInitializer;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import rikka.shizuku.C0337kc;
import rikka.shizuku.C0805zg;
import rikka.shizuku.InterfaceC0402mf;
import rikka.shizuku.InterfaceC0743xg;
import rikka.shizuku.K3;
import rikka.shizuku.Ma;
import rikka.shizuku.Na;
import rikka.shizuku.Ys;

/* JADX INFO: loaded from: classes.dex */
public class EmojiCompatInitializer implements InterfaceC0402mf {
    @Override // rikka.shizuku.InterfaceC0402mf
    public final List a() {
        return Collections.singletonList(ProcessLifecycleInitializer.class);
    }

    @Override // rikka.shizuku.InterfaceC0402mf
    public final Object b(Context context) {
        Object objG;
        C0337kc c0337kc = new C0337kc(new Ys(context));
        c0337kc.a = 1;
        if (Ma.j == null) {
            synchronized (Ma.i) {
                try {
                    if (Ma.j == null) {
                        Ma.j = new Ma(c0337kc);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        K3 k3L = K3.l(context);
        k3L.getClass();
        synchronized (K3.e) {
            try {
                objG = ((HashMap) k3L.a).get(ProcessLifecycleInitializer.class);
                if (objG == null) {
                    objG = k3L.g(ProcessLifecycleInitializer.class, new HashSet());
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
        C0805zg c0805zgE = ((InterfaceC0743xg) objG).e();
        c0805zgE.a(new Na(this, c0805zgE));
        return Boolean.TRUE;
    }
}
