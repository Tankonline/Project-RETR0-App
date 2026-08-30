package rikka.shizuku;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;

/* JADX INFO: renamed from: rikka.shizuku.lg, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0372lg extends AbstractC0726x {
    public byte[] c;

    @Override // rikka.shizuku.AbstractC0726x, rikka.shizuku.AbstractC0633u, rikka.shizuku.AbstractC0417n
    public final int hashCode() {
        z();
        return super.hashCode();
    }

    @Override // rikka.shizuku.AbstractC0726x, java.lang.Iterable
    public final Iterator iterator() {
        z();
        return super.iterator();
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final void j(Ys ys, boolean z) throws IOException {
        byte[] bArr;
        synchronized (this) {
            bArr = this.c;
        }
        if (bArr != null) {
            ys.H(z, 48, bArr);
        } else {
            super.q().j(ys, z);
        }
    }

    @Override // rikka.shizuku.AbstractC0633u
    public final int m(boolean z) {
        byte[] bArr;
        synchronized (this) {
            bArr = this.c;
        }
        return bArr != null ? Ys.v(bArr.length, z) : super.q().m(z);
    }

    @Override // rikka.shizuku.AbstractC0726x, rikka.shizuku.AbstractC0633u
    public final AbstractC0633u p() {
        z();
        return super.p();
    }

    @Override // rikka.shizuku.AbstractC0726x, rikka.shizuku.AbstractC0633u
    public final AbstractC0633u q() {
        z();
        return super.q();
    }

    @Override // rikka.shizuku.AbstractC0726x
    public final int size() {
        z();
        return this.a.length;
    }

    @Override // rikka.shizuku.AbstractC0726x
    public final InterfaceC0202g u(int i) {
        z();
        return this.a[i];
    }

    @Override // rikka.shizuku.AbstractC0726x
    public final Enumeration v() {
        byte[] bArr;
        synchronized (this) {
            bArr = this.c;
        }
        if (bArr == null) {
            return new C0695w(this);
        }
        C0341kg c0341kg = new C0341kg();
        c0341kg.a = new C0355l(bArr);
        c0341kg.b = c0341kg.a();
        return c0341kg;
    }

    @Override // rikka.shizuku.AbstractC0726x
    public final AbstractC0079c w() {
        return ((AbstractC0726x) q()).w();
    }

    @Override // rikka.shizuku.AbstractC0726x
    public final r x() {
        return ((AbstractC0726x) q()).x();
    }

    @Override // rikka.shizuku.AbstractC0726x
    public final AbstractC0757y y() {
        return ((AbstractC0726x) q()).y();
    }

    public final synchronized void z() {
        if (this.c != null) {
            C0355l c0355l = new C0355l(this.c);
            try {
                C0233h c0233hG = c0355l.g();
                c0355l.close();
                this.a = c0233hG.g();
                this.c = null;
            } catch (IOException e) {
                throw new C0602t("malformed ASN.1: " + e, e);
            }
        }
    }
}
