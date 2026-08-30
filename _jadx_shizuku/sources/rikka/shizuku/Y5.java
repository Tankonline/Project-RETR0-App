package rikka.shizuku;

import android.graphics.Typeface;
import com.google.android.material.chip.Chip;

/* JADX INFO: loaded from: classes.dex */
public final class Y5 extends AbstractC0750xn {
    public final /* synthetic */ int d;
    public final /* synthetic */ Object e;

    public /* synthetic */ Y5(int i, Object obj) {
        this.d = i;
        this.e = obj;
    }

    @Override // rikka.shizuku.AbstractC0750xn
    public final void o(int i) {
        switch (this.d) {
            case 0:
                break;
            default:
                Fp fp = (Fp) this.e;
                fp.d = true;
                Ep ep = (Ep) fp.e.get();
                if (ep != null) {
                    C0086c6 c0086c6 = (C0086c6) ep;
                    c0086c6.x();
                    c0086c6.invalidateSelf();
                }
                break;
        }
    }

    @Override // rikka.shizuku.AbstractC0750xn
    public final void p(Typeface typeface, boolean z) {
        switch (this.d) {
            case 0:
                Chip chip = (Chip) this.e;
                C0086c6 c0086c6 = chip.e;
                chip.setText(c0086c6.A0 ? c0086c6.F : chip.getText());
                chip.requestLayout();
                chip.invalidate();
                break;
            default:
                if (!z) {
                    Fp fp = (Fp) this.e;
                    fp.d = true;
                    Ep ep = (Ep) fp.e.get();
                    if (ep != null) {
                        C0086c6 c0086c7 = (C0086c6) ep;
                        c0086c7.x();
                        c0086c7.invalidateSelf();
                    }
                    break;
                }
                break;
        }
    }

    private final void r(int i) {
    }
}
