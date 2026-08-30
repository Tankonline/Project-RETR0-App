package rikka.shizuku;

import java.util.Arrays;

/* JADX INFO: renamed from: rikka.shizuku.ro, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0565ro {
    public boolean a;
    public float e;
    public int l;
    public int b = -1;
    public int c = -1;
    public int d = 0;
    public boolean f = false;
    public final float[] g = new float[9];
    public final float[] h = new float[9];
    public C0360l4[] i = new C0360l4[16];
    public int j = 0;
    public int k = 0;

    public C0565ro(int i) {
        this.l = i;
    }

    public final void a(C0360l4 c0360l4) {
        int i = 0;
        while (true) {
            int i2 = this.j;
            if (i >= i2) {
                C0360l4[] c0360l4Arr = this.i;
                if (i2 >= c0360l4Arr.length) {
                    this.i = (C0360l4[]) Arrays.copyOf(c0360l4Arr, c0360l4Arr.length * 2);
                }
                C0360l4[] c0360l4Arr2 = this.i;
                int i3 = this.j;
                c0360l4Arr2[i3] = c0360l4;
                this.j = i3 + 1;
                return;
            }
            if (this.i[i] == c0360l4) {
                return;
            } else {
                i++;
            }
        }
    }

    public final void b(C0360l4 c0360l4) {
        int i = this.j;
        int i2 = 0;
        while (i2 < i) {
            if (this.i[i2] == c0360l4) {
                while (i2 < i - 1) {
                    C0360l4[] c0360l4Arr = this.i;
                    int i3 = i2 + 1;
                    c0360l4Arr[i2] = c0360l4Arr[i3];
                    i2 = i3;
                }
                this.j--;
                return;
            }
            i2++;
        }
    }

    public final void c() {
        this.l = 5;
        this.d = 0;
        this.b = -1;
        this.c = -1;
        this.e = 0.0f;
        this.f = false;
        int i = this.j;
        for (int i2 = 0; i2 < i; i2++) {
            this.i[i2] = null;
        }
        this.j = 0;
        this.k = 0;
        this.a = false;
        Arrays.fill(this.h, 0.0f);
    }

    public final void d(C0360l4 c0360l4) {
        int i = this.j;
        for (int i2 = 0; i2 < i; i2++) {
            this.i[i2].h(c0360l4, false);
        }
        this.j = 0;
    }

    public final String toString() {
        return "" + this.b;
    }
}
