package rikka.shizuku;

import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: renamed from: rikka.shizuku.q7, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0518q7 extends InputStream {
    public final C0788z a;
    public boolean b = true;
    public InputStream c;

    public C0518q7(C0788z c0788z) {
        this.a = c0788z;
    }

    public final InterfaceC0571s a() throws IOException {
        C0788z c0788z = this.a;
        int i = ((Cg) c0788z.b).read();
        InterfaceC0202g interfaceC0202gC = i < 0 ? null : c0788z.c(i);
        if (interfaceC0202gC == null) {
            return null;
        }
        if (interfaceC0202gC instanceof InterfaceC0571s) {
            return (InterfaceC0571s) interfaceC0202gC;
        }
        throw new IOException("unknown object encountered: " + interfaceC0202gC.getClass());
    }

    @Override // java.io.InputStream
    public final int read() throws IOException {
        InterfaceC0571s interfaceC0571sA;
        if (this.c == null) {
            if (!this.b || (interfaceC0571sA = a()) == null) {
                return -1;
            }
            this.b = false;
            this.c = interfaceC0571sA.c();
        }
        while (true) {
            int i = this.c.read();
            if (i >= 0) {
                return i;
            }
            InterfaceC0571s interfaceC0571sA2 = a();
            if (interfaceC0571sA2 == null) {
                this.c = null;
                return -1;
            }
            this.c = interfaceC0571sA2.c();
        }
    }

    @Override // java.io.InputStream
    public final int read(byte[] bArr, int i, int i2) throws IOException {
        InterfaceC0571s interfaceC0571sA;
        int i3 = 0;
        if (this.c == null) {
            if (!this.b || (interfaceC0571sA = a()) == null) {
                return -1;
            }
            this.b = false;
            this.c = interfaceC0571sA.c();
        }
        while (true) {
            int i4 = this.c.read(bArr, i + i3, i2 - i3);
            if (i4 >= 0) {
                i3 += i4;
                if (i3 == i2) {
                    return i3;
                }
            } else {
                InterfaceC0571s interfaceC0571sA2 = a();
                if (interfaceC0571sA2 == null) {
                    this.c = null;
                    if (i3 < 1) {
                        return -1;
                    }
                    return i3;
                }
                this.c = interfaceC0571sA2.c();
            }
        }
    }
}
