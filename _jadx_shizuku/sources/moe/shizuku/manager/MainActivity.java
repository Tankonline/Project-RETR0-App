package moe.shizuku.manager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import moe.shizuku.manager.MainActivity;
import moe.shizuku.manager.settings.SettingsActivity;
import moe.shizuku.privileged.api.R;
import rikka.shizuku.AbstractActivityC0113d2;
import rikka.shizuku.AbstractC0046as;
import rikka.shizuku.AbstractC0090ca;
import rikka.shizuku.AbstractC0599sr;
import rikka.shizuku.AbstractC0688vn;
import rikka.shizuku.AbstractC0744xh;
import rikka.shizuku.AbstractC0750xn;
import rikka.shizuku.C0419n1;
import rikka.shizuku.C0512q1;
import rikka.shizuku.C0517q6;
import rikka.shizuku.C0542r1;
import rikka.shizuku.C0550r9;
import rikka.shizuku.C0648ue;
import rikka.shizuku.C0710we;
import rikka.shizuku.C0772ye;
import rikka.shizuku.C0799za;
import rikka.shizuku.C0803ze;
import rikka.shizuku.Ch;
import rikka.shizuku.DialogInterfaceOnClickListenerC0679ve;
import rikka.shizuku.E1;
import rikka.shizuku.ExecutorC0243h9;
import rikka.shizuku.H3;
import rikka.shizuku.InterfaceC0462od;
import rikka.shizuku.Nn;
import rikka.shizuku.T3;
import rikka.shizuku.X8;
import rikka.shizuku.Xn;
import rikka.shizuku.Y3;
import rikka.shizuku.Z3;
import rikka.widget.borderview.BorderRecyclerView;

/* JADX INFO: loaded from: classes.dex */
public class MainActivity extends AbstractActivityC0113d2 {
    public static final /* synthetic */ int I = 0;
    public final C0648ue D = new C0648ue(0, this);
    public final T3 E = new T3(this, 1);
    public final Xn F = new Xn(new C0542r1(3, this), new Z3(3), C0803ze.class);
    public final Xn G = new Xn(new C0542r1(2, this), new Z3(this), Y3.class);
    public final Object H = AbstractC0046as.H(new C0419n1(2, this));

    public final Y3 A() {
        return (Y3) this.G.getValue();
    }

    /* JADX WARN: Type inference failed for: r0v8, types: [java.lang.Object, rikka.shizuku.jg] */
    @Override // rikka.shizuku.AbstractActivityC0113d2, rikka.shizuku.AbstractActivityC0051b2, rikka.shizuku.T6, rikka.shizuku.S6, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        View viewInflate = getLayoutInflater().inflate(R.layout._0_resource_name_obfuscated_res_0x7f0c003b, (ViewGroup) null, false);
        if (viewInflate == null) {
            throw new NullPointerException("rootView");
        }
        BorderRecyclerView borderRecyclerView = (BorderRecyclerView) viewInflate;
        setContentView(borderRecyclerView);
        final int i = 0;
        ((C0803ze) this.F.getValue()).c.d(this, new C0512q1(new InterfaceC0462od(this) { // from class: rikka.shizuku.te
            public final /* synthetic */ MainActivity b;

            {
                this.b = this;
            }

            /* JADX WARN: Type inference failed for: r4v15, types: [java.lang.Object, rikka.shizuku.jg] */
            /* JADX WARN: Type inference failed for: r4v5, types: [java.lang.Object, rikka.shizuku.jg] */
            @Override // rikka.shizuku.InterfaceC0462od
            public final Object g(Object obj) {
                C0103cn c0103cn;
                Rl rl = (Rl) obj;
                switch (i) {
                    case 0:
                        int i2 = MainActivity.I;
                        if (rl.a == 1) {
                            MainActivity mainActivity = this.b;
                            Object obj2 = ((C0803ze) mainActivity.F.getValue()).c.e;
                            if (obj2 == Pi.k) {
                                obj2 = null;
                            }
                            Rl rl2 = (Rl) obj2;
                            if (rl2 != null && (c0103cn = (C0103cn) rl2.b) != null) {
                                ((C0710we) mainActivity.H.getValue()).m();
                                AbstractC0750xn.a.edit().putInt("mode", c0103cn.a == 0 ? 0 : 1).apply();
                            }
                        }
                        break;
                    default:
                        int i3 = MainActivity.I;
                        if (rl.a == 1) {
                            ((C0710we) this.b.H.getValue()).m();
                        }
                        break;
                }
                return Pe.b;
            }
        }, 1));
        final int i2 = 1;
        A().e.d(this, new C0512q1(new InterfaceC0462od(this) { // from class: rikka.shizuku.te
            public final /* synthetic */ MainActivity b;

            {
                this.b = this;
            }

            /* JADX WARN: Type inference failed for: r4v15, types: [java.lang.Object, rikka.shizuku.jg] */
            /* JADX WARN: Type inference failed for: r4v5, types: [java.lang.Object, rikka.shizuku.jg] */
            @Override // rikka.shizuku.InterfaceC0462od
            public final Object g(Object obj) {
                C0103cn c0103cn;
                Rl rl = (Rl) obj;
                switch (i2) {
                    case 0:
                        int i3 = MainActivity.I;
                        if (rl.a == 1) {
                            MainActivity mainActivity = this.b;
                            Object obj2 = ((C0803ze) mainActivity.F.getValue()).c.e;
                            if (obj2 == Pi.k) {
                                obj2 = null;
                            }
                            Rl rl2 = (Rl) obj2;
                            if (rl2 != null && (c0103cn = (C0103cn) rl2.b) != null) {
                                ((C0710we) mainActivity.H.getValue()).m();
                                AbstractC0750xn.a.edit().putInt("mode", c0103cn.a == 0 ? 0 : 1).apply();
                            }
                        }
                        break;
                    default:
                        int i4 = MainActivity.I;
                        if (rl.a == 1) {
                            ((C0710we) this.b.H.getValue()).m();
                        }
                        break;
                }
                return Pe.b;
            }
        }, 1));
        borderRecyclerView.h0((C0710we) this.H.getValue());
        X8.t(borderRecyclerView);
        DisplayMetrics displayMetrics = borderRecyclerView.getContext().getResources().getDisplayMetrics();
        borderRecyclerView.i(new C0799za(AbstractC0046as.R(TypedValue.applyDimension(1, 0.0f, displayMetrics)), AbstractC0046as.R(TypedValue.applyDimension(1, 4.0f, displayMetrics)), AbstractC0046as.R(TypedValue.applyDimension(1, 0.0f, displayMetrics)), AbstractC0046as.R(TypedValue.applyDimension(1, 4.0f, displayMetrics)), 1));
        X8.e(borderRecyclerView, 16.0f, 4.0f, 16.0f, 4.0f);
        Nn.b(this.D, true, null);
        Nn.a(this.E);
    }

    @Override // android.app.Activity
    public final boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu._0_resource_name_obfuscated_res_0x7f0e0000, menu);
        return true;
    }

    @Override // rikka.shizuku.AbstractActivityC0051b2, android.app.Activity
    public final void onDestroy() {
        super.onDestroy();
        Nn.j(this.D);
        Nn.i(this.E);
    }

    @Override // android.app.Activity
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id._0_resource_name_obfuscated_res_0x7f090030 /* 2131296304 */:
                View viewInflate = LayoutInflater.from(this).inflate(R.layout._0_resource_name_obfuscated_res_0x7f0c001c, (ViewGroup) null, false);
                int i = android.R.id.icon;
                ImageView imageView = (ImageView) AbstractC0750xn.h(viewInflate, android.R.id.icon);
                if (imageView != null) {
                    i = R.id._0_resource_name_obfuscated_res_0x7f09019a;
                    TextView textView = (TextView) AbstractC0750xn.h(viewInflate, R.id._0_resource_name_obfuscated_res_0x7f09019a);
                    if (textView != null) {
                        i = R.id._0_resource_name_obfuscated_res_0x7f0901f9;
                        TextView textView2 = (TextView) AbstractC0750xn.h(viewInflate, R.id._0_resource_name_obfuscated_res_0x7f0901f9);
                        if (textView2 != null) {
                            textView.setMovementMethod(LinkMovementMethod.getInstance());
                            textView.setText(AbstractC0688vn.A(getString(R.string._0_resource_name_obfuscated_res_0x7f11001b, "<b><a href=\"https://github.com/RikkaApps/Shizuku\">GitHub</a></b>"), 0));
                            H3 h3 = H3.a;
                            imageView.setImageBitmap(H3.b(this, getApplicationInfo(), Process.myUid() / 100000, getResources().getDimensionPixelOffset(R.dimen._0_resource_name_obfuscated_res_0x7f07006d)));
                            textView2.setText(getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
                            Ch ch = new Ch(this);
                            ((E1) ch.b).s = (LinearLayout) viewInflate;
                            ch.f();
                            return true;
                        }
                    }
                }
                throw new NullPointerException("Missing required view with ID: ".concat(viewInflate.getResources().getResourceName(i)));
            case R.id._0_resource_name_obfuscated_res_0x7f090041 /* 2131296321 */:
                startActivity(new Intent(this, (Class<?>) SettingsActivity.class));
                return true;
            case R.id._0_resource_name_obfuscated_res_0x7f090042 /* 2131296322 */:
                if (!Nn.h()) {
                    return true;
                }
                Ch ch2 = new Ch(this);
                E1 e1 = (E1) ch2.b;
                e1.f = e1.a.getText(R.string._0_resource_name_obfuscated_res_0x7f11005e);
                ch2.g(android.R.string.ok, new DialogInterfaceOnClickListenerC0679ve());
                e1.i = e1.a.getText(android.R.string.cancel);
                e1.j = null;
                ch2.f();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override // rikka.shizuku.AbstractActivityC0051b2, android.app.Activity
    public final void onResume() {
        super.onResume();
        z();
    }

    public final void z() {
        C0803ze c0803ze = (C0803ze) this.F.getValue();
        c0803ze.getClass();
        C0517q6 c0517q6A = AbstractC0744xh.A(c0803ze);
        C0550r9 c0550r9 = AbstractC0090ca.a;
        ExecutorC0243h9 executorC0243h9 = ExecutorC0243h9.b;
        AbstractC0599sr.z(c0517q6A, new C0772ye(c0803ze, null), 2);
    }
}
