package rikka.shizuku;

import android.graphics.Outline;
import android.view.View;
import android.view.ViewOutlineProvider;
import com.google.android.material.chip.Chip;

/* JADX INFO: loaded from: classes.dex */
public final class Z5 extends ViewOutlineProvider {
    public final /* synthetic */ int a;
    public final /* synthetic */ Object b;

    public /* synthetic */ Z5(int i, Object obj) {
        this.a = i;
        this.b = obj;
    }

    @Override // android.view.ViewOutlineProvider
    public final void getOutline(View view, Outline outline) {
        switch (this.a) {
            case 0:
                C0086c6 c0086c6 = ((Chip) this.b).e;
                if (c0086c6 == null) {
                    outline.setAlpha(0.0f);
                } else {
                    c0086c6.getOutline(outline);
                }
                break;
            default:
                ((C0318jo) this.b).d().getOutline(outline);
                break;
        }
    }
}
