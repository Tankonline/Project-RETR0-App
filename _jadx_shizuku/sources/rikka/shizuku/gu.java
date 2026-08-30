package rikka.shizuku;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import java.util.WeakHashMap;
import moe.shizuku.privileged.api.R;

/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class gu implements InterfaceC0157eg {
    @Override // rikka.shizuku.InterfaceC0157eg
    public final void a(View view, Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = view.getContext().obtainStyledAttributes(attributeSet, Bk.a, 0, 0);
        boolean z = typedArrayObtainStyledAttributes.getType(1) == 1 ? view.getContext().getResources().getBoolean(typedArrayObtainStyledAttributes.getResourceId(1, 0)) : typedArrayObtainStyledAttributes.getBoolean(1, false);
        int iC = AbstractC0750xn.c(typedArrayObtainStyledAttributes, view, 2);
        int iC2 = AbstractC0750xn.c(typedArrayObtainStyledAttributes, view, 3);
        int iC3 = AbstractC0750xn.c(typedArrayObtainStyledAttributes, view, 0);
        typedArrayObtainStyledAttributes.recycle();
        if (z) {
            view.setSystemUiVisibility(view.getSystemUiVisibility() | 1792);
        }
        if (iC == 0 && iC2 == 0 && iC3 == 0) {
            return;
        }
        hu huVar = new hu(view, iC, iC2, iC3);
        WeakHashMap weakHashMap = Hs.a;
        AbstractC0817zs.l(view, huVar);
        view.setTag(R.id._0_resource_name_obfuscated_res_0x7f0901c2, huVar);
        if (view.isAttachedToWindow()) {
            return;
        }
        view.addOnAttachStateChangeListener(new ViewOnAttachStateChangeListenerC0201ft(1));
    }
}
