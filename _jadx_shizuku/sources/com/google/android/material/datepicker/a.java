package com.google.android.material.datepicker;

import android.view.View;
import android.widget.AdapterView;
import rikka.shizuku.Fi;
import rikka.shizuku.Oh;

/* JADX INFO: loaded from: classes.dex */
public final class a implements AdapterView.OnItemClickListener {
    public final /* synthetic */ MaterialCalendarGridView a;
    public final /* synthetic */ c b;

    public a(c cVar, MaterialCalendarGridView materialCalendarGridView) {
        this.b = cVar;
        this.a = materialCalendarGridView;
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
        MaterialCalendarGridView materialCalendarGridView = this.a;
        Fi fiA = materialCalendarGridView.a();
        if (i < fiA.a() || i > fiA.c()) {
            return;
        }
        if (materialCalendarGridView.a().getItem(i).longValue() >= ((Oh) this.b.e.b).Y.c.a) {
            throw null;
        }
    }
}
