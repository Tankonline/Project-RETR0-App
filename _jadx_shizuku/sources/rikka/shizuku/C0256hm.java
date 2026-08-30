package rikka.shizuku;

/* JADX INFO: renamed from: rikka.shizuku.hm, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0256hm extends AbstractC0046as {
    @Override // rikka.shizuku.AbstractC0046as
    public final void v(C0626tn c0626tn, float f, float f2) {
        c0626tn.d(f2 * f, 180.0f, 90.0f);
        float f3 = f2 * 2.0f * f;
        C0503pn c0503pn = new C0503pn(0.0f, 0.0f, f3, f3);
        c0503pn.f = 180.0f;
        c0503pn.g = 90.0f;
        c0626tn.f.add(c0503pn);
        C0441nn c0441nn = new C0441nn(c0503pn);
        c0626tn.a(180.0f);
        c0626tn.g.add(c0441nn);
        c0626tn.d = 270.0f;
        float f4 = (0.0f + f3) * 0.5f;
        float f5 = (f3 - 0.0f) / 2.0f;
        double d = 270.0f;
        c0626tn.b = (((float) Math.cos(Math.toRadians(d))) * f5) + f4;
        c0626tn.c = (f5 * ((float) Math.sin(Math.toRadians(d)))) + f4;
    }
}
