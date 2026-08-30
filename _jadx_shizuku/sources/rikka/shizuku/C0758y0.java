package rikka.shizuku;

import android.content.Context;
import android.view.View;
import moe.shizuku.privileged.api.R;

/* JADX INFO: renamed from: rikka.shizuku.y0, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0758y0 extends C0467oi {
    public final /* synthetic */ int l = 1;
    public final /* synthetic */ C0 m;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0758y0(C0 c0, Context context, MenuC0129di menuC0129di, View view) {
        super(R.attr._0_resource_name_obfuscated_res_0x7f040020, context, view, menuC0129di, true);
        this.m = c0;
        this.f = 8388613;
        C0789z0 c0789z0 = c0.u;
        this.h = c0789z0;
        AbstractC0374li abstractC0374li = this.i;
        if (abstractC0374li != null) {
            abstractC0374li.c(c0789z0);
        }
    }

    @Override // rikka.shizuku.C0467oi
    public final void c() {
        switch (this.l) {
            case 0:
                C0 c0 = this.m;
                c0.r = null;
                c0.getClass();
                super.c();
                break;
            default:
                C0 c1 = this.m;
                MenuC0129di menuC0129di = c1.c;
                if (menuC0129di != null) {
                    menuC0129di.c(true);
                }
                c1.q = null;
                super.c();
                break;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0758y0(C0 c0, Context context, SubMenuC0166ep subMenuC0166ep, View view) {
        super(R.attr._0_resource_name_obfuscated_res_0x7f040020, context, view, subMenuC0166ep, false);
        this.m = c0;
        if ((subMenuC0166ep.A.x & 32) != 32) {
            View view2 = c0.i;
            this.e = view2 == null ? (View) c0.h : view2;
        }
        C0789z0 c0789z0 = c0.u;
        this.h = c0789z0;
        AbstractC0374li abstractC0374li = this.i;
        if (abstractC0374li != null) {
            abstractC0374li.c(c0789z0);
        }
    }
}
