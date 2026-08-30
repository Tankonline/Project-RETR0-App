package rikka.shizuku;

import java.util.Calendar;
import java.util.GregorianCalendar;
import rikka.html.text.style.BulletSpan;

/* JADX INFO: renamed from: rikka.shizuku.ej, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0160ej implements Mq {
    public final /* synthetic */ int a;
    public final /* synthetic */ Object b;

    public /* synthetic */ C0160ej(int i, Object obj) {
        this.a = i;
        this.b = obj;
    }

    @Override // rikka.shizuku.Mq
    public final Lq a(Rd rd, C0630tr c0630tr) {
        switch (this.a) {
            case 0:
                if (c0630tr.a == Number.class) {
                    return (Pd) this.b;
                }
                return null;
            case 1:
                if (c0630tr.a == Object.class) {
                    return new C0283ij(rd, (Zp) this.b);
                }
                return null;
            default:
                Class cls = c0630tr.a;
                if (cls == Calendar.class || cls == GregorianCalendar.class) {
                    return (C0199fr) this.b;
                }
                return null;
        }
    }

    public String toString() {
        switch (this.a) {
            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                return "Factory[type=" + Calendar.class.getName() + "+" + GregorianCalendar.class.getName() + ",adapter=" + ((C0199fr) this.b) + "]";
            default:
                return super.toString();
        }
    }
}
