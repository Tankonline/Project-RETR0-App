package rikka.shizuku;

import android.text.StaticLayout;
import android.widget.TextView;

/* JADX INFO: renamed from: rikka.shizuku.z3, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0792z3 {
    public abstract void a(StaticLayout.Builder builder, TextView textView);

    public boolean b(TextView textView) {
        return ((Boolean) A3.e(textView, "getHorizontallyScrolling", Boolean.FALSE)).booleanValue();
    }
}
