package rikka.shizuku;

import android.util.SparseArray;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
public final class Wa {
    public int a = 1;
    public final Ci b;
    public Ci c;
    public Ci d;
    public int e;
    public int f;

    public Wa(Ci ci) {
        this.b = ci;
        this.c = ci;
    }

    public final int a(int i) {
        SparseArray sparseArray = this.c.a;
        Ci ci = sparseArray == null ? null : (Ci) sparseArray.get(i);
        int i2 = 1;
        int i3 = 2;
        if (this.a == 2) {
            if (ci != null) {
                this.c = ci;
                this.f++;
            } else if (i == 65038) {
                b();
            } else if (i != 65039) {
                Ci ci2 = this.c;
                if (ci2.b != null) {
                    i3 = 3;
                    if (this.f != 1) {
                        this.d = ci2;
                        b();
                    } else if (c()) {
                        this.d = this.c;
                        b();
                    } else {
                        b();
                    }
                } else {
                    b();
                }
            }
            i2 = i3;
        } else if (ci == null) {
            b();
        } else {
            this.a = 2;
            this.c = ci;
            this.f = 1;
            i2 = i3;
        }
        this.e = i;
        return i2;
    }

    public final void b() {
        this.a = 1;
        this.c = this.b;
        this.f = 0;
    }

    public final boolean c() {
        Ai aiB = this.c.b.b();
        int iA = aiB.a(6);
        return !(iA == 0 || ((ByteBuffer) aiB.d).get(iA + aiB.a) == 0) || this.e == 65039;
    }
}
