package rikka.shizuku;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.style.ReplacementSpan;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
public final class Ar extends ReplacementSpan {
    public final Va b;
    public final Paint.FontMetricsInt a = new Paint.FontMetricsInt();
    public float c = 1.0f;

    public Ar(Va va) {
        AbstractC0046as.j("metadata cannot be null", va);
        this.b = va;
    }

    @Override // android.text.style.ReplacementSpan
    public final void draw(Canvas canvas, CharSequence charSequence, int i, int i2, float f, int i3, int i4, int i5, Paint paint) {
        Ma.a().getClass();
        Va va = this.b;
        Xn xn = va.b;
        Typeface typeface = (Typeface) xn.d;
        Typeface typeface2 = paint.getTypeface();
        paint.setTypeface(typeface);
        canvas.drawText((char[]) xn.b, va.a * 2, 2, f, i4, paint);
        paint.setTypeface(typeface2);
    }

    @Override // android.text.style.ReplacementSpan
    public final int getSize(Paint paint, CharSequence charSequence, int i, int i2, Paint.FontMetricsInt fontMetricsInt) {
        Paint.FontMetricsInt fontMetricsInt2 = this.a;
        paint.getFontMetricsInt(fontMetricsInt2);
        float fAbs = Math.abs(fontMetricsInt2.descent - fontMetricsInt2.ascent) * 1.0f;
        Va va = this.b;
        Ai aiB = va.b();
        int iA = aiB.a(14);
        this.c = fAbs / (iA != 0 ? ((ByteBuffer) aiB.d).getShort(iA + aiB.a) : (short) 0);
        Ai aiB2 = va.b();
        int iA2 = aiB2.a(14);
        if (iA2 != 0) {
            ((ByteBuffer) aiB2.d).getShort(iA2 + aiB2.a);
        }
        Ai aiB3 = va.b();
        int iA3 = aiB3.a(12);
        short s = (short) ((iA3 != 0 ? ((ByteBuffer) aiB3.d).getShort(iA3 + aiB3.a) : (short) 0) * this.c);
        if (fontMetricsInt != null) {
            fontMetricsInt.ascent = fontMetricsInt2.ascent;
            fontMetricsInt.descent = fontMetricsInt2.descent;
            fontMetricsInt.top = fontMetricsInt2.top;
            fontMetricsInt.bottom = fontMetricsInt2.bottom;
        }
        return s;
    }
}
