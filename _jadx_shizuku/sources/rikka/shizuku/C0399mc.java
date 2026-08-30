package rikka.shizuku;

import java.util.ArrayList;

/* JADX INFO: renamed from: rikka.shizuku.mc, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0399mc implements InterfaceC0734x7 {
    public final /* synthetic */ int a;
    public final /* synthetic */ Object b;

    public /* synthetic */ C0399mc(int i, Object obj) {
        this.a = i;
        this.b = obj;
    }

    @Override // rikka.shizuku.InterfaceC0734x7
    public final void a(Object obj) {
        switch (this.a) {
            case 0:
                C0430nc c0430nc = (C0430nc) obj;
                if (c0430nc == null) {
                    c0430nc = new C0430nc(-3);
                }
                ((R2) this.b).N(c0430nc);
                return;
            default:
                C0430nc c0430nc2 = (C0430nc) obj;
                synchronized (AbstractC0461oc.c) {
                    try {
                        C0104co c0104co = AbstractC0461oc.d;
                        ArrayList arrayList = (ArrayList) c0104co.get((String) this.b);
                        if (arrayList == null) {
                            return;
                        }
                        c0104co.remove((String) this.b);
                        for (int i = 0; i < arrayList.size(); i++) {
                            ((InterfaceC0734x7) arrayList.get(i)).a(c0430nc2);
                        }
                        return;
                    } catch (Throwable th) {
                        throw th;
                    }
                }
        }
    }
}
