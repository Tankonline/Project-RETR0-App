package rikka.shizuku;

import moe.shizuku.manager.MainActivity;
import moe.shizuku.manager.starter.StarterActivity;
import rikka.html.text.style.BulletSpan;

/* JADX INFO: renamed from: rikka.shizuku.r1, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0542r1 extends AbstractC0127dg implements InterfaceC0124dd {
    public final /* synthetic */ int b;
    public final /* synthetic */ Object c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ C0542r1(int i, Object obj) {
        super(0);
        this.b = i;
        this.c = obj;
    }

    @Override // rikka.shizuku.InterfaceC0124dd
    public final Object a() {
        switch (this.b) {
            case 0:
                return (C0573s1) this.c;
            case 1:
                return ((C0573s1) ((C0542r1) this.c).c).d();
            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                return ((T6) this.c).d();
            case 3:
                return ((MainActivity) this.c).d();
            default:
                return ((StarterActivity) this.c).d();
        }
    }
}
