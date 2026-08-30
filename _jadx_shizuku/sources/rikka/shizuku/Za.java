package rikka.shizuku;

import android.widget.EditText;
import androidx.appcompat.widget.SwitchCompat;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
public final class Za extends Ka {
    public final /* synthetic */ int a = 0;
    public final WeakReference b;

    public Za(EditText editText) {
        this.b = new WeakReference(editText);
    }

    @Override // rikka.shizuku.Ka
    public void a() {
        switch (this.a) {
            case 1:
                SwitchCompat switchCompat = (SwitchCompat) this.b.get();
                if (switchCompat != null) {
                    switchCompat.g(switchCompat.o);
                    switchCompat.f(switchCompat.q);
                    switchCompat.requestLayout();
                }
                break;
        }
    }

    @Override // rikka.shizuku.Ka
    public final void b() {
        switch (this.a) {
            case 0:
                C0029ab.a((EditText) this.b.get(), 1);
                break;
            default:
                SwitchCompat switchCompat = (SwitchCompat) this.b.get();
                if (switchCompat != null) {
                    switchCompat.g(switchCompat.o);
                    switchCompat.f(switchCompat.q);
                    switchCompat.requestLayout();
                }
                break;
        }
    }

    public Za(SwitchCompat switchCompat) {
        this.b = new WeakReference(switchCompat);
    }
}
