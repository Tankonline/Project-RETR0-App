package rikka.shizuku;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicIntegerArray;

/* JADX INFO: loaded from: classes.dex */
public class Oq extends Lq {
    @Override // rikka.shizuku.Lq
    public final Object b(Vf vf) {
        ArrayList arrayList = new ArrayList();
        vf.a();
        while (vf.i()) {
            try {
                arrayList.add(Integer.valueOf(vf.n()));
            } catch (NumberFormatException e) {
                throw new Rf(e);
            }
        }
        vf.e();
        int size = arrayList.size();
        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(size);
        for (int i = 0; i < size; i++) {
            atomicIntegerArray.set(i, ((Integer) arrayList.get(i)).intValue());
        }
        return atomicIntegerArray;
    }

    @Override // rikka.shizuku.Lq
    public final void c(Wf wf, Object obj) throws IOException {
        AtomicIntegerArray atomicIntegerArray = (AtomicIntegerArray) obj;
        wf.b();
        int length = atomicIntegerArray.length();
        for (int i = 0; i < length; i++) {
            wf.n(atomicIntegerArray.get(i));
        }
        wf.e();
    }
}
