package rikka.shizuku;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.WeakHashMap;
import moe.shizuku.privileged.api.R;

/* JADX INFO: loaded from: classes.dex */
public abstract class Qk extends ConstraintLayout {
    public final G0 o;
    public int p;
    public final Xh q;

    public Qk(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, R.attr._0_resource_name_obfuscated_res_0x7f040307);
        LayoutInflater.from(context).inflate(R.layout._0_resource_name_obfuscated_res_0x7f0c005a, this);
        Xh xh = new Xh();
        this.q = xh;
        Dl dl = new Dl(0.5f);
        C0317jn c0317jnG = xh.a.a.g();
        c0317jnG.e = dl;
        c0317jnG.f = dl;
        c0317jnG.g = dl;
        c0317jnG.h = dl;
        xh.a(c0317jnG.a());
        this.q.l(ColorStateList.valueOf(-1));
        Xh xh2 = this.q;
        WeakHashMap weakHashMap = Hs.a;
        setBackground(xh2);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Lk.z, R.attr._0_resource_name_obfuscated_res_0x7f040307, 0);
        this.p = typedArrayObtainStyledAttributes.getDimensionPixelSize(0, 0);
        this.o = new G0(13, this);
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // android.view.ViewGroup
    public final void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        super.addView(view, i, layoutParams);
        if (view.getId() == -1) {
            WeakHashMap weakHashMap = Hs.a;
            view.setId(View.generateViewId());
        }
        Handler handler = getHandler();
        if (handler != null) {
            G0 g0 = this.o;
            handler.removeCallbacks(g0);
            handler.post(g0);
        }
    }

    public abstract void f();

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        f();
    }

    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.ViewGroup
    public final void onViewRemoved(View view) {
        super.onViewRemoved(view);
        Handler handler = getHandler();
        if (handler != null) {
            G0 g0 = this.o;
            handler.removeCallbacks(g0);
            handler.post(g0);
        }
    }

    @Override // android.view.View
    public final void setBackgroundColor(int i) {
        this.q.l(ColorStateList.valueOf(i));
    }
}
