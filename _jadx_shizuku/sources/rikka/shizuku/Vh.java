package rikka.shizuku;

import android.R;
import android.content.res.ColorStateList;

/* JADX INFO: loaded from: classes.dex */
public final class Vh extends S2 {
    public static final int[][] g = {new int[]{R.attr.state_enabled, R.attr.state_checked}, new int[]{R.attr.state_enabled, -16842912}, new int[]{-16842910, R.attr.state_checked}, new int[]{-16842910, -16842912}};
    public ColorStateList e;
    public boolean f;

    @Override // android.widget.TextView, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.f && getButtonTintList() == null) {
            this.f = true;
            if (this.e == null) {
                int iS = AbstractC0744xh.s(this, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f0400f4);
                int iS2 = AbstractC0744xh.s(this, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f040108);
                int iS3 = AbstractC0744xh.s(this, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f04011f);
                this.e = new ColorStateList(g, new int[]{AbstractC0744xh.H(iS3, iS, 1.0f), AbstractC0744xh.H(iS3, iS2, 0.54f), AbstractC0744xh.H(iS3, iS2, 0.38f), AbstractC0744xh.H(iS3, iS2, 0.38f)});
            }
            setButtonTintList(this.e);
        }
    }
}
