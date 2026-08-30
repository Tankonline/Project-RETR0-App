package rikka.shizuku;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;
import moe.shizuku.privileged.api.R;

/* JADX INFO: loaded from: classes.dex */
public final class Rg extends BaseAdapter {
    public int a = -1;
    public final /* synthetic */ Sg b;

    public Rg(Sg sg) {
        this.b = sg;
        a();
    }

    public final void a() {
        MenuC0129di menuC0129di = this.b.c;
        C0221gi c0221gi = menuC0129di.v;
        if (c0221gi != null) {
            menuC0129di.i();
            ArrayList arrayList = menuC0129di.j;
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                if (((C0221gi) arrayList.get(i)) == c0221gi) {
                    this.a = i;
                    return;
                }
            }
        }
        this.a = -1;
    }

    @Override // android.widget.Adapter
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public final C0221gi getItem(int i) {
        Sg sg = this.b;
        MenuC0129di menuC0129di = sg.c;
        menuC0129di.i();
        ArrayList arrayList = menuC0129di.j;
        sg.getClass();
        int i2 = this.a;
        if (i2 >= 0 && i >= i2) {
            i++;
        }
        return (C0221gi) arrayList.get(i);
    }

    @Override // android.widget.Adapter
    public final int getCount() {
        Sg sg = this.b;
        MenuC0129di menuC0129di = sg.c;
        menuC0129di.i();
        int size = menuC0129di.j.size();
        sg.getClass();
        return this.a < 0 ? size : size - 1;
    }

    @Override // android.widget.Adapter
    public final long getItemId(int i) {
        return i;
    }

    @Override // android.widget.Adapter
    public final View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = this.b.b.inflate(R.layout._0_resource_name_obfuscated_res_0x7f0c0010, viewGroup, false);
        }
        ((InterfaceC0714wi) view).e(getItem(i));
        return view;
    }

    @Override // android.widget.BaseAdapter
    public final void notifyDataSetChanged() {
        a();
        super.notifyDataSetChanged();
    }
}
