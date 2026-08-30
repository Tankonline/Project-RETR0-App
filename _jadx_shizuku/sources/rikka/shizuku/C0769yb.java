package rikka.shizuku;

import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: renamed from: rikka.shizuku.yb, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0769yb implements Mq, Cloneable {
    public static final C0769yb d = new C0769yb();
    public double a = -1.0d;
    public final List b;
    public final List c;

    public C0769yb() {
        List list = Collections.EMPTY_LIST;
        this.b = list;
        this.c = list;
    }

    @Override // rikka.shizuku.Mq
    public final Lq a(Rd rd, C0630tr c0630tr) {
        Class cls = c0630tr.a;
        boolean zC = c(cls, true);
        boolean zC2 = c(cls, false);
        if (zC || zC2) {
            return new C0738xb(this, zC2, zC, rd, c0630tr);
        }
        return null;
    }

    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public final C0769yb clone() {
        try {
            return (C0769yb) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x002f, code lost:
    
        if (r7.a < r2.value()) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean c(Class cls, boolean z) {
        if (this.a != -1.0d) {
            InterfaceC0349ko interfaceC0349ko = (InterfaceC0349ko) cls.getAnnotation(InterfaceC0349ko.class);
            Sr sr = (Sr) cls.getAnnotation(Sr.class);
            if (interfaceC0349ko != null) {
                if (this.a >= interfaceC0349ko.value()) {
                }
                return true;
            }
            if (sr != null) {
            }
        }
        if (!z && !Enum.class.isAssignableFrom(cls)) {
            AbstractC0046as abstractC0046as = AbstractC0717wl.a;
            if (!Modifier.isStatic(cls.getModifiers()) && (cls.isAnonymousClass() || cls.isLocalClass())) {
                return true;
            }
        }
        Iterator it = (z ? this.b : this.c).iterator();
        if (!it.hasNext()) {
            return false;
        }
        it.next().getClass();
        throw new ClassCastException();
    }
}
