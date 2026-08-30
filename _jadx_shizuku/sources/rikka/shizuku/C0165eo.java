package rikka.shizuku;

import android.graphics.Rect;
import android.os.Build;
import android.util.Property;
import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: renamed from: rikka.shizuku.eo, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0165eo extends Property {
    public static final C0165eo a = new C0165eo(Rect.class, "bounds");

    @Override // android.util.Property
    public final Object get(Object obj) {
        return ((C0778yk) obj).a.getBounds();
    }

    @Override // android.util.Property
    public final void set(Object obj, Object obj2) {
        C0778yk c0778yk = (C0778yk) obj;
        Rect rect = (Rect) obj2;
        C0302j8 c0302j8 = c0778yk.a;
        c0302j8.getClass();
        c0302j8.a.setBounds(rect.left, rect.top, rect.right, rect.bottom);
        RecyclerView recyclerView = c0778yk.b;
        recyclerView.invalidateOutline();
        if (Build.VERSION.SDK_INT <= 25) {
            recyclerView.invalidate();
        }
    }
}
