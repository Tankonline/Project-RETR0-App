package rikka.shizuku;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class Cn implements Runnable {
    public final /* synthetic */ int a;
    public final /* synthetic */ Gn b;

    public /* synthetic */ Cn(Gn gn, int i) {
        this.a = i;
        this.b = gn;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.a) {
            case 0:
                break;
            default:
                Gn gn = this.b;
                while (true) {
                    InterfaceC0812zn interfaceC0812znC = gn.c(false);
                    if (interfaceC0812znC == null) {
                        return;
                    } else {
                        try {
                            gn.a(interfaceC0812znC);
                        } catch (IOException unused) {
                        }
                    }
                }
                break;
        }
        while (true) {
            Gn gn2 = this.b;
            InterfaceC0812zn interfaceC0812znC2 = gn2.c(false);
            if (interfaceC0812znC2 == null) {
                return;
            } else {
                try {
                    gn2.a(interfaceC0812znC2);
                } catch (IOException unused2) {
                }
            }
        }
    }
}
