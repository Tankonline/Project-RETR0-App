package rikka.shizuku;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Looper;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import moe.shizuku.privileged.api.R;

/* JADX INFO: renamed from: rikka.shizuku.ik, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0284ik extends Ac {
    public C0438nk X;
    public RecyclerView Y;
    public boolean Z;
    public boolean a0;
    public final C0254hk W = new C0254hk(this);
    public int b0 = R.layout._0_resource_name_obfuscated_res_0x7f0c008f;
    public final F1 c0 = new F1(this, Looper.getMainLooper());
    public final RunnableC0607t4 d0 = new RunnableC0607t4(10, this);

    @Override // rikka.shizuku.Ac
    public final void A(Bundle bundle) {
        PreferenceScreen preferenceScreen = this.X.h;
        if (preferenceScreen != null) {
            Bundle bundle2 = new Bundle();
            preferenceScreen.c(bundle2);
            bundle.putBundle("android:preferences", bundle2);
        }
    }

    @Override // rikka.shizuku.Ac
    public final void B() {
        this.E = true;
        C0438nk c0438nk = this.X;
        c0438nk.i = this;
        c0438nk.j = this;
    }

    @Override // rikka.shizuku.Ac
    public final void C() {
        this.E = true;
        C0438nk c0438nk = this.X;
        c0438nk.i = null;
        c0438nk.j = null;
    }

    @Override // rikka.shizuku.Ac
    public final void D(Bundle bundle) {
        PreferenceScreen preferenceScreen;
        Bundle bundle2;
        PreferenceScreen preferenceScreen2;
        if (bundle != null && (bundle2 = bundle.getBundle("android:preferences")) != null && (preferenceScreen2 = this.X.h) != null) {
            preferenceScreen2.b(bundle2);
        }
        if (this.Z && (preferenceScreen = this.X.h) != null) {
            this.Y.h0(new C0376lk(preferenceScreen));
            preferenceScreen.k();
        }
        this.a0 = true;
    }

    public final Preference M(String str) {
        PreferenceScreen preferenceScreen;
        C0438nk c0438nk = this.X;
        if (c0438nk == null || (preferenceScreen = c0438nk.h) == null) {
            return null;
        }
        return preferenceScreen.B(str);
    }

    public abstract void N();

    public RecyclerView O(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        RecyclerView recyclerView;
        if (H().getPackageManager().hasSystemFeature("android.hardware.type.automotive") && (recyclerView = (RecyclerView) viewGroup.findViewById(AbstractC0809zk.recycler_view)) != null) {
            return recyclerView;
        }
        RecyclerView recyclerView2 = (RecyclerView) layoutInflater.inflate(R.layout._0_resource_name_obfuscated_res_0x7f0c0091, viewGroup, false);
        H();
        recyclerView2.i0(new LinearLayoutManager(1));
        C0469ok c0469ok = new C0469ok(recyclerView2);
        recyclerView2.l0 = c0469ok;
        Hs.m(recyclerView2, c0469ok);
        return recyclerView2;
    }

    @Override // rikka.shizuku.Ac
    public final void v(Bundle bundle) {
        super.v(bundle);
        TypedValue typedValue = new TypedValue();
        H().getTheme().resolveAttribute(R.attr._0_resource_name_obfuscated_res_0x7f0403f4, typedValue, true);
        int i = typedValue.resourceId;
        if (i == 0) {
            i = R.style._0_resource_name_obfuscated_res_0x7f12018b;
        }
        H().getTheme().applyStyle(i, false);
        C0438nk c0438nk = new C0438nk(H());
        this.X = c0438nk;
        c0438nk.k = this;
        Bundle bundle2 = this.f;
        if (bundle2 != null) {
            bundle2.getString("androidx.preference.PreferenceFragmentCompat.PREFERENCE_ROOT");
        }
        N();
    }

    @Override // rikka.shizuku.Ac
    public final View w(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        TypedArray typedArrayObtainStyledAttributes = H().obtainStyledAttributes(null, Jk.h, R.attr._0_resource_name_obfuscated_res_0x7f0403ee, 0);
        this.b0 = typedArrayObtainStyledAttributes.getResourceId(0, this.b0);
        Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(1);
        int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(2, -1);
        boolean z = typedArrayObtainStyledAttributes.getBoolean(3, true);
        typedArrayObtainStyledAttributes.recycle();
        LayoutInflater layoutInflaterCloneInContext = layoutInflater.cloneInContext(H());
        View viewInflate = layoutInflaterCloneInContext.inflate(this.b0, viewGroup, false);
        View viewFindViewById = viewInflate.findViewById(android.R.id.list_container);
        if (!(viewFindViewById instanceof ViewGroup)) {
            throw new IllegalStateException("Content has view with id attribute 'android.R.id.list_container' that is not a ViewGroup class");
        }
        ViewGroup viewGroup2 = (ViewGroup) viewFindViewById;
        RecyclerView recyclerViewO = O(layoutInflaterCloneInContext, viewGroup2, bundle);
        this.Y = recyclerViewO;
        C0254hk c0254hk = this.W;
        recyclerViewO.i(c0254hk);
        if (drawable != null) {
            c0254hk.getClass();
            c0254hk.b = drawable.getIntrinsicHeight();
        } else {
            c0254hk.b = 0;
        }
        c0254hk.a = drawable;
        AbstractC0284ik abstractC0284ik = c0254hk.d;
        RecyclerView recyclerView = abstractC0284ik.Y;
        if (recyclerView.p.size() != 0) {
            AbstractC0162el abstractC0162el = recyclerView.n;
            if (abstractC0162el != null) {
                abstractC0162el.c("Cannot invalidate item decorations during a scroll or layout");
            }
            recyclerView.R();
            recyclerView.requestLayout();
        }
        if (dimensionPixelSize != -1) {
            c0254hk.b = dimensionPixelSize;
            RecyclerView recyclerView2 = abstractC0284ik.Y;
            if (recyclerView2.p.size() != 0) {
                AbstractC0162el abstractC0162el2 = recyclerView2.n;
                if (abstractC0162el2 != null) {
                    abstractC0162el2.c("Cannot invalidate item decorations during a scroll or layout");
                }
                recyclerView2.R();
                recyclerView2.requestLayout();
            }
        }
        c0254hk.c = z;
        if (this.Y.getParent() == null) {
            viewGroup2.addView(this.Y);
        }
        this.c0.post(this.d0);
        return viewInflate;
    }

    @Override // rikka.shizuku.Ac
    public final void x() {
        RunnableC0607t4 runnableC0607t4 = this.d0;
        F1 f1 = this.c0;
        f1.removeCallbacks(runnableC0607t4);
        f1.removeMessages(1);
        if (this.Z) {
            this.Y.h0(null);
            PreferenceScreen preferenceScreen = this.X.h;
            if (preferenceScreen != null) {
                preferenceScreen.p();
            }
        }
        this.Y = null;
        this.E = true;
    }
}
