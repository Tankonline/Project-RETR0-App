package androidx.appcompat.view.menu;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import rikka.shizuku.C0221gi;
import rikka.shizuku.InterfaceC0098ci;
import rikka.shizuku.InterfaceC0745xi;
import rikka.shizuku.K3;
import rikka.shizuku.MenuC0129di;

/* JADX INFO: loaded from: classes.dex */
public final class ExpandedMenuView extends ListView implements InterfaceC0098ci, InterfaceC0745xi, AdapterView.OnItemClickListener {
    public static final int[] b = {R.attr.background, R.attr.divider};
    public MenuC0129di a;

    public ExpandedMenuView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setOnItemClickListener(this);
        K3 k3N = K3.n(context, attributeSet, b, R.attr.listViewStyle);
        TypedArray typedArray = (TypedArray) k3N.a;
        if (typedArray.hasValue(0)) {
            setBackgroundDrawable(k3N.i(0));
        }
        if (typedArray.hasValue(1)) {
            setDivider(k3N.i(1));
        }
        k3N.o();
    }

    @Override // rikka.shizuku.InterfaceC0745xi
    public final void a(MenuC0129di menuC0129di) {
        this.a = menuC0129di;
    }

    @Override // rikka.shizuku.InterfaceC0098ci
    public final boolean c(C0221gi c0221gi) {
        return this.a.q(c0221gi, null, 0);
    }

    @Override // android.widget.ListView, android.widget.AbsListView, android.widget.AdapterView, android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setChildrenDrawingCacheEnabled(false);
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
        c((C0221gi) getAdapter().getItem(i));
    }
}
