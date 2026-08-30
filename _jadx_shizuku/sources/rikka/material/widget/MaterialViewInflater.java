package rikka.material.widget;

import android.content.Context;
import android.util.AttributeSet;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.theme.MaterialComponentsViewInflater;
import rikka.shizuku.AbstractC0744xh;
import rikka.shizuku.C0420n2;
import rikka.shizuku.C0513q2;
import rikka.shizuku.C0606t3;
import rikka.shizuku.Ib;
import rikka.shizuku.Kb;
import rikka.shizuku.Lb;
import rikka.shizuku.Zh;

/* JADX INFO: loaded from: classes.dex */
public class MaterialViewInflater extends MaterialComponentsViewInflater {
    @Override // com.google.android.material.theme.MaterialComponentsViewInflater, rikka.shizuku.D3
    public final C0420n2 b(Context context, AttributeSet attributeSet) {
        return !AbstractC0744xh.F() ? new MaterialButton(context, attributeSet) : new Kb(context, attributeSet, 0);
    }

    @Override // rikka.shizuku.D3
    public final C0513q2 d(Context context, AttributeSet attributeSet) {
        return !AbstractC0744xh.F() ? super.d(context, attributeSet) : new Ib(context, attributeSet);
    }

    @Override // com.google.android.material.theme.MaterialComponentsViewInflater, rikka.shizuku.D3
    public final C0606t3 f(Context context, AttributeSet attributeSet) {
        return !AbstractC0744xh.F() ? new Zh(context, attributeSet) : new Lb(context, attributeSet);
    }
}
