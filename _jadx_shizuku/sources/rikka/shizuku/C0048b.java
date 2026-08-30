package rikka.shizuku;

import rikka.html.text.style.BulletSpan;

/* JADX INFO: renamed from: rikka.shizuku.b, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0048b extends C {
    public final /* synthetic */ int c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ C0048b(int i, Class cls) {
        super(0, cls);
        this.c = i;
    }

    @Override // rikka.shizuku.C
    public AbstractC0633u b(AbstractC0726x abstractC0726x) {
        switch (this.c) {
            case 0:
                return abstractC0726x.w();
            case BulletSpan.STANDARD_BULLET_RADIUS /* 5 */:
                return abstractC0726x.x();
            case 6:
                return abstractC0726x;
            case 7:
                return abstractC0726x.y();
            default:
                return super.b(abstractC0726x);
        }
    }

    @Override // rikka.shizuku.C
    public AbstractC0633u c(A8 a8) {
        switch (this.c) {
            case 0:
                return AbstractC0079c.r(a8.a);
            case 1:
                return C0140e.r(a8.a);
            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                return new C0704w8(a8.a);
            case 3:
                return new C0386m(a8.a);
            case 4:
                byte[] bArr = a8.a;
                C0510q c0510q = (C0510q) C0510q.c.get(new C0479p(bArr));
                return c0510q == null ? new C0510q(false, bArr) : c0510q;
            case BulletSpan.STANDARD_BULLET_RADIUS /* 5 */:
                return a8;
            default:
                return super.c(a8);
        }
    }
}
