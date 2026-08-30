package rikka.shizuku;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.Locale;
import moe.shizuku.privileged.api.R;

/* JADX INFO: loaded from: classes.dex */
public final class qu extends Yk {
    public final Oh d;

    public qu(Oh oh) {
        this.d = oh;
    }

    @Override // rikka.shizuku.Yk
    public final int a() {
        return this.d.Y.f;
    }

    @Override // rikka.shizuku.Yk
    public final void d(AbstractC0470ol abstractC0470ol, int i) {
        Oh oh = this.d;
        int i2 = oh.Y.a.c + i;
        String str = String.format(Locale.getDefault(), "%d", Integer.valueOf(i2));
        TextView textView = ((pu) abstractC0470ol).u;
        textView.setText(str);
        Context context = textView.getContext();
        textView.setContentDescription(Zr.b().get(1) == i2 ? String.format(context.getString(R.string._0_resource_name_obfuscated_res_0x7f1100d2), Integer.valueOf(i2)) : String.format(context.getString(R.string._0_resource_name_obfuscated_res_0x7f1100d3), Integer.valueOf(i2)));
        R2 r2 = oh.b0;
        if (Zr.b().get(1) == i2) {
            Object obj = r2.c;
        } else {
            Object obj2 = r2.b;
        }
        throw null;
    }

    @Override // rikka.shizuku.Yk
    public final AbstractC0470ol f(ViewGroup viewGroup, int i) {
        return new pu((TextView) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout._0_resource_name_obfuscated_res_0x7f0c0071, viewGroup, false));
    }
}
