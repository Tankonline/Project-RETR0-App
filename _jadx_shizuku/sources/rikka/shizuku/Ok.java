package rikka.shizuku;

/* JADX INFO: loaded from: classes.dex */
public final class Ok extends AbstractC0417n {
    public AbstractC0757y a;

    @Override // rikka.shizuku.InterfaceC0202g
    public final AbstractC0633u d() {
        return this.a;
    }

    public final C0515q4 i() {
        InterfaceC0202g[] interfaceC0202gArr = this.a.a;
        if (interfaceC0202gArr.length == 0) {
            return null;
        }
        return C0515q4.i(interfaceC0202gArr[0]);
    }

    public final C0515q4[] j() {
        AbstractC0757y abstractC0757y = this.a;
        int length = abstractC0757y.a.length;
        C0515q4[] c0515q4Arr = new C0515q4[length];
        for (int i = 0; i != length; i++) {
            c0515q4Arr[i] = C0515q4.i(abstractC0757y.a[i]);
        }
        return c0515q4Arr;
    }
}
