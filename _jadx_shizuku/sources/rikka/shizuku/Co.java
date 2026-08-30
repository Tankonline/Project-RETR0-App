package rikka.shizuku;

import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/* JADX INFO: loaded from: classes.dex */
public final class Co extends Lq {
    public static final Bo b = new Bo();
    public final SimpleDateFormat a;

    public /* synthetic */ Co(int i) {
        this();
    }

    @Override // rikka.shizuku.Lq
    public final Object b(Vf vf) throws IOException {
        Time time;
        if (vf.v() == 9) {
            vf.r();
            return null;
        }
        String strT = vf.t();
        synchronized (this) {
            TimeZone timeZone = this.a.getTimeZone();
            try {
                try {
                    time = new Time(this.a.parse(strT).getTime());
                    this.a.setTimeZone(timeZone);
                } catch (ParseException e) {
                    throw new Rf("Failed parsing '" + strT + "' as SQL Time; at path " + vf.h(true), e);
                }
            } catch (Throwable th) {
                this.a.setTimeZone(timeZone);
                throw th;
            }
        }
        return time;
    }

    @Override // rikka.shizuku.Lq
    public final void c(Wf wf, Object obj) throws IOException {
        String str;
        Time time = (Time) obj;
        if (time == null) {
            wf.i();
            return;
        }
        synchronized (this) {
            str = this.a.format((Date) time);
        }
        wf.p(str);
    }

    private Co() {
        this.a = new SimpleDateFormat("hh:mm:ss a");
    }
}
