package rikka.shizuku;

import android.text.Editable;

/* JADX INFO: renamed from: rikka.shizuku.lb, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0367lb extends Kp {
    public final /* synthetic */ C0460ob a;

    public C0367lb(C0460ob c0460ob) {
        this.a = c0460ob;
    }

    @Override // android.text.TextWatcher
    public final void afterTextChanged(Editable editable) {
        this.a.b().a();
    }

    @Override // rikka.shizuku.Kp, android.text.TextWatcher
    public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        this.a.b().b();
    }
}
