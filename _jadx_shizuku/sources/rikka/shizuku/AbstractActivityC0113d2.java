package rikka.shizuku;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.appbar.AppBarLayout;
import moe.shizuku.privileged.api.R;

/* JADX INFO: renamed from: rikka.shizuku.d2, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractActivityC0113d2 extends AbstractActivityC0051b2 {
    public final Object A;
    public final Object B;
    public final Object C;

    public AbstractActivityC0113d2() {
        final int i = 0;
        this.A = AbstractC0046as.H(new InterfaceC0124dd(this) { // from class: rikka.shizuku.c2
            public final /* synthetic */ AbstractActivityC0113d2 b;

            {
                this.b = this;
            }

            @Override // rikka.shizuku.InterfaceC0124dd
            public final Object a() {
                switch (i) {
                    case 0:
                        return (ViewGroup) this.b.findViewById(R.id._0_resource_name_obfuscated_res_0x7f09016c);
                    case 1:
                        return (AppBarLayout) this.b.findViewById(R.id._0_resource_name_obfuscated_res_0x7f0901e3);
                    default:
                        return (Toolbar) this.b.findViewById(R.id._0_resource_name_obfuscated_res_0x7f0901e2);
                }
            }
        });
        final int i2 = 1;
        this.B = AbstractC0046as.H(new InterfaceC0124dd(this) { // from class: rikka.shizuku.c2
            public final /* synthetic */ AbstractActivityC0113d2 b;

            {
                this.b = this;
            }

            @Override // rikka.shizuku.InterfaceC0124dd
            public final Object a() {
                switch (i2) {
                    case 0:
                        return (ViewGroup) this.b.findViewById(R.id._0_resource_name_obfuscated_res_0x7f09016c);
                    case 1:
                        return (AppBarLayout) this.b.findViewById(R.id._0_resource_name_obfuscated_res_0x7f0901e3);
                    default:
                        return (Toolbar) this.b.findViewById(R.id._0_resource_name_obfuscated_res_0x7f0901e2);
                }
            }
        });
        final int i3 = 2;
        this.C = AbstractC0046as.H(new InterfaceC0124dd(this) { // from class: rikka.shizuku.c2
            public final /* synthetic */ AbstractActivityC0113d2 b;

            {
                this.b = this;
            }

            @Override // rikka.shizuku.InterfaceC0124dd
            public final Object a() {
                switch (i3) {
                    case 0:
                        return (ViewGroup) this.b.findViewById(R.id._0_resource_name_obfuscated_res_0x7f09016c);
                    case 1:
                        return (AppBarLayout) this.b.findViewById(R.id._0_resource_name_obfuscated_res_0x7f0901e3);
                    default:
                        return (Toolbar) this.b.findViewById(R.id._0_resource_name_obfuscated_res_0x7f0901e2);
                }
            }
        });
    }

    @Override // rikka.shizuku.AbstractActivityC0051b2
    public final void o() {
        super.o();
        Window window = getWindow();
        if (window != null) {
            window.setStatusBarColor(0);
        }
    }

    /* JADX WARN: Type inference failed for: r5v2, types: [java.lang.Object, rikka.shizuku.jg] */
    @Override // rikka.shizuku.AbstractActivityC0051b2, rikka.shizuku.T6, rikka.shizuku.S6, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        super.setContentView(y());
        Toolbar toolbar = (Toolbar) this.C.getValue();
        I2 i2 = (I2) k();
        i2.C();
        AbstractC0744xh abstractC0744xh = i2.o;
        if (abstractC0744xh instanceof Ct) {
            throw new IllegalStateException("This Activity already has an action bar supplied by the window decor. Do not request Window.FEATURE_SUPPORT_ACTION_BAR and set windowActionBar to false in your theme to use a Toolbar instead.");
        }
        i2.p = null;
        if (abstractC0744xh != null) {
            abstractC0744xh.K();
        }
        i2.o = null;
        Object obj = i2.j;
        C0260hq c0260hq = new C0260hq(toolbar, obj instanceof Activity ? ((Activity) obj).getTitle() : i2.q, i2.m);
        i2.o = c0260hq;
        i2.m.b = c0260hq.s;
        if (!toolbar.R) {
            toolbar.R = true;
            toolbar.D();
        }
        i2.d();
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.Object, rikka.shizuku.jg] */
    @Override // rikka.shizuku.AbstractActivityC0051b2, android.app.Activity
    public final void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        ((ViewGroup) this.A.getValue()).addView(view, 0, layoutParams);
    }

    public int y() {
        return R.layout._0_resource_name_obfuscated_res_0x7f0c0023;
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Object, rikka.shizuku.jg] */
    /* JADX WARN: Type inference failed for: r1v0, types: [java.lang.Object, rikka.shizuku.jg] */
    @Override // rikka.shizuku.AbstractActivityC0051b2, android.app.Activity
    public final void setContentView(int i) {
        LayoutInflater layoutInflater = getLayoutInflater();
        ?? r1 = this.A;
        layoutInflater.inflate(i, (ViewGroup) r1.getValue(), true);
        ((ViewGroup) r1.getValue()).bringChildToFront((AppBarLayout) this.B.getValue());
    }

    @Override // rikka.shizuku.AbstractActivityC0051b2, android.app.Activity
    public final void setContentView(View view) {
        setContentView(view, new FrameLayout.LayoutParams(-1, -1));
    }
}
