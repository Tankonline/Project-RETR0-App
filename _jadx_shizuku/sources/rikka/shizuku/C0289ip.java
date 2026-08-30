package rikka.shizuku;

import android.content.Context;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

/* JADX INFO: renamed from: rikka.shizuku.ip, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0289ip extends ActionMode {
    public final Context a;
    public final F0 b;

    public C0289ip(Context context, F0 f0) {
        this.a = context;
        this.b = f0;
    }

    @Override // android.view.ActionMode
    public final void finish() {
        this.b.b();
    }

    @Override // android.view.ActionMode
    public final View getCustomView() {
        return this.b.c();
    }

    @Override // android.view.ActionMode
    public final Menu getMenu() {
        return new MenuC0776yi(this.a, this.b.d());
    }

    @Override // android.view.ActionMode
    public final MenuInflater getMenuInflater() {
        return this.b.e();
    }

    @Override // android.view.ActionMode
    public final CharSequence getSubtitle() {
        return this.b.f();
    }

    @Override // android.view.ActionMode
    public final Object getTag() {
        return this.b.a;
    }

    @Override // android.view.ActionMode
    public final CharSequence getTitle() {
        return this.b.g();
    }

    @Override // android.view.ActionMode
    public final boolean getTitleOptionalHint() {
        return this.b.b;
    }

    @Override // android.view.ActionMode
    public final void invalidate() {
        this.b.h();
    }

    @Override // android.view.ActionMode
    public final boolean isTitleOptional() {
        return this.b.i();
    }

    @Override // android.view.ActionMode
    public final void setCustomView(View view) {
        this.b.j(view);
    }

    @Override // android.view.ActionMode
    public final void setSubtitle(CharSequence charSequence) {
        this.b.l(charSequence);
    }

    @Override // android.view.ActionMode
    public final void setTag(Object obj) {
        this.b.a = obj;
    }

    @Override // android.view.ActionMode
    public final void setTitle(CharSequence charSequence) {
        this.b.o(charSequence);
    }

    @Override // android.view.ActionMode
    public final void setTitleOptionalHint(boolean z) {
        this.b.p(z);
    }

    @Override // android.view.ActionMode
    public final void setSubtitle(int i) {
        this.b.k(i);
    }

    @Override // android.view.ActionMode
    public final void setTitle(int i) {
        this.b.m(i);
    }
}
