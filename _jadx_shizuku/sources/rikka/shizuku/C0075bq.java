package rikka.shizuku;

import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* JADX INFO: renamed from: rikka.shizuku.bq, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0075bq implements InterfaceC0067bi {
    public final /* synthetic */ Toolbar a;

    public /* synthetic */ C0075bq(Toolbar toolbar) {
        this.a = toolbar;
    }

    @Override // rikka.shizuku.InterfaceC0067bi
    public void a(MenuC0129di menuC0129di) {
        Toolbar toolbar = this.a;
        C0 c0 = toolbar.a.t;
        if (c0 == null || !c0.j()) {
            Iterator it = ((CopyOnWriteArrayList) toolbar.G.c).iterator();
            while (it.hasNext()) {
                ((Jc) it.next()).a.t();
            }
        }
        C0229gq c0229gq = toolbar.O;
        if (c0229gq != null) {
            c0229gq.a(menuC0129di);
        }
    }

    @Override // rikka.shizuku.InterfaceC0067bi
    public boolean n(MenuC0129di menuC0129di, MenuItem menuItem) {
        C0229gq c0229gq = this.a.O;
        return false;
    }
}
