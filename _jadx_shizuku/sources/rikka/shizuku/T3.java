package rikka.shizuku;

import moe.shizuku.manager.MainActivity;
import moe.shizuku.manager.management.ApplicationManagementActivity;

/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class T3 {
    public final /* synthetic */ int a;
    public final /* synthetic */ AbstractActivityC0113d2 b;

    public /* synthetic */ T3(AbstractActivityC0113d2 abstractActivityC0113d2, int i) {
        this.a = i;
        this.b = abstractActivityC0113d2;
    }

    public final void a() {
        AbstractActivityC0113d2 abstractActivityC0113d2 = this.b;
        switch (this.a) {
            case 0:
                int i = ApplicationManagementActivity.G;
                ApplicationManagementActivity applicationManagementActivity = (ApplicationManagementActivity) abstractActivityC0113d2;
                if (!applicationManagementActivity.isFinishing()) {
                    applicationManagementActivity.finish();
                }
                break;
            default:
                int i2 = MainActivity.I;
                ((MainActivity) abstractActivityC0113d2).z();
                break;
        }
    }
}
