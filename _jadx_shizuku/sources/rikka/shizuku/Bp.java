package rikka.shizuku;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextPaint;

/* JADX INFO: loaded from: classes.dex */
public final class Bp extends AbstractC0750xn {
    public final /* synthetic */ Context d;
    public final /* synthetic */ TextPaint e;
    public final /* synthetic */ AbstractC0750xn f;
    public final /* synthetic */ Cp g;

    public Bp(Cp cp, Context context, TextPaint textPaint, AbstractC0750xn abstractC0750xn) {
        this.g = cp;
        this.d = context;
        this.e = textPaint;
        this.f = abstractC0750xn;
    }

    @Override // rikka.shizuku.AbstractC0750xn
    public final void o(int i) {
        this.f.o(i);
    }

    @Override // rikka.shizuku.AbstractC0750xn
    public final void p(Typeface typeface, boolean z) {
        this.g.g(this.d, this.e, typeface);
        this.f.p(typeface, z);
    }
}
