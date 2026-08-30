package rikka.shizuku;

import android.os.Handler;

/* JADX INFO: renamed from: rikka.shizuku.uk, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0654uk implements InterfaceC0743xg {
    public static final C0654uk i = new C0654uk();
    public int a;
    public int b;
    public Handler e;
    public boolean c = true;
    public boolean d = true;
    public final C0805zg f = new C0805zg(this);
    public final G0 g = new G0(12, this);
    public final Ys h = new Ys(29, this);

    public final void a() {
        int i2 = this.b + 1;
        this.b = i2;
        if (i2 == 1) {
            if (!this.c) {
                this.e.removeCallbacks(this.g);
            } else {
                this.f.d(EnumC0557rg.ON_RESUME);
                this.c = false;
            }
        }
    }

    @Override // rikka.shizuku.InterfaceC0743xg
    public final C0805zg e() {
        return this.f;
    }
}
