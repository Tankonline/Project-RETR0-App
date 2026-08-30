package rikka.shizuku;

import android.view.View;
import android.view.animation.BaseInterpolator;
import java.util.ArrayList;

/* JADX INFO: renamed from: rikka.shizuku.dt, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0139dt {
    public BaseInterpolator c;
    public AbstractC0688vn d;
    public boolean e;
    public long b = -1;
    public final C0320jq f = new C0320jq(this);
    public final ArrayList a = new ArrayList();

    public final void a() {
        if (this.e) {
            ArrayList arrayList = this.a;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                ((C0109ct) obj).b();
            }
            this.e = false;
        }
    }

    public final void b() {
        View view;
        if (this.e) {
            return;
        }
        ArrayList arrayList = this.a;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            C0109ct c0109ct = (C0109ct) obj;
            long j = this.b;
            if (j >= 0) {
                c0109ct.c(j);
            }
            BaseInterpolator baseInterpolator = this.c;
            if (baseInterpolator != null && (view = (View) c0109ct.a.get()) != null) {
                view.animate().setInterpolator(baseInterpolator);
            }
            if (this.d != null) {
                c0109ct.d(this.f);
            }
            View view2 = (View) c0109ct.a.get();
            if (view2 != null) {
                view2.animate().start();
            }
        }
        this.e = true;
    }
}
