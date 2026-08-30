package rikka.shizuku;

/* JADX INFO: loaded from: classes.dex */
public final class Ea {
    public final Fa a;
    public final C0545r4 b;
    public Ea c;
    public boolean d;

    public Ea(Fa fa, boolean z) {
        this.a = fa;
        if (z) {
            C0545r4 c0545r4 = fa.g;
            C0545r4 c0545r5 = new C0545r4();
            if (c0545r5.b != null) {
                for (int i = 0; i < c0545r5.a * 5; i++) {
                    c0545r5.b[i] = null;
                }
            }
            c0545r5.a = 0;
            int length = c0545r4.getLength();
            c0545r5.a = length;
            if (length > 0) {
                c0545r5.b = new String[length * 5];
                for (int i2 = 0; i2 < c0545r5.a; i2++) {
                    int i3 = i2 * 5;
                    c0545r5.b[i3] = c0545r4.getURI(i2);
                    c0545r5.b[i3 + 1] = c0545r4.getLocalName(i2);
                    c0545r5.b[i3 + 2] = c0545r4.getQName(i2);
                    c0545r5.b[i3 + 3] = c0545r4.getType(i2);
                    c0545r5.b[i3 + 4] = c0545r4.getValue(i2);
                }
            }
            this.b = c0545r5;
        } else {
            this.b = new C0545r4();
        }
        this.c = null;
        this.d = false;
    }

    public final boolean a(Ea ea) {
        return (ea.a.e & this.a.d) != 0;
    }
}
