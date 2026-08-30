package rikka.shizuku;

import android.os.Bundle;
import java.util.LinkedHashMap;

/* JADX INFO: loaded from: classes.dex */
public final class Am {
    public final Bm a;
    public final C0419n1 b;
    public boolean e;
    public Bundle f;
    public boolean g;
    public final Me c = new Me();
    public final LinkedHashMap d = new LinkedHashMap();
    public boolean h = true;

    public Am(Bm bm, C0419n1 c0419n1) {
        this.a = bm;
        this.b = c0419n1;
    }

    public final void a() {
        Bm bm = this.a;
        if (bm.e().c != EnumC0588sg.b) {
            throw new IllegalStateException("Restarter must be created only during owner's initialization stage");
        }
        if (this.e) {
            throw new IllegalStateException("SavedStateRegistry was already attached.");
        }
        this.b.a();
        bm.e().a(new InterfaceC0681vg() { // from class: rikka.shizuku.zm
            @Override // rikka.shizuku.InterfaceC0681vg
            public final void a(InterfaceC0743xg interfaceC0743xg, EnumC0557rg enumC0557rg) {
                EnumC0557rg enumC0557rg2 = EnumC0557rg.ON_START;
                Am am = this.a;
                if (enumC0557rg == enumC0557rg2) {
                    am.h = true;
                } else if (enumC0557rg == EnumC0557rg.ON_STOP) {
                    am.h = false;
                }
            }
        });
        this.e = true;
    }
}
