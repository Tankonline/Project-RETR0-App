package rikka.shizuku;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: renamed from: rikka.shizuku.hk, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0254hk extends AbstractC0070bl {
    public Drawable a;
    public int b;
    public boolean c = true;
    public final /* synthetic */ AbstractC0284ik d;

    public C0254hk(AbstractC0284ik abstractC0284ik) {
        this.d = abstractC0284ik;
    }

    @Override // rikka.shizuku.AbstractC0070bl
    public final void a(Rect rect, View view, RecyclerView recyclerView) {
        if (d(view, recyclerView)) {
            rect.bottom = this.b;
        }
    }

    @Override // rikka.shizuku.AbstractC0070bl
    public final void c(Canvas canvas, RecyclerView recyclerView) {
        if (this.a == null) {
            return;
        }
        int childCount = recyclerView.getChildCount();
        int width = recyclerView.getWidth();
        for (int i = 0; i < childCount; i++) {
            View childAt = recyclerView.getChildAt(i);
            if (d(childAt, recyclerView)) {
                int height = childAt.getHeight() + ((int) childAt.getY());
                this.a.setBounds(0, height, width, this.b + height);
                this.a.draw(canvas);
            }
        }
    }

    public final boolean d(View view, RecyclerView recyclerView) {
        AbstractC0470ol abstractC0470olJ = recyclerView.J(view);
        if (!(abstractC0470olJ instanceof C0500pk) || !((C0500pk) abstractC0470olJ).y) {
            return false;
        }
        boolean z = this.c;
        int iIndexOfChild = recyclerView.indexOfChild(view);
        if (iIndexOfChild >= recyclerView.getChildCount() - 1) {
            return z;
        }
        AbstractC0470ol abstractC0470olJ2 = recyclerView.J(recyclerView.getChildAt(iIndexOfChild + 1));
        return (abstractC0470olJ2 instanceof C0500pk) && ((C0500pk) abstractC0470olJ2).x;
    }
}
