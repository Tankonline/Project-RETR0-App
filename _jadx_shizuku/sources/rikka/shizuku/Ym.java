package rikka.shizuku;

import android.content.Context;
import android.os.IBinder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import moe.shizuku.privileged.api.R;

/* JADX INFO: loaded from: classes.dex */
public final class Ym extends X4 {
    public static final C0759y1 y = new C0759y1(5);
    public static final C0759y1 z = new C0759y1(7);
    public final /* synthetic */ int w = 1;
    public final Object x;

    public Ym(K3 k3, MaterialCardView materialCardView) {
        super(materialCardView);
        this.x = k3;
        Y0 y0 = new Y0(7, this);
        ((MaterialButton) k3.a).setOnClickListener(y0);
        ((MaterialButton) k3.b).setOnClickListener(y0);
        ((TextView) k3.c).setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override // rikka.shizuku.X4
    public final void q() {
        String string;
        View view = this.a;
        Object obj = this.x;
        switch (this.w) {
            case 0:
                Context context = view.getContext();
                C0103cn c0103cn = (C0103cn) this.u;
                boolean zA = c0103cn.a();
                boolean z2 = c0103cn.a == 0;
                K3 k3 = (K3) obj;
                if (zA) {
                    ((ImageView) k3.a).setImageDrawable(context.getDrawable(R.drawable._0_resource_name_obfuscated_res_0x7f0800c8));
                } else {
                    ((ImageView) k3.a).setImageDrawable(context.getDrawable(R.drawable._0_resource_name_obfuscated_res_0x7f0800c7));
                }
                String str = z2 ? "root" : "adb";
                String string2 = zA ? context.getString(R.string._0_resource_name_obfuscated_res_0x7f110080, context.getString(R.string._0_resource_name_obfuscated_res_0x7f110032)) : context.getString(R.string._0_resource_name_obfuscated_res_0x7f110081, context.getString(R.string._0_resource_name_obfuscated_res_0x7f110032));
                if (zA) {
                    IBinder iBinder = Nn.a;
                    int i = c0103cn.c;
                    int i2 = c0103cn.b;
                    string = (i2 == 13 && i == 5) ? context.getString(R.string._0_resource_name_obfuscated_res_0x7f110082, str, i2 + "." + i) : context.getString(R.string._0_resource_name_obfuscated_res_0x7f110083, str, i2 + "." + i, "13.5");
                } else {
                    string = "";
                }
                ((TextView) k3.b).setText(AbstractC0046as.Y(string2));
                TextView textView = (TextView) k3.c;
                textView.setText(AbstractC0046as.Y(string));
                if (!TextUtils.isEmpty(textView.getText())) {
                    textView.setVisibility(0);
                } else {
                    textView.setVisibility(8);
                }
                break;
            default:
                K3 k4 = (K3) obj;
                ((MaterialButton) k4.a).setEnabled(true);
                ((MaterialButton) k4.b).setEnabled(true);
                if (((Boolean) this.u).booleanValue()) {
                    ((MaterialButton) k4.a).setVisibility(8);
                    ((MaterialButton) k4.b).setVisibility(0);
                } else {
                    ((MaterialButton) k4.a).setVisibility(0);
                    ((MaterialButton) k4.b).setVisibility(8);
                }
                StringBuilder sb = new StringBuilder();
                sb.append(view.getContext().getString(R.string._0_resource_name_obfuscated_res_0x7f11007d, "<b><a href=\"https://dontkillmyapp.com/\">Don't kill my app!</a></b>"));
                if (Nn.h()) {
                    sb.append("<p>");
                    sb.append(view.getContext().getString(R.string._0_resource_name_obfuscated_res_0x7f11007e, AbstractC0535qo.d("<b><a href=\"", AbstractC0494pe.d.a(), "\">Sui</a></b>"), "Sui"));
                }
                ((TextView) k4.c).setText(AbstractC0688vn.A(sb, 512));
                break;
        }
    }

    @Override // rikka.shizuku.X4
    public void s() {
        int i = this.w;
    }

    public Ym(K3 k3, MaterialCardView materialCardView, byte b) {
        super(materialCardView);
        this.x = k3;
    }

    private final void t() {
    }
}
