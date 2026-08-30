package rikka.shizuku;

import android.content.Context;
import android.graphics.drawable.Drawable;
import moe.shizuku.privileged.api.R;

/* JADX INFO: loaded from: classes.dex */
public final class B0 extends O2 implements D0 {
    public final /* synthetic */ C0 d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public B0(C0 c0, Context context) {
        super(context, null, R.attr._0_resource_name_obfuscated_res_0x7f04001f);
        this.d = c0;
        setClickable(true);
        setFocusable(true);
        setVisibility(0);
        setEnabled(true);
        AbstractC0688vn.y(this, getContentDescription());
        setOnTouchListener(new C0727x0(this, this));
    }

    @Override // rikka.shizuku.D0
    public final boolean a() {
        return false;
    }

    @Override // rikka.shizuku.D0
    public final boolean c() {
        return false;
    }

    @Override // android.view.View
    public final boolean performClick() {
        if (super.performClick()) {
            return true;
        }
        playSoundEffect(0);
        this.d.l();
        return true;
    }

    @Override // android.widget.ImageView
    public final boolean setFrame(int i, int i2, int i3, int i4) {
        boolean frame = super.setFrame(i, i2, i3, i4);
        Drawable drawable = getDrawable();
        Drawable background = getBackground();
        if (drawable != null && background != null) {
            int width = getWidth();
            int height = getHeight();
            int iMax = Math.max(width, height) / 2;
            int paddingLeft = (width + (getPaddingLeft() - getPaddingRight())) / 2;
            int paddingTop = (height + (getPaddingTop() - getPaddingBottom())) / 2;
            background.setHotspotBounds(paddingLeft - iMax, paddingTop - iMax, paddingLeft + iMax, paddingTop + iMax);
        }
        return frame;
    }
}
