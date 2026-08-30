package rikka.shizuku;

import android.animation.TypeEvaluator;
import android.graphics.Rect;

/* JADX INFO: loaded from: classes.dex */
public final class Uk implements TypeEvaluator {
    public final Rect a;
    public final Rect b = new Rect();

    public Uk(Rect rect) {
        this.a = rect;
    }

    @Override // android.animation.TypeEvaluator
    public final Object evaluate(float f, Object obj, Object obj2) {
        Rect rect = (Rect) obj;
        Rect rect2 = (Rect) obj2;
        int i = rect.left;
        int i2 = i + ((int) ((rect2.left - i) * f));
        Rect rect3 = this.b;
        rect3.left = i2;
        int i3 = rect.top;
        rect3.top = i3 + ((int) ((rect2.top - i3) * f));
        int i4 = rect.right;
        rect3.right = i4 + ((int) ((rect2.right - i4) * f));
        int i5 = rect.bottom;
        rect3.bottom = i5 + ((int) ((rect2.bottom - i5) * f));
        rect3.setIntersect(this.a, rect3);
        return rect3;
    }
}
