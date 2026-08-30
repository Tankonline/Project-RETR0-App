package rikka.shizuku;

import android.content.res.Resources;
import android.graphics.Rect;
import android.view.View;
import androidx.appcompat.widget.SearchView;
import moe.shizuku.privileged.api.R;

/* JADX INFO: renamed from: rikka.shizuku.i5, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class ViewOnLayoutChangeListenerC0270i5 implements View.OnLayoutChangeListener {
    public final /* synthetic */ int a;
    public final /* synthetic */ Object b;

    public /* synthetic */ ViewOnLayoutChangeListenerC0270i5(int i, Object obj) {
        this.a = i;
        this.b = obj;
    }

    @Override // android.view.View.OnLayoutChangeListener
    public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        int dimensionPixelSize;
        switch (this.a) {
            case 0:
                C0287in c0287in = (C0287in) this.b;
                if (((Boolean) c0287in.e) == null || ((Boolean) c0287in.f) == null) {
                    c0287in.e = Boolean.valueOf(c0287in.b());
                    c0287in.f = Boolean.valueOf(c0287in.a());
                }
                view.removeOnLayoutChangeListener(this);
                return;
            case 1:
                throw null;
            default:
                SearchView searchView = (SearchView) this.b;
                View view2 = searchView.x;
                if (view2.getWidth() > 1) {
                    Resources resources = searchView.getContext().getResources();
                    int paddingLeft = searchView.r.getPaddingLeft();
                    Rect rect = new Rect();
                    boolean zA = AbstractC0323jt.a(searchView);
                    if (searchView.G) {
                        dimensionPixelSize = resources.getDimensionPixelSize(R.dimen._0_resource_name_obfuscated_res_0x7f07002a) + resources.getDimensionPixelSize(R.dimen._0_resource_name_obfuscated_res_0x7f070029);
                    } else {
                        dimensionPixelSize = 0;
                    }
                    SearchView.SearchAutoComplete searchAutoComplete = searchView.p;
                    searchAutoComplete.getDropDownBackground().getPadding(rect);
                    searchAutoComplete.setDropDownHorizontalOffset(zA ? -rect.left : paddingLeft - (rect.left + dimensionPixelSize));
                    searchAutoComplete.setDropDownWidth((((view2.getWidth() + rect.left) + rect.right) + dimensionPixelSize) - paddingLeft);
                    return;
                }
                return;
        }
    }
}
