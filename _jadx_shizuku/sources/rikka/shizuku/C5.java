package rikka.shizuku;

import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.carousel.CarouselLayoutManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import moe.shizuku.privileged.api.R;

/* JADX INFO: loaded from: classes.dex */
public final class C5 extends AbstractC0070bl {
    public final Paint a;
    public final List b;

    public C5() {
        Paint paint = new Paint();
        this.a = paint;
        this.b = Collections.unmodifiableList(new ArrayList());
        paint.setStrokeWidth(5.0f);
        paint.setColor(-65281);
    }

    @Override // rikka.shizuku.AbstractC0070bl
    public final void c(Canvas canvas, RecyclerView recyclerView) {
        Canvas canvas2;
        int iD;
        int iE;
        int iF;
        int iC;
        Paint paint = this.a;
        paint.setStrokeWidth(recyclerView.getResources().getDimension(R.dimen._0_resource_name_obfuscated_res_0x7f070102));
        Iterator it = this.b.iterator();
        while (it.hasNext()) {
            ((AbstractC0096cg) it.next()).getClass();
            paint.setColor(E6.b(-65281, -16776961, 0.0f));
            if (((CarouselLayoutManager) recyclerView.n).D0()) {
                D5 d5 = ((CarouselLayoutManager) recyclerView.n).q;
                switch (d5.b) {
                    case 0:
                        iF = 0;
                        break;
                    default:
                        iF = d5.c.F();
                        break;
                }
                float f = iF;
                D5 d6 = ((CarouselLayoutManager) recyclerView.n).q;
                switch (d6.b) {
                    case 0:
                        iC = d6.c.o;
                        break;
                    default:
                        CarouselLayoutManager carouselLayoutManager = d6.c;
                        iC = carouselLayoutManager.o - carouselLayoutManager.C();
                        break;
                }
                canvas2 = canvas;
                canvas2.drawLine(0.0f, f, 0.0f, iC, paint);
            } else {
                canvas2 = canvas;
                D5 d7 = ((CarouselLayoutManager) recyclerView.n).q;
                switch (d7.b) {
                    case 0:
                        iD = d7.c.D();
                        break;
                    default:
                        iD = 0;
                        break;
                }
                float f2 = iD;
                D5 d8 = ((CarouselLayoutManager) recyclerView.n).q;
                switch (d8.b) {
                    case 0:
                        CarouselLayoutManager carouselLayoutManager2 = d8.c;
                        iE = carouselLayoutManager2.n - carouselLayoutManager2.E();
                        break;
                    default:
                        iE = d8.c.n;
                        break;
                }
                canvas2.drawLine(f2, 0.0f, iE, 0.0f, paint);
            }
            canvas = canvas2;
        }
    }
}
