package rikka.shizuku;

import android.content.Context;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

/* JADX INFO: renamed from: rikka.shizuku.lc, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class CallableC0368lc implements Callable {
    public final /* synthetic */ int a;
    public final /* synthetic */ String b;
    public final /* synthetic */ Context c;
    public final /* synthetic */ int d;
    public final /* synthetic */ Object e;

    public /* synthetic */ CallableC0368lc(String str, Context context, Object obj, int i, int i2) {
        this.a = i2;
        this.b = str;
        this.c = context;
        this.e = obj;
        this.d = i;
    }

    @Override // java.util.concurrent.Callable
    public final Object call() {
        switch (this.a) {
            case 0:
                Object[] objArr = {(C0277ic) this.e};
                ArrayList arrayList = new ArrayList(1);
                arrayList.add(objArr[0]);
                return AbstractC0461oc.b(this.b, this.c, Collections.unmodifiableList(arrayList), this.d);
            default:
                try {
                    return AbstractC0461oc.b(this.b, this.c, (List) this.e, this.d);
                } catch (Throwable unused) {
                    return new C0430nc(-3);
                }
        }
    }
}
