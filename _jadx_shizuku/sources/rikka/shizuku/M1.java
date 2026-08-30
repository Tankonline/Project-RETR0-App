package rikka.shizuku;

import android.content.Context;
import android.graphics.Canvas;
import android.widget.EdgeEffect;
import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes.dex */
public final class M1 extends EdgeEffect {
    public boolean a;
    public final /* synthetic */ int b;
    public final /* synthetic */ RecyclerView c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public M1(int i, RecyclerView recyclerView, Context context) {
        super(context);
        this.b = i;
        this.c = recyclerView;
    }

    @Override // android.widget.EdgeEffect
    public final boolean draw(Canvas canvas) {
        boolean z = this.a;
        int i = this.b;
        RecyclerView recyclerView = this.c;
        if (!z) {
            this.a = true;
            if (i == 0) {
                setSize((recyclerView.getMeasuredHeight() - recyclerView.getPaddingTop()) - recyclerView.getPaddingBottom(), (recyclerView.getMeasuredWidth() - recyclerView.getPaddingLeft()) - recyclerView.getPaddingRight());
            } else if (i == 1) {
                setSize((recyclerView.getMeasuredWidth() - recyclerView.getPaddingLeft()) - recyclerView.getPaddingRight(), (recyclerView.getMeasuredHeight() - recyclerView.getPaddingTop()) - recyclerView.getPaddingBottom());
            } else if (i == 2) {
                setSize((recyclerView.getMeasuredHeight() - recyclerView.getPaddingTop()) - recyclerView.getPaddingBottom(), (recyclerView.getMeasuredWidth() - recyclerView.getPaddingLeft()) - recyclerView.getPaddingRight());
            } else if (i == 3) {
                setSize((recyclerView.getMeasuredWidth() - recyclerView.getPaddingLeft()) - recyclerView.getPaddingRight(), (recyclerView.getMeasuredHeight() - recyclerView.getPaddingTop()) - recyclerView.getPaddingBottom());
            }
        }
        int iSave = canvas.save();
        if (i == 0) {
            canvas.translate(recyclerView.getPaddingBottom(), 0.0f);
        } else if (i == 1) {
            canvas.translate(recyclerView.getPaddingLeft(), recyclerView.getPaddingTop());
        } else if (i == 2) {
            canvas.translate(-recyclerView.getPaddingTop(), 0.0f);
        } else if (i == 3) {
            canvas.translate(recyclerView.getPaddingRight(), recyclerView.getPaddingBottom());
        }
        boolean zDraw = super.draw(canvas);
        canvas.restoreToCount(iSave);
        return zDraw;
    }
}
