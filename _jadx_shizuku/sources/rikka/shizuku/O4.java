package rikka.shizuku;

/* JADX INFO: loaded from: classes.dex */
public final class O4 extends AbstractC0056b7 {
    public int g;
    public int h;
    public P4 i;

    @Override // rikka.shizuku.AbstractC0056b7
    public final void e(C0425n7 c0425n7, boolean z) {
        int i = this.g;
        this.h = i;
        if (z) {
            if (i == 5) {
                this.h = 1;
            } else if (i == 6) {
                this.h = 0;
            }
        } else if (i == 5) {
            this.h = 0;
        } else if (i == 6) {
            this.h = 1;
        }
        if (c0425n7 instanceof P4) {
            ((P4) c0425n7).f0 = this.h;
        }
    }
}
