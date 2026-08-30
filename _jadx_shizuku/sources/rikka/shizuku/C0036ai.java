package rikka.shizuku;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import androidx.appcompat.view.menu.ListMenuItemView;
import java.util.ArrayList;

/* JADX INFO: renamed from: rikka.shizuku.ai, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0036ai extends BaseAdapter {
    public final MenuC0129di a;
    public int b = -1;
    public boolean c;
    public final boolean d;
    public final LayoutInflater e;
    public final int f;

    public C0036ai(MenuC0129di menuC0129di, LayoutInflater layoutInflater, boolean z, int i) {
        this.d = z;
        this.e = layoutInflater;
        this.a = menuC0129di;
        this.f = i;
        a();
    }

    public final void a() {
        MenuC0129di menuC0129di = this.a;
        C0221gi c0221gi = menuC0129di.v;
        if (c0221gi != null) {
            menuC0129di.i();
            ArrayList arrayList = menuC0129di.j;
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                if (((C0221gi) arrayList.get(i)) == c0221gi) {
                    this.b = i;
                    return;
                }
            }
        }
        this.b = -1;
    }

    @Override // android.widget.Adapter
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public final C0221gi getItem(int i) {
        ArrayList arrayListL;
        MenuC0129di menuC0129di = this.a;
        if (this.d) {
            menuC0129di.i();
            arrayListL = menuC0129di.j;
        } else {
            arrayListL = menuC0129di.l();
        }
        int i2 = this.b;
        if (i2 >= 0 && i >= i2) {
            i++;
        }
        return (C0221gi) arrayListL.get(i);
    }

    @Override // android.widget.Adapter
    public final int getCount() {
        ArrayList arrayListL;
        MenuC0129di menuC0129di = this.a;
        if (this.d) {
            menuC0129di.i();
            arrayListL = menuC0129di.j;
        } else {
            arrayListL = menuC0129di.l();
        }
        return this.b < 0 ? arrayListL.size() : arrayListL.size() - 1;
    }

    @Override // android.widget.Adapter
    public final long getItemId(int i) {
        return i;
    }

    @Override // android.widget.Adapter
    public final View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = this.e.inflate(this.f, viewGroup, false);
        }
        int i2 = getItem(i).b;
        int i3 = i - 1;
        ListMenuItemView listMenuItemView = (ListMenuItemView) view;
        boolean z = this.a.m() && i2 != (i3 >= 0 ? getItem(i3).b : i2);
        ImageView imageView = listMenuItemView.h;
        if (imageView != null) {
            imageView.setVisibility((listMenuItemView.o || !z) ? 8 : 0);
        }
        InterfaceC0714wi interfaceC0714wi = (InterfaceC0714wi) view;
        if (this.c) {
            listMenuItemView.q = true;
            listMenuItemView.m = true;
        }
        interfaceC0714wi.e(getItem(i));
        return view;
    }

    @Override // android.widget.BaseAdapter
    public final void notifyDataSetChanged() {
        a();
        super.notifyDataSetChanged();
    }
}
