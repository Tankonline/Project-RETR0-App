package rikka.shizuku;

/* JADX INFO: renamed from: rikka.shizuku.jq, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0320jq extends AbstractC0688vn {
    public final /* synthetic */ int b;
    public boolean c;
    public int d;
    public final /* synthetic */ Object e;

    public C0320jq(C0351kq c0351kq, int i) {
        this.b = 0;
        this.e = c0351kq;
        this.d = i;
        this.c = false;
    }

    @Override // rikka.shizuku.InterfaceC0170et
    public final void a() {
        switch (this.b) {
            case 0:
                if (!this.c) {
                    ((C0351kq) this.e).a.setVisibility(this.d);
                }
                break;
            default:
                int i = this.d + 1;
                this.d = i;
                C0139dt c0139dt = (C0139dt) this.e;
                if (i == c0139dt.a.size()) {
                    AbstractC0688vn abstractC0688vn = c0139dt.d;
                    if (abstractC0688vn != null) {
                        abstractC0688vn.a();
                    }
                    this.d = 0;
                    this.c = false;
                    c0139dt.e = false;
                }
                break;
        }
    }

    @Override // rikka.shizuku.AbstractC0688vn, rikka.shizuku.InterfaceC0170et
    public void b() {
        switch (this.b) {
            case 0:
                this.c = true;
                break;
        }
    }

    @Override // rikka.shizuku.AbstractC0688vn, rikka.shizuku.InterfaceC0170et
    public final void c() {
        switch (this.b) {
            case 0:
                ((C0351kq) this.e).a.setVisibility(0);
                break;
            default:
                if (!this.c) {
                    this.c = true;
                    AbstractC0688vn abstractC0688vn = ((C0139dt) this.e).d;
                    if (abstractC0688vn != null) {
                        abstractC0688vn.c();
                    }
                    break;
                }
                break;
        }
    }

    public C0320jq(C0139dt c0139dt) {
        this.b = 1;
        this.e = c0139dt;
        this.c = false;
        this.d = 0;
    }
}
