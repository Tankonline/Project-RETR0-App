package com.google.android.material.datepicker;

import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import java.util.Calendar;
import moe.shizuku.privileged.api.R;
import rikka.shizuku.AbstractC0470ol;
import rikka.shizuku.C0193fl;
import rikka.shizuku.C0608t5;
import rikka.shizuku.Ei;
import rikka.shizuku.Fi;
import rikka.shizuku.Uh;
import rikka.shizuku.Yk;
import rikka.shizuku.Ys;
import rikka.shizuku.Zr;

/* JADX INFO: loaded from: classes.dex */
public final class c extends Yk {
    public final C0608t5 d;
    public final Ys e;
    public final int f;

    public c(ContextThemeWrapper contextThemeWrapper, C0608t5 c0608t5, Ys ys) {
        Ei ei = c0608t5.a;
        Ei ei2 = c0608t5.d;
        if (ei.a.compareTo(ei2.a) > 0) {
            throw new IllegalArgumentException("firstPage cannot be after currentPage");
        }
        if (ei2.a.compareTo(c0608t5.b.a) > 0) {
            throw new IllegalArgumentException("currentPage cannot be after lastPage");
        }
        this.f = (contextThemeWrapper.getResources().getDimensionPixelSize(R.dimen._0_resource_name_obfuscated_res_0x7f070293) * Fi.d) + (Uh.S(contextThemeWrapper, android.R.attr.windowFullscreen) ? contextThemeWrapper.getResources().getDimensionPixelSize(R.dimen._0_resource_name_obfuscated_res_0x7f070293) : 0);
        this.d = c0608t5;
        this.e = ys;
        j(true);
    }

    @Override // rikka.shizuku.Yk
    public final int a() {
        return this.d.g;
    }

    @Override // rikka.shizuku.Yk
    public final long b(int i) {
        Calendar calendarA = Zr.a(this.d.a.a);
        calendarA.add(2, i);
        calendarA.set(5, 1);
        Calendar calendarA2 = Zr.a(calendarA);
        calendarA2.get(2);
        calendarA2.get(1);
        calendarA2.getMaximum(7);
        calendarA2.getActualMaximum(5);
        calendarA2.getTimeInMillis();
        return calendarA2.getTimeInMillis();
    }

    @Override // rikka.shizuku.Yk
    public final void d(AbstractC0470ol abstractC0470ol, int i) {
        b bVar = (b) abstractC0470ol;
        C0608t5 c0608t5 = this.d;
        Calendar calendarA = Zr.a(c0608t5.a.a);
        calendarA.add(2, i);
        Ei ei = new Ei(calendarA);
        bVar.u.setText(ei.c());
        MaterialCalendarGridView materialCalendarGridView = (MaterialCalendarGridView) bVar.v.findViewById(R.id._0_resource_name_obfuscated_res_0x7f090107);
        if (materialCalendarGridView.a() == null || !ei.equals(materialCalendarGridView.a().a)) {
            new Fi(ei, c0608t5);
            throw null;
        }
        materialCalendarGridView.invalidate();
        materialCalendarGridView.a().getClass();
        throw null;
    }

    @Override // rikka.shizuku.Yk
    public final AbstractC0470ol f(ViewGroup viewGroup, int i) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout._0_resource_name_obfuscated_res_0x7f0c006d, viewGroup, false);
        if (!Uh.S(viewGroup.getContext(), android.R.attr.windowFullscreen)) {
            return new b(linearLayout, false);
        }
        linearLayout.setLayoutParams(new C0193fl(-1, this.f));
        return new b(linearLayout, true);
    }
}
