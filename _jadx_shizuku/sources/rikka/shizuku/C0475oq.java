package rikka.shizuku;

import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import moe.shizuku.privileged.api.R;

/* JADX INFO: renamed from: rikka.shizuku.oq, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0475oq {
    public final Context a;
    public final View b;
    public final TextView c;
    public final WindowManager.LayoutParams d;
    public final Rect e;
    public final int[] f;
    public final int[] g;

    public C0475oq(Context context) {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        this.d = layoutParams;
        this.e = new Rect();
        this.f = new int[2];
        this.g = new int[2];
        this.a = context;
        View viewInflate = LayoutInflater.from(context).inflate(R.layout._0_resource_name_obfuscated_res_0x7f0c001b, (ViewGroup) null);
        this.b = viewInflate;
        this.c = (TextView) viewInflate.findViewById(R.id._0_resource_name_obfuscated_res_0x7f090103);
        layoutParams.setTitle(C0475oq.class.getSimpleName());
        layoutParams.packageName = context.getPackageName();
        layoutParams.type = 1002;
        layoutParams.width = -2;
        layoutParams.height = -2;
        layoutParams.format = -3;
        layoutParams.windowAnimations = R.style._0_resource_name_obfuscated_res_0x7f120004;
        layoutParams.flags = 24;
    }
}
