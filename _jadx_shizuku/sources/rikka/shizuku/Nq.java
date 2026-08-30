package rikka.shizuku;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/* JADX INFO: loaded from: classes.dex */
public final class Nq extends Lq {
    public final Rd a;
    public final Lq b;
    public final Type c;

    public Nq(Rd rd, Lq lq, Type type) {
        this.a = rd;
        this.b = lq;
        this.c = type;
    }

    @Override // rikka.shizuku.Lq
    public final Object b(Vf vf) {
        return this.b.b(vf);
    }

    /* JADX WARN: Code duplicated, block: B:25:0x003c  */
    @Override // rikka.shizuku.Lq
    public final void c(Wf wf, Object obj) {
        Lq lqD;
        Type type = this.c;
        Type type2 = (obj == null || !((type instanceof Class) || (type instanceof TypeVariable))) ? type : obj.getClass();
        Lq lq = this.b;
        if (type2 != type) {
            Lq lqC = this.a.c(new C0630tr(type2));
            if (lqC instanceof AbstractC0779yl) {
                Lq lq2 = lq;
                while ((lq2 instanceof Wm) && (lqD = ((Wm) lq2).d()) != lq2) {
                    lq2 = lqD;
                }
                if (lq2 instanceof AbstractC0779yl) {
                    lq = lqC;
                }
            } else {
                lq = lqC;
            }
        }
        lq.c(wf, obj);
    }
}
