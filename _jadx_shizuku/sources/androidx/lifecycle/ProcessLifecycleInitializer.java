package androidx.lifecycle;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import java.util.HashSet;
import java.util.List;
import rikka.shizuku.AbstractC0650ug;
import rikka.shizuku.C0214gb;
import rikka.shizuku.C0619tg;
import rikka.shizuku.C0623tk;
import rikka.shizuku.C0654uk;
import rikka.shizuku.EnumC0557rg;
import rikka.shizuku.InterfaceC0402mf;
import rikka.shizuku.K3;

/* JADX INFO: loaded from: classes.dex */
public final class ProcessLifecycleInitializer implements InterfaceC0402mf {
    @Override // rikka.shizuku.InterfaceC0402mf
    public final List a() {
        return C0214gb.a;
    }

    @Override // rikka.shizuku.InterfaceC0402mf
    public final Object b(Context context) {
        if (!((HashSet) K3.l(context).b).contains(ProcessLifecycleInitializer.class)) {
            throw new IllegalStateException("ProcessLifecycleInitializer cannot be initialized lazily.\n               Please ensure that you have:\n               <meta-data\n                   android:name='androidx.lifecycle.ProcessLifecycleInitializer'\n                   android:value='androidx.startup' />\n               under InitializationProvider in your AndroidManifest.xml");
        }
        if (!AbstractC0650ug.a.getAndSet(true)) {
            ((Application) context.getApplicationContext()).registerActivityLifecycleCallbacks(new C0619tg());
        }
        C0654uk c0654uk = C0654uk.i;
        c0654uk.getClass();
        c0654uk.e = new Handler();
        c0654uk.f.d(EnumC0557rg.ON_CREATE);
        ((Application) context.getApplicationContext()).registerActivityLifecycleCallbacks(new C0623tk(c0654uk));
        return c0654uk;
    }
}
