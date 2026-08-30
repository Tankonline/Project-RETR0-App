package rikka.shizuku;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* JADX INFO: renamed from: rikka.shizuku.jc, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0306jc implements La {
    public final Context a;
    public final C0277ic b;
    public final Ee c;
    public final Object d;
    public Handler e;
    public ThreadPoolExecutor f;
    public ThreadPoolExecutor g;
    public AbstractC0599sr h;

    public C0306jc(Context context, C0277ic c0277ic) {
        Ee ee = C0337kc.d;
        this.d = new Object();
        AbstractC0046as.j("Context cannot be null", context);
        this.a = context.getApplicationContext();
        this.b = c0277ic;
        this.c = ee;
    }

    public final void a() {
        synchronized (this.d) {
            try {
                this.h = null;
                Handler handler = this.e;
                if (handler != null) {
                    handler.removeCallbacks(null);
                }
                this.e = null;
                ThreadPoolExecutor threadPoolExecutor = this.g;
                if (threadPoolExecutor != null) {
                    threadPoolExecutor.shutdown();
                }
                this.f = null;
                this.g = null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final C0615tc b() {
        try {
            Ee ee = this.c;
            Context context = this.a;
            C0277ic c0277ic = this.b;
            ee.getClass();
            Object[] objArr = {c0277ic};
            ArrayList arrayList = new ArrayList(1);
            arrayList.add(objArr[0]);
            I1 i1A = AbstractC0246hc.a(context, Collections.unmodifiableList(arrayList));
            int i = i1A.a;
            if (i != 0) {
                throw new RuntimeException("fetchFonts failed (" + i + ")");
            }
            C0615tc[] c0615tcArr = (C0615tc[]) ((List) i1A.b).get(0);
            if (c0615tcArr == null || c0615tcArr.length == 0) {
                throw new RuntimeException("fetchFonts failed (empty result)");
            }
            return c0615tcArr[0];
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("provider not found", e);
        }
    }

    @Override // rikka.shizuku.La
    public final void k(AbstractC0599sr abstractC0599sr) {
        synchronized (this.d) {
            this.h = abstractC0599sr;
        }
        synchronized (this.d) {
            try {
                if (this.h == null) {
                    return;
                }
                if (this.f == null) {
                    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, 1, 15L, TimeUnit.SECONDS, new LinkedBlockingDeque(), new V6("emojiCompat"));
                    threadPoolExecutor.allowCoreThreadTimeOut(true);
                    this.g = threadPoolExecutor;
                    this.f = threadPoolExecutor;
                }
                this.f.execute(new G0(8, this));
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
