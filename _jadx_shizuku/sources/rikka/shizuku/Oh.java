package rikka.shizuku;

import android.R;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.Scroller;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class Oh<S> extends Tj {
    public int X;
    public C0608t5 Y;
    public Ei Z;
    public int a0;
    public R2 b0;
    public RecyclerView c0;
    public RecyclerView d0;
    public View e0;
    public View f0;
    public View g0;
    public View h0;

    @Override // rikka.shizuku.Ac
    public final void A(Bundle bundle) {
        bundle.putInt("THEME_RES_ID_KEY", this.X);
        bundle.putParcelable("GRID_SELECTOR_KEY", null);
        bundle.putParcelable("CALENDAR_CONSTRAINTS_KEY", this.Y);
        bundle.putParcelable("DAY_VIEW_DECORATOR_KEY", null);
        bundle.putParcelable("CURRENT_MONTH_KEY", this.Z);
    }

    public final void M(Ei ei) {
        com.google.android.material.datepicker.c cVar = (com.google.android.material.datepicker.c) this.d0.m;
        int iD = cVar.d.a.d(ei);
        int iD2 = iD - cVar.d.a.d(this.Z);
        boolean z = Math.abs(iD2) > 3;
        boolean z2 = iD2 > 0;
        this.Z = ei;
        if (z && z2) {
            this.d0.g0(iD - 3);
            this.d0.post(new RunnableC0670v5(iD, 3, this));
        } else if (!z) {
            this.d0.post(new RunnableC0670v5(iD, 3, this));
        } else {
            this.d0.g0(iD + 3);
            this.d0.post(new RunnableC0670v5(iD, 3, this));
        }
    }

    public final void N(int i) {
        this.a0 = i;
        if (i != 2) {
            if (i == 1) {
                this.g0.setVisibility(8);
                this.h0.setVisibility(0);
                this.e0.setVisibility(0);
                this.f0.setVisibility(0);
                M(this.Z);
                return;
            }
            return;
        }
        RecyclerView recyclerView = this.c0;
        recyclerView.n.q0(this.Z.c - ((qu) recyclerView.m).d.Y.a.c);
        this.g0.setVisibility(0);
        this.h0.setVisibility(8);
        this.e0.setVisibility(8);
        this.f0.setVisibility(8);
    }

    @Override // rikka.shizuku.Ac
    public final void v(Bundle bundle) {
        super.v(bundle);
        if (bundle == null) {
            bundle = this.f;
        }
        this.X = bundle.getInt("THEME_RES_ID_KEY");
        if (bundle.getParcelable("GRID_SELECTOR_KEY") != null) {
            throw new ClassCastException();
        }
        this.Y = (C0608t5) bundle.getParcelable("CALENDAR_CONSTRAINTS_KEY");
        if (bundle.getParcelable("DAY_VIEW_DECORATOR_KEY") != null) {
            throw new ClassCastException();
        }
        this.Z = (Ei) bundle.getParcelable("CURRENT_MONTH_KEY");
    }

    @Override // rikka.shizuku.Ac
    public final View w(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        int i;
        int i2;
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(i(), this.X);
        this.b0 = new R2(contextThemeWrapper);
        LayoutInflater layoutInflaterCloneInContext = layoutInflater.cloneInContext(contextThemeWrapper);
        Ei ei = this.Y.a;
        if (Uh.S(contextThemeWrapper, R.attr.windowFullscreen)) {
            i = moe.shizuku.privileged.api.R.layout._0_resource_name_obfuscated_res_0x7f0c0070;
            i2 = 1;
        } else {
            i = moe.shizuku.privileged.api.R.layout._0_resource_name_obfuscated_res_0x7f0c006b;
            i2 = 0;
        }
        View viewInflate = layoutInflaterCloneInContext.inflate(i, viewGroup, false);
        Resources resources = H().getResources();
        int dimensionPixelOffset = resources.getDimensionPixelOffset(moe.shizuku.privileged.api.R.dimen._0_resource_name_obfuscated_res_0x7f0702a7) + resources.getDimensionPixelOffset(moe.shizuku.privileged.api.R.dimen._0_resource_name_obfuscated_res_0x7f0702a9) + resources.getDimensionPixelSize(moe.shizuku.privileged.api.R.dimen._0_resource_name_obfuscated_res_0x7f0702a8);
        int dimensionPixelSize = resources.getDimensionPixelSize(moe.shizuku.privileged.api.R.dimen._0_resource_name_obfuscated_res_0x7f070298);
        int i3 = Fi.d;
        viewInflate.setMinimumHeight(dimensionPixelOffset + dimensionPixelSize + (resources.getDimensionPixelOffset(moe.shizuku.privileged.api.R.dimen._0_resource_name_obfuscated_res_0x7f0702a6) * (i3 - 1)) + (resources.getDimensionPixelSize(moe.shizuku.privileged.api.R.dimen._0_resource_name_obfuscated_res_0x7f070293) * i3) + resources.getDimensionPixelOffset(moe.shizuku.privileged.api.R.dimen._0_resource_name_obfuscated_res_0x7f070290));
        GridView gridView = (GridView) viewInflate.findViewById(moe.shizuku.privileged.api.R.id._0_resource_name_obfuscated_res_0x7f090110);
        Hs.m(gridView, new Kh(0));
        int i4 = this.Y.e;
        gridView.setAdapter((ListAdapter) (i4 > 0 ? new V8(i4) : new V8()));
        gridView.setNumColumns(ei.d);
        gridView.setEnabled(false);
        this.d0 = (RecyclerView) viewInflate.findViewById(moe.shizuku.privileged.api.R.id._0_resource_name_obfuscated_res_0x7f090113);
        this.d0.i0(new Lh(this, i2, i2));
        this.d0.setTag("MONTHS_VIEW_GROUP_TAG");
        com.google.android.material.datepicker.c cVar = new com.google.android.material.datepicker.c(contextThemeWrapper, this.Y, new Ys(23, this));
        this.d0.h0(cVar);
        int integer = contextThemeWrapper.getResources().getInteger(moe.shizuku.privileged.api.R.integer._0_resource_name_obfuscated_res_0x7f0a0034);
        RecyclerView recyclerView = (RecyclerView) viewInflate.findViewById(moe.shizuku.privileged.api.R.id._0_resource_name_obfuscated_res_0x7f090116);
        this.c0 = recyclerView;
        if (recyclerView != null) {
            recyclerView.t = true;
            recyclerView.i0(new GridLayoutManager(integer));
            this.c0.h0(new qu(this));
            this.c0.i(new Mh(this));
        }
        if (viewInflate.findViewById(moe.shizuku.privileged.api.R.id._0_resource_name_obfuscated_res_0x7f090109) != null) {
            MaterialButton materialButton = (MaterialButton) viewInflate.findViewById(moe.shizuku.privileged.api.R.id._0_resource_name_obfuscated_res_0x7f090109);
            materialButton.setTag("SELECTOR_TOGGLE_TAG");
            Hs.m(materialButton, new R5(2, this));
            View viewFindViewById = viewInflate.findViewById(moe.shizuku.privileged.api.R.id._0_resource_name_obfuscated_res_0x7f09010b);
            this.e0 = viewFindViewById;
            viewFindViewById.setTag("NAVIGATION_PREV_TAG");
            View viewFindViewById2 = viewInflate.findViewById(moe.shizuku.privileged.api.R.id._0_resource_name_obfuscated_res_0x7f09010a);
            this.f0 = viewFindViewById2;
            viewFindViewById2.setTag("NAVIGATION_NEXT_TAG");
            this.g0 = viewInflate.findViewById(moe.shizuku.privileged.api.R.id._0_resource_name_obfuscated_res_0x7f090116);
            this.h0 = viewInflate.findViewById(moe.shizuku.privileged.api.R.id._0_resource_name_obfuscated_res_0x7f09010f);
            N(1);
            materialButton.setText(this.Z.c());
            this.d0.j(new Nh(this, cVar, materialButton));
            materialButton.setOnClickListener(new ViewOnClickListenerC0572s0(2, this));
            this.f0.setOnClickListener(new Jh(this, cVar, 1));
            this.e0.setOnClickListener(new Jh(this, cVar, 0));
        }
        if (!Uh.S(contextThemeWrapper, R.attr.windowFullscreen)) {
            Xn xn = new Xn(6);
            RecyclerView recyclerView2 = this.d0;
            RecyclerView recyclerView3 = (RecyclerView) xn.a;
            if (recyclerView3 != recyclerView2) {
                C0473oo c0473oo = (C0473oo) xn.b;
                if (recyclerView3 != null) {
                    ArrayList arrayList = recyclerView3.h0;
                    if (arrayList != null) {
                        arrayList.remove(c0473oo);
                    }
                    ((RecyclerView) xn.a).U = null;
                }
                xn.a = recyclerView2;
                if (recyclerView2 != null) {
                    if (recyclerView2.U != null) {
                        throw new IllegalStateException("An instance of OnFlingListener already set.");
                    }
                    recyclerView2.j(c0473oo);
                    ((RecyclerView) xn.a).U = xn;
                    new Scroller(((RecyclerView) xn.a).getContext(), new DecelerateInterpolator());
                    xn.x();
                }
            }
        }
        this.d0.g0(cVar.d.a.d(this.Z));
        Hs.m(this.d0, new Kh(1));
        return viewInflate;
    }
}
