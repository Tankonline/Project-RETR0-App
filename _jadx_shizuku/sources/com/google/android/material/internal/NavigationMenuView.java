package com.google.android.material.internal;

import android.content.Context;
import android.util.AttributeSet;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import rikka.shizuku.InterfaceC0745xi;
import rikka.shizuku.MenuC0129di;

/* JADX INFO: loaded from: classes.dex */
public class NavigationMenuView extends RecyclerView implements InterfaceC0745xi {
    public NavigationMenuView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
        i0(new LinearLayoutManager(1));
    }

    @Override // rikka.shizuku.InterfaceC0745xi
    public final void a(MenuC0129di menuC0129di) {
    }
}
