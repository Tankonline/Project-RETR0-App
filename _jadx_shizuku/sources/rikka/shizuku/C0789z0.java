package rikka.shizuku;

/* JADX INFO: renamed from: rikka.shizuku.z0, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0789z0 implements InterfaceC0652ui {
    public final /* synthetic */ C0 a;

    @Override // rikka.shizuku.InterfaceC0652ui
    public void b(MenuC0129di menuC0129di, boolean z) {
        if (menuC0129di instanceof SubMenuC0166ep) {
            ((SubMenuC0166ep) menuC0129di).z.k().c(false);
        }
        InterfaceC0652ui interfaceC0652ui = this.a.e;
        if (interfaceC0652ui != null) {
            interfaceC0652ui.b(menuC0129di, z);
        }
    }

    @Override // rikka.shizuku.InterfaceC0652ui
    public boolean c(MenuC0129di menuC0129di) {
        C0 c0 = this.a;
        if (menuC0129di == c0.c) {
            return false;
        }
        ((SubMenuC0166ep) menuC0129di).A.getClass();
        c0.getClass();
        InterfaceC0652ui interfaceC0652ui = c0.e;
        if (interfaceC0652ui != null) {
            return interfaceC0652ui.c(menuC0129di);
        }
        return false;
    }
}
