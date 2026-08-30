package rikka.shizuku;

import android.view.View;

/* JADX INFO: renamed from: rikka.shizuku.j6, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class ViewOnFocusChangeListenerC0300j6 implements View.OnFocusChangeListener {
    public final /* synthetic */ int a;
    public final /* synthetic */ AbstractC0491pb b;

    public /* synthetic */ ViewOnFocusChangeListenerC0300j6(AbstractC0491pb abstractC0491pb, int i) {
        this.a = i;
        this.b = abstractC0491pb;
    }

    @Override // android.view.View.OnFocusChangeListener
    public final void onFocusChange(View view, boolean z) {
        switch (this.a) {
            case 0:
                C0393m6 c0393m6 = (C0393m6) this.b;
                c0393m6.t(c0393m6.u());
                break;
            default:
                C0644ua c0644ua = (C0644ua) this.b;
                c0644ua.l = z;
                c0644ua.q();
                if (!z) {
                    c0644ua.t(false);
                    c0644ua.m = false;
                }
                break;
        }
    }
}
