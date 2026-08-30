package rikka.shizuku;

import android.content.Context;
import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.carousel.CarouselLayoutManager;

/* JADX INFO: loaded from: classes.dex */
public final class B5 extends Jg {
    public final /* synthetic */ int q;
    public final /* synthetic */ Object r;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ B5(int i, Context context, Object obj) {
        super(context);
        this.q = i;
        this.r = obj;
    }

    @Override // rikka.shizuku.Jg
    public int b(View view, int i) {
        switch (this.q) {
            case 0:
                ((CarouselLayoutManager) this.r).getClass();
                return 0;
            default:
                return super.b(view, i);
        }
    }

    @Override // rikka.shizuku.Jg
    public int c(View view, int i) {
        switch (this.q) {
            case 0:
                ((CarouselLayoutManager) this.r).getClass();
                return 0;
            default:
                return super.c(view, i);
        }
    }

    @Override // rikka.shizuku.Jg
    public float d(DisplayMetrics displayMetrics) {
        switch (this.q) {
            case 1:
                return 100.0f / displayMetrics.densityDpi;
            default:
                return super.d(displayMetrics);
        }
    }

    @Override // rikka.shizuku.Jg
    public int e(int i) {
        switch (this.q) {
            case 1:
                return Math.min(100, super.e(i));
            default:
                return super.e(i);
        }
    }

    @Override // rikka.shizuku.Jg
    public PointF f(int i) {
        switch (this.q) {
            case 0:
                ((CarouselLayoutManager) this.r).getClass();
                return null;
            default:
                return super.f(i);
        }
    }

    @Override // rikka.shizuku.Jg
    public void h(View view, C0346kl c0346kl) {
        switch (this.q) {
            case 1:
                Xn xn = (Xn) this.r;
                int[] iArrB = xn.b(((RecyclerView) xn.a).n, view);
                int i = iArrB[0];
                int i2 = iArrB[1];
                int iCeil = (int) Math.ceil(((double) e(Math.max(Math.abs(i), Math.abs(i2)))) / 0.3356d);
                if (iCeil > 0) {
                    DecelerateInterpolator decelerateInterpolator = this.j;
                    c0346kl.a = i;
                    c0346kl.b = i2;
                    c0346kl.c = iCeil;
                    c0346kl.e = decelerateInterpolator;
                    c0346kl.f = true;
                }
                break;
            default:
                super.h(view, c0346kl);
                break;
        }
    }
}
