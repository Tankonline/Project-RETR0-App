package rikka.shizuku;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;
import moe.shizuku.manager.MainActivity;
import moe.shizuku.manager.legacy.LegacyIsNotSupportedActivity;
import moe.shizuku.privileged.api.R;

/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class Oo implements DialogInterface.OnClickListener {
    public final /* synthetic */ int a;
    public final /* synthetic */ Context b;

    public /* synthetic */ Oo(Context context, int i) {
        this.a = i;
        this.b = context;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        Context context = this.b;
        switch (this.a) {
            case 0:
                String str = Qo.a;
                try {
                    ((ClipboardManager) context.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("label", str));
                    Toast.makeText(context, context.getString(R.string._0_resource_name_obfuscated_res_0x7f11012d, str), 0).show();
                } catch (Exception unused) {
                    return;
                }
                break;
            case 1:
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("text/plain");
                intent.putExtra("android.intent.extra.TEXT", Qo.a);
                context.startActivity(Intent.createChooser(intent, context.getString(R.string._0_resource_name_obfuscated_res_0x7f110070)));
                break;
            default:
                int i2 = LegacyIsNotSupportedActivity.A;
                LegacyIsNotSupportedActivity legacyIsNotSupportedActivity = (LegacyIsNotSupportedActivity) context;
                legacyIsNotSupportedActivity.startActivity(new Intent(legacyIsNotSupportedActivity, (Class<?>) MainActivity.class).addFlags(268435456));
                break;
        }
    }
}
