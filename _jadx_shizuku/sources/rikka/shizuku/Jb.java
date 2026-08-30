package rikka.shizuku;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.widget.TextView;

/* JADX INFO: loaded from: classes.dex */
public final class Jb {
    public static final Jb a = new Jb();
    public static final Typeface b = Typeface.create("sans-serif", 0);
    public static final Typeface c = Typeface.create("sans-serif-medium", 0);

    public static Typeface a(Typeface typeface) {
        return !c.equals(typeface) ? typeface : b;
    }

    public static void b(TextView textView, Typeface typeface) {
        boolean zEquals = c.equals(typeface);
        TextPaint paint = textView.getPaint();
        if (zEquals) {
            paint.setStrokeWidth(0.65f);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
        } else {
            paint.setStrokeWidth(0.0f);
            paint.setStyle(Paint.Style.FILL);
        }
    }
}
