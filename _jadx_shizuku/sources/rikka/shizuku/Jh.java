package rikka.shizuku;

import android.content.Context;
import android.view.View;
import android.view.Window;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.Calendar;

/* JADX INFO: loaded from: classes.dex */
public final class Jh implements View.OnClickListener {
    public final /* synthetic */ int a;
    public final Object b;
    public final /* synthetic */ Object c;

    public /* synthetic */ Jh(Oh oh, com.google.android.material.datepicker.c cVar, int i) {
        this.a = i;
        this.c = oh;
        this.b = cVar;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        switch (this.a) {
            case 0:
                Oh oh = (Oh) this.c;
                int iN0 = ((LinearLayoutManager) oh.d0.n).N0() - 1;
                if (iN0 >= 0) {
                    Calendar calendarA = Zr.a(((com.google.android.material.datepicker.c) this.b).d.a.a);
                    calendarA.add(2, iN0);
                    oh.M(new Ei(calendarA));
                }
                break;
            case 1:
                Oh oh2 = (Oh) this.c;
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) oh2.d0.n;
                View viewP0 = linearLayoutManager.P0(0, linearLayoutManager.v(), false, true);
                int iG = (viewP0 == null ? -1 : AbstractC0162el.G(viewP0)) + 1;
                if (iG < oh2.d0.m.a()) {
                    Calendar calendarA2 = Zr.a(((com.google.android.material.datepicker.c) this.b).d.a.a);
                    calendarA2.add(2, iG);
                    oh2.M(new Ei(calendarA2));
                }
                break;
            default:
                C0351kq c0351kq = (C0351kq) this.c;
                Window.Callback callback = c0351kq.k;
                if (callback != null && c0351kq.l) {
                    callback.onMenuItemSelected(0, (C0696w0) this.b);
                    break;
                }
                break;
        }
    }

    public Jh(C0351kq c0351kq) {
        this.a = 2;
        this.c = c0351kq;
        Context context = c0351kq.a.getContext();
        CharSequence charSequence = c0351kq.h;
        C0696w0 c0696w0 = new C0696w0();
        c0696w0.e = 4096;
        c0696w0.g = 4096;
        c0696w0.l = null;
        c0696w0.m = null;
        c0696w0.n = false;
        c0696w0.o = false;
        c0696w0.p = 16;
        c0696w0.i = context;
        c0696w0.a = charSequence;
        this.b = c0696w0;
    }
}
