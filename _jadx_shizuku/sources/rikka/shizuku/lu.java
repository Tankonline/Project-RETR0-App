package rikka.shizuku;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
public final class lu extends AbstractC0417n implements InterfaceC0171f {
    public static final C0700w4 f = C0700w4.B;
    public boolean a;
    public int b;
    public final C0700w4 c;
    public final Ok[] d;
    public final D8 e;

    public lu() {
        C0700w4 c0700w4 = f;
        c0700w4.getClass();
        mu muVar = new mu("CN=00", ',');
        R2 r2 = new R2(27, false);
        r2.c = new Vector();
        r2.b = c0700w4;
        while (true) {
            int i = 0;
            if (!muVar.a()) {
                Vector vector = (Vector) r2.c;
                int size = vector.size();
                Ok[] okArr = new Ok[size];
                while (i != size) {
                    okArr[i] = (Ok) vector.elementAt(i);
                    i++;
                }
                this(c0700w4, (Ok[]) new lu((C0700w4) r2.b, okArr).d.clone());
                this.c = c0700w4;
                return;
            }
            String strB = muVar.b();
            if (strB.indexOf(43) > 0) {
                mu muVar2 = new mu(strB, '+');
                mu muVar3 = new mu(muVar2.b(), '=');
                String strB2 = muVar3.b();
                if (!muVar3.a()) {
                    throw new IllegalArgumentException("badly formatted directory string");
                }
                String strB3 = muVar3.b();
                C0510q c0510qD0 = c0700w4.d0(strB2.trim());
                if (muVar2.a()) {
                    Vector vector2 = new Vector();
                    Vector vector3 = new Vector();
                    while (true) {
                        vector2.addElement(c0510qD0);
                        vector3.addElement(AbstractC0744xh.a0(strB3));
                        if (!muVar2.a()) {
                            int size2 = vector2.size();
                            C0510q[] c0510qArr = new C0510q[size2];
                            for (int i2 = 0; i2 != size2; i2++) {
                                c0510qArr[i2] = (C0510q) vector2.elementAt(i2);
                            }
                            int size3 = vector3.size();
                            String[] strArr = new String[size3];
                            for (int i3 = 0; i3 != size3; i3++) {
                                strArr[i3] = (String) vector3.elementAt(i3);
                            }
                            InterfaceC0202g[] interfaceC0202gArr = new InterfaceC0202g[size3];
                            for (int i4 = 0; i4 != size3; i4++) {
                                C0510q c0510q = c0510qArr[i4];
                                String str = strArr[i4];
                                ((C0700w4) r2.b).getClass();
                                interfaceC0202gArr[i4] = AbstractC0744xh.Y(c0510q, str);
                            }
                            C0515q4[] c0515q4Arr = new C0515q4[size2];
                            while (i != size2) {
                                C0510q c0510q2 = c0510qArr[i];
                                InterfaceC0202g interfaceC0202g = interfaceC0202gArr[i];
                                C0515q4 c0515q4 = new C0515q4();
                                c0515q4.a = c0510q2;
                                c0515q4.b = interfaceC0202g;
                                c0515q4Arr[i] = c0515q4;
                                i++;
                            }
                            Vector vector4 = (Vector) r2.c;
                            Ok ok = new Ok();
                            E8 e8 = new E8(c0515q4Arr);
                            e8.c = -1;
                            ok.a = e8;
                            vector4.addElement(ok);
                            break;
                        }
                        mu muVar4 = new mu(muVar2.b(), '=');
                        String strB4 = muVar4.b();
                        if (!muVar4.a()) {
                            throw new IllegalArgumentException("badly formatted directory string");
                        }
                        strB3 = muVar4.b();
                        c0510qD0 = c0700w4.d0(strB4.trim());
                    }
                } else {
                    r2.b(c0510qD0, AbstractC0744xh.a0(strB3));
                }
            } else {
                mu muVar5 = new mu(strB, '=');
                String strB5 = muVar5.b();
                if (!muVar5.a()) {
                    throw new IllegalArgumentException("badly formatted directory string");
                }
                r2.b(c0700w4.d0(strB5.trim()), AbstractC0744xh.a0(muVar5.b()));
            }
        }
    }

    @Override // rikka.shizuku.InterfaceC0202g
    public final AbstractC0633u d() {
        return this.e;
    }

    @Override // rikka.shizuku.AbstractC0417n
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof lu) && !(obj instanceof AbstractC0726x)) {
            return false;
        }
        if (this.e.n(((InterfaceC0202g) obj).d())) {
            return true;
        }
        try {
            C0700w4 c0700w4 = this.c;
            lu luVar = new lu(AbstractC0726x.t(((InterfaceC0202g) obj).d()));
            c0700w4.getClass();
            return AbstractC0744xh.c(this, luVar);
        } catch (Exception unused) {
            return false;
        }
    }

    @Override // rikka.shizuku.AbstractC0417n
    public final int hashCode() {
        if (this.a) {
            return this.b;
        }
        this.a = true;
        this.c.getClass();
        Ok[] okArr = (Ok[]) this.d.clone();
        int iHashCode = 0;
        for (int i = 0; i != okArr.length; i++) {
            Ok ok = okArr[i];
            if (ok.a.a.length > 1) {
                C0515q4[] c0515q4ArrJ = ok.j();
                for (int i2 = 0; i2 != c0515q4ArrJ.length; i2++) {
                    iHashCode = (iHashCode ^ c0515q4ArrJ[i2].a.a.hashCode()) ^ AbstractC0744xh.f(c0515q4ArrJ[i2].b).hashCode();
                }
            } else {
                iHashCode = (iHashCode ^ ok.i().a.a.hashCode()) ^ AbstractC0744xh.f(okArr[i].i().b).hashCode();
            }
        }
        this.b = iHashCode;
        return iHashCode;
    }

    public final String toString() {
        C0700w4 c0700w4 = this.c;
        c0700w4.getClass();
        StringBuffer stringBuffer = new StringBuffer();
        boolean z = true;
        for (Ok ok : (Ok[]) this.d.clone()) {
            if (z) {
                z = false;
            } else {
                stringBuffer.append(',');
            }
            Hashtable hashtable = c0700w4.r;
            if (ok.a.a.length > 1) {
                C0515q4[] c0515q4ArrJ = ok.j();
                boolean z2 = true;
                for (int i = 0; i != c0515q4ArrJ.length; i++) {
                    if (z2) {
                        z2 = false;
                    } else {
                        stringBuffer.append('+');
                    }
                    AbstractC0744xh.b(stringBuffer, c0515q4ArrJ[i], hashtable);
                }
            } else if (ok.i() != null) {
                AbstractC0744xh.b(stringBuffer, ok.i(), hashtable);
            }
        }
        return stringBuffer.toString();
    }

    public lu(AbstractC0726x abstractC0726x) {
        Ok ok;
        AbstractC0757y abstractC0757y;
        this.c = f;
        this.d = new Ok[abstractC0726x.size()];
        Enumeration enumerationV = abstractC0726x.v();
        boolean z = true;
        int i = 0;
        while (enumerationV.hasMoreElements()) {
            Object objNextElement = enumerationV.nextElement();
            if (objNextElement instanceof Ok) {
                ok = (Ok) objNextElement;
            } else if (objNextElement != null) {
                if (objNextElement instanceof AbstractC0757y) {
                    abstractC0757y = (AbstractC0757y) objNextElement;
                } else if (objNextElement instanceof InterfaceC0202g) {
                    AbstractC0633u abstractC0633uD = ((InterfaceC0202g) objNextElement).d();
                    if (!(abstractC0633uD instanceof AbstractC0757y)) {
                        throw new IllegalArgumentException("unknown object in getInstance: ".concat(objNextElement.getClass().getName()));
                    }
                    abstractC0757y = (AbstractC0757y) abstractC0633uD;
                } else {
                    if (!(objNextElement instanceof byte[])) {
                        throw new IllegalArgumentException("unknown object in getInstance: ".concat(objNextElement.getClass().getName()));
                    }
                    try {
                        AbstractC0633u abstractC0633uO = AbstractC0633u.o((byte[]) objNextElement);
                        if (AbstractC0757y.class.isInstance(abstractC0633uO)) {
                            abstractC0757y = (AbstractC0757y) abstractC0633uO;
                        } else {
                            throw new IllegalStateException("unexpected object: ".concat(abstractC0633uO.getClass().getName()));
                        }
                    } catch (IOException e) {
                        throw new IllegalArgumentException("failed to construct set from byte[]: " + e.getMessage());
                    }
                }
                ok = new Ok();
                ok.a = abstractC0757y;
            } else {
                ok = null;
            }
            z &= ok == objNextElement;
            this.d[i] = ok;
            i++;
        }
        if (z) {
            this.e = (D8) abstractC0726x.p();
        } else {
            this.e = new D8(this.d);
        }
    }

    public lu(C0700w4 c0700w4, Ok[] okArr) {
        this.c = c0700w4;
        Ok[] okArr2 = (Ok[]) okArr.clone();
        this.d = okArr2;
        this.e = new D8(okArr2);
    }
}
