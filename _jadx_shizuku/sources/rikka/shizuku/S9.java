package rikka.shizuku;

import android.view.View;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public final class S9 implements InterfaceC0313jj {
    public final /* synthetic */ U9 a;

    public S9(U9 u9) {
        this.a = u9;
    }

    @Override // rikka.shizuku.InterfaceC0313jj
    public final void b(Object obj) {
        if (((InterfaceC0743xg) obj) != null) {
            U9 u9 = this.a;
            if (u9.d0) {
                View viewI = u9.I();
                if (viewI.getParent() != null) {
                    throw new IllegalStateException("DialogFragment can not be attached to a container view");
                }
                if (u9.h0 != null) {
                    if (Qc.J(3)) {
                        Objects.toString(u9.h0);
                    }
                    u9.h0.setContentView(viewI);
                }
            }
        }
    }
}
