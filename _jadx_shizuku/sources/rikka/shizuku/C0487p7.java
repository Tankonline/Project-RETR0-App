package rikka.shizuku;

import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: renamed from: rikka.shizuku.p7, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0487p7 extends InputStream {
    public final C0788z a;
    public boolean b = true;
    public int c = 0;
    public InterfaceC0110d d;
    public InputStream e;

    public C0487p7(C0788z c0788z) {
        this.a = c0788z;
    }

    public final InterfaceC0110d a() throws IOException {
        C0788z c0788z = this.a;
        int i = ((Cg) c0788z.b).read();
        InterfaceC0202g interfaceC0202gC = i < 0 ? null : c0788z.c(i);
        if (interfaceC0202gC == null) {
            return null;
        }
        if (interfaceC0202gC instanceof InterfaceC0110d) {
            if (this.c == 0) {
                return (InterfaceC0110d) interfaceC0202gC;
            }
            throw new IOException("only the last nested bitstring can have padding");
        }
        throw new IOException("unknown object encountered: " + interfaceC0202gC.getClass());
    }

    @Override // java.io.InputStream
    public final int read() throws IOException {
        if (this.e == null) {
            if (this.b) {
                InterfaceC0110d interfaceC0110dA = a();
                this.d = interfaceC0110dA;
                if (interfaceC0110dA != null) {
                    this.b = false;
                    this.e = interfaceC0110dA.b();
                }
            }
            return -1;
        }
        while (true) {
            int i = this.e.read();
            if (i >= 0) {
                return i;
            }
            this.c = this.d.f();
            InterfaceC0110d interfaceC0110dA2 = a();
            this.d = interfaceC0110dA2;
            if (interfaceC0110dA2 == null) {
                this.e = null;
                return -1;
            }
            this.e = interfaceC0110dA2.b();
        }
    }

    @Override // java.io.InputStream
    public final int read(byte[] bArr, int i, int i2) throws IOException {
        int i3 = 0;
        if (this.e == null) {
            if (!this.b) {
                return -1;
            }
            InterfaceC0110d interfaceC0110dA = a();
            this.d = interfaceC0110dA;
            if (interfaceC0110dA == null) {
                return -1;
            }
            this.b = false;
            this.e = interfaceC0110dA.b();
        }
        while (true) {
            int i4 = this.e.read(bArr, i + i3, i2 - i3);
            if (i4 >= 0) {
                i3 += i4;
                if (i3 == i2) {
                    return i3;
                }
            } else {
                this.c = this.d.f();
                InterfaceC0110d interfaceC0110dA2 = a();
                this.d = interfaceC0110dA2;
                if (interfaceC0110dA2 == null) {
                    this.e = null;
                    if (i3 < 1) {
                        return -1;
                    }
                    return i3;
                }
                this.e = interfaceC0110dA2.b();
            }
        }
    }
}
