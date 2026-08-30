package moe.shizuku.manager.starter;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import moe.shizuku.privileged.api.R;
import rikka.shizuku.AbstractActivityC0113d2;
import rikka.shizuku.AbstractC0744xh;
import rikka.shizuku.AbstractC0750xn;
import rikka.shizuku.C0019a1;
import rikka.shizuku.C0419n1;
import rikka.shizuku.C0450o1;
import rikka.shizuku.C0542r1;
import rikka.shizuku.C0741xe;
import rikka.shizuku.Us;
import rikka.shizuku.Xn;
import rikka.widget.borderview.BorderNestedScrollView;

/* JADX INFO: loaded from: classes.dex */
public final class StarterActivity extends AbstractActivityC0113d2 {
    public static final /* synthetic */ int E = 0;
    public final Xn D = new Xn(new C0542r1(4, this), new C0419n1(5, this), Us.class);

    @Override // rikka.shizuku.AbstractActivityC0113d2, rikka.shizuku.AbstractActivityC0051b2, rikka.shizuku.T6, rikka.shizuku.S6, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        AbstractC0744xh abstractC0744xhL = l();
        if (abstractC0744xhL != null) {
            abstractC0744xhL.T(true);
        }
        AbstractC0744xh abstractC0744xhL2 = l();
        if (abstractC0744xhL2 != null) {
            abstractC0744xhL2.U();
        }
        View viewInflate = getLayoutInflater().inflate(R.layout._0_resource_name_obfuscated_res_0x7f0c00a1, (ViewGroup) null, false);
        int i = android.R.id.list;
        if (((BorderNestedScrollView) AbstractC0750xn.h(viewInflate, android.R.id.list)) != null) {
            i = android.R.id.text1;
            TextView textView = (TextView) AbstractC0750xn.h(viewInflate, android.R.id.text1);
            if (textView != null) {
                FrameLayout frameLayout = (FrameLayout) viewInflate;
                C0741xe c0741xe = new C0741xe(frameLayout, textView);
                setContentView(frameLayout);
                ((Us) this.D.getValue()).d.d(this, new C0019a1(new C0450o1(this, 2, c0741xe), 2));
                return;
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(viewInflate.getResources().getResourceName(i)));
    }
}
