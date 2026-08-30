package rikka.shizuku;

import android.view.View;
import android.view.ViewGroup;
import moe.shizuku.privileged.api.R;

/* JADX INFO: loaded from: classes.dex */
public final class Ec implements View.OnAttachStateChangeListener {
    public final /* synthetic */ Xc a;
    public final /* synthetic */ Fc b;

    public Ec(Fc fc, Xc xc) {
        this.b = fc;
        this.a = xc;
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewAttachedToWindow(View view) {
        E9 e9;
        Xc xc = this.a;
        xc.k();
        ViewGroup viewGroup = (ViewGroup) xc.c.G.getParent();
        this.b.a.H();
        Object tag = viewGroup.getTag(R.id._0_resource_name_obfuscated_res_0x7f09019c);
        if (tag instanceof E9) {
            e9 = (E9) tag;
        } else {
            e9 = new E9(viewGroup);
            viewGroup.setTag(R.id._0_resource_name_obfuscated_res_0x7f09019c, e9);
        }
        e9.h();
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewDetachedFromWindow(View view) {
    }
}
