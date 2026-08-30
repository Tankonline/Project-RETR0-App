package rikka.shizuku;

import java.util.HashMap;
import rikka.shizuku.server.ShizukuService;

/* JADX INFO: loaded from: classes.dex */
public final class Zm {
    public static final HashMap b = new HashMap();
    public static final boolean c;
    public final /* synthetic */ ShizukuService a;

    static {
        c = android.system.Os.getuid() == 0;
    }

    public Zm(ShizukuService shizukuService) {
        this.a = shizukuService;
    }
}
