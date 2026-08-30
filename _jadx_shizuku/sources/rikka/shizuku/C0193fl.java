package rikka.shizuku;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.ViewGroup;

/* JADX INFO: renamed from: rikka.shizuku.fl, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0193fl extends ViewGroup.MarginLayoutParams {
    public AbstractC0470ol a;
    public final Rect b;
    public boolean c;
    public boolean d;

    public C0193fl(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.b = new Rect();
        this.c = true;
        this.d = false;
    }

    public C0193fl(int i, int i2) {
        super(i, i2);
        this.b = new Rect();
        this.c = true;
        this.d = false;
    }

    public C0193fl(ViewGroup.MarginLayoutParams marginLayoutParams) {
        super(marginLayoutParams);
        this.b = new Rect();
        this.c = true;
        this.d = false;
    }

    public C0193fl(ViewGroup.LayoutParams layoutParams) {
        super(layoutParams);
        this.b = new Rect();
        this.c = true;
        this.d = false;
    }

    public C0193fl(C0193fl c0193fl) {
        super((ViewGroup.LayoutParams) c0193fl);
        this.b = new Rect();
        this.c = true;
        this.d = false;
    }
}
