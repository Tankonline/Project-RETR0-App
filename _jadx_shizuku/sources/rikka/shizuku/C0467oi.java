package rikka.shizuku;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import java.util.WeakHashMap;
import moe.shizuku.privileged.api.R;

/* JADX INFO: renamed from: rikka.shizuku.oi, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0467oi {
    public final Context a;
    public final MenuC0129di b;
    public final boolean c;
    public final int d;
    public View e;
    public boolean g;
    public InterfaceC0652ui h;
    public AbstractC0374li i;
    public C0405mi j;
    public int f = 8388611;
    public final C0405mi k = new C0405mi(this);

    public C0467oi(int i, Context context, View view, MenuC0129di menuC0129di, boolean z) {
        this.a = context;
        this.b = menuC0129di;
        this.e = view;
        this.c = z;
        this.d = i;
    }

    public final AbstractC0374li a() {
        AbstractC0374li no;
        if (this.i == null) {
            Context context = this.a;
            Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
            Point point = new Point();
            AbstractC0436ni.a(defaultDisplay, point);
            if (Math.min(point.x, point.y) >= context.getResources().getDimensionPixelSize(R.dimen._0_resource_name_obfuscated_res_0x7f070016)) {
                no = new G5(context, this.e, this.d, this.c);
            } else {
                View view = this.e;
                Context context2 = this.a;
                boolean z = this.c;
                no = new No(this.d, context2, view, this.b, z);
            }
            no.l(this.b);
            no.r(this.k);
            no.n(this.e);
            no.c(this.h);
            no.o(this.g);
            no.p(this.f);
            this.i = no;
        }
        return this.i;
    }

    public final boolean b() {
        AbstractC0374li abstractC0374li = this.i;
        return abstractC0374li != null && abstractC0374li.a();
    }

    public void c() {
        this.i = null;
        C0405mi c0405mi = this.j;
        if (c0405mi != null) {
            c0405mi.onDismiss();
        }
    }

    public final void d(int i, int i2, boolean z, boolean z2) {
        AbstractC0374li abstractC0374liA = a();
        abstractC0374liA.s(z2);
        if (z) {
            int i3 = this.f;
            View view = this.e;
            WeakHashMap weakHashMap = Hs.a;
            if ((Gravity.getAbsoluteGravity(i3, view.getLayoutDirection()) & 7) == 5) {
                i -= this.e.getWidth();
            }
            abstractC0374liA.q(i);
            abstractC0374liA.t(i2);
            int i4 = (int) ((this.a.getResources().getDisplayMetrics().density * 48.0f) / 2.0f);
            abstractC0374liA.a = new Rect(i - i4, i2 - i4, i + i4, i2 + i4);
        }
        abstractC0374liA.e();
    }
}
