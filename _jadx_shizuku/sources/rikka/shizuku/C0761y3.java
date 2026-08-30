package rikka.shizuku;

import android.text.StaticLayout;
import android.widget.TextView;

/* JADX INFO: renamed from: rikka.shizuku.y3, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0761y3 extends C0730x3 {
    @Override // rikka.shizuku.C0730x3, rikka.shizuku.AbstractC0792z3
    public final void a(StaticLayout.Builder builder, TextView textView) {
        builder.setTextDirection(textView.getTextDirectionHeuristic());
    }

    @Override // rikka.shizuku.AbstractC0792z3
    public final boolean b(TextView textView) {
        return textView.isHorizontallyScrollable();
    }
}
