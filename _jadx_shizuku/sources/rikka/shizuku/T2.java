package rikka.shizuku;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RatingBar;
import moe.shizuku.privileged.api.R;

/* JADX INFO: loaded from: classes.dex */
public final class T2 extends RatingBar {
    public final R2 a;

    public T2(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, R.attr._0_resource_name_obfuscated_res_0x7f040402);
        Lp.a(this, getContext());
        R2 r2 = new R2(0, this);
        this.a = r2;
        r2.H(attributeSet, R.attr._0_resource_name_obfuscated_res_0x7f040402);
    }

    @Override // android.widget.RatingBar, android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    public final synchronized void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        Bitmap bitmap = (Bitmap) this.a.c;
        if (bitmap != null) {
            setMeasuredDimension(View.resolveSizeAndState(bitmap.getWidth() * getNumStars(), i, 0), getMeasuredHeight());
        }
    }
}
