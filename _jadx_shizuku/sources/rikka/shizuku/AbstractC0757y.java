package rikka.shizuku;

import java.io.IOException;
import java.util.Iterator;

/* JADX INFO: renamed from: rikka.shizuku.y, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0757y extends AbstractC0633u implements Iterable {
    public final InterfaceC0202g[] a;
    public final boolean b;

    static {
        new C0048b(7, AbstractC0757y.class);
    }

    public AbstractC0757y() {
        this.a = C0233h.d;
        this.b = true;
    }

    public static byte[] r(InterfaceC0202g interfaceC0202g) {
        try {
            return interfaceC0202g.d().h();
        } catch (IOException unused) {
            throw new IllegalArgumentException("cannot encode object added to SET");
        }
    }

    public static boolean s(byte[] bArr, byte[] bArr2) {
        int i = bArr[0] & (-33);
        int i2 = bArr2[0] & (-33);
        if (i != i2) {
            return i < i2;
        }
        int iMin = Math.min(bArr.length, bArr2.length) - 1;
        for (int i3 = 1; i3 < iMin; i3++) {
            byte b = bArr[i3];
            byte b2 = bArr2[i3];
            if (b != b2) {
                return (b & 255) < (b2 & 255);
            }
        }
        return (bArr[iMin] & 255) <= (bArr2[iMin] & 255);
    }

    public static void t(InterfaceC0202g[] interfaceC0202gArr) {
        int i;
        int length = interfaceC0202gArr.length;
        if (length < 2) {
            return;
        }
        InterfaceC0202g interfaceC0202g = interfaceC0202gArr[0];
        InterfaceC0202g interfaceC0202g2 = interfaceC0202gArr[1];
        byte[] bArrR = r(interfaceC0202g);
        byte[] bArrR2 = r(interfaceC0202g2);
        if (s(bArrR2, bArrR)) {
            interfaceC0202g2 = interfaceC0202g;
            interfaceC0202g = interfaceC0202g2;
            bArrR2 = bArrR;
            bArrR = bArrR2;
        }
        for (int i2 = 2; i2 < length; i2++) {
            InterfaceC0202g interfaceC0202g3 = interfaceC0202gArr[i2];
            byte[] bArrR3 = r(interfaceC0202g3);
            if (s(bArrR2, bArrR3)) {
                interfaceC0202gArr[i2 - 2] = interfaceC0202g;
                interfaceC0202g = interfaceC0202g2;
                bArrR = bArrR2;
                interfaceC0202g2 = interfaceC0202g3;
                bArrR2 = bArrR3;
            } else if (s(bArrR, bArrR3)) {
                interfaceC0202gArr[i2 - 2] = interfaceC0202g;
                interfaceC0202g = interfaceC0202g3;
                bArrR = bArrR3;
            } else {
                int i3 = i2 - 1;
                while (true) {
                    i = i3 - 1;
                    if (i <= 0) {
                        break;
                    }
                    InterfaceC0202g interfaceC0202g4 = interfaceC0202gArr[i3 - 2];
                    if (s(r(interfaceC0202g4), bArrR3)) {
                        break;
                    }
                    interfaceC0202gArr[i] = interfaceC0202g4;
                    i3 = i;
                }
                interfaceC0202gArr[i] = interfaceC0202g3;
            }
        }
        interfaceC0202gArr[length - 2] = interfaceC0202g;
        interfaceC0202gArr[length - 1] = interfaceC0202g2;
    }

    @Override // rikka.shizuku.AbstractC0633u, rikka.shizuku.AbstractC0417n
    public final int hashCode() {
        InterfaceC0202g[] interfaceC0202gArr = this.a;
        int length = interfaceC0202gArr.length;
        int iHashCode = length + 1;
        while (true) {
            length--;
            if (length < 0) {
                return iHashCode;
            }
            iHashCode += interfaceC0202gArr[length].d().hashCode();
        }
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final boolean i(AbstractC0633u abstractC0633u) {
        if (abstractC0633u instanceof AbstractC0757y) {
            AbstractC0757y abstractC0757y = (AbstractC0757y) abstractC0633u;
            int length = this.a.length;
            if (abstractC0757y.a.length == length) {
                E8 e8 = (E8) p();
                E8 e9 = (E8) abstractC0757y.p();
                for (int i = 0; i < length; i++) {
                    AbstractC0633u abstractC0633uD = e8.a[i].d();
                    AbstractC0633u abstractC0633uD2 = e9.a[i].d();
                    if (abstractC0633uD == abstractC0633uD2 || abstractC0633uD.i(abstractC0633uD2)) {
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override // java.lang.Iterable
    public final Iterator iterator() {
        return new C0115d4(C0233h.e(this.a), 1);
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final boolean k() {
        return true;
    }

    @Override // rikka.shizuku.AbstractC0633u
    public AbstractC0633u p() {
        boolean z = this.b;
        InterfaceC0202g[] interfaceC0202gArr = this.a;
        if (!z) {
            interfaceC0202gArr = (InterfaceC0202g[]) interfaceC0202gArr.clone();
            t(interfaceC0202gArr);
        }
        E8 e8 = new E8(true, interfaceC0202gArr);
        e8.c = -1;
        return e8;
    }

    @Override // rikka.shizuku.AbstractC0633u
    public AbstractC0633u q() {
        return new R8(this.b, this.a);
    }

    public final String toString() {
        InterfaceC0202g[] interfaceC0202gArr = this.a;
        int length = interfaceC0202gArr.length;
        if (length == 0) {
            return "[]";
        }
        StringBuffer stringBuffer = new StringBuffer("[");
        int i = 0;
        while (true) {
            stringBuffer.append(interfaceC0202gArr[i]);
            i++;
            if (i >= length) {
                stringBuffer.append(']');
                return stringBuffer.toString();
            }
            stringBuffer.append(", ");
        }
    }

    public AbstractC0757y(C0233h c0233h) {
        if (c0233h == null) {
            throw new NullPointerException("'elementVector' cannot be null");
        }
        InterfaceC0202g[] interfaceC0202gArrG = c0233h.g();
        this.a = interfaceC0202gArrG;
        this.b = interfaceC0202gArrG.length < 2;
    }

    public AbstractC0757y(D8 d8) {
        this.a = new InterfaceC0202g[]{d8};
        this.b = true;
    }

    public AbstractC0757y(boolean z, InterfaceC0202g[] interfaceC0202gArr) {
        this.a = interfaceC0202gArr;
        this.b = z || interfaceC0202gArr.length < 2;
    }

    public AbstractC0757y(InterfaceC0202g[] interfaceC0202gArr) {
        for (InterfaceC0202g interfaceC0202g : interfaceC0202gArr) {
            if (interfaceC0202g == null) {
                throw new NullPointerException("'elements' cannot be null, or contain null");
            }
        }
        InterfaceC0202g[] interfaceC0202gArrE = C0233h.e(interfaceC0202gArr);
        if (interfaceC0202gArrE.length >= 2) {
            t(interfaceC0202gArrE);
        }
        this.a = interfaceC0202gArrE;
        this.b = true;
    }
}
