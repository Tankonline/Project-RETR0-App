package com.google.android.material.theme;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import com.google.android.material.button.MaterialButton;
import moe.shizuku.privileged.api.R;
import rikka.shizuku.C0389m2;
import rikka.shizuku.C0420n2;
import rikka.shizuku.C0482p2;
import rikka.shizuku.C0606t3;
import rikka.shizuku.D3;
import rikka.shizuku.Eh;
import rikka.shizuku.Lk;
import rikka.shizuku.S2;
import rikka.shizuku.Th;
import rikka.shizuku.Vh;
import rikka.shizuku.X8;
import rikka.shizuku.Zh;

/* JADX INFO: loaded from: classes.dex */
public class MaterialComponentsViewInflater extends D3 {
    @Override // rikka.shizuku.D3
    public final C0389m2 a(Context context, AttributeSet attributeSet) {
        return new Eh(context, attributeSet);
    }

    @Override // rikka.shizuku.D3
    public C0420n2 b(Context context, AttributeSet attributeSet) {
        return new MaterialButton(context, attributeSet);
    }

    @Override // rikka.shizuku.D3
    public final C0482p2 c(Context context, AttributeSet attributeSet) {
        return new Th(context, attributeSet);
    }

    @Override // rikka.shizuku.D3
    public final S2 e(Context context, AttributeSet attributeSet) {
        Vh vh = new Vh(X8.c0(context, attributeSet, R.attr._0_resource_name_obfuscated_res_0x7f040400, R.style._0_resource_name_obfuscated_res_0x7f12047e), attributeSet);
        Context context2 = vh.getContext();
        TypedArray typedArrayJ = X8.J(context2, attributeSet, Lk.t, R.attr._0_resource_name_obfuscated_res_0x7f040400, R.style._0_resource_name_obfuscated_res_0x7f12047e, new int[0]);
        if (typedArrayJ.hasValue(0)) {
            vh.setButtonTintList(X8.w(context2, typedArrayJ, 0));
        }
        vh.f = typedArrayJ.getBoolean(1, false);
        typedArrayJ.recycle();
        return vh;
    }

    @Override // rikka.shizuku.D3
    public C0606t3 f(Context context, AttributeSet attributeSet) {
        return new Zh(context, attributeSet);
    }
}
