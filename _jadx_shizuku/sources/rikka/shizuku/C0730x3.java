package rikka.shizuku;

import android.text.StaticLayout;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.widget.TextView;

/* JADX INFO: renamed from: rikka.shizuku.x3, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0730x3 extends AbstractC0792z3 {
    @Override // rikka.shizuku.AbstractC0792z3
    public void a(StaticLayout.Builder builder, TextView textView) {
        builder.setTextDirection((TextDirectionHeuristic) A3.e(textView, "getTextDirectionHeuristic", TextDirectionHeuristics.FIRSTSTRONG_LTR));
    }
}
