package rikka.shizuku;

import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import moe.shizuku.manager.management.ApplicationManagementActivity;

/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class Z0 implements InterfaceC0462od {
    public final /* synthetic */ int a;
    public final /* synthetic */ Object b;

    public /* synthetic */ Z0(int i, Object obj) {
        this.a = i;
        this.b = obj;
    }

    @Override // rikka.shizuku.InterfaceC0462od
    public final Object g(Object obj) {
        Pe pe = Pe.b;
        Object obj2 = this.b;
        switch (this.a) {
            case 0:
                Integer num = (Integer) obj;
                if (num.intValue() <= 65535 && num.intValue() >= 1) {
                    ((C0050b1) obj2).Q(num.intValue());
                }
                return pe;
            case 1:
                Rl rl = (Rl) obj;
                int i = ApplicationManagementActivity.G;
                int iJ = AbstractC0535qo.j(rl.a);
                ApplicationManagementActivity applicationManagementActivity = (ApplicationManagementActivity) obj2;
                if (iJ == 0) {
                    List list = (List) rl.b;
                    W3 w3 = applicationManagementActivity.E;
                    ArrayList arrayList = w3.d;
                    arrayList.clear();
                    if (list.isEmpty()) {
                        arrayList.add(new Object());
                    } else {
                        arrayList.addAll(list);
                    }
                    w3.a.b();
                } else if (iJ == 1) {
                    applicationManagementActivity.finish();
                    Throwable th = rl.c;
                    Toast.makeText(applicationManagementActivity, Objects.toString(th, "unknown"), 0).show();
                    th.printStackTrace();
                } else if (iJ != 2) {
                    throw new K6();
                }
                return pe;
            default:
                Us us = (Us) obj2;
                us.b.append(new String((byte[]) obj, P5.a));
                us.d(null);
                return pe;
        }
    }
}
