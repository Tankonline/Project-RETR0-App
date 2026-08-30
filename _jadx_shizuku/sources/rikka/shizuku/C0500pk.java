package rikka.shizuku;

import android.R;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

/* JADX INFO: renamed from: rikka.shizuku.pk, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0500pk extends AbstractC0470ol {
    public final Drawable u;
    public final ColorStateList v;
    public final SparseArray w;
    public boolean x;
    public boolean y;

    public C0500pk(View view) {
        super(view);
        SparseArray sparseArray = new SparseArray(4);
        this.w = sparseArray;
        TextView textView = (TextView) view.findViewById(R.id.title);
        sparseArray.put(R.id.title, textView);
        sparseArray.put(R.id.summary, view.findViewById(R.id.summary));
        sparseArray.put(R.id.icon, view.findViewById(R.id.icon));
        sparseArray.put(moe.shizuku.privileged.api.R.id._0_resource_name_obfuscated_res_0x7f0900cd, view.findViewById(moe.shizuku.privileged.api.R.id._0_resource_name_obfuscated_res_0x7f0900cd));
        sparseArray.put(R.id.icon_frame, view.findViewById(R.id.icon_frame));
        this.u = view.getBackground();
        if (textView != null) {
            this.v = textView.getTextColors();
        }
    }

    public final View q(int i) {
        SparseArray sparseArray = this.w;
        View view = (View) sparseArray.get(i);
        if (view != null) {
            return view;
        }
        View viewFindViewById = this.a.findViewById(i);
        if (viewFindViewById != null) {
            sparseArray.put(i, viewFindViewById);
        }
        return viewFindViewById;
    }
}
