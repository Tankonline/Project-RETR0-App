package rikka.shizuku;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.PointF;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public final class O5 extends AbstractC0722wq {
    public static final String[] C = {"android:changeBounds:bounds", "android:changeBounds:clip", "android:changeBounds:parent", "android:changeBounds:windowX", "android:changeBounds:windowY"};
    public static final K5 D = new K5(PointF.class, "topLeft", 0);
    public static final K5 E = new K5(PointF.class, "bottomRight", 1);
    public static final K5 F = new K5(PointF.class, "bottomRight", 2);
    public static final K5 G = new K5(PointF.class, "topLeft", 3);
    public static final K5 H = new K5(PointF.class, "position", 4);

    public static void L(Eq eq) {
        View view = eq.b;
        if (!view.isLaidOut() && view.getWidth() == 0 && view.getHeight() == 0) {
            return;
        }
        HashMap map = eq.a;
        map.put("android:changeBounds:bounds", new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom()));
        map.put("android:changeBounds:parent", eq.b.getParent());
    }

    @Override // rikka.shizuku.AbstractC0722wq
    public final void d(Eq eq) {
        L(eq);
    }

    @Override // rikka.shizuku.AbstractC0722wq
    public final void g(Eq eq) {
        L(eq);
    }

    @Override // rikka.shizuku.AbstractC0722wq
    public final Animator k(ViewGroup viewGroup, Eq eq, Eq eq2) {
        int i;
        O5 o5;
        Animator animatorA;
        if (eq != null && eq2 != null) {
            HashMap map = eq.a;
            HashMap map2 = eq2.a;
            ViewGroup viewGroup2 = (ViewGroup) map.get("android:changeBounds:parent");
            ViewGroup viewGroup3 = (ViewGroup) map2.get("android:changeBounds:parent");
            if (viewGroup2 != null && viewGroup3 != null) {
                Rect rect = (Rect) map.get("android:changeBounds:bounds");
                Rect rect2 = (Rect) map2.get("android:changeBounds:bounds");
                int i2 = rect.left;
                int i3 = rect2.left;
                int i4 = rect.top;
                int i5 = rect2.top;
                int i6 = rect.right;
                int i7 = rect2.right;
                int i8 = rect.bottom;
                int i9 = rect2.bottom;
                int i10 = i6 - i2;
                int i11 = i8 - i4;
                int i12 = i7 - i3;
                int i13 = i9 - i5;
                Rect rect3 = (Rect) map.get("android:changeBounds:clip");
                Rect rect4 = (Rect) map2.get("android:changeBounds:clip");
                if ((i10 == 0 || i11 == 0) && (i12 == 0 || i13 == 0)) {
                    i = 0;
                } else {
                    i = (i2 == i3 && i4 == i5) ? 0 : 1;
                    if (i6 != i7 || i8 != i9) {
                        i++;
                    }
                }
                if ((rect3 != null && !rect3.equals(rect4)) || (rect3 == null && rect4 != null)) {
                    i++;
                }
                if (i > 0) {
                    View view = eq2.b;
                    AbstractC0263ht.a(view, i2, i4, i6, i8);
                    if (i != 2) {
                        o5 = this;
                        if (i2 == i3 && i4 == i5) {
                            o5.v.getClass();
                            animatorA = AbstractC0222gj.a(view, F, Pe.b(i6, i8, i7, i9));
                        } else {
                            o5.v.getClass();
                            animatorA = AbstractC0222gj.a(view, G, Pe.b(i2, i4, i3, i5));
                        }
                    } else if (i10 == i12 && i11 == i13) {
                        o5 = this;
                        o5.v.getClass();
                        animatorA = AbstractC0222gj.a(view, H, Pe.b(i2, i4, i3, i5));
                    } else {
                        o5 = this;
                        N5 n5 = new N5(view);
                        o5.v.getClass();
                        ObjectAnimator objectAnimatorA = AbstractC0222gj.a(n5, D, Pe.b(i2, i4, i3, i5));
                        o5.v.getClass();
                        ObjectAnimator objectAnimatorA2 = AbstractC0222gj.a(n5, E, Pe.b(i6, i8, i7, i9));
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.playTogether(objectAnimatorA, objectAnimatorA2);
                        animatorSet.addListener(new L5(n5));
                        animatorA = animatorSet;
                    }
                    if (view.getParent() instanceof ViewGroup) {
                        ViewGroup viewGroup4 = (ViewGroup) view.getParent();
                        Hn.n(viewGroup4, true);
                        o5.o().a(new M5(viewGroup4));
                    }
                    return animatorA;
                }
            }
        }
        return null;
    }

    @Override // rikka.shizuku.AbstractC0722wq
    public final String[] q() {
        return C;
    }
}
