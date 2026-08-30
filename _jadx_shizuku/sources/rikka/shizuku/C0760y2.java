package rikka.shizuku;

import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import java.util.WeakHashMap;

/* JADX INFO: renamed from: rikka.shizuku.y2, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0760y2 extends AbstractC0688vn {
    public final /* synthetic */ int b;
    public final /* synthetic */ Object c;

    public /* synthetic */ C0760y2(int i, Object obj) {
        this.b = i;
        this.c = obj;
    }

    @Override // rikka.shizuku.InterfaceC0170et
    public final void a() {
        Object obj = this.c;
        switch (this.b) {
            case 0:
                I2 i2 = ((RunnableC0698w2) obj).b;
                i2.v.setAlpha(1.0f);
                i2.y.d(null);
                i2.y = null;
                break;
            case 1:
                I2 i3 = (I2) obj;
                i3.v.setAlpha(1.0f);
                i3.y.d(null);
                i3.y = null;
                break;
            default:
                R2 r2 = (R2) obj;
                ((I2) r2.c).v.setVisibility(8);
                I2 i4 = (I2) r2.c;
                PopupWindow popupWindow = i4.w;
                if (popupWindow != null) {
                    popupWindow.dismiss();
                } else if (i4.v.getParent() instanceof View) {
                    View view = (View) i4.v.getParent();
                    WeakHashMap weakHashMap = Hs.a;
                    AbstractC0755xs.c(view);
                }
                i4.v.f();
                i4.y.d(null);
                i4.y = null;
                ViewGroup viewGroup = i4.A;
                WeakHashMap weakHashMap2 = Hs.a;
                AbstractC0755xs.c(viewGroup);
                break;
        }
    }

    @Override // rikka.shizuku.AbstractC0688vn, rikka.shizuku.InterfaceC0170et
    public void c() {
        Object obj = this.c;
        switch (this.b) {
            case 0:
                ((RunnableC0698w2) obj).b.v.setVisibility(0);
                break;
            case 1:
                I2 i2 = (I2) obj;
                i2.v.setVisibility(0);
                if (i2.v.getParent() instanceof View) {
                    View view = (View) i2.v.getParent();
                    WeakHashMap weakHashMap = Hs.a;
                    AbstractC0755xs.c(view);
                }
                break;
        }
    }
}
