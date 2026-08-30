package rikka.shizuku;

import java.util.Arrays;
import moe.shizuku.manager.MainActivity;
import moe.shizuku.manager.shell.Shell;

/* JADX INFO: renamed from: rikka.shizuku.ue, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class C0648ue implements Mn {
    public final /* synthetic */ int a;
    public final /* synthetic */ Object b;

    public /* synthetic */ C0648ue(int i, Object obj) {
        this.a = i;
        this.b = obj;
    }

    @Override // rikka.shizuku.Mn
    public final void a() {
        Object obj = this.b;
        switch (this.a) {
            case 0:
                int i = MainActivity.I;
                MainActivity mainActivity = (MainActivity) obj;
                mainActivity.z();
                Y3 y3A = mainActivity.A();
                y3A.getClass();
                C0517q6 c0517q6A = AbstractC0744xh.A(y3A);
                C0550r9 c0550r9 = AbstractC0090ca.a;
                ExecutorC0243h9 executorC0243h9 = ExecutorC0243h9.b;
                AbstractC0599sr.z(c0517q6A, new X3(false, y3A, null), 2);
                break;
            default:
                int iF = Nn.f();
                if (iF < 12) {
                    System.err.println("Rish requires server 12 (running " + iF + ")");
                    System.err.flush();
                    System.exit(1);
                }
                Shell shell = new Shell();
                String[] strArr = (String[]) obj;
                Arrays.toString(strArr);
                shell.a(strArr, false);
                break;
        }
    }
}
