package rikka.shizuku;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;

/* JADX INFO: loaded from: classes.dex */
public final class Ee implements U7, Gm, InterfaceC0180f8 {
    public static final /* synthetic */ Ee a = new Ee();

    public static Ee c(Context context, int i) {
        AbstractC0046as.h("Cannot create a CalendarItemStyle with a styleResId of 0", i != 0);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(i, Lk.q);
        Rect rect = new Rect(typedArrayObtainStyledAttributes.getDimensionPixelOffset(0, 0), typedArrayObtainStyledAttributes.getDimensionPixelOffset(2, 0), typedArrayObtainStyledAttributes.getDimensionPixelOffset(1, 0), typedArrayObtainStyledAttributes.getDimensionPixelOffset(3, 0));
        X8.w(context, typedArrayObtainStyledAttributes, 4);
        X8.w(context, typedArrayObtainStyledAttributes, 9);
        X8.w(context, typedArrayObtainStyledAttributes, 7);
        typedArrayObtainStyledAttributes.getDimensionPixelSize(8, 0);
        C0317jn.b(context, typedArrayObtainStyledAttributes.getResourceId(5, 0), typedArrayObtainStyledAttributes.getResourceId(6, 0), new G(0)).a();
        typedArrayObtainStyledAttributes.recycle();
        Ee ee = new Ee();
        AbstractC0046as.i(rect.left);
        AbstractC0046as.i(rect.top);
        AbstractC0046as.i(rect.right);
        AbstractC0046as.i(rect.bottom);
        return ee;
    }

    @Override // rikka.shizuku.Gm
    public void a(int i, int i2, int i3, boolean z) {
    }

    @Override // rikka.shizuku.Gm
    public void b(int i, int i2, int i3, int i4) {
    }
}
