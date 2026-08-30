package rikka.shizuku;

import android.R;
import android.content.DialogInterface;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;
import moe.shizuku.manager.authorization.RequestPermissionActivity;

/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class R3 implements DialogInterface.OnShowListener {
    public final /* synthetic */ int a;

    @Override // android.content.DialogInterface.OnShowListener
    public final void onShow(DialogInterface dialogInterface) {
        switch (this.a) {
            case 0:
                TextView textView = (TextView) ((J1) dialogInterface).findViewById(R.id.message);
                if (textView != null) {
                    textView.setMovementMethod(LinkMovementMethod.getInstance());
                }
                break;
            default:
                int i = RequestPermissionActivity.B;
                TextView textView2 = (TextView) ((J1) dialogInterface).findViewById(R.id.message);
                if (textView2 != null) {
                    textView2.setMovementMethod(LinkMovementMethod.getInstance());
                }
                break;
        }
    }
}
