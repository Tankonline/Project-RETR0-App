package rikka.shizuku;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

/* JADX INFO: renamed from: rikka.shizuku.fr, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0199fr extends Lq {
    @Override // rikka.shizuku.Lq
    public final Object b(Vf vf) throws IOException {
        int i = 0;
        if (vf.v() == 9) {
            vf.r();
            return null;
        }
        vf.b();
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        while (vf.v() != 4) {
            String strP = vf.p();
            int iN = vf.n();
            strP.getClass();
            switch (strP) {
                case "dayOfMonth":
                    i3 = iN;
                    break;
                case "minute":
                    i5 = iN;
                    break;
                case "second":
                    i6 = iN;
                    break;
                case "year":
                    i = iN;
                    break;
                case "month":
                    i2 = iN;
                    break;
                case "hourOfDay":
                    i4 = iN;
                    break;
            }
        }
        vf.f();
        return new GregorianCalendar(i, i2, i3, i4, i5, i6);
    }

    @Override // rikka.shizuku.Lq
    public final void c(Wf wf, Object obj) throws IOException {
        Calendar calendar = (Calendar) obj;
        if (calendar == null) {
            wf.i();
            return;
        }
        wf.c();
        wf.g("year");
        wf.n(calendar.get(1));
        wf.g("month");
        wf.n(calendar.get(2));
        wf.g("dayOfMonth");
        wf.n(calendar.get(5));
        wf.g("hourOfDay");
        wf.n(calendar.get(11));
        wf.g("minute");
        wf.n(calendar.get(12));
        wf.g("second");
        wf.n(calendar.get(13));
        wf.f();
    }
}
