package rikka.shizuku;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import com.google.android.material.card.MaterialCardView;
import moe.shizuku.manager.management.ApplicationManagementActivity;
import moe.shizuku.privileged.api.R;

/* JADX INFO: renamed from: rikka.shizuku.zh, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class ViewOnClickListenerC0806zh extends X4 implements View.OnClickListener {
    public static final C0759y1 x = new C0759y1(4);
    public final R2 w;

    public ViewOnClickListenerC0806zh(R2 r2, MaterialCardView materialCardView) {
        super(materialCardView);
        this.w = r2;
        materialCardView.setOnClickListener(this);
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        view.getContext().startActivity(new Intent(view.getContext(), (Class<?>) ApplicationManagementActivity.class));
    }

    @Override // rikka.shizuku.X4
    public final void q() {
        View view = this.a;
        Context context = view.getContext();
        boolean zA = ((C0103cn) ((Hj) this.u).a).a();
        R2 r2 = this.w;
        if (zA) {
            view.setEnabled(true);
            ((TextView) r2.b).setText(context.getResources().getQuantityString(R.plurals._0_resource_name_obfuscated_res_0x7f100000, ((Number) ((Hj) this.u).b).intValue(), ((Hj) this.u).b));
            ((TextView) r2.c).setText(context.getString(R.string._0_resource_name_obfuscated_res_0x7f110078));
        } else {
            view.setEnabled(false);
            ((TextView) r2.b).setText(R.string._0_resource_name_obfuscated_res_0x7f110077);
            ((TextView) r2.c).setText(context.getString(R.string._0_resource_name_obfuscated_res_0x7f110081, context.getString(R.string._0_resource_name_obfuscated_res_0x7f110032)));
        }
    }
}
