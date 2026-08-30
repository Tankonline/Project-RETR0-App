package rikka.shizuku;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ListAdapter;
import androidx.appcompat.app.AlertController$RecycleListView;

/* JADX INFO: renamed from: rikka.shizuku.c3, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class DialogInterfaceOnClickListenerC0083c3 implements InterfaceC0268i3, DialogInterface.OnClickListener {
    public J1 a;
    public C0114d3 b;
    public CharSequence c;
    public final /* synthetic */ C0297j3 d;

    public DialogInterfaceOnClickListenerC0083c3(C0297j3 c0297j3) {
        this.d = c0297j3;
    }

    @Override // rikka.shizuku.InterfaceC0268i3
    public final boolean a() {
        J1 j1 = this.a;
        if (j1 != null) {
            return j1.isShowing();
        }
        return false;
    }

    @Override // rikka.shizuku.InterfaceC0268i3
    public final void b(int i) {
        Log.e("AppCompatSpinner", "Cannot set horizontal offset for MODE_DIALOG, ignoring");
    }

    @Override // rikka.shizuku.InterfaceC0268i3
    public final int c() {
        return 0;
    }

    @Override // rikka.shizuku.InterfaceC0268i3
    public final void d(int i, int i2) {
        if (this.b == null) {
            return;
        }
        C0297j3 c0297j3 = this.d;
        I1 i1 = new I1(c0297j3.b);
        CharSequence charSequence = this.c;
        E1 e1 = (E1) i1.b;
        if (charSequence != null) {
            e1.d = charSequence;
        }
        C0114d3 c0114d3 = this.b;
        int selectedItemPosition = c0297j3.getSelectedItemPosition();
        e1.q = c0114d3;
        e1.r = this;
        e1.w = selectedItemPosition;
        e1.v = true;
        J1 j1B = i1.b();
        this.a = j1B;
        AlertController$RecycleListView alertController$RecycleListView = j1B.f.f;
        AbstractC0021a3.d(alertController$RecycleListView, i);
        AbstractC0021a3.c(alertController$RecycleListView, i2);
        this.a.show();
    }

    @Override // rikka.shizuku.InterfaceC0268i3
    public final void dismiss() {
        J1 j1 = this.a;
        if (j1 != null) {
            j1.dismiss();
            this.a = null;
        }
    }

    @Override // rikka.shizuku.InterfaceC0268i3
    public final int f() {
        return 0;
    }

    @Override // rikka.shizuku.InterfaceC0268i3
    public final Drawable g() {
        return null;
    }

    @Override // rikka.shizuku.InterfaceC0268i3
    public final CharSequence h() {
        return this.c;
    }

    @Override // rikka.shizuku.InterfaceC0268i3
    public final void i(CharSequence charSequence) {
        this.c = charSequence;
    }

    @Override // rikka.shizuku.InterfaceC0268i3
    public final void k(Drawable drawable) {
        Log.e("AppCompatSpinner", "Cannot set popup background for MODE_DIALOG, ignoring");
    }

    @Override // rikka.shizuku.InterfaceC0268i3
    public final void l(int i) {
        Log.e("AppCompatSpinner", "Cannot set vertical offset for MODE_DIALOG, ignoring");
    }

    @Override // rikka.shizuku.InterfaceC0268i3
    public final void m(ListAdapter listAdapter) {
        this.b = (C0114d3) listAdapter;
    }

    @Override // rikka.shizuku.InterfaceC0268i3
    public final void n(int i) {
        Log.e("AppCompatSpinner", "Cannot set horizontal (original) offset for MODE_DIALOG, ignoring");
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        C0297j3 c0297j3 = this.d;
        c0297j3.setSelection(i);
        if (c0297j3.getOnItemClickListener() != null) {
            c0297j3.performItemClick(null, i, this.b.getItemId(i));
        }
        dismiss();
    }
}
