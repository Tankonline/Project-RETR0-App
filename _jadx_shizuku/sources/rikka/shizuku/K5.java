package rikka.shizuku;

import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Property;
import android.view.View;
import androidx.appcompat.widget.SwitchCompat;
import rikka.html.text.style.BulletSpan;

/* JADX INFO: loaded from: classes.dex */
public final class K5 extends Property {
    public final /* synthetic */ int a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ K5(Class cls, String str, int i) {
        super(cls, str);
        this.a = i;
    }

    @Override // android.util.Property
    public final Object get(Object obj) {
        switch (this.a) {
            case 0:
                return null;
            case 1:
                return null;
            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                return null;
            case 3:
                return null;
            case 4:
                return null;
            case BulletSpan.STANDARD_BULLET_RADIUS /* 5 */:
                return Float.valueOf(((SwitchCompat) obj).z);
            case 6:
                return Float.valueOf(AbstractC0263ht.a.c((View) obj));
            default:
                return ((View) obj).getClipBounds();
        }
    }

    @Override // android.util.Property
    public final void set(Object obj, Object obj2) {
        switch (this.a) {
            case 0:
                N5 n5 = (N5) obj;
                PointF pointF = (PointF) obj2;
                n5.getClass();
                n5.a = Math.round(pointF.x);
                int iRound = Math.round(pointF.y);
                n5.b = iRound;
                int i = n5.f + 1;
                n5.f = i;
                if (i == n5.g) {
                    AbstractC0263ht.a(n5.e, n5.a, iRound, n5.c, n5.d);
                    n5.f = 0;
                    n5.g = 0;
                }
                break;
            case 1:
                N5 n6 = (N5) obj;
                PointF pointF2 = (PointF) obj2;
                n6.getClass();
                n6.c = Math.round(pointF2.x);
                int iRound2 = Math.round(pointF2.y);
                n6.d = iRound2;
                int i2 = n6.g + 1;
                n6.g = i2;
                if (n6.f == i2) {
                    AbstractC0263ht.a(n6.e, n6.a, n6.b, n6.c, iRound2);
                    n6.f = 0;
                    n6.g = 0;
                }
                break;
            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                View view = (View) obj;
                PointF pointF3 = (PointF) obj2;
                AbstractC0263ht.a(view, view.getLeft(), view.getTop(), Math.round(pointF3.x), Math.round(pointF3.y));
                break;
            case 3:
                View view2 = (View) obj;
                PointF pointF4 = (PointF) obj2;
                AbstractC0263ht.a(view2, Math.round(pointF4.x), Math.round(pointF4.y), view2.getRight(), view2.getBottom());
                break;
            case 4:
                View view3 = (View) obj;
                PointF pointF5 = (PointF) obj2;
                int iRound3 = Math.round(pointF5.x);
                int iRound4 = Math.round(pointF5.y);
                AbstractC0263ht.a(view3, iRound3, iRound4, view3.getWidth() + iRound3, view3.getHeight() + iRound4);
                break;
            case BulletSpan.STANDARD_BULLET_RADIUS /* 5 */:
                SwitchCompat switchCompat = (SwitchCompat) obj;
                switchCompat.z = ((Float) obj2).floatValue();
                switchCompat.invalidate();
                break;
            case 6:
                float fFloatValue = ((Float) obj2).floatValue();
                AbstractC0263ht.a.p((View) obj, fFloatValue);
                break;
            default:
                ((View) obj).setClipBounds((Rect) obj2);
                break;
        }
    }
}
