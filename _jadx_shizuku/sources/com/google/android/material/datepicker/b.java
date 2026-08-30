package com.google.android.material.datepicker;

import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.WeakHashMap;
import moe.shizuku.privileged.api.R;
import rikka.shizuku.AbstractC0470ol;
import rikka.shizuku.C0693vs;
import rikka.shizuku.Hs;

/* JADX INFO: loaded from: classes.dex */
public final class b extends AbstractC0470ol {
    public final TextView u;
    public final MaterialCalendarGridView v;

    public b(LinearLayout linearLayout, boolean z) {
        super(linearLayout);
        TextView textView = (TextView) linearLayout.findViewById(R.id._0_resource_name_obfuscated_res_0x7f09010c);
        this.u = textView;
        WeakHashMap weakHashMap = Hs.a;
        new C0693vs(R.id._0_resource_name_obfuscated_res_0x7f0901bc, Boolean.class, 0, 28, 3).d(textView, Boolean.TRUE);
        this.v = (MaterialCalendarGridView) linearLayout.findViewById(R.id._0_resource_name_obfuscated_res_0x7f090107);
        if (z) {
            return;
        }
        textView.setVisibility(8);
    }
}
