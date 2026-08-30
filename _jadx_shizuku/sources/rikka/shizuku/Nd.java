package rikka.shizuku;

import java.io.IOException;
import rikka.html.text.style.BulletSpan;

/* JADX INFO: loaded from: classes.dex */
public final class Nd extends Lq {
    public final /* synthetic */ int a;

    @Override // rikka.shizuku.Lq
    public final Object b(Vf vf) throws IOException {
        switch (this.a) {
            case 0:
                if (vf.v() != 9) {
                    return Double.valueOf(vf.m());
                }
                vf.r();
                return null;
            case 1:
                if (vf.v() != 9) {
                    return Float.valueOf((float) vf.m());
                }
                vf.r();
                return null;
            default:
                vf.B();
                return null;
        }
    }

    @Override // rikka.shizuku.Lq
    public final void c(Wf wf, Object obj) throws IOException {
        switch (this.a) {
            case 0:
                Number number = (Number) obj;
                if (number != null) {
                    double dDoubleValue = number.doubleValue();
                    Rd.a(dDoubleValue);
                    wf.m(dDoubleValue);
                } else {
                    wf.i();
                }
                break;
            case 1:
                Number numberValueOf = (Number) obj;
                if (numberValueOf != null) {
                    float fFloatValue = numberValueOf.floatValue();
                    Rd.a(fFloatValue);
                    if (!(numberValueOf instanceof Float)) {
                        numberValueOf = Float.valueOf(fFloatValue);
                    }
                    wf.o(numberValueOf);
                } else {
                    wf.i();
                }
                break;
            default:
                wf.i();
                break;
        }
    }

    public String toString() {
        switch (this.a) {
            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                return "AnonymousOrNonStaticLocalClassAdapter";
            default:
                return super.toString();
        }
    }
}
