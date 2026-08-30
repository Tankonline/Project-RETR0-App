package rikka.shizuku;

import android.os.Looper;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/* JADX INFO: renamed from: rikka.shizuku.zg, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0805zg {
    public final boolean a;
    public Qb b;
    public EnumC0588sg c;
    public final WeakReference d;
    public int e;
    public boolean f;
    public boolean g;
    public final ArrayList h;
    public final Ro i;

    public C0805zg(InterfaceC0743xg interfaceC0743xg) {
        new AtomicReference(null);
        this.a = true;
        this.b = new Qb();
        EnumC0588sg enumC0588sg = EnumC0588sg.b;
        this.c = enumC0588sg;
        this.h = new ArrayList();
        this.d = new WeakReference(interfaceC0743xg);
        this.i = new Ro(enumC0588sg);
    }

    public final void a(InterfaceC0712wg interfaceC0712wg) {
        InterfaceC0681vg c0520q9;
        Object obj;
        InterfaceC0743xg interfaceC0743xg;
        EnumC0557rg enumC0557rg;
        ArrayList arrayList = this.h;
        Object obj2 = null;
        int i = 2;
        int i2 = 0;
        c("addObserver");
        EnumC0588sg enumC0588sg = this.c;
        EnumC0588sg enumC0588sg2 = EnumC0588sg.a;
        if (enumC0588sg != enumC0588sg2) {
            enumC0588sg2 = EnumC0588sg.b;
        }
        C0774yg c0774yg = new C0774yg();
        HashMap map = Ag.a;
        boolean z = interfaceC0712wg instanceof InterfaceC0681vg;
        boolean z2 = interfaceC0712wg instanceof Na;
        if (z && z2) {
            c0520q9 = new C0520q9((Na) interfaceC0712wg, i2, (InterfaceC0681vg) interfaceC0712wg);
        } else if (z2) {
            c0520q9 = new C0520q9((Na) interfaceC0712wg, i2, obj2);
        } else if (z) {
            c0520q9 = (InterfaceC0681vg) interfaceC0712wg;
        } else {
            Class<?> cls = interfaceC0712wg.getClass();
            if (Ag.c(cls) == 2) {
                List list = (List) Ag.b.get(cls);
                if (list.size() == 1) {
                    Ag.a((Constructor) list.get(0), interfaceC0712wg);
                    c0520q9 = new C0380lo();
                } else {
                    int size = list.size();
                    Id[] idArr = new Id[size];
                    for (int i3 = 0; i3 < size; i3++) {
                        Ag.a((Constructor) list.get(i3), interfaceC0712wg);
                        idArr[i3] = null;
                    }
                    c0520q9 = new Tk(i, idArr);
                }
            } else {
                c0520q9 = new C0520q9(interfaceC0712wg);
            }
        }
        c0774yg.b = c0520q9;
        c0774yg.a = enumC0588sg2;
        Qb qb = this.b;
        C0409mm c0409mmA = qb.a(interfaceC0712wg);
        if (c0409mmA != null) {
            obj = c0409mmA.b;
        } else {
            HashMap map2 = qb.e;
            C0409mm c0409mm = new C0409mm(interfaceC0712wg, c0774yg);
            qb.d++;
            C0409mm c0409mm2 = qb.b;
            if (c0409mm2 == null) {
                qb.a = c0409mm;
                qb.b = c0409mm;
            } else {
                c0409mm2.c = c0409mm;
                c0409mm.d = c0409mm2;
                qb.b = c0409mm;
            }
            map2.put(interfaceC0712wg, c0409mm);
            obj = null;
        }
        if (((C0774yg) obj) == null && (interfaceC0743xg = (InterfaceC0743xg) this.d.get()) != null) {
            i2 = (this.e != 0 || this.f) ? 1 : 0;
            EnumC0588sg enumC0588sgB = b(interfaceC0712wg);
            this.e++;
            while (c0774yg.a.compareTo(enumC0588sgB) < 0 && this.b.e.containsKey(interfaceC0712wg)) {
                arrayList.add(c0774yg.a);
                C0496pg c0496pg = EnumC0557rg.Companion;
                EnumC0588sg enumC0588sg3 = c0774yg.a;
                c0496pg.getClass();
                int iOrdinal = enumC0588sg3.ordinal();
                if (iOrdinal == 1) {
                    enumC0557rg = EnumC0557rg.ON_CREATE;
                } else if (iOrdinal != 2) {
                    enumC0557rg = iOrdinal != 3 ? null : EnumC0557rg.ON_RESUME;
                } else {
                    enumC0557rg = EnumC0557rg.ON_START;
                }
                if (enumC0557rg == null) {
                    throw new IllegalStateException("no event up from " + c0774yg.a);
                }
                c0774yg.a(interfaceC0743xg, enumC0557rg);
                arrayList.remove(arrayList.size() - 1);
                enumC0588sgB = b(interfaceC0712wg);
            }
            if (i2 == 0) {
                g();
            }
            this.e--;
        }
    }

    public final EnumC0588sg b(InterfaceC0712wg interfaceC0712wg) {
        HashMap map = this.b.e;
        C0409mm c0409mm = map.containsKey(interfaceC0712wg) ? ((C0409mm) map.get(interfaceC0712wg)).d : null;
        EnumC0588sg enumC0588sg = c0409mm != null ? ((C0774yg) c0409mm.b).a : null;
        ArrayList arrayList = this.h;
        EnumC0588sg enumC0588sg2 = arrayList.isEmpty() ? null : (EnumC0588sg) arrayList.get(arrayList.size() - 1);
        EnumC0588sg enumC0588sg3 = this.c;
        if (enumC0588sg == null || enumC0588sg.compareTo(enumC0588sg3) >= 0) {
            enumC0588sg = enumC0588sg3;
        }
        return (enumC0588sg2 == null || enumC0588sg2.compareTo(enumC0588sg) >= 0) ? enumC0588sg : enumC0588sg2;
    }

    public final void c(String str) {
        if (this.a) {
            C0022a4.C().b.getClass();
            if (Looper.getMainLooper().getThread() != Thread.currentThread()) {
                throw new IllegalStateException(AbstractC0535qo.d("Method ", str, " must be called on the main thread").toString());
            }
        }
    }

    public final void d(EnumC0557rg enumC0557rg) {
        c("handleLifecycleEvent");
        e(enumC0557rg.a());
    }

    public final void e(EnumC0588sg enumC0588sg) {
        if (this.c == enumC0588sg) {
            return;
        }
        InterfaceC0743xg interfaceC0743xg = (InterfaceC0743xg) this.d.get();
        EnumC0588sg enumC0588sg2 = this.c;
        EnumC0588sg enumC0588sg3 = EnumC0588sg.b;
        EnumC0588sg enumC0588sg4 = EnumC0588sg.a;
        if (enumC0588sg2 == enumC0588sg3 && enumC0588sg == enumC0588sg4) {
            throw new IllegalStateException(("State must be at least '" + EnumC0588sg.c + "' to be moved to '" + enumC0588sg + "' in component " + interfaceC0743xg).toString());
        }
        if (enumC0588sg2 == enumC0588sg4 && enumC0588sg2 != enumC0588sg) {
            throw new IllegalStateException(("State is '" + enumC0588sg4 + "' and cannot be moved to `" + enumC0588sg + "` in component " + interfaceC0743xg).toString());
        }
        this.c = enumC0588sg;
        if (this.f || this.e != 0) {
            this.g = true;
            return;
        }
        this.f = true;
        g();
        this.f = false;
        if (this.c == enumC0588sg4) {
            this.b = new Qb();
        }
    }

    public final void f(InterfaceC0712wg interfaceC0712wg) {
        c("removeObserver");
        this.b.b(interfaceC0712wg);
    }

    public final void g() {
        int i;
        EnumC0588sg enumC0588sg;
        EnumC0588sg enumC0588sg2;
        EnumC0557rg enumC0557rg;
        EnumC0557rg enumC0557rg2;
        InterfaceC0743xg interfaceC0743xg = (InterfaceC0743xg) this.d.get();
        if (interfaceC0743xg == null) {
            throw new IllegalStateException("LifecycleOwner of this LifecycleRegistry is already garbage collected. It is too late to change lifecycle state.");
        }
        while (true) {
            Qb qb = this.b;
            if (qb.d == 0 || ((enumC0588sg = ((C0774yg) qb.a.b).a) == (enumC0588sg2 = ((C0774yg) qb.b.b).a) && this.c == enumC0588sg2)) {
                break;
            }
            this.g = false;
            if (this.c.compareTo(enumC0588sg) < 0) {
                Qb qb2 = this.b;
                C0378lm c0378lm = new C0378lm(qb2.b, qb2.a, 1);
                qb2.c.put(c0378lm, Boolean.FALSE);
                while (c0378lm.hasNext() && !this.g) {
                    Map.Entry entry = (Map.Entry) c0378lm.next();
                    InterfaceC0712wg interfaceC0712wg = (InterfaceC0712wg) entry.getKey();
                    C0774yg c0774yg = (C0774yg) entry.getValue();
                    while (c0774yg.a.compareTo(this.c) > 0 && !this.g && this.b.e.containsKey(interfaceC0712wg)) {
                        C0496pg c0496pg = EnumC0557rg.Companion;
                        EnumC0588sg enumC0588sg3 = c0774yg.a;
                        c0496pg.getClass();
                        int iOrdinal = enumC0588sg3.ordinal();
                        if (iOrdinal == 2) {
                            enumC0557rg2 = EnumC0557rg.ON_DESTROY;
                        } else if (iOrdinal != 3) {
                            enumC0557rg2 = iOrdinal != 4 ? null : EnumC0557rg.ON_PAUSE;
                        } else {
                            enumC0557rg2 = EnumC0557rg.ON_STOP;
                        }
                        if (enumC0557rg2 == null) {
                            throw new IllegalStateException("no event down from " + c0774yg.a);
                        }
                        this.h.add(enumC0557rg2.a());
                        c0774yg.a(interfaceC0743xg, enumC0557rg2);
                        ArrayList arrayList = this.h;
                        arrayList.remove(arrayList.size() - 1);
                    }
                }
            }
            C0409mm c0409mm = this.b.b;
            if (!this.g && c0409mm != null && this.c.compareTo(((C0774yg) c0409mm.b).a) > 0) {
                Qb qb3 = this.b;
                qb3.getClass();
                C0440nm c0440nm = new C0440nm(qb3);
                qb3.c.put(c0440nm, Boolean.FALSE);
                while (c0440nm.hasNext() && !this.g) {
                    Map.Entry entry2 = (Map.Entry) c0440nm.next();
                    InterfaceC0712wg interfaceC0712wg2 = (InterfaceC0712wg) entry2.getKey();
                    C0774yg c0774yg2 = (C0774yg) entry2.getValue();
                    while (c0774yg2.a.compareTo(this.c) < 0 && !this.g && this.b.e.containsKey(interfaceC0712wg2)) {
                        this.h.add(c0774yg2.a);
                        C0496pg c0496pg2 = EnumC0557rg.Companion;
                        EnumC0588sg enumC0588sg4 = c0774yg2.a;
                        c0496pg2.getClass();
                        int iOrdinal2 = enumC0588sg4.ordinal();
                        if (iOrdinal2 == 1) {
                            enumC0557rg = EnumC0557rg.ON_CREATE;
                        } else if (iOrdinal2 != 2) {
                            enumC0557rg = iOrdinal2 != 3 ? null : EnumC0557rg.ON_RESUME;
                        } else {
                            enumC0557rg = EnumC0557rg.ON_START;
                        }
                        if (enumC0557rg == null) {
                            throw new IllegalStateException("no event up from " + c0774yg2.a);
                        }
                        c0774yg2.a(interfaceC0743xg, enumC0557rg);
                        ArrayList arrayList2 = this.h;
                        arrayList2.remove(arrayList2.size() - 1);
                    }
                }
            }
        }
        this.g = false;
        Object obj = this.c;
        Ro ro = this.i;
        if (obj == null) {
            ro.getClass();
            obj = AbstractC0046as.e;
        }
        synchronized (ro) {
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = Ro.J;
            if (AbstractC0046as.c(atomicReferenceFieldUpdater.get(ro), obj)) {
                return;
            }
            atomicReferenceFieldUpdater.set(ro, obj);
            int i2 = ro.I;
            if ((i2 & 1) != 0) {
                ro.I = i2 + 2;
                return;
            }
            int i3 = i2 + 1;
            ro.I = i3;
            while (true) {
                synchronized (ro) {
                    i = ro.I;
                    if (i == i3) {
                        ro.I = i3 + 1;
                        return;
                    }
                }
                i3 = i;
            }
        }
    }
}
