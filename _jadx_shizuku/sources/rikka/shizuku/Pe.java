package rikka.shizuku;

import android.graphics.Path;
import android.widget.EdgeEffect;
import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes.dex */
public class Pe {
    public static final Pe b = new Pe(1);
    public final /* synthetic */ int a;

    public /* synthetic */ Pe(int i) {
        this.a = i;
    }

    public static Path b(float f, float f2, float f3, float f4) {
        Path path = new Path();
        path.moveTo(f, f2);
        path.lineTo(f3, f4);
        return path;
    }

    public EdgeEffect a(RecyclerView recyclerView, int i) {
        return new EdgeEffect(recyclerView.getContext());
    }

    public String toString() {
        switch (this.a) {
            case 1:
                return "kotlin.Unit";
            default:
                return super.toString();
        }
    }
}
