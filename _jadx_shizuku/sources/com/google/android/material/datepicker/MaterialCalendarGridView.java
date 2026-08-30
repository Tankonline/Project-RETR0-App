package com.google.android.material.datepicker;

import android.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import rikka.shizuku.Fi;
import rikka.shizuku.Hs;
import rikka.shizuku.Kh;
import rikka.shizuku.Uh;
import rikka.shizuku.Zr;

/* JADX INFO: loaded from: classes.dex */
final class MaterialCalendarGridView extends GridView {
    public final boolean a;

    public MaterialCalendarGridView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
        Zr.c(null);
        if (Uh.S(getContext(), R.attr.windowFullscreen)) {
            setNextFocusLeftId(moe.shizuku.privileged.api.R.id._0_resource_name_obfuscated_res_0x7f090062);
            setNextFocusRightId(moe.shizuku.privileged.api.R.id._0_resource_name_obfuscated_res_0x7f090074);
        }
        this.a = Uh.S(getContext(), moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f040366);
        Hs.m(this, new Kh(2));
    }

    public final Fi a() {
        return (Fi) super.getAdapter();
    }

    @Override // android.widget.GridView, android.widget.AdapterView
    public final Adapter getAdapter() {
        return (Fi) super.getAdapter();
    }

    @Override // android.widget.AbsListView, android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        ((Fi) super.getAdapter()).notifyDataSetChanged();
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Fi fi = (Fi) super.getAdapter();
        fi.getClass();
        int iMax = Math.max(fi.a(), getFirstVisiblePosition());
        int iMin = Math.min(fi.c(), getLastVisiblePosition());
        fi.getItem(iMax);
        fi.getItem(iMin);
        throw null;
    }

    @Override // android.widget.GridView, android.widget.AbsListView, android.view.View
    public final void onFocusChanged(boolean z, int i, Rect rect) {
        if (!z) {
            super.onFocusChanged(false, i, rect);
            return;
        }
        if (i == 33) {
            setSelection(((Fi) super.getAdapter()).c());
        } else if (i == 130) {
            setSelection(((Fi) super.getAdapter()).a());
        } else {
            super.onFocusChanged(true, i, rect);
        }
    }

    @Override // android.widget.GridView, android.widget.AbsListView, android.view.View, android.view.KeyEvent.Callback
    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (!super.onKeyDown(i, keyEvent)) {
            return false;
        }
        if (getSelectedItemPosition() == -1 || getSelectedItemPosition() >= ((Fi) super.getAdapter()).a()) {
            return true;
        }
        if (19 != i) {
            return false;
        }
        setSelection(((Fi) super.getAdapter()).a());
        return true;
    }

    @Override // android.widget.GridView, android.widget.AbsListView, android.view.View
    public final void onMeasure(int i, int i2) {
        if (!this.a) {
            super.onMeasure(i, i2);
            return;
        }
        super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(16777215, Integer.MIN_VALUE));
        getLayoutParams().height = getMeasuredHeight();
    }

    @Override // android.widget.GridView, android.widget.AdapterView
    public final void setSelection(int i) {
        if (i < ((Fi) super.getAdapter()).a()) {
            super.setSelection(((Fi) super.getAdapter()).a());
        } else {
            super.setSelection(i);
        }
    }

    @Override // android.widget.GridView, android.widget.AdapterView
    public final ListAdapter getAdapter() {
        return (Fi) super.getAdapter();
    }

    @Override // android.widget.AdapterView
    public final void setAdapter(ListAdapter listAdapter) {
        if (!(listAdapter instanceof Fi)) {
            throw new IllegalArgumentException(String.format("%1$s must have its Adapter set to a %2$s", MaterialCalendarGridView.class.getCanonicalName(), Fi.class.getCanonicalName()));
        }
        super.setAdapter(listAdapter);
    }
}
