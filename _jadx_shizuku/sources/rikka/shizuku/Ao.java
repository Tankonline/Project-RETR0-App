package rikka.shizuku;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/* JADX INFO: loaded from: classes.dex */
public final class Ao extends Lq {
    public static final C0813zo b = new C0813zo();
    public final SimpleDateFormat a;

    public /* synthetic */ Ao(int i) {
        this();
    }

    @Override // rikka.shizuku.Lq
    public final Object b(Vf vf) throws IOException {
        Date date;
        if (vf.v() == 9) {
            vf.r();
            return null;
        }
        String strT = vf.t();
        synchronized (this) {
            TimeZone timeZone = this.a.getTimeZone();
            try {
                try {
                    date = new Date(this.a.parse(strT).getTime());
                    this.a.setTimeZone(timeZone);
                } catch (ParseException e) {
                    throw new Rf("Failed parsing '" + strT + "' as SQL Date; at path " + vf.h(true), e);
                }
            } catch (Throwable th) {
                this.a.setTimeZone(timeZone);
                throw th;
            }
        }
        return date;
    }

    @Override // rikka.shizuku.Lq
    public final void c(Wf wf, Object obj) throws IOException {
        String str;
        Date date = (Date) obj;
        if (date == null) {
            wf.i();
            return;
        }
        synchronized (this) {
            str = this.a.format((java.util.Date) date);
        }
        wf.p(str);
    }

    private Ao() {
        this.a = new SimpleDateFormat("MMM d, yyyy");
    }
}
