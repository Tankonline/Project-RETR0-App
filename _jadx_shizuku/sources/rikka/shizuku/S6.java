package rikka.shizuku;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public abstract class S6 extends Activity implements InterfaceC0743xg, InterfaceC0065bg {
    public final C0805zg a = new C0805zg(this);

    @Override // rikka.shizuku.InterfaceC0065bg
    public final boolean c(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent);
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        View decorView = getWindow().getDecorView();
        if (AbstractC0599sr.j(decorView, keyEvent)) {
            return true;
        }
        return AbstractC0599sr.k(this, decorView, this, keyEvent);
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public final boolean dispatchKeyShortcutEvent(KeyEvent keyEvent) {
        if (AbstractC0599sr.j(getWindow().getDecorView(), keyEvent)) {
            return true;
        }
        return super.dispatchKeyShortcutEvent(keyEvent);
    }

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        int i = Jl.b;
        AbstractC0744xh.B(this);
    }

    @Override // android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        EnumC0588sg enumC0588sg = EnumC0588sg.c;
        C0805zg c0805zg = this.a;
        c0805zg.c("setCurrentState");
        c0805zg.e(enumC0588sg);
        super.onSaveInstanceState(bundle);
    }
}
