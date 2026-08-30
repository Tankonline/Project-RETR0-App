package rikka.shizuku;

/* JADX INFO: renamed from: rikka.shizuku.gh, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0220gh {
    public final InterfaceC0313jj a;
    public boolean b;
    public int c = -1;
    public final /* synthetic */ Pi d;

    public AbstractC0220gh(Pi pi, InterfaceC0313jj interfaceC0313jj) {
        this.d = pi;
        this.a = interfaceC0313jj;
    }

    public final void b(boolean z) {
        if (z == this.b) {
            return;
        }
        this.b = z;
        int i = z ? 1 : -1;
        Pi pi = this.d;
        int i2 = pi.c;
        pi.c = i + i2;
        if (!pi.d) {
            pi.d = true;
            while (true) {
                try {
                    int i3 = pi.c;
                    if (i2 == i3) {
                        break;
                    } else {
                        i2 = i3;
                    }
                } catch (Throwable th) {
                    pi.d = false;
                    throw th;
                }
            }
            pi.d = false;
        }
        if (this.b) {
            pi.c(this);
        }
    }

    public boolean d(InterfaceC0743xg interfaceC0743xg) {
        return false;
    }

    public abstract boolean e();

    public void c() {
    }
}
