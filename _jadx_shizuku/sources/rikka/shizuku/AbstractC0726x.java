package rikka.shizuku;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;

/* JADX INFO: renamed from: rikka.shizuku.x, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0726x extends AbstractC0633u implements Iterable {
    public static final C0048b b = new C0048b(6, AbstractC0726x.class);
    public InterfaceC0202g[] a;

    public AbstractC0726x() {
        this.a = C0233h.d;
    }

    public static AbstractC0726x t(Object obj) {
        if (obj == null || (obj instanceof AbstractC0726x)) {
            return (AbstractC0726x) obj;
        }
        if (obj instanceof InterfaceC0202g) {
            AbstractC0633u abstractC0633uD = ((InterfaceC0202g) obj).d();
            if (abstractC0633uD instanceof AbstractC0726x) {
                return (AbstractC0726x) abstractC0633uD;
            }
        } else if (obj instanceof byte[]) {
            try {
                C0048b c0048b = b;
                AbstractC0633u abstractC0633uO = AbstractC0633u.o((byte[]) obj);
                c0048b.a(abstractC0633uO);
                return (AbstractC0726x) abstractC0633uO;
            } catch (IOException e) {
                throw new IllegalArgumentException("failed to construct sequence from byte[]: " + e.getMessage());
            }
        }
        throw new IllegalArgumentException("unknown object in getInstance: ".concat(obj.getClass().getName()));
    }

    @Override // rikka.shizuku.AbstractC0633u, rikka.shizuku.AbstractC0417n
    public int hashCode() {
        int length = this.a.length;
        int iHashCode = length + 1;
        while (true) {
            length--;
            if (length < 0) {
                return iHashCode;
            }
            iHashCode = (iHashCode * 257) ^ this.a[length].d().hashCode();
        }
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final boolean i(AbstractC0633u abstractC0633u) {
        if (abstractC0633u instanceof AbstractC0726x) {
            AbstractC0726x abstractC0726x = (AbstractC0726x) abstractC0633u;
            int size = size();
            if (abstractC0726x.size() == size) {
                for (int i = 0; i < size; i++) {
                    AbstractC0633u abstractC0633uD = this.a[i].d();
                    AbstractC0633u abstractC0633uD2 = abstractC0726x.a[i].d();
                    if (abstractC0633uD == abstractC0633uD2 || abstractC0633uD.i(abstractC0633uD2)) {
                    }
                }
                return true;
            }
        }
        return false;
    }

    public Iterator iterator() {
        return new C0115d4(this.a, 1);
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final boolean k() {
        return true;
    }

    @Override // rikka.shizuku.AbstractC0633u
    public AbstractC0633u p() {
        D8 d8 = new D8(this.a);
        d8.c = -1;
        return d8;
    }

    @Override // rikka.shizuku.AbstractC0633u
    public AbstractC0633u q() {
        Q8 q8 = new Q8(this.a);
        q8.c = -1;
        return q8;
    }

    public final AbstractC0079c[] r() {
        int size = size();
        AbstractC0079c[] abstractC0079cArr = new AbstractC0079c[size];
        for (int i = 0; i < size; i++) {
            abstractC0079cArr[i] = AbstractC0079c.s(this.a[i]);
        }
        return abstractC0079cArr;
    }

    public final r[] s() {
        int size = size();
        r[] rVarArr = new r[size];
        for (int i = 0; i < size; i++) {
            rVarArr[i] = r.r(this.a[i]);
        }
        return rVarArr;
    }

    public int size() {
        return this.a.length;
    }

    public final String toString() {
        int size = size();
        if (size == 0) {
            return "[]";
        }
        StringBuffer stringBuffer = new StringBuffer("[");
        int i = 0;
        while (true) {
            stringBuffer.append(this.a[i]);
            i++;
            if (i >= size) {
                stringBuffer.append(']');
                return stringBuffer.toString();
            }
            stringBuffer.append(", ");
        }
    }

    public InterfaceC0202g u(int i) {
        return this.a[i];
    }

    public Enumeration v() {
        return new C0695w(this);
    }

    public abstract AbstractC0079c w();

    public abstract r x();

    public abstract AbstractC0757y y();

    public AbstractC0726x(C0233h c0233h) {
        if (c0233h == null) {
            throw new NullPointerException("'elementVector' cannot be null");
        }
        this.a = c0233h.g();
    }

    public AbstractC0726x(AbstractC0633u abstractC0633u) {
        if (abstractC0633u == null) {
            throw new NullPointerException("'element' cannot be null");
        }
        this.a = new InterfaceC0202g[]{abstractC0633u};
    }

    public AbstractC0726x(InterfaceC0202g[] interfaceC0202gArr) {
        this.a = interfaceC0202gArr;
    }
}
