package rikka.shizuku;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import moe.shizuku.privileged.api.R;

/* JADX INFO: renamed from: rikka.shizuku.go, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0227go extends Yk {
    public final C0318jo d;

    public C0227go(C0318jo c0318jo) {
        this.d = c0318jo;
    }

    @Override // rikka.shizuku.Yk
    public final int a() {
        CharSequence[] charSequenceArr = this.d.l;
        if (charSequenceArr == null) {
            return 0;
        }
        return charSequenceArr.length;
    }

    @Override // rikka.shizuku.Yk
    public final void d(AbstractC0470ol abstractC0470ol, int i) {
        ViewOnClickListenerC0196fo viewOnClickListenerC0196fo = (ViewOnClickListenerC0196fo) abstractC0470ol;
        C0318jo c0318jo = this.d;
        viewOnClickListenerC0196fo.v = c0318jo;
        CharSequence charSequence = c0318jo.l[i];
        CheckedTextView checkedTextView = viewOnClickListenerC0196fo.u;
        checkedTextView.setText(charSequence);
        checkedTextView.setChecked(i == viewOnClickListenerC0196fo.v.m);
        checkedTextView.setMaxLines(viewOnClickListenerC0196fo.v.h == 1 ? Integer.MAX_VALUE : 1);
        C0318jo c0318jo2 = viewOnClickListenerC0196fo.v;
        int i2 = c0318jo2.c[c0318jo2.h][0];
        int paddingTop = checkedTextView.getPaddingTop();
        checkedTextView.setPadding(i2, paddingTop, i2, paddingTop);
    }

    @Override // rikka.shizuku.Yk
    public final AbstractC0470ol f(ViewGroup viewGroup, int i) {
        View viewInflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout._0_resource_name_obfuscated_res_0x7f0c009f, viewGroup, false);
        ViewOnClickListenerC0196fo viewOnClickListenerC0196fo = new ViewOnClickListenerC0196fo(viewInflate);
        viewOnClickListenerC0196fo.u = (CheckedTextView) viewInflate.findViewById(android.R.id.text1);
        viewInflate.setOnClickListener(viewOnClickListenerC0196fo);
        return viewOnClickListenerC0196fo;
    }
}
