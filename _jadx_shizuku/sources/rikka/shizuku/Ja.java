package rikka.shizuku;

import android.graphics.Rect;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public abstract class Ja {
    public int a;
    public final Object b;
    public final Object c;

    public Ja(AbstractC0162el abstractC0162el) {
        this.a = Integer.MIN_VALUE;
        this.c = new Rect();
        this.b = abstractC0162el;
    }

    public static Ja a(AbstractC0162el abstractC0162el, int i) {
        if (i == 0) {
            return new Aj(abstractC0162el, 0);
        }
        if (i == 1) {
            return new Aj(abstractC0162el, 1);
        }
        throw new IllegalArgumentException("invalid orientation");
    }

    public abstract int b(View view);

    public abstract int c(View view);

    public abstract int d(View view);

    public abstract int e(View view);

    public abstract int f();

    public abstract int g();

    public abstract int h();

    public abstract int i();

    public abstract int j();

    public abstract int k();

    public abstract int l();

    public abstract int m(View view);

    public abstract int n(View view);

    public abstract void o(int i);

    public Ja(La la) {
        this.a = 0;
        this.c = new C0212g9();
        this.b = la;
    }
}
