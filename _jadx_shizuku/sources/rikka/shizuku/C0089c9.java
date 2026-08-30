package rikka.shizuku;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/* JADX INFO: renamed from: rikka.shizuku.c9, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0089c9 extends Lq {
    public static final Z8 c = new Z8();
    public final AbstractC0058b9 a;
    public final ArrayList b;

    public C0089c9(AbstractC0058b9 abstractC0058b9, int i, int i2) {
        String str;
        String str2;
        ArrayList arrayList = new ArrayList();
        this.b = arrayList;
        this.a = abstractC0058b9;
        Locale locale = Locale.US;
        arrayList.add(DateFormat.getDateTimeInstance(i, i2, locale));
        if (!Locale.getDefault().equals(locale)) {
            arrayList.add(DateFormat.getDateTimeInstance(i, i2));
        }
        if (Df.a >= 9) {
            StringBuilder sb = new StringBuilder();
            if (i == 0) {
                str = "EEEE, MMMM d, yyyy";
            } else if (i == 1) {
                str = "MMMM d, yyyy";
            } else if (i == 2) {
                str = "MMM d, yyyy";
            } else {
                if (i != 3) {
                    throw new IllegalArgumentException(AbstractC0535qo.b(i, "Unknown DateFormat style: "));
                }
                str = "M/d/yy";
            }
            sb.append(str);
            sb.append(" ");
            if (i2 == 0 || i2 == 1) {
                str2 = "h:mm:ss a z";
            } else if (i2 == 2) {
                str2 = "h:mm:ss a";
            } else {
                if (i2 != 3) {
                    throw new IllegalArgumentException(AbstractC0535qo.b(i2, "Unknown DateFormat style: "));
                }
                str2 = "h:mm a";
            }
            sb.append(str2);
            arrayList.add(new SimpleDateFormat(sb.toString(), locale));
        }
    }

    @Override // rikka.shizuku.Lq
    public final Object b(Vf vf) throws IOException {
        Date dateB;
        if (vf.v() == 9) {
            vf.r();
            return null;
        }
        String strT = vf.t();
        synchronized (this.b) {
            try {
                ArrayList arrayList = this.b;
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    DateFormat dateFormat = (DateFormat) obj;
                    TimeZone timeZone = dateFormat.getTimeZone();
                    try {
                        try {
                            dateB = dateFormat.parse(strT);
                            dateFormat.setTimeZone(timeZone);
                        } catch (Throwable th) {
                            dateFormat.setTimeZone(timeZone);
                            throw th;
                        }
                    } catch (ParseException unused) {
                        dateFormat.setTimeZone(timeZone);
                    }
                }
                try {
                    dateB = Ve.b(strT, new ParsePosition(0));
                } catch (ParseException e) {
                    StringBuilder sbE = AbstractC0535qo.e("Failed parsing '", strT, "' as Date; at path ");
                    sbE.append(vf.h(true));
                    throw new Rf(sbE.toString(), e);
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
        return this.a.a(dateB);
    }

    @Override // rikka.shizuku.Lq
    public final void c(Wf wf, Object obj) throws IOException {
        String str;
        Date date = (Date) obj;
        if (date == null) {
            wf.i();
            return;
        }
        DateFormat dateFormat = (DateFormat) this.b.get(0);
        synchronized (this.b) {
            str = dateFormat.format(date);
        }
        wf.p(str);
    }

    public final String toString() {
        DateFormat dateFormat = (DateFormat) this.b.get(0);
        if (dateFormat instanceof SimpleDateFormat) {
            return "DefaultDateTypeAdapter(" + ((SimpleDateFormat) dateFormat).toPattern() + ')';
        }
        return "DefaultDateTypeAdapter(" + dateFormat.getClass().getSimpleName() + ')';
    }
}
