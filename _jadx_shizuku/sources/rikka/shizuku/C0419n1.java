package rikka.shizuku;

import moe.shizuku.manager.MainActivity;
import moe.shizuku.manager.starter.StarterActivity;
import rikka.html.text.style.BulletSpan;

/* JADX INFO: renamed from: rikka.shizuku.n1, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class C0419n1 implements InterfaceC0124dd {
    public final /* synthetic */ int a;
    public final /* synthetic */ Object b;

    public /* synthetic */ C0419n1(int i, Object obj) {
        this.a = i;
        this.b = obj;
    }

    @Override // rikka.shizuku.InterfaceC0124dd
    public final Object a() {
        int i = 0;
        Object obj = this.b;
        switch (this.a) {
            case 0:
                return new Ts(((C0573s1) obj).H());
            case 1:
                ((AbstractActivityC0051b2) obj).reportFullyDrawn();
                return null;
            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                int i2 = MainActivity.I;
                MainActivity mainActivity = (MainActivity) obj;
                return new C0710we((C0803ze) mainActivity.F.getValue(), mainActivity.A());
            case 3:
                return AbstractC0599sr.t((InterfaceC0047at) obj);
            case 4:
                Bm bm = (Bm) obj;
                bm.e().a(new Tk(i, bm));
                return Pe.b;
            default:
                int i3 = StarterActivity.E;
                StarterActivity starterActivity = (StarterActivity) obj;
                return new Us(starterActivity.getIntent().getBooleanExtra("moe.shizuku.manager.extra.IS_ROOT", true), starterActivity.getIntent().getStringExtra("moe.shizuku.manager.extra.HOST"), starterActivity.getIntent().getIntExtra("moe.shizuku.manager.extra.PORT", 0));
        }
    }
}
