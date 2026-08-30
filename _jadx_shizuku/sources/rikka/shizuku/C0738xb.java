package rikka.shizuku;

import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: renamed from: rikka.shizuku.xb, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0738xb extends Lq {
    public volatile Lq a;
    public final /* synthetic */ boolean b;
    public final /* synthetic */ boolean c;
    public final /* synthetic */ Rd d;
    public final /* synthetic */ C0630tr e;
    public final /* synthetic */ C0769yb f;

    public C0738xb(C0769yb c0769yb, boolean z, boolean z2, Rd rd, C0630tr c0630tr) {
        this.f = c0769yb;
        this.b = z;
        this.c = z2;
        this.d = rd;
        this.e = c0630tr;
    }

    /* JADX WARN: Code duplicated, block: B:29:0x0074  */
    /* JADX WARN: Code duplicated, block: B:37:0x008a A[EDGE_INSN: B:37:0x008a->B:38:0x008e BREAK  A[LOOP:0: B:27:0x006e->B:48:0x006e]] */
    /* JADX WARN: Code duplicated, block: B:39:0x0091  */
    /* JADX WARN: Code duplicated, block: B:43:0x0088 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:44:0x0080 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:45:0x007e A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:46:0x0086 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:47:0x007c A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:49:0x006e A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:50:0x006e A[SYNTHETIC] */
    /* JADX WARN: Instruction removed from duplicated block: B:39:0x0091, please report this as an issue */
    @Override // rikka.shizuku.Lq
    public final Object b(Vf vf) throws IOException {
        Iterator it;
        boolean z;
        Mq mq;
        Lq lqA;
        if (this.b) {
            vf.B();
            return null;
        }
        Lq lqC = this.a;
        if (lqC == null) {
            Rd rd = this.d;
            Mq mq2 = this.f;
            C0630tr c0630tr = this.e;
            rd.getClass();
            Mf mf = Nf.c;
            Nf nf = rd.d;
            if (mq2 == mf) {
                nf.getClass();
            } else {
                ConcurrentHashMap concurrentHashMap = nf.b;
                Class cls = c0630tr.a;
                Mq mq3 = (Mq) concurrentHashMap.get(cls);
                if (mq3 == null) {
                    Lf lf = (Lf) cls.getAnnotation(Lf.class);
                    if (lf != null) {
                        Class clsValue = lf.value();
                        if (Mq.class.isAssignableFrom(clsValue)) {
                            Mq mq4 = (Mq) nf.a.b(new C0630tr(clsValue), true).d();
                            Mq mq5 = (Mq) nf.b.putIfAbsent(cls, mq4);
                            if (mq5 != null) {
                                mq4 = mq5;
                            }
                            if (mq4 == mq2) {
                            }
                        }
                    }
                } else if (mq3 == mq2) {
                }
                it = rd.e.iterator();
                z = false;
                while (true) {
                    if (it.hasNext()) {
                        if (!z) {
                            lqC = rd.c(c0630tr);
                            break;
                        }
                        throw new IllegalArgumentException("GSON cannot serialize or deserialize " + c0630tr);
                    }
                    mq = (Mq) it.next();
                    if (!z) {
                        lqA = mq.a(rd, c0630tr);
                        if (lqA != null) {
                            lqC = lqA;
                            break;
                        }
                    } else if (mq == mq2) {
                        z = true;
                    }
                }
                this.a = lqC;
            }
            mq2 = nf;
            it = rd.e.iterator();
            z = false;
            while (true) {
                if (it.hasNext()) {
                    if (!z) {
                        lqC = rd.c(c0630tr);
                        break;
                    }
                    throw new IllegalArgumentException("GSON cannot serialize or deserialize " + c0630tr);
                }
                mq = (Mq) it.next();
                if (!z) {
                    lqA = mq.a(rd, c0630tr);
                    if (lqA != null) {
                        lqC = lqA;
                        break;
                    }
                } else if (mq == mq2) {
                    z = true;
                }
            }
            this.a = lqC;
        }
        return lqC.b(vf);
    }

    /* JADX WARN: Code duplicated, block: B:29:0x0073  */
    /* JADX WARN: Code duplicated, block: B:37:0x0089 A[EDGE_INSN: B:37:0x0089->B:38:0x008d BREAK  A[LOOP:0: B:27:0x006d->B:48:0x006d]] */
    /* JADX WARN: Code duplicated, block: B:39:0x0090  */
    /* JADX WARN: Code duplicated, block: B:43:0x0087 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:44:0x007f A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:45:0x007d A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:46:0x0085 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:47:0x007b A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:49:0x006d A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:50:0x006d A[SYNTHETIC] */
    /* JADX WARN: Instruction removed from duplicated block: B:39:0x0090, please report this as an issue */
    @Override // rikka.shizuku.Lq
    public final void c(Wf wf, Object obj) throws IOException {
        Iterator it;
        boolean z;
        Mq mq;
        Lq lqA;
        if (this.c) {
            wf.i();
            return;
        }
        Lq lqC = this.a;
        if (lqC == null) {
            Rd rd = this.d;
            Mq mq2 = this.f;
            C0630tr c0630tr = this.e;
            rd.getClass();
            Mf mf = Nf.c;
            Nf nf = rd.d;
            if (mq2 == mf) {
                nf.getClass();
            } else {
                ConcurrentHashMap concurrentHashMap = nf.b;
                Class cls = c0630tr.a;
                Mq mq3 = (Mq) concurrentHashMap.get(cls);
                if (mq3 == null) {
                    Lf lf = (Lf) cls.getAnnotation(Lf.class);
                    if (lf != null) {
                        Class clsValue = lf.value();
                        if (Mq.class.isAssignableFrom(clsValue)) {
                            Mq mq4 = (Mq) nf.a.b(new C0630tr(clsValue), true).d();
                            Mq mq5 = (Mq) nf.b.putIfAbsent(cls, mq4);
                            if (mq5 != null) {
                                mq4 = mq5;
                            }
                            if (mq4 == mq2) {
                            }
                        }
                    }
                } else if (mq3 == mq2) {
                }
                it = rd.e.iterator();
                z = false;
                while (true) {
                    if (it.hasNext()) {
                        if (!z) {
                            lqC = rd.c(c0630tr);
                            break;
                        } else {
                            throw new IllegalArgumentException("GSON cannot serialize or deserialize " + c0630tr);
                        }
                    }
                    mq = (Mq) it.next();
                    if (!z) {
                        lqA = mq.a(rd, c0630tr);
                        if (lqA != null) {
                            lqC = lqA;
                            break;
                        }
                    } else if (mq == mq2) {
                        z = true;
                    }
                }
                this.a = lqC;
            }
            mq2 = nf;
            it = rd.e.iterator();
            z = false;
            while (true) {
                if (it.hasNext()) {
                    if (!z) {
                        lqC = rd.c(c0630tr);
                        break;
                    } else {
                        throw new IllegalArgumentException("GSON cannot serialize or deserialize " + c0630tr);
                    }
                }
                mq = (Mq) it.next();
                if (!z) {
                    lqA = mq.a(rd, c0630tr);
                    if (lqA != null) {
                        lqC = lqA;
                        break;
                    }
                } else if (mq == mq2) {
                    z = true;
                }
            }
            this.a = lqC;
        }
        lqC.c(wf, obj);
    }
}
