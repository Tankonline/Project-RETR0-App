package rikka.shizuku;

import android.text.TextPaint;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
public final class Fp {
    public float c;
    public final WeakReference e;
    public Cp f;
    public final TextPaint a = new TextPaint(1);
    public final Y5 b = new Y5(1, this);
    public boolean d = true;

    public Fp(C0086c6 c0086c6) {
        this.e = new WeakReference(null);
        this.e = new WeakReference(c0086c6);
    }

    public final float a(String str) {
        if (!this.d) {
            return this.c;
        }
        TextPaint textPaint = this.a;
        this.c = str == null ? 0.0f : textPaint.measureText((CharSequence) str, 0, str.length());
        if (str != null) {
            Math.abs(textPaint.getFontMetrics().ascent);
        }
        this.d = false;
        return this.c;
    }
}
