package rikka.shizuku;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public final class Dh extends ArrayAdapter {
    public final ColorStateList a;
    public final ColorStateList b;
    public final /* synthetic */ Eh c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Dh(Eh eh, Context context, int i, String[] strArr) {
        ColorStateList colorStateList;
        ColorStateList colorStateList2;
        super(context, i, strArr);
        this.c = eh;
        ColorStateList colorStateList3 = eh.k;
        ColorStateList colorStateList4 = null;
        if (colorStateList3 != null) {
            int[] iArr = {R.attr.state_pressed};
            colorStateList = new ColorStateList(new int[][]{iArr, new int[0]}, new int[]{colorStateList3.getColorForState(iArr, 0), 0});
        } else {
            colorStateList = null;
        }
        this.b = colorStateList;
        if (eh.j != 0 && (colorStateList2 = eh.k) != null) {
            int[] iArr2 = {R.attr.state_hovered, -16842919};
            int[] iArr3 = {R.attr.state_selected, -16842919};
            int colorForState = colorStateList2.getColorForState(iArr3, 0);
            int colorForState2 = eh.k.getColorForState(iArr2, 0);
            int i2 = eh.j;
            colorStateList4 = new ColorStateList(new int[][]{iArr3, iArr2, new int[0]}, new int[]{E6.c(colorForState, i2), E6.c(colorForState2, i2), i2});
        }
        this.a = colorStateList4;
    }

    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    public final View getView(int i, View view, ViewGroup viewGroup) {
        ColorStateList colorStateList = this.b;
        View view2 = super.getView(i, view, viewGroup);
        if (view2 instanceof TextView) {
            TextView textView = (TextView) view2;
            Eh eh = this.c;
            Drawable rippleDrawable = null;
            if (eh.getText().toString().contentEquals(textView.getText()) && eh.j != 0) {
                ColorDrawable colorDrawable = new ColorDrawable(eh.j);
                if (colorStateList != null) {
                    colorDrawable.setTintList(this.a);
                    rippleDrawable = new RippleDrawable(colorStateList, colorDrawable, null);
                } else {
                    rippleDrawable = colorDrawable;
                }
            }
            WeakHashMap weakHashMap = Hs.a;
            textView.setBackground(rippleDrawable);
        }
        return view2;
    }
}
