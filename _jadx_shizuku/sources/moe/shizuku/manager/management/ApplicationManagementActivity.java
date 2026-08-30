package moe.shizuku.manager.management;

import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import moe.shizuku.privileged.api.R;
import rikka.shizuku.AbstractActivityC0113d2;
import rikka.shizuku.AbstractC0090ca;
import rikka.shizuku.AbstractC0599sr;
import rikka.shizuku.AbstractC0744xh;
import rikka.shizuku.C0019a1;
import rikka.shizuku.C0147e6;
import rikka.shizuku.C0517q6;
import rikka.shizuku.C0542r1;
import rikka.shizuku.C0550r9;
import rikka.shizuku.C0790z1;
import rikka.shizuku.ExecutorC0243h9;
import rikka.shizuku.Nn;
import rikka.shizuku.Pi;
import rikka.shizuku.S3;
import rikka.shizuku.T3;
import rikka.shizuku.U3;
import rikka.shizuku.W3;
import rikka.shizuku.X3;
import rikka.shizuku.X8;
import rikka.shizuku.Xn;
import rikka.shizuku.Y3;
import rikka.shizuku.Z0;
import rikka.shizuku.Z3;
import rikka.widget.borderview.BorderRecyclerView;

/* JADX INFO: loaded from: classes.dex */
public final class ApplicationManagementActivity extends AbstractActivityC0113d2 {
    public static final /* synthetic */ int G = 0;
    public final Xn D = new Xn(new C0542r1(2, this), new Z3(this), Y3.class);
    public final W3 E;
    public final T3 F;

    public ApplicationManagementActivity() {
        W3 w3 = new W3(new ArrayList());
        C0147e6 c0147e6 = (C0147e6) w3.e;
        c0147e6.c(PackageInfo.class, S3.z);
        c0147e6.c(Object.class, C0790z1.y);
        w3.j(true);
        this.E = w3;
        this.F = new T3(this, 0);
    }

    @Override // rikka.shizuku.AbstractActivityC0113d2, rikka.shizuku.AbstractActivityC0051b2, rikka.shizuku.T6, rikka.shizuku.S6, android.app.Activity
    public final void onCreate(Bundle bundle) {
        int i = 1;
        super.onCreate(bundle);
        if (!Nn.h()) {
            finish();
            return;
        }
        View viewInflate = getLayoutInflater().inflate(R.layout._0_resource_name_obfuscated_res_0x7f0c0025, (ViewGroup) null, false);
        if (viewInflate == null) {
            throw new NullPointerException("rootView");
        }
        BorderRecyclerView borderRecyclerView = (BorderRecyclerView) viewInflate;
        setContentView(borderRecyclerView);
        AbstractC0744xh abstractC0744xhL = l();
        if (abstractC0744xhL != null) {
            abstractC0744xhL.T(true);
        }
        ((Y3) this.D.getValue()).c.d(this, new C0019a1(new Z0(i, this), i));
        Object obj = ((Y3) this.D.getValue()).c.e;
        if (obj == Pi.k) {
            obj = null;
        }
        if (obj == null) {
            Y3 y3 = (Y3) this.D.getValue();
            y3.getClass();
            C0517q6 c0517q6A = AbstractC0744xh.A(y3);
            C0550r9 c0550r9 = AbstractC0090ca.a;
            ExecutorC0243h9 executorC0243h9 = ExecutorC0243h9.b;
            AbstractC0599sr.z(c0517q6A, new X3(false, y3, null), 2);
        }
        W3 w3 = this.E;
        borderRecyclerView.h0(w3);
        X8.t(borderRecyclerView);
        X8.e(borderRecyclerView, 0.0f, (5 & 2) != 0 ? 0.0f : 8.0f, 0.0f, 8.0f);
        w3.a.registerObserver(new U3(this, 0));
        Nn.a(this.F);
    }

    @Override // rikka.shizuku.AbstractActivityC0051b2, android.app.Activity
    public final void onDestroy() {
        super.onDestroy();
        Nn.i(this.F);
    }

    @Override // rikka.shizuku.AbstractActivityC0051b2, android.app.Activity
    public final void onResume() {
        super.onResume();
        this.E.a.b();
    }
}
