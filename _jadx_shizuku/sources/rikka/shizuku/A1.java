package rikka.shizuku;

import android.graphics.RectF;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public final class A1 implements S7 {
    public final S7 a;
    public final float b;

    public A1(float f, S7 s7) {
        while (s7 instanceof A1) {
            s7 = ((A1) s7).a;
            f += ((A1) s7).b;
        }
        this.a = s7;
        this.b = f;
    }

    @Override // rikka.shizuku.S7
    public final float a(RectF rectF) {
        return Math.max(0.0f, this.a.a(rectF) + this.b);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof A1)) {
            return false;
        }
        A1 a1 = (A1) obj;
        return this.a.equals(a1.a) && this.b == a1.b;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.a, Float.valueOf(this.b)});
    }
}
