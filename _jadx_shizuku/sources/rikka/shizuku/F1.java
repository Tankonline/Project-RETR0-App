package rikka.shizuku;

import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.preference.PreferenceScreen;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
public final class F1 extends Handler {
    public final /* synthetic */ int a = 0;
    public Object b;

    public /* synthetic */ F1() {
    }

    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        switch (this.a) {
            case 0:
                int i = message.what;
                if (i == -3 || i == -2 || i == -1) {
                    ((DialogInterface.OnClickListener) message.obj).onClick((DialogInterface) ((WeakReference) this.b).get(), message.what);
                    break;
                } else if (i == 1) {
                    ((DialogInterface) message.obj).dismiss();
                    break;
                }
                break;
            default:
                if (message.what == 1) {
                    AbstractC0284ik abstractC0284ik = (AbstractC0284ik) this.b;
                    PreferenceScreen preferenceScreen = abstractC0284ik.X.h;
                    if (preferenceScreen != null) {
                        abstractC0284ik.Y.h0(new C0376lk(preferenceScreen));
                        preferenceScreen.k();
                    }
                    break;
                }
                break;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public F1(AbstractC0284ik abstractC0284ik, Looper looper) {
        super(looper);
        this.b = abstractC0284ik;
    }
}
