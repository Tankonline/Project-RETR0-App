package rikka.shizuku;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

/* JADX INFO: renamed from: rikka.shizuku.o4, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0453o4 extends Lq {
    public static final C0422n4 c = new C0422n4();
    public final Class a;
    public final Nq b;

    public C0453o4(Rd rd, Lq lq, Class cls) {
        this.b = new Nq(rd, lq, cls);
        this.a = cls;
    }

    @Override // rikka.shizuku.Lq
    public final Object b(Vf vf) {
        if (vf.v() == 9) {
            vf.r();
            return null;
        }
        ArrayList arrayList = new ArrayList();
        vf.a();
        while (vf.i()) {
            arrayList.add(this.b.b.b(vf));
        }
        vf.e();
        int size = arrayList.size();
        Class cls = this.a;
        if (!cls.isPrimitive()) {
            return arrayList.toArray((Object[]) Array.newInstance((Class<?>) cls, size));
        }
        Object objNewInstance = Array.newInstance((Class<?>) cls, size);
        for (int i = 0; i < size; i++) {
            Array.set(objNewInstance, i, arrayList.get(i));
        }
        return objNewInstance;
    }

    @Override // rikka.shizuku.Lq
    public final void c(Wf wf, Object obj) throws IOException {
        if (obj == null) {
            wf.i();
            return;
        }
        wf.b();
        int length = Array.getLength(obj);
        for (int i = 0; i < length; i++) {
            this.b.c(wf, Array.get(obj, i));
        }
        wf.e();
    }
}
