package rikka.shizuku;

import android.os.Bundle;
import android.text.style.ClickableSpan;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public final class P extends ClickableSpan {
    public final int a;
    public final C0141e0 b;
    public final int c;

    public P(int i, C0141e0 c0141e0, int i2) {
        this.a = i;
        this.b = c0141e0;
        this.c = i2;
    }

    @Override // android.text.style.ClickableSpan
    public final void onClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt("ACCESSIBILITY_CLICKABLE_SPAN_ID", this.a);
        this.b.a.performAction(this.c, bundle);
    }
}
