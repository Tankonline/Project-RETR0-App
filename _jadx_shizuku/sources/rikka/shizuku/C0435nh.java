package rikka.shizuku;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/* JADX INFO: renamed from: rikka.shizuku.nh, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0435nh {
    public static final /* synthetic */ AtomicReferenceFieldUpdater a = AtomicReferenceFieldUpdater.newUpdater(C0435nh.class, Object.class, "_next$volatile");
    public static final /* synthetic */ AtomicReferenceFieldUpdater b = AtomicReferenceFieldUpdater.newUpdater(C0435nh.class, Object.class, "_prev$volatile");
    public static final /* synthetic */ AtomicReferenceFieldUpdater c = AtomicReferenceFieldUpdater.newUpdater(C0435nh.class, Object.class, "_removedRef$volatile");
    private volatile /* synthetic */ Object _next$volatile = this;
    private volatile /* synthetic */ Object _prev$volatile = this;
    private volatile /* synthetic */ Object _removedRef$volatile;

    public final boolean e(C0435nh c0435nh, int i) {
        while (true) {
            C0435nh c0435nhF = f();
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = b;
            if (c0435nhF == null) {
                Object obj = atomicReferenceFieldUpdater.get(this);
                while (true) {
                    c0435nhF = (C0435nh) obj;
                    if (!c0435nhF.i()) {
                        break;
                    }
                    obj = atomicReferenceFieldUpdater.get(c0435nhF);
                }
            }
            if (c0435nhF instanceof Qg) {
                return (((Qg) c0435nhF).d & i) == 0 && c0435nhF.e(c0435nh, i);
            }
            atomicReferenceFieldUpdater.set(c0435nh, c0435nhF);
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater2 = a;
            atomicReferenceFieldUpdater2.set(c0435nh, this);
            do {
                if (atomicReferenceFieldUpdater2.compareAndSet(c0435nhF, this, c0435nh)) {
                    c0435nh.g(this);
                    return true;
                }
            } while (atomicReferenceFieldUpdater2.get(c0435nhF) == this);
        }
    }

    public final C0435nh f() {
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater;
        Object obj;
        while (true) {
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater2 = b;
            C0435nh c0435nh = (C0435nh) atomicReferenceFieldUpdater2.get(this);
            C0435nh c0435nh2 = c0435nh;
            while (true) {
                C0435nh c0435nh3 = null;
                while (true) {
                    atomicReferenceFieldUpdater = a;
                    obj = atomicReferenceFieldUpdater.get(c0435nh2);
                    if (obj == this) {
                        if (c0435nh == c0435nh2) {
                            return c0435nh2;
                        }
                        while (!atomicReferenceFieldUpdater2.compareAndSet(this, c0435nh, c0435nh2)) {
                            if (atomicReferenceFieldUpdater2.get(this) != c0435nh) {
                                break;
                            }
                        }
                        return c0435nh2;
                    }
                    if (i()) {
                        return null;
                    }
                    if (!(obj instanceof Gl)) {
                        c0435nh3 = c0435nh2;
                        c0435nh2 = (C0435nh) obj;
                    } else {
                        if (c0435nh3 != null) {
                            break;
                        }
                        c0435nh2 = (C0435nh) atomicReferenceFieldUpdater2.get(c0435nh2);
                    }
                }
                C0435nh c0435nh4 = ((Gl) obj).a;
                while (!atomicReferenceFieldUpdater.compareAndSet(c0435nh3, c0435nh2, c0435nh4)) {
                    if (atomicReferenceFieldUpdater.get(c0435nh3) != c0435nh2) {
                        break;
                    }
                }
                c0435nh2 = c0435nh3;
            }
        }
    }

    public final void g(C0435nh c0435nh) {
        while (true) {
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = b;
            C0435nh c0435nh2 = (C0435nh) atomicReferenceFieldUpdater.get(c0435nh);
            if (a.get(this) != c0435nh) {
                return;
            }
            do {
                if (atomicReferenceFieldUpdater.compareAndSet(c0435nh, c0435nh2, this)) {
                    if (i()) {
                        c0435nh.f();
                        return;
                    }
                    return;
                }
            } while (atomicReferenceFieldUpdater.get(c0435nh) == c0435nh2);
        }
    }

    public final C0435nh h() {
        C0435nh c0435nh;
        Object obj = a.get(this);
        Gl gl = obj instanceof Gl ? (Gl) obj : null;
        return (gl == null || (c0435nh = gl.a) == null) ? (C0435nh) obj : c0435nh;
    }

    public boolean i() {
        return a.get(this) instanceof Gl;
    }

    public String toString() {
        return new C0404mh(this) + '@' + X8.B(this);
    }
}
