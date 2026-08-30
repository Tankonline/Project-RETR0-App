package rikka.shizuku;

import android.text.TextUtils;
import java.io.IOException;

/* JADX INFO: renamed from: rikka.shizuku.q5, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0516q5 {
    public int a;

    public C0516q5(int i) {
        switch (i) {
            case 1:
                this.a = -1;
                break;
            default:
                this.a = 0;
                break;
        }
    }

    public Gn a(String... strArr) {
        try {
            TextUtils.join(" ", strArr);
            try {
                Gn gn = new Gn(this, Runtime.getRuntime().exec(strArr));
                if ((this.a & 8) == 8) {
                    Gn.k = true;
                }
                synchronized (AbstractC0744xh.class) {
                    if (AbstractC0744xh.b) {
                        Gn[] gnArr = AbstractC0744xh.a;
                        synchronized (gnArr) {
                            gnArr[0] = gn;
                        }
                    }
                }
                return gn;
            } catch (IOException e) {
                throw new Zi("Unable to create a shell!", e);
            }
        } catch (IOException e2) {
            throw new Zi("Unable to create a shell!", e2);
        }
    }
}
