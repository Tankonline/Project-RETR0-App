package rikka.html.text.style;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.text.Layout;
import android.text.Spanned;

/* JADX INFO: loaded from: classes.dex */
public class BulletSpan extends android.text.style.BulletSpan {
    public static final int STANDARD_BULLET_RADIUS = 5;
    public static final int STANDARD_GAP_WIDTH = 2;
    private Path mBulletPath;
    private final int mBulletRadius;
    private final int mColor;
    private final int mGapWidth;
    private final boolean mWantColor;

    public BulletSpan() {
        this.mBulletPath = null;
        this.mGapWidth = 2;
        this.mBulletRadius = 5;
        this.mWantColor = false;
        this.mColor = 0;
    }

    @Override // android.text.style.BulletSpan, android.text.style.LeadingMarginSpan
    public void drawLeadingMargin(Canvas canvas, Paint paint, int i, int i2, int i3, int i4, int i5, CharSequence charSequence, int i6, int i7, boolean z, Layout layout) {
        int color;
        if (((Spanned) charSequence).getSpanStart(this) == i6) {
            Paint.Style style = paint.getStyle();
            if (this.mWantColor) {
                color = paint.getColor();
                paint.setColor(this.mColor);
            } else {
                color = 0;
            }
            paint.setStyle(Paint.Style.FILL);
            if (canvas.isHardwareAccelerated()) {
                if (this.mBulletPath == null) {
                    Path path = new Path();
                    this.mBulletPath = path;
                    path.addCircle(0.0f, 0.0f, this.mBulletRadius * 1.2f, Path.Direction.CW);
                }
                canvas.save();
                canvas.translate((i2 * this.mBulletRadius) + i + this.mGapWidth, (i3 + i5) / 2.0f);
                canvas.drawPath(this.mBulletPath, paint);
                canvas.restore();
            } else {
                int i8 = this.mBulletRadius;
                canvas.drawCircle((i2 * i8) + i + this.mGapWidth, (i3 + i5) / 2.0f, i8, paint);
            }
            if (this.mWantColor) {
                paint.setColor(color);
            }
            paint.setStyle(style);
        }
    }

    @Override // android.text.style.BulletSpan, android.text.style.LeadingMarginSpan
    public int getLeadingMargin(boolean z) {
        return (this.mGapWidth * 2) + (this.mBulletRadius * 2);
    }

    public BulletSpan(int i) {
        super(i);
        this.mBulletPath = null;
        this.mGapWidth = i;
        this.mBulletRadius = 5;
        this.mWantColor = false;
        this.mColor = 0;
    }

    public BulletSpan(int i, int i2) {
        super(i);
        this.mBulletPath = null;
        this.mGapWidth = i;
        this.mBulletRadius = i2;
        this.mWantColor = false;
        this.mColor = 0;
    }

    public BulletSpan(int i, int i2, int i3) {
        super(i, i3);
        this.mBulletPath = null;
        this.mGapWidth = i;
        this.mBulletRadius = i2;
        this.mWantColor = true;
        this.mColor = i3;
    }
}
