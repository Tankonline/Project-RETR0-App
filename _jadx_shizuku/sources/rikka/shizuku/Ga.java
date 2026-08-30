package rikka.shizuku;

import android.content.Context;
import moe.shizuku.privileged.api.R;

/* JADX INFO: loaded from: classes.dex */
public final class Ga {
    public static final int f = (int) Math.round(5.1000000000000005d);
    public final boolean a;
    public final int b;
    public final int c;
    public final int d;
    public final float e;

    public Ga(Context context) {
        boolean zN = AbstractC0046as.N(R.attr._0_resource_name_obfuscated_res_0x7f0401ad, context, false);
        int iR = AbstractC0744xh.r(context, R.attr._0_resource_name_obfuscated_res_0x7f0401ac, 0);
        int iR2 = AbstractC0744xh.r(context, R.attr._0_resource_name_obfuscated_res_0x7f0401ab, 0);
        int iR3 = AbstractC0744xh.r(context, R.attr._0_resource_name_obfuscated_res_0x7f04011f, 0);
        float f2 = context.getResources().getDisplayMetrics().density;
        this.a = zN;
        this.b = iR;
        this.c = iR2;
        this.d = iR3;
        this.e = f2;
    }
}
